package maxwell_lt.socialmediaproject.service;

import javaxt.io.Image;
import maxwell_lt.socialmediaproject.repository.FileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class FileServiceTest {

    @Autowired
    private FileService fileService;

    @MockBean
    private FileRepository fileRepository;

    private MultipartFile multipartFile;

    @BeforeEach
    void setUp() throws IOException {
        BufferedImage bi = new BufferedImage(2048, 1024, BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        g.drawOval(10, 10, 1014, 1014);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", baos);
        byte[] image = baos.toByteArray();

        multipartFile = new MockMultipartFile("file", image);

        when(fileRepository.getFile("testfile.png")).thenReturn(image);
        when(fileRepository.getFile("testfile2.png")).thenThrow(IOException.class);
    }

    @AfterEach
    void tearDown() {
        multipartFile = null;
    }

    @Test
    void givenFileExists_WhenGetFileByName_ThenReturnFileContents() throws IOException {
        byte[] byteImage = fileService.getBytesFromFilename("testfile.png");
        assertThat(byteImage)
                .isNotEmpty();
        javaxt.io.Image image = new javaxt.io.Image(byteImage);
        assertThat(image)
                .matches(i -> i.getHeight() == 1024)
                .matches(i -> i.getWidth() == 2048);
    }

    @Test
    void givenFileDoesNotExist_WhenGetFileByName_ThenThrowException() {
        assertThatThrownBy(() -> fileService.getBytesFromFilename("testfile2.png"))
                .isInstanceOf(IOException.class);

    }

    @Test
    void givenMultipartFile_WhenSavingFile_ThenFileIsSaved() throws IOException {
        String uuid = fileService.createImageFileAndThumbnail(multipartFile);

        assertThat(uuid)
                .matches("[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}");

        verify(fileRepository, times(1))
                .saveFile(argThat(bytes -> {
                            final Image image = new Image(bytes);
                            return image.getHeight() == 1024 && image.getWidth() == 2048;
                        }),
                        matches("[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}\\.png"));

        verify(fileRepository, times(1))
                .saveFile(argThat(bytes -> {
                            final Image image = new Image(bytes);
                            return image.getHeight() == 128 && image.getWidth() == 256;
                        }),
                        matches("[0-9a-fA-F]{8}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{4}\\-[0-9a-fA-F]{12}_thumb\\.png"));
    }

    @Test
    void givenInvalidMultipartFile_WhenSavingFile_ThenExceptionIsThrown() {
        multipartFile = new MockMultipartFile("filename", new byte[0]);
        assertThatThrownBy(() -> fileService.createImageFileAndThumbnail(multipartFile))
                .isInstanceOf(NullPointerException.class);
    }
}