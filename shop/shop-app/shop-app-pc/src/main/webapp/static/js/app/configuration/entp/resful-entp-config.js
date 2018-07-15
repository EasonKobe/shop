// resful-entp-config.js
'use strict';
define(function (require, exports, module) {
    var config = {
        entp: {
            query: '/entp/query',
            tree: '/entp/tree',
            get: '/entp/get',
            save: '/entp/save',
            'delete': '/entp/delete',
            queryMenu: '/entpMenu/query',
            saveMenu: '/entpMenu/save',
            queryRole: '/entpRole/query',
            saveRole: '/entpRole/save',
            deleteRole: '/entpRole/delete',
            queryRoleMenu: '/entpRole/queryMenu',
            roleSelector:'/entpRole/selector',
        },
		 //用户管理
        entpUser : {
	    	query : '/entpUser/query',
            queryByRole : '/entpUser/queryByRole',
	    	save : '/entpUser/save',
	    	get : '/entpUser/get',
	    	'delete' : '/entpUser/delete',
            resetPassword : '/entpUser/resetPassword',
        },
	    entpUserAndRole : {
	    	query : '/entpUser/userAndRoleRel/query',
	    	save : '/entpUser/userAndRoleRel/save',
	    	get : '/entpUser/userAndRoleRel/get',
	    	'delete' : '/entpUser/userAndRoleRel/delete'
	    },
        batch: {
            'import':'/batch/import'
        },
	    ukey : {
	    	query : '/entpUser/ukeyquery',
	    	ukeyBind : '/entpUser/userUkey',
	    	get : '/entpUser/initUkeyInfo',
	    }
    };
    module.exports = config;
});
