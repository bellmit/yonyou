
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : PotentialCusServiceImpl.java
 *
 * @Author : Administrator
 *
 * @Date : 2016年9月1日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月1日    Administrator    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.customer.service.customerManage;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusListDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCustomerIntentDetailDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCustomerVehicleListDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtPoCusLinkmanDTO;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.PoCusWholesalePO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.QcsArchivesPO;
import com.yonyou.dms.common.domains.PO.basedata.SalesPromotionPO;
import com.yonyou.dms.common.domains.PO.basedata.TmMemberPO;
import com.yonyou.dms.common.domains.PO.basedata.TmModelPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerGatheringPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesCrPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtTestdrivePlanPO;
import com.yonyou.dms.common.domains.PO.basedata.VisitingRecordPO;
import com.yonyou.dms.common.domains.PO.monitor.OperateLogPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.common.service.monitor.Utility;
import com.yonyou.dms.commonSales.domains.PO.stockmanage.SalesOrderPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCustomerImportDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SADMS049Coud;
import com.yonyou.dms.gacfca.SOTDCS003Coud;
import com.yonyou.dms.gacfca.SOTDCS005Coud;
import com.yonyou.dms.gacfca.SOTDCS008Coud;
import com.yonyou.dms.gacfca.SOTDCS013Coud;

/**
 * 潜在客户
 * 
 * @author zhanshiwei
 * @date 2016年9月1日
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class PotentialCusServiceImpl implements PotentialCusService {
    private static final Logger logger = LoggerFactory.getLogger(PotentialCusServiceImpl.class);
    @Autowired
    private VisitingRecordService visitingrecordservice;
    @Autowired
    private TrackingTaskService   trackingtaskservice;
    @Autowired
    private OperateLogService operateLogService;
    @Autowired
    private CommonNoService     commonNoService;
    @Autowired
    private SOTDCS005Coud   sotdcs005;
    @Autowired
    private SOTDCS008Coud   sotdcs008;
    @Autowired
    private SOTDCS003Coud sotdcs003;
    @Autowired
    private SOTDCS013Coud sotdcs013;
    @Autowired
    SADMS049Coud SADMS049;



    /**
     * 潜在客户信息新增
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param potentialCusDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#addPotentialCusInfo(com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCusDTO)
     */

    @Override
    public String addPotentialCusInfo(PotentialCusDTO potentialCusDto, String customerNo) throws ServiceBizException {
        PotentialCusPO potentialCusPo = new PotentialCusPO();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        TtCusIntentPO intentpo = new TtCusIntentPO();
        potentialCusDto.setCustomerNo(customerNo);// 潜客编码
        List<Object> keepList = new ArrayList<Object>();
        keepList.add(customerNo);
        keepList.add(FrameworkUtil.getLoginInfo().getDealerCode());
        List<TtCustomerVehicleListPO> keepCarPo = TtCustomerVehicleListPO.findBySQL("select * from TT_CUSTOMER_VEHICLE_LIST where CUSTOMER_NO= ? AND DEALER_CODE= ? ", keepList.toArray());
        
        Long intentId = commonNoService.getId("ID");
        System.out.println("——————————————————————————————————开始保存——————————————————————————————---");
        System.out.println(potentialCusDto.getIsPersonDriveCar());
        System.out.println(customerNo);
        System.out.println(intentId);
   /*     // 1. 取Min(来访时间)且是否首次到店为是的展厅记录中的来访时间作为客户首次到店时间
        // 2. 取Min(来访时间)且是否首次到店为是，是否二次到店为是的展厅记录中的来访时间作为客户二次到店时间。
        this.setFirstArriveTime(potentialCusDto.getContactorMobile(), potentialCusPo);
        this.setSecondArriveTime(potentialCusDto.getContactorMobile(), potentialCusPo);*/
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        System.out.println("日期"+format1.format(new Date()));
        potentialCusPo.setString("FOUND_DATE",  format1.format(new Date()));
        potentialCusPo.setString("VALIDITY_BEGIN_DATE",  format1.format(new Date()));
        potentialCusPo.setLong("INTENT_ID", intentId);
        potentialCusPo.setLong("IS_UPLOAD", potentialCusDto.getIsUpload());
        this.setPotentialCus(potentialCusPo, potentialCusDto);
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsTheShop())){
            potentialCusPo.setLong("IS_TO_SHOP", potentialCusDto.getIsToShop());//是否到店
            //potentialCusPo.setString("TIME_TO_SHOP", format1.format(new Date()));
        }else{
            potentialCusPo.setLong("IS_TO_SHOP", DictCodeConstants.IS_NOT);
        }
        potentialCusPo.setLong("IS_TEST_DRIVE", DictCodeConstants.IS_NOT);
        potentialCusPo.saveIt();
        logger.debug("客户信息已经生成，下面去LMS校验线程开始");
       
        String lmsMobile="";
        String lmsPhone="";
        if (null != potentialCusPo.getString("CONTACTOR_PHONE") && !("".equals(potentialCusPo.getString("CONTACTOR_PHONE")))) {
            lmsPhone=potentialCusPo.getString("CONTACTOR_PHONE");
        }
        if (null != potentialCusPo.getString("CONTACTOR_MOBILE") && !("".equals(potentialCusPo.getString("CONTACTOR_MOBILE")))) {
            lmsMobile=potentialCusPo.getString("CONTACTOR_MOBILE");
        }
        if (!StringUtils.isNullOrEmpty(lmsMobile) || !StringUtils.isNullOrEmpty(lmsPhone)){
            logger.debug("客户信息已经生成，下面去LMS校验线程开始1");
        //lms要的entityCode是DCS的那种六位数字的，要接口传
                 LmsThread  thread = new LmsThread();   
                    thread.setCusNo(customerNo);
                    thread.setEntityCode(loginInfo.getDealerCode());
                    thread.setPhone(lmsPhone);
                    thread.setMobile(lmsMobile);
                    thread.start();
        }
        logger.debug("客户信息已经生成，下面去LMS校验线程结束");
        System.out.println("——————————————————————————————————潜客保存结束——————————————————————————————---");
        System.out.println(potentialCusDto.getPurchaseType());
        //保存意向
        intentpo.setString("CUSTOMER_NO", customerNo);
        intentpo.setLong("INTENT_ID", intentId);
        intentpo.setLong("PURCHASE_TYPE", potentialCusDto.getPurchaseType());
       
        intentpo.setDate("TEST_DRIVE_DATE", potentialCusDto.getTestDriveDate());
        intentpo.setLong("BILL_TYPE", potentialCusDto.getBillType());
        intentpo.setString("DECISION_MAKER",potentialCusDto.getDecisionMaker());
        intentpo.setDouble("BUDGET_AMOUNT",potentialCusDto.getBudgetAmount());
        intentpo.setLong("IS_BUDGET_ENOUGH",potentialCusDto.getIsBudgetEnough());
        intentpo.setLong("OWNED_BY",potentialCusDto.getSoldBy());
        intentpo.saveIt();
        System.out.println("——————————————————————————————————意向保存结束——————————————————————————————---");

        String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_TRACKING_TASK");
        PotentialCusPO cuspo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),customerNo);
        List<Object> taskList = new ArrayList<Object>();
        taskList.add(cuspo.getInteger("INTENT_LEVEL"));
        taskList.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
        taskList.add(DictCodeConstants.IS_YES);
        taskList.add(groupCode);
        List<TrackingTaskPO> pp=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", taskList.toArray());
     
      
        if (potentialCusDto.getIntentList().size() > 0 && potentialCusDto.getIntentList() != null) {
            for (TtCustomerIntentDetailDTO cuintentDto : potentialCusDto.getIntentList()) {
                int a=2;
                //只有一条意向时自动设置为主要车型
                if(potentialCusDto.getIntentList().size()==1){                
                    a=1;
                }
                geCustomerIntent(cuintentDto,intentId,a).saveIt();
            } 
      
        }
        System.out.println("——————————————————————————————————意向明细保存结束——————————————————————————————---");
        if(potentialCusDto.getKeepCarList().size()>0 && potentialCusDto.getKeepCarList() !=null){
            for(TtCustomerVehicleListDTO keepDto : potentialCusDto.getKeepCarList()){
                
                getCustomerKeepCar(keepDto, customerNo).saveIt();
            }
        }
        System.out.println("——————————————————————————————————保有车辆保存结束——————————————————————————————---");
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getContactorName())){
            TtPoCusLinkmanPO linkPO = new TtPoCusLinkmanPO();
            linkPO.setString("CUSTOMER_NO", customerNo);
            linkPO.setString("COMPANY", potentialCusDto.getCompanyName());
            linkPO.setString("CONTACTOR_NAME", potentialCusDto.getContactorName());
            linkPO.setLong("GENDER", potentialCusDto.getGender());
            linkPO.setLong("IS_DEFAULT_CONTACTOR", DictCodeConstants.IS_YES);
            linkPO.setInteger("CONTACTOR_TYPE", 13301003);
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(potentialCusDto.getContactorMobile());
            if(m.matches()){
                linkPO.setString("MOBILE", potentialCusDto.getContactorMobile());// 手机
                linkPO.setString("PHONE", null);// 电话
            }else{
                linkPO.setString("MOBILE", null);// 手机
                linkPO.setString("PHONE", potentialCusDto.getContactorMobile());// 电话 
            }
            linkPO.setString("PHONE", potentialCusDto.getContactorPhone());
            linkPO.setString("MOBILE", potentialCusDto.getContactorMobile());
            linkPO.setString("E_MAIL",potentialCusDto.geteMail());
            linkPO.setString("FAX", potentialCusDto.getFax());
            linkPO.setLong("BEST_CONTACT_TYPE", potentialCusDto.getBestContactType());
            linkPO.setLong("OWNED_BY",loginInfo.getUserId());
            linkPO.setLong("BEST_CONTACT_TIME", potentialCusDto.getBestContactTime());
            linkPO.setString("REMARK", potentialCusDto.getRemark());
            linkPO.saveIt();
        }else{
            TtPoCusLinkmanPO linkPO = new TtPoCusLinkmanPO();
            linkPO.setString("CUSTOMER_NO", customerNo);
            linkPO.setString("COMPANY", potentialCusDto.getCompanyName());
            linkPO.setString("CONTACTOR_NAME", potentialCusDto.getCustomerName());
            linkPO.setLong("GENDER", potentialCusDto.getGender());
            linkPO.setLong("IS_DEFAULT_CONTACTOR", DictCodeConstants.IS_YES);
            linkPO.setInteger("CONTACTOR_TYPE", 13301003);
            Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher m = p.matcher(potentialCusDto.getContactorMobile());
            if(m.matches()){
                linkPO.setString("MOBILE", potentialCusDto.getContactorMobile());// 手机
                linkPO.setString("PHONE", null);// 电话
            }else{
                linkPO.setString("MOBILE", null);// 手机
                linkPO.setString("PHONE", potentialCusDto.getContactorMobile());// 电话 
            }
            linkPO.setString("E_MAIL",potentialCusDto.geteMail());
            linkPO.setString("FAX", potentialCusDto.getFax());
            linkPO.setLong("BEST_CONTACT_TYPE", potentialCusDto.getBestContactType());
            linkPO.setLong("BEST_CONTACT_TIME", potentialCusDto.getBestContactTime());
            linkPO.setString("REMARK", potentialCusDto.getRemark());
            linkPO.setLong("OWNED_BY",loginInfo.getUserId());
            linkPO.saveIt();
        }
        System.out.println("——————————————————————————————————联系人保存结束——————————————————————————————---");
     // 更新展厅接待
        //this.modifyVisitingRecordByid(potentialCusDto,customerNo);
           // 新增跟进任务
       /* TtSalesPromotionPlanPO salesPropo = new TtSalesPromotionPlanPO();*/
        

        if(pp!=null&&pp.size()>0){
            System.out.println("dududdu");
            System.out.println(pp.size());
            for(int p=0;p<pp.size();p++){
                TrackingTaskPO task = pp.get(p);
                String dates = new String();
                Calendar c = Calendar.getInstance();
                TtSalesPromotionPlanPO sPlanPo = new TtSalesPromotionPlanPO();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
              //大客户间隔天数逻辑判定
                if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsBigCustomer())&&potentialCusDto.getIsBigCustomer()==Long.parseLong("12781001")){
                    if(task.getInteger("BIG_CUSTOMER_INTERVAL_DAYS")!=null){
                        c.setTime(new Date());
                        c.add(7, task.getInteger("BIG_CUSTOMER_INTERVAL_DAYS"));
                        dates = format.format(c.getTime()).toString();
                    }
                    sPlanPo.setInteger("IS_BIG_CUSTOMER_PLAN", 12781001);
                }else{
                    if(task.getInteger("INTERVAL_DAYS")!=null){
                        c.setTime(new Date());
                        c.add(7, task.getInteger("INTERVAL_DAYS"));
                        dates = format.format(c.getTime()).toString();
                    }
                    sPlanPo.setInteger("IS_BIG_CUSTOMER_PLAN", 12781002);
                }
                
                sPlanPo.setString("CUSTOMER_NO", customerNo);
                sPlanPo.setLong("INTENT_ID", cuspo.getLong("INTENT_ID"));
                sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
                sPlanPo.setString("CUSTOMER_NAME", potentialCusDto.getCustomerName());
                sPlanPo.setInteger("PRIOR_GRADE", Integer.parseInt(task.getLong("INTENT_LEVEL").toString()));
                try {
                    sPlanPo.setDate("SCHEDULE_DATE", format.parse(dates));
                } catch (ParseException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                sPlanPo.setInteger("PROM_WAY",task.getInteger("EXECUTE_TYPE"));
                sPlanPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                sPlanPo.setString("PROM_CONTENT",task.getString("TASK_CONTENT"));
                sPlanPo.setInteger("PROM_RESULT",null);
                sPlanPo.setString("SCENE",null);
                sPlanPo.setInteger("IS_AUDITING",12781002);
                sPlanPo.setString("SOLD_BY",cuspo.getString("SOLD_BY"));
                sPlanPo.setString("OWNED_BY",cuspo.getString("SOLD_BY"));
                List<Object> link1List = new ArrayList<Object>();
                link1List.add(customerNo);
                link1List.add(Integer.parseInt(DictCodeConstants.D_KEY));
                link1List.add(loginInfo.getDealerCode());
                List<TtPoCusLinkmanPO> linkPO=TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND  D_KEY= ? AND DEALER_CODE= ? ", link1List.toArray());
                if(linkPO!=null&&linkPO.size()>0){
                    TtPoCusLinkmanPO PO = linkPO.get(0);
                    if(!StringUtils.isNullOrEmpty(PO.getString("CONTACTOR_NAME"))){
                        sPlanPo.setString("CONTACTOR_NAME", PO.getString("CONTACTOR_NAME"));
                    }
                    if(!StringUtils.isNullOrEmpty(PO.getString("PHONE"))){
                        sPlanPo.setString("PHONE", PO.getString("PHONE"));
                    }
                    if(!StringUtils.isNullOrEmpty(PO.getString("Mobile"))){
                        sPlanPo.setString("MOBILE", PO.getString("MOBILE"));
                    }
                }
                sPlanPo.saveIt();
            }
        }
        System.out.println("——————————————————————————————————保存结束——————————————————————————————---");
       
        int flag1=sotdcs005.getSOTDCS005(customerNo, "A",keepCarPo, potentialCusDto.getKeepCarList());
        if(flag1==0){
            throw new ServiceBizException("保存失败！");
        }
        int flag2=sotdcs003.getSOTDCS003("A", cuspo.getString("CONTACTOR_PHONE"), cuspo.getString("CONTACTOR_MOBILE"));
        if(flag2==0){
            throw new ServiceBizException("保存失败！");
        }
        int flag3 = SADMS049.getSADMS049(keepCarPo, "A", customerNo);
        if(flag3==0){
            throw new ServiceBizException("二手车置换意向明细上报失败！");
        }
        logger.info("============A+++++++++++");
        System.out.println("============A+++++++++++");
       //sotdcs008.getSOTDCS008("U", customerNo);
        return customerNo;
    }
    /**
     * 潜客信息修改
     * 
     * @author zhanshiwei
     * @date 2016年9月10日
     * @param id
     * @param potentialCusDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#modifyCustomerTrackingInfo(java.lang.Long,
     * com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCusDTO)
     */

    @Override
    public void modifyPotentialCusInfo(String id, PotentialCusDTO potentialCusDto) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        System.out.println(loginInfo.getDealerCode());
        System.out.println(id);
        //更新保有车辆
        List<Object> keepList = new ArrayList<Object>();
        keepList.add(id);
        keepList.add(FrameworkUtil.getLoginInfo().getDealerCode());
        List<TtCustomerVehicleListPO> keepCarPo = TtCustomerVehicleListPO.findBySQL("select * from TT_CUSTOMER_VEHICLE_LIST where CUSTOMER_NO= ? AND DEALER_CODE= ? ", keepList.toArray());
        
        String level="";
        String strIsBigCustomer="";
        PotentialCusPO potentialCusPo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),id);
        if(!StringUtils.isNullOrEmpty(potentialCusPo.getString("INTENT_LEVEL"))){
            level = potentialCusPo.getInteger("INTENT_LEVEL").toString();
        }
        if(!StringUtils.isNullOrEmpty(potentialCusPo.getString("IS_BIG_CUSTOMER"))){
            strIsBigCustomer=potentialCusPo.getInteger("IS_BIG_CUSTOMER").toString();
        }
        //PotentialCusPO potentialCusPo = PotentialCusPO.findById(id);
        if (!StringUtils.isEquals(potentialCusDto.getContactorMobile(), potentialCusPo.getString("CONTACTOR_MOBILE"))) {
            // 如果修改了手机号，判断除当前潜客外有无重复，如果重复不允许保存。
            this.queryMobileIsExists(potentialCusDto.getContactorMobile(), id);
            //更新展厅接待
            this.modifyVisitingRecordByid(potentialCusDto,potentialCusPo.getString("CUSTOMER_NO"));
        }
        // 1. 取Min(来访时间)且是否首次到店为是的展厅记录中的来访时间作为客户首次到店时间
        // 2. 取Min(来访时间)且是否首次到店为是，是否二次到店为是的展厅记录中的来访时间作为客户二次到店时间。
        /*this.setFirstArriveTime(potentialCusDto.getContactorMobile(), potentialCusPo);
        this.setSecondArriveTime(potentialCusDto.getContactorMobile(), potentialCusPo);*/
        potentialCusDto.setCustomerNo(potentialCusPo.getString("CUSTOMER_NO"));
        this.setPotentialCus(potentialCusPo, potentialCusDto);
        if(potentialCusDto.getIntentLevel()==Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_FO)||potentialCusDto.getIntentLevel()==Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_F)){
            if(potentialCusPo.getLong("INTENT_LEVEL")!=Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_FO)||potentialCusPo.getLong("INTENT_LEVEL")!=Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_F)){
                potentialCusPo.setLong("INTENT_LEVEL", potentialCusPo.getLong("INTENT_LEVEL"));
            } 
        }
        if(!StringUtils.isNullOrEmpty(potentialCusPo.getLong("INTENT_LEVEL"))&&potentialCusDto.getIntentLevel()!=potentialCusPo.getLong("INTENT_LEVEL")){
            potentialCusPo.setDate("DDCN_UPDATE_DATE", new Date());
        }
        if(!StringUtils.isNullOrEmpty(potentialCusPo.getLong("REBUY_CUSTOMER_TYPE"))&&potentialCusPo.getLong("REBUY_CUSTOMER_TYPE")!=Long.parseLong(DictCodeConstants.DICT_CUS_TYPE_REPLACE)){
            if(potentialCusDto.getIsFirstBuy()==Long.parseLong(DictCodeConstants.DICT_IS_NO)&&potentialCusDto.getRebuyCustomerType()==Long.parseLong(DictCodeConstants.DICT_CUS_TYPE_REPLACE)){
                potentialCusPo.setDate("REPLACE_DATE", new Date());
            }else{
                potentialCusPo.setDate("REPLACE_DATE", null);
            }
        }else{
            if(potentialCusPo.getLong("REBUY_CUSTOMER_TYPE")==Long.parseLong(DictCodeConstants.DICT_CUS_TYPE_REPLACE)){
               if(potentialCusDto.getRebuyCustomerType()==Long.parseLong(DictCodeConstants.DICT_CUS_TYPE_REPLACE)){
                   if(!StringUtils.isNullOrEmpty(potentialCusPo.getDate("REPLACE_DATE"))){
                       potentialCusPo.setDate("REPLACE_DATE", potentialCusPo.getDate("REPLACE_DATE"));
                   }else{
                       potentialCusPo.setDate("REPLACE_DATE", new Date()); 
                   }
                   
               }else{
                   potentialCusPo.setDate("REPLACE_DATE", null);
               }
            }else{
                potentialCusPo.setDate("REPLACE_DATE", null);
            }
        }
        if(StringUtils.isNullOrEmpty(potentialCusPo.getLong("REBUY_CUSTOMER_TYPE"))){
            if(potentialCusDto.getIsFirstBuy()==Long.parseLong(DictCodeConstants.DICT_IS_NO)&&potentialCusDto.getRebuyCustomerType()==Long.parseLong(DictCodeConstants.DICT_CUS_TYPE_REPLACE)){
                potentialCusPo.setDate("REPLACE_DATE", new Date());
            }else{
                potentialCusPo.setDate("REPLACE_DATE", null);
            }
        }
        if(!StringUtils.isNullOrEmpty(potentialCusPo.getDate("TIME_TO_SHOP"))){
            potentialCusPo.setDate("TIME_TO_SHOP", potentialCusPo.getDate("TIME_TO_SHOP"));
        }
        potentialCusPo.saveIt();
       
        //更新联系人
        List<Object> linkList = new ArrayList<Object>();
        linkList.add(id);
        linkList.add(loginInfo.getDealerCode());
        List<TtPoCusLinkmanPO> liknPo = TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND DEALER_CODE= ? ", linkList.toArray());
        // 删除原联系人
        if(liknPo!=null&&liknPo.size()>0){
            for(int i=0;i<liknPo.size();i++){
                
                TtPoCusLinkmanPO linkman = liknPo.get(i);
                linkman.delete();
            }
        }
        //增加新联系人
 
            TtPoCusLinkmanPO linkPO1 = new TtPoCusLinkmanPO();
            linkPO1.setString("CUSTOMER_NO", id);
            linkPO1.setString("COMPANY", potentialCusDto.getCompanyName());
            linkPO1.setString("CONTACTOR_NAME", potentialCusDto.getCustomerName());
            linkPO1.setLong("GENDER", potentialCusDto.getGender());
            linkPO1.setLong("IS_DEFAULT_CONTACTOR", DictCodeConstants.IS_YES);
            Pattern ppp = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
            Matcher mm = ppp.matcher(potentialCusDto.getContactorMobile());
            if(mm.matches()){
                linkPO1.setString("MOBILE", potentialCusDto.getContactorMobile());// 手机
                linkPO1.setString("PHONE", null);// 电话
            }else{
                linkPO1.setString("MOBILE", null);// 手机
                linkPO1.setString("PHONE", potentialCusDto.getContactorMobile());// 电话 
            }
            linkPO1.setString("E_MAIL",potentialCusDto.geteMail());
            linkPO1.setString("FAX", potentialCusDto.getFax());
            linkPO1.setLong("BEST_CONTACT_TYPE", potentialCusDto.getBestContactType());
            linkPO1.setLong("BEST_CONTACT_TIME", potentialCusDto.getBestContactTime());
            linkPO1.setString("REMARK", potentialCusDto.getRemark());
            linkPO1.saveIt();
        
      //更新意向
        List<Object> intentList = new ArrayList<Object>();
        intentList.add(potentialCusPo.getLong("INTENT_ID"));
        intentList.add(loginInfo.getDealerCode());
        intentList.add(id);
        List<TtCusIntentPO> intentlPo = TtCusIntentPO.findBySQL("select * from TT_CUSTOMER_INTENT where INTENT_ID= ? AND DEALER_CODE= ? AND CUSTOMER_NO= ?", intentList.toArray());
        if(intentlPo!=null&&intentlPo.size()>0){
            TtCusIntentPO intent = intentlPo.get(0);
            intent.setLong("PURCHASE_TYPE", potentialCusDto.getPurchaseType());
            intent.setLong("IS_TEST_DRIVE", potentialCusDto.getIsTestDrive());
            intent.setDate("TEST_DRIVE_DATE", potentialCusDto.getTestDriveDate());
            intent.setLong("BILL_TYPE", potentialCusDto.getBillType());
            intent.setString("DECISION_MAKER",potentialCusDto.getDecisionMaker());
            intent.setDouble("BUDGET_AMOUNT",potentialCusDto.getBudgetAmount());
            intent.setLong("IS_BUDGET_ENOUGH",potentialCusDto.getIsBudgetEnough());
            intent.setLong("OWNED_BY",potentialCusDto.getSoldBy());
            intent.saveIt();
        }

        
        System.out.println("——————————————————————————————————联系人保存结束——————————————————————————————---");
        //更新意向明细
        List<Object> intentDetailList = new ArrayList<Object>();
        intentDetailList.add(potentialCusPo.getLong("INTENT_ID"));
        intentDetailList.add(loginInfo.getDealerCode());
        List<TtCustomerIntentDetailPO> intentDetailPo = TtCustomerIntentDetailPO.findBySQL("select * from TT_CUSTOMER_INTENT_DETAIL where INTENT_ID= ? AND DEALER_CODE= ? ", intentDetailList.toArray());
        //删除已经有的意向明细
        if(intentDetailPo!=null&&intentDetailPo.size()>0){
            for(int i = 0; i<intentDetailPo.size();i++){
                TtCustomerIntentDetailPO intentDetail = intentDetailPo.get(i);
                intentDetail.delete();
            }
        }
        //新增意向
        if (potentialCusDto.getIntentList().size() > 0 && potentialCusDto.getIntentList() != null) {
            for (TtCustomerIntentDetailDTO cuintentDto : potentialCusDto.getIntentList()) {
                int a=2;
                //只有一条意向时自动设置为主要车型
                if(potentialCusDto.getIntentList().size()==1){                
                    a=1;
                }
                geCustomerIntent(cuintentDto,potentialCusPo.getLong("INTENT_ID"),a).saveIt();
            }
        }

        //新增保有车辆
        if(potentialCusDto.getKeepCarList().size()>0 && potentialCusDto.getKeepCarList() !=null){
            for(TtCustomerVehicleListDTO keepDto : potentialCusDto.getKeepCarList()){
                getCustomerKeepCar(keepDto, id).saveIt();
            }
        }
     // 如果跟进活动里有相应已经生成的记录 则修改相应的客户信息
        List<Object> salesPromotionList = new ArrayList<Object>();
        salesPromotionList.add(id);
        salesPromotionList.add(loginInfo.getDealerCode());          
        List<TtSalesPromotionPlanPO> salesPromotionPO=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND DEALER_CODE= ?", salesPromotionList.toArray());
        if(salesPromotionPO!=null&&salesPromotionPO.size()>0){
            for(int j=0;j<salesPromotionPO.size();j++){
                TtSalesPromotionPlanPO salesPromotion = salesPromotionPO.get(j);
                salesPromotion.setString("MOBILE", potentialCusDto.getContactorMobile());
                salesPromotion.setString("SOLD_BY", potentialCusDto.getSoldBy().toString());
                salesPromotion.setString("OWNED_BY", potentialCusDto.getSoldBy().toString());
                salesPromotion.setString("CONTACTOR_NAME", potentialCusDto.getContactorName());
                salesPromotion.saveIt();
            }
        }
        
     // 客户信息改变 订单表里的客户信息要相应的 改变
        List<Object> salesOrderList = new ArrayList<Object>();
        salesOrderList.add(id);
        salesOrderList.add(loginInfo.getDealerCode());
        List<SalesOrderPO> saleOrderPo = SalesOrderPO.findBySQL("select * from tt_sales_order where CUSTOMER_NO= ? AND DEALER_CODE= ? ", salesOrderList.toArray());
        if(saleOrderPo!=null&&saleOrderPo.size()>0){
            for(int o=0;o<saleOrderPo.size();o++){
                SalesOrderPO salesPo = saleOrderPo.get(o);
                salesPo.setString("CERTIFICATE_NO", potentialCusDto.getCertificateNo());
                salesPo.setString("CUSTOMER_NAME", potentialCusDto.getCustomerName());
                salesPo.setInteger("CUSTOMER_TYPE", potentialCusDto.getCustomerType());
                salesPo.setInteger("CT_CODE", potentialCusDto.getCtCode());
                salesPo.setString("CONTACTOR_NAME", potentialCusDto.getContactorName());
                salesPo.setString("MOBILE", potentialCusDto.getContactorMobile());
                salesPo.saveIt();
            }
        }
      //更新潜客联系人时，如果该潜客做过大客户报备 则报备表中的联系人信息要和潜客的默认联系人保持一致
        List<Object> sWholesaleList = new ArrayList<Object>();
        sWholesaleList.add(id);
        sWholesaleList.add(loginInfo.getDealerCode());
        List<PoCusWholesalePO> sWholesalerPo = PoCusWholesalePO.findBySQL("select * from tt_po_cus_wholesale where CUSTOMER_NO= ? AND DEALER_CODE= ? ", sWholesaleList.toArray());
        if(sWholesalerPo!=null&&sWholesalerPo.size()>0){
            for(int s=0;s<sWholesalerPo.size();s++){
                PoCusWholesalePO  sWholePO =sWholesalerPo.get(s);
                sWholePO.setString("CUSTOMER_NAME", potentialCusDto.getCustomerName());
                sWholePO.setString("CONTACTOR_NAME", potentialCusDto.getContactorName());
                sWholePO.setString("MOBILE", potentialCusDto.getContactorMobile());
                sWholePO.saveIt();
            }
        }
     // add by jll 2011-12-05 DMS客户信息变更同步会员变更
        if(!StringUtils.isNullOrEmpty(commonNoService.getDefalutPara("1149"))&&commonNoService.getDefalutPara("1149").equals("12781001")){
            List<Object> memberList = new ArrayList<Object>();
            memberList.add(id);
            memberList.add(loginInfo.getDealerCode());
            List<TmMemberPO> memberPo = TmMemberPO.findBySQL("select * from tm_member where CUSTOMER_NO= ? AND DEALER_CODE= ? ", sWholesaleList.toArray());
            if(memberPo!=null&&memberPo.size()>0){
                for(int m=0;m<memberPo.size();m++){
                    TmMemberPO member = memberPo.get(m);
                    member.setString("CONTACTOR_NAME", potentialCusDto.getContactorName());
                    member.setString("CONTACTOR_MOBILE", potentialCusDto.getContactorMobile());
                    member.setString("CUSTOMER_NAME", potentialCusDto.getCustomerName());
                    member.setString("ADDRESS", potentialCusDto.getAddress());
                    member.setInteger("GENDER", potentialCusDto.getGender());
                    member.setString("ZIP_CODE", potentialCusDto.getZipCode());
                    member.setDate("BIRTHDAY", potentialCusDto.getBirthday());
                    member.setString("PROVINCE", potentialCusDto.getProvince());
                    member.setString("CITY", potentialCusDto.getCity());
                    member.setString("DISTRICT", potentialCusDto.getDistrict());
                    member.saveIt();
                }
            }
        }
        System.out.println();
