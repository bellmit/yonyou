
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.customer
 *
 * @File name : RetainCusTrackServiceImpl.java
 *
 * @Author : zhanshiwei
 *
 * @Date : 2016年8月26日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月26日    zhanshiwei    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.customer.service.customerManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.itextpdf.text.log.SysoLogger;
import com.yonyou.dms.common.domains.DTO.basedata.TreatDTO;
import com.yonyou.dms.common.domains.DTO.customer.CustomerTrackingDTO;
import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.CustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesCrPO;
import com.yonyou.dms.common.domains.PO.customer.CustomerTrackingPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonSales.service.basedata.OrgDeptService;
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

/**
 * 保有客户跟进
 * 
 * @author zhanshiwei
 * @date 2016年8月26日
 */
@Service
public class RetainCusTrackServiceImpl implements RetainCusTrackService {
    @Autowired
    private OrgDeptService orgdeptservice;
    @Autowired
    private CommonNoService commonNoService;
    @Autowired
    private OperateLogService operateLogService;

    /**
     * 查询保有客户信息
     * 
     * @author zhanshiwei
     * @date 2016年8月26日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.RetainCusTrackService#queryRetainCusTrackInfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryRetainCusTrackInfo(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
       // StringBuffer sb = new StringBuffer("select tg.TRACKING_ID,tg.DEALER_CODE,tg.TRACKING_DESCRIPTON,tg.SCHEDULE_DATE, \n");
        sb.append(" select * from (");
        sb
                .append("SELECT "
                        + "  S.ITEM_ID,S.CUSTOMER_NO,M.OWNER_NO,"
                        + "T.CUSTOMER_NAME,S.VIN,S.dealer_code,S.CR_NAME,S.CR_TYPE,S.SCHEDULE_DATE,S.ACTION_DATE,S.CR_SCENE,"
                        + "S.CR_CONTEXT,S.CR_RESULT,S.CR_LINKER,S.NEXT_CR_DATE,S.NEXT_CR_CONTEXT,S.CREATE_TYPE,S.LINK_PHONE,"
                        + "S.LINK_MOBILE,S.SOLD_BY,S.OWNED_BY,S.TRANCE_TIME,S.TRANCE_USER,T.ADDRESS FROM TT_SALES_CR S,TM_CUSTOMER T,TT_PO_CUS_RELATION TP,TT_SALES_ORDER O,("+CommonConstants.VM_VEHICLE+") M "
                        + "WHERE S.CUSTOMER_NO=T.CUSTOMER_NO and s.dealer_code=t.dealer_code AND TP.CUSTOMER_NO=T.CUSTOMER_NO AND TP.dealer_code=T.dealer_code AND O.CUSTOMER_NO=TP.PO_CUSTOMER_NO AND TP.dealer_code=O.dealer_code AND M.VIN=O.VIN AND M.DEALER_CODE=O.DEALER_CODE AND S.dealer_code='"
                        + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND S.D_KEY=0 ");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("deStartDate"))) {
            sb.append(" and S.SCHEDULE_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("deStartDate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("deEndDate"))) {
            sb.append(" and S.SCHEDULE_DATE <= ? ");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("deEndDate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and S.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
      
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerName"))){
            sb.append(" AND T.CUSTOMER_NAME like ? ");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("linkMobile"))){
            sb.append(" AND S.LINK_MOBILE like ? ");
            queryList.add("%" + queryParam.get("linkMobile") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("linkPhoe"))){
            sb.append(" AND S.LINK_PHONE like ? ");
            queryList.add("%" + queryParam.get("linkPhoe") + "%");
        }    
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldBy"))){

            sb.append(" AND S.SOLD_BY=?");
            queryList.add(queryParam.get("soldBy"));
        }else
        {
          //  logger.debug("销售顾问应该为空的*********"+soldby);
         //   sql.append(Utility.getOwnedByStr(con, "", userid, orgcode, functionCode, entitycode));
            sb.append(DAOUtilGF.getOwnedByStr("S", loginInfo.getUserId(), loginInfo.getOrgCode(),  "45751500", loginInfo.getDealerCode()));
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("executeStatus")))
        {
            if (queryParam.get("executeStatus").toString().equals("12781001"))
            {
                sb.append(" AND S.CR_RESULT <> 0 AND S.CR_RESULT IS NOT NULL");
            }
            if (queryParam.get("executeStatus").toString().equals("12781002"))
            {
                sb.append(" AND (S.CR_RESULT = 0 OR S.CR_RESULT IS  NULL)");
            }
        }

        sb.append("  ORDER BY T.CUSTOMER_NAME )s");   
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    
    @Override
    public PageInfoDto queryRetainCusTrackVin(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer(); 
     
        sb.append("SELECT T.dealer_code,T.SOLD_BY,T.D_KEY, V.VIN,V.OWNER_NO,T.CUSTOMER_NAME,T.CUSTOMER_NO,T.CUSTOMER_TYPE,T.CONTACTOR_PHONE,T.CONTACTOR_MOBILE FROM TM_CUSTOMER T,("+CommonConstants.VM_VEHICLE+") V WHERE ");
        sb.append("  V.CUSTOMER_NO=T.CUSTOMER_NO AND T.dealer_code=V.dealer_code AND T.dealer_code='" +FrameworkUtil.getLoginInfo().getDealerCode()+ "' AND T.D_KEY=0 ");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerName"))) {
            sb.append(" and T.CUSTOMER_NAME like ?");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and V.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("customerNo"))) {
            sb.append(" and T.CUSTOMER_NO like ?");
            queryList.add("%" + queryParam.get("customerNo") + "%");
        }
        if (queryParam.get("soldBy")!=null && !"".equals(queryParam.get("soldBy"))){
            sb.append(" AND T.SOLD_BY=?");
            queryList.add(queryParam.get("soldBy"));
        }
        
      
        System.err.println(sb.toString());
//        else{
//            sb.append(Utility.getOwnedByStr(con, "T", userid, orgCode, functionCode, entitycode)); 
//        }   
     
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
        
    }
    /**
     * 查询条件
     * 
     * @author zhanshiwei
     * @date 2016年8月26日
     * @param sb
     * @param queryParam
     * @param queryList
     */

