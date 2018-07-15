package com.jeedev.msdp.sys.dict.api;

import java.util.Map;

import com.alibaba.dubbo.rpc.RpcException;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;

/**
 * 数据字典接口
 * @类名称 DictApi
 * @类描述 <pre> 数据字典接口 </pre>
 * @作者 chenld
 * @创建时间 2016年10月28日
 * @版本 v1.00
 * @修改记录 <pre>
 * 版本     		修改人 	修改时间    	 	修改内容	描述
 * ----------------------------------------------
 * 1.00 	chenld     	2016年10月28日 	新建
 * ----------------------------------------------
 * </pre>
 */
public interface DictApi {
    /**
     * 
     * @方法名称 findDdctPage
     * @功能描述 <pre>获取所有的数据字典列表</pre>
     * @作者    Colin.DZX
     * @创建时间 2017年9月1日 下午3:30:56
     * @param params dctTpCd：数据字典类型<br>
     *               dctGrp：字典组
     * @return dctKey：字典键<br>
     *          dctVal：字典值<br>
     *          dctValNm：字典值名称<br>
     *          dctTpCd：字典类型<br>
     *          dctTpNm：字典类型名称<br>
     *          dctGrp：字典组<br>
     *          dctDsc：字典描述<br>
     *          dctSeq：字典排序<br>
     *          stCd：状态（无效0\有效1）<br>
     *          id：主键
     */
    PageInfo<Map<String, String>> findDictPage(Map<String, String> params,PageParam pageParam);

    /**
     * 根据ID查询数据字典
     * @方法名称 getDictMap
     * @功能描述 <pre>根据ID查询数据字典</pre>
     * @作者    yangkunwei
     * @创建时间 2016年11月16日 下午6:00:06
     * @param 
     * @return dctKey：字典键<br>
     *          dctVal：字典值<br>
     *          dctValNm：字典值名称<br>
     *          dctTpCd：字典类型<br>
     *          dctTpNm：字典类型名称<br>
     *          dctGrp：字典组<br>
     *          dctDsc：字典描述<br>
     *          dctSeq：字典排序<br>
     *          stCd：状态（无效0\有效1）
     * @throws RpcException RpcException
     */
    Map<String,String> getDictMap(Map<String, String> params);

    /**
     * 添加数据字典
     * @方法名称 saveDictTrans
     * @功能描述 <pre> 添加数据字典 </pre>
     * @作者    chenld
     * @创建时间 2016/10/28 16:03
     * @param params  dctKey：字典键，必填<br>
     *          dctVal：字典值<br>
     *          dctValNm：字典值名称<br>
     *          dctTpCd：字典类型，必填<br>
     *          dctTpNm：字典类型名称<br>
     *          dctGrp：字典组<br>
     *          dctDsc：字典描述<br>
     *          dctSeq：字典排序<br>
     *          stCd：状态（无效0\有效1）<br>
     *          operator：操作人员
     * @throws RpcException RpcException
     */
    void saveDictTrans(Map<String, String> params) throws Exception;

    /**
     * 删除数据字典
     * @方法名称 deleteDictTrans
     * @功能描述 <pre> 删除数据字典 </pre>
     * @作者    chenld
     * @创建时间 2016/10/28 17:12
     * @param id 数据字典主键
     * @param operator 操作人员
     * @throws RpcException RpcException
     */
    void deleteDictTrans(Map<String, String> params) throws Exception;

    /**
     * 修改数据字典信息
     * @方法名称 updateDictTrans
     * @功能描述 <pre> 修改数据字典信息 </pre>
     * @作者    chenld
     * @创建时间 2016/10/28 17:10
     * @param params dctKey：字典键，必填<br>
     *          dctVal：字典值<br>
     *          dctValNm：字典值名称<br>
     *          dctTpCd：字典类型，必填<br>
     *          dctTpNm：字典类型名称<br>
     *          dctGrp：字典组<br>
     *          dctDsc：字典描述<br>
     *          dctSeq：字典排序<br>
     *          stCd：状态（无效0\有效1）<br>
     *          operator：操作人员<br>
     *          id：数据字典主键，必填
     * @throws RpcException RpcException
     */
    void updateDictTrans(Map<String, String> params) throws Exception;
   
}
