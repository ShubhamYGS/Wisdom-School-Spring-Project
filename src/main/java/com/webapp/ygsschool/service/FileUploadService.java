package com.webapp.ygsschool.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Service
public class FileUploadService {

    // Path where files will be uploaded
    public final String UPLOAD_DIR = Paths.get("src/main/resources/static/assets/uploadfile/").toAbsolutePath().toString();

    // To Upload the file
    public boolean uploadFile(String fileName, MultipartFile multipartFile) {
        boolean isUpload = false;

        // Copying the file to a folder with standard Files.copy option
        try (InputStream inputStream = multipartFile.getInputStream()) {
            Files.copy(inputStream,
                    Paths.get(UPLOAD_DIR + File.separator + fileName),
                    StandardCopyOption.REPLACE_EXISTING);

            isUpload = true;
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }

        return isUpload;
    }
}
