'use strict';

define(function(require, exports, module) {
	var config = [
	 //[路由地址,页面地址,js地址]
 	 //主页
 	 ['/index', 'filtwhal/index.html', 'indexController.js'],
 	 ['/index/main', 'filtwhal/main.html', 'indexController.js'],
 	 ['/index/contactus', 'filtwhal/contactus.html', 'indexController.js'],
 	 ['/index/register', 'filtwhal/register.html', 'indexController.js'],
 	 ['/index/sign', 'filtwhal/sign.html', 'indexController.js'],
 	 
// 	 ['/index?error&login', 'index.html', 'indexController.js'],
// 	 ['/index/aboutus', 'common/index/index-aboutus.html', 'indexController.js'],
// 	 ['/index/contactus', 'common/index/index-contactus.html', 'indexController.js'],
// 	 ['/index/helper', 'common/index/index-helper.html', 'indexController.js'],

];
	module.exports = config;
});
