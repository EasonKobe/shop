package com.tansun.tb.entp.api;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

import java.util.Map;

public interface EnterpriseMenuApi {

    PageInfo<Map<String, Object>> findEnterpriseMenuPage(Map<String, Object> params, PageParam pageParam);

    Map<String, Object> getEnterpriseMenuMap(Map<String, Object> params);

    Map<String, Object> saveEnterpriseMenuTrans(Map<String, Object> params);

    void deleteEnterpriseMenuTrans(Map<String, Object> params);

}

