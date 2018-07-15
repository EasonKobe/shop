'use strict';
define(function (require){

	var webApp = require('app'); 
	
	//租户列表页面
	webApp.controller('tenantCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $location,$validation,$parse) {
		
		//表格初始化信息 
		$scope.tenantList = {
			      checkType : 'radio', //当为checkbox时为多选
			      params :{}, //表格查询时的参数信息
			      paging : true ,//默认true,是否分页
			      resource : 'tenant.query' ,//列表的资源
			      callback : function() { //表格查询后的回调函数
			    	  
			      }
		};
		
		
		//设置失效（只有生效的数据才能设置失效）
		$scope.updateInvalid = function (event) {
			$scope.tenant = $scope.tenantList.checkedList(); 
			if (!$scope.tenant || $scope.tenant == {}) {
				jfLayer.fail("请选择有效数据！");
				return;
			}
			if ($scope.tenant.statusCd!=1) {
				jfLayer.fail("只有生效的数据才能设置失效！");
				return;
			}
			jfLayer.confirm("确定要设置为失效数据吗?",function(){
				jfRest.request('tenant','get', Tansun.param($scope.tenant)).then(function(data) {
					if (data.status == 200) {
						$scope.tenant = data.data;
						$scope.tenant.statusCd=2;
						$scope.$emit('to-parent', $scope.tenant);
						
						jfRest.request('tenant','save', Tansun.param($scope.tenant)).then(function(data){
							if(data.status == 200){
								jfLayer.success("设置成功！",function(){
									$scope.tenantList.search();
								});
							}else{
								jfLayer.fail(data.description);
							}
						});
					} 
				});
				},function(){}
			);
		
		}
		//批量导入
		$scope.importDialog = function(event) {
			$rootScope.$dialog(event, "tenantBatchImport");
		}
		
	});
	
	//---------保理商批量导入----start----------------
	webApp.controller('tenantBatchImportCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $validation, $parse) {
		
		$scope.back = function () {
            jfLayer.closeAll();
        }
		
		$scope.fileChanged = function (ele) {
            $scope.file = ele.files[0];
            $scope.$apply();
        };
        $scope.downLoad = function(){
        	location.href=ctx+"/static/template/tenantImp.xlsx";
         };
        $scope.importFile = function () {
            var fd = new FormData();
            if (!$scope.file) {
                jfLayer.alert("请选择导入数据！");
                return;
            }
            $scope.import={};
            fd.append('importFile', $scope.file);
            $http({
                method: 'POST',
                url: ctx + "/tenant/importTenant",
                data: fd,
                headers: {'Content-Type': undefined}, transformRequest: angular.identity
            }).success( function ( response )
                   {
                //上传成功的操作
                alert("uplaod success");
                }); 
        };
        
        $scope.confirmImp=function(){
        	jfLayer.closeAll();
        	$scope.search();
        };
	});
		
	//---------保理商批量导入----end----------------
	
	//租户信息页面
	webApp.controller('tenantAddCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $location,$validation,$parse) {
		$scope.tenant = {
			tenantId : $stateParams.tenantId,
			id : $stateParams.id,
		}
		$scope.commonData.tenantId = $stateParams.tenantId;
		
		$scope.getTenant = function() {
			if ($scope.tenant.tenantNum || $scope.tenant.id) {
				jfRest.request('tenant','get', Tansun.param($scope.tenant)).then(function(data) {
					if (data.status == 200) {
						$scope.tenant = data.data;
						$scope.$emit('to-parent', data.data);
					} 
				});
			}
		}
		$scope.getTenant();
		$scope.save = function (event) {
			if($scope.tenant.password!==$scope.tenant.repassword){
				jfLayer.fail("两次密码输入不一致，请重新输入！");
				return false;
			}
			$validation.validate($parse('tenantForm')($scope)).success(function(){//校验成功后, 进行保存操作 
				jfRest.request('tenant','save', Tansun.param($scope.tenant)).then(function(data) {
						if (data.status == 200) {	
							angular.extend($scope.tenant, data.data);
							$scope.$emit('to-parent', data.data);
							jfLayer.alert(data.description);
						} else {
							jfLayer.fail(data.description);
						}
					});
			});
		}
		
		$scope.effect = function (event) {
			if($scope.tenant.password!==$scope.tenant.repassword){
				jfLayer.fail("两次密码输入不一致，请重新输入！");
				return false;
			}
			$validation.validate($parse('tenantForm')($scope)).success(function(){//校验成功后, 进行保存操作 
				jfRest.request('tenant','effect', Tansun.param($scope.tenant)).then(function(data) {
					if (data.status == 200) {
						$scope.$emit('to-parent', data.data);
						jfLayer.alert(data.description);
					} else {
						jfLayer.fail(data.description);
					}
				});
			});
		}
		
		
		
	});
	
	
	
	//租户信息页面
	webApp.controller('tenantMenuCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $location,$validation,$parse) {
		
		$scope.queryMenu={}
		$scope.tenant={}
		if ($scope.commonData.tenantId) {
			$scope.queryMenu.roleCode = $scope.commonData.roleCode;
			$scope.queryMenu.tenantId = $scope.commonData.tenantId;
			$scope.tenant.tenantId=$scope.commonData.tenantId;
		};
		
		$scope.tenatMenu = {
			    isCheck : true,
			    params : $scope.queryMenu,
			    resource : 'tenantRoleMenu.query',
			    nodeCheck : function(treeNode) {
			    	$scope.menuNodeClick(this,treeNode);
			}
		};
		
		//菜单树选择事件
		$scope.menuNodeClick=function(tree,treeNode){
		    var getCheckedNodes = tree.getChecked(); 
		     var length = getCheckedNodes.length;
		     $scope.tenant.menuCodes = ''; //当前选中的资源权限code串
		     for (var i = 0; i < length; i++) {
		        if ($scope.tenant.menuCodes == '') {
		        	$scope.tenant.menuCodes = getCheckedNodes[i].id;
		        } else {
		        	$scope.tenant.menuCodes = $scope.tenant.menuCodes + ',' + getCheckedNodes[i].id;
		        }
		     }
		};
		
		$scope.saveRoleMenuRel=function(){
			if (!$scope.tenant.tenantId || $scope.tenant.tenantId == '') {
				jfLayer.fail("请先保存租户信息");
				return;
			} 
			if(!$scope.tenant.menuCodes){
				jfLayer.fail("无更新数据！");
			}else{
//				console.log($scope.roleIds);
				var param = Tansun.param($scope.tenant) ;
				jfRest.request('tenantRoleMenu', 'save', param).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description); 
					}else{
						jfLayer.fail(data.description);
					}
				});
			}
		}
	});
});
	