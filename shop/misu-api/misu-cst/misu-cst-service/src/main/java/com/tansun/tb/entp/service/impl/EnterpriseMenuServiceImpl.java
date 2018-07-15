package com.tansun.tb.entp.service.impl;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
import com.tansun.tb.entp.api.EnterpriseMenuApiConstants;
import com.tansun.tb.entp.dao.IEnterpriseMenuDao;
import com.tansun.tb.entp.entity.EnterpriseMenuEntity;
import com.tansun.tb.entp.service.IEnterpriseMenuService;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service("enterpriseMenuService")
public class EnterpriseMenuServiceImpl implements IEnterpriseMenuService {

    @Autowired
    private IEnterpriseMenuDao enterpriseMenuDao;

    @Override
    public PageInfo<Map<String, Object>> findEnterpriseMenuPage(Map<String, Object> params) {
        //判断当前参数params是否为空，则为默认查询
        if (null == params) {
            params = new HashMap<>();
        }
        params.put(EnterpriseMenuApiConstants.DEL_IND, "0");
        return new PageInfo<>(enterpriseMenuDao.findEnterpriseMenuList(params));
    }

    @Override
    public Map<String, Object> getEnterpriseMenuMap(Map<String, Object> params) {
        params.put("delInd", "0");
        //默认调用分页查询方法。
        PageInfo<Map<String, Object>> enterpriseMenuPage = this.findEnterpriseMenuPage(params);
        //判断是否存在数据
        long total = enterpriseMenuPage.getTotal();
        if (0 < total) {
            //获取查询结果列表
            List<Map<String, Object>> list = enterpriseMenuPage.getList();
            if (null != list && list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> saveEnterpriseMenu(Map<String, Object> params) {
        // 组装方法要判空
        if (params == null || params.isEmpty()) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "params");
        }

        EnterpriseMenuEntity entity = new EnterpriseMenuEntity();
        convertToEntity(params, entity);

        EnterpriseMenuEntity result = enterpriseMenuDao.save(entity);
        params.put("id", result.getId());
        return params;
    }

    @Override
    public void updateEnterpriseMenu(Map<String, Object> params) {
        //update要先根据ID获取BO对象，然后在拷贝map里面的值
        Integer id = MapUtils.getInteger(params, EnterpriseMenuApiConstants.ID);
        if (!params.containsKey(EnterpriseMenuApiConstants.ID)) {
            throw ExceptionFactory.getBizException("entp-mg-00002");
        }
        EnterpriseMenuEntity entity = enterpriseMenuDao.findOne(id);
        if (entity == null) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "findOne");
        }
        convertToEntity(params, entity);

        enterpriseMenuDao.update(entity);
    }

    @Override
    public void deleteEnterpriseMenu(Map<String, Object> params) {
        Integer id = MapUtils.getInteger(params, EnterpriseMenuApiConstants.ID);
        if (id == null) {
            throw ExceptionFactory.getBizException("entp-mg-00002");
        }
        EnterpriseMenuEntity entity = enterpriseMenuDao.findOne(id);
        if (entity == null) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "findOne");
        }
        entity.setDelInd("1");
        enterpriseMenuDao.update(entity);
    }

    private void convertToEntity(Map<String, Object> params, EnterpriseMenuEntity entity) {
        String entpCode = MapUtil.getString(params, EnterpriseMenuApiConstants.ENTP_CODE);
        String menuCode = MapUtil.getString(params, EnterpriseMenuApiConstants.MENU_CODE);

        if (StringUtil.isNotBlank(entpCode)) {
            entity.setEntpCode(entpCode);
        }

        if (StringUtil.isNotBlank(menuCode)) {
            entity.setMenuCode(menuCode);
        }

        //BaseEntity
        Integer id = MapUtils.getInteger(params, "id");
        if (id != null) {
            entity.setId(id);
        }
        String delInd = MapUtils.getString(params, "delInd");
        if (!StringUtils.isEmpty(delInd)) {
            entity.setDelInd(delInd);
        }
        //给租户创建管理员角色时需要赋值
        String tenantId= MapUtils.getString(params, "tenantId");
        if (!StringUtils.isEmpty(tenantId)) {
            entity.setTenantId(tenantId);
        }
    }
}
