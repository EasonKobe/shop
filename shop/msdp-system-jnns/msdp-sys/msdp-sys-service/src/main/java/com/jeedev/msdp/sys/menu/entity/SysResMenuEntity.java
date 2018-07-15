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
@Table(name = "SYS_RES_MENU")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_RES_MENU")
public class SysResMenuEntity extends BaseEntity<Integer> implements Serializable {

	private static final long serialVersionUID = -8650542884652653302L;

	/**
	 * 菜单编码
	 */
	@Column(name = "MENU_CODE")
	private String menuCode;

	/**
	 * 菜单名称
	 */
	@Column(name = "MENU_NAME")
	private String menuName;

	/**
	 * 菜单类型(页面view/按钮click)
	 */
	@Column(name = "MENU_TYPE_CD")
	private String menuTypeCd;

	/**
	 * 父菜单编号
	 */
	@Column(name = "PARENT_MENU_CODE")
	private String parentMenuCode;

	/**
	 * 客户端编号
	 */
	@Column(name = "CLNTEND_ID")
	private String clntendId;
	/**
	 * 菜单URL
	 */
	@Column(name = "URL")
	private String url;

	/**
	 * 排序
	 */
	@Column(name = "SORT")
	private Integer sort;

	/**
	 * 叶子节点标识(非叶子节点0/叶子节点1)
	 */
	@Column(name = "LEAF_FLAG_CD")
	private String leafFlagCd;

	/**
	 * 菜单图标
	 */
	@Column(name = "ICON")
	private String icon;

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

	public String getClntendId() {
		return clntendId;
	}

	public void setClntendId(String clntendId) {
		this.clntendId = clntendId;
	}

	public SysResMenuEntity coverToEntity(Map<String, Object> params, SysResMenuEntity entity) {
		if (entity == null) {
			entity = new SysResMenuEntity();
		}
		try {
			String menuCode = MapUtils.getString(params, "menuCode");
			if (!StringUtils.isEmpty(menuCode)) {
				entity.setMenuCode(menuCode);
			}
			String menuName = MapUtils.getString(params, "menuName");
			if (!StringUtils.isEmpty(menuName)) {
				entity.setMenuName(menuName);
			}
			String menuTypeCd = MapUtils.getString(params, "menuTypeCd");
			if (!StringUtils.isEmpty(menuTypeCd)) {
				entity.setMenuTypeCd(menuTypeCd);
			}
			String parentMenuCode = MapUtils.getString(params, "parentMenuCode");
			if (!StringUtils.isEmpty(parentMenuCode)) {
				entity.setParentMenuCode(parentMenuCode);
			}
			String url = MapUtils.getString(params, "url");
			if (!StringUtils.isEmpty(url)) {
				entity.setUrl(url);
			}
			Integer sort = MapUtils.getInteger(params, "sort");
			if (!StringUtils.isEmpty(sort)) {
				entity.setSort(sort);
			}
			String leafFlagCd = MapUtils.getString(params, "leafFlagCd");
			if (!StringUtils.isEmpty(leafFlagCd)) {
				entity.setLeafFlagCd(leafFlagCd);
			}
			String icon = MapUtils.getString(params, "icon");
			if (!StringUtils.isEmpty(icon)) {
				entity.setIcon(icon);
			}
			String statusCd = MapUtils.getString(params, "statusCd");
			if (!StringUtils.isEmpty(statusCd)) {
				entity.setStatusCd(statusCd);
			}
			String remark = MapUtils.getString(params, "remark");
			if (!StringUtils.isEmpty(remark)) {
				entity.setRemark(remark);
			}
			 if (params.containsKey("clntendId")) {
				   String clntendId= MapUtils.getString(params, "clntendId");//客户端ID
					entity.setClntendId(clntendId);
			 }

			// BaseEntity//BaseEntity
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
			throw ExceptionFactory.getBizException("sys-00001", "1");
		}
	}

	public String getMenuCode() {
		return menuCode;
	}

	public void setMenuCode(String menuCode) {
		this.menuCode = menuCode;
	}

	public String getMenuName() {
		return menuName;
	}

	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}

	public String getMenuTypeCd() {
		return menuTypeCd;
	}

	public void setMenuTypeCd(String menuTypeCd) {
		this.menuTypeCd = menuTypeCd;
	}

	public String getParentMenuCode() {
		return parentMenuCode;
	}

	public void setParentMenuCode(String parentMenuCode) {
		this.parentMenuCode = parentMenuCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Integer getSort() {
		return sort;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public String getLeafFlagCd() {
		return leafFlagCd;
	}

	public void setLeafFlagCd(String leafFlagCd) {
		this.leafFlagCd = leafFlagCd;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
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

}