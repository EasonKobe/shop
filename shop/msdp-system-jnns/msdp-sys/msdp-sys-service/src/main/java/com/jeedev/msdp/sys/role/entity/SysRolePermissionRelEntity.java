
package com.jeedev.msdp.sys.role.entity;

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
 * 
 * @类名称 SysRolePermissionRel.java
 * @类描述 <pre>角色与权限关系实体类</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月15日 下午7:50:40
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月15日             
 *     ----------------------------------------------
 * </pre>
 */
@Entity
@Table(name = "SYS_ROLE_PERMISSION_REL")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_ROLE_PERMISSION_REL")
public class SysRolePermissionRelEntity extends BaseEntity<Integer> implements Serializable  {
	
	private static final long serialVersionUID = 1L;
	
	/**
     * 角色代码
     */
	 @Column(name = "ROLE_CODE")
	 private String roleCode;
	 
	 /**
     * 权限代码
     */
	 @Column(name = "PERMISSION_CODE")
	 private String permissionCode;
	 
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

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getPermissionCode() {
		return permissionCode;
	}

	public void setPermissionCode(String permissionCode) {
		this.permissionCode = permissionCode;
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
	
	public SysRolePermissionRelEntity coverToEntity(Map<String, Object> params, SysRolePermissionRelEntity entity) {
		if (entity == null) {
			entity = new SysRolePermissionRelEntity();
		}
		try {
			String roleCode = MapUtils.getString(params, "roleCode");
	    	if(!StringUtils.isEmpty(roleCode)){
	    		entity.setRoleCode(roleCode);
	    	}
	    	String permissionCode = MapUtils.getString(params, "permissionCode");
	    	if(!StringUtils.isEmpty(permissionCode)){
	    		entity.setPermissionCode(permissionCode);
	    	}
	    	
	    	String statusCd = MapUtils.getString(params, "statusCd");
	    	if(!StringUtils.isEmpty(statusCd)){
	    		entity.setStatusCd(statusCd);
	    	}
	    	String remark = MapUtils.getString(params, "remark");
	    	if(!StringUtils.isEmpty(remark)){
	    		entity.setRemark(remark);
	    	}
			
			//BaseEntity
	    	//BaseEntity
			Integer id = MapUtils.getInteger(params, "id");
			if (id != null) {
				entity.setId(id);
			}
			String delInd = MapUtils.getString(params, "delInd");
			if (!StringUtils.isEmpty(delInd)) {
				entity.setDelInd(delInd);
			}
			//给租户分配菜单时需要赋值
			String tenantId= MapUtils.getString(params, "tenantId");  
			 if (!StringUtils.isEmpty(tenantId)) {
					entity.setTenantId(tenantId);
			}
			return entity;
		} catch (Exception e) {
			throw ExceptionFactory.getBizException("pre-00001","1");
		}
	}
    
    
}
