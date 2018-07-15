//route-entp-config.js
'use strict';
define(function (require, exports, module) {
    var config = [
        ['/entp', 'entp/entp-index.html', 'entp/entp-index-controller.js'],

        /*['/entp/cmEntpUserUkeyRel', 'entp/cmEntpUserUkeyRel.html', 'entp/entp-ukeybind-controller.js'],*/
        
        ['/entp/cmEntpUserUkeyList', 'entp/cmEntpUserUkeyList.html', 'entp/entp-ukeybind-controller.js'],
        
        ['/entp/cmEntpUserUkeyRel?id', 'entp/cmEntpUserUkeyRel.html', 'entp/entp-ukeybind-controller.js'],
        
        ['/batch', 'entp/batch-import.html', 'entp/batch-import-controller.js'],
    ];
    module.exports = config;
});