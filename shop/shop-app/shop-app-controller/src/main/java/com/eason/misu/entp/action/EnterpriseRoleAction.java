package com.eason.misu.entp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.sys.role.api.RoleConstants;
import com.tansun.tb.entp.api.*;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.HeadInit;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.sys.role.api.RoleApi;
import com.jeedev.msdp.trace.constants.HeadConstants;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;

@Controller
@RequestMapping("/entpRole")
public class EnterpriseRoleAction extends BaseAppAction {

    /**
     * 角色id-树显示
     */
    public final String ID = "id";
    /**
     * 角色名称-树显示
     */
    public final String NAME = "name";
    /**
     * 父角色id-树显示
     */
    public final String PID = "pId";
    /**
     * 父角色名称-树显示
     */
    public final String PARENT_ROLE_NAME = "parentRoleName";
    /**
     * 是否展开
     */
    public final String OPEN = "open";
    /**
     * 失败
     */
    public final String CHECKED = "checked";


    @Autowired
    private EnterpriseMenuApi enterpriseMenuApi;

    @Autowired
    private EnterpriseRoleApi enterpriseRoleApi;

    @Autowired
    private RoleApi roleApi;

    @Autowired
    private MenuApi menuApi;


    @RequestMapping("/query")
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO findRoleList(@RequestObjectParam Map<String, Object> RequstObjectMap) {
        PageInfo<Map<String, Object>> rolePage = enterpriseRoleApi.findEnterpriseRolePage(RequstObjectMap, null);
        List<Map<String, Object>> list = rolePage.getList();
        List<String> roleCodes = new ArrayList<>();
        for (Map<String, Object> roleRef : list) {
            roleCodes.add(MapUtil.getString(roleRef, EnterpriseRoleApiConstants.ROLE_CODE));
        }

        if (CollectionUtil.isEmpty(roleCodes)) {
            return this.successResponse(rolePage);
        }

        Map<String, Object> params = new HashMap<>();
        params.put("roleCodes", roleCodes);
        PageInfo<Map<String, Object>> page = roleApi.findRolePage(params, null);

        mergeList(list, page);

        return this.successResponse(page);
    }

    @RequestMapping(value = {"/queryMenu"})
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO listEnterpriseMenu(@RequestParam Map<String, Object> requstObjectMap, PageParam page) {
        //请求参数不建议使用HttpServletRequest request
        try {
            String entpCode = MapUtil.getString(requstObjectMap, EnterpriseMenuApiConstants.ENTP_CODE);
            String roleCode = MapUtil.getString(requstObjectMap, "roleCode");

            if (StringUtil.isBlank(entpCode) || StringUtil.isBlank(roleCode)) {
                return this.successResponse(new ArrayList());
            }

            //查询企业客户已分配菜单
            List<Map<String, Object>> assignedList = null;
            if (StringUtil.isNotBlank(entpCode)) {
                PageInfo<Map<String, Object>> pageInfo = enterpriseMenuApi.findEnterpriseMenuPage(requstObjectMap, page);
                assignedList = pageInfo.getList();
            }

            //查询企业角色已分配菜单
            List<Map<String, Object>> roleAssignedList = null;
            if (StringUtil.isNotBlank(entpCode)) {
                PageInfo<Map<String, Object>> pageInfo = menuApi.findUserMenuPage(requstObjectMap, page);
                roleAssignedList = pageInfo.getList();
            }

            List<String> menuCodes = new ArrayList<>();
            for (Map<String, Object> map : assignedList) {
                menuCodes.add(MapUtil.getString(map, EnterpriseMenuApiConstants.MENU_CODE));
            }

            //查询企业客户已分配菜单
            Map<String, Object> menuParams = new HashMap<>();
            menuParams.put(MenuConstants.DEL_IND, "0");
            menuParams.put(MenuConstants.MENU_CODES, menuCodes);
            PageInfo<Map<String, Object>> menuPage = menuApi.findMenuPage(menuParams, null);

            List<Map<String, Object>> ahrList = new ArrayList<>();
            for (Map<String, Object> menuMap : menuPage.getList()) {
                String menuCode = MapUtils.getString(menuMap, MenuConstants.MENU_CODE);
                Map<String, Object> map = new HashMap<>();
                map.put(ID, menuCode);
                map.put(NAME, menuMap.get(MenuConstants.MENU_NAME));
                String pId = MapUtils.getString(menuMap, MenuConstants.PARENT_MENU_CODE);
                if (pId == "10001" && pId == "10002") {
                    map.put("pId", "0");
                } else {
                    map.put(PID, pId);
                }
                map.put(OPEN, false);

                //如果是已分配的菜单，标记为选中
                Map<String, Object> assignedMenu = getAssignedMenu(roleAssignedList, menuCode);
                map.put(CHECKED, assignedMenu != null);

                ahrList.add(map);
            }

            return this.successResponse(ahrList);
        } catch (Exception e) {
            return this.errorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO save(@RequestObjectParam Map<String, Object> RequstObjectMap, @CurrentUser Map<String, Object> curUserMap) throws Exception {
        String entpCode = MapUtil.getString(RequstObjectMap, EnterpriseRoleApiConstants.ENTP_CODE);
        String roleCode = MapUtil.getString(RequstObjectMap, EnterpriseRoleApiConstants.ROLE_CODE);

        if(StringUtil.isBlank(entpCode)){
            return this.errorResponse("机构不能为空");
        }
        if(StringUtil.isBlank(roleCode)){
            return this.errorResponse("角色不能为空");
        }

        Map<String, Object> params = new HashMap<>();
        params.put(EnterpriseRoleApiConstants.ENTP_CODE, entpCode);
        params.put(EnterpriseRoleApiConstants.ROLE_CODE, roleCode);

        Map<String, Object> enterpriseRoleMap = enterpriseRoleApi.getEnterpriseRoleMap(params);
        if (CollectionUtil.isNotEmpty(enterpriseRoleMap)){
            return this.errorResponse("角色已绑定");
        }

        Map<String, Object> enterpriseMap = enterpriseRoleApi.saveEnterpriseRoleTrans(RequstObjectMap);
        return successResponse(enterpriseMap, "绑定成功");
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO delete(@RequestObjectParam Map<String, Object> RequstObjectMap) throws Exception {
        Integer id = MapUtils.getInteger(RequstObjectMap, EnterpriseApiConstants.ID);
        if (null == id) {
            return this.errorResponse("角色id不能为空");
        }
        //调用删除方法
        enterpriseRoleApi.deleteEnterpriseRoleTrans(RequstObjectMap);
        return this.successResponse("删除成功");
    }

    private Map<String, Object> getAssignedMenu(List<Map<String, Object>> assignedList, String menuCode) {
        if (CollectionUtil.isNotEmpty(assignedList)) {
            for (Map<String, Object> assignedMap : assignedList) {
                if (StringUtil.equals(menuCode, MapUtils.getString(assignedMap, MenuConstants.MENU_CODE))) {
                    return assignedMap;
                }
            }
        }
        return null;
    }

    /**
     * @方法名称 selector
     * @功能描述 <pre>提供给页面展示的下拉角色数据(默认不带查询条件)</pre>
     * @作者 yuyq
     * @创建时间 2018年1月27日 下午11:25:55
     * @param RequstObjectMap
     * @param page
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/selector")
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO selector(@RequestObjectParam Map<String, Object> RequstObjectMap, PageParam page) throws Exception {
        String entpCode = MapUtil.getString(RequstObjectMap, EnterpriseRoleApiConstants.ENTP_CODE);
        String clntendId = MapUtil.getString(RequstObjectMap, "clntendId");
        Map<String, Object> result = new HashMap<String, Object>();

        if (StringUtil.isBlank(entpCode) && StringUtil.isBlank(clntendId)){
            return this.errorResponse("clntendId和entpCode不能同时为空");
        }

        PageInfo<Map<String, Object>> pageInfo;

        if (StringUtil.isNotBlank(clntendId)){
            RequstObjectMap.remove(EnterpriseRoleApiConstants.ENTP_CODE);
            pageInfo = roleApi.findRolePage(RequstObjectMap, null);
            result.put("grid", pageInfo);
            return this.successResponse(result);
        }else {
            PageInfo<Map<String, Object>> rolePage = enterpriseRoleApi.findEnterpriseRolePage(RequstObjectMap, null);
            List<Map<String, Object>> list = rolePage.getList();
            if (CollectionUtil.isEmpty(list)) {
                result.put("grid", rolePage);
                return this.successResponse(result);
            }

            List<String> roleCodes = new ArrayList<>();
            for (Map<String, Object> roleRef : list) {
                roleCodes.add(MapUtil.getString(roleRef, EnterpriseRoleApiConstants.ROLE_CODE));
            }

            Map<String, Object> params = new HashMap<>();
            params.put("roleCodes", roleCodes);
            PageInfo<Map<String, Object>> rolePageInfo = roleApi.findRolePage(params, null);

            mergeList(list, rolePageInfo);
            result.put("grid", rolePageInfo);
            return this.successResponse(result);
        }
    }

    private void mergeList(List<Map<String, Object>> list, PageInfo<Map<String, Object>> pageInfo) {
        for (Map<String, Object> role : list) {
            for (Map<String, Object> roleRef : pageInfo.getList()) {
                String roleCode = MapUtil.getString(role, "roleCode");
                String roleCode1 = MapUtil.getString(roleRef, "roleCode");
                if (StringUtil.equals(roleCode, roleCode1)) {
                    roleRef.putAll(role);
                }
            }
        }
    }
}
