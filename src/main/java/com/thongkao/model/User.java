package com.thongkao.model;

import lombok.Data;

import java.util.List;

@Data
public class User {
    private String name;
    private String username;
    private String password;
    private String email;
    private List<String> rules;
}
