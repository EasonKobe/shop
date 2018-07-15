package com.jeedev.msdp.sys.dataauth.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.StringUtil;
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.base.encode.service.IEncodeService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.dataauth.api.DataAuthConstants;
import com.jeedev.msdp.sys.dataauth.dao.ISysResDataAuthModelDao;
import com.jeedev.msdp.sys.dataauth.entity.SysResDataAuthModelEntity;
import com.jeedev.msdp.sys.dataauth.service.IDataAuthModelService;

@Service("dataAuthModelService")
public class DataAuthModelServiceImpl implements IDataAuthModelService {

	@Autowired
	private ISysResDataAuthModelDao sysDataAuthModelDao;
	/**
	 * 编码生成服务
	 */
	@Autowired
	private IEncodeService encodeService;
	/**
	 * 
	 * @方法名称 findDataAuthPage
	 * @功能描述 <pre>分页获取权限的数据集合</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findDataAuthModelPage(Map<String, Object> params) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		return new PageInfo<>(sysDataAuthModelDao.findDataAuthModelList(params));
	}
	/**
	 * 
	 * @方法名称 getDataAuthModelMap
	 * @功能描述 <pre>根据条件获取单个权限数据</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> getDataAuthModelMap(Map<String, Object> params) {
		String modelCode = MapUtils.getString(params, DataAuthConstants.MODEL_CODE); 
		String id = MapUtils.getString(params, DataAuthConstants.ID);
		if (!StringUtil.isEmpty(id)) {
			params.remove(DataAuthConstants.DATA_AUTH_CODE);
		}
		if (!StringUtil.isEmpty(modelCode)) {
			params.put("delInd",DictUtil.INDICATOR_NO());
		}
		if (StringUtil.isEmpty(id) && StringUtil.isEmpty(modelCode) ) {
			throw ExceptionFactory.getBizException("sys-00004",DataAuthConstants.ID, DataAuthConstants.DATA_AUTH_CODE);
		}
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> DataAuthPage = this.findDataAuthModelPage(params);
		//判断是否存在数据
		long total = DataAuthPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			List<Map<String, Object>> DataAuthList = DataAuthPage.getList();
			if(null!= DataAuthList && DataAuthList.size()>0 ){
				return (Map<String, Object>) DataAuthList.get(0);
			}
		}
		return null;
	}
	/**
	 * 
	 * @方法名称 saveDataAuthModel
	 * @功能描述 <pre>保存权限</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveDataAuthModel(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		String dataAuthCode;
		try {
			dataAuthCode = encodeService.buildEncode("10081", "systemAuto");
		} catch (Exception e) {
			throw ExceptionFactory.getBizException("sys-00006");
		}
		params.put(DataAuthConstants.DATA_AUTH_CODE, dataAuthCode);
		
		SysResDataAuthModelEntity DataAuthEntity=new SysResDataAuthModelEntity().coverToEntity(params, null);
		SysResDataAuthModelEntity DataAuth = sysDataAuthModelDao.save(DataAuthEntity);
		params.put(DataAuthConstants.ID, DataAuth.getId());
		return params;
	}
	/**
	 * 
	 * @方法名称 updateDataAuth
	 * @功能描述 <pre>更新权限数据</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月16日 下午3:05:38
	 * @param params
	 */
	@Override
	public void updateDataAuthModel(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值  
		Integer id = MapUtils.getInteger(params, DataAuthConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysResDataAuthModelEntity findOne = sysDataAuthModelDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysResDataAuthModelEntity entity = findOne.coverToEntity(params, findOne);
		sysDataAuthModelDao.update(entity);
	}
	/**
	 * 
	 * @方法名称 deleteDataAuth
	 * @功能描述 <pre></pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月16日 下午3:07:35
	 * @param params
	 */
	@Override
	public void deleteDataAuthModel(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, DataAuthConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysResDataAuthModelEntity entity = sysDataAuthModelDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd(DictUtil.INDICATOR_YES()); // 逻辑删除标识
		sysDataAuthModelDao.update(entity);
	}

}
