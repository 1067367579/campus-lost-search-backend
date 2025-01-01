package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.annotation.AdminAction;
import com.example.campuslostsearch.pojo.dto.BlockUserDTO;
import com.example.campuslostsearch.pojo.entity.BlackList;
import com.example.campuslostsearch.pojo.vo.BlackListVO;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface BlackListMapper {
    @AdminAction
    @Insert("insert into blacklist (user_id, reason, type,admin_id) values (#{userId},#{reason},#{type},#{adminId})")
    void save(BlockUserDTO blockUserDTO);

    @AdminAction
    @Delete("delete from blacklist where user_id = #{userId}")
    void deleteByUserId(Long userId);

    @Select("select * from blacklist where user_id = #{userId}")
    BlackList getByUserId(Long userId);

    List<BlackListVO> getBlacklistView(String username);

    @Select("select * from blacklist_detail where blackListId = #{blacklistId}")
    BlackListVO getDetailById(Integer blacklistId);
}
