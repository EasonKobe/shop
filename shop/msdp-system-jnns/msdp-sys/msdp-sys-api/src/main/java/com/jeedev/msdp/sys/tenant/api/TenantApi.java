package com.jeedev.msdp.sys.tenant.api;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

public interface TenantApi {
	
	/**
	 * @方法名称 findTenantPage
	 * @功能描述 <pre>查询租户分页</pre>
	 * @param paramMap 查询参数Map</br>
	 * +tenantName：租户名称</br>
	 * +orgCode：统一社会信用证</br>
	 * +tenantId：租户Id</br>
	 * +{list}tenantIds：租户Id列表</br>
	 * @param pageSize 分页条数
	 * @param pageNum 分页页数 
	 * @return 租户信息</br>
	 * id:主键编号</br>
	 * tenantName：租户名称</br>
	 * tenantNameLike：租户名称模糊查询</br>
	 * tenantId：租户Id</br>
	 * orgCode：统一社会信用代码</br>
	 * statusCd：租户状态。0未生效;1生效
	 */
	public PageInfo<Map<String,Object>> findTenantPage(Map<String,Object> paramMap,PageParam pageParam) ;
	/**
	 * @方法名称 getTenantMap
	 * @功能描述 <pre>获取租户详细信息</pre>
	 * @param params 查询参数Map<br>
	 * id：租户主键编号<br>
	 * tenantId：租户编码<br>
	 * loginUriSuffix:登录后缀<br>
	 * @return 租户信息<br>
	 * id：租户主键编号<br>
	 * tenantId：租户编号<br>
	 * tenantName：租户名称<br>
	 * orgCode：统一社会信用证</br>
	 * managerName：管理人员</br>
	 * loginName：登录名</br>
	 * mobile：手机号<br>
	 * telephone：固定电话<br>
	 * statusCd：租户状态。0未生效;1生效
	 */
	public Map<String, Object> getTenantMap(Map<String,Object> params);
	/**
	 * @方法名称 saveTenantMap
	 * @功能描述 <pre>新增或修改租户信息</pre>
	 * @param params
	 * id：租户主键编号<br>
	 * tenantId：租户编码<br>
	 * tenantName：租户名称<br>
	 * orgCode：统一社会信用证</br>
	 * managerName：管理人员</br>
	 * loginName：登录名</br>
	 * mobile：手机号<br>
	 * telephone：固定电话<br>
	 * statusCd：租户状态。0未生效;1生效
	 * password：密码</br>
	 * salt：盐</br>
	 * @return
	 * tenantId：租户编号
	 */
	public Map<String, Object> saveTenantMap(Map<String,Object> params);
	/**
	 * @方法名称 deleteTenant
	 * @功能描述 <pre>删除租户实例</pre>
	 * @param params
	 * @return
	 */
	public void deleteTenant(Map<String,Object> params);
	
	
	/**
	 * 保存租户的角色信息 
	 * @方法名称 saveTenantRole
	 * @功能描述 <pre>1.使用租户ID作为角色ID新建角色 （角色存在则不保存）
	 * 				2.更新角色菜单（删除再新增）</pre>
	 * @作者    chenjc
	 * @创建时间 2017年10月16日 下午7:40:04
	 * @param params
	 * +tenantId 租户ID
	 * +{list}permissionCodes 资源编号集合
	 * @return
	 */
	public void saveTenantRole(Map<String,Object> params);
}
