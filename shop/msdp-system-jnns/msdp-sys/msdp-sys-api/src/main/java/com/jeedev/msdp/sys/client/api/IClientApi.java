package com.jeedev.msdp.sys.client.api;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

/**
 * @类名称 ClientApi.java
 * @类描述 <pre>授权客户端对外API</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月22日 上午11:30:54
 * @版本 1.00
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月22日             
 *     ----------------------------------------------
 * </pre>
 */
public interface IClientApi {
	/**
	 * @方法名称 findByClntendId
	 * @功能描述 <pre>通过客户端编号获取客户端信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月22日 上午11:32:51
	 * @param clntendId 客户端编号
	 * @return 客户端信息
	 * id 表主键
	 * clntendId 客户端编号
	 * clntendNm 客户端名称
	 * clntendSecret 客户端安全码
	 * clntendDomain 客户端域名
	 * clntendSt 客户端状态(1:启用，2:禁用)
	 */
	public Map<String,Object> findByClntendId(Map<String, Object> params) ;
	/**
	 * @方法名称 findEnabledList
	 * @功能描述 <pre>获取启用的客户端列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月22日 上午11:32:25
	 * @return 客户端信息</br>
	 * clntendId：客户端编号
	 * clntendNm：客户端名称
	 */
	public List<Map<String,Object>> findEnabledList(); 
	
	/**
	 * 
	 * @方法名称 findClientPage
	 * @功能描述 <pre>查询客户端列表</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月15日 上午11:32:27
	 * @param params
	 * @param pageParam
	 * +{list}clntendIds 客户端编号集合
	 * +clntendId 客户端编号
	 * +clntendNm 客户端名称
	 * +clntendDomain 客户端域名
	 * +clntendSt 客户端状态(1:启用，2:禁用)
	 * @return
	 * id 表主键
	 * clntendId 客户端编号
	 * clntendNm 客户端名称
	 * clntendSecret 客户端安全码
	 * clntendDomain 客户端域名
	 * clntendSt 客户端状态(1:启用，2:禁用)
	 */
	public PageInfo<Map<String, Object>> findClientPage(Map<String, Object> params,PageParam pageParam);
}
