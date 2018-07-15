'use strict';
define(function (require){

	var webApp = require('app'); 
	
	//数据权限页面
	webApp.controller('dataauthCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $location,$validation,$parse) {
		$scope.queryDataauthModelParam = {};
		
		$scope.dataAuthGridOpts = {
			checkType : 'radio',
			params : {},
			paging : true,
			resource : "dataauth.query",	
		};
		
		/*//重置
		$scope.reset = function() {
			$scope.queryDataauth={};
			$scope.dataAuthGridOpts.params = $scope.queryDataauth;
			$scope.dataAuthGridOpts.search();
		};
		*/
		/*$scope.dataAuthModalGridOpts = {
			checkType : 'radio',
			params : $scope.queryDataauthModelParam,
			paging : true,
			resource : "dataauth.modelSelector",
		};*/
		
		$scope.queryModel = {
	            header: [ '数据权限类型'],
	            body: ['modelName'],
	            text: 'MODEL',
	            api: 'dataauth',
	            method: 'modelSelector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.dataAuthGridOpts.params.modelCode = item.modelCode;
	            	$scope.dataAuthGridOpts.params.modelName = item.modelName;
	            }
	    };
		//机构选择框
		$scope.queryOrg = {
	            header: ['机构名称', '机构编号'],
	            body: ['orgName', 'orgCode'],
	            text: 'ORG',
	            api: 'org',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.dataAuthGridOpts.params.orgCode = item.orgCode;
	            	$scope.dataAuthGridOpts.params.orgName = item.orgName;
	            }
	    };
		//部门选择框
		$scope.queryDept = {
	            header: ['部门名称', '部门编号'],
	            body: ['deptName', 'deptCode'],
	            text: 'dept',
	            api: 'department',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.dataAuthGridOpts.params.deptCode = item.deptCode;
	            	$scope.dataAuthGridOpts.params.deptName = item.deptName;
	            }
	    };
		//角色选择框
		$scope.queryRole = {
	            header: ['角色名称', '角色编号'],
	            body: ['roleName', 'roleCode'],
	            text: 'role',
	            api: 'role',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.dataAuthGridOpts.params.roleCode = item.roleCode;
	            	$scope.dataAuthGridOpts.params.roleName = item.roleName;
	            }
	    };
		//用户选择框
		$scope.queryUser = {
	            header: ['用户名称', '用户编号'],
	            body: ['loginName', 'userNum'],
	            text: 'user',
	            api: 'sysUser1',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.dataAuthGridOpts.params.userNum = item.userNum;
	            	$scope.dataAuthGridOpts.params.loginName = item.loginName;
	            }
	    };
		//新增、修改界面弹出
		$scope.addDataauth = function() {
			$scope.modal("/sysmanage/dataauth/addDataauth.html", $scope.dataauth={}, {
				title : '基本信息',
				buttons:['确认', '取消'], 
				size : ['600px','500px'],
				callbacks : [$scope.addAuthCallback]
			});
		};
		$scope.addAuthCallback = function(result) {
			$scope.addDataauthSub(result);
		};
		
		$scope.addDataauthSub = function(result) {
//			$validation.validate($parse('dataauthForm')($scope)).success(function(){//校验成功后, 进行保存操作 
			
			var dataauth = Tansun.param($scope.dataauth) ;
			
			jfRest.request('dataauth', 'save', dataauth).then(function(data){
				if(data.status == 200){
					jfLayer.success(data.description,function(){
						$scope.dataAuthGridOpts.search() ;
						result.cancel();
					}); 
				}else{
					jfLayer.fail(data.description);
				}
			});
//		  });
		};
		
	    //查询get
		$scope.getDataauth = function(event) { 
			$scope.dataauth = $scope.dataAuthGridOpts.checkedList(); 
			if (!$scope.dataauth ) {
				jfLayer.fail("请选择有效数据！");
				return;
			}
			$scope.modal("/sysmanage/dataauth/addDataauth.html", $scope.dataauth, {
				title : '基本信息',
				buttons:['确认', '取消'], 
				size : ['600px','500px'],
				callbacks : [$scope.addAuthCallback]
			});
		};
		
	});
	
	
	//新增页面
	webApp.controller('dataauthAddCtrl',  function($scope, jfRest, $http, jfGlobal, $location, jfLayer,$validation,$parse) {
		//机构选择框
		$scope.selectOrg = {
            header: ['机构名称', '机构编号'],
            body: ['orgName', 'orgCode'],
            text: 'ORG',
            api: 'org',
            method: 'selector',
            pageShow:true,
            params: {
            },
            callback: function (item) {
            	$scope.dataauth.orgCode = item.orgCode;
            	$scope.dataauth.orgName = item.orgName;
            }
	    };
		
		//部门选择框
		$scope.selectDept = {
	            header: ['部门名称', '部门编号'],
	            body: ['deptName', 'deptCode'],
	            text: 'dept',
	            api: 'department',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.dataauth.deptCode = item.deptCode;
	            	$scope.dataauth.deptName = item.deptName;
	            }
	    };
		
		//角色选择框
		$scope.selectRole = {
	            header: ['角色名称', '角色编号'],
	            body: ['roleName', 'roleCode'],
	            text: 'role',
	            api: 'role',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.dataauth.roleCode = item.roleCode;
	            	$scope.dataauth.roleName = item.roleName;
	            }
	    };
		
		
		//用户选择框
		$scope.selectUser = {
	            header: ['用户名称', '用户编号'],
	            body: ['loginName', 'userNum'],
	            text: 'user',
	            api: 'sysUser1',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.dataauth.userNum = item.userNum;
	            	$scope.dataauth.loginName = item.loginName;
	            }
	    };
		//数据权限模式选择框
		$scope.selectModel = {
	            header: [ '数据权限类型'],
	            body: ['modelName'],
	            text: 'MODEL',
	            api: 'dataauth',
	            method: 'modelSelector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.dataauth.modelCode = item.modelCode;
	            	$scope.dataauth.modelName = item.modelName;
	            }
	    };
		//新增、修改权限设置
		$scope.addConditons = function() {
			$scope.conditions.push({});
		};
		 //刪除权限设置
		$scope.delConditons=function(){
			$('input:radio[name="id"]:checked').each(function(){
				$(this).parent().parent().remove();
			});
		  
		 };
		/*$scope.addDataauthSub = function() {
			$validation.validate($parse('dataauthForm')($scope)).success(function(){//校验成功后, 进行保存操作 
			
			var dataauth = Tansun.param($scope.dataauth) ;
			
			jfRest.request('dataauth', 'save', dataauth).then(function(data){
				if(data.status == 200){
					jfLayer.success(data.description,function(){
						$scope.search() ;
						jfLayer.closeAll();
					}); 
				}else{
					jfLayer.fail(data.description);
				}
			});
		  });
		};*/
	});
	
});