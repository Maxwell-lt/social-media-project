package maxwell_lt.socialmediaproject.service;

import maxwell_lt.socialmediaproject.repository.FileRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

class FileServiceTest {

    @Autowired
    private FileService fileService;

    @MockBean
    private FileRepository fileRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getBytesFromFilename() {

    }

    @Test
    void createImageFileAndThumbnail() {
    }
}