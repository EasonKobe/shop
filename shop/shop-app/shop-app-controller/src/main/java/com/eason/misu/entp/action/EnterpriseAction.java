package com.eason.misu.entp.action;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageInfo;
import com.github.pagehelper.PageParam;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.HeadInit;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.standard.log.CommonLoggerFactory;
import com.jeedev.msdp.standard.log.Logger;
import com.jeedev.msdp.trace.constants.HeadConstants;
import com.jeedev.msdp.utlis.StringUtil;
import com.tansun.tb.entp.api.EnterpriseApi;
import com.tansun.tb.entp.api.EnterpriseApiConstants;

@Controller
@RequestMapping("/entp")
public class EnterpriseAction extends BaseAppAction {

	private static final Logger logger = CommonLoggerFactory.getLogger(EnterpriseAction.class);


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
     * 是否展开
     */
    public final String OPEN = "open";
    /**
     * 失败
     */
    public final String CHECKED = "checked";


    @Autowired
    private EnterpriseApi enterpriseApi;


    @RequestMapping(value = {"/tree"})
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO treeEnterprise(@RequestParam Map<String, Object> requstObjectMap, PageParam page) {
        //请求参数不建议使用HttpServletRequest request
        try {
            PageInfo<Map<String, Object>> pageInfo = enterpriseApi.findEnterprisePage(requstObjectMap, page);
            List<Map<String, Object>> list = pageInfo.getList();
            List<Map<String, Object>> ahrList = new ArrayList<Map<String, Object>>();
            for (Map entpMap : list) {
                Map<String, Object> map = new HashMap<String, Object>();
                String pId = MapUtils.getString(entpMap, EnterpriseApiConstants.PARENT_ENTP_CODE);
                String id = MapUtils.getString(entpMap, EnterpriseApiConstants.ID);
                map.put(ID, id);
                map.put(NAME, entpMap.get(EnterpriseApiConstants.ENTP_NM));
                if (pId == null) {
                    map.put(PID, "0");
                } else {
                    map.put(PID, pId);
                }
                map.put(OPEN, false);
                map.put(CHECKED, false);
                ahrList.add(map);
            }
            return this.successResponse(ahrList);
        } catch (Exception e) {
            return this.errorResponse(e.getMessage());
        }
    }


    @RequestMapping(value = {"/parentList"})
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO listParentEnterprise(@RequestParam Map<String, Object> requstObjectMap, PageParam page) {
        //请求参数不建议使用HttpServletRequest request
        try {
            return this.successResponse(enterpriseApi.findParentEnterpriseList(requstObjectMap));
        } catch (Exception e) {
            return this.errorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = {"/childList"})
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO listChildEnterprise(@RequestParam Map<String, Object> requstObjectMap, PageParam page) {
        //请求参数不建议使用HttpServletRequest request
        try {
            return this.successResponse(enterpriseApi.findChildEnterpriseList(requstObjectMap));
        } catch (Exception e) {
            return this.errorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = {"/query"})
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO listEnterprise(@RequestParam Map<String, Object> requstObjectMap, PageParam page) {
        //请求参数不建议使用HttpServletRequest request
        try {
            PageInfo<Map<String, Object>> pageInfo = enterpriseApi.findEnterprisePage(requstObjectMap, page);
            return this.successResponse(pageInfo);
        } catch (Exception e) {
            return this.errorResponse(e.getMessage());
        }
    }

    @RequestMapping(value = "/get")
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO get(@RequestObjectParam Map<String, Object> RequstObjectMap) throws Exception {
        Integer id = MapUtils.getInteger(RequstObjectMap, EnterpriseApiConstants.ID);
        if (null != id) {
            try {
                //查询客户信息
                Map<String, Object> map = enterpriseApi.getEnterpriseMap(RequstObjectMap);
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
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO save(@RequestObjectParam Map<String, Object> RequstObjectMap, @CurrentUser Map<String, Object> curUserMap) throws Exception {
        if (!StringUtil.isEmpty(MapUtils.getString(RequstObjectMap, EnterpriseApiConstants.ID))) {//修改
            Map<String, Object> enterpriseMap = enterpriseApi.saveEnterpriseTrans(RequstObjectMap);
            return successResponse(enterpriseMap, "修改成功");
        } else {//保存
            Map<String, Object> enterpriseMap;
            try {
                enterpriseMap = enterpriseApi.saveEnterpriseTrans(RequstObjectMap);
            } catch (Exception e) {
                return this.errorResponse(e.getMessage());
            }
            return successResponse(enterpriseMap, "保存成功");
        }
    }

    @RequestMapping(value = "/delete")
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO delete(@RequestObjectParam Map<String, Object> RequstObjectMap) throws Exception {
        Integer id = MapUtils.getInteger(RequstObjectMap, EnterpriseApiConstants.ID);
        if (null == id) {
            return this.errorResponse("客户id不能为空");
        }
        //调用删除方法
        enterpriseApi.deleteEnterpriseTrans(RequstObjectMap);
        return this.successResponse("删除成功");
    }

    /**
     * @方法名称 importEntp
     * @功能描述 <pre>批量导入用户机构树</pre>
     * @作者 zhaoj
     * @创建时间 2018年04月26日15:34:48
     * @param request
     * @return
     * @throws IOException
     */
   /* @RequestMapping(value = "/importEntp")
    @ResponseBody
    @HeadInit(name = HeadConstants.HEAD_SCENE, value = "tenantManage")
    public ResponseVO importEntp(HttpServletRequest request) throws IOException {
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
        MultipartFile multipartFile = multipartRequest.getFile("importEntp");
        if (multipartFile == null) {
            return this.errorResponse("请先上传文件");
        }

        CommonsMultipartFile cf = (CommonsMultipartFile) multipartFile;
        DiskFileItem fi = (DiskFileItem) cf.getFileItem();
        File file = fi.getStoreLocation();
        String fileName = fi.getName();
        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());


        @SuppressWarnings("rawtypes")
        List<List> list = ExcelReadUtil.readExcel(file, prefix);

        //校验数据
        List<Map> dataList = list.get(0);
        List<Map> typeList = list.get(1);

        if (dataList.size() < 2) {
            return this.errorResponse("没有数据");
        }

        Map<String, String> creditTypeMap = new HashMap();
        Map<String, String> cptplModelMap = new HashMap();
        Map<String, String> wthrCptplHostEntpMap = new HashMap();
        Map<String, String> wthrSignMap = new HashMap();
        Map<String, String> currentMap = null;


        for (Map type : typeList) {
            String typeName = MapUtil.getString(type, "1");
            String typeValue = MapUtil.getString(type, "2");
            switch (typeName) {
                case "证件类型":
                    currentMap = creditTypeMap;
                    continue;
                case "是否资金池主办单位":
                    currentMap = wthrCptplHostEntpMap;
                    continue;
                case "资金池模式":
                    currentMap = cptplModelMap;
                    continue;
                case "是否已签约":
                    currentMap = wthrSignMap;
                    continue;
            }

            if (currentMap == null) {
                return this.errorResponse("没有类型");
            }
            currentMap.put(typeName, typeValue);
        }

        if (MapUtils.isEmpty(creditTypeMap)) {
            return this.errorResponse("没有证件类型");
        }
        if (MapUtils.isEmpty(wthrCptplHostEntpMap)) {
            return this.errorResponse("没有是否资金池主办单位类型");
        }
        if (MapUtils.isEmpty(cptplModelMap)) {
            return this.errorResponse("没有资金池模式类型");
        }
        if (MapUtils.isEmpty(wthrSignMap)) {
            return this.errorResponse("没有是否已签约类型");
        }


        StringBuilder columnMatch = new StringBuilder();
        for (int i = 0; i < dataList.size(); i++) {
            Map dataMap = dataList.get(i);
            if (dataMap.size() != 14) {
                columnMatch.append(i).append(',');
            }
        }

        if (columnMatch.length() > 0) {
            return this.errorResponse("数据列数不匹配，" + columnMatch.substring(0, columnMatch.length() - 1));
        }

        List<Map> paramsList = new ArrayList<>();

        StringBuilder columnCorrect = new StringBuilder();
        for (int i = 1; i < dataList.size(); i++) {
            Map dataMap = dataList.get(i);
            Map paramMap = new HashMap();
            String serialNo = MapUtil.getString(dataMap, "0");
            String entpNm = MapUtil.getString(dataMap, "1");
            String entpCode = MapUtil.getString(dataMap, "2");
            String entpCrdtTpCdName = MapUtil.getString(dataMap, "3");
            String entpCrdtNo = MapUtil.getString(dataMap, "4");
            String entpEmailAdr = MapUtil.getString(dataMap, "5");
            String entpFaxNo = MapUtil.getString(dataMap, "6");
            String entpCtcpsn = MapUtil.getString(dataMap, "7");
            String entpCtcpsnTel = MapUtil.getString(dataMap, "8");
            String entpRgstAdr = MapUtil.getString(dataMap, "9");
            String wthrCptplHostEntpName = MapUtil.getString(dataMap, "10");
            String cptplModelName = MapUtil.getString(dataMap, "11");
            String wthrSignName = MapUtil.getString(dataMap, "12");
            String parentEntpName = MapUtil.getString(dataMap, "13");

            String entpCrdtTpCd = MapUtil.getString(creditTypeMap, entpCrdtTpCdName);
            String wthrCptplHostEntp = MapUtil.getString(wthrCptplHostEntpMap, wthrCptplHostEntpName);
            String cptplModel = MapUtil.getString(cptplModelMap, cptplModelName);
            String wthrSign = MapUtil.getString(wthrSignMap, wthrSignName);

            if (StringUtils.isBlank(serialNo) ||
                    StringUtils.isBlank(entpNm) ||
                    StringUtils.isBlank(entpCode) ||
                    StringUtils.isBlank(entpCrdtTpCdName) ||
                    StringUtils.isBlank(entpCrdtNo) ||
                    StringUtils.isBlank(entpEmailAdr) ||
                    StringUtils.isBlank(entpFaxNo) ||
                    StringUtils.isBlank(entpCtcpsn) ||
                    StringUtils.isBlank(entpCtcpsnTel) ||
                    StringUtils.isBlank(entpRgstAdr) ||
                    StringUtils.isBlank(wthrCptplHostEntpName) ||
                    StringUtils.isBlank(cptplModel) ||
                    StringUtils.isBlank(wthrSignName) ||
                    StringUtils.isBlank(parentEntpName) ||
                    StringUtils.isBlank(entpCrdtTpCd) ||
                    StringUtils.isBlank(wthrCptplHostEntp) ||
                    StringUtils.isBlank(cptplModel) ||
                    StringUtils.isBlank(wthrSign)) {
                columnCorrect.append("第").append(i + 1).append("行存在非法数据，值不能为空");
                continue;
            }
            if ("空白".equals(entpNm) ||
                    "空白".equals(entpCrdtTpCd) ||
                    "空白".equals(entpCrdtNo) ||
                    "空白".equals(entpFaxNo) ||
                    "空白".equals(entpCtcpsn) ||
                    "空白".equals(entpCtcpsnTel) ||
                    "空白".equals(entpRgstAdr) ||
                    "空白".equals(wthrCptplHostEntp) ||
                    "空白".equals(wthrSign)) {
                columnCorrect.append("第").append(i + 1).append("行存在非法数据，不能填写空白");
                continue;
            }

            paramMap.put(EnterpriseApiConstants.ENTP_NM, entpNm);
            if (!"空白".equals(entpCode)) {
                paramMap.put(EnterpriseApiConstants.ENTP_CODE, entpCode);
            }
            paramMap.put(EnterpriseApiConstants.ENTP_CRDT_TP_CD, entpCrdtTpCd);
            paramMap.put(EnterpriseApiConstants.ENTP_CRDT_NO, entpCrdtNo);
            if (!"空白".equals(entpEmailAdr)) {
                paramMap.put(EnterpriseApiConstants.ENTP_EMAIL_ADR, entpEmailAdr);
            }
            paramMap.put(EnterpriseApiConstants.ENTP_FAX_NO, entpFaxNo);
            paramMap.put(EnterpriseApiConstants.ENTP_CTCPSN, entpCtcpsn);
            paramMap.put(EnterpriseApiConstants.ENTP_CTCPSN_TEL, entpCtcpsnTel);
            paramMap.put(EnterpriseApiConstants.ENTP_RGST_ADR, entpRgstAdr);
            paramMap.put(EnterpriseApiConstants.WTHR_CPTPL_HOST_ENTP, wthrCptplHostEntp);

            paramMap.put(EnterpriseApiConstants.CPTPL_MODEL, cptplModel);
            paramMap.put(EnterpriseApiConstants.WTHR_SIGN, wthrSign);

            if (!"空白".equals(parentEntpName)) {
                paramMap.put("parentEntpName", parentEntpName);
            }

            paramsList.add(paramMap);
        }

        if (columnCorrect.length() > 0) {
            return this.errorResponse(columnCorrect.substring(0, columnCorrect.length() - 1));
        }

        //调用api保存用户机构树
        List<Map> resultList = new ArrayList<>();
        String msg = "批量添加用户机构树成功";
        for (Map params : paramsList) {
            String parentEntpName = MapUtil.getString(params, "parentEntpName");

            //调用api插入数据
            //如果指定了上级，就查出上级，并替换params的上级编号为真实的上级编号
            if (StringUtil.isNotBlank(parentEntpName)) {
                HashMap<String, Object> parentParams = new HashMap<>();
                parentParams.put(EnterpriseApiConstants.ENTP_NM, parentEntpName);

                Map<String, Object> parent = enterpriseApi.getEnterpriseMap(parentParams);
                //如果指定的上级找不到，就退出
                if (CollectionUtil.isEmpty(parent)) {
                    msg = "批量保存用户机构树失败，找不到上级机构：" + parentEntpName;
                    logger.info(msg);
                    break;
                }
                params.put(EnterpriseApiConstants.PARENT_ENTP_CODE, parent.get(EnterpriseApiConstants.ID));
            }
            try {
                resultList.add(enterpriseApi.saveEnterpriseTrans(params));
            } catch (Exception ex) {
                msg = "批量保存用户机构树异常:" + ex.getMessage();
                logger.info("批量保存用户机构树异常", ex);
                break;
            }
        }

        //处理结果
        Map<String, Object> resultMap = new HashMap<>();
        resultMap.put("successData", resultList);
        resultMap.put("successSize", resultList.size());
        resultMap.put("failedSize", paramsList.size() - resultList.size());

        msg += "。成功" + resultList.size() + "条，失败" + (paramsList.size() - resultList.size());

        return successResponse(resultMap, msg);
    }
*/
}
