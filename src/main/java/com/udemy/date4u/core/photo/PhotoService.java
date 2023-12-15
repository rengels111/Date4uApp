package com.udemy.date4u.core.photo;

import com.udemy.date4u.core.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.Optional;
import java.util.UUID;

@Service
public class PhotoService {

    // Erstmal ohne IoC bzw. DI
    //private final FileSystem fs = new FileSystem();

    // Und nun mit DI:
    private final FileSystem fs;
    private final Thumbnail thumbnail;  // hier können wir nun den Basistyp Thumbnail aus der Schnittstelle nehmen

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    public PhotoService( FileSystem fs, Thumbnail thumbnail ) {  // und hier wird der Basistyp Thumbnail aus der
        // Schnittstelle injiziert und somit nur die Methode thumbnail hergeholt ohne die private create Methode von Awt
        this.fs = fs;
        this.thumbnail = thumbnail;
    }

    /*
    Da das FileSystem eine Ausnahme auslöst, falls die Datei nicht vorhanden ist und wir diese nicht haben
    möchten, wenden wir hierfür einen anderen Rückgabetyp an: Optional vom byte[]
    Falls die Datei also nicht vorhanden sein sollte, dann wird die Ausnahme abgefangen und auf ein Optional empty
    gemappt:
    */

    @Cacheable( "date4u.filesystem.file" )  // zum cachen, falls Datei bereits existiert.
    public Optional<byte[]> download(String imageName) {
        try {
            log.info("Load image {}", imageName);  // zur Prüfung, ob das Bild neu geladen wird oder aus dem Cache kommt.
            return Optional.ofNullable(fs.load(imageName + ".jpg"));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public String upload( byte[] imageBytes ) {
        String imageName = UUID.randomUUID().toString();

        // First: store original image
        fs.store( imageName + ".jpg", imageBytes );

        // Second: store thumbnail
        byte[] thumbnailBytes = thumbnail.thumbnail( imageBytes );
        fs.store( imageName + "-thumb.jpg", thumbnailBytes );

        return imageName;
    }
}
