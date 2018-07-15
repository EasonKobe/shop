'use strict';
define(function (require){
	var angular = require('angular'); 
	
	//组织机构树形页面
	Tansun.controller('menuTreeCtrl',  function($scope, $rootScope,jfRest, $http, jfGlobal, $location, jfLayer,$compile) {
		$scope.maxLevel =3;
		
		//功能菜单树
		$scope.queryMenu={
        		clntendId:'10002'
        		}
        $scope.ztree = {
	            isCheck : false,
	            resource : 'menuManage.query',
	            params :$scope.queryMenu,
	            nodeClick : function(treeNode) {
	            	$scope.nodeClick(treeNode);
	            },
		        nodeCheck : function(treeNode) {
		        
		        }
	        }
		$scope.queryMenuFro={
        		clntendId:'10001'
        		}
        $scope.ztreeFro = {
	            isCheck : false,
	            resource : 'menuManage.query',
	            params :$scope.queryMenuFro,
	            nodeClick : function(treeNode) {
	            	$scope.nodeClick(treeNode);
	            },
		        nodeCheck : function(treeNode) {
		        
		        }
	        }
		//事件列表
		$scope.menuEventResList = {
				checkType : 'radio', //当为checkbox时为多选
				params :{}, //表格查询时的参数信息
				paging : true ,//默认true,是否分页
				resource : 'menuEventRelManage.query',//列表的资源
				callback : function() { //表格查询后的回调函数
				}
			};
		//初始化构造图标弹窗
		$scope.initIcon=function(){
			var icon_array_v1=new Array();
			var length_v1=8;
			var icon_name_v1="iconmenu{tmp}";
			for(var i=1;i<length_v1;i++){
				var ii=i<10?"0"+i:i;
				var value=icon_name_v1.replace("{tmp}",ii)
				icon_array_v1.push({url:value}); 
			}
			
			$scope.icon1=icon_array_v1;
			
			var icon_array_v2=new Array();
			var length_v2=10;
			var icon_name_v2="iconlistmenu{tmp}";
			for(var i=1;i<length_v2;i++){
				var ii=i<10?"0"+i:i;
				var value=icon_name_v2.replace("{tmp}",ii)
				icon_array_v2.push({url:value}); 
			}
			$scope.icon2=icon_array_v2;
			
			var icon_array_v3=new Array();
			var length_v3=8;
			var icon_name_v3="iconlistmenu{tmp}";
			for(var i=1;i<length_v3;i++){
				var ii=i<10?"0"+i:i;
				var value=icon_name_v3.replace("{tmp}",ii)
				icon_array_v3.push({url:value}); 
			}
			$scope.icon3=icon_array_v3;
		}
		
		//初始化弹出框图标
		$scope.initIcon();

		$scope.nodeCheck = function(treeNode, zTree){
		     var getCheckedNodes = zTree.getCheckedNodes(true);
		     var length = getCheckedNodes.length;
		     var menuIds = ''; //当前选中的资源权限code串
		     for (var i = 0; i < length; i++) {
		        if (menuIds == '') {
		        	menuIds = getCheckedNodes[i].id;
		        } else {
		        	menuIds = menuIds + ',' + getCheckedNodes[i].id;
		        }
		     }
		     $("#menuIds").val(menuIds);
		};
		
		$scope.nodeClick = function(treeNode) { 
			jfRest.request('menuManage','get', Tansun.param({'menuCode' : treeNode.id})).then(function(data){
				if(data.status == 200){
					$scope.treeNode=treeNode;
					$scope.level=treeNode.level;
					$scope.menu = data.data;
					$scope.menu.treeNode = treeNode;
					$scope.menu.level = treeNode.level;
					$scope.menu.preMenuTypeCd=$scope.menu.menuTypeCd;
					if($scope.menu.menuTypeCd=='no-source'){
						$scope.menu.url='';
						$scope.menu.urlDisabled=true;
					}else{
						$scope.menu.urlDisabled=false;
					}
					
					//刷新事件数据 
					$scope.queryParam={};
					if($scope.menu.menuTypeCd=='click'){
						$scope.menuEventResList.params.menuCode=$scope.menu.menuCode;
						$scope.menuEventResList.params.canEdit= true;
					}else{
						$scope.menuEventResList.params.menuCode=$scope.menu.menuCode;
						$scope.menuEventResList.params.canEdit= false;
					}
/*					$scope.searchMenuEvent();*/
					$scope.menuEventResList.search();
					
				}
			});
		}
		$scope.checkMenuType= function(menu){
			var preMenuTypeCd = menu.preMenuTypeCd
			
			
			if(menu.treeNode.isParent){
				 //改成按钮
				 if(menu.menuTypeCd=='click'){
					 jfLayer.fail("存在子节点不能改成按钮");
					 menu.menuTypeCd=preMenuTypeCd;
					 return 
				 }
				//改成页面
				 else if(menu.menuTypeCd=='view'){
					//原来是目录
					 if('no-source'==preMenuTypeCd){
						 //下面是否存在2层
						 if(menu.treeNode.children && menu.treeNode.children.length>0){
							 for(var i=0;i<menu.treeNode.children.length;i++){
								 if(menu.treeNode.children[i].isParent){
									 jfLayer.fail("存在多级子节点，不能改为页面");
									 menu.menuTypeCd=preMenuTypeCd;
									 return 
								 }
							 }
						 }
						
					 }
				 }//改成目录
				 else if(menu.menuTypeCd=='no-source'){
					 //原来是页面
				 }
				
			 }else{
				//叶子节点想怎么改就怎么改
			 }
			
			//比允许最大层少一层在建子节点时只能建按钮
			if(menu.level>=($scope.maxLevel)){
				if(menu.menuTypeCd!='click'){
					jfLayer.fail("第 "+($scope.maxLevel+1)+" 层只能建按钮");
					menu.menuTypeCd = 'click';
					return;
				}
				
			}else if(menu.level>=($scope.maxLevel-1)){
				if(menu.menuTypeCd=='no-source'){
					jfLayer.fail("第 "+($scope.maxLevel)+" 层不能建目录");
					menu.menuTypeCd = preMenuTypeCd;
					return;
				}
			}
			
			menu.preMenuTypeCd=menu.menuTypeCd;
			
			
			if(menu.menuTypeCd=='no-source'){
				menu.url='';
				menu.urlDisabled=true;
			}else{
				menu.urlDisabled=false;
			}
		}
		
		//添加根树节点
		$scope.addRootMenu = function(type) {
			$scope.newMenu={};
			$scope.iocnList={};
			$scope.iocnList=$scope.icon1;
//			$scope.menu.parentMenuCode="10002";
			$scope.newMenu.leafFlagCd="0";//非叶子节点0
			$scope.modal('/sysmanage/menu/addMenu.html',
					$scope.newMenu,{title :'添加树',
					buttons : ['确认','取消'],
					size : ['600px','350px'],
					callbacks : [$scope.addMenuSub]
					});
		}
			
		//图片选择
		$scope.iconSelect = function() {  
			$scope.iocnList={};
/*			if($scope.level==0)
				$scope.iocnList=$scope.icon1;
			else if($scope.level==1)
				$scope.iocnList=$scope.icon2;
			else if($scope.level==2)
				$scope.iocnList=$scope.icon3;
			else  return;*/
			$scope.iocnList=$scope.icon1;
			$scope.menuType='menu';
			$scope.modal('/sysmanage/menu/icon_item.html',
					{},{
					title :'图标',
					buttons : ['确认','取消'],
					size : ['500px','250px']
					});
/*			$scope.dialog($scope,'iconDiv');*/
			
		}
		$scope.addmenuEvent=function(){
			if($scope.checkBefore())
			{
				$scope.modal('/sysmanage/menu/eventResSelector.html',
						$scope.iocnList,{
						title :'事件列表',
						buttons : ['确认','取消'],
						size : ['1200px','600px'],
						callbacks : [$scope.saveConfirm]}
						);
			}else{
				jfLayer.fail("非按钮不能维护事件");
			}
		}
		//自定义弹出界面
		$scope.dialog=function(scope,dialogId){
			var diaElement = angular.element("#"+dialogId);
			var title = diaElement.attr("title");
			var size = diaElement.attr("size").split(",");
			var dialog = angular.element("#"+dialogId);
			var url = ctx +'/static/pages' + dialog.attr("url");
			var tableName = dialog.attr("tbNm");
			if(dialog.find("div").length > 0){
				dialog.find("div").scope().$destroy();
			}
			dialog.html($compile('<div ng-include="\''+url+'\'" </div>')(scope));
			var newIndex = jfLayer.dialogForView(dialogId, title, function () {
			}, size);
			scope._curDialogIndex = newIndex;
		}
		$scope.addMenu = function(type) {
			if (!$scope.menu || JSON.stringify($scope.menu) =="{}") {
				jfLayer.fail("请先选择节点");
			} else {
				if( $scope.menu.menuTypeCd=='click'){
					jfLayer.fail("按钮下不能添加节点");
					return;
				}
				if($scope.level>=$scope.maxLevel){
					jfLayer.fail("菜单树只能有 "+(maxLevel+1)+" 层,第 "+(maxLevel+1)+" 层只能建按钮");
					return;
				}
				$scope.newMenu={};
				$scope.newMenu.treeNode = {isParent:false};
				$scope.newMenu.level = $scope.treeNode.level+1;
				$scope.newMenu.parentMenuCode=$scope.treeNode.id;
				$scope.newMenu.parentMenuName=$scope.treeNode.name;
				$scope.level=$scope.treeNode.level;
				$scope.newMenu.leafFlagCd="1";//非叶子节点0
				$scope.modal('/sysmanage/menu/addMenu.html',
						$scope.newMenu,{
						title :'添加树',
						buttons : ['确认','取消'],
						size : ['600px','350px'],
						callbacks : [$scope.addMenuSub]
						});
/*				$rootScope.$dialog(event,'menuDia');*/
			}
		}
		//下拉选择
		$scope.selectMenu = {
	            header: ['菜单编号', '菜单名称'],
	            body: ['menuCode', 'menuName'],
	            text: 'MENU',
	            api: 'menuManage',
	            method: 'selector',
	            pageShow:true,
	            params: {
	            },
	            callback: function (item) {
	            	$scope.menu.parentMenuCode = item.menuCode;
	            	$scope.menu.parentMenuName = item.menuName;
	            }
	    };
		//修改方法
		$scope.save=function(){
			if (!$scope.menu || $scope.menu == {}) {
				jfLayer.fail("无修改数据！");
			} else{
				if($scope.menu.menuTypeCd=='view'
					&&$scope.menu.url==''){
					jfLayer.fail("资源类型为页面时，资源URL不能为空");
					return;
				}else if($scope.menu.menuTypeCd=='click'
					||$scope.menu.menuTypeCd=='no-source'){
					//非页面时，url清空
					$scope.menu.url='';
				}
				var param = Tansun.param($scope.menu) ;
				jfRest.request('menuManage', 'save', param).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description,function(){
							jfLayer.closeAll();
							$scope.ztree.reload();
							$scope.ztreeFro.reload();
						}); 
					}else{
						jfLayer.fail(data.description);
					}
				});
			}
		}
		
		$scope.delMenu = function() {
			if (!$scope.menu ||JSON.stringify($scope.menu) =="{}") {
				jfLayer.fail("请先选择节点");
			} else if ($scope.treeNode.isParent) {
				jfLayer.fail("请选择叶子节点");
			} else {
				jfLayer.confirm('将会删除菜单信息，确认删除？',function(){
				    jfRest.request('menuManage','delete', Tansun.param($scope.menu)).then(function(data) {
				        if(data.status == 200){
				            var msg = data.description ? data.description : '删除成功' ;
				            jfLayer.success(msg,function(){
				                $scope.ztree.reload();
				                $scope.ztreeFro.reload();
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
		//重置菜单事件查询条件
		$scope.resetMenuEvenQueryParam=function(){
			//刷新菜单事件表数据 
			/*$scope.queryMenuEventParam=$scope.menu;*/
			$scope.menuEventResList.params={
					menuCode: $scope.menu.menuCode
			};
		}
		$scope.checkBefore=function(){
			if($scope.menuEventResList.params.canEdit){
/*				 $rootScope.clearChecked('menuEventResList');*/
				return true
				;
			}else{
			/*	return "非按钮不能维护事件";*/
				return false
			}
			
		}
		//菜单事件选择后的回调方法
		$scope.saveConfirm = function(result){
			var item = result.scope.eventResList.checkedList();
			if (!item) {
				jfLayer.fail("该操作至少选择一条记录");
				return;
			} else{
				$scope.newMenuEvent={
						'eventCode' : item.eventCode,
						'menuCode': $scope.menu.menuCode
				};
				jfRest.request('menuEventRelManage', 'save', Tansun.param($scope.newMenuEvent)).then(function (data) {
		            if (data.status === 200) {
		            	result.cancel();
		                jfLayer.success("新增成功", function () {
/*		                    $rootScope.clearChecked();*/
//		                    $scope.$parent.search('menuEventList');
		                	$scope.menuEventResList.search();
/*		                    $scope.searchMenuEvent('menuEventList');*/
		                });
		            } else {
		                jfLayer.alert(data.description);
		            }
		        });
			}
			
		}
		$scope.addMenuSub = function(result) {
			var param = Tansun.param($scope.newMenu) ;
			jfRest.request('menuManage', 'save', param).then(function(data){
				if(data.status == 200){
					jfLayer.success(data.description,function(){
						 $scope.ztree.reload();
						 $scope.ztreeFro.reload();
						 result.cancel();
					}); 
				}else{
					jfLayer.fail(data.description);
				}
			});
		};
		
	})
	
	Tansun.controller('menuAddCtrl',  function($rootScope,$scope, jfRest, $http, jfGlobal, $location, jfLayer) {
//		$scope.checkMenuType4add= function(){
//			var preMenuTypeCd = $scope.newMenu.preMenuTypeCd;
//			//比允许最大层少一层在建子节点时只能建按钮
//			if($scope.level>=($scope.maxLevel-1)){
//				if(!$scope.newMenu.menuTypeCd=='click'){
//					jfLayer.fail("第 "+(maxLevel+1)+" 层只能建按钮");
//					$scope.newMenu.menuTypeCd = 'click';
//					return;
//				}
//				
//			}else if($scope.level>=($scope.maxLevel-2)){
//				if($scope.newMenu.menuTypeCd=='no-source'){
//					jfLayer.fail("第 "+(maxLevel)+" 层不能建目录");
//					$scope.newMenu.menuTypeCd = preMenuTypeCd;
//					return;
//				}
//			}
//			$scope.newMenu.preMenuTypeCd=$scope.newMenu.menuTypeCd
//			
//			if($scope.menu.menuTypeCd=='no-source'){
//				$scope.menu.url='';
//				$scope.menu.urlDisabled=true;
//			}else{
//				$scope.menu.urlDisabled=false;
//			}
//		}
		//图片选择
		$scope.iconSelect = function() {  
			$scope.iocnList={};
/*			if($scope.level==0)
				$scope.iocnList=$scope.icon1;
			else if($scope.level==1)
				$scope.iocnList=$scope.icon2;
			else if($scope.level==2)
				$scope.iocnList=$scope.icon3;
			else  return;*/
			$scope.iocnList=$scope.icon1;
			$scope.menuType='newMenu';
			$scope.modal('/sysmanage/menu/icon_item.html',
					$scope.iocnList,{
					title :'图标',
					buttons : ['确认','取消'],
					size : ['500px','250px']
					});
/*			$scope.dialog($scope,'iconDiv');*/
		}
		
		
	})
	
	Tansun.controller('eventResListDialogCtrl',  function($scope, $rootScope,jfRest, $http, jfGlobal, $location, jfLayer,$compile) {
		//事件列表
		$scope.queryEventParam={};
		$scope.eventResList = {
				checkType : 'radio', //当为checkbox时为多选
				params :$scope.queryEventParam, //表格查询时的参数信息
				paging : true ,//默认true,是否分页
				resource : 'eventResManage.query',//列表的资源
				callback : function() { //表格查询后的回调函数
				}
			};
		$scope.saveConfirm = function(result){
			var param = Tansun.param($scope.eventResInfo);
			jfRest.request('eventResManage', 'save', param).then(function(data){
				if(data.status == 200){
				//	jfLayer.close(index);
					jfLayer.success(data.description,function(){
						result.cancel();
						$scope.eventResList.search();
					}); 
				}else{
					jfLayer.fail(data.description);
				}
			});
		}
		$scope.editEventDialog=function(result){
			$scope.eventResInfo=$scope.eventResList.checkedList();
			if (!$scope.eventResInfo) {
				jfLayer.fail("该操作至少选择一条记录");
				return;
			}
			$scope.modal('/sysmanage/menu/eventResEditDialog.html',
					$scope.eventResInfo,{title :'事件维护',size : ['800px','350px'],callbacks : [$scope.saveConfirm]})
		}
/*		//自定义弹出界事件列表面
		$scope.dialog=function(event,dialogId,type){
			if('update'==type){
				var item = $scope.eventResList.checkedList();
				if (!item) {
					jfLayer.fail("该操作至少选择一条记录");
					return;
				}
				$scope.eventResInfo=item;
			}else{
				$scope.eventResInfo={};
			}
			
			//事件列表查询条件
			
			var diaElement = angular.element("#"+dialogId);
			var title = diaElement.attr("title");
			var size = diaElement.attr("size").split(",");
			var callback = diaElement.attr("callback");
			var url = ctx +'/static/pages' + diaElement.attr("url");
			var tableName = diaElement.attr("tbNm");
			if(diaElement.find("div").length > 0){
				diaElement.find("div").scope().$destroy();
			}
			diaElement.html($compile('<div ng-include="\''+url+'\'" </div>')($scope));
			jfLayer.dialog(dialogId, title, function () {}, size,function (index) {
				var diaLogScope = diaElement.scope().$parent;
				var callbackMethod = Tansun.get(diaLogScope, callback ? callback : 'confirm');
				var diaLogFind = diaElement.find("div").get(1);
				var diaLogFindScope = angular.element(diaLogFind).scope();
				var callbackDiaMethod = Tansun.get(diaLogFindScope, callback ? callback : 'confirm');
                if(callbackMethod && typeof(callbackMethod) === 'function'){
                	callbackMethod(index) ;
                }else if(callbackDiaMethod && typeof(callbackDiaMethod) === 'function'){
                	callbackDiaMethod(index);
                }
			});
		}*/
	})
	Tansun.controller('eventResEditDialogCtrl',  function($rootScope,$scope, jfRest, $http, jfGlobal, $location, jfLayer) {
/*		$scope.eventResInfo=$scope.$parent.eventResInfo;
		$scope.get = function(index){
			if($scope.eventResInfo.id){
				var param = Tansun.param($scope.eventResInfo) ;
				jfRest.request('eventResManage', 'get', param).then(function(data){
					if(data.status == 200){
						$scope.eventResInfo=data.data;
					}else{
						jfLayer.fail(data.description);
					}
				});
			}
			
		}
		$scope.get();*/
	})
		Tansun.controller('treeDialogCtrl',  function($rootScope,$scope, jfRest, $http, jfGlobal, $location, jfLayer) {			
			//移除确定取消按钮
			$(".layui-layer-btn0").remove();
			$(".layui-layer-btn1").remove();
			$scope.selectIcon = function(val) {
				 $scope._modal.close();
				 if($scope.menuType=='menu'){					 
					 $scope.menu.icon=val;			
				}
				 if($scope.menuType=='newMenu'){
				 $scope.newMenu.icon=val;
				 
				 }
			}
	})
});
	