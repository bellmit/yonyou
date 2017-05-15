package com.yonyou.dms.repair.service.Precontract;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtBookingOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;



/**
* TODO 客户预约
* @author zhl
* @date 2017年3月27日
*/
@Service
public class PrecontractArrangeServiceImpl implements PrecontractArrangeService{

    @Override
    public PageInfoDto QueryChooseVin(Map<String, String> queryParam) throws ServiceBizException {
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        StringBuffer sb = new StringBuffer();
        sb.append( "SELECT  DISTINCT A.*,  ");
        sb.append( "B.OWNER_PROPERTY,B.OWNER_NAME, M.MODEL_NAME,S.SERIES_NAME,BB.BRAND_NAME ,");
        sb.append(" B.CONTACTOR_ZIP_CODE,B.CONTACTOR_EMAIL,B.CONTACTOR_ADDRESS,B.ADDRESS,B.PHONE,B.MOBILE,");
        sb.append( " (case when B.CONTACTOR_NAME IS NULL or B.CONTACTOR_NAME='' then B.OWNER_NAME else B.CONTACTOR_NAME end) AS CONTACTOR_NAME,");
        sb.append( " (case when B.CONTACTOR_PHONE IS NULL or B.CONTACTOR_PHONE='' then B.PHONE else B.CONTACTOR_PHONE end) AS CONTACTOR_PHONE,");
        sb.append( " (case when B.CONTACTOR_MOBILE IS NULL or B.CONTACTOR_MOBILE='' then B.MOBILE else B.CONTACTOR_MOBILE end) AS CONTACTOR_MOBILE,");
        sb.append( " B.GENDER,B.CT_CODE,B.CERTIFICATE_NO,B.CONTACTOR_GENDER,B.E_MAIL,B.BIRTHDAY,'' as IS_MILEAGE_MODIFY,'' as sell_date, ");
        sb.append( " '' as DELIVERER_DDD_CODE,'' as CHANGE_MILEAGE ");
        sb.append( " FROM (  " + CommonConstants.VM_VEHICLE +" ) A  LEFT JOIN ( " + CommonConstants.VM_OWNER +" ) B     on  A.OWNER_NO = B.OWNER_NO and A.DEALER_CODE = b.DEALER_CODE  ");
        sb.append(" LEFT JOIN TM_MODEL M on  A.DEALER_CODE =M.DEALER_CODE AND A.MODEL=M.MODEL_CODE  LEFT JOIN tm_series  S on  A.DEALER_CODE =S.DEALER_CODE AND A.SERIES=S.SERIES_CODE  ");
        sb.append("   LEFT JOIN tm_brand bb on  A.DEALER_CODE =bb.DEALER_CODE AND A.BRAND=bb.BRAND_CODE   ");
        sb.append( " where 1=1  ");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
        System.out.println(sb.toString());
        return result; 
    }

    /**
     * 查询条件设置
     * 
     * @param sb
     * @param queryParam
     * @param queryList
     */
    public void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {// 车系
            sb.append(" and A.VIN LIKE ?");
            queryList.add("%" +queryParam.get("license")+"%");
        }   
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {// VIN
            sb.append(" OR A.LICENSE LIKE ?");
            queryList.add("%" + queryParam.get("license") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {// VIN
            sb.append(" and A.VIN LIKE ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {// 车主
            sb.append(" and B.OWNER_NAME = ?");
            queryList.add(queryParam.get("ownerName"));
        }
       
    }

    @Override
    public String findBookingRecord(String vin, String license) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT A.* FROM  Tt_Booking_Order A WHERE 1=1  and A.BOOKING_ORDER_STATUS='" + CommonConstants.DICT_BOS_NOT_ENTER+ "'");
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(vin)) {// 车系
            sql.append(" and A.VIN = ?");
            queryList.add(vin);
        }   
        if (!StringUtils.isNullOrEmpty(license)) {// VIN
            sql.append(" and A.LICENSE= ?");
            queryList.add(license);
        }
        System.err.println("---------------------------------------");
        List<TtBookingOrderPO> findBySQL = TtBookingOrderPO.findBySQL(sql.toString(), vin, license);
       if(findBySQL.size()>0){
           return findBySQL.get(0).get("VIN").toString();
       }else{
           return    "";
       }
           
    }

