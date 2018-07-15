package com.jeedev.msdp.sys.dataauth.api;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
/**
 * 
 * @类名称 DataAuthApi.java
 * @类描述 <pre>数据权限对外接口</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月30日 上午10:36:02
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	2017年9月30日             
 *     ----------------------------------------------
 * </pre>
 */
public interface DataAuthApi {
	/**
	 * @方法名称 findDataAuthPage
	 * @功能描述 <pre>分页查询数据权限信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月17日 上午10:37:20
	 * @param params
	 * queryType:查询方式（0.完全匹配，1通配）</br>
	 * id：主键编号</br>
	 * DataAuthCode:数据权限编号</br>
	 * modelCode：数据权限类型编码</br>
	 * userNum 用户编号</br>
	 * roleCode 角色编号</br>
	 * deptCode 部门编号</br>
	 * orgCode 机构编号</br>
	 * bizScene 业务场景</br>
	 * eventCode 事件编号</br>
	 * clntendId 客户端编号</br>
	 * @return 分页列表
	 *  * id：主键编号</br>
	 * dataAuthCode:数据权限编号</br>
	 * modelCode：数据权限类型编码</br>
	 * userNum 用户编号</br>
	 * roleCode 角色编号</br>
	 * deptCode 部门编号</br>
	 * orgCode 机构编号</br>
	 * bizScene 业务场景</br>
	 * eventCode 事件编号</br>
	 * clntendId 客户端编号</br>
	 * remark：备注</br>
	 */
	public PageInfo<Map<String, Object>> findDataAuthPage(Map<String, Object> params,PageParam pageParam);
	/**
	 * @方法名称 getDataAuthMap
	 * @功能描述 <pre>获取数据权限详细信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月17日 上午10:37:45
	 * @param params 参数
	 * id：主键编号</br>
	 * dataAuthCode:数据权限编号</br>
	 * @return map
	 * id：主键编号</br>
	 * DataAuthissionCode:数据权限编号</br>
	 * modelCode：数据权限类型编码</br>
	 * remark：备注</br>
	 * userNum 用户编号</br>
	 * roleCode 角色编号</br>
	 * deptCode 部门编号</br>
	 * orgCode 机构编号</br>
	 * bizScene 业务场景</br>
	 * eventCode 事件编号</br>
	 * clntendId 客户端编号</br>
	 */
	public Map<String, Object> getDataAuthMap(Map<String, Object> params);
	/**
	 * @方法名称 saveDataAuthTrans
	 * @功能描述 <pre>新增&保存数据权限信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月17日 上午10:38:17
	 * @param params
	 * id：主键编号</br>
	 * DataAuthCode:数据权限编号</br>
	 * modelCode：数据权限类型编码</br>
	 * userNum 用户编号</br>
	 * roleCode 角色编号</br>
	 * deptCode 部门编号</br>
	 * orgCode 机构编号</br>
	 * bizScene 业务场景</br>
	 * eventCode 事件编号</br>
	 * clntendId 客户端编号</br>
	 * remark：备注</br>
	 * @return
	 * id：主键编号</br>
	 * DataAuthCode:数据权限编号</br>
	 * modelCode：数据权限类型编码</br>
	 * userNum 用户编号</br>
	 * roleCode 角色编号</br>
	 * deptCode 部门编号</br>
	 * orgCode 机构编号</br>
	 * bizScene 业务场景</br>
	 * eventCode 事件编号</br>
	 * clntendId 客户端编号</br>
	 * remark：备注</br>
	 */
	public Map<String, Object> saveDataAuthTrans(Map<String, Object> params);
	/**
	 * @方法名称 saveBatchDataAuthTrans
	 * @功能描述 <pre>批量新增&保存数据权限信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月17日 上午10:38:17
	 * @param params
	 * +{list} list
	 * ++id：主键编号</br>
	 * ++DataAuthCode:数据权限编号</br>
	 * ++modelCode：数据权限类型编码</br>
	 * ++userNum 用户编号</br>
	 * ++roleCode 角色编号</br>
	 * ++deptCode 部门编号</br>
	 * ++orgCode 机构编号</br>
	 * ++bizScene 业务场景</br>
	 * ++eventCode 事件编号</br>
	 * ++clntendId 客户端编号</br>
	 * ++remark：备注</br>
	 * @return
	 * +{list} list
	 * ++id：主键编号</br>
	 * ++DataAuthCode:数据权限编号</br>
	 * ++modelCode：数据权限类型编码</br>
	 * ++userNum 用户编号</br>
	 * ++roleCode 角色编号</br>
	 * ++deptCode 部门编号</br>
	 * ++orgCode 机构编号</br>
	 * ++bizScene 业务场景</br>
	 * ++eventCode 事件编号</br>
	 * ++clntendId 客户端编号</br>
	 * ++remark：备注</br>
	 */
	public Map<String, Object> saveBatchDataAuthTrans(Map<String, Object> params);
	/**
	 * @方法名称 deleteDataAuthTrans
	 * @功能描述 <pre>删除数据权限信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月17日 上午10:38:43
	 * @param params
	 * id：主键编号</br>
	 * @return
	 */
	public void deleteDataAuthTrans(Map<String, Object> params);
	
	/**
	 * @方法名称 findDataAuthPage
	 * @功能描述 <pre>分页查询数据权限信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月26日 上午10:37:20
	 * @param params
	 * modelId:数据权限编号</br>
	 * modelCode：数据权限类型编码</br>
	 * @return 分页列表
	 *  * id：主键编号</br>
	 * modelId:数据权限编号</br>
	 * modelCode：数据权限类型编码</br>
	 * remark：备注</br>
	 */
	public PageInfo<Map<String, Object>> findDataAuthModelPage(Map<String, Object> params,PageParam pageParam);
}
