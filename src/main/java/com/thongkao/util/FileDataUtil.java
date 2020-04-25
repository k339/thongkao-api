package com.thongkao.util;

import com.thongkao.entity.FileData;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.Objects;

public class FileDataUtil {

    public static String getUrlFile(MultipartFile file) throws IOException {
        String[] temp = Objects.requireNonNull(file.getOriginalFilename()).split("\\.");
        String fileName = "fd" + new Date().getTime() + "." + temp[temp.length - 1];
        String workingDir = System.getProperty("user.dir") + "/fileData";
        File convertFile = new File(workingDir + "/" + fileName);
        convertFile.getParentFile().mkdirs();
        FileOutputStream fos = new FileOutputStream(convertFile);
        fos.write(file.getBytes());
        fos.close();
        return fileName;
    }

    public static void deleteFile(FileData fileData) {
        String workingDir = System.getProperty("user.dir") + "/fileData";
        File file = new File(workingDir + "/" + fileData.getName());
        if (file.delete()) {
            System.out.println("File deleted successfully");
        }
    }
}
