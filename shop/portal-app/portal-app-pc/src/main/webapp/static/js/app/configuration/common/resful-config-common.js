'use strict';

define(function (require, exports, module) {

    var resourceConfig = {
        session : {
            get: '/getSession'
        },
        header : {
            query: '/header'
        },
        user : {
            query: '/user/queryUser'
        },
        menu : {
            query: '/menu'
        },
        /**相关文档**/
        file : {
            query : '/file/query',
            get : '/file/get',
            save : '/file/save',
            'delete' : '/file/delete'
        },
        dict : {
            query : '/dict/list',
            get : '/dict/get',
            'delete' : '/dict/delete',
            save : '/dict/save'
        },
        param : {
            query : '/param/list',
            get : '/param/get',
            'delete' : '/param/delete',
            save : '/param/save'
        },
        dataauth : {
            query : '/dataauth/query',
            get : '/dataauth/get',
            'delete' : '/dataauth/delete',
            save : '/dataauth/save',
            batchSave: '/dataauth/batchSave',
            modelSelector:'/dataauth/modelSelector'
        },
        // 找回密码
        forgotPwd : {
            query : '/sys/forgotPwd/checkUser',
            get : '/sys/forgotPwd/checkMobile',
            save : '/sys/forgotPwd/setNewPwd'
        },
        // 发送短信验证码
        sendCode : {
            get : '/sys/forgotPwd/captcha'
        },
        loginConfig : {
            get : '/getLoginConfig'
        }
    };
    module.exports = resourceConfig;
});
