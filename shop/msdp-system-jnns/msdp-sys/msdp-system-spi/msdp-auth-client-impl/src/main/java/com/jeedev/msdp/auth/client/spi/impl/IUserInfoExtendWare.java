package com.jeedev.msdp.auth.client.spi.impl;

import java.util.Map;

/**
 * 用户衍生接口
 * @类名称 IUserInfoExtend.java
 * @类描述 <pre></pre>
 * @作者 hqhangyu hqhangyu@tansun.com.cn
 * @创建时间 2018年3月9日 下午4:49:00
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	hqhangyu 	2018年3月9日             
 *     ----------------------------------------------
 * </pre>
 */
public interface IUserInfoExtendWare {
	
	/**
	 * 扩展接口
	 * @方法名称 extendUserInfo
	 * @功能描述 <pre></pre>
	 * @作者    hqhangyu
	 * @创建时间 2018年3月9日 下午4:50:30
	 * @param user
	 */
	public void extendUserInfo(Map<String,Object> user);

}
