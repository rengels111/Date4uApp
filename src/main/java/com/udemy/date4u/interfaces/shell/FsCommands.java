package com.udemy.date4u.interfaces.shell;

import com.udemy.date4u.configuration.Date4uProperties;
import com.udemy.date4u.core.FileSystem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.core.Converter;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.unit.DataSize;

import java.nio.file.Path;

// Wir bauen uns eine Klasse, um per Konsole (Shell) mit dem laufenden Programm zu interagieren.

// Das ist unsere Klasse für die Shell Anfragen von außen.
// Spezielle ShellComponent wird implementiert
@ShellComponent
public class FsCommands {

    // Da wir DI anwenden, klammern wir das nun aus:
    // private final FileSystem fs = new FileSystem();

    // Und nun wird FileSystem mit Constructor Injection erzeugt:

    // Wir erzeugen erst eine Variable:
    private final FileSystem fs;
    //private final long minimumFreeDiskSpace;
    //private final Environment env;

    private final Date4uProperties date4uProperties;

    // Mit Autowired wird dieses Bean automatisch mit den anderen Beans vernetzt:
    @Autowired
    // nun wird FileSystem in Konstruktor übergeben, sodass FileSystem erst erzeugt werden muss, damit der
    // Konstruktor ausgeführt werden kann. 1. FileSystem erzeugen, 2. An Konstruktor übergeben und ausführen:
    public FsCommands( FileSystem fs, Date4uProperties date4uProperties
            /* @Value( "${date4u.filesystem.minimum-free-disk-space:1000000}") long minimumFreeDiskSpace, Environment env*/ ) {
        this.fs = fs;
        // this.minimumFreeDiskSpace = minimumFreeDiskSpace;
        // this.env = env;
        this.date4uProperties = date4uProperties;
    }

    /*
    Alternativ mit Setter-Injection:

    private FileSystem fs;

    @Autowired
    public void setFileSystem(FileSystem fs) {
        this.fs = fs;
    }

    Alternativ mit Field-Injection

    @Autowired private FileSystem fs; // hier wird @Autowired direkt neben die Variable geschrieben, da wir uns etwas
    // von der Objekt-Variable wünschen.
     */

    // Damit diese Methode als Shell Methode erkannt wird, müssen wir noch etwas dafür tun.
    // Sie braucht eine Dokumentation: Annotation ShellMethod
    @ShellMethod("Display required free disk space")
    // Dafür brauchen wir bestimmte Methoden, wie:
    public long minimumFreeDiskSpace() {
        //return 1_000_000;
        // return minimumFreeDiskSpace;
        // Da wir die Properties nun in einer Klasse als Objekt gemappt haben, fügen wir die Daten auf diese Weise ein:
        return date4uProperties.getFileSystem().getMinimumFreeDiskSpace();
    }

    /* Kleine Beispiel Shell Methode, wie wir etwas von außen entgegennehmen
    @ShellMethod("Lowercase")
    public String toLowercase(String input) {return input.toLowerCase(Locale.ROOT);}
    */

    @ShellMethod("Display free disk space")
    public String freeDiskSpace() {
        return DataSize.ofBytes(fs.getFreeDiskSpace()).toGigabytes() + " GB";
    }

    /* Externe Konfiguration und Environment Beispiel:
    @ShellMethod("Display user home")
    public String userHome() {
        return env.getProperty( "user.name" );
    }
     */

    @ShellMethod("Display if a path exists")
    public boolean exists(Path path) {
        System.out.println(path);
        return true;
    }
}
