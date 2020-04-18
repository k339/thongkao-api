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
        return new ArrayList<>();
    }
}