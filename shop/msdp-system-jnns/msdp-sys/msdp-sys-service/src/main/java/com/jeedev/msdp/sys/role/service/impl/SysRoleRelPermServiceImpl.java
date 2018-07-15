package com.jeedev.msdp.sys.role.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.role.api.RoleConstants;
import com.jeedev.msdp.sys.role.dao.ISysRolePermissionRelDao;
import com.jeedev.msdp.sys.role.entity.SysRolePermissionRelEntity;
import com.jeedev.msdp.sys.role.service.ISysRoleRelPermService;

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
@Service("sysRoleRelPermService")
public class SysRoleRelPermServiceImpl implements ISysRoleRelPermService {
	
	@Autowired
	private ISysRolePermissionRelDao sysRolePermissionRelDao;
	
	/**
	 * @方法名称 findRolePermissionRelPage
	 * @功能描述 <pre>获取角色权限菜单列表</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午4:27:39
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findRolePermissionRelPage(Map<String, Object> params, PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
        return new PageInfo<>(sysRolePermissionRelDao.findRolePermList(params)); 
	}
	/**
	 * @方法名称 deleteRolePermissionRel
	 * @功能描述 <pre>删除角色相关的菜单权限-物理删除</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午4:28:57
	 * @param params roleCode
	 */
	@Override
	public void deleteRolePermissionRel(Map<String, Object> params) {
		Integer roleCode = MapUtils.getInteger(params, RoleConstants.ROLE_CODE);
		if (roleCode == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		sysRolePermissionRelDao.deleteByRoleCode(params);
		
	}
	/**
	 * @方法名称 saveRolePermissionRel
	 * @功能描述 <pre>角色资源权限设置</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午4:25:52
	 * @param params
	 * @return
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> saveRolePermissionRel(Map<String, Object> params) {
		String roleCode=MapUtils.getString(params, RoleConstants.ROLE_CODE); 
		List<String> plist=(List<String>) MapUtils.getObject(params, "permissionCodes");
		sysRolePermissionRelDao.deleteByRoleCode(params);
		if(plist!=null){
			for(String s:plist){
				Map<String, Object> saveMap=new HashMap<>();
				saveMap.put(RoleConstants.ROLE_CODE, roleCode);
				saveMap.put(RoleConstants.PERMISSION_CODE, s);
				saveMap.put(RoleConstants.STATUS_CD, DictUtil.getDictValue("StCd", "StCd_1"));
				saveMap.put("tenantId", params.get("tenantId"));
				SysRolePermissionRelEntity entity=new SysRolePermissionRelEntity().coverToEntity(saveMap, null);
				try{
				sysRolePermissionRelDao.save(entity);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
}