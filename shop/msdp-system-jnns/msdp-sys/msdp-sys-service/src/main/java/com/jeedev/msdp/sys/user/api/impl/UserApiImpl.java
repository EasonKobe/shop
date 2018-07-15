package com.jeedev.msdp.sys.user.api.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.base.encode.service.IEncodeService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.sys.user.service.IUserDeptRelService;
import com.jeedev.msdp.sys.user.service.IUserGroupRelService;
import com.jeedev.msdp.sys.user.service.IUserOrgRelService;
import com.jeedev.msdp.sys.user.service.IUserRoleRelService;
import com.jeedev.msdp.sys.user.service.IUserService;
import com.jeedev.msdp.trace.IProviderSign;

/**
 * @类名称 UserApiImpl.java
 * @类描述
 *      <pre>用户接口实现类</pre>
 * @作者 Colin.DZX Colin.DZX@tansun.com.cn
 * @创建时间 2017年8月17日 上午11:18:44
 * @版本 1.00
 *
 * @修改记录
 * 
 *       <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	Colin.DZX 	2017年8月17日             
 *     ----------------------------------------------
 *       </pre>
 */
@Service("userApi")
public class UserApiImpl implements UserApi,IProviderSign {

	/**
	 * 用户服务
	 */
	@Autowired
	private IUserService userService;
	/**
	 * 用户与部门关系服务
	 */
	@Autowired
	private IUserDeptRelService userDeptRelService;
	/**
	 * 用户与机构关系服务
	 */
	@Autowired
	private IUserOrgRelService userOrgRelService;
	/**
	 * 用户与角色关系服务
	 */
	@Autowired
	private IUserRoleRelService userRoleRelService;
	/**
	 * 用户与分组关系服务
	 */
	@Autowired
	private IUserGroupRelService userGroupRelService;
	/**
	 * 序号生成器服务
	 */
	@Autowired
	private IEncodeService encodeService;

	/**
	 * @方法名称 findUserPage
	 * @功能描述
	 *      <pre>查询用户分页</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2016年11月22日 下午3:03:31
	 * @param paramMap
	 *            查询参数Map</br>
	 *            realname：真实用户名（模糊）</br>
	 *            loginName：用户名（模糊）</br>
	 *            userNum：用户编号</br>
	 *            mobile：手机号码</br>
	 *            email：邮箱</br>
	 *            statusCd：用户状态。1正常，2锁定
	 * @param pageSize 分页条数
	 * @param pageNum 分页页数
	 * @return 用户信息</br>
	 *         userNum：用户编号</br>
	 *         realname：真实用户名</br>
	 *         loginName：用户名</br>
	 *         mobile：手机号</br>
	 *         email：邮箱</br>
	 *         statusCd：用户状态。1正常，2锁定
	 */
	@Override
	public PageInfo<Map<String, Object>> findUserPage(Map<String, Object> params,PageParam pageParam) {
		return userService.findUserPage(params);
	}
	/**
	 * 查询用户列表
	 * @方法名称 findUserList
	 * @功能描述
	 * <pre>查询用户列表</pre>
	 * @作者 chenld
	 * @创建时间 2017/1/23 11:21
	 * @param paramMap
	 *            查询参数Map<br>
	 *            realname：真实名称（模糊）<br>
	 *            loginName：登录名（模糊）<br>
	 *            userNum：用户编号（多个逗号隔开,in）<br>
	 *            mobile：手机号码<br>
	 *            email：邮箱<br>
	 *            statusCd：用户状态。1正常，2锁定
	 * @return 用户信息<br>
	 *         userNum：用户编号<br>
	 *         loginName：用户名<br>
	 *         mobile：手机号<br>
	 *         email：邮箱<br>
	 *         statusCd：用户状态。1正常，2锁定
	 */
	public List<Map<String, Object>> findUserList(Map<String, Object> params) {
		PageInfo<Map<String, Object>> result = this.findUserPage(params,new PageParam());
		return result.getList();
	}

