package com.udemy.date4u.interfaces.shell;

import com.udemy.date4u.core.photo.PhotoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@ShellComponent
public class PhotoCommands {

    // Wir versuchen erstmal den Weg ohne Dependency Injection:
    // private final PhotoService photoService = new PhotoService();

    // Nun das Ganze mit DI:
    private final PhotoService photoService;

    @Autowired
    public PhotoCommands(PhotoService photoService) {
        this.photoService = photoService;
    }

    // Methode, die die Metadaten eines Fotos anzeigen soll:
    @ShellMethod("Show photo")  // show-photo
    String showPhoto(String name) {
        // Wir rufen den photoService auf und nutzen die download Methode.
        // Zurückgegeben wird ein Optional. Es enthält entweder ein Foto oder nicht.
        return photoService.download(name)
                // Falls die Daten vorhanden sind, können wir diese Daten mit map auf die Metadaten mappen.
                .map(bytes ->
                {
                    // Falls per read Daten nicht gelesen werden können -> try/catch
                    try {
                        // die ImageIO hat die Methode read.
                        // Da die Daten als byte-Array vorliegen, konvertieren wir das byte-Array in einen
                        // ByteArrayInputStream und speichere es in einer Variable:
                        var image = ImageIO.read(new ByteArrayInputStream(bytes));
                        // Die Rückgabe soll nun die Breite und Höhe zurückgeben:
                        return "Width: " + image.getWidth() + " Height: " + image.getHeight();
                    } catch (IOException e) {
                        return "Unable to read image dimensions";
                    }
                })
                // Falls das Bild nicht gefunden wurde:
                .orElse("Image not found");
    }

    @ShellMethod("Upload photo")  // upload-photo
    String uploadPhoto(String filename) throws IOException {
        byte[] bytes = Files.readAllBytes( Paths.get( filename ) );
        return "Uploaded " + photoService.upload( bytes );
    }
}