    @Override
    public PageInfoDto findBookingOrder(String vin, String license) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT B.BOOKING_TYPE_NAME,A.* FROM  Tt_Booking_Order A  LEFT JOIN  tM_booking_TYPE  B ON A.BOOKING_TYPE_CODE =B.BOOKING_TYPE_CODE AND A.DEALER_CODE=B.DEALER_CODE   WHERE 1=1  and A.BOOKING_ORDER_STATUS=( " + CommonConstants.DICT_BOS_NOT_ENTER+ " )");
  /*      sql.append( "SELECT  DISTINCT A.*,  ");
        sql.append( "B.OWNER_PROPERTY,B.OWNER_NAME, M.MODEL_NAME,S.SERIES_NAME,BB.BRAND_NAME ,");
        sql.append(" B.CONTACTOR_ZIP_CODE,B.CONTACTOR_EMAIL,B.CONTACTOR_ADDRESS,B.ADDRESS,B.PHONE,B.MOBILE,");
        sql.append( " (case when B.CONTACTOR_NAME IS NULL or B.CONTACTOR_NAME='' then B.OWNER_NAME else B.CONTACTOR_NAME end) AS CONTACTOR_NAME,");
        sql.append( " (case when B.CONTACTOR_PHONE IS NULL or B.CONTACTOR_PHONE='' then B.PHONE else B.CONTACTOR_PHONE end) AS CONTACTOR_PHONE,");
        sql.append( " (case when B.CONTACTOR_MOBILE IS NULL or B.CONTACTOR_MOBILE='' then B.MOBILE else B.CONTACTOR_MOBILE end) AS CONTACTOR_MOBILE,");
        sql.append( " B.GENDER,B.CT_CODE,B.CERTIFICATE_NO,B.CONTACTOR_GENDER,B.E_MAIL,B.BIRTHDAY,'' as IS_MILEAGE_MODIFY,'' as sell_date, ");
        sql.append( " '' as DELIVERER_DDD_CODE,'' as CHANGE_MILEAGE ");
        sql.append( " FROM (  " + CommonConstants.VM_VEHICLE +" ) A  LEFT JOIN ( " + CommonConstants.VM_OWNER +" ) B     on  A.OWNER_NO = B.OWNER_NO and A.DEALER_CODE = b.DEALER_CODE  ");
        sql.append(" LEFT JOIN TM_MODEL M on  A.DEALER_CODE =M.DEALER_CODE AND A.MODEL=M.MODEL_CODE  LEFT JOIN tm_series  S on  A.DEALER_CODE =S.DEALER_CODE AND A.SERIES=S.SERIES_CODE  ");
        sql.append("   LEFT JOIN tm_brand bb on  A.DEALER_CODE =bb.DEALER_CODE AND A.BRAND=bb.BRAND_CODE   ");
        */
        List<Object> queryList = new ArrayList<Object>();
        if (!StringUtils.isNullOrEmpty(vin)) {// vin
            sql.append(" and A.VIN = ?");
            queryList.add(vin);
        }   
        if (!StringUtils.isNullOrEmpty(license)) {// 车牌
            sql.append(" and A.LICENSE= ?");
            queryList.add(license);
        } 
        return DAOUtil.pageQuery(sql.toString(), queryList);
    }

    @Override
    public PageInfoDto QuerySelectOwner(Map<String, String> queryParam) throws ServiceBizException {
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT B.LICENSE,A.*,B.VIN FROM (  " + CommonConstants.VM_OWNER +" ) A  LEFT JOIN ( " + CommonConstants.VM_VEHICLE +" ) B   ON A.DEALER_CODE =B.DEALER_CODE  AND A.OWNER_NO=B.OWNER_NO WHERE 1=1  ");
        List<Object> queryList = new ArrayList<Object>();
        this.setWhereOwner(sb, queryParam, queryList);
        PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
        System.out.println(sb.toString());
        return result; 
    }


     public void setWhereOwner(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
            if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {// 车系
                sb.append(" and A.OWNER_NO LIKE ?");
                queryList.add("%" +queryParam.get("license")+"%");
            }   
            if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {// VIN
                sb.append(" AND A.OWNER_SPELL LIKE ?");
                queryList.add("%" + queryParam.get("license") + "%");
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {// VIN
                sb.append(" and A.OWNER_NAME LIKE ?");
                queryList.add("%" + queryParam.get("vin") + "%");
            }
            if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {// 车主
                sb.append(" and B.LICENSE = ?");
                queryList.add(queryParam.get("ownerName"));
            }
           
        }

    @Override
    public PageInfoDto QueryselectEmployee(Map<String, String> queryParam) throws ServiceBizException {
        String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT  * from TM_EMPLOYEE WHERE 1=1  ");
        List<Object> queryList = new ArrayList<Object>();
        PageInfoDto result = DAOUtil.pageQuery(sb.toString(), null);
        System.out.println(sb.toString());
        return result; 
    }

    @Override
    public PageInfoDto queryPartStocks(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        double rate = 1 + Utility.getDouble(Utility.getDefaultValue("2034"));
        StringBuffer sqlsb = new StringBuffer();
        sqlsb.append("SELECT td.DEALER_NAME,A.PROVIDER_CODE,A.PROVIDER_NAME,B.OPTION_NO,A.COST_PRICE * "+rate);
        sqlsb.append(" AS NET_COST_PRICE,A.COST_AMOUNT * "+rate);
        sqlsb.append(" AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,A.PART_SPE_TYPE,A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME,");
        sqlsb.append(" A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE,");
        sqlsb.append(" A.CLAIM_PRICE, B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK, A.SAFE_STOCK,");
        sqlsb.append(" A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.PART_STATUS,");
        sqlsb.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER,");
        sqlsb.append(" A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS,");
        sqlsb.append(" A. SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA,");
        sqlsb.append(" B.IS_ACC,B.SUB_CATEGORY_CODE,B.BIG_CATEGORY_CODE,B.THD_CATEGORY_CODE,B.IS_STORAGE_SALE,");
        sqlsb.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY, ");  
        sqlsb.append(" D.OPTION_STOCK,A.INSURANCE_PRICE,  B.INSTRUCT_PRICE,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.NODE_PRICE,");
        sqlsb.append(" vst.STORAGE_NAME,B.IS_BACK FROM TM_PART_STOCK A ");
        sqlsb.append(" LEFT JOIN tm_dealer_basicinfo td ON A.DEALER_CODE=td.DEALER_CODE");
        sqlsb.append(" LEFT OUTER JOIN ("+CommonConstants.VM_PART_INFO+")");
        sqlsb.append(" B ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  )");
        sqlsb.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C WHERE DEALER_CODE");
        sqlsb.append(" in (select A.SHARE_ENTITY from ("+CommonConstants.VM_ENTITY_SHARE_WITH+") A where DEALER_CODE ='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        sqlsb.append(" AND biz_code = 'UNIFIED_VIEW' )");
        sqlsb.append(" AND C.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        sqlsb.append(" AND D_KEY="+CommonConstants.D_KEY);
        sqlsb.append(" GROUP BY DEALER_CODE,PART_NO ) D ");
        sqlsb.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )");
        sqlsb.append(" LEFT JOIN ("+CommonConstants.VM_STORAGE+")");
        sqlsb.append(" vst ON vst.DEALER_CODE=a.DEALER_CODE AND vst.storage_code=a.storage_code ");
        sqlsb.append(" WHERE A.DEALER_CODE in (select A.SHARE_ENTITY FROM ("+CommonConstants.VM_ENTITY_SHARE_WITH+") A WHERE DEALER_CODE =?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlsb.append(" AND biz_code = 'UNIFIED_VIEW' )");
        sqlsb.append(" AND A.DEALER_CODE = ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sqlsb.append(" AND A.D_KEY = ?");
        sqlsb.append(" AND A.PART_STATUS <>("+CommonConstants.DICT_IS_YES+")");
        params.add(CommonConstants.D_KEY);
        if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_CODE"))){
            sqlsb.append(" AND A.STORAGE_CODE like ?");
            params.add(queryParam.get("STORAGE_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_NO"))){
            sqlsb.append(" AND A.PART_NO like  ?");
            params.add("%"+queryParam.get("PART_NO")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_NAME"))){
            sqlsb.append(" AND A.PART_NAME like ?");
            params.add("%"+queryParam.get("PART_NAME")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_GROUP_CODE"))){
            sqlsb.append(" AND A.PART_GROUP_CODE = ?");
            params.add(queryParam.get("PART_GROUP_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("STORAGE_POSITION_CODE"))){
            sqlsb.append(" AND A.STORAGE_POSITION_CODE  LIKE  ?");
            params.add("%"+queryParam.get("STORAGE_POSITION_CODE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("SPELL_CODE"))){
            sqlsb.append(" AND A.SPELL_CODE LIKE ?");
            params.add("%"+queryParam.get("SPELL_CODE")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("PART_MODEL_GROUP_CODE_SET"))){
            sqlsb.append(" AND A.PART_MODEL_GROUP_CODE_SET = ?");
            params.add(queryParam.get("PART_MODEL_GROUP_CODE_SET"));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("BRAND"))){
            sqlsb.append(" AND B.BRAND = ?");
            params.add(queryParam.get("BRAND"));
        }    
        PageInfoDto pageinfoDto=DAOUtil.pageQuery(sqlsb.toString(), params);
        return pageinfoDto;
    }

    @Override
    public PageInfoDto queryLimit(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT E.DEALER_CODE,E.BOOKING_TYPE_CODE, E.BOOKING_TYPE_NAME，E.BEGIN_TIME,E.END_TIME,E.SATURATION,COUNT(E.BOOKING_COME_TIME) BOOKING_COUNT  FROM ");
        sql.append("  ( SELECT A.DEALER_CODE,A.BOOKING_TYPE_CODE,A.BOOKING_COME_TIME,D.BEGIN_TIME,D.END_TIME,D.SATURATION ,D.BOOKING_TYPE_NAME");
        sql.append(" FROM TT_BOOKING_ORDER A  LEFT JOIN(");
        sql.append(" SELECT B.DEALER_CODE,B.BOOKING_TYPE_CODE,B.BOOKING_TYPE_NAME,C.BEGIN_TIME,C.END_TIME,C.SATURATION ");
        sql.append(" FROM ("+CommonConstants.VM_BOOKING_TYPE+")  B ");
        sql.append(" LEFT JOIN TM_BOOKING_LIMIT C ON C.BOOKING_TYPE_CODE= B.BOOKING_TYPE_CODE AND C.DEALER_CODE = B.DEALER_CODE ");
        sql.append(" WHERE B.DEALER_CODE = ?) D ");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sql.append(" ON D.BOOKING_TYPE_CODE = A.BOOKING_TYPE_CODE AND D.DEALER_CODE = A.DEALER_CODE");
        sql.append(" AND ((HOUR(A.BOOKING_COME_TIME)>=HOUR(D.BEGIN_TIME) AND HOUR(A.BOOKING_COME_TIME)<HOUR(D.END_TIME)) OR (D.BEGIN_TIME IS NULL))");
        sql.append(" WHERE A.DEALER_CODE = ? " );
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sql.append(" AND A.D_KEY = ?");
        params.add(CommonConstants.D_KEY);
         Utility.getDateCond("A", "BOOKING_COME_TIME", queryParam.get("startdate"), queryParam.get("startdate"));
        if(!StringUtils.isNullOrEmpty(queryParam.get("bookingType"))){
              sql.append(" AND A.BOOKING_TYPE_CODE = ? ");
              params.add(queryParam.get("bookingType"));
         }  
         sql.append(" ) E  GROUP BY E.BOOKING_TYPE_CODE,E.BEGIN_TIME,E.END_TIME,E.SATURATION");
         PageInfoDto pageinfoDto=DAOUtil.pageQuery(sql.toString(), params);
         return pageinfoDto;
    }

    @Override
    public PageInfoDto queryLimitorder(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT A.DEALER_CODE,A.BOOKING_TYPE_CODE,A.BOOKING_ORDER_NO,A.LICENSE, ");
        sql.append(" A.VIN,A.BOOKING_COME_TIME, A.SERVICE_ADVISOR ,  ");
        sql.append(" A.CHIEF_TECHNICIAN FROM TT_BOOKING_ORDER A ");
        sql.append(" WHERE A.DEALER_CODE = ? " );
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        sql.append(" AND A.D_KEY = ?");
        params.add(CommonConstants.D_KEY);
        Utility.getDateCond("A", "BOOKING_COME_TIME", queryParam.get("startdate"), queryParam.get("startdate"));
        if(!StringUtils.isNullOrEmpty(queryParam.get("bookingType"))){
              sql.append(" AND A.BOOKING_TYPE_CODE = ? ");
              params.add(queryParam.get("bookingType"));
         }  
        PageInfoDto pageinfoDto=DAOUtil.pageQuery(sql.toString(), params);
        return pageinfoDto;
    }

    @Override
    public List<Map> getBookingTypes() throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT * FROM tm_booking_type");
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sql.toString(), params);
    }

    @Override
    public PageInfoDto querybookingpart(String bookingOrderNo) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append(   "SELECT A.*,B.DOWN_TAG,B.OEM_LABOUR_HOUR,B.CLAIM_LABOUR,B.ASSIGN_LABOUR_HOUR ");
        sql.append(  "FROM TT_BOOKING_ORDER_LABOUR A ");
        sql.append(  "LEFT JOIN TM_REPAIR_ITEM B ON A.DEALER_CODE=B.DEALER_CODE AND A.LABOUR_CODE=B.LABOUR_CODE AND A.MODEL_LABOUR_CODE=B.MODEL_LABOUR_CODE ");
        sql.append( "WHERE A.DEALER_CODE=? AND A.D_KEY=? AND A.BOOKING_ORDER_NO=?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        params.add(CommonConstants.D_KEY);
        params.add(bookingOrderNo);
        PageInfoDto pageinfoDto=DAOUtil.pageQuery(sql.toString(), params);
        return pageinfoDto;
    }

    @Override
    public PageInfoDto querybookingpartitem(String bookingOrderNo) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append( "select case when (x.activity_Code is null)or(x.activity_Code='') then y.sales_Price else x.sales_Price end as sales_Price, ");
        sql.append(" x.*,y.*,z.NODE_PRICE,Z.DOWN_TAG,(y.STOCK_QUANTITY + y.BORROW_QUANTITY - y.LEND_QUANTITY - y.LOCKED_QUANTITY) AS USEABLE_STOCK");
        sql.append(" from ( select model_Labour_Code,part_No,booking_Order_No,d_Key,update_Date,create_By,DEALER_CODE,ver,labour_Code, ");
        sql.append(" create_Date,obligated_Man,obligated_Date,package_Code,activity_Code,part_Name,booking_Quantity,update_By,is_Obligated,MAINTAIN_PACKAGE_CODE,item_Id , ");
        /*                            +" CLAIM_PRICE,sales_Price,STORAGE_POSITION_CODE ,LATEST_PRICE,COST_PRICE,NODE_PRICE,INSURANCE_PRICE, "*/
        sql.append(" a.sales_Price,case when (num is null and num2 is null) then 'OEMK'  when num is null then num2 else num end as storage_code ");
        sql.append("  from ( select  a.sales_Price,A.model_Labour_Code,A.part_No,A.booking_Order_No,A.d_Key,A.update_Date,A.create_By,A.DEALER_CODE,A.ver,A.storage_Code,A.labour_Code, ");
        sql.append(" A.create_Date,A.obligated_Man,A.obligated_Date,A.package_Code,A.activity_Code,A.part_Name,A.booking_Quantity,A.update_By,A.is_Obligated,A.MAINTAIN_PACKAGE_CODE,A.item_Id");
          /*  +",C.CLAIM_PRICE,C.sales_Price,C.STORAGE_POSITION_CODE ,C.LATEST_PRICE,C.COST_PRICE,D.NODE_PRICE,C.INSURANCE_PRICE "*/
        sql.append(" ,case  when (a.storage_code is null) or (a.storage_code='') then   ( ");
        sql.append(" select  b.storage_code from TM_PART_STOCK B where  B.part_no=A.part_no  and b.storage_code=a.storage_code and a.DEALER_CODE=b.DEALER_CODE ");
        sql.append("  and ( b.storage_code='OEMK' and (b.STOCK_QUANTITY + b.BORROW_QUANTITY - b.LEND_QUANTITY - b.LOCKED_QUANTITY)>=a.BOOKING_QUANTITY)) else  a.storage_code end as num ");
        sql.append(" , case  when (a.storage_code is null) or (a.storage_code='') then ( select  b.storage_code ");
        sql.append("  from (select max(STOCK_QUANTITY + BORROW_QUANTITY - LEND_QUANTITY - LOCKED_QUANTITY),part_no,DEALER_CODE,storage_code from TM_PART_STOCK group by part_no,DEALER_CODE,storage_code order by max(STOCK_QUANTITY + BORROW_QUANTITY - LEND_QUANTITY - LOCKED_QUANTITY) desc) B ");
        sql.append("where  B.part_no=A.part_no  and a.DEALER_CODE=b.DEALER_CODE ");
        sql.append("  fetch   first   1   rows   only ) else  a.storage_code end as num2 ");
        sql.append("  FROM TT_BOOKING_ORDER_PART A ");
        sql.append(" LEFT OUTER JOIN TM_PART_STOCK C  ON ( A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE = C.STORAGE_CODE ) ");
        sql.append("  LEFt join VM_PART_INFO D  on D.DEALER_CODE=C.DEALER_CODE and D.part_no=C.part_NO ");
        sql.append(" WHERE A.BOOKING_ORDER_NO=? and  A.DEALER_CODE = ?  AND A.D_KEY =?" );
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        params.add(bookingOrderNo);
        params.add(CommonConstants.D_KEY);
        sql.append(" )a  ) x inner join TM_PART_STOCK y on x.DEALER_CODE=y.DEALER_CODE and x.part_no=y.part_no and x.storage_code=y.storage_code ");
        sql.append(" inner join VM_PART_INFO z on z.DEALER_CODE=y.DEALER_CODE and z.part_no=y.part_no");
      
        PageInfoDto pageinfoDto=DAOUtil.pageQuery(sql.toString(), params);
        return pageinfoDto;
    }

    @Override
    public PageInfoDto querybookingitem(String bookingOrderNo) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT A.BOOKING_ORDER_NO,A.ITEM_ID,A.ADD_ITEM_CODE,B.ADD_ITEM_NAME,A.CHARGE_PARTITION_CODE,");
        sql.append( " B.ADD_ITEM_PRICE AS ADD_ITEM_AMOUNT,A.DEALER_CODE,A.REMARK FROM TT_BOOKING_ORDER_ADD_ITEM A " );
        sql.append(  " LEFT JOIN TM_BALANCE_MODE_ADD_ITEM B ON A.DEALER_CODE = B.DEALER_CODE AND A.ADD_ITEM_CODE = B.ADD_ITEM_CODE ");
        sql.append( " WHERE A.DEALER_CODE = ? AND A.BOOKING_ORDER_NO = ?");
        params.add(FrameworkUtil.getLoginInfo().getDealerCode());
        params.add(bookingOrderNo);
        PageInfoDto pageinfoDto=DAOUtil.pageQuery(sql.toString(), params);
        return pageinfoDto;
    }




 
    

}
