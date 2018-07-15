'use strict';

define(function(require, exports, module) {
	var config_list = [
		  require('./system/route-config-system'),
	      require('./common/route-config-common'),
	      require('./index/route-config-index'),
	      require('./usercenter/route-config-usercenter'),
	      require('./base/route-config-base')
	                   ];
	require('../router/route-service');
	var angular = require('angular');
	var injector = angular.injector([ "router-service" ]);
	var routeService = injector.get("routeService");
	var config = {
	};
	angular.forEach(config_list, function(data){
		var obj = {};
		angular.forEach(data, function(dataS,indexS,arrayS){
			obj[dataS[0].split("?")[0]] = routeService.initAdminView(dataS[0],dataS[1],dataS[2]);
		});
		config = angular.extend(config,obj);
	});
	module.exports = config;
});
