package com.jeedev.msdp.sys.dict.api.impl;

import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.dict.api.DictApi;
import com.jeedev.msdp.sys.dict.api.DictConstants;
import com.jeedev.msdp.sys.dict.service.IDictService;
import com.jeedev.msdp.trace.IProviderSign;
/**
 * 
 * @类名称 DdctApiImpl.java
 * @类描述 <pre>数据字典实现类</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年9月5日 上午8:44:18
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年9月5日             
 *     ----------------------------------------------
 * </pre>
 */
@Service("sysDictApi")
public class DictApiImpl implements DictApi,IProviderSign{
	
	/**
	 *字典服务类 
	 */
	@Autowired
	private IDictService dictService ;
	
	@Override
	public PageInfo<Map<String, String>> findDictPage(Map<String, String> params,PageParam pageParam) { 
		return dictService.findDictPage(params,  pageParam);
	}
	@Override
	public Map<String, String> getDictMap(Map<String, String> params) { 
		return dictService.getDictMap(params);
	}
	
	@Override
	public void saveDictTrans(Map<String, String> params) throws Exception {
	
		// 组装方法要判空
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		String id= MapUtils.getString(params, DictConstants.ID);
		if(id==null){
			dictService.saveDict(params);//新增 
			return;
		}
		dictService.updateDict(params);
		
	}

	@Override
	public void deleteDictTrans(Map<String, String> params) throws Exception { 
		dictService.deleteDict(params);
	}

	@Override
	public void updateDictTrans(Map<String, String> params) throws Exception {
		dictService.updateDict(params);
	}
  }
