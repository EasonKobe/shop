package com.tansun.tb.jnTra.app.action;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.common.logger.Logger;
import com.alibaba.dubbo.common.logger.LoggerFactory;
import com.jeedev.msdp.common.ResponseVO;
import com.jeedev.msdp.core.annotation.CurrentUser;
import com.jeedev.msdp.core.annotation.RequestObjectParam;
import com.jeedev.msdp.core.web.action.BaseAppAction;
import com.jeedev.msdp.sys.tenant.api.TenantApi;
import com.jeedev.msdp.sys.user.api.UserApi;
import com.jeedev.msdp.sys.user.api.UserConstants;
import com.jeedev.msdp.utlis.DateUtil;

/**
 * 用户中心
 * @类名称 UserCenterAction
 * @类描述 <pre>用户中心</pre>
 * @作者 chenld
 * @创建时间 2017/8/30 11:31
 * @版本 3.00
 * @修改记录 <pre>
 *     版本                       修改人 		修改日期 		 修改内容描述
 *     ----------------------------------------------
 *     3.00 	chenld 	 2017/8/30
 *     ----------------------------------------------
 * </pre>
 */
@Controller
@RequestMapping(value = "/")
public class UserCenterAction extends BaseAppAction {
    
    private Logger logger = LoggerFactory.getLogger(UserCenterAction.class);
    
//    /**
//     * 注入额度api
//     */
//    @Autowired
//    private ILimitApi limitApi;
//    
//    /**
//     * 注入应收账款录入api
//     */
//    @Autowired
//    private IRcvbInptApi rcvbInptApi;
//    
//    /**
//     * 注入金票api
//     */
//    @Autowired
//    private IGoldenTicketApi goldenTicketApi;
//    
//    /**
//     * 注入客户邀请api
//     */
//    @Autowired
//    private ICstInvtInfApi cstInvtInfApi;
//    
//    /**
//     * 注入认证api
//     */
//    @Autowired
//    private ICstAprvApi cstAprvApi;
//    
//    /**
//     * 注入还款计划api
//     */
//    @Autowired
//    private ILoanBrgApi loanBrgApi;
    
    /**
     * 注入用户api
     */
    @Autowired
    private UserApi userApi;
    
    
//    /**
//     * 注入客户api
//     */
//    @Autowired
//    private ICustomerApi customerApi;
    
    
    @Autowired
    private TenantApi tenantApi;
    
//    @Autowired
//    private ITbSysparamApi tbSysparamApi;
//    
//    @Autowired
//    private IBuyrPymApi buyrPymApi;
//
//    @Autowired
//    private ILawTxtApi lawTxtApi;
//
//    @Autowired
//    private ICmsApi cmsApi;
//
//    @Autowired
//    private IWithdrawApi withdrawApi;
    
    /**
     * 金票签发人在金票到期前一天晚8点生成金票的《付款通知书》
     */
    private static final int TWENTY = 20;

    /**
     * 付款通知书 biz_cms_artc.artc_tpcd = 12
     */
    private static final String PAYMENT_ORDER = "12";

    /**
     * 原金票编号没有默认为0
     */
    private static final String NO_GOLDID = "0";

    /**
     * 文章管理-法律文本栏目ID
     */
    private String colmId;

    /**
     * 跳转到用户中心页面
     * @方法名称 userCenter
     * @功能描述 <pre>跳转到用户中心页面</pre>
     * @作者    chenld
     * @创建时间 2017/8/30 11:34
     * @return ModelAndView
     */
    @RequestMapping(value = "/center")
    public ModelAndView userCenter() {
        return new ModelAndView("center");
    }
    
    
//    @RequestMapping(value = "/center")
//    public ModelAndView userCenter() {
//        return new ModelAndView("indexScf");
//    }


