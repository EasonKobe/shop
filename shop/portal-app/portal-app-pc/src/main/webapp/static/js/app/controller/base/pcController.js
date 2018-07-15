'use strict';

define(function(require) {

	var angular = require('angular') ;

	/**流程配置列表**/
	Tansun.controller('pcscfgListCtrl', function($scope, jfRest, jfLayer, jfGlobal, $http) {
		
		$scope.queryParam={};
		$scope.search = function() {
		};
		
		//search-senior高级收索
		angular.element(".search-senior").click(function() {
			angular.element(".contract-search-wrap").toggleClass("cur");
		});
		
		//search 取消
		angular.element(".search-btn .cancel-btn").click(function() {
			angular.element(".contract-search-wrap").addClass("cur");
		});
		
		//列表查询
		$scope.query = function() {
			$scope.search();
		};
		
		//新增
		$scope.add = function() {
			
			$scope.wfConfigAddPage = ctx + '/static/pages/base/pcscfg-add.html';
			jfLayer.dialog('wfConfigAdd', "流程配置导航", function() {
				$scope.wfConfigAddPage = "";
				$scope.$apply();
			}, [ '700px', '400px' ]);

		};
		
		//修改
		$scope.modify = function(item){
			var wfBscId = item.wfBscId;
			var wfSt = item.wfSt;
			if(wfSt!="0"){
				 jfLayer.alert("未生效的流程配置才能修改！");
				 return;
			}
			location.href = ctx+ "/user/pcscfg/next?wfBscId=" + wfBscId;
		}
		
		//详情
		$scope.view= function(wfBscId){
			location.href = ctx + '/user/pcscfg/view?wfBscId='+ wfBscId;
		};
		
		//删除
		$scope.del=function(item){
			 var wfBscId = item.wfBscId;
			 var wfSt = item.wfSt;
			 if(wfSt!="0"){
				 jfLayer.alert("未生效的流程配置才能删除！");
				 return;
			}
			$scope.qp = {};
	        $scope.qp.wfBscId = wfBscId;
	        var params = Tansun.param($scope.qp) 
			jfLayer.confirm("确认删除此流程配置?", function(){
				jfRest.request('workflow', 'delete', params).then(function(data){
					jfLayer.success(data.description, function () {
						//  location.href = ctx + "/user/wfcfg/list";
						$scope.query();
                    });
				});
			});
		};
		
		//失效
		$scope.dis=function(item){
			var wfBscId = item.wfBscId;
			var wfSt = item.wfSt;
			if(wfSt!="1"){
				 jfLayer.alert("生效的流程配置才能失效！");
				 return;
			}
			$scope.qp = {};
	        $scope.qp.wfBscId = wfBscId;
	        var params = Tansun.param($scope.qp) 
			jfLayer.confirm("确认失效此流程配置?", function(){
				jfRest.request('wfdis', 'save', params).then(function(data){
					jfLayer.success(data.description, function () {
                       // location.href = ctx + "/user/wfcfg/list";
						$scope.query();
                    });
				});
			});
		}
	});

	/**流程配置**/
	Tansun.controller('pcscfgAddCtrl', function($scope, jfRest, jfLayer, jfGlobal) {
		$scope.wf={};
		$scope.wf.bnsType = '0'
		$scope.WfNm =  {'codeType': 'WfNm', 'placeholder': '请选择', 'group':'0'};
		$scope.next = function() {
			if (!$scope.wf) {
				return;
			}
			var params = Tansun.param($scope.wf);
			jfRest.request('workflow', 'save', params).then(
					function(data) {
						if (data.status == 200) {
							var wfBscId = data.data.wfBscId;
							location.href = ctx+ "/user/pcscfg/next?wfBscId=" + wfBscId;
						} else {
							jfLayer.fail(data.description);
						}
					});
		};
		
		$scope.chooseBsnType=function(){
			if($scope.wf.bnsType){
				$scope.options =  Tansun.getDict("WfNm",$scope.wf.bnsType);
			}
		};
		$scope.chooseBsnType();
	});

	/**流程配置**/
	Tansun.controller('pcscfgNextCtrl', function($scope, jfRest, jfLayer, jfGlobal, $location) {

		/*获取配置基本信息*/
		$scope.query = function() {
			var wfBscId = $location.search().wfBscId
			$scope.queryParam = {};
			$scope.queryParam.wfBscId = wfBscId;
			var params = Tansun.param($scope.queryParam);
			jfRest.request('workflow', 'get', params).then(function(data) {
				$scope.wf = data.data;
			});
		};
		$scope.query();
		/*角色选择*/
		var itemTmp;
		$scope.chooseRole = function(item) {
			itemTmp = item;
			jfLayer.dialog('roleList', "选择角色", function () {
            }, ['800px', '500px']);
			
		};
		 //回填角色信息
		 $scope.$on('setRoleData', function (event, data) {
			 itemTmp.roleNm = data.rlNm;
			 itemTmp.roleId = data.rlId;
	     });
		/*保存流程配置信息*/
		$scope.save = function() {
			if (!$scope.wf) {
                return;
            }
			if(!$scope.wf.steps){
				jfLayer.alert('没有审批节点！');
				return;
			}
			var go=true;
			var steps=$scope.wf.steps;
			angular.forEach(angular.fromJson(steps),function(item,index){
				   if(go){
						if(item.roleId==null||item.roleId==""||item.roleId==undefined){
							jfLayer.fail(item.auditIndNm+'没有审批角色！');
							go=false;
						} else{
							go =true;
						}
				   }
             });
			 if(!go){return;};
			 $scope.wf.stepsStr = angular.toJson($scope.wf.steps);
			 $scope.wf.steps=[];
			 var params = Tansun.param($scope.wf);
			 jfRest.request('wfRole', 'save', params).then(function (data) {
	                if (data.status == 200) {
	                    jfLayer.success(data.description, function () {
	                        location.href = ctx + "/user/pcscfg/list";
	                    });
	                    jfLayer.closeAll();
	                } else {
	                    jfLayer.fail(data.description);
	                }
	            });
		};
		//返回
		$scope.back=function(){
			location.href=ctx+"/user/pcscfg/list";
		};
	});
	
	/**角色选择**/
	Tansun.controller('pcRoleListCtrl', function($scope, jfRest, jfLayer, jfGlobal, $location) {
        $scope.query = function () {
            var params = {};
            jfRest.request('wfRole', 'query', params).then(function (data) {
                $scope.roleList = data.data;
            });
        };
        $scope.query();
		 //选中的角色
        $scope.selectItem = function (item) {
            $scope.selected = item;
        };
        /*选择角色*/
        $scope.selectRole = function(){
            if ($scope.selected) {
                $scope.$emit('setRoleData', $scope.selected);
                jfLayer.closeAll();
            } else {
                jfLayer.alert('请选择角色！');
            }
        }
	});
	
	/**详情页面**/
	Tansun.controller('pcscfgViewCtrl', function($scope, jfRest, jfLayer, jfGlobal, $location) {
			$scope.query = function() {
				var wfBscId = $location.search().wfBscId
				$scope.queryParam = {};
				$scope.queryParam.wfBscId = wfBscId;
				var params = Tansun.param($scope.queryParam);
				jfRest.request('workflow', 'get', params).then(function(data) {
					$scope.wf = data.data;
				});
			};
			$scope.query();
			$scope.back=function(){
				location.href=ctx+"/user/pcscfg/list";
			};
	});
})