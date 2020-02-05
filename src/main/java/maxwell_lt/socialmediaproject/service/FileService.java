package maxwell_lt.socialmediaproject.service;

import javaxt.io.Image;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.UUID;

@Service
public class FileService {
    private final String resourceRoot;


    public FileService(@Value("${maxwell_lt.socialmediaproject.resourceroot}") String resourceRoot) {
        this.resourceRoot = resourceRoot;
    }

    public byte[] getBytesFromFilename(String filename) throws IOException {
        return Files.readAllBytes(new File(resourceRoot + File.separator + filename).toPath());
    }


    public String createImageFileAndThumbnail(MultipartFile imageFile) {
        String fileBaseName = UUID.randomUUID().toString();
        try {
            Image image = new Image(imageFile.getInputStream());
            System.out.println(image.getExifTags());
            image.rotate();
            image.saveAs(new File(resourceRoot + File.separator +
                    fileBaseName + ".png"));
            image.setHeight(128);
            image.saveAs(new File(resourceRoot + File.separator +
                    fileBaseName + "_thumb.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileBaseName;
    }
}
