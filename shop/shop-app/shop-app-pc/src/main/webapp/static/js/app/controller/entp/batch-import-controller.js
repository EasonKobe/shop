'use strict';
define(function (require) {
    require('angular');
    Tansun.controller('batchImportCtrl', function ($scope, jfRest, jfLayer, $stateParams, $validation, $parse) {

        $scope.file = {};
        $scope.result = null;
        $scope.message = null;

        $scope.batchImport = function () {
            var params = {file: $scope.file.path};

            $scope.file = {};
            $scope.result = null;
            $scope.message = null;
            if (!params.file || params.file == '') {
                jfLayer.fail("请填写文件地址");
                return;
            }

            jfRest.request('batch', 'import', Tansun.param(params)).then(function (data) {
                if (data.status == 200) {
                    jfLayer.success(data.description);
                    $scope.message = data.description;
                    $scope.result = JSON.stringify(data.data.successData);
                } else {
                    jfLayer.fail(data.description);
                }
            });
        }
    })
});