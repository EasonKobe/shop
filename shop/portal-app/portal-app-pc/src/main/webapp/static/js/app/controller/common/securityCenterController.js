'use strict';

define(function (require) {
    var webApp = require('app');  
    // 自定义过滤器truncate
    webApp.filter('hide',function(){
        return function(val,type,len){
        	if (val != undefined) {
	            if(type == 'email') {
	            	return val.substr(0,3)+new String('*').repeat(3)+val.substr(val.indexOf("@"),val.length);
	            } else if (type=='mobile'){
	            	return val.substr(0,3)+new String('*').repeat(len)+val.substr(len+3,11);
	            }
            }
        }
    });
    // 安全中心
    webApp.controller('securityCenterCtrl', function ($scope,$rootScope, jfRest, jfLayer,  $http, $validation, $filter, $state, jfFileUploader) {
        // 头部菜单高亮选中账户总览
        $rootScope.global.tabIndex = 2;
    	// 初始化
		
    	$scope.password = false;
    	$scope.emailFlag = false;
    	$scope.phoneDiv = false;
        $scope.txnPswdDiv = false;
        $scope.mobileData = {};
        $scope.pwdData = {};
        $scope.emailData = {};
        $scope.txnPswd = {};
		$scope.get = function() {
			var params = {};
			jfRest.request('securityCenter', 'get', Tansun.param(params)).then(function(data) {
				if (data.status == 200) {
                    $scope.security = data.data;
                    $scope.initUploader($scope.security.aprvId);
				} else {
                    jfLayer.fail(data.description);
                }
			});
		}
		$scope.get();
		$scope.preHandleMobile = function () {
			return $validation.validate($scope.phoneForm.mobile);
        }
        $scope.preHandleNewMobile = function () {
        	return $validation.validate($scope.phoneForm.newMobile);
        }
		// 修改手机号
		$scope.modifyMobile = function() {
			var params = {
				mobile : $scope.security.mobile,
				captcha : $scope.mobileData.captcha,
				oriCode : $scope.mobileData.oriCode,
				newMobile : $scope.mobileData.newMobile,
				code : $scope.mobileData.code,
				jfCode : 'mobile'
        	}
			jfRest.request('securityCenter','save', Tansun.param(params)).then(function(data) {
				if (data.status == 200) {
					jfLayer.success("恭喜您，手机号修改成功!");
				    $state.go('security-center',{},{reload:true});
				} else {
                    jfLayer.fail(data.description);
                }
			});
		}
    	// 修改登录密码
    	$scope.modifyPswd = function() {
    		var params = {
				mobile : $scope.security.mobile,
				code : $scope.pwdData.code,
				oriPswd : $scope.pwdData.oriPswd,
				newPswd : $scope.pwdData.newPswd,
				jfCode : 'pwd'
        	}
    		jfRest.request('modifyPswd','save', Tansun.param(params)).then(function(data) {
    			if (data.status == 200) {
    				jfLayer.success("恭喜您，新密码设置成功!");
    				$state.go('security-center',{},{reload:true});
    			} else {
                    jfLayer.fail(data.description);
                }
    		});
    	}
    	$scope.preHandleEmail = function () {
        	return $validation.validate($scope.mailForm.email);
        }
    	$scope.preHandleNewEmail = function () {
        	return $validation.validate($scope.mailForm.newEmail);
        }
    	// 绑定或修改邮箱
    	$scope.modifyEmail = function() {
    		var flag = false;
    		var params = {};
    		var preHandle;
    		if($scope.security.emailFlag) {
        		preHandle = $validation.validate($scope.mailForm.email) && $validation.validate($scope.mailForm.code) && $validation.validate($scope.mailForm.newEmail) 
    			          && $validation.validate($scope.mailForm.newCode);
    			params = {
	    			email : $scope.security.email,
					code : $scope.emailData.code,
					newEmail : $scope.emailData.newEmail,
					newCode : $scope.emailData.newCode,
					jfCode : 'email'
	        	}
    		} else {
    			preHandle = $validation.validate($scope.mailForm.newEmail) && $validation.validate($scope.mailForm.newCode);
    			params = {
					newEmail : $scope.emailData.newEmail,
					newCode : $scope.emailData.newCode,
					jfCode : 'email'
	        	}
    		}
    		preHandle.success(function() {
	    		jfRest.request('modifyEmail','save', Tansun.param(params)).then(function(data) {
	    			if (data.status == 200) {
	    				jfLayer.success("恭喜您，新邮箱设置成功!");
	    				$state.go('security-center',{},{reload:true});
	    			} else {
	                    jfLayer.fail(data.description);
	                }
	    		});
    		});
    	}
    	// 导入弹出框
        $scope.importFile = function () {
            $scope.page = ctx + '/static/pages/common/import.html';
            jfLayer.dialog('page', "导入", function () {
                $scope.page = "";
                $scope.$apply();
            }, ['800px', '250px']);
        };
    	// 修改交易密码
    	$scope.modifyTxnPswd = function() {
    		var params = {
				mobile : $scope.security.mobile,
				code : $scope.txnPswd.code,
				oriPswd : $scope.txnPswd.oriPswd,
				newPswd : $scope.txnPswd.newPswd,
				jfCode : 'txnPwd'
        	}
    		jfRest.request('modifyTxnPswd','save', Tansun.param(params)).then(function(data) {
    			if (data.status == 200) {
    				jfLayer.success("恭喜您，新交易密码已经提交审核!");
    				$state.go('security-center',{},{reload:true});
    			} else {
    				jfLayer.fail(data.description);
    			}
    		});
    	}
    	// 找回交易密码
        $scope.findTxnPwd = function() {
           $state.go('pwd',{type:1});
        };

        $scope.initUploader = function(tblBlId) {
            // 授权委托书
            $scope.authDocFile = jfFileUploader({
                isMultiple: false,
                allowType: '|doc|docx|',
                formData: {
                    app : "portal-pc",
                    mdlId : "approve" ,
                    tblBlId : tblBlId ,
                    suprFileId : "7"
                }
            });
        };

		// 下载模板
	    $scope.downLoad = function(url){
	  	    location.href=url;
	    };
    });
});