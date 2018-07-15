'use strict';

define(function (require) {
    var webApp = require('app');

    // 用户中心
    webApp.controller('userCenterCtrl', function ($scope, $rootScope,jfRest, jfLayer,  $state) {
		// 头部菜单高亮选中账户总览
        $rootScope.global.tabIndex = 1;

    	var params = {};
		$scope.get = function() {
			jfRest.request('userCenter', 'get', Tansun.param(params)).then(function(data) {
				if (data.status == 200) {
					$scope.lastLoginTime = data.data.lastLoginTime;
					$scope.accBal = data.data.accBal;
					$scope.accFrzAmt = data.data.accFrzAmt;
					//$scope.availAmt = data.data.availAmt;
					$scope.sumUsedAmt = data.data.sumUsedAmt;
					$scope.maxAmt = data.data.maxAmt;
					$scope.sumGoldenTicket = data.data.sumGoldenTicket;
					$scope.sumReceivable = data.data.sumReceivable;
					$scope.sumPayable = data.data.sumPayable;
					$scope.receivableCount = data.data.receivableCount;
					$scope.inviteCount = data.data.inviteCount;
					$scope.manageFeeCount = data.data.manageFeeCount;
					$scope.contractCount = data.data.contractCount;
					$scope.singCount = data.data.singCount;
					$scope.contractList = data.data.contractList;
					$scope.sumManageFee = data.data.sumManageFee;
					$scope.inviteList = data.data.inviteList;
					$scope.aprvStCd = data.data.aprvStCd;
					$scope.cstNm = data.data.cstNm;
					$scope.accNo = data.data.accNo;
                    // 待支付利息
                    $scope.sumInterestPaymentAmt = data.data.sumInterestPaymentAmt;
                    // 付款通知书信息
                    $scope.paymentsDetails = data.data.paymentsDetails;
					if ($scope.aprvStCd == null || $scope.aprvStCd == undefined || $scope.aprvStCd == '' || $scope.aprvStCd == '2') {
		                $scope.page = ctx + '/static/pages/common/warn-dialog.html';
		                jfLayer.dialog('approve', "提示", function () {
		                    $scope.page = "";
		                    $scope.$apply();
		                }, ['300px', '160px']);
		                angular.element('.layui-layer-setwin').remove(); // 移除关闭符号
		            } else if($scope.aprvStCd != '1') { // 审批中,被退回，跳转到安全中心页面
		            	$state.go('security-center');
		            }
					
				} else {
                    jfLayer.fail(data.description);
                }
			});
		}
		// 页面初始化加载方法，为了不报错，暂时去掉
		// $scope.get();
		// 还款通知
        $scope.setTab = function (type) {
        	if (type){
            	$scope.tab = type;
            	var params = {
    				type : type
    			};
    			jfRest.request('userCenter','query', Tansun.param(params)).then(function(data) {
    				if (data.status == 200) {
    					$scope.payableAmount = data.data.payableAmount;
    					$scope.payableCount = data.data.payableCount;
    					if(data.data.payableChart.length > 0){
                            $scope.repaymentChart.data = data.data.payableChart;
                            $scope.repaymentChart.group = "repayment";
                            angular.forEach( $scope.repaymentChart.data, function(data){
                                data.repayment = '待还款金额';
                            });
                            $scope.showChart = true;
    					} else {
                            $scope.showChart = false;
						}
    				} else {
	                    jfLayer.fail(data.description);
	                }
    			});
            }
        };
        $scope.showChart = false;
        $scope.repaymentChart = {
    		data : [],
    		type : 'bar',
    		label : 'date',
    		value : 'value',
    		// group : 'user',
    		title : ''
    	};		
        $scope.setTab('0');
        // 充值
        $scope.recharge = function() {
           $scope.page = ctx + '/static/pages/common/usercenter/recharge.html';
           jfLayer.dialog('recharge', "充值", function () {
        	   $scope.page = "";
               $scope.$apply();
           }, ['640px', '300px']);
        }
        // 提现
        $scope.withdraw = function() {
           $state.go('withdraw');
        }
        // 跳转到我的金票
        $scope.toGoldenTicket = function() {
           $state.go('gold-seller-list');
        }
        // 跳转到应收账款
        $scope.toReceivable = function() {
           $state.go('doc-seller-list');
        }
        // 跳转到应付账款
        $scope.toPayable = function() {
           $state.go('doc-buyer-list');
        }
        // 我要借款
        $scope.toLoan = function() {
           $state.go('sell-lend');
        }
        // 跳转到付费
        $scope.toCharge = function() {
           $state.go('pay-fee');
        }
        // 跳转到认证页面
        $scope.toApprove = function() {
           jfLayer.closeAll();
           $state.go('static-approve');
        }
        // 跳转到待签署协议列表
        $scope.toSignLaw = function() {
            jfLayer.closeAll();
            $state.go('law-txt-list');
        }
        // 接收邀请弹出框
        $scope.showReceiveInviteDialog = function() {
            $scope.page = ctx + '/static/pages/common/usercenter/invite.html';
            jfLayer.dialog('invite', "接收邀请", function () {
                $scope.page = "";
                $scope.$apply();
            }, ['640px', '300px']);
        };
        //应付账款确认信息弹出框
        $scope.rcvbComfirm = function() {
            $scope.rcvbUri = ctx + '/static/pages/common/usercenter/rcvb-confirm.html';
            jfLayer.dialog('rcvb', "确认导入", function () {
                $scope.rcvbUri = "";
                $scope.$apply();
            }, ['880px', '600px']);
        };
		//电子签证授权弹出框
		$scope.showValidation = function() {
			$scope.page = ctx + '/static/pages/common/lawtxt/law-validation.html';
			jfLayer.dialog('validationId', "电子签证授权", function () {
				$scope.page = "";
				$scope.$apply();
			}, ['450px', '350px']);
		};
        // 显示付款通知书
        $scope.showPaymentOrder = function(payment) {
        	var PAYMENT_ORDER = "12";
            var params = {
                gldId : payment.gldId
            };
            jfRest.request('userCenter','getGoldenTicket', Tansun.param(params)).then(function(data) {
                if (data.status == 200) {
                    $scope.artcDetail = data.data;
                    $scope.page = ctx + '/static/pages/common/lawtxt/notification-payment.html';
                    jfLayer.dialog('invite', "付费通知书", function () {
                        $scope.page = "";
                        $scope.$apply();
                    }, ['780px', '500px']);
                } else {
                    jfLayer.fail(data.description);
                }
            });
        }
    });
    // 接收邀请弹出框页面
    webApp.controller('inviteDialogCtrl', function ($scope, jfRest, jfLayer,  $validation, $parse) {
    	$scope.isShow = false;
    	//确定
    	$scope.confirm = function(index) {
    		var data = $scope.inviteList[index];
    		var formName = 'inviteForm'+index;
    		var type = data.inviteStatus; // 类型
    		if(type == '3') {//接受邀请
    			if(!data.confirmInviteCode){
    				jfLayer.fail('请填写邀请码');
    				return ;
    			}
				jfRest.request('cstInvite','save', Tansun.param(data)).then(function(data) {
					if (data.status == 200) {
                        jfLayer.success(data.description, function () {
                            jfLayer.closeAll();
                            $state.go('user-center');
                        });
					} else {
						jfLayer.fail(data.description);
					}
				});
    		} else {//拒绝邀请
    			jfRest.request('cstInvite','save', Tansun.param(data)).then(function(data) {
    				if (data.status == 200) {
                        jfLayer.success(data.description, function () {
                            jfLayer.closeAll();
                            $state.go('user-center');
                        });
    				} else {
    					jfLayer.fail(data.description);
    				}
    			});
    		}
    	}
        $scope.showFun = function () {
            $scope.isShow = !$scope.isShow;
        };
    	// 重新获取
    	$scope.regain = function(index) {
    		var data = $scope.inviteList[index];
    		var params = {
    			id : data.id
    		}
    		jfRest.request('cstInvite','get', Tansun.param(params)).then(function(data) {
				if (data.status == 200) {
					jfLayer.success(data.description, function () {
                        jfLayer.closeAll();
                    });
				} else {
					jfLayer.fail(data.description);
				}
			});
    	}
    });
    // 提现页面
    webApp.controller('withdrawCtrl', function ($scope, $stateParams, jfRest, jfLayer,  $validation, $state) {
    	$scope.tab = 1;
    	$scope.withdraw = {};
    	// 下一步
    	$scope.toTwoStep = function() {
	    	jfRest.request('withdraw','query', Tansun.param($scope.withdraw)).then(function(data) {
				if (data.status == 200) {
					$scope.tab = 2;
					$scope.withdraw.cstNm = data.data.cstNm;
				} else {
                    jfLayer.fail(data.description);
                }
			});
    	}
    	// 找回交易密码
        $scope.findTxnPwd = function() {
           $state.go('pwd',{type:1});
        }
    	// 下一步
    	$scope.toThreeStep = function() {
        	// 确认提现
        	jfRest.request('withdraw','save', Tansun.param($scope.withdraw)).then(function(data) {
    			if (data.status == 200) {
    				$scope.tab = 3;
    			} else {
                    jfLayer.fail(data.description);
                }
    		});
    	}
    });
    
    // 应收账款弹出框页面
    webApp.controller('rcvbConfirmCtrl', function ($scope, $stateParams, jfRest, jfLayer,  $validation, $state) {
    	//返回按钮
    	$scope.back = function () {
            jfLayer.closeAll();
        };
    	//保理商选择器
		$scope.selectFactor = {				
	            header: ['保理商名称'],
	            body: ['tenantName'],
	            text: 'tenantName',
	            api: 'factor',
	            method: 'query',
	            callback: function (item) {
	            	$scope.queryParam.tenantName = item.tenantName;
	            	$scope.queryParam.tenantId = item.tenantId;
	            }
	     };
		// 确认
		$scope.toConfirm = function() {
			if (!$scope.rcvbSelected || $scope.rcvbSelected.length <= 0) {
                jfLayer.alert("请先选择数据");
            }else{
        	var params = {
        			rcvbListStr: angular.toJson($scope.rcvbSelected),
        			tenantId:$scope.queryParam.tenantId
                };	
        	jfRest.request('toRcvbConfirm','save', Tansun.param(params)).then(function(data) {
        		if (data.status === 200) {
    				jfLayer.alert(data.description,function(){
    					$scope.back();
    					$scope.search();
                    	$scope.get();
					});
    			} else {
                    jfLayer.fail(data.description);
                }
    		});
           }
    	};
    	// 拒绝
    	$scope.toRefuse = function() { 
    		if (!$scope.rcvbSelected || $scope.rcvbSelected.length <= 0) {
                jfLayer.alert("请先选择数据");
            }else{
        	var params = {
        			rcvbListStr: angular.toJson($scope.rcvbSelected)
                };
        	jfRest.request('toRcvbRefuse','save', Tansun.param(params)).then(function(data) {
        		if (data.status === 200) {
    				jfLayer.alert(data.description,function(){
    					$scope.back();
    					$scope.search();
                    	$scope.get();
					});
    			} else {
                    jfLayer.fail(data.description);
                }
    		});
           }
    	};
    	
    	$scope.callback = function(data) {
            data.cancelSelected();
            data.onSelect = function () {
                $scope.rcvbSelected = data.getSelected();
                angular.forEach($scope.rcvbSelected, function (item) {
                    if (item.gldBal){
                        $scope.goldAllAmt += Number(item.gldBal);
                    }
                });
            }
		};
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
	webApp.controller('lawValidationCtrl', function ($scope, jfGlobal, jfRest, jfLayer,$state) {
		$scope.phoneCode = {};
		var params = {
			oriCode : $scope.phoneCode.oriCode
		}
		$scope.getCode = function() {
			jfRest.request('authCode', 'get', Tansun.param(params)).then(function (data) {
				if (data.status === 200) {
					jfLayer.alert("短信验证码已发送");
				} else {
					jfLayer.fail(data.description);
				}
			});
		};
		$scope.confirmCode = function() {
			jfRest.request('authCode', 'save', Tansun.param($scope.phoneCode)).then(function (data) {
				if (data.status === 200) {
					jfLayer.success("验证成功",function () {
                        $state.go('user-center');
                        jfLayer.closeAll();
                    });

				} else {
                    jfLayer.fail(data.description);
				}
			});
		}
		$scope.backCode = function() {
			jfLayer.closeAll();
		}
	});
});