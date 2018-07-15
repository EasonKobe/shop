package com.jeedev.msdp.sys.tenant.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.bind.DatatypeConverter;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.base.dict.utlis.DictUtil;
import com.jeedev.msdp.base.encode.service.IEncodeService;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.param.api.SysParamApi;
import com.jeedev.msdp.sys.perm.service.IPermService;
import com.jeedev.msdp.sys.role.api.RoleApi;
import com.jeedev.msdp.sys.role.service.ISysRoleService;
import com.jeedev.msdp.sys.tenant.api.TenantApi;
import com.jeedev.msdp.sys.tenant.api.TenantConstants;
import com.jeedev.msdp.sys.tenant.service.ITenantService;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.sys.user.service.IUserRoleRelService;
import com.jeedev.msdp.sys.user.service.IUserService;
import com.jeedev.msdp.trace.constants.LoginUserConstants;
import com.jeedev.msdp.trace.utils.HeadUtil;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
import com.jeedev.msdp.utlis.encrypt.symmetric.AES;

@Service("tenantApi")
public class TenantApiImpl implements TenantApi {
    private String key = "aRpbO38AuXHvJUaorJWm3A==";
    @Autowired
    private ITenantService tenantService;
    /**
     * 用户服务
     */
    @Autowired
    private IUserService userService;

    /**
     * 角色服务
     */
    @Autowired
    private ISysRoleService sysRoleService;

    /**
     * 角色与用户关系 服务
     */
    @Autowired
    private IUserRoleRelService userRoleRelService;


    /**
     * 序号生成器服务
     */
    @Autowired
    private IEncodeService encodeService;

    @Autowired
    private RoleApi roleApi;

    /**
     * 权限服务
     */
    @Autowired
    private IPermService permService;

    @Autowired
    private SysParamApi sysParamApi;

    @Override
    public PageInfo<Map<String, Object>> findTenantPage(
            Map<String, Object> paramMap, PageParam pageParam) {

        return tenantService.findTenantPage(paramMap);
    }

    @Override
    public Map<String, Object> getTenantMap(Map<String, Object> params) {

        if (params.containsKey(TenantConstants.LOGIN_URI_SUFFIX)) {
            String loginUriSuffix = (String) params.get(TenantConstants.LOGIN_URI_SUFFIX);
            Map<String, String> queryCond = new HashMap<String, String>();
            queryCond.put("parmTpCd", "loginUriSuffix2TenantName");
            queryCond.put("parmId", loginUriSuffix);
            PageInfo<Map<String, String>> sysParams = sysParamApi.findParamPage(queryCond, null);
            if (sysParams != null && sysParams.getSize() > 0) {
                params.put("tenantName", sysParams.getList().get(0).get("parval"));
                PageInfo<Map<String, Object>> tenants = tenantService.findTenantPage(params);
                if (tenants != null && tenants.getSize() > 0) {
                    tenants.getList().get(0).put(TenantConstants.LOGIN_URI_SUFFIX, loginUriSuffix);
                    return tenants.getList().get(0);
                } else {
                    return params;
                }
            } else {
                return null;
            }
        }
        Map<String, Object> tenantInfo = tenantService.getTenantMap(params);
        if (tenantInfo == null) {
            return null;
        }
        String userNum = (String) tenantInfo.get("userNum");
        String tenantId = (String) tenantInfo.get("tenantId");
        Map<String, Object> queryUserCond = new HashMap<String, Object>();
        queryUserCond.put("userNum", userNum);
        Map<String, Object> userInfo = userService.getUserMap(queryUserCond);
        if (userInfo != null) {
            tenantInfo.put("managerName", userInfo.get("realname"));
            tenantInfo.put("loginName", userInfo.get("loginName"));
            tenantInfo.put("mobile", userInfo.get("mobile"));
            tenantInfo.put("password", userInfo.get("password"));
            tenantInfo.put("repassword", userInfo.get("password"));
            tenantInfo.put("oldpassword", userInfo.get("password"));
            tenantInfo.put("salt", userInfo.get("salt"));
        }

        AES des = new AES(DatatypeConverter.parseBase64Binary(key));
        String roleCode = "TENA" + des.decryptStr(tenantId);
        tenantInfo.put("roleCode", roleCode);


        //加载参数
        Map<String, String> queryCond = new HashMap<String, String>();
        queryCond.put("parmTpCd", "loginUriSuffix2TenantName");
        queryCond.put("parval", (String) tenantInfo.get("tenantName"));
        PageInfo<Map<String, String>> sysParams = sysParamApi.findParamPage(queryCond, null);
        if (sysParams != null && sysParams.getSize() > 0) {
            tenantInfo.put(TenantConstants.LOGIN_URI_SUFFIX, sysParams.getList().get(0).get("parmId"));
        }

        return tenantInfo;
    }

