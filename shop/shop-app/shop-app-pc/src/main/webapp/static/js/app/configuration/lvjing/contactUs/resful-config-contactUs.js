'use strict';
define(function(require, exports, module) {
    var config = {
    	contactUsList : {
			query : '/lvjing/contactUs/pagingLjContactUsList',
			'delete' : '/lvjing/contactUs/doDeleteContactUs'
		},
		contactUs : {
			get : '/lvjing/contactUs/initLjContactUs',
			save : '/lvjing/contactUs/doSaveContactUs'
		}
    }
    module.exports = config;
});
