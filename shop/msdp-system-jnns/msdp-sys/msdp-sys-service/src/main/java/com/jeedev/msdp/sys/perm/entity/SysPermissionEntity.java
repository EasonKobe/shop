package com.jeedev.msdp.sys.perm.entity;

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
@Table(name = "SYS_PERMISSION")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_PERMISSION")
public class SysPermissionEntity extends BaseEntity<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 权限编号
	 */
	@Column(name = "PERMISSION_CODE")
	private String permissionCode;

	/**
	 * 权限类型(菜单0页面元素1文件2)
	 */
	@Column(name = "PERMISSION_TYPE_CD")
	private String permissionTypeCd;

	/**
	 * 资源编号
	 */
	@Column(name = "RES_CODE")
	private String resCode;

	/**
	 * 权限名称
	 */
	@Column(name = "PERMISSION_NAME")
	private String permissionName;

	/**
	 * 是否系统内置权限(否0是1)
	 */
	@Column(name = "IS_BUILT_IN")
	private String isBuiltIn;
 

	/**
	 * 状态：无效0有效1
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

	
	public String getClntendId() {
		return clntendId;
	}

	public void setClntendId(String clntendId) {
		this.clntendId = clntendId;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
	}

	public String getPermissionTypeCd() {
		return permissionTypeCd;
	}

	public void setPermissionTypeCd(String permissionTypeCd) {
		this.permissionTypeCd = permissionTypeCd;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getPermissionName() {
		return permissionName;
	}

	public void setPermissionName(String permissionName) {
		this.permissionName = permissionName;
	}

	public String getIsBuiltIn() {
		return isBuiltIn;
	}

	public void setIsBuiltIn(String isBuiltIn) {
		this.isBuiltIn = isBuiltIn;
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

	public SysPermissionEntity coverToEntity(Map<String, Object> params, SysPermissionEntity entity) {
		if (entity == null) {
			entity = new SysPermissionEntity();
		}
		try {
			String permissionCode = MapUtils.getString(params, "permissionCode");
	    	if(!StringUtils.isEmpty(permissionCode)){
	    		entity.setPermissionCode(permissionCode);
	    	}
	    	String permissionTypeCd = MapUtils.getString(params, "permissionTypeCd");
	    	if(!StringUtils.isEmpty(permissionTypeCd)){
	    		entity.setPermissionTypeCd(permissionTypeCd);
	    	}
	    	String resCode = MapUtils.getString(params, "resCode");
	    	if(!StringUtils.isEmpty(resCode)){
	    		entity.setResCode(resCode);
	    	}
	    	String permissionName = MapUtils.getString(params, "permissionName");
	    	if(!StringUtils.isEmpty(permissionName)){
	    		entity.setPermissionName(permissionName);
	    	}
	    	String isBuiltIn = MapUtils.getString(params, "isBuiltIn");
	    	if(!StringUtils.isEmpty(isBuiltIn)){
	    		entity.setIsBuiltIn(isBuiltIn);
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
			//对租户分配菜单时同时生成资源
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