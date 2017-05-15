
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : VisitingRegisterServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2016年12月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月28日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.SalesPromotionPO;
import com.yonyou.dms.common.domains.PO.basedata.VisitingRecordPO;
import com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitingIntentDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO;
import com.yonyou.dms.customer.domains.PO.customerManage.VisitingIntentPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
* TODO description
* @author Administrator
* @date 2016年12月28日
*/
@Service
@SuppressWarnings("rawtypes")
public class VisitingRegisterServiceImpl implements VisitingRegisterService {
    
//    @Autowired
//    private TrackingTaskService trackingtaskservice;
    
    /**
    * @author Administrator
    * @date 2016年12月28日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#queryVisitingRecordInfo(java.util.Map)
    */

    @Override
    public PageInfoDto queryVisitingRecordInfo(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT V.ITEM_ID,V.DEALER_CODE,CASE WHEN V.IS_FIRST_VISIT=12781001 THEN 10571001 END AS IS_FIRST_VISIT,"
                + "CASE WHEN V.IS_VALID=12781001 THEN 10571001 END AS IS_VALID,"
                + "CASE WHEN V.IS_TEST_DRIVE=12781001 THEN 10571001 END AS IS_TEST_DRIVE,"
                + "V.CUSTOMER_NO,V.CUS_SOURCE,V.CUSTOMER_NAME,"
                + "V.CONTACTOR_NAME,V.CONTACTOR_PHONE,V.CONTACTOR_MOBILE,V.COMPLAINT_RESULT,V.INTENT_LEVEL,V.INTENT_MODEL,V.INTENT_CONFIG,"
                + "V.COLOR_CODE,V.MEDIA_TYPE,V.VISITOR,em.EMPLOYEE_NAME,V.NEXT_CONSULTANT,V.VISIT_TYPE,V.FIRST_QUOTA,V.VISIT_TIME,V.LEAVE_TIME,V.SCENE,"
                + "V.CAMPAIGN_CODE,V.BETTER_VISIT_PLACE,V.BETTER_VISIT_TIME,V.MEDIA_DETAIL,V.GENDER,V.CONTACT_TIME,"
                + "CASE WHEN (CASE WHEN V.CONTACT_TIME != 0 THEN 12781001 ELSE 12781002 END)=12781001  THEN 10571001 END AS IS_FINISH, "
                + "Q.VISITING_INTENT_ID,Q.INTENT_BRAND,Q.INTENT_SERIES,Q.INTENT_COLOR");
        sb.append(" from TT_VISITING_RECORD V\n");
        sb.append(" LEFT JOIN TT_VISITING_INTENT_DETAIL Q  ON (V.ITEM_ID=Q.ITEM_ID AND V.DEALER_CODE=Q.DEALER_CODE )\n");
        sb.append(" LEFT JOIN TM_EMPLOYEE em ON V.SOLD_BY=em.EMPLOYEE_NO");
        sb.append(" where 1=1");        
        
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }
    
    /**
     * 查询条件设置
     * 
     * @author zhanshiwei,Benzc
     * @date 2016年9月7日,2016年12月26日
     * @param sb
     * @param queryParam
     * @param queryList
     */

    public void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
