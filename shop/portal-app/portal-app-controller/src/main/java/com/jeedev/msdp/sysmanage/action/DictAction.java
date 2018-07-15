package com.jeedev.msdp.sysmanage.action;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
/**
 * 
 */
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.dict.api.DictApi;
/**
 * 
 * @类名称 DictAction.java
 * @类描述 <pre>数据字典Action</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年9月12日 下午4:59:37
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年9月12日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping("/dict")
public class DictAction extends BaseAppAction {
	
	@Autowired
	private DictApi dictApi;
	
	/**
	 * @方法名称 tree
	 * @功能描述 <pre>根据条件查询数据字典列表</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:09:02
	 * @param reqMap 请求参数
	 * dctKey 字典key
	 * dctTpCd 字典类型
	 * dctTpNm 字典类型名称
	 * dctVal 字典值
	 * dctValNm 字典值名称
	 * dctGrp 字典组
	 * dctDsc 字典描述
	 * dctSeq 字典排序
	 * @param page 分页信息
	 * @return 字典列表
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public ResponseVO tree(@RequestParam Map<String, String> reqMap,PageParam page) throws Exception {
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("grid", dictApi.findDictPage(reqMap, page));
		return this.successResponse(result);
	}
	
	/**
	 * @方法名称 get
	 * @功能描述 <pre>根据id获取数据字典信息</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:09:54
	 * @param reqMap 请求参数
	 * id 主键编号
	 * @return 字典信息map
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/get")
	public ResponseVO get(@RequestParam Map<String, String> reqMap) throws Exception {  
		Map<String, String> ddctMap = dictApi.getDictMap(reqMap);
		return this.successResponse(ddctMap);
	}
	
	/**
	 * 
	 * @方法名称 delete
	 * @功能描述 <pre>根据id删除数据字典</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:10:01
	 * @param reqMap 请求参数
	 * id 主键编号
	 * @return 成功或事变
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/delete")
	public ResponseVO delete(@RequestParam Map<String, String> reqMap) throws Exception { 
		try{
			dictApi.deleteDictTrans(reqMap);
			return this.successResponse("删除成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}

	/**
	 * @方法名称 save
	 * @功能描述 <pre>新增或修改数据字典</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月17日 下午11:10:06
	 * @param RequstObjectMap 请求参数
	 * dctKey 字典key
	 * dctTpCd 字典类型
	 * dctTpNm 字典类型名称
	 * dctVal 字典值
	 * dctValNm 字典值名称
	 * dctGrp 字典组
	 * dctDsc 字典描述
	 * dctSeq 字典排序
	 * @return 成功或失败
	 * @throws Exception 异常
	 */
	@ResponseBody
	@RequestMapping(value = "/save")
	public ResponseVO save(@RequestObjectParam Map<String, String> RequstObjectMap) throws Exception { 
		try{
			dictApi.saveDictTrans(RequstObjectMap);
			return this.successResponse("保存成功");
		}catch(Exception e){
			return this.errorResponse(e.getMessage());
		}
	}
}
