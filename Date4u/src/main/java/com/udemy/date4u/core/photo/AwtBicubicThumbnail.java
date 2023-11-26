package com.udemy.date4u.core.photo;

import org.springframework.stereotype.Service;
import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

@Service
public class AwtBicubicThumbnail implements Thumbnail {
  private static BufferedImage create( BufferedImage source,
                                       int width, int height ) {
    double thumbRatio = (double) width / height;
    double imageRatio = (double) source.getWidth() / source.getHeight();
    if ( thumbRatio < imageRatio ) height = (int) (width / imageRatio);
    else width = (int) (height * imageRatio);
    BufferedImage thumb = new BufferedImage( width, height,
                                             BufferedImage.TYPE_INT_RGB );
    Graphics2D g2 = thumb.createGraphics();
    g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION,
                         RenderingHints.VALUE_INTERPOLATION_BICUBIC );
    g2.drawImage( source, 0, 0, width, height, null );
    g2.dispose();
    return thumb;
  }

  // Wir wandeln die Methode thumbnail nun etwas ab, indem wir diese Methode nicht als Methode der Klasse AwtBicubic...
  // behalten, sondern wir erzeugen eine Schnittstelle "Thumbnail" Interface mit einer Methode thumbnail und wir
  // implementieren die Schnittstelle in diese Klasse "AtwBic..." Dann überschreiben wir sie und führen die benötigte
  // Programmierung hier ein.
  // Warum tun wir das alles?? Damit wir nicht mehr die exakte Klasse Awt... in PhotoService injizieren müssen, sondern
  // nur den Basistyp Thumbnail. Dadurch verschwindet bei der Injizierung auch die private statische Methode create,
  // die im PhotoService nichts zu suchen hat, da sie nur zu dieser Klasse gehört.
  // Diese Vorgehensweise ist bei Spring Boot auch sehr wichtig.
  @Override
  public byte[] thumbnail( byte[] imageBytes ) {
    try ( InputStream is = new ByteArrayInputStream( imageBytes );
          ByteArrayOutputStream baos = new ByteArrayOutputStream() ) {
      ImageIO.write( create( ImageIO.read( is ), 200, 200 ), "jpg", baos );
      return baos.toByteArray();
    }
    catch ( IOException e ) {
      throw new UncheckedIOException( e );
    }
  }
}