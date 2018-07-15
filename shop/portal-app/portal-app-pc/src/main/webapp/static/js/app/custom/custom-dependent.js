'use strict';
define(function (require, exports, module) {
	var path=window.location.href;
    module.exports = {};
	/*if(path.indexOf("login")>-1){//登录页面
		module.exports = {
            overallStyle:['/web_login.css'],
		};
	}else if(path.indexOf("center")>-1){//用户中心页面
        module.exports = {
            overallStyle:['/layui/css/layui.css','/layui/css/layui_mend.css','/style.css'],
        };
    }else{
        module.exports = {
            overallStyle:['/style.css','/index.css','/jquery.fullPage.css']
        };
	}*/
    /*if(path.indexOf("main")>-1){//主页面
		module.exports = {
            overallStyle:['/reset.css', '/common.css', '/main.css'],
		};
	}else if(path.indexOf("contactus")>-1){//联系我们
        module.exports = {
        	overallStyle:['/reset.css', '/common.css', '/contactus.css'],
        };
    }else if(path.indexOf("sign")>-1){//登录
        module.exports = {
        	overallStyle:['/reset.css', '/common.css', '/sign.css'],
        };
    }else if(path.indexOf("register")>-1){//注册
        module.exports = {
        	overallStyle:['/reset.css', '/common.css', '/register.css'],
        };
    }else{
        module.exports = {//首页
        	overallStyle:['/reset.css', '/common.css']
//        	overallStyle:['/reset.css', '/common.css', '/index.css', '/sign.css', '/contactus.css', '/main.css', '/register.css']
        };
	}*/
    module.exports = {overallStyle:['/reset.css', '/common.css', '/index.css', '/sign.css', '/contactus.css', '/main.css', '/register.css']
    };
});