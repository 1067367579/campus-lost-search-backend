package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.annotation.AdminAction;
import com.example.campuslostsearch.pojo.entity.Category;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface CategoryMapper {
    @Select("select category_id, name, status from category where status = 1")
    List<Category> listAll();

    @Insert("insert into category (name,status) values " +
            "(#{name},#{status})")
    @Options(useGeneratedKeys = true, keyProperty = "categoryId", keyColumn = "category_id")
    void insert(Category category);

    @AdminAction
    void update(Category category);

    @Select("select category_id, name, status from category")
    List<Category> listAllWithoutStatus();

    @Select("select * from category where category_id = #{categoryId} and status = 1;")
    Category getById(Integer categoryId);
}
