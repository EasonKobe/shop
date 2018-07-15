'use strict';

define(function (require, exports, module) {

    var post = function (url, appCtx) {
        if (!appCtx) { appCtx = ctx; }
        return {
            method: 'POST',
            url: appCtx + url,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            isArray: false
        };
    };
    
    var get = function (url, appCtx) {
        if (!appCtx) { appCtx = ctx; }
        return {
            method: 'GET',
            url: appCtx + url,
            headers: { 'Content-Type': 'application/x-www-form-urlencoded' },
            isArray: false
        };
    };
    
    //跨域请求
    var jsonp = function (url) {
        if (url.indexOf('http') != 0) {
            url = "http://" + url;
        }
        return {method: 'JSONP', url: url, isArray: false};
    };

    var resourceConfig = {
    		session : {
    			get: '/getSession'
    		},	
    		header : {
    			query: '/menu'
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
            menuManage : {
            	query : '/menu/menuTree',
            	get : '/menu/get',
            	'delete' : '/menu/delete',
            	save : '/menu/save'
            },
            menuEventRelManage : {
            	query : '/menu/eventRel/query',
            	'delete' : '/menu/eventRel/delete',
            	save : '/menu/eventRel/save'
            },
            menuEventDataAuthManage:{
            	query : '/menu/eventDataauth/query'
            },
            eventResManage : {
            	query : '/eventRes/query',
            	get : '/eventRes/get',
            	'delete' : '/eventRes/delete',
            	save : '/eventRes/save'
            },
            org : {
            	query : '/org/orgTree',
            	get : '/org/get',
            	'delete' : '/org/delete',
            	save : '/org/save',
            	selector:'/org/selector',
            	selectorCoreOrg:'/org/selectorCoreOrg'
            },
            role : {
            	query : '/role/roleTree',
            	get : '/role/get',
            	'delete' : '/role/delete',
            	save : '/role/save',
            	selector:'/role/selector'
            },
            roleMenu : {
            	query : '/role/roleMenu',
            	save : '/role/saveRoleMuen',
            	queryAssigned : '/role/roleMenuAssigned',
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
            roleUser : {
            	query : '/role/roleUser'
            },
            admin : {
            	query : '/admin/userOrgDept',
            	changer : '/admin/changer'
            },
            department : {
            	query : '/department/departmentTree',
            	get : '/department/get',
            	'delete' : '/department/delete',
            	save : '/department/save',
            	selector:'/department/selector'
            },
            dataauth : {
            	query : '/dataauth/query',
            	get : '/dataauth/get',
            	'delete' : '/dataauth/delete',
            	save : '/dataauth/save',
            	batchSave: '/dataauth/batchSave',
            	modelSelector:'/dataauth/modelSelector'
            },
          //用户管理
            sysUser1 : { 
            	query : '/usermg/query',
            	save : '/usermg/save',
            	get : '/usermg/get',
            	'delete' : '/usermg/delete',
            	selector:'/usermg/selector',
            	updatePswd:'/usermg/changePassword',
            	resetPassword:'/usermg/resetPassword'
            },
            userAndOrgRel : { 
            	query : '/usermg/userAndOrgRel/query',
            	save : '/usermg/userAndOrgRel/save',
            	get : '/usermg/userAndOrgRel/get',
            	'delete' : '/usermg/userAndOrgRel/delete',
            	selector : '/usermg/userAndOrgRel/selector'
            } ,
            userAndDeptRel : { 
            	query : '/usermg/userAndDeptRel/query',
            	save : '/usermg/userAndDeptRel/save',
            	get : '/usermg/userAndDeptRel/get',
            	'delete' : '/usermg/userAndDeptRel/delete'
            } ,
            userAndRoleRel : { 
            	query : '/usermg/userAndRoleRel/query',
            	save : '/usermg/userAndRoleRel/save',
            	get : '/usermg/userAndRoleRel/get',
            	'delete' : '/usermg/userAndRoleRel/delete'
            },
            candidateUser : {
            	query : '/selectUser/getCandidateUser'
            },
            instDeptTree : {
            	query : '/selectUser/getInstDeptTree'
            },
            permission : {
            	query : '/sys/indexMenu/permission'
            },
            //租户管理
            tenant : { 
            	query : '/tenant/query',
            	get : '/tenant/get',
            	save : '/tenant/save',
            	effect :'/tenant/effect',
                invalid :'/tenant/invalid',
                'delete' :'/tenant/delete',
            	roleMenu:{
            		save : '/tenant/roleMenu/save'
            	}
            },
          //租户管理
            tenantRoleMenu : { 
            	save : '/tenant/roleMenu/save',
            	query:'/tenant/roleMenu/menuTree'
            },
            
          //快捷菜单
            quickMenu : { 
            	query : '/quickMenu/query',
            	tree : '/quickMenu/queryMenuTree',
            	save : '/quickMenu/save',
            	'delete' : '/quickMenu/delete'
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
    		},
    		//查询地址
            area: {
                query: '/area/query'
            }

    };
    module.exports = resourceConfig;
});