    @SuppressWarnings("unused")
	private void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
            sb.append(" and tg.CONSULTANT = ?");
            queryList.add(queryParam.get("consultant"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and ve.vin like ?");
            queryList.add("%" +queryParam.get("vin")+"%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
            sb.append(" and ow.OWNER_NAME like  ?");
            queryList.add("%" +queryParam.get("ownerName")+"%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("schedule_startdate"))) {
            sb.append(" and SCHEDULE_DATE >=?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("schedule_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("schedule_enddate"))) {
            sb.append(" and SCHEDULE_DATE <?");
            queryList.add(DateUtil.addOneDay(queryParam.get("schedule_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("action_startdate"))) {
            sb.append(" and SCHEDULE_DATE >=?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("action_startdate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("action_enddate"))) {
            sb.append(" and ACTION_DATE <?");
            queryList.add(DateUtil.addOneDay(queryParam.get("action_enddate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("trackingDescripton"))) {
            sb.append(" and TRACKING_DESCRIPTON like ?");
            queryList.add("%" +queryParam.get("trackingDescripton")+"%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("trackingResult"))) {
            sb.append(" and TRACKING_RESULT =?");
            queryList.add(Integer.parseInt(queryParam.get("trackingResult")));
        }
    }

    /**
     * 根据保有客户跟进ID查询跟进信息
     * 
     * @author zhanshiwei
     * @date 2016年8月26日
     * @param tracking_id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.RetainCusTrackService#queryRetainCusTrackInfoByid(java.lang.Long)
     */

    @SuppressWarnings("unchecked")
    @Override
    public Map<String, Object> queryRetainCusTrackInfoByid(Long tracking_id) throws ServiceBizException {
        StringBuffer sb = new StringBuffer("select tg.TRACKING_ID,tg.DEALER_CODE,tg.SCHEDULE_DATE,tg.ACTION_DATE,tg.TRACKING_WAY, \n");
        sb.append(" tg.TRACKING_DESCRIPTON,tg.TRACKING_RESULT,tg.CONSULTANT,tg.TRACKING_PROCESS,tg.TRACKING_CONTENT, \n");
        sb.append(" ve.VIN,cu.CUSTOMER_NO,ow.OWNER_NAME\n");
        sb.append("from TT_CUSTOMER_TRACKING tg\n");
        sb.append(" left join TM_VEHICLE  ve  on  tg.VEHICLE_ID=ve.VEHICLE_ID\n");
        sb.append(" left join TM_CUSTOMER cu  on  tg.CUSTOMER_ID=cu.CUSTOMER_ID\n");
        sb.append(" left join TM_OWNER    ow  on  ve.OWNER_ID=ow.OWNER_ID\n");
        sb.append(" where 1=1");
        sb.append(" and TRACKING_ID = ?");
        List<Object> queryList = new ArrayList<Object>();
        queryList.add(tracking_id);
        Map<String, Object> cusTraMap = DAOUtil.findFirst(sb.toString(), queryList);
        return cusTraMap;
    }

    /**
     * 保有客户跟进新增
     * 
     * @author zhnashiwei
     * @date 2016年8月26日
     * @param traDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.RetainCusTrackService#addRetainCusTrackInfo(com.yonyou.dms.customer.domains.DTO.customerManage.CustomerTrackingDTO)
     */

    @Override
    public long addRetainCusTrackInfo(TreatDTO traDto) throws ServiceBizException {
      
            Long intentId = commonNoService.getId("ID");
            TtSalesCrPO traPo = new TtSalesCrPO();
            traPo.setLong("item_id",intentId);       
            this.customerRelation(traDto,traPo);
            traPo.saveIt();      
        return 0;
    }
    public void customerRelation(TreatDTO traDto,TtSalesCrPO traPo) {         
        traPo.setString("VIN",traDto.getVin());      
        traPo.setString("CUSTOMER_NO", traDto.getCustomerNo());
        traPo.setString("CR_NAME",traDto.getCustomerName());
        traPo.setInteger("CREATE_TYPE",traDto.getCreateType());  
        traPo.setString("CR_LINKER",traDto.getCrLinker());  
        traPo.setString("LINK_PHONE",traDto.getLinkPhone());  
        traPo.setString("LINK_MOBILE",traDto.getLinkMobile());  
        traPo.setDate("SCHEDULE_DATE",traDto.getCheduleDate());  
        traPo.setDate("ACTION_DATE",traDto.getActionDate());  
        traPo.setInteger("CR_TYPE",traDto.getCrType());  
        traPo.setString("CR_NAME",traDto.getCrName());  
        traPo.setInteger("CR_RESULT",traDto.getCrResult()); 
        traPo.setString("SOLD_BY",traDto.getSoldBy());
        traPo.setString("CR_SCENE",traDto.getCrScene()); 
        traPo.setString("CR_CONTEXT",traDto.getCrContext());        
        traPo.saveIt();        
    }


    /**
     * 保有客户跟进修改
     * 
     * @author zhanshiwei
     * @date 2016年8月26日
     * @param tracking_id
     * @param traDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.RetainCusTrackService#modifyRetainCusTrackInfo(Long,
     * com.yonyou.dms.customer.domains.DTO.customerManage.CustomerTrackingDTO)
     */

    @Override
    public long modifyRetainCusTrackInfo(Long itemId, TreatDTO traDto) throws ServiceBizException {
        TtSalesCrPO traPo = TtSalesCrPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),itemId);
        
        this.customerRelation(traDto,traPo);
        traPo.saveIt();
        return 0;
    }

    /**
     * 据ID删除保有客户跟进信息
     * 
     * @author zhanshiwei
     * @date 2016年8月26日
     * @param tracking_id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.RetainCusTrackService#deleteRetainCusTrackInfo(Long)
     */

    @Override
    public long deleteRetainCusTrackInfo(Long itemId) throws ServiceBizException {
        TtSalesCrPO traPo = TtSalesCrPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),itemId);
        traPo.delete();
        return 0;
    }

    /**
     * 保有客户跟进重新分配
     * 
     * @author zhanshiwei
     * @date 2016年8月26日
     * @param traDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.customer.service.customerManage.RetainCusTrackService#modifyReRetainCusTrack(com.yonyou.dms.customer.domains.DTO.customerManage.CustomerTrackingDTO)
     */

    @Override
    public void modifyReRetainCusTrack(CustomerTrackingDTO traDto) throws ServiceBizException {
        String[] ids = traDto.getTrackingIds().split(",");
        for (int i = 0; i < ids.length; i++) {
            long id = Long.parseLong(ids[i]);
            CustomerTrackingPO traPo = CustomerTrackingPO.findById(id);
            traPo.setString("CONSULTANT", traDto.getConsultant());// 销售顾问
            traPo.saveIt();
        }
    }
    @SuppressWarnings({ "rawtypes" })
    @Override
    public List<Map> queryRetainCusTrackLink(String customerNo) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT L.ITEM_ID,L.CUSTOMER_NO,L.BEST_CONTACT_TYPE,L.BEST_CONTACT_TIME,L.IS_DEFAULT_CONTACTOR,"
                + "L.CONTACTOR_TYPE,L.dealer_code,L.COMPANY,L.CONTACTOR_NAME,L.GENDER,L.PHONE,"
                + "L.MOBILE,L.E_MAIL,L.FAX,L.REMARK,L.CONTACTOR_DEPARTMENT,L.POSITION_NAME"
                + " FROM tt_po_cus_linkman L WHERE L.dealer_code='" + FrameworkUtil.getLoginInfo().getDealerCode()
                + "' AND L.D_KEY=0 AND L.CUSTOMER_NO='" + customerNo + "'");
        List queryParam = new ArrayList();
        List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);
        return result;
    }
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public Map<String, Object> queryRetainCusTrackInfoVin(String itemId) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append(" select * from (");
        sb
                .append("SELECT "
                        + "  S.ITEM_ID,S.CUSTOMER_NO,"
                        + "T.CUSTOMER_NAME,S.VIN,S.dealer_code,S.CR_NAME,S.CR_TYPE,S.SCHEDULE_DATE,S.ACTION_DATE,S.CR_SCENE,"
                        + "S.CR_CONTEXT,S.CR_RESULT,S.CR_LINKER,S.NEXT_CR_DATE,S.NEXT_CR_CONTEXT,S.CREATE_TYPE,S.LINK_PHONE,"
                        + "S.LINK_MOBILE,S.SOLD_BY,S.OWNED_BY,S.TRANCE_TIME,S.TRANCE_USER,T.ADDRESS FROM TT_SALES_CR S,TM_CUSTOMER T "
                        + "WHERE S.CUSTOMER_NO=T.CUSTOMER_NO and s.dealer_code=t.dealer_code AND S.dealer_code='"
                        + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND S.D_KEY=0 and s.item_id="+itemId+" ORDER BY T.CUSTOMER_NAME )s");
        sb.append(" LEFT JOIN TM_VEHICLE V ON V.`VIN` = s.VIN ");
        System.err.println(sb.toString());
       
        List<Map> result = Base.findAll(sb.toString());
        return result.get(0);
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public List<Map> queryOwnerCusByTreat(String customerNo, String vin, String dealerCode) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append(
                "select * from (SELECT 12781002 AS IS_SELECTED,0 AS IS_SELECT, S.ITEM_ID,S.CUSTOMER_NO,T.CUSTOMER_NAME,S.VIN,S.dealer_code,S.CR_NAME,S.CR_TYPE,S.SCHEDULE_DATE,"
                        + "S.ACTION_DATE,S.CR_SCENE,S.CR_CONTEXT,S.CR_RESULT,S.CR_LINKER,S.NEXT_CR_DATE,S.NEXT_CR_CONTEXT,S.CREATE_TYPE,S.LINK_PHONE,S.LINK_MOBILE,S.SOLD_BY,S.OWNED_BY "
                        + ",S.TRANCE_TIME,S.TRANCE_USER,T.ADDRESS FROM TT_SALES_CR S,TM_CUSTOMER T WHERE S.CUSTOMER_NO=T.CUSTOMER_NO and s.dealer_code=t.dealer_code AND S.dealer_code=? "
                        + "AND S.D_KEY=0  AND S.VIN LIKE '%" + vin
                        + "%' AND S.CUSTOMER_NO=?  ORDER BY T.CUSTOMER_NAME ) P  ");
        List queryParam = new ArrayList();
        queryParam.add(dealerCode);
        queryParam.add(customerNo);
        List<Map> result = DAOUtil.findAll(sql.toString(), queryParam);

        return result;
    }
    @SuppressWarnings({ "rawtypes", "unchecked" })
    @Override
    public PageInfoDto queryRetainCusTrackInfoCampaign(String dealerCode) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql
        .append(" select 12781002 AS IS_SELECTED,dealer_code,REF_WEB_LINK,PRINCIPAL_NAME,PRINCIPAL_PHONE,PRINCIPAL_EMAIL,DISTRIBUTER_NAME,DISTRIBUTER_PHONE,DISTRIBUTER_EMAIL, CAMPAIGN_CODE, CAMPAIGN_NAME, SPOT, CAMPAIGN_BUDGET, BEGIN_DATE, END_DATE, "
                + " TARGET_CUSTOMER, CAMPAIGN_PERFORM_TYPE, APPLY_DATE,  APPLICANT, MEMO, "
                + " CUR_AUDITOR, CUR_AUDITING_STATUS,COM_AUDITING_STATUS, SERIES_CODE, OWNED_BY,TARGET_TRAFFIC,TARGET_NEW_CUSTOMERS,TARGET_NEW_ORDERS,TARGET_NEW_VEHICLES,OEM_TAG   "
                + " from TT_CAMPAIGN_PLAN WHERE  dealer_code = ?"
                + " AND D_KEY = 0 ");
        List queryParam = new ArrayList();
        queryParam.add(dealerCode);
        PageInfoDto page = DAOUtil.pageQuery(sql.toString(), queryParam);
        return page;
    }
    
