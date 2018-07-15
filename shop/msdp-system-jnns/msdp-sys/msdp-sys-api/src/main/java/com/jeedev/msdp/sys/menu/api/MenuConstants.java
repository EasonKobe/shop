package com.jeedev.msdp.sys.menu.api;

/**
 * 
 * @类名称 MenuConstants.java
 * @类描述 <pre>菜单常量类</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月28日 下午2:13:56
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月28日             
 *     ----------------------------------------------
 * </pre>
 */
public class MenuConstants {
	/**
	 * 主键编号
	 */
	public final static String ID = "id";
	/**
	 * 菜单名称
	 */
	public final static String MENU_NAME = "menuName";
	/**
	 * 菜单编号
	 */
	public final static String MENU_CODE = "menuCode";
	
	/**
	 * 菜单类型(目录no-source/页面view/按钮click)
	 */
	public final static String MENU_TYPE_CD = "menuTypeCd";
	
	/**
	 * 父菜单编号
	 */ 
	public final static String PARENT_MENU_CODE = "parentMenuCode";
	/**
	 * 菜单状态 0无效 1有效
	 */
	public final static String STATUS_CD = "statusCd";
	/**
	 * 删除状态 0未删除 1已删除
	 */
	public final static String DEL_IND = "delInd";
	/**
	 * 角色编号
	 */
	public final static String ROLE_CODE = "roleCode";
	/**
	 * 角色名称
	 */
	public final static String ROLE_NAME = "roleName";
	
	/**
	 * 权限编码
	 */
	public final static String PERMISSION_CODE = "permissionCode";
	/**
	 * 权限编码集合
	 */
	public final static String PERMISSION_CODES = "permissionCodes";
	/**
	 * 资源菜单编码
	 */
	public final static String RES_CODE = "resCode";
	/**
	 * 菜单集合
	 */
	public final static String MENU_CODES = "menuCodes";
	/**
	 * 叶子节点标识(非叶子节点0/叶子节点1)
	 */
	public final static String LEAF_FLAG_CD = "leafFlagCd";
	
	
	
}
