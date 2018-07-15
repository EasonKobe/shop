package com.jeedev.msdp.sys.dataauth.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
/**
 * @类名称 IDataAuthModelService.java
 * @类描述 <pre>数据权限类型服务类</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月25日 下午5:26:46
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
public interface IDataAuthModelService {
	/**
	 * 
	 * @方法名称 findDataAuthModelPage
	 * @功能描述 <pre>分页查询数据权限类型信息</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午7:13:52
	 * @param params 参数
	 * modelId 数据权限类型
	 * modelCode 数据权限类型编码
	 * @return
	 */
	public PageInfo<Map<String, Object>> findDataAuthModelPage(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 findDataAuthModel
	 * @功能描述 <pre>查询选中数据权限类型</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午4:44:15
	 * @param params 参数
	 * modelCode 数据权限类型编码
	 * id 主键
	 * @return
	 */
	public Map<String, Object> getDataAuthModelMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveDataAuthModel
	 * @功能描述 <pre>保存数据权限类型信息</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午4:58:39
	 * @param params 参数
	 * modelId 数据权限类型
	 * modelCode 数据权限类型编码
	 * @return
	 */
	public Map<String, Object> saveDataAuthModel(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateDataAuthModel
	 * @功能描述 <pre>更新数据权限类型</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午3:00:33
	 * @param params 参数
	 * id 主键
	 * modelId 数据权限类型
	 * modelCode 数据权限类型编码
	 * @return
	 */
	public void updateDataAuthModel(Map<String, Object> params) ;
	
	/**
	 * 
	 * @方法名称 deleteDataAuthModel
	 * @功能描述 <pre>删除数据权限类型信息</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午5:11:58
	 * @param params 参数
	 * id 主键
	 */
	public void deleteDataAuthModel(Map<String, Object> params);

}
