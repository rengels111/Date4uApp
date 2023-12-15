package com.udemy.date4u.core;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

// mit @Component wird dem Spring Framework mitgeteilt, dass diese Klasse eine Komponente werden soll und
// automatisch erkannt werden soll. Damit wird die Klasse erzeugt und angemeldet.
@Service
public class FileSystem {

    // ein Pfad-Objekt aufbauen, Pfad vom eigenen Benutzerverzeichnis mit Paths.get(System.getProperty...
    // resolve("fs") erzeugt ein Unterverzeichnis des Benutzerverzeichnisses:
    private final Path root = Paths.get(System.getProperty("user.home")).resolve("fs");
    public FileSystem() {
        // Falls Datei nicht existiert, wird sie erzeugt:
        if (!Files.isDirectory(root)) {
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Freien Speicherplatz zurückgeben:
    public long getFreeDiskSpace() {
        return root.toFile().getFreeSpace();
    }

    // soll etwas von einer Datei laden:
    public byte[] load(String filename) {
        try {
            Path path = resolve(filename);
            return Files.readAllBytes(path);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // soll bytes in einer Datei speichern:

    public void store(String filename, byte[] bytes) {
        try {
            Files.write(resolve(filename), bytes);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // Methode erstellt, nachdem im Test festgestellt wurde, dass auf sensible Inhalte auf der Festplatte zugegriffen
    // werden konnte:
    private Path resolve(String filename) {
        Path path = root.resolve(filename).toAbsolutePath().normalize();
        if (!path.startsWith(root))
            throw new SecurityException("Access to " + path + " denied");
        return path;
    }
}
