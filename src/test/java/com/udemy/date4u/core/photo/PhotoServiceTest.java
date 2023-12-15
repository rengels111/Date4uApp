package com.udemy.date4u.core.photo;

import com.udemy.date4u.core.FileSystem;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

// @SpringBootTest
@ExtendWith(MockitoExtension.class)  // Mockito für Mock-Objekte.
class PhotoServiceTest {
  private static final byte[] MINIMAL_JPG = Base64.getDecoder().decode(
     "/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAP////////////////////////////////////"
    +"//////////////////////////////////////////////////wgALCAABAAEBAREA/8QA"
    +"FBABAAAAAAAAAAAAAAAAAAAAAP/aAAgBAQABPxA=" );    // https://git.io/J9GXr

//  Das brauchen wir nun nicht mehr, da Mockito verwendet wird.
//  private static class DummyFileSystem extends FileSystem {
//    @Override public long getFreeDiskSpace() { return 1; }
//    @Override public byte[] load( String filename ) { return MINIMAL_JPG; }
//    @Override public void store( String filename, byte[] bytes ) { }
//  }

  @Mock(strictness = Mock.Strictness.LENIENT)  // Da wir nicht alle Methoden nutzen wollen, nehmen wir "lenient" hinzu.
  FileSystem fs;
  @Spy
  AwtBicubicThumbnail thumbnail;
  @InjectMocks
  PhotoService photoService;  // im Hintergrund wird der parametrisierte Konstruktor mit dem Mock- und Spy-Objekt
  // übergeben.

  @Test
  void successful_photo_upload() {
    // given
    // FileSystem fs = new DummyFileSystem(); brauchen wir nicht mehr, da @Mock das übernommen hat.

    // when
    String imageName = photoService.upload( MINIMAL_JPG );

    // then
    assertThat( imageName ).isNotEmpty();
    // Mockito-Methode verify: Verifiziere bitte, dass die Methode "store" der FileSystem-Klasse aufgerufen wurde:
    // times: verifiziere, dass store zweimal aufgerufen wurde:
    verify(fs, times(2)).store(anyString(), any(byte[].class));
    // Verifiziere, dass die Thumbnail-Methode mit einem byte[] aufgerufen wurde:
    verify(thumbnail).thumbnail(any(byte[].class));
  }
}