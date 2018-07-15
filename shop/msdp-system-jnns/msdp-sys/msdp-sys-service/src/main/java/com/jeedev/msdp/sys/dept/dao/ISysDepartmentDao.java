package com.jeedev.msdp.sys.dept.dao;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.dao.BaseDao;
import com.jeedev.msdp.core.support.mybatis.dataauth.annotation.DataAuthClassIgnore;
import com.jeedev.msdp.sys.dept.entity.SysDepartmentEntity;
/**
 * 
 * @类名称 SysDepartmentDao.java
 * @类描述 <pre>部门管理数据访问类 </pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月15日 上午11:15:33
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
@DataAuthClassIgnore
public interface ISysDepartmentDao extends BaseDao<SysDepartmentEntity, Integer>
{
    /**
     * 
     * @方法名称 findSysDepartmentList
     * @功能描述 <pre>根据不同条件查询部门</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年8月15日 下午1:49:40
     * @param map
     * @return
     */
    public List<Map<String,Object>> findDeptList(Map<String, Object> map);
    
    
}