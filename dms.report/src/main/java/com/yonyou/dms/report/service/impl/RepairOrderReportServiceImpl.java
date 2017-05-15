
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.report
*
* @File name : RepairOrderReportServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2016年9月27日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月27日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.DefinedRowProcessor;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 维修
 * 
 * @author zhanshiwei
 * @date 2016年9月27日
 */
@Service

public class RepairOrderReportServiceImpl implements RepairOrderReportService {

    /**
     * 维修业务查询
     * 
     * @author zhanshiwei
     * @date 2016年9月27日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.RepairOrderService#queryRepairOrders(java.util.Map)
     */

    @Override
    public PageInfoDto queryRepairOrders(Map<String, String> queryParam) throws ServiceBizException {

        StringBuffer sb = new StringBuffer("select reo.RO_ID,reo.DEALER_CODE,reo.RO_NO,reo.RO_CREATE_DATE,reo.RO_TYPE,reo.RO_STATUS,reo.REPAIR_TYPE_CODE,rep.REPAIR_TYPE_NAME,\n");
        sb.append("reo.COMPLETE_TIME,reo.FOR_BALANCE_TIME,reo.WAIT_INFO_TAG,reo.WAIT_PART_TAG,reo.IN_MILEAGE,reo.OUT_MILEAGE,reo.DELIVERER,reo.DELIVERER_MOBILE,\n");
        sb.append("reo.END_TIME_SUPPOSED,mo.BRAND_NAME,mo.SERIES_NAME,mo.MODEL_NAME,reo.CUSTOMER_DESC,reo.RO_TROUBLE_DESC,reo.REMARK,\n");
        sb.append("veh.ENGINE_NO,reo.LABOUR_AMOUNT,reo.REPAIR_PART_AMOUNT,reo.SALES_PART_AMOUNT,reo.ADD_ITEM_AMOUNT,\n");
        sb.append("veh.VIN,veh.LICENSE,owr.OWNER_NAME,veh.SALES_DATE,reo.LAST_MAINTENANCE_DATE,reo.LAST_MAINTENANCE_MILEAGE,\n");
        sb.append("e.EMPLOYEE_NAME AS SERVICE_ADVISOR_ASS, \n");// -- 服务顾问
        sb.append("bac.REPAIR_AMOUNT\n");// -- 维修金额
        sb.append("from TT_REPAIR_ORDER reo\n");
        sb.append("left join TM_VEHICLE veh on reo.VEHICLE_ID=veh.VEHICLE_ID and reo.DEALER_CODE=veh.DEALER_CODE\n");
        sb.append("left join TM_OWNER   owr on reo.OWNER_ID=owr.OWNER_ID and veh.OWNER_ID=owr.OWNER_ID and reo.DEALER_CODE=owr.DEALER_CODE\n");
       
    /*    sb.append("left  join   tm_brand   br  on   veh.BRAND_CODE = br.BRAND_ID\n");
        sb.append("left  join   TM_SERIES  se  on   veh.SERIES_CODE=se.SERIES_ID\n");
        sb.append("left  join   TM_MODEL   mo  on   veh.MODEL_CODE=mo.MODEL_ID\n");*/
        sb.append("left join vw_modelinfo  mo on  mo.BRAND_CODE=veh.BRAND_CODE  and mo.MODEL_CODE=veh.MODEL_CODE and veh.SERIES_CODE=mo.SERIES_CODE \n");
        
        sb.append("left join    tm_employee e on reo.SERVICE_ADVISOR_ASS=e.EMPLOYEE_NO and reo.DEALER_CODE=e.DEALER_CODE\n");
        sb.append("left join TT_BALANCE_ACCOUNTS bac on reo.RO_ID=bac.RO_ID and reo.DEALER_CODE=bac.DEALER_CODE \n");
        sb.append("left join TM_REPAIR_TYPE rep on reo.REPAIR_TYPE_CODE=rep.REPAIR_TYPE_CODE and reo.DEALER_CODE=rep.DEALER_CODE \n");
        sb.append(" where 1=1\n");
        List<Object> queryList = new ArrayList<Object>();
        this.setRepairOrderWhere(sb, queryParam, queryList);
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 维修业务查询查询条件设置
     * 
     * @author zhanshiwei
     * @date 2016年9月27日
     * @param sb
     * @param queryParam
     * @param queryList
     */

    @SuppressWarnings("rawtypes")
	public void setRepairOrderWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
    	 LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	 Map repairJurisdiction=loginInfo.getRepair();
    	 if(StringUtils.isEquals(repairJurisdiction.get("12111001"),"false")){
         	if(StringUtils.isEquals(queryParam.get("repairTypeCode"),"SQWX")){
         		sb.append(" and reo.REPAIR_TYPE_CODE<>'SQWX'");
         	}else{
         		sb.append(" and reo.REPAIR_TYPE_CODE<>'SQWX'");
         		if(!StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))){
         			sb.append(" and reo.REPAIR_TYPE_CODE=?");
         			queryList.add(queryParam.get("repairTypeCode"));
         		}
         	}
         }else{
             if(!StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))){
            	 sb.append(" and reo.REPAIR_TYPE_CODE=?");
            	 queryList.add(queryParam.get("repairTypeCode"));
             }
         }
        if (!StringUtils.isNullOrEmpty(queryParam.get("roNo"))) {
            sb.append(" and reo.RO_NO like ?");
            queryList.add("%" + queryParam.get("roNo") + "%");
        }
        sb.append(" and reo.RO_CREATE_DATE>= ?");
        queryList.add(DateUtil.parseDefaultDate(queryParam.get("roCreate_startdate")));
        sb.append(" and reo.RO_CREATE_DATE<?");
        queryList.add(DateUtil.addOneDay(queryParam.get("roCreate_enddate")));
        if (!StringUtils.isNullOrEmpty(queryParam.get("roStatus"))) {
            sb.append(" and reo.RO_STATUS= ?");
            queryList.add(Integer.parseInt(queryParam.get("roStatus")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("roType"))) {
            sb.append(" and reo.RO_TYPE= ?");
            queryList.add(Integer.parseInt(queryParam.get("roType")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
            sb.append(" and reo.SERVICE_ADVISOR_ASS= ?");
            queryList.add(queryParam.get("consultant"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
            sb.append(" and veh.LICENSE like ?");
            queryList.add("%" + queryParam.get("license") + "%");
        }

        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and veh.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
            sb.append(" and veh.BRAND_CODE = ?");
            queryList.add(queryParam.get("brandCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
            sb.append(" and veh.SERIES_CODE = ?");
            queryList.add(queryParam.get("seriesCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
            sb.append(" and veh.MODEL_CODE = ?");
            queryList.add(queryParam.get("modelCode"));
        }
    }

    /**
     * 本月进厂
     * 
     * @author zhanshiwei
     * @date 2016年9月28日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.RepairOrderReportService#queryEnterFactory(java.util.Map)
     */

    @Override
    public PageInfoDto queryEnterFactory(Map<String, String> queryParam) throws ServiceBizException {
   /*     StringBuffer sb1 = new StringBuffer("from (select RO_ID,DEALER_CODE,REPAIR_TYPE_CODE,VEHICLE_ID,RO_CREATE_DATE from TT_REPAIR_ORDER ");
        StringBuffer sb = new StringBuffer("select\n");
        sb.append(" RO_CREATE_DATE,sum(REPAIR_TYPE_CODE='1') as  serious_num\n");
        sb.append(" ,sum(REPAIR_TYPE_CODE='1001')   as  ordinary_num\n");
        sb.append(" ,sum(REPAIR_TYPE_CODE='1002')   as  maintain_num\n");
        sb.append(" ,sum(REPAIR_TYPE_CODE='101')    as  decorate_num\n");
        sb.append(" ,count(distinct t1.VEHICLE_ID,date_format(t1.RO_CREATE_DATE,'%Y-%c-%d')) as car_num\n");
        sb.append(" ,count(RO_ID) as cars_num,t1.DEALER_CODE\n");

        sb1.append("where 1=1");
        List<Object> queryList = new ArrayList<Object>();
        sb1.append(" and RO_CREATE_DATE>= ?");
        queryList.add(DateUtil.parseDefaultDate(queryParam.get("roCreate_startdate")));
        sb1.append(" and RO_CREATE_DATE<?");
        queryList.add(DateUtil.addOneDay(queryParam.get("roCreate_enddate")));
        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
            sb1.append(" and SERVICE_ADVISOR_ASS= ?");
            queryList.add(queryParam.get("consultant"));
        }
        sb1.append(") t1");
        sb.append(sb1);
        sb.append(" GROUP BY date_format(t1.RO_CREATE_DATE,'%Y-%c-%d'),DEALER_CODE\n");*/
    	 List<Object> queryList = new ArrayList<Object>();
    	// StringBuffer sb = new StringBuffer("");
    	String sql= setRepairTypesql(queryList,queryParam);
        PageInfoDto id = DAOUtil.pageQuery(sql, queryList);
        return id;
    }
    
    /**
     * 本月进厂sql
     * @param sb1
     */
    @SuppressWarnings("unused")
	public String setRepairTypesql(List<Object> queryList,Map<String, String> queryParam){
        StringBuilder sb = new StringBuilder("select  REPAIR_TYPE_ID,DEALER_CODE,REPAIR_TYPE_CODE,REPAIR_TYPE_NAME from TM_REPAIR_TYPE ORDER BY CREATED_AT desc ");
        final StringBuilder sb1=new StringBuilder("");
         sb1.append("SELECT\n")
			.append("  RO_ID,DEALER_CODE,RO_CREATE_DATE,\n")
		    .append("   count(distinct VEHICLE_ID)+'' as CAR_NUM\n")
		    .append("  ,count(RO_ID)+'' as CARS_NUM \n");
         final  StringBuilder sb2 = new StringBuilder("");
        List<Object> params = new ArrayList<Object>();
        DAOUtil.findAll(sb.toString(), params, new DefinedRowProcessor() {
            @Override
            protected void process(Map<String, Object> row) {
            	sb1.append("  ,sum(REPAIR_TYPE_CODE='").append(row.get("REPAIR_TYPE_CODE")).append("')+'' as '").append(row.get("REPAIR_TYPE_CODE")).append("'\n");
            }
        });
   
		sb1.append("FROM\n")
		.append("  TT_REPAIR_ORDER\n")
		.append("WHERE\n")
		.append("  1 = 1\n")
		.append("AND RO_CREATE_DATE >= ?\n")
		.append("AND RO_CREATE_DATE <? \n");
		queryList.add(DateUtil.parseDefaultDate(queryParam.get("roCreate_startdate")));
		queryList.add(DateUtil.addOneDay(queryParam.get("roCreate_enddate")));
		if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
			sb1.append(" and SERVICE_ADVISOR_ASS= ?");
			queryList.add(queryParam.get("consultant"));
		}
		sb1.append(" GROUP BY DMsdata_format(RO_CREATE_DATE,'").append(CommonConstants.SIMPLE_DATE_FORMAT).append("'),DEALER_CODE \n");
		

		return sb1.toString();
    }

    /**
     * 技师维修台次统计
     * 
     * @author zhanshiwei
     * @date 2016年10月9日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.RepairOrderReportService#queryEemployeeemployeeRepair(java.util.Map)
     */

    @Override
    public PageInfoDto queryEemployeeemployeeRepair(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        StringBuffer sb = new StringBuffer("select reo.SERVICE_ADVISOR_ASS,reo.RO_ID,reo.DEALER_CODE,rea.TECHNICIAN,\n");
        sb.append("       em.EMPLOYEE_NAME,\n");
        sb.append("       reo.RO_CREATE_DATE,\n");
        sb.append("       COUNT(*) as REPAIR_NUM \n");
        sb.append(" from TT_REPAIR_ORDER reo\n");
        sb.append(" INNER JOIN   TT_RO_ASSIGN rea on reo.RO_ID=rea.RO_ID\n");
        sb.append("left join TM_EMPLOYEE em  on rea.TECHNICIAN=em.EMPLOYEE_NO\n");

        sb.append(" where 1=1");

        sb.append(" and reo.RO_CREATE_DATE>= ?\n");
        queryList.add(DateUtil.parseDefaultDate(queryParam.get("roCreate_startdate")));
        sb.append(" and reo.RO_CREATE_DATE<?\n");
        queryList.add(DateUtil.addOneDay(queryParam.get("roCreate_enddate")));
        if (!StringUtils.isNullOrEmpty(queryParam.get("consultant"))) {
            sb.append(" and rea.TECHNICIAN= ?");
            queryList.add(queryParam.get("consultant"));
        }

        if (StringUtils.isEquals(queryParam.get("statisticalPattern"), "day")) {
            sb.append("GROUP BY SERVICE_ADVISOR_ASS,date_format(reo.RO_CREATE_DATE,'%Y-%c-%d')\n");
        } else {
            sb.append("GROUP BY SERVICE_ADVISOR_ASS,date_format(reo.RO_CREATE_DATE,'%Y-%c')\n");
        }
        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

    /**
     * 技师工时统计
     * 
     * @author zhanshiwei
     * @date 2016年10月11日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.report.service.impl.RepairOrderReportService#labourAmount(java.util.Map)
     */

    @Override
    public PageInfoDto labourAmount(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();

        StringBuffer sb = new StringBuffer("select r2.DEALER_CODE,case when (COUNT(r2.RO_LABOUR_ID)) = 1 then r2.EMPLOYEE_NAME ELSE '合派' end as EMPLOYEE_NAME \n");
        sb.append("     ,case when (COUNT(r2.RO_LABOUR_ID)) = 1 then r2.ORG_SHORT_NAME ELSE '' end as ORG_SHORT_NAME\n");
        sb.append("     ,sum(r2.ASSIGN_LABOUR_HOUR) as ASSIGN_LABOUR_HOUR,sum(r2.STD_LABOUR_HOUR) as STD_LABOUR_HOUR,sum(r2.LABOUR_AMOUNT) as LABOUR_AMOUNT,sum(r2.AFTER_DISCOUNT_AMOUNT) as AFTER_DISCOUNT_AMOUNT\n");
        sb.append("     from ( select \n");
        sb.append("      reo.RO_ID,reo.RO_NO,reo.DEALER_CODE,\n");
        sb.append("      rol.RO_LABOUR_ID,\n");// -- 工单维修项目ID
        sb.append("      sum(rol.STD_LABOUR_HOUR) as STD_LABOUR_HOUR,\n");// -- 标准工时
        sb.append("      sum(rol.ASSIGN_LABOUR_HOUR) as ASSIGN_LABOUR_HOUR,\n");// -- 派工工时
        sb.append("      rol.LABOUR_PRICE,\n");// -- 工时单价
        sb.append("      sum(rol.LABOUR_AMOUNT) as LABOUR_AMOUNT ,\n");// -- 工时费= 标准工时*工时单价
        sb.append("      rol.DISCOUNT,    \n");// -- 折扣率
        sb.append("      sum(rol.AFTER_DISCOUNT_AMOUNT) as AFTER_DISCOUNT_AMOUNT,\n");// -- 折后总金额
        // sb.append(" -- bac.RECEIVE_AMOUNT,\n");
        sb.append("      em.EMPLOYEE_NAME,\n");
        if (StringUtils.isEquals(DictCodeConstants.STATISTICAL_MODE_03, queryParam.get("statistical_mode"))) {
            sb.append("      repo.LABOUR_POSITION_NAME as ORG_SHORT_NAME\n");
        } else if (StringUtils.isEquals(DictCodeConstants.STATISTICAL_MODE_02, queryParam.get("statistical_mode"))) {
            sb.append("      em.WORKGROUP_CODE as ORG_SHORT_NAME\n");
        } else {
            sb.append("      org1.ORG_SHORT_NAME as ORG_SHORT_NAME\n");
        }

        sb.append("from  TT_REPAIR_ORDER reo\n");
        sb.append("INNER JOIN   TT_RO_LABOUR rol on reo.RO_ID=rol.RO_ID \n");// -- 工单维修项目明细
        sb.append("INNER JOIN   TT_RO_ASSIGN rea on reo.RO_ID=rea.RO_ID\n");// -- 工单派工分配明细
        // sb.append("-- left join TT_BALANCE_ACCOUNTS bac on reo.RO_ID=bac.RO_ID-- 结算单\n");
        sb.append("left join  TM_EMPLOYEE em   on rea.TECHNICIAN=em.EMPLOYEE_NO\n");
        sb.append("left join tm_organization  org1 on org1.ORG_CODE=em.ORG_CODE\n");
        sb.append("left join  TM_REPAIR_POSITION  repo    on rea.REPAIR_POSITION_ID=repo.REPAIR_POSITION_ID\n");

        sb.append(" where 1=1\n");

         //竣工日期
         sb.append(" and reo.COMPLETE_TIME>= ?");
         queryList.add(DateUtil.parseDefaultDate(queryParam.get("complete_startdate")));
         sb.append(" and reo.COMPLETE_TIME<?");
         queryList.add(DateUtil.addOneDay(queryParam.get("complete_enddate")));

        if (!StringUtils.isNullOrEmpty(queryParam.get("chargePartitionCode"))) {
            sb.append(" and rol.CHARGE_PARTITION_CODE= ?");
            queryList.add(queryParam.get("chargePartitionCode"));
        }

        sb.append("GROUP BY rol.RO_ID,rea.TECHNICIAN\n");
        sb.append(") r2 GROUP BY  r2.RO_LABOUR_ID\n");

        PageInfoDto id = DAOUtil.pageQuery(sb.toString(), queryList);
        return id;
    }

	
	/**
	 * 车辆进厂月报列显示
	* @author zhanshiwei
	* @date 2016年11月24日
	* @param queryParam
	* @return
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.report.service.impl.RepairOrderReportService#quRepairType(java.util.Map)
	*/
		
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> quRepairType(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("select  REPAIR_TYPE_ID,DEALER_CODE,REPAIR_TYPE_CODE,REPAIR_TYPE_NAME from TM_REPAIR_TYPE ORDER BY CREATED_AT desc ");
        return DAOUtil.findAll(sb.toString(), new ArrayList<Object>());
	}

	
	
	/**
	 * 车辆进厂月报导出
	* @author zhanshiwei
	* @date 2016年11月24日
	* @param queryParam
	* @return
	* @throws ServiceBizException
	* (non-Javadoc)
	* @see com.yonyou.dms.report.service.impl.RepairOrderReportService#exportenterFactory(java.util.Map)
	*/
		
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> exportenterFactory(Map<String, String> queryParam) throws ServiceBizException {
		List<Object> queryList = new ArrayList<Object>();
    	String sql= setRepairTypesql(queryList,queryParam);
		return DAOUtil.findAll(sql, queryList);
	}
}
