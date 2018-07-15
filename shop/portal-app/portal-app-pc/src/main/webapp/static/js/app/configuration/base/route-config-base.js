'use strict';

define(function(require, exports, module) {
	var config = [
				('/user/pcscfg/list', 'base/pcscfg-list.html', 'pcController.js'),
				('/user/pcscfg/next', 'base/pcscfg-next.html', 'pcController.js'),
				('/user/pcscfg/view', 'base/pcscfg-view.html', 'pcController.js')
 		      		];
	module.exports = config;
});
