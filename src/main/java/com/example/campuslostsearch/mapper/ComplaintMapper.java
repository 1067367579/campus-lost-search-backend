package com.example.campuslostsearch.mapper;

import com.example.campuslostsearch.pojo.dto.ComplaintHandleDTO;
import com.example.campuslostsearch.pojo.dto.PageComplaintDTO;
import com.example.campuslostsearch.pojo.entity.Complaint;
import com.example.campuslostsearch.pojo.vo.ComplaintDetailVO;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ComplaintMapper {
    List<Complaint> pageQuery(PageComplaintDTO pageComplaintDTO);

    @Insert("insert into complaint (user_id, claim_id, reason) values (#{userId},#{claimId},#{reason})")
    @Options(useGeneratedKeys = true, keyProperty = "complaintId", keyColumn = "complaint_id")
    void save(Complaint complaint);

    @Select("select * from complaint where complaint_id = #{complaintId}")
    Complaint getById(Long complaintId);

    void processComplaint(ComplaintHandleDTO complaintHandleDTO);

    @Select("select * from complaint_details_view where complaintId = #{complaintId}")
    ComplaintDetailVO getDetailById(Long complaintId);
}
