package com.thongkao.controller;

import com.thongkao.entity.Portfolio;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/portfolio")
public class PortfolioController {

    @GetMapping("/list")
    public List<Portfolio> listGoldenQuote() {
        List<Portfolio> list = new ArrayList<>();
        for(int i = 0; i < 10; i++) {
            Portfolio portfolio = new Portfolio();
            portfolio.setId((long) i);
            portfolio.setTitle("Title " + i);
            portfolio.setDetail("Detail " + i);
            list.add(portfolio);
        }
        return list;
    }
}