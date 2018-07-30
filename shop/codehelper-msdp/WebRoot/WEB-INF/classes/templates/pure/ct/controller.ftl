package ${packageName}.web.controller;


import com.cloudtop.core.controller.BaseController;

@Controller
@RequestMapping("/${tableName}")
public class ${tableName}Controller extends BaseController {

	@Autowired
	private ${tableName}Service ${tableName?uncap_first}Service;
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