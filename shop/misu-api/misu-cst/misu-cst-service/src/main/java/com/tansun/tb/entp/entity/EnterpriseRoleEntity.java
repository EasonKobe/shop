package com.tansun.tb.entp.entity;

import com.jeedev.msdp.core.data.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;

/**
 *
 * @类名称 EnterpriseRoleEntity.java
 * @类描述 <pre>企业客户角色</pre>
 * @作者 赵健 zhaojianxm@tansun.com.cn
 * @创建时间 2018年01月24日 PM 02:19
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	赵健 	2018年01月24日
 *     ----------------------------------------------
 * </pre>
 */
@Entity
@Table(name = "BIZ_ENTP_ROLE")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_BIZ_ENTP_ROLE")
public class EnterpriseRoleEntity extends BaseEntity<Integer> implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 客户编号
     */
    @Column(name = "ENTP_CODE")
    private String entpCode;
    /**
     * 角色编号
     */
    @Column(name = "ROLE_CODE")
    private String roleCode;

    public String getEntpCode() {
        return entpCode;
    }

    public void setEntpCode(String entpCode) {
        this.entpCode = entpCode;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}