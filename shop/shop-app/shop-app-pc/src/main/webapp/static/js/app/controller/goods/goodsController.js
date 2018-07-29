'use strict';
define(function (require){

	var angular = require('angular') ;
	
	//联系我们列表
	Tansun.controller('goodsListCtrl', function($scope, jfRest, $http, jfGlobal, $rootScope, jfLayer) {
		
		$scope.goodsList = {
		      checkType : 'radio', //当为checkbox时为多选
		      params : {}, //表格查询时的参数信息
		      paging : true ,//默认true,是否分页
		      resource : 'goodsList.query' ,//列表的资源
		      callback : function() { //表格查询后的回调函数
		      }
		};
		
		$scope.goods = {}
		
		//保存
		$scope.save = function(){
			jfRest.request('goods','save', $scope.goods).then(function(data) {
				if (data.status == 200) {
					jfLayer.success("保存成功");
					$rootScope.turn("/user/goods/goodsList");
				} else {
					jfLayer.fail(data.description);
				}
			});
		};
		
		$scope.entpUserListParams={
    		clntendId : '10001',
    		tenantId : $scope.goods.tenantId
        }
        
        $scope.entpList = {
				header: ['机构名称', '机构编号'],
		        body: ['entpNm', 'entpCode'],
		        alias:'entpNm',
		        params:{},
		        search:true,
		        resource:'entp.query',
		        callback:function(item){
		        	$scope.goods.orgCode = item.entpCode;
		        	$scope.goods.orgName = item.entpNm;
		        	$scope.entpUserListParams.tenantId=item.tenantId;
		        }
		   };
        
       
        $scope.entpUserList = {
        		header: ['用户名称', '编号'],
	            body: ['realname', 'userNum'],
		        alias:'realname',
		        params:$scope.entpUserListParams,
		        search:true,
		        resource:'entpUser.query',
		        callback:function(item){
		        	$scope.goods.userNm = item.realname;
	            	$scope.goods.userId = item.userNum;
	            	$scope.goods.certificateTypeCd = item.certificateTypeCd;
	            	$scope.goods.certificateNum = item.certificateNum;
		        }
		   };
        
        //账户签约列表
        $scope.accSignList = {
        		header: ['账户', '账户类型'],
	            body: ['accNo', 'accType'],
		        alias:'accNo',
		        params:{agreeStatus : '1'},//已签约
		        search:false,
		        resource:'accSignInfo.query',
		        callback:function(item){
		        	$scope.goods.accNo = item.accNo;
	            	$scope.goods.accType = item.accType;
		        }
		   };
        
        
	});
	
	Tansun.controller('goodsCtrl', function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer) {
		//初始化联系我们详情
		$scope.init = function(){
			jfRest.request('goods','get', $stateParams.id).then(function(data) {
				if (data.status == 200) {
					$scope.goods = data.data;
				} else {back
					jfLayer.fail(data.description);
				}
			});
		};
		
		$scope.init();
		
	});
	
});
	