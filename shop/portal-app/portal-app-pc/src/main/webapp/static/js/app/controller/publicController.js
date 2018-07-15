'use strict';

define(function (require) {
    var webApp = require('app');

    //登录
    webApp.controller('loginCtrl',  function($scope, $window,jfParam, jfGlobal, jfRest, $state) {

    	$scope.user = {};

        $scope.init = function () {
            var error = $scope.$getQueryString($window,"error");
            $scope.showError = false;
            if (error !== null && error !== undefined && error !== '') {
                $scope.showError=true;
                switch (error) {
                    case '10001':
                        $scope.errMsg="用户名密码错误";
                        break;
                    case '10002':
                        $scope.errMsg="用户已被锁定，请2小时后登录";
                        break;
                    case '10003':
                        $scope.errMsg="验证码为空或者不正确";
                        break;
                    case '10004':
                        $scope.errMsg="用户已被锁定，请联系管理员";
                        break;
                    case '10099':
                        $scope.errMsg="请登录";
                        break;
                    default :
                        $scope.errMsg=error;
                }
            }
        };


        $scope.enter = function (e) {
            var keycode = window.event?e.keyCode:e.which;
            if(keycode==13){
                $scope.login();
            }
        };

        $scope.login = function() {
            jfGlobal.$("form").submit() ;
        };
        // 找回密码
        $scope.findPwd = function() {
        	if($scope.user.username) {
        		location.href = jfGlobal.config.website + "/pwd?username="+$scope.user.username+"&type=0";
        	} else {
        		location.href = jfGlobal.config.website + "/pwd?type=0";
        	}
        };
        // 注册
        $scope.reg = function() {
            $state.go("reg");
        };
        // 换一张验证码
		$scope.change = function() {
			angular.element('.valid-img').attr("src", ctx + "/captcha/login?v=" + (new Date()).valueOf());
		};

        // 登录页面初始化加载方法
        $scope.init();
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
});