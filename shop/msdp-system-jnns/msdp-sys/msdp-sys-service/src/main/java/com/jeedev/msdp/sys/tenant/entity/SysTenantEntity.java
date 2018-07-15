package com.jeedev.msdp.sys.tenant.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;

import com.jeedev.msdp.core.data.BaseEntity;
import com.jeedev.msdp.standard.exception.ExceptionFactory;


@Entity
@Table(name = "SYS_TENANT")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_TENANT")
public class SysTenantEntity extends BaseEntity<Integer> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 3666416947514904293L;

	
	/**
	 * 用户编码
	 */
	@Column(name = "USER_NUM")
	private String userNum;

	/**
	 * 租户名称
	 */
	@Column(name = "TENANT_NAME")
	private String tenantName;
	

	/**
	 * 统一社会编码
	 */
	@Column(name = "ORG_CODE")
	private String orgCode;

	/**
	 * 状态STATUS:无效0/有效1
	 */
	@Column(name = "STATUS_CD")
	private String statusCd;

	
	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}


	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}



	public String getTenantName() {
		return tenantName;
	}

	public void setTenantName(String tenantName) {
		this.tenantName = tenantName;
	}


	public SysTenantEntity coverToEntity(Map<String, Object> params, SysTenantEntity entity) {
		if (entity == null) {
			entity = new SysTenantEntity();
		}
		try { 
			 String userNum= MapUtils.getString(params, "userNum");  
			 if (!StringUtils.isEmpty(userNum)) {
					entity.setUserNum(userNum);
				}
			 
			 String tenantId= MapUtils.getString(params, "tenantId");  
			 if (!StringUtils.isEmpty(tenantId)) {
					entity.setTenantId(tenantId);
				}
			 String tenantName= MapUtils.getString(params, "tenantName");  
			 if (!StringUtils.isEmpty(tenantName)) {
					entity.setTenantName(tenantName);
				}
			 
			 String orgCode= MapUtils.getString(params, "orgCode"); 
			 if (!StringUtils.isEmpty(orgCode)) {
					entity.setOrgCode(orgCode);
				}
			 
			 
			 String statusCd= MapUtils.getString(params, "statusCd");
			 if (!StringUtils.isEmpty(statusCd)) {
					entity.setStatusCd(statusCd);
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
			return entity;
		} catch (Exception e) {
			throw ExceptionFactory.getBizException("sys-00001","1");
		}
	}

}
