'use strict';
var resfulConfig = {};
define(function(require) {
	var config_list = [
		  require('./system/resful-config-system'),
		  require('./workflow/resful-config-system')
	                   ];
	var angular = require('angular');
	
	angular.forEach(config_list,function(config){
		angular.extend(resfulConfig,config) ;
	});
});
