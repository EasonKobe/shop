package ${packageName}.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import com.sinoservices.pureui.controllers.JSONActionSupport;
import com.sinoservices.pureui.utils.MessageUtils;
import ${packageName}.services.${tableName}Manager;
import ${packageName}.view.${tableName}QueryConditionView;
import com.sinoservices.xframework.core.support.Pagination;

@Controller
@Scope(value = "prototype")
public class ${tableName}Action extends JSONActionSupport {
	/**********传递参数*/
	/** 查询条件*/private ${tableName}QueryConditionView condition;
	/** 删除id*/private Long id;
	
	/**********注入*/
	/** $!{data.table.comments}单Manager注入*/
	@Autowired
	private ${tableName}Manager ${tableName?uncap_first}Manager;
	/**********返回*/
	private static String PAGE="page";
	private static String SUCCESS="success";

	/** 列表 */
	public String page(){
		Pagination page=${tableName?uncap_first}Manager.getPageByCondition(condition, condition.buildBounds());
		jsonMessage=MessageUtils.createPageMsg(page);
		return PAGE;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public ${tableName}QueryConditionView getCondition() {
		return condition;
	}

	public void setCondition(${tableName}QueryConditionView condition) {
		this.condition = condition;
	}
}