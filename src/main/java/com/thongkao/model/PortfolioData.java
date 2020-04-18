package com.thongkao.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;

@Data
public class PortfolioData {
    private String title;
    private String customer;
    private String description;
    private Integer totalImage;
}
