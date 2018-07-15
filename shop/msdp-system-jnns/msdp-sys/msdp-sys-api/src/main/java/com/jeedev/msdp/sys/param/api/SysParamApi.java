package com.jeedev.msdp.sys.param.api;

import java.util.Map;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.BizException;

/**
 * 系统参数接口
 * @类名称 ParamApi
 * @类描述 <pre> 系统参数接口 </pre>
 * @作者 chenld
 * @创建时间 2016年11月05日
 * @版本 v1.00
 * @修改记录 <pre>
 * 版本     		修改人 	修改时间    	 	修改内容	描述
 * ----------------------------------------------
 * 1.00 	chenld     	2016年11月05日 	新建
 * ----------------------------------------------
 * </pre>
 */
public interface SysParamApi {
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
    PageInfo<Map<String, String>> findParamPage(Map<String, String> params, PageParam pageParam);
    /**
     * @方法名称 getParamMap
     * @功能描述 <pre>根据编号获取参数详情</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年9月18日 下午2:27:09
     * @param params
     * @return
     * id:主键编号
     * parmTpCd:参数类型
     * parmTpNm: 参数类型名称
     * parmId: 参数编号
     * parval: 参数值
     * parmDsc: 参数描述
     * wthrCchCd: 是否缓存（否0\是1）
     */
    Map<String, String> getParamMap(Map<String, String> params);
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
    void updateParamTrans(Map<String, String> params) throws Exception;
    /**
     * @方法名称 saveParamTrans
     * @功能描述 <pre>新增系统参数</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年9月18日 下午2:27:32
     * @param params
     * parmTpCd:参数类型
     * parmTpNm: 参数类型名称
     * parmId: 参数编号
     * parval: 参数值
     * parmDsc: 参数描述
     * wthrCchCd: 是否缓存（否0\是1）
     * @throws Exception
     */
    void saveParamTrans(Map<String, String> params) throws Exception;
    /**
     * @方法名称 deleteParamTrans
     * @功能描述 <pre>根据编号ID删除系统参数</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年9月18日 下午2:27:51
     * @param params
     * id 主键编号
     * parmId 参数编号
     * @throws Exception
     */
	void deleteParamTrans(Map<String, String> params) throws Exception;

	

}
