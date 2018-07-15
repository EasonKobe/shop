package com.tansun.tb.entp.api;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

import java.util.Map;

public interface EnterpriseRoleApi {

    PageInfo<Map<String, Object>> findEnterpriseRolePage(Map<String, Object> params, PageParam pageParam);

    Map<String, Object> getEnterpriseRoleMap(Map<String, Object> params);

    Map<String, Object> saveEnterpriseRoleTrans(Map<String, Object> params);

    void deleteEnterpriseRoleTrans(Map<String, Object> params);

}

