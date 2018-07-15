'use strict';
define(function (require){

	var webApp = require('app'); 

	// 法律文本列表
	webApp.controller('lawTxtCtrl',  function($scope, jfParam, jfRest, $http, jfGlobal,jfLayer,$filter) {

        // 用户协议签署详情信息弹出框
        $scope.noteDialog = {
            open : function(item) {
                $scope.law = item;
                this.showDialog = true ;
                this.pageUri = ctx + '/static/pages/common/lawtxt/lawContract.html';
                $scope.noteDialog.index = jfLayer.dialog('law', "协议/合同详情", function () {
                    $scope.noteDialog.close();
                    $scope.$apply();
                }, ['880px', '600px']);
            },
            confirm : function(artcCntnt) {
                jfRest.request('lawTxt', 'save', jfParam.param($scope.law)).then(function (data) {
                    if (data.status === 200) {
                        jfLayer.success(data.description,function () {
                            $scope.$parent.noteDialog.close();
                            $scope.$apply();
                            $scope.search();
                        });
                    } else {
                        jfLayer.fail(data.description);
                    }
                });
            },
            close : function(){
                this.pageUri = '';
                this.showDialog = false ;
                jfLayer.closeAll();
            },
            init : function() {
                //初始化页面
                if(!$scope.law){
                    return;
                }
                jfRest.request('lawTxt','get', jfParam.param($scope.law)).then(function(data) {
                    if (data.status == 200) {
                        $scope.artcDetail = data.data;
                        $scope.law.artcCntnt = data.data.artcCntnt;
                        $scope.law.artcTtl = data.data.artcTtl;
                    } else {
                        jfLayer.fail(data.description);
                    }
                });
            },
            showDialog:false
        };

        $scope.download = function(item){
            if(!item.fileId){
                jfLayer.fail("下载失败，法律文本编号为空！");
                return;
            }
            window.open(ctx+'/user/lawTxt/download/'+item.fileId);
		}
    });
	
});
	