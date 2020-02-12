package maxwell_lt.socialmediaproject.service;

import javaxt.io.Image;
import maxwell_lt.socialmediaproject.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class FileService {

    private static final String EXTENSION = ".png";
    private FileRepository fileRepository;

    @Autowired
    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public byte[] getBytesFromFilename(String filename) throws IOException {
        return fileRepository.getFile(filename);
    }


    public String createImageFileAndThumbnail(MultipartFile imageFile) throws IOException {
        String fileBaseName = UUID.randomUUID().toString();
        Image image = new Image(imageFile.getInputStream());
        image.rotate();
        fileRepository.saveFile(image.getByteArray(), fileBaseName + EXTENSION);
        image.setHeight(128);
        fileRepository.saveFile(image.getByteArray(), fileBaseName + "_thumb" + EXTENSION);
        return fileBaseName;
    }
}
