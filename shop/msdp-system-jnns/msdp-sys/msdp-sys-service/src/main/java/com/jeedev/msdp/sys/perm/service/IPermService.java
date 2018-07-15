package com.jeedev.msdp.sys.perm.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

public interface IPermService {
	/**
	 * 
	 * @方法名称 findPermPage
	 * @功能描述 <pre>分页查询权限信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params
	 * @param pageParam
	 * @return
	 */
	public PageInfo<Map<String, Object>> findPermPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * 
	 * @方法名称 findPerm
	 * @功能描述 <pre>查询选中权限</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params
	 * @return
	 */
	public Map<String, Object> getPermMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 savePerm
	 * @功能描述 <pre>保存权限信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params
	 * @return
	 */
	public Map<String, Object> savePerm(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updatePerm
	 * @功能描述 <pre>更新权限</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params
	 * @return
	 */
	public void updatePerm(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deletePerm
	 * @功能描述 <pre>删除权限信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params
	 */
	public void deletePerm(Map<String, Object> params);

}
