package com.jeedev.msdp.core.support.mybatis.dataauth.aspect.impl;

import java.util.HashMap;
import java.util.Map;

import org.aopalliance.intercept.MethodInvocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.jeedev.msdp.core.support.mybatis.dataauth.aspect.DataAuthStartInterceptor;
import com.jeedev.msdp.trace.constants.HeadConstants;
import com.jeedev.msdp.trace.utils.HeadUtil;
import com.jeedev.msdp.utlis.StringUtil;

/**
 * 
 * @类名称 MsdpDataAuthMethodInterceptorV1Impl.java
 * @类描述 <pre>msdp v1的默认实现， 从第一个参数中获取head，再从head获取user，以user作为startMap</pre>
 * @作者 chenjiancong chenjiancong@tansun.com.cn
 * @创建时间 2017年8月21日 上午9:57:36
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenjiancong 	2017年8月21日             
 *     ----------------------------------------------
 *     1.01 	chenjiancong 	2017年11月02日             如果请求头带有不需要数据权限的标识，则start 返回空
 *     ----------------------------------------------
 * </pre>
 */
public class MsdpDataAuthMethodInterceptorV1Impl extends
		DataAuthStartInterceptor {
	private Logger logger = LoggerFactory.getLogger(MsdpDataAuthMethodInterceptorV1Impl.class);
	@Override
	public Map<String, Object> getStartMap(MethodInvocation invocation) {
		Map<String,Object> user = new HashMap<String,Object>();
		Map<String, Object> map = HeadUtil.getCurUser();
		if(map==null||map.isEmpty()) {
			
			logger.debug("找不到用户数据，不开启数据权限过滤功能");
			return null;
		}
		user.putAll(map);
		user.put(HeadConstants.HEAD_SCENE, HeadUtil.getScene());
		if("MsdpTranslate".equals(HeadUtil.getScene())) {
			logger.debug("翻译,不开启数据权限过滤功能");
			return null;
		}
		//API调用深度
		String depth = (String) HeadUtil.getHeadParam(HeadConstants.Head_API_DEPTH);
		if(StringUtil.isNotBlank(depth)
				&&Integer.parseInt(depth)>1) {
			logger.debug("api深度["+depth+"]大于1，不开启数据权限过滤功能");
			return null;
		}
		return user;
	}

	

}