//        String perfromStatus = queryParam.get("perfromStatus");
       
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and V.CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
            sb.append(" and V.CONTACTOR_PHONE like ?");
            queryList.add("%" + queryParam.get("contactorPhone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
            sb.append(" and V.CONTACTOR_MOBILE like ?");
            queryList.add("%" + queryParam.get("contactorMobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
            sb.append(" and V.INTENT_LEVEL = ?");
            queryList.add(queryParam.get("intentLevel"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" and V.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("visitTime"))) {
            sb.append(" and V.VISIT_TIME >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("visitTime")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("visitTime"))) {
            sb.append(" and V.VISIT_TIME < ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("visitTime")));
        }
       
        if (!StringUtils.isNullOrEmpty(queryParam.get("cusSource"))) {
            sb.append(" and V.CUS_SOURCE like ?");
            queryList.add("%" + queryParam.get("cusSource") + "%");
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("visitType"))) {
            sb.append(" and V.VISIT_TYPE = ?");
            queryList.add(Integer.parseInt(queryParam.get("visitType")));
        }
       
        if (!StringUtils.isNullOrEmpty(queryParam.get("leaveTime"))) {
            sb.append(" and V.LEAVE_TIME >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("leaveTime")));
        }
        
        if (!StringUtils.isNullOrEmpty(queryParam.get("leaveTime"))) {
            sb.append(" and V.LEAVE_TIME < ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("leaveTime")));
        }       
        if (!StringUtils.isNullOrEmpty(queryParam.get("isValid"))) {
            sb.append(" and V.IS_VALID = ?");
            queryList.add(Integer.parseInt(queryParam.get("isValid")));
        }
       
        if (!StringUtils.isNullOrEmpty(queryParam.get("isFirstVisit"))) {
            sb.append(" and V.IS_FIRST_VISIT = ?");
            queryList.add(Integer.parseInt(queryParam.get("isFirstVisit")));
        }
      
        if (!StringUtils.isNullOrEmpty(queryParam.get("isTestDrive"))) {
            sb.append(" and V.IS_TEST_DRIVE = ?");
            queryList.add(Integer.parseInt(queryParam.get("isTestDrive")));
        }
      
        
    }

    /**
    * @author Administrator
    * @date 2016年12月28日
    * @param visitDto
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#addVisitingRecordInfo(com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO)
    */

    @Override
    public Long addVisitingRecordInfo(VisitingRecordDTO visitDto) throws ServiceBizException {
        // 验证主要意向的个数是否正确
        
        
        if (StringUtils.isNullOrEmpty(visitDto.getCustomerNo())) {
            if (checkoutMobile(visitDto.getContactorPhone(), visitDto.getCustomerNo())) {
                throw new ServiceBizException("手机号已存在!");
            }
        }
  
        VisitingRecordPO visitPo = new VisitingRecordPO();
        this.setVisitingRecord(visitPo, visitDto);
        visitPo.saveIt();
     /*   if (visitDto.getIntentList().size() > 0 && visitDto.getIntentList() != null) {
            for (VisitingIntentDTO intentDto : visitDto.getIntentList()) {
                visitPo.add(getVisitingIntent(intentDto));
            }
        }*/
        // 1.客户编号已存在，且客户信息中是否首次到店为否，则自动更新客户信息中，是否首次到店，首次到店时间(展厅来访时间)
        // 2.客户编号已存在，且客户信息中是否二次到店为否，则自动更新客户信息中，是否二次到店，二次到店时间(展厅来访时间)
        this.modifyPotentialCusByid(visitDto);
       // 来访方式为来店或来电时 保存后对应更新潜客跟进“邀约是否到店”为是。
        modifySalesPromotion(visitDto);

        return visitPo.getLongId();
    }
    /**
     * @author Administrator
     * @date 2016年12月28日
     * @param salesProDto
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#addSalesPromotionInfo(com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO)
     */

     @Override
     public Long addSalesPromotionInfo(SalesPromotionDTO salesProDto) throws ServiceBizException {
        SalesPromotionPO salesProPo = new SalesPromotionPO();
        this.setSalesPromotionPo(salesProPo, salesProDto);
        salesProPo.saveIt();
        return null;
    }
    
    /**
     * 设置 SalesPromotionPO 属性
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param salesProPo
     * @param salesProDto
     */

    public void setSalesPromotionPo(SalesPromotionPO salesProPo, SalesPromotionDTO salesProDto) throws ServiceBizException {
        salesProPo.setDate("SCHEDULE_DATE", salesProDto.getScheduleDate());// 计划日期
        salesProPo.setString("TASK_NAME", salesProDto.getTaskName());
        salesProPo.setString("CONSULTANT", salesProDto.getConsultant());
        salesProPo.setInteger("PROM_RESULT", DictCodeConstants.TRACKING_RESULT_NOT);// 未跟进
        salesProPo.setInteger("PRIOR_GRADE", salesProDto.getPriorGrade());
        salesProPo.setLong("POTENTIAL_CUSTOMER_ID", salesProDto.getPotentialCustomerId());// ID
    }
    /**
     * 1.客户编号已存在，且客户信息中是否首次到店为否，则自动更新客户信息中，是否首次到店，首次到店时间(展厅来访时间)
     * 2.客户编号已存在，且客户信息中是否二次到店为否，则自动更新客户信息中，是否二次到店，二次到店时间(展厅来访时间)
     * 
     * @author zhanshiwei,Benzc
     * @date 2016年9月9日,2016年12月27日
     * @param visiting_record_id
     * @param visitDto
     */

    public void modifyPotentialCusByid(VisitingRecordDTO visitDto) throws ServiceBizException {
        if (!StringUtils.isNullOrEmpty(visitDto.getCustomerNo())) {
            String sql = "select CUSTOMER_NO,DEALER_CODE from TM_POTENTIAL_CUSTOMER where CUSTOMER_NO =?";
            List<Object> queryParams = new ArrayList<Object>();
            queryParams.add(visitDto.getCustomerNo());
            
            PotentialCusPO potentialCusPo = PotentialCusPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),DAOUtil.findFirst(sql,queryParams).get("CUSTOMER_NO"));
            if (!StringUtils.isNullOrEmpty(potentialCusPo.getInteger("FIRST_IS_ARRIVED"))&&potentialCusPo.getInteger("FIRST_IS_ARRIVED")== DictCodeConstants.STATUS_IS_NOT) {
                potentialCusPo.setTimestamp("ARRIVE_TIME", visitDto.getVisitTime());
                potentialCusPo.setInteger("FIRST_IS_ARRIVED", visitDto.getIsFirstTehShop());
            }
            if (!StringUtils.isNullOrEmpty(potentialCusPo.getInteger("IS_SECOND_TEH_SHOP"))&&potentialCusPo.getInteger("IS_SECOND_TEH_SHOP")== DictCodeConstants.STATUS_IS_NOT) {
                potentialCusPo.setTimestamp("SECOND_ARRIVE_TIME", visitDto.getVisitTime());
                potentialCusPo.setInteger("IS_SECOND_TEH_SHOP", visitDto.getIsSecondTehShop());
            }
            potentialCusPo.saveIt();
        }

    }
    /**
     * 来访方式为来店或来电时 保存后对应更新潜客跟进“邀约是否到店”为是
     * 
     * @author zhanshiwei
     * @date 2016年9月18日
     * @param visitDto
     */

    public void modifySalesPromotion(VisitingRecordDTO visitDto) throws ServiceBizException {
        if (visitDto.getVisitType() == DictCodeConstants.COMPLAINT_VISIT_TYPE_03
            || visitDto.getVisitType() == DictCodeConstants.COMPLAINT_VISIT_TYPE_02) {
            String sql = "select pro.PROMOTION_ID,pro.DEALER_CODE from TT_SALES_PROMOTION_PLAN pro  where POTENTIAL_CUSTOMER_ID=(select pcu.POTENTIAL_CUSTOMER_ID from TM_POTENTIAL_CUSTOMER pcu where POTENTIAL_CUSTOMER_NO =? and pro.DEALER_CODE=pcu.DEALER_CODE )  and BOOKING_DATE >=? and BOOKING_DATE <?";
            List<Object> queryList = new ArrayList<Object>();
            queryList.add(visitDto.getCustomerNo());
            SimpleDateFormat sdf = new SimpleDateFormat(CommonConstants.SIMPLE_DATE_FORMAT);
            String resultDate = "";
            resultDate = sdf.format(new Date());
            queryList.add(DateUtil.parseDefaultDate(resultDate));
            queryList.add(DateUtil.addOneDay(resultDate));
            List<Map> result = DAOUtil.findAll(sql, queryList);
            for (int i = 0; i < result.size(); i++) {
                SalesPromotionPO salesProPo = SalesPromotionPO.findById(result.get(i).get("promotion_id"));
                salesProPo.setInteger("BOOKING_IS_ARRIVE", DictCodeConstants.STATUS_IS_YES);
                salesProPo.saveIt();
            }
        }
    }
    
    /**
     * 设置VisitingRecordPO属性
     * 
     * @author zhanshiwei,Benzc
     * @date 2016年9月22日,2016年12月27日
     * @param visitPo
     * @param visitDto
     */

    public void setVisitingRecord(VisitingRecordPO visitPo, VisitingRecordDTO visitDto) throws ServiceBizException {
        visitPo.setString("CUSTOMER_NO", visitDto.getCustomerNo());// 客户编码
        visitPo.setString("CUSTOMER_NAME", visitDto.getCustomerName());// 客户名称
        visitPo.setString("CONTACTOR_PHONE", visitDto.getContactorPhone());// 联系电话
        visitPo.setString("CONTACTOR_MOBILE", visitDto.getContactorMobile());// 联系手机
        visitPo.setString("CONTACTOR_NAME", visitDto.getContactorName());// 联系人
        visitPo.setInteger("GENDER", visitDto.getGender());// 性别
        if(visitDto.getVisitTime()==null){
            visitPo.setTimestamp("VISIT_TIME",System.currentTimeMillis());// 来访时间
        }else if(visitDto.getVisitTime()!=null){
            visitPo.setTimestamp("VISIT_TIME", visitDto.getVisitTime());// 来访时间
        }
        visitPo.setInteger("VISIT_TYPE", visitDto.getVisitType());// 来访方式
        visitPo.setString("SOLD_BY", visitDto.getSoldBy());// 销售顾问
        visitPo.setString("VISITOR", visitDto.getVisitor());// 来访人数
        visitPo.setInteger("INTENT_LEVEL", visitDto.getIntentLevel());// 意见级别
        visitPo.setInteger("CUS_SOURCE", visitDto.getCusSource());// 客户来源
        visitPo.setInteger("MEDIA_TYPE", visitDto.getMediaType());// 信息渠道
        visitPo.setInteger("IS_VALID", visitDto.getIsValid());// 是否有效
        visitPo.setInteger("IS_FIRST_VISIT", visitDto.getIsFirstVisit());// 是否首次
        visitPo.setString("REMARK", visitDto.getRemark());// 备注
        visitPo.setString("SCENE", visitDto.getScene());// SCENE
       // visitPo.setTimestamp("LEAVE_TIME", visitDto.getLeaveTime());// 离店时间
        if(visitDto.getLeaveTime()!=null){
         visitPo.setString("CONTACT_TIME", DateUtil.toCompareTime(visitDto.getLeaveTime(), visitDto.getVisitTime()));// 接触时长
        }else if(visitDto.getLeaveTime()==null){
         visitPo.setTimestamp("CONTACT_TIME", visitDto.getContactTime());//接触时长 
        }

    }
    
    /**
     * 设置VisitingIntentPO属性
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param intentDto
     * @return
     */

    public VisitingIntentPO getVisitingIntent(VisitingIntentDTO intentDto) {
        VisitingIntentPO intentPo = new VisitingIntentPO();
        intentPo.setString("INTENT_BRAND", intentDto.getIntentBrand());// 意向品牌
        intentPo.setString("INTENT_MODEL", intentDto.getIntentModel());// 意向车型
        intentPo.setString("INTENT_SERIES", intentDto.getIntentSeries());// 意向车系
        intentPo.setString("INTENT_CONFIG", intentDto.getIntentConfig());// 意向配置
        intentPo.setString("INTENT_CONFIG", intentDto.getIntentConfig());// 意向配置
        intentPo.setString("INTENT_COLOR", intentDto.getIntentColor());// 意向颜色
        intentPo.setInteger("IS_MAIN_MODEL", intentDto.getIsMainModel());// 主要意向
        intentPo.setDouble("QUOTED_AMOUNT", intentDto.getQuotedAmount());// 意向报价
        intentPo.setString("QUOTED_REMARK", intentDto.getQuotedRemark());// 备注
        return intentPo;
    }

    /**
    * @author Administrator
    * @date 2016年12月28日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#queryVisitingRecordInfoByid(java.lang.Long)
    */

    @Override
    public VisitingRecordPO queryVisitingRecordInfoByid(Long id) throws ServiceBizException {
    	String dealerCode  = FrameworkUtil.getLoginInfo().getDealerCode();
    	
        return VisitingRecordPO.findByCompositeKeys(id,dealerCode);
    }

    /**
    * @author Administrator
    * @date 2016年12月28日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#queryVisitIntentInfoByParendId(java.lang.Long)
    */

    @Override
    public List<Map> queryVisitIntentInfoByParendId(Long id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT VISITING_INTENT_ID,INTENT_BRAND,INTENT_SERIES,INTENT_MODEL,INTENT_CONFIG,INTENT_COLOR,IS_MAIN_SERIES,IS_MAIN_MODEL,QUOTED_AMOUNT \n ");
        sb.append("from TT_VISITING_INTENT_DETAIL where VISITING_INTENT_ID=?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(id);
        return Base.findAll(sb.toString(), queryParams.toArray());
    }

    /**
    * @author Administrator
    * @date 2016年12月28日
    * @param mobile
    * @param cusno
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#checkoutMobile(java.lang.String, java.lang.String)
    */

    @Override
    public boolean checkoutMobile(String mobile, String cusno) throws ServiceBizException {
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        sb.append("select CONTACTOR_MOBILE,DEALER_CODE from TM_POTENTIAL_CUSTOMER where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        sb.append(" and CONTACTOR_MOBILE=?\n");
        queryList.add(mobile);
        if (!StringUtils.isNullOrEmpty(cusno)) {
            sb.append("and CUSTOMER_NO <> ? ");
            queryList.add(cusno.trim());
        }
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if (result.size() > 0) {
            flag = true;
        }
        return flag;
    }
    
    /**
     * 厅接待修改
    * @author Administrator
    * @date 2016年12月28日
    * @param id
    * @param visitDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#modifyVisitingRecordInfo(java.lang.Long, com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO)
    */

    @Override
    public void modifyVisitingRecordInfo(Long id, VisitingRecordDTO visitDto) throws ServiceBizException {
//        if(StringUtils.isNullOrEmpty(visitDto.toString())){
//            VisitingRecordPO visitPo=VisitingRecordPO.findById(id);
//            visitPo.setTimestamp("LEAVE_TIME", System.currentTimeMillis());// 离店时间
//            visitPo.saveIt();
//            
//        }
    	String dealerCode  = FrameworkUtil.getLoginInfo().getDealerCode();
        if (StringUtils.isNullOrEmpty(visitDto.getCustomerNo())) {
            if (checkoutMobile(visitDto.getContactorPhone(), visitDto.getCustomerNo())) {
                throw new ServiceBizException("手机号已存在!");
            }
        }
        // 验证主要意向的个数是否正确
       // this.MainIntentSum(visitDto);
        VisitingRecordPO visitPo = VisitingRecordPO.findByCompositeKeys(id,dealerCode);
//        VisitingRecordPO visitPo = VisitingRecordPO.findById(id);
        this.setVisitingRecord(visitPo, visitDto);
        visitPo.saveIt();

        VisitingIntentPO.delete("ITEM_ID = ?", id);

//        if (StringUtils.isNullOrEmpty(visitDto.getIntentList())) {
//            for (VisitingIntentDTO intentDto : visitDto.getIntentList()) {
//                visitPo.add(getVisitingIntent(intentDto));
//            }
//        }

        // 1.客户编号已存在，且客户信息中是否首次到店为否，则自动更新客户信息中，是否首次到店，首次到店时间(展厅来访时间)
        // 2.客户编号已存在，且客户信息中是否二次到店为否，则自动更新客户信息中，是否二次到店，二次到店时间(展厅来访时间)
       // this.modifyPotentialCusByid(visitDto);
        // 来访方式为来店或来电时 保存后对应更新潜客跟进“邀约是否到店”为是。
       // modifySalesPromotion(visitDto);
    }
    
    /**
     * 展厅主要意向验证只能有一条
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param visitDto
     */

    public void MainIntentSum(VisitingRecordDTO visitDto) {
        int n = 0;
        if (StringUtils.isNullOrEmpty(visitDto.getIntentList())) {
            for (VisitingIntentDTO intentDto : visitDto.getIntentList()) {
                if (intentDto.getIsMainModel() == DictCodeConstants.STATUS_IS_YES) {
                    n = n + 1;
                    if (n > 1) {
                        throw new ServiceBizException("展厅主要意向只能有一个!");
                    }
                }
            }
        }
        if (n == 0) {
          //  throw new ServiceBizException("展厅主要意向必须有一个!");
        }
    }

    /**
     * 查询展厅主要意向的条数
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param id
     * @return
     * @throws ServiceBizException
     */

    public List<Map> queryMainIntent(Long id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("select VISITING_RECORD_ID,VISITING_INTENT_ID,DEALER_CODE from TT_VISITING_INTENT \n");
        sb.append(" where IS_MAIN_INTENT =? \n");
        sb.append(" and VISITING_RECORD_ID=? \n");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
    }


    /**
    * @author Administrator
    * @date 2016年12月28日
    * @param mobile
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#checkoutMobileforVisits(java.lang.String, java.lang.String)
    */

    @Override
    public boolean checkoutMobileforVisits(String mobile, String id) throws ServiceBizException {
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        sb.append("select VISITING_RECORD_ID,DEALER_CODE from TT_VISITING_RECORD where 1=1 ");
        List<Object> queryList = new ArrayList<Object>();
        sb.append("and VISIT_TYPE in (?,?) \n");
        queryList.add(DictCodeConstants.COMPLAINT_VISIT_TYPE_02);
        queryList.add(DictCodeConstants.COMPLAINT_VISIT_TYPE_03);
        sb.append(" and MOBILE=?\n");
        queryList.add(mobile);
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append("and VISITING_RECORD_ID <> ? ");
            queryList.add(id.trim());
        }
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if (result.size() > 0) {
            flag = true;
        }
        return flag;
    }


    

    /**
     * 导出
    * @author Administrator
    * @date 2016年12月28日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#queryVisitingRecordforExport(java.util.Map)
    */

    @Override
    public List<Map> queryVisitingRecordforExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append(" select vd.DEALER_CODE,vd.CUSTOMER_NO,vd.CUSTOMER_NAME,vd.MOBILE,vd.VISIT_TYPE,vd.VISIT_TIME,vd.INTENT_LEVEL,vd.CUS_SOURCE,vd.MEDIA_TYPE,vd.IS_FIRST_ARRIVE,vd.IS_SECOND_ARRIVE,vd.LEAVE_TIME,vd.IS_TEST_DRIVE,vd.SCENE,\n");
        sb.append(" vi.QUOTED_PRICE,vi.REMARK,\n");
        sb.append(" vwp.BRAND_NAME,vwp.SERIES_NAME,vwp.CONFIG_NAME,vwp.MODEL_NAME,color1.COLOR_NAME,\n");
        sb.append(" em.EMPLOYEE_NAME\n");
        sb.append(" from TT_VISITING_RECORD vd\n");
        sb.append(" left  join   TT_VISITING_INTENT vi on vd.VISITING_RECORD_ID=vi.VISITING_RECORD_ID and IS_MAIN_INTENT=?\n");
        sb.append(" left  join   TM_EMPLOYEE em  on   vd.CONSULTANT=em.EMPLOYEE_NO\n");
        sb.append(" left  join   TM_COLOR  color1 on  color1.COLOR_ID=vi.INTENT_COLOR\n");
        sb.append(" LEFT JOIN    vw_packageinfo  vwp  on vi.INTENT_BRAND=vwp.BRAND_CODE  and vi.INTENT_SERIES=vwp.SERIES_CODE and vwp.CONFIG_CODE=vi.INTENT_CONFIG  and vwp.MODEL_CODE=vi.INTENT_MODEL \n");
      /*  sb.append(" left  join   TM_PACKAGE pa   on   vi.INTENT_CONFIG=pa.PACKAGE_ID\n");
        sb.append(" left  join   TM_MODEL   mo   on   vi.INTENT_MODEL=mo.MODEL_ID\n");
        sb.append(" left  join   TM_SERIES  se   on   vi.INTENT_SERIES=se.SERIES_ID\n");
        sb.append(" left  join   tm_brand   br   on   vi.INTENT_BRAND = br.BRAND_ID\n");
        sb.append(" left  join   TM_COLOR   cr   on   vi.INTENT_COLOR = cr.COLOR_ID\n");*/

        sb.append("where  1=1");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        this.setWhere(sb, queryParam, queryList);
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
        return resultList;
    }

   

    /**
     * 删除
    * @author Administrator
    * @date 2016年12月28日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#deleteVisitIntentInfo(java.lang.Long)
    */

    @Override
    public void deleteVisitIntentInfo(Long id) throws ServiceBizException {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	VisitingRecordPO visitPo = VisitingRecordPO.findByCompositeKeys(id,dealerCode);
//        VisitingRecordPO visitPo = VisitingRecordPO.findById(id);
        visitPo.deleteCascadeShallow();

    }

    
  
    /**
     * 再分配查询客户信息
    * @author Administrator
    * @date 2016年12月28日
    * @param queryParam
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRegisterService#deleteVisitIntentInfo(java.lang.Long)
    */
    @Override
    public PageInfoDto queryCustomerInfoByid(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT  INTENT_SERIES,ITEM_ID, DEALER_CODE, CUSTOMER_NO, CUSTOMER_NAME, GENDER, SOLD_BY, CONTACTOR_NAME, CONTACTOR_PHONE,"
                + " CONTACTOR_MOBILE,VISITOR,VISIT_TYPE,INTENT_LEVEL,INTENT_MODEL,INTENT_CONFIG,CUS_SOURCE,MEDIA_TYPE,FIRST_QUOTA,VISIT_TIME,EMPLOYEE_NAME,"
                + " LEAVE_TIME, COMPLAINT_RESULT, SCENE, NEXT_CONSULTANT, COLOR_CODE, PRODUCT_CODE,IS_TEST_DRIVE,IS_FIRST_VISIT,CAMPAIGN_CODE,"
                + " INTENT_ID, IS_UPLOAD, OWNED_BY, VER, VALUE_CUST, BETTER_VISIT_PLACE, BETTER_VISIT_TIME, MEDIA_DETAIL, IS_SECOND_TEH_SHOP, is_followed "
                + " FROM ( SELECT  V.INTENT_SERIES, V.ITEM_ID, V.DEALER_CODE, V.CUSTOMER_NO, V.CUSTOMER_NAME, V.SOLD_BY, V.GENDER, V.CONTACTOR_NAME, V.CONTACTOR_PHONE,"
                + " V.CONTACTOR_MOBILE,  V.VISITOR,  V.VISIT_TYPE,  V.INTENT_LEVEL,  V.INTENT_MODEL,  V.INTENT_CONFIG,  V.CUS_SOURCE,  V.MEDIA_TYPE,"
                + " V.FIRST_QUOTA,  V.VISIT_TIME,  V.LEAVE_TIME,  V.COMPLAINT_RESULT,  V.SCENE,  V.NEXT_CONSULTANT,  V.COLOR_CODE,  V.PRODUCT_CODE,"
                + " V.IS_TEST_DRIVE,V.IS_FIRST_VISIT,V.CAMPAIGN_CODE,V.INTENT_ID,V.IS_UPLOAD,V.OWNED_BY,V.VER,V.BETTER_VISIT_PLACE,V.BETTER_VISIT_TIME,em.EMPLOYEE_NAME,"
                + " (SELECT  P.CAMPAIGN_NAME FROM  TT_CAMPAIGN_PLAN P WHERE V.CAMPAIGN_CODE = P.CAMPAIGN_CODE  AND V.DEALER_CODE = P.DEALER_CODE) P_CAMPAIGN_NAME,"
                + " (SELECT  M.ADVERT_TOPIC FROM  TT_ADVERT_MEDIA M  WHERE V.DEALER_CODE = M.DEALER_CODE  AND V.CAMPAIGN_CODE = CAST(M.ITEM_ID AS CHAR(14))) M_CAMPAIGN_NAME,"
                + " CASE WHEN (V.CUSTOMER_NAME IS NOT NULL)  AND (LENGTH(V.CUSTOMER_NAME) > 0) AND (V.MEDIA_TYPE IS NOT NULL)AND (V.MEDIA_TYPE <> 0)"
                + " AND (V.INTENT_SERIES IS NOT NULL) AND (LENGTH(V.INTENT_SERIES) > 0) AND (V.CONTACTOR_PHONE IS NOT NULL) AND (V.CONTACTOR_PHONE IS NOT NULL)"
                + " AND (LENGTH(V.CONTACTOR_PHONE) > 0)  OR ( (V.CONTACTOR_MOBILE IS NOT NULL) AND (LENGTH(V.CONTACTOR_MOBILE) > 0)) THEN 12781001 ELSE 12781002 END VALUE_CUST,"
                + " V.MEDIA_DETAIL,V.IS_SECOND_TEH_SHOP, CASE WHEN (vv.reception_id IS NOT NULL) THEN 12781001 ELSE 12781002 END is_followed "
                + " FROM  TT_VISITING_RECORD V LEFT JOIN tT_SALES_PROMOTION_PLAN vv ON v.dealer_code = vv.dealer_code  AND v.customer_no = vv.customer_no"
                + " AND v.item_id = vv.reception_id "
                + " LEFT JOIN TM_EMPLOYEE em ON V.SOLD_BY=em.EMPLOYEE_NO"
                + " WHERE  v.customer_no IS NULL  ) AS total");
        List<Object> queryList = new ArrayList<Object>();
        PageInfoDto ids = DAOUtil.pageQuery(sb.toString(), queryList);
        return ids;
    }
    

    
   
    /**
     * 再分配
     * 
     * @author LGQ
     * @date 2016年12月29日
     * @param potentialCusPo
     */
    @Override
    public void modifySoldBy(VisitingRecordDTO visitDto) throws ServiceBizException {
        String[] ids = visitDto.getNoList().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        for (int i = 0; i < ids.length; i++) {
            String no = ids[i];
           // VisitingRecordPO visitPo=VisitingRecordPO.findByCompositeKeys(loginInfo.getDealerCode(),no);
           String dealerCode  = FrameworkUtil.getLoginInfo().getDealerCode();
           VisitingRecordPO visitPo=VisitingRecordPO.findByCompositeKeys(no,dealerCode);
//            VisitingRecordPO visitPo=VisitingRecordPO.findById(no);
           // CustomerTrackingPO traPo = CustomerTrackingPO.findById(id);
            visitPo.setString("SOLD_BY", visitDto.getSoldBy());// 销售顾问
            visitPo.saveIt();
        }
        
    }
    
    



}
