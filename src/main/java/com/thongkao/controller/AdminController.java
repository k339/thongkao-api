package com.thongkao.controller;

import com.thongkao.entity.AppUser;
import com.thongkao.entity.Portfolio;
import com.thongkao.model.User;
import com.thongkao.service.AdminService;
import com.thongkao.service.PortfolioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private PortfolioService portfolioService;

    @Secured("ADMIN")
    @GetMapping("/app-user/list")
    public List<AppUser> listAppUser() {
        return adminService.getAppUserList();
    }

    @Secured("ADMIN")
    @PostMapping("/app-user/create")
    public void createAppUser(@RequestBody User user) {
        adminService.createUser(user);
    }

    @Secured({"ADMIN", "WEB_MASTER"})
    @GetMapping(value = "/portfolio/{id}")
    public Portfolio getPortfolio(@PathVariable(name = "id") Long id) {
        return portfolioService.getPortfolio(id);
    }

    @Secured({"ADMIN", "WEB_MASTER"})
    @GetMapping(value = "/portfolio/list")
    public List<Portfolio> listPortfolio() {
        return portfolioService.getPortfolioList();
    }

    @Secured({"ADMIN", "WEB_MASTER"})
    @PostMapping(value = "/portfolio/create")
    public void createPortfolio(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        portfolioService.createPortFolio(multipartHttpServletRequest);
    }

    @Secured({"ADMIN", "WEB_MASTER"})
    @PostMapping(value = "/portfolio/update")
    public void updatePortfolio(MultipartHttpServletRequest multipartHttpServletRequest) throws IOException {
        portfolioService.updatePortfolio(multipartHttpServletRequest);
    }

    @Secured({"ADMIN", "WEB_MASTER"})
    @DeleteMapping(value = "/portfolio/delete/{id}")
    public void deletePortfolio(@PathVariable(name = "id") Long id) {
        portfolioService.deletePortfolio(id);
    }
}
