package com.jeedev.msdp.sys.param.service;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.BizException;

public interface ISysParamService {
    /**
	 * 分页查询系统参数列表
     * @方法名称 findParams
     * @功能描述 <pre> 查询系统参数列表 </pre>
     * @作者    chenld
     * @创建时间 2016/11/5 16:13
	 * @param params parmTpCd：参数类型
	 * @param params pageNum：页码
	 * @param params pageSize：条数
     * @return parmId：参数编号<br>
     *          parmDsc：参数描述<br>
     *          parmTpCd：参数类型<br>
     *          parmTpNm：参数类型名称<br>
     *          parval：参数值
     * @throws BizException BizException
     */
    PageInfo<Map<String, String>> findParamPage(Map<String, String> params,PageParam pageParam);
    /**
	 * 更新系统参数
     * @方法名称 updateParam
     * @功能描述 <pre> 更新系统参数 </pre>
     * @作者    chenld
     * @创建时间 2016/11/5 16:13
     * @param params parmId：参数编号<br>
     *               parval：参数值<br>
     *               operator：操作人员
     * @throws BizException BizException
     */
    void updateParam(Map<String, String> params) throws Exception;
    /**
     * @方法名称 saveParam
     * @功能描述 <pre>保存系统参数</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年9月18日 下午5:32:02
     * @param params
     * @throws Exception
     */
    void saveParam(Map<String, String> params) throws Exception;

    /**
     * @方法名称 deleteParam
     * @功能描述 <pre>删除系统参数</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年9月18日 下午5:32:16
     * @param params
     * @throws Exception
     */
	void deleteParam(Map<String, String> params) throws Exception;

	/** 
	 * @方法名称 getParamMap
	 * @功能描述 <pre>获取系统参数详细信息</pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月18日 下午5:32:28
	 * @param params
	 * @return
	 */
	Map<String, String> getParamMap(Map<String, String> params);
}
