'use strict';
define(function (require){

	var angular = require('angular') ;
	
	//联系我们列表
	Tansun.controller('contactUsListCtrl', function($scope, jfRest, $http, jfGlobal, $rootScope, jfLayer) {
		
		$scope.contactUsList = {
		      checkType : 'radio', //当为checkbox时为多选
		      params : {}, //表格查询时的参数信息
		      paging : true ,//默认true,是否分页
		      resource : 'contactUsList.query' ,//列表的资源
		      callback : function() { //表格查询后的回调函数
		      }
		};
		
		$scope.contactUs = {}
		
		//保存
		$scope.save = function(){
			jfRest.request('contactUs','save', $scope.contactUs).then(function(data) {
				if (data.status == 200) {
					jfLayer.success("保存成功");
					$rootScope.turn("/user/contactUs/contactUsList");
				} else {
					jfLayer.fail(data.description);
				}
			});
		};
		
		$scope.entpUserListParams={
    		clntendId : '10001',
    		tenantId : $scope.contactUs.tenantId
        }
        
        $scope.entpList = {
				header: ['机构名称', '机构编号'],
		        body: ['entpNm', 'entpCode'],
		        alias:'entpNm',
		        params:{},
		        search:true,
		        resource:'entp.query',
		        callback:function(item){
		        	$scope.contactUs.orgCode = item.entpCode;
		        	$scope.contactUs.orgName = item.entpNm;
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
		        	$scope.contactUs.userNm = item.realname;
	            	$scope.contactUs.userId = item.userNum;
	            	$scope.contactUs.certificateTypeCd = item.certificateTypeCd;
	            	$scope.contactUs.certificateNum = item.certificateNum;
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
		        	$scope.contactUs.accNo = item.accNo;
	            	$scope.contactUs.accType = item.accType;
		        }
		   };
        
        
	});
	
	Tansun.controller('contactUsCtrl', function($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer) {
		//初始化联系我们详情
		$scope.init = function(){
			jfRest.request('contactUs','get', $stateParams.id).then(function(data) {
				if (data.status == 200) {
					$scope.contactUs = data.data;
				} else {back
					jfLayer.fail(data.description);
				}
			});
		};
		
		$scope.init();
		
	});
	
});
	