package com.thongkao.model;

import lombok.Data;

import java.util.List;

@Data
public class PortfolioData {
    private Long id;
    private String title;
    private String customer;
    private String description;
    private Integer totalImage;
    private List<Long> idImagesDelete;
}
