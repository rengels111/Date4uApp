package com.udemy.date4u.core.photo;

import com.udemy.date4u.core.FileSystem;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Base64;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest( properties = "spring.shell.interactive.enabled=false" )
class PhotoServiceTest {
    private static final byte[] MINIMAL_JPG = Base64.getDecoder().decode(
            "/9j/4AAQSkZJRgABAQEASABIAAD/2wBDAP////////////////////////////////////"
                    +"//////////////////////////////////////////////////wgALCAABAAEBAREA/8QA"
                    +"FBABAAAAAAAAAAAAAAAAAAAAAP/aAAgBAQABPxA=" );    // https://git.io/J9GXr


//    private static class DummyFileSystem extends FileSystem {
//        @Override public long getFreeDiskSpace() { return 1; }
//        @Override public byte[] load( String filename ) { return MINIMAL_JPG; }
//        @Override public void store( String filename, byte[] bytes ) { }


//    @Mock(lenient = true)
//    FileSystem fs;
//    @Spy
//    AwtBicubicThumbnail thumbnail;
//    @InjectMocks
//    PhotoService photoService;

    @MockBean
    FileSystem fs;
    @Autowired PhotoService photoService;

    @Test
    void successful_photo_upload() {
        // given
        // PhotoService photoService = new PhotoService(fs,new AwtBicubicThumbnail());

        // when
        String imageName = photoService.upload( MINIMAL_JPG );

        // then
        assertThat( imageName ).isNotEmpty();
//        verify(thumbnail).thumbnail(any(byte[].class));
//        verify(fs, times(2)).store(anyString(), any(byte[].class));
    }
}