    /**
     * 获取用户中心信息
     * @方法名称 getUserInfo
     * @功能描述 <pre>获取用户中心信息</pre>
     * @作者    chenld
     * @创建时间 2017/8/30 11:40
     * @param userMap 当前登陆用户信息
     * @return ResponseVO
     */
//    @SuppressWarnings("unchecked")
//	@ResponseBody
//    @RequestMapping(value = "/user/center/getUserInfo")
//    public ResponseVO getUserInfo(@CurrentUser Map<String, Object> userMap) {
//        // 获取当前登录人用户ID
//        String userId = MapUtils.getString(userMap, UserConstants.USER_NUM);
//        Map<String, Object> params = new HashMap<>();
//        params.put("userNum", userId); // 用户编号
//        // 查找用户信息
//        Map<String, Object> curUserMap = this.userApi.getUserMap(params);
//        // 上次登陆时间
//        String lastLoginTime = "";
//        if(MapUtils.getObject(curUserMap, "lastLoginTime") != null) {
//            lastLoginTime = DateUtil.format((Date)MapUtils.getObject(curUserMap, "lastLoginTime"), DateUtil.NORM_DATETIME_PATTERN);
//        }
//        // 查询静态认证信息
//        Map<String, Object> aprvMap = this.cstAprvApi.getCstAprv(params);
//        String cstNm = MapUtils.getString(aprvMap, "cstNm"); // 获取企业名称
//        String socialCreditCode = MapUtils.getString(aprvMap, "orgCode"); // 获取社会信用代码
//        String accNo = ""; // 虚拟账号号码
//        String accBlngBnk = ""; // 账户所属银行
//        BigDecimal accBal     = BigDecimal.ZERO; // 账户余额
//        BigDecimal accFrzAmt  = BigDecimal.ZERO; // 账户冻结金额
//        BigDecimal sumUsedAmt = BigDecimal.ZERO; // 总的已用额度
//        BigDecimal maxAmt     = BigDecimal.ZERO; // 最高可用额度
//        BigDecimal sumGoldenTicket = BigDecimal.ZERO; // 金票金额
//        BigDecimal sumReceivable   = BigDecimal.ZERO; // 应收账款
//        BigDecimal sumPayable      = BigDecimal.ZERO; // 应付账款
//        BigDecimal sumManageFee    = BigDecimal.ZERO; // 应缴纳的应收账款管理费
//        int receivableCount = 0; // 待确认的应收账款笔数
//        int inviteCount = 0; // 待处理的邀请信息个数
//        int manageFeeCount = 0; // 应缴纳的应收账款管理费笔数
//        /* 2017.12.2 start 周晓旭*/
//        // 待支付利息金额
//        BigDecimal interestPaymentAmt = BigDecimal.ZERO;
//        // 待支付利息总金额
//        BigDecimal sumInterestPaymentAmt = BigDecimal.ZERO;
//        // 付款通知书信息
//        List<Map<String, Object>> paymentsDetails = new ArrayList<>();
//        /* 2017.12.2 end   周晓旭*/
//        List<Map<String, Object>> list = new ArrayList<>();
//        List<String> cstIds = this.cstAprvApi.findCstIdsByUserId(userId);
//        if (cstIds != null && !cstIds.isEmpty()) {
//            /************************** 1、查询账户余额 ************************************/
//            // 查询客户的虚拟账号
//            Map<String, Object> queryMap = new HashMap<String, Object>();
//            queryMap.put("cstRltvSn", socialCreditCode);//社会信用证代码
//            Map<String,Object> accInfo = this.acctInfoManagApi.queryAccInfo(queryMap);
//            String code = MapUtil.getString(accInfo, "code");
//            if (!"1".equals(code)) {
//				return errorResponse(MapUtil.getString(accInfo, "message"));
//			}else {
//				List<Map<String, Object>> accList = (List<Map<String, Object>>) accInfo.get("list");
//				if (CollectionUtil.isNotEmpty(accList)) {
//	                Map<String, Object> map = accList.get(0);
//	                accNo = MapUtils.getString(map, "accNo");
//	                accBlngBnk = MapUtils.getString(map, "accBlngBnk");
//	                if (StringUtil.isNotEmpty(accNo)) {
//	                    // 根据虚拟账号查余额
//	                    params.clear();
//	                    params.put("subAccNo", accNo); // 开户账户
//	                    params.put("bnkCd",    accBlngBnk); // 账户所属银行
//	                    params.put("clientID", userId); // 流水号
//	                    try {
//	                        Map<String, Object> accMap = this.bankCustodyApi.getAccBal(params);
//	                        accBal = new BigDecimal(MapUtils.getString(accMap, "accBal")); // 账号余额
//	                        accFrzAmt = new BigDecimal(MapUtils.getString(accMap, "accFrzAmt")); // 冻结金额
//	                    } catch (Exception e) {
//	                        logger.error("查询虚拟账号余额异常！");
//	                    }
//	                }
//
//				}
//			}
//            /************************* 2、查询已用金额和最高可用余额 **********************/
//            params.clear();
//            // 查询类型参数queryType 0：新增列表查询
//            params.put("dmsnVals", cstIds);
//            // 额度维度 3:客户
//            params.put("dmsnTpCd", "3");
//            // 额度查询类型 6:有效数据列表查询
//            /**
//             * AND LMT_ST_CD != '0' AND LMT_ST_CD != '4' AND LMT_ST_CD != '5'
//             * 额度状态代码 != 0:未生效
//             * 额度状态代码 != 4:无效
//             * 额度状态代码 != 5:失效
//             * */
//            params.put("queryType", "6");
//
//            // 过滤标志
//            params.put("filterFlag","userCenterFlag");
//            // 非授信额度list
//            List<String> ctlgIds = new ArrayList<>();
//            ctlgIds.add("edbh009"); // 买方交易额度
//            params.put("filterCtlgIds",ctlgIds);
//
//            // 根据客户编号查询额度信息
//            PageInfo<Map<String, Object>> pageInfo = this.limitApi.findLimitListPage(params, null);
//            BigDecimal sumAmt = BigDecimal.ZERO; // 总的额度金额
//            BigDecimal sumSubAmt = BigDecimal.ZERO; // 总的子额度占用金额
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                // 获取当前系统日期
//                Date currentTime = new Date();
//                list = pageInfo.getList();
//                for (Map<String, Object> map : list) {
//                    // 额度开始日期
//                    Date stDate = (Date)MapUtils.getObject(map, "stDt");
//                    // 额度结束日期
//                    Date edDate = (Date)MapUtils.getObject(map, "edDt");
//                    if(stDate != null && edDate != null){
//                        // 当前日期是否晚于结束日期 或 早于开始日期
//                        if(currentTime.after(edDate) || currentTime.before(stDate)){
//                            // 过期
//                            continue;
//                        }
//                    }
//                    // 额度金额
//                    BigDecimal lmtAmt = (BigDecimal)MapUtils.getObject(map, "lmtAmt");
//                    // 所有子额度的实际占用金额
//                    BigDecimal subTotalOcpAmt = (BigDecimal)MapUtils.getObject(map, "subTotalOcpAmt");
//                    // 额度已用金额
//                    BigDecimal usedAmtRmtNoShr = (BigDecimal)MapUtils.getObject(map, "usedAmtRmtNoShr");
//                    sumAmt = sumAmt.add(lmtAmt);
//                    sumUsedAmt = sumUsedAmt.add(usedAmtRmtNoShr);
//                    sumSubAmt = sumSubAmt.add(subTotalOcpAmt);
//                }
//                maxAmt = sumAmt.subtract(sumUsedAmt);
//            }
//            /************************* 3、查询金票金额 *********************************/
//            params.clear();
//            BigDecimal gldAmt = BigDecimal.ZERO;
//       /*     // 3、1 开立方
//            params.put("gldStCd", "1"); // 金票状态：已签收
//            params.put("estbs", cstIds); // 开立方
//            pageInfo = this.goldenTicketApi.findGldInfListPage(params, null);
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                list = pageInfo.getList();
//                for (Map<String, Object> map : list) {
//                    // 金票金额
//                    gldAmt = new BigDecimal(map.get("gldBal").toString());
//                    sumGoldenTicket = sumGoldenTicket.add(gldAmt);
//                }
//            }*/
//            // 3、2 接收方
//            params.clear();
//            params.put("gldStCd", "1"); // 金票状态：已签收
//            params.put("rcPtys", cstIds);
//            pageInfo = this.goldenTicketApi.findGldInfListPage(params, null);
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                list = pageInfo.getList();
//                for (Map<String, Object> map : list) {
//                    // 金票金额
//                    gldAmt = new BigDecimal(map.get("gldBal").toString());
//                    sumGoldenTicket = sumGoldenTicket.add(gldAmt);
//                }
//            }
//            /******************** 4、查询应收账款-卖方，包含自动显示状态为未确认未转让/已转让/已确认未转让且已匹配合同的应收账款余额之和 ****************/
//            params.clear();
//            params.put("cstIds", cstIds); // 客户编号
//            List<String> cfmInd = new ArrayList<>();
//            cfmInd.add("0"); // 已确认
//            params.put("rcvbTrsferInd","0");//未转让
//            params.put("cfmInd", cfmInd);
//            BigDecimal rcvbAmt = BigDecimal.ZERO; // 应收账款
//            //查询一次
//            pageInfo = this.rcvbInptApi.findRcvbInptDtlListPage(params, null);
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                list = pageInfo.getList();
//                for (Map<String, Object> map : list) {
//                    // 应收账款
//                    rcvbAmt = new BigDecimal(map.get("rcvbAmt").toString());
//                    sumReceivable = sumReceivable.add(rcvbAmt);
//                }
//            }
//            params.clear();
//            params.put("cstIds", cstIds); // 客户编号
//            params.put("rcvbTrsferInd","1");//已转让
//            //查询一次
//            pageInfo = this.rcvbInptApi.findRcvbInptDtlListPage(params, null);
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                list = pageInfo.getList();
//                for (Map<String, Object> map : list) {
//                    if (!StringUtil.isEmpty(MapUtil.getString(map, "ctrId"))) {
//                         // 应收账款
//                        rcvbAmt = new BigDecimal(map.get("rcvbBal").toString());
//                        sumReceivable = sumReceivable.add(rcvbAmt);
//                    }
//                }
//            }
//            params.clear();
//            List<String> cfmInd1 = new ArrayList<>();
//            params.put("cstIds", cstIds); // 客户编号
//            cfmInd1.add("1"); //已确认
//            params.put("cfmInd", cfmInd1);
//            params.put("rcvbTrsferInd","0");//未转让
//            //查一次（匹配了合同的）
//            pageInfo = this.rcvbInptApi.findRcvbInptDtlListPage(params, null);
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                list = pageInfo.getList();
//                for (Map<String, Object> map : list) {
//                	if (!StringUtil.isEmpty(MapUtil.getString(map, "ctrId"))) {
//                		 // 应收账款
//                        rcvbAmt = new BigDecimal(map.get("rcvbAmt").toString());
//                        sumReceivable = sumReceivable.add(rcvbAmt);
//					}
//                   
//                }
//            }
//            /*************************** 5、查询应付账款-买方，自动显示状态为已确认应付账款 **********************/
//            params.clear();
//            List<String> cfmInd2 = new ArrayList<>();
//            cfmInd2.add("1");
//            params.put("cfmInd", cfmInd1);//已确认
//            Map<String, Object> userIdMap = new HashMap<String,Object>();
//            userIdMap.put("userNum", userId);
//            Map<String, Object> cstAprv = cstAprvApi.getCstAprv(userIdMap);//获取社会信用代码
//            params.put("buyrCrCd", MapUtils.getString(cstAprv, "orgCode"));
//            params.put("fctrnCtrInd", "1");
//    		params.put("inptStCds", "1");
//            pageInfo = this.rcvbInptApi.findRcvbInptDtlListPage(params, null);
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                list = pageInfo.getList();
//                for (Map<String, Object> map : list) {
//                    // 应付账款
//                	if(MapUtil.getString(map, "rcvbTrsferInd").equals("0")){//未转让
//	                    rcvbAmt = new BigDecimal(map.get("rcvbAmt").toString());
//	                    sumPayable = sumPayable.add(rcvbAmt);
//                	}
//                	if(MapUtil.getString(map, "rcvbTrsferInd").equals("1")){//已转让
//                        rcvbAmt = new BigDecimal(map.get("rcvbBal").toString());
//                        sumPayable = sumPayable.add(rcvbAmt);
//                    }
//                	
//                }
//            }
//            /************************** 6、查询待确认的应收账款信息条数 ***********************/
//            params.clear();
//            cfmInd = new ArrayList<>();
//            cfmInd.add("1"); // 已确认
//            params.put("cfmInd", cfmInd); // 已确认
//            params.put("rcvbTrsferInd", "0"); // 未转让
//            params.put("alrdyEstbGldInd","0");//未开立金票
//            params.put("poolCtrInd", "1");//池合同标志
//            params.put("sellrCrCd", socialCreditCode);
//            pageInfo = this.rcvbInptApi.findRcvbInptDtlListPage(params, null);
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                receivableCount = pageInfo.getList().size();
//            }
//            /************************** 8、查询应缴纳的应收账款管理费及笔数 ***********************/
//            params.clear();
//            params.put("userNum", userId);
//            params.put("chrgTpCd", "2");
//            params.put("dtSrc",    "1");//数据来源保理门户
//            pageInfo = this.accEpsMgtApi.pageExpenseListFromCore(params, null);
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                list = pageInfo.getList();
//                manageFeeCount = list.size();
//                for (Map<String, Object> map : list) {
//                    rcvbAmt = new BigDecimal(map.get("pyFeeAmt").toString()); // 付费金额
//                    sumManageFee = sumManageFee.add(rcvbAmt); // 管理费
//                }
//            }
//        }
//        /*************************** 7、查询待处理的邀请信息条数 ***************************/
//        params.clear();
//        params.put("cstOrgCode", socialCreditCode); // 当前客户的社会信用代码
//        params.put("inviteStatus", "1"); // 邀请状态:邀请中
//        PageInfo<Map<String, Object>> pageInfo = this.cstInvtInfApi.findBizCstInvtInfPage(params, null);
//        if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//            list = pageInfo.getList();
//            inviteCount = list.size();
////            for (Map<String, Object> map : list) {
////                map.put("inviteCode","");
////            }
//        }
//        /* 2017.12.2 start 周晓旭*/
//        /*************************** 9、查询待支付利息金额 ***************************/
//        params.clear();
//        // 客户编号
//        params.put("lndCstId",userId);
//        // 是否查结清标识
//        params.put("pastWithUnClearFlag ","0");
//        // 获取当前系统日期
//        Date currentTime = new Date();
//        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
//        // 当前日期
//        params.put("bussDate ",f.format(currentTime));
//        pageInfo = this.loanBrgApi.queryRepayPlanListByParamsPage(params,null,null);
//        if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//            list = pageInfo.getList();
//            // 遍历累加利息
//            for (Map<String, Object> map : list) {
//                // 单个利息
//                interestPaymentAmt = new BigDecimal(map.get("crnPrdRepyInt").toString());
//                // 累加利息
//                sumInterestPaymentAmt = sumInterestPaymentAmt.add(interestPaymentAmt);
//            }
//        }
//        /*************************** 10、查询付款书通知的信息条数 ***************************/
//        params.clear();
//        // 开立方id
//        List<String> cstIdList = this.cstAprvApi.findCstIdsByUserId(userId);
//        if(cstIdList != null && cstIdList.size()>0){
//            params.put("estbs",cstIdList);
//            // 状态：1（已签收）
//            params.put("gldStCd","1");
//            // 获取系统时间
//            Date date=new Date();
//            Calendar calendar = new GregorianCalendar();
//            calendar.setTime(date);
//            int hour = calendar.get(Calendar.HOUR_OF_DAY);
//            // 大于20点，即可认为当前为次日
//            if(hour >= TWENTY){
//                // 把日期往后增加一天.整数往后推,负数往前移动
//                calendar.add(calendar.DATE,1);
//            }
//            // 这个时间就是日期往后推一天的结果
//            date=calendar.getTime();
//            // 日期格式转换
//            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
//            String dateString = formatter.format(date);
//            // 截至日期
//            params.put("exExdt",dateString);
//            // 原金票编号.无
//            params.put("oriGldId",NO_GOLDID);
//            pageInfo = this.goldenTicketApi.findGldInfListPage(params,null);
//            if (pageInfo != null && pageInfo.getList() != null && !pageInfo.getList().isEmpty()) {
//                paymentsDetails = pageInfo.getList();
//            }
//        }
//        /* 2017.12.2 end 周晓旭*/
//
//
//        /*************************** 11、查询待处理电子签章授权信息 ***************************/
//        /* 2017.12.7 start 蔡群健*/
//        String aprvStCd = MapUtils.getString(aprvMap, "aprvStCd"); // 获取认证状态
//        String verifyStCd = MapUtils.getString(aprvMap, "verifyStCd"); // 获取安心签状态
//        int singCount = 0;// 安心签认证
//        int tickCount = 0;// 协议
//        if(!StringUtil.isEmpty(aprvStCd)&&aprvStCd.equals("1")){//判断是否静态认证通过
//            if("".equals(verifyStCd)||"0".equals(verifyStCd)){ //判断是否电子签章授权
//                singCount ++;
//            }
//        }
//
//
//
//        /*************************** 12、查询待签署的合同文本/协议***************************/
//        int contractCount = 1;// 未签署的合同数
//        Map<String,Object> lawMap = new HashMap<>();
//        lawMap.put("userNum",MapUtils.getString(userMap,"userNum"));
//        lawMap.put("signStCd","0");
//        PageInfo<Map<String, Object>> lawList = lawTxtApi.findBizLawTxtPage(lawMap,null);//根据客户Id去查询是否签合同文本
//        List<Map<String,Object>> contractList = lawList.getList();
//        contractCount = contractList.size();
//        /* 2017.12.7 end 蔡群健*/
//
//
//        Map<String, Object> result = new HashMap<>();
//        result.put("lastLoginTime", lastLoginTime);
//        result.put("accBal", accBal);
//        result.put("accFrzAmt", accFrzAmt);
//        //result.put("availAmt",  accBal.subtract(accFrzAmt));
//        result.put("sumUsedAmt", sumUsedAmt);
//        result.put("maxAmt", maxAmt);
//        result.put("sumGoldenTicket", sumGoldenTicket);
//        result.put("sumReceivable", sumReceivable);
//        result.put("sumPayable", sumPayable);
//        result.put("receivableCount", receivableCount);
//        result.put("inviteCount", inviteCount);
//        result.put("sumManageFee", sumManageFee);
//        result.put("manageFeeCount", manageFeeCount);
//        result.put("inviteList", list); // 邀请列表
//        result.put("aprvStCd", aprvStCd); // 认证状态
//        result.put("cstNm", cstNm); // 客户名称
//        result.put("accNo", accNo); // 客户的平台虚拟账户
//        result.put("contractCount", contractCount);//协议签署
//        result.put("singCount", singCount);//安心签状态contractList
//        result.put("tickCount", tickCount);//金票协议状态
//        result.put("contractList", contractList); // 带签署合同列表
//        /* 2017.12.2 start 周晓旭*/
//        // 待支付利息
//        result.put("sumInterestPaymentAmt", sumInterestPaymentAmt);
//        // 付款通知书信息
//        result.put("paymentsDetails", paymentsDetails);
//        /* 2017.12.2 end 周晓旭*/
//        return successResponse(result);
//    }
    