	/**
	 * @方法名称 getUserMap
	 * @功能描述
	 *       <pre>获取单条数据信息</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2017年9月7日 下午5:30:53
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> getUserMap(Map<String, Object> params) {
		return userService.getUserMap(params);
	}
	/**
	 * @方法名称 saveUserTrans
	 * @功能描述
	 *       <pre>保存用户</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2017年9月7日 下午5:31:20
	 * @param params
	 * @return
	 */
	@Override
	public Map<String, Object> saveUserTrans(Map<String, Object> params) {
		// 有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		String loginName = MapUtils.getString(params, UserConstants.LOGIN_NAME);
		String realname = MapUtils.getString(params, UserConstants.REALNAME);
		String password = MapUtils.getString(params, UserConstants.PASSWORD);
		String statusCd = MapUtils.getString(params, UserConstants.STATUS_CD);
		// 判断必填项是否填写
		if (loginName == null || loginName.isEmpty())
			throw ExceptionFactory.getBizException("sys-usr-00001");
		if (realname == null || realname.isEmpty())
			throw ExceptionFactory.getBizException("sys-usr-00002");
		if (id == null) {
			if (userService.countSysUserByLoginName(loginName) > 0) {
				throw ExceptionFactory.getBizException("sys-usr-00003",loginName);
			}
			// 判断必填项是否填写
			if (password == null || password.isEmpty())
				throw ExceptionFactory.getBizException("sys-usr-00004");
			params.put(UserConstants.DEL_IND, DictUtil.INDICATOR_NO());//默认删除状态
			if (statusCd == null || statusCd.isEmpty()) {
				params.put(UserConstants.STATUS_CD, DictUtil.INDICATOR_NO());//默认状态
			}
			String userNum = "";
			try {
				userNum = encodeService.buildEncode("10012", "0000");
			} catch (Exception e) {
				e.printStackTrace();
			}
			params.put(UserConstants.USER_NUM, userNum);
			return userService.saveUser(params);
		}
		userService.updateUser(params);
		return null;
	}
	/**
	 * 
	 * @方法名称 updateUserTrans
	 * @功能描述 <pre></pre>
	 * @作者    Colin.DZX
	 * @创建时间 2017年9月13日 上午9:07:06
	 * @param params
	 * @return
	 */
	public void updateUserTrans(Map<String,Object> params){
		//根据ID和userNum来获取对象后更新数据
		Map<String,Object> queryMap=new HashMap<>();
		if(!params.containsKey(UserConstants.ID) && !params.containsKey(UserConstants.USER_NUM)){
			throw ExceptionFactory.getBizException("sys-00002");
		}
		//根据userNum查对象
		if(params.containsKey(UserConstants.USER_NUM)){
			queryMap.put(UserConstants.USER_NUM, MapUtils.getString(params, UserConstants.USER_NUM));
			Map<String, Object> resultMap=userService.getUserMap(queryMap);
			if(!resultMap.isEmpty()){
				params.remove(UserConstants.ID);//删除id对象
				params.put(UserConstants.ID, MapUtils.getInteger(resultMap, UserConstants.ID));
			}
		}
		userService.updateUser(params);
	}

	@Override
	public void deleteUserTrans(Map<String, Object> params) {
		userService.deleteUser(params);
	}

