'use strict';
define(function (require) {
    var angular = require('angular');
    Tansun.controller('entpIndexCtrl', function ($scope, jfRest, jfLayer, $stateParams, $validation, $parse) {

        $scope.clntendId = '10001';
        //新版tree配置示例
        $scope.entpTree = {
            isCheck: false,
            params: {},
            resource: 'entp.tree',
            nodeClick: function (treeNode) {
                $scope.refreshEnterprise(treeNode.id, treeNode);
            }
        };

        $scope.refreshThis = function () {
            $scope.backEntp = null;
            $scope.enterprise = {};
            $scope.role2 = {showDiv: 1};
            $scope.role = {};
            $scope.user = {};

            $scope.entpTree.params = {};
            $scope.entpTree.reload();

            $scope.roleGrid.params.entpCode = Tansun.GUID();
            $scope.roleGrid.search();

            $scope.roleUserList.params.tenantId = Tansun.GUID();
            $scope.roleUserList.search();

            $scope.entpUserList.params.tenantId = Tansun.GUID();
            $scope.entpUserList.search();
        };

        //机构树点击事件
        //1.展开树
        //2.刷新机构信息
        //3.刷新账户信息
        //5.刷新已分配菜单树
        $scope.refreshEnterprise = function (id, treeNode) {
            jfRest.request('entp', 'get', Tansun.param({'id': id})).then(function (data) {
                if (data.status == 200) {
                    //2.刷新机构信息
                    $scope.enterprise = data.data;
                    $scope.enterprise.treeNode = treeNode;
                    if (treeNode) {
                        $scope.treeNode = treeNode;
                    }

                    //3.刷新账户信息
                    //TODO:账户信息

                    //4.刷新高管信息
                    // $scope.enterpriseExecGrid.reload();

                    //5刷新已分配菜单树
                    // $scope.queryMenu.entpCode = data.data.entpCode;
                    // $scope.menuTree.reload();


                    //触发用户查询
                    $scope.entpUserList.params.tenantId = data.data.tenantId;
                    $scope.entpUserList.search();

                    $scope.userRoleList.params.entpCode = data.data.entpCode || Tansun.GUID();

                    //清除选中角色，刷新角色树
                    $scope.roleGrid.params.entpCode = data.data.entpCode || Tansun.GUID();
                    $scope.roleGrid.search();
                    //同事刷新角色用户列表
                    $scope.roleUserList.params.tenantId = data.data.tenantId;
                    $scope.role = {};
                    $scope.role2 = {showDiv: 1};
                    $scope.roleUserList.search();
                }
            });
        };

        $scope.addEnterprise = function () {
            $scope.backEntp = $scope.enterprise;
            $scope.enterprise = {entpCrdtTpCd: 1, wthrCptplHostEntp: 0, wthrSign: 0};
        };

        $scope.addEnterpriseNode = function () {
            if (!$scope.enterprise || !$scope.enterprise.id) {
                jfLayer.fail("请选择一个机构！");
                return;
            }

            $scope.backEntp = $scope.enterprise;
            $scope.enterprise = {parentEntpCode: $scope.backEntp.id, entpCrdtTpCd: 1};
        };

        $scope.save = function () {
            $validation.validate($parse('entpForm')($scope)).success(function () {
                var entp = $scope.enterprise;
                entp.clntendId = $scope.clntendId;
                jfRest.request('entp', 'save', Tansun.param(entp)).then(function (data) {
                    if (data.status == 200) {
                        jfLayer.success(data.description, function () {
                            $scope.enterprise = $scope.backEntp = null;
                            $scope.refreshThis();
                        });
                    } else {
                        jfLayer.fail(data.description);
                    }
                });
            });
        };
        $scope.back = function () {
            $scope.enterprise = $scope.backEntp;
            $scope.backEntp = null;
        };

        $scope.delEnterprise = function () {
            if (!$scope.enterprise || !$scope.enterprise.id) {
                jfLayer.fail("请选择一个机构！");
                return;
            }

            jfLayer.confirm('将会删除机构信息，确认删除？', function () {
                jfRest.request('entp', 'delete', {id: $scope.enterprise.id}).then(function (data) {
                    if (data.status == 200) {
                        jfLayer.success(data.description, function () {
                            $scope.refreshThis();
                        });
                    } else {
                        jfLayer.fail(data.description);
                    }
                });
            }, function () {
                jfLayer.closeAll();
            })
        };


        $scope.wthrArray = [{name: '是', id: '1'}, {name: '否', id: '0'}];

        $scope.roleGrid = {
            checkType: 'radio', //当为checkbox时为多选
            params: {entpCode: Tansun.GUID()}, //表格查询时的参数信息
            paging: true,//默认true,是否分页
            resource: 'entp.queryRole',//列表的资源
            checkBack: function (item) {//行选中回调事件
                $scope.role = item;
                $scope.roleUserList.params.roleCode = item.roleCode;
                $scope.roleUserList.search();
            }
        };

        $scope.bindEntpRole = function () {
            $scope.roleDialogParam = {clntendId: $scope.clntendId};
            $scope.roleGrid.modal('/entp/user/userRoleAddDialog.html', {
                title: '所属角色信息',
                buttons: ['提交', '取消'],
                size: ['700px', '400px'],
                callbacks: [$scope.saveEntpRole]
            });
        };

        $scope.saveEntpRole = function (result) {
            if (!$scope.enterprise || !$scope.enterprise.id) {
                jfLayer.fail("请选择一个机构！");
                return;
            }

            if (!result.scope.userAndRoleRel.tenantId) {
                result.scope.userAndRoleRel.tenantId = $scope.enterprise.tenantId;
            }

            result.scope.userAndRoleRel.entpCode = $scope.enterprise.entpCode || Tansun.GUID();

            //手动进行校验
            jfRest.request('entp', 'saveRole', Tansun.param(result.scope.userAndRoleRel)).then(function (data) {
                if (data.status == 200) {
                    jfLayer.success(data.description, function () {
                        $scope.roleGrid.search();
                        result.cancel();
                    });
                } else {
                    jfLayer.fail(data.description);
                }
            }, function (data) {
                jfLayer.fail(data.data.description);
            });
        };

        $scope.delRole = function () {
            if (!$scope.enterprise || !$scope.enterprise.id) {
                jfLayer.fail("请选择一个机构！");
                return;
            }
            $scope.role = $scope.roleGrid.checkedList();
            if (!$scope.role) {
                jfLayer.fail("请选择有效数据！");
                return;
            }

            jfLayer.confirm('将会解绑角色信息，确认删除？', function () {
                jfRest.request('entp', 'deleteRole', Tansun.param($scope.role)).then(function (data) {
                    if (data.status == 200) {
                        var msg = data.description ? data.description : '解绑成功';
                        jfLayer.success(msg, function () {
                            $scope.roleGrid.search();
                        });
                    } else {
                        var msg = data.description ? data.description : '解绑失败';
                        jfLayer.fail(msg);
                    }
                });
            }, function () {
                jfLayer.closeAll();
            })
        };

        $scope.goBackRoleGrid = function () {
            $scope.role2 = {showDiv: 1};
        };

        //用户列表
        $scope.roleUserList = {
            params: {roleCode: Tansun.GUID(), tenantId: Tansun.GUID()}, //表格查询时的参数信息
            paging: true,//默认true,是否分页
            resource: 'entpUser.queryByRole',//列表的资源
            callback: function () { //表格查询后的回调函数
            }
        };

        //用户列表
        $scope.entpUserList = {
            checkType: 'radio', //当为checkbox时为多选
            params: {}, //表格查询时的参数信息
            paging: true,//默认true,是否分页
            resource: 'entpUser.query',//列表的资源
            autoQuery: false,
            callback: function () { //表格查询后的回调函数
            }
        };
        $scope.addOrUpdateUser = function (type) {
            $scope.userRoleList.params.tenantId = $scope.enterprise.tenantId;
            if (type == "update") {
                //校验是否选中
                if (!$scope.entpUserList.validCheck()) {
                    return;
                }
                $scope.user = $scope.entpUserList.checkedList();
                //设置角色查询条件
                $scope.userRoleList.params.userNum = $scope.user.userNum;
                $scope.userRoleList.search();
            } else {
                $scope.user = {};
                $scope.userRoleList.params.userNum = Tansun.GUID();
                $scope.userRoleList.search();
            }
            $("#ctsUserAddCtrl").show();
            $("#ctsUserListCtrl").hide();

        };

        $scope.deleteUser = function () {
            if (!$scope.enterprise || !$scope.enterprise.id) {
                jfLayer.fail("请选择一个机构！");
                return;
            }
            var user = $scope.entpUserList.checkedList();
            if (!user) {
                jfLayer.fail("请选择一个用户！");
                return;
            }

            jfLayer.confirm('将会删除用户，确认删除？', function () {
                jfRest.request('sysUser1', 'delete', {id: user.id}).then(function (data) {
                    if (data.status == 200) {
                        var msg = data.description ? data.description : '删除成功';
                        jfLayer.success(msg, function () {
                            $scope.roleUserList.search();
                        });
                    } else {
                        var msg = data.description ? data.description : '删除失败';
                        jfLayer.fail(msg);
                    }
                });
            }, function () {
                jfLayer.closeAll();
            })
        }

        $scope.resetPassword = function () {
            if (!$scope.entpUserList.validCheck()) {
                jfLayer.fail("请选择一个用户");
                return;
            }
            var user1 = $scope.entpUserList.checkedList();
            jfLayer.confirm("确定要重置“" + user1.loginName + "”的密码吗（重置完为111111）?", function () {
                    jfRest.request('entpUser', 'resetPassword', Tansun.param({'id': user1.id})).then(function (data) {
                        if (data.status == 200) {
                            jfLayer.success(data.description, function () {
                                $scope.entpUserList.search();
                            });
                        } else {
                            jfLayer.fail(data.description);
                        }
                    });
                }, function () {
                }
            );
        };

        $scope.user = {};
        $scope.getUser = function () {
            if ($scope.user.userNum || $scope.user.id) {
                jfRest.request('entpUser', 'get', Tansun.param($scope.user)).then(function (data) {
                    if (data.status == 200) {
                        $scope.user = data.data;
                    }
                });
            }
        }
        $scope.getUser();
        //返回用户分配列表
        $scope.userBack = function () {
            $("#ctsUserAddCtrl").hide();
            $("#ctsUserListCtrl").show();
            $scope.entpUserList.search();
        }
        $scope.userSave = function (buttonType) {
            $validation.validate($parse('userForm')($scope)).success(function () {//校验成功后, 进行保存操作
                $scope.user.buttonType = buttonType;
                if (!$scope.user.tenantId) {
                    $scope.user.tenantId = $scope.enterprise.tenantId;
                }
                $scope.user.clntendId = $scope.clntendId;
                jfRest.request('entpUser', 'save', Tansun.param($scope.user)).then(function (data) {
                    if (data.status == 200) {
                        jfLayer.success(data.description, function () {
                            //设置角色查询条件
                            $scope.userRoleList.params.userNum = $scope.user.userNum;
                            $scope.user = data.data;
                        });
                    } else {
                        jfLayer.fail(data.description);
                    }
                });
            });
        }


        //用户角色信息
        $scope.userRoleList = {
            checkType: 'radio', //当为checkbox时为多选
            params: {}, //表格查询时的参数信息
            paging: true,//默认true,是否分页
            resource: 'entpUserAndRole.query',//列表的资源
            autoQuery: false,
            callback: function () { //表格查询后的回调函数
            }
        };

        $scope.bindUserRole = function () {
            $scope.roleDialogParam = {entpCode: $scope.enterprise.entpCode || Tansun.GUID()};
            $scope.userRoleList.modal('/entp/user/userRoleAddDialog.html', {
                title: '所属角色信息',
                buttons: ['提交', '取消'],
                size: ['700px', '400px'],
                callbacks: [$scope.saveRole]
            })
        };
        $scope.saveRole = function (result) {
            if (!result.scope.userAndRoleRel.tenantId) {
                result.scope.userAndRoleRel.tenantId = $scope.enterprise.tenantId;
            }
            // result.scope.userAndRoleRel.clntendId = $scope.clntendId;
            //手动进行校验
            //$validation.validate($parse('userAndRoleRelForm')($scope)).success(function(){//校验成功后, 进行保存操作
            jfRest.request('entpUserAndRole', 'save', Tansun.param(result.scope.userAndRoleRel)).then(function (data) {
                if (data.status == 200) {
                    jfLayer.success(data.description, function () {
                        $scope.userRoleList.params.userNum = $scope.user.userNum;
                        $scope.userRoleList.search();
                        $scope.roleUserList.search();
                        result.cancel();
                    });
                } else {
                    jfLayer.fail(data.description);
                }
            }, function (data) {
                jfLayer.fail(data.data.description);
            });
            //});
        };

        $scope.deleteUserRole = function () {

            if (!$scope.enterprise || !$scope.enterprise.id) {
                jfLayer.fail("请选择一个机构！");
                return;
            }
            var userRole = $scope.userRoleList.checkedList();
            if (!userRole) {
                jfLayer.fail("请选择一个角色！");
                return;
            }

            jfLayer.confirm('将会解除该角色绑定，确认删除？', function () {
                jfRest.request('userAndRoleRel', 'delete', {id: userRole.id}).then(function (data) {
                    if (data.status == 200) {
                        var msg = data.description ? data.description : '解除成功';
                        jfLayer.success(msg, function () {
                            $scope.userRoleList.search();
                            $scope.roleUserList.search();
                        });
                    } else {
                        var msg = data.description ? data.description : '解除失败';
                        jfLayer.fail(msg);
                    }
                });
            }, function () {
                jfLayer.closeAll();
            })
        }

    });
    //用户角色维护
    Tansun.controller('entpUserAndRoleRelCtrl', function ($scope, $stateParams, jfRest, $http, jfGlobal, $rootScope, jfLayer, $location, $parse, $validation) {

        $scope.get = function () {

            if (!$scope._modal.param.id) {
                $scope.userAndRoleRel = {
                    userNum: $scope.user.userNum
                }
            } else {
                $scope.userAndRoleRel = {
                    id: $scope._modal.param.id
                }
                jfRest.request('entpUserAndRole', 'get', Tansun.param($scope._modal.param)).then(function (data) {
                    $scope.userAndRoleRel = data.data;
                });
            }
        }

        $scope.get();

        $scope.selectRole = {
            header: ['角色名称', '角色编号'],
            body: ['roleName', 'roleCode'],
            text: 'role',
            api: 'entp',
            method: 'roleSelector',
            pageShow: true,
            params: $scope.roleDialogParam,
            callback: function (item) {
                $scope.userAndRoleRel.roleCode = item.roleCode;
                $scope.userAndRoleRel.roleName = item.roleName;
            }
        };
    })
})