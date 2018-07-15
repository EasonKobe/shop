package com.jeedev.msdp.sys.perm.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.github.pagehelper.StringUtil;
import com.jeedev.msdp.base.encode.service.IEncodeService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.perm.api.PermConstants;
import com.jeedev.msdp.sys.perm.dao.ISysPermissionDao;
import com.jeedev.msdp.sys.perm.entity.SysPermissionEntity;
import com.jeedev.msdp.sys.perm.service.IPermService;
@Service("permService")
public class PermServiceImpl implements IPermService {

	@Autowired
	private ISysPermissionDao sysPermissionDao;
	/**
	 * 编码生成服务
	 */
	@Autowired
	private IEncodeService encodeService;
	/**
	 * 
	 * @方法名称 findPermPage
	 * @功能描述 <pre>分页获取权限的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findPermPage(Map<String, Object> params, PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
		return new PageInfo<>(sysPermissionDao.findPermList(params));
	}
	/**
	 * 
	 * @方法名称 getPermMap
	 * @功能描述 <pre>根据条件获取单个权限数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> getPermMap(Map<String, Object> params) {
		String permissionCode = MapUtils.getString(params, "permissionCode"); 
		String id = MapUtils.getString(params, "id");
		if (!StringUtil.isEmpty(id)) {
			params.remove("permissionCode");
		}
		if (!StringUtil.isEmpty(permissionCode)) {
			params.put("delInd", "0");
		}
		if (StringUtil.isEmpty(id) && StringUtil.isEmpty(permissionCode) ) {
			throw ExceptionFactory.getBizException("sys-00004", "id", "permissionCode");
		}
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> PermPage = this.findPermPage(params, null);
		//判断是否存在数据
		long total = PermPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			List PermList = PermPage.getList();
			if(null!= PermList && PermList.size()>0 ){
				return (Map<String, Object>) PermList.get(0);
			}
		}
		return null;
	}
	/**
	 * 
	 * @方法名称 savePerm
	 * @功能描述 <pre>保存权限</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> savePerm(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		String permCode =  (String) params.get(PermConstants.PERMISSION_CODE);
		if(com.jeedev.msdp.utlis.StringUtil.isBlank(permCode)) {
			try {
				permCode = encodeService.buildEncode("10004", "systemAuto");
			} catch (Exception e) {
				throw ExceptionFactory.getBizException("sys-00006");
			}
			params.put(PermConstants.PERMISSION_CODE, permCode);
		}
		
		SysPermissionEntity permEntity=new SysPermissionEntity().coverToEntity(params, null);
		SysPermissionEntity perm = sysPermissionDao.save(permEntity);
		params.put("id", perm.getId());
		return params;
	}
	/**
	 * 
	 * @方法名称 updatePerm
	 * @功能描述 <pre>更新权限数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:05:38
	 * @param params
	 */
	@Override
	public void updatePerm(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值  
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysPermissionEntity findOne = sysPermissionDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysPermissionEntity entity = findOne.coverToEntity(params, findOne);
		sysPermissionDao.update(entity);
	}
	/**
	 * 
	 * @方法名称 deletePerm
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:07:35
	 * @param params
	 */
	@Override
	public void deletePerm(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysPermissionEntity entity = sysPermissionDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); //TODO 逻辑删除标识
		sysPermissionDao.update(entity);
	}

}
