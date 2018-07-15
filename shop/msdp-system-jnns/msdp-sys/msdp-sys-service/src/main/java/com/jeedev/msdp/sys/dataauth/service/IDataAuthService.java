package com.jeedev.msdp.sys.dataauth.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
/**
 * @类名称 IDataAuthService.java
 * @类描述 <pre>数据权限服务类</pre>
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
public interface IDataAuthService {
	/**
	 * 
	 * @方法名称 findDataAuthPage
	 * @功能描述 <pre>分页查询数据权限信息</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午7:13:52
	 * @param params 参数
	 * dataAuthCode 数据权限编码
	 * modelCode 数据权限类型编码
	 * authConditions 过滤条件
	 * @return
	 */
	public PageInfo<Map<String, Object>> findDataAuthPage(Map<String, Object> params);
	
	/**
	 * 精确查询数据权限，不进行通配符匹配
	 * @方法名称 findDataAuthListByExact
	 * @功能描述 <pre></pre>
	 * @作者    chenjc
	 * @创建时间 2017年11月10日 上午11:16:45
	 * @param params
	 * @return
	 */
	public PageInfo<Map<String, Object>> findDataAuthListByExact(Map<String, Object> params);
	
	/**
	 * 
	 * @方法名称 findDataAuth
	 * @功能描述 <pre>查询选中数据权限</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午4:44:15
	 * @param params 参数
	 * dataAuthCode 数据权限编码
	 * id 主键
	 * @return
	 */
	public Map<String, Object> getDataAuthMap(Map<String, Object> params);
	/**
	 * 
	 * @方法名称 saveDataAuth
	 * @功能描述 <pre>保存数据权限信息</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午4:58:39
	 * @param params 参数
	 * dataAuthCode 数据权限编码
	 * modelCode 数据权限类型编码
	 * authConditions 过滤条件
	 * @return
	 */
	public Map<String, Object> saveDataAuth(Map<String, Object> params) ;
	/**
	 * 
	 * @方法名称 updateDataAuth
	 * @功能描述 <pre>更新数据权限</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午3:00:33
	 * @param params 参数
	 * id 主键
	 * dataAuthCode 数据权限编码
	 * modelCode 数据权限类型编码
	 * authConditions 过滤条件
	 * @return
	 */
	public void updateDataAuth(Map<String, Object> params) ;
	
	/**
	 * 
	 * @方法名称 deleteDataAuth
	 * @功能描述 <pre>删除数据权限信息</pre>
	 * @作者   yuyq
	 * @创建时间 2017年9月25日 下午5:11:58
	 * @param params 参数
	 * id 主键
	 */
	public void deleteDataAuth(Map<String, Object> params);

}
