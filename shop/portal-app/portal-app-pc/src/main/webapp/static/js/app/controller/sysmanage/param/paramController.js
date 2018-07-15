'use strict';
define(function (require){

	var angular = require('angular');
	
	//数据字典页面
	Tansun.controller('paramCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $location,$validation,$parse) {
		//资源列表
		$scope.paramList = {
				checkType : 'radio', //当为checkbox时为多选
				params :{}, //表格查询时的参数信息
				paging : true ,//默认true,是否分页
				resource : 'param.query',//列表的资源
				callback : function() { //表格查询后的回调函数
				}
			};
		//新增、修改界面弹出
		$scope.addParam = function() {
			$scope.param={};
			$scope.modal('/sysmanage/param/addParam.html',
					$scope.param,{
					title :'新增参数',
					buttons : ['确认','取消'],
					size : ['700px','300px'],
					callbacks : [$scope.addParamSub]}
					);
/*			$rootScope.$dialog(event,'paramDia');*/
		}
	    //查询get
		$scope.getParam = function() { 
			$scope.param = $scope.paramList.checkedList(); 
			if (!$scope.param || $scope.param == {}) {
				jfLayer.fail("请选择有效数据！");
				return;
			}
			jfRest.request('param','get', Tansun.param({'id' : $scope.param.id})).then(function(data){
				if(data.status == 200){
					$scope.param = data.data;
					$scope.modal('/sysmanage/param/addParam.html',
							$scope.param,{
							title :'修改参数',
							buttons : ['确认','取消'],
							size : ['700px','300px'],
							callbacks : [$scope.addParamSub]}
							);
				}
			});
		}
		$scope.addParamSub = function(result) {
//			$validation.validate($parse('paramForm')($scope)).success(function(){//校验成功后, 进行保存操作 
			var param = Tansun.param($scope.param) ;
			jfRest.request('param', 'save', param).then(function(data){
				if(data.status == 200){
					jfLayer.success(data.description,function(){
						//$scope.search() ;
						$scope.paramList.search();
						result.cancel();
					}); 
				}else{
					jfLayer.fail(data.description);
				}
			});
//		  });
		};
	});
	
	//新增页面
	Tansun.controller('paramAddCtrl',  function($scope, jfRest, $http, jfGlobal, $location, jfLayer,$validation,$parse) {
		$scope.addParamSub = function(result) {
//			$validation.validate($parse('paramForm')($scope)).success(function(){//校验成功后, 进行保存操作 
			var param = Tansun.param($scope.param) ;
			jfRest.request('param', 'save', param).then(function(data){
				if(data.status == 200){
					jfLayer.success(data.description,function(){
						//$scope.search() ;
						$scope.paramList.search();
						result.cancel();
					}); 
				}else{
					jfLayer.fail(data.description);
				}
			});
//		  });
		};
	});
	
});