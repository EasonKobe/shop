'use strict';
define(function(require, exports, module) {
    var config = {
    	goodsList : {
			query : '/public/goods/findGoodsList',
			'delete' : '/public/goods/deleleGoods'
		},
		goods : {
			get : '/public/goods/getGoods',
			save : '/public/goods/saveOrUpdateGoods'
		}
    }
    module.exports = config;
});