    /**
     * @方法名称 queryRepaymentInfo
     * @功能描述 <pre>查询还款通知统计信息</pre>
     * @作者    jiaxx
     * @创建时间 2017年9月12日 下午3:52:47
     * @param userMap
     * @return
     * @throws ParseException 
     */
    @ResponseBody
    @RequestMapping(value = "/user/center/queryRepaymentInfo")
    public ResponseVO queryRepaymentInfo(@RequestObjectParam Map<String, Object> params,
            @CurrentUser Map<String, Object> userMap) {
        // 获取当前登录人用户ID
        String userId = MapUtils.getString(userMap, UserConstants.USER_NUM);
        List<Map<String, Object>> payableChartList = new ArrayList<>();
        Map<String, Object> dayMap = new HashMap<>();
        Map<String, Object> weekMap = new HashMap<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cl = Calendar.getInstance();
        String repyBgDy = DateUtil.formatDate(new Date()); // 开始时间
        String repyEnDy = DateUtil.formatDate(new Date()); // 结束时间
        // BigDecimal payableAmount = BigDecimal.ZERO; // 应还款总额
        int payableCount = 0; // 应还款笔数
        List<Map<String, Object>> list = new ArrayList<>();
        String type = MapUtils.getString(params, "type");
        if ("0".equals(type)) { // 按周
            repyEnDy = DateUtil.formatDate(DateUtil.addDay(new Date(), 7));
        } else if ("1".equals(type)) { // 按月
            repyEnDy = DateUtil.formatDate(DateUtil.addMouth(new Date(), 1));
        } else {
            repyBgDy = null;
        }
       
        Map<String, Object> result = new HashMap<>();
        // result.put("payableAmount", payableAmount);
        result.put("payableCount", payableCount);
        result.put("payableChart", payableChartList);
        return successResponse(result);
    }
    
