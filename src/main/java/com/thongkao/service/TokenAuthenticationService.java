package com.thongkao.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
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

    public static void addAuthentication(HttpServletResponse res, Authentication auth) {
        String JWT = Jwts.builder()
                .setSubject(auth.getName())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SECRET_KEY)
                .compact();
        res.addHeader(HEADER_STRING, TOKEN_PREFIX + " " + JWT);
    }
}
