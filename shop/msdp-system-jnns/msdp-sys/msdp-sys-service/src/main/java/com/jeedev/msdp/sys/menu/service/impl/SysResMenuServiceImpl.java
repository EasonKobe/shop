package com.jeedev.msdp.sys.menu.service.impl;

import java.util.ArrayList;
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
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.sys.menu.dao.ISysResMenuDao;
import com.jeedev.msdp.sys.menu.entity.SysResMenuEntity;
import com.jeedev.msdp.sys.menu.service.ISysResMenuService;
import com.jeedev.msdp.sys.org.api.OrgApiConstants;
import com.jeedev.msdp.sys.utils.RecursionHelper;
/**
 * @类名称 SysResMenuServiceImpl.java
 * @类描述 <pre>菜单服务类</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月18日 上午9:53:09
 * @版本 1.00
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	yuyq            
 *     ----------------------------------------------
 * </pre>
 */
@Service("sysResMenuService")
public class SysResMenuServiceImpl implements ISysResMenuService {
	/**
	 * 菜单dao
	 */
	@Autowired
	private ISysResMenuDao sysResMenuDao;
	

	@Override
	public PageInfo<Map<String, Object>> findMenuPage(Map<String, Object> params,PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put(MenuConstants.DEL_IND, "0");
        return new PageInfo<>(sysResMenuDao.findMenuList(params));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> getMenuMap(Map<String, Object> params) {
		String menuCode = MapUtils.getString(params, MenuConstants.MENU_CODE);
		String parentMenuCode = MapUtils.getString(params, MenuConstants.PARENT_MENU_CODE);
		String id = MapUtils.getString(params, MenuConstants.ID);
		if (!StringUtil.isEmpty(id)) {
			params.remove(MenuConstants.MENU_CODE);
			params.remove(MenuConstants.PARENT_MENU_CODE);
		}
		if (!StringUtil.isEmpty(parentMenuCode)) {
			params.remove(MenuConstants.MENU_CODE);
		}
		if (!StringUtil.isEmpty(menuCode)) {
			params.put(MenuConstants.DEL_IND, "0");
		}
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> MenuPage = this.findMenuPage(params,null);
		//判断是否存在数据
		long total = MenuPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			
			List MenuList = MenuPage.getList();
			if(null!= MenuList && MenuList.size()>0 ){
				return (Map<String, Object>) MenuList.get(0);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> saveMenu(Map<String, Object> params) {
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		SysResMenuEntity entity = sysResMenuDao.save(new SysResMenuEntity().coverToEntity(params, null));
		
		Map<String, Object> result=new HashMap<>();
		result.put(MenuConstants.ID, entity.getId());
		return result;
	}

	@Override
	public void updateMenu(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, MenuConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysResMenuEntity findEntity = sysResMenuDao.findOne(id);
		if (findEntity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysResMenuEntity entity = findEntity.coverToEntity(params, findEntity);
		sysResMenuDao.update(entity);
		
	}

	@Override
	public void deleteMenu(Map<String, Object> params) {
		//删除和update一样，更新的是delInd
		Integer id = MapUtils.getInteger(params, MenuConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysResMenuEntity entity = sysResMenuDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); //是否删除
		sysResMenuDao.update(entity);
	}
	@Override
	public List<Map<String, Object>> findChildrenMenus(Map<String, Object> params) {
		List<Map<String, Object>> menus = sysResMenuDao.findMenuList(params);
		if (menus == null || menus.size() != 1 ) {
			throw ExceptionFactory.getBizException();
		}
		
		Map<String,Object> reqParam = new HashMap<>();
		reqParam.put(OrgApiConstants.DEL_IND, "0");
		List<Map<String,Object>>  list = sysResMenuDao.findMenuList(reqParam);
		List<Map<String, Object>> childrenMenus = new ArrayList<>();
		Map<String,List<Map<String,Object>>>  deptsMap = RecursionHelper.createMapByListForCatalog(list, MenuConstants.PARENT_MENU_CODE);
		RecursionHelper.findAllChildren(deptsMap, menus.get(0), MenuConstants.MENU_CODE,childrenMenus);
		return childrenMenus;
	}
}
