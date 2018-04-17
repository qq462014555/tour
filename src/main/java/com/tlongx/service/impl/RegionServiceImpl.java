package com.tlongx.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tlongx.common.ServiceResponse;
import com.tlongx.dao.RegionMapper;
import com.tlongx.service.IRegionService;
import com.tlongx.util.MUtil;

@Service("iRegionService")
public class RegionServiceImpl implements IRegionService{

	
	@Autowired
	RegionMapper regionMapper;
	
	@Override
	public ServiceResponse selectRegion(Map<String, Object> paramMap) {
		// TODO Auto-generated method stub
		List<Map<Double, String>> map=null;
		if(StringUtils.isBlank(MUtil.strObject(paramMap.get("id")))){
			//查询全部省会
			map=regionMapper.selectParentOne();
		}
		else{
			String id=MUtil.strObject(paramMap.get("id"));
			map=regionMapper.selectByParent(id);
		}
		
		
		
		
		return ServiceResponse.createSuccessData(map);
	}

}
