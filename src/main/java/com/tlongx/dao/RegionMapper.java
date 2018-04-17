package com.tlongx.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.tlongx.pojo.Region;

public interface RegionMapper {
    int deleteByPrimaryKey(Double regionId);

    int insert(Region record);

    int insertSelective(Region record);

    Region selectByPrimaryKey(Double regionId);

    int updateByPrimaryKeySelective(Region record);

    int updateByPrimaryKey(Region record);

	List<Map<Double, String>> selectParentOne();

	List<Map<Double, String>> selectByParent(@Param(value="id")String id);
}