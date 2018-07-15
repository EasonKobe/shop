package com.jeedev.msdp.sys.dept.api;

import java.util.Date;

public class DeptApiConstants {
	public static enum FindDeptPageReqKey{
		id("id","主键ID",Integer.class),
		deptCode("deptCode","部门编号",String.class),
		deptName("deptName","部门名称",String.class),
		parentDeptCode("parentDeptCode","上一级部门编号",String.class),
		deptLevelCd("deptLevelCd","部门级别代码",String.class),
		sort("sort","排序",Integer.class),
		statusCd("statusCd","状态",String.class),
		delInd("delInd","是否删除数据",String.class),
		version("version","版本号",Integer.class),
		tenantId("tenantId","租户ID",String.class);
		private final String key;
		private final String desc;
		private final Class<?> dateType;
		FindDeptPageReqKey(String key,String desc,Class<?> dateType)
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
	public static enum FindDeptPageResKey{
		id("id","主键ID",Integer.class),
		deptCode("deptCode","部门编号",String.class),
		deptName("deptName","部门名称",String.class),
		parentDeptCode("parentDeptCode","上一级部门编号",String.class),
		deptLevelCd("deptLevelCd","部门级别代码",String.class),
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
		FindDeptPageResKey(String key,String desc,Class<?> dateType)
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
	
	public static enum GetDeptMapReqKey{
		id("id","主键ID",Integer.class),
		deptCode("deptCode","部门编号",String.class);
		private final String key;
		private final String desc;
		private final Class<?> dateType;
		GetDeptMapReqKey(String key,String desc,Class<?> dateType)
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
	public static enum GetDeptMapResKey{
		id("id","主键ID",Integer.class),
		deptCode("deptCode","部门编号",String.class),
		deptName("deptName","部门名称",String.class),
		parentDeptCode("parentDeptCode","上一级部门编号",String.class),
		deptLevelCd("deptLevelCd","部门级别代码",String.class),
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
		GetDeptMapResKey(String key,String desc,Class<?> dateType)
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
	public final static String ID="id";
	/**部门编号
	 */
	public final static String DEPT_CODE="deptCode";
	/**部门名称
	 */
	public final static String DEPT_NAME="deptName";
	/**上一级部门编号
	 */
	public final static String PARENT_DEPT_CODE="parentDeptCode";
	/**部门级别代码
	 */
	public final static String DEPT_LEVEL_CD="deptLevelCd";
	/**排序
	 */
	public final static String SORT="sort";
	/**状态
	 */
	public final static String STATUS_CD="statusCd";
	/**删除标识
	 */
	public final static String DEL_IND="delInd";
	/**租户ID
	 */
	public final static String TENANT_ID="tenantId";
	
}
