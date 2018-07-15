package com.jeedev.msdp.auth.client.spi.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.jeedev.msdp.auth.client.spi.AuthUserSpi;
import com.jeedev.msdp.standard.log.CommonLoggerFactory;
import com.jeedev.msdp.standard.log.Logger;
import com.jeedev.msdp.sys.dept.api.DeptApi;
import com.jeedev.msdp.sys.org.api.OrgApi;
import com.jeedev.msdp.sys.role.api.RoleApi;
import com.jeedev.msdp.sys.tenant.api.TenantApi;
import com.jeedev.msdp.sys.tenant.api.TenantConstants;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.trace.constants.LoginUserConstants;
import com.jeedev.msdp.trace.utils.HeadUtil;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;

@Service("client.authUser")
public class AuthUserImpl implements AuthUserSpi {
	private static Logger logger = CommonLoggerFactory.getLogger(AuthUserImpl.class);
	@Autowired
	private UserApi userApi;
	@Autowired
	private OrgApi orgApi;
	@Autowired
	private DeptApi deptApi;
	@Autowired
	private RoleApi roleApi;
	
	@Autowired(required=false)
	private List<IUserInfoExtendWare> userInfoExtends;
	
	@Autowired
	private TenantApi tenantApi;
	
	private static String DEFAULTIND_YES="1";
	public Map<String, Object> findByUsrNm(String usrNm) {
		Map<String, Object> queryMap=new  HashMap<>();
		queryMap.put(UserConstants.LOGIN_NAME, usrNm); 
		return userApi.getUserMap(queryMap);
	}

	@Override
	public void updateUserStatus(String loginName,String status,String operator) { 
		Map<String, Object> queryMap=new  HashMap<>();
		Map<String, Object> resultMap =this.findByUsrNm(loginName);//获取用户编码
		queryMap.put(UserConstants.USER_NUM, MapUtils.getString(resultMap, UserConstants.USER_NUM));
		queryMap.put(UserConstants.STATUS_CD, status);
		queryMap.put(UserConstants.OPERATOR, operator);
		userApi.updateUserTrans(queryMap);
	}

