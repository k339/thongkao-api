package com.thongkao.config;

import com.thongkao.service.TokenAuthenticationService;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.SignatureException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTGenericFilter extends GenericFilterBean {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse res = (HttpServletResponse) response;
        try {
            Authentication authentication = TokenAuthenticationService.getAuthentication((HttpServletRequest) request);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (SignatureException e) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        chain.doFilter(request, res);
    }
}
