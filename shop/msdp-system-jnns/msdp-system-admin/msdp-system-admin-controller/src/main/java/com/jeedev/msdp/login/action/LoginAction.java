package com.jeedev.msdp.login.action;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.ExcessiveAttemptsException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.jeedev.msdp.captcha.exception.CaptchaErrorException;
import com.jeedev.msdp.core.web.action.BaseAppAction;


/**
 * @类名称 LoginAction.java
 * @类描述 <pre></pre>
 * @作者 yangkunwei yangkunwei@tansun.com.cn
 * @创建时间 2017年6月26日 下午2:59:50
 * @版本 1.00
 *
 * @修改记录
 * <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     1.00 	yangkunwei 	2017年6月26日             
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping(value = "")
public class LoginAction extends BaseAppAction{
	
//	    @RequestMapping(value="/login",method=RequestMethod.GET)
//		public ModelAndView index(){
//			return new ModelAndView("login") ;
//		}
//	    
//	    @RequestMapping(value="/logout",method=RequestMethod.GET)
//	    public ModelAndView logout(){
//	    	return new ModelAndView("login") ;
//	    }
	    
	    /*
		 * 转向到登陆页面 
		 */
		@RequestMapping(value = {"/login","/login/**"}, method = RequestMethod.GET)
		public ModelAndView showLoginForm(HttpServletRequest req, Model model) {
			return new ModelAndView("login") ;
		}
	    
	    @RequestMapping(value="/logout",method=RequestMethod.GET)
	    public ModelAndView logout(){
//	    	return new ModelAndView("login") ;
//	    	return new ModelAndView("redirect:login" ) ;
	    	return new ModelAndView("login") ;
	    }
		/**
		 *本地登录
		 * @param userName
		 * @param req
		 * @param model
		 * @return
		 */
		@RequestMapping(value = {"/login"}, method = RequestMethod.POST)
		public ModelAndView loginFail(@RequestParam(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM) String userName,HttpServletRequest request, Model model) {
			String exceptionClassName = (String) request.getAttribute("shiroLoginFailure");
			String error = null;
			if (UnknownAccountException.class.getName().equals(exceptionClassName)
					||IncorrectCredentialsException.class.getName().equals(exceptionClassName)) {
//				error = "用户名/密码错误";
				error="10001";
			} else if (LockedAccountException.class.getName().equals(
					exceptionClassName)) {
//				error = "该用户已失效，无法登陆";
				error="10004";
			} else if (ExcessiveAttemptsException.class.getName().equals(
					exceptionClassName)) {
//				error = "用户已被锁定，请2小时后登陆"	;
				error="10002";
			} else if (CaptchaErrorException.class.getName().equals(
					exceptionClassName)) {
//				error = "验证码为空或者不正确 "	;
				error="10003";
			} else if (exceptionClassName != null) {
//				"其他错误：" + "认证失败";
			    error = "99999";
			}
//			model.addAttribute("error", error);
//			model.addAttribute(FormAuthenticationFilter.DEFAULT_USERNAME_PARAM,userName);
//			return "login";
			 // 使用相对路径，处理多层代理 引起的schema不精准问题。
	    	StringBuffer reqUri = new StringBuffer(request.getRequestURI().substring(request.getContextPath().length()));
//	    	Map<String,String> paramMap = this.getParameterMap(request) ;
//	    	Set<String> keySet = paramMap.keySet() ;
	    	reqUri.append("?").append("error=").append(error) ;
	    	String loginUriSuffix = request.getParameter("loginUriSuffix");
	    	if(StringUtils.isNotBlank(loginUriSuffix)) {
	    		String[] str = reqUri.toString().split("\\?");
	    		return new ModelAndView("redirect:" + str[0]+"/"+loginUriSuffix+"?"+str[1]) ;
	    	}
	        return new ModelAndView("redirect:" + reqUri) ;
		}
}
