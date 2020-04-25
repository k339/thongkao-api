package com.thongkao.controller;

import com.thongkao.entity.Portfolio;
import com.thongkao.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @Autowired
    private PortfolioService portfolioService;

    @GetMapping("/list")
    public List<Portfolio> getPortfolioList() {
        return portfolioService.getPortfolioList();
    }

    @GetMapping("/{id}")
    public Portfolio getPortfolio(@PathVariable(name = "id") Long id) {
        return portfolioService.getPortfolio(id);
    }
}