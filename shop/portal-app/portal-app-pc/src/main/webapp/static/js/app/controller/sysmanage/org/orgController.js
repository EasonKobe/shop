'use strict';
define(function (require){
	var webApp = require('app'); 
	
	//组织机构树形页面
	webApp.controller('orgTreeCtrl',  function($scope, jfRest, $http, jfGlobal, $location, jfLayer,$parse, $validation) {
		$scope.treeParams={}, $scope.queryUser={};
		
		//机构树配置项
		$scope.orgTreeOpts = {
	        isCheck : false,
	        params : $scope.treeParams,
	        resource : 'org.query',
	        nodeClick : function(treeNode) {
	        	jfRest.request('org','get', Tansun.param({'orgCode' : treeNode.id})).then(function(data){
					if(data.status == 200){
						$scope.org = data.data;
						$scope.treeNode=treeNode;					
						$scope.queryUser['orgCode'] = data.data ? data.data['orgCode'] : '---';
						//angular.element('#departmentIds').val($scope.department.departmentIds);
						//查询用户
						$scope.userGrid.search(treeNode);
						//计算当前机构等级
						$scope.calorgLevelCd(treeNode);	
					}
				});
	        }
	    }
		
		$scope.resetQueryUserParam= function(treeNode) {
			typeof($scope.org=="undefined")?$scope.org={}:$scope.org;
			$scope.queryUser={
					'orgCode':$scope.org.orgCode
			};
			$scope.userGrid.params = $scope.queryUser;
			$scope.userGrid.search();
		}
		
		$scope.callback = function (result) {
			$scope.save(result);
		};
		
		
		$scope.update = function() {
			var param = Tansun.param($scope.org) ;
//			$validation.validate($parse('orgForm')($scope)).success(function(){
				jfRest.request('org', 'save', param).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description,function(){
							jfLayer.closeAll();
							$scope.treeParams.openNodeId=$scope.treeNode.pId;
							$scope.orgTreeOpts.reload();
						}); 
					}else{
						jfLayer.fail(data.description);
					}
				});
//			});
		}
		$scope.delOrg = function() {
			if (!$scope.org || $scope.org == {}) {
				jfLayer.fail("请选择节点");
			} else if ($scope.treeNode.isParent) {
				jfLayer.fail("请选择叶子节点");
			}else {
				jfLayer.confirm('将会删除机构信息，确认删除？',function(){
				    jfRest.request('org','delete', Tansun.param($scope.org)).then(function(data) {
				        if(data.status == 200){
				            var msg = data.description ? data.description : '删除成功' ;
				            jfLayer.success(msg,function(){
				            	$scope.treeParams.openNodeId=$scope.treeNode.pId;
				            	$scope.orgTreeOpts.reload();
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
		};
		//用户列表配置项
		$scope.userGrid = {
			params : $scope.queryUser,
			paging : true,
			resource : "userAndOrgRel.query",
			callback : function() {
			}
		};
		
		//新增子机构
		$scope.addChildOrg = function () {
			if (!$scope.org || $scope.org == {}) {
				jfLayer.fail("请先选择机构");
			} 
			$scope.addOrg={};
			$scope.addOrg.parentOrgCode=$scope.treeNode.id;
			$scope.addOrg.parentOrgName=$scope.treeNode.name;
			$scope.addOrg.level=$scope.treeNode.level;
			$scope.modal('/sysmanage/org/addOrg.html', $scope.addOrg, {
						title:'添加机构树', 
						buttons:['确认', '取消'], 
						size : ['600px','400px'],
						callbacks : [$scope.callback]
			});
		}
		
		$scope.save = function(result) {
			var param = Tansun.param($scope.addOrg);
			
//			$validation.validate($parse('orgAddForm')($scope)).success(function(){
				jfRest.request('org', 'save', param).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description,function(){
							result.cancel();
							$scope.treeParams.openNodeId=$scope.addOrg.orgCode;
							$scope.orgTreeOpts.reload();
						}); 
					}else{
						jfLayer.fail(data.description);
					}
				});
//			});
		};
		$scope.calorgLevelCd=function(treeNode){
			var nodeLevel;
			if(treeNode.level<10){
				nodeLevel="0"+treeNode.level;
			}
				$scope.org.orgLevelCd="LV"+nodeLevel;

		}
	})
	
	webApp.controller('orgAddCtrl',  function($scope, jfRest, $http, jfGlobal, $location, jfLayer,$parse, $validation) {
		/*$scope.save = function() {
			var param = Tansun.param($scope.addOrg);
			$validation.validate($parse('orgAddForm')($scope)).success(function(){
				jfRest.request('org', 'save', param).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description,function(){
							jfLayer.closeAll();
							$scope.treeParams.openNodeId=$scope.addOrg.orgCode;
							$scope.reloadTree();
						}); 
					}else{
						jfLayer.fail(data.description);
					}
				});
			});
		};*/
		$scope.calorgLevelCd=function(level){
			var nodeLevel;
		if(level){
			if(level<10){
				nodeLevel="0"+level;
			}
				$scope.addOrg.orgLevelCd="LV"+nodeLevel;
		}else{
			$scope.addOrg.orgLevelCd="LV00";
		}
		}
		$scope.calorgLevelCd($scope.addOrg.level+1);
	})
});
	