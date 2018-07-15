'use strict';
var resfulConfig = {};
define(function(require) {
	var config_list = [
		  require('./system/resful-config-system'),
		  require('./common/resful-config-common'),
	      require('./index/resful-config-index'),
	      require('./usercenter/resful-config-usercenter'),
	      require('./base/resful-config-base')
	                   ];
	var angular = require('angular');
	
	angular.forEach(config_list,function(config){
		angular.extend(resfulConfig,config) ;
	});
});
