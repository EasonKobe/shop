package com.jeedev.msdp.sys.area.api;

import java.util.List;
import java.util.Map;

import com.jeedev.msdp.standard.exception.BizException;

public interface BaseNationAreaApi {
	/**
	 * @方法名称 findByParentAreaCode
	 * @功能描述 <pre>通过parentAreaCode在数据库中查找所有信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午10:53:47
	 * @param   cityCode ：地址代码  areaName：地址名称  regionLevel：上级代码
	 * @return  cityCode ：地址代码  areaName：地址名称  regionLevel：上级代码
	 */
	public List<Map<String, Object>> findByParentAreaCode(Map<String, String> params);
	/**
	 * @方法名称 getAreaMap
	 * @功能描述 <pre>获取对象的详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午10:54:12
	 * @param params
	 * @return
	 */
	public Map<String, Object> getAreaMap(Map<String, String> params);
	/**
	 * @方法名称 saveAreaTrans
	 * @功能描述 <pre>新增地区实例</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午10:54:49
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int saveAreaTrans(Map<String, String> params) throws Exception;
	/**
	 * @方法名称 updateAreaTrans
	 * @功能描述 <pre>更新地区实例</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午10:57:45
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int updateAreaTrans(Map<String, String> params) throws Exception;
	/**
	 * @方法名称 deleteAreaTransById
	 * @功能描述 <pre>根据ID删除区域实例</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 上午10:58:02
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public int deleteAreaTransById(Map<String, String> params) throws Exception;
	/**
	 * @方法名称 countNotDistinct
	 * @功能描述 <pre>统计数据库areaName字段的值重复的次数</pre>
	 * @作者    youhuiqing
	 * @创建时间 2015年12月3日 下午4:49:09
	 * @param areaName
	 * @throws BizException
	 * @return Integer
	 */
	public Integer countNotDistinct(String areaCode);
	 /**
	  * @方法名称 findAddresList
	  * @功能描述 <pre>查询地址列表</pre>
	  * @作者    weiqinshu
	  * @创建时间 2016年12月28日 下午3:50:52
	  * @param   cityCode ：地址代码  areaName：地址名称  regionLevel：上级代码
	  * @return  cityCode ：地址代码  areaName：地址名称  regionLevel：上级代码
	  */
    public List<Map<String, Object>> findAddresList(Map<String, String> params);
}