	/**
	 * @方法名称 updatePasswordTrans
	 * @功能描述
	 *       <pre>修改密码</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2016年11月1日 下午2:06:46
	 * @param paramMap
	 *            参数Map</br>
	 * 			loginName：用户名</br>
	 * 			password：原密码</br>
	 * 			newPswd：新密码</br>
	 * @return 1成功，-1失败，-2原密码错误
	 */
	@Override
	public Integer changePasswordTrans(Map<String, Object> paramMap) {

		Integer result = -2;
		Map<String, Object> params = new HashMap<>();
		String loginName = MapUtils.getString(paramMap, UserConstants.LOGIN_NAME);
		String password = MapUtils.getString(paramMap, UserConstants.PASSWORD);
		String newPswd = MapUtils.getString(paramMap, UserConstants.NEW_PSWD);
		params.put(UserConstants.LOGIN_NAME, loginName);// 获取用户对象
		Map<String, Object> entityMap = userService.getUserMap(params);
		// 说明用户不存在
		if (entityMap == null) {
			return -1;
		}
		String pwd = MapUtils.getString(entityMap, UserConstants.PASSWORD);// 原密码
		if (pwd.equals(password)) {
			entityMap.put(UserConstants.PASSWORD, newPswd);// 修改新密码
			 userService.updateUser(entityMap);
			 result = 1;
		}
		return result;
	}
	/**
	 * @方法名称 resetPasswordTrans
	 * @功能描述
	 *  <pre>重置密码</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2016年11月1日 下午2:33:31
	 * @param paramMap
	 *            参数Map</br>
	 * 			loginName：用户名</br>
	 * 			newPswd：新密码</br>
	 * 			salt：盐
	 * @return 1成功，-1失败
	 */
	@Override
	public Integer resetPasswordTrans(Map<String, Object> paramMap) {

		Integer result = 1;
		Map<String, Object> params = new HashMap<>();
		String loginName = MapUtils.getString(paramMap, UserConstants.LOGIN_NAME);
		String newPswd = MapUtils.getString(paramMap, UserConstants.NEW_PSWD);
		params.put(UserConstants.LOGIN_NAME, loginName);// 获取用户对象
		Map<String, Object> entityMap = userService.getUserMap(params);
		// 说明用户不存在
		if (entityMap == null) {
			return -1;
		}
		entityMap.put(UserConstants.PASSWORD, newPswd);// 修改新密码
		userService.updateUser(entityMap);
		return result;
	}

	/**
	 * @方法名称 retrievalPassword
	 * @功能描述
	 * <pre>根据手机号、邮箱找回密码</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2017年9月7日 上午10:47:52
	 * @param paramMap
	 *            mobile 手机号 email 邮箱
	 * @return 1:发送成功 -1发送失败 -2,手机号不存在，-3邮箱不存在
	 */
	public Integer retrievalPassword(Map<String, Object> paramMap) {
		if (paramMap == null) {
			return -1;
		}
		String operator = MapUtils.getString(paramMap, UserConstants.OPERATOR);
		// 判断手机号是否存在
		if (paramMap.containsKey(UserConstants.MOBILE)) {
			String mobile = MapUtils.getString(paramMap, UserConstants.MOBILE);
			Integer isMobile = this.findMobileIsExist(paramMap);
			if (isMobile != -3) {
				// 生成验证码
				Map<String, String> params = new HashMap<>();
				params.put("vldTpCd", "captcha");// 验证码类型
				params.put("vldAcc", mobile);// 验证账户
				params.put("operator ", operator);
				//String vaco = validateCodeService.buildValidateCodeTran(params);
				// 发送验证码
				Map<String, Object> msgparams = new HashMap<>();
				msgparams.put("rvlPsn", mobile);
				msgparams.put("tplCd", "captcha");
				msgparams.put("operator", operator);
				//Map<String, Object> templateParams = new HashMap<>();
				//templateParams.put("code", vaco);
				//msgService.sendTextMessageTran(msgparams, templateParams);
			}
			return -2;
		}
		// 判断邮箱是否存在
		if (paramMap.containsKey(UserConstants.EMAIL)) {
			String email = MapUtils.getString(paramMap, UserConstants.EMAIL);
			Integer isEmail = this.findEmailIsExist(paramMap);
			if (isEmail != -4) {
				// 生成验证码
				Map<String, String> params = new HashMap<>();
				params.put("vldTpCd", "captcha");// 验证码类型
				params.put("vldAcc", email);// 验证账户
				params.put("operator ", operator);
				//String vaco = validateCodeService.buildValidateCodeTran(params);
				// 发送邮箱
				Map<String, Object> mailparams = new HashMap<>();
				mailparams.put("rvlPsn", email);
				mailparams.put("msgTtl", "邮箱验证码");
				mailparams.put("tplCd", "email_auth");
				mailparams.put("operator", operator);
				//Map<String, Object> templateParams = new HashMap<>();
				//templateParams.put("vldCd", vaco);
				//msgService.sendEmailTran(mailparams, templateParams);
			}
			return -3;
		}
		return 1;
	}

	/**
	 * @方法名称 saveReg
	 * @功能描述
	 * <pre>注册</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2016年11月1日 下午3:16:58
	 * @param paramMap
	 *            参数Map</br>
	 *            userNum：用户编号</br>
	 *            loginName：用户名</br>
	 *            pswd：密码</br>
	 *            salt：盐</br>
	 *            mobile：手机号码</br>
	 *            clntendId:客户端编号</br>
	 *            email：邮箱</br>
	 * @return 1成功，-1失败，-3手机号码已被使用，-4邮箱已被使用，-5用户名已被使用
	 * @throws Exception
	 */
	public Integer saveReg(Map<String, Object> params) throws Exception {
		String loginName = MapUtils.getString(params, UserConstants.LOGIN_NAME);
		String password = MapUtils.getString(params, UserConstants.PASSWORD);
		//String salt = MapUtils.getString(params, UserConstants.SALT);
		// 判断必填项是否填写
		if (loginName == null || loginName.isEmpty())
			throw ExceptionFactory.getBizException("sys-usr-00001");
		// 检查登录名称
		Integer isUser = this.findUserIsExist(params);
		if (isUser != 1) {
			return isUser;
		}
		// 检查邮箱
		if (params.containsKey(UserConstants.EMAIL)) {
			Integer isEmali = this.findEmailIsExist(params);
			if (!(isEmali == 1)) {
				return isEmali;
			}
		}
		// 检查电话
		if (params.containsKey(UserConstants.MOBILE)) {
			Integer isMobile = this.findMobileIsExist(params);
			if (!(isMobile == 1)) {
				return isMobile;
			}
		}
		// 判断必填项是否填写
		if (password == null || password.isEmpty())
			throw ExceptionFactory.getBizException("sys-usr-00004"); 
		params.put(UserConstants.DEL_IND, DictUtil.INDICATOR_NO());//初始化删除状态
		params.put(UserConstants.STATUS_CD, DictUtil.INDICATOR_NO());//初始化用户状态
		// 如果没有传用户编号则按照系统规则生成.
		if (params.containsKey(UserConstants.USER_NUM)) {
			String userNum = MapUtils.getString(params, UserConstants.USER_NUM);
			if (userNum.isEmpty()) {
				userNum = encodeService.buildEncode("10012", "0000");
			}
			params.put(UserConstants.USER_NUM, userNum);
		}
		userService.saveUser(params);
		return 1;
	}

	/**
	 * @方法名称 checkUserName
	 * @功能描述
	 * <pre>校验用户名是否可用</pre>
	 * @作者 Colin.DZX
	 * @创建时间 2016年11月1日 下午3:29:55
	 * @param loginName
	 *            用户名
	 * @return 1可用，-1不可用，-5用户名已被使用
	 */
	public Integer findUserIsExist(Map<String, Object> params) {
		if (params.containsKey(UserConstants.LOGIN_NAME)) {
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put(UserConstants.LOGIN_NAME, MapUtils.getString(params, UserConstants.LOGIN_NAME));
			Map<String, Object> entityMap = userService.getUserMap(queryMap);
			if (entityMap.isEmpty()) {
				return 1;// 不存在，可用
			}
			return -5;
		}
		return -1;
	}

	/**
	 * @方法名称 checkEmail
	 * @功能描述
	 * <pre>校验邮箱是否可用</pre>
	 * 
	 * @作者 Colin.DZX
	 * @创建时间 2016年11月1日 下午3:30:44
	 * @param email
	 *            邮箱
	 * @return 1可用，-1不可用，-4邮箱已被使用
	 */
	public Integer findEmailIsExist(Map<String, Object> params) {
		String regex = "\\w+(\\.\\w)*@\\w+(\\.\\w{2,3}){1,3}";
		if (params.containsKey(UserConstants.EMAIL)) {
			String email = MapUtils.getString(params, UserConstants.EMAIL);
			if (!email.matches(regex))
				return -1;
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put(UserConstants.EMAIL, email);
			Map<String, Object> resultMap = userService.getUserMap(queryMap);
			if (resultMap.isEmpty()) {
				return 1;
			}
			return -4;
		}
		return -1;
	}

