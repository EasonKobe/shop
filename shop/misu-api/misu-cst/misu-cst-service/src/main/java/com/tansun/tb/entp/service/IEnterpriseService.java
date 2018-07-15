package com.tansun.tb.entp.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IEnterpriseService {
	

    PageInfo<Map<String, Object>> findEnterprisePage(Map<String, Object> params);

    Map<String, Object> getEnterpriseMap(Map<String, Object> params);

    Map<String,Object> saveEnterprise(Map<String, Object> params);

    void updateEnterprise(Map<String, Object> params);

    void deleteEnterprise(Map<String, Object> params);
    
    Map<String, Object> findEntpCodeByLoginName(Map<String, Object> params);
    
}
