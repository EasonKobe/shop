'use strict';

define(function(require, exports, module) {
	
	 var config = [
	 //[路由地址,页面地址,js地址]
	 ['/lvjing/contactUs/contactUsList', 'lvjing/contactUs/contactUsList.html', 'lvjing/contactUs/contactUsController.js'],
	 ['/lvjing/contactUs/contactUsEdit?id', 'lvjing/contactUs/edit/contactUsEdit.html', 'lvjing/contactUs/contactUsController.js']
	 	];
	module.exports = config;
});