    public static void main(String[] args) {

        String key = DatatypeConverter.printBase64Binary(new AES().getSecretKey().getEncoded());
//				DatatypeConverter.printBase64Binary(new SymmetricCrypto(SymmetricAlgorithm.DESede).getSecretKey().getEncoded());
        System.out.println(key);
        AES des = new AES(DatatypeConverter.parseBase64Binary(key));
////		
        String tenantId = "TENA171008000007";
        tenantId = tenantId.substring(tenantId.length() - 12);
        System.out.println(tenantId);
        String str = des.encryptHex(tenantId);

//		String str = DatatypeConverter.printBase64Binary(new SymmetricCrypto(SymmetricAlgorithm.DESede).getSecretKey().getEncoded());
        System.out.print(str);
    }

    /**
     * 需更新保存三张表，用户表，角色表，租户表。
     */
    @Override
    public Map<String, Object> saveTenantMap(Map<String, Object> params) {
        // 有主键的情况下去更新，没有的话新增
        Integer id = MapUtils.getInteger(params, UserConstants.ID);
        String loginName = MapUtils.getString(params, TenantConstants.LOGIN_NAME);
        String managerName = MapUtils.getString(params, TenantConstants.MANAGER_NAME);
        String password = MapUtils.getString(params, TenantConstants.PASSWORD);
        String statusCd = MapUtils.getString(params, TenantConstants.STATUS_CD);

        String tenantName = MapUtils.getString(params, TenantConstants.TENANT_NAME);

        Boolean withoutUser = MapUtils.getBoolean(params, "withoutUser");
        if (null == withoutUser) {
            withoutUser = false;
        }

        // 判断必填项是否填写
        if (!withoutUser) {
            if (loginName == null || loginName.isEmpty())
                throw ExceptionFactory.getBizException("sys-usr-00001");
            if (managerName == null || managerName.isEmpty())
                throw ExceptionFactory.getBizException("sys-usr-00002");
        }

        //
        if (id == null) {
            //名称是否已存在
            Map<String, Object> queryTenantParam = new HashMap<String, Object>();
            queryTenantParam.put("tenantName", tenantName);
            PageInfo<Map<String, Object>> existsTenants = tenantService.findTenantPage(queryTenantParam);
            if (existsTenants != null && CollectionUtil.isNotEmpty(existsTenants.getList())) {
                throw ExceptionFactory.getBizException("sys-tenant-00001", tenantName);
            }

            if (!withoutUser && userService.countSysUserByLoginName(loginName) > 0) {//是否重复
                throw ExceptionFactory.getBizException("sys-usr-00003", loginName);
            }
            // 判断必填项是否填写
            if (!withoutUser && (password == null || password.isEmpty()))
                throw ExceptionFactory.getBizException("sys-usr-00004");
            params.put(UserConstants.DEL_IND, DictUtil.INDICATOR_NO());//默认删除状态
            if (statusCd == null || statusCd.isEmpty()) {
                params.put(UserConstants.STATUS_CD, DictUtil.INDICATOR_NO());//默认状态
            }


            saveLoginUriSuffix(params, null);

            String userNum = "";
            String tenantId = "";
            try {
                userNum = encodeService.buildEncode("10012", "0000");
            } catch (Exception e) {
                throw ExceptionFactory.getBizException("sys-usr-00005");
            }
            try {
                tenantId = encodeService.buildEncode("10083", "0000");
                AES des = new AES(DatatypeConverter.parseBase64Binary(key));
                if (tenantId.length() > 12) {
                    tenantId = des.encryptHex(tenantId.substring(tenantId.length() - 12));
                } else {
                    tenantId = des.encryptHex(tenantId);
                }
            } catch (Exception e) {
                throw ExceptionFactory.getBizException("sys-usr-00006");
            }


            params.put(UserConstants.USER_NUM, userNum);
            params.put(UserConstants.REALNAME, managerName);
            params.put(TenantConstants.TENANT_ID, tenantId);
            params.put("createUser", userNum);//
            if (!withoutUser) {
                userService.saveUser(params);
            }
            params.remove("createUser");
            return tenantService.saveTenant(params);

        }
        Map<String, Object> existMap = new HashMap<>();
        existMap.put(TenantConstants.TENANT_ID, params.get(TenantConstants.TENANT_ID));
        Map<String, Object> existTenantInfo = tenantService.getTenantMap(existMap);

        //名次是否已存在
        Map<String, Object> queryTenantParam = new HashMap<String, Object>();
        queryTenantParam.put("tenantName", tenantName);
        PageInfo<Map<String, Object>> existsTenants = tenantService.findTenantPage(queryTenantParam);
        if (existsTenants != null && CollectionUtil.isNotEmpty(existsTenants.getList())) {
            //已存在的租户名称的ID
            Integer existId = MapUtil.getInteger(existsTenants.getList().get(0), "id");
            if (existId.intValue() != id.intValue()) {
                throw ExceptionFactory.getBizException("sys-tenant-00001", tenantName);
            }
        }

        saveLoginUriSuffix(params, existTenantInfo);

        tenantService.updateTenant(params);
        params.put(UserConstants.REALNAME, managerName);
        Map<String, Object> usrerMap = this.getUserMapByUserNum(MapUtils.getString(params, TenantConstants.USER_NUM));
        params.put(UserConstants.ID, MapUtils.getInteger(usrerMap, UserConstants.ID));
        //失效状态同时把用户改为失效
        if (statusCd.equals("2")) {
            params.put(UserConstants.STATUS_CD, DictUtil.INDICATOR_NO());//默认状态
        }
        userService.updateUser(params);


        return null;
    }

    private void saveLoginUriSuffix(Map<String, Object> params, Map<String, Object> existTenantInfo) {
        String loginUriSuffix = MapUtils.getString(params, TenantConstants.LOGIN_URI_SUFFIX);

        String tenantName = MapUtils.getString(params, TenantConstants.TENANT_NAME);
        //1.获得原来租户名称对应的后缀
        Map<String, String> existTenantInfoSuffixInfo = null;
        if (existTenantInfo != null) {
            Map<String, String> queryCond = new HashMap<String, String>();
            queryCond.put("parmTpCd", "loginUriSuffix2TenantName");
            queryCond.put("parval", MapUtil.getString(existTenantInfo, "tenantName"));
            PageInfo<Map<String, String>> sysParams = sysParamApi.findParamPage(queryCond, null);
            if (sysParams != null && sysParams.getSize() > 0) {
                existTenantInfoSuffixInfo = sysParams.getList().get(0);
            }
        }


        //2保存后缀
        //2.1当前后缀为空，则删除原来的配置
        if (StringUtil.isBlank(loginUriSuffix)) {
            if (existTenantInfoSuffixInfo != null) {
                try {
                    sysParamApi.deleteParamTrans(existTenantInfoSuffixInfo);
                } catch (Exception e) {
                    throw ExceptionFactory.getBizException("sys-tenant-00003");
                }
            }
            return;
        }


        //2.2当前后缀不为空,
        //2.2.1查找新的后缀是否存在记录
        Map<String, String> queryCond = new HashMap<String, String>();
        Map<String, String> existSuffixInfo = null;
        queryCond.put("parmTpCd", "loginUriSuffix2TenantName");
        queryCond.put("parmId", loginUriSuffix);
        PageInfo<Map<String, String>> sysParams = sysParamApi.findParamPage(queryCond, null);
        if (sysParams != null && sysParams.getSize() > 0) {
            existSuffixInfo = sysParams.getList().get(0);
        }
        //2.2.2新的后缀不存在记录，则直接保存
        if (existSuffixInfo == null) {
            //后缀未被使用
            existSuffixInfo = queryCond;
            existSuffixInfo.put("parmId", loginUriSuffix);
            existSuffixInfo.put("parval", tenantName);
            try {
                sysParamApi.saveParamTrans(existSuffixInfo);
            } catch (Exception e) {
                throw ExceptionFactory.getBizException("sys-tenant-00003");
            }
            return;
        }

        //2.2.3新的后缀存在记录，则判断记录的名称是否与原租户名称一致 modify time 2017-12-18 yuyq
        //如果原来的租户配置找不到，则表示是更改了名称导致
        if (existSuffixInfo.get("parval").equals(existTenantInfo.get(TenantConstants.TENANT_NAME))) {
            //判断改完后的名称是否唯一
            Map<String, String> map = new HashMap<String, String>();
            map.put("parmTpCd", "loginUriSuffix2TenantName");
            map.put("parval", tenantName);
            Map<String, String> nameParams = sysParamApi.getParamMap(map);
            //存在则判断是否是当前id,不是则为重复
            if (nameParams != null && !nameParams.get("id").equals(existSuffixInfo.get("id"))) {
                //如果存在则提示重复
                throw ExceptionFactory.getBizException("sys-tenant-00002");
            }
            //如果修改后的名称与原来不一致，则修改配置表的名称与租户名称一致
            if (!tenantName.equals(existSuffixInfo.get("parval"))) {
                existSuffixInfo.put("parval", tenantName);
                try {
                    sysParamApi.updateParamTrans(existSuffixInfo);
                } catch (Exception e) {
                    throw ExceptionFactory.getBizException("sys-tenant-00003");
                }
            }
            return;
        }
        // end time 2017-12-18 yuyq
        //如果不一致则提示重复
        throw ExceptionFactory.getBizException("sys-tenant-00002");

    }

    private Map<String, Object> getUserMapByUserNum(String userNum) {
        Map<String, Object> params = new HashMap<String, Object>();
        params.put(UserConstants.USER_NUM, userNum);
        return userService.getUserMap(params);
    }

    @Override
    public void deleteTenant(Map<String, Object> params) {
        String id = MapUtil.getString(params, TenantConstants.ID);
        String tenantId = MapUtil.getString(params, TenantConstants.TENANT_ID);

        if (StringUtil.isBlank(id) && StringUtil.isBlank(tenantId)){
            throw ExceptionFactory.getBizException("sys-00002");
        }

        Map<String, Object> tenantInfo = tenantService.getTenantMap(params);
        if (tenantInfo != null) {
            tenantService.deleteTenant(tenantInfo);

            String userNum = (String) tenantInfo.get(TenantConstants.USER_NUM);
            tenantId = (String) tenantInfo.get(TenantConstants.TENANT_ID);
            //定位超级管理员角色
            //从参数中获取超级管理员角色
            AES des = new AES(DatatypeConverter.parseBase64Binary(key));
            String roleCode = "TENA" + des.decryptStr(tenantId);

            //删除角色关联
            //维护角色与用户的关系ROLE_CODE
            Map<String, Object> queryRoleUserRelCond = new HashMap<String, Object>();
            queryRoleUserRelCond.put("userNum", userNum);
            queryRoleUserRelCond.put("roleCode", roleCode);
            Map<String, Object> existRoleUserRel = userRoleRelService.getUserRoleRelMap(queryRoleUserRelCond);
            if (existRoleUserRel != null) {//存在，则进行删除
                userRoleRelService.deleteUserRoleRel(existRoleUserRel);
            }

            //删除角色
            Map<String, Object> queryCond = new HashMap<String, Object>();
            queryCond.put("roleCode", roleCode);
            Map<String, Object> existRole = sysRoleService.getRoleMap(queryCond);
            if (existRole != null) {//不 存在，则进行删除
                sysRoleService.deleteRole(existRole);
            }

            //删除用户
            Map<String, Object> usrerMap = this.getUserMapByUserNum(userNum);
            if (CollectionUtil.isNotEmpty(usrerMap)) {
                params.put(UserConstants.ID, MapUtils.getInteger(usrerMap, UserConstants.ID));
                userService.deleteUser(params);
            }
        }
    }

    @Override
    public void saveTenantRole(Map<String, Object> params) {

        String tenantId = (String) params.get(TenantConstants.TENANT_ID);
        Map<String, Object> tenantInfo = tenantService.getTenantMap(params);
        String userNum = (String) tenantInfo.get(TenantConstants.USER_NUM);
        //1维护角色
        //1.1超级管理员角色
        AES des = new AES(DatatypeConverter.parseBase64Binary(key));
        String roleCode = "TENA" + des.decryptStr(tenantId);

        //1.2维护超级管理员角色
        Map<String, Object> queryCond = new HashMap<String, Object>();
        queryCond.put("roleCode", roleCode);
        queryCond.put(TenantConstants.TENANT_ID, tenantId);
        Map<String, Object> existRole = sysRoleService.getRoleMap(queryCond);
        if (existRole == null) {//不 存在，进行新增
            Map<String, Object> newRole = new HashMap<String, Object>();
            newRole.put("roleCode", roleCode);
            newRole.put("roleName", tenantInfo.get(TenantConstants.TENANT_NAME) + "管理员");
            newRole.put("statusCd", "1");
            newRole.put("sort", 1);
            newRole.put(TenantConstants.TENANT_ID, tenantId);
            sysRoleService.saveRole(newRole);
        }
        //1.3 维护经办岗RL0001
        queryCond = new HashMap<String, Object>();
        queryCond.put("roleCode", "RL0001");
        queryCond.put(TenantConstants.TENANT_ID, tenantId);
        Map<String, Object> existRole_RL0001 = sysRoleService.getRoleMap(queryCond);
        if (existRole_RL0001 == null) {//不 存在，进行新增
            Map<String, Object> newRole_RL0001 = new HashMap<String, Object>();
            newRole_RL0001.put("roleCode", "RL0001");
            newRole_RL0001.put("roleName", "经办岗");
            newRole_RL0001.put("statusCd", "1");
            newRole_RL0001.put("sort", 1);
            newRole_RL0001.put(TenantConstants.TENANT_ID, tenantId);
            sysRoleService.saveRole(newRole_RL0001);
        }
        //1.4 维护复核岗位RL0002
        queryCond = new HashMap<String, Object>();
        queryCond.put("roleCode", "RL0002");
        queryCond.put(TenantConstants.TENANT_ID, tenantId);
        Map<String, Object> existRole_RL0002 = sysRoleService.getRoleMap(queryCond);
        if (existRole_RL0002 == null) {//不 存在，进行新增
            Map<String, Object> newRole_RL0002 = new HashMap<String, Object>();
            newRole_RL0002.put("roleCode", "RL0002");
            newRole_RL0002.put("roleName", "复核岗");
            newRole_RL0002.put("statusCd", "1");
            newRole_RL0002.put("sort", 1);
            newRole_RL0002.put(TenantConstants.TENANT_ID, tenantId);
            sysRoleService.saveRole(newRole_RL0002);
        }


        //2.维护角色与用户的关系ROLE_CODE
        Map<String, Object> queryRoleUserRelCond = new HashMap<String, Object>();
        queryRoleUserRelCond.put("userNum", userNum);
        queryRoleUserRelCond.put("roleCode", roleCode);
        queryRoleUserRelCond.put(TenantConstants.TENANT_ID, tenantId);
        Map<String, Object> existRoleUserRel = userRoleRelService.getUserRoleRelMap(queryRoleUserRelCond);
        if (existRoleUserRel == null) {//不 存在，进行新增
            Map<String, Object> newRoleUserRel = new HashMap<String, Object>();
            newRoleUserRel.put("userNum", userNum);
            newRoleUserRel.put("defaultInd", DictUtil.INDICATOR_YES());
            newRoleUserRel.put("roleCode", roleCode);
            newRoleUserRel.put("statusCd", "1");
            newRoleUserRel.put("sort", 1);
            newRoleUserRel.put(TenantConstants.TENANT_ID, tenantId);
            userRoleRelService.saveUserRoleRel(newRoleUserRel);
        }


        //3.维护菜单资源
        //3.1删除原有的资源
        //根据菜单编号获取权限编号
        Map<String, Object> menuParams = new HashMap<>();
        menuParams.put("tenantId", tenantId);
        PageInfo<Map<String, Object>> existPermsInfos = permService.findPermPage(menuParams, null);
        for (Map<String, Object> map : existPermsInfos.getList()) {
            permService.deletePerm(map);
        }

        //3.2添加新的资源
        menuParams.clear();
        String menuCodes = (String) params.get("menuCodes");
        String[] menus = menuCodes.split(",");
        List<String> menusList = new ArrayList<String>();
        for (String str : menus) {
            menusList.add(str);
        }
        menuParams.put("resCodes", menusList);//菜单编号
        String curTenantId = (String) (HeadUtil.getCurUser() != null ? HeadUtil.getCurUser().get(LoginUserConstants.LOGIN_USER_TENANT_ID) : "");
        if (StringUtil.isBlank(curTenantId)) {
            menuParams.put("tenantIdIsNull", true);
        }
        menuParams.put("tenantId", curTenantId);
        //根据菜单编号获取权限编号
        PageInfo<Map<String, Object>> newPermsInfos = permService.findPermPage(menuParams, null);
        for (Map<String, Object> newPermsInfo : newPermsInfos.getList()) {
            newPermsInfo.put("id", null);
            newPermsInfo.put("tenantId", tenantId);
            permService.savePerm(newPermsInfo);
        }


        //4.维护角色下的菜单
        Map<String, Object> saveRolePermissionRelParams = new HashMap<String, Object>();
        saveRolePermissionRelParams.put(TenantConstants.TENANT_ID, params.get(TenantConstants.TENANT_ID));
        saveRolePermissionRelParams.put("roleCode", roleCode);
        saveRolePermissionRelParams.put("menuCodes", params.get("menuCodes"));
//		sysRoleRelPermService.saveRolePermissionRel(saveRolePermissionRelParams);
        roleApi.saveRolePermissionRelTrans(saveRolePermissionRelParams);
    }

}