	/**
	 * @方法名称 checkMobile
	 * @功能描述
	 * <pre>校验手机号是否可用</pre>
	 * 
	 * @作者 Colin.DZX
	 * @创建时间 2016年11月1日 下午3:31:15
	 * @param mobile
	 *            手机号码
	 * @return 1可用，-1不可用，-3手机号码已被使用
	 */
	public Integer findMobileIsExist(Map<String, Object> params) {
		if (params.containsKey(UserConstants.MOBILE)) {
			String mobile = MapUtils.getString(params, UserConstants.MOBILE);
			String regex = "^[1]([3][0-9]{1}|59|58|88|89)[0-9]{8}$";
			if (!mobile.matches(regex)) {
				return -1;
			}
			Map<String, Object> queryMap = new HashMap<>();
			queryMap.put(UserConstants.MOBILE, mobile);
			Map<String, Object> entityMap = userService.getUserMap(queryMap);
			if (entityMap.isEmpty()) {
				return 1;
			}
			return -3;
		}
		return -1;
	}

	// ==========用户与部门关系==========
	@Override
	public PageInfo<Map<String, Object>> findUserDeptRelPage(Map<String, Object> params, PageParam pageParam) {
		return userDeptRelService.findUserDeptRelPage(params, pageParam);
	}

	@Override
	public Map<String, Object> getUserDeptRelMap(Map<String, Object> params) {
		return userDeptRelService.getUserDeptRelMap(params);
	}

	@Override
	public Map<String, Object> saveUserDeptRelTrans(Map<String, Object> params) {
		// 有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		String userNum = MapUtils.getString(params, UserConstants.USER_NUM);
		String deptCode = MapUtils.getString(params, UserConstants.DEPT_CODE);
		String statusCd = MapUtils.getString(params, UserConstants.STATUS_CD);
		String delInd = MapUtils.getString(params, UserConstants.DEL_IND);

		if (statusCd == null || statusCd.isEmpty()) {
			params.put(UserConstants.STATUS_CD, "0");
		}
		if (delInd == null || delInd.isEmpty()) {
			params.put(UserConstants.DEL_IND, "0");
		}
		if (id == null) {
			Map<String, Object> ins_params = new HashMap<>();
			ins_params.put(UserConstants.USER_NUM, userNum);
			ins_params.put(UserConstants.DEPT_CODE, deptCode);
			if (userDeptRelService.countNum(ins_params) > 0) {
				// 用户关联该部门已存在！
				throw ExceptionFactory.getBizException("sys-usr-00011");
			}
			return userDeptRelService.saveUserDeptRel(params);
		}
		userDeptRelService.updateUserDeptRel(params);
		return null;
	}

	@Override
	public void deleteUserDeptRelTrans(Map<String, Object> params) {
		userDeptRelService.deleteUserDeptRel(params);
	}

	// ========用户与角色==========
	@Override
	public PageInfo<Map<String, Object>> findUserRoleRelPage(Map<String, Object> params,  PageParam pageParam) {
		return userRoleRelService.findUserRoleRelPage(params, pageParam);
	}
	public List<Map<String, Object>> findUserRoleRelList(Map<String, Object> params) {
		// 逻辑代码调用-分子结构
		params.put(UserConstants.DEL_IND, "0");// 删除标记
		// 调用关系表
		PageInfo<Map<String, Object>> pageInfo = userRoleRelService.findUserRoleRelPage(params, null);
		return pageInfo.getList();
	}

	@Override
	public Map<String, Object> getUserRoleRelMap(Map<String, Object> params) {
		return userRoleRelService.getUserRoleRelMap(params);
	}

