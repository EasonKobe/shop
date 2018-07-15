'use strict';
var resfulConfig = {};
define(function(require) {
	var config_list = [
		  require('./system/resful-config-system'),
		  require('./entp/resful-entp-config'),
		  require('./lvjing/contactUs/resful-config-contactUs'),
	                   ];
	var angular = require('angular');
	
	angular.forEach(config_list,function(config){
		angular.extend(resfulConfig,config) ;
	});
});
