package com.jeedev.msdp.sys.dept.entity;

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
 * @类名称 SysDepartmentEntity.java
 * @类描述 <pre>部门实体类</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月15日 上午11:12:13
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
@Table(name = "SYS_DEPARTMENT")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_DEPARTMENT")
public class SysDepartmentEntity extends BaseEntity<Integer> implements Serializable
{
	private static final long serialVersionUID = 1L;

	/**
	 * 部门代码
	 */
	@Column(name = "DEPT_CODE")
	private String deptCode;

	/**
	 * 部门名称
	 */
	@Column(name = "DEPT_NAME")
	private String deptName;
	/**
	 * 上级部门代码
	 */
	@Column(name = "PARENT_DEPT_CODE")
	private String parentDeptCode;
	/**
	 * 部门级别(DEPT_LEVEL:一级1\二级2\三级3\四级4)
	 */
	@Column(name = "DEPT_LEVEL_CD")
	private String deptLevelCd;
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
	
	
	public String getDeptCode() {
		return deptCode;
	}
	public void setDeptCode(String deptCode) {
		this.deptCode = deptCode;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public String getParentDeptCode() {
		return parentDeptCode;
	}
	public void setParentDeptCode(String parentDeptCode) {
		this.parentDeptCode = parentDeptCode;
	}
	public String getDeptLevelCd() {
		return deptLevelCd;
	}
	public void setDeptLevelCd(String deptLevelCd) {
		this.deptLevelCd = deptLevelCd;
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
	public SysDepartmentEntity coverToEntity(Map<String, Object> params, SysDepartmentEntity entity) {

		if (entity == null) {
			entity = new SysDepartmentEntity();
		}
		try {
			String deptCode = MapUtils.getString(params, "deptCode");//部门代码
	    	if(!StringUtils.isEmpty(deptCode)){
	    		entity.setDeptCode(deptCode);
	    	}
	    	String deptName  = MapUtils.getString(params, "deptName");//部门名称
	    	if(!StringUtils.isEmpty(deptName )){
	    		entity.setDeptName(deptName);
	    	}
	    	String parentDeptCode = MapUtils.getString(params, "parentDeptCode");//上级部门代码
	    	if(!StringUtils.isEmpty(parentDeptCode)){
	    		entity.setParentDeptCode(parentDeptCode);
	    	}
	    	String deptLevelCd = MapUtils.getString(params, "deptLevelCd");//部门级别
	    	if(!StringUtils.isEmpty(deptLevelCd)){
	    		entity.setDeptLevelCd(deptLevelCd);
	    	} 
	    	Integer sort = MapUtils.getInteger(params, "sort");//排序
	    	if(sort != null){
	    		entity.setSort(sort);
	    	} 
	    	String statusCd = MapUtils.getString(params, "statusCd");//状态
	    	if(!StringUtils.isEmpty(statusCd)){
	    		entity.setStatusCd(statusCd);
	    	}
	    	String remark = MapUtils.getString(params, "remark");//备注
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