package com.jeedev.msdp.sys.menu.service;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 ISysResMenuService.java
 * @类描述 <pre></pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 下午8:24:46
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月17日             
 *     ----------------------------------------------
 * </pre>
 */
public interface ISysResMenuService {
	/**
	 * 
	 * @方法名称 findMenuPage
	 * @功能描述 <pre>分页查询菜单信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params 查询参数
	 * menuCode 菜单编码
	 * menuName 菜单名称
	 * menuTypeCd 菜单类型
	 * parentMenuCode 父菜单编号
	 * url 菜单url
	 * sort 排序
	 * icon 菜单图标
	 * statusCd 状态
	 * remark 备注
	 * @param pageParam 分页
	 * @return 分页信息
	 */
	public PageInfo<Map<String, Object>> findMenuPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * 
	 * @方法名称 findMenu
	 * @功能描述 <pre>查询选中菜单（id不为空则根据id查询忽略其他条件）</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params 参数
     * menuCode 菜单编码
	 * parentMenuCode 父菜单编号
	 * id 主键
	 * @return 菜单信息map
	 */
	public Map<String, Object> getMenuMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveMenu
	 * @功能描述 <pre>保存菜单信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params 保存参数
	 * menuCode 菜单编码
	 * menuName 菜单名称
	 * menuTypeCd 菜单类型
	 * parentMenuCode 父菜单编号
	 * url 菜单url
	 * sort 排序
	 * icon 菜单图标
	 * statusCd 状态
	 * remark 备注
	 * @return 菜单信息map
	 */
	public Map<String, Object> saveMenu(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateMenu
	 * @功能描述 <pre>更新菜单</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params 更新参数
	 * menuCode 菜单编码
	 * menuName 菜单名称
	 * menuTypeCd 菜单类型
	 * parentMenuCode 父菜单编号
	 * url 菜单url
	 * sort 排序
	 * icon 菜单图标
	 * statusCd 状态
	 * remark 备注
	 * id 主键
	 */
	public void updateMenu(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteMenu
	 * @功能描述 <pre>删除菜单信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params 删除参数
	 * id 主键
	 */
	public void deleteMenu(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 findChildrenMenus
	 * @功能描述 <pre>查询子菜单</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月17日 下午2:34:46
	 * @param params
	 * @return
	 */
	public List<Map<String, Object>> findChildrenMenus(Map<String, Object> params);
}
