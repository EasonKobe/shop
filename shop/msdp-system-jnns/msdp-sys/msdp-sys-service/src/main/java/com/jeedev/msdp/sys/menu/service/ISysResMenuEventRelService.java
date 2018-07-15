package com.jeedev.msdp.sys.menu.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

public interface ISysResMenuEventRelService {
	/**
	 * 分页查询菜单与事件关联关系信息
	 * @方法名称 findSysResMenuEventRelPage
	 * @功能描述 <pre>分页查询菜单与事件信息</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:48:12
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findSysResMenuEventRelPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * 根据业务唯一条件查询菜单与资源关系，如ID、菜单编号+事件编号
	 * @方法名称 getSysResMenuEventRelMap
	 * @功能描述 <pre>根据业务唯一条件查询菜单与资源关系，如ID、菜单编号+事件编号</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:48:20
	 * @param params
	 * @return
	 */
	public Map<String, Object> getSysResMenuEventRelMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveSysResMenuEventRel
	 * @功能描述 <pre>保存菜单与事件关系</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:48:35
	 * @param params
	 * @return
	 */
	public Map<String, Object> saveSysResMenuEventRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateSysResMenuEventRel
	 * @功能描述 <pre>更新菜单与事件关系</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:49:11
	 * @param params
	 */
	public void updateSysResMenuEventRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteSysResMenuEventRel
	 * @功能描述 <pre>删除菜单与事件关系</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月7日 下午4:49:32
	 * @param params
	 */
	public void deleteSysResMenuEventRel(Map<String, Object> params);
}
