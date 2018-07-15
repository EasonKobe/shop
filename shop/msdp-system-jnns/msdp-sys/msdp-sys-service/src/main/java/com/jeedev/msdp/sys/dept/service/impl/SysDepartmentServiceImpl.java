package com.jeedev.msdp.sys.dept.service.impl;

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
import com.jeedev.msdp.sys.dept.dao.ISysDepartmentDao;
import com.jeedev.msdp.sys.dept.entity.SysDepartmentEntity;
import com.jeedev.msdp.sys.dept.service.ISysDepartmentService;
import com.jeedev.msdp.sys.utils.RecursionHelper;


/**
 * 
 * @类名称 SysDepartmentServiceImpl.java
 * @类描述 <pre>部门管理服务类</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月15日 下午1:55:38
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月15日             
 *     ----------------------------------------------
 * </pre>
 */
@Service("sysDepartmentService")
public class SysDepartmentServiceImpl implements ISysDepartmentService
{   
	/**
	 * 部门dao
	 */
	@Autowired
	private ISysDepartmentDao sysDepartmentDao;

	@Override
	public PageInfo<Map<String, Object>> findSysDepartmentPage(Map<String, Object> params,PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
        return new PageInfo<>(sysDepartmentDao.findDeptList(params));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, Object> getSysDepartmentMap(Map<String, Object> params) {
		String deptCode = MapUtils.getString(params, "deptCode");
		String parentDeptCode = MapUtils.getString(params, "parentDeptCode");
		String id = MapUtils.getString(params, "id");
		if (!StringUtil.isEmpty(id)) {
			params.remove("deptCode");
			params.remove("parentDeptCode");
		}
		if (!StringUtil.isEmpty(parentDeptCode)) {
			params.remove("deptCode");
		}
		if (!StringUtil.isEmpty(deptCode)) {
			params.put("delInd", "0");
		}
		if (StringUtil.isEmpty(id) && StringUtil.isEmpty(deptCode) && StringUtil.isEmpty(parentDeptCode)) {
			throw ExceptionFactory.getBizException("sys-00004", "id", "deptCode", "parentDeptCode");
		}
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> sysDepartmentPage = this.findSysDepartmentPage(params, null);
		//判断是否存在数据
		long total = sysDepartmentPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			
			List sysDepartmentList = sysDepartmentPage.getList();
			if(null!= sysDepartmentList && sysDepartmentList.size()>0 ){
				return (Map<String, Object>) sysDepartmentList.get(0);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> saveSysDepartment(Map<String, Object> params) {
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		//状态为空默认设置为生效
		if(params.get("statusCd")==null){
			params.put("statusCd", "1");
		}
		SysDepartmentEntity entity = sysDepartmentDao.save(new SysDepartmentEntity().coverToEntity(params, null));
		params.put("id", entity.getId());
		return params;
	}

	@Override
	public void updateSysDepartment(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysDepartmentEntity findOne = sysDepartmentDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysDepartmentEntity entity = findOne.coverToEntity(params, findOne);
		sysDepartmentDao.update(entity);
		
	}

	@Override
	public void deleteSysDepartment(Map<String, Object> params) {
		//删除和update一样，更新的是delInd
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysDepartmentEntity entity = sysDepartmentDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); //是否删除
		sysDepartmentDao.update(entity);
	}

	@Override
	public List<Map<String, Object>> findParentDepts(Map<String, Object> params) {
		
		List<Map<String, Object>> depts = sysDepartmentDao.findDeptList(params);
		if (depts == null || depts.size() != 1 ) {
			throw ExceptionFactory.getBizException();
		}
		Map<String,Object> reqParam = new HashMap<>();
		reqParam.put("delInd", "0");
		List<Map<String,Object>>  list = sysDepartmentDao.findDeptList(reqParam);
		List<Map<String, Object>> parentsDept = new ArrayList<>();
		Map<String,Map<String,Object>>  deptsMap = RecursionHelper.createMapByListForIndex(list, "deptCode");
		RecursionHelper.findAllParent(deptsMap, depts.get(0),"parentDeptCode", parentsDept);
		return parentsDept;
	}

	@Override
	public List<Map<String, Object>> findChildrenDepts(Map<String, Object> params) {
		List<Map<String, Object>> depts = sysDepartmentDao.findDeptList(params);
		if (depts == null || depts.size() != 1 ) {
			throw ExceptionFactory.getBizException();
		}
		Map<String,Object> reqParam = new HashMap<>();
		reqParam.put("delInd", "0");
		List<Map<String,Object>>  list = sysDepartmentDao.findDeptList(reqParam);
		List<Map<String, Object>> childrenDept = new ArrayList<>();
		Map<String,List<Map<String,Object>>> deptsMap = RecursionHelper.createMapByListForCatalog(list, "parentDeptCode");
		RecursionHelper.findAllChildren(deptsMap,depts.get(0),"deptCode",childrenDept);
		return childrenDept;
	}
	
}