package com.udemy.date4u.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

// Wie im Tutorial beschrieben erstellen wir nun eine eigene Properties-Klasse, die die Hierarchie bei
// date4u.filesystem.minimum-free-disk-space darstellt.
@Component
@ConfigurationProperties("date4u")
public class Date4uProperties {
    private FileSystem fileSystem = new FileSystem(); // Falls FileSystem in Properties nicht gefunden wird, referenziere
    // ich somit sicherheitshalber jedes Mal auf eins.

    public FileSystem getFileSystem() { return fileSystem; }

    public void setFileSystem( FileSystem fileSystem ) { this.fileSystem = fileSystem; }

    public static class FileSystem {

        /*
        * Required minimum free disk space for local filesystem.
         */
        private long minimumFreeDiskSpace = 1_000_000;  // Default value gesetzt, falls Wert in Properties nicht gefunden.

        public long getMinimumFreeDiskSpace() {
            return minimumFreeDiskSpace;
        }

        public void setMinimumFreeDiskSpace( long minimumFreeDiskSpace ) {
            this.minimumFreeDiskSpace = minimumFreeDiskSpace;
        }
    }
}