	@Override
	public boolean extendUserInfo(Map<String, Object> userInfo) {
		Map<String,Object> queryCond = new HashMap<String,Object>();
		String userId = userInfo.containsKey("userNum")?(String) userInfo.get("userNum"):(String) userInfo.get("userNum".toUpperCase());
		userInfo.put(LoginUserConstants.LOGIN_USER_USRID, userId);
		userInfo.remove("password");
		userInfo.remove("salt");
		userInfo.remove("createTime");
		userInfo.remove("version");
		logger.trace("加载用户["+userId+"]机构信息");
		queryCond.put("userNum", userId);
		
		if (userInfoExtends != null) {
			for (int i = 0; i < userInfoExtends.size(); i++) {
				IUserInfoExtendWare iUserInfoExtendWare = userInfoExtends.get(i);
				iUserInfoExtendWare.extendUserInfo(userInfo);
			}
			
		}
		//2.初始化head到线程上下文中
		HeadUtil.initHead2ThreadContext(userInfo);
//		Map<String,Object> userInst = usrInstRelApi.findUsrInsRel(queryCond);
		
		PageInfo<Map<String, Object>> userOrgInfs  = userApi.findUserOrgRelPage(queryCond, null);
		Map<String, Object> instInfo = new HashMap<String,Object>();
		if(userOrgInfs!=null && userOrgInfs.getSize()>0){
			instInfo = userOrgInfs.getList().get(0);
//			orgCode = (String) userOrgInfs.getList().get(0).get("orgCode");
			for(Map<String, Object> userOrgInf:userOrgInfs.getList()){
				if(DEFAULTIND_YES.equals(userOrgInf.get("defaultInd"))){
					instInfo = userOrgInf;
				}
			}
			if(instInfo!=null){
				//机构信息 id:主键ID;instCd:机构代码;instNm:机构名称;suprInstCd:上级机构代码; instLvl:机构级别;seq:排序;stCd:状态
				userInfo.put(LoginUserConstants.LOGIN_USER_ORGINF_INSTCD, instInfo.get("orgCode"));
				userInfo.put(LoginUserConstants.LOGIN_USER_ORGINF_INSTLVL, instInfo.get("orgLevelCd"));
				userInfo.put(LoginUserConstants.LOGIN_USER_ORGINF_INSTNM, instInfo.get("orgName"));
			}
			userInfo.put(LoginUserConstants.LOGIN_USER_INSTINF_LIST, userOrgInfs.getList());
		}else{
			logger.trace("用户["+userId+"]机构信息未设置");
		}
		
		logger.trace("加载用户["+userId+"]部门信息");
		queryCond.clear();
		queryCond.put("userNum", userId);
		queryCond.put("orgCode", instInfo.get("orgCode"));
		PageInfo<Map<String, Object>> usrDepts = userApi.findUserDeptRelPage(queryCond,null);
		if(usrDepts!=null&&usrDepts.getSize()>0){
			String deptCd = (String) usrDepts.getList().get(0).get("deptCode");
			Map<String,Object> queryDept = new HashMap<String,Object>();
			queryDept.put("deptCode", deptCd);
			Map<String,Object> deptInf =deptApi.getDeptMap(queryDept);
			if(deptInf!=null){
				//id:业务主键;deptCd:部门代码;deptNm:部门名称;suprDeptCd:上级部门代码; deptLvl:部门级别;seq:排序;stCd:状态;
				userInfo.put(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTCD, deptInf.get("deptCode"));
				userInfo.put(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTLVL, deptInf.get("deptLeveCd"));
				userInfo.put(LoginUserConstants.LOGIN_USER_DEPTINF_DEPTNM, deptInf.get("deptName"));
			}
			userInfo.put(LoginUserConstants.LOGIN_USER_DEPTINF_LIST, usrDepts.getList());
		}else{
			logger.trace("用户["+userId+"]部门信息未设置");
		}
		
		
		logger.trace("加载用户["+userId+"]角色信息");
		
		Map<String,Object> queryRoleMap = new HashMap<String,Object>();
		queryRoleMap.put("userNum", userId);
//		List<String> roleIds= roleApi.findUserRoleRelPage(queryRoleMap, null, null);  // findRlIdByUsrId(userId);
//		PageInfo<Map<String, Object>> roleRelInfo = null;// roleApi.findUserRoleRelPage(queryRoleMap, null, null);
		PageInfo<Map<String, Object>> roleRelInfo = roleApi.findRoleUserRelPage(queryRoleMap, null);
		if(null!= roleRelInfo){
			if(roleRelInfo.getSize()>0){
				List<Map<String, Object>> roleList = roleRelInfo.getList();
				if(CollectionUtil.isNotEmpty(roleList)){
					Map<String, Object> userRoleRel = roleList.get(0);
					for(Map<String, Object> tmp:roleList){
						if(DEFAULTIND_YES.equals(tmp.get("defaultInd"))){
							userRoleRel = tmp;
						}
					}
					userInfo.put(LoginUserConstants.LOGIN_USER_ROLEINF_RLID,userRoleRel.get("roleCode"));
					userInfo.put(LoginUserConstants.LOGIN_USER_ROLEINF_RLNM,userRoleRel.get("roleName"));
					userInfo.put("roleInfList", roleList);
				}
			}else{
				logger.trace("用户["+userId+"]角色信息未设置");
			}
			if(userInfo.containsKey(LoginUserConstants.LOGIN_USER_TENANT_ID)&&
					StringUtil.isNotBlank(MapUtil.getString(userInfo,LoginUserConstants.LOGIN_USER_TENANT_ID)))	{
				//加载登录配置 
				Map<String,Object> queryTenant = new HashMap<String,Object>();
				queryTenant.put(TenantConstants.TENANT_ID, userInfo.get(LoginUserConstants.LOGIN_USER_TENANT_ID));
				Map<String,Object> tenantInfo = tenantApi.getTenantMap(queryTenant);
				if(null!=tenantInfo) {
					userInfo.put(LoginUserConstants.LOGIN_USER_LOGIN_URI_SUFFIX, tenantInfo.get(TenantConstants.LOGIN_URI_SUFFIX));
					userInfo.put(LoginUserConstants.LOGIN_USER_TENANT_NAME, tenantInfo.get(TenantConstants.TENANT_NAME));
					userInfo.put(LoginUserConstants.LOGIN_USER_TENANT_ORG_CODE, tenantInfo.get(TenantConstants.ORG_CODE));
				}
			}
		}
		 return true;
	}

	@Override
	public void updateUserInfo(Map<String, Object> userMap) {
		Map<String, Object> queryMap=new  HashMap<>();
		Map<String, Object> resultMap =this.findByUsrNm((String)userMap.get("username"));//获取用户编码
		queryMap.put(UserConstants.USER_NUM, MapUtils.getString(resultMap, UserConstants.USER_NUM));
		queryMap.put("lastLoginTime", userMap.get("lastLoginTime"));
		userApi.updateUserTrans(queryMap);
	}

}