/*        if(!level.equals(potentialCusDto.getIntentLevel())){
            System.out.println("在意向级别这里");
            String groupCode = Utility.getGroupEntity(loginInfo.getDealerCode(), "TM_TRACKING_TASK");
            PotentialCusPO cuspo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),id);
            List<Object> taskList = new ArrayList<Object>();
            taskList.add(cuspo.getInteger("INTENT_LEVEL"));
            taskList.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
            taskList.add(DictCodeConstants.IS_YES);
            taskList.add(groupCode);
            List<TrackingTaskPO> pp=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", taskList.toArray());
            if(pp!=null&&pp.size()>0){
                for(int p=0;p<pp.size();p++){
                    TrackingTaskPO task = pp.get(p);
                    String dates = new String();
                    Calendar c = Calendar.getInstance();
                    TtSalesPromotionPlanPO sPlanPo = new TtSalesPromotionPlanPO();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                  //大客户间隔天数逻辑判定
                    if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsBigCustomer())&&potentialCusDto.getIsBigCustomer()==Long.parseLong("12781001")){
                        if(task.getInteger("BIG_CUSTOMER_INTERVAL_DAYS")!=null){
                            c.setTime(new Date());
                            c.add(7, task.getInteger("BIG_CUSTOMER_INTERVAL_DAYS"));
                            dates = format.format(c.getTime()).toString();
                        }
                        sPlanPo.setInteger("IS_BIG_CUSTOMER_PLAN", 12781001);
                    }else{
                        if(task.getInteger("INTERVAL_DAYS")!=null){
                            c.setTime(new Date());
                            c.add(7, task.getInteger("INTERVAL_DAYS"));
                            dates = format.format(c.getTime()).toString();
                        }
                        sPlanPo.setInteger("IS_BIG_CUSTOMER_PLAN", 12781002);
                    }
                    
                    sPlanPo.setString("CUSTOMER_NO", id);
                    sPlanPo.setLong("INTENT_ID", cuspo.getLong("INTENT_ID"));
                    sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
                    sPlanPo.setString("CUSTOMER_NAME", potentialCusDto.getCustomerName());
                    sPlanPo.setInteger("PRIOR_GRADE", Integer.parseInt(task.getLong("INTENT_LEVEL").toString()));
                    try {
                        sPlanPo.setDate("SCHEDULE_DATE", format.parse(dates));
                    } catch (ParseException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    sPlanPo.setInteger("PROM_WAY",task.getInteger("EXECUTE_TYPE"));
                    sPlanPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                    sPlanPo.setString("PROM_CONTENT",task.getString("TASK_CONTENT"));
                    sPlanPo.setInteger("PROM_RESULT",null);
                    sPlanPo.setString("SCENE",null);
                    sPlanPo.setInteger("IS_AUDITING",12781002);
                    sPlanPo.setString("SOLD_BY",cuspo.getString("SOLD_BY"));
                    sPlanPo.setString("OWNED_BY",cuspo.getString("SOLD_BY"));
                    List<Object> link1List = new ArrayList<Object>();
                    link1List.add(id);
                    link1List.add(Integer.parseInt(DictCodeConstants.D_KEY));
                    link1List.add(loginInfo.getDealerCode());
                    List<TtPoCusLinkmanPO> linkPO=TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND  D_KEY= ? AND DEALER_CODE= ? ", link1List.toArray());
                    if(linkPO!=null&&linkPO.size()>0){
                        TtPoCusLinkmanPO PO = linkPO.get(0);
                        if(!StringUtils.isNullOrEmpty(PO.getString("CONTACTOR_NAME"))){
                            sPlanPo.setString("CONTACTOR_NAME", PO.getString("CONTACTOR_NAME"));
                        }
                        if(!StringUtils.isNullOrEmpty(PO.getString("PHONE"))){
                            sPlanPo.setString("PHONE", PO.getString("PHONE"));
                        }
                        if(!StringUtils.isNullOrEmpty(PO.getString("Mobile"))){
                            sPlanPo.setString("MOBILE", PO.getString("MOBILE"));
                        }
                    }
                    sPlanPo.saveIt();
                }
            }
        }*/
      //在维护客户信息时，选择是否大客户为是时，将该客户所有未跟进的记录更新成已跟进，级别保持客户当前级别不变 
        if(!StringUtils.isNullOrEmpty(strIsBigCustomer)){
            if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsBigCustomer())&&potentialCusDto.getIsBigCustomer()==Long.parseLong("12781001")&&strIsBigCustomer.equals("12781002")){
                System.out.println("在大客户这里");
                PotentialCusPO cus2po = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),id);
                List<Object> PromotionPlanList = new ArrayList<Object>();
                PromotionPlanList.add(id);
                PromotionPlanList.add(loginInfo.getDealerCode());
                List<TtSalesPromotionPlanPO> PromotionPlanPo = TtSalesPromotionPlanPO.findBySQL("select * from tt_sales_promotion_plan where CUSTOMER_NO= ? AND DEALER_CODE= ? AND (PROM_RESULT IS NULL OR PROM_RESULT = 0) ", PromotionPlanList.toArray());
                if(PromotionPlanPo!=null&&PromotionPlanPo.size()>0){
                    for(int q=0;q<PromotionPlanPo.size();q++){
                        TtSalesPromotionPlanPO PromotionPlan = PromotionPlanPo.get(q);
                        PromotionPlan.setInteger("NEXT_GRADE", cus2po.getInteger("INTENT_LEVEL"));
                        PromotionPlan.setInteger("PROM_RESULT", Integer.parseInt(DictCodeConstants.DICT_PROM_RESULT_CONTINUE));
                        PromotionPlan.setInteger("PROM_RESULT", Integer.parseInt(DictCodeConstants.DICT_PROM_WAY_VISIT));
                        PromotionPlan.setDate("ACTION_DATE", new Date());
                        PromotionPlan.setString("SCENE","大客户拜访跟进");
                        PromotionPlan.saveIt();
                    }
                }
              //在维护客户信息时，选择是否大客户为是时，通过跟进任务定义中的大客户拜访间隔天数自动生成一条待跟进的计划  
                List<Object> taskList = new ArrayList<Object>();
                taskList.add(cus2po.getInteger("INTENT_LEVEL"));
                taskList.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
                taskList.add(DictCodeConstants.IS_YES);
                taskList.add(loginInfo.getDealerCode());
                List<TrackingTaskPO> pp=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", taskList.toArray());
                if(pp!=null&&pp.size()>0){
                    for(int p=0;p<pp.size();p++){
                        TrackingTaskPO task = pp.get(p);
                        String dates = new String();
                        Calendar c = Calendar.getInstance();
                        TtSalesPromotionPlanPO sPlanPo = new TtSalesPromotionPlanPO();
                        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                      //大客户间隔天数逻辑判定
                        if(task.getInteger("BIG_CUSTOMER_INTERVAL_DAYS")!=null){
                                c.setTime(new Date());
                                c.add(7, task.getInteger("BIG_CUSTOMER_INTERVAL_DAYS"));
                                dates = format.format(c.getTime()).toString();
                            }
                            sPlanPo.setInteger("IS_BIG_CUSTOMER_PLAN", 12781001);
                        
                        
                        sPlanPo.setString("CUSTOMER_NO", id);
                        sPlanPo.setLong("INTENT_ID", cus2po.getLong("INTENT_ID"));
                        sPlanPo.setLong("TASK_ID", task.getLong("TASK_ID"));
                        sPlanPo.setString("CUSTOMER_NAME", potentialCusDto.getCustomerName());
                        sPlanPo.setInteger("PRIOR_GRADE", Integer.parseInt(task.getLong("INTENT_LEVEL").toString()));
                        try {
                            sPlanPo.setDate("SCHEDULE_DATE", format.parse(dates));
                        } catch (ParseException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                        sPlanPo.setInteger("PROM_WAY",task.getInteger("EXECUTE_TYPE"));
                        sPlanPo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                        sPlanPo.setString("PROM_CONTENT",task.getString("TASK_CONTENT"));
                        sPlanPo.setInteger("PROM_RESULT",null);
                        sPlanPo.setString("SCENE",null);
                        sPlanPo.setInteger("IS_AUDITING",12781002);
                        sPlanPo.setString("SOLD_BY",cus2po.getString("SOLD_BY"));
                        sPlanPo.setString("OWNED_BY",cus2po.getString("SOLD_BY"));
                        List<Object> link1List = new ArrayList<Object>();
                        link1List.add(id);
                        link1List.add(Integer.parseInt(DictCodeConstants.D_KEY));
                        link1List.add(loginInfo.getDealerCode());
                        List<TtPoCusLinkmanPO> linkPO=TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND  D_KEY= ? AND DEALER_CODE= ? ", link1List.toArray());
                        if(linkPO!=null&&linkPO.size()>0){
                            TtPoCusLinkmanPO PO = linkPO.get(0);
                            if(!StringUtils.isNullOrEmpty(PO.getString("CONTACTOR_NAME"))){
                                sPlanPo.setString("CONTACTOR_NAME", PO.getString("CONTACTOR_NAME"));
                            }
                            if(!StringUtils.isNullOrEmpty(PO.getString("PHONE"))){
                                sPlanPo.setString("PHONE", PO.getString("PHONE"));
                            }
                            if(!StringUtils.isNullOrEmpty(PO.getString("Mobile"))){
                                sPlanPo.setString("MOBILE", PO.getString("MOBILE"));
                            }
                        }
                        sPlanPo.saveIt();
                    }
                }
            }
        }
       int flag1=sotdcs005.getSOTDCS005(id, "U",keepCarPo, potentialCusDto.getKeepCarList());
       if(flag1==0){
           throw new ServiceBizException("更新失败！");
       }
       int flag2=sotdcs008.getSOTDCS008("U", id);
       if(flag2==0){
           throw new ServiceBizException("更新失败！");
       }
       int flag3 = SADMS049.getSADMS049(keepCarPo, "U", id);
       if(flag3==0){
           throw new ServiceBizException("二手车置换意向明细上报失败！");
       } 
    }
    /**
     * 再分配
     * 
     * @author LGQ
     * @date 2016年12月29日
     * @param potentialCusPo
     */
    @Override
    public void modifySoldBy(PotentialCusDTO potentialCusDto) throws ServiceBizException {
        String[] ids = potentialCusDto.getNoList().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        for (int i = 0; i < ids.length; i++) {
            String no = ids[i];
           
            PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),no);
           // CustomerTrackingPO traPo = CustomerTrackingPO.findById(id);
            //跟进活动记录做相应修改
            List<Object> salesPromotionList = new ArrayList<Object>();
            salesPromotionList.add(no);
            salesPromotionList.add(DictCodeConstants.D_KEY);
            salesPromotionList.add(loginInfo.getDealerCode());          
            List<TtSalesPromotionPlanPO> salesPromotionPO=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND D_KEY= ? AND DEALER_CODE= ? AND (SOLD_BY IS NULL OR SOLd_BY=0)", salesPromotionList.toArray());
            if(salesPromotionPO!=null&&salesPromotionPO.size()>0){
                for(int j=0;j<salesPromotionPO.size();j++){
                    TtSalesPromotionPlanPO planPO = salesPromotionPO.get(j);
                    planPO.setString("SOLD_BY", potentialCusDto.getSoldBy().toString());
                    planPO.setString("OWNED_BY", potentialCusDto.getSoldBy().toString());
                    planPO.saveIt();
                }
            }
            List<Object> salesPromotionList2 = new ArrayList<Object>();
            salesPromotionList2.add(no);
            salesPromotionList2.add(DictCodeConstants.D_KEY);
            salesPromotionList2.add(loginInfo.getDealerCode());          
            List<TtSalesPromotionPlanPO> salesPromotionPO2=TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where  CUSTOMER_NO= ? AND D_KEY= ? AND DEALER_CODE= ? AND DATE(SCHEDULE_DATE) >= CURRENT_DATE AND (PROM_RESULT IS NULL OR PROM_RESULT = 0 OR PROM_RESULT ='13341009') ", salesPromotionList2.toArray());
            if(salesPromotionPO2!=null&&salesPromotionPO2.size()>0){
               for(int p=0;p<salesPromotionPO2.size();p++){
                   TtSalesPromotionPlanPO planPO2 = salesPromotionPO2.get(p);
                   planPO2.setString("SOLD_BY", potentialCusDto.getSoldBy().toString());
                   planPO2.setString("OWNED_BY", potentialCusDto.getSoldBy().toString());
                   planPO2.saveIt();
               }
            }
         // D级潜在客户使用再分配功能将同时分配潜客相同的保有客户
            if(!StringUtils.isNullOrEmpty(potentialCusPo.getInteger("INTENT_LEVEL"))&&potentialCusPo.getInteger("INTENT_LEVEL")==Integer.parseInt(DictCodeConstants.DICT_INTENT_LEVEL_D)){
       /*         List<Object> customerList = new ArrayList<Object>();
                customerList.add(no);
                customerList.add(DictCodeConstants.D_KEY);
                customerList.add(loginInfo.getDealerCode());          
                List<CustomerPO> customerPO=CustomerPO.findBySQL("select * from TM_CUSTOMER where  CUSTOMER_NO= ? AND D_KEY= ? AND DEALER_CODE= ? AND (SOLD_BY IS NULL OR SOLd_BY=0)", customerList.toArray());
                */
                List<Map> result = this.queryCusNoByPoCusNo(no, loginInfo.getDealerCode());
                if(result!=null&&result.size()>0){
                    String customerNO = result.get(0).get("CUSTOMER_NO").toString();
                    String soldBy =  result.get(0).get("SOLD_BY").toString();
                    CustomerPO cusmpo = CustomerPO.findByCompositeKeys(loginInfo.getDealerCode(),customerNO);
                    cusmpo.setString("LAST_SOLD_BY", soldBy);
                    cusmpo.setString("SOLD_BY", potentialCusDto.getSoldBy().toString());
                    cusmpo.setString("OWNED_BY", potentialCusDto.getSoldBy().toString());
                    cusmpo.saveIt();
                 // CR关怀记录做相应修改
                    List<Object> salesCrList = new ArrayList<Object>();
                    salesCrList.add(customerNO);
                    salesCrList.add(DictCodeConstants.D_KEY);
                    salesCrList.add(loginInfo.getDealerCode());          
                    List<TtSalesCrPO> crPO=TtSalesCrPO.findBySQL("select * from tt_sales_cr where  CUSTOMER_NO= ? AND D_KEY= ? AND DEALER_CODE= ? AND DATE(SCHEDULE_DATE) >= CURRENT_DATE AND (CR_RESULT IS NULL OR CR_RESULT = 0)", salesCrList.toArray());
                    if(crPO!=null&&crPO.size()>0){
                        for(int c=0;c<crPO.size();c++){
                            TtSalesCrPO cr = crPO.get(c);
                            cr.setString("SOLD_BY", potentialCusDto.getSoldBy().toString());
                            cr.setString("OWNED_BY", potentialCusDto.getSoldBy().toString());
                            cr.saveIt();
                        }
                    }
                }

            }
         // 更新潜在客户分配前的销售顾问
            if(!StringUtils.isNullOrEmpty(potentialCusPo.getDate("DCC_DATE"))&&!StringUtils.isNullOrEmpty(potentialCusPo.getString("LAST_SOLD_BY"))){
                if(potentialCusPo.getString("LAST_SOLD_BY").equals(potentialCusDto.getSoldBy().toString())){
                    throw new ServiceBizException(no+"是培育后客户,不能分配给原销售顾问!");
                }
            }
            potentialCusPo.setString("LAST_SOLD_BY", potentialCusPo.getString("SOLD_BY"));
            potentialCusPo.setLong("SOLD_BY", potentialCusDto.getSoldBy());// 销售顾问
            potentialCusPo.setLong("OWNED_BY", potentialCusDto.getSoldBy());// 销售顾问
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            potentialCusPo.setString("CONSULTANT_TIME", format.format(new Date()));
            potentialCusPo.saveIt();
        }   
        logger.info("============开始======================");
        sotdcs013.getSOTDCS013(ids);
    }
    
    @Override
    public void mainCustomerUnite(PotentialCusDTO potentialCusDto) throws ServiceBizException {
        String[] customerNos = potentialCusDto.getMainCusList().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer operateContent = new StringBuffer();//日志内容
        System.out.println("主要客户");
        System.out.println(potentialCusDto.getMainCus());
        String mergerCusNo = null;
        String mergerCusNoMain = null;
        for(int i = 0; i < customerNos.length; i++){
            String no = customerNos[i];
            List<Object> orderList = new ArrayList<Object>();
            orderList.add(no);
            orderList.add(loginInfo.getDealerCode());
            List<SalesOrderPO> salePo = SalesOrderPO.findBySQL("select * from tt_sales_order where CUSTOMER_NO= ? AND DEALER_CODE= ? ", orderList.toArray());
           System.out.println(salePo);
            if(!StringUtils.isNullOrEmpty(salePo)&&salePo.size()>0&&!potentialCusDto.getMainCus().equals(no)){
                throw new ServiceBizException("客户编号为"+no+"的客户存在订单,不能进行合并!");
            }
            System.out.println("将非主要客户编号所对应的联系人表的客户编号改为主要客户的客户编号");
            if(!potentialCusDto.getMainCus().equals(no)){
              //将非主要客户编号所对应的联系人表的客户编号改为主要客户的客户编号
                List<Object> linkList = new ArrayList<Object>();
                linkList.add(no);
                linkList.add(loginInfo.getDealerCode());
                List<TtPoCusLinkmanPO> liknPo = TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO= ? AND DEALER_CODE= ? ", linkList.toArray());
                if(liknPo!=null&&liknPo.size()>0){
                    for(int j=0;j<liknPo.size();j++){
                        TtPoCusLinkmanPO linkman = liknPo.get(j);
                        linkman.setString("CUSTOMER_NO", potentialCusDto.getMainCus());
                        linkman.setLong("IS_DEFAULT_CONTACTOR", DictCodeConstants.IS_NOT);
                        linkman.setLong("OWNED_BY", loginInfo.getUserId());
                        linkman.saveIt();
                        System.out.println("将非主要客户编号所对应的联系人表的客户编号改为主要客户的客户编号");
                    }
                }
                System.out.println("将非主要客户编号所对应的 意向表 的客户编号改为主要客户的客户编号");
              //将非主要客户编号所对应的 意向表 的客户编号改为主要客户的客户编号
                List<Object> intentList = new ArrayList<Object>();
                intentList.add(no);
                intentList.add(loginInfo.getDealerCode());
                intentList.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<TtCusIntentPO> intentPo = TtCusIntentPO.findBySQL("select * from TT_CUSTOMER_INTENT where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", intentList.toArray());
                if(intentPo!=null&&intentPo.size()>0){
                    for(int k=0;k<intentPo.size();k++){
                        TtCusIntentPO intent = intentPo.get(k);
                        intent.setString("CUSTOMER_NO", potentialCusDto.getMainCus());      
                        intent.setLong("OWNED_BY", loginInfo.getUserId());
                        intent.saveIt();
                        System.out.println("将非主要客户编号所对应的 意向表 的客户编号改为主要客户的客户编号");
                    }
                 
                }
                System.out.println("将非主要客户编号所对应的 保有车辆表 的客户编号改为主要客户的客户编号");
              //将非主要客户编号所对应的 保有车辆表 的客户编号改为主要客户的客户编号
                List<Object> keepList = new ArrayList<Object>();
                keepList.add(no);
                keepList.add(loginInfo.getDealerCode());
                keepList.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<TtCustomerVehicleListPO> keepCarPo = TtCustomerVehicleListPO.findBySQL("select * from TT_CUSTOMER_VEHICLE_LIST where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", keepList.toArray());
                if(keepCarPo!=null&&keepCarPo.size()>0){
                    for(int m=0;m<keepCarPo.size();m++){
                        TtCustomerVehicleListPO keepcar = keepCarPo.get(m);
                        keepcar.setString("CUSTOMER_NO", potentialCusDto.getMainCus());      
                        keepcar.setLong("OWNED_BY", loginInfo.getUserId());
                        keepcar.saveIt();
                        System.out.println("将非主要客户编号所对应的 保有车辆表 的客户编号改为主要客户的客户编号");
                    }
                }
                System.out.println("将非主要客户编号所对应的 预约试乘试驾 的客户编号改为主要客户的客户编号");
              //将非主要客户编号所对应的 预约试乘试驾 的客户编号改为主要客户的客户编号
                List<Object> testdriveList = new ArrayList<Object>();
                testdriveList.add(no);
                testdriveList.add(loginInfo.getDealerCode());
                testdriveList.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<TtTestdrivePlanPO> testdrivePo = TtTestdrivePlanPO.findBySQL("select * from TT_TESTDRIVE_PLAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", testdriveList.toArray());
                if(testdrivePo!=null&&testdrivePo.size()>0){
                    for(int m=0;m<testdrivePo.size();m++){
                        TtTestdrivePlanPO testdrive = testdrivePo.get(m);
                        testdrive.setString("CUSTOMER_NO", potentialCusDto.getMainCus());      
                        testdrive.setLong("OWNED_BY", loginInfo.getUserId());
                        testdrive.saveIt();
                        System.out.println("将非主要客户编号所对应的 预约试乘试驾 的客户编号改为主要客户的客户编号");
                        
                    }
                }
              //查询非主要客户的信息
                String megreCustomerNo = null ;//被合并客户编号
                String megreOemCustomerNo = "";//被合并上端客户编号
                List<Object> cusList = new ArrayList<Object>();
                cusList.add(no);
                cusList.add(loginInfo.getDealerCode());
                cusList.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<PotentialCusPO> cusPo = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", cusList.toArray());
                if(cusPo!=null&&cusPo.size()>0){
                    PotentialCusPO cus = cusPo.get(0);
                    String meCusNo = cus.getString("MERGE_CUSTOMER_NO");
                    if(meCusNo!=null && !"".equals(meCusNo))
                        megreCustomerNo += meCusNo+",";
                    String oemCusNo = cus.getString("OEM_CUSTOMER_NO");
                    if(oemCusNo!=null && !"".equals(oemCusNo))
                        megreOemCustomerNo += oemCusNo + ",";
                    String meOemCusNo = cus.getString("MERGE_OEM_CUSTOMER_NO");
                    if(meOemCusNo!=null && !"".equals(meOemCusNo))
                        megreOemCustomerNo += meOemCusNo + ",";
                }
              //查询主要客户信息
                List<Object> cusMList = new ArrayList<Object>();
                cusMList.add(potentialCusDto.getMainCus());
                cusMList.add(loginInfo.getDealerCode());
                cusMList.add(Long.parseLong(DictCodeConstants.D_KEY));
                PotentialCusPO updatePo=null;
                List<PotentialCusPO> cusMPo = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", cusMList.toArray());
                if(cusMPo!=null&&cusMPo.size()>0){
                    PotentialCusPO cusM = cusMPo.get(0);
                    String meCusNo = cusM.getString("MERGE_CUSTOMER_NO");
                    if(meCusNo!=null && !"".equals(meCusNo))
                        megreCustomerNo += meCusNo + ",";
                       
                    String meOemCusNo = cusM.getString("MERGE_OEM_CUSTOMER_NO");
                    if(meOemCusNo!=null && !"".equals(meOemCusNo))
                        megreOemCustomerNo += meOemCusNo + ",";
                    updatePo=cusM;
                        
                }
                if(megreCustomerNo!=null && !"".equals(megreCustomerNo))
                    megreCustomerNo = megreCustomerNo.substring(0, megreCustomerNo.length()-1);
                if(megreOemCustomerNo!=null && !"".equals(megreOemCustomerNo))
                    megreOemCustomerNo = megreOemCustomerNo.substring(0, megreOemCustomerNo.length()-1);
                System.out.println("_______________________1"+megreOemCustomerNo);
                if(updatePo!=null){
                    updatePo.setString("MERGE_CUSTOMER_NO", megreCustomerNo);
                    updatePo.setString("MERGE_OEM_CUSTOMER_NO", megreOemCustomerNo);
                    updatePo.saveIt();
                    System.out.println("_______________________2"+megreOemCustomerNo);
                }
              //再次查询主要客户信息
                List<Object> mainList = new ArrayList<Object>();
                mainList.add(potentialCusDto.getMainCus());
                mainList.add(loginInfo.getDealerCode());
                mainList.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<PotentialCusPO> mainPo = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", mainList.toArray());
              //再次查询非主要客户信息
                
                List<Object> customerList = new ArrayList<Object>();
                customerList.add(no);
                customerList.add(loginInfo.getDealerCode());
                customerList.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<PotentialCusPO> customerPO = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", customerList.toArray());
                if(customerPO!=null&&customerPO.size()>0){
                    PotentialCusPO customer = customerPO.get(0);
                    if(customer.getString("MERGER_CUS_NO") != null && !customer.getString("MERGER_CUS_NO").equals("")){
                        mergerCusNo = no + "," + customer.getString("MERGER_CUS_NO");
                    }
                    else{
                        mergerCusNo = no;
                    }
                }
                if(mainPo!=null&&mainPo.size()>0){
                    PotentialCusPO mainCustomer = mainPo.get(0);
                    System.out.println("_______________________3"+mainCustomer.getString("MERGER_CUS_NO"));
                    if(mainCustomer.getString("MERGER_CUS_NO") != null && !mainCustomer.getString("MERGER_CUS_NO").equals("")){
                        if (mergerCusNoMain != null && !mergerCusNoMain.equals("")){
                           if(mainCustomer.getString("MERGER_CUS_NO").length()+ mergerCusNoMain.length() <= 187){
                                mergerCusNoMain = mergerCusNo + "," + mainCustomer.getString("MERGER_CUS_NO");
                            }
                            else{
                                throw new ServiceBizException("一位客户只能合并15位客户");
                               
                            }                               
                        }
                        else{
                            
                            if(mainCustomer.getString("MERGER_CUS_NO").length() <= 187){
                                mergerCusNoMain = mergerCusNo + "," + mainCustomer.getString("MERGER_CUS_NO");
                            }
                            else{
                                throw new ServiceBizException("一位客户只能合并15位客户");
                               
                            }   
                        }

                        
                    }
                    else{
                        mergerCusNoMain = mergerCusNo;
                    }
                    mainCustomer.setString("MERGER_CUS_NO",mergerCusNoMain);
                    mainCustomer.setLong("IS_UPLOAD",DictCodeConstants.IS_NOT);  
                    mainCustomer.saveIt();
                    System.out.println("_______________________4"+mergerCusNoMain);
                }
                
              //将非主要客户编号所对应的 客户资料表 信息删除         
                PotentialCusPO potentialCusPo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),no);
                System.out.println("将非主要客户编号所对应的 客户资料表 信息删除");
                if(potentialCusPo!=null){
                    System.out.println("将非主要客户编号所对应的 客户资料表 信息删除");
                    potentialCusPo.delete();
                }
              //把非主要客户 跟进活动中记录删除
                List<Object> promotionList = new ArrayList<Object>();
                promotionList.add(no);
                promotionList.add(loginInfo.getDealerCode());
                promotionList.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<TtSalesPromotionPlanPO> promotionPo = TtSalesPromotionPlanPO.findBySQL("select * from TT_SALES_PROMOTION_PLAN where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", promotionList.toArray());
                System.out.println("把非主要客户 跟进活动中记录删除");
                if(promotionPo!=null&&promotionPo.size()>0){
                    for(int p = 0;p<promotionPo.size();p++){
                        TtSalesPromotionPlanPO promotion = promotionPo.get(p);
                        promotion.delete();
                        System.out.println("把非主要客户 跟进活动中记录删除");
                    }
                    
                }
                //预收款登记合并
                if(DictCodeConstants.IS_YES==potentialCusDto.getIsReceive()){
                    List<Object> gatheringList = new ArrayList<Object>();
                    gatheringList.add(no);
                    gatheringList.add(loginInfo.getDealerCode());
                    gatheringList.add(Long.parseLong(DictCodeConstants.D_KEY));
                    List<TtCustomerGatheringPO> gatheringPo = TtCustomerGatheringPO.findBySQL("select * from TT_CUSTOMER_GATHERING where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", gatheringList.toArray());
                    System.out.println("预收款登记合并1");
                    if(gatheringPo!=null&&gatheringPo.size()>0){
                        System.out.println("预收款登记合并2");
                        Double dGatheredSum = 0.00;//总金额
                        Double dUsableAmount = 0.00;//可用余额
                        Double unWriteoffSum=0.00;//未销帐总金额
                        for(int n = 0;n<gatheringPo.size();n++){
                            TtCustomerGatheringPO gathering = gatheringPo.get(n);
                            gathering.setString("CUSTOMER_NO", potentialCusDto.getMainCus());
                            gathering.setLong("OWNED_BY", loginInfo.getUserId());
                            if (gathering.getDouble("RECEIVE_AMOUNT") != null && gathering.getDouble("RECEIVE_AMOUNT") > 0) {
                                dGatheredSum += gathering.getDouble("RECEIVE_AMOUNT");
                                if (gathering.getLong("WRITEOFF_TAG").equals(DictCodeConstants.IS_YES))
                                {
                                    dUsableAmount += gathering.getDouble("RECEIVE_AMOUNT");
                                } 
                                else
                                {
                                    unWriteoffSum+=gathering.getDouble("RECEIVE_AMOUNT");
                                }
                            }
                            System.out.println("预收款登记合并"+dUsableAmount);
                            System.out.println("预收款登记合并"+unWriteoffSum);
                            gathering.saveIt();
                        }
                      //修改主要客户的总金额和可用余额
                        if (dGatheredSum > 0 || dUsableAmount > 0||unWriteoffSum>0 ){
                            PotentialCusPO mainCustomerPo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),potentialCusDto.getMainCus());
                            if(mainCustomerPo!=null){
                                mainCustomerPo.setLong("OWNED_BY", loginInfo.getUserId());
                             
                                if (mainCustomerPo.getDouble("GATHERED_SUM") != null   ) {                                      
                                    mainCustomerPo.setDouble("GATHERED_SUM",dGatheredSum+mainCustomerPo.getDouble("GATHERED_SUM"));
                                } else{
                                    mainCustomerPo.setDouble("GATHERED_SUM",dGatheredSum);
                                }
                                if (mainCustomerPo.getDouble("USABLE_AMOUNT") != null   ) {                                      
                                    mainCustomerPo.setDouble("USABLE_AMOUNT",dUsableAmount+mainCustomerPo.getDouble("USABLE_AMOUNT"));
                                } else{
                                    mainCustomerPo.setDouble("USABLE_AMOUNT",dUsableAmount);
                                }
                                if (mainCustomerPo.getDouble("UN_WRITEOFF_SUM") != null   ) {                                      
                                    mainCustomerPo.setDouble("UN_WRITEOFF_SUM",unWriteoffSum+mainCustomerPo.getDouble("UN_WRITEOFF_SUM"));
                                } else{
                                    mainCustomerPo.setDouble("UN_WRITEOFF_SUM",unWriteoffSum);
                                }
                                mainCustomerPo.saveIt();
                          
                            }
                        }
                        
                    }
                }
                //删除非主要客户TT_QCS_ARCHIVES表信息
                List<Object> qcsList = new ArrayList<Object>();
                qcsList.add(no);
                qcsList.add(loginInfo.getDealerCode());
                qcsList.add(Long.parseLong(DictCodeConstants.D_KEY));
                List<QcsArchivesPO> qcsPo = QcsArchivesPO.findBySQL("select * from TT_QCS_ARCHIVES where CUSTOMER_NO= ? AND DEALER_CODE= ? AND D_KEY= ? ", qcsList.toArray());
                if(qcsPo!=null&&qcsPo.size()>0){
                    for(int a = 0;a<qcsPo.size();a++){
                        QcsArchivesPO qcs = qcsPo.get(a);
                        qcs.delete();
                    }
                }
                //写日志
                operateContent.append("编号为："+no+" ");
                operateContent.append("的客户做了合并操作，删除了客户： "+no);
                OperateLogPO po = new OperateLogPO();
                po.setString("OPERATE_CONTENT", operateContent.toString());
                po.setDate("OPERATE_DATE", new Date());
                po.setString("REMARK", "TM_POTENTIAL_CUSTOMER,CUSTOMER_NO="+no);
                po.setLong("OPERATE_TYPE", Long.valueOf(DictCodeConstants.DICT_ASCLOG_CLIENT_MANAGE));
                po.setLong("OPERATOR", loginInfo.getUserId());
                po.saveIt();
                System.out.println("——————————————————————————————————"+i);
                System.out.println(no);
            }
            
        }
        
    }
    /**
     * 客户激活
     * 
     * @author LGQ
     * @date 2016年12月29日
     * @param potentialCusPo
     */
    @Override
    public void activeCustomer(PotentialCusDTO potentialCusDto) throws ServiceBizException {
        String[] nos = potentialCusDto.getActiveList().split(",");
        String[] intentIds = potentialCusDto.getActiveIntentId().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        for (int i = 0; i < nos.length; i++) {
            String no = nos[i];
            String intentid = intentIds[i];
           PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),no);
            //修改潜客信息
            potentialCusPo.setLong("D_KEY", Long.parseLong(DictCodeConstants.D_KEY));// DKEY
            potentialCusPo.setLong("INTENT_LEVEL", potentialCusDto.getIntentLevel());// 意向级别
            potentialCusPo.setDate("CONSULTANT_TIME", new Date());
            potentialCusPo.setDate("DCC_DATE", new Date());
            potentialCusPo.setDate("DDCN_UPDATE_DATE", new Date());
            potentialCusPo.setDate("VALIDITY_BEGIN_DATE", new Date());
            //清空掉战败前的级别
            potentialCusPo.setLong("FAIL_INTENT_LEVEL", Long.parseLong("0"));
            potentialCusPo.setLong("SOLD_BY", potentialCusDto.getSoldBy());// 销售顾问
            potentialCusPo.setLong("OWNED_BY", potentialCusDto.getSoldBy());// 销售顾问
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("日期"+format1.format(new Date()));
            potentialCusPo.setString("VALIDITY_BEGIN_DATE",  format1.format(new Date()));
            potentialCusPo.saveIt();
            
            
           
            //清空掉战败类型，战败车型，战败原因，停止原因
           
            List<Object> queryList = new ArrayList<Object>();
            queryList.add(no);
            queryList.add(Long.parseLong(intentid));
            queryList.add(loginInfo.getDealerCode());          
            TtCusIntentPO intentpo = TtCusIntentPO.findFirst("CUSTOMER_NO= ? AND INTENT_ID= ? AND DEALER_CODE= ? ",queryList.toArray());           
            if(intentpo!=null){
                intentpo.setLong("FAIL_TYPE", Long.parseLong("0"));
                intentpo.setString("FAIL_MODEL", null);
                intentpo.setString("DR_CODE", null);
                intentpo.setString("ABORT_REASON", null);
                intentpo.setDate("FAIL_CONFIRMED_DATE", null);
                //如果意向级别为F,应该把已完成的意向改为 未完成
                if(potentialCusDto.getIntentLevel()==Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_F)){
                    intentpo.setLong("INTENT_FINISHED",DictCodeConstants.IS_NOT);
                }
                intentpo.saveIt();
            }
           
            //先把该客户以前生成的没有跟进结果的跟进记录删除
            List<Object> planList = new ArrayList<Object>();
            planList.add(no);
            planList.add(loginInfo.getDealerCode());
            planList.add(Long.parseLong("0"));
            TtSalesPromotionPlanPO salesPlanPo = TtSalesPromotionPlanPO.findFirst("CUSTOMER_NO= ? AND  DEALER_CODE= ? AND (PROM_RESULT IS NULL OR PROM_RESULT= ?)", planList.toArray());
            if(salesPlanPo!=null){
                salesPlanPo.delete();
            }
            
            
            //客户跟进任务
     
            List<Object> taskList = new ArrayList<Object>();
            taskList.add(potentialCusDto.getIntentLevel());
            taskList.add(Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
            taskList.add(DictCodeConstants.IS_YES);
            taskList.add(loginInfo.getDealerCode());
           
            /*       StringBuffer sb = new StringBuffer();
            sb.append("select * from TM_TRACTING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ?");*/
            /*List<Map> result = DAOUtil.findAll(sb.toString(), taskList);*/
            //TrackingTaskPO taskPo = TrackingTaskPO.findFirst("", taskList.toArray());
            TtSalesPromotionPlanPO salePo = new TtSalesPromotionPlanPO();
            List<TrackingTaskPO> pp=TrackingTaskPO.findBySQL("select * from TM_TRACKING_TASK where INTENT_LEVEL= ? AND CUSTOMER_STATUS= ? AND IS_VALID= ? AND DEALER_CODE= ? ", taskList.toArray());
           System.out.println(potentialCusDto.getIntentLevel());
            if(pp!=null&&pp.size()>0){
                for(int j=0;j<pp.size();j++){
                    TrackingTaskPO taskPo = pp.get(j);
                    salePo.setLong("INTENT_ID",Long.parseLong(intentid));
                    salePo.setString("CUSTOMER_NO", no);
                    salePo.setString("CUSTOMER_NAME",potentialCusPo.getString("CUSTOMER_NAME"));
                    salePo.setInteger("PRIOR_GRADE",taskPo.getInteger("INTENT_LEVEL"));
                    salePo.setLong("TASK_ID",taskPo.getLong("TASK_ID"));
                    salePo.setString("PROM_CONTENT",taskPo.getString("TASK_CONTENT"));
                    salePo.setInteger("CREATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_CREATE_TYPE_SYSTEM));
                    List<Object> linkList = new ArrayList<Object>();
                    linkList.add(no);
                    linkList.add(loginInfo.getDealerCode());
                    linkList.add(DictCodeConstants.IS_YES);
                    //linkList.add(Long.parseLong(DictCodeConstants.D_KEY));
                   System.out.println(no);
                   //ist<TtPoCusLinkmanPO> linkPo=TtPoCusLinkmanPO.findBySQL("select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO=? AND DEALER_CODE=? AND IS_DEFAULT_CONTACTOR =? ", linkList); 
                   List<Map> linkPo =queryLinkMan(no);
                if(!StringUtils.isNullOrEmpty(linkPo)&&linkPo.size()>0){
                        if(!StringUtils.isNullOrEmpty(linkPo.get(0).get("CONTACTOR_NAME")))
                        salePo.setString("CONTACTOR_NAME",linkPo.get(0).get("CONTACTOR_NAME").toString());
                        if(!StringUtils.isNullOrEmpty(linkPo.get(0).get("PHONE")))
                        salePo.setString("PHONE",linkPo.get(0).get("PHONE").toString());
                        if(!StringUtils.isNullOrEmpty(linkPo.get(0).get("MOBILE")))
                        salePo.setString("MOBILE",linkPo.get(0).get("MOBILE").toString());
                    }
                    salePo.setInteger("IS_AUDITING",12781002);
                    salePo.setString("SOLD_BY",potentialCusDto.getSoldBy().toString());
                    salePo.setString("OWNED_BY",potentialCusDto.getSoldBy().toString());
                    salePo.saveIt();
                    
                }
               
            }
            
            
        }
        
    }
    /**
     * 客户休眠申请
     * 
     * @author LGQ
     * @date 2016年12月30日
     * @param potentialCusPo
     */
    @Override
    public void dormancyApply(PotentialCusDTO potentialCusDto) throws ServiceBizException {
        String[] ids = potentialCusDto.getApplyList().split(",");
        String[] intents = potentialCusDto.getApplyintent().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        for (int i = 0; i < ids.length; i++) {
            String no = ids[i];
            String intent = intents[i];
            PotentialCusPO potentialCusPo=PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),no);
           // CustomerTrackingPO traPo = CustomerTrackingPO.findById(id);
            potentialCusPo.setLong("INTENT_LEVEL",Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_FO));
            potentialCusPo.setLong("AUDIT_STATUS",Long.parseLong(DictCodeConstants.DICT_AUDIT_STATUS_ING));
            potentialCusPo.setDate("DDCN_UPDATE_DATE", new Date());
            potentialCusPo.setString("KEEP_APPLY_REASION", StringUtils.listToString(potentialCusDto.getKeepApplyReasion(), ','));// 购车因素
            potentialCusPo.setLong("SLEEP_TYPE", potentialCusDto.getSleepType());
            potentialCusPo.setLong("FAIL_INTENT_LEVEL", Long.parseLong(intent));
            potentialCusPo.setString("KEEP_APPLY_REMARK", potentialCusDto.getKeepApplyRemark());
            if(!StringUtils.isNullOrEmpty(potentialCusDto.getSleepSeries())){
                potentialCusPo.setString("SLEEP_SERIES", potentialCusDto.getSleepSeries());
            }
            if(!StringUtils.isNullOrEmpty(potentialCusDto.getSleepSeries1())){
                potentialCusPo.setString("SLEEP_SERIES", potentialCusDto.getSleepSeries1());
            }
            if(StringUtils.isNullOrEmpty(potentialCusDto.getSleepSeries())&&StringUtils.isNullOrEmpty(potentialCusDto.getSleepSeries1())){
                potentialCusPo.setString("SLEEP_SERIES", null);
            }
            potentialCusPo.saveIt();
        }
        
    }
    /**
     * 设置第一次到店时间
     * 
     * @author zhanshiwei
     * @date 2016年10月8日
     * @param mobile
     * @param potentialCusPo
     */

    public void setFirstArriveTime(String mobile, PotentialCusPO potentialCusPo) {
        StringBuffer sb = new StringBuffer();
        sb.append("select VISIT_TYPE,VISIT_TIME,DEALER_CODE from TT_VISITING_RECORD where MOBILE=? and IS_FIRST_ARRIVE =? and VISIT_TYPE in (?,?) ORDER  BY VISIT_TIME asc");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(mobile);
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        queryList.add(DictCodeConstants.COMPLAINT_VISIT_TYPE_02);
        queryList.add(DictCodeConstants.COMPLAINT_VISIT_TYPE_03);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if (result != null && result.size() > 0) {
            Map<Object, String> resuFistMap = result.get(0);
            potentialCusPo.setInteger("IS_FIRST_ARRIVE", DictCodeConstants.STATUS_IS_YES);// 是否首次到店
            potentialCusPo.setTimestamp("FIRST_ARRIVE_TIME", resuFistMap.get("visit_time"));// 首次到店时间
        } else {
            potentialCusPo.setInteger("IS_FIRST_ARRIVE", DictCodeConstants.STATUS_IS_NOT);// 是否二次到店
        }
    }

    /**
     * 二次到店/二次到店时间设置
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param mobile
     * @param potentialCusPo
     */

    public void setSecondArriveTime(String mobile, PotentialCusPO potentialCusPo) {
        StringBuffer sb = new StringBuffer();
        sb.append("select VISIT_TYPE,VISIT_TIME,DEALER_CODE from TT_VISITING_RECORD where MOBILE=? and IS_SECOND_ARRIVE =? and VISIT_TYPE in (?,?) ORDER  BY VISIT_TIME asc");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(mobile);
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        queryList.add(DictCodeConstants.COMPLAINT_VISIT_TYPE_02);
        queryList.add(DictCodeConstants.COMPLAINT_VISIT_TYPE_03);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if (result != null && result.size() > 0) {
            Map<Object, String> resuSecondMap = result.get(0);
            potentialCusPo.setInteger("IS_SECOND_TEH_SHOP", DictCodeConstants.STATUS_IS_YES);// 是否二次到店
            potentialCusPo.setTimestamp("SECOND_ARRIVE_TIME", resuSecondMap.get("visit_time"));// 二次到店时间
        } else {
            potentialCusPo.setInteger("IS_SECOND_TEH_SHOP", DictCodeConstants.STATUS_IS_NOT);// 是否二次到店
        }
    }

    /**
     * 更新展厅接待客戶編碼
     * 
     * @author zhanshiwei
     * @date 2016年9月8日
     * @param visiting_record_id 展厅接待id
     * @param customerNo
     */

    public void modifyVisitingRecordByid(PotentialCusDTO potentialCusDto,String customerNo) {
    	String dealerCode  = FrameworkUtil.getLoginInfo().getDealerCode();
        if (!StringUtils.isNullOrEmpty(potentialCusDto.getContactorMobile())||!StringUtils.isNullOrEmpty(potentialCusDto.getContactorPhone())) {
            List<Map> result = this.queryVisitingRecordByMobile(potentialCusDto.getContactorMobile(),potentialCusDto.getContactorPhone());
           if(result!=null&& result.size()>0){
               for (int i = 0; i < result.size(); i++) {
            	   VisitingRecordPO visitPo = VisitingRecordPO.findByCompositeKeys(result.get(i).get("item_id"),dealerCode);
//                   VisitingRecordPO visitPo = VisitingRecordPO.findById(result.get(i).get("item_id"));
                   visitPo.setString("CUSTOMER_NO", potentialCusDto.getCustomerNo());// 客户编码
                   Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
                   Matcher m = p.matcher(potentialCusDto.getContactorMobile());
                   if(m.matches()){
                       visitPo.setString("CONTACTOR_MOBILE", potentialCusDto.getContactorMobile());// 手机
                       visitPo.setString("CONTACTOR_PHONE", null);// 电话
                   }else{
                       visitPo.setString("CONTACTOR_MOBILE", null);// 手机
                       visitPo.setString("CONTACTOR_PHONE", potentialCusDto.getContactorMobile());// 电话 
                   }
              /*     if(!StringUtils.isNullOrEmpty(potentialCusDto.getContactorMobile())){
                       visitPo.setString("CONTACTOR_MOBILE", potentialCusDto.getContactorMobile());
                   }
                   if(!StringUtils.isNullOrEmpty(potentialCusDto.getContactorPhone())){
                       visitPo.setString("CONTACTOR_PHONE", potentialCusDto.getContactorPhone());
                   }*/
                   visitPo.setString("CUSTOMER_NAME", potentialCusDto.getCustomerName());
                   if(!StringUtils.isNullOrEmpty(potentialCusDto.getGender())){
                       visitPo.setInteger("GENDER", potentialCusDto.getGender());
                   }
                   visitPo.setLong("SOLD_BY", potentialCusDto.getSoldBy());
                   visitPo.setLong("OWNED_BY",potentialCusDto.getSoldBy());
                   visitPo.setLong("INTENT_LEVEL", potentialCusDto.getIntentLevel());
                   visitPo.setLong("CUS_SOURCE", potentialCusDto.getCusSource());
                   visitPo.setLong("MEDIA_TYPE", potentialCusDto.getMediaType());
                   visitPo.setLong("MEDIA_TYPE", potentialCusDto.getIsTestDrive());
                   visitPo.setLong("CAMPAIGN_CODE", potentialCusDto.getCampaignCode());
                   visitPo.saveIt();
               }
           }/*else{
               VisitingRecordPO visitPo = new VisitingRecordPO();
           
               System.out.println("11111111222222222222221111111");
               System.out.println(potentialCusDto.getCustomerName());
               visitPo.setString("CUSTOMER_NAME", potentialCusDto.getCustomerName());
               visitPo.setString("CUSTOMER_NO", customerNo);
               if(!StringUtils.isNullOrEmpty(potentialCusDto.getContactorMobile())){
                   visitPo.setString("CONTACTOR_MOBILE", potentialCusDto.getContactorMobile());
               }
               if(!StringUtils.isNullOrEmpty(potentialCusDto.getContactorPhone())){
                   visitPo.setString("CONTACTOR_PHONE", potentialCusDto.getContactorPhone());
               }
               if(!StringUtils.isNullOrEmpty(potentialCusDto.getGender())){
                   visitPo.setInteger("GENDER", potentialCusDto.getGender());
               }
               if(!StringUtils.isNullOrEmpty(potentialCusDto.getSoldBy())){
                   visitPo.setLong("SOLD_BY", potentialCusDto.getSoldBy());
               }
               if(!StringUtils.isNullOrEmpty(potentialCusDto.getInitLevel())){
                   visitPo.setLong("INTENT_LEVEL", potentialCusDto.getInitLevel());
               }
               if(!StringUtils.isNullOrEmpty(potentialCusDto.getCusSource())){
                   visitPo.setLong("CUS_SOURCE", potentialCusDto.getCusSource());
               }
               if(!StringUtils.isNullOrEmpty(potentialCusDto.getMediaType())){
                   visitPo.setLong("MEDIA_TYPE", potentialCusDto.getMediaType());
               }
               
                   visitPo.setLong("IS_FIRST_VISIT", DictCodeConstants.IS_YES);
               
               if(!StringUtils.isNullOrEmpty(potentialCusDto.getCampaignCode())){
                   visitPo.setString("CAMPAIGN_CODE", potentialCusDto.getCampaignCode());
               }
               if(!StringUtils.isNullOrEmpty(potentialCusDto.getSoldBy())){
                   if (potentialCusDto.getFirstIsArrived()==DictCodeConstants.IS_YES) {
                       visitPo.setLong("VISIT_TYPE", Long.parseLong(DictCodeConstants.DICT_VISIT_TYPE_IN_BODY));
                   } else if (potentialCusDto.getFirstIsArrived()==DictCodeConstants.IS_NOT) {
                       visitPo.setLong("VISIT_TYPE", Long.parseLong(DictCodeConstants.DICT_VISIT_TYPE_SPECIAL_OUT));
                   }
               }
               visitPo.setDate("VISIT_TIME", new Date());
               visitPo.saveIt();
            
           }*/
           
        }
    }

    /**
     * 查询展厅记录通过手机号
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param mobile
     * @return
     */

    private List<Map> queryVisitingRecordByMobile(String mobile,String phone) {
        StringBuffer sb = new StringBuffer();
        sb.append("select ITEM_ID,DEALER_CODE from TT_VISITING_RECORD where (1!=1");
        if(!StringUtils.isNullOrEmpty(mobile))
        {
//          sql.append(" and (CONTACTOR_PHONE = '"+conPhone+"' or CONTACTOR_MOBILE='"+conPhone+"')");
            sb.append(" or (1=1 and (CONTACTOR_PHONE = '"+mobile+"' or CONTACTOR_MOBILE='"+mobile+"')) ");
            
        }
        if(!StringUtils.isNullOrEmpty(phone))
        {
//          sql.append(" and (CONTACTOR_PHONE = '"+conMobile+"' or CONTACTOR_MOBILE='"+conMobile+"')");
            sb.append("or (1=1 and (CONTACTOR_PHONE = '"+phone+"' or CONTACTOR_MOBILE='"+phone+"')) ");
            
        }
        sb.append(") ");
        System.out.println(sb.toString());
        List<Object> queryList = new ArrayList<Object>();
        //queryList.add(mobile);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
    }

    /**
     * 潜在客户查询
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryPotentialCusInfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryPotentialCusInfo(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DISTINCT C.*\n");
        sb.append(" from ( select c.DEALER_CODE,c.CUSTOMER_NAME,C.CONTACTOR_MOBILE AS MOBILE,c.CUSTOMER_NO,ci.INTENT_COLOR,\n");
        sb.append(" c.INTENT_ID,C.AUDIT_STATUS,C.UPDATED_AT,C.CREATED_AT,C.DETAIL_DESC,C.TEST_DRIVE_REMARK,C.IS_TEST_DRIVE,\n");
        sb.append(" C.CUSTOMER_STATUS,C.CUSTOMER_TYPE,C.GENDER,C.BIRTHDAY,c.VISIT_TIME AS V_TIME,c.VISIT_TIMES AS V_TIMES,");
        sb.append(" C.ZIP_CODE,C.COUNTRY_CODE,C.PROVINCE,C.CITY,C.DISTRICT,C.EXPECT_TIMES_RANGE,EXPECT_DATE,ci.INTENT_BRAND,ci.INTENT_SERIES,ci.INTENT_MODEL,ci.INTENT_CONFIG,\n");
        sb.append(" C.ADDRESS,C.E_MAIL,C.HOBBY,C.CONTACTOR_PHONE,CASE WHEN C.CONTACTOR_MOBILE IS NULL THEN C.CONTACTOR_PHONE ELSE C.CONTACTOR_MOBILE END AS CONTACTOR_MOBILE,\n");
        sb.append(" C.IS_WHOLESALER,C.RECOMMEND_EMP_NAME,C.INIT_LEVEL,C.CT_CODE,\n");
        sb.append(" C.CERTIFICATE_NO,C.INTENT_LEVEL,C.FAIL_CONSULTANT,C.DELAY_CONSULTANT,\n");
        sb.append(" C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.SOLD_BY,C.CUS_SOURCE,C.MEDIA_TYPE,\n");
        sb.append(" C.IS_REPORTED,C.REPORT_REMARK,C.REPORT_DATETIME,C.REPORT_STATUS,\n");
        sb.append(" C.REPORT_AUDITING_REMARK,C.REPORT_ABORT_REASON,C.GATHERED_SUM,C.ORDER_PAYED_SUM,\n");
        sb.append(" C.CON_PAYED_SUM,C.USABLE_AMOUNT,C.UN_WRITEOFF_SUM,C.FAMILY_INCOME,C.AGE_STAGE,\n");
        sb.append(" C.IS_PERSON_DRIVE_CAR,C.IS_DIRECT,C.FAX,C.EDUCATION_LEVEL,C.LAST_ARRIVE_TIME,C.REBUY_CUSTOMER_TYPE,\n");
        sb.append(" C.OWNER_MARRIAGE,C.VOCATION_TYPE,C.POSITION_NAME,C.IS_CRPVIP,C.HAS_DRIVER_LICENSE,C.MEDIA_DETAIL,\n");
        sb.append(" C.IS_FIRST_BUY,C.BUY_PURPOSE,C.BUY_REASON,C.CHOICE_REASON,C.MODIFY_REASON,C.BEST_CONTACT_TYPE,C.VER,C.LAST_SOLD_BY,C.VALIDITY_BEGIN_DATE,\n");
        sb.append(" C.KEEP_APPLY_REASION,C.KEEP_APPLY_REMARK,C.SLEEP_SERIES,C.SLEEP_TYPE,C.LMS_REMARK,C.DDCN_UPDATE_DATE,\n");
        sb.append(" C.FIRST_IS_ARRIVED,C.ARRIVE_TIME,C.FOUND_DATE,C.REPLACE_DATE,C.SUBMIT_TIME,C.DOWN_TIMESTAMP,C.LARGE_CUSTOMER_NO,C.REMARK,C.CONSULTANT_TIME,C.ORGAN_TYPE,C.DCRC_SERVICE,C.D_KEY ,C.OWNED_BY,C.IS_SECOND_TEH_SHOP,C.SECOND_ARRIVE_TIME,\n");
        sb.append(" C.CUSTOMER_IMPORTANT_LEVEL,C.IS_PAD_CREATE,C.IS_BIG_CUSTOMER,C.OLD_CUSTOMER_NAME,C.OLD_CUSTOMER_VIN,C.COMPANY_NAME,C.ESC_ORDER_STATUS,C.ESC_TYPE,C.EC_ORDER_NO,C.IS_TO_SHOP,C.TIME_TO_SHOP,\n");
        
        // sb.append(" br.BRAND_NAME,se.SERIES_NAME,pa.CONFIG_NAME,mo.MODEL_NAME\n");
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,em.USER_NAME,em.USER_ID,c.ORGANIZATION_ID,tl.CONTACTOR_NAME,tci.TEST_DRIVE_DATE,pa.CONFIG_NAME,co.COLOR_NAME,pot.SCENE,tl.IS_DEFAULT_CONTACTOR\n");//数据权限范围管控
        sb.append(" from TM_POTENTIAL_CUSTOMER c\n");
        sb.append(" left join TM_USER em  on c.SOLD_BY=em.USER_ID AND c.DEALER_CODE=em.DEALER_CODE\n");
        sb.append(" left join   tt_customer_intent_detail ci on c.INTENT_ID=ci.INTENT_ID and ci.dealer_code=c.dealer_code and IS_MAIN_MODEL=?\n");
        sb.append(" left join   tt_customer_intent tci on c.INTENT_ID=tci.INTENT_ID and tci.dealer_code=c.dealer_code and c.CUSTOMER_NO=tci.CUSTOMER_NO\n");
        sb.append(" left  join   tm_brand   br   on   ci.INTENT_BRAND = br.BRAND_CODE and c.DEALER_CODE=br.DEALER_CODE\n");
        sb.append(" left  join   TM_SERIES  se   on   ci.INTENT_SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and c.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" left  join   TM_MODEL   mo   on   ci.INTENT_MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and c.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" left  join   tm_configuration pa   on   ci.INTENT_CONFIG=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and c.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" left  join   tm_color   co   on   ci.INTENT_COLOR = co.COLOR_CODE and c.DEALER_CODE=co.DEALER_CODE\n");
     /*   sb.append(" left  join   TT_VISITING_RECORD   tv   on   c.CUSTOMER_NO = tv.CUSTOMER_NO and c.DEALER_CODE=tv.DEALER_CODE\n");*/
        sb.append(" left join (SELECT MAX(CREATED_AT),SCENE,DEALER_CODE,INTENT_ID,CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN WHERE PROM_RESULT IS NOT NULL AND SCENE IS NOT NULL GROUP BY CREATED_AT,SCENE,DEALER_CODE,INTENT_ID,CUSTOMER_NO) pot on c.INTENT_ID = pot.INTENT_ID and c.DEALER_CODE=pot.DEALER_CODE\n");
        sb.append(" left join TT_PO_CUS_LINKMAN tl on c.CUSTOMER_NO=tl.CUSTOMER_NO and c.DEALER_CODE=tl.DEALER_CODE and tl.IS_DEFAULT_CONTACTOR=12781001) C left join TT_CUSTOMER_VEHICLE_LIST sss on c.DEALER_CODE=sss.DEALER_CODE and c.CUSTOMER_NO=sss.CUSTOMER_NO\n");
        sb.append(" where 1=1 and c.dealer_code='"+loginInfo.getDealerCode()+"'");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
    @Override
    public PageInfoDto queryMainCusInfoByid(String id) throws ServiceBizException {
        String[] ids = id.split(",");
        List<Object> queryList = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer("select * from\n")
                .append("TM_POTENTIAL_CUSTOMER\n")
                .append("where 1=1 and (\n");
        for (int i = 0; i < ids.length; i++){
            sb.append("CUSTOMER_NO= ? or\n");
            queryList.add(ids[i]);
            System.out.println("-----------------"+i);
            System.out.println(ids[i]);
        }
        sb.append("1=2)");
        PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
        return result;
    }
    /**
     * 查询条件设置
     * 
     * @author LGQ
     * @date 2016年12月22日
     * @param sb
     * @param queryParam
     * @param queryList
     */

    public void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
        String flag ="";
        String sflag ="";
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        if(!StringUtils.isNullOrEmpty(queryParam.get("number"))){
            flag=queryParam.get("number");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("snumber"))){
            sflag=queryParam.get("snumber");
        }      
            if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
                sb.append(" and c.SOLD_BY = ? ");
                queryList.add(queryParam.get("soldBy"));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("customerType"))) {
                sb.append(" and c.CUSTOMER_TYPE = ?");
                queryList.add(Integer.parseInt(queryParam.get("customerType")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
                sb.append(" and (c.CONTACTOR_MOBILE like ? or c.CONTACTOR_PHONE like ?)");
                queryList.add("%" + queryParam.get("contactorMobile") + "%");
                queryList.add("%" + queryParam.get("contactorMobile") + "%");
            }
            // lim add  是否增购意向客户的查询
            if(!StringUtils.isNullOrEmpty(queryParam.get("isReplace"))){
                if ("10561002".equals(queryParam.get("isReplace").toString())){
                    sb.append(" AND C.REBUY_CUSTOMER_TYPE=10541002 and sss.File_Message_A is not null and sss.File_Message_B is not null AND sss.File_Message_C is not null");
                } else {
                //  sql.append(" AND (C.REBUY_CUSTOMER_TYPE!=10541002 OR  C.REBUY_CUSTOMER_TYPE IS NULL ) ");
                    sb.append(" AND C.REBUY_CUSTOMER_TYPE=10541002 AND  not (sss.File_Message_A is not null and sss.File_Message_B is not null and sss.File_Message_C is not null)");
                }
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPHONE"))) {
                sb.append(" and c.CONTACTOR_PHONE like ?");
                queryList.add("%" + queryParam.get("contactorPHONE") + "%");
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("cusSource"))) {
                sb.append(" and c.CUS_SOURCE = ?");
                queryList.add(Integer.parseInt(queryParam.get("cusSource")));
            }
            
            if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
                sb.append(" and c.CUSTOMER_NO like ?");
                queryList.add("%" + queryParam.get("customerNo") + "%");
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
                sb.append(" and c.CUSTOMER_NAME like ?");
                queryList.add("%" + queryParam.get("customerName") + "%");
            }
           
           
            if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
                if(queryParam.get("intentLevel").length()==8){
                    sb.append(" and c.INTENT_LEVEL= ? ");
                    queryList.add(Integer.parseInt(queryParam.get("intentLevel")));
                }else{
                    String[] ids = queryParam.get("intentLevel").split(",");
                    sb.append(" and 1=1 and (\n");
                    for (int i = 0; i < ids.length; i++){
                        sb.append("c.INTENT_LEVEL= ? or\n");
                        queryList.add(ids[i]);
                        System.out.println("-----------------"+i);
                        System.out.println(ids[i]);
                    }
                    sb.append("1=2)");
                }
                
            }
          
            if (!StringUtils.isNullOrEmpty(queryParam.get("isPadCreate"))) {
                sb.append(" and c.IS_PAD_CREATE = ?");
                queryList.add(Integer.parseInt(queryParam.get("isPadCreate")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("escOrderStatus"))) {
                sb.append(" and c.ESC_ORDER_STATUS = ?");
                queryList.add(Integer.parseInt(queryParam.get("escOrderStatus")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("escType"))) {
                sb.append(" and c.ESC_TYPE = ?");
                queryList.add(Integer.parseInt(queryParam.get("escType")));
            }
          
            if (!StringUtils.isNullOrEmpty(queryParam.get("intentBrand"))) {
                sb.append(" and c.INTENT_BRAND = ?");
                queryList.add(queryParam.get("intentBrand"));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("intentSeries"))) {
                sb.append(" and c.INTENT_SERIES = ?");
                queryList.add(queryParam.get("intentSeries"));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("intentModel"))) {
                sb.append(" and c.INTENT_MODEL = ?");
                queryList.add(queryParam.get("intentModel"));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("configCode"))) {
                sb.append(" and c.INTENT_CONFIG = ?");
                queryList.add(queryParam.get("configCode"));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("color"))) {
                sb.append(" and c.INTENT_COLOR = ?");
                queryList.add(queryParam.get("color"));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_startdate"))) {
                sb.append(" and c.FOUND_DATE>= ?");
                queryList.add(DateUtil.parseDefaultDate(queryParam.get("foundDate_startdate")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_enddate"))) {
                sb.append(" and c.FOUND_DATE<?");
                queryList.add(DateUtil.addOneDay(queryParam.get("foundDate_enddate")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("beginLastArriveTime"))) {
                sb.append(" and c.LAST_ARRIVE_TIME>= ?");
                queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginLastArriveTime")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("endLastArriveTime"))) {
                sb.append(" and c.LAST_ARRIVE_TIME<?");
                queryList.add(DateUtil.addOneDay(queryParam.get("endLastArriveTime")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("firstReplaceDate"))) {
                sb.append(" and c.REPLACE_DATE>= ?");
                queryList.add(DateUtil.parseDefaultDate(queryParam.get("firstReplaceDate")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("endReplaceDate"))) {
                sb.append(" and c.REPLACE_DATE<?");
                queryList.add(DateUtil.addOneDay(queryParam.get("endReplaceDate")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("beginTimeToShop"))) {
                sb.append(" and c.TIME_TO_SHOP>= ?");
                queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginTimeToShop")));
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("endTimeToShop"))) {
                sb.append(" and c.TIME_TO_SHOP<?");
                queryList.add(DateUtil.addOneDay(queryParam.get("endTimeToShop")));
            }
            StringBuilder sqlSb = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE= '80800000' ");
            List<Object> params = new ArrayList<Object>();
            params.add(loginInfo.getDealerCode());
            params.add(loginInfo.getUserId());
            
            List<Map> list = DAOUtil.findAll(sqlSb.toString(), params);
            if(list!=null&&list.size()>0){
                sb.append(" AND (c.CUS_SOURCE="+DictCodeConstants.DICT_CUS_SOURCE_BY_DCC );
                sb.append(" OR c.CUS_SOURCE="+DictCodeConstants.DICT_CUS_SOURCE_BY_WEB+")"  );
            }else{
                sb.append(" AND (c.CUS_SOURCE!="+DictCodeConstants.DICT_CUS_SOURCE_BY_DCC+ " or c.CUS_SOURCE is null)" + " and (C.SOLD_BY is not NULL)"  ); 
            }
          //销售顾问可见客户属性只能是普通客户,大客户经理可见自己名下的大客户/普通客户
            StringBuilder sqlS = new StringBuilder("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE= '80900000' ");
            List<Object> bigparams = new ArrayList<Object>();
            bigparams.add(loginInfo.getDealerCode());
            bigparams.add(loginInfo.getUserId());
            
            List<Map> biglist = Base.findAll(sqlS.toString(), bigparams.toArray());
            System.out.println("11111111111122222222222222");
            System.out.println(loginInfo.getUserId());
            System.out.println(sqlS.toString());
            System.out.println(biglist);
            if(biglist!=null&&biglist.size()>0){
                if (!StringUtils.isNullOrEmpty(queryParam.get("isBigCustomer"))) {
                    sb.append(" and c.IS_BIG_CUSTOMER = ?");
                    queryList.add(Integer.parseInt(queryParam.get("isBigCustomer")));
                }
            }else{
                if (!StringUtils.isNullOrEmpty(queryParam.get("isBigCustomer"))) {
                    if(queryParam.get("isBigCustomer").equals(DictCodeConstants.DICT_IS_YES)){
                        sb.append(" AND 1<>1");
                    }else{
                        sb.append(" and c.IS_BIG_CUSTOMER = ?");
                        queryList.add(Integer.parseInt(queryParam.get("isBigCustomer")));
                    }
                }else{
                    sb.append(" AND C.IS_BIG_CUSTOMER=" + DictCodeConstants.DICT_IS_NO);
                }
            }
      
      
     
        sb.append(DAOUtilGF.getOwnedByStr("C", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45701500", loginInfo.getDealerCode()));//替换权限201003
      //快速查询不起作用
        if(!StringUtils.isNullOrEmpty(sflag)&&sflag.equals("10571002")){

            //add by jll 2012-06-07 快速查询
//          重要客户 --客户重要批级为“重要”或“非常重要
            if(flag.equals("0")){
                sb.append(" AND (C.CUSTOMER_IMPORTANT_LEVEL = "+DictCodeConstants.DICT_CUSTOMER_IMPORTANT_LEVEL_VERY+"  " );
                sb.append(" OR C.CUSTOMER_IMPORTANT_LEVEL = "+DictCodeConstants.DICT_CUSTOMER_IMPORTANT+" )  ");
            }
            //有望客户级别为O及H的客户 --潜在客户意向级别为O或H客户；
            if(flag.equals("1")){
                sb.append(" AND (C.INTENT_LEVEL = "+DictCodeConstants.DICT_INTENT_LEVEL_H+"  ");
                sb.append(" OR C.INTENT_LEVEL = "+DictCodeConstants.DICT_INTENT_LEVEL_O+" )");
            }
          //没有接触计划的有望客户---没有跟进计划的潜在客户(不包含没有意向的客户)
            if(flag.equals("2")){
                sb.append(" AND NOT EXISTS ( SELECT B.CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN B " );
                sb.append(" WHERE B.CUSTOMER_NO=C.CUSTOMER_NO AND B.DEALER_CODE=C.DEALER_CODE) ");
                
                sb.append(" AND EXISTS ( SELECT R.CUSTOMER_NO FROM TT_CUSTOMER_INTENT R " );
                sb.append(" INNER JOIN TT_CUSTOMER_INTENT_DETAIL Y ON R.DEALER_CODE=Y.DEALER_CODE AND" );
                sb.append(" R.INTENT_ID=Y.INTENT_ID WHERE " );
                sb.append(" R.CUSTOMER_NO=C.CUSTOMER_NO AND R.DEALER_CODE=C.DEALER_CODE  ) ");
            }
            //已试乘试驾客户 --在试乘驾驶计划已试驾的潜在客户；
            if(flag.equals("3")){
                sb.append(" AND EXISTS ( SELECT O.CUSTOMER_NO FROM TT_TEST_DRIVE O  WHERE  " );
                sb.append(" O.CUSTOMER_NO=C.CUSTOMER_NO AND O.DEALER_CODE=C.DEALER_CODE ) ");
            }
            
          //已订单未交车客户--（一般销售及委托交车的销售订单除已交车确认,已交车出库，已取消,已退回,退回已入库、已关单状态外）的潜在客户；
            if(flag.equals("5")){
                sb.append("  AND EXISTS (SELECT P.CUSTOMER_NO FROM  TT_SALES_ORDER P WHERE " );
                sb.append(" P.CUSTOMER_NO=C.CUSTOMER_NO AND P.DEALER_CODE=C.DEALER_CODE  " );
                sb.append(" AND  P.SO_STATUS != "+DictCodeConstants.DICT_SO_STATUS_HAVE_CONFIRMED+" " );
                sb.append(" AND  P.SO_STATUS != "+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+"" );
                sb.append(" AND  P.SO_STATUS != "+DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+" " );
                sb.append(" AND  P.SO_STATUS != "+DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD+" " );
                sb.append(" AND  P.SO_STATUS != "+DictCodeConstants.DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK+" " );
                sb.append(" AND P.SO_STATUS != "+DictCodeConstants.DICT_SO_STATUS_CLOSED+" " );
                sb.append(" AND (P.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_GENERAL+" OR " );
                sb.append("  P.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY+" )) ");
            }
            //今天过生日的客户
            if(flag.equals("6")){
                SimpleDateFormat sf=new SimpleDateFormat("MM");
                SimpleDateFormat sf2=new SimpleDateFormat("dd");
                String currdateM=sf.format(new Date());
                String currdateD=sf2.format(new Date());
                sb.append(" AND MONTH(C.BIRTHDAY)="+currdateM+" AND DAY(C.BIRTHDAY)="+currdateD+" ");
            }
            
          //7天内过生日的客户 --7天内过生日的潜在客户(从今天开始算起)
            if(flag.equals("7")){  
                Date date=new Date();//取时间
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,6);//把日期往后增加一天.整数往后推,负数往前移动
                date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(date);
                Date date1=new Date();//取时间
                Calendar calendar1 = new GregorianCalendar();
                calendar1.setTime(date1);
                calendar1.add(calendar1.DATE,1);//把日期往后增加一天.整数往后推,负数往前移动
                date1=calendar1.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                String dateString1 = formatter1.format(date1);
                
                Date date2=new Date();//取时间
                Calendar calendar2 = new GregorianCalendar();
                calendar2.setTime(date2);
                calendar2.add(calendar2.DATE,2);//把日期往后增加一天.整数往后推,负数往前移动
                date2=calendar2.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString2 = formatter.format(date2);
                Date date3=new Date();//取时间
                Calendar calendar3 = new GregorianCalendar();
                calendar3.setTime(date3);
                calendar3.add(calendar3.DATE,3);//把日期往后增加一天.整数往后推,负数往前移动
                date3=calendar3.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString3 = formatter.format(date3);
                Date date4=new Date();//取时间
                Calendar calendar4 = new GregorianCalendar();
                calendar4.setTime(date4);
                calendar4.add(calendar4.DATE,4);//把日期往后增加一天.整数往后推,负数往前移动
                date4=calendar4.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString4 = formatter.format(date4);
                Date date5=new Date();//取时间
                Calendar calendar5 = new GregorianCalendar();
                calendar5.setTime(date5);
                calendar5.add(calendar5.DATE,5);//把日期往后增加一天.整数往后推,负数往前移动
                date5=calendar5.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString5 = formatter.format(date5);
                sb.append(" AND (MONTH("+dateString+") = MONTH(C.BIRTHDAY)  OR MONTH('"+dateString+"') = MONTH(C.BIRTHDAY))    AND (DAY("+dateString+")=DAY(C.BIRTHDAY)  OR  DAY("+dateString5+")=DAY(C.BIRTHDAY)  OR DAY("+dateString4+")=DAY(C.BIRTHDAY)  OR DAY("+dateString3+")=DAY(C.BIRTHDAY)  OR DAY("+dateString2+")=DAY(C.BIRTHDAY)  OR DAY("+dateString1+")=DAY(C.BIRTHDAY)  OR DAY("+formatter.format(new Date())+")=DAY(C.BIRTHDAY) ) ");
                
            }
            //本周新建客户--本周新建的潜在客户
            Calendar cal=Calendar.getInstance();
            if(flag.equals("8")){

                Date date=new Date();//取时间
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,-6);//把日期往后增加一天.整数往后推,负数往前移动
                date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(date);
                Date date1=new Date();//取时间
                Calendar calendar1 = new GregorianCalendar();
                calendar1.setTime(date1);
                calendar1.add(calendar1.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
                date1=calendar1.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                String dateString1 = formatter1.format(date1);
                
                Date date2=new Date();//取时间
                Calendar calendar2 = new GregorianCalendar();
                calendar2.setTime(date2);
                calendar2.add(calendar2.DATE,-2);//把日期往后增加一天.整数往后推,负数往前移动
                date2=calendar2.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString2 = formatter.format(date2);
                Date date3=new Date();//取时间
                Calendar calendar3 = new GregorianCalendar();
                calendar3.setTime(date3);
                calendar3.add(calendar3.DATE,-3);//把日期往后增加一天.整数往后推,负数往前移动
                date3=calendar3.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString3 = formatter.format(date3);
                Date date4=new Date();//取时间
                Calendar calendar4 = new GregorianCalendar();
                calendar4.setTime(date4);
                calendar4.add(calendar4.DATE,-4);//把日期往后增加一天.整数往后推,负数往前移动
                date4=calendar4.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString4 = formatter.format(date4);
                Date date5=new Date();//取时间
                Calendar calendar5 = new GregorianCalendar();
                calendar5.setTime(date5);
                calendar5.add(calendar5.DATE,-5);//把日期往后增加一天.整数往后推,负数往前移动
                date5=calendar5.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString5 = formatter.format(date5);
                if(cal.get(Calendar.DAY_OF_WEEK)==1){
                    //今天为周日--查询周一到周日--向前推6天
                    sb.append(" AND C.CREATED_AT>='"+dateString+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==2){
                    //今天为周---查询周一
                    sb.append(" AND C.CREATED_AT>= CURRENT_DATE ");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==3){
                    sb.append(" AND C.CREATED_AT>='"+dateString1+"' " +
                    "  ");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==4){
                    sb.append(" AND C.CREATED_AT>='"+dateString2+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==5){
                    sb.append(" AND C.CREATED_AT>='"+dateString3+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==6){
                    sb.append(" AND C.CREATED_AT>='"+dateString4+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==7){
//                  今天为周六--查询周一到周六
                    sb.append(" AND C.CREATED_AT>='"+dateString5+"'");
                }
                
            }
          //本周交车客户--一般销售及委托交车的销售订单交车日期为本周的潜在客户
            if(flag.equals("9")){
                StringBuffer sqlApp=new StringBuffer();

                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                String currdate=sf.format(new Date());
                Date date=new Date();//取时间
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,-6);//把日期往后增加一天.整数往后推,负数往前移动
                date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(date);
                Date date1=new Date();//取时间
                Calendar calendar1 = new GregorianCalendar();
                calendar1.setTime(date1);
                calendar1.add(calendar1.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
                date1=calendar1.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                String dateString1 = formatter1.format(date1);
                
                Date date2=new Date();//取时间
                Calendar calendar2 = new GregorianCalendar();
                calendar2.setTime(date2);
                calendar2.add(calendar2.DATE,-2);//把日期往后增加一天.整数往后推,负数往前移动
                date2=calendar2.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString2 = formatter.format(date2);
                Date date3=new Date();//取时间
                Calendar calendar3 = new GregorianCalendar();
                calendar3.setTime(date3);
                calendar3.add(calendar3.DATE,-3);//把日期往后增加一天.整数往后推,负数往前移动
                date3=calendar3.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString3 = formatter.format(date3);
                Date date4=new Date();//取时间
                Calendar calendar4 = new GregorianCalendar();
                calendar4.setTime(date4);
                calendar4.add(calendar4.DATE,-4);//把日期往后增加一天.整数往后推,负数往前移动
                date4=calendar4.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString4 = formatter.format(date4);
                Date date5=new Date();//取时间
                Calendar calendar5 = new GregorianCalendar();
                calendar5.setTime(date5);
                calendar5.add(calendar5.DATE,-5);//把日期往后增加一天.整数往后推,负数往前移动
                date5=calendar5.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString5 = formatter.format(date5);
                if(cal.get(Calendar.DAY_OF_WEEK)==1){
                    //今天为周日--查询周一到周日--向前推6天
                    sqlApp.append(" AND P.CONFIRMED_DATE>='"+dateString+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==2){
                    //今天为周---查询周一
                    sqlApp.append(" AND P.CONFIRMED_DATE>= CURRENT_DATE ");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==3){
                    sqlApp.append(" AND P.CONFIRMED_DATE>='"+dateString1+"' " +
                    "  ");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==4){
                    sqlApp.append(" AND P.CONFIRMED_DATE>='"+dateString2+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==5){
                    sqlApp.append(" AND P.CONFIRMED_DATE>='"+dateString3+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==6){
                    sqlApp.append(" AND P.CONFIRMED_DATE>='"+dateString4+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==7){
//                  今天为周六--查询周一到周六
                    sqlApp.append(" AND P.CONFIRMED_DATE>='"+dateString5+"'");
                }
                
            
                
                sb.append("  AND EXISTS (SELECT P.CUSTOMER_NO FROM  TT_SALES_ORDER P WHERE ");
                sb.append(" P.CUSTOMER_NO=C.CUSTOMER_NO AND P.DEALER_CODE=C.DEALER_CODE AND ");
                sb.append(" P.SO_STATUS = "+DictCodeConstants.DICT_SO_STATUS_HAVE_OUT_STOCK+" ");
                sb.append(" AND (P.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_GENERAL+" OR ");
                sb.append(" P.BUSINESS_TYPE = "+DictCodeConstants.DICT_SO_TYPE_ENTRUST_DELIVERY+" )  ");
                sb.append(sqlApp);
                sb.append( "  AND P.CONFIRMED_DATE<='"+currdate+"'");
                sb.append("  )");
                
                //sql.append(Utility.getDateCond("P", "CONFIRMED_DATE", "", currdate));
            }
            //本周已联系客户--本周做过跟进的潜在客户
            if(flag.equals("10")){
                StringBuffer sqlApp=new StringBuffer();

                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                String currdate=sf.format(new Date());
                Date date=new Date();//取时间
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,-6);//把日期往后增加一天.整数往后推,负数往前移动
                date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(date);
                Date date1=new Date();//取时间
                Calendar calendar1 = new GregorianCalendar();
                calendar1.setTime(date1);
                calendar1.add(calendar1.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
                date1=calendar1.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                String dateString1 = formatter1.format(date1);
                
                Date date2=new Date();//取时间
                Calendar calendar2 = new GregorianCalendar();
                calendar2.setTime(date2);
                calendar2.add(calendar2.DATE,-2);//把日期往后增加一天.整数往后推,负数往前移动
                date2=calendar2.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString2 = formatter.format(date2);
                Date date3=new Date();//取时间
                Calendar calendar3 = new GregorianCalendar();
                calendar3.setTime(date3);
                calendar3.add(calendar3.DATE,-3);//把日期往后增加一天.整数往后推,负数往前移动
                date3=calendar3.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString3 = formatter.format(date3);
                Date date4=new Date();//取时间
                Calendar calendar4 = new GregorianCalendar();
                calendar4.setTime(date4);
                calendar4.add(calendar4.DATE,-4);//把日期往后增加一天.整数往后推,负数往前移动
                date4=calendar4.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString4 = formatter.format(date4);
                Date date5=new Date();//取时间
                Calendar calendar5 = new GregorianCalendar();
                calendar5.setTime(date5);
                calendar5.add(calendar5.DATE,-5);//把日期往后增加一天.整数往后推,负数往前移动
                date5=calendar5.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString5 = formatter.format(date5);
                if(cal.get(Calendar.DAY_OF_WEEK)==1){
                    //今天为周日--查询周一到周日--向前推6天
                    sqlApp.append(" AND Q.ACTION_DATE>='"+dateString+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==2){
                    //今天为周---查询周一
                    sqlApp.append(" AND Q.ACTION_DATE>= CURRENT_DATE ");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==3){
                    sqlApp.append(" AND Q.ACTION_DATE>='"+dateString1+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==4){
                    sqlApp.append(" AND Q.ACTION_DATE>='"+dateString2+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==5){
                    sqlApp.append(" AND Q.ACTION_DATE>='"+dateString3+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==6){
                    sqlApp.append(" AND Q.ACTION_DATE>='"+dateString4+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==7){
//                  今天为周六--查询周一到周六
                    sqlApp.append(" AND Q.ACTION_DATE>='"+dateString5+"'");
                }
                
                sb.append(" AND  EXISTS ( SELECT Q.CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN Q ");
                sb.append(" WHERE Q.CUSTOMER_NO=C.CUSTOMER_NO AND Q.DEALER_CODE=C.DEALER_CODE ");
                sb.append(sqlApp);
                sb.append("  AND Q.ACTION_DATE<="+currdate+"   " );
                sb.append(" "); 
                sb.append(" AND Q.PROM_RESULT IS NOT NULL AND Q.PROM_RESULT!=0 ) ");
            }
          //本周计划联系客户--本周跟进计划的潜在客户
            if(flag.equals("11")){
                SimpleDateFormat sf=new SimpleDateFormat("yyyy-MM-dd");
                String currdate=sf.format(new Date());
                Date date=new Date();//取时间
                Calendar calendar = new GregorianCalendar();
                calendar.setTime(date);
                calendar.add(calendar.DATE,-6);//把日期往后增加一天.整数往后推,负数往前移动
                date=calendar.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                String dateString = formatter.format(date);
                Date date1=new Date();//取时间
                Calendar calendar1 = new GregorianCalendar();
                calendar1.setTime(date1);
                calendar1.add(calendar1.DATE,-1);//把日期往后增加一天.整数往后推,负数往前移动
                date1=calendar1.getTime(); //这个时间就是日期往后推一天的结果 
                SimpleDateFormat formatter1 = new SimpleDateFormat("yyyy-MM-dd");
                String dateString1 = formatter1.format(date1);
                
                Date date2=new Date();//取时间
                Calendar calendar2 = new GregorianCalendar();
                calendar2.setTime(date2);
                calendar2.add(calendar2.DATE,-2);//把日期往后增加一天.整数往后推,负数往前移动
                date2=calendar2.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString2 = formatter.format(date2);
                Date date3=new Date();//取时间
                Calendar calendar3 = new GregorianCalendar();
                calendar3.setTime(date3);
                calendar3.add(calendar3.DATE,-3);//把日期往后增加一天.整数往后推,负数往前移动
                date3=calendar3.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString3 = formatter.format(date3);
                Date date4=new Date();//取时间
                Calendar calendar4 = new GregorianCalendar();
                calendar4.setTime(date4);
                calendar4.add(calendar4.DATE,-4);//把日期往后增加一天.整数往后推,负数往前移动
                date4=calendar4.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString4 = formatter.format(date4);
                Date date5=new Date();//取时间
                Calendar calendar5 = new GregorianCalendar();
                calendar5.setTime(date5);
                calendar5.add(calendar5.DATE,-5);//把日期往后增加一天.整数往后推,负数往前移动
                date5=calendar5.getTime(); //这个时间就是日期往后推一天的结果 
                String dateString5 = formatter.format(date5);
                StringBuffer sqlApp=new StringBuffer();
                if(cal.get(Calendar.DAY_OF_WEEK)==1){
                    //今天为周日--查询周一到周日
                    sqlApp.append(" AND Q.SCHEDULE_DATE>='"+dateString+"'");
                    
                    sqlApp.append(" AND Q.SCHEDULE_DATE<="+currdate+" ");
                    //sqlApp.append(Utility.getDateCond("Q", "SCHEDULE_DATE", "", currdate));
                }else if(cal.get(Calendar.DAY_OF_WEEK)==2){
                    //今天为周一
                    sqlApp.append(" AND Q.SCHEDULE_DATE>= CURRENT_DATE ");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==3){
                    sqlApp.append(" AND Q.SCHEDULE_DATE>='"+dateString1+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==4){
                    sqlApp.append(" AND Q.SCHEDULE_DATE>='"+dateString2+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==5){
                    sqlApp.append(" AND Q.SCHEDULE_DATE>='"+dateString3+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==6){
                    sqlApp.append(" AND Q.SCHEDULE_DATE>='"+dateString4+"'");
                }else if(cal.get(Calendar.DAY_OF_WEEK)==7){
//                  今天为周六--查询周一到周六
                    sqlApp.append(" AND Q.SCHEDULE_DATE>='"+dateString5+"'");
                }
                
                sb.append(" AND  EXISTS ( SELECT Q.CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN Q " +
                        " WHERE Q.CUSTOMER_NO=C.CUSTOMER_NO AND Q.DEALER_CODE=C.DEALER_CODE " +
                        sqlApp +
                        //"  "+Utility.getDateCond("Q", "SCHEDULE_DATE", "", currdate)+" " +
                        " " +
                        " ) ");

            }
            //未建立销售机会的客户--没有意向的潜在客户(导入的潜在客户和没有意向车型的客户)
            if(flag.equals("12")){
                sb.append(" AND NOT EXISTS ( SELECT R.CUSTOMER_NO FROM TT_CUSTOMER_INTENT R " +
                        " INNER JOIN TT_CUSTOMER_INTENT_DETAIL Y ON R.DEALER_CODE=Y.DEALER_CODE AND" +
                        " R.INTENT_ID=Y.INTENT_ID WHERE " +
                        " R.CUSTOMER_NO=C.CUSTOMER_NO AND R.DEALER_CODE=C.DEALER_CODE  ) ");
            } 
            if(flag.equals("13")){
                Date date = new Date();
                  Calendar calm = Calendar.getInstance();
                  calm.setTime(date);
                  calm.add(Calendar.MONTH, -6);
                  calm.add(Calendar.DATE, 10);
                  String dateAA="'"+new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString()+"'";
                sb.append(" and (C.INTENT_LEVEL!="+DictCodeConstants.DICT_INTENT_LEVEL_F+" and  C.INTENT_LEVEL!="+DictCodeConstants.DICT_INTENT_LEVEL_FO+
                            ") and DATEDIFF("+dateAA+",C.VALIDITY_BEGIN_DATE)>=0" +
                                    " and DATEDIFF("+dateAA+",C.VALIDITY_BEGIN_DATE)<=10" +
                            " and not exists ( select ss.customer_no from tt_sales_order ss  where " +
                            " ss.customer_no=c.customer_no and ss.DEALER_CODE =c.DEALER_CODE " +
                            " and (ss.so_status!="+DictCodeConstants.DICT_SO_STATUS_HAVE_CANCEL+" and ss.so_status!="+DictCodeConstants.DICT_SO_STATUS_HAVE_UNTREAD+"" +
                                    " and ss.so_status!="+DictCodeConstants.DICT_SO_STATUS_WAIT_UNTREAT_IN_STOCK+" and ss.so_status!="+DictCodeConstants.DICT_SO_STATUS_UNTREAT_HAVE_IN_STOCK+"" +
                                            " ))");
            }
        }else{
            sb.append(" and (C.IS_DEFAULT_CONTACTOR="+DictCodeConstants.DICT_IS_YES+" or C.IS_DEFAULT_CONTACTOR is null) "); 
        }
    }

    /**
     * 销售订单潜客选择
     * 
     * @author xukl
     * @date 2016年9月8日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#qryPotentialCus(java.util.Map)
     */

    @Override
    public PageInfoDto qryPotentialCus(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT tpc.CUSTOMER_ID, tpc.DEALER_CODE, tpc.CUSTOMER_NAME, tpc.CUSTOMER_NO,, tpc.ADDRESS, tpc.INTENT_LEVEL, tpc.MEDIA_TYPE, tpc.CUS_SOURCE, te.EMPLOYEE_NAME FROM TM_POTENTIAL_CUSTOMER tpc LEFT JOIN tm_employee te ON tpc.CONSULTANT = te.EMPLOYEE_NO and tpc.DEALER_CODE = te.DEALER_CODE WHERE tpc.INTENT_LEVEL IN(?,?,?,?,?)");
        List<Object> params = new ArrayList<Object>();
        params.add(DictCodeConstants.A_LEVEL);
        params.add(DictCodeConstants.B_LEVEL);
        params.add(DictCodeConstants.C_LEVEL);
        params.add(DictCodeConstants.H_LEVEL);
        params.add(DictCodeConstants.N_LEVEL);
     
        if (!StringUtils.isNullOrEmpty(queryParam.get("mobile"))) {
            sb.append(" and tpc.MOBILE like ? ");
            params.add("%" + queryParam.get("mobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and tpc.CUSTOMER_NAME like ? ");
            params.add("%" + queryParam.get("customerName") + "%");
        }
        PageInfoDto pageinfodto = DAOUtil.pageQuery(sb.toString(), params);
        return pageinfodto;
    }

    /**
     * 设置PotentialCusPO属性
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param potentialCusPo
     * @param potentialCusDto
     */

    public void setPotentialCus(PotentialCusPO potentialCusPo, PotentialCusDTO potentialCusDto) {
        potentialCusPo.setString("CUSTOMER_NO", potentialCusDto.getCustomerNo());// 潜客编号
        potentialCusPo.setString("CUSTOMER_NAME", potentialCusDto.getCustomerName());// 客户编码
        potentialCusPo.setLong("CUSTOMER_STATUS",Long.parseLong(DictCodeConstants.DICT_CUSTOMER_STATUS_LATENCY));
        potentialCusPo.setInteger("CUSTOMER_TYPE", potentialCusDto.getCustomerType());// 客户类型
        potentialCusPo.setInteger("GENDER", potentialCusDto.getGender());// 性别
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(potentialCusDto.getContactorMobile());
        if(m.matches()){
            potentialCusPo.setString("CONTACTOR_MOBILE", potentialCusDto.getContactorMobile());// 手机
            potentialCusPo.setString("CONTACTOR_PHONE", null);// 电话
        }else{
            potentialCusPo.setString("CONTACTOR_MOBILE", null);// 手机
            potentialCusPo.setString("CONTACTOR_PHONE", potentialCusDto.getContactorMobile());// 电话 
        }
      potentialCusPo.setDate("BIRTHDAY", potentialCusDto.getBirthday());// 出生日期
        potentialCusPo.setDate("REPLACE_DATE", new Date());
        potentialCusPo.setString("PROVINCE", potentialCusDto.getProvince());// 省
        potentialCusPo.setString("CITY", potentialCusDto.getCity());// 市
        potentialCusPo.setString("DISTRICT", potentialCusDto.getDistrict());// 区
        potentialCusPo.setString("ADDRESS", potentialCusDto.getAddress());// 地址
        potentialCusPo.setString("ZIP_CODE", potentialCusDto.getZipCode());// 邮编
        potentialCusPo.setString("E_MAIL", potentialCusDto.geteMail());// 邮箱
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsWholeSaler())){
            potentialCusPo.setLong("IS_WHOLESALER", potentialCusDto.getIsWholeSaler());//是否批售
        }else{
            potentialCusPo.setLong("IS_WHOLESALER", DictCodeConstants.IS_NOT);//是否批售
        }
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIntentLevel())){
            potentialCusPo.setInteger("INTENT_LEVEL", potentialCusDto.getIntentLevel());// 意见级别
        }
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIndustryFirst())){
            potentialCusPo.setInteger("INDUSTRY_FIRST", potentialCusDto.getIndustryFirst());// 所属行业
        }
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsReported())){
            potentialCusPo.setLong("IS_REPORTED", potentialCusDto.getIsReported());//是否报备
        }
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getReportRemark())){
            potentialCusPo.setString("REPORT_REMARK", potentialCusDto.getReportRemark());//报备说明
         
        }
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getReportDatetime())){
            potentialCusPo.setDate("REPORT_DATETIME", potentialCusDto.getReportDatetime());// 报备日期
         
        }
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsReported())&&potentialCusDto.getIsReported()!=0){
            potentialCusPo.setLong("REPORT_STATUS", potentialCusDto.getReportStatus());//报备状态
        }else{
            potentialCusPo.setLong("REPORT_STATUS", DictCodeConstants.IS_NOT);//报备状态
        }
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        System.out.println("销售顾问"+potentialCusDto.getSoldBy());
        System.out.println("USER_ID"+loginInfo.getUserId());
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getSoldBy())){
            potentialCusPo.setLong("SOLD_BY", potentialCusDto.getSoldBy());
            potentialCusPo.setLong("OWNED_BY", potentialCusDto.getSoldBy());// 销售顾问potentialCusDto.getSoldBy()
        }else{
            potentialCusPo.setLong("SOLD_BY", loginInfo.getUserId());
            potentialCusPo.setLong("OWNED_BY", loginInfo.getUserId());
        }
        potentialCusPo.setLong("MEDIA_TYPE", potentialCusDto.getMediaType());//信息渠道
        potentialCusPo.setString("MEDIA_DETAIL", potentialCusDto.getMediaDetail());
 
        potentialCusPo.setInteger("CUS_SOURCE", potentialCusDto.getCusSource());// 客户来源
        potentialCusPo.setInteger("CT_CODE", potentialCusDto.getCtCode());// 证件类型
        potentialCusPo.setString("CERTIFICATE_NO", potentialCusDto.getCertificateNo());// 证件号
        
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsPersonDriveCar())&&potentialCusDto.getIsPersonDriveCar()==DictCodeConstants.IS_YES){
            potentialCusPo.setLong("IS_PERSON_DRIVE_CAR", potentialCusDto.getIsPersonDriveCar());//是否自己驾驶
        }else{
            potentialCusPo.setLong("IS_PERSON_DRIVE_CAR", DictCodeConstants.IS_NOT);//报备状态
        }
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsDirect())&&potentialCusDto.getIsDirect()!=0){
            potentialCusPo.setLong("IS_DIRECT", potentialCusDto.getIsDirect());//是否直销
        }else{
            potentialCusPo.setLong("IS_DIRECT", DictCodeConstants.IS_NOT);//报备状态
        }
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsBigCustomer())){
            potentialCusPo.setLong("IS_BIG_CUSTOMER", potentialCusDto.getIsBigCustomer());//是否大客户
            if(potentialCusDto.getIsBigCustomer()==DictCodeConstants.IS_YES){
                if(!StringUtils.isNullOrEmpty(potentialCusDto.getKaType())){
                    potentialCusPo.setLong("KA_TYPE", potentialCusDto.getKaType());//大客户类型
                }
            }else{
                potentialCusPo.setLong("IS_BIG_CUSTOMER", DictCodeConstants.IS_NOT); 
            }
        }else{
            potentialCusPo.setLong("IS_BIG_CUSTOMER", DictCodeConstants.IS_NOT);
        }
        potentialCusPo.setDate("LAST_ARRIVE_TIME", potentialCusDto.getLastArriveTime());// 最后来访时间
        potentialCusPo.setString("DETAIL_DESC", potentialCusDto.getDetailDesc());
        potentialCusPo.setString("REMARK", potentialCusDto.getRemark());
        potentialCusPo.setString("RECOMMEND_EMP_NAME", potentialCusDto.getMediaType());// 推荐单位
        potentialCusPo.setLong("INIT_LEVEL", potentialCusDto.getInitLevel());// 初始级别
        potentialCusPo.setString("CAMPAIGN_CODE", potentialCusDto.getCampaignCode());
        potentialCusPo.setString("CAMPAIGN_NAME", potentialCusDto.getCampaignName());
        
        potentialCusPo.setInteger("BUY_PURPOSE", potentialCusDto.getBuyPurpose());// 购车目的
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getBuyReason())){
            potentialCusPo.setString("BUY_REASON", StringUtils.listToString(potentialCusDto.getBuyReason(), ','));// 购车因素
        }
        potentialCusPo.setString("FAMILY_MEMBER", potentialCusDto.getFamilyMember());//家庭成员
        potentialCusPo.setString("MEMBER_MOBILE",potentialCusDto.getMemberMobile());//成员手机
        potentialCusPo.setString("MEMBER_PHONE",potentialCusDto.getMemberPhone());//成员电话
        potentialCusPo.setString("COMPANY_NAME", potentialCusDto.getCompanyName());// 公司名称
        potentialCusPo.setInteger("FAX", potentialCusDto.getFax());// FAX
        potentialCusPo.setString("POSITION_NAME", potentialCusDto.getPositionName());// 职位名称
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsFirstBuy())&&potentialCusDto.getIsFirstBuy()==DictCodeConstants.IS_YES){
            potentialCusPo.setLong("IS_FIRST_BUY", potentialCusDto.getIsFirstBuy());//是否首次购车
        }else{
            potentialCusPo.setLong("IS_FIRST_BUY", DictCodeConstants.IS_NOT);
        }
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getHasDriverLicense())&&potentialCusDto.getHasDriverLicense()==DictCodeConstants.IS_YES){
            potentialCusPo.setLong("HAS_DRIVER_LICENSE", potentialCusDto.getHasDriverLicense());//是否有驾照
        }else{
            potentialCusPo.setLong("HAS_DRIVER_LICENSE", DictCodeConstants.IS_NOT);
        }
        
       
        potentialCusPo.setString("BEST_CONTACT_TYPE", potentialCusDto.getBestContactType());// 最佳联系方式
        
        
        potentialCusPo.setString("ORGAN_TYPE", potentialCusDto.getOrganType());// 机构类型
        potentialCusPo.setLong("ORGAN_TYPE_CODE", potentialCusDto.getOrganTypeCode());
        potentialCusPo.setString("LARGE_CUSTOMER_NO", potentialCusDto.getLargeCustomerNo());// 大客户编号
        potentialCusPo.setLong("REBUY_CUSTOMER_TYPE", potentialCusDto.getRebuyCustomerType());// 置换客户类型
        potentialCusPo.setLong("FIRST_IS_ARRIVED", potentialCusDto.getFirstIsArrived());//初次是否到店
        potentialCusPo.setDate("ARRIVE_TIME", potentialCusDto.getArriveTime());//到店时间
        if(!StringUtils.isNullOrEmpty(potentialCusDto.getIsSecondTehShop())){
            potentialCusPo.setLong("IS_SECOND_TEH_SHOP", potentialCusDto.getIsSecondTehShop());//是否二次到店
        }else{
            potentialCusPo.setLong("IS_SECOND_TEH_SHOP", DictCodeConstants.IS_NOT);
        }
        potentialCusPo.setDate("SECOND_ARRIVE_TIME", potentialCusDto.getSecondArriveTime());//二次到店时间
        potentialCusPo.setLong("CUSTOMER_IMPORTANT_LEVEL", potentialCusDto.getCustomerImportantLevel());//客户重要级别
        potentialCusPo.setString("CUS_RATING_DESC", potentialCusDto.getCusRatingDesc());//重要评级描述
        potentialCusPo.setLong("CUS_ORIENT_SORT", potentialCusDto.getCusOrientSort());//客户导向类别
        potentialCusPo.setString("IM", potentialCusDto.getIm());//IM
        potentialCusPo.setLong("BETTER_CONTACT_TIME", potentialCusDto.getBetterContactTime());//适宜联系时间
        potentialCusPo.setLong("BETTER_CONTACT_PERIOD", potentialCusDto.getBetterContactPeriod());//适宜联系时间段
        potentialCusPo.setString("OLD_CUSTOMER_NAME", potentialCusDto.getOldCustomerName());//推荐人姓名
        potentialCusPo.setString("OLD_CUSTOMER_VIN", potentialCusDto.getOldCustomerVin());//推荐人VIN
        System.out.println("是否到店"+potentialCusDto.getIsTheShop());
      
        System.out.println("TIME_TO_SHOP");
        System.out.println(potentialCusDto.getTimeToshop());
       //到店时间
       

    }

    /**
     * 设置CustomerIntentPO属性
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param cuintentDto
     * @return
     */

    public TtCustomerIntentDetailPO geCustomerIntent(TtCustomerIntentDetailDTO cuintentDto,Long id,int a) {
        TtCustomerIntentDetailPO intentPo = new TtCustomerIntentDetailPO();
        intentPo.setLong("INTENT_ID", id);
        System.out.println("验证");
        System.out.println(cuintentDto.getIntentBrand());
        System.out.println(cuintentDto.getIntentModel());
        System.out.println(cuintentDto.getIntentBrand());
        System.out.println(cuintentDto.getIntentColor());
        System.out.println(cuintentDto.getIntentConfig());
        intentPo.setString("INTENT_BRAND", cuintentDto.getIntentBrand());// 意向品牌
        intentPo.setString("INTENT_MODEL", cuintentDto.getIntentModel());// 意向车型
        intentPo.setString("INTENT_SERIES", cuintentDto.getIntentSeries());// 意向车系
        intentPo.setString("INTENT_CONFIG", cuintentDto.getIntentConfig());// 意向配置
        intentPo.setString("INTENT_COLOR", cuintentDto.getIntentColor());// 意向颜色
        System.out.println("ShiFO");
        System.out.println(cuintentDto.getPurchaseCount());
        intentPo.setLong("PURCHASE_COUNT", cuintentDto.getPurchaseCount());// 购买数量
        intentPo.setString("COMPETITOR_BRAND", cuintentDto.getCompetitorBrand());// 
        intentPo.setString("COMPETITOR_SERIES", cuintentDto.getCompetitorSeries());//
        intentPo.setDouble("WS_INTENT_PRICE", cuintentDto.getWsIntentPrice());//
        intentPo.setDouble("SUG_RETAIL_PRICE", cuintentDto.getSugRetailPrice());//
        intentPo.setDate("SALES_QUOTE_DATE", cuintentDto.getSalesQuoteDate());//
        intentPo.setLong("INTENDING_BUY_TIME", cuintentDto.getIntendingBuyTime());
        intentPo.setString("QUOTED_REMARK", cuintentDto.getQuotedRemark());
        intentPo.setString("CHOOSE_REASON", cuintentDto.getChooseReason());
        intentPo.setString("OTHER_REQUIREMENTS", cuintentDto.getOtherRequirements());
        if(!StringUtils.isNullOrEmpty(cuintentDto.getIsEcoIntentModel())&&cuintentDto.getIsEcoIntentModel()==DictCodeConstants.IS_YES){
            intentPo.setLong("IS_ECO_INTENT_MODEL", DictCodeConstants.IS_YES);
        }else{
            intentPo.setLong("IS_ECO_INTENT_MODEL", DictCodeConstants.IS_NOT); 
        }
        
        intentPo.setString("RETAIL_FINANCE", cuintentDto.getRetailFinance());
        intentPo.setDouble("DEPOSIT_AMOUNT", cuintentDto.getDepositAmount());
        intentPo.setString("EC_ORDER_NO", cuintentDto.getEcOrderNo());
        intentPo.setDate("DETERMINED_TIME", cuintentDto.getDeterminedTime());
        
        if(a==1){
            intentPo.setLong("IS_MAIN_MODEL", DictCodeConstants.STATUS_IS_YES);// 主要意向
        }else{
            if(!StringUtils.isNullOrEmpty(cuintentDto.getIsMainModel())){
                if(cuintentDto.getIsMainModel()==0){
                    intentPo.setLong("IS_MAIN_MODEL", DictCodeConstants.STATUS_IS_NOT);
                }else{
                    intentPo.setLong("IS_MAIN_MODEL", cuintentDto.getIsMainModel());// 主要意向  
                }
               
            }else{
                intentPo.setLong("IS_MAIN_MODEL", DictCodeConstants.STATUS_IS_NOT);// 主要意向
            }
            
        }
        
        intentPo.setDouble("QUOTED_AMOUNT", cuintentDto.getQuotedAmount());// 报价
        intentPo.setString("QUOTED_REMARK", cuintentDto.getQuotedRemark());// 备注
        return intentPo;
    }
    public TtCustomerVehicleListPO getCustomerKeepCar(TtCustomerVehicleListDTO Dto,String customerNo){
        System.out.println("122222222222222222222222222222222222222222222222");
        System.out.println(Dto.getItemId());
        TtCustomerVehicleListPO  keepCarPO = null;
        if(!StringUtils.isNullOrEmpty(Dto.getItemId())){
            keepCarPO=TtCustomerVehicleListPO.findById(Dto.getItemId());
        }else{
         keepCarPO = new TtCustomerVehicleListPO();
        }
        keepCarPO.setString("CUSTOMER_NO", customerNo);
        keepCarPO.setString("BRAND_NAME", Dto.getBrandName());
        keepCarPO.setString("VIN", Dto.getVin());
        keepCarPO.setLong("VEHICLE_COUNT", Dto.getVehicleCount());
        keepCarPO.setDate("PURCHASE_DATE", Dto.getPurchaseDate());
        keepCarPO.setString("REMARK", Dto.getRemark());
        keepCarPO.setString("COLOR_NAME", Dto.getColorName());
        keepCarPO.setString("LICENSE", Dto.getLicense());
        keepCarPO.setString("ENGINE_NUM", Dto.getEngineNum());
        keepCarPO.setString("MILEAGE", Dto.getMileage());
        keepCarPO.setDate("PRODUCTION_DATE", Dto.getProductionDate());
        keepCarPO.setString("GEAR_FORM", Dto.getGearForm());
        keepCarPO.setString("FUEL_TYPE", Dto.getFuelType());
        keepCarPO.setLong("USE_TYPE", Dto.getUseType());
        if(Dto.getUseType()==0){
            keepCarPO.setLong("USE_TYPE", null);
        }
        System.out.println("_________________________________--");
        System.out.println(Dto.getTrafficInsureInfo());
        if(!StringUtils.isNullOrEmpty(Dto.getTrafficInsureInfo())&&Dto.getTrafficInsureInfo()!=0){
            keepCarPO.setLong("TRAFFIC_INSURE_INFO", Dto.getTrafficInsureInfo());
        }else{
            keepCarPO.setLong("TRAFFIC_INSURE_INFO", null);
        }
        if(!StringUtils.isNullOrEmpty(Dto.getDrivingLicense())&&Dto.getDrivingLicense()!=0){
            keepCarPO.setLong("DRIVING_LICENSE", Dto.getTrafficInsureInfo());
        }else{
            keepCarPO.setLong("DRIVING_LICENSE", null);
        }
        
        keepCarPO.setLong("BUSINESS_INSURE_INFO", Dto.getBusinessInsureInfo());
        if(Dto.getBusinessInsureInfo()==0){
            keepCarPO.setLong("BUSINESS_INSURE_INFO", null);
        }
        keepCarPO.setLong("REGISTRY", Dto.getRegistry());
        if(Dto.getRegistry()==0){
            keepCarPO.setLong("REGISTRY", null);
        }
        keepCarPO.setLong("ORIGIN_CERTIFICATE", Dto.getOriginCertificate());
        if(Dto.getOriginCertificate()==0){
            keepCarPO.setLong("ORIGIN_CERTIFICATE", null);
        }
        keepCarPO.setLong("PURCHASE_TAX", Dto.getPurchaseTax());
        if(Dto.getPurchaseTax()==0){
            keepCarPO.setLong("PURCHASE_TAX", null);
        }
        keepCarPO.setLong("VEHICLE_AND_VESSEL_TAX", Dto.getVehicleAndVesselTax());
        if(Dto.getTrafficInsureData()==null){
            keepCarPO.setLong("VEHICLE_AND_VESSEL_TAX", null);
        }
        keepCarPO.setDate("TRAFFIC_INSURE_DATA", Dto.getTrafficInsureData());
        keepCarPO.setDate("ANNUAL_INSPECTION_DATE", Dto.getAnnualInspectionDate());
        keepCarPO.setDate("SCRAP_DATE", Dto.getScrapDate());
        keepCarPO.setString("PROCEDURE_SPECIAL_EXPLAIN", Dto.getProcedureSpecialExplain());
        keepCarPO.setString("FILE_MESSAGE_A", Dto.getProcedureSpecialExplain());
        keepCarPO.setString("FILE_MESSAGE_B", Dto.getProcedureSpecialExplain());
        keepCarPO.setString("FILE_MESSAGE_C", Dto.getProcedureSpecialExplain());
        keepCarPO.setString("FILE_URLMESSAGE_A", Dto.getProcedureSpecialExplain());
        keepCarPO.setString("FILE_URLMESSAGE_B", Dto.getProcedureSpecialExplain());
        keepCarPO.setString("FILE_URLMESSAGE_C", Dto.getProcedureSpecialExplain());
        keepCarPO.setLong("IS_ASSESSED", Dto.getIsAssessed());
        keepCarPO.setDouble("ASSESSED_PRICE", Dto.getAssessedPrice());
        keepCarPO.setString("OTHER_ACCESSORY", Dto.getOtherAccessory());
        keepCarPO.setString("SERIES_NAME", Dto.getSeriesName());
        keepCarPO.setString("MODEL_NAME", Dto.getModelName());
        System.out.println("111111111111111111111111111111111111111111111111111");
        System.out.println(Dto.getVehicleAllocation());
        if(!StringUtils.isNullOrEmpty(Dto.getVehicleAllocation())){
            keepCarPO.setString("VEHICLE_ALLOCATION", Dto.getVehicleAllocation());// 车辆配置
            /*StringUtils.listToString(Dto.getVehicleAllocation(), ',')*/
            
        }
       return keepCarPO;
    }
    
    public TtPoCusLinkmanPO getLinkman(TtPoCusLinkmanDTO Dto,String customerNo){
        TtPoCusLinkmanPO linkPO = new TtPoCusLinkmanPO();
        linkPO.setString("CUSTOMER_NO", customerNo);
        linkPO.setString("COMPANY", Dto.getCompany());
        linkPO.setString("CONTACTOR_DEPARTMENT", Dto.getContactorDepartment());
        linkPO.setString("CONTACTOR_NAME", Dto.getContactorName());
        linkPO.setLong("GENDER", Dto.getGender());
        linkPO.setLong("IS_DEFAULT_CONTACTOR", Dto.getIsDefaultContactor());
        linkPO.setLong("CONTACTOR_TYPE", Dto.getContactorType());
        linkPO.setString("POSITION_NAME", Dto.getPositionName());
        linkPO.setString("PHONE", Dto.getPhone());
        linkPO.setString("MOBILE", Dto.getMobile());
        linkPO.setString("E_MAIL", Dto.geteMail());
        linkPO.setString("FAX", Dto.getFax());
        linkPO.setLong("BEST_CONTACT_TYPE", Dto.getBestContactType());
        linkPO.setLong("BEST_CONTACT_TIME", Dto.getBestContactTime());
        linkPO.setString("REMARK", Dto.getRemark());
        return linkPO;
        
    }
    

    
    /**
    * @author LiGaoqi
    * @date 2017年4月25日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryOwnerVehicles(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryOwnerVehicles(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        List<Object> queryList = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT B.LICENSE,B.VIN AS OLD_CUSTOMER_VIN,A.*,A.OWNER_NAME AS OLD_CUSTOMER_NAME,B.VIN FROM ("+CommonConstants.VM_OWNER+") A LEFT JOIN ("+CommonConstants.VM_VEHICLE+") B ON A.DEALER_CODE=B.DEALER_CODE AND A.OWNER_NO=B.OWNER_NO WHERE A.DEALER_CODE='"+loginInfo.getDealerCode()+"' AND B.VIN IS NOT NULL ");
        sql.append(" AND A.OWNER_NO <> '" + DictCodeConstants.DEALER_DEFAULT_OWNER_NO + "' ");
        if(!StringUtils.isNullOrEmpty(queryParam.get("ownerNo"))){
            sql.append(" and A.OWNER_NO like ?");
            queryList.add("%" + queryParam.get("ownerNo") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))){
            sql.append(" and A.OWNER_NAME like ?");
            queryList.add("%" + queryParam.get("ownerName") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("license"))){
            sql.append(" and B.LICENSE like ?");
            queryList.add("%" + queryParam.get("license") + "%");
        }
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), queryList);
        return id;
    }
    @Override
    public PageInfoDto queryCampaignName(Map<String, String> queryParam) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql = new StringBuffer("");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String time = dateFormat.format( new Date());
        List<Object> queryList = new ArrayList<Object>();
        sql.append("SELECT * FROM TT_CAMPAIGN_PLAN WHERE DEALER_CODE='" + loginInfo.getDealerCode()
                   + "' AND D_KEY=" + DictCodeConstants.D_KEY + " AND CUR_AUDITING_STATUS="
                   + DictCodeConstants.DICT_MARKET_DEPT_AUDIT_STATUS_1003 + " "
                   + " AND DATE(BEGIN_DATE)<='" + time+ "'"
                   + " AND DATE(END_DATE)>= '" + time+ "' ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_startdate"))) {
            sql.append(" and BEGIN_DATE>= ?");
            queryList.add(queryParam.get("foundDate_startdate"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_enddate"))) {
            sql.append(" and END_DATE<= ?");
            queryList.add(queryParam.get("foundDate_enddate"));
        }
//         if (begin != null && !"".equals(begin.trim()))
//         {
//             sql.append(" AND DATE(BEGIN_DATE)>='" + begin + "'  ");
//         }
//         if (end != null && !"".equals(end.trim()))
//         {
//             sql.append(" AND DATE(BEGIN_DATE)>='" + end + "' ");
//         }
           
           if(commonNoService.getDefalutPara("1801").equals(DictCodeConstants.DICT_IS_YES)){
               sql.append(" AND COM_AUDITING_STATUS= "+ DictCodeConstants.DICT_MARKET_DEPT_AUDIT_STATUS_1005);
           }
           sql.append(" UNION ALL ");
           sql.append("SELECT * FROM TT_CAMPAIGN_PLAN WHERE DEALER_CODE='" + loginInfo.getDealerCode()
                   + "' AND D_KEY=" + DictCodeConstants.D_KEY + " AND CAMPAIGN_PERFORM_TYPE="
                   + DictCodeConstants.DICT_CAMPAIGN_PERFORM_TYPE_INDEPENDANCE + " "
                   + " AND CUR_AUDITING_STATUS="
                   + DictCodeConstants.DICT_MARKET_DEPT_AUDIT_STATUS_1001 + "  "
                   + " AND DATE(BEGIN_DATE)<='" + time+ "'"
                   + " AND DATE(END_DATE)>= '" + time+ "' ");
           if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_startdate"))) {
               sql.append(" and BEGIN_DATE>= ?");
               queryList.add(queryParam.get("foundDate_startdate"));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_enddate"))) {
               sql.append(" and END_DATE<= ?");
               queryList.add(queryParam.get("foundDate_enddate"));
           }
           if(commonNoService.getDefalutPara("1801").equals(DictCodeConstants.DICT_IS_YES)){
               sql.append(" AND COM_AUDITING_STATUS= "+ DictCodeConstants.DICT_MARKET_DEPT_AUDIT_STATUS_1001);
           }

           sql.append(" UNION ALL ");
           sql.append("SELECT * FROM TT_CAMPAIGN_PLAN WHERE DEALER_CODE='" + loginInfo.getDealerCode()
                   + "' AND D_KEY=" + DictCodeConstants.D_KEY + " AND CAMPAIGN_PERFORM_TYPE="
                   + DictCodeConstants.DICT_CAMPAIGN_PERFORM_TYPE_INDEPENDANCE + " "
                   + " AND CUR_AUDITING_STATUS="
                   + DictCodeConstants.DICT_MARKET_DEPT_AUDIT_STATUS_1001 + "  "
                   + " AND DATE(BEGIN_DATE)<='" + time+ "'"
                   + " AND DATE(END_DATE)>= '" + time+ "' "
                   +" AND BEGIN_DATE is null");
           if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_startdate"))) {
               sql.append(" and BEGIN_DATE>= ?");
               queryList.add(queryParam.get("foundDate_startdate"));
           }
           if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_enddate"))) {
               sql.append(" and END_DATE<= ?");
               queryList.add(queryParam.get("foundDate_enddate"));
           }
//         if (begin != null && !"".equals(begin.trim()))
//         {
//             sql.append(" AND DATE(BEGIN_DATE)>='" + begin + "'  ");
//         }
//         if (end != null && !"".equals(end.trim()))
//         {
//             sql.append(" AND DATE(BEGIN_DATE)>='" + end + "' ");
//         }
   /*        sql.append(Utility.getDateCond("", "BEGIN_DATE", begin, end));
        sql.append("SELECT * FROM TT_CAMPAIGN_PLAN ")
        .append("WHERE D_KEY= ? ").append(" AND CUR_AUDITING_STATUS= ? ");
         List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.D_KEY);
        queryList.add(DictCodeConstants.DICT_MARKET_DEPT_AUDIT_STATUS_1003);
        if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_startdate"))) {
            sql.append(" and BEGIN_DATE>= ?");
            queryList.add(queryParam.get("foundDate_startdate"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_enddate"))) {
            sql.append(" and END_DATE<= ?");
            queryList.add(queryParam.get("foundDate_enddate"));
        }*/
            
        PageInfoDto id = DAOUtil.pageQuery(sql.toString(), queryList);
        System.err.println(sql.toString());
        return id;
    }
    /**
     * 展厅接待选择潜客查询
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryPotentialCusForSel(java.util.Map)
     */

    @Override
    public PageInfoDto queryPotentialCusForSel(Map<String, String> queryParam) throws ServiceBizException {
    	StringBuffer sb = new StringBuffer();
    	sb.append(" SELECT distinct '12781001' AS BZC,'12781002' AS IS_FIRST_VISIT,C.DEALER_CODE,C.CUSTOMER_NO,C.CUSTOMER_NAME,C.CUSTOMER_STATUS,C.CUSTOMER_TYPE,C.GENDER as GEN,C.BIRTHDAY,\n");
    	sb.append(" C.ZIP_CODE,C.COUNTRY_CODE,C.PROVINCE,C.CITY,C.DISTRICT,C.ADDRESS,C.E_MAIL,C.HOBBY,C.CONTACTOR_PHONE as PHONE,\n");
    	sb.append(" C.CONTACTOR_MOBILE as MOBILE,C.IS_WHOLESALER,C.RECOMMEND_EMP_NAME,C.INIT_LEVEL,C.CT_CODE,C.CERTIFICATE_NO,\n");
    	sb.append(" C.INTENT_LEVEL,C.FAIL_CONSULTANT,C.DELAY_CONSULTANT,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.SOLD_BY,\n");
    	sb.append(" C.CUS_SOURCE,C.MEDIA_TYPE,C.MEDIA_DETAIL,C.IS_REPORTED,C.REPORT_REMARK,C.REPORT_DATETIME,C.REPORT_STATUS,\n");
    	sb.append(" C.REPORT_AUDITING_REMARK,C.REPORT_ABORT_REASON,C.GATHERED_SUM,C.ORDER_PAYED_SUM,\n");
    	sb.append(" C.CON_PAYED_SUM,C.USABLE_AMOUNT,C.UN_WRITEOFF_SUM,C.IS_SECOND_TEH_SHOP,C.FIRST_IS_ARRIVED,C.ARRIVE_TIME,\n");
    	sb.append(" L.ITEM_ID AS II,L.CUSTOMER_NO as CONTACTOR_NAME,L.BEST_CONTACT_TYPE,L.IS_DEFAULT_CONTACTOR,L.CONTACTOR_TYPE,L.DEALER_CODE as DC,L.COMPANY,\n");
    	sb.append(" L.CONTACTOR_NAME as CN,L.GENDER,L.PHONE as CONTACTOR_PHONE,L.MOBILE as CONTACTOR_MOBILE,L.E_MAIL as EM,L.CONTACTOR_DEPARTMENT,L.POSITION_NAME,L.FAX,L.REMARK,\n");
    	sb.append(" E.USER_ID,E.USER_NAME,vid.ITEM_ID,cid.ITEM_ID AS ID,\n");
    	sb.append(" (SELECT COUNT(1) FROM TT_SALES_PROMOTION_PLAN A\n");
    	sb.append(" WHERE A.DEALER_CODE = C.DEALER_CODE\n");
    	sb.append(" AND A.CUSTOMER_NO = C.CUSTOMER_NO\n");
    	sb.append(" AND (A.PROM_RESULT IS NOT NULL OR A.PROM_RESULT <> 0)\n");
    	sb.append(" ) AS COUNT_SALES_PROMOTION_PLAN\n");
    	sb.append(" FROM TM_POTENTIAL_CUSTOMER C LEFT JOIN TT_PO_CUS_LINKMAN L\n");
    	sb.append(" ON C.CUSTOMER_NO = L.CUSTOMER_NO LEFT JOIN TM_USER E\n");
    	sb.append(" ON C.SOLD_BY=E.USER_ID\n");	 
        sb.append(" LEFT JOIN tt_visiting_record tvr ON tvr.CUSTOMER_NO=C.CUSTOMER_NO");
    	sb.append(" LEFT JOIN tt_visiting_intent_detail vid ON vid.ITEM_ID=tvr.ITEM_ID");
    	sb.append(" LEFT JOIN TT_CUSTOMER_INTENT_DETAIL cid ON cid.INTENT_ID=C.INTENT_ID");
    	sb.append(" where L.IS_DEFAULT_CONTACTOR='12781001' AND 1=1");
    	System.err.println(sb.toString());

  
        List<Object> queryList = new ArrayList<Object>();
        this.setHere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 通过id查询潜客信息
     * 
     * @author zhanshiwei
     * @date 2016年9月9日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryPotentialCusInfo(java.lang.Long)
     */

    @Override
    public List<Map> queryPotentialCusInfoByid(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select cus.*,CASE WHEN  cus.CONTACTOR_MOBILE IS NULL THEN  cus.CONTACTOR_PHONE ELSE  cus.CONTACTOR_MOBILE END AS CONTACTOR_MOBILEANDPHONE,ci.INTENT_SERIES,se.SERIES_NAME,tl.CONTACTOR_NAME,ti.BUDGET_AMOUNT,ti.DECISION_MAKER,ti.PURCHASE_TYPE,ti.BILL_TYPE,ti.IS_BUDGET_ENOUGH from\n")
                .append("TM_POTENTIAL_CUSTOMER cus\n")
                .append("left join TT_CUSTOMER_INTENT ti on cus.CUSTOMER_NO=ti.CUSTOMER_NO and cus.INTENT_ID=ti.INTENT_ID\n")
                .append(" left join   tt_customer_intent_detail ci on cus.INTENT_ID=ci.INTENT_ID and IS_MAIN_MODEL=12781001\n")
                .append("left join TT_PO_CUS_LINKMAN tl on cus.CUSTOMER_NO=tl.CUSTOMER_NO and cus.DEALER_CODE=tl.DEALER_CODE\n")
                .append(" left  join   TM_SERIES  se   on   ci.INTENT_SERIES=se.SERIES_CODE and cus.DEALER_CODE=se.DEALER_CODE\n")
                .append("where cus.CUSTOMER_NO=?");
        System.out.println(sb.toString());
        System.err.println(sb.toString());
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
        /*LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        System.out.println(loginInfo.getDealerCode());
        System.out.println(id);
        return PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),id);*/
    }

    
    
    /**
    * @author LiGaoqi
    * @date 2017年3月15日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryxiumian(java.lang.String)
    */
    	
    @Override
    public List<Map> queryxiumian(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select cus.*,CASE WHEN  cus.CONTACTOR_MOBILE IS NULL THEN  cus.CONTACTOR_PHONE ELSE  cus.CONTACTOR_MOBILE END AS CONTACTOR_MOBILEANDPHONE,ci.INTENT_SERIES,se.SERIES_NAME,tl.CONTACTOR_NAME,ti.BUDGET_AMOUNT,ti.DECISION_MAKER,ti.PURCHASE_TYPE,ti.BILL_TYPE,ti.IS_BUDGET_ENOUGH from\n")
                .append("TM_POTENTIAL_CUSTOMER cus\n")
                .append("left join TT_CUSTOMER_INTENT ti on cus.CUSTOMER_NO=ti.CUSTOMER_NO and cus.INTENT_ID=ti.INTENT_ID\n")
                .append(" left join   tt_customer_intent_detail ci on cus.INTENT_ID=ci.INTENT_ID and IS_MAIN_MODEL=12781001\n")
                .append("left join TT_PO_CUS_LINKMAN tl on cus.CUSTOMER_NO=tl.CUSTOMER_NO and cus.DEALER_CODE=tl.DEALER_CODE\n")
                .append(" left  join   TM_SERIES  se   on   ci.INTENT_SERIES=se.SERIES_CODE and cus.DEALER_CODE=se.DEALER_CODE\n")
                .append("where 1=1");
        System.out.println(sb.toString());
        System.err.println(sb.toString());
        List<Object> queryList = new ArrayList<Object>();
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
    }

    
    
    @Override
    public List<Map> checkMainCustomer(String id) throws ServiceBizException {
        String[] ids = id.split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        StringBuffer sql=new StringBuffer();
        sql.append("SELECT A.CUSTOMER_NO,A.DEALER_CODE FROM TM_POTENTIAL_CUSTOMER  A,TT_PO_CUS_WHOLESALE B " +
                "WHERE A.CUSTOMER_NO=B.CUSTOMER_NO  " );
        sql.append(" AND A.DEALER_CODE='"+loginInfo.getDealerCode()+"' AND A.D_KEY="+DictCodeConstants.D_KEY+"");
        sql.append(" AND (");
        for (int i = 0; i < ids.length; i++)
        {
           
                sql.append(" A.CUSTOMER_NO='"+ids[i]+"'");
                if(ids.length-i!=1){
                    sql.append(" OR ");
                }
            
          
        }
        sql.append(") ");
        
        
        sql.append("  UNION  ");
        sql.append(" SELECT D.CUSTOMER_NO,D.DEALER_CODE FROM TM_POTENTIAL_CUSTOMER  D,TT_SALES_ORDER  E " +
                "WHERE D.CUSTOMER_NO=E.CUSTOMER_NO  AND D.DEALER_CODE=E.DEALER_CODE ");
        sql.append("  AND D.DEALER_CODE='"+loginInfo.getDealerCode()+"' AND D.D_KEY="+DictCodeConstants.D_KEY+"");
        sql.append("  AND (");
        for (int j = 0; j < ids.length; j++)
        {
          
                sql.append(" D.CUSTOMER_NO='"+ids[j]+"'");
                if(ids.length-j!=1){
                    sql.append(" OR ");
                }
           
         
        }
        sql.append(") ");
        System.out.println(sql);
        List<Object> queryList = new ArrayList<Object>();
        List<Map> result = DAOUtil.findAll(sql.toString(), queryList);
        return result;
    }
    /**
     * 根据父表主键查询(客户意向)明细
     * 
     * @author zhanshiwei
     * @date 2016年9月9日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryCustomerIntentoByParendId(java.lang.Long)
     */

    @Override
    public List<Map> queryCustomerIntentoByCustomerNo(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,cus.CUSTOMER_NO,ti.*, DATE_FORMAT(ti.SALES_QUOTE_DATE, '%Y-%m-%d') as SALES_QUOTE_DATE1 \n ");
        sb.append("from TM_POTENTIAL_CUSTOMER cus\n");
        sb.append("left join TT_CUSTOMER_INTENT_DETAIL ti on cus.DEALER_CODE=ti.DEALER_CODE and cus.INTENT_ID=ti.INTENT_ID\n");
        sb.append(" left  join   tm_brand   br   on   ti.INTENT_BRAND = br.BRAND_CODE and cus.DEALER_CODE=br.DEALER_CODE\n");
        sb.append(" left  join   TM_SERIES  se   on   ti.INTENT_SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and cus.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" left  join   TM_MODEL   mo   on   ti.INTENT_MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and cus.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" left  join   tm_configuration pa   on   ti.INTENT_CONFIG=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and cus.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" left  join   tm_color   co   on   ti.INTENT_COLOR = co.COLOR_CODE and cus.DEALER_CODE=co.DEALER_CODE\n");
        sb.append("where cus.CUSTOMER_NO=?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return Base.findAll(sb.toString(), queryParams.toArray());
    }

    @Override
    public List<Map> queryKeepCartoByCustomerNo(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select a.*,b.BRAND_NAME as BRAND \n ");
        sb.append("from TT_CUSTOMER_VEHICLE_LIST a left join tm_sc_brand b on a.dealer_code=b.dealer_code and a.brand_name=b.brand_code where CUSTOMER_NO=? ");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return Base.findAll(sb.toString(), queryParams.toArray());
    }

    @Override
    public List<Map> queryFollowtoByCustomerNo(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select a.*,b.USER_NAME \n ");
        sb.append("from TT_SALES_PROMOTION_PLAN A left join tm_user b on a.dealer_code=b.dealer_code and a.sold_by=b.user_id where CUSTOMER_NO=?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return Base.findAll(sb.toString(), queryParams.toArray());
    }

    @Override
    public List<Map> queryLinkMantoByCustomerNo(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select * \n ");
        sb.append("from TT_PO_CUS_LINKMAN where CUSTOMER_NO=?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return Base.findAll(sb.toString(), queryParams.toArray());
    }

    

    /**
     * 验证手机号
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param mobile
     * @param id
     * @throws ServiceBizException
     */

    private void queryMobileIsExists(String mobile, String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select DEALER_CODE from tm_potential_customer where 1=1 ");
        List<Object> queryList = new ArrayList<Object>();
        sb.append("and CONTACTOR_MOBILE = ? ");
        queryList.add(mobile);
        sb.append("and CUSTOMER_NO <> ? ");
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if (result.size() > 0) {
            throw new ServiceBizException("手机号在客户信息已存在 请修改!");
        }
    }

    /**
     * 导出查询
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryPotentialCusforExport(java.util.Map)
     */

    @Override
    public List<Map> queryPotentialCusforExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DISTINCT C.*\n");
        sb.append(" from ( select c.DEALER_CODE,c.CUSTOMER_NAME,c.CUSTOMER_NO,ci.INTENT_COLOR,\n");
        sb.append(" c.INTENT_ID,C.AUDIT_STATUS,C.UPDATED_AT,C.DETAIL_DESC,\n");
        sb.append(" C.CUSTOMER_STATUS,C.CUSTOMER_TYPE,C.GENDER,C.BIRTHDAY,");
        sb.append(" C.ZIP_CODE,C.COUNTRY_CODE,C.PROVINCE,C.CITY,C.DISTRICT,C.EXPECT_TIMES_RANGE,EXPECT_DATE,ci.INTENT_BRAND,ci.INTENT_SERIES,ci.INTENT_MODEL,ci.INTENT_CONFIG,\n");
        sb.append(" C.ADDRESS,C.E_MAIL,C.HOBBY,C.CONTACTOR_PHONE,C.CONTACTOR_MOBILE,\n");
        sb.append(" C.IS_WHOLESALER,C.RECOMMEND_EMP_NAME,C.INIT_LEVEL,C.CT_CODE,\n");
        sb.append(" C.CERTIFICATE_NO,C.INTENT_LEVEL,C.FAIL_CONSULTANT,C.DELAY_CONSULTANT,\n");
        sb.append(" C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.SOLD_BY,C.CUS_SOURCE,C.MEDIA_TYPE,\n");
        sb.append(" C.IS_REPORTED,C.REPORT_REMARK,C.REPORT_DATETIME,C.REPORT_STATUS,\n");
        sb.append(" C.REPORT_AUDITING_REMARK,C.REPORT_ABORT_REASON,C.GATHERED_SUM,C.ORDER_PAYED_SUM,\n");
        sb.append(" C.CON_PAYED_SUM,C.USABLE_AMOUNT,C.UN_WRITEOFF_SUM,C.FAMILY_INCOME,C.AGE_STAGE,\n");
        sb.append(" C.IS_PERSON_DRIVE_CAR,C.IS_DIRECT,C.FAX,C.EDUCATION_LEVEL,C.LAST_ARRIVE_TIME,C.REBUY_CUSTOMER_TYPE,\n");
        sb.append(" C.OWNER_MARRIAGE,C.VOCATION_TYPE,C.POSITION_NAME,C.IS_CRPVIP,C.HAS_DRIVER_LICENSE,C.MEDIA_DETAIL,\n");
        sb.append(" C.IS_FIRST_BUY,C.BUY_PURPOSE,C.BUY_REASON,C.CHOICE_REASON,C.MODIFY_REASON,C.BEST_CONTACT_TYPE,C.VER,C.LAST_SOLD_BY,C.VALIDITY_BEGIN_DATE,\n");
        sb.append(" C.KEEP_APPLY_REASION,C.KEEP_APPLY_REMARK,C.SLEEP_SERIES,C.SLEEP_TYPE,C.LMS_REMARK,C.DDCN_UPDATE_DATE,\n");
        sb.append(" C.FIRST_IS_ARRIVED,C.ARRIVE_TIME,C.FOUND_DATE,C.REPLACE_DATE,C.SUBMIT_TIME,C.DOWN_TIMESTAMP,C.LARGE_CUSTOMER_NO,C.REMARK,C.CONSULTANT_TIME,C.ORGAN_TYPE,C.DCRC_SERVICE,C.D_KEY ,C.OWNED_BY,C.IS_SECOND_TEH_SHOP,C.SECOND_ARRIVE_TIME,\n");
        sb.append(" C.CUSTOMER_IMPORTANT_LEVEL,C.IS_PAD_CREATE,C.IS_BIG_CUSTOMER,C.OLD_CUSTOMER_NAME,C.OLD_CUSTOMER_VIN,C.COMPANY_NAME,C.ESC_ORDER_STATUS,C.ESC_TYPE,C.EC_ORDER_NO,C.IS_TO_SHOP,C.TIME_TO_SHOP,\n");
        
        // sb.append(" br.BRAND_NAME,se.SERIES_NAME,pa.CONFIG_NAME,mo.MODEL_NAME\n");
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,em.USER_NAME,em.USER_ID,c.ORGANIZATION_ID,tl.CONTACTOR_NAME,tci.TEST_DRIVE_DATE,tci.IS_TEST_DRIVE,pa.CONFIG_NAME,co.COLOR_NAME,P.SCENE,tl.IS_DEFAULT_CONTACTOR\n");//数据权限范围管控
        sb.append(" from TM_POTENTIAL_CUSTOMER c\n");
        sb.append(" left join TM_USER em  on c.SOLD_BY=em.USER_ID\n");
        sb.append(" left join   tt_customer_intent_detail ci on c.INTENT_ID=ci.INTENT_ID and IS_MAIN_MODEL=?\n");
        sb.append(" left join   tt_customer_intent tci on c.INTENT_ID=tci.INTENT_ID and c.CUSTOMER_NO=tci.CUSTOMER_NO\n");
        sb.append(" left  join   tm_configuration pa   on   ci.INTENT_CONFIG=pa.CONFIG_CODE and c.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" left  join   TM_MODEL   mo   on   ci.INTENT_MODEL=mo.MODEL_CODE and c.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" left  join   TM_SERIES  se   on   ci.INTENT_SERIES=se.SERIES_CODE and c.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" left  join   tm_brand   br   on   ci.INTENT_BRAND = br.BRAND_CODE and c.DEALER_CODE=br.DEALER_CODE\n");
        sb.append(" left  join   tm_color   co   on   ci.INTENT_COLOR = co.COLOR_CODE and c.DEALER_CODE=co.DEALER_CODE\n");
        sb.append(" left join (SELECT MAX(CREATED_AT),SCENE,DEALER_CODE,INTENT_ID,CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN WHERE PROM_RESULT IS NOT NULL GROUP BY customer_no) p on c.INTENT_ID = P.INTENT_ID and c.DEALER_CODE=P.DEALER_CODE\n");
        
       /* sb.append(" left  join   TT_SALES_PROMOTION_PLAN p on c.INTENT_ID = P.INTENT_ID and c.DEALER_CODE=P.DEALER_CODE AND P.PROM_RESULT IS NOT NULL\n");*/
        sb.append(" left join TT_PO_CUS_LINKMAN tl on c.CUSTOMER_NO=tl.CUSTOMER_NO and c.DEALER_CODE=tl.DEALER_CODE) C\n");
        sb.append(" where 1=1");
        

        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        
        this.setWhere(sb, queryParam, queryList);
        List<Map> resultList = DAOUtil.downloadPageQuery(sb.toString(), queryList);
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("潜客信息导出");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        return resultList;
    }

    /**
     * (在分配),(战败再分配)
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param potentialCusDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#modifyPotentialCusForRedis(com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCusDTO)
     */

    @Override
    public void modifyPotentialCusForRedis(PotentialCusDTO potentialCusDto) throws ServiceBizException {
        String[] ids = potentialCusDto.getCustomerIds().split(",");
        for (int i = 0; i < ids.length; i++) {
            long id = Long.parseLong(ids[i]);
            if (DictCodeConstants.F_LEVEL == potentialCusDto.getIntentLevel()) {
                // b. 潜客信息销售顾问，再分配时间取当前系统时间，战败再分配为是，前销售顾问取未分配前销售顾问。
                PotentialCusPO potentialCusPo = PotentialCusPO.findById(id);
                potentialCusPo.setString("LAST_CONSULTANT", potentialCusPo.getString("CONSULTANT"));// 前销售顾问
                /*potentialCusPo.setString("CONSULTANT", potentialCusDto.getConsultant());// 销售顾问
*/                potentialCusPo.set("CONSULTANT_TIME", new Date());// 再分配时间
                potentialCusPo.setInteger("FAIL_CONSULTANT", DictCodeConstants.STATUS_IS_YES);
                potentialCusPo.saveIt();
                // a. 潜客跟进销售顾问。
                this.modifyConsultantOfSalesPromotion(potentialCusDto, potentialCusPo);

            } else if (DictCodeConstants.FO_LEVEL != potentialCusDto.getIntentLevel()) {
                // b. 潜客信息销售顾问，再分配时间取当前系统时间。前销售顾问取未分配前销售顾问。
                PotentialCusPO potentialCusPo = PotentialCusPO.findById(id);
                potentialCusPo.setString("LAST_CONSULTANT", potentialCusPo.getString("CONSULTANT"));// 前销售顾问
             /*   potentialCusPo.setString("CONSULTANT", potentialCusDto.getConsultant());// 销售顾问
*/                potentialCusPo.set("CONSULTANT_TIME", new Date());// 再分配时间
                potentialCusPo.saveIt();
                // a. 潜客跟进销售顾问。
                this.modifyConsultantOfSalesPromotion(potentialCusDto, potentialCusPo);
                // c. 销售订单销售顾问。
                modifySalesOrder(potentialCusDto, potentialCusPo);
                // d. 保有客户销售顾问。
                modifyCustomer(potentialCusDto, potentialCusPo);
            }
        }

    }

    
    /**
    * 保有客户销售顾问
    * @author zhanshiwei
    * @date 2016年10月20日
    * @param potentialCusDto
    * @param potentialCusPo
    */
    	
    private void modifyCustomer(PotentialCusDTO potentialCusDto, PotentialCusPO potentialCusPo) {
       /* List<Map> result = this.queryCustomerInfo(potentialCusPo.getString("POTENTIAL_CUSTOMER_NO"));
        for (int i = 0; i < result.size(); i++) {
            CustomerPO cusPo = CustomerPO.findById(result.get(i).get("customer_id"));
            cusPo.setString("CONSULTANT", potentialCusDto.getConsultant());// 销售顾问
            cusPo.saveIt();
        }*/
    }

    
    /**
    * 保有客户查询
    * @author zhanshiwei
    * @date 2016年10月20日
    * @param customerNo
    * @return
    */
    	
    public List<Map> queryCustomerInfo(String customerNo) {
        StringBuffer sb = new StringBuffer("select bcus.CUSTOMER_ID,bcus.DEALER_CODE from\n")
                .append("TM_CUSTOMER bcus\n")
                .append("INNER JOIN TM_VEHICLE ve on bcus.VEHICLE_ID=ve.VEHICLE_ID and bcus.DEALER_CODE=ve.DEALER_CODE\n")
                .append("INNER JOIN TM_VS_STOCK sto on sto.VIN=ve.VIN and sto.DEALER_CODE=ve.DEALER_CODE\n")
                .append("INNER JOIN TT_SALES_ORDER sa on sto.VS_STOCK_ID=sa.VS_STOCK_ID and sa.DEALER_CODE=sto.DEALER_CODE\n")
                .append("INNER JOIN TM_POTENTIAL_CUSTOMER cs on sa.CUSTOMER_NO=cs.POTENTIAL_CUSTOMER_NO and  sa.DEALER_CODE=cs.DEALER_CODE\n")
                .append("where cs.POTENTIAL_CUSTOMER_NO=?");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(customerNo);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
}

    /**
     * 销售订单销售顾问
     * 
     * @author zhanshiwei
     * @date 2016年10月20日
     * @param potentialCusDto
     * @param potentialCusPo
     */

    private void modifySalesOrder(PotentialCusDTO potentialCusDto, PotentialCusPO potentialCusPo) {
        List<Map> result = this.querySalesOrderByNo(potentialCusPo.getString("POTENTIAL_CUSTOMER_NO"));
        for (int i = 0; i < result.size(); i++) {
            SalesOrderPO salePo = SalesOrderPO.findById(result.get(i).get("so_no_id"));
           /* salePo.setString("CONSULTANT", potentialCusDto.getConsultant());// 销售顾问
*/            salePo.saveIt();
        }
    }

    /**
     * 销售订单查询
     * 
     * @author zhanshiwei
     * @date 2016年10月20日
     * @param customerNo
     * @return
     */

    public List<Map> querySalesOrderByNo(String customerNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("select CUSTOMER_NO,DEALER_CODE,SO_NO_ID from TT_SALES_ORDER where CUSTOMER_NO=? ");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(customerNo);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
    }

    /**
     * 更新潜客跟进销售顾问。
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param potentialCusDto
     * @param potentialCusPo
     */

    private void modifyConsultantOfSalesPromotion(PotentialCusDTO potentialCusDto, PotentialCusPO potentialCusPo) {
        List<Map> result = this.queryVisitingRecordByPId(potentialCusPo.getString("POTENTIAL_CUSTOMER_ID"));
        for (int i = 0; i < result.size(); i++) {
            SalesPromotionPO saleproPo = SalesPromotionPO.findById(result.get(i).get("promotion_id"));
           /* saleproPo.setString("CONSULTANT", potentialCusDto.getConsultant());// 销售顾问
*/            saleproPo.saveIt();
        }
    }
    private List<Map> queryCusNoByPoCusNo(String id,String entityCode){
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.CUSTOMER_NO, A.DEALER_CODE, A.SOLD_BY FROM");
        sql.append(" TM_CUSTOMER A INNER JOIN TT_PO_CUS_RELATION B");
        sql.append(" ON A.DEALER_CODE = B.DEALER_CODE");
        sql.append(" AND A.CUSTOMER_NO = B.CUSTOMER_NO");
        sql.append(" WHERE 1 = 1");
        sql.append(" AND A.DEALER_CODE = '" + entityCode + "'");
        sql.append(" AND A.D_KEY = " + DictCodeConstants.D_KEY);
        sql.append(" AND B.PO_CUSTOMER_NO = '" + id + "'");
        List<Object> queryList = new ArrayList<Object>();
        List<Map> result = DAOUtil.findAll(sql.toString(), queryList);
        return result;
    }
    /**
     * 潜客跟进查询根据潜客id
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param id 潜客ID
     * @return
     */

    private List<Map> queryVisitingRecordByPId(String id) {
        StringBuffer sb = new StringBuffer();
        sb.append("select PROMOTION_ID,DEALER_CODE from TT_SALES_PROMOTION where POTENTIAL_CUSTOMER_ID=? ");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
    }

    /**
     * 战败确认
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param potentialCusDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#modifyFailConsultant(com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCusDTO)
     */

    @Override
    public void modifyFailConsultant(PotentialCusListDTO potentialCusDto) throws ServiceBizException {
        for (int i = 0; i < potentialCusDto.getCusList().size(); i++) {
            /*
             * a. 潜客信息意向级别由FO级变更为F级。 b. 潜客信息战败时间取当前系统时间。
             */
            PotentialCusPO potentialCusPo = PotentialCusPO.findById(potentialCusDto.getCusList().get(i).getCusId());
            potentialCusPo.setInteger("INTENT_LEVEL", DictCodeConstants.F_LEVEL);
            potentialCusPo.set("DR_TIME", new Date());
            potentialCusPo.saveIt();
        }

    }

    /**
     * 转潜客
     * 
     * @author zhanshiwei
     * @date 2016年9月12日
     * @param id
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#ChangePotentialCus(java.lang.Long)
     */

    @Override
    public void ChangePotentialCus(Long id,PotentialCusDTO potentialCusDto) throws ServiceBizException {
        // 只可选择一条意向级别为D级的客户信息进行操作，更新以下信息：
        // a. 潜客信息意向级别由D级变更为N级，客户来源变更为保客。
        PotentialCusPO potentialCusPo = PotentialCusPO.findById(id);
        potentialCusPo.setInteger("INTENT_LEVEL", potentialCusDto.getIntentLevel());
        potentialCusPo.setInteger("CUS_SOURCE", DictCodeConstants.CUS_SOURCE_03);
        // 新增跟进任务
        SalesPromotionDTO salesProDto = new SalesPromotionDTO();
        List<Map> result = trackingtaskservice.queryTrackingTaskBylevel(potentialCusDto.getIntentLevel(), null);
        if (result != null && result.size() > 0) {
            salesProDto.setConsultant(potentialCusPo.getString("CONSULTANT"));
            salesProDto.setTaskName(result.get(0).get("task_name").toString());
            salesProDto.setScheduleDate(DateUtil.addDay(new Date(),
                                                        Integer.parseInt(result.get(0).get("interval_days").toString())));
            salesProDto.setPotentialCustomerId(potentialCusPo.getLongId());
           /* salesProDto.setPriorGrade(potentialCusDto.getIntentLevel());*/
            visitingrecordservice.addSalesPromotionInfo(salesProDto);

        }
        potentialCusPo.saveIt();
    }

    @Override
    public PageInfoDto queryCompanyName(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select ve.COMPANY_NAME,ve.DEALER_CODE,ve.CUSTOMER_NO");
     
        sb.append(" from TM_POTENTIAL_CUSTOMER ve \n");
       /* sb.append(" left join TM_EMPLOYEE em on ve.OWNER_NO=ow.OWNER_NO\n");*/
        sb.append(" where COMPANY_NAME IS NOT NULL");
       
        List<Object> queryList = new ArrayList<Object>();
        sb.append(" and IS_BIG_CUSTOMER = ? ");
        queryList.add(DictCodeConstants.IS_YES);
        if (!StringUtils.isNullOrEmpty(queryParam.get("companyName"))) {
            sb.append(" and COMPANY_NAME like ? ");
            queryList.add("%" + queryParam.get("companyName") + "%");
        }
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 潜客跟进更新战败车型
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param potentialCusDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#modifyPotentialCusforDefeat(com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCusDTO)
     */

    @Override
    public Long modifyPotentialCusforDefeat(PotentialCusDTO potentialCusDto) throws ServiceBizException {
        PotentialCusPO potentialCusPo = PotentialCusPO.findById(potentialCusDto.getCusId());
       /* potentialCusPo.setString("FAIL_MODEL", potentialCusDto.getFailModel());*/
       /* potentialCusPo.setString("DR_DESC", potentialCusDto.getDrDesc());
        potentialCusPo.setString("DR_CODE",potentialCusDto.getDrDescs()!=null?StringUtils.listToString(potentialCusDto.getDrDescs(), ','):"");
        potentialCusDto.setDrTime(new Date());
        potentialCusPo.setTimestamp("DR_TIME", potentialCusDto.getDrTime());
        potentialCusPo.setInteger("AIL_INTENT_LEVEL", potentialCusDto.getAilIntentLevel());*/
        potentialCusPo.saveIt();
        return potentialCusPo.getLongId();
    }

    /**
     * 潜客跟进选择潜客查询
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryPotentialCusForSalesPromotion(java.util.Map)
     */

    @Override
    public PageInfoDto queryPotentialCusForSalesPromotion(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
      //  sb.append("select c.POTENTIAL_CUSTOMER_ID,c.DEALER_CODE,c.CUSTOMER_NAME,c.POTENTIAL_CUSTOMER_NO,c.FOUND_DATE,c.INTENT_LEVEL,\n");
        sb.append("select c.ORGANIZATION_ID,em.EMPLOYEE_NO,c.POTENTIAL_CUSTOMER_ID,c.DEALER_CODE,c.CUSTOMER_NAME,c.POTENTIAL_CUSTOMER_NO,c.FOUND_DATE,c.INTENT_LEVEL,\n");//数据权限范围控制
        sb.append(" c.CONSULTANT,em.EMPLOYEE_NAME,\n");
        sb.append(" ci.QUOTED_PRICE,ci.REMARK,ci.INTENT_BRAND,ci.INTENT_SERIES,ci.INTENT_MODEL,ci.INTENT_CONFIG,\n");
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,pa.CONFIG_NAME,mo.MODEL_NAME\n");
        sb.append(" from TM_POTENTIAL_CUSTOMER c\n");
        sb.append(" left join TM_EMPLOYEE em  on c.CONSULTANT=em.EMPLOYEE_NO\n");
        sb.append(" left join   TT_CUSTOMER_INTENT    ci on c.POTENTIAL_CUSTOMER_ID=ci.POTENTIAL_CUSTOMER_ID and IS_MAIN_INTENT=?\n");
        sb.append(" left  join   TM_PACKAGE pa   on   ci.INTENT_CONFIG=pa.CONFIG_CODE and pa.DEALER_CODE=c.DEALER_CODE\n");
        sb.append(" left  join   TM_MODEL   mo   on   ci.INTENT_MODEL=mo.MODEL_CODE   and mo.DEALER_CODE=c.DEALER_CODE\n");
        sb.append(" left  join   TM_SERIES  se   on   ci.INTENT_SERIES=se.SERIES_CODE and se.DEALER_CODE=c.DEALER_CODE\n");
        sb.append(" left  join   tm_brand   br   on   ci.INTENT_BRAND = br.BRAND_CODE and br.DEALER_CODE=c.DEALER_CODE\n");

        sb.append(" where 1=1");
        sb.append(" and c.INTENT_LEVEL  in (?,?,?,?,?)");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        queryList.add(DictCodeConstants.H_LEVEL);
        queryList.add(DictCodeConstants.A_LEVEL);
        queryList.add(DictCodeConstants.B_LEVEL);
        queryList.add(DictCodeConstants.C_LEVEL);
        queryList.add(DictCodeConstants.N_LEVEL);

        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList,"201004");
        return id;
    }
    
    /**
     * 展厅接待潜客查询条件
     * @author Benzc
     * @date 2016年12月29日
     * @param sb
     * @param queryParam
     * @param queryList
     */
    public void setHere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" and C.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
            sb.append(" and C.CONTACTOR_PHONE = ?");
            queryList.add(queryParam.get("contactorPhone"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
            sb.append(" and C.CONTACTOR_MOBILE = ?");
            queryList.add(queryParam.get("contactorMobile"));
        }
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年2月23日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#CheckCustomerContactorMobile(java.lang.String)
    */
    	
    @Override
    public String CheckCustomerContactorMobile(String id) throws ServiceBizException {
        String msg="0";
        StringBuffer sb = new StringBuffer();
        sb.append("select CUSTOMER_NO,DEALER_CODE from TM_POTENTIAL_CUSTOMER where CONTACTOR_MOBILE= ? ");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(result!=null&&result.size()>0){
             msg="1";
        }
        return msg;
    }
    
    
    /**
    * @author LiGaoqi
    * @date 2017年3月9日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#PhoneOrMobile(java.lang.String)
    */
    	
    @Override
    public String PhoneOrMobile(String id) throws ServiceBizException {
        System.out.println(id);
        System.out.println(id);
       String msg="0";
   /*    String reg="(?:(\\(\\+?86\\))(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)|" +  
               "(?:(86-?)?(0[0-9]{2,3}\\-?)?([2-9][0-9]{6,7})+(\\-[0-9]{1,4})?)"; */
       
       if(id.length()>9){
           Pattern p1 = Pattern.compile("^[0][1-9]{2,3}-[0-9]{5,8}$");
           Matcher m1 = p1.matcher(id);
           if(m1.matches()){
               msg="2";
           }
       }else{
           Pattern p2 = Pattern.compile("^[1-9]{1}[0-9]{5,8}$");
           Matcher m2 = p2.matcher(id);
           if(m2.matches()){
               msg="2";
           } 
       }
       Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
       Matcher m = p.matcher(id);
       if(m.matches()){
           msg="3";//手机
           System.out.println("手机");
       }
        return msg;
    }
    /**
    * @author LiGaoqi
    * @date 2017年2月23日
    * @param id
    * @param customerNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#CheckCustomerContactorMobile(java.lang.String, java.lang.String)
    */
    	
    @Override
    public String CheckCustomerContactorMobile(String id, String customerNo) throws ServiceBizException {
        String msg="0";
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        PotentialCusPO cuspo =PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),customerNo);
        if(cuspo!=null){
            if(!id.equals(cuspo.getString("CONTACTOR_MOBILE"))){
                StringBuffer sb = new StringBuffer();
                sb.append("select CUSTOMER_NO,DEALER_CODE from TM_POTENTIAL_CUSTOMER where CONTACTOR_MOBILE= ? ");
                List<Object> queryList = new ArrayList<Object>();
                queryList.add(id);
                List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
                if(result!=null&&result.size()>0){
                    msg="1";
                }
            }
        }
        return msg;
    }
    
    
    /**
    * @author LiGaoqi
    * @date 2017年3月13日
    * @param id
    * @param customerNo
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#CheckCusSourse(java.lang.String, java.lang.String)
    */
    	
    @Override
    public String CheckCusSourse(String id, String customerNo) throws ServiceBizException {
        String sourse="0";
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        PotentialCusPO cuspo =PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),customerNo);
        if(cuspo!=null){
            if(!id.equals(cuspo.getString("CUS_SOURCE"))){
                if(!StringUtils.isNullOrEmpty(cuspo.getString("CUS_SOURCE"))&&cuspo.getString("CUS_SOURCE").equals("13111021")){
                    sourse="1";
                }
              if(id.equals("13111021")){
                    sourse="2";
              }
            }else{
                sourse ="0" ;
            }
          

        }
        return sourse;
    }
    /**
    * @author LiGaoqi
    * @date 2017年2月27日
    * @param dto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#addInfo(com.yonyou.dms.customer.domains.DTO.customerManage.PotentialCustomerImportDTO)
    */
    	
    @Override
    public void addInfo(PotentialCustomerImportDTO dto) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        PotentialCusPO cuspo = new PotentialCusPO();
        if(StringUtils.isNullOrEmpty(dto.getCustomerName())||StringUtils.isNullOrEmpty(dto.getCusSource())
                ||StringUtils.isNullOrEmpty(dto.getGender())||StringUtils.isNullOrEmpty(dto.getIsToShop())
                ||StringUtils.isNullOrEmpty(dto.getIntentLevel())||dto.getCusSource()==DictCodeConstants.DICT_CUS_SOURCE_BY_WEB||dto.getCusSource()==DictCodeConstants.DICT_CUS_SOURCE_BY_DCC){
            throw new ServiceBizException("客户姓名、客户来源、性别、联系手机、意向级别、是否到店、为必填项且不能导入客户来源为官网客户和DCC转入的客户!"); 
        }else{
            String msg = this.CheckCustomerContactorMobile(dto.getContactorMobile());
            if(msg.equals("1")){
                throw new ServiceBizException("客户姓名为:"+dto.getCustomerName()+"的联系手机"+dto.getContactorMobile()+"重复,不能导入！");
            }else{
                if(dto.getIntentLevel()!=13101001&&dto.getIntentLevel()!=13101002&&dto.getIntentLevel()!=13101003&&dto.getIntentLevel()!=13101004&&dto.getIntentLevel()!=13101005){
                    throw new ServiceBizException("意向级别必须是ＨＡＢＣＮ的一种!"); 
                }else{
                    if(dto.getIsTestDrive()==12781001&&StringUtils.isNullOrEmpty(dto.getTestDriveDate())){
                        throw new ServiceBizException(dto.getCustomerName()+"的试乘试驾时间不能为空!"); 
                    }else{
                        //根据姓名找到id 如果没有就是userId
                        String useid = loginInfo.getUserId().toString();
                       
                        if(!StringUtils.isNullOrEmpty(dto.getSoldBy())){
                            List<Map> eresult =this.searchEmployee(dto.getSoldBy());
                            if(eresult!=null&&eresult.size()>0){
                                List<Map> uResult =this.searchSoldBy(eresult.get(0).get("EMPLOYEE_NO").toString());
                                if(uResult!=null&&uResult.size()>0){
                                    useid=uResult.get(0).get("USER_ID").toString();
                                }
                            }
                        }
                        cuspo.setString("SOLD_BY", useid);
                        cuspo.setString("OWNED_BY", useid);
                        String customerNo = commonNoService.getSystemOrderNo(CommonConstants.POTENTIAL_CUSTOMER_PREFIX);
                        Long intentId = commonNoService.getId("ID");
                        cuspo.setString("CUSTOMER_NAME", dto.getCustomerName());
                        cuspo.setInteger("CUSTOMER_TYPE", dto.getCustomerType());
                        cuspo.setInteger("CUS_SOURCE", dto.getCusSource());
                        cuspo.setInteger("CUSTOMER_TYPE", dto.getCustomerType());
                        cuspo.setInteger("GENDER", dto.getGender());
                        cuspo.setDate("FOUND_DATE", new Date());
                        cuspo.setString("CONTACTOR_MOBILE", dto.getContactorMobile());
                        cuspo.setInteger("INIT_LEVEL", dto.getIntentLevel());
                        cuspo.setInteger("INTENT_LEVEL", dto.getIntentLevel());
                        cuspo.setInteger("IS_TO_SHOP", dto.getIsToShop());
                        cuspo.setLong("INTENT_ID", intentId);
                        cuspo.setString("CUSTOMER_NO", customerNo);
                        cuspo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                        cuspo.saveIt();
                      //end lim 客户主表
                        TtCusIntentPO intentpo = new TtCusIntentPO();
                        intentpo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                        intentpo.setLong("INTENT_ID", intentId);
                        intentpo.setString("CUSTOMER_NO", customerNo);
                        intentpo.setString("OWNED_BY", useid);
                        intentpo.setInteger("IS_TEST_DRIVE", dto.getIsTestDrive());
                        intentpo.setDate("TEST_DRIVE_DATE", dto.getTestDriveDate());
                        intentpo.saveIt();
                     // 意向 end
                        TtCustomerIntentDetailPO detailpo = new TtCustomerIntentDetailPO();
                        detailpo.setLong("INTENT_ID", intentId);
                       /* detailpo.setString("CUSTOMER_NO", customerNo);*/
                        detailpo.setInteger("D_KEY", Integer.parseInt(DictCodeConstants.D_KEY));
                        detailpo.setString("OWNED_BY", useid);
                        detailpo.setInteger("IS_MAIN_MODEL", Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                        if(StringUtils.isNullOrEmpty(dto.getIntentModel())){
                            TmModelPO modelpo = TmModelPO.findByCompositeKeys(dto.getIntentModel(),loginInfo.getDealerCode());
                            if(modelpo!=null){
                                if(StringUtils.isNullOrEmpty(modelpo.getString("BRAND_CODE"))){
                                    detailpo.setString("INTENT_BRAND", modelpo.getString("BRAND_CODE"));
                                }
                                if(StringUtils.isNullOrEmpty(modelpo.getString("MODEL_CODE"))){
                                    detailpo.setString("INTENT_MODEL", modelpo.getString("MODEL_CODE"));
                                }
                                if(StringUtils.isNullOrEmpty(modelpo.getString("BRAND_CODE"))){
                                    detailpo.setString("INTENT_SERIES", modelpo.getString("SERIES_CODE"));
                                }
                            }
                        }
                        detailpo.saveIt();
                    }
                 
                }
                
                //
            }
        }
     
        
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年2月27日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#searchSoldBy()
    */
    	
    @Override
    public List<Map> searchSoldBy(String no) throws ServiceBizException {
        String sql = "SELECT DEALER_CODE,USER_ID,USER_NAME FROM TM_USER";
        return DAOUtil.findAll(sql, null);
    }
    
    
    public List<Map> searchEmployee(String name) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String sql = "SELECT * FROM TM_EMPLOYEE where EMPLOYEE_NAME= ? AND DEALER_CODE= ? AND IS_VALID=12781001";
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(name);
        queryList.add(loginInfo.getDealerCode());
        return DAOUtil.findAll(sql, queryList);
    }
    
    /**
    * @author LiGaoqi
    * @date 2017年3月2日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#searchSeriesName(java.lang.String)
    */
    	
    @Override
    public String searchSeriesName(String id) throws ServiceBizException {
        String msg="";
        StringBuffer sb = new StringBuffer();
        System.out.println(id);
        sb.append("select SERIES_CODE,SERIES_NAME,DEALER_CODE from TM_SERIES where SERIES_CODE= ? ");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(result!=null&&result.size()>0){
             System.out.println(result.get(0).get("SERIES_NAME").toString());
             msg=result.get(0).get("SERIES_NAME").toString();
        }
        System.out.println(msg);
        return msg;
    }
    
    
    /**
    * @author LiGaoqi
    * @date 2017年3月29日
    * @param s
    * @param m
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#searchModelName(java.lang.String, java.lang.String)
    */
    	
    @Override
    public String searchModelName(String s, String m) throws ServiceBizException {
        String msg="";
        StringBuffer sb = new StringBuffer();
        sb.append("select MODEL_CODE,MODEL_NAME,DEALER_CODE from TM_MODEL where SERIES_CODE= ? AND MODEL_CODE= ?");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(s);
        queryList.add(m);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(result!=null&&result.size()>0){
             System.out.println(result.get(0).get("MODEL_NAME").toString());
             msg=result.get(0).get("MODEL_NAME").toString();
        }
        System.out.println(msg);
        return msg;
    }
    
    
    
    /**
    * @author LiGaoqi
    * @date 2017年3月29日
    * @param s
    * @param m
    * @param c
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#searchConfigName(java.lang.String, java.lang.String, java.lang.String)
    */
    	
    @Override
    public String searchConfigName(String m, String c) throws ServiceBizException {

        

        String msg="";
        StringBuffer sb = new StringBuffer();
        sb.append("select CONFIG_CODE,CONFIG_NAME,DEALER_CODE from TM_CONFIGURATION where  MODEL_CODE= ? AND CONFIG_CODE= ? ");
        List<Object> queryList = new ArrayList<Object>();

        queryList.add(m);
        System.out.println("ccccccccccccccccc");
        System.out.println(c);
        queryList.add(c);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(result!=null&&result.size()>0){
             System.out.println(result.get(0).get("CONFIG_NAME").toString());
             msg=result.get(0).get("CONFIG_NAME").toString();
        }
        System.out.println(msg);
        return msg;
    
    }
    
    
    /**
    * @author LiGaoqi
    * @date 2017年4月25日
    * @param ID
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#CheckIsCustomer(java.lang.String)
    */
    	
    @Override
    public String CheckIsCustomer(String ID) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        String msg="1";
        StringBuffer sb = new StringBuffer();
        sb.append("select * from tm_user_CTRL where DEALER_CODE= ? AND USER_ID= ? AND CTRL_CODE= '80900000' ");
        List<Object> queryList = new ArrayList<Object>();

        queryList.add(loginInfo.getDealerCode());
        queryList.add(loginInfo.getUserId());
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(result!=null&&result.size()>0){
             
             msg="2";
        }
        return msg;
    
    
    }
    /**
    * @author LiGaoqi
    * @date 2017年3月15日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#querySleepSeries(java.util.Map)
    */
    	
    @Override
    public List<Map> querySleepSeries(Map<String, String> queryParam) throws ServiceBizException {
         
        StringBuilder sqlSb = new StringBuilder("");
        sqlSb.append(" SELECT SLEEP_SERIES_CODE,SLEEP_SERIES_NAME FROM TM_SLEEP_SERIES " +
                " WHERE INTENT_SERIER_CODE='whatever' ");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
         sqlSb.append(" OR INTENT_SERIER_CODE='"+queryParam.get("code")+"'");

     }
        System.out.println(sqlSb);
        List<Map> result = Base.findAll(sqlSb.toString(),params.toArray());
        return result;
    }
    
    public List<Map> queryLinkMan(String customerNo) throws ServiceBizException {
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        
        StringBuilder sqlSb = new StringBuilder("");
        sqlSb.append(" select * from TT_PO_CUS_LINKMAN where CUSTOMER_NO='"+customerNo+"' AND DEALER_CODE="+loginInfo.getDealerCode()+" AND IS_DEFAULT_CONTACTOR =12781001 ");
        List<Object> params = new ArrayList<Object>();
 
        System.out.println(sqlSb);
        List<Map> result = Base.findAll(sqlSb.toString(),params.toArray());
        
        return result;
    }
    
    
}
