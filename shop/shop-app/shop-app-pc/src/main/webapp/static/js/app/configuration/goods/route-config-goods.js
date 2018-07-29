'use strict';

define(function(require, exports, module) {
	
	 var config = [
	 //[路由地址,页面地址,js地址]
	 ['/public/goods/goodsList', 'public/goods/goodsList.html', 'public/goods/goodsController.js'],
	 ['/public/goods/goodsEdit?id', 'public/goods/edit/goodsEdit.html', 'public/goods/goodsController.js']
	 	];
	module.exports = config;
});
