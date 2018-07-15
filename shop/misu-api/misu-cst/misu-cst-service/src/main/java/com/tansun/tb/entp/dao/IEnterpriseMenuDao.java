package com.tansun.tb.entp.dao;

import com.jeedev.msdp.core.dao.BaseDao;
import com.tansun.tb.entp.entity.EnterpriseMenuEntity;

import java.util.List;
import java.util.Map;

public interface IEnterpriseMenuDao extends BaseDao<EnterpriseMenuEntity, Integer> {
    List<Map<String, Object>> findEnterpriseMenuList(Map<String, Object> params);
}
