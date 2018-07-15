'use strict';

define(function (require, exports, module) {

    var resourceConfig = {
        // 用户中心
        userCenter : {
            query : '/user/center/queryRepaymentInfo',
            get : '/user/center/getUserInfo',
            getGoldenTicket : '/user/center/getGoldenTicket'
        },
        // 安全中心
        securityCenter : {
            get :'/security/center/getUserInfo',
            save :'/security/center/modifyMobile'
        }
    };
    module.exports = resourceConfig;
});
