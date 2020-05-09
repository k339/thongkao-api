package com.thongkao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thongkao.entity.FileData;
import com.thongkao.entity.Portfolio;
import com.thongkao.model.PortfolioData;
import com.thongkao.repository.FileDataRepository;
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

    @Autowired
    private FileDataRepository fileDataRepository;

    public Portfolio getPortfolio(Long id) {
        return portfolioRepository.findById(id).orElse(null);
    }

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
        portfolio.setPinOrder(new Date().getTime());
        portfolioRepository.save(portfolio);

        int totalImage = portfolioData.getTotalImage();
        for (int i = 0; i < totalImage; i++) {
            String fileName = FileDataUtil.getUrlFile(Objects.requireNonNull(multipartHttpServletRequest.getFile("image" + i)));
            FileData fileData = new FileData();
            fileData.setCreateDate(new Date());
            fileData.setName(fileName);
            fileData.setPortfolio(portfolio);
            fileDataRepository.save(fileData);
        }
    }

    public void updatePortfolio(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        String data = multipartHttpServletRequest.getParameter("data");
        PortfolioData portfolioData = mapper.readValue(data, PortfolioData.class);
        Portfolio portfolio = portfolioRepository.findById(portfolioData.getId()).get();
        portfolio.setTitle(portfolioData.getTitle());
        portfolio.setDescription(portfolioData.getDescription());
        portfolio.setCustomer(portfolioData.getCustomer());
        portfolio.setPinOrder(new Date().getTime());

        portfolioData.getIdImagesDelete().forEach(id -> {
            FileData fileData = fileDataRepository.findById(id).get();
            FileDataUtil.deleteFile(fileData);
            fileDataRepository.delete(fileData);
        });

        int totalImage = portfolioData.getTotalImage();
        for (int i = 0; i < totalImage; i++) {
            String fileName = FileDataUtil.getUrlFile(Objects.requireNonNull(multipartHttpServletRequest.getFile("image" + i)));
            FileData fileData = new FileData();
            fileData.setCreateDate(new Date());
            fileData.setName(fileName);
            fileData.setPortfolio(portfolio);
            fileDataRepository.saveAndFlush(fileData);
        }
        portfolioRepository.saveAndFlush(portfolio);
    }

    public void deletePortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id).get();
        portfolio.getFiles().forEach(FileDataUtil::deleteFile);
        portfolioRepository.delete(portfolio);
    }
}
