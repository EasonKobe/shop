package com.tansun.tb.entp.api;

import java.util.List;
import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

public interface EnterpriseApi {
	

	PageInfo<Map<String, Object>> findEnterprisePage(Map<String, Object> params, PageParam pageParam);

	/**
	 * @方法名称 findParentEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       查询企业和上级企业列表
	 *       </pre>
	 * 
	 * @作者 赵健 zhaojianxm@tansun.com.cn
	 * @创建时间 2018年01月29日 AM 09:59
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findParentEnterpriseList(Map<String, Object> params);

	/**
	 * @方法名称 findChildEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       查询企业和下级企业列表
	 *       </pre>
	 * 
	 * @作者 赵健 zhaojianxm@tansun.com.cn
	 * @创建时间 2018年01月29日 AM 09:59
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findChildEnterpriseList(Map<String, Object> params);
	
	/**
	 * @方法名称 findOnlyParentEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       查询企业和上级企业列表
	 *       </pre>
	 * 
	 * @创建时间 2018年01月29日 AM 09:59
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findOnlyParentEnterpriseList(Map<String, Object> params);

	/**
	 * @方法名称 findOnlyChildEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       查询企业和下级企业列表
	 *       </pre>
	 * 
	 * @创建时间 2018年01月29日 AM 09:59
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findOnlyChildEnterpriseList(Map<String, Object> params);

	Map<String, Object> getEnterpriseMap(Map<String, Object> params);

	Map<String, Object> saveEnterpriseTrans(Map<String, Object> params);

	void deleteEnterpriseTrans(Map<String, Object> params);

	/**
	 * @方法名称 findEntpCodeByLoginName
	 * @功能描述
	 * 
	 * 		<pre>
	 *       根据用户登录名查找客户号
	 *       </pre>
	 * 
	 * @作者 yuminjun
	 * @创建时间 2018年2月4日 下午6:58:04
	 * @param params
	 * @return
	 */
	Map<String, Object> findEntpCodeByLoginName(Map<String, Object> params);

	/**
	 * @方法名称 findHostEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       根据客户编号或租户id查询主办机构
	 *       </pre>
	 * 
	 * @作者 linjunfa
	 * @创建时间 2018 4月4日 下午6:58:04
	 * @param params
	 * @return
	 */
	Map<String, Object> findHostEnterpriseList(Map<String, Object> params);

	/**
	 * @方法名称 findHostEnterpriseList
	 * @功能描述
	 * 
	 * 		<pre>
	 *       根据客户编号或租户id集团客户列表
	 *       </pre>
	 * 
	 * @作者 linjunfa
	 * @创建时间 2018 4月4日 下午6:58:04
	 * @param params
	 * @return
	 */
	List<Map<String, Object>> findGroupEnterpriseList(Map<String, Object> entpCode, Map<String, Object> params,
			PageParam pageParam);
	
}
