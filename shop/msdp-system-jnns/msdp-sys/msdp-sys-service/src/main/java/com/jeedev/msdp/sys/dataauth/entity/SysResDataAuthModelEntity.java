package com.jeedev.msdp.sys.dataauth.entity;

import java.io.Serializable;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.apache.commons.collections.MapUtils;
import org.springframework.util.StringUtils;

import com.jeedev.msdp.core.data.BaseEntity;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
/**
 * @类名称 SysDataAuthEntity.java
 * @类描述 <pre>数据权限类型表</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年9月26日 下午4:48:06
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yuyq 	yuyq            
 *     ----------------------------------------------
 * </pre>
 */
@Entity
@Table(name = "SYS_RES_DATA_AUTH_MODEL")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_RES_DATA_AUTH_MODEL")
public class SysResDataAuthModelEntity extends BaseEntity<Integer> implements Serializable {
	private static final long serialVersionUID = 1L;


	/**
	 * 数据权限类型编码
	 */
	@Column(name = "MODEL_CODE")
	private String modelCode;

	/**
	 * 数据权限类型
	 */
	@Column(name = "MODEL_ID")
	private String modelId;
	
	/**
	 * 数据权限类型名称
	 */
	@Column(name = "MODEL_NAME")
	private String modelName;
	
	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelCode() {
		return modelCode;
	}

	public void setModelCode(String modelCode) {
		this.modelCode = modelCode;
	}

	public String getModelId() {
		return modelId;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public SysResDataAuthModelEntity coverToEntity(Map<String, Object> params, SysResDataAuthModelEntity entity) {
		if (entity == null) {
			entity = new SysResDataAuthModelEntity();
		}
		try {
			if (params.containsKey("modelCode")) {
				String modelCode = MapUtils.getString(params, "modelCode");
				entity.setModelCode(modelCode);
			}
			if (params.containsKey("modelId")) {
				String modelId = MapUtils.getString(params, "modelId");
				entity.setModelId(modelId);
			}
			if (params.containsKey("modelName")) {
				String modelName = MapUtils.getString(params, "modelName");
				entity.setModelId(modelName);
			}
			
			if (params.containsKey("remark")) {
				String remark = MapUtils.getString(params, "remark");
				entity.setRemark(remark);
			}

			// BaseEntity
			// BaseEntity
			Integer id = MapUtils.getInteger(params, "id");
			if (id != null) {
				entity.setId(id);
			}
			String delInd = MapUtils.getString(params, "delInd");
			if (!StringUtils.isEmpty(delInd)) {
				entity.setDelInd(delInd);
			}
			return entity;
		} catch (Exception e) {
			throw ExceptionFactory.getBizException("sys-00001", "1");
		}
	}

}