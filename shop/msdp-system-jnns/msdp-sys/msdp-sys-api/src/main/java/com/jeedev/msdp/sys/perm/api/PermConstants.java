package com.jeedev.msdp.sys.perm.api;

/**
 * 
 * @类名称 RoleConstants.java
 * @类描述 <pre>权限常量信息</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月23日 下午5:12:47
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月23日             
 *     ----------------------------------------------
 * </pre>
 */
public class PermConstants {

	/**
	 * 主键编号
	 */
	public final static String Id = "id";
	/**
	 * 权限编号
	 */
	public  final static String PERMISSION_CODE="permissionCode";
	/**
	 * 权限类型(菜单0页面元素1文件2)
	 */ 
	public final static String PERMISSION_TYPE_CD="permissionTypeCd";
	/**
	 * 资源编号
	 */ 
	public final static String RES_CODE="resCode";

	/**
	 * 权限名称
	 */ 
	public final static String PERMISSION_NAME="permissionName";

	/**
	 * 是否系统内置权限(否0是1)
	 */ 
	public final static String IS_BUILT_IN="isBuiltIn";
	/**
	 * 状态：无效0有效1
	 */ 
	public final static String STATUS_CD="statusCd";
	/**
	 * 删除标识：未删除 0已经删除1
	 */ 
	public final static String DEL_IND="delInd";
	
	
}
