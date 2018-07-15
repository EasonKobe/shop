package com.tansun.tb.entp.api.impl;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.utlis.StringUtil;
import com.tansun.tb.entp.api.EnterpriseRoleApi;
import com.tansun.tb.entp.api.EnterpriseRoleApiConstants;
import com.tansun.tb.entp.service.IEnterpriseRoleService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("enterpriseRoleApi")
public class EnterpriseRoleApiImpl implements EnterpriseRoleApi {


    @Autowired
    private IEnterpriseRoleService enterpriseRoleService;


    @Override
    public PageInfo<Map<String, Object>> findEnterpriseRolePage(Map<String, Object> params, PageParam pageParam) {
        PageInfo<Map<String, Object>> page = enterpriseRoleService.findEnterpriseRolePage(params);
        return page;
    }


    @Override
    public Map<String, Object> getEnterpriseRoleMap(Map<String, Object> params) {
        Map<String, Object> enterpriseRoleMap = enterpriseRoleService.getEnterpriseRoleMap(params);

        return enterpriseRoleMap;
    }

    @Override
    public Map<String, Object> saveEnterpriseRoleTrans(Map<String, Object> params) {
        Integer id = MapUtils.getInteger(params, EnterpriseRoleApiConstants.ID);

        String roleCode = MapUtils.getString(params, EnterpriseRoleApiConstants.ROLE_CODE);
        String entpCode = MapUtils.getString(params, EnterpriseRoleApiConstants.ENTP_CODE);

        //新增客户角色
        if (null == id) {
            if (StringUtil.isBlank(entpCode)) {
                throw ExceptionFactory.getBizException("entp-mg-00005", "客户编号");
            }
            if (StringUtil.isBlank(roleCode)) {
                throw ExceptionFactory.getBizException("entp-mg-00005", "角色编号");
            }
            return enterpriseRoleService.saveEnterpriseRole(params);
        } else {
            //修改客户角色
            enterpriseRoleService.updateEnterpriseRole(params);
        }
        return null;
    }

    @Override
    public void deleteEnterpriseRoleTrans(Map<String, Object> params) {
        enterpriseRoleService.deleteEnterpriseRole(params);
    }
}
