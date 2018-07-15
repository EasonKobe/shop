package com.jeedev.msdp.sys.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.github.pagehelper.StringUtil;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.sys.user.dao.ISysUserGroupRelDao;
import com.jeedev.msdp.sys.user.entity.SysUserGroupRelEntity;
import com.jeedev.msdp.sys.user.service.IUserGroupRelService;

/**
 * 
 * @类名称 SysUserServiceImpl.java
 * @类描述 <pre>用户与分组关系管理</pre>
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
@Service("userGroupRelService")
public class UserGroupRelServiceImpl implements IUserGroupRelService {

	@Autowired
	private ISysUserGroupRelDao userGroupRelDao;
	/**
	 * 
	 * @方法名称 findUserPage
	 * @功能描述 <pre>分页获取用户与组别关系的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findUserGroupRelPage(Map<String, Object> params, PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
		return new PageInfo<>(userGroupRelDao.findUserGroupRelList(params));
	}
	/**
	 * 
	 * @方法名称 getUserMap
	 * @功能描述 <pre>根据条件获取单个用户与组别关系数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getUserGroupRelMap(Map<String, Object> params) {
		String userNum = MapUtils.getString(params, UserConstants.USER_NUM); 
		String id = MapUtils.getString(params, UserConstants.ID);
		if (!StringUtil.isEmpty(id)) {
			params.remove(UserConstants.USER_NUM);
		}
		if (!StringUtil.isEmpty(userNum)) {
			params.put(UserConstants.DEL_IND, "0");
		}
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> UserPage = this.findUserGroupRelPage(params, null);
		//判断是否存在数据
		long total = UserPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			List UserList = UserPage.getList();
			if(null!= UserList && UserList.size()>0 ){
				return (Map<String, Object>) UserList.get(0);
			}
		}
		return null;
	}
	/**
	 * 
	 * @方法名称 saveUser
	 * @功能描述 <pre>保存用户与组别关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveUserGroupRel(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		SysUserGroupRelEntity entity = userGroupRelDao.save(new SysUserGroupRelEntity().coverToEntity(params, null));
		params.put("id", entity.getId());
		return params;
	}
	/**
	 * 
	 * @方法名称 updateUser
	 * @功能描述 <pre>更新用户与组别关系数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:05:38
	 * @param params
	 */
	@Override
	public void updateUserGroupRel(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值  
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysUserGroupRelEntity findOne = userGroupRelDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysUserGroupRelEntity entity = findOne.coverToEntity(params, findOne);
		userGroupRelDao.update(entity);
	}
	/**
	 * 
	 * @方法名称 deleteUser
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:07:35
	 * @param params
	 */
	@Override
	public void deleteUserGroupRel(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysUserGroupRelEntity entity = userGroupRelDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); //TODO 逻辑删除标识
		userGroupRelDao.update(entity);
		
	}
}