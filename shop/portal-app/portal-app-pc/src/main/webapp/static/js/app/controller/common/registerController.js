'use strict';

define(function (require) {
    var webApp = require('app');

    // 注册-企业信息
    webApp.controller('regCtrl', function ($scope, jfRest, jfLayer, jfParam, $validation, $interval) {
    	$scope.tab = 1;
    	$scope.reg = {};
    	// 下一步
    	$scope.toTwoStep = function() {
    		if(!$scope.reg.ctrFlag){
                jfLayer.fail("请先同意《蔷薇大树金融平台服务协议》");
                return;
			}
	    	// 校验邀请码、企业名称、社会信用代码
    		var params = {
    			cstNm : $scope.reg.cstNm,
    			cstOrgCode : $scope.reg.cstOrgCode,
    			inviteCode : $scope.reg.inviteCode
        	}
	    	jfRest.request('reg','query', jfParam.param(params)).then(function(data) {
				if (data.status == 200) {
					$scope.tab = 2;
					$scope.reg.mobile = data.data.ctcPsnTel;
				} else {
                    jfLayer.fail(data.description);
                }
			});
    	};
    	// 获取邀请码
    	$scope.getInviteCode = function() {
    		var handle = $validation.validate($scope.regForm.cstNm) && $validation.validate($scope.regForm.cstOrgCode);
    		handle.success(function() {
	    		var params = {
	    			cstNm : $scope.reg.cstNm,
	    			cstOrgCode : $scope.reg.cstOrgCode
	        	}
		    	jfRest.request('reg','get', jfParam.param(params)).then(function(data) {
					if (data.status == 200) { // 发送成功
						$scope.enabledInviteCodeBtn = true;
						$scope.timing();
					} else {
	                    jfLayer.fail(data.description);
	                }
				});
    		});
    	}
    	// 下一步
    	$scope.toThreeStep = function() {
    		$scope.reg.jfCode = "reg";
        	// 保存用户信息
        	jfRest.request('reg','save', jfParam.param($scope.reg)).then(function(data) {
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
    	// 计时器
    	$scope.timing = function() {
    		var text='重新获取';
    		var element = angular.element("#getInviteCodeBtn");
			var second = 60 ;
    		var timePromise = $interval(function(){ 
    			if(second<=0){  
					$interval.cancel(timePromise);  
					element.html(text);
					element.click($scope.getInviteCode);
    			} else {  
    				element.html(second + "s");
                	second--;  
                }  
            },1000,100) ;
		};
		//用户协议签署确认信息弹出框
        $scope.noteDialog = {
            open : function() {
                this.showDialog = true ;
                this.pageUri = ctx + '/static/pages/common/register/reg-cms.html';
                $scope.noteDialog.index = jfLayer.dialog('law', "蔷薇大树金融平台服务协议", function () {
                    $scope.noteDialog.close();
                    $scope.$apply();
                }, ['880px', '600px']);
            },
            confirm : function(artcCntnt) {
                $scope.reg.ctrFlag = !$scope.reg.ctrFlag;
                jfLayer.closeAll();
            },
            close : function(){
                this.pageUri = '';
                this.showDialog = false ;
                jfLayer.closeAll();
            },
            init : function() {
                //初始化页面
                jfRest.request('qwDetails','get', jfParam.param({})).then(function(data) {
                    if (data.status == 200) {
                        $scope.artcDetail = data.data;
                    } else {
                        jfLayer.fail(data.description);
                    }
                });
            },
            showDialog:false
        };
		// 返回
		$scope.back = function() {
			$scope.tab = 1;
    	}
    });
    webApp.controller('footerCtrl', function ($scope, jfLayer) {
    	// 使用须知弹出框
        $scope.noticeDialog = function() {
            $scope.page = ctx + '/static/pages/common/index/index-notice.html';
            jfLayer.dialog('notice', "使用须知", function () {
                $scope.page = "";
                $scope.$apply();
            }, ['640px', '365px']);
        };
    });
	webApp.controller('qwDtealsCtrl',  function($scope,jfRest,jfLayer,jfParam, $http, jfGlobal) {
		var params = {};
		$scope.init = function() {
			jfRest.request('qwDetails', 'get', jfParam.param(params)).then(function (data) {
				if (data.status === 200) {
					$scope.artcCntnt = data.data.artcCntnt;
				} else {
					jfLayer.fail(data.description);
				}
			});
		};
		$scope.init();
		$scope.closePage = function() {
			
			jfLayer.closeAll();
		}

	});
});