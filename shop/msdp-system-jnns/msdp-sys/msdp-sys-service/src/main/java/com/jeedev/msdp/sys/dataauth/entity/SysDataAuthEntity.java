package com.jeedev.msdp.sys.dataauth.entity;

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
/**
 * @类名称 SysDataAuthEntity.java
 * @类描述 <pre>数据权限授权表</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月25日 下午4:48:06
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	yuyq            
 *     ----------------------------------------------
 * </pre>
 */
@Entity
@Table(name = "SYS_DATA_AUTH")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_DATA_AUTH")
public class SysDataAuthEntity extends BaseEntity<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据权限编号
	 */
	@Column(name = "DATA_AUTH_CODE")
	private String dataAuthCode;

	/**
	 * 数据权限类型编码
	 */
	@Column(name = "MODEL_CODE")
	private String modelCode;

	/**
	 * 部门代码
	 */
	@Column(name = "DEPT_CODE")
	private String deptCode;
	
	/**
	 * 机构代码
	 */
	@Column(name = "ORG_CODE")
	private String orgCode;
	
	/**
     * 角色代码
     */
    @Column(name = "ROLE_CODE")
    private String roleCode;
    
    /**
	 * 用户编码
	 */
	@Column(name = "USER_NUM")
	private String userNum;
	
	/**
	 * 场景编码
	 */
	@Column(name = "BIZ_SCENE")
	private String bizScene;
	/**
	 * 场景编码
	 */
	@Column(name = "EVENT_CODE")
	private String eventCode;
	/**
	 * 客户端编号
	 */
	@Column(name = "CLNTEND_ID")
	private String clntendId;
	
	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;

	/**
	 * 备注
	 */
	@Column(name = "AUTH_CONDITIONS")
	private String authConditions;
	
	
	public String getAuthConditions() {
		return authConditions;
	}

	public void setAuthConditions(String authConditions) {
		this.authConditions = authConditions;
	}

	public String getDeptCode() {
		return deptCode;
	}

	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getBizScene() {
		return bizScene;
	}

	public void setBizScene(String bizScene) {
		this.bizScene = bizScene;
	}

	public String getClntendId() {
		return clntendId;
	}

	public void setClntendId(String clntendId) {
		this.clntendId = clntendId;
	}

	public String getDataAuthCode() {
		return dataAuthCode;
	}

	public void setDataAuthCode(String dataAuthCode) {
		this.dataAuthCode = dataAuthCode;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public SysDataAuthEntity coverToEntity(Map<String, Object> params, SysDataAuthEntity entity) {
		if (entity == null) {
			entity = new SysDataAuthEntity();
		}
		try {
			String dataAuthCode = MapUtils.getString(params, "dataAuthCode");
			if (!StringUtils.isEmpty(dataAuthCode)) {
				entity.setDataAuthCode(dataAuthCode);
			}
			String modelCode = MapUtils.getString(params, "modelCode");
			if (!StringUtils.isEmpty(modelCode)) {
				entity.setModelCode(modelCode);
			}
			String eventCode = MapUtils.getString(params, "eventCode");
			if (!StringUtils.isEmpty(eventCode)) {
				entity.setEventCode(eventCode);
			}
			
			String remark = MapUtils.getString(params, "remark");
			if (!StringUtils.isEmpty(remark)) {
				entity.setRemark(remark);
			}
			String deptCode = MapUtils.getString(params, "deptCode");
			if (!StringUtils.isEmpty(deptCode)) {
				entity.setDeptCode(deptCode);
			}
			String orgCode = MapUtils.getString(params, "orgCode");
			if (!StringUtils.isEmpty(orgCode)) {
				entity.setOrgCode(orgCode);
			}
			String roleCode = MapUtils.getString(params, "roleCode");
			if (!StringUtils.isEmpty(roleCode)) {
				entity.setRoleCode(roleCode);
			}
			String userNum = MapUtils.getString(params, "userNum");
			if (!StringUtils.isEmpty(userNum)) {
				entity.setUserNum(userNum);
			}
			String bizScene = MapUtils.getString(params, "bizScene");
			if (!StringUtils.isEmpty(bizScene)) {
				entity.setBizScene(bizScene);
			}
			String clntendId = MapUtils.getString(params, "clntendId");
			if (!StringUtils.isEmpty(clntendId)) {
				entity.setClntendId(clntendId);
			}
			String authConditions = MapUtils.getString(params, "authConditions");
			if (!StringUtils.isEmpty(authConditions)) {
				entity.setAuthConditions(authConditions);
			}
			// BaseEntity
			Integer id = MapUtils.getInteger(params, "id");
			if (id != null) {
				entity.setId(id);
			}
			String delInd = MapUtils.getString(params, "delInd");
			if (!StringUtils.isEmpty(delInd)) {
				entity.setDelInd(delInd);
			}
			
			String tenantId = MapUtils.getString(params, "tenantId");
			if (!StringUtils.isEmpty(tenantId)) {
				entity.setTenantId(tenantId);
			}
			
			return entity;
		} catch (Exception e) {
			throw ExceptionFactory.getBizException("sys-00001", "1");
		}
	}

}