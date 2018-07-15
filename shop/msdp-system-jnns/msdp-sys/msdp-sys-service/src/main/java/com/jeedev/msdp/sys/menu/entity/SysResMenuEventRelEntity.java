package com.jeedev.msdp.sys.menu.entity;

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
@Table(name = "SYS_RES_MENU_EVENT_REL")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_RES_MENU_EVENT_REL")
public class SysResMenuEventRelEntity  extends BaseEntity<Integer> implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5052311368639048591L;
	/**
	 * 菜单编码
	 */
	@Column(name = "MENU_CODE")
	private String menuCode;
	/**
	 * 事件编码
	 */
	@Column(name = "EVENT_CODE")
	private String eventCode;
	
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
	
	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
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

	public SysResMenuEventRelEntity coverToEntity(Map<String, Object> params, SysResMenuEventRelEntity entity) {
		if (entity == null) {
			entity = new SysResMenuEventRelEntity();
		}
		try { 
			 String menuCode= MapUtils.getString(params, "menuCode");//菜单编码 
			 if (!StringUtils.isEmpty(menuCode)) {
					entity.setMenuCode(menuCode);
				}
			 String eventCode= MapUtils.getString(params, "eventCode");//事件编码
			 if (!StringUtils.isEmpty(eventCode)) {
					entity.setEventCode(eventCode);
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
