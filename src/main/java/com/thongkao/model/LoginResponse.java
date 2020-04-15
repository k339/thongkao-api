package com.thongkao.model;

import lombok.Data;

import java.util.List;

@Data
public class LoginResponse {
    private String token;
    private String name;
    private List<String > rules;
}
