package com.tansun.tb.entp.api.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.standard.exception.ExceptionFactory;
import com.jeedev.msdp.sys.menu.api.MenuApi;
import com.jeedev.msdp.sys.menu.api.MenuConstants;
import com.jeedev.msdp.utlis.CollectionUtil;
import com.jeedev.msdp.utlis.MapUtil;
import com.jeedev.msdp.utlis.StringUtil;
import com.tansun.tb.entp.api.EnterpriseApi;
import com.tansun.tb.entp.api.EnterpriseApiConstants;
import com.tansun.tb.entp.api.EnterpriseMenuApi;
import com.tansun.tb.entp.api.EnterpriseMenuApiConstants;
import com.tansun.tb.entp.service.IEnterpriseMenuService;

@Service
public class EnterpriseMenuApiImpl implements EnterpriseMenuApi {


    @Autowired
    private IEnterpriseMenuService enterpriseMenuService;

    @Autowired
    private EnterpriseApi enterpriseApi;

    @Autowired
    private MenuApi menuApi;


    @Override
    public PageInfo<Map<String, Object>> findEnterpriseMenuPage(Map<String, Object> params, PageParam pageParam) {
        PageInfo<Map<String, Object>> page = enterpriseMenuService.findEnterpriseMenuPage(params);
        return page;
    }


    @Override
    public Map<String, Object> getEnterpriseMenuMap(Map<String, Object> params) {
        Map<String, Object> enterpriseMenuMap = enterpriseMenuService.getEnterpriseMenuMap(params);

        return enterpriseMenuMap;
    }

    @Override
    public Map<String, Object> saveEnterpriseMenuTrans(Map<String, Object> params) {
        String entpCode = MapUtils.getString(params, EnterpriseMenuApiConstants.ENTP_CODE);
        String menuCodes = MapUtils.getString(params, "menuCodes");

        if (StringUtil.isBlank(entpCode)) {
            throw ExceptionFactory.getBizException("entp-mg-00005", "客户编号");
        }

        Map<String, Object> menuParam = new HashMap<>();

        menuParam.put(EnterpriseApiConstants.ENTP_CODE, entpCode);
        menuParam.put(EnterpriseApiConstants.DEL_IND, "0");
        Map<String, Object> enterpriseMap;
        try {
            enterpriseMap = enterpriseApi.getEnterpriseMap(menuParam);
        } catch (NullPointerException ex) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "客户");
        }

        menuParam.clear();
        menuParam.put(EnterpriseMenuApiConstants.ENTP_CODE, entpCode);
        menuParam.put(MenuConstants.DEL_IND, "0");
        PageInfo<Map<String, Object>> entpPage = enterpriseMenuService.findEnterpriseMenuPage(menuParam);
        List<Map<String, Object>> assignedList = entpPage.getList();

        for (Map<String, Object> map : assignedList) {
            enterpriseMenuService.deleteEnterpriseMenu(map);
        }

        List<String> menuCodeList = StringUtil.split(menuCodes, ',');

        Map<String, Object> menuMap = new HashMap<>();
        for (String mcode : menuCodeList) {
            menuMap.clear();
            menuMap.put(EnterpriseMenuApiConstants.ENTP_CODE, entpCode);
            menuMap.put(EnterpriseMenuApiConstants.MENU_CODE, mcode);
            menuMap.put(EnterpriseMenuApiConstants.TENANT_ID, MapUtil.getString(enterpriseMap, EnterpriseMenuApiConstants.TENANT_ID));
            enterpriseMenuService.saveEnterpriseMenu(menuMap);
        }

        return null;
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

    @Override
    public void deleteEnterpriseMenuTrans(Map<String, Object> params) {
        String menuCode = MapUtils.getString(params, EnterpriseMenuApiConstants.MENU_CODE);
        String entpCode = MapUtils.getString(params, EnterpriseMenuApiConstants.ENTP_CODE);


        if (StringUtil.isBlank(menuCode)) {
            throw ExceptionFactory.getBizException("entp-mg-00005", "客户菜单");
        }
        if (StringUtil.isBlank(entpCode)) {
            throw ExceptionFactory.getBizException("entp-mg-00005", "客户编号");
        }

        Map<String, Object> menuParam = new HashMap<>();

        menuParam.put(EnterpriseApiConstants.ENTP_CODE, entpCode);
        menuParam.put(EnterpriseApiConstants.DEL_IND, "0");
        try {
            enterpriseApi.getEnterpriseMap(menuParam);
        } catch (NullPointerException ex) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "客户");
        }

        menuParam.clear();
        menuParam.put(MenuConstants.MENU_CODE, menuCode);
        menuParam.put(MenuConstants.STATUS_CD, "1");
        menuParam.put(MenuConstants.DEL_IND, "0");
        Map<String, Object> menuMap;
        try {
            menuMap = menuApi.getMenuMap(menuParam);
        } catch (NullPointerException ex) {
            throw ExceptionFactory.getBizException("entp-mg-00003", "菜单");
        }

        List<Map<String, Object>> list = new ArrayList();
        list.add(menuMap);

        menuParam.clear();
        menuParam.put(MenuConstants.DEL_IND, "0");
        menuParam.put("tenantId", "10002");
        PageInfo<Map<String, Object>> menuPage = menuApi.findMenuPage(menuParam, null);

        for (Map<String, Object> menu : menuPage.getList()) {
            String parentCode = MapUtil.getString(menu, MenuConstants.PARENT_MENU_CODE);
            List<Map<String, Object>> children = new ArrayList<>();
            List<Map<String, Object>> excludes = new ArrayList<>();
            if (StringUtil.equals(parentCode, menuCode)) {
                children.add(menu);
            } else {
                excludes.add(menu);
            }
        }


        menuParam.clear();
        menuParam.put(EnterpriseMenuApiConstants.ENTP_CODE, entpCode);
        menuParam.put(MenuConstants.DEL_IND, "0");
        PageInfo<Map<String, Object>> entpPage = enterpriseMenuService.findEnterpriseMenuPage(menuParam);
        List<Map<String, Object>> assignedList = entpPage.getList();

        //不存在的不管，已存在的保存
        for (Map<String, Object> menu : list) {
            menuCode = MapUtil.getString(menu, MenuConstants.MENU_CODE);
            Map<String, Object> assignedMenu = getAssignedMenu(assignedList, menuCode);
            if (assignedMenu != null) {
                assignedMenu.put(EnterpriseMenuApiConstants.DEL_IND, "1");
                enterpriseMenuService.updateEnterpriseMenu(assignedMenu);
            }
        }
    }

    private List<Map<String, Object>> chooseChild(List<Map<String, Object>> parentList, List<Map<String, Object>> children) {
        List<Map<String, Object>> list = new ArrayList<>();
        list.addAll(parentList);

        for (Map<String, Object> parent : parentList) {
            String menuCode = MapUtil.getString(parent, MenuConstants.MENU_CODE);
            for (Map<String, Object> child : children) {
                String parentCode = MapUtil.getString(child, MenuConstants.PARENT_MENU_CODE);
                if (StringUtil.equals(parentCode, menuCode)) {

                } else {

                }
            }
        }
        return null;
    }
}
