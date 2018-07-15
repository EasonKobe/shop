package com.tansun.tb.entp.service;

import com.github.pagehelper.PageInfo;

import java.util.Map;

public interface IEnterpriseMenuService {

    PageInfo<Map<String, Object>> findEnterpriseMenuPage(Map<String, Object> params);

    Map<String, Object> getEnterpriseMenuMap(Map<String, Object> params);

    Map<String,Object> saveEnterpriseMenu(Map<String, Object> params);

    void updateEnterpriseMenu(Map<String, Object> params);

    void deleteEnterpriseMenu(Map<String, Object> params);
}
