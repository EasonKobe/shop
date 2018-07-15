package com.jeedev.msdp.sys.org.api;

import java.util.Date;

public class OrgApiConstants {
	public static enum FindOrgPageReqKey{
		id("id","主键ID",Integer.class),
		orgCode("orgCode","机构编号",String.class),
		orgName("orgName","机构名称",String.class),
		parentOrgCode("parentOrgCode","上一级机构编号",String.class),
		orgLevelCd("orgLevelCd","机构级别代码",String.class),
		statusCd("statusCd","状态",String.class),
		delInd("delInd","是否删除数据",String.class),
		version("version","版本号",Integer.class),
		tenantId("tenantId","租户ID",String.class);
		private final String key;
		private final String desc;
		private final Class<?> dateType;
		FindOrgPageReqKey(String key,String desc,Class<?> dateType)
		{
			this.key = key;
			this.desc = desc;
			this.dateType = dateType;
		}
		public String getKey() {
			return key;
		}
		public String getDesc() {
			return desc;
		}
		public Class<?> getDateType() {
			return dateType;
		}
	}
	public static enum FindOrgPageResKey{
		id("id","主键ID",Integer.class),
		orgCode("orgCode","机构编号",String.class),
		orgName("orgName","机构名称",String.class),
		parentOrgCode("parentOrgCode","上一级机构编号",String.class),
		orgLevelCd("orgLevelCd","机构级别代码",String.class),
		sort("sort","排序",Integer.class),
		extJsonData("extJsonData","扩展信息的json格式数据",String.class),
		statusCd("statusCd","状态",String.class),
		remark("remark","备注",String.class),
		delInd("delInd","是否删除数据",String.class),
		createUser("createUser","创建人",String.class),
		createTime("createTime","创建时间",Date.class),
		updateUser("updateUser","修改人",String.class),
		updateTime("updateTime","修改时间",Date.class),
		expdId("expdId","扩展 ID",String.class),
		version("version","版本号",Integer.class),
		tenantId("tenantId","租户ID",String.class);
		private final String key;
		private final String desc;
		private final Class<?> dateType;
		FindOrgPageResKey(String key,String desc,Class<?> dateType)
		{
			this.key = key;
			this.desc = desc;
			this.dateType = dateType;
		}
		public String getKey() {
			return key;
		}
		public String getDesc() {
			return desc;
		}
		public Class<?> getDateType() {
			return dateType;
		}
	}
	
	
	public static enum GetOrgMapReqKey{
		id("id","主键ID",Integer.class),
		orgCode("orgCode","机构编号",String.class);
		private final String key;
		private final String desc;
		private final Class<?> dateType;
		GetOrgMapReqKey(String key,String desc,Class<?> dateType)
		{
			this.key = key;
			this.desc = desc;
			this.dateType = dateType;
		}
		public String getKey() {
			return key;
		}
		public String getDesc() {
			return desc;
		}
		public Class<?> getDateType() {
			return dateType;
		}
	}
	public static enum GetOrgMapResKey{
		id("id","主键ID",Integer.class),
		orgCode("orgCode","机构编号",String.class),
		orgName("orgName","机构名称",String.class),
		parentOrgCode("parentOrgCode","上一级机构编号",String.class),
		orgLevelCd("orgLevelCd","机构级别代码",String.class),
		sort("sort","排序",Integer.class),
		extJsonData("extJsonData","扩展信息的json格式数据",String.class),
		statusCd("statusCd","状态",String.class),
		remark("remark","备注",String.class),
		delInd("delInd","是否删除数据",String.class),
		createUser("createUser","创建人",String.class),
		createTime("createTime","创建时间",Date.class),
		updateUser("updateUser","修改人",String.class),
		updateTime("updateTime","修改时间",Date.class),
		expdId("expdId","扩展 ID",String.class),
		version("version","版本号",Integer.class),
		tenantId("tenantId","租户ID",String.class);
		private final String key;
		private final String desc;
		private final Class<?> dateType;
		GetOrgMapResKey(String key,String desc,Class<?> dateType)
		{
			this.key = key;
			this.desc = desc;
			this.dateType = dateType;
		}
		public String getKey() {
			return key;
		}
		public String getDesc() {
			return desc;
		}
		public Class<?> getDateType() {
			return dateType;
		}
	}
	
	public static enum FindOrgDeptRelPageReqKey{
		id("id","主键ID",Integer.class),
		orgCode("orgCode","机构编号",String.class),
		deptCode("deptCode","部门编号",String.class),
		statusCd("statusCd","状态",String.class),
		delInd("delInd","是否删除数据",String.class),
		tenantId("tenantId","租户ID",String.class);
		private final String key;
		private final String desc;
		private final Class<?> dateType;
		FindOrgDeptRelPageReqKey(String key,String desc,Class<?> dateType)
		{
			this.key = key;
			this.desc = desc;
			this.dateType = dateType;
		}
		public String getKey() {
			return key;
		}
		public String getDesc() {
			return desc;
		}
		public Class<?> getDateType() {
			return dateType;
		}
	}
	public static enum FindOrgDeptRelPageResKey{
		id("id","主键ID",Integer.class),
		orgCode("orgCode","机构编号",String.class),
		deptCode("deptCode","部门编号",String.class),
		statusCd("statusCd","状态",String.class),
		remark("remark","备注",String.class),
		delInd("delInd","是否删除数据",String.class),
		createUser("createUser","创建人",String.class),
		createTime("createTime","创建时间",Date.class),
		updateUser("updateUser","修改人",String.class),
		updateTime("updateTime","修改时间",Date.class),
		expdId("expdId","扩展 ID",String.class),
		version("version","版本号",Integer.class),
		tenantId("tenantId","租户ID",String.class),
		
		orgName("orgName","机构名称",String.class);
		private final String key;
		private final String desc;
		private final Class<?> dateType;
		FindOrgDeptRelPageResKey(String key,String desc,Class<?> dateType)
		{
			this.key = key;
			this.desc = desc;
			this.dateType = dateType;
		}
		public String getKey() {
			return key;
		}
		public String getDesc() {
			return desc;
		}
		public Class<?> getDateType() {
			return dateType;
		}
	}
	
	/**
	 * 主键编号
	 */
	public static String Id = "id";
	/**
	 * 部门编号
	 */
	public static String DEPT_CODE = "deptCode";
	/**
	 * 机构编号
	 */
	public static String ORG_CODE = "orgCode";
	/**
	 * 机构名称
	 */
	public static String ORG_NAME = "orgName";
	/**
	 * 上级机构名称
	 */
	public static String PARENT_ORG_CODE = "parentOrgCode";
	/**
	 * 删除状态
	 */
	public final static String DEL_IND = "delInd";
}
