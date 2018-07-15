'use strict';

define(function(require, exports, module) {
	var config = [
	 //[路由地址,页面地址,js地址]
 	 //系统管理start            
 	 ['/index', 'index/index.html', ''],
 	 ['/login', 'login.html', ''],
 	 // 找回密码
 	 ['/forgotPwd','forgotPwd.html?username', ''],
 	 ['/user/usermg/personMgList', 'sysmanage/user/personMgList.html', 'sysmanage/user/userController.js'],
 	 ['/user/usermg/personAddMsg?userNum', 'sysmanage/user/edit/personAddMessage.html', 'sysmanage/user/userController.js'],
 	 ['/updatePswd', 'sysmanage/user/edit/personUpdatePswd.html', 'sysmanage/user/userController.js'],
 	 ['/role/tree', 'sysmanage/role/roleTree.html', 'sysmanage/role/roleController.js'],
 	 ['/entpRole/tree', 'sysmanage/role/entpRoleTree.html', 'sysmanage/role/entpRoleController.js'],
 	 ['/department/tree', 'sysmanage/department/departmentTree.html', '/sysmanage/department/departmentController.js'],
 	 ['/org/tree', 'sysmanage/org/orgTree.html', '/sysmanage/org/orgController.js'],
 	 ['/menu/tree', 'sysmanage/menu/menuTree.html', '/sysmanage/menu/menuController.js'],
 	 ['/dict/list', 'sysmanage/dict/dictList.html', '/sysmanage/dict/dictController.js'],
	 ['/param/list', 'sysmanage/param/paramList.html', '/sysmanage/param/paramController.js'],
 	 ['/dataauth/query', 'sysmanage/dataauth/dataauthList.html', '/sysmanage/dataauth/dataauthController.js'],
 	 ['/tenant/tenantmg/tenantMgList', 'sysmanage/tenant/tenantMgList.html', 'sysmanage/tenant/tenantController.js'],
 	 ['/dataauth/query', 'sysmanage/dataauth/dataauthList.html', '/sysmanage/dataauth/dataauthController.js'],
 	 ['/tenant/tenantmg/tenantAddMsg?id&tenantId', '/sysmanage/tenant/edit/tenantAddMessage.html', 'sysmanage/tenant/tenantController.js'],
 	 ['/doc', 'common/doc.html', ''],
 	 ['/tenant/tenantmg/tenantViewMsg?id&tenantId', '/sysmanage/tenant/view/tenantBaseInfo.html', 'sysmanage/tenant/tenantController.js']
 	 //系统管理end	      		
 		      		];
	module.exports = config;
});
