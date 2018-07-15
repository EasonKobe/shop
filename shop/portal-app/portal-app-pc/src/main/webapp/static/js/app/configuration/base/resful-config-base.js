'use strict';

define(function (require, exports, module) {

    var resourceConfig = {
		 workflow : {
	            query:    '/user/pcscfg/list',
	            save:     '/user/pcscfg/add',
	            get:      '/user/pcscfg/get',
	            'delete': '/user/pcscfg/del'
	        },
	        
		wfdis :{
			save : '/user/pcscfg/dis'
		},
		
		wfRole:{
			query: '/user/pcscfg/roleList',
			save:  '/user/pcscfg/save'
		}
    };
    module.exports = resourceConfig;
});
