package com.jeedev.msdp.sys.org.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 ISysOrgDeptRelService.java
 * @类描述 <pre>机构与部门管理</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月18日 上午10:02:59
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月18日             
 *     ----------------------------------------------
 * </pre>
 */
public interface ISysOrgDeptRelService {
	/**
	 * 
	 * @方法名称 findOrgDeptRelPage
	 * @功能描述 <pre>分页查询机构与部门关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午7:13:52
	 * @param params 查询条件
	 * orgCode 机构代码
	 * deptCode 部门代码
	 * statusCd 状态
	 * remark 备注
	 * @param pageNum 页数
	 * @param pageSize 条数
	 * @return 分页信息
	 */
	public PageInfo<Map<String, Object>> findOrgDeptRelPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * 
	 * @方法名称 findOrgDeptRel
	 * @功能描述 <pre>查询选中机构与部门关系（id不为空则根据id做查询）</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:44:15
	 * @param params 参数
	 * deptCode 部门编码
	 * orgCode 机构编码
	 * id 主键
	 * @return
	 */
	public Map<String, Object> getOrgDeptRelMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveOrgDeptRel
	 * @功能描述 <pre>保存机构与部门关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午4:58:39
	 * @param params 保存参数
	 * orgCode 机构代码
	 * deptCode 部门代码
	 * statusCd 状态
	 * remark 备注
	 * @return 机构与部门信息map
	 */
	public Map<String, Object> saveOrgDeptRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateOrgDeptRel
	 * @功能描述 <pre>更新机构与部门关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:00:33
	 * @param params 更新参数
	 * id 主键
	 * orgCode 机构代码
	 * deptCode 部门代码
	 * statusCd 状态
	 * remark 备注
	 */
	public void updateOrgDeptRel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 deleteOrgDeptRel
	 * @功能描述 <pre>删除机构与部门关系信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月15日 下午5:11:58
	 * @param params 删除参数
	 * id 主键
	 */
	public void deleteOrgDeptRel(Map<String, Object> params);
}
