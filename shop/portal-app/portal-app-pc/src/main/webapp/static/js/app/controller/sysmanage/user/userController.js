'use strict';
define(function (require){

	var webApp = require('app'); 
	
	//用户列表
	webApp.controller('userListCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $location) {
		$scope.userList = {
				checkType : 'radio', //当为checkbox时为多选
				params : {}, //表格查询时的参数信息
				paging : true ,//默认true,是否分页
				resource : 'sysUser1.query' ,//列表的资源
				callback : function() { //表格查询后的回调函数
				}
		  };  
		$scope.resetPassword =function() {
			$scope.user = $scope.userList.checkedList(); 
			if (!$scope.userList.validCheck()) {
				return ;
			}
			jfLayer.confirm("确定要重置“"+$scope.user.loginName+"”的密码吗（重置完为111111）?",function(){
				jfRest.request('sysUser1','resetPassword', Tansun.param({'id' :$scope.user.id})).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description,function(){
							$scope.userList.search();
						});
					}else{
						jfLayer.fail(data.description);
					}
				});
				},function(){}
			);
		}
		
		
	});
	
	//用户信息页面
	webApp.controller('userAddCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer,  $location,$validation,$parse) {
		//新增的时候userNum为空，默认生成一个唯一编码
		$scope.commonData.userNum=$stateParams.userNum==undefined?Tansun.GUID("user"):$stateParams.userNum;
		$scope.user = {
			userNum : $scope.commonData.userNum,
			id : $stateParams.businessKey,
			taskId : $stateParams.taskId
		}
		$scope.getUser = function() {
			if ($scope.user.userNum || $scope.user.id) {
				jfRest.request('sysUser1','get', Tansun.param($scope.user)).then(function(data) {
					if (data.status == 200) {
						$scope.user = data.data;
						$scope.$emit('to-parent', $scope.user);
					} 
				});
			}
		}
		$scope.getUser();
		
		$scope.save = function (buttonType) {
			$validation.validate($parse('userForm')($scope)).success(function(){//校验成功后, 进行保存操作 
				    $scope.user.buttonType = buttonType;
					jfRest.request('sysUser1','save', Tansun.param($scope.user)).then(function(data) {
						if (data.status == 200) {
							jfLayer.success(data.description,function(){
								$scope.$emit('to-parent', data.data==undefined?$scope.user:data.data);
							});
						} else {
							jfLayer.fail(data.description);
						}
					});
			});
		}
	});
	
	//用户机构信息
	webApp.controller('userOrgRelManageCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $location) {
		$scope.queryParam = {userNum: $scope.commonData==null?'':$scope.commonData.userNum}
		$scope.userOrgList = {
				checkType : 'radio', //当为checkbox时为多选
				params : $scope.queryParam, //表格查询时的参数信息
				paging : true ,//默认true,是否分页
				resource : 'userAndOrgRel.query' ,//列表的资源
				callback : function() { //表格查询后的回调函数
				}
		  };
		$scope.userAndOrgRel={};
		$scope.saveOrg = function (result) {
			//默认做了校验，此处无需再校验
			//$validation.validate($parse('userAndOrgRelForm')($scope)).success(function(){//校验成功后, 进行保存操作 
				jfRest.request('userAndOrgRel','save', Tansun.param(result.scope.userAndOrgRel)).then(function(data) {
					if (data.status == 200) {
						jfLayer.success(data.description,function(){
							$scope.userOrgList.search();
							result.cancel();
						});
					} else {
						jfLayer.fail(data.description);
					}
				},function(data){
					jfLayer.fail(data.data.description);
				});
			//});
		}
	});
	//用户机构维护
	webApp.controller('userAndOrgRelEditCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer,  $location,$parse, $validation) {
		
		$scope.get = function () {
			
			if(!$scope._modal.param.id){
				$scope.userAndOrgRel = {
						userNum : $scope.queryParam.userNum
				}
			}else{
				$scope.userAndOrgRel = {
						id : $scope._modal.param.id
				}
				jfRest.request('userAndOrgRel','get', Tansun.param($scope._modal.param)).then(function(data) {
					$scope.userAndOrgRel = data.data;
				});
			}
		}
		
		$scope.get();
		
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
	            	$scope.userAndOrgRel.orgCode = item.orgCode;
	            	$scope.userAndOrgRel.orgName = item.orgName;
	            }
	    };
		//接收弹出框确认事件
		$scope.confirm = function(){
			$scope.save();
		}
	})
	
	//用户部门信息
	webApp.controller('userAndDeptRelManageCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer,  $location) {
		$scope.queryParam = {userNum: $scope.commonData==null?'':$scope.commonData.userNum}
		$scope.userDeptList = {
				checkType : 'radio', //当为checkbox时为多选
				params : $scope.queryParam, //表格查询时的参数信息
				paging : true ,//默认true,是否分页
				resource : 'userAndDeptRel.query' ,//列表的资源
				callback : function() { //表格查询后的回调函数
				}
		  };
		$scope.save = function (result) {
			//手动进行校验
			//$validation.validate($parse('userAndDeptRelForm')($scope)).success(function(){//校验成功后, 进行保存操作 
				jfRest.request('userAndDeptRel','save', Tansun.param(result.scope.userAndDeptRel)).then(function(data) {
					if (data.status == 200) {
						jfLayer.success(data.description,function(){
							$scope.userDeptList.search();
							result.cancel();
						});
					} else {
						jfLayer.fail(data.description);
					}
				},function(data){
					jfLayer.fail(data.data.description);
				});
			//});
		}
	});
	//用户部门维护
	webApp.controller('userAndDeptRelEditCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer,  $location,$parse, $validation) {
		
		$scope.get = function () {
			if(!$scope._modal.param.id){
				$scope.userAndDeptRel = {
						userNum : $scope.queryParam.userNum
				}
			}else{
				$scope.userAndDeptRel = {
						id : $scope._modal.param.id
				}
				jfRest.request('userAndDeptRel','get', Tansun.param($scope._modal.param)).then(function(data) {
					$scope.userAndDeptRel = data.data;
				});
			}
		}
		
		$scope.get();
		$scope.selectUserAndOrgRel = {
	            header: ['机构名称', '机构编号'],
	            body: ['orgName', 'orgCode'],
	            text: 'ORG',
	            api: 'userAndOrgRel',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            	userNum: $scope.commonData==null?'':$scope.commonData.userNum
	            },
	            callback: function (item) {
	            	$scope.userAndDeptRel.orgCode = item.orgCode;
	            	$scope.userAndDeptRel.orgName = item.orgName;
	            }
	    };
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
	            	$scope.userAndDeptRel.deptCode = item.deptCode;
	            	$scope.userAndDeptRel.deptName = item.deptName;
	            }
	    };
		//接收弹出框确认事件
		$scope.confirm = function(){
			$scope.save();
		}
	})
	
	//用户角色信息
	webApp.controller('userAndRoleManageCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer,  $location) {
		$scope.queryParam = {userNum: $scope.commonData==null?'':$scope.commonData.userNum}
		$scope.userRoleList = {
				checkType : 'radio', //当为checkbox时为多选
				params : $scope.queryParam, //表格查询时的参数信息
				paging : true ,//默认true,是否分页
				resource : 'userAndRoleRel.query' ,//列表的资源
				callback : function() { //表格查询后的回调函数
				}
		  };
		$scope.save = function (result) {
			//手动进行校验
			//$validation.validate($parse('userAndRoleRelForm')($scope)).success(function(){//校验成功后, 进行保存操作 
				jfRest.request('userAndRoleRel','save', Tansun.param(result.scope.userAndRoleRel)).then(function(data) {
					if (data.status == 200) {
						jfLayer.success(data.description,function(){
							$scope.userRoleList.search();
							result.cancel();
						});
					} else {
						jfLayer.fail(data.description);
					}
				},function(data){
					jfLayer.fail(data.data.description);
				});
			//});
		}
	});
	//用户角色维护
	webApp.controller('userAndRoleRelEditCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer,  $location,$parse, $validation) {
		
		$scope.get = function () {
			
			if(!$scope._modal.param.id){
				$scope.userAndRoleRel = {
						userNum : $scope.queryParam.userNum
//						,
//						statusCd:'1',
//						defaultInd:'0'
				}
			}else{
				$scope.userAndRoleRel = {
						id : $scope._modal.param.id
				}
				jfRest.request('userAndRoleRel','get', Tansun.param($scope._modal.param)).then(function(data) {
					$scope.userAndRoleRel = data.data;
				});
			}
		}
		
		$scope.get();
		
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
	            	$scope.userAndRoleRel.roleCode = item.roleCode;
	            	$scope.userAndRoleRel.roleName = item.roleName;
	            }
	    };
		//接收弹出框确认事件
		$scope.confirm = function(){
			$scope.save();
		}
	})
	
	//用户信息页面
	webApp.controller('uptPswdCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer,  $location) {
		$scope.user = {
				realname : $scope.getUser().realname,
				loginName:$scope.getUser().loginName,
				salt:$scope.getUser().salt,
		}
		
		$scope.updatePswd = function () {
			if($scope.user.newPswd!==$scope.user.renewPswd){
				jfLayer.fail("两次密码输入不一致，请重新输入！");
				return false;
			}
			jfRest.request('sysUser1','updatePswd', Tansun.param($scope.user)).then(function(data) {
				if (data.status == 200) {
					jfLayer.success(data.description,function(){
					});
				} else {
					jfLayer.fail(data.description);
				}
			});
		}
		
	})
});
	