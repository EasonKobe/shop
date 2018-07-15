package com.jeedev.msdp.sys.menu.api;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 MenuApi.java
 * @类描述 <pre>菜单对外接口</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 上午10:19:18
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
public interface MenuApi {
	/**
	 * @方法名称 findMenuPage
	 * @功能描述 <pre>分页查询菜单数据 </pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:19:36
	 * @param params
	 * @param  pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findMenuPage(Map<String, Object> params, PageParam pageParam);
	/**
	 * @方法名称 getMenuMap
	 * @功能描述 <pre>获取菜单详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:29:00
	 * @param params
	 * @return
	 */
	public Map<String, Object> getMenuMap(Map<String, Object> params);
	/**
	 * @方法名称 saveMenuTrans
	 * @功能描述 <pre>新增&变更菜单信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:29:36
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveMenuTrans(Map<String, Object> params);
	/**
	 * @方法名称 deleteMenuTrans
	 * @功能描述 <pre>删除菜单信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:30:00
	 * @param params
	 * @return
	 */
	public void deleteMenuTrans(Map<String, Object> params);
	/**
	 * @方法名称 findUserMenuPage
	 * @功能描述 <pre>获取用户拥有的菜单权限</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:08:30
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findUserMenuPage(Map<String, Object> params, PageParam pageParam);
	
	/**
	 * 分页查询菜单的事件关联关系
	 * @方法名称 findMenuEventRelPage
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午5:18:40
	 * @param params
	 * menuCode 菜单编号
	 * menuCodes 菜单编号集合
	 * eventName 事件名称
	 * eventCode 事件编号
	 * eventCodes 事件编号集合
	 * url 事件url
	 * @param pageParam
	 * @return
	 * id 菜单事件关联关系主键ID
	 * eventCode 事件编号
	 * menuCode 菜单编号
	 * statusCd 状态
	 * remark 备注
	 * expdId 扩展Id
	 * eventName 事件名称 
	 * url 事件url
	 * menuName 菜单名称 
	 */
	public PageInfo<Map<String, Object>> findMenuEventRelPage(Map<String, Object> params, PageParam pageParam);
	/**
	 * @方法名称 saveMenuEventRelTrans
	 * @功能描述 <pre>新增&变更菜单的事件关联关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:29:36
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveMenuEventRelTrans(Map<String, Object> params);
	/**
	 * @方法名称 deleteMenuEventRelTrans
	 * @功能描述 <pre>删除菜单的事件关联关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月17日 上午10:30:00
	 * @param params
	 * @return
	 */
	public void deleteMenuEventRelTrans(Map<String, Object> params);
	
}
