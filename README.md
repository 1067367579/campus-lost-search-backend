# 校园失物招领系统

## 项目介绍
本项目是一个基于Spring Boot的校园失物招领管理系统,主要功能包括:
- 用户管理(登录注册、信息管理)
- 物品管理(失物发布、寻物发布)
- 认领管理(物品认领、认领审核)
- 投诉管理(投诉提交、投诉处理)
- 黑名单管理
- 管理员操作日志

## 技术栈
- Spring Boot 2.7.x
- MyBatis
- MySQL 8.0
- JWT
- 阿里云OSS
- Maven

## 项目结构
```
src/main/java/com/example/campuslostsearch
├── annotation    // 自定义注解
├── common        // 公共组件
│   ├── context  // 上下文
│   ├── exception // 异常处理
│   └── result   // 统一返回结果
├── config       // 配置类
├── controller   // 控制器
├── interceptor  // 拦截器
├── mapper      // MyBatis接口
├── pojo        // 实体类
│   ├── dto    // 数据传输对象
│   ├── entity // 数据库实体
│   └── vo     // 视图对象
├── service     // 业务逻辑
└── utils       // 工具类
```

## 主要功能
1. 用户模块
   - 用户注册登录
   - 用户信息管理
   - 密码修改

2. 物品模块
   - 失物发布
   - 寻物发布
   - 物品分类管理
   - 物品状态管理

3. 认领模块
   - 认领申请
   - 认领审核
   - 认领记录查询

4. 投诉模块
   - 投诉提交
   - 投诉处理
   - 投诉记录查询

5. 管理员模块
   - 用户管理
   - 黑名单管理
   - 操作日志查询
   - 数据统计

## 快速开始
1. 克隆项目
```bash
git clone https://github.com/yourusername/campus-lost-found.git
```

2. 配置数据库
- 创建数据库lost_found_system
- 执行database-creation.sql脚本

3. 配置application.yml
- 修改数据库连接信息
- 配置阿里云OSS信息
- 配置JWT密钥

4. 运行项目
```bash
mvn spring-boot:run
```

## 接口文档
详细的接口文档请参考api-design.md文件

## 注意事项
1. 所有需要认证的接口都需要在请求头中携带token
2. 分页接口的pageNum从1开始
3. 时间格式统一使用: YYYY-MM-DD HH:mm:ss
4. 所有响应都遵循统一格式：包含 code、msg 和 data 三个字段 