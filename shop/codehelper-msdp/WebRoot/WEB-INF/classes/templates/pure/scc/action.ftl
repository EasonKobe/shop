package ${packageName}.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.sinoservices.pureui.controllers.JSONActionSupport;
import com.sinoservices.pureui.utils.MessageUtils;
import ${packageName}.services.${tableName}Manager;
import ${packageName}.model.${tableName}Model;
import com.sinoservices.xframework.core.support.Pagination;

@Controller
@Scope(value = "prototype")
public class ${tableName}Action extends JSONActionSupport {
	/**********传递参数*/
	/** 对象*/private ${tableName}Model model;
	/** 删除id*/private Long id;
	
	/**********注入*/
	@Autowired
	private ${tableName}Manager ${tableName?uncap_first}Manager;
	/**********返回*/
	private static String PAGE="page";
	private static String SUCCESS="success";

	/** 列表 */
	public String page(){
		Pagination page=${tableName?uncap_first}Manager.getPageByCondition(model, model.buildBounds());
		jsonMessage=MessageUtils.createPageMsg(page);
		return PAGE;
	}
	/** 新增 */
	public String insert(){
		${tableName?uncap_first}Manager.insert(model);
		return SUCCESS;
	}
	/** 修改 */
	public String update(){
		${tableName?uncap_first}Manager.update(model);
		return SUCCESS;
	}
	/** 删除 */
	public String delete(){
		${tableName?uncap_first}Manager.delete(id);
		return SUCCESS;
	}
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public ${tableName}Model getModel(){
		return model;
	}
	
	public void setModel(${tableName}Model model){
		this.model = model;
	}
}