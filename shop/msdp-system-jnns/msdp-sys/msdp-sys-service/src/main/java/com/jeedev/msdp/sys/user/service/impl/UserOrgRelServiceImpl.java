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
import com.jeedev.msdp.sys.org.api.OrgApiConstants;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.sys.user.dao.ISysUserOrgRelDao;
import com.jeedev.msdp.sys.user.entity.SysUserOrgRelEntity;
import com.jeedev.msdp.sys.user.service.IUserOrgRelService;

/**
 * @类名称 SysUserServiceImpl.java
 * @类描述 <pre>用户与机构关系管理</pre>
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
@Service("userOrgRelService")
public class UserOrgRelServiceImpl implements IUserOrgRelService {

	@Autowired
	private ISysUserOrgRelDao userOrgRelDao;
	/**
	 * 
	 * @方法名称 findUserOrgRelPage
	 * @功能描述 <pre>分页获取用户与机构的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findUserOrgRelPage(Map<String, Object> params, PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
		return new PageInfo<>(userOrgRelDao.findUserOrgRelList(params));
	}
	
	/**
	 * 
	 * @方法名称 findUserOrgDeptRelList
	 * @功能描述 <pre>分页获取用户与机构和部门的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findUserOrgDeptRelPage(Map<String, Object> params, PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
		return new PageInfo<>(userOrgRelDao.findUserOrgDeptRelList(params));
	}
	
	/**
	 * 
	 * @方法名称 getUserMap
	 * @功能描述 <pre>根据条件获取单个用户与机构数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getUserOrgRelMap(Map<String, Object> params) {
		String userNum = MapUtils.getString(params, UserConstants.USER_NUM); 
		String id = MapUtils.getString(params, UserConstants.ID);
		if (!StringUtil.isEmpty(id)) {
			params.remove(UserConstants.USER_NUM);
		}
		if (!StringUtil.isEmpty(userNum)) {
			params.put(UserConstants.DEL_IND, "0");
		}
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> UserPage = this.findUserOrgRelPage(params, null);
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
	 * @功能描述 <pre>保存用户与机构</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveUserOrgRel(Map<String, Object> params) {
		//数据校验
		params=this.validateUserOrg(params);
		SysUserOrgRelEntity entity = userOrgRelDao.save(new SysUserOrgRelEntity().coverToEntity(params, null));
		params.put(UserConstants.ID, entity.getId());
		return params;
	}
	/**
	 * 
	 * @方法名称 updateUser
	 * @功能描述 <pre>更新用户与机构数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:05:38
	 * @param params
	 */
	@Override
	public void updateUserOrgRel(Map<String, Object> params) {
		
		//update要先根据ID获取BO对象，然后在拷贝map里面的值  
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysUserOrgRelEntity findOne = userOrgRelDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		//数据校验
		params=this.validateUserOrg(params);
		SysUserOrgRelEntity entity = findOne.coverToEntity(params, findOne);
		userOrgRelDao.update(entity);
	}
	
	/**
	 * @方法名称 validateUserRole
	 * @功能描述 <pre>用户机构数据校验</pre>
	 * @作者    yuyq
	 * @创建时间 2017年11月1日 下午3:22:04
	 * @param params
	 * @return
	 */
	private Map<String, Object> validateUserOrg(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}

		Object userNum = params.get(UserConstants.USER_NUM);
		if (userNum == null) {
			throw ExceptionFactory.getBizException("sys-usr-00007");
		}
		Object orgCode = params.get(OrgApiConstants.ORG_CODE);
		if (orgCode == null) {
			throw ExceptionFactory.getBizException("sys-org-00002");
		}	
		//获取所有用户机构数据
		Map<String, Object> condMap = new HashMap<String, Object>();
		condMap.put("userNum", userNum);
		condMap.put("statusCd", DictUtil.getDictValue("StCd", "StCd_1"));
		PageInfo<Map<String, Object>> orgRel = findUserOrgRelPage(condMap, null);
		//id
		Integer id = MapUtils.getInteger(params, "id");
		Map<String, Object> defaultOrgRel = new HashMap<String, Object>();
		//获取默认机构
		if(orgRel.getSize()>0){
			for(Map<String, Object> map:orgRel.getList()){
				if(DictUtil.INDICATOR_YES().equals(map.get("defaultInd"))){
					defaultOrgRel=map;
				}
				//如果id为空，则要根据用户编码和机构编码判断是否重复插入，修改也要判断是否重复
				if ((id == null || !id.equals( MapUtils.getInteger(map,UserConstants.ID)))
						&& orgCode.equals(map.get(OrgApiConstants.ORG_CODE))) {
					throw ExceptionFactory.getBizException("sys-usr-00014");
				}
			}
		}
		if (defaultOrgRel.isEmpty()) {// 数据库无默认设置，一般为新增
			if (!DictUtil.INDICATOR_YES().equals(params.get("defaultInd"))) {
				// 至少选择一个默认机构
				throw ExceptionFactory.getBizException("sys-usr-00010");
			}
		} else {// 已存在默认设置
				// 本次操作设置为默认
			if (DictUtil.INDICATOR_YES().equals(params.get("defaultInd"))) {
				for(Map<String, Object> map:orgRel.getList()){
					//把原来的默认标识去掉
					Integer defualtOndeId = MapUtils.getInteger(map, "id");
					if(id==null||id.intValue()!=defualtOndeId.intValue()){
						SysUserOrgRelEntity defaultOne = userOrgRelDao.findOne(defualtOndeId);
						defaultOne.setDefaultInd(DictUtil.INDICATOR_NO());
						userOrgRelDao.update(defaultOne);
					}
				}
			} else if (defaultOrgRel.get(UserConstants.ID).equals(params.get(UserConstants.ID))) {
				// 至少选择一个默认机构
				throw ExceptionFactory.getBizException("sys-usr-00010");
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
	public void deleteUserOrgRel(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysUserOrgRelEntity entity = userOrgRelDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		if(entity.getDefaultInd().equals(DictUtil.INDICATOR_YES())){
			Map<String,Object> cond = new HashMap<String,Object>();
			cond.put("delInd", DictUtil.INDICATOR_NO());
			cond.put("statusCd", DictUtil.getDictValue("StCd", "StCd_1"));
			PageInfo<Map<String, Object>> defaultOrgRel = findUserOrgRelPage(cond, null);
			if(defaultOrgRel.getSize()>0){
				throw  ExceptionFactory.getBizException("sys-usr-00010");
			}else{
				//没有其他部门了，不需要检验
			}
		}
		
		
		entity.setDelInd("1"); //TODO 逻辑删除标识
		
		userOrgRelDao.update(entity);
		
	}
	@Override
	public PageInfo<Map<String, Object>> findUserByOrgRoleRel(Map<String, Object> params, PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		
		return new  PageInfo<Map<String, Object>>(userOrgRelDao.findUserByOrgRoleRel(params));
	}
}