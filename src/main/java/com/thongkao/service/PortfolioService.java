package com.thongkao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thongkao.entity.FileData;
import com.thongkao.entity.Portfolio;
import com.thongkao.model.PortfolioData;
import com.thongkao.repository.PortfolioRepository;
import com.thongkao.util.FileDataUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.*;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;

    public List<Portfolio> getPortfolioList() {
        return portfolioRepository.findAll();
    }

    public void createPortFolio(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String data = multipartHttpServletRequest.getParameter("data");
        PortfolioData portfolioData = mapper.readValue(data, PortfolioData.class);

        Portfolio portfolio = new Portfolio();
        portfolio.setTitle(portfolioData.getTitle());
        portfolio.setCustomer(portfolioData.getCustomer());
        portfolio.setDescription(portfolioData.getDescription());

        Set<FileData> fileDataSet = new HashSet<>();

        int totalImage = portfolioData.getTotalImage();
        for (int i = 0; i < totalImage; i++) {
            String fileName = FileDataUtil.getUrlFile(Objects.requireNonNull(multipartHttpServletRequest.getFile("image" + i)));
            FileData fileData = new FileData();
            fileData.setCreateDate(new Date());
            fileData.setNAME(fileName);
            fileData.setPortfolio(portfolio);
            fileDataSet.add(fileData);
        }

        portfolio.setFiles(fileDataSet);
        portfolioRepository.save(portfolio);
    }
}
