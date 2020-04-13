package com.thongkao.service;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.io.IOException;
import java.security.Key;
import java.util.Collections;
import java.util.Date;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class TokenAuthenticationService {

    static final long EXPIRATION_TIME = 1000 * 30; // 30 seconds timeout
    static final String HEADER_STRING = "Authorization";
    static final String TOKEN_PREFIX = "Bearer";
    static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS512);

    public static Authentication getAuthentication(HttpServletRequest req) {
        String token = req.getHeader(HEADER_STRING);
        if(token == null) {
            return null;
        }

        String username = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token.replace(TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();

        return username != null ?
                new UsernamePasswordAuthenticationToken(username, null, Collections.emptyList())
                : null;
    }

    public static void addAuthentication(HttpServletResponse res, Authentication auth) throws IOException {
        String token = Jwts.builder()
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
        JsonArray jsonArray = new JsonArray();
        auth.getAuthorities().forEach(rule -> {
            jsonArray.add(rule.toString());
        });
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("token", token);
        jsonObject.addProperty("name", auth.getName());
        jsonObject.addProperty("rules", jsonArray.toString());
        res.setStatus(HttpServletResponse.SC_OK);
        res.getWriter().write(jsonObject.toString());
    }
}
