package com.example.campuslostsearch.interceptor;


import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.common.properties.JwtProperties;
import com.example.campuslostsearch.common.utils.JwtUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * jwt令牌校验的拦截器
 */
@SuppressWarnings("ALL")
@Component
@Slf4j
public class JwtTokenInterceptor implements HandlerInterceptor {

    @Autowired
    private JwtProperties jwtProperties;

    /**
     * 校验jwt
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        System.out.println("当前线程的ID："+Thread.currentThread().getId());
        //判断当前拦截到的是Controller的方法还是其他资源
        if (!(handler instanceof HandlerMethod)) {
            //当前拦截到的不是动态方法，直接放行
            return true;
        }
        //1、从请求头中获取令牌
        String token = request.getHeader(jwtProperties.getToken());
        //2、校验令牌
        try {
            log.info("jwt校验:{}", token);
            Claims claims = JwtUtil.parseJWT(jwtProperties.getSecret(), token);
            Long userId = Long.valueOf(claims.get("userId").toString());
            log.info("当前用户id：{}", userId);
            //这里是拦截器类，每一次请求时都要到达这里，所以在这里获取该线程操作者的ID
            //基于ThreadLocal类，为该线程在内存上分出一块空间，用于存储值，便于后面同个线程调用
            BaseContext.setCurrentId(userId);
            //3、通过，放行
            return true;
        } catch (Exception ex) {
            //4、不通过，响应401状态码
            ex.printStackTrace();
            response.setStatus(401);
            return false;
        }
    }

    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        log.info("当前线程结束:{}",BaseContext.getCurrentId());
        BaseContext.removeCurrentId();
    }
}
