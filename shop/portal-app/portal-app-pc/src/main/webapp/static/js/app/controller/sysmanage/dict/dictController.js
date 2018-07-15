'use strict';
define(function (require){

	var webApp = require('app'); 
	
	//数据字典页面
	webApp.controller('dictCtrl',  function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $location,$validation,$parse) {
		$scope.queryParam = {};
		$scope.addDict = function() {
			$scope.queryParam={};
			$scope.modal("/sysmanage/dict/addDict.html", $scope.dict={}, {
				title : '基本信息',
				buttons:['确认', '取消'], 
				size : ['800px','400px'],
				callbacks : [$scope.addDictCallback]
			})
		}
		
	    //查询get
		$scope.getDict = function() { 
			$scope.queryParam={};
			$scope.dict = $scope.dictGridOpts.checkedList();
			
			if (!$scope.dict) {
				jfLayer.fail("请选择有效数据！");
				return;
			}
			jfRest.request('dict','get', Tansun.param({'id' : $scope.dict.id})).then(function(data){
				if(data.status == 200){
					$scope.dict = data.data;
					$scope.modal("/sysmanage/dict/addDict.html", $scope.dict, {
						title : '基本信息',
						buttons:['确认', '取消'], 
						size : ['800px','400px'],
						callbacks : [$scope.addDictCallback]
					})
				}
			});
		}
		
		$scope.dictGridOpts = {
			checkType : 'radio',
			params : {},
			paging : true,
			resource : "dict.query",
				
		};
		
		/*$scope.reset = function() {
			$scope.queryParam = {};
			$scope.dictGridOpts.params = $scope.queryParam;
			$scope.dictGridOpts.search();
		}*/
		
		$scope.addDictCallback = function(result) {
			$scope.saveDict(result);
		};
		
		$scope.saveDict = function(result) {
//			$validation.validate($parse('dictFrom')($scope)).success(function(){//校验成功后, 进行保存操作 
				var param = Tansun.param($scope.dict) ;
				jfRest.request('dict', 'save', param).then(function(data){
					if(data.status == 200){
						jfLayer.success(data.description,function(){ 
							result.cancel();
							$scope.queryParam.dctTpCd=$scope.dict.dctTpCd;
							$scope.dictGridOpts.search();
						}); 
					}else{
						jfLayer.fail(data.description);
					}
				});
//		    });
		};
		
	});
	
});