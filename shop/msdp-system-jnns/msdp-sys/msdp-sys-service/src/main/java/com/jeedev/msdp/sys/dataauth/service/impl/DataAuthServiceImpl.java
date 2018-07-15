package com.jeedev.msdp.sys.dataauth.service.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.base.encode.service.IEncodeService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.dataauth.api.DataAuthConstants;
import com.jeedev.msdp.sys.dataauth.dao.ISysDataAuthDao;
import com.jeedev.msdp.sys.dataauth.entity.SysDataAuthEntity;
import com.jeedev.msdp.sys.dataauth.service.IDataAuthService;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;

@Service("dataAuthService")
public class DataAuthServiceImpl implements IDataAuthService {
	static Map<String,String> pagekey2Dbkey = new HashMap<String,String>();
	static {
		pagekey2Dbkey.put("clntendId", "clntendId");
		pagekey2Dbkey.put("userNum", "currUsrId");
		pagekey2Dbkey.put("bizScene", "currBizScene");
		pagekey2Dbkey.put("roleCode", "currRlId");
		pagekey2Dbkey.put("orgCode", "currInstCd");
		pagekey2Dbkey.put("deptCode", "currDeptCd");
		pagekey2Dbkey.put("eventCode", "currEventCd");
	}
	private void  createAuthConditions(Map<String, Object> dataauthMap) {
		StringBuffer authConditions = new StringBuffer();
		Iterator<String> keyMap_it = pagekey2Dbkey.keySet().iterator();
		String pageKey = "";
		String dbKey = "";
		while(keyMap_it.hasNext()) {
			pageKey = keyMap_it.next();
			if(dataauthMap.containsKey(pageKey)) {
				dbKey = pagekey2Dbkey.get(pageKey);
				authConditions.append(dbKey);
				authConditions.append(":");
				authConditions.append(dataauthMap.get(pageKey));
				authConditions.append(";");
			}
		}
		dataauthMap.put("authConditions", authConditions.toString());
	}
	@Autowired
	private ISysDataAuthDao sysDataAuthDao;
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
	public PageInfo<Map<String, Object>> findDataAuthPage(Map<String, Object> params) {
		
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put(DataAuthConstants.DEL_IND,DictUtil.INDICATOR_NO());
		List<Map<String, Object>> list = null;
		if(params.containsKey(DataAuthConstants.QUERY_TYPE) && "0".equals(params.get(DataAuthConstants.QUERY_TYPE))) {
			list=sysDataAuthDao.findDataAuthListByExact(params);
		}else {
			list=sysDataAuthDao.findDataAuthList(params);
		}
		
		return new PageInfo<>(list);
	}
	
	@Override
	public PageInfo<Map<String, Object>> findDataAuthListByExact(Map<String, Object> params) {
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put(DataAuthConstants.DEL_IND,DictUtil.INDICATOR_NO());
		List<Map<String, Object>> list=sysDataAuthDao.findDataAuthListByExact(params);
		return new PageInfo<>(list);
	}
	/**
	 * 
	 * @方法名称 getDataAuthMap
	 * @功能描述 <pre>根据条件获取单个权限数据</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> getDataAuthMap(Map<String, Object> params) {
		String dataAuthCode = MapUtils.getString(params, DataAuthConstants.DATA_AUTH_CODE); 
		String id = MapUtils.getString(params, DataAuthConstants.ID);
		if (!StringUtil.isEmpty(id)) {
			params.remove(DataAuthConstants.DATA_AUTH_CODE);
		}
		if (!StringUtil.isEmpty(dataAuthCode)) {
			params.put("delInd",DictUtil.INDICATOR_NO());
		}
		if (StringUtil.isEmpty(id) && StringUtil.isEmpty(dataAuthCode) ) {
			throw ExceptionFactory.getBizException("sys-00004",DataAuthConstants.ID, DataAuthConstants.DATA_AUTH_CODE);
		}
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> dataAuthPage = this.findDataAuthListByExact(params);
		//判断是否存在数据
		long total = dataAuthPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			List<Map<String, Object>> dataAuthList = dataAuthPage.getList();
			if(null!= dataAuthList && dataAuthList.size()>0 ){
//				parse(dataAuthList.get(0));
				return (Map<String, Object>) dataAuthList.get(0);
			}
		}
		return null;
	}
	/**
	 * 
	 * @方法名称 saveDataAuth
	 * @功能描述 <pre>保存权限</pre>
	 * @作者    yuyq
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveDataAuth(Map<String, Object> params) {
//		serial(params);
		
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		
		//1.对空维度进行初始化
		init(params);
		
		//2.验证规则是否重复
		checkExist(params);
		//3.生成 编号
		String dataAuthCode;
		try {
			dataAuthCode = encodeService.buildEncode("10081", "DA");
		} catch (Exception e) {
			throw ExceptionFactory.getBizException("sys-00006");
		}
		params.put(DataAuthConstants.DATA_AUTH_CODE, dataAuthCode);
		
		//4.保存
		SysDataAuthEntity dataAuthEntity=new SysDataAuthEntity().coverToEntity(params, null);
		SysDataAuthEntity dataAuth = sysDataAuthDao.save(dataAuthEntity);
		params.put(DataAuthConstants.ID, dataAuth.getId());
		return params;
	}
	
	/**
	 * 
	 * @方法名称 init
	 * @功能描述 <pre>空值初始化</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月10日 下午1:29:13
	 * @param params
	 */
	private void init(Map<String, Object> params) {
		Iterator<String> keyMap_it = pagekey2Dbkey.keySet().iterator();
		String pageKey = "";
		while(keyMap_it.hasNext()) {
			pageKey = keyMap_it.next();
			if(StringUtil.isEmpty(MapUtil.getString(params, pageKey))) {
				params.put(pageKey, "*");
			}
		}
		createAuthConditions(params);
	}
	
	/**
	 * 
	 * @方法名称 checkExist
	 * @功能描述 <pre>校验维度组合下是否已存在</pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月10日 下午1:28:50
	 * @param params
	 */
	private void checkExist(Map<String, Object> params) {
		Map<String, Object> queryParams=new HashMap<String, Object>();
		Iterator<String> keyMap_it = pagekey2Dbkey.keySet().iterator();
		String pageKey = "";
		while(keyMap_it.hasNext()) {
			pageKey = keyMap_it.next();
			queryParams.put(pageKey, params.get(pageKey));
		}
		List<Map<String, Object>> list=sysDataAuthDao.findDataAuthListByExact(queryParams);
		Integer id = MapUtils.getInteger(params, DataAuthConstants.ID);
		if(id==null) {
			if(list!=null&&!list.isEmpty()){
				throw ExceptionFactory.getBizException("sys-data-00001");
			}
		}else {
			//如果id不一致则表示与其他数据出现规则重复
			if(list!=null&&!list.isEmpty()&&!id.equals(list.get(0).get(DataAuthConstants.ID))){
				throw ExceptionFactory.getBizException("sys-data-00001");
			}
		}
		
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
	public void updateDataAuth(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值  
		Integer id = MapUtils.getInteger(params, DataAuthConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		//1.空值初始化
		init(params);
		//2.校验是否已存在
		checkExist(params);
		
		//3.修改
		SysDataAuthEntity findOne = sysDataAuthDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysDataAuthEntity entity = findOne.coverToEntity(params, findOne);
		
		sysDataAuthDao.update(entity);
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
	public void deleteDataAuth(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, DataAuthConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysDataAuthEntity entity = sysDataAuthDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd(DictUtil.INDICATOR_YES()); // 逻辑删除标识
		sysDataAuthDao.update(entity);
	}
	

}
