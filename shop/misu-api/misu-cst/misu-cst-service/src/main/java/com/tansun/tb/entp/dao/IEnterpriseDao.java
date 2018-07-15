package com.tansun.tb.entp.dao;

import com.jeedev.msdp.core.dao.BaseDao;
import com.tansun.tb.entp.entity.EnterpriseEntity;

import java.util.List;
import java.util.Map;

public interface IEnterpriseDao extends BaseDao<EnterpriseEntity, Integer> {
	
	
    List<Map<String, Object>> findEnterpriseList(Map<String, Object> params);
    /**
     * @方法名称 findEntpCode
     * @功能描述 <pre>根据用户登录名查找客户号</pre>
     * @作者    yuminjun
     * @创建时间 2018年2月4日 下午6:46:59
     * @param params
     * @return
     */
    List<Map<String, Object>> findEntpCodeByLoginName(Map<String, Object> params);
}
