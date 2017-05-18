
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : VisitingRecordServiceImpl.java
 *
 * @Author : zhanshiwei
 *
 * @Date : 2016年8月31日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月31日    zhanshiwei    1.0
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
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCustomerIntentDetailDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtCustomerVehicleListDTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.SalesPromotionPO;
import com.yonyou.dms.common.domains.PO.basedata.TrackingTaskPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCusIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.VisitingRecordPO;
import com.yonyou.dms.common.service.monitor.Utility;
import com.yonyou.dms.customer.domains.DTO.customerManage.SalesPromotionDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitRecordDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitingIntentDTO;
import com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO;
import com.yonyou.dms.customer.domains.PO.customerManage.VisitingIntentPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
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

/**
 * 展厅接待
 * 
 * @author zhanshiwei,Benzc
 * @date 2016年8月31日,2016年12月27日
 */
@Service
@SuppressWarnings("rawtypes")
public class VisitingRecordServiceImpl implements VisitingRecordService {
	
    @Autowired
    private CommonNoService     commonNoService;

    @Override
    public PageInfoDto queryVisitingRecordInfo(Map<String, String> queryParam) throws ServiceBizException {   	
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT DISTINCT V.ITEM_ID,V.DEALER_CODE,IS_FIRST_VISIT,\n");
        sb.append(" CASE WHEN V.CONTACTOR_MOBILE OR V.CONTACTOR_PHONE IS NOT NULL THEN 12781001 ELSE 12781002 END AS IS_VALID,IS_STEP_FORWARD_GREETING,");
        sb.append(" IS_TEST_DRIVE,V.SOLD_BY,");
        sb.append(" V.CUSTOMER_NO,V.CUS_SOURCE,V.CUSTOMER_NAME,");
        sb.append(" V.CONTACTOR_NAME AS CON,V.CONTACTOR_PHONE,V.CONTACTOR_MOBILE,V.COMPLAINT_RESULT,V.INTENT_LEVEL,V.INTENT_MODEL,V.INTENT_CONFIG,");
        sb.append(" V.COLOR_CODE,V.MEDIA_TYPE,V.VISITOR,em.USER_NAME,V.NEXT_CONSULTANT,V.VISIT_TYPE,V.FIRST_QUOTA,V.VISIT_TIME,V.LEAVE_TIME,V.SCENE,");
        sb.append(" V.CAMPAIGN_CODE,V.BETTER_VISIT_PLACE,V.BETTER_VISIT_TIME,M.MEDIA_DETAIL_NAME AS MEDIA_DETAIL,V.GENDER,");
        sb.append(" round((UNIX_TIMESTAMP(LEAVE_TIME)- UNIX_TIMESTAMP(VISIT_TIME))/60,2) AS CONTACT_TIME,");
        sb.append(" CASE WHEN (CASE WHEN V.CONTACT_TIME != 0 THEN 12781001 ELSE 12781002 END)=12781001  THEN 10571001 END AS IS_FINISH, ");
        sb.append(" Q.VISITING_INTENT_ID,Q.INTENT_BRAND,Q.INTENT_SERIES,Q.INTENT_COLOR,Q.QUOTED_AMOUNT,P.CONTACTOR_NAME, ");
        sb.append(" pa.CONFIG_NAME, mo.MODEL_NAME,se.SERIES_NAME,co.COLOR_NAME,us.USER_NAME AS NAME");
        sb.append(" from TT_VISITING_RECORD V\n");
        sb.append(" LEFT JOIN tt_media_detail M ON M.MEDIA_DETAIL=V.MEDIA_DETAIL\n");
        sb.append(" LEFT JOIN TT_VISITING_INTENT_DETAIL Q  ON (V.ITEM_ID=Q.ITEM_ID AND V.DEALER_CODE=Q.DEALER_CODE )\n");
        sb.append(" LEFT JOIN TM_USER em ON V.SOLD_BY=em.USER_ID AND V.DEALER_CODE=EM.DEALER_CODE");
        sb.append(" LEFT JOIN TM_USER us ON V.NEXT_CONSULTANT=us.USER_ID AND V.DEALER_CODE=US.DEALER_CODE");
        sb.append(" LEFT JOIN tt_po_cus_linkman P ON V.CONTACTOR_NAME=P.CUSTOMER_NO ");
        sb.append(" left  join   tm_brand   br   on   Q.INTENT_BRAND = br.BRAND_CODE and V.DEALER_CODE=br.DEALER_CODE ");
        sb.append(" LEFT JOIN TM_SERIES  se   on   Q.INTENT_SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and V.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" LEFT JOIN TM_MODEL   mo   on   Q.INTENT_MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and V.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" LEFT JOIN tm_configuration pa   on   Q.INTENT_CONFIG=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and V.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" LEFT JOIN tm_color   co   on   Q.INTENT_COLOR = co.COLOR_CODE and V.DEALER_CODE=co.DEALER_CODE\n");
        sb.append(" where 1=1");          
        
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        System.err.println(sb.toString());
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
    	String perfromStatus = queryParam.get("perfromStatus");
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" and V.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentSeries1"))) {
            sb.append(" and Q.INTENT_SERIES = ?");
            queryList.add(queryParam.get("intentSeries1"));
        }
        /*if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
            sb.append(" and V.CUSTOMER_NO like ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }*/
        if (!StringUtils.isNullOrEmpty(queryParam.get("visit_startdate"))) {
            sb.append(" and V.VISIT_TIME >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("visit_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("visit_enddate"))) {
            sb.append(" and V.VISIT_TIME <= ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("visit_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("leave_startdate"))) {
            sb.append(" and V.LEAVE_TIME >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("leave_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("leave_enddate"))) {
            sb.append(" and V.LEAVE_TIME <= ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("leave_enddate")));
        } 
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and V.CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
            sb.append(" and V.CONTACTOR_PHONE like ? or V.CONTACTOR_MOBILE like ?");
            queryList.add("%" + queryParam.get("contactorPhone") + "%");
            queryList.add("%" + queryParam.get("contactorPhone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("visitType"))) {
            sb.append(" and V.VISIT_TYPE = ?");
            queryList.add(Integer.parseInt(queryParam.get("visitType")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("isValid"))) {
        	if("12781001".equals(queryParam.get("isValid"))){
        		sb.append(" and V.CONTACTOR_MOBILE is not null");
        	}else{
        		sb.append(" and V.CONTACTOR_MOBILE is null");
        	} 
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("isStepForwardGreeting"))) {
            sb.append(" and V.IS_STEP_FORWARD_GREETING = ?");
            queryList.add(Integer.parseInt(queryParam.get("isStepForwardGreeting")));
        }
        //已回访，未回访判断
        if (StringUtils.isEquals(DictCodeConstants.DICT_PERFORMANCE_STATUS_NOT, perfromStatus)){
			sb.append(" AND V.COMPLAINT_RESULT is null");
		}
        if (StringUtils.isEquals(DictCodeConstants.DICT_PERFORMANCE_STATUS_HAVE, perfromStatus)){
			sb.append(" AND V.COMPLAINT_RESULT is not null");
		}
        
    }

    /**
     * 展厅接待新增
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param visitDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VisitingRecordService#addVisitingRecordInfo(com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO)
     */

    @Override
    public void addVisitingRecordInfo(VisitingRecordDTO visitDto) throws ServiceBizException {
        // 验证主要意向的个数是否正确
        this.MainIntentSum(visitDto);
        
        if (StringUtils.isNullOrEmpty(visitDto.getCustomerNo())) {
            if (checkoutMobile(visitDto.getContactorMobile(), visitDto.getCustomerNo())) {
                throw new ServiceBizException("手机号已存在!");
            }
        }
   /*     else{
            // 新增跟进任务
            SalesPromotionDTO salesProDto = new SalesPromotionDTO();
            List<Map> result = trackingtaskservice.queryTrackingTaskBylevel(visitDto.getIntentLevel(), null);
            if (result != null&&result.size()>0) {
                salesProDto.setConsultant(visitDto.getConsultant());
                salesProDto.setTaskName(result.get(0).get("task_name").toString());
                salesProDto.setScheduleDate(DateUtil.addDay(new Date(),
                                                            Integer.parseInt(result.get(0).get("interval_days").toString())));
                salesProDto.setPotentialCustomerId(visitDto.getPotentialCustomerId());
                salesProDto.setPriorGrade(visitDto.getIntentLevel());
                addSalesPromotionInfo(salesProDto);
            }
        }*/
        VisitingRecordPO visitPo = new VisitingRecordPO();
        this.setVisitingRecord(visitPo, visitDto, true);
        visitPo.saveIt();
        Long itemId = (Long) visitPo.get("ITEM_ID");
        System.err.println(visitPo.get("CUSTOMER_NAME"));
        System.err.println(visitPo.get("CUSTOMER_NO"));
        System.err.println(visitPo.get("DEALER_CODE"));
        System.err.println(visitPo.get("ITEM_ID"));
        System.out.println(visitPo.getCompositeKeys());
        System.err.println(itemId);
        if (visitDto.getIntentList().size() > 0 && visitDto.getIntentList() != null) {
            for (VisitingIntentDTO intentDto : visitDto.getIntentList()) {
                //visitPo.add(getVisitingIntent(intentDto,itemId));
                getVisitingIntent(visitDto,intentDto,itemId).saveIt();
            }
        }
        /*VisitingIntentDTO intentDto =new VisitingIntentDTO();
        VisitingIntentPO intentPo = new VisitingIntentPO();
        this.getVisitingIntent(intentDto);*/
        // 1.客户编号已存在，且客户信息中是否首次到店为否，则自动更新客户信息中，是否首次到店，首次到店时间(展厅来访时间)
        // 2.客户编号已存在，且客户信息中是否二次到店为否，则自动更新客户信息中，是否二次到店，二次到店时间(展厅来访时间)
        this.modifyPotentialCusByid(visitDto);
       // 来访方式为来店或来电时 保存后对应更新潜客跟进“邀约是否到店”为是。
        //modifySalesPromotion(visitDto);
    }

    /**
     * 自动生成跟进任务
     * 
     * @author zhanshiwei
     * @date 2016年10月8日
     * @param SalesPromotionDTO
     * @return
     * @throws ServiceBizException
     */

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
     * @param item_id
     * @param visitDto
     */

    public void modifyPotentialCusByid(VisitingRecordDTO visitDto) throws ServiceBizException {
        if (!StringUtils.isNullOrEmpty(visitDto.getCustomerNo())) {
            String sql = "select CUSTOMER_NO,DEALER_CODE,FIRST_IS_ARRIVED,ARRIVE_TIME,"
            		+ "IS_SECOND_TEH_SHOP,SECOND_ARRIVE_TIME from TM_POTENTIAL_CUSTOMER where CUSTOMER_NO =?";
            List<Object> queryParams = new ArrayList<Object>();
            queryParams.add(visitDto.getCustomerNo());
            PotentialCusPO potentialCusPo = PotentialCusPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),DAOUtil.findFirst(sql,queryParams).get("CUSTOMER_NO"));
            
            if (!StringUtils.isNullOrEmpty(potentialCusPo.getInteger("FIRST_IS_ARRIVED"))&&potentialCusPo.getInteger("FIRST_IS_ARRIVED")== DictCodeConstants.STATUS_IS_YES) {
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
        if (visitDto.getVisitType().toString() == DictCodeConstants.DICT_VISIT_TYPE_INVITE
            || visitDto.getVisitType().toString() == DictCodeConstants.DICT_VISIT_TYPE_IN_BODY) {
            String sql = "select pro.ITEM_ID,pro.DEALER_CODE from TT_SALES_PROMOTION_PLAN pro  where CUSTOMER_NO=(select pcu.CUSTOMER_NO from TM_POTENTIAL_CUSTOMER pcu where CUSTOMER_NO =? and pro.DEALER_CODE=pcu.DEALER_CODE )  and BOOKING_DATE >=? and BOOKING_DATE <?";
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

    public void setVisitingRecord(VisitingRecordPO visitPo, VisitingRecordDTO visitDto,Boolean flag) throws ServiceBizException {
    	if(flag){
	    	Long id = commonNoService.getId("ID");
	        System.out.println(id);
	        visitPo.setLong("ITEM_ID", id);
    	}
        visitPo.setString("CUSTOMER_NO", visitDto.getCustomerNo());// 客户编码
        visitPo.setString("CUSTOMER_NAME", visitDto.getCustomerName());// 客户名称
        visitPo.setString("CONTACTOR_NAME", visitDto.getContactorName());// 联系人
        visitPo.setString("CONTACTOR_PHONE", visitDto.getContactorPhone());// 联系电话
        visitPo.setString("CONTACTOR_MOBILE", visitDto.getContactorMobile());// 联系手机
        visitPo.setInteger("GENDER", visitDto.getGender());// 性别
        visitPo.setInteger("VISIT_TYPE", visitDto.getVisitType());// 来访方式
        if(flag){
        	visitPo.setTimestamp("VISIT_TIME", new Date());// 来访时间
        }
        visitPo.setString("SOLD_BY", visitDto.getSoldBy());// 销售顾问
        visitPo.setString("VISITOR", visitDto.getVisitor());// 来访人数
        visitPo.setInteger("INTENT_LEVEL", visitDto.getIntentLevel());// 意见级别
        visitPo.setInteger("CUS_SOURCE", visitDto.getCusSource());// 客户来源
        visitPo.setInteger("MEDIA_TYPE", visitDto.getMediaType());// 信息渠道
        visitPo.setInteger("IS_FIRST_TEH_SHOP", DictCodeConstants.STATUS_IS_YES);// 是否首次到店
        //visitPo.setInteger("IS_SECOND_TEH_SHOP", DictCodeConstants.STATUS_IS_NOT);// 是否二次到店
        visitPo.setInteger("IS_TEST_DRIVE", DictCodeConstants.IS_NOT);// 是否试乘驾
        visitPo.setString("SCENE", visitDto.getScene());// SCENE
        if(flag){
        	visitPo.setTimestamp("LEAVE_TIME", visitDto.getLeaveTime());// 离店时间 
        }else{
        	if(visitDto.getLeaveTime()==null){
        		visitPo.setTimestamp("LEAVE_TIME", new Date());// 离店时间 
        	}      	
        } 
        visitPo.setInteger("MEDIA_DETAIL", visitDto.getMediaDetail());// 渠道细分
        visitPo.setString("BETTER_VISIT_TIME", visitDto.getBetterVisitTime());// 拜访时间
        
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT * FROM TT_VISITING_RECORD WHERE CONTACTOR_PHONE = ? OR CONTACTOR_MOBILE = ? AND 1=1");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(visitDto.getContactorPhone());																
        queryList.add(visitDto.getContactorMobile());
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if(flag){//新增
        	if(result.size() == 0){                             //手机号和电话都不存在的
        		if(visitDto.getVisitType() == 13091002){        //来店
        			visitPo.setDouble("VISIT_TIMES", 1);
        		}else{                                          //非来店
        			visitPo.setDouble("VISIT_TIMES", 0);
        		}
        	}else{                                              //手机号或电话存在的
        		if(visitDto.getVisitType() == 13091002){        //来店
        			visitPo.setDouble("VISIT_TIMES", result.size() + 1);
        		}else{                                          //非来店
        			visitPo.setDouble("VISIT_TIMES", 0);
        		}
        	}
        	/*if(!StringUtils.isNullOrEmpty(visitDto.getVisitTimes())){
            	if (result.size() == 0) {//如果查出来的记录数为0条
                	visitPo.setDouble("VISIT_TIMES", visitDto.getVisitTimes());//到店次数
                }else if(result.size() >= 1){
                	visitPo.setDouble("VISIT_TIMES", result.size() + 1);//到店次数
                }  
            }else{
            	visitPo.setDouble("VISIT_TIMES", 1);//到店次数
            }*/
        }else{//修改
        	visitPo.setDouble("VISIT_TIMES", visitDto.getVisitTimes());
        }
        
            
        if(!StringUtils.isNullOrEmpty(visitDto.getIsStepForwardGreeting())){    // 是否已接待
        	visitPo.setLong("IS_STEP_FORWARD_GREETING", DictCodeConstants.IS_YES);
        }else{
        	visitPo.setLong("IS_STEP_FORWARD_GREETING", DictCodeConstants.IS_NOT);
        }
        visitPo.setString("IS_FIRST_VISIT", visitDto.getIsFirstVisit());// 是否首次客流
        visitPo.setString("COMPLAINT_RESULT", visitDto.getComplaintResult());// 回访结果
        visitPo.setString("CAMPAIGN_CODE", visitDto.getCampaignCode());//市场活动
        /*if(!StringUtils.isNullOrEmpty(visitDto.getIsLeavingFarewell())){    // 是否离店远送
        	visitPo.setLong("IS_LEAVING_FAREWELL", DictCodeConstants.IS_YES);
        }else{
        	visitPo.setLong("IS_LEAVING_FAREWELL", DictCodeConstants.IS_NOT);
        }      
        if(!StringUtils.isNullOrEmpty(visitDto.getIsRecording())){    // 是否录音
        	visitPo.setLong("IS_RECORDING", DictCodeConstants.IS_YES);
        }else{
        	visitPo.setLong("IS_RECORDING", DictCodeConstants.IS_NOT);
        }*/
        
        if(visitDto.getCusSource() == 13111003 || visitDto.getCusSource() == 13111017||
        		visitDto.getCusSource() == 13111018 || visitDto.getCusSource() == 13111019 ){
        	if(visitDto.getCampaignCode() == null){
        		throw new ServiceBizException("市场活动不能为空");
        	}
        }
        
            

    }

    /**
     * 设置VisitingIntentPO属性
     * 
     * @author zhanshiwei,Benzc
     * @date 2016年9月22日,2016年12月27日
     * @param intentDto
     * @return
     */

    public VisitingIntentPO getVisitingIntent(VisitingRecordDTO visitDto,VisitingIntentDTO intentDto,Long itemId) {
    	VisitingIntentPO intentPo = new VisitingIntentPO();
		intentPo.setLong("ITEM_ID", itemId);
        intentPo.setString("INTENT_BRAND", intentDto.getIntentBrand());// 意向品牌
        intentPo.setString("INTENT_MODEL", intentDto.getIntentModel());// 意向车型
        intentPo.setString("INTENT_SERIES", intentDto.getIntentSeries());// 意向车系
        intentPo.setString("INTENT_CONFIG", intentDto.getIntentConfig());// 意向配置
        intentPo.setString("INTENT_COLOR", intentDto.getIntentColor());// 意向颜色
        if(visitDto.getIntentList().size() == 1){
        	intentPo.setInteger("IS_MAIN_SERIES", DictCodeConstants.STATUS_IS_YES);// 是否主要车系
            intentPo.setInteger("IS_MAIN_MODEL", DictCodeConstants.STATUS_IS_YES);// 是否主要车型
        }else{
        	intentPo.setInteger("IS_MAIN_SERIES", intentDto.getIsMainSeries());// 是否主要车系
            intentPo.setInteger("IS_MAIN_MODEL", intentDto.getIsMainModel());// 是否主要车型
        }
        intentPo.setDouble("QUOTED_AMOUNT", intentDto.getQuotedAmount());// 初次报价
        return intentPo;
    }

    /**
     * 根据展厅接待ID查询信息
     * 
     * @author zhanshiwei
     * @date 2016年9月1日
     * @param id 展厅接待ID
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VisitingRecordService#queryVisitingRecordInfoByid(Long)
     */

    @SuppressWarnings("unchecked")
	@Override
    public Map<String,Object> queryVisitingRecordInfoByid(Long id) throws ServiceBizException {
    	StringBuffer sb = new StringBuffer();
    	sb.append("SELECT V.*,C.CAMPAIGN_NAME,V.CONTACTOR_NAME AS CN FROM TT_VISITING_RECORD V");
    	sb.append(" LEFT JOIN TT_CAMPAIGN_PLAN C ON V.CAMPAIGN_CODE=C.CAMPAIGN_CODE WHERE V.ITEM_ID=?");
    	List<Object> queryList = new ArrayList<Object>();
    	queryList.add(id);
    	System.err.println(sb.toString());
 
        return DAOUtil.findAll(sb.toString(), queryList).get(0);
    }

    /**
     * 根据父表主键查询(展厅客户意向报价)明细
     * 
     * @author zhanshiwei,Benzc
     * @date 2016年9月1日,2016月12月27日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VisitingRecordService#queryVisitIntentInfoByParendId(Long)
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
     * 客户手机验证
     * 
     * @author zhanshiwei,Benzc
     * @date 2016年9月1日,2016年12月27日
     * @param mobile
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VisitingRecordService#checkoutMobile(java.lang.String)
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
        /*sb.append(" and CONTACTOR_MOBILE="+mobile);
        if(!StringUtils.isNullOrEmpty(cusno)){
        	sb.append("and CUSTOMER_NO <> "+cusno);
        }*/
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if (result.size() > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 厅接待修改
     * 
     * @author zhanshiwei
     * @date 2016年9月7日
     * @param id
     * @param visitDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VisitingRecordService#modifyVisitingRecordInfo(Long,
     * com.yonyou.dms.customer.domains.DTO.customerManage.VisitingRecordDTO)
     */

    @Override
    public void modifyVisitingRecordInfo(Long id, VisitingRecordDTO visitDto) throws ServiceBizException {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        if (StringUtils.isNullOrEmpty(visitDto.getCustomerNo())) {
            if (checkoutMobile(visitDto.getContactorMobile(), visitDto.getCustomerNo())) {
                throw new ServiceBizException("手机号已存在!");
            }
        }
        // 验证主要意向的个数是否正确
        this.MainIntentSum(visitDto);
        VisitingRecordPO visitPo  = VisitingRecordPO.findByCompositeKeys(id,dealerCode);
//        VisitingRecordPO visitPo = VisitingRecordPO.findById(id);
        this.setVisitingRecord(visitPo, visitDto, false);
        visitPo.saveIt();

        VisitingIntentPO.delete("ITEM_ID = ?", id);

        if (visitDto.getIntentList().size() > 0 && visitDto.getIntentList() != null) {
            for (VisitingIntentDTO intentDto : visitDto.getIntentList()) {
                //visitPo.add(getVisitingIntent(intentDto));
                getVisitingIntent(visitDto,intentDto,id).saveIt();
            }
        }

        // 1.客户编号已存在，且客户信息中是否首次到店为否，则自动更新客户信息中，是否首次到店，首次到店时间(展厅来访时间)
        // 2.客户编号已存在，且客户信息中是否二次到店为否，则自动更新客户信息中，是否二次到店，二次到店时间(展厅来访时间)
        this.modifyPotentialCusByid(visitDto);
        // 来访方式为来店或来电时 保存后对应更新潜客跟进“邀约是否到店”为是。
        
        //modifySalesPromotion(visitDto);
        
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
        if (visitDto.getIntentList().size() == 0) {
          throw new ServiceBizException("必须新增一条主要车系车型!");
        }else if (visitDto.getIntentList().size() > 1 ) {
            for (VisitingIntentDTO intentDto : visitDto.getIntentList()) {
                if (intentDto.getIsMainSeries() == DictCodeConstants.STATUS_IS_YES) {
                    n = n + 1;
                    if (n > 1) {
                        throw new ServiceBizException("主要车系有且只能有一条!");
                    }
                }
                if (intentDto.getIsMainModel() == DictCodeConstants.STATUS_IS_YES && 
                		intentDto.getIsMainSeries() == DictCodeConstants.STATUS_IS_NOT) {
                	throw new ServiceBizException("主要车型必须为主要车系");
                }
            }
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
        sb.append("SELECT ITEM_ID,VISITING_INTENT_ID,DEALER_CODE FROM TT_VISITING_INTENT_DETAIL \n");
        sb.append(" WHERE ITEM_ID =? \n");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
    }

    /**
     * @author zhanshiwei
     * @date 2016年9月10日
     * @param mobile
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VisitingRecordService#checkoutMobileforVisits(java.lang.String,
     * java.lang.Long)
     */

    @Override
    public boolean checkoutMobileforVisits(String mobile, String id) throws ServiceBizException {
        boolean flag = false;
        StringBuffer sb = new StringBuffer();
        sb.append("select ITEM_ID,DEALER_CODE from TT_VISITING_RECORD where 1=1 ");
        List<Object> queryList = new ArrayList<Object>();
        sb.append("and VISIT_TYPE in (?,?) \n");
        queryList.add(DictCodeConstants.COMPLAINT_VISIT_TYPE_02);
        queryList.add(DictCodeConstants.COMPLAINT_VISIT_TYPE_03);
        sb.append(" and CONTACTOR_MOBILE=?\n");
        queryList.add(mobile);
        if (!StringUtils.isNullOrEmpty(id)) {
            sb.append("and ITEM_ID <> ? ");
            queryList.add(id.trim());
        }
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        if (result.size() > 0) {
            flag = true;
        }
        return flag;
    }

    /**
     * 展厅接待导出
     * 
     * @author zhanshiwei
     * @date 2016年9月11日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.VisitingRecordService#queryVisitingRecordforExport(java.util.Map)
     */

    @SuppressWarnings("unchecked")
	@Override
    public List<Map> queryVisitingRecordforExport(Map<String, String> queryParam) throws ServiceBizException {
    	StringBuffer sb = new StringBuffer();
    	sb.append(" SELECT DISTINCT V.ITEM_ID,V.DEALER_CODE,IS_FIRST_VISIT,\n");
        sb.append(" CASE WHEN V.CONTACTOR_MOBILE OR V.CONTACTOR_PHONE IS NOT NULL THEN 12781001 ELSE 12781002 END AS IS_VALID,IS_STEP_FORWARD_GREETING,");
        sb.append(" IS_TEST_DRIVE,V.SOLD_BY,");
        sb.append(" V.CUSTOMER_NO,V.CUS_SOURCE,V.CUSTOMER_NAME,");
        sb.append(" V.CONTACTOR_NAME AS CON,V.CONTACTOR_PHONE,V.CONTACTOR_MOBILE,V.COMPLAINT_RESULT,V.INTENT_LEVEL,V.INTENT_MODEL,V.INTENT_CONFIG,");
        sb.append(" V.COLOR_CODE,V.MEDIA_TYPE,V.VISITOR,em.USER_NAME,V.NEXT_CONSULTANT,V.VISIT_TYPE,V.FIRST_QUOTA,V.VISIT_TIME,V.LEAVE_TIME,V.SCENE,");
        sb.append(" V.CAMPAIGN_CODE,V.BETTER_VISIT_PLACE,V.BETTER_VISIT_TIME,M.MEDIA_DETAIL_NAME AS MEDIA_DETAIL,V.GENDER,");
        sb.append(" round((UNIX_TIMESTAMP(LEAVE_TIME)- UNIX_TIMESTAMP(VISIT_TIME))/60,2) AS CONTACT_TIME,");
        sb.append(" CASE WHEN (CASE WHEN V.CONTACT_TIME != 0 THEN 12781001 ELSE 12781002 END)=12781001  THEN 10571001 END AS IS_FINISH, ");
        sb.append(" Q.VISITING_INTENT_ID,Q.INTENT_BRAND,Q.INTENT_SERIES,Q.INTENT_COLOR,Q.QUOTED_AMOUNT,P.CONTACTOR_NAME, ");
        sb.append(" pa.CONFIG_NAME, mo.MODEL_NAME,se.SERIES_NAME,co.COLOR_NAME,us.USER_NAME AS NAME");
        sb.append(" from TT_VISITING_RECORD V\n");
        sb.append(" LEFT JOIN tt_media_detail M ON M.MEDIA_DETAIL=V.MEDIA_DETAIL\n");
        sb.append(" LEFT JOIN TT_VISITING_INTENT_DETAIL Q  ON (V.ITEM_ID=Q.ITEM_ID AND V.DEALER_CODE=Q.DEALER_CODE )\n");
        sb.append(" LEFT JOIN TM_USER em ON V.SOLD_BY=em.USER_ID");
        sb.append(" LEFT JOIN TM_USER us ON V.NEXT_CONSULTANT=us.USER_ID");
        sb.append(" LEFT JOIN tt_po_cus_linkman P ON V.CONTACTOR_NAME=P.CUSTOMER_NO ");
        sb.append(" LEFT JOIN tm_configuration pa   on   Q.INTENT_CONFIG=pa.CONFIG_CODE and V.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" LEFT JOIN TM_MODEL   mo   on   Q.INTENT_MODEL=mo.MODEL_CODE and V.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" LEFT JOIN TM_SERIES  se   on   Q.INTENT_SERIES=se.SERIES_CODE and V.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" LEFT JOIN tm_color   co   on   Q.INTENT_COLOR = co.COLOR_CODE and V.DEALER_CODE=co.DEALER_CODE\n");
        sb.append(" where 1=1");               

        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
        for (Map map : list) {
            if (map.get("IS_STEP_FORWARD_GREETING") != null && map.get("IS_STEP_FORWARD_GREETING") != "") {
                if (Integer.parseInt(map.get("IS_STEP_FORWARD_GREETING").toString()) == 12781001) {
                    map.put("IS_STEP_FORWARD_GREETING", "是");
                } else if (Integer.parseInt(map.get("IS_STEP_FORWARD_GREETING").toString()) == 12781002) {
                    map.put("IS_STEP_FORWARD_GREETING", "否");
                }
            }else{
            	 map.put("IS_STEP_FORWARD_GREETING", "否");
            }
            
            if (map.get("IS_FIRST_VISIT") != null && map.get("IS_FIRST_VISIT") != "") {
                if (Integer.parseInt(map.get("IS_FIRST_VISIT").toString()) == 12781001) {
                    map.put("IS_FIRST_VISIT", "是");
                } else if (Integer.parseInt(map.get("IS_FIRST_VISIT").toString()) == 12781002) {
                    map.put("IS_FIRST_VISIT", "否");
                }
            }else{
            	map.put("IS_FIRST_VISIT", "否");
            }
            
            if (map.get("IS_VALID") != null && map.get("IS_VALID") != "") {
                if (Integer.parseInt(map.get("IS_VALID").toString()) == 12781001) {
                    map.put("IS_VALID", "是");
                } else if (Integer.parseInt(map.get("IS_VALID").toString()) == 12781002) {
                    map.put("IS_VALID", "否");
                }
            }else{
            	map.put("IS_VALID", "否");
            }
            
            if (map.get("IS_TEST_DRIVE") != null && map.get("IS_TEST_DRIVE") != "") {
                if (Integer.parseInt(map.get("IS_TEST_DRIVE").toString()) == 12781001) {
                    map.put("IS_TEST_DRIVE", "是");
                } else if (Integer.parseInt(map.get("IS_TEST_DRIVE").toString()) == 12781002) {
                    map.put("IS_TEST_DRIVE", "否");
                }
            }else{
            	map.put("IS_TEST_DRIVE", "否");
            }
            
            if (map.get("CUS_SOURCE") != null && map.get("CUS_SOURCE") != "") {
                if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_EXHI_HALL) {
                    map.put("CUS_SOURCE", "来店/展厅顾客");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_MARKET_ACTIVITY) {
                    map.put("CUS_SOURCE", "活动/展厅活动");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_TENURE_CUSTOMER) {
                    map.put("CUS_SOURCE", "保客增购");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_FRIEND) {
                    map.put("CUS_SOURCE", "保客推荐");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_OTHER) {
                    map.put("CUS_SOURCE", "其他");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_PHONE_VISITER) {
                    map.put("CUS_SOURCE", "陌生拜访/电话销售");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_INTERNET) {
                    map.put("CUS_SOURCE", "网络/电子商务");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_WAY) {
                    map.put("CUS_SOURCE", "路过");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_ORG_CODE) {
                    map.put("CUS_SOURCE", "代理商/代销网点");
                }else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_PHONE_CUSTOMER) {
                    map.put("CUS_SOURCE", "来电顾客");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_DCC) {
                    map.put("CUS_SOURCE", "DCC转入");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_SHOW) {
                    map.put("CUS_SOURCE", "活动-车展");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_EXPERIENCE_DAY) {
                    map.put("CUS_SOURCE", "活动-外场试驾活动");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_CARAVAN) {
                    map.put("CUS_SOURCE", "活动-巡展/外展");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_BY_WEB) {
                    map.put("CUS_SOURCE", "保客置换");
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_DS_WEBSITE) {
                    map.put("CUS_SOURCE", "官网客户  	");
                } 
            }
            
            if (map.get("COMPLAINT_RESULT") != null && map.get("COMPLAINT_RESULT") != "") {
                if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_CREATE) {
                    map.put("COMPLAINT_RESULT", "新建客户信息");
                } else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_BARGAIN) {
                    map.put("COMPLAINT_RESULT", "签订合同");
                } else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_NO_INTENT) {
                    map.put("COMPLAINT_RESULT", "无意向");
                } else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_OTHER_BRAND) {
                    map.put("COMPLAINT_RESULT", "已购其它品牌车");
                } else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_OTHER_SHOP) {
                    map.put("COMPLAINT_RESULT", "已订其它店本品牌车");
                } else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_INFO_SCARCITY) {
                    map.put("COMPLAINT_RESULT", "信息不准");
                } else if (Integer.parseInt(map.get("COMPLAINT_RESULT").toString()) == DictCodeConstants.DICT_COMPLAINT_RESULT_CREATEED) {
                    map.put("COMPLAINT_RESULT", "已来过已建卡");
                } 
            }
            
            if (map.get("INTENT_LEVEL") != null && map.get("INTENT_LEVEL") != "") {
                if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101001) {
                    map.put("INTENT_LEVEL", "H级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101002) {
                    map.put("INTENT_LEVEL", "A级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101003) {
                    map.put("INTENT_LEVEL", "B级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101004) {
                    map.put("INTENT_LEVEL", "C级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101005) {
                    map.put("INTENT_LEVEL", "N级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101006) {
                    map.put("INTENT_LEVEL", "F0级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101007) {
                    map.put("INTENT_LEVEL", "F级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101008) {
                    map.put("INTENT_LEVEL", "O级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101009) {
                    map.put("INTENT_LEVEL", "D级");
                }
            }
            
            if (map.get("MEDIA_TYPE") != null && map.get("MEDIA_TYPE") != "") {
                if (Integer.parseInt(map.get("MEDIA_TYPE").toString()) == DictCodeConstants.DICT_MEDIA_TYPE_NEWSPAPER) {
                    map.put("MEDIA_TYPE", "报纸");
                } else if (Integer.parseInt(map.get("MEDIA_TYPE").toString()) == DictCodeConstants.DICT_MEDIA_TYPE_TELEVISION) {
                    map.put("MEDIA_TYPE", "电视");
                } else if (Integer.parseInt(map.get("MEDIA_TYPE").toString()) == DictCodeConstants.DICT_MEDIA_TYPE_BROADCASTING) {
                    map.put("MEDIA_TYPE", "户外");
                } else if (Integer.parseInt(map.get("MEDIA_TYPE").toString()) == DictCodeConstants.DICT_MEDIA_TYPE_NET) {
                    map.put("MEDIA_TYPE", "广播");
                } else if (Integer.parseInt(map.get("MEDIA_TYPE").toString()) == DictCodeConstants.DICT_MEDIA_TYPE_MAGAZINE) {
                    map.put("MEDIA_TYPE", "BTL Event");
                } else if (Integer.parseInt(map.get("MEDIA_TYPE").toString()) == DictCodeConstants.DICT_MEDIA_TYPE_GUIDEPOST) {
                    map.put("MEDIA_TYPE", "Hotline");
                } else if (Integer.parseInt(map.get("MEDIA_TYPE").toString()) == DictCodeConstants.DICT_MEDIA_TYPE_OUTDOORS) {
                    map.put("MEDIA_TYPE", "网络");
                } else if (Integer.parseInt(map.get("MEDIA_TYPE").toString()) == DictCodeConstants.DICT_MEDIA_TYPE_OTHER) {
                    map.put("MEDIA_TYPE", "其它");
                } else if (Integer.parseInt(map.get("MEDIA_TYPE").toString()) == DictCodeConstants.DICT_MEDIA_TYPE_BODYWORK) {
                    map.put("MEDIA_TYPE", "Inhouse");
                } 
            }
            
            if (map.get("VISIT_TYPE") != null && map.get("VISIT_TYPE") != "") {
                if (Integer.parseInt(map.get("VISIT_TYPE").toString()) == 13091001) {
                    map.put("VISIT_TYPE", "来电");
                } else if (Integer.parseInt(map.get("VISIT_TYPE").toString()) == 13091002) {
                    map.put("VISIT_TYPE", "来店");
                } else if (Integer.parseInt(map.get("VISIT_TYPE").toString()) == 13091003) {
                    map.put("VISIT_TYPE", "陌生拜访");
                } else if (Integer.parseInt(map.get("VISIT_TYPE").toString()) == 13091004) {
                    map.put("VISIT_TYPE", "特定外拓");
                } else if (Integer.parseInt(map.get("VISIT_TYPE").toString()) == 13091005) {
                    map.put("VISIT_TYPE", "其它");
                } else if (Integer.parseInt(map.get("VISIT_TYPE").toString()) == 13091006) {
                    map.put("VISIT_TYPE", "邀约");
                }
            }
            
            if (map.get("GENDER") != null && map.get("GENDER") != "") {
                if (Integer.parseInt(map.get("GENDER").toString()) == DictCodeConstants.DICT_GENDER_MAN) {
                    map.put("GENDER", "男");
                } else if (Integer.parseInt(map.get("GENDER").toString()) == DictCodeConstants.DICT_GENDER_WOMAN) {
                    map.put("GENDER", "女");
                }
            }
            
        }
        
        return list;
    }

    
    /**
     * 删除展厅
    * @author zhanshiwei
    * @date 2016年10月25日
    * @param id
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.VisitingRecordService#deleteVisitIntentInfo(java.lang.Long)
    */
    	
    @Override
    public void deleteVisitIntentInfo(Long id) throws ServiceBizException {
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	VisitingRecordPO  visitPo = VisitingRecordPO.findByCompositeKeys(id,dealerCode);
//        VisitingRecordPO visitPo = VisitingRecordPO.findById(id);
        visitPo.deleteCascadeShallow();
    }
    
    /**
     * 下拉框查询联系人
    * @author Benzc
    * @date 2016年12月29日
    * @param id
    * @throws ServiceBizException
    */
	@Override
	public List<Map> queryContactor(Map<String, String> queryParam) throws ServiceBizException {
		 StringBuilder sb = new StringBuilder("SELECT CUSTOMER_NO,CONTACTOR_NAME,DEALER_CODE FROM tt_po_cus_linkman where 1=1 ");
	     List<Object> params = new ArrayList<Object>();	    
         List<Map> list = DAOUtil.findAll(sb.toString(),params);
         return list;
	}
    
	/**
     * 修改时查询意向信息
    * @author Benzc
    * @date 2017年1月3日
    * @param id
    * @throws ServiceBizException
    */
	@Override
	public List<Map> queryIntent(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT vi.DEALER_CODE,vi.ITEM_ID,vr.ITEM_ID as ID,vi.INTENT_BRAND,vi.INTENT_SERIES,vi.INTENT_MODEL,\n");
		sb.append(" vi.INTENT_CONFIG,vi.INTENT_COLOR,vi.QUOTED_AMOUNT,vi.IS_MAIN_SERIES,vi.IS_MAIN_MODEL\n");
		sb.append(" FROM tt_visiting_intent_detail vi,tt_visiting_record vr\n");
		sb.append(" WHERE vi.ITEM_ID=vr.ITEM_ID AND VI.DEALER_CODE=VR.DEALER_CODE AND VR.DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' and 1=1\n").append("and vi.ITEM_ID=").append(id);
		List<Object> queryList = new ArrayList<Object>();
		System.err.println(sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(),queryList);
        return list;
	}
    
	/**
     * 新增时查询意向信息
    * @author Benzc
    * @date 2017年1月4日
    * @param id
    * @throws ServiceBizException
    */
	@Override
	public List<Map> queryAddIntent(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT vi.DEALER_CODE,vi.ITEM_ID,vr.ITEM_ID as ID,vi.INTENT_BRAND,vi.INTENT_SERIES,vi.INTENT_MODEL,\n");
		sb.append(" vi.INTENT_CONFIG,vi.INTENT_COLOR,vi.QUOTED_AMOUNT,vi.IS_MAIN_SERIES,vi.IS_MAIN_MODEL\n");
		sb.append(" FROM tt_visiting_intent_detail vi,tt_visiting_record vr\n");
		sb.append(" WHERE vi.ITEM_ID=vr.ITEM_ID and 1=1\n");
		List<Object> queryList = new ArrayList<Object>();
		List<Map> list = DAOUtil.findAll(sb.toString(),queryList);
        return list;
	}
    
	/**
     * 根据信息渠道查询渠道细分
    * @author Benzc
    * @date 2017年1月5日
    * @param id
    * @throws ServiceBizException
    */
	@Override
	public List<Map> queryMediaDetail(Map<String, String> queryParam) throws ServiceBizException {
		StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,MEDIA_DETAIL,MEDIA_TYPE,MEDIA_DETAIL_NAME FROM tt_media_detail WHERE 1=1 ");
		List<Object> params = new ArrayList<Object>();
		if(!StringUtils.isNullOrEmpty(queryParam.get("code"))){
			sqlSb.append(" and MEDIA_TYPE = ?");
			params.add(queryParam.get("code"));
		}

		List<Map> list = DAOUtil.findAll(sqlSb.toString(),params);
		return list;
	}
    
	//根据联系人手机，查询联系人信息
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryMobileInfoByid(String mobile) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT '12781002' AS IS_FIRST_VISIT,C.DEALER_CODE,C.CUSTOMER_NO,C.CUSTOMER_NAME,C.CUSTOMER_STATUS,C.CUSTOMER_TYPE,C.GENDER as GEN,C.BIRTHDAY,\n");
    	sb.append(" C.ZIP_CODE,C.COUNTRY_CODE,C.PROVINCE,C.CITY,C.DISTRICT,C.ADDRESS,C.E_MAIL,C.HOBBY,C.CONTACTOR_PHONE as PHONE,\n");
    	sb.append(" C.CONTACTOR_MOBILE as MOBILE,C.IS_WHOLESALER,C.RECOMMEND_EMP_NAME,C.INIT_LEVEL,C.CT_CODE,C.CERTIFICATE_NO,\n");
    	sb.append(" C.INTENT_LEVEL,C.FAIL_CONSULTANT,C.DELAY_CONSULTANT,C.INDUSTRY_FIRST,C.INDUSTRY_SECOND,C.SOLD_BY,\n");
    	sb.append(" C.CUS_SOURCE,C.MEDIA_TYPE,C.MEDIA_DETAIL,C.IS_REPORTED,C.REPORT_REMARK,C.REPORT_DATETIME,C.REPORT_STATUS,\n");
    	sb.append(" C.REPORT_AUDITING_REMARK,C.REPORT_ABORT_REASON,C.GATHERED_SUM,C.ORDER_PAYED_SUM,\n");
    	sb.append(" C.CON_PAYED_SUM,C.USABLE_AMOUNT,C.UN_WRITEOFF_SUM,C.IS_SECOND_TEH_SHOP,C.FIRST_IS_ARRIVED,C.ARRIVE_TIME,\n");
    	sb.append(" L.ITEM_ID,L.CUSTOMER_NO as CONTACTOR_NAME,L.BEST_CONTACT_TYPE,L.IS_DEFAULT_CONTACTOR,L.CONTACTOR_TYPE,L.DEALER_CODE as DC,L.COMPANY,\n");
    	sb.append(" L.CONTACTOR_NAME as CN,L.GENDER,L.PHONE as CONTACTOR_PHONE,L.MOBILE as CONTACTOR_MOBILE,L.E_MAIL as EM,L.CONTACTOR_DEPARTMENT,L.POSITION_NAME,L.FAX,L.REMARK,\n");
    	sb.append(" E.USER_ID,E.USER_NAME,\n");
    	sb.append(" (SELECT COUNT(1) FROM TT_SALES_PROMOTION_PLAN A\n");
    	sb.append(" WHERE A.DEALER_CODE = C.DEALER_CODE\n");
    	sb.append(" AND A.CUSTOMER_NO = C.CUSTOMER_NO\n");
    	sb.append(" AND (A.PROM_RESULT IS NOT NULL OR A.PROM_RESULT <> 0)\n");
    	sb.append(" ) AS COUNT_SALES_PROMOTION_PLAN\n");
    	sb.append(" FROM TM_POTENTIAL_CUSTOMER C LEFT JOIN TT_PO_CUS_LINKMAN L\n");
    	sb.append(" ON C.CUSTOMER_NO = L.CUSTOMER_NO LEFT JOIN TM_USER E\n");
    	sb.append(" ON C.SOLD_BY=E.USER_ID\n");
    	sb.append(" where C.CONTACTOR_MOBILE='"+mobile+"' "
    			
    			+ "and 1=1");
//    	List<Object> queryList = new ArrayList<Object>();
//    	queryList.add(mobile);
    	System.err.println(sb.toString());
    	List<Map> findAll = DAOUtil.findAll(sb.toString(), null);
    	if(findAll.size()>0){
    		return DAOUtil.findAll(sb.toString(), null).get(0);
    	}else{
    		return null;
    	}
 
	}
    
	/**
	 * 再分配
	 * @author Benzc
	 * @date 2017年3月13日
	 */
	@Override
	public void modifySoldBy(VisitRecordDTO visitDto) throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		String[] ids = visitDto.getNoList().split(",");
        for (int i = 0; i < ids.length; i++) {
            String no = ids[i];
            VisitingRecordPO visitPo=VisitingRecordPO.findByCompositeKeys(no,dealerCode);
//            VisitingRecordPO visitPo=VisitingRecordPO.findById(no);
            visitPo.setString("SOLD_BY", visitDto.getSoldBy2());// 销售顾问
            visitPo.setString("NEXT_CONSULTANT", visitDto.getSoldBy2());// 再分配          
            visitPo.saveIt();
        }
		
	}
    
	/**
	 * 新增时可修改来访时间（受控权限）
	 * @author Benzc
	 * @date 2017年3月15日
	 */
	/*@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryVisitTime(Map<String, String> queryParam) throws ServiceBizException {
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		List<Object> paramList = new ArrayList<Object>();
        StringBuilder sb = new StringBuilder();
        sb.append(" select COUNT(*) as num,tma.MENU_ID,tma.ACTION_CODE,tum.DEALER_CODE from tm_user_menu tum,tm_user_menu_action tuma,tc_menu_action tma");
        sb.append(" where  tum.USER_MENU_ID=tuma.USER_MENU_ID and tma.menu_curing_id=tuma.menu_curing_id\n");
        sb.append(" and tum.USER_ID=? and tma.menu_id='201001' and tma.ACTION_CODE='/customerManage/visitingRecord/visitTime'\n");
        sb.append(" and tma.ACTION_METHOD NOT in ('GET','PUT','POST','DELETE')");
        paramList.add(userId);
        System.err.println(sb.toString());
        return DAOUtil.findAll(sb.toString(), paramList).get(0);
	}*/
    
	/**
	 * 再分配查询
	 * @author Benzc
	 * @date 2017年4月12日
	 */
	@Override
	public PageInfoDto queryRedistributionInfo(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
        sb.append(" SELECT DISTINCT V.ITEM_ID,V.DEALER_CODE,IS_FIRST_VISIT,\n");
        sb.append(" CASE WHEN V.CONTACTOR_MOBILE OR V.CONTACTOR_PHONE IS NOT NULL THEN 12781001 ELSE 12781002 END AS IS_VALID,IS_STEP_FORWARD_GREETING,");
        sb.append(" IS_TEST_DRIVE,");
        sb.append(" V.CUSTOMER_NO,V.CUS_SOURCE,V.CUSTOMER_NAME,");
        sb.append(" V.CONTACTOR_NAME AS CON,V.CONTACTOR_PHONE,V.CONTACTOR_MOBILE,V.COMPLAINT_RESULT,V.INTENT_LEVEL,V.INTENT_MODEL,V.INTENT_CONFIG,");
        sb.append(" V.COLOR_CODE,V.MEDIA_TYPE,V.VISITOR,em.USER_NAME,V.NEXT_CONSULTANT,V.VISIT_TYPE,V.FIRST_QUOTA,V.VISIT_TIME,V.LEAVE_TIME,V.SCENE,");
        sb.append(" V.CAMPAIGN_CODE,V.BETTER_VISIT_PLACE,V.BETTER_VISIT_TIME,M.MEDIA_DETAIL_NAME AS MEDIA_DETAIL,V.GENDER,");
        sb.append(" round((UNIX_TIMESTAMP(LEAVE_TIME)- UNIX_TIMESTAMP(VISIT_TIME))/60,2) AS CONTACT_TIME,");
        sb.append(" CASE WHEN (CASE WHEN V.CONTACT_TIME != 0 THEN 12781001 ELSE 12781002 END)=12781001  THEN 10571001 END AS IS_FINISH, ");
        sb.append(" Q.VISITING_INTENT_ID,Q.INTENT_BRAND,Q.INTENT_SERIES,Q.INTENT_COLOR,P.CONTACTOR_NAME, ");
        sb.append(" pa.CONFIG_NAME, mo.MODEL_NAME,se.SERIES_NAME,co.COLOR_NAME,us.USER_NAME AS NAME");
        sb.append(" from TT_VISITING_RECORD V\n");
        sb.append(" LEFT JOIN tt_media_detail M ON M.MEDIA_DETAIL=V.MEDIA_DETAIL\n");
        sb.append(" LEFT JOIN TT_VISITING_INTENT_DETAIL Q  ON (V.ITEM_ID=Q.ITEM_ID AND V.DEALER_CODE=Q.DEALER_CODE )\n");
        sb.append(" LEFT JOIN TM_USER em ON V.SOLD_BY=em.USER_ID");
        sb.append(" LEFT JOIN TM_USER us ON V.NEXT_CONSULTANT=us.USER_ID");
        sb.append(" LEFT JOIN tt_po_cus_linkman P ON V.CONTACTOR_NAME=P.CUSTOMER_NO ");
        sb.append(" LEFT JOIN tm_configuration pa   on   Q.INTENT_CONFIG=pa.CONFIG_CODE and V.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" LEFT JOIN TM_MODEL   mo   on   Q.INTENT_MODEL=mo.MODEL_CODE and V.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" LEFT JOIN TM_SERIES  se   on   Q.INTENT_SERIES=se.SERIES_CODE and V.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" LEFT JOIN tm_color   co   on   Q.INTENT_COLOR = co.COLOR_CODE and V.DEALER_CODE=co.DEALER_CODE\n");
        sb.append(" where V.CUSTOMER_NO IS NULL AND 1=1");          
        
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        System.err.println(sb.toString());
        return id;
	}
    
	/**
	 * 建档、接待时带出客户信息
	 * @author Benzc
	 * @date 2017年4月13日
	 */
	@Override
	public List<Map> queryPotentialCusInfoByid(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT V.ITEM_ID,V.DEALER_CODE,IS_FIRST_VISIT,\n");
        sb.append(" CASE WHEN V.CONTACTOR_MOBILE OR V.CONTACTOR_PHONE IS NOT NULL THEN 12781001 ELSE 12781002 END AS IS_VALID,IS_STEP_FORWARD_GREETING,");
        sb.append(" IS_TEST_DRIVE,'10181001' AS CUSTOMER_TYPE,'12781001' AS IS_TO_SHOP,");
        sb.append(" V.CUSTOMER_NO,V.CUS_SOURCE,V.CUSTOMER_NAME,V.SOLD_BY,V.INTENT_LEVEL AS INIT_LEVEL,");
        sb.append(" V.CONTACTOR_NAME AS CON,V.CONTACTOR_PHONE,V.CONTACTOR_MOBILE,V.COMPLAINT_RESULT,V.INTENT_LEVEL,V.INTENT_MODEL,V.INTENT_CONFIG,");
        sb.append(" V.COLOR_CODE,V.MEDIA_TYPE,V.MEDIA_DETAIL,V.VISITOR,em.USER_NAME,V.NEXT_CONSULTANT,V.VISIT_TYPE,V.FIRST_QUOTA,V.VISIT_TIME,V.LEAVE_TIME,V.SCENE,");
        sb.append(" V.CAMPAIGN_CODE,V.BETTER_VISIT_PLACE,V.BETTER_VISIT_TIME,M.MEDIA_DETAIL_NAME,V.GENDER,");
        sb.append(" round((UNIX_TIMESTAMP(LEAVE_TIME)- UNIX_TIMESTAMP(VISIT_TIME))/60,2) AS CONTACT_TIME,");
        sb.append(" CASE WHEN (CASE WHEN V.CONTACT_TIME != 0 THEN 12781001 ELSE 12781002 END)=12781001  THEN 10571001 END AS IS_FINISH, ");
        sb.append(" Q.VISITING_INTENT_ID,Q.INTENT_BRAND,Q.INTENT_SERIES,Q.INTENT_COLOR,P.CONTACTOR_NAME, ");
        sb.append(" pa.CONFIG_NAME, mo.MODEL_NAME,se.SERIES_NAME,co.COLOR_NAME,us.USER_NAME AS NAME");
        sb.append(" from TT_VISITING_RECORD V\n");
        sb.append(" LEFT JOIN tt_media_detail M ON M.MEDIA_DETAIL=V.MEDIA_DETAIL\n");
        sb.append(" LEFT JOIN TT_VISITING_INTENT_DETAIL Q  ON (V.ITEM_ID=Q.ITEM_ID AND V.DEALER_CODE=Q.DEALER_CODE )\n");
        sb.append(" LEFT JOIN TM_USER em ON V.SOLD_BY=em.USER_ID");
        sb.append(" LEFT JOIN TM_USER us ON V.NEXT_CONSULTANT=us.USER_ID");
        sb.append(" LEFT JOIN tt_po_cus_linkman P ON V.CONTACTOR_NAME=P.CUSTOMER_NO ");
        sb.append(" LEFT JOIN tm_configuration pa   on   Q.INTENT_CONFIG=pa.CONFIG_CODE and V.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" LEFT JOIN TM_MODEL   mo   on   Q.INTENT_MODEL=mo.MODEL_CODE and V.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" LEFT JOIN TM_SERIES  se   on   Q.INTENT_SERIES=se.SERIES_CODE and V.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" LEFT JOIN tm_color   co   on   Q.INTENT_COLOR = co.COLOR_CODE and V.DEALER_CODE=co.DEALER_CODE\n");
        sb.append(" where V.ITEM_ID=? AND 1=1");  
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        System.err.println(sb.toString());
        return result;
	}
    
	//接待时查询客户意向
	@Override
	public List<Map> queryCusIntent(String id) throws ServiceBizException {
		System.err.println(id);
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT I.*,br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,co.COLOR_NAME,pa.CONFIG_NAME FROM TT_VISITING_INTENT_DETAIL I");
		sb.append(" LEFT JOIN TM_BRAND   br   on   I.INTENT_BRAND=br.BRAND_CODE and I.DEALER_CODE=br.DEALER_CODE");
		sb.append(" LEFT JOIN TM_SERIES  se   on   I.INTENT_SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and I.DEALER_CODE=se.DEALER_CODE");
		sb.append(" LEFT JOIN TM_MODEL   mo   on   I.INTENT_MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and I.DEALER_CODE=mo.DEALER_CODE");
		sb.append(" LEFT JOIN tm_color   co   on   I.INTENT_COLOR = co.COLOR_CODE and I.DEALER_CODE=co.DEALER_CODE");
		sb.append(" LEFT JOIN tm_configuration pa   on   I.INTENT_CONFIG=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and I.DEALER_CODE=pa.DEALER_CODE");
        sb.append(" WHERE I.DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND ITEM_ID="+id);  
        List<Object> queryList = new ArrayList<Object>();
        //queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        System.err.println(sb.toString());
        return result;
	}

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
    public String addPotentialCusInfo(String id,PotentialCusDTO potentialCusDto, String customerNo) throws ServiceBizException {
        PotentialCusPO potentialCusPo = new PotentialCusPO();
        
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        TtCusIntentPO intentpo = new TtCusIntentPO();
        potentialCusDto.setCustomerNo(customerNo);// 潜客编码
        Long intentId = commonNoService.getId("ID");
        System.out.println("——————————————————————————————————开始保存——————————————————————————————---");
        System.out.println(potentialCusDto.getIsPersonDriveCar());
        System.out.println(customerNo);
        System.out.println(intentId);
        System.err.println(id);
        VisitingRecordPO visitPo = VisitingRecordPO.findByCompositeKeys(id,FrameworkUtil.getLoginInfo().getDealerCode());
        visitPo.setString("CUSTOMER_NO", customerNo);
        visitPo.saveIt();
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
        potentialCusPo.setTimestamp("TIME_TO_SHOP", potentialCusDto.getTimeToshop());
        potentialCusPo.saveIt();
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
        return customerNo;
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
        System.err.println(potentialCusDto.getTimeToshop());
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
        TtCustomerVehicleListPO  keepCarPO = new TtCustomerVehicleListPO();
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
    
    //新增时校验已建档手机号
	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryContactorMobile(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT PC.DEALER_CODE,PC.CUSTOMER_NO,PC.SOLD_BY,U.USER_NAME FROM TM_POTENTIAL_CUSTOMER PC");
		sb.append(" LEFT JOIN TM_USER U ON U.DEALER_CODE=PC.DEALER_CODE AND U.USER_ID=PC.SOLD_BY");
		sb.append(" WHERE PC.CUSTOMER_NO IS NOT NULL AND PC.CONTACTOR_MOBILE=" + id);
		System.err.println(sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(),null);
		if(list.size()>0){
			return DAOUtil.findAll(sb.toString(), null).get(0);
		}else{
			return null;
		}
        
	}
    
	/**
	 * 客户带出意向
	 * @author Benzc
	 * @date 2017年5月2日
	 */
	@Override
	public List<Map> queryCusIntent1(String id) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT vi.DEALER_CODE,vi.ITEM_ID,vi.INTENT_BRAND,vi.INTENT_SERIES,vi.INTENT_MODEL,\n");
		sb.append(" vi.INTENT_CONFIG,vi.INTENT_COLOR,vi.QUOTED_AMOUNT,12781001 AS IS_MAIN_SERIES,12781001 AS IS_MAIN_MODEL\n");
		sb.append(" FROM TT_CUSTOMER_INTENT_DETAIL vi\n");
		sb.append(" WHERE vi.ITEM_ID=").append(id);
		List<Object> queryList = new ArrayList<Object>();
		System.err.println(sb.toString());
		List<Map> list = DAOUtil.findAll(sb.toString(),queryList);
        return list;
	}

}
