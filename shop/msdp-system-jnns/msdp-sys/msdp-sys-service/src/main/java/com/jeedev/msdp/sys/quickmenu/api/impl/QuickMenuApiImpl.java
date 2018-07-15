package com.jeedev.msdp.sys.quickmenu.api.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.sys.quickmenu.api.QuickMenuApi;
import com.jeedev.msdp.sys.quickmenu.service.IQuickMenuService;
import com.jeedev.msdp.trace.IProviderSign;
/**
 * 
 * @类名称 QuickMenuApiImpl.java
 * @类描述 <pre>快捷菜单业务组装层</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年10月12日 下午4:18:02
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年10月12日             
 *     ----------------------------------------------
 * </pre>
 */
@Service("quickMenuApi")
public class QuickMenuApiImpl implements QuickMenuApi, IProviderSign {

	@Autowired
	private IQuickMenuService quickMenuService;

	@Override
	public PageInfo<Map<String, Object>> findQuickMenuPage(Map<String, Object> params, PageParam pageparam) {

		return quickMenuService.findQuickMenuPage(params);
	}

	@Override
	public Map<String, Object> getQuickMenuMap(Map<String, Object> params) {

		return quickMenuService.getQuickMenuMap(params);
	}

	@Override
	public Map<String, Object> saveQuickMenuTrans(Map<String, Object> params) {

		return quickMenuService.saveQuickMenuTrans(params);
	}

	@Override
	public void deleteQuickMenuTrans(Map<String, Object> params) {

		quickMenuService.deleteQuickMenuTrans(params);
	}
}
