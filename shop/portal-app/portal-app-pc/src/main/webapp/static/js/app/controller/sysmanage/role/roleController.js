'use strict';
define(function (require){
	var angular = require('angular'); 
	
	//组织机构树形页面
	Tansun.controller('roleTreeCtrl',  function($scope, $rootScope,jfRest, $http, jfGlobal, $location, jfLayer,$timeout) {
		//按钮事件权限列表
		$scope.eventResList = {
				//checkType : 'checkbox', //当为checkbox时为多选
				params :$scope.queryMenuEventParam, //表格查询时的参数信息
				paging : true ,//默认true,是否分页
				resource : 'menuEventDataAuthManage.query',//列表的资源
				callback : function() { //表格查询后的回调函数
				
				}
			};
		//用户列表
		$scope.userList = {
		//		checkType : 'true', //当为checkbox时为多选
				params :{}, //表格查询时的参数信息
				paging : true ,//默认true,是否分页
				resource : 'roleUser.query' ,//列表的资源
				callback : function() { //表格查询后的回调函数
				}
			};
		$scope.queryMenuEventParam={}
		//资源列表
		$scope.menuEventDataAuthList = {
				//checkType : 'checkbox', //当为checkbox时为多选
				params :$scope.queryMenuEventParam, //表格查询时的参数信息
				paging : false ,//默认true,是否分页
				resource : 'menuEventDataAuthManage.query',//列表的资源
				callback : function() { //表格查询后的回调函数
				}
			};
		$scope.queryMenu={};
		//菜单树
        $scope.menu = {
	            isCheck : true,
	            resource : 'roleMenu.query',
	            params :$scope.queryMenu,
	            nodeClick : function(treeNode) {
	            	$scope.menuNodeClick(treeNode);
	            },
		        nodeCheck : function(treeNode) {
		        	$scope.menuNodeCheck(this,treeNode);
		        }
	        }
		//角色已分配树
        $scope.menuAssigned = {
	            isCheck : false,
	            params :$scope.queryMenu,
	            resource : 'roleMenu.queryAssigned',
	            nodeClick : function(treeNode) {
	            	$scope.menuAssignedNodeClick(treeNode);
	            }
	        }
		//角色树
        $scope.radioRole = {
	            isCheck : false,
	            resource : 'role.query',
	            nodeClick : function(treeNode) {
	            $scope.nodeClick(treeNode);
	            }
	        }
		//菜单树选择事件
		$scope.menuNodeCheck=function(tree,treeNode){
			var getCheckedNodes = tree.getChecked(); 
		     var length = getCheckedNodes.length;
		     $scope.roleIds = ''; //当前选中的资源权限code串
		     for (var i = 0; i < length; i++) {
		        if ($scope.roleIds == '') {
		        	$scope.roleIds = getCheckedNodes[i].id;
		        } else {
		        	$scope.roleIds = $scope.roleIds + ',' + getCheckedNodes[i].id;
		        }
		     }
		     $("#roleIds").val($scope.roleIds);
		     
		    var child = angular.element('table[name="eventResList"]').find('tbody input[type="checkbox"]');
			child.each(function(index, item){
				if($scope.roleIds&&$scope.roleIds.indexOf(item.value)>=0){
					item.checked = true;
				}else{
					item.checked = false;
				}
			});
			var form = layui.form;
			$timeout(function(){
				layui.form.render('checkbox');
			});
		};
		//菜单树选择事件,查看按钮事件列表
		$scope.menuNodeClick=function(treeNode){
			//已选菜单，与roleIds分开 roleIds是用来提交，这个是用来判断事件是否勾选
			$scope.checkedRoleIds =$scope.roleIds;
			if(!$scope.checkedRoleIds){
				var getCheckedNodes = treeNode; 
			     var length = getCheckedNodes.length;
			     $scope.checkedRoleIds = ''; //当前选中的资源权限code串
			     for (var i = 0; i < length; i++) {
			        if ($scope.checkedRoleIds == '') {
			        	$scope.checkedRoleIds = getCheckedNodes[i].id;
			        } else {
			        	$scope.checkedRoleIds = $scope.checkedRoleIds + ',' + getCheckedNodes[i].id;
			        }
			     }
			}
		     
			//刷新事件
			$scope.queryMenuEventParam.menuCodes=$scope.findSubMenuCodes(treeNode);
			$scope.queryMenuEventParam.returnDataAuth=false;
			if(treeNode.isParent){
				$scope.queryMenuEventParam.menuCodes=$scope.findSubMenuCodes(treeNode);
			}else{
				$scope.queryMenuEventParam.menuCodes=treeNode.id;
			}
			
/*			$scope.queryMenuEventParam.menuCodes=treeNode.id;
			$scope.queryMenuEventParam.returnDataAuth=false;*/
			$scope.eventResList.search();
			
			
			
		};
		
		//按钮事件列表 回调函数，主要用于处理默认已选
		$scope.eventResListCallBack = function(data){
			if(data && data.status == 200){
				var itemArr = $scope.eventResList.checkedList();
				if(!itemArr){
					itemArr = [];
				}
				if(data.data&&data.data.grid&&data.data.grid.list){
					for(var i=0;i<data.data.grid.list.length;i++){
						if ($scope.checkedRoleIds && $scope.checkedRoleIds.indexOf(data.data.grid.list[i].menuCode) >= 0) {
							itemArr.push(data.data.grid.list[i]);
						}
					}
				}
				//$scope.checkItemMap.put("eventResList", itemArr);
			}
			
			$timeout(function(){
				var child = angular.element('table[name="eventResList"]').find('tbody input[type="checkbox"]');
				child.each(function(index, item){
					if($scope.checkedRoleIds&&$scope.checkedRoleIds.indexOf(item.value)>=0){
						item.checked = true;
					}else{
						item.checked = false;
					}
				});
				var form = layui.form;
				//延迟2秒，进行渲染
				layui.form.render('checkbox');
			});
		}
		//角色树点击事件
		//1.展开树
		//2.刷新角色信息
		//3.刷新菜单树及菜单下事件列表
		//4.刷新已分配菜单树结构及 事件数据权限列表的
		//5。刷新用户列表
		$scope.nodeClick = function(treeNode) {
			jfRest.request('role','get', Tansun.param({'roleCode' : treeNode.id})).then(function(data){
				if(data.status == 200){
					//2.刷新角色信息
					$scope.role = data.data; 
					$scope.treeNode=treeNode;
					
					//3.刷新菜单树及菜单下事件列表
					//刷新菜单树结构
					$scope.queryMenu.roleCode=$scope.role.roleCode;
					$scope.menu.reload();
					//刷新菜单的按钮事件
					$scope.queryMenuEventParam.roleCode=$scope.role.roleCode;
					$scope.eventResList.search();
					//4.刷新已分配菜单树结构及 事件数据权限列表的
					//刷新已分配菜单树结构
//					$scope.menuAssignedReloadTree();
					
					$scope.menuAssigned.reload();
					$scope.menuEventDataAuthList.search();
					//刷新默认数据权限
					$scope.loadDefaultDataauth();
					
					
					//5。刷新用户列表
					$scope.userList.params.roleCode=$scope.role.roleCode;
					$scope.userList.search();
				}
			});
		}
		//重置用户查询条件
		$scope.resetQueryParam=function(){
			//刷新用户表数据 
			$scope.userList.params={};
		}
		
		$scope.saveRoleMenuRel=function(){
			if (!$scope.role || $scope.role == {}) {
				jfLayer.fail("请先选择角色");
			} 
			var roleIds=$scope.roleIds;
			if(!$scope.roleIds){
				jfLayer.fail("无更新数据！");
			}else{
				console.log($scope.roleIds);
				$scope.postjson={};
				$scope.postjson.menuCodes=$scope.roleIds;
				$scope.postjson.roleCode=$scope.role.roleCode;
				var param = Tansun.param($scope.postjson) ;
				jfRest.request('roleMenu', 'save', param).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description); 
					}else{
						jfLayer.fail(data.description);
					}
				});
			}
		}
		
		$scope.addRoleNode = function() {
			if (!$scope.role || $scope.role == {}) {
				jfLayer.fail("请先选择节点");
			} else {
				$scope.role2={};
				$scope.role2.parentRoleCode=$scope.treeNode.id;
				$scope.role2.parentRoleName=$scope.treeNode.name;
				$scope.modal('/sysmanage/role/addRole.html',
						$scope.role2, {
						title : '新增角色',
						buttons : [ '确认', '取消' ],
						size : [ '500px', '400px' ],
						callbacks : [ $scope.addRoleSub ]
						});
			}
		}
		//添加根树节点
		$scope.addRole = function() {
			$scope.role2={}; 
			$scope.modal('/sysmanage/role/addRole.html',
			$scope.role2, {
				title : '新增角色',
				buttons : [ '确认', '取消' ],
				size : [ '500px', '400px' ],
				callbacks : [ $scope.addRoleSub ]
				});
		}
		$scope.updateRole=function(){
//			if($scope.role.roleName){
//				jfLayer.fail("角色名称不能为空");
//			}
			var param = Tansun.param($scope.role) ;
			jfRest.request('role', 'save', param).then(function(data){
				if(data.status == 200){
					jfLayer.success(data.description); 
					$scope.radioRole.reload();
				}else{
					jfLayer.fail(data.description);
				}
			});
		}
		
		$scope.delRole = function() {
			if (!$scope.role || $scope.role == {}) {
				jfLayer.fail("请先选择节点");
			}else if ($scope.treeNode.isParent) {
				jfLayer.fail("请选择叶子节点");
			} else {
				jfLayer.confirm('将会删除角色信息，确认删除？',function(){
				    jfRest.request('role','delete', Tansun.param($scope.role)).then(function(data) {
				        if(data.status == 200){
				            var msg = data.description ? data.description : '删除成功' ;
				            jfLayer.success(msg,function(){
				                $scope.reloadRoleTree() ;
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
		
		$scope.findSubMenuCodes = function(treeNode){
			var menus = "";
			if(treeNode.isParent){
				var childrens =treeNode.children;
				for(var i=0;i<childrens.length;i++){
					if(!childrens[i].isParent){
						menus = menus+childrens[i].id+","
					}else{
						menus = menus+$scope.findSubMenuCodes(childrens[i])+","
					}
					
				}
			}
			return menus;
		}
		//已分配菜单树选择事件
		$scope.queryMenuEventParam.menuCodes='-1';
		$scope.menuAssignedNodeClick=function(treeNode){
			
			//刷新事件
			$scope.dataauth ={
					roleCode:$scope.role.roleCode,
					userNum:'*',
					orgCode:'*',
					deptCode:'*',
					bizScene:treeNode.id,
					bizSceneName:treeNode.name,
					clntendId:'*',
					eventCode:'*',
					queryType:'0'
			}
			
			$scope.queryMenuEventParam.returnDataAuth=true;
			if(treeNode.type=='view'){
				$scope.dataauth.show=true;
				jfRest.request('dataauth','get', Tansun.param($scope.dataauth)).then(function(data){
					if(data.status == 200){
						var bizSceneName = $scope.dataauth.bizSceneName;
						var bizScene = $scope.dataauth.bizScene;
						var roleCode = $scope.dataauth.roleCode;
						$scope.dataauth = data.data;
						$scope.dataauth.bizSceneName = bizSceneName;
						$scope.dataauth.bizScene = bizScene;
						$scope.dataauth.roleCode = roleCode;
						$scope.dataauth.oriModelName=$scope.dataauth.modelName
					}
				});
				$scope.queryMenuEventParam.menuCodes=$scope.findSubMenuCodes(treeNode);
		        //$scope.queryMenuEventParam.menuCodes=treeNode.id;
			}else{
				$scope.dataauth={}
				$scope.dataauth.show=false;
				$scope.queryMenuEventParam.menuCodes='';
			}
			$scope.menuEventDataAuthList.search();
/*			$rootScope.clearChecked('menuEventDataAuthList');*/
		};
		
		$scope.loadDefaultDataauth= function () {
			var params={
					roleCode:$scope.role.roleCode,
					userNum:'*',
					orgCode:'*',
					deptCode:'*',
					bizScene:'*',
					clntendId:'*',
					eventCode:'*',
					queryType:'0'
			}
			jfRest.request('dataauth','get', Tansun.param(params)).then(function(data){
				if(data.status == 200){
					$scope.defaultDataauth = data.data;
					$scope.defaultDataauth.oriModelName=$scope.defaultDataauth.modelName
				}
			});
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
	            	if(!$scope.role||!$scope.role.roleCode){
	            		jfLayer.fail("请先选择角色！");
	            		return;
	            	}
	            	var dataauth={
	            			roleCode:$scope.role.roleCode,
	            			modelCode:item.modelCode,
	            			id: $scope.defaultDataauth.id
	            	}
	            	$scope.defaultDataauth.modelName=$scope.defaultDataauth.oriModelName;
	            	dataauth = Tansun.param(dataauth);
	            	jfLayer.confirm('确认选择模式['+item.modelName+']？',function(){
	            		jfRest.request('dataauth', 'save', dataauth).then(function(data){
		    				if(data.status == 200){
		    					$scope.defaultDataauth.modelName = item.modelName;
		    					$scope.defaultDataauth.modelCode = item.modelCode;
		    					if(!$scope.defaultDataauth.id){
		    						$scope.defaultDataauth.id=data.data.id;
		    					}
		    					jfLayer.success(data.description); 
		    				}else{
		    					jfLayer.fail(data.description);
		    				}
		    			});
	    			},function(){
	    				
	    			});
	            	
	            	
	            }
	    };
		//数据权限模式选择框
		$scope.selectModel2 = {
	            header: [ '数据权限类型'],
	            body: ['modelName'],
	            text: 'MODEL',
	            api: 'dataauth',
	            method: 'modelSelector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	if(!$scope.dataauth||!$scope.dataauth.bizScene){
	            		jfLayer.fail("请先选择业务场景！");
	            		return;
	            	}
	            	$scope.dataauth.modelCode=item.modelCode;
	            	$scope.dataauth.modelName=$scope.dataauth.oriModelName;
	            	jfLayer.confirm('确认选择模式['+item.modelName+']？',function(){
	            		jfRest.request('dataauth', 'save', Tansun.param($scope.dataauth)).then(function(data){
	            			if(data.status == 200){
		    					$scope.dataauth.modelName = item.modelName;
		    					$scope.dataauth.modelCode = item.modelCode;
		    					if(!$scope.dataauth.id){
		    						$scope.dataauth.id=data.data.id;
		    					}
		    					jfLayer.success(data.description); 
		    				}else{
		    					jfLayer.fail(data.description);
		    				}
		    			});
	    			},function(){
	    				
	    			});
	            	
	            	
	            }
	    };
		$scope.addRoleSub = function(result) {
			if($scope.role2.roleName==undefined){
				jfLayer.fail("角色名称不能为空");
				return;
			}
//			$validation.validate($parse('roleForm')($scope)).success(function(){//校验成功后, 进行保存操作 
			var param = Tansun.param($scope.role2) ;
			jfRest.request('role', 'save', param).then(function(data){
				if(data.status == 200){
					jfLayer.success(data.description,function(){
						$scope.radioRole.reload();
						result.cancel();
					}); 
				}else{
					jfLayer.fail(data.description);
				}
			});
//		  });
		};
	})
	Tansun.controller('roleAddCtrl',  function($scope, jfRest, $http, jfGlobal, $location, jfLayer,$validation,$parse) {
		$scope.addRoleSub = function(result) {
			if($scope.role2.roleName==undefined){
				jfLayer.fail("角色名称不能为空");
				return;
			}
			$validation.validate($parse('roleForm')($scope)).success(function(){//校验成功后, 进行保存操作 
			var param = Tansun.param($scope.role2);
			jfRest.request('role', 'save', param).then(function(data){
				if(data.status == 200){
					jfLayer.success(data.description,function(){
						$scope.reloadRoleTree() ;
						result.cancel();
					}); 
				}else{
					jfLayer.fail(data.description);
				}
			});
		  });
		};
	})
});
