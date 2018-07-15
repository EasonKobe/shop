package com.jeedev.msdp.sys.user.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeedev.msdp.utlis.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.sys.user.dao.ISysUserDao;
import com.jeedev.msdp.sys.user.entity.SysUserEntity;
import com.jeedev.msdp.sys.user.service.IUserService;

/**
 *
 * @类名称 SysUserServiceImpl.java
 * @类描述 <pre>用户管理</pre>
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
@Service("sysUserService")
public class UserServiceImpl implements IUserService {

	@Autowired
	private ISysUserDao sysUserDao;
	/**
	 * @方法名称 findUserPage
	 * @功能描述 <pre>分页获取用户的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findUserPage(Map<String, Object> params) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put(UserConstants.DEL_IND, "0");
		return new PageInfo<>(sysUserDao.findUserList(params));
	}
	/**
	 * @方法名称 countSysUserByLoginName
	 * @功能描述 <pre>根据登录名统计用户数量，作唯一性判断使用</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月21日 下午8:37:39
	 * @param loginName
	 * @return
	 */
	public Integer countSysUserByLoginName(String loginName){
		return sysUserDao.countSysUserByLoginName(loginName);
	}
	/**
	 * @方法名称 getUserMap
	 * @功能描述 <pre>根据条件获取单个用户数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> getUserMap(Map<String, Object> params) {
		params.put("delInd", "0");
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> UserPage = this.findUserPage(params);
		//判断是否存在数据
		long total = UserPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			List<Map<String, Object>> UserList = UserPage.getList();
			if(null!= UserList && UserList.size()>0 ){
				return (Map<String, Object>) UserList.get(0);
			}
		}
		return null;
	}
	/**
	 * @方法名称 saveUser
	 * @功能描述 <pre>保存用户</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveUser(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		/** add by zhaoj 20180320 如果loginName重复，抛出异常*/
		duplicateUser(params);

		SysUserEntity entity=new SysUserEntity().coverToEntity(params, null);
		SysUserEntity userEntity = sysUserDao.save(entity);
		params.put("id", userEntity.getId());
		return params;
	}
	/**
	 * @方法名称 updateUser
	 * @功能描述 <pre>更新用户数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:05:38
	 * @param params
	 */
	@Override
	public void updateUser(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		if (!params.containsKey(UserConstants.ID)) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysUserEntity findEntity = sysUserDao.findOne(id);
		if (findEntity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}

		/** add by zhaoj 20180320 如果loginName重复，抛出异常*/
		duplicateUser(params);

		findEntity.coverToEntity(params, findEntity);
		sysUserDao.update(findEntity);
	}

	private void duplicateUser(Map<String, Object> params) {
		String loginName = MapUtils.getString(params, UserConstants.LOGIN_NAME);
		Integer id = MapUtils.getInteger(params, UserConstants.ID);

		if (StringUtil.isBlank(loginName)){
			return;
		}

		PageInfo<Map<String, Object>> userPage = findUserPage(params);
		List<Map<String, Object>> list = userPage.getList();

		if (CollectionUtils.isEmpty(list)) {
			return;
		}
		//如果是新增，而且已存在该用户名，则失败
		if (id == null){
			throw ExceptionFactory.getBizException("sys-usr-00003", loginName);
		}
		//如果是修改，且ID不同，则失败
		for (Map<String, Object> userMap : list) {
			Integer id2 = MapUtils.getInteger(userMap, UserConstants.ID);
			if (!id2.equals(id)) {
				throw ExceptionFactory.getBizException("sys-usr-00003", loginName);
			}
		}
	}

	/**
	 * @方法名称 deleteUser
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:07:35
	 * @param params
	 */
	@Override
	public void deleteUser(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysUserEntity entity = sysUserDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); //TODO 逻辑删除标识
		sysUserDao.update(entity);

	}
}