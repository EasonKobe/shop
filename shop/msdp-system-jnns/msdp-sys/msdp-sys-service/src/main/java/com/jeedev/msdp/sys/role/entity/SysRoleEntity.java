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
 * @类名称 SysRole.java
 * @类描述 <pre>角色实体类</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月15日 下午7:48:12
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
@Table(name = "SYS_ROLE")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_ROLE")
public class SysRoleEntity extends BaseEntity<Integer> implements Serializable {

    private static final long serialVersionUID = 1L;
	/**
     * 角色代码
     */
    @Column(name = "ROLE_CODE")
    private String roleCode;

    /**
     * 角色名称
     */
    @Column(name = "ROLE_NAME")
    private String roleName;

    /**
     * 父级节点编号
     */
    @Column(name = "PARENT_ROLE_CODE")
    private String parentRoleCode;
   
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
    
    /**
	 * 客户端编号
	 */
	@Column(name = "CLNTEND_ID")
	private String clntendId;

	/**
	 * 排序
	 */
	@Column(name = "SORT")
	private Integer sort;
	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
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

	public String getParentRoleCode() {
		return parentRoleCode;
	}

	public void setParentRoleCode(String parentRoleCode) {
		this.parentRoleCode = parentRoleCode;
	}

	public String getClntendId() {
		return clntendId;
	}

	public void setClntendId(String clntendId) {
		this.clntendId = clntendId;
	}
	

	public SysRoleEntity coverToEntity(Map<String, Object> params, SysRoleEntity entity) {
		if (entity == null) {
			entity = new SysRoleEntity();
		}
		try {
			String roleCode = MapUtils.getString(params, "roleCode");
	    	if(!StringUtils.isEmpty(roleCode)){
	    		entity.setRoleCode(roleCode);
	    	}
	    	String roleName = MapUtils.getString(params, "roleName");
	    	if(!StringUtils.isEmpty(roleName)){
	    		entity.setRoleName(roleName);
	    	}
	    	String parentRoleCode = MapUtils.getString(params, "parentRoleCode");
	    	if(!StringUtils.isEmpty(parentRoleCode)){
	    		entity.setParentRoleCode(parentRoleCode);
	    	}
	    	String statusCd = MapUtils.getString(params, "statusCd");
	    	if(!StringUtils.isEmpty(statusCd)){
	    		entity.setStatusCd(statusCd);
	    	}
	    	String remark = MapUtils.getString(params, "remark");
	    	if(!StringUtils.isEmpty(remark)){
	    		entity.setRemark(remark);
	    	}
	    	Integer sort = MapUtils.getInteger(params, "sort");
			if (!StringUtils.isEmpty(sort)) {
				entity.setSort(sort);
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
			//给租户创建管理员角色时需要赋值
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