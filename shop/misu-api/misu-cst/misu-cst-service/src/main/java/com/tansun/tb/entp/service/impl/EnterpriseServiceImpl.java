package com.tansun.tb.entp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
import com.tansun.tb.entp.api.EnterpriseApiConstants;
import com.tansun.tb.entp.dao.IEnterpriseDao;
import com.tansun.tb.entp.entity.EnterpriseEntity;
import com.tansun.tb.entp.service.IEnterpriseService;

@Service("enterpriseService")
public class EnterpriseServiceImpl implements IEnterpriseService {

    @Autowired
    private IEnterpriseDao enterpriseDao;
    
    @Override
    public PageInfo<Map<String, Object>> findEnterprisePage(Map<String, Object> params) {
        //判断当前参数params是否为空，则为默认查询
        if (null == params) {
            params = new HashMap<>();
        }
        params.put(EnterpriseApiConstants.DEL_IND, "0");
        return new PageInfo<>(enterpriseDao.findEnterpriseList(params));
    }

    
    @Override
    public Map<String, Object> getEnterpriseMap(Map<String, Object> params) {
        params.put("delInd", "0");
        //默认调用分页查询方法。
        PageInfo<Map<String, Object>> enterprisePage = this.findEnterprisePage(params);
        //判断是否存在数据
        long total = enterprisePage.getTotal();
        if (0 < total) {
            //获取查询结果列表
            List<Map<String, Object>> list = enterprisePage.getList();
            if (null != list && list.size() > 0) {
                return list.get(0);
            }
        }
        return null;
    }

    @Override
    public Map<String, Object> saveEnterprise(Map<String, Object> params) {
        // 组装方法要判空
        if (params == null || params.isEmpty()) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "params");
        }

        EnterpriseEntity entity = new EnterpriseEntity();
        convertToEntity(params, entity);

        EnterpriseEntity result = enterpriseDao.save(entity);
        params.put("id", result.getId());
        return params;
    }

    @Override
    public void updateEnterprise(Map<String, Object> params) {
        //update要先根据ID获取BO对象，然后在拷贝map里面的值
        Integer id = MapUtils.getInteger(params, EnterpriseApiConstants.ID);
        if (!params.containsKey(EnterpriseApiConstants.ID)) {
            throw ExceptionFactory.getBizException("entp-mg-00002");
        }
        EnterpriseEntity entity = enterpriseDao.findOne(id);
        if (entity == null) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "findOne");
        }
        convertToEntity(params, entity);

        enterpriseDao.update(entity);
    }

    @Override
    public void deleteEnterprise(Map<String, Object> params) {
        Integer id = MapUtils.getInteger(params, EnterpriseApiConstants.ID);
        if (id == null) {
            throw ExceptionFactory.getBizException("entp-mg-00002");
        }
        EnterpriseEntity entity = enterpriseDao.findOne(id);
        if (entity == null) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "findOne");
        }
        entity.setDelInd("1");
        enterpriseDao.update(entity);
    }

    private void convertToEntity(Map<String, Object> params, EnterpriseEntity entity) {
        String entpCode = MapUtil.getString(params, EnterpriseApiConstants.ENTP_CODE);
        String entpNm = MapUtil.getString(params, EnterpriseApiConstants.ENTP_NM);
        String entpOrgCode = MapUtil.getString(params, EnterpriseApiConstants.ENTP_ORG_CODE);
        String entpOrgLvlCd = MapUtil.getString(params, EnterpriseApiConstants.ENTP_ORG_LVL_CD);
        String entpCrdtTpCd = MapUtil.getString(params, EnterpriseApiConstants.ENTP_CRDT_TP_CD);
        String entpCrdtNo = MapUtil.getString(params, EnterpriseApiConstants.ENTP_CRDT_NO);
        String entpEmailAdr = MapUtil.getString(params, EnterpriseApiConstants.ENTP_EMAIL_ADR);
        String entpFaxNo = MapUtil.getString(params, EnterpriseApiConstants.ENTP_FAX_NO);
        String entpCtcpsn = MapUtil.getString(params, EnterpriseApiConstants.ENTP_CTCPSN);
        String entpCtcpsnTel = MapUtil.getString(params, EnterpriseApiConstants.ENTP_CTCPSN_TEL);
        String entpRgstAdr = MapUtil.getString(params, EnterpriseApiConstants.ENTP_RGST_ADR);
        String wthrCptplHostEntp = MapUtil.getString(params, EnterpriseApiConstants.WTHR_CPTPL_HOST_ENTP);
        String cptplModel = MapUtil.getString(params, EnterpriseApiConstants.CPTPL_MODEL);
        String wthrSign = MapUtil.getString(params, EnterpriseApiConstants.WTHR_SIGN);
        String parentEntpCode = MapUtil.getString(params, EnterpriseApiConstants.PARENT_ENTP_CODE);

        if (StringUtil.isNotBlank(entpCode)) {
            entity.setEntpCode(entpCode);
        }
        if (StringUtil.isNotBlank(entpNm)) {
            entity.setEntpNm(entpNm);
        }
        if (StringUtil.isNotBlank(entpOrgCode)) {
            entity.setEntpOrgCode(entpOrgCode);
        }
        if (StringUtil.isNotBlank(entpOrgLvlCd)) {
            entity.setEntpOrgLvlCd(entpOrgLvlCd);
        }
        if (StringUtil.isNotBlank(entpCrdtTpCd)) {
            entity.setEntpCrdtTpCd(entpCrdtTpCd);
        }
        if (StringUtil.isNotBlank(entpCrdtNo)) {
            entity.setEntpCrdtNo(entpCrdtNo);
        }
        if (StringUtil.isNotBlank(entpEmailAdr)) {
            entity.setEntpEmailAdr(entpEmailAdr);
        }
        if (StringUtil.isNotBlank(entpFaxNo)) {
            entity.setEntpFaxNo(entpFaxNo);
        }
        if (StringUtil.isNotBlank(entpCtcpsn)) {
            entity.setEntpCtcpsn(entpCtcpsn);
        }
        if (StringUtil.isNotBlank(entpCtcpsnTel)) {
            entity.setEntpCtcpsnTel(entpCtcpsnTel);
        }
        if (StringUtil.isNotBlank(entpRgstAdr)) {
            entity.setEntpRgstAdr(entpRgstAdr);
        }
        if (StringUtil.isNotBlank(wthrCptplHostEntp)) {
            entity.setWthrCptplHostEntp(wthrCptplHostEntp);
        }
        if (StringUtil.isNotBlank(cptplModel)) {
            entity.setCptplModel(cptplModel);
        }
        if (StringUtil.isNotBlank(wthrSign)) {
            entity.setWthrSign(wthrSign);
        }
        if (StringUtil.isNotBlank(parentEntpCode)) {
            entity.setParentEntpCode(parentEntpCode);
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
        String tenantId = MapUtils.getString(params, "tenantId");
        if (!StringUtils.isEmpty(tenantId)) {
            entity.setTenantId(tenantId);
        }
    }

	@Override
	public Map<String, Object> findEntpCodeByLoginName(Map<String, Object> params) {
		List<Map<String, Object>> list = this.enterpriseDao.findEntpCodeByLoginName(params);
		if(!CollectionUtil.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
	
}
