'use strict';
define(function (require){
	var webApp = require('app'); 
	
	//组织机构树形页面
	webApp.controller('departmentTreeCtrl',  function($scope, $rootScope, jfRest, $http, jfGlobal, $location, jfLayer,$parse, $validation) {
		//用户查询参数
		$scope.queryUser = {};
		//部门树配置项
		$scope.deptTreeOpts = {
			isCheck : false,
			resource : "department.query",
			nodeClick : function(treeNode) {
				console.log("nodeClick...");
				jfRest.request('department','get', Tansun.param({'deptCode' : treeNode.id})).then(function(data){
					if(data.status == 200){
						$scope.department = data.data;
						$scope.treeNode=treeNode;
						
						$scope.queryUser['deptCode'] = data.data ? data.data['deptCode'] : '---';
						//angular.element('#departmentIds').val($scope.department.departmentIds);
						//查询用户
						$scope.deptUserGrid.search();
					}
				});
			}
		};
		//新增子部门
		$scope.addDepartmentNode = function(event) {
			if (!$scope.treeNode) {
				jfLayer.fail("请先选择节点");
			} else {
				$scope.addDepartment={};
				$scope.addDepartment.parentDeptCode=$scope.treeNode.id;
				$scope.addDepartment.parentDeptName=$scope.treeNode.name;
				$scope.modal('/sysmanage/department/addDepartChild.html', $scope.addDepartment, {
					title : '添加子部门',
					buttons:['确认', '取消'], 
					size : ['600px','400px'],
					callbacks : [$scope.addDeptCallback]
				});
				
			}
		}
		//部门新增回调
		$scope.addDeptCallback = function(result) {
			$scope.save(result);
		}
		
		$scope.delDepartment = function() {
			if (!$scope.department || $scope.department == {}) {
				jfLayer.fail("请先选择节点");
			} else if ($scope.treeNode.isParent) {
				jfLayer.fail("请选择叶子节点");
			}else {
				jfLayer.confirm('删除部门将会删除部门信息，确认删除？',function(){
				    jfRest.request('department','delete', Tansun.param($scope.department)).then(function(data) {
				        if(data.status == 200){
				            var msg = data.description ? data.description : '删除成功' ;
				            jfLayer.success(msg,function(){
				                $scope.deptTreeOpts.reload();
				            });
				        }else{
				            var msg = data.description ? data.description : '删除失败' ;
				            jfLayer.fail(msg) ;
				        }
				    });
				},function(){
				    jfLayer.closeAll();
				})
			}
		}
		$scope.update = function() {
			var param = Tansun.param($scope.department) ;
			$validation.validate($parse('departmentUpdateForm')($scope)).success(function(){
				jfRest.request('department', 'save', param).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description,function(){
							jfLayer.closeAll();
							$scope.deptTreeOpts.reload();
						}); 
					}else{
						jfLayer.fail(data.description);
					}
				});
			});
			
		};
		$scope.selectOrgForUpdate = {
	            header: ['机构名称', '机构编号'],
	            body: ['orgName', 'orgCode'],
	            text: 'ORG',
	            api: 'org',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.department.orgCode = item.orgCode;
	            	$scope.department.orgName = item.orgName;
	            }
	    };
		//用户列表配置项
		$scope.deptUserGrid = {
			params : $scope.queryUser,
			paging : true,
			resource : "userAndDeptRel.query",
			callback : function() {
				
			}
		};
		
		$scope.save = function(result) {
			var param = Tansun.param($scope.addDepartment) ;
//			$validation.validate($parse('departmentAddForm')($scope)).success(function(){
				jfRest.request('department', 'save', param).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description,function(){
							result.cancel();
							$scope.deptTreeOpts.reload();
						}); 
					}else{
						jfLayer.fail(data.description);
					}
				});
//			});
			
		};
		
	})
	
	webApp.controller('departmentAddCtrl',  function($scope, jfRest, $http, jfGlobal, $location, jfLayer,$parse, $validation) {
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
	            	$scope.addDepartment.orgCode = item.orgCode;
	            	$scope.addDepartment.orgName = item.orgName;
	            }
	    };
	})
});
	