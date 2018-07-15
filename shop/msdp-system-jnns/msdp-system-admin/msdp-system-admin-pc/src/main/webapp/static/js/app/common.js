'use strict';
define(function (require){

    var _file_down_path;
	var webApp = require('app'); 
	webApp.controller('defaultLoginCtrl',  function($scope,$window,jfRest,jfLayer,$rootScope,$stateParams,$validation,$parse,jfGlobal) {
		$(document).ready(function(){				
			 $("#webbtn").click(function(){
				  $('#web_login').hide();
				  $('#webcat_login').show();
		     });
			 $("#webcatbtn").click(function(){
				  $('#web_login').show();
				  $('#webcat_login').hide();
		     });
		});	
		layui.use(['form'], function(){
       var form = layui.form
       });
	   $(function(){
	      function placeholder(target){
	               $(target).val($(target).attr("datavalue")).addClass("textcolor");
	               $(target).focus(function() {
	                   if($(this).val() == $(this).attr("datavalue")) {
	                       $(this).val("").removeClass("textcolor");
	                   } 
	               
	               })
	               $(target).blur(function(){
	                   if($(this).val() == "" || $(this).val() == $(this).attr("datavalue")) {
	                       $(this).val($(target).attr("datavalue")).addClass("textcolor");
	                   }
	               })
	           }
	        placeholder(".inputtype")
			 placeholder(".inputuser")
			 placeholder(".inputpasswod")
	   })
		$scope.loginConfig={};
		$scope.user={};
		//
		function getQueryString($window,name)
	    {
	        var reg = new RegExp("(^|&)"+ name +"=([^&]*)(&|$)");
	        var r = $window.location.search.substr(1).match(reg);
	        if(r!=null)return  unescape(r[2]); return null;
	    }
		var error = getQueryString($window,"error");
		var showError = false;
		if (error !== null && error !== undefined && error !== '') {
			showError=true;
			var errMsg="";
			switch (error) {
			case '10001': 
				errMsg="用户名密码错误";
				break;
			case '10002': 
				errMsg="用户已被锁定，无法登录";
				break;
			case '10003': 
				errMsg="验证码为空或者不正确";
				break;
			case '10004': 
				errMsg="该用户已失效，无法登陆";
				break;
			case '10099': 
				errMsg="请登录";
				break;
			default : 
			    errMsg="系统异常，请联系管理员";
			} 
		}
		 // 找回密码
	    $scope.findPwd = function() {
	    	$("#pwdForm").show();
	    	$("#loginForm").hide();
	    };
	    $scope.toLogin = function() {
    		location.href = ctx +"/"+ $scope.loginConfig.loginSubmitUrl;
    	}
	    
	    $scope.login = function () {
	    	jfGlobal.$("#loginForm").submit();
        };

		//清掉菜单栏
		if(sessionStorage 
				&& sessionStorage.getItem('tabList')){
			$rootScope.tabList = [];
			sessionStorage.setItem('tabList',angular.toJson($rootScope.tabList));
		}
		
		var pathName = document.location.pathname;
		var loginUri = jfGlobal.ctx + "/login";
		var loginUriSuffix = pathName.length>loginUri.length?pathName.substring(loginUri.length+1):"";
		$scope.change = function() {
			$scope.captcha = $scope.loginConfig.captchaUrl + '/login?v=' + (new Date()).valueOf() ;
		};
		$scope.params={loginUriSuffix:loginUriSuffix};
		jfRest.request('loginConfig','get',Tansun.param($scope.params)).then(function(data) {
			if(data.status == 200){
				$scope.loginConfig=data.data;
				$scope.loginConfig.showError=showError;
				$scope.loginConfig.errMsg=errMsg;
				$scope.loginConfig.loginUriSuffix=loginUriSuffix;
				$scope.user.tenantName=data.data.tenantName;
				if(loginUriSuffix!=null && loginUriSuffix!=''){
					$scope.loginConfig.showTenantName='none';
				}else{
					$scope.loginConfig.showTenantName='display';
				}
				if($scope.loginConfig.errMsg){
		    		$("#errMessage").show();
		    	}else{
		    		$("#errMessage").hide();
		    	}
				$scope.change();
				$scope.$on("refresh",function(){
					$scope.change();
				});
				
			}
    	});
	});
	 // 找回密码-身份认证
    webApp.controller('forgotPwdCtrl', function ($scope, $stateParams, jfRest, jfLayer, $validation, $interval) {
    	$scope.pwd = {};
    	$scope.pwd.flag = "";
    	if($stateParams.username) {
    		$scope.pwd.loginName = $stateParams.username;
    	}
    	$scope.preHandle = function () {
    		// 校验用户名和手机号/邮箱
    		var preHandle = $validation.validate($scope.pwdForm.loginName) && $validation.validate($scope.pwdForm.mobileOrEmail);
    		var captchaType = $scope.getCaptchaType(); // 发送类型
        	angular.element(".btn-getcode").attr("mode", captchaType);
        	
    		preHandle.success(function() {
    			var params = {
	        		loginName : $scope.pwd.loginName,
	        		mobileOrEmail : $scope.pwd.mobileOrEmail,
	        		captchaType : captchaType
	        	}
	        	jfRest.request('forgotPwd','query', Tansun.param(params)).then(function(data) {
					if (data.status == 200) {
						$scope.pwd.flag = "1";
					} else {
						jfLayer.fail(data.description);
	                }
				});
    		});
    		return  $validation.validate($scope.pwdForm.flag);
        };
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
    	// 提交修改
    	$scope.save = function() {
    			var params = {
            		mobileOrEmail : $scope.pwd.mobileOrEmail,
            		code : $scope.pwd.code,
            		jfCode : 'pwd'
            	}
    			
    	    	jfRest.request('forgotPwd','get', Tansun.param(params)).then(function(data) {
    				if (data.status == 200) {
    					var captchaType = $scope.getCaptchaType(); // 发送类型
    		    		var param = {
    		    			loginName : $scope.pwd.loginName,
    		            	mobileOrEmail : $scope.pwd.mobileOrEmail,
    		        		newPswd : $scope.pwd.newPswd,
    		        		captchaType : captchaType
    		        	}
    		        	// 保存用户信息
    		        	jfRest.request('forgotPwd','save', Tansun.param(param)).then(function(data) {
    		    			if (data.status == 200) {
    		    				location.href = ctx + "/login";
    		    			} else {
    		                    jfLayer.fail(data.description);
    		                }
    		    		});
    				} else {
    					jfLayer.fail(data.description);
                        return false;
                    }
    			});
    			
    	}
        $scope.toLogin = function() {
    		location.href = ctx + "/login";
    	}
    });
	webApp.controller('headerCtrl',  function($scope,$window,$rootScope,jfRest,$http,jfGlobal,$state,$stateParams,$location,jfLayer) {
		$scope.isActive = true;
		
		$scope.login = function() {
			location.href = jfGlobal.ctx + "/login" ;
		};
		
		$scope.reg = function() {
			location.href = jfGlobal.config.website + "/reg" ;
		};
		
		$scope.logout = function() {
			location.href = jfGlobal.ctx + "/logout" ;
		};
		$scope.updatePswd = function() {
			location.href = jfGlobal.ctx + "#/updatePswd" ;
		};
		
		//添加页签
		$scope.addTab = function(item){
			if(item.url == "#"){
				return;
			}
			//是否被打开
			var isOpened = false;
			angular.forEach($rootScope.tabList,function(tab,index){
				if(tab.id == item.id){
					$rootScope.tabList[index] = item ;
					isOpened = true;
				}else{
					$rootScope.tabList[index].isActive = false ;
				}
			}) ;
			//已打开页面
			if(isOpened){
			   openTable();
			}
			else{
				if($rootScope.tabList.length > 7){
                    jfLayer.confirm("您打开的标签太多,点击确定后将替换第一条,请谨慎选择!", function () {
                        $rootScope.tabList.splice(1, 1);
                        openTable();
                    }, function () {
                        return;
                    });
				}else{
					openTable();
				}
			}	
			
			function openTable(){
				item.isActive = true ;  
				if($rootScope.tabList.indexOf(item) < 0){
					$rootScope.tabList.push(item);
				}
				try{
					$scope.turn(item.url) ;
				}catch(e){
					jfLayer.alert("地址错误，跳转异常。");
				}
			}
		};
		
		$scope.changeTab = function(tab) {
			angular.forEach($rootScope.tabList,function(item){
				item.isActive = false ;
			}) ;
			tab.isActive = true ;
			$location.url(tab.backChainValue[tab.backChainValue.length - 1]) ;
		};
		
		//关闭当前
		$scope.removeTab = function(index){
			var closeTab = $rootScope.tabList[index] ;
			var activeTab = null ;
			if(closeTab.isActive){
				activeTab = $rootScope.tabList[index-1] ;
				activeTab.isActive = true ;
			}
			$rootScope.tabList.splice(index,1) ;
			//未选中当前页签做关闭时不处理
			if(activeTab){
				$location.url(activeTab.backChainValue[activeTab.backChainValue.length - 1]) ;
			}
		};
		
		//关闭其他页签
		$scope.removeOtherTab = function(tab) {
			$rootScope.tabList.splice(1,$rootScope.tabList.length-1);
			$rootScope.tabList.push(tab);
			if(tab.isActive){
				return ;
			}
			tab.isActive = true ;
			//从页签中取出所有
			$location.url(tab.backChainValue[tab.backChainValue.length - 1]) ;
		};

        $rootScope.removeAllTab = function() {
			$rootScope.tabList.splice(1,$rootScope.tabList.length-1) ;
			var indexTab = $rootScope.tabList[0] ;
			indexTab.isActive = true ;
			
			$location.url(indexTab.backChainValue[indexTab.backChainValue.length - 1]) ;
		};
		
		//如果页签是空的，将首页添加进去
		if($rootScope.tabList.length <= 0){
			$scope.addTab({
				id : 'index',
				text : '首页' ,
				url : '/index',
				close : false
			}) ;
		};
		//获取当前激活的标签
		$rootScope.getActiveTab = function() {
			var activeTab = {text : '首页'} ;
			angular.forEach($rootScope.tabList,function(tab){
				if(tab.isActive){
					activeTab = tab ;
				}
			}) ;
			return activeTab ;
		};
		//获取当前位置
		$rootScope.getCurrentLoaction = function(text) {

			var currentTab = $rootScope.getActiveTab() ;
			
			if(!$rootScope.menuList || $rootScope.menuList.length <= 0){
				return '' ;
			}
			
			var parentText = '' ;
			//当前菜单id
			var actionMuneId = '' ;
			var currentTab = $rootScope.getActiveTab() ;
			var getChildText = function (menu) {
				if(!menu){
					return '' ;
				}
				var childText = '' ;
				angular.forEach(menu.menu,function(item){
					//如果已查找到子菜单，则不继续迭代
					if(childText){
						return ;
					}
					//如果还有子菜单，则继续递归
					if(item.menu && item.menu.length > 0){
						//获取子菜单文本
						childText = getChildText(item) ;
						//如果子菜单文本是有值的，代表当前菜单是被激活的页签的父级菜单，拼上自己的文本
						if(childText){
							childText = item.text + ' > ' + childText ;
						}
					}else{//如果是根菜单，则判断与当前页签是否同一个，如果是的话返回当前菜单名称
						if(menu && item.id == currentTab.id){
							childText = item.text ;
							actionMuneId=item.id;
						}
					}
				}) ;
				return childText ;
			}
			
			angular.forEach($rootScope.menuList,function(item){
				var childText = getChildText(item) ;
				if(childText){
					parentText = item.text + ' > ' + childText ;
				}
			})
		
			if(text){
				parentText += ' > ';
				parentText += text ;
			}
			//记录当前位置
			jfGlobal._currentLoaction=parentText;
			jfGlobal._actionMuneId=actionMuneId;
			var currentLoaction = '<i class="position_icon"></i>当前位置：' + parentText ;
			
			if(parent){
				currentLoaction = currentLoaction ;
			}
			
			return currentLoaction ;
		};
		//获取文件上传参数配置
		var request = jfRest.request("param", "query", {"parmTpCd":"fileConfiguration"}) ;
		request.then(function (data) {
			  if(data && data.status == 200){
				  if(angular.isArray(data.data.grid.list)){
					  $rootScope.global.uploader = {} ;
					  if(angular.isArray(data.data.grid.list)){
						  angular.forEach(data.data.grid.list,function(item){
							  if(item.parmId=="fileUrl"){
								  $rootScope.global.uploader.server = item.parval ;
								  jfGlobal.config["uploader.file.server"]= item.parval ;

							  }
							  if(item.parmId=="fileUpload"){
								  $rootScope.global.uploader.upload = item.parval ;
								  jfGlobal.config["uploader.file.upload"]= item.parval ;
							  }
							  if(item.parmId=="fileDownload"){
								  $rootScope.global.uploader.download = item.parval ;
								  jfGlobal.config["uploader.file.download"]= item.parval ;
							  }
						  }) ; 
						  $rootScope.DEFAULT_DOWNLOAD = $rootScope.global.uploader.server+$rootScope.global.uploader.download;
						  $rootScope.global.uploader.filePath=$rootScope.global.uploader.server+$rootScope.global.uploader.upload;
						  //全局文件下载路径
						  _file_down_path=$rootScope.global.uploader.server+$rootScope.global.uploader.download;
					  }
				  }
			  }
		});
	});
	webApp.controller('menuCtrl',  function($rootScope,$scope,$state,jfRest,$timeout,$window) {
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
				$timeout(function(){
					angular.element('.drop-menu-effect').each(function(){
			            var thiz = angular.element(this);
			            var theMenu = thiz.find(".submenu");
			            var tarHeight = theMenu.height();
			            theMenu.css({height:0});
			            thiz.hover(
				            function(){
				            	angular.element(this).addClass("hover_menu");
					            theMenu.stop().show().animate({height:tarHeight},400);
				            },
				            function(){
				            	angular.element(this).removeClass("hover_menu");
					            theMenu.stop().animate({height:0},400,function(){
					            	angular.element(this).css({display:"none"});
					            });
				            }
			            );
			        });

					var webMenu = angular.element('.web_menu') ;
					
					angular.element($window).on("scroll", function() {
						var scrolTop = angular.element(document).scrollTop() ;
						var scrollTop = webMenu[0].scrollTop || scrolTop;
						if(scrollTop <= webMenu[0].offsetTop){
							webMenu.css({position : 'relative'}) ;
						} else {
							webMenu.css({position : 'fixed'}) ;
						}
			        });
				});
			}
		});	
	});
	webApp.controller('parentCtrl',  function($stateParams,$scope, $location, jfRest, $http, jfGlobal, $rootScope, jfLayer,$timeout) {
		$scope.commonData={};
		$scope.tabAuthObj = {}; //保存子页面页签权限对象
		$scope.pageNum = '1'; //默认显示第一个页签基础信息
		var shadeHtml='<div class="maskshade"></div>';//遮罩层样式
		$scope.onNavChange = function(nav){
			if($scope.parentNav.mode == 'scroll'){
				
				if(nav.valiParam){
					var params = nav.valiParam.split(':')[0] || '';
					var bsnId = params.split("&")[1];
					if(bsnId && $scope.tabAuthObj[bsnId]){
						$scope.tblBlId = $scope.tabAuthObj[bsnId]; //必须字段传入，查询文档
					}
				}
				
				if($stateParams.pageView=="view"||$stateParams.pageView=="disabled"){//表示页面只能查看
					return true;
				}
				if(!nav.valiParam){
					//没有设置valiParam属性机表示默认可编辑
					angular.element('#' + nav.id).find(".maskshade").remove();
					return true ;
				}
				var params = nav.valiParam.split(':')[0] || '';
				var id = params.split("&")[0];
				if ($scope.tabAuthObj && $scope.tabAuthObj[id]) {
					if(!$scope.queryParam){
						$scope.queryParam = {};
					}
					angular.element('#' + nav.id).find(".maskshade").remove();
				} else {
					//遮罩样式步不存在是添加，已存在则不处理
					if(angular.element('#' + nav.id).find(".maskshade").length<=0){
						angular.element('#' + nav.id).append(shadeHtml);
					}
				}
				return true ;
			}else{
				if(!nav.valiParam){
					return true ;
				}
				
				var params = nav.valiParam.split(':')[0] || '';
				var message = nav.valiParam.split(':')[1] || '请先填写基本信息' ;
				
				var id = params.split("&")[0];
				var bsnId = params.split("&")[1];
				if(bsnId && $scope.tabAuthObj[bsnId]){
					$scope.tblBlId = $scope.tabAuthObj[bsnId]; //必须字段传入，查询文档
				}
				if ($scope.tabAuthObj && $scope.tabAuthObj[id]) {
					if(!$scope.queryParam){
						$scope.queryParam = {};
					}
					return true ;
				} else {
					jfLayer.alert(message);
					return false;
				}
			}
		}
		
		$scope.parentNav = {
			mode : 'scroll'
		} ;
		//页面引入后做遮罩处理
		$scope.$on('$includeContentLoaded',function(event){
			if($stateParams.pageView=="disabled"){//表示页面只能查看
				$("input,select,textarea").attr("disabled",true);
				$("button").hide();
				$("button[notHide]").show();
				$("input[notHide],select[notHide],textarea[notHide]").attr("disabled",false);
			}else{
				$.each($scope.parentNav.navs, function(index,nav){ 
					//遮罩
					if(angular.element('#' + nav.id).find(".maskshade").length<=0){
						angular.element('#' + nav.id).append(shadeHtml);
						angular.element('#' + nav.id).addClass('section');//控制遮罩的大小在指定div里面
						angular.element('#' + nav.id).addClass('maskbox');//控制遮罩的大小在指定div里面
					}
					if($stateParams.pageView=="view"){//表示页面只能查看
						return true;
					}
					if(!nav.valiParam){
						//没有设置valiParam属性机表示默认可编辑
						angular.element('#' + nav.id).find(".maskshade").remove();
						return true ;
					}
					var params = nav.valiParam.split(':')[0] || '';
					var id = params.split("&")[0]; 
					if ($scope.tabAuthObj && $scope.tabAuthObj[id]) {
						angular.element('#' + nav.id).find(".maskshade").remove();
					} else {
						//遮罩样式步不存在是添加，已存在则不处理
						if(angular.element('#' + nav.id).find(".maskshade").length<=0){
							angular.element('#' + nav.id).append(shadeHtml);
						}
					}
			    });
			}
			
			
		})
		$scope.$on('to-parent', function (event, data) {
			$scope.tabAuthObj = data;
			$scope.commonData = data;
			
        });
		//根据页签下标获取对应的scope(下标从0开始算)var scope=$scope.getScope(1);//1代表第二个页签
		$scope.getScope = function(i) {
			if(i){
				var i=parseInt(i);
				var nav=$scope.parentNav.navs[i];
				if(nav){
					var element = angular.element('#' +nav.id).find('div[ng-controller]');
					var scope = element.scope();
					return scope;
				}
			}
			
		};
		$scope.toParent = function(data) {
			$scope.tabAuthObj = data;
			$scope.commonData = data;
		};
		
		$scope.showPageManager = [];
		$scope.$on('to-parent-pages', function (event, data) {
			$scope.showPageManager = data;
        });
		
		$scope.toParentPages = function(data) {
			$scope.showPageManager = data;
		};
		
		$scope.showTab = function (tab, addNavRule) {
			return addNavRule ? $scope.showPageManager.indexOf(tab) > -1 : true;
		}
		
	})
	
	 
	 webApp.controller('docAtchListCtrl', function ($scope,jfGlobal, jfRest, jfLayer,$rootScope,$timeout) {
		
		 $scope.fileList = {
					checkType : 'radio', //当为checkbox时为多选
					params : {}, //表格查询时的参数信息
					paging : true ,//默认true,是否分页
					resource : 'file.query' ,//列表的资源
					callback : function() { //表格查询后的回调函数
					}
			  };  
			 $scope.fileList.params = {
	 	 			mdlId : $scope.mdlId,
	 	 			tblBlId : $scope.tblBlId
	 		}
			 if($scope.mdlId&&!$scope.tblBlId){
				 $scope.fileList.autoQuery=false;
				 $scope.fileList.fileSearch=false;
			 }
		 	  $scope.$watch("tblBlId",function(newValue,oldValue){
		 		  if(newValue){
				 		 $scope.fileList.autoQuery=true;
		 		  }
	            if(oldValue == newValue)
	                return ;
	            $scope.fileList.params = {
	    	 			mdlId : $scope.mdlId,
	    	 			tblBlId : $scope.tblBlId
	    		}
	            $timeout(function(){
	            	$scope.fileList.search();
	            });
	        });
		 	
		 	$scope.download = function () {
				if (!$scope.fileList.validCheck()) {
					return;
				}
				$scope.doc = {};
				$scope.doc.fileId = $scope.fileList.checkedList().fileId;
				jfRest.request('file','get', Tansun.param($scope.doc)).then(function(data) {
					if (data.status == 200) {
						var path = data.data.fileRte;
						if(path){
							var fileServer = jfGlobal.config["uploader.file.server"];
							var fileDownload = jfGlobal.config["uploader.file.download"];
							var realName="/"+data.data.fileNm;
							window.open(fileServer + fileDownload + path+realName);
						}
					}else{
						jfLayer.alert("下载失败");
					} 
				});
	        };
	        //确认选择
	        $scope.confirm = function (result) {
	        	result.scope.saveFile(result);
	        };
	        $scope.back = function() {
	        	history.back();
	        }
	        
	    });
	 
	//上传附件对话框
    webApp.controller('docAtchCtrl', function ($scope,jfGlobal, jfRest, jfLayer,$rootScope,$validation,$parse) {
    		//确认选择
	        $scope.saveFile = function (result) {
	        	if(!result.scope.atch){
	        		result.scope.atch = {};
	        	}
	        	var formData = [];
	        	if(result.scope.atch.atchInf && result.scope.atch.atchInf.length > 0){
	        		angular.forEach(angular.fromJson(result.scope.atch.atchInf), function(data){
	        			var atchInf = data;
	        			var params = {
	    					mdlId : $scope.mdlId,
	    					tblBlId : $scope.tblBlId,
	    					fileNm : atchInf.originalFilename,
	    					fileExpdNm : atchInf.contentType,
	    					fileSize : atchInf.size,
	    					fileRte : angular.fromJson(atchInf.savePath).file,
	    					atchNm : atchInf.originalFilename
	        			};
	    				if(result.scope.atchIf){
	    				params.fileTpCd = result.scope.atchIf.fileTpCd;
		        		}
	        			formData.push(params);
	        	   	});
	        		$scope.saveData = {
	        			atchList : angular.toJson(formData)
	        		}
	        	}
	        	jfRest.request('file','save', Tansun.param($scope.saveData)).then(function(data) {
					if (data.status == 200) {
						jfLayer.success("保存成功",function(){
							$scope.fileList.search();
							result.cancel();
						});
					} else {
						jfLayer.fail(data.description);
					}
				});
	        
	        	
	        };
	       $scope.uploadAtch = {
            // 允许上传的文件后缀
            extensions: "bmp,jpg,jpeg,png,doc,docx,xlsx,xls,pdf,rar,zip,tar",
            // 单个文件大小，单位MB，不传默认5MB
            fileSize: 5,
            // 可上传2个文件,不传默认5个
            fileCount: 999,
            // 文件所属APP
            app: "admin-pc",
            // 文件所属模块
            module: "doc",
            userSource:"user.query"
        };
        
    });
    
    Tansun.directive('moreclick',function(){
    	return {
    		restrict: 'A',
			scope: false,//独立作用域，防止污染父级作用域
	        link: function (scope, element, attrs, ngModel) {
	        	var show = false ;
	        	element.click(function(event) {
	        		show = !show ;
	        		if(show){
	        			element.parents('.search_mian').find('.search_width').show() ;
	        			element.find('i').html('&#xe619;');
	        		}else{
	        			element.parents('.search_mian').find('.search_width').hide() ;
	        			element.find('i').html('&#xe61a;');
	        		}
				}) ;
	        }
    	}
    }) ;
    webApp.controller('adminChangeCtrl',  function($scope,$window,$rootScope,jfRest,$http,jfGlobal,$state,$stateParams,jfLayer,$compile,$validation,$parse) {
    	var request = jfRest.request("session","get",{}) ;
 		request.then(function(data) {
 			//设置全局用户信息，只需要设置一次即可
 			$scope.setUser(data.data);
 			//获取全局当前登录用户信息
 			$scope.loginUser=$scope.getUser();
			$scope.roleList=$scope.getUser().roleInfList;
//			$scope.orgList=data.data.instInfList;
			$scope.deptList=$scope.getUser().deptInfList;
 		});
		//修改密码
		$scope.updatePW = function(event) {
			$scope.user={};
			$scope.user.id=$scope.loginUser.id;
			$scope.modal('/template/admin_pw.html',{},{title :'修改密码',buttons : ['提交','取消'],size : ['450px','300px'],callbacks : [$scope.savePW]});
		}
		$scope.savePW=function(result){
			if(!$scope.user.password){
				jfLayer.fail("原密码不能为空，请重新输入！");
				return false;
			}
			if(!$scope.user.newPswd){
				jfLayer.fail("新密码不能为空，请重新输入！");
				return false;
			}
			if($scope.user.newPswd!==$scope.user.renewPswd){
				jfLayer.fail("两次密码输入不一致，请重新输入！");
				return false;
			}
            if($scope.user.newPswd == $scope.user.password){
                jfLayer.fail("新密码与原密码相同，请重新输入！");
                return false;
            }
			jfRest.request('sysUser1','updatePswd',Tansun.param($scope.user)).then(function(data) {
				if (data.status == 200) {
					jfLayer.success(data.description,function(index){
                        result.cancel();
					});
				} else {
					jfLayer.fail(data.description);
				}
			});
		}
		//弹出角色列表
		$scope.changeRole = function(event) {
			$scope.modal('/template/admin_role.html',{},{title :'角色切换',buttons : ['取消'],size : ['500px','300px']});

		}
		//弹出机构列表
		$scope.changeOrg = function(event) { 
			var param={};
			jfRest.request('admin', 'query', param).then(function(data){
				if(data.status == 200){
					$scope.orgList=data.data;
				}
				else{
					jfLayer.fail(data.description);
				}
			}); 
			$scope.modal('/template/admin_org.html',$scope.orgList,{title :'机构切换',buttons : ['提交','取消'],size : ['500px','300px']});
		}
		//弹出部门列表
		$scope.changeDept = function(event) { 
			$scope.modal('/template/admin_dept.html',{},{title :'部门切换',buttons : ['提交','取消'],size : ['500px','300px']});
		}
		//切换
		$scope.changer = function(param,type) {
			param.type=type;
			var param = Tansun.param(param) ;
			jfRest.request('admin', 'changer', param).then(function(data){
				if(data.status == 200){
                    $rootScope.removeAllTab();
                    window.location.reload();
				}else{
					jfLayer.fail(data.description);
				}
			});
		}
	})
	
	//快捷键
	webApp.controller('shortMenuCtrl',  function($scope,$window,$rootScope,jfRest,$http,jfGlobal,$state,$stateParams,jfLayer,$compile) {
		
		$scope.queryList();
		
		//查询所有快捷菜单
		$scope.queryList=function(){
			jfRest.request('quickMenu','query', Tansun.param({})).then(function(data){
				if(data.status == 200){
					$scope.quickMenuList=data.data.grid;
				}
			});
		}
		
		//添加
		$scope.clickAddMenu=function(){
			$scope.modal('/template/quick_,menu.html',{},{title :'添加快捷键',buttons : ['提交','取消'],size : ['500px','300px']});
		}
		//打开菜单
		$scope.openMenu=function(menu){
			$scope.$turn(menu.url,menu.menuName);
		}
		
		
		//菜单树点击事件
		$scope.nodeClick = function(treeNode) {
			if(treeNode.level!=2) return null;
			jfRest.request('quickMenu','save', Tansun.param({'menuCode' : treeNode.id})).then(function(data){
				if(data.status == 200){
					$scope.menuSearch();
					$scope.queryList();
				}else{
					jfLayer.fail(data.description);
				}
			});
		}
		//删除菜单
		$scope.delMenu = function(id) { 
			jfRest.request('quickMenu','delete', Tansun.param({'id' : id})).then(function(data){
				if(data.status == 200){
					$scope.menuSearch();
				}else{
					jfLayer.fail(data.description);
				}
			});
		}
		
	});
    Tansun.controller('chartsCtrl',function($scope){
		$scope.charts = {
			 data : [{date : '融资到期预警',value : 10, user : '融资到期预警'},
					 {date : '预合同到期预警',value : 15, user : '预合同到期预警'},
					 {date : '池模式融资水位预警',value : 11, user : '池模式融资水位预警'},
					 {date : '融资到期预警',value : 5, user : '融资到期预警'},
					 {date : '融资到期预警',value : 12, user : '融资到期预警'},
					 {date : '预合同到期预警',value : 7, user : '预合同到期预警'},
					 {date : '预合同到期预警',value : 4, user : '预合同到期预警'},
					 {date : '池模式融资水位预警',value : 18, user : '池模式融资水位预警'}],
			 type : 'pie', //折线图
			 label : 'date', //月份为X轴
			 value : 'value', //value为数值
			 group : 'user', //以用户为统计角度
			 title : '' //标题
		}
	}) ;
    var webUploader = require('webuploader');
    Tansun.directive('fileUploader', function ($injector,jfGlobal,$rootScope) {
        return {
        	restrict: 'EA',
	        template: 
	        	'<div class="wu-example">' +
			        '<!--用来存放文件信息-->' +
			        '<div class="uploader-list">' +
			        '</div>' +
			    '</div>' ,
	        replace: true,
	        require: 'ngModel',
	        scope: {
	        	uploaderConf: '=',
	        	ngModel : '='
	        },
	        link : function (scope, element, attrs, ngModel) {
	        	if(!scope.uploaderConf){
	        		scope.uploaderConf = {};
	        	}
				var resouces = scope.uploaderConf.userSource.split(".");
				var jfRest = $injector.get('jfRest') ;
	        	var jfModel = $injector.get('jfModel');
				jfRest.request(resouces[0], resouces[1],{}).then(function (data) {
					if(data && data.status == 200) {
						scope.uploaderConf.user = $rootScope.getUser().loginName;
						scope.fileServer = jfGlobal.config["uploader.file.server"] ;
						scope.fileUpload = jfGlobal.config["uploader.file.upload"] ;
						scope.fileDownload = jfGlobal.config["uploader.file.download"] ;

						scope.uploaderConf.fileSize = scope.uploaderConf.fileSize ? scope.uploaderConf.fileSize : 5 ;
						scope.uploaderConf.fileCount = scope.uploaderConf.fileCount ? scope.uploaderConf.fileCount : 5 ;
						scope.uploaderConf.extensions = scope.uploaderConf.extensions ? scope.uploaderConf.extensions : '' ;

						var id,$operator,$compile;
						id = Tansun.GUID("fu");
						$operator = angular.element('<div ng-hide="uploaderConf.readonly"  class="mg_top10"></div>') ;
						//$operator.append(angular.element('<div id="' + id + '">选择文件</div>'));
						$operator.append(angular.element('<div id="' + id + '"><button id="test3" class="layui-btn" type="button"><i class="layui-icon"></i>上传文件</button></div>'));
						$operator.append(angular.element('<div ng-bind="error"></div>'));
						$compile = $injector.get('$compile') ;
						$compile($operator)(scope) ;
						element.append($operator);
						var uploader = webUploader.create({
							//允许上传的文件后缀
							accept :{
								extensions: scope.uploaderConf.extensions
							},
							//选择文件后自动上传
							auto: true,
							//默认可上传5个文件
							fileNumLimit : scope.uploaderConf.fileCount,
							//单个文件最大
							fileSingleSizeLimit : scope.uploaderConf.fileSize * 1024 * 1024,
							// swf文件路径
							swf: jfGlobal.ctx + '/static/js/libs/webuploader/Uploader.swf',
							// 文件接收服务端。
							server: scope.fileServer + scope.fileUpload,
							//每次上传附带参数
							formData : {
								app : scope.uploaderConf.app ,
								module : scope.uploaderConf.module ,
								user : $rootScope.getUser().loginName
							},
							// 选择文件的按钮。可选。
							// 内部根据当前运行是创建，可能是input元素，也可能是flash.
							pick: '#' + id,
							// 不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
							resize: false
						});

						var widget = {
							list : angular.element('<div class="uploader-list"></list>'),
							init : {
								event : function() {
									uploader.on( 'fileQueued', function( file ) {
										var fileItem = angular.element(
											'<div id="' + file.id + '" class="item">' +
											'<h4 style="display:inline-block"><a href="javascript:void(0);" class="info">' + file.name + '</a></h4>' +
											'<div style="display:inline-block;margin-left:5px">' +
											'<label ng-if="!uploaderConf.readonly" class="state">' + file.statusText + '</label>' +
											'<a ng-if="!uploaderConf.readonly" style="margin-left:5px" class="addinput" ng-click="uploaderConf.remove(\'' + file.id + '\')" href="javascript:void(0)">删除</a>' +
											'</div>' +
											'</div>');
										$compile(fileItem)(scope) ;
										widget.list.append(fileItem);
										scope.$apply();
									});
									// 文件上传过程中创建进度条实时显示。
									uploader.on( 'uploadProgress', function( file, percentage ) {
									});
									// 文件上传成功事件
									uploader.on( 'uploadSuccess', function( file , response ) {
										scope.error = "" ;
										scope.$apply();
										if(widget.watch){
											widget.watch();
										}
										var fileJson = [] ;
										if(ngModel.$viewValue){
											fileJson = angular.fromJson(ngModel.$viewValue) ;
										}
										//标记为新增
										response[0].status = 1;
										fileJson.push(response[0]);
										ngModel.$setViewValue(angular.toJson(fileJson));

										file.$index = fileJson.length - 1 ;
										jfModel.set(scope, 'uploaderConf.file', file.source.source); //设置文件到作用域
										jfGlobal.$('#' + file.id).find('.state').text('已上传');
										widget.download(file);
									});
									// 文件上传错误
									uploader.on( 'uploadError', function( file ) {
										jfGlobal.$('#' + file.id).find('.state').text('上传失败');
									});
									// 文件被移除
									uploader.on( 'fileDequeued', function(file) {
										jfGlobal.$('#' + file.id).remove();
										var fileJson = [] ;
										if(ngModel.$viewValue){
											fileJson = angular.fromJson(ngModel.$viewValue) ;
										}
										fileJson.splice(file.$index,1);
										//var fileJson = angular.fromJson(ngModel.$viewValue)  ;
//										fileJson[file.$index].status = -1 ;
										ngModel.$setViewValue(angular.toJson(fileJson));
									});

									uploader.on( 'error', function( result ) {
										if(result == "Q_TYPE_DENIED"){
											scope.error = "请上传" + scope.uploaderConf.extensions + "格式的文件";
										}else if(result == "Q_EXCEED_SIZE_LIMIT" || result == "F_EXCEED_SIZE"){
											scope.error = "请上传" + scope.uploaderConf.fileSize + "Mb以内的文件";
										}else if(result == "Q_EXCEED_NUM_LIMIT"){
											scope.error = "最多只能上传" + scope.uploaderConf.fileCount + "个文件";
										}else if(result == "F_DUPLICATE"){
											scope.error = "上传文件重复";
										}
										scope.$apply();
									});
									// 完成上传完了，成功或者失败，先删除进度条。
									uploader.on( 'uploadComplete', function( file ) {
									});
								},
								//创建文件视图
								view : function() {
									element.prepend(widget.list);
								},
								//重新显示已上传文件
								review : function() {
									var items = angular.fromJson(ngModel.$viewValue) ;
									angular.forEach(items,function(item,index){
										var file = new webUploader.File({
											name:item.originalFilename,
											ext:item.contentType,
											size:item.size
										})  ;
										file.setStatus("complete","已上传");
										file.$index = index ;
										widget.add(file);

										widget.download(file);
									});
								}
							},
							create : function() {
								widget.init.view() ;
								widget.init.event();
							},
							add : function(file) {
								uploader.addFile(file);
							},
							remove : function(id) {
								if(widget.watch){
									widget.watch();
								}
								uploader.removeFile(id,true);
							},
							download : function(file) {
								jfGlobal.$('#' + file.id).find('.info').click(function() {
									var fileJson = angular.fromJson(ngModel.$viewValue)  ;
									var path = angular.fromJson(fileJson[file.$index].savePath).file ;
									window.open(scope.fileDownload + path);
								});
							},
							watch : scope.$watch('ngModel',function(){
								widget.init.review();
							})
						}
						widget.create();

						scope.uploaderConf.remove = widget.remove;
					}
				});
	        }
        }
	});
    
    //验证码
    Tansun.directive('jfCaptcha', function ($compile,jfGlobal) {
	    return {
	        restrict: 'EA',
	        /*template: 
	        		'<span class="prompt" ng-click="change()">' + 
	        			'<img src="{{captcha}}" width="99" height="30" style="vertical-align:middle; ">'+
	        			'看不清楚换图片'+
	        		'</span>',
	        replace: true,*/
	        scope : false ,
	        link: function (scope, element, attrs) {
	        	var $scope = scope.$new(false) ;
        		var type = attrs.jfCaptcha ;
        		var captchaClass =attrs.class;
        		if(!captchaClass){
        			captchaClass="valid-img";
        		}
        		var $img = angular.element('<img class="'+captchaClass+'" src="{{captcha}}" ng-click="change()">');
        		$scope.change = function() {
        			$scope.captcha = ctx + "/captcha/" + type + '?v=' + (new Date()).valueOf() ;
				};
				$scope.change();
				
				var content = $compile($img)($scope);  
				element.replaceWith(content);  
				$scope.$on("refresh",function(){
					$scope.change();
				});
	        }
	    };
	});
  //计时
	Tansun.directive('jfCode', function ($interval,$injector) {
		var ATTR_PARAM = {
			text : 'text' ,//显示文本
			type : 'jfCode' ,//发送类型
			target : 'target',//接收人
			mode : 'mode',//发送方式
			title : 'title',//发送主题
			captcha : 'captcha' //图形验证码
		}
	    return {
	        restrict: 'A',
	        /*template: 
	        		'<button ng-disabled="paraevent" class="btn_public" ng-bind="paracont" ng-click="doSend()"></button>',
	        replace: true,*/
	        scope: false ,
	        link: function (scope, element, attrs) {
	        	var $scope = scope.$new(false) ;
	        	
	        	var jfRest = $injector.get('jfRest');
	        	var jfLayer = $injector.get('jfLayer');
	        	var text = element.html() ;
	        	if(!text){
	        		text = '发送验证码' ;
	        	}
	        	
	        	$scope.send = function(e) {
	        		var preHanlde = scope.$eval(attrs.handle)  ;
	        		if(preHanlde){
	        			preHanlde.success(function() {
	        				element.off('click');
	        				$scope.doSend(e) ;
						});
	        		}else{
	        			element.off('click');
	        			$scope.doSend(e) ;
	        		}
				};
				
				$scope.doSend = function(e) {
	        		$scope.param = {
		        		target : scope.$eval(attrs[ATTR_PARAM.target]),
		        		mode : attrs[ATTR_PARAM.mode],
		        		type : attrs[ATTR_PARAM.type],
		        		title : attrs[ATTR_PARAM.title],
		        		captcha : scope.$eval(attrs[ATTR_PARAM.captcha]) 
		        	} ;
	        		
					var send = jfRest.request('sendCode','get',Tansun.param($scope.param)) ;
					send.then(function(data) {
						if(data.status == 200){
							jfLayer.success('验证码发送成功');
							$scope.timing();
						}else{
							var error = scope.$eval(attrs.error) ;
							scope.$broadcast('refresh') ;
							element.click($scope.send);
							var msg = data.description ? data.description : '获取验证码失败，请重新获取';
							jfLayer.fail(msg) ;
						}
					});
				};
	        	
	        	$scope.timing = function() {
					scope.paraevent = true;
    				var second = 60 ;
	        		var timePromise = $interval(function(){ 
	        			if(second<=0){  
							scope.paraevent = false;
							$interval.cancel(timePromise);  
							element.html(text);
							element.click($scope.send);
	        			}else{  
	        				element.html(second + "s");
		                	second--;  
		                }  
		              },1000,100) ;
				};
				
				element.click($scope.send);
	        	
	        }
	    };
	});
     
		/*Tansun.app.run(function($rootScope,jfRest){
		var request = jfRest.request("param", "query", {"parmTpCd":"fileConfiguration"}) ;
		request.then(function (data) {
			  if(data && data.status == 200){
				  $rootScope.global.uploader = {} ;
				  if(angular.isArray(data.data.grid.list)){
					  angular.forEach(data.data.grid.list,function(item){
						  if(item.parmId=="fileUrl"){
							  $rootScope.global.uploader.server = item.parval ;
						  }
						  if(item.parmId=="fileUpload"){
							  $rootScope.global.uploader.upload = item.parval ;
						  }
						  if(item.parmId=="fileDownload"){
							  $rootScope.global.uploader.download = item.parval ;
						  }
					  }) ; 
				  }
			  }
		});
		
		$rootScope.uploaderInit = function(option) {
			var param = {
				mdlId : option.data.mdlId ,
				tblBlId : option.data.tblBlId ,
				suprFileId : option.data.suprFileId
			};
			
			var downloadUrl = this.global.uploader.server + this.global.uploader.download ;
			jfRest.request('file','query',param).then(function(data) {
				option.files = data.data.grid.list ;
				angular.forEach(data.data.grid.list,function(item){
					item.fullPath = downloadUrl + item.fileRte + '/' + item.fileNm
				}) ;
			}) ;
		};
		
		$rootScope.uploaderDone = function(uploader , response) {
			var thiz = this ; 
			if(angular.isUndefined(response) || response.length < 1)
		        return jfLayer.fail('文件上传失败') ;
		    var fileInfo = response[0] ;
		    var savePath = angular.fromJson(fileInfo.savePath) ;
		
		    var fileParam = {
		        fileNm : fileInfo.originalFilename ,
		        fileSize : fileInfo.size ,
		        fileRte : savePath.file,
		        fileExpdNm : fileInfo.contentType,
		        mdlId : uploader.data.mdlId ,
		        tblBlId : uploader.data.tblBlId ,
		        suprFileId : uploader.data.suprFileId,
		        fileTpCd : uploader.data.fileTpCd
		    } ;
		
		    //如果不需要上传多个附件，且已经存在上传的文件，则对该文件做更新
		    if(!uploader.multiple && uploader.files[0]){
		        fileParam.id = thiz.files[0].id ;
		    }
		
		    jfRest.request('file','save',Tansun.param(fileParam)).then(function(data) {
		        thiz.uploaderInit(uploader) ;
		    });
		};
		
		$rootScope.uploaderError = function() {
			jfLayer.fail('文件上传失败') ;
		};
	 });*/
});
	