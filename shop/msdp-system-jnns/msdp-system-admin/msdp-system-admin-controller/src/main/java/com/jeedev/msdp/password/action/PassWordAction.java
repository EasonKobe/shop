package com.jeedev.msdp.password.action;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.jeedev.msdp.captcha.AuthUtils;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.msg.api.MsgApi;
import com.jeedev.msdp.msg.api.MsgConstants;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.trace.constants.LoginUserConstants;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
import com.jeedev.msdp.validatecode.api.ValidateCodeApi;

/**
 * @类名称 PassWordAction.java
 * @类描述 <pre>用户找回密码</pre>
 * @作者 yuyq yuyq@tansun.com.cn
 * @创建时间 2017年11月13日 上午10:32:41
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
@Controller
@RequestMapping(value = "/sys/forgotPwd")
public class PassWordAction extends BaseAppAction{
    
    /**
     * 发送类型：SMS
     */
    private static final String MODE_SMS = "SMS";
    
    /**
     * 发送类型：EMAIL
     */
    private static final String MODE_EMAIL = "EMAIL";
    
    /**
     * 注入用户api
     */
    @Autowired
    private UserApi userApi;
    
    /**
     * 注入短信api
     */
    @Autowired
    private MsgApi msgApi;
    /**
     * 注入验证码api
     */
    @Autowired
    private ValidateCodeApi validateCodeApi;
    
    
    /**
     * @方法名称 pwd
     * @功能描述 <pre>跳转到找回密码首页</pre>
     * @作者    yuyq
     * @创建时间 2017年9月21日 下午5:50:44
     * @return
     */
    @RequestMapping(value = "", method = RequestMethod.GET)
    public ModelAndView pwd() {
        return new ModelAndView("forgotPwd");
    }
    
    /**
     * @方法名称 checkUser
     * @功能描述 <pre>校验用户名和手机号/邮箱</pre>
     * @作者    yuyq
     * @创建时间 2017年11月13日 下午6:44:53
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkUser")
    public ResponseVO checkUser(@RequestObjectParam Map<String, String> params) {
        String loginName = MapUtils.getString(params, "loginName"); // 获取用户名
        String mobileOrEmail = MapUtils.getString(params, "mobileOrEmail"); // 获取手机号或邮箱
        String captchaType = MapUtils.getString(params, "captchaType"); // 获取发送类型
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", loginName);
        Map<String, Object> userMap = this.userApi.getUserMap(map); // 根据登陆名获取用户信息
        if (userMap == null) {
            return errorResponse("该用户名不存在");
        }
        if (MODE_SMS.equals(captchaType)&&!mobileOrEmail.equals(MapUtil.getString(userMap, UserConstants.MOBILE))) {
        	 return errorResponse("用户名和手机号码不一致，请重新输入");
        } else if (MODE_EMAIL.equals(captchaType)&&!mobileOrEmail.equals(MapUtil.getString(userMap, UserConstants.EMAIL))) {
            return errorResponse("用户名和邮箱不一致，请重新输入");
        }
        return successResponse("成功");
    }
    
    /**
     * @方法名称 checkMobile
     * @功能描述 <pre>验证手机号/邮箱和校验码</pre>
     * @作者    yuyq
     * @创建时间 2017年11月13日 下午6:44:53
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/checkMobile")
    public ResponseVO checkMobile(@RequestObjectParam Map<String, Object> params) {
        String mobileOrEmail = MapUtils.getString(params, "mobileOrEmail"); // 获取手机号或邮箱
        String code = MapUtils.getString(params, "code"); // 获取验证码
        String jfCode = MapUtils.getString(params, "jfCode"); // 验证类型
        Map<String, String> map = new HashMap<>();
        map.put("vldTpCd", jfCode);
        map.put("vldCd", code);
        map.put("vldAcc", mobileOrEmail);
        boolean flag = this.validateCodeApi.verifyWithoutDestroy(map);
        if (!flag) { // 验证码错误
            return errorResponse("验证码错误，请重新输入");
        }
        return successResponse("校验成功");
    }
    
    /**
     * @方法名称 setNewPwd
     * @功能描述 <pre>设置新密码</pre>
     * @作者    yuyq
     * @创建时间 2017年11月13日 下午6:44:53
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/setNewPwd")
    public ResponseVO setNewPwd(@RequestObjectParam Map<String, Object> params) {
        String loginName = MapUtils.getString(params, "loginName"); // 获取用户名
        String mobileOrEmail = MapUtils.getString(params, "mobileOrEmail"); // 获取手机号或邮箱
        String newPswd = MapUtils.getString(params, "newPswd"); // 获取新密码
        String captchaType = MapUtils.getString(params, "captchaType"); // 发送类型
        Map<String, Object> map = new HashMap<>();
        map.put("loginName", loginName);
        Map<String, Object> userMap = this.userApi.getUserMap(map); // 根据登陆名获取用户信息
        if (userMap == null) {
            return errorResponse("该用户名不存在");
        }
        if (MODE_SMS.equals(captchaType)&&!mobileOrEmail.equals(MapUtil.getString(userMap, UserConstants.MOBILE))) {
          	 return errorResponse("用户名和手机号码不一致，请重新输入");
          } else if (MODE_EMAIL.equals(captchaType)&&!mobileOrEmail.equals(MapUtil.getString(userMap, UserConstants.EMAIL))) {
              return errorResponse("用户名和邮箱不一致，请重新输入");
          }
        // 登陆密码
        String salt = AuthUtils.getSalt(); // 获取盐
        newPswd = AuthUtils.encryptPassword(loginName, newPswd, salt); // 密码MD5加密
        userMap.put("password", newPswd);
        userMap.put("salt", salt);
        userMap = this.userApi.saveUserTrans(userMap); // 修改密码
        return successResponse(userMap);
    }
    
    /**
     * @方法名称 modifyPswd
     * @功能描述 <pre>修改密码</pre>
     * @作者    jiaxx
     * @创建时间 2017年9月13日 下午2:17:27
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/modifyPswd")
    public ResponseVO modifyPswd(@RequestObjectParam Map<String, Object> params, @CurrentUser Map<String, Object> user) {
        String currUsrId = MapUtils.getString(user, LoginUserConstants.LOGIN_USER_USRID); // 获取当前登录人用户ID
        String loginName = MapUtils.getString(user, LoginUserConstants.LOGIN_USER_LOGINNAME); // 获取当前登录用户名
        String mobile = MapUtils.getString(params, "mobile"); // 获取手机号
        String oriPswd = MapUtils.getString(params, "oriPswd"); // 获取原密码
        String newPswd = MapUtils.getString(params, "newPswd"); // 获取新密码
        String code = MapUtils.getString(params, "code"); // 手机验证码
        String jfCode = MapUtils.getString(params, "jfCode"); // 验证类型
        Map<String, String> map = new HashMap<>();
        map.put("vldTpCd", jfCode);
        map.put("vldCd", code);
        map.put("vldAcc", mobile);
        boolean flag = this.validateCodeApi.verifyWithoutDestroy(map);
        if (!flag) { // 验证码错误
            return errorResponse("手机验证码错误，请重新输入");
        }
        params.clear();
        params.put("userNum", currUsrId); // 用户编号
        Map<String, Object> userMap = this.userApi.getUserMap(params); // 查询用户信息
        String password = MapUtils.getString(userMap, "password"); // 获取密码
        String salt = MapUtils.getString(userMap, "salt"); // 获取盐
        oriPswd = AuthUtils.encryptPassword(loginName, oriPswd, salt); // 原密码MD5加密
        if (!oriPswd.equals(password)) {
            return errorResponse("原密码错误，请重新输入");
        }
        salt = AuthUtils.getSalt(); // 获取新盐
        newPswd = AuthUtils.encryptPassword(loginName, newPswd, salt); // 密码MD5加密
        userMap.put("password", newPswd);
        userMap.put("salt", salt);
        userMap = this.userApi.saveUserTrans(userMap); // 修改密码
        return successResponse(userMap);
    }
    /**
     * @方法名称 getCaptcha
     * @功能描述 <pre>发送验证码</pre>
     * @作者    jiaxx
     * @创建时间 2017年9月29日 下午4:38:08
     * @param params
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/captcha")
    public ResponseVO getCaptcha(@RequestObjectParam Map<String, Object> params, @CurrentUser Map<String, Object> user) {
        String currUsrId = ""; // 当前登录人用户ID
        if (user != null) {
            currUsrId = (String)user.get(LoginUserConstants.LOGIN_USER_USRID);
        }
        String type = MapUtils.getString(params, "type"); // 发送类型
        String target = MapUtils.getString(params, "target"); // 接收人
        String mode = MapUtils.getString(params, "mode"); // 发送方式
        Map<String, String> map = new HashMap<>();
        map.put("vldTpCd", type); // 验证码类型
        // map.put("vldTm", "60"); // 有效时间
        map.put("vldAcc", target); // 验证账户
        map.put("operator", currUsrId); // 操作人员
        String vldCd = this.validateCodeApi.buildValidateCodeTran(map); // 生成验证码
        if (StringUtil.isBlank(vldCd)) {
            return errorResponse("生成验证码失败");
        }
        Map<String, Object> msgMap = new HashMap<>();
        msgMap.put(MsgConstants.RVL_PSN, target); // 收件人
       
        msgMap.put("operator", currUsrId); // 操作人员
        Map<String, Object> templateParams = new HashMap<>(); // 短信模板填充参数
        templateParams.put("code", vldCd);
  
        //设置应用编码和密钥
        msgMap.put(MsgConstants.APP_ID, "dashu");
        if (MODE_SMS.equals(mode)) {
            msgMap.put(MsgConstants.ENCODING_AES_KEY, "dashu");
        	msgMap.put(MsgConstants.TPL_CD, "captcha"); // 短信模板代码
            msgMap.put(MsgConstants.MSG_TP_CD, MsgConstants.SMS);
            this.msgApi.sendMessageTrans(msgMap, templateParams);
        } else if (MODE_EMAIL.equals(mode)) {
            templateParams.put("website", "smtp.qq.com");
            templateParams.put("vlCode", vldCd);
            templateParams.put("mail", target);
            templateParams.put("date", vldCd);
            msgMap.put(MsgConstants.ENCODING_AES_KEY, "8e8d717bd9cf4048849889aa0f426586");
            msgMap.put(MsgConstants.TPL_CD, "findPwd"); // 邮件模板代码
            msgMap.put(MsgConstants.MSG_TP_CD, MsgConstants.EMAIL);
            this.msgApi.sendMessageTrans(msgMap, templateParams);
        }
        return successResponse("发送成功");
    }
}
