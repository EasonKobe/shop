package com.jeedev.msdp.sys.dict.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.github.pagehelper.StringUtil;
import com.jeedev.msdp.base.dict.service.IBaseDdctService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.dict.api.DictConstants;
import com.jeedev.msdp.sys.dict.service.IDictService;
/**
 * @类名称 DictServiceImpl.java
 * @类描述 <pre>数据字典实现类</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月18日 上午9:50:00
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
@Service("sysDictService")
public class DictServiceImpl implements IDictService {
	
	/**
	 * 数据字典基础服务类
	 */
	@Autowired
	private IBaseDdctService baseDdctService;
	
	@Override
	public PageInfo<Map<String, String>> findDictPage(Map<String, String> params,PageParam pageParam) {
		// 判断当前参数params是否为空，则为默认查询
		if (null == params) {
			params = new HashMap<String, String>();
		}
		params.put("delInd", "0");
		return new PageInfo<>(baseDdctService.findDicts(params));
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public Map<String, String> getDictMap(Map<String, String> params) {
		String dctTpCd = MapUtils.getString(params, DictConstants.DCT_TP_CD);
		String id = MapUtils.getString(params, DictConstants.ID);
		if (!StringUtil.isEmpty(id)) {
			params.remove(DictConstants.DCT_TP_CD);
		}
		if (!StringUtil.isEmpty(dctTpCd)) {
			params.remove(DictConstants.ID);
		}
		// 默认调用分页查询方法。
		PageInfo<Map<String, String>> dictPage = this.findDictPage(params,null);
		// 判断是否存在数据
		long total = dictPage.getTotal();
		if (0 < total) {
			// 获取查询结果列表

			List dictList = dictPage.getList();
			if (null != dictList && dictList.size() > 0) {
				return (Map<String, String>) dictList.get(0);
			}
		}
		return null;
	}

	@Override
	public void saveDict(Map<String, String> params) throws Exception {
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		baseDdctService.saveDict(params);

	}

	@Override
	public void deleteDict(Map<String, String> params) throws Exception {
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		String operator = MapUtils.getString(params, DictConstants.OPERATOR);
		String id = MapUtils.getString(params, DictConstants.ID);
		baseDdctService.deleteDict(id, operator);

	}

	@Override
	public void updateDict(Map<String, String> params) throws Exception {
		if (params == null || params.isEmpty()) {
			throw ExceptionFactory.getBizException("sys-00003", "params");
		}
		Integer id = MapUtils.getInteger(params, DictConstants.ID);
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		baseDdctService.updateDict(params);
	}

}
