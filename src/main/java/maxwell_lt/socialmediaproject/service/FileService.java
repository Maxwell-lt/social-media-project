package maxwell_lt.socialmediaproject.service;

import org.imgscalr.Scalr;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
            BufferedImage image = ImageIO.read(imageFile.getInputStream());
            BufferedImage scaledImage = Scalr.resize(image, 128);

            File imageOutputFile = new File(resourceRoot + File.separator +
                    fileBaseName + ".png");
            File scaledImageOutputFile = new File(resourceRoot + File.separator +
                    fileBaseName + "_thumb.png");

            ImageIO.write(image, "png", imageOutputFile);
            ImageIO.write(scaledImage, "png", scaledImageOutputFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return fileBaseName;
    }
}
