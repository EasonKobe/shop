package com.jeedev.msdp.sys.org.service.impl;

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
import com.jeedev.msdp.sys.org.api.OrgApiConstants;
import com.jeedev.msdp.sys.org.dao.ISysOrgDeptRelDao;
import com.jeedev.msdp.sys.org.entity.SysOrgDeptRelEntity;
import com.jeedev.msdp.sys.org.service.ISysOrgDeptRelService;
/**
 * @类名称 SysOrgDeptRelServiceImpl.java
 * @类描述 <pre>机构与部门管理实现类</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月18日 上午9:54:19
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	yuyq            
 *     ----------------------------------------------
 * </pre>
 */
@Service("sysOrgDeptRelService")
public class SysOrgDeptRelServiceImpl implements ISysOrgDeptRelService
{
	@Autowired
	private ISysOrgDeptRelDao sysOrgDeptRelDao;
	/**
	 * 
	 * @方法名称 findOrgDeptRelPage
	 * @功能描述 <pre>分页获取机构与部门关系的数据集合</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:26
	 * @param params
	 * @param pageNum
	 * @param pageSize
	 * @return
	 */
	@Override
	public PageInfo<Map<String, Object>> findOrgDeptRelPage(Map<String, Object> params,PageParam pageParam) {
		//判断当前参数params是否为空，则为默认查询
		if(null == params){
			params = new HashMap<String,Object>();
		}
		params.put("delInd", "0");
		return new PageInfo<>(sysOrgDeptRelDao.findOrgDeptRelList(params));
	}
	/**
	 * 
	 * @方法名称 getOrgDeptRelMap
	 * @功能描述 <pre>根据条件获取单个机构与部门关系数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:28:22
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> getOrgDeptRelMap(Map<String, Object> params) {
		String deptCode = MapUtils.getString(params, OrgApiConstants.DEPT_CODE); 
		String orgCode = MapUtils.getString(params, OrgApiConstants.ORG_CODE); 
		String id = MapUtils.getString(params, OrgApiConstants.Id);
		if (!StringUtil.isEmpty(id)) {
			params.remove(OrgApiConstants.DEPT_CODE);
			params.remove(OrgApiConstants.ORG_CODE);
		}
		if (!StringUtil.isEmpty(deptCode)) {
			params.put(OrgApiConstants.DEL_IND, "0");
		}
		//默认调用分页查询方法。
		PageInfo<Map<String, Object>> orgDeptRelPage = this.findOrgDeptRelPage(params, null);
		//判断是否存在数据
		long total = orgDeptRelPage.getTotal();
		if( 0 < total){
			//获取查询结果列表
			List<Map<String, Object>> OrgDeptRelList = orgDeptRelPage.getList();
			if(null!= OrgDeptRelList && OrgDeptRelList.size()>0 ){
				return (Map<String, Object>) OrgDeptRelList.get(0);
			}
		}
		return null;
	}
	/**
	 * 
	 * @方法名称 saveOrgDeptRel
	 * @功能描述 <pre>保存机构与部门关系</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午2:55:37
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveOrgDeptRel(Map<String, Object> params) {
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		SysOrgDeptRelEntity orgDeptRelEntity = sysOrgDeptRelDao.save(new SysOrgDeptRelEntity().coverToEntity(params, null));
		params.put("id", orgDeptRelEntity.getId());
		return params;
	}
	/**
	 * 
	 * @方法名称 updateOrgDeptRel
	 * @功能描述 <pre>更新机构与部门关系数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:05:38
	 * @param params
	 */
	@Override
	public void updateOrgDeptRel(Map<String, Object> params) {
		//update要先根据ID获取BO对象，然后在拷贝map里面的值  
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysOrgDeptRelEntity findOne = sysOrgDeptRelDao.findOne(id);
		if (findOne == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		SysOrgDeptRelEntity entity = findOne.coverToEntity(params, findOne);
		sysOrgDeptRelDao.update(entity);
	}
	/**
	 * 
	 * @方法名称 deleteOrgDeptRel
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月16日 下午3:07:35
	 * @param params
	 */
	@Override
	public void deleteOrgDeptRel(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, OrgApiConstants.Id);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		SysOrgDeptRelEntity entity = sysOrgDeptRelDao.findOne(id);
		if (entity == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}
		entity.setDelInd("1"); //逻辑删除标识
		sysOrgDeptRelDao.update(entity);
		
	}
}