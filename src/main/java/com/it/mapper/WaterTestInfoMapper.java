package com.it.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.it.pojo.ConsignorInfo;
import com.it.pojo.WaterTestInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface WaterTestInfoMapper extends BaseMapper<WaterTestInfo> {
    @Select("select case_num,create_time from water_test_info where status = #{status} group by case_num,create_time;")
    List<WaterTestInfo> getCaseList(String status);
}
