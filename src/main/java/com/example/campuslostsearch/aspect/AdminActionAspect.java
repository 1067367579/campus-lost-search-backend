package com.example.campuslostsearch.aspect;

import com.example.campuslostsearch.common.context.BaseContext;
import com.example.campuslostsearch.mapper.CommonMapper;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AdminActionAspect {
    @Autowired
    private CommonMapper commonMapper; // 用于获取数据库会话

    @Before("@annotation(com.example.campuslostsearch.annotation.AdminAction)")  // 监听带有 @AdminAction 注解的方法
    public void beforeAdminAction() {
        Long adminId = BaseContext.getCurrentId(); // 从 ThreadLocal 获取 adminId
        if (adminId != null) {
            // 设置数据库会话中的 adminId，假设你的数据库支持 SESSION 变量
            commonMapper.setAdminId(adminId);
        }
    }
}
