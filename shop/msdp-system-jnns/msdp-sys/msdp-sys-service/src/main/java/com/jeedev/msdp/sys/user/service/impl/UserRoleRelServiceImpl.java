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
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.role.api.RoleConstants;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.sys.user.dao.ISysUserRoleRelDao;
import com.jeedev.msdp.sys.user.entity.SysUserRoleRelEntity;
import com.jeedev.msdp.sys.user.service.IUserRoleRelService;

/**
 * 
 * @类名称 SysUserServiceImpl.java
 * @类描述 <pre>用户与角色关系关系管理</pre>
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
@Service("userRoleRelService")
public class UserRoleRelServiceImpl implements IUserRoleRelService {

	@Autowired
	private ISysUserRoleRelDao userRoleRelDao;
	/**
	 * 
	 * @方法名称 findUserPage
	 * @功能描述 <pre>分页获取用户与角色关系的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findUserRoleRelPage(Map<String, Object> params, PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
		return new PageInfo<>(userRoleRelDao.findUserRoleRelList(params));
	}
	/**
	 * 
	 * @方法名称 getUserMap
	 * @功能描述 <pre>根据用户角色id或用户编码，角色编码组合 获取用户与角色关系信息详情</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getUserRoleRelMap(Map<String, Object> params) {
		String id = MapUtils.getString(params, UserConstants.ID);
		String userNum = MapUtils.getString(params, UserConstants.USER_NUM);
		String roleCode = MapUtils.getString(params, RoleConstants.ROLE_CODE);
		if (StringUtil.isEmpty(id)&&(StringUtil.isEmpty(userNum)||StringUtil.isEmpty(roleCode))) {
				throw ExceptionFactory.getBizException("sys-usr-00008");
		}
		params.put("delInd", "0");
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> UserPage = this.findUserRoleRelPage(params, null);
		//判断是否存在数据
		long total = UserPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			@SuppressWarnings("rawtypes")
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
	 * @功能描述 <pre>保存用户与角色关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveUserRoleRel(Map<String, Object> params) {
		//数据校验
		params=this.validateUserRole(params);
		SysUserRoleRelEntity entity = userRoleRelDao.save(new SysUserRoleRelEntity().coverToEntity(params, null));
		params.put("id", entity.getId());
		return params;
	}
	/**
	 * 
	 * @方法名称 updateUser
	 * @功能描述 <pre>更新用户与角色关系数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:05:38
	 * @param params
	 */
	@Override
	public void updateUserRoleRel(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值  
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysUserRoleRelEntity findOne = userRoleRelDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		//数据校验
		params=this.validateUserRole(params);
		SysUserRoleRelEntity entity = findOne.coverToEntity(params, findOne);
		userRoleRelDao.update(entity);
	}
	/**
	 * @方法名称 validateUserRole
	 * @功能描述 <pre>用户角色数据校验</pre>
	 * @作者    yuyq
	 * @创建时间 2017年11月1日 下午3:22:04
	 * @param params
	 * @return
	 */
	private Map<String, Object> validateUserRole(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}

		Object userNum = params.get(UserConstants.USER_NUM);
		if (userNum == null) {
			throw ExceptionFactory.getBizException("sys-usr-00007");
		}
		Object roleCode = params.get(RoleConstants.ROLE_CODE);
		if (roleCode == null) {
			throw ExceptionFactory.getBizException("sys-role-00002");
		}
		//获取所有用户角色数据
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("userNum", userNum);
		condMap.put("statusCd", DictUtil.getDictValue("StCd", "StCd_1"));
		PageInfo<Map<String, Object>> orgRel = findUserRoleRelPage(condMap, null);
		//id
		Integer id = MapUtils.getInteger(params, "id");
		Map<String, Object> defaultOrgRel = new HashMap<String, Object>();
		//获取默认角色
		if(orgRel.getSize()>0){
			for(Map<String, Object> map:orgRel.getList()){
				if(DictUtil.INDICATOR_YES().equals(map.get("defaultInd"))){
					defaultOrgRel=map;
				}
				//如果id为空，则要根据用户编码和角色编码判断是否重复插入，修改也要判断是否重复
				if ((id == null || !id.equals( MapUtils.getInteger(map,UserConstants.ID)))
						&& roleCode.equals(map.get(RoleConstants.ROLE_CODE))) {
					throw ExceptionFactory.getBizException("sys-usr-00013");
				}
			}
		}
		if (defaultOrgRel.isEmpty()) {// 数据库无默认设置，一般为新增
			if (!DictUtil.INDICATOR_YES().equals(params.get("defaultInd"))) {
				// 至少选择一个默认角色
				throw ExceptionFactory.getBizException("sys-usr-0009");
			}
		} else {// 已存在默认设置
				// 本次操作设置为默认
			if (DictUtil.INDICATOR_YES().equals(params.get("defaultInd"))) {
				for(Map<String, Object> map:orgRel.getList()){
					//把原来的默认标识去掉
					Integer defualtOndeId = MapUtils.getInteger(map, "id");
					if(id==null||id.intValue()!=defualtOndeId.intValue()){
						SysUserRoleRelEntity defaultOne = userRoleRelDao.findOne(defualtOndeId);
						defaultOne.setDefaultInd(DictUtil.INDICATOR_NO());
						userRoleRelDao.update(defaultOne);
					}
				}
			} else if (defaultOrgRel.get(UserConstants.ID).equals(params.get(UserConstants.ID))) {
				// 至少选择一个默认角色
				throw ExceptionFactory.getBizException("sys-usr-0009");
			}
		}
		return params;
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
	public void deleteUserRoleRel(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysUserRoleRelEntity entity = userRoleRelDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}

//		if(entity.getDefaultInd().equals(DictUtil.INDICATOR_YES())){
//			Map<String,Object> cond = new HashMap<String,Object>();
//			cond.put("delInd", DictUtil.INDICATOR_NO());
//			cond.put("statusCd", DictUtil.getDictValue("StCd", "StCd_1"));
//			cond.put("userNum", entity.getUserNum());
//			PageInfo<Map<String, Object>> defaultOrgRel = findUserRoleRelPage(cond, null);
//			if(defaultOrgRel.getSize()>0){
//				throw  ExceptionFactory.getBizException("sys-usr-00012");
//			}else{
//				//没有其他部门了，不需要检验
//			}
//		}
		entity.setDelInd("1"); //TODO 逻辑删除标识
		userRoleRelDao.update(entity);
		
	}
}