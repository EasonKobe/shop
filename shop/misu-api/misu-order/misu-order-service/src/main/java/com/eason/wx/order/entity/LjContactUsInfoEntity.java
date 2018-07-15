
package com.eason.wx.order.entity;

import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.jeedev.msdp.core.data.BaseEntity;
import com.jeedev.msdp.utlis.MapUtil;

/**
 * 联系我们信息表实体类
 *
 * @类名称 LjContactUsInfo
 * @类描述 <pre> 联系我们信息表实体类</pre>
 * @作者 chenyuxin
 * @创建时间 2018年03月14日
 * @版本 v1.00
 * @修改记录 <pre>
 * 版本     		修改人 	修改时间    	 	修改内容	描述
 * ----------------------------------------------
 * 1.00 	chenyuxin     	2018年03月14日 	新建
 * ----------------------------------------------
 * </pre>
 */
@Entity
@Table(name = "LJ_CONTACT_US_INFO")
public class LjContactUsInfoEntity extends BaseEntity<Integer>{

	private static final long serialVersionUID = 1L;

 	/**  
 	 *公司品牌 
 	 */
	@Column(name = "BRAND")
	private String brand;

 	/**  
 	 *邮箱 
 	 */
	@Column(name = "EMAIL")
	private String email;

 	/**  
 	 *联系方式 
 	 */
	@Column(name = "TELEPHONE")
	private String telephone;

 	/**  
 	 *姓 
 	 */
	@Column(name = "FIRST_NAME")
	private String firstName;

 	/**  
 	 *名 
 	 */
	@Column(name = "LAST_NAME")
	private String lastName;

 	/**  
 	 *留言内容 
 	 */
	@Column(name = "MESSAGE")
	private String message;



	public String getBrand(){
		return brand;
	}

	public void setBrand(String brand){
		this.brand = brand;
	}

	public String getEmail(){
		return email;
	}

	public void setEmail(String email){
		this.email = email;
	}

	public String getTelephone(){
		return telephone;
	}

	public void setTelephone(String telephone){
		this.telephone = telephone;
	}

	public String getFirstName(){
		return firstName;
	}

	public void setFirstName(String firstName){
		this.firstName = firstName;
	}

	public String getLastName(){
		return lastName;
	}

	public void setLastName(String lastName){
		this.lastName = lastName;
	}

	public String getMessage(){
		return message;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public LjContactUsInfoEntity coverToEntity(Map<String,Object> params) {
		if(params.containsKey("brand")){
			this.brand = MapUtil.getString (params,"brand");
		}
		if(params.containsKey("email")){
			this.email = MapUtil.getString (params,"email");
		}
		if(params.containsKey("telephone")){
			this.telephone = MapUtil.getString (params,"telephone");
		}
		if(params.containsKey("firstName")){
			this.firstName = MapUtil.getString (params,"firstName");
		}
		if(params.containsKey("lastName")){
			this.lastName = MapUtil.getString (params,"lastName");
		}
		if(params.containsKey("message")){
			this.message = MapUtil.getString (params,"message");
		}
		if(params.containsKey("id")){
			this.id = MapUtil.getInteger(params, "id");
		}
		if(params.containsKey("delInd")){
			this.delInd = MapUtil.getString(params, "delInd");
		}
		return this;
	}
	
}
