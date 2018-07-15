package com.jeedev.msdp.sys.role.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.github.pagehelper.StringUtil;
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.role.api.RoleConstants;
import com.jeedev.msdp.sys.role.dao.ISysRoleDao;
import com.jeedev.msdp.sys.role.entity.SysRoleEntity;
import com.jeedev.msdp.sys.role.service.ISysRoleService;

/**
 * 
 * @类名称 SysRoleServiceImpl.java
 * @类描述 <pre>角色管理</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月16日 下午2:06:09
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月16日             
 *     ----------------------------------------------
 * </pre>
 */
@Service("sysRoleService")
public class SysRoleServiceImpl implements ISysRoleService {

	@Autowired
	private ISysRoleDao sysRoleDao;
	/**
	 * 
	 * @方法名称 findRolePage
	 * @功能描述 <pre>分页获取角色的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findRolePage(Map<String, Object> params, PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
		List<Map<String,Object>> result=sysRoleDao.findRoleList(params);
		return new PageInfo<>(result);
	}
	/**
	 * 
	 * @方法名称 getRoleMap
	 * @功能描述 <pre>根据条件获取单个角色数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getRoleMap(Map<String, Object> params) {
		String roleCode = MapUtils.getString(params, RoleConstants.ROLE_CODE); 
		String id = MapUtils.getString(params, RoleConstants.Id);
		if (!StringUtil.isEmpty(id)) {
			params.remove(RoleConstants.ROLE_CODE);
		}
		if (!StringUtil.isEmpty(roleCode)) {
			params.remove(RoleConstants.Id);
		}
		params.put("delInd", DictUtil.INDICATOR_NO());
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> rolePage = this.findRolePage(params, null);
		//判断是否存在数据
		long total = rolePage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			List roleList = rolePage.getList();
			if(null!= roleList && roleList.size()>0 ){
				return (Map<String, Object>) roleList.get(0);
			}
		}
		return null;
	}
	/**
	 * 
	 * @方法名称 saveRole
	 * @功能描述 <pre>保存角色</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveRole(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		SysRoleEntity role=new SysRoleEntity().coverToEntity(params, null);
		SysRoleEntity roleEntity = sysRoleDao.save(role);
		Map<String, Object> result=new HashMap<>();
		result.put("id", roleEntity.getId());
		result.put(RoleConstants.ROLE_CODE, roleEntity.getRoleCode());
		result.put(RoleConstants.ROLE_NAME, roleEntity.getRoleName());
		result.put(RoleConstants.PARENT_ROLE_CODE, roleEntity.getParentRoleCode());
		result.put(RoleConstants.STATUS_CD, roleEntity.getStatusCd());
		return result;
	}
	/**
	 * 
	 * @方法名称 updateRole
	 * @功能描述 <pre>更新角色数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:05:38
	 * @param params
	 */
	@Override
	public void updateRole(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值  
		Integer id = MapUtils.getInteger(params, RoleConstants.Id);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysRoleEntity findOne = sysRoleDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysRoleEntity entity = findOne.coverToEntity(params, findOne);
		sysRoleDao.update(entity);
	}
	/**
	 * 
	 * @方法名称 deleteRole
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:07:35
	 * @param params
	 */
	@Override
	public void deleteRole(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, RoleConstants.Id);
		String roleCode = MapUtils.getString(params, RoleConstants.ROLE_CODE); 
		if (id == null&&StringUtils.isBlank(roleCode)) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
//		SysRoleEntity entity = sysRoleDao.findOne(id); 
		Map<String, String> conditMap=new HashMap<>();
		conditMap.put(RoleConstants.Id, id.toString());
		conditMap.put(RoleConstants.ROLE_CODE, roleCode);
		SysRoleEntity entity = sysRoleDao.findOneByCondition(conditMap);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); // 逻辑删除标识
		sysRoleDao.update(entity);
		
	}
}