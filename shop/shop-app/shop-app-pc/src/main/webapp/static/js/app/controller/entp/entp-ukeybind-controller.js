'use strict';
define(function(require) {
	var angular = require('angular');
	Tansun.controller('entpUserListCtrl', function($scope, jfRest, jfLayer,
			$stateParams, $validation, $parse) {
		$scope.disa=false;
		$scope.entpUserUkeyList = {
			      checkType : 'radio', //当为checkbox时为多选
			      params : {}, //表格查询时的参数信息
			      paging : true ,//默认true,是否分页
			      resource : 'ukey.query' ,//列表的资源
			      callback : function() { //表格查询后的回调函数
			      }
			};
	});
	Tansun.controller('ukeyBindCtrl', function($scope, jfRest, jfLayer,
		$stateParams, $validation, $parse) {
			$scope.commit = function() {jfRest.request('ukey', 'ukeyBind', $scope.ukey).then(function(data) {
				if (data.status == 200) {
					jfLayer.success(data.description)
				} else {
					jfLayer.fail(data.description);
				}
			});
		};
		$scope.modifyInfo={
				id : $stateParams.id
			}
		//Ukey绑定信息修改初始化
		$scope.init = function() {
			jfRest.request('ukey','get', Tansun.param($scope.modifyInfo)).then(function(data) {
				if (data.status == 200) {
					$scope.ukey = data.data;
					$scope.ukey.operaType = $scope.ukey.delInd;
					$scope.disa=true;
				}else{
					jfLayer.alert(data.description);
				} 
			});
		};
		if(null != $scope.modifyInfo && null != $scope.modifyInfo.id){
			$scope.init();
		}
	});
})
