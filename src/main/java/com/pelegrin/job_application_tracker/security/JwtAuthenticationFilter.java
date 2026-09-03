package com.pelegrin.job_application_tracker.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.pelegrin.job_application_tracker.service.auth.CustomUserDetailsService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil;
    private final CustomUserDetailsService userDetailsService;

    public JwtAuthenticationFilter(JwtUtil jwtUtil, CustomUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain chain)
            throws ServletException, IOException {

        Cookie[] cookies = req.getCookies();

        if (cookies != null) {

            for (Cookie c : cookies) {

                if ("JWT".equals(c.getName())) {

                    String token = c.getValue();

                    if (jwtUtil.validateToken(token)) {

                        String email = jwtUtil.getEmailFromToken(token);
                        UserDetails ud = userDetailsService.loadUserByUsername(email);

                        if (SecurityContextHolder.getContext().getAuthentication() == null) {
                            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                                    ud,
                                    null,
                                    ud.getAuthorities());

                            SecurityContextHolder.getContext().setAuthentication(auth);
                        }
                    }
                    break;
                }
            }
        }
        chain.doFilter(req, res);
    }
}
