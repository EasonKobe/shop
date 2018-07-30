<#assign editFields = page.resource.editFields>
<#macro  buildField>
	<#list editFields as field>
	 *         ${field.fieldName}：${field.comment}<br>
	</#list>
</#macro>
package ${packageName}.service;

import com.jeedev.msdp.standard.exception.BizException;

import java.util.List;
import java.util.Map;

/**
 * 【实例】对内接口
 *
 * @类名称 ${tableName}Service
 * @类描述 <pre> 【实例】对外接口</pre>
 * @作者 ${author}
 * @创建时间 ${nowDateZh}
 * @版本 v1.00
 * @修改记录 <pre>
 * 版本     		修改人 	修改时间    	 	修改内容	描述
 * ----------------------------------------------
 * 1.00 	${author}     	${nowDateZh} 	新建
 * ----------------------------------------------
 * </pre>
 */
public interface I${tableName}Service {

	/**
	 * 查询【实例】列表
	 * @方法名称 findDemoList
	 * @功能描述 <pre> 查询【实例】列表 </pre>
	 * @作者    ${author}
	 * @创建时间 ${nowDate}
	 * @param params 参数<br>
	 * @return
	 <@buildField/>
	 * @throws RpcException RpcException
	 */
	List<Map<String, Object>> findDemoList(Map<String, Object> params) throws BizException;

	/**
	 * 查询【实例】
	 * @方法名称 findDemo
	 * @功能描述 <pre> 查询【实例】 </pre>
	 * @作者    ${author}
	 * @创建时间 ${nowDate}
	 * @param params 参数<br>
	 * @return
	 <@buildField/>
	 * @throws RpcException RpcException
	 */
	Map<String, Object> getDemo(Map<String, Object> params) throws BizException;

	/**
	 * 新增/更新【实例】
	 * @方法名称 saveOrUpdateDemo
	 * @功能描述 <pre> 新增【实例】 </pre>
	 * @作者    ${author}
	 * @创建时间 ${nowDate}
	 * @param params 参数<br>
	 *			operator：操作用户<br>
	 * @return
	 <@buildField/>
	 * @throws RpcException RpcException
	 */
	Map<String, Object> saveOrUpdateDemo(Map<String, Object> params) throws BizException;

	/**
	 * 删除【实例】
	 * @方法名称 deleteDemo
	 * @功能描述 <pre> 删除【实例】 </pre>
	 * @作者    ${author}
	 * @创建时间 ${nowDate}
	 * @param params 参数<br>
	 *          demoId：【实例】编号<br>
	 *          operator：操作用户
	 * @throws RpcException RpcException
	 */
	void deleteDemo(Map<String,Object> params) throws BizException;
}
