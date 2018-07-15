package com.jeedev.msdp.sys.event.entity;

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
import com.jeedev.msdp.sys.event.entity.SysResEventEntity;

/**
 * 事件资源实体 
 * @类名称 SysResEventEntity.java
 * @类描述 <pre></pre>
 * @作者 chenjc chenjc@tansun.com.cn
 * @创建时间 2017年11月7日 下午4:07:15
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	chenjc 	2017年11月7日             
 *     ----------------------------------------------
 * </pre>
 */
@Entity
@Table(name = "SYS_RES_EVENT")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_SYS_RES_EVENT")
public class SysResEventEntity extends BaseEntity<Integer> implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5642710386704017701L;
	/**
	 * 事件编码
	 */
	@Column(name = "EVENT_CODE")
	private String eventCode;
	/**
	 * 事件URL
	 */
	@Column(name = "URL")
	private String url;
	
	/**
	 * 事件名称
	 */
	@Column(name = "EVENT_NAME")
	private String eventName;
	
	/**
	 * 状态：无效0有效1
	 */
	@Column(name = "STATUS_CD")
	private String statusCd;

	/**
	 * 备注
	 */
	@Column(name = "REMARK")
	private String remark;
	
	/**
	 * 客户端编号
	 */
	@Column(name = "CLNTEND_ID")
	private String clntendId;
	
	public SysResEventEntity coverToEntity(Map<String, Object> params, SysResEventEntity entity) {
		if (entity == null) {
			entity = new SysResEventEntity();
		}
		try {
			String eventCode = MapUtils.getString(params, "eventCode");
			if (!StringUtils.isEmpty(eventCode)) {
				entity.setEventCode(eventCode);
			}
			String eventName = MapUtils.getString(params, "eventName");
			if (!StringUtils.isEmpty(eventName)) {
				entity.setEventName(eventName);
			}
			String url = MapUtils.getString(params, "url");
			if (!StringUtils.isEmpty(url)) {
				entity.setUrl(url);
			}
			String statusCd = MapUtils.getString(params, "statusCd");
			if (!StringUtils.isEmpty(statusCd)) {
				entity.setStatusCd(statusCd);
			}
			String remark = MapUtils.getString(params, "remark");
			if (!StringUtils.isEmpty(remark)) {
				entity.setRemark(remark);
			}
			 if (params.containsKey("clntendId")) {
				   String clntendId= MapUtils.getString(params, "clntendId");//客户端ID
					entity.setClntendId(clntendId);
			 }

			// BaseEntity//BaseEntity
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
			throw ExceptionFactory.getBizException("sys-00001",e, "SysResEventEntity");
		}
	}

	public String getEventCode() {
		return eventCode;
	}

	public void setEventCode(String eventCode) {
		this.eventCode = eventCode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getEventName() {
		return eventName;
	}

	public void setEventName(String eventName) {
		this.eventName = eventName;
	}

	public String getStatusCd() {
		return statusCd;
	}

	public void setStatusCd(String statusCd) {
		this.statusCd = statusCd;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getClntendId() {
		return clntendId;
	}

	public void setClntendId(String clntendId) {
		this.clntendId = clntendId;
	}
	
}
