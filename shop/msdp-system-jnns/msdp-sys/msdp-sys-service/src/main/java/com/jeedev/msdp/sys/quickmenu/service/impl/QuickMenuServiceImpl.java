package com.jeedev.msdp.sys.quickmenu.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.quickmenu.dao.ISysQuickMenuDao;
import com.jeedev.msdp.sys.quickmenu.entity.SysQuickMenuEntity;
import com.jeedev.msdp.sys.quickmenu.service.IQuickMenuService;

@Service("QuickMenuService")
public class QuickMenuServiceImpl implements IQuickMenuService{
	
	@Autowired
    private ISysQuickMenuDao sysQuickMenuDao; 
	
	@Override
	public PageInfo<Map<String, Object>> findQuickMenuPage(Map<String, Object> params) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
        return new PageInfo<>(sysQuickMenuDao.findQuickMenuList(params));
    }
	@Override
	public Map<String, Object> getQuickMenuMap(Map<String, Object> params) {
		//默认调用查询方法。 
		PageInfo<Map<String, Object>> pageInfo=this.findQuickMenuPage(params);
		//判断是否存在数据
		long total = pageInfo.getTotal();
		if( 0 < total){
			//获取查询结果列表
			if(null!= pageInfo.getList() && pageInfo.getList().size()>0 ){
				return (Map<String, Object>) pageInfo.getList().get(0);
			}
		}
		return null;
	}
	@Override
	public Map<String, Object> saveQuickMenuTrans(Map<String, Object> params) {
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		SysQuickMenuEntity coverEntity=new SysQuickMenuEntity().coverToEntity(params, null);
		SysQuickMenuEntity userEntity = sysQuickMenuDao.save(coverEntity);
		params.put("id", userEntity.getId());
		return params;
	}

	@Override
	public void updateQuickMenuTrans(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysQuickMenuEntity findOne = sysQuickMenuDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysQuickMenuEntity entity = findOne.coverToEntity(params, findOne);
		sysQuickMenuDao.update(entity);
		
	}

	@Override
	public void deleteQuickMenuTrans(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		sysQuickMenuDao.delete(id);//直接物理删除
	}

}
