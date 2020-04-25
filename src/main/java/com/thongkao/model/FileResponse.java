package com.thongkao.model;

import lombok.Data;

@Data
public class FileResponse {
    private String fileName;
    private String contentType;
    private byte[] data;
}