    @SuppressWarnings("rawtypes")
	public List<Map> queryRetainCustrackforExport(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append(" select * from (");
        sb
                .append("SELECT "
                        + "  S.ITEM_ID,S.CUSTOMER_NO,"
                        + "T.CUSTOMER_NAME,S.VIN,S.dealer_code,S.CR_NAME,S.CR_TYPE,S.SCHEDULE_DATE,S.ACTION_DATE,S.CR_SCENE,"
                        + "S.CR_CONTEXT,S.CR_RESULT,S.CR_LINKER,S.NEXT_CR_DATE,S.NEXT_CR_CONTEXT,S.CREATE_TYPE,S.LINK_PHONE,"
                        + "S.LINK_MOBILE,S.SOLD_BY,S.OWNED_BY,S.TRANCE_TIME,S.TRANCE_USER,T.ADDRESS FROM TT_SALES_CR S,TM_CUSTOMER T "
                        + "WHERE S.CUSTOMER_NO=T.CUSTOMER_NO and s.dealer_code=t.dealer_code AND S.dealer_code='"
                        + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND S.D_KEY=0 ");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(queryParam.get("deStartDate"))) {
            sb.append(" and S.SCHEDULE_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("deStartDate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("deEndDate"))) {
            sb.append(" and S.SCHEDULE_DATE <= ? ");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("deEndDate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and S.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
      
        if(!StringUtils.isNullOrEmpty(queryParam.get("customerName"))){
            sb.append(" AND T.CUSTOMER_NAME=? ");
            queryList.add("%" + queryParam.get("customerName") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("linkMobile"))){
            sb.append(" AND S.LINK_MOBILE=? ");
            queryList.add("%" + queryParam.get("linkMobile") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("linkPhoe"))){
            sb.append(" AND S.LINK_PHONE=? ");
            queryList.add("%" + queryParam.get("linkPhoe") + "%");
        }    
        if (!StringUtils.isNullOrEmpty(queryParam.get("soldby"))){

            sb.append(" AND S.SOLD_BY=?");
            queryList.add(queryParam.get("soldby"));
        }else
        {
//            logger.debug("销售顾问应该为空的*********"+soldby);
//            sql.append(Utility.getOwnedByStr(con, "S", userid, orgcode, functionCode, entitycode));
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("executeStatus")))
        {
            if (queryParam.get("executeStatus") == "12781001")
            {
                sb.append(" AND S.CR_RESULT <> 0 AND S.CR_RESULT IS NOT NULL");
            }
            if (queryParam.get("executeStatus") == "12781002")
            {
                sb.append(" AND (S.CR_RESULT = 0 OR S.CR_RESULT IS  NULL)");
            }
        }

        sb.append("  ORDER BY T.CUSTOMER_NAME )s");   
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
        OperateLogDto operateLogDto=new OperateLogDto();
        operateLogDto.setOperateContent("保客导出");
        operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
        operateLogService.writeOperateLog(operateLogDto);
        return resultList;
    }


    
    /**
    * @author LiGaoqi
    * @date 2017年3月20日
    * @param traDto
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.customer.service.customerManage.RetainCusTrackService#modifySoldBy(com.yonyou.dms.common.domains.DTO.customer.CustomerTrackingDTO)
    */
    	
    @Override
    public void modifySoldBy(CustomerTrackingDTO traDto) throws ServiceBizException {
        String[] ids = traDto.getNoList().split(",");
        LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
        for (int i = 0; i < ids.length; i++) {
            String no = ids[i];
            CustomerPO cuspo = CustomerPO.findByCompositeKeys(loginInfo.getDealerCode(),no);
            cuspo.setInteger("DELAY_CONSULTANT", traDto.getSoldBy());
            cuspo.setInteger("SOLD_BY", traDto.getSoldBy());
            cuspo.setInteger("FAIL_CONSULTANT", 0);
            List<Object> salesPromotionList2 = new ArrayList<Object>();
            salesPromotionList2.add(no);
            salesPromotionList2.add(DictCodeConstants.D_KEY);
            salesPromotionList2.add(loginInfo.getDealerCode());
            List<TtSalesCrPO> salesPromotionPO2=TtSalesCrPO.findBySQL("select * from TT_SALES_CR where  CUSTOMER_NO= ? AND D_KEY= ? AND DEALER_CODE= ? ", salesPromotionList2.toArray());
            if(salesPromotionPO2!=null&&salesPromotionPO2.size()>0){
                for(int j=0;j<salesPromotionPO2.size();j++){
                    TtSalesCrPO crpo = salesPromotionPO2.get(j);
                    crpo.setInteger("SOLD_BY", traDto.getSoldBy());
                    crpo.setInteger("OWNED_BY", traDto.getSoldBy());
                    crpo.saveIt();
                }
            }
            cuspo.setInteger("OWNED_BY", traDto.getSoldBy());
            cuspo.setInteger("IS_UPLOAD", 12781002);
            cuspo.setDate("CONSULTANT_TIME", new Date());
            cuspo.saveIt();
        }
            
            
        
    }
    

        
}
