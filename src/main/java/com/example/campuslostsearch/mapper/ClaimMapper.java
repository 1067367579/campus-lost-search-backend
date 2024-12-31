package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.pojo.entity.Claim;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ClaimMapper {

    @Insert("insert into claim (user_id, item_id, item_type, description) VALUES " +
            "(#{userId},#{itemId},#{itemType},#{description})")
    @Options(useGeneratedKeys = true,keyProperty = "claimId",keyColumn = "claim_id")
    void save(Claim claim);

    List<Claim> pageQuery(Claim claim);

    @Select("select * from claim where item_id = #{itemId}")
    List<Claim> getByItemId(Long itemId);

    @Select("select * from claim where claim_id = #{claimId}")
    Claim getById(Long claimId);

    @Update("update claim set status = #{status},handle_remark = #{handleRemark},handle_time = CURRENT_TIMESTAMP where claim_id = #{claimId}")
    void handleClaim(Claim claim);
}
