package com.eason.wx.goods.service;

import com.jeedev.msdp.standard.exception.BizException;

import java.util.List;
import java.util.Map;

/**
 * 联系我们信息表对内接口
 *
 * @类名称 LjContactUsInfoService
 * @类描述 <pre> 联系我们信息表对外接口</pre>
 * @作者 chenyuxin
 * @创建时间 2018年03月14日
 * @版本 v1.00
 * @修改记录 <pre>
 * 版本     		修改人 	修改时间    	 	修改内容	描述
 * ----------------------------------------------
 * 1.00 	chenyuxin     	2018年03月14日 	新建
 * ----------------------------------------------
 * </pre>
 */
public interface ILjContactUsInfoService {

	/**
	 * 查询联系我们信息表列表
	 * @方法名称 findLjContactUsInfoList
	 * @功能描述 <pre> 查询联系我们信息表列表 </pre>
	 * @作者    chenyuxin
	 * @创建时间 2018/03/14 16:23
	 * @param params 参数<br>
	 * @return
	 *         brand：公司品牌<br>
	 *         email：邮箱<br>
	 *         telephone：联系方式<br>
	 *         firstName：姓<br>
	 *         lastName：名<br>
	 *         message：留言内容<br>
	 * @throws RpcException RpcException
	 */
	List<Map<String, Object>> findLjContactUsInfoList(Map<String, Object> params) throws BizException;

	/**
	 * 查询联系我们信息表
	 * @方法名称 findLjContactUsInfo
	 * @功能描述 <pre> 查询联系我们信息表 </pre>
	 * @作者    chenyuxin
	 * @创建时间 2018/03/14 16:23
	 * @param params 参数<br>
	 * @return
	 *         brand：公司品牌<br>
	 *         email：邮箱<br>
	 *         telephone：联系方式<br>
	 *         firstName：姓<br>
	 *         lastName：名<br>
	 *         message：留言内容<br>
	 * @throws RpcException RpcException
	 */
	Map<String, Object> getLjContactUsInfo(Map<String, Object> params) throws BizException;

	/**
	 * 新增/更新联系我们信息表
	 * @方法名称 saveOrUpdateLjContactUsInfo
	 * @功能描述 <pre> 新增联系我们信息表 </pre>
	 * @作者    chenyuxin
	 * @创建时间 2018/03/14 16:23
	 * @param params 参数<br>
	 *			operator：操作用户<br>
	 * @return
	 *         brand：公司品牌<br>
	 *         email：邮箱<br>
	 *         telephone：联系方式<br>
	 *         firstName：姓<br>
	 *         lastName：名<br>
	 *         message：留言内容<br>
	 * @throws RpcException RpcException
	 */
	Map<String, Object> saveOrUpdateLjContactUsInfo(Map<String, Object> params) throws BizException;

	/**
	 * 删除联系我们信息表
	 * @方法名称 deleteLjContactUsInfo
	 * @功能描述 <pre> 删除联系我们信息表 </pre>
	 * @作者    chenyuxin
	 * @创建时间 2018/03/14 16:23
	 * @param params 参数<br>
	 *          LjContactUsInfoId：联系我们信息表编号<br>
	 *          operator：操作用户
	 * @throws RpcException RpcException
	 */
	void deleteLjContactUsInfo(Map<String,Object> params) throws BizException;
}
