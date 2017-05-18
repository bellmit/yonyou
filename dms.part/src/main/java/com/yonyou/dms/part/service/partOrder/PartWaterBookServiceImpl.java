
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartWaterBookServiceImpl.java
*
* @Author : zhansw
*
* @Date : 2017年5月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月15日    zhansw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.part.service.partOrder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
*配件流水账号
* @author zhansw
* @date 2017年5月15日
*/
@Service
public class PartWaterBookServiceImpl implements PartWaterBookService{

    private static final Logger logger = LoggerFactory.getLogger(PartWaterBookService.class);

    @Autowired
    private CommonNoService commonNoService;

    /**
     * 业务描述: 查询符合条件的库存配件
    * @author zhansw
    * @date 2017年5月15日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.partOrder.PartWaterBookService#queryPartStockInfoUnifiedView(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryPartStockInfoUnifiedView(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
        List<String> queryPrams=new ArrayList<>();
        getPartStockInfoUnifiedViewSql(sql, queryParam,queryPrams);
       
        return  queryStockInfoUnifiedView(sql, queryPrams);
    }
    
    public void getPartStockInfoUnifiedViewSql(StringBuffer sql,Map<String, String> queryParam,List<String> queryPrams){

        String stor = "";
        String no = "";
        String name = "";
        String group = "";
        String partModel = "";
        String position = "";
        String spell = "";
        String salePrice = "";
        String stockCount = "";
        String partBrand = "";
        String entityCodeStr1="";
        String entityCodeStr2="";
       // String brand="";
        
        String entityCodeLocal=queryParam.get("entityCodeLocal");//FrameworkUtil.getLoginInfo().getDealerCode();
        String partNo=queryParam.get("partNo");
        String partName=queryParam.get("partName");
        String storageCode=queryParam.get("storageCode");
        String entityCode=FrameworkUtil.getLoginInfo().getDealerCode();
        
        if(Utility.testString(entityCodeLocal)){
            entityCodeStr1=" and C.Dealer_Code = '"+entityCodeLocal+"' ";
        }
        
        if(Utility.testString(entityCodeLocal)){
            entityCodeStr2=" and A.Dealer_Code = '"+entityCodeLocal+"' ";
        }


    /*    if (brand != null && brand.trim().length() > 0)
        {
            partBrand = " and B.BRAND =  '" + brand + "'   ";
        }
        else
        {
            partBrand = " and  1=1 ";
        }

        if (sale != null && sale.trim().length() > 0)
        {
            salePrice = " and A.SALES_PRICE > 0   ";
        }
        else
        {
            salePrice = " and  1=1 ";
        }

        if (stock != null && stock.trim().length() > 0)
        {
            stockCount = " and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY)>0 ";
        }
        else
        {
            stockCount = " and  1=1 ";
        }
        position = Utility.getLikeCond("A", "STORAGE_POSITION_CODE", positionCode, "AND");
        spell = Utility.getLikeCond("A", "SPELL_CODE", spellCode, "AND");*/
        //
        if (storageCode != null && !storageCode.equals(""))
        {

            stor = " and A.STORAGE_CODE = '" + storageCode + "' ";
        }
        else
        {
            stor = " and  1 = 1 ";
        }
        //
        no = Utility.getLikeCond("A", "PART_NO", partNo, "AND");
        name = Utility.getLikeCond("A", "PART_NAME", partName, "AND");
      /*  if (groupCode != null && !groupCode.equals(""))
        {

            group = " and A.PART_GROUP_CODE = " + groupCode + " ";
        }
        else
        {
            group = " and  1 = 1 ";
        }*/
      //  partModel = Utility.getLikeCond("A", "PART_MODEL_GROUP_CODE_SET", model, "AND");
        String a =commonNoService.getDefalutPara(CommonConstants.DEFAULT_PARA_PART_RATE+"");
        double rate = 1 + Utility.getDouble(a);
        sql
                .append(" SELECT B.OPTION_NO,A.COST_PRICE*"
                        + rate
                        + "  AS NET_COST_PRICE,A.COST_AMOUNT*"
                        + rate
                        + " AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MAIN_TYPE,A.PART_SPE_TYPE,A.PART_MODEL_GROUP_CODE_SET,A.Dealer_Code, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, "
                        +"  (select dealer_shortname from TM_DEALER_BASICINFO db where db.dealer_code=a.dealer_code) as dealer_shortname,"
                        + " A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, "
                        + " A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK, "
                        + " A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.PART_STATUS, "
                        + " A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER, "
                        + " A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS,"
                        + " A. SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA,"
                        + "   B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY,   "
                        + " D.OPTION_STOCK,A.INSURANCE_PRICE,  B.INSTRUCT_PRICE,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.NODE_PRICE "
                        + " ,vst.STORAGE_NAME as STORAGE_CODE_NAME FROM TM_PART_STOCK A  LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+") B"
                        + " ON ( A.Dealer_Code = B.Dealer_Code AND A.PART_NO= B.PART_NO  ) "
                        + " LEFT OUTER JOIN (select Dealer_Code, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C WHERE Dealer_Code "
                        + " in (select SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+")vm where Dealer_Code ='"+entityCode+"' and biz_code = 'UNIFIED_VIEW' ) "
                        + entityCodeStr1
                        + " AND D_KEY="
                        + CommonConstants.D_KEY
                        + " GROUP BY Dealer_Code,PART_NO ) D "
                        + " ON ( A.Dealer_Code = D.Dealer_Code AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )"
                        + " left join ("+CommonConstants.VM_STORAGE+") vst on vst.Dealer_Code=A.Dealer_Code and vst.STORAGE_CODE=A.STORAGE_CODE "
                        + "WHERE A.Dealer_Code  "
                        + " in (select SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+")vm where Dealer_Code ='"+entityCode+"' and biz_code = 'UNIFIED_VIEW' ) "
                        + entityCodeStr2
                        + " AND A.D_KEY =  "
                        + CommonConstants.D_KEY + partModel + stor + no + name + group + position
                        + spell + salePrice + stockCount + partBrand);
  /*      if (DictCodeConstants.DICT_IS_YES.equals(judgePartsRepair))
        {
            sql.append(" AND A.PART_STATUS<>" + judgePartsRepair + " ");
        }
        if (partStatus != null && !"".equals(partStatus))
        {
            if (DictCodeConstants.DICT_IS_YES.equals(partStatus.trim()))
            {
                //查詢停用的配件
                sql.append(" AND A.PART_STATUS=" + partStatus + " ");
            }else
            //if (DictDataConstant.DICT_IS_NO.equals(partStatus.trim()))
            {
                //查沒有停用的
                sql.append(" AND A.PART_STATUS<>" + DictCodeConstants.DICT_IS_YES + " ");
            }
        }
        if (isStopIsZero != null && !"".equals(isStopIsZero.trim()))
        {
            if (isStopIsZero.equals(DictCodeConstants.DICT_IS_YES))
            {
                //主数据停用本地库存为零的
                sql.append(" AND (B.PART_STATUS=" + DictCodeConstants.DICT_IS_YES
                        + "   AND  (A.STOCK_QUANTITY=0 OR A.STOCK_QUANTITY is null )) ");
            }
        }*/
        String[] stoC = Utility.getStorageByUserId(FrameworkUtil.getLoginInfo().getUserId()).split(",");
        sql.append(" AND ( 1=2 ");
        for (int i = 0; i < stoC.length; i++)
        {
            if (stoC[i] != null && !"".equals(stoC[i].trim()))
            {
                sql.append(" OR A.STORAGE_CODE=" + stoC[i] + "  ");
            }
        }
        sql.append(" ) ");
      /*  if (partMainType != null && !"".equals(partMainType.trim()))
        {
            sql.append(" AND A.PART_MAIN_TYPE=" + partMainType + " ");
        }*/


       logger.debug("sql00= " + sql);
         
     
      

    }
    
    public PageInfoDto queryStockInfoUnifiedView(StringBuffer sql,List<String> queryParam){
       return DAOUtil.pageQuery(sql.toString(), queryParam);
    }

    @Override
    public PageInfoDto queryPartDetail(Map<String, String> queryParam) throws ServiceBizException {

        StringBuffer sql = new StringBuffer("");
        List<String> quryParams=new ArrayList<>();
        getqueryPartDetailSql(sql, queryParam, quryParams);


        return DAOUtil.pageQuery(sql.toString(), quryParams);

        }

    public void getqueryPartDetailSql( StringBuffer sql,Map<String, String> queryParam,List<String> quryParams){
        String mypartNo = "";
        String mypartName = "";
        String storage = "";
        String beginTime = "";
        String endTime = "";
        String myType = "";
        String batch = "";
        String partName="";
        String batchNO="";
        
        String partNo = queryParam.get("PART_NO");
        String storageCode =  queryParam.get("STORAGE_CODE");
        String type =  queryParam.get("PARTITION");
        String begin =  queryParam.get("beginTime");
        String end =  queryParam.get("endTime");
        String entityCode= queryParam.get("DEALER_CODE");
        
        
        beginTime =  Utility.getDateCond("pf", "OPERATE_DATE", begin, end); //查询条件应该是操作时间OPERATE_DATE comg2011-07-28 DMS-3827 
             
        if (partNo != null && partNo.trim().length() != 0) {
            mypartNo = " and pf.PART_NO = '" + partNo + "' ";
        }else { mypartNo = " and 1 = 1 " ;};
        mypartName=Utility.getLikeCond("pf", "PART_NAME", partName, "AND");
        if( storageCode !=null && storageCode.trim().length()>0){
            storage = " and pf.STORAGE_CODE = '"+storageCode+"'  ";
        }else { storage = " and  1=1 "; }
        batch=Utility.getLikeCond("pf", "PART_BATCH_NO", batchNO, "AND");
        
      sql.append(" select pf.FLOW_ID, pf.DEALER_CODE, pf.STORAGE_CODE, pf.PART_NO, pf.PART_BATCH_NO, pf.PART_NAME, "
                + " pf.LICENSE, pf.SHEET_NO, pf.IN_OUT_TYPE, pf.IN_OUT_TAG, pf.STOCK_IN_QUANTITY, "
                + " pf.STOCK_OUT_QUANTITY, pf.COST_PRICE, pf.COST_AMOUNT,(cast(pf.IN_OUT_TAXED_PRICE AS decimal(14,2))) AS IN_OUT_TAXED_PRICE, " 
                + " pf.IN_OUT_NET_PRICE, pf.IN_OUT_NET_AMOUNT, pf.IN_OUT_TAXED_AMOUNT, pf.STOCK_QUANTITY, "
                + " pf.CUSTOMER_NAME, pf.CUSTOMER_CODE, pf.OPERATOR, pf.OPERATE_DATE, pf.CREATED_BY, "
                + " pf.CREATED_AT, pf.UPDATED_BY, pf.UPDATED_AT, pf.VER , emp.EMPLOYEE_NAME as OPERATOR_NAME  "
                + " from TT_PART_FLOW pf "
                + " left join TM_EMPLOYEE emp on emp.DEALER_CODE=pf.DEALER_CODE and emp.EMPLOYEE_NO=pf.OPERATOR "
                + " WHERE pf.DEALER_CODE = '"+entityCode+"' "
                + " and pf.D_KEY = "+CommonConstants.D_KEY +" "
                + mypartNo
                + mypartName                    
                + storage 
                + beginTime
                + endTime
                + myType
                + batch );
                logger.debug("sql= "+sql);
    }

    @Override
    public List<Map> queryPartStockInfoUnifiedViewList(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
        List<String> queryPrams=new ArrayList<>();
        getPartStockInfoUnifiedViewSql(sql, queryParam,queryPrams);
        return  DAOUtil.findAll(sql.toString(), queryPrams);
    }

    @Override
    public List<Map> exportPartWaterBookItem(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer("");
        List<String> queryPrams=new ArrayList<>();
        getqueryPartDetailSql(sql, queryParam,queryPrams);
        return  DAOUtil.findAll(sql.toString(), queryPrams);
    }
}
