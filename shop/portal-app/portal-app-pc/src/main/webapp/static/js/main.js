'use strict';
require.config({
    baseUrl: ctx+"/static/js/",
//    urlArgs: 'v=' + (new Date()).getTime() + Math.random() * 10000,
    paths:{
        //模块
    	'jquery':'./libs/jquery1.10.2',
    	'jquery1.3':'./app/controller/jquery-3.3.1.min',
    	'jquery.SuperSlide':'./libs/angular/ngSlide/jquery.SuperSlide',
        'angular': './libs/angular/angular.min',
        'angular-resource': './libs/angular/angular-resource.min',
        'angular-ui-route': './libs/angular/angular-ui-router.min',
        'angular-loader' : './libs/angular/angular-loader.min',
        'angular-sanitize' : './libs/angular/angular-sanitize.min',
        'angular-progress': './libs/angular/ngprogress.min',
        'angular-async-loader': './libs/angular/angular-async-loader.min',
        'angular-slider':'./libs/angular/ngSlide/ngslider',
        'angular-validator':'./libs/angular/angular-validation',
        'angular-validator-rules':'./libs/angular/angular-validation-rule',
        'angular-moment':'./libs/angular/angular-moment.min',
        'webuploader':'./libs/webuploader/webuploader',
        /*'jfast-directive':'./app/directive/jf-directive',
        'jfast-filter':'./app/filter/jf-filter',
        'jfast-service' : './app/service/jf-service',*/
        'app':'./app',
        'angular-file-uploader':'./libs/angular/angular-file-upload.min',

        'app-routes':'./app/router/app-routes',
		'layui':'./libs/layui/layui',
		'es5-shim':'./libs/es5/es5-shim.min',
		'es5-sham':'./libs/es5/es5-sham.min',
		'jf-es5-ext' : './libs/es5/jf-es5-ext',
		'base64':'./libs/encrypt/base64',
		'xxtea':'./libs/encrypt/XXTEA',
		'des':'./libs/encrypt/des',
		'ct-ui-router-extras':'./libs/angular/ct-ui-router-extras',
        'moment':'./libs/moment/moment.min',
		'jquery-ztree':'./libs/ztree/jquery.ztree.all-3.5.min',
		'echarts' : './libs/echarts.min',
		'tansun' : './tansun',
		'tansun-dependent' : './app/tansun-dependent',
		'tansun-resful' : './app/dist/tansun-resful',
		'tansun-layer' : './app/dist/tansun-layer',
		'tansun-charts' : './app/dist/tansun-charts',
		'tansun-system' : './app/dist/tansun-system',
		'tansun-form' : './app/dist/tansun-form',
		'tansun-other' : './app/dist/tansun-other',
		'tansun-shim' : './app/dist/tansun-shim',
		'tansun-table' : './app/dist/tansun-table',
		'tansun-translate' : './app/dist/tansun-translate'
    },
    //引入不符合AMD规范的模块
    shim:{
    	'es5-sham' : {deps:['es5-shim']},
    	'angular': {exports: 'angular',deps:['jquery','es5-shim','es5-sham','jf-es5-ext']},
    	'angular-resource':['angular'],
        'angular-route': ['angular'],
        'angular-ui-route':['angular'],
        'angular-loader': ['angular'],
        'angular-sanitize': ['angular'],
        'angular-progress': ['angular'],
        'angular-validator': ['angular'],
        'angular-validator-rules': ['angular'],
        'app-routes' : ['angular-ui-route'],
        'app' : ['angular'],
        'webuploader' : ['jquery'],
		'layui':{exports: 'layui',deps: ['jquery']},
        'jquery-ztree' : {exports: 'jquery-ztree',deps: ['jquery']},
        'echarts':['jquery'] 
    },
    waitSeconds: 0
});

require(['es5-shim','es5-sham','jf-es5-ext']);
require(['angular', 'jquery','tansun','angular-ui-route','ct-ui-router-extras','app'], function (angular) {
    
});
