package com.thongkao.controller;

import com.thongkao.entity.Portfolio;
import com.thongkao.model.User;
import com.thongkao.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @GetMapping("/portfolio/list")
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

    @PostMapping("/app-user/create")
    public void createAppUser(@RequestBody User user) {
        adminService.createUser(user);
    }
}
