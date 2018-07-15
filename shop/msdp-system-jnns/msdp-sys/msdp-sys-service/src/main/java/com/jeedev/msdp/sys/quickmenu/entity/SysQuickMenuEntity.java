package com.jeedev.msdp.sys.quickmenu.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.collections.MapUtils;

import com.jeedev.msdp.core.data.BaseEntity;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.quickmenu.api.QuickMenuConstants;
@Entity
@Table(name = "SYS_QUICK_MENU")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_QUICK_MENU")
public class SysQuickMenuEntity extends BaseEntity<Integer> implements Serializable {

	private static final long serialVersionUID = -64640793225195475L;

	/**
	 * 归属用户
	 */
	@Column(name = "USER_NUM")
	private String userNum;
	/**
	 * 菜单代码
	 */
	@Column(name = "MENU_CODE")
	private String menuCode;
	/**
	 *  菜单名称
	 */
	@Column(name = "MENU_Name")
	private String menuName;
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
	 * 菜单图标
	 */
	@Column(name = "ICON")
	private String icon;
	/**
	 * 客户端ID
	 */
	@Column(name = "CLNTEND_ID")
	private String clntendId;

	public SysQuickMenuEntity coverToEntity(Map<String, Object> params, SysQuickMenuEntity entity) {
		if (entity == null) {
			entity = new SysQuickMenuEntity();
		}
		try {
			if (params.containsKey(QuickMenuConstants.USER_NUM)) {
				this.userNum = MapUtils.getString(params, QuickMenuConstants.USER_NUM);
				entity.setUserNum(userNum);
			}
			if (params.containsKey(QuickMenuConstants.MENU_CODE)) {
				this.menuCode = MapUtils.getString(params, QuickMenuConstants.MENU_CODE);
				entity.setMenuCode(menuCode);
			}
			if (params.containsKey(QuickMenuConstants.MENU_NAME)) {
				this.menuName = MapUtils.getString(params, QuickMenuConstants.MENU_NAME);
				entity.setMenuName(menuName);
			}
			if (params.containsKey(QuickMenuConstants.URL)) {
				this.url = MapUtils.getString(params, QuickMenuConstants.URL);
				entity.setUrl(url);
			}
			if (params.containsKey(QuickMenuConstants.SORT)) {
				this.sort = MapUtils.getInteger(params, QuickMenuConstants.SORT);
				entity.setSort(sort);
			}
			if (params.containsKey(QuickMenuConstants.ICON)) {
				this.icon = MapUtils.getString(params, QuickMenuConstants.ICON);
				entity.setIcon(icon);
			}
			
			if (params.containsKey(QuickMenuConstants.CLNTEND_ID)) {
				this.clntendId = MapUtils.getString(params, QuickMenuConstants.CLNTEND_ID);
				entity.setClntendId(clntendId);
			}
			// BaseEntity
			if (params.containsKey(QuickMenuConstants.ID)) {
				this.id = MapUtils.getInteger(params, QuickMenuConstants.ID);
				entity.setId(id);
			}
			if (params.containsKey(QuickMenuConstants.DEL_IND)) {
				this.delInd = MapUtils.getString(params, QuickMenuConstants.DEL_IND);
				entity.setDelInd(delInd);
			}
			return entity;
		} catch (Exception e) {
			throw ExceptionFactory.getBizException("msg-00001", "1");
		}
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
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

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getClntendId() {
		return clntendId;
	}

	public void setClntendId(String clntendId) {
		this.clntendId = clntendId;
	}

	

}
