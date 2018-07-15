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

@Entity
@Table(name = "SYS_USER_GROUP_REL")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_USER_GROUP_REL")
public class SysUserGroupRelEntity extends BaseEntity<Integer>{

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
     * 组编号
     */
    @Column(name = "GROUP_CODE")
    private String groupCode;

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
    

	public String getUserNum() {
		return userNum;
	}


	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}


	public String getGroupCode() {
		return groupCode;
	}


	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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


	public SysUserGroupRelEntity coverToEntity(Map<String, Object> params, SysUserGroupRelEntity entity) {
		if (entity == null) {
			entity = new SysUserGroupRelEntity();
		}
		try { 
			 String userNum= MapUtils.getString(params, "userNum");//用户编码  
			 if (!StringUtils.isEmpty(userNum)) {
					entity.setUserNum(userNum);
				}
			 String groupCode= MapUtils.getString(params, "groupCode");  
			 if (!StringUtils.isEmpty(groupCode)) {
					entity.setGroupCode(groupCode);
				}
			 String statusCd= MapUtils.getString(params, "statusCd");
			 if (!StringUtils.isEmpty(statusCd)) {
					entity.setStatusCd(statusCd);
				}
			 String remark= MapUtils.getString(params, "remark");
			 if (!StringUtils.isEmpty(statusCd)) {
					entity.setRemark(remark);
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