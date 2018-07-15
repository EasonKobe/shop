package com.jeedev.msdp.sys.dict.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

public interface IDictService {
	/**
	 * @方法名称 findDictPage
	 * @功能描述 <pre>分页获取数据字典</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午5:47:17
	 * @param params
	 * @param pageParam
	 * @return
	 */
	PageInfo<Map<String, String>> findDictPage(Map<String, String> params,PageParam pageParam);
	/**
	 * @方法名称 getDictMap
	 * @功能描述 <pre>获取数据字典详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午5:47:39
	 * @param params
	 * @return
	 */
	Map<String, String> getDictMap(Map<String, String> params);
	/**
	 * @方法名称 saveDict
	 * @功能描述 <pre>保存数据字典</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午5:47:59
	 * @param params
	 * @throws Exception
	 */
	void saveDict(Map<String, String> params) throws Exception;
	/**
	 * @方法名称 deleteDict
	 * @功能描述 <pre>根据ID删除数据字典</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午5:48:13
	 * @param params
	 * @throws Exception
	 */
	void deleteDict(Map<String, String> params) throws Exception;
	/**
	 * @方法名称 updateDict
	 * @功能描述 <pre>更新数据字典</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午5:48:28
	 * @param params
	 * @throws Exception
	 */
	void updateDict(Map<String, String> params) throws Exception;
	
}
