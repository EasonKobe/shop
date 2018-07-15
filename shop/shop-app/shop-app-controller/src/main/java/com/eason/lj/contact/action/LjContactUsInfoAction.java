package com.eason.lj.contact.action;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.eason.lj.contact.api.ILjContactUsInfoApi;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.web.action.BaseAppAction;

/**
*
* @类名称 CmCreditPayAction
* @类描述 <pre>
* LjContactUsInfoAction控制类
* </pre>
* @作者 chenyuxin
* @创建时间 2018年03月05日
* @版本 v1.00
* @修改记录 <pre>
* 版本     		修改人 	修改时间    	 	修改内容	描述
* ----------------------------------------------
* 1.00 	chenyuxin     	2018年03月05日 	新建
* ----------------------------------------------
* </pre>
*/
@Controller
@RequestMapping("/lvjing/contactUs")
public class LjContactUsInfoAction extends BaseAppAction{
	
	@Autowired
	private ILjContactUsInfoApi ljContactUsInfoApi;
	
	/**
	 * @方法名称 pagingLjContactUsList
	 * @功能描述 <pre>联系我们信息列表查询</pre>
	 * @作者    chenyuxin
	 * @创建时间 2018年3月14日 下午4:52:28
	 * @param params
	 * @param pageParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pagingLjContactUsList")
	public ResponseVO pagingLjContactUsList(
			@RequestParam Map<String, Object> params, PageParam pageParam) {
		List<Map<String, Object>> listPage = this.ljContactUsInfoApi.findLjContactUsInfoList(params, pageParam);
		return this.successResponse(new PageInfo<>(listPage));
	}
	
	/**
	 * @方法名称 initLjContactUs
	 * @功能描述 <pre>初始化联系我们信息</pre>
	 * @作者    chenyuxin
	 * @创建时间 2018年3月14日 下午7:13:17
	 * @param params
	 * @param pageParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/initLjContactUs")
	public ResponseVO initLjContactUs(
			@RequestParam Map<String, Object> params, PageParam pageParam) {
		Map<String, Object> resultMap = this.ljContactUsInfoApi.getLjContactUsInfo(params);
		return this.successResponse(resultMap);
	}
	
	/**
	 * @方法名称 doSaveContactUs
	 * @功能描述 <pre>保存联系我们信息</pre>
	 * @作者    chenyuxin
	 * @创建时间 2018年3月14日 下午7:14:18
	 * @param params
	 * @param pageParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doSaveContactUs")
	public ResponseVO doSaveContactUs(
			@RequestParam Map<String, Object> params, PageParam pageParam) {
		Map<String, Object> resultMap = this.ljContactUsInfoApi.saveOrUpdateLjContactUsInfoTrans(params);
		return this.successResponse(resultMap);
	}
	
	/**
	 * @方法名称 doDeleteContactUs
	 * @功能描述 <pre>删除联系我们信息</pre>
	 * @作者    chenyuxin
	 * @创建时间 2018年3月14日 下午7:15:04
	 * @param params
	 * @param pageParam
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/doDeleteContactUs")
	public ResponseVO doDeleteContactUs(
			@RequestParam Map<String, Object> params, PageParam pageParam) {
		this.ljContactUsInfoApi.deleteLjContactUsInfoTrans(params);
		return this.successResponse("删除成功");
	}
}
