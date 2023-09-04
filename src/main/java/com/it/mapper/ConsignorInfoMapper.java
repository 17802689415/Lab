package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.it.pojo.ConsignorInfo;
import com.it.utils.R;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface ConsignorInfoMapper extends BaseMapper<ConsignorInfo> {
    @Select("select c.* from " +
            "(select * from consignor_info where emp_id = #{consignorInfo.empId}) c " +
            "join " +
            "(select case_num from sample_info where status = '拒绝收样') s " +
            "where c.case_num = s.case_num " +
            "LIMIT #{consignorInfo.pageSize} OFFSET #{consignorInfo.currentPage};")
    List<ConsignorInfo> getApplyDataBySampleStatus(@Param("consignorInfo") ConsignorInfo consignorInfo);
}
