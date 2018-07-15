package com.tansun.tb.entp.service.impl;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
import com.tansun.tb.entp.api.EnterpriseRoleApiConstants;
import com.tansun.tb.entp.dao.IEnterpriseRoleDao;
import com.tansun.tb.entp.entity.EnterpriseRoleEntity;
import com.tansun.tb.entp.service.IEnterpriseRoleService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("enterpriseRoleService")
public class EnterpriseRoleServiceImpl implements IEnterpriseRoleService {

    @Autowired
    private IEnterpriseRoleDao enterpriseRoleDao;

    @Override
    public PageInfo<Map<String, Object>> findEnterpriseRolePage(Map<String, Object> params) {
        //判断当前参数params是否为空，则为默认查询
        if (null == params) {
            params = new HashMap<>();
        }
        params.put(EnterpriseRoleApiConstants.DEL_IND, "0");
        return new PageInfo<>(enterpriseRoleDao.findEnterpriseRoleList(params));
    }

    @Override
    public Map<String, Object> getEnterpriseRoleMap(Map<String, Object> params) {
        params.put("delInd", "0");
        //默认调用分页查询方法。
        PageInfo<Map<String, Object>> enterpriseRolePage = this.findEnterpriseRolePage(params);
        //判断是否存在数据
        long total = enterpriseRolePage.getTotal();
        if (0 < total) {
            //获取查询结果列表
            List<Map<String, Object>> list = enterpriseRolePage.getList();
            if (null != list && list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> saveEnterpriseRole(Map<String, Object> params) {
        // 组装方法要判空
        if (params == null || params.isEmpty()) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "params");
        }

        EnterpriseRoleEntity entity = new EnterpriseRoleEntity();
        convertToEntity(params, entity);

        EnterpriseRoleEntity result = enterpriseRoleDao.save(entity);
        params.put("id", result.getId());
        return params;
    }

    @Override
    public void updateEnterpriseRole(Map<String, Object> params) {
        //update要先根据ID获取BO对象，然后在拷贝map里面的值
        Integer id = MapUtils.getInteger(params, EnterpriseRoleApiConstants.ID);
        if (!params.containsKey(EnterpriseRoleApiConstants.ID)) {
            throw ExceptionFactory.getBizException("entp-mg-00002");
        }
        EnterpriseRoleEntity entity = enterpriseRoleDao.findOne(id);
        if (entity == null) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "findOne");
        }
        convertToEntity(params, entity);

        enterpriseRoleDao.update(entity);
    }

    @Override
    public void deleteEnterpriseRole(Map<String, Object> params) {
        Integer id = MapUtils.getInteger(params, EnterpriseRoleApiConstants.ID);
        if (id == null) {
            throw ExceptionFactory.getBizException("entp-mg-00002");
        }
        EnterpriseRoleEntity entity = enterpriseRoleDao.findOne(id);
        if (entity == null) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "findOne");
        }
        entity.setDelInd("1");
        enterpriseRoleDao.update(entity);
    }

    private void convertToEntity(Map<String, Object> params, EnterpriseRoleEntity entity) {
        String entpCode = MapUtil.getString(params, EnterpriseRoleApiConstants.ENTP_CODE);
        String roleCode = MapUtil.getString(params, EnterpriseRoleApiConstants.ROLE_CODE);

        if (StringUtil.isNotBlank(entpCode)) {
            entity.setEntpCode(entpCode);
        }

        if (StringUtil.isNotBlank(roleCode)) {
            entity.setRoleCode(roleCode);
        }
    }
}
