'use strict';

define(function (require) {
    var webApp = require('app');
    
    webApp.controller('headerCtrl', function ($scope, jfParam, jfRest, $http, jfGlobal) {
        $scope.goLogin = function () {
            location.href = jfGlobal.ctx + "/login";
        };
    });

    // 找回密码-身份认证
    webApp.controller('pwdCtrl', function ($scope, $stateParams, jfRest, jfLayer, jfParam, $validation, $interval, jfFileUploader) {
    	$scope.tab = 1;
    	$scope.type = $stateParams.type;
    	$scope.pwd = {};
    	$scope.flag = '';
    	if($stateParams.username) {
    		$scope.pwd.loginName = $stateParams.username;
    	}
    	$scope.preHandle = function () {
    		// 校验用户名和手机号/邮箱
    		var preHandle = $validation.validate($scope.pwdForm.loginName) && $validation.validate($scope.pwdForm.mobileOrEmail);
    		preHandle.success(function() {
	        	var captchaType = $scope.getCaptchaType(); // 发送类型
	        	angular.element(".btn-getcode").attr("mode", captchaType);
    			var params = {
	        		loginName : $scope.pwd.loginName,
	        		mobileOrEmail : $scope.pwd.mobileOrEmail,
	        		captchaType : captchaType
	        	}
	        	jfRest.request('pwd','query', jfParam.param(params)).then(function(data) {
					if (data.status == 200) {
						$scope.flag = "1";
					} else {
						jfLayer.fail(data.description);
						$scope.flag = '';
	                }
				});
    		});
    		return $validation.validate($scope.pwdForm.loginName) && $validation.validate($scope.pwdForm.mobileOrEmail)
    		          && $validation.validate($scope.pwdForm.flag);
        };
    	// 下一步
    	$scope.toTwoStep = function() {
    		var params = {
        		mobileOrEmail : $scope.pwd.mobileOrEmail,
        		code : $scope.pwd.code,
        		jfCode : 'pwd'
        	}
	    	jfRest.request('pwd','get', jfParam.param(params)).then(function(data) {
				if (data.status == 200) {
					$scope.tab = 2;
				} else {
                    jfLayer.fail(data.description);
                }
			});
    	}
    	// 获取发送类型
    	$scope.getCaptchaType = function() {
    		var captchaType = ''; // 获取验证码类型
    		var mobileOrEmail = $scope.pwd.mobileOrEmail; // 手机号或邮箱
        	var pattern = /^1[3|4|5|7|8][0-9]\d{8}$/;
        	if (pattern.test(mobileOrEmail)) { // 手机号
        		captchaType = 'SMS'
        	} else {
        		captchaType = 'EMAIL'
        	}
        	return captchaType;
    	}
    	// 授权委托书
		$scope.authDocFile = jfFileUploader({
            isMultiple: false,
            allowType: '|doc|docx|',
            formData: {
                app : "portal-pc",
                mdlId : "authDoc" ,
                tblBlId : "abcd1234" ,
                suprFileId : "1"
            }
		});
		// 下载模板
	    $scope.downLoad = function(){
	  	    location.href=ctx+"/static/template/circleExcel.xlsx";
	    };
    	// 下一步
    	$scope.toThreeStep = function() {
    		var params = {
    			loginName : $scope.pwd.loginName,
            	mobileOrEmail : $scope.pwd.mobileOrEmail,
        		newPswd : $scope.pwd.newPswd,
        		type : $scope.type
        	}
        	// 保存用户信息
        	jfRest.request('pwd','save', jfParam.param(params)).then(function(data) {
    			if (data.status == 200) {
    				$scope.tab = 3;
    				var second = 3 ;
    				$scope.time = second;
    				var timePromise = $interval(function(){
    					$scope.time = second;
    	    			if(second<=0){  
    						$interval.cancel(timePromise);
    						location.href = ctx + "/login";
    	    			} else {  
    	                	second--;  
    	                }
    	            },1000,100) ;
    			} else {
                    jfLayer.fail(data.description);
                }
    		});
    	}
        $scope.toLogin = function() {
    		location.href = ctx + "/login";
    	}
    });
});