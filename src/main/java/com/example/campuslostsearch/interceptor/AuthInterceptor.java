package com.example.campuslostsearch.interceptor;

import com.example.campuslostsearch.common.Result;
import com.example.campuslostsearch.util.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class AuthInterceptor implements HandlerInterceptor {
    @Autowired
    private JwtUtil jwtUtil;
    
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        if (token == null || token.isEmpty()) {
            handleAuthError(response, "未登录");
            return false;
        }

        try {
            Claims claims = jwtUtil.getClaimsFromToken(token);
            request.setAttribute("userId", claims.get("userId"));
            request.setAttribute("userType", claims.get("userType"));
            
            // 检查管理员权限
            if (request.getRequestURI().startsWith("/admin") && 
                !Integer.valueOf(1).equals(claims.get("userType"))) {
                handleAuthError(response, "无权限访问");
                return false;
            }
            
            return true;
        } catch (Exception e) {
            handleAuthError(response, "认证失败");
            return false;
        }
    }

    private void handleAuthError(HttpServletResponse response, String message) throws Exception {
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(Result.error(message)));
    }
} 