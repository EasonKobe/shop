package com.tansun.tb.entp.dao;

import com.jeedev.msdp.core.dao.BaseDao;
import com.tansun.tb.entp.entity.EnterpriseRoleEntity;

import java.util.List;
import java.util.Map;

public interface IEnterpriseRoleDao extends BaseDao<EnterpriseRoleEntity, Integer> {
    List<Map<String, Object>> findEnterpriseRoleList(Map<String, Object> params);
}
