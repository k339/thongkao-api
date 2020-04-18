package com.thongkao.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.thongkao.model.LoginResponse;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.security.Key;
import java.util.*;
import java.util.stream.Collectors;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationService {

    static final long EXPIRATION_TIME = (1000 * 60 * 60) * 24; // 1 day
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer ";
    static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static Authentication getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(HEADER_STRING);
        if(token == null) {
            return null;
        }

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody();
        if (claims != null) {
            String username = (String) claims.get("username");
            String rulesString = claims.get("rules", String.class);
            List<String> rules = Arrays.asList(rulesString.split(","));
            List<GrantedAuthority> authorities = rules.stream()
                    .map(SimpleGrantedAuthority::new)
                    .collect(Collectors.toList());
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        return null;
    }

    public static void addAuthentication(HttpServletResponse res, Authentication auth) throws IOException {
        List<String> list = new ArrayList<>();
        auth.getAuthorities().forEach(rule -> {
            list.add(rule.toString());
        });
        String token = Jwts.builder()
                .setSubject(auth.getName())
                .claim("rules", String.join(",", list))
                .claim("username", auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setToken(token);
        loginResponse.setName(auth.getName());
        loginResponse.setRules(list);
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(loginResponse);
        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(jsonString);
    }

    public static void loginUnSuccess(HttpServletResponse res, AuthenticationException failed) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("message", "Invalid username or password");
        res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        res.getWriter().write(jsonObject.toString());
    }
}