	@Override
	public Map<String, Object> saveUserRoleRelTrans(Map<String, Object> params) {
		// 有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		if (id == null) {
			return userRoleRelService.saveUserRoleRel(params);
		}
		userRoleRelService.updateUserRoleRel(params);
		return null;
	}

	@Override
	public void deleteUserRoleRelTrans(Map<String, Object> params) {
		Integer id = MapUtils.getInteger(params, "id");
		if (id == null) {
			throw ExceptionFactory.getBizException("sys-00002");
		}
		Map<String, Object> userRoleRelMap = userRoleRelService.getUserRoleRelMap(params);
		if (userRoleRelMap == null) {
			throw ExceptionFactory.getBizException("sys-00003", "findOne");
		}

		//如果是默认角色
		// TODO: 没看懂业务含义
		if(StringUtil.equals(MapUtil.getString(userRoleRelMap, "defaultInd"),DictUtil.INDICATOR_YES())){
			Map<String,Object> cond = new HashMap<String,Object>();
			cond.put("delInd", DictUtil.INDICATOR_NO());
			cond.put("statusCd", DictUtil.getDictValue("StCd", "StCd_1"));
			cond.put("userNum", userRoleRelMap.get("userNum"));
			PageInfo<Map<String, Object>> defaultOrgRel = findUserRoleRelPage(cond, null);
			if(defaultOrgRel.getSize()>0){
				throw  ExceptionFactory.getBizException("sys-usr-00012");
			}else{
				//没有其他部门了，不需要检验
			}
		}

		userRoleRelService.deleteUserRoleRel(params);
	}

	// ===========用户与机构=============
	@Override
	public PageInfo<Map<String, Object>> findUserOrgRelPage(Map<String, Object> params,PageParam pageParam) {
		return userOrgRelService.findUserOrgRelPage(params, pageParam);
	}

	// ===========用户与机构与部门=============
	@Override
	public PageInfo<Map<String, Object>> findUserOrgDeptRelPage(Map<String, Object> params,PageParam pageParam) {
		return userOrgRelService.findUserOrgDeptRelPage(params, pageParam);
	}
		
	@Override
	public Map<String, Object> getUserOrgRelMap(Map<String, Object> params) {
		return userOrgRelService.getUserOrgRelMap(params);
	}

	@Override
	public Map<String, Object> saveUserOrgRelTrans(Map<String, Object> params) {
		// 有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		if (id == null) {
			return userOrgRelService.saveUserOrgRel(params);
		} else {
			userOrgRelService.updateUserOrgRel(params);
			return params;
		}
	}

	@Override
	public void deleteUserOrgRelTrans(Map<String, Object> params) {
		userOrgRelService.deleteUserOrgRel(params);
	}

	// ============用户与组==============
	@Override
	public PageInfo<Map<String, Object>> findUserGroupRelPage(Map<String, Object> params,PageParam pageParam) {
		return userGroupRelService.findUserGroupRelPage(params, pageParam);
	}

	@Override
	public Map<String, Object> getUserGroupRelMap(Map<String, Object> params) {
		return userGroupRelService.getUserGroupRelMap(params);
	}

	@Override
	public Map<String, Object> saveUserGroupRelTrans(Map<String, Object> params) {
		// 有主键的情况下去更新，没有的话新增
		Integer id = MapUtils.getInteger(params, UserConstants.ID);
		if (id == null) {
			return userGroupRelService.saveUserGroupRel(params);
		}
		userGroupRelService.updateUserGroupRel(params);
		return null;
	}

	@Override
	public void deleteUserGroupRelTrans(Map<String, Object> params) {
		userGroupRelService.deleteUserGroupRel(params);
	}

	@Override
	public PageInfo<Map<String, Object>> findUserByOrgRoleRel(Map<String, Object> params) {
		return userOrgRelService.findUserByOrgRoleRel(params, null);
	}

	@Override
	public PageInfo<Map<String, Object>> findUserByDeptRoleRel(Map<String, Object> params) {
		return userDeptRelService.findUserByDeptRoleRel(params);
	}

}
