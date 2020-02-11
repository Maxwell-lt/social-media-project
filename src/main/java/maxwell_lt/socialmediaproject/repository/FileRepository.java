package maxwell_lt.socialmediaproject.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class FileRepository {
    private final String resourceRoot;


    public FileRepository(@Value("${maxwell_lt.socialmediaproject.resourceroot}") String resourceRoot) {
        this.resourceRoot = resourceRoot;
    }

    public void saveFile(byte[] data, String filename) throws IOException {
        Path path = Paths.get(resourceRoot + File.separator + filename);
        Files.write(path, data);
    }

    public byte[] getFile(String filename) throws IOException {
        return Files.readAllBytes(new File(resourceRoot + File.separator + filename).toPath());
    }
}
