'use strict';
define(function (require){

	var webApp = require('app');
    require('./jquery.fullPage.min.js');
    //js执行顺序为逆序
    require('./js/jquery-3.3.1.min.js');
    require('./js/main.js');
    require('./js/register.js');
    require('./js/sign.js');
    
	webApp.controller('indexCtrl',  function($scope,$rootScope, jfRest, $window,$interval,$http, jfGlobal, $timeout,$stateParams) {
        // 登录页面相关
        $scope.user = {};
        $scope.mshow='';
        $scope.step='1';
        $scope.current='1';

        $scope.init = function () {
            $scope.showError = false;
            if ($stateParams.error !== null && $stateParams.error !== undefined && $stateParams.error !== '') {
                $scope.showError=true;
                switch ($stateParams.error) {
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
            $scope.current = 1;
            $scope.loginAction = ctx+'/login';
            // 如果是登出跳转过来，需要默认显示登录
            $scope.funcShow($stateParams.login);
        };

        $scope.showTab=function(param){
            $scope.current=param;
            $scope.step='1';
        }
        $scope.funcShow=function(showtype){
        	if('http' == $scope.mshow){
                $scope.mshow= 'toreg';
            }else{
                $scope.mshow=showtype;
            }
            $scope.step='1';
        }

        $scope.funcStep=function(stepNum){
            $scope.step=stepNum;
        }

        var fullpage = null ;
        $timeout(function() {
        	fullpage = jQuery('#scroll-page').fullpage({
        		'navigation': true,
        	});
		})
		//页面变化时销毁fullpage
		$scope.$on('$stateChangeStart',function(event, toState, toParams, fromState, fromParams) {
			jQuery.fn.fullpage.destroy('all');
		})

        $scope.enter = function (e) {
            var keycode = window.event?e.keyCode:e.which;
            if(keycode==13){
                $scope.login();
            }
        };

        $scope.login = function() {
            jfGlobal.$("#loginForm").submit() ;
        };
        // 找回密码
        $scope.findPwd = function() {
            if($scope.user.username) {
                location.href = jfGlobal.config.website + "/pwd?username="+$scope.user.username+"&type=0";
            } else {
                location.href = jfGlobal.config.website + "/pwd?type=0";
            }
        };
        // 换一张验证码
        $scope.change = function() {
            angular.element('.valid-img').attr("src", ctx + "/captcha/login?v=" + (new Date()).valueOf());
        };

        // 登录页面初始化加载方法
        $scope.init();

		// 注册页面相关
        $scope.reg = {};
        $scope.regSubmit = function() {
            $scope.reg.jfCode = "reg";
            // 保存用户信息
            jfRest.request('reg','save', Tansun.param($scope.reg)).then(function(data) {
                if (data.status == 200) {
                    $scope.funcStep(2);
                    var second = 3 ;
                    $scope.time = second;
                    var timePromise = $interval(function(){
                        $scope.time = second;
                        if(second<=0){
                            $interval.cancel(timePromise);
                            $scope.funcShow('tologin');
                        } else {
                            second--;
                        }
                    },1000,100) ;
                } else {
                    jfLayer.fail(data.description);
                }
            });
        }
	});
	// 联系我们
	webApp.controller('contactusCtrl',  function($scope,  jfRest, $http, jfGlobal) {
		$scope.save = function(){
			jfRest.request('contactUs','save', Tansun.param($scope.contact)).then(function(data) {
                if (data.status == 200) {
                	jfLayer.success("提交成功");
                } else {
                    jfLayer.fail(data.description);
                }
            });
		}
        /*$scope.setTab = function (type) {
        	$scope.tab = type;
        };
        $scope.setTab('0');*/
	});

	webApp.controller('newsCtrl',  function($scope,jfRest) {
		
		$scope.newsList = function(){
			var params = {};
			jfRest.request('newsList','query',params).then(function(data){
				$scope.artcListLeft = data.data.artcListLeft;
				$scope.artcListRight = data.data.artcListRight;
			});
			
		}
		
		$scope.newsList();
	});
	
});