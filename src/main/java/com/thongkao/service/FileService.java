package com.thongkao.service;

import com.thongkao.model.FileResponse;
import org.springframework.stereotype.Service;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;

@Service
public class FileService {

    public FileResponse getFile(String fileName) throws IOException {
        String filePath = System.getProperty("user.dir") + "/fileData/" + fileName;
        File file = new File(filePath);
        String contentType = Files.probeContentType(file.toPath());
        FileInputStream fis = new FileInputStream(file);
        FileResponse fileResponse = new FileResponse();
        fileResponse.setFileName(fileName);
        fileResponse.setContentType(contentType);
        fileResponse.setData(fis.readAllBytes());
        fis.close();
        return fileResponse;
    }
}
