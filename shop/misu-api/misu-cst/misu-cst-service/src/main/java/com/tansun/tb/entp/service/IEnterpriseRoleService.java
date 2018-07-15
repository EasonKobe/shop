package com.tansun.tb.entp.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IEnterpriseRoleService {

    PageInfo<Map<String, Object>> findEnterpriseRolePage(Map<String, Object> params);

    Map<String, Object> getEnterpriseRoleMap(Map<String, Object> params);

    Map<String,Object> saveEnterpriseRole(Map<String, Object> params);

    void updateEnterpriseRole(Map<String, Object> params);

    void deleteEnterpriseRole(Map<String, Object> params);
}
