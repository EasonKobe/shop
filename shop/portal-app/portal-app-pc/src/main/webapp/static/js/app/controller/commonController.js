'use strict';
define(function (require) {

    var webApp = require('app');

    webApp.controller('centHeaderCtrl', function ($scope,$rootScope,  jfRest, $http, jfGlobal, jfLayer) {
    	// 获取初始化数据
        jfRest.request('session','get',{}).then(function(data) {
            if (data.status === 200) {
            	$scope.setUser(data.data);
                $scope.user = data.data;
            }
        });

        $scope.goHome = function () {
            location.href = ctx + "/center/#/user/center";
        };

        $rootScope.logout = function () {
            location.href = ctx + "/#/index";
        };

    });

    webApp.controller('firMenuCtrl',  function($rootScope,$scope,$state,jfRest,$timeout,$window) {
		$scope.left=function(index){
			$scope.nub=(-140)*(index);
			return {
				"left" : $scope.nub+'px'
			}
		}
		
		jfRest.register({menu : {
			query : '/menu.json'
		}}) ;
		
		jfRest.request('menu','query',{}).then(function(data) {
			if (data.status == 200) {
				$rootScope.menuList=data.data;
				if($rootScope.menuList.length>0){
	        		$rootScope.secMenuList=$rootScope.menuList[0].menu;
				}
			}
		});	
		$scope.firMunuClick=function(secMenuList){
    		if(secMenuList.menu){
        		$rootScope.secMenuList=secMenuList.menu;
    		}
    		$scope.turn('/user/center');
    	}
	});
    // 首页更多
    webApp.controller('publicMenuCtrl',  function($scope) {
        $scope.mouseover = function () {
            $scope.moreNav = {
                "right" : "0px"
            };
        }
        $scope.mouseleave = function () {
            $scope.moreNav = {};
        }
        $scope.setTab = function (type) {
            var top = 100 + type*40;
            $scope.bdot = {
                "left" : "18px",
                "top" : top +"px"
            };
        };
    });

});