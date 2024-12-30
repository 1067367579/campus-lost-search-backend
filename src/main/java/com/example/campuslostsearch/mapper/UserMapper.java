package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.pojo.dto.UserLoginDTO;
import com.example.campuslostsearch.pojo.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface UserMapper {
    //根据登录信息获取用户对象
    @Select("select user_id, username, password, id_number, phone, email, user_type, status, create_time" +
            " from user where username = #{username} and password = #{password}")
    User getByLoginDTO(UserLoginDTO userLoginDTO);

    @Select("select user_id, username, password, id_number, phone, email, user_type, status, create_time " +
            "from user where username = #{username}")
    User getByUsername(String username);

    @Select("select user_id, username, password, id_number, phone, email, user_type, status, create_time" +
            " from user where id_number = #{idNumber}")
    User getByIdNumber(String idNumber);

    @Insert("insert into user (user_id, username, password, id_number, phone, email, user_type)" +
            "values (#{userId},#{username},#{password},#{idNumber},#{phone},#{email},#{userType})")
    void save(User user);

    @Select("select user_id, username, password, id_number, phone, email, user_type, status, create_time" +
            " from user where user_id = #{userId}")
    User getById(Long userId);

    void updateUser(User user);
}
