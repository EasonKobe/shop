package com.jeedev.msdp.spi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.jeedev.msdp.core.support.mybatis.dataauth.spi.RemoteDataAuthSpi;
import com.jeedev.msdp.sys.user.api.UserApi;

/**
 * 
 * @类名称 RemoteDataAuthSpiImpl.java
 * @类描述<pre>远程调用数据权限接口实现类</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月13日 上午9:39:47
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	yuyq            
 *     ----------------------------------------------
 *       </pre>
 */
public class RemoteDataAuthSpiImpl implements RemoteDataAuthSpi {
	
	/**
	 * 用户api
	 */
	@Autowired
	private UserApi userApi;
	
	/**
	 * @方法名称 queryDataAuthList
	 * @功能描述 <pre>数据权限远程模式参数传递控制</pre>
	 * @作者    yuyq
	 * @创建时间 2017年9月19日 下午3:57:44
	 * @param cond 上下问中存储的参数
	 * @param datauthModeId 数据权限定义的模块id
	 * @return 集合对象
	 */
	@Override
	public List<Object> queryDataAuthList(Map<String, Object> cond, String datauthModeId) {
		List<Object> list = new ArrayList<>();
		//根据创建者编码过滤
		if("ownerModel4Create".equals(datauthModeId)){
			Object currUsrId = cond.get("currUsrId");
			Map<String, Object> params=new HashMap<>();
			params.put("userNum", currUsrId);
			Map<String, Object> userMap=userApi.getUserMap(params);
			if(userMap!=null&&!userMap.isEmpty()){
				list.add(userMap.get("loginName"));
			}
		}
		return list;
	}

}
