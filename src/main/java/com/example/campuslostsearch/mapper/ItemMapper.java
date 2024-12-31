package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.pojo.dto.PageItemDTO;
import com.example.campuslostsearch.pojo.entity.Item;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ItemMapper {
    List<Item> pageQuery(PageItemDTO pageItemDTO);

    List<String> getImagesByItem(Long itemId);

    @Insert("INSERT INTO item (user_id, category_id, item_name, description, location, key_time, item_type) " +
            "VALUES (#{userId}, #{categoryId}, #{itemName}, #{description}, #{location}, #{keyTime}, #{itemType})")
    @Options(useGeneratedKeys = true, keyProperty = "itemId", keyColumn = "item_id")
    void saveItem(Item item);


    void saveImages(List<String> images, Long itemId);

    @Select("select * from item where item_id = #{itemId}")
    Item getItemById(Long itemId);

    void deleteImages(List<String> images);

    @Delete("delete from item where item_id = #{itemId}")
    void deleteById(Long itemId);

    @Select("select count(*) from item where status = 0 and item_type = 0")
    Integer getLostCount();

    @Select("select count(*) from item where status = 0 and item_type = 1")
    Integer getFoundCount();
}
