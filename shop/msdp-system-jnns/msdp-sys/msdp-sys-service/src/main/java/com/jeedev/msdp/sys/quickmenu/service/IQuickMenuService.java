package com.jeedev.msdp.sys.quickmenu.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 IQuickMenuService.java
 * @类描述 <pre></pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年10月12日 下午4:08:15
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年10月12日             
 *     ----------------------------------------------
 * </pre>
 */
public interface IQuickMenuService {
	/**
	 * 
	 * @方法名称 findQuickMenuPage
	 * @功能描述 <pre>分页获取快捷菜单</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年10月12日 下午3:14:53
	 * @param params
	 * @param pageparam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findQuickMenuPage(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 getQuickMenuMap
	 * @功能描述 <pre>根据条件获取快捷菜单</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年10月12日 下午3:15:10
	 * @param params
	 * @return
	 */
	public Map<String, Object> getQuickMenuMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveQuickMenuTrans
	 * @功能描述 <pre>保存快捷菜单</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年10月12日 下午3:15:26
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveQuickMenuTrans(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 updateQuickMenuTrans
	 * @功能描述 <pre>更新</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年10月12日 下午4:09:41
	 * @param params
	 */
	public void updateQuickMenuTrans(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteQuickMenuTrans
	 * @功能描述 <pre>删除快捷菜单</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年10月12日 下午3:15:37
	 * @param params
	 */
	public void deleteQuickMenuTrans(Map<String, Object> params);
	
}
