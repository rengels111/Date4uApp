package com.udemy.date4u.core.photo;

import com.udemy.date4u.core.FileSystem;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest( properties = "spring.shell.interactive.enabled=false" )  // Spring Shell deaktivieren, damit Test
// funktioniert.
// @ExtendWith(MockitoExtension.class)  // Mockito für Mock-Objekte. Brauchen wir nun nicht mehr mit SpringBootTest.
class PhotoServiceTest {
  private static final byte[] MINIMAL_JPG = Base64.getDecoder().decode(
     "/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAP////////////////////////////////////"
    +"//////////////////////////////////////////////////wgALCAABAAEBAREA/8QA"
    +"FBABAAAAAAAAAAAAAAAAAAAAAP/aAAgBAQABPxA=" );

//  Das brauchen wir nun nicht mehr, da Mockito verwendet wird.
//  private static class DummyFileSystem extends FileSystem {
//    @Override public long getFreeDiskSpace() { return 1; }
//    @Override public byte[] load( String filename ) { return MINIMAL_JPG; }
//    @Override public void store( String filename, byte[] bytes ) { }
//  }

//  @Mock(strictness = Mock.Strictness.LENIENT)  // Da wir nicht alle Methoden nutzen wollen, nehmen wir "lenient" hinzu.
//  FileSystem fs;
//  @Spy
//  AwtBicubicThumbnail thumbnail;
//  @InjectMocks
//  PhotoService photoService;  // im Hintergrund wird der parametrisierte Konstruktor mit dem Mock- und Spy-Objekt
//  // übergeben.

  @MockBean  // Erzeugt eine echte Bean aus dem SpringContext.
  FileSystem fs;
  @Autowired PhotoService photoService;  // Nun kann ich den echten PhotoService autowiren.

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
//    verify(fs, times(2)).store(anyString(), any(byte[].class));
//    // Verifiziere, dass die Thumbnail-Methode mit einem byte[] aufgerufen wurde:
//    verify(thumbnail).thumbnail(any(byte[].class));
  }
}