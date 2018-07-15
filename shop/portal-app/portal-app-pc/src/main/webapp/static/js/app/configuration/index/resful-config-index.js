'use strict';

define(function (require, exports, module) {

    var resourceConfig = {
        // 注册
        reg : {
            query : '/reg/checkRegister',
            get : '/reg/getInviteCode',
            save : '/reg/saveUserInf'
        },
        // 找回密码
        pwd : {
            query : '/pwd/checkUser',
            get : '/pwd/checkMobile',
            save : '/pwd/setNewPwd'
        },
        // 修改密码
        modifyPswd : {
            save : '/pwd/modifyPswd'
        },
        // 修改邮箱
        modifyEmail : {
            save : '/security/center/modifyEmail'
        },
        contactUs : {
        	save : '/ljContactUs/doSaveContactUs'
        }
    };
    module.exports = resourceConfig;
});
