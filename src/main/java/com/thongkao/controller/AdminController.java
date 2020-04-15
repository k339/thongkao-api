package com.thongkao.controller;

import com.thongkao.entity.AppUser;
import com.thongkao.entity.Portfolio;
import com.thongkao.model.User;
import com.thongkao.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private AdminService adminService;

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
}
