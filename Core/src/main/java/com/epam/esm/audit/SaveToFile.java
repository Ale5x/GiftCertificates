package com.epam.esm.audit;

import com.epam.esm.model.GiftCertificate;
import com.epam.esm.model.Tag;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * The type SaveToFile implements methods of the Save interface.
 * The class saves an object to a file.
 *
 * @author Alexander Pishchala
 */
@Component
public class SaveToFile implements Save {

    private final Path pathDir = Paths.get("Audit");
    private final Path pathFile = Paths.get(pathDir + "/AuditFile.txt");

    @Override
    public void save(Object object)  {
        try {
            existsFile();
            String message = (String) object;
            Files.write(pathFile,"\n".getBytes(), StandardOpenOption.APPEND);
            Files.write(pathFile, message.getBytes(), StandardOpenOption.APPEND);
        } catch (IOException ignored) {
            ignored.printStackTrace();
        }
    }

    /**
     * Checking for the existence of a file. If it doesn't exist, it will be created automatically.
     *
     * @throws IOException
     */
    private void existsFile() throws IOException {
        existsDir();
        if (!Files.exists(pathFile)) {
            Files.createFile(pathFile);
        }
    }

    /**
     * Checking for the existence of a directory. If it doesn't exist, it will be created automatically.
     *
     * @throws IOException
     */
    private void existsDir() throws IOException {
        if (!Files.isDirectory(pathDir)) {
            Files.createDirectories(pathDir);
        }
    }
}