    /**
     * @方法名称 checkWithdrawInfo
     * @功能描述 <pre>校验提现申请信息</pre>
     * @作者    jiaxx
     * @创建时间 2017年9月12日 下午12:17:11
     * @param params
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/user/center/checkWithdrawInfo")
//    public ResponseVO checkWithdrawInfo(@RequestObjectParam Map<String, Object> params, @CurrentUser Map<String, Object> userMap) {
//        // 获取当前登录人用户ID
//        String userId = MapUtils.getString(userMap, UserConstants.USER_NUM);
//        BigDecimal amount = new BigDecimal(MapUtils.getString(params, "amount")); // 提现金额
//        String accNo = ""; // 虚拟账号号码
//        String accBlngBnk = ""; // 账户所属银行
//        BigDecimal accBal = BigDecimal.ZERO; // 账户可用余额
//        /************************** 1、查询虚拟账户余额 ************************************/
//        // 查询客户的虚拟账号
//        params.clear();
//        params.put("userNum", userId); // 用户编号
//        // 查询静态认证信息
//        Map<String, Object> aprvMap = this.cstAprvApi.getCstAprv(params);
//        String socialCreditCode = MapUtils.getString(aprvMap, "orgCode"); // 获取社会信用代码
//        String cstNm = MapUtils.getString(aprvMap, "cstNm"); // 获取客户名称
//        Map<String, Object> queryMap = new HashMap<String, Object>();
//        queryMap.put("cstRltvSn", socialCreditCode);// 社会信用证代码
//        Map<String, Object> accInfo = this.acctInfoManagApi.queryAccInfo(queryMap);
//        String code = MapUtil.getString(accInfo, "code");
//        if (!"1".equals(code)) {
//            return errorResponse(MapUtil.getString(accInfo, "message"));
//        } else {
//            List<Map<String, Object>> accList = (List<Map<String, Object>>)accInfo.get("list");
//            if (CollectionUtil.isEmpty(accList)) {
//                return errorResponse("虚拟账号不存在！");
//            }
//            Map<String, Object> map = accList.get(0);
//            accNo = MapUtils.getString(map, "accNo");
//            accBlngBnk = MapUtils.getString(map, "accBlngBnk");
//            if (StringUtil.isNotEmpty(accNo)) {
//                // 根据虚拟账号查余额
//                params.clear();
//                params.put("subAccNo", accNo); // 开户账户
//                params.put("bnkCd", accBlngBnk); // 账户所属银行
//                params.put("clientID", userId); // 流水号
//                try {
//                    Map<String, Object> accMap = this.bankCustodyApi.getAccBal(params);
//                    accBal = new BigDecimal(MapUtils.getString(accMap, "accBal")); // 账号可用余额
//                    if (amount.compareTo(accBal) > 0) {
//                        return errorResponse("余额不足，提现失败！");
//                    }
//                } catch (Exception e) {
//                    logger.error("查询虚拟账号余额异常！");
//                }
//            }
//        }
//        Map<String, Object> resultMap = new HashMap<>();
//        resultMap.put("cstNm", cstNm);
//        return successResponse(resultMap);
//    }
    
    /**
     * @方法名称 withdraw
     * @功能描述 <pre>提现</pre>
     * @作者    jiaxx
     * @创建时间 2017年9月12日 下午12:17:23
     * @param params
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/user/center/withdraw")
//    public ResponseVO withdraw(@RequestObjectParam Map<String, Object> params, @CurrentUser Map<String, Object> userMap) {
//        // 获取当前登录人用户ID
//        String userId = MapUtils.getString(userMap, UserConstants.USER_NUM);
//        String depBnk = MapUtils.getString(params, "depBnk"); // 开户银行
//        String bnkAccNo = MapUtils.getString(params, "bnkAccNo"); // 银行账户
//        BigDecimal amount = new BigDecimal(MapUtils.getString(params, "amount")); // 提现金额
//        String txnPwd = MapUtils.getString(params, "txnPwd"); // 交易密码
//        BigDecimal accBal = BigDecimal.ZERO; // 账户余额
//        /************************** 1、查询虚拟账户余额 ************************************/
//        params.clear();
//        params.put("userNum", userId); // 用户编号
//        // 查询静态认证信息
//        Map<String, Object> aprvMap = this.cstAprvApi.getCstAprv(params);
//        String socialCreditCode = MapUtils.getString(aprvMap, "orgCode"); // 获取社会信用代码
//        Map<String, Object> queryMap = new HashMap<String, Object>();
//        queryMap.put("cstRltvSn", socialCreditCode);// 社会信用证代码
//        Map<String, Object> accInfo = this.acctInfoManagApi.queryAccInfo(queryMap);
//        String code = MapUtil.getString(accInfo, "code");
//        if (!"1".equals(code)) {
//            return errorResponse(MapUtil.getString(accInfo, "message"));
//        }
//        List<Map<String, Object>> acclist = (List<Map<String, Object>>)accInfo.get("list");
//        if (CollectionUtil.isEmpty(acclist)) {
//            return errorResponse("虚拟账号不存在！");
//        }
//        Map<String, Object> map = acclist.get(0);
//        String accNo = MapUtils.getString(map, "accNo"); // 虚拟账号号码
//        if (StringUtil.isNotEmpty(accNo)) {
//            // 根据虚拟账号查余额
//            params.clear();
//            params.put("subAccNo", accNo); // 开户账户
//            params.put("bnkCd", IBankCustodyApi.BANK_CITIC); // 账户所属银行
//            params.put("clientID", userId); // 流水号
//            try {
//                Map<String, Object> accMap = this.bankCustodyApi.getAccBal(params);
//                accBal = new BigDecimal(MapUtils.getString(accMap, "accBal")); // 账号可用余额
//                if (amount.compareTo(accBal) > 0) {
//                    return errorResponse("余额不足，提现失败！");
//                }
//            } catch (Exception e) {
//                logger.error("查询虚拟账号余额异常！");
//            }
//        }
//        /************************** 2、查询交易密码 ************************************/
//        params.clear();
//        params.put("userNum", userId); // 用户编号
//        // 查询静态认证信息
//        String txnPswd = MapUtils.getString(aprvMap, "txnPswd"); // 获取交易密码
//        if (!txnPwd.equals(txnPswd)) {
//            return errorResponse("交易密码输入错误！");
//        }
//        /************************** 3、保存申请提现记录 ************************************/
//        String aplyId = this.withdrawApi.buildAplyId(); // 获取申请编号
//        params.put("accNm", depBnk);
//        params.put("accNo", bnkAccNo);
//        params.put("txnAmt", amount);
//        params.put("clientId", aplyId); // 交易流水号
//        params.put("dtSrc", "1"); // 门户
//        this.withdrawApi.saveOrUpdateBizWithdrawRcrdTrans(params);
//        return successResponse("提现申请成功！");
//    }
    
    /**
     * @方法名称 updateCstInvite
     * @功能描述 <pre>接受或拒绝客户邀请</pre>
     * @作者    jiaxx
     * @创建时间 2017年9月22日 下午5:32:41
     * @param params
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/user/center/updateCstInvite")
//    public ResponseVO updateCstInvite(@RequestObjectParam Map<String, Object> params, @CurrentUser Map<String, Object> user) {
//        String id = MapUtils.getString(params, "id"); // id
//        String inviterOrgCode = MapUtils.getString(params, "inviterOrgCode"); // 获取邀请人社会信用代码
//        String cstOrgCode = MapUtils.getString(params, "cstOrgCode"); // 获取被邀请人社会信用代码
////        String type = MapUtils.getString(params, "type"); // 类型
//        String type = MapUtils.getString(params, "inviteStatus"); // 类型
//        Map<String, Object> map = new HashMap<>();
//        map.put("id", id);
//        map.put("inviterOrgCode", inviterOrgCode);
//        map.put("cstOrgCode", cstOrgCode);
////        if ("0".equals(type)) {
//        if ("3".equals(type)) {
//            String inviteCode = MapUtils.getString(params, "inviteCode"); // 获取邀请码
//            String confirmInviteCode = MapUtils.getString(params, "confirmInviteCode"); // 获取输入的邀请码
//            if (!confirmInviteCode.equals(inviteCode)) {
//                return errorResponse("录入的邀请码有误，无法接受邀请");
//            }
//            map.put("inviteCode", confirmInviteCode);
//            this.cstInvtInfApi.acceptInviteTran(map); // 接收邀请
//        } else {
//            this.cstInvtInfApi.rejectInviteTran(map); // 拒绝邀请
//        }
//        return successResponse("成功");
//    }
    
    /**
     * @方法名称 sendInviteCode
     * @功能描述 <pre>发送邀请码</pre>
     * @作者    jiaxx
     * @创建时间 2017年9月25日 下午4:55:01
     * @param params
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/user/center/sendInviteCode")
//    public ResponseVO sendInviteCode(@RequestObjectParam Map<String, Object> params, @CurrentUser Map<String, Object> user) {
//        // 获取基本信息
//        Map<String, Object> aprvInfo = this.cstInvtInfApi.getBizCstInvtInf(params);
//        Map<String, Object> invtInfo = this.cstInvtInfApi.sendInvtCodeTran(aprvInfo); // 发送邀请码
//        if (ObjectUtil.isEmpty(invtInfo)) {
//            return errorResponse("发送邀请码失败！");
//        }
//        return successResponse("发送邀请码成功");
//    }
    
    /**
     * @方法名称 rcvbConfirm
     * @功能描述 <pre>应收账款卖方确认弹出框</pre>
     * @作者    zhangc
     * @创建时间 2017年10月22日 下午1:01:35
     * @param params
     * @param pageParam
     * @return
     */
//    @ResponseBody
//    @Translaters({
//		@Translater(name="creditCode",fromKeys={"buyrCrCd"},toKey="cntprNm")
//	})
//    @RequestMapping(value = "/user/center/rcvbConfirm")
//    public ResponseVO rcvbConfirm(@RequestObjectParam Map<String, Object> params, PageParam pageParam,
//    		@CurrentUser Map<String,Object> userMap) {
//    	List<String> cfmInd = new ArrayList<>();    	
//        cfmInd.add("1"); //已确认
//        params.put("cfmInd", cfmInd); // 已确认
//        params.put("rcvbTrsferInd","0");//未转让
//        params.put("poolCtrInd","1");//未匹配到池合同
//        params.put("alrdyEstbGldInd","0");//未开立金票
//        String userNum = MapUtils.getString(userMap, "userNum");//获取登录名称
//        Map<String,Object> userNumMap = new HashMap<String,Object>();
//        userNumMap.put("userNum", userNum);
//        Map<String, Object> cstAprvMap = cstAprvApi.getCstAprv(userNumMap);//获取卖方信用代码
//		String orgCodeNum = MapUtils.getString(cstAprvMap, "orgCode");
//        params.put("sellrCrCd", orgCodeNum);
//    	PageInfo<Map<String, Object>> rcvbInptDtlListPage = rcvbInptApi.findRcvbInptDtlListPage(params, pageParam);
//    	Map<String, Object> result = new HashMap<String,Object>();
//    	result.put("grid", rcvbInptDtlListPage);		
//		return this.successResponse(result);   	
//    }

    /**
     * @方法名称 getGoldenTicket
     * @功能描述 <pre>获取付款通知书信息</pre>
     * @作者    zhouxx
     * @创建时间 2017年12月2日 下午4:55:01
     * @param params
     * @return
     */
//    @ResponseBody
//    @RequestMapping(value = "/user/center/getGoldenTicket")
//    public ResponseVO getGoldenTicket(@RequestObjectParam Map<String, Object> params, @CurrentUser Map<String, Object> user) {
//        // 容器定义
//        // 持票人信息
//        List<Map<String, Object>> rcInfoList = new ArrayList<Map<String, Object>>();
//        // 融资信息
//        List<Map<String, Object>> tenInfoList = new ArrayList<Map<String, Object>>();
//        // 根据金票号获得相关信息（金票基础表）
//        PageInfo<Map<String, Object>> gldBaseInfo = this.goldenTicketApi.findGldInfListPage(params,null);
//        // 金票baseMap
//        Map<String, Object> gldBaseMap = gldBaseInfo.getList().get(0);
//        // 金票号（母票）
//        String baseGldId = (String)gldBaseMap.get("gldId");
//        // 开立方id
//        String estbId = (String)gldBaseMap.get("estb");
//        // 保理商id
//        String tenantId = MapUtils.getString(user, UserConstants.TENANT_ID);
//        // 开立方信息
//        params.clear();
//        params.put("cstId", estbId);
//        Map<String, Object> estbInfo = this.customerApi.getBizCstBscInfByParams(params);
//        if (ObjectUtil.isEmpty(estbInfo)) {
//            return errorResponse("该金票的开立方信息不存在！");
//        }
//        Map<String,Object> accInfo = customerApi.getVrtlAccByCstId(estbId);
//        estbInfo.put("accNo", accInfo.get("accNo"));
//        estbInfo.put("accNm", accInfo.get("accNm"));
//        // 一手信息
//        Map<String, Object> tempInfo = this.customerApi.getVrtlAccByCstId((String) gldBaseMap.get("rcPty"));
//        // 一手账号
//        gldBaseMap.put("accNo", tempInfo.get("accNo"));
//        // 一手名字
//        gldBaseMap.put("accNm", tempInfo.get("accNm"));
//        // 所有持有该金票的账户信息（除了一手）
//        params.clear();
//        params.put("oriGldId", baseGldId);
//        PageInfo<Map<String, Object>> holdInfo = this.goldenTicketApi.findGldInfListPage(params,null);
//        // 遍历根据持票人的id查找该信息
//        for(Map<String, Object> map : holdInfo.getList()){
//            // 持票人
//            params.clear();
//            params.put("cstId",map.get("rcPty"));
//            Map<String, Object> rcInfo = this.userApi.getUserMap(params);
//            if (ObjectUtil.isEmpty(rcInfo)) {
//                return errorResponse("该金票的持票人信息不存在！");
//            }
//            accInfo.clear();
//            accInfo = customerApi.getVrtlAccByCstId((String) map.get("rcPty"));
//            if (ObjectUtil.isEmpty(accInfo)) {
//                return errorResponse("该金票的持票人信息不存在！");
//            }
//            rcInfo.put("accNo", accInfo.get("accNo"));
//            rcInfo.put("gldBal", map.get("gldBal"));
//            rcInfo.put("accNm", accInfo.get("accNm"));
//            rcInfoList.add(rcInfo);
//            // 判断是否存在融资
//            BigDecimal fncAmt  = (BigDecimal)map.get("fncAmt");
//            if(fncAmt.compareTo(BigDecimal.ZERO) == 1){
//                String ten = (String)map.get("fctr");
//                //根据租户Id获取保理商账户信息
//                Map<String, Object> tenantAccNo = this.customerApi.getVrtlAccByTenantId((String)map.get("tenantId"));
//                Map<String, Object> tenTemp = new HashMap<String, Object>();
//                tenTemp.put("accNo", tenantAccNo.get("accNo"));
//                tenTemp.put("accNm", tenantAccNo.get("accNm"));
//                tenTemp.put("fncAmt", fncAmt);
//                tenInfoList.add(tenTemp);
//            }
//        }
//        // 获取母票的融资金额
//        BigDecimal fncAmt  = (BigDecimal)gldBaseMap.get("fncAmt");
//        if(fncAmt.compareTo(BigDecimal.ZERO) == 1){
//            String ten = (String)gldBaseMap.get("fctr");
//            //根据租户Id获取保理商账户信息
//            Map<String, Object> tenantAccNo = this.customerApi.getVrtlAccByTenantId((String)gldBaseMap.get("tenantId"));
//            Map<String, Object> tenTemp = new HashMap<String, Object>();
//            tenTemp.put("accNo", tenantAccNo.get("accNo"));
//            tenTemp.put("accNm", tenantAccNo.get("accNm"));
//            tenTemp.put("fncAmt", fncAmt);
//            tenInfoList.add(tenTemp);
//        }
//        // 保理商信息
//        params.clear();
//        params.put("tenantId", tenantId); // 保理商编号
//        // 根据保理商编号获取保理商信息
//        Map<String, Object> tenantMap = this.customerApi.getVrtlAccByTenantId((String)gldBaseMap.get("tenantId"));//this.tenantApi.getTenantMap(params);
//        // 获取固定文言
//        params.clear();
//        params.put("colmId", colmId);
//        params.put("artcTpcd", PAYMENT_ORDER);
//        Map<String, Object> artc = cmsApi.getCmsArtcByParams(params);
//        // 模板文言
//        String tempArtcCntnt = MapUtil.getString(artc, "artcCntnt");
//        // 数据填充到模板文言中
//        params.clear();
//        // 开立方信息
//        params.put("estbInfo", estbInfo);
//        // 保理商信息
//        params.put("tenantInfo", tenantMap);
//        // 金票baseMap
//        params.put("gldBaseInfo", gldBaseMap);
//        // 持票人
//        params.put("rcInfos", rcInfoList);
//        // 融资信息
//        params.put("tenInfos", tenInfoList);
//        String artcCntnt = FreemarkerUtil.getTemplateAndContent(params, tempArtcCntnt);
//        // 传递数据
//        params.clear();
//        params.put("artcCntnt", artcCntnt);
//        return this.successResponse(params);
//    }
}
