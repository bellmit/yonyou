
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.customer
*
* @File name : CusApproveServiceImpl.java
*
* @Author : Administrator
*
* @Date : 2017年1月5日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月5日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.PotentialCusDTO;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonSales.service.basedata.OrgDeptService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DAOUtilGF;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
* TODO description
* @author Administrator
* @date 2017年1月5日
*/
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class CusApproveServiceImpl implements CusApproveService {
   
    @SuppressWarnings("unused")
	@Autowired
    private VisitingRecordService visitingrecordservice;
    @SuppressWarnings("unused")
	@Autowired
    private TrackingTaskService   trackingtaskservice;
    @Autowired
    private OperateLogService operateLogService;
    @SuppressWarnings("unused")
	@Autowired
    private OrgDeptService orgdeptservice;
    @SuppressWarnings("unused")
	@Autowired
    private CommonNoService     commonNoService;

    
    
    
    
    
    /**
    * @author Administrator
    * @date 2017年1月5日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.CusApproveService#queryPotentialCusInfo(java.util.Map)
    */

    @Override
    public PageInfoDto queryApproveCusInfo(Map<String, String> queryParam) throws ServiceBizException {
    	
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DISTINCT C.*\n");
        sb.append(" from ( select c.DEALER_CODE,c.CUSTOMER_NAME,c.CUSTOMER_NO,ci.INTENT_COLOR,\n");
        sb.append(" c.INTENT_ID,C.AUDIT_STATUS,C.UPDATED_AT,C.CREATED_AT,C.DETAIL_DESC,C.TEST_DRIVE_REMARK,C.IS_TEST_DRIVE,\n");
        sb.append(" C.CUSTOMER_STATUS,C.CUSTOMER_TYPE,C.GENDER,C.BIRTHDAY,tv.VISIT_TIME AS V_TIME,tv.VISIT_TIMES AS V_TIMES,");
        sb.append(" C.ZIP_CODE,C.COUNTRY_CODE,C.PROVINCE,C.CITY,C.DISTRICT,C.EXPECT_TIMES_RANGE,EXPECT_DATE,ci.INTENT_BRAND,ci.INTENT_SERIES,ci.INTENT_MODEL,ci.INTENT_CONFIG,\n");
        sb.append(" C.ADDRESS,C.E_MAIL,C.HOBBY,C.CONTACTOR_PHONE, C.CONTACTOR_MOBILE,tl.MOBILE,tl.PHONE,\n");
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
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,em.USER_NAME,em.USER_ID,c.ORGANIZATION_ID,tl.CONTACTOR_NAME,tci.TEST_DRIVE_DATE,pa.CONFIG_NAME,co.COLOR_NAME,tl.IS_DEFAULT_CONTACTOR\n");//数据权限范围管控
        sb.append(" from TM_POTENTIAL_CUSTOMER c\n");
        sb.append(" left join TM_USER em  on c.SOLD_BY=em.USER_ID AND c.DEALER_CODE=em.DEALER_CODE\n");
        sb.append(" left join   tt_customer_intent_detail ci on c.INTENT_ID=ci.INTENT_ID and IS_MAIN_MODEL=?\n");
        sb.append(" left join   tt_customer_intent tci on c.INTENT_ID=tci.INTENT_ID and c.CUSTOMER_NO=tci.CUSTOMER_NO\n");
        sb.append(" left  join   tm_brand   br   on   ci.INTENT_BRAND = br.BRAND_CODE and c.DEALER_CODE=br.DEALER_CODE\n");
        sb.append(" left  join   TM_SERIES  se   on   ci.INTENT_SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and c.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" left  join   TM_MODEL   mo   on   ci.INTENT_MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and c.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" left  join   tm_configuration pa   on   ci.INTENT_CONFIG=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and c.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" left  join   tm_color   co   on   ci.INTENT_COLOR = co.COLOR_CODE and c.DEALER_CODE=co.DEALER_CODE\n");
        sb.append(" left  join   TT_VISITING_RECORD   tv   on   c.CUSTOMER_NO = tv.CUSTOMER_NO and c.DEALER_CODE=tv.DEALER_CODE\n");
       // sb.append(" left join (SELECT MAX(CREATED_AT),SCENE,DEALER_CODE,INTENT_ID,CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN WHERE PROM_RESULT IS NOT NULL GROUP BY CREATED_AT,SCENE,DEALER_CODE,INTENT_ID,CUSTOMER_NO) pot on c.INTENT_ID = pot.INTENT_ID and c.DEALER_CODE=pot.DEALER_CODE\n");
        sb.append(" left join TT_PO_CUS_LINKMAN tl on c.CUSTOMER_NO=tl.CUSTOMER_NO and c.DEALER_CODE=tl.DEALER_CODE and tl.IS_DEFAULT_CONTACTOR='"+DictCodeConstants.IS_YES+"') C left join TT_CUSTOMER_VEHICLE_LIST sss on c.DEALER_CODE=sss.DEALER_CODE and c.CUSTOMER_NO=sss.CUSTOMER_NO \n");
        sb.append(" where (C.intent_level='"+DictCodeConstants.DICT_INTENT_LEVEL_FO+"' or c.intent_level='"+DictCodeConstants.DICT_INTENT_LEVEL_F+"')");
       
      
    
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        
        return id;
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
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))) {
            sb.append(" and c.SOLD_BY = ?");
            queryList.add(queryParam.get("soldBy"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerType"))) {
            sb.append(" and c.CUSTOMER_TYPE = ?");
            queryList.add(Integer.parseInt(queryParam.get("customerType")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorMobile"))) {
            sb.append(" and c.CONTACTOR_MOBILE like ?");
            queryList.add("%" + queryParam.get("contactorMobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("cusSource"))) {
            sb.append(" and c.CUS_SOURCE = ?");
            queryList.add(Integer.parseInt(queryParam.get("cusSource")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("contactorPhone"))) {
            sb.append(" and c.CONTACTOR_PHONE like ?");
            queryList.add("%" + queryParam.get("contactorPhone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("intentLevel"))) {
            sb.append(" and c.INTENT_LEVEL = ?");
            queryList.add(Integer.parseInt(queryParam.get("intentLevel")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
            sb.append(" and c.CUSTOMER_NO like ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and c.CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
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
        if (!StringUtils.isNullOrEmpty(queryParam.get("beginLastArriveTime"))) {
            sb.append(" and c.LAST_ARRIVE_TIME>= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("beginLastArriveTime")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("endLastArriveTime"))) {
            sb.append(" and c.LAST_ARRIVE_TIME<?");
            queryList.add(DateUtil.addOneDay(queryParam.get("endLastArriveTime")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_startdate"))) {
            sb.append(" and c.FOUND_DATE>= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("foundDate_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("foundDate_enddate"))) {
            sb.append(" and c.FOUND_DATE<?");
            queryList.add(DateUtil.addOneDay(queryParam.get("foundDate_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("auditStatus"))) {
            sb.append(" and c.AUDIT_STATUS = ?");
            queryList.add(Integer.parseInt(queryParam.get("auditStatus")));
        }
        Long userid = FrameworkUtil.getLoginInfo().getUserId();
		String orgCode = FrameworkUtil.getLoginInfo().getOrgCode();
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        sb.append(" and c.CUSTOMER_NO in (SELECT CUSTOMER_NO FROM TT_PO_CUS_LINKMAN tp WHERE tp.DEALER_CODE=? ");
        queryList.add(loginInfo.getDealerCode());
        if (!StringUtils.isNullOrEmpty(queryParam.get("linkMobile"))) {
            sb.append(" AND tp.MOBILE= ?");
            queryList.add(queryParam.get("linkMobile"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("linkPhone"))) {
            sb.append(" AND tp.PHONE= ?");
            queryList.add(queryParam.get("linkPhone"));
        }
      
		sb.append(DAOUtilGF.getOwnedByStr( "C", userid, orgCode,"10154100", dealerCode));//数据权限范围管控
		
		sb.append(" ) ");
        sb.append(" order by CUSTOMER_NO");
        
      
  }
      
     
        
    
    

    /**
     * 通过id查询休眠申请信息
     * 
     * @author zhanshiwei
     * @date 2016年9月9日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.PotentialCusService#queryPotentialCusInfo(java.lang.Long)
     */

    @Override
    public List<Map> queryApproveCusInfoByid(String id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select cus.*,ts.sleep_series_name as sleep_series_name,ti.BUDGET_AMOUNT,ti.DECISION_MAKER,ti.PURCHASE_TYPE,ti.BILL_TYPE,em.USER_ID, em.USER_NAME from\n")
                .append("TM_POTENTIAL_CUSTOMER cus\n")
                .append("left join TT_CUSTOMER_INTENT ti on cus.CUSTOMER_NO=ti.CUSTOMER_NO and cus.INTENT_ID=ti.INTENT_ID\n")
                .append("left join TM_USER em on cus.SOLD_BY=em.USER_ID \n")
                .append("left join TM_SLEEP_SERIES ts on ts.sleep_series_code=cus.sleep_series \n")
                .append("where cus.CUSTOMER_NO=?");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(id);
        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
        return result;
        
      
    }

    /**
     * 审批申请休眠客户
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
       
        PotentialCusPO potentialCusPo = PotentialCusPO.findByCompositeKeys(loginInfo.getDealerCode(),id);
        potentialCusDto.setCustomerNo(potentialCusPo.getString("CUSTOMER_NO"));
        this.setPotentialCus(potentialCusPo, potentialCusDto);
        potentialCusPo.saveIt();
        
    }

    /**
     * 设置PotentialCusPO属性
     * 
     * @author zhanshiwei
     * @date 2016年9月22日
     * @param potentialCusPo
     * @param potentialCusDto
     */
    private void setPotentialCus(PotentialCusPO potentialPo, PotentialCusDTO potentialCusDto) {
        potentialPo.setLong("AUDIT_STATUS",potentialCusDto.getAuditStatus());
        potentialPo.setString("AUDIT_VIEW",potentialCusDto.getAuditView() );
        if(potentialCusDto.getAuditStatus()==Long.parseLong(DictCodeConstants.DICT_AUDIT_STATUS_YES)){
            potentialPo.setInteger("INTENT_LEVEL", Long.parseLong(DictCodeConstants.DICT_INTENT_LEVEL_F));// 意见级别
        }else if(potentialCusDto.getAuditStatus()==Long.parseLong(DictCodeConstants.DICT_AUDIT_STATUS_NO)){
            potentialPo.setInteger("INTENT_LEVEL", potentialPo.getLong("FAIL_INTENT_LEVEL"));// 意见级别
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
        sb.append(" c.INTENT_ID,C.AUDIT_STATUS,C.UPDATED_AT,C.CREATED_AT,C.DETAIL_DESC,C.TEST_DRIVE_REMARK,C.IS_TEST_DRIVE,\n");
        sb.append(" C.CUSTOMER_STATUS,C.CUSTOMER_TYPE,C.GENDER,C.BIRTHDAY,tv.VISIT_TIME AS V_TIME,tv.VISIT_TIMES AS V_TIMES,");
        sb.append(" C.ZIP_CODE,C.COUNTRY_CODE,C.PROVINCE,C.CITY,C.DISTRICT,C.EXPECT_TIMES_RANGE,EXPECT_DATE,ci.INTENT_BRAND,ci.INTENT_SERIES,ci.INTENT_MODEL,ci.INTENT_CONFIG,\n");
        sb.append(" C.ADDRESS,C.E_MAIL,C.HOBBY,C.CONTACTOR_PHONE, C.CONTACTOR_MOBILE,tl.MOBILE,tl.PHONE, \n");
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
        sb.append(" br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,em.USER_NAME,em.USER_ID,c.ORGANIZATION_ID,tl.CONTACTOR_NAME,tci.TEST_DRIVE_DATE,pa.CONFIG_NAME,co.COLOR_NAME,tl.IS_DEFAULT_CONTACTOR\n");//数据权限范围管控
        sb.append(" from TM_POTENTIAL_CUSTOMER c\n");
        sb.append(" left join TM_USER em  on c.SOLD_BY=em.USER_ID AND c.DEALER_CODE=em.DEALER_CODE\n");
        sb.append(" left join   tt_customer_intent_detail ci on c.INTENT_ID=ci.INTENT_ID and IS_MAIN_MODEL=?\n");
        sb.append(" left join   tt_customer_intent tci on c.INTENT_ID=tci.INTENT_ID and c.CUSTOMER_NO=tci.CUSTOMER_NO\n");
        sb.append(" left  join   tm_brand   br   on   ci.INTENT_BRAND = br.BRAND_CODE and c.DEALER_CODE=br.DEALER_CODE\n");
        sb.append(" left  join   TM_SERIES  se   on   ci.INTENT_SERIES=se.SERIES_CODE and br.BRAND_CODE=se.BRAND_CODE and c.DEALER_CODE=se.DEALER_CODE\n");
        sb.append(" left  join   TM_MODEL   mo   on   ci.INTENT_MODEL=mo.MODEL_CODE and se.BRAND_CODE=mo.BRAND_CODE and mo.SERIES_CODE=se.SERIES_CODE and c.DEALER_CODE=mo.DEALER_CODE\n");
        sb.append(" left  join   tm_configuration pa   on   ci.INTENT_CONFIG=pa.CONFIG_CODE and mo.MODEL_CODE=pa.MODEL_CODE and mo.SERIES_CODE=pa.SERIES_CODE and mo.BRAND_CODE=pa.BRAND_CODE and c.DEALER_CODE=pa.DEALER_CODE\n");
        sb.append(" left  join   tm_color   co   on   ci.INTENT_COLOR = co.COLOR_CODE and c.DEALER_CODE=co.DEALER_CODE\n");
        sb.append(" left  join   TT_VISITING_RECORD   tv   on   c.CUSTOMER_NO = tv.CUSTOMER_NO and c.DEALER_CODE=tv.DEALER_CODE\n");
       // sb.append(" left join (SELECT MAX(CREATED_AT),SCENE,DEALER_CODE,INTENT_ID,CUSTOMER_NO FROM TT_SALES_PROMOTION_PLAN WHERE PROM_RESULT IS NOT NULL GROUP BY CREATED_AT,SCENE,DEALER_CODE,INTENT_ID,CUSTOMER_NO) pot on c.INTENT_ID = pot.INTENT_ID and c.DEALER_CODE=pot.DEALER_CODE\n");
        sb.append(" left join TT_PO_CUS_LINKMAN tl on c.CUSTOMER_NO=tl.CUSTOMER_NO and c.DEALER_CODE=tl.DEALER_CODE and tl.IS_DEFAULT_CONTACTOR='"+DictCodeConstants.IS_YES+"') C left join TT_CUSTOMER_VEHICLE_LIST sss on c.DEALER_CODE=sss.DEALER_CODE and c.CUSTOMER_NO=sss.CUSTOMER_NO \n");
        sb.append(" where (C.intent_level='"+DictCodeConstants.DICT_INTENT_LEVEL_FO+"' or c.intent_level='"+DictCodeConstants.DICT_INTENT_LEVEL_F+"')");
       
      
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(DictCodeConstants.STATUS_IS_YES);
        this.setWhere(sb, queryParam, queryList);
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
        
        for (Map map : resultList) {
            if (map.get("CUSTOMER_TYPE") != null && map.get("CUSTOMER_TYPE") != "") {
                 if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181001) {
                     map.put("CUSTOMER_TYPE", "个人");
                 } else if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 10181002) {
                     map.put("CUSTOMER_TYPE", "公司");
                 } else if (Integer.parseInt(map.get("CUSTOMER_TYPE").toString()) == 0) {
                     map.put("CUSTOMER_TYPE", " ");
                 }                        
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
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == DictCodeConstants.DICT_CUS_SOURCE_PHONE_CUSTOMER) {
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
                } else if (Integer.parseInt(map.get("CUS_SOURCE").toString()) == 0) {
                    map.put("CUS_SOURCE", " ");
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
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString())== 13101005) {
                    map.put("INTENT_LEVEL", "N级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101006) {
                    map.put("INTENT_LEVEL", "F0级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101007) {
                    map.put("INTENT_LEVEL", "F级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101008) {
                    map.put("INTENT_LEVEL", "O级");
                } else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 13101009) {
                    map.put("INTENT_LEVEL", "D级");
                }else if (Integer.parseInt(map.get("INTENT_LEVEL").toString()) == 0) {
                    map.put("INTENT_LEVEL", " ");
                }
                
            }
             if (map.get("IS_TO_SHOP") != null && map.get("IS_TO_SHOP") != "") {
                 if (Integer.parseInt(map.get("IS_TO_SHOP").toString()) == 12781001) {
                     map.put("IS_TO_SHOP", "是");
                 } else if (Integer.parseInt(map.get("IS_TO_SHOP").toString()) == 12781002) {
                     map.put("IS_TO_SHOP", "否");
                 } else if (Integer.parseInt(map.get("IS_TO_SHOP").toString()) == 0) {
                     map.put("IS_TO_SHOP", " ");
                 } 
             }
             if (map.get("AUDIT_STATUS") != null && map.get("AUDIT_STATUS") != "") {
                 if (Integer.parseInt(map.get("AUDIT_STATUS").toString()) == 33351001) {
                     map.put("AUDIT_STATUS", "经理审核中");
                 } else if (Integer.parseInt(map.get("AUDIT_STATUS").toString()) == 33351002) {
                     map.put("AUDIT_STATUS", "经理审核通过");
                 } else if (Integer.parseInt(map.get("AUDIT_STATUS").toString()) == 33351003) {
                     map.put("AUDIT_STATUS", "经理审核不通过");
                 }else if (Integer.parseInt(map.get("AUDIT_STATUS").toString()) == 0) {
                     map.put("AUDIT_STATUS", " ");
                 }
             }
           
            
             
        }
        
        
        
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("客户休眠申请审批导出");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        return resultList;
    }



}
