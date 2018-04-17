package com.tlongx.service;

import java.util.Map;

import com.tlongx.common.ServiceResponse;

public interface IRegionService {
	ServiceResponse selectRegion(Map<String, Object> paramMap);
}
