package com.jeedev.msdp.sys.user.entity;

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
 * 
 * @类名称 SysUserRoleRelEntity.java
 * @类描述 <pre>用户角色关联实体</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 下午3:36:21
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月17日             
 *     ----------------------------------------------
 * </pre>
 */
@Entity
@Table(name = "SYS_USER_ROLE_REL")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_USER_ROLE_REL")
public class SysUserRoleRelEntity extends BaseEntity<Integer> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2001496981513366292L;

	/**
	 * 用户编码
	 */
	@Column(name = "USER_NUM")
	private String userNum;

	/**
	 * 角色
	 */
	@Column(name = "ROLE_CODE")
	private String roleCode;

	/**
	 * 状态STATUS:无效0/有效1
	 */
	@Column(name = "STATUS_CD")
	private String statusCd;

	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;

	@Column(name = "DEFAULT_IND")
	private String defaultInd;
	 
	public String getUserNum() {
		return userNum;
	}


	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}


	public String getRoleCode() {
		return roleCode;
	}


	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
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


	public String getDefaultInd() {
		return defaultInd;
	}


	public void setDefaultInd(String defaultInd) {
		this.defaultInd = defaultInd;
	}


	public SysUserRoleRelEntity coverToEntity(Map<String, Object> params, SysUserRoleRelEntity entity) {
		if (entity == null) {
			entity = new SysUserRoleRelEntity();
		}
		try { 
			 String userNum= MapUtils.getString(params, "userNum");//用户编码  
			 if (!StringUtils.isEmpty(userNum)) {
					entity.setUserNum(userNum);
				}
			 String roleCode= MapUtils.getString(params, "roleCode");//角色编码  
			 if (!StringUtils.isEmpty(roleCode)) {
					entity.setRoleCode(roleCode);
				}
			 String statusCd= MapUtils.getString(params, "statusCd");
			 if (!StringUtils.isEmpty(statusCd)) {
					entity.setStatusCd(statusCd);
				}
			 String remark= MapUtils.getString(params, "remark");
			 if (!StringUtils.isEmpty(statusCd)) {
					entity.setRemark(remark);
				}
			 String defaultInd= MapUtils.getString(params, "defaultInd");
			 if (!StringUtils.isEmpty(defaultInd)) {
					entity.setDefaultInd(defaultInd);
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
				
				//给租户创建管理员时需要赋值
				String tenantId= MapUtils.getString(params, "tenantId");  
				 if (!StringUtils.isEmpty(tenantId)) {
						entity.setTenantId(tenantId);
				}
			return entity;
		} catch (Exception e) {
			throw ExceptionFactory.getBizException("sys-00001","1");
		}
	}
}