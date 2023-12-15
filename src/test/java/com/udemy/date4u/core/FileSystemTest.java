package com.udemy.date4u.core;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class FileSystemTest {

    @Test
    @DisplayName("free disk space has to be positive")  // Optional, Namen der Methode im Test einblenden.
    // Methode sollte passenden Namen für den Testfall haben (freeDiskSpace -> freeDiskSpaceHasToBePositive):
    void freeDiskSpaceHasToBePositive() {
        // given:
        FileSystem fileSystem = new FileSystem();

        // when:
        long freeDiskSpace = fileSystem.getFreeDiskSpace();

        // then:
        // assertTrue(freeDiskSpace > 0, "Free disk space was not > 0");
        // Alternative statische Methode aus dem Paket org.assertj.core.api.Assertions:
        // Sie ist ganz praktisch, da es eine große Anzahl an vorgefertigten Annahmen gibt, welche die Fehler beschreiben.
        assertThat(freeDiskSpace).isGreaterThan(0);
    }

    @Test
    void storeAndLoadIsSuccessful() {
        // given:
        FileSystem fileSystem = new FileSystem();
        // when:
        fileSystem.store("test.txt", "Hello World".getBytes());
        byte[] content = fileSystem.load("test.txt");
        // then:
        assertThat(content).containsExactly("Hello World".getBytes());
    }

    @Test
    void loadUnknownFileThrowsException() {
        // given:
        FileSystem fileSystem = new FileSystem();
        // when:
        //byte[] nothing = fileSystem.load(UUID.randomUUID().toString());
        // then:
        assertThatThrownBy(() -> {
            fileSystem.load(UUID.randomUUID().toString());
        } ).isInstanceOf(UncheckedIOException.class);
    }

    @Test
    // Auf Sicherheitslücke testen:
    void loadArbitraryFile() throws IOException {
        FileSystem fileSystem = new FileSystem();
        assertThatThrownBy( () -> {
            fileSystem.load("../../../../../../../windows/notepad.exe");
        }).isInstanceOf(SecurityException.class)
                .hasMessageContaining("Access to")
                .hasMessageContaining("denied");

//        byte[] load = fileSystem.load("../../../../../../../windows/notepad.exe");
//        assertThat(load).isNotEmpty();  // True! Krass...
//
//        // Notepad tatsächlich vorhanden und ladbar:
//        System.out.println(Paths.get("../../../../../../../windows/notepad.exe").toRealPath());
    }
}