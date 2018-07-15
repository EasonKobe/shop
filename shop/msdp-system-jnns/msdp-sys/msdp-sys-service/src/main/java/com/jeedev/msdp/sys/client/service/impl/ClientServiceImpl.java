package com.jeedev.msdp.sys.client.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.sys.client.dao.SysClntendDao;
import com.jeedev.msdp.sys.client.service.IClientService;

/**
 * @类名称 ClientServiceImpl.java
 * @类描述 <pre></pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月22日 下午12:30:44
 * @版本 1.00
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月22日             
 *     ----------------------------------------------
 * </pre>
 */
@Service("clientService")
public class ClientServiceImpl implements IClientService{
	
	@Autowired
	private SysClntendDao sysClntendDao ;
	
	/**
	 * @方法名称 findByClntendId
	 * @功能描述 <pre>通过客户端编号获取客户端信息<</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月22日 下午12:24:15
	 * @param clntendId 客户端编号
	 * @return 客户端信息
	 */
	@Override
	public Map<String, Object> findByClntendId(String clntendId) {
		return this.sysClntendDao.findByClntendId(clntendId);
	}
	/**
	 * @方法名称 findEnabledList
	 * @功能描述 <pre>获取启用的客户端信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年8月22日 下午12:24:15
	 * @return 客户端信息
	 */
	@Override
	public List<Map<String, Object>> findEnabledList() {
		return this.sysClntendDao.findEnabledList();
	}
	@Override
	public PageInfo<Map<String, Object>> findClientPage(Map<String, Object> params, PageParam pageParam) {
		// TODO Auto-generated method stub
		List list = sysClntendDao.queryList(params);
		return new PageInfo<>(list);
	}

}
