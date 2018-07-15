package com.jeedev.msdp.sysmanage.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
/**
 * 
 */
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.param.api.SysParamApi;
/**
 * 
 * @类名称 ParamAction.java
 * @类描述 <pre>系统参数Action层</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年9月17日 下午11:06:24
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年9月17日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping("/param")
public class ParamAction extends BaseAppAction {
	
	@Autowired
	private SysParamApi sysParamApi;
	/**
	 * @方法名称 tree
	 * @功能描述 <pre>根据条件请求获取系统参数分页信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:06:09
	 * @param RequstObjectMap
	 * @param page
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseVO tree(@RequestObjectParam Map<String, String> RequstObjectMap,PageParam page) throws Exception {
		try{
			Map<String, Object> result = new HashMap<String, Object>();
			result.put("grid", sysParamApi.findParamPage(RequstObjectMap, page));
			return this.successResponse(result);
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 get
	 * @功能描述 <pre>根据编号ID获取参数详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:07:37
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public ResponseVO get(@RequestObjectParam Map<String, String> RequstObjectMap) throws Exception {  
		try{
			Map<String, String> paramMap = sysParamApi.getParamMap(RequstObjectMap); 
			return this.successResponse(paramMap);
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
	/**
	 * @方法名称 delete
	 * @功能描述 <pre>根据编号删除对象的数据</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:08:00
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseVO delete(@RequestObjectParam Map<String, String> RequstObjectMap) throws Exception { 
		try{
			sysParamApi.deleteParamTrans(RequstObjectMap);
			return this.successResponse("删除成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 save
	 * @功能描述 <pre>保存或修改系统参数实例</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月17日 下午11:08:24
	 * @param RequstObjectMap
	 * @return
	 * @throws Exception
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResponseVO save(@RequestObjectParam Map<String, String> RequstObjectMap) throws Exception { 
		try{
			sysParamApi.saveParamTrans(RequstObjectMap);
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
}
