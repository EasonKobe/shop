package com.jeedev.msdp.sys.org.entity;

import java.io.Serializable;
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
@Table(name = "SYS_ORGANIZATION")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_ORGANIZATION")
public class SysOrganizationEntity extends BaseEntity<Integer> implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 机构代码
	 */
	@Column(name = "ORG_CODE")
	private String orgCode;

	/**
	 * 机构名称
	 */
	@Column(name = "ORG_NAME")
	private String orgName;

	/**
	 * 上级机构代码
	 */
	@Column(name = "PARENT_ORG_CODE")
	private String parentOrgCode;

	/**
	 * 机构级别(DEPT_LEVEL:一级1\二级2\三级3\四级4)
	 */
	@Column(name = "ORG_LEVEL_CD")
	private String orgLevelCd;

	/**
	 * 排序
	 */
	@Column(name = "SORT")
	private Integer sort;
 

	/**
	 * 状态(STATUS:无效0\有效1)
	 */
	@Column(name = "STATUS_CD")
	private String statusCd;

	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;
	 /**
	 * 客户端编号
	 */
	@Column(name = "CLNTEND_ID")
	private String clntendId;

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getParentOrgCode() {
		return parentOrgCode;
	}

	public void setParentOrgCode(String parentOrgCode) {
		this.parentOrgCode = parentOrgCode;
	}

	public String getOrgLevelCd() {
		return orgLevelCd;
	}

	public void setOrgLevelCd(String orgLevelCd) {
		this.orgLevelCd = orgLevelCd;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClntendId() {
		return clntendId;
	}

	public void setClntendId(String clntendId) {
		this.clntendId = clntendId;
	}

	public SysOrganizationEntity coverToEntity(Map<String, Object> params, SysOrganizationEntity entity) {
		if (entity == null) {
			entity = new SysOrganizationEntity();
		}
		try {
			String orgCode = MapUtils.getString(params, "orgCode");
	    	if(!StringUtils.isEmpty(orgCode)){
	    		entity.setOrgCode(orgCode);
	    	}
	    	String orgName = MapUtils.getString(params, "orgName");
	    	if(!StringUtils.isEmpty(orgName)){
	    		entity.setOrgName(orgName);
	    	}
	    	String parentOrgCode = MapUtils.getString(params, "parentOrgCode");
	    	if(!StringUtils.isEmpty(parentOrgCode)){
	    		entity.setParentOrgCode(parentOrgCode);
	    	}
	    	String orgLevelCd = MapUtils.getString(params, "orgLevelCd");
	    	if(!StringUtils.isEmpty(orgLevelCd)){
	    		entity.setOrgLevelCd(orgLevelCd);
	    	}
	    	Integer sort = MapUtils.getInteger(params, "sort");
	    	if(!StringUtils.isEmpty(sort)){
	    		entity.setSort(sort);
	    	}
	    	String statusCd = MapUtils.getString(params, "statusCd");
	    	if(!StringUtils.isEmpty(statusCd)){
	    		entity.setStatusCd(statusCd);
	    	}
	    	String remark = MapUtils.getString(params, "remark");
	    	if(!StringUtils.isEmpty(remark)){
	    		entity.setRemark(remark);
	    	}
	    	if (params.containsKey("clntendId")) {
				   String clntendId= MapUtils.getString(params, "clntendId");//客户端ID
					entity.setClntendId(clntendId);
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