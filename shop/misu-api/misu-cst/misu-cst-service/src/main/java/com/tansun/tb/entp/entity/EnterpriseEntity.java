package com.tansun.tb.entp.entity;

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
 *
 * @类名称 EnterpriseEntity.java
 * @类描述 <pre>企业客户信息，用户机构树</pre>
 * @作者 赵健 zhaojianxm@tansun.com.cn
 * @创建时间 2018年01月24日 PM 02:19
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	赵健 	2018年01月24日
 *     1.00 	赵健 	2018年03月05日    添加资金池模式字段
 *     ----------------------------------------------
 * </pre>
 */
@Entity
@Table(name = "BIZ_ENTP_INF")
@SequenceGenerator(name="seq",allocationSize=1,initialValue=1,sequenceName="SEQ_BIZ_ENTP_INF")
public class EnterpriseEntity extends BaseEntity<Integer> implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 客户编号
     */
    @Column(name = "ENTP_CODE")
    private String entpCode;
    /**
     * 客户名称
     */
    @Column(name = "ENTP_NM")
    private String entpNm;
    /**
     * 机构号
     */
    @Column(name = "ENTP_ORG_CODE")
    private String entpOrgCode;
    /**
     *  机构管理级别(一级1\二级2、三级3\四级4\五级5)
     */
    @Column(name = "ENTP_ORG_LVL_CD")
    private String entpOrgLvlCd;
    /**
     *  证件类型(机构代码1\营业执照号2\其他3)
     */
    @Column(name = "ENTP_CRDT_TP_CD")
    private String entpCrdtTpCd;
    /**
     * 证件号码
     */
    @Column(name = "ENTP_CRDT_NO")
    private String entpCrdtNo;
    /**
     * EMAIL地址
     */
    @Column(name = "ENTP_EMAIL_ADR")
    private String entpEmailAdr;
    /**
     * 传真
     */
    @Column(name = "ENTP_FAX_NO")
    private String entpFaxNo;
    /**
     * 联系人
     */
    @Column(name = "ENTP_CTCPSN")
    private String entpCtcpsn;
    /**
     * 联系电话
     */
    @Column(name = "ENTP_CTCPSN_TEL")
    private String entpCtcpsnTel;
    /**
     * 注册地址
     */
    @Column(name = "ENTP_RGST_ADR")
    private String entpRgstAdr;
    /**
     *  是否资金池主办单位(否0/是1)
     */
    @Column(name = "WTHR_CPTPL_HOST_ENTP")
    private String wthrCptplHostEntp;
    /**
     *  资金池模式(母实子实0/母实子虚1/母虚子实2)
     */
    @Column(name = "CPTPL_MODEL")
    private String cptplModel;
    /**
     *  是否已签约(否0/是1)
     */
    @Column(name = "WTHR_SIGN")
    private String wthrSign;
    /**
     * 上级客户编号
     */
    @Column(name = "PARENT_ENTP_CODE")
    private String parentEntpCode;

    public String getEntpCode() {
        return entpCode;
    }

    public void setEntpCode(String entpCode) {
        this.entpCode = entpCode;
    }

    public String getEntpNm() {
        return entpNm;
    }

    public void setEntpNm(String entpNm) {
        this.entpNm = entpNm;
    }

    public String getEntpOrgCode() {
        return entpOrgCode;
    }

    public void setEntpOrgCode(String entpOrgCode) {
        this.entpOrgCode = entpOrgCode;
    }

    public String getEntpOrgLvlCd() {
        return entpOrgLvlCd;
    }

    public void setEntpOrgLvlCd(String entpOrgLvlCd) {
        this.entpOrgLvlCd = entpOrgLvlCd;
    }

    public String getEntpCrdtTpCd() {
        return entpCrdtTpCd;
    }

    public void setEntpCrdtTpCd(String entpCrdtTpCd) {
        this.entpCrdtTpCd = entpCrdtTpCd;
    }

    public String getEntpCrdtNo() {
        return entpCrdtNo;
    }

    public void setEntpCrdtNo(String entpCrdtNo) {
        this.entpCrdtNo = entpCrdtNo;
    }

    public String getEntpEmailAdr() {
        return entpEmailAdr;
    }

    public void setEntpEmailAdr(String entpEmailAdr) {
        this.entpEmailAdr = entpEmailAdr;
    }

    public String getEntpFaxNo() {
        return entpFaxNo;
    }

    public void setEntpFaxNo(String entpFaxNo) {
        this.entpFaxNo = entpFaxNo;
    }

    public String getEntpCtcpsn() {
        return entpCtcpsn;
    }

    public void setEntpCtcpsn(String entpCtcpsn) {
        this.entpCtcpsn = entpCtcpsn;
    }

    public String getEntpCtcpsnTel() {
        return entpCtcpsnTel;
    }

    public void setEntpCtcpsnTel(String entpCtcpsnTel) {
        this.entpCtcpsnTel = entpCtcpsnTel;
    }

    public String getEntpRgstAdr() {
        return entpRgstAdr;
    }

    public void setEntpRgstAdr(String entpRgstAdr) {
        this.entpRgstAdr = entpRgstAdr;
    }

    public String getWthrCptplHostEntp() {
        return wthrCptplHostEntp;
    }

    public void setWthrCptplHostEntp(String wthrCptplHostEntp) {
        this.wthrCptplHostEntp = wthrCptplHostEntp;
    }

    public String getWthrSign() {
        return wthrSign;
    }

    public void setWthrSign(String wthrSign) {
        this.wthrSign = wthrSign;
    }

    public String getParentEntpCode() {
        return parentEntpCode;
    }

    public void setParentEntpCode(String parentEntpCode) {
        this.parentEntpCode = parentEntpCode;
    }

    public String getCptplModel() {
        return cptplModel;
    }

    public void setCptplModel(String cptplModel) {
        this.cptplModel = cptplModel;
    }
}