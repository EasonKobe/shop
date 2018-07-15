package com.eason.misu.entp.action;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.HeadInit;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.trace.constants.HeadConstants;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
import com.tansun.tb.entp.api.EnterpriseApi;
import com.tansun.tb.entp.api.EnterpriseMenuApi;
import com.tansun.tb.entp.api.EnterpriseMenuApiConstants;
import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/entpMenu")
public class EnterpriseMenuAction extends BaseAppAction {

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
    private EnterpriseApi enterpriseApi;

    @Autowired
    private EnterpriseMenuApi enterpriseMenuApi;

    @Autowired
    private MenuApi menuApi;


    @RequestMapping(value = {"/query"})
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO listEnterpriseMenu(@RequestParam Map<String, Object> requstObjectMap, PageParam page) {
        //请求参数不建议使用HttpServletRequest request
        try {
            //查询已分配菜单
            String entpCode = MapUtil.getString(requstObjectMap, EnterpriseMenuApiConstants.ENTP_CODE);
            List<Map<String, Object>> assignedList = null;
            if (StringUtil.isNotBlank(entpCode)) {
                PageInfo<Map<String, Object>> pageInfo = enterpriseMenuApi.findEnterpriseMenuPage(requstObjectMap, page);
                assignedList = pageInfo.getList();
            }

            //查询前端菜单
            Map<String, Object> menuParams = new HashMap<>();
            menuParams.put(MenuConstants.DEL_IND, "0");
            menuParams.put("clntendId", "10001");
            PageInfo<Map<String, Object>> menuPage = menuApi.findMenuPage(menuParams, null);

            List<Map<String, Object>> ahrList = new ArrayList<Map<String, Object>>();
            for (Map<String, Object> menuMap : menuPage.getList()) {
                Map<String, Object> map = new HashMap<>();
                String menuCode = MapUtils.getString(menuMap, MenuConstants.MENU_CODE);
                map.put(ID, menuCode);
                map.put(NAME, menuMap.get(MenuConstants.MENU_NAME));
                String pId = MapUtils.getString(menuMap, MenuConstants.PARENT_MENU_CODE);
                if (pId == "10001" && pId == "10002")
                    map.put("pId", "0");
                else
                    map.put(PID, pId);
                map.put(OPEN, false);

                //如果是已分配的菜单，标记为选中
                Map<String, Object> assignedMenu = getAssignedMenu(assignedList, menuCode);
                map.put(CHECKED, assignedMenu != null);

                ahrList.add(map);
            }

            return this.successResponse(ahrList);
        } catch (Exception e) {
            return this.errorResponse(e.getMessage());
        }
    }

    private Map<String, Object> getAssignedMenu(List<Map<String, Object>> assignedList, String menuCode) {
        Map<String, Object> assignedMenu;
        if (CollectionUtil.isNotEmpty(assignedList)) {
            for (Map<String, Object> assignedMap : assignedList) {
                if (StringUtil.equals(menuCode, MapUtils.getString(assignedMap, MenuConstants.MENU_CODE))) {
                    return assignedMap;
                }
            }
        }
        return null;
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO get(@RequestObjectParam Map<String, Object> RequstObjectMap) throws Exception {
        Integer id = MapUtils.getInteger(RequstObjectMap, EnterpriseMenuApiConstants.ID);
        if (null != id) {
            try {
                //查询客户信息
                Map<String, Object> map = enterpriseMenuApi.getEnterpriseMenuMap(RequstObjectMap);
                return successResponse(map);
            } catch (Exception e) {
                return this.errorResponse(e.getMessage());
            }
        } else {
            return successResponse("查询条件不存在");
        }
    }

    @RequestMapping(value = "/save")
    @ResponseBody
    public ResponseVO save(@RequestObjectParam Map<String, Object> RequstObjectMap, @CurrentUser Map<String, Object> curUserMap) throws Exception {
            enterpriseMenuApi.saveEnterpriseMenuTrans(RequstObjectMap);
            return successResponse("保存成功");
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    public ResponseVO delete(@RequestObjectParam Map<String, Object> RequstObjectMap) throws Exception {
        //调用删除方法
        enterpriseMenuApi.deleteEnterpriseMenuTrans(RequstObjectMap);
        return this.successResponse("删除成功");
    }

}
