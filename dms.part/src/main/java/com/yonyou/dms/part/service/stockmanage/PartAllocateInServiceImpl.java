package com.yonyou.dms.part.service.stockmanage;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TmPartStockPO;
import com.yonyou.dms.common.domains.PO.basedata.TtAccountsTransFlowPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartMonthReportPO;
import com.yonyou.dms.common.domains.PO.stockmanage.PartAllocateInItemPO;
import com.yonyou.dms.common.domains.PO.stockmanage.PartAllocateInPO;
import com.yonyou.dms.common.domains.PO.stockmanage.TtPartAllocOutISrcePO;
import com.yonyou.dms.common.domains.PO.stockmanage.TtPartAllocateOutSourcePO;
import com.yonyou.dms.common.domains.PO.stockmanage.TtPaymentInfoPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonAS.domains.DTO.basedata.PartStockDTO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.PO.TmDefaultParaPO;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.impl.CommonNoServiceImpl;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.stockmanage.ListPartAllocateInItemDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateInDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartAllocateInItemDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.TmAccountingCycleDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TmDefaultParaDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TmPartStockDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TmPartStockItemDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TtPartAllocOutISrceDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TtPartAllocateInItemDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TtPartFlowDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TtPartMonthReportDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TtPartPeriodReportDTO;
import com.yonyou.dms.part.domains.DTO.stockmanage.TtPaymentInfoDTO;
import com.yonyou.f4.common.database.DBUtil;
/**
 * 调拨入库接口实现
 * @author xukl
 * @date 2016年8月10日
 */
@Service
public class PartAllocateInServiceImpl implements PartAllocateInService {
    private static final String factory = null;
    private static final Logger logger = LoggerFactory.getLogger(PartAllocateInServiceImpl.class);
    @Autowired
    private SystemParamService  systemparamservice;
    
    @Autowired
    private OperateLogService operateLogService;
    /**
     * 根据调拨入库单号查询调拨出库单号
    * TODO description
    * @author yujiangheng
    * @date 2017年4月25日
    * @param queryParam
    * @throws ServiceBizException
     */
    public void queryOutOrders(Map<String, String> queryParam) throws ServiceBizException{
        String[] allocateOntNo=null;
        if(queryParam.get("allocateInNo")!=""){
            //根据调拨入库单号查询调拨出库单号 如果不等于空，将出库单号取出来  参数：调拨入库单号、
            StringBuilder sb1 = new StringBuilder("SELECT From_Entity,Customer_Name,D_Key,Allocate_In_No,Stock_Out_Date,");
            sb1.append(" Out_Amount,DEALER_CODE,Ver,Allocate_Out_No,Lock_User,Customer_Code,"); 
            sb1.append(" Cost_Amount,Is_Payoff,Finished_Date,Credence,Is_Finished,HANDLER ");
            sb1.append(" FROM Tt_Part_Allocate_Out_Source ");
            sb1.append("WHERE 1=1  AND DEALER_CODE= '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
            List<Object> params = new ArrayList<Object>();
            if(!StringUtils.isNullOrEmpty(queryParam.get("allocateInNo"))){
                sb1.append(" and  Allocate_In_No LIKE ? ");
                params.add("'%"+queryParam.get("allocateInNo")+"%'");
            }
            List<Map> allocateOutSource=DAOUtil.findAll(sb1.toString(), params);
            if(allocateOutSource.size()>0){//判断是否有调拨出库的单号
                for(Map allocateOut:allocateOutSource){//将调拨出库的单号存入数组中
                    allocateOntNo[0]=(String)allocateOut.get("ALLOCATE_OUT_NO");
                }
            }
           //System.out.println(allocateOntNo.toString());
        }
        /*
 SELECT REMARK,DEALER_CODE, ALLOCATE_IN_NO,ALLOCATE_OUT_NO, CUSTOMER_CODE, STOCK_IN_DATE,  
STOCK_IN_VOUCHER, BEFOE_TAX_AMOUNT, IS_FINISHED, HANDLER, FINISHED_DATE,  IS_PAYOFF, LOCK_USER,
 CREDENCE,  CUSTOMER_NAME, TOTAL_AMOUNT ,IS_IDLE_ALLOCATION,IS_NET_TRANSFER 
 FROM TT_PART_ALLOCATE_IN  WHERE DEALER_CODE = '2100000' AND IS_FINISHED = 12781002  AND D_KEY = 0 
         */
    }
    /**
     * 根据查询条件查询调拨入库单
     *  @author xukl
     * @date 2016年8月10日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#qryAllocateInOrders(java.util.Map)
     */
    @Override
    public   PageInfoDto qryAllocateInOrders(Map<String, String> queryParam) throws ServiceBizException {
       
        //查询未入账的调拨入库单
      /*  SELECT REMARK,DEALER_CODE, ALLOCATE_IN_NO,ALLOCATE_OUT_NO, CUSTOMER_CODE, STOCK_IN_DATE, 
        STOCK_IN_VOUCHER, BEFOE_TAX_AMOUNT, IS_FINISHED, HANDLER, FINISHED_DATE, 
             IS_PAYOFF, LOCK_USER, CREDENCE,  CUSTOMER_NAME, TOTAL_AMOUNT ,IS_IDLE_ALLOCATION,IS_NET_TRANSFER 
from TT_PART_ALLOCATE_IN 
         WHERE DEALER_CODE = 'CL110200' AND IS_FINISHED = '12781002'  and ALLOCATE_IN_NO LIKE '%111%'
                */
        StringBuilder sb = new StringBuilder(" SELECT REMARK,DEALER_CODE, ALLOCATE_IN_NO,ALLOCATE_OUT_NO, CUSTOMER_CODE, STOCK_IN_DATE,  ");
        sb.append("STOCK_IN_VOUCHER, BEFOE_TAX_AMOUNT, IS_FINISHED, HANDLER, FINISHED_DATE,  IS_PAYOFF, LOCK_USER,");
        sb.append("CREDENCE,  CUSTOMER_NAME, TOTAL_AMOUNT ,");
        sb.append("(case when IS_IDLE_ALLOCATION=12781001  then 10571001 END )  IS_IDLE_ALLOCATION,");
        sb.append("(case when IS_NET_TRANSFER=12781001  then 10571001 END ) IS_NET_TRANSFER ");
        sb.append("FROM TT_PART_ALLOCATE_IN  WHERE DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
        sb.append("AND IS_FINISHED = 12781002  AND D_KEY = 0 ");
        List<Object> param= new ArrayList<Object>();
//        if(!StringUtils.isNullOrEmpty(queryParam.get("unitName"))){
//            sb.append(" and UNIT_NAME like ? ");
//            params.add("%"+queryParam.get("unitName")+"%");
//        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("allocateInNo"))){
            sb.append(" and  ALLOCATE_IN_NO LIKE ? ");
            param.add("%"+queryParam.get("allocateInNo")+"%");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), param);
//        System.out.println("--------------------------------111");
//        System.out.println(pageInfoDto.toString());
        return pageInfoDto;
    }
    /**
     * 通过调拨入库单号查询调拨入库明细
     *  @author xukl
     * @date 2016年8月10日
     * @param masterid
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#getPartAllocateInItemsById(java.lang.Long)
     */
    @Override
    public PageInfoDto getAllocateInItemsByAllocateInNo(String allocateInNo) throws ServiceBizException {
      //根据核对加锁得到 LOCK_USER的值，进行锁定，如果LOCK_USER的值不为锁定，则查询该单号下的明细，否则抛出异常
        // 核对是否加锁
//        String tableName = actionContext.getStringValue("TABLE_NAME");//TT_PART_ALLOCATE_IN
//        String noName = actionContext.getStringValue("NO_NAME");
//        String noValue = actionContext.getStringValue("NO_VALUE");
//        String lockName = actionContext.getStringValue("LOCK_NAME");
//        String lockFlag = actionContext.getStringValue("LOCK_FLAG");//0不加锁，1加锁
        String lockName=Utility.selLockerName("LOCK_USER", "TT_PART_ALLOCATE_IN", "Allocate_IN_No", allocateInNo);
        //System.out.println("___________________________________________________"+lockName);
        if(lockName.length()!=0){
            //用户锁定
           int lockFlag= Utility.updateByLocker("TT_PART_ALLOCATE_IN", FrameworkUtil.getLoginInfo().getEmployeeNo(),
                                                "Allocate_IN_No", allocateInNo,"LOCK_USER" );
           if(lockFlag==1){
               throw  new ServiceBizException("该单号已被"+lockName+"锁定！");
           }
        }
        //查询未入账的调拨入库单明细表
        StringBuilder sb = new StringBuilder("SELECT A.ITEM_ID,A.ALLOCATE_IN_NO,A.DEALER_CODE,A.STORAGE_CODE,C.STORAGE_NAME,A.STORAGE_POSITION_CODE,A.PART_BATCH_NO,A.PART_NO,");
        sb.append("A.PART_NAME,A.UNIT_CODE,D.UNIT_NAME,A.COST_AMOUNT,A.IN_QUANTITY,A.COST_PRICE,A.IN_PRICE,A.IN_AMOUNT,b.down_tag  ");
        sb.append("FROM tt_part_Allocate_in_item A ");
        sb.append("LEFT JOIN tm_part_info b ON a.dealer_code = b.dealer_code AND A.part_no = b.part_no ");
        sb.append(" LEFT JOIN tm_storage c ON c.DEALER_CODE=A.DEALER_CODE AND C.STORAGE_CODE=A.STORAGE_CODE ");
        sb.append("LEFT JOIN tm_UNIT D ON D.DEALER_CODE=A.DEALER_CODE AND D.UNIT_CODE=A.UNIT_CODE ");
        sb.append("WHERE 1=1  AND A.DEALER_CODE= '"+FrameworkUtil.getLoginInfo().getDealerCode()+"' ");
        sb.append("AND A.D_Key=0  ");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(allocateInNo)){
            sb.append(" and  A.Allocate_IN_No=? ");
            params.add(allocateInNo);
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), params);
        return pageInfoDto;
    }

    /**
     * 获取仓库代码下拉选
    * @author yujiangheng
    * @date 2017年4月25日
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#getStorageAllSelect()
     */
    @Override
    public List<Map> getStorageAllSelect() throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("SELECT DEALER_CODE,STORAGE_CODE,STORAGE_NAME FROM tm_storage  WHERE 1=1 ");
        List<Object> params = new ArrayList<Object>();
        List<Map> result=DAOUtil.findAll(sqlSb.toString(), params);
        return result;
    }
    /**
     * 查询符合条件的库存配件，用于增加调拨配件
    * @author yujiangheng 
    * @date 2017年4月27日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#queryStoragePartForAdd(java.util.Map)
     */
    @Override
    public PageInfoDto queryStoragePartForAdd(Map<String, String> queryParam) throws ServiceBizException {
        //查询参数
        String storageCode = queryParam.get("storageCode");
        String brand = queryParam.get("brand");
        String partNo = queryParam.get("PartNo");
        String spellCode = queryParam.get("spellCode");
        String partName = queryParam.get("partName");
        String groupCode = queryParam.get("partGroupCode");
        String positionCode = queryParam.get("storagePositionCode");
        String model = queryParam .get("partModelGroupCodeSet");
        String sale = queryParam.get("SALE");
        String stock = queryParam.get("STOCK");
        String partMainType=queryParam.get("PART_MAIN_TYPE");
        String isStopIsZero=queryParam.get("IS_STOP_AND_ZERO");//主数据已停用并且本地库存为零
      //  int pageSize =Integer.parseInt(queryParam.get("PAGE_SIZE"));
        String judgeRepairOrParts=queryParam.get("JUDGE_REPAIR_PARTS");//判断是客户接待界面的查配件还是配件库存查询
        String allPart = queryParam.get("ALL_PART");
        String partInfixName = queryParam.get("PART_INFIX_NAME");
        /*
         * 增加是否停用查询条件
         */
        //String partStatus=actionContext.getStringValue("PART_STATUS");//是否停用
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
        String apartInfixName = "";
        String aremark = "";
        String ghStorage = "";
        String partno= "";
        if (partInfixName!=null && partInfixName.trim().length()>0){
            apartInfixName = " and B.PART_INFIX_NAME like '" + partInfixName + "%' ";
        }
        
        aremark = Utility.getStrLikeCond("A", "REMARK",queryParam.get("remark"));
        if (brand != null && brand.trim().length() > 0)
        {
            partBrand = " and B.BRAND =  '" + brand + "'   ";
        }
        else
        {
            partBrand = " and  1=1 ";
        }
        if("12781002".equals(Utility.getDefaultValue("5433") )){
            ghStorage = " and TS.CJ_TAG=12781001 ";
        }else{
            ghStorage = " and  1=1 ";
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
        partno = Utility.getLikeCond("B", "part_no", partNo, "AND");
        position = Utility.getLikeCond("A", "STORAGE_POSITION_CODE", positionCode, "AND");
        spell = Utility.getLikeCond("A", "SPELL_CODE", spellCode, "AND");
        
        if (storageCode != null && !storageCode.equals(""))
        {

            stor = " and A.STORAGE_CODE = '" + storageCode + "' ";
        }
        else
        {
            stor = " and  1 = 1 ";
        }
        no = Utility.getLikeCond("A", "PART_NO", partNo, "AND");
        name = Utility.getLikeCond("A", "PART_NAME", partName, "AND");
        if (groupCode != null && !groupCode.equals(""))
        {

            group = " and B.PART_GROUP_CODE = " + groupCode + " ";
        }
        else
        {
            group = " and  1 = 1 ";
        }
        partModel = Utility.getLikeCond("A", "PART_MODEL_GROUP_CODE_SET", model, "AND");
        String a = String.valueOf(CommonConstants.DEFAULT_PARA_PART_RATE) ;
        double rate = 1 + Utility.getDouble(a);
        
        StringBuilder sb = new StringBuilder("SELECT B.OPTION_NO,A.COST_PRICE*" + rate    );
        sb.append("  AS NET_COST_PRICE,A.COST_AMOUNT*" +rate );
        sb.append(" AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MODEL_GROUP_CODE_SET,A.PART_MAIN_TYPE,");
        sb.append("A.PART_SPE_TYPE,A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE,TS.STORAGE_NAME, A.STORAGE_POSITION_CODE, A.PART_NAME, ");
        sb.append("A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE,");
        sb.append("A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK,  ");
        sb.append("A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY,TS.CJ_TAG, A.PART_STATUS, A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER,");
        sb.append("A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS,  ");
        sb.append("A. SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA,");
        sb.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY,   ");
        sb.append("D.OPTION_STOCK,A.INSURANCE_PRICE,B.INSTRUCT_PRICE, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.NODE_PRICE ");
      
        //返回开关控制参数  Utility.getDefaultValue("1180");
        if(Utility.getDefaultValue("1180") != null && 
                Utility.getDefaultValue("1180") .equals(CommonConstants.DICT_IS_YES)){
            sb.append(",B.PART_INFIX,F.POS_CODE,E.POS_NAME");
        } else{
            sb.append(",'' as PART_INFIX,'' as POS_CODE,'' as POS_NAME");
        }
       sb.append(" FROM TM_PART_STOCK A LEFT OUTER JOIN ( "+CommonConstants.VM_PART_INFO+") B ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  ) ");
       sb.append( " LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C " );
       sb.append(" WHERE DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+ "' AND D_KEY="+ CommonConstants.D_KEY);
       sb.append( " GROUP BY DEALER_CODE,PART_NO ) D ");
       sb.append( " ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO )");
        
       if(Utility.getDefaultValue("1180") != null && Utility.getDefaultValue("1180").equals("12781001")){
           sb.append(" LEFT JOIN TW_POS_INFIX_RELATION F ON A.DEALER_CODE = F.DEALER_CODE AND B.PART_INFIX  = F.PART_INFIX AND F.IS_VALID = " +CommonConstants.DICT_IS_YES); 
       sb.append(" LEFT JOIN TW_MALFUNCTION_POSITION E ON e.is_valid="+CommonConstants.DICT_IS_YES+" and A.DEALER_CODE = E.DEALER_CODE AND F.POS_CODE = E.POS_CODE ");
    }
       //sql.append("LEFT JOIN TM_STORAGE ts ON A.DEALER_CODE=ts.DEALER_CODE and A.STORAGE_CODE=ts.STORAGE_CODE");
       sb.append(" LEFT JOIN TM_STORAGE TS ON A.DEALER_CODE = TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE ");
       sb .append(" WHERE A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' " + " AND A.D_KEY =  ");
       sb .append(CommonConstants.D_KEY + partModel + stor + no + name+ghStorage + group + position
               + spell + salePrice + partno+stockCount + partBrand+apartInfixName + aremark);
       if (CommonConstants.DICT_IS_YES.equals(judgeRepairOrParts))
       {
           sb.append(" AND A.PART_STATUS<>" + judgeRepairOrParts+ " ");
       }
//       if (partStatus != null && !"".equals(partStatus))//如果配件状态为12781001 则在前台页面显示：配件已经停用！
//       {
//           if (CommonConstants.DICT_IS_YES.equals(partStatus.trim()))
//           {
//               //查詢停用的配件
//               sb.append(" AND A.PART_STATUS=" + partStatus + " ");
//           }else
//           //if (DictDataConstant.DICT_IS_NO.equals(partStatus.trim()))
//           {
//               //查沒有停用的
//               sb.append(" AND A.PART_STATUS<>" + CommonConstants.DICT_IS_YES + " ");
//           }
//       }
       if (isStopIsZero != null && !"".equals(isStopIsZero.trim()))
       {
           if (isStopIsZero.equals(CommonConstants.DICT_IS_YES))
           {
               //主数据停用本地库存为零的
               sb.append(" AND (B.PART_STATUS=" + CommonConstants.DICT_IS_YES + "   AND  (A.STOCK_QUANTITY=0 OR A.STOCK_QUANTITY is null )) ");
           }
       }
     //add by dyz 2010.10.15 增加条件是否获取所有仓库配件
//       if (!Utility.testString(allPart) || allPart.equals(CommonConstants.DICT_IS_NO))
//       {
//           String[] stoC = Utility.getStorageByUserId(FrameworkUtil.getLoginInfo().getUserId()).split(",");//根据用户ID查询出他能操作的仓库 USERID FUNCIONCODE TABNAME
//           sb.append(" AND ( 1=2 ");
//           for (int i = 0; i < stoC.length; i++)
//           {
//               if (stoC[i] != null && !"".equals(stoC[i].trim()))
//               {
//                   sb.append(" OR A.STORAGE_CODE=" + stoC[i] + "  ");
//               }
//           }       
//           sb.append(" ) ");
//       }
       if (partMainType != null && !"".equals(partMainType.trim()))
       {
           sb.append(" AND A.PART_MAIN_TYPE=" + partMainType + " ");
       }
//        List<Object> params = new ArrayList<Object>();
//        if(!StringUtils.isNullOrEmpty(allocateInNo)){
//            sb.append(" and  A.Allocate_IN_No=? ");
//            params.add(allocateInNo);
//        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), null);
        return pageInfoDto;
        
    }

//    /**
//     * 
//    * @author yujiangheng
//    * @date 2017年4月27日
//    * @param queryParam
//    * @return
//    * @throws ServiceBizException
//    * (non-Javadoc)
//    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#queryStoragePartForAddC(java.util.Map)
//     */
//    @Override
//    public PageInfoDto queryStoragePartForAddC(Map<String, String> queryParam) throws ServiceBizException {
//        
//        
//        return null;
//    }
    /**
     * 增加时查询子表格里的内容
    * @author yujiangheng 
    * @date 2017年4月26日
    * @param queryParam
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#findAllPartInfoC(java.util.Map)
     */
    @Override
    public PageInfoDto findAllPartInfoC(Map<String, String> queryParam) {
        StringBuffer sb = new StringBuffer();
        sb.append(" SELECT '' as POS_CODE,'' as POS_NAME,A.PART_MODEL_GROUP_CODE_SET as '舍弃的', 0 ")
                .append(" as PART_QUANTITY, E.PART_BATCH_NO, A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, ")
                .append(" A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, A.PART_GROUP_CODE, ")
                .append(" B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG, ")
                .append(" A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.LAST_STOCK_IN, A.PART_STATUS, ")
                .append(" A.LAST_STOCK_OUT, A.REMARK,   A.MIN_STOCK, ")
                .append(" B.OPTION_NO, B.PART_MODEL_GROUP_CODE_SET, A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE, B.URGENT_PRICE, ")
                .append(" B.INSTRUCT_PRICE,A.INSURANCE_PRICE, D.OPTION_STOCK, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,B.IS_BACK,B.PART_INFIX, ")
                .append(" B.MIN_LIMIT_PRICE ").append(" FROM TM_PART_STOCK A  LEFT OUTER JOIN (")
                .append(CommonConstants.VM_PART_INFO).append(") B")
                .append(" ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO ) ")
                .append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D ")
                .append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO )")
                .append(" LEFT  JOIN TM_PART_STOCK_ITEM E ON ( A.DEALER_CODE = E.DEALER_CODE AND A.PART_NO= E.PART_NO AND A.STORAGE_CODE = E.STORAGE_CODE ) ")
                .append(" WHERE A.DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode())
                .append("' AND A.D_KEY = ").append(CommonConstants.D_KEY);
        Utility.sqlToEquals(sb, queryParam.get("PART_NO"), "PART_NO", "A");
        Utility.sqlToEquals(sb, queryParam.get("STORAGE_CODE"), "STORAGE_CODE", "A");
        return DAOUtil.pageQuery(sb.toString(), null);
    }

    /**
     * 查询符合条件的客户
    * @author yujiangheng
    * @date 2017年4月26日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#QueryPartCustomer(java.util.Map)
     */
    @Override
    public PageInfoDto QueryPartCustomer(Map<String, String> queryParam) throws ServiceBizException {
        //请求参数
        StringBuffer sql= new StringBuffer("select A.IS_VALID,A.BAL_OBJ_CODE,A.BAL_OBJ_NAME,A.ACCOUNT_AGE,A.ARREARAGE_AMOUNT,A.CUS_RECEIVE_SORT,A.CUSTOMER_CODE,");
        sql.append(" A.DEALER_CODE, A.CUSTOMER_TYPE_CODE, A.CUSTOMER_NAME, A.PRE_PAY, A.CUSTOMER_SPELL, A.CUSTOMER_SHORT_NAME, ");
        sql.append("A.ADDRESS, A.ZIP_CODE, A.CONTACTOR_NAME,  A.CONTACTOR_PHONE, A.CONTACTOR_MOBILE, A.FAX, A.CONTRACT_NO,");
        sql.append("A.AGREEMENT_BEGIN_DATE,  A.AGREEMENT_END_DATE, A.PRICE_ADD_RATE, A.PRICE_RATE, A.SALES_BASE_PRICE_TYPE,");
        sql.append(" A.LEAD_TIME, A.CREDIT_AMOUNT, A.TOTAL_ARREARAGE_AMOUNT, A.ACCOUNT_TERM, A.TOTAL_ARREARAGE_TERM,");
        sql.append(" A.OEM_TAG,A.VER,  ");
        sql.append(" B.DEALER_SHORT_NAME as DEALER_NAME,D.VIN  ");
        sql.append("  from("+CommonConstants.VM_PART_CUSTOMER+")  A   ");
        sql.append("  left join TM_PART_CUSTOMER C on A.DEALER_CODE=C.DEALER_CODE and A.CUSTOMER_CODE=C.CUSTOMER_CODE   ");
        sql.append("  left join  TM_PART_CUSTOMER_DEALER B on C.DEALER_CODE=B.DEALER_CODE and C.DEALER_CODE=B.DEALER_CODE    ");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_OWNER+")  E ON A.DEALER_CODE=E.DEALER_CODE AND A.CUSTOMER_CODE=E.CUSTOMER_CODE    ");
        sql.append(" LEFT JOIN ("+CommonConstants.VM_VEHICLE+")   D ON E.DEALER_CODE=D.DEALER_CODE AND E.OWNER_NO=D.OWNER_NO    ");
        sql.append(" WHERE  A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"'   ");
//     Utility.getLikeCond("A", "CUSTOMER_CODE", queryParam.get("CUSTOMER_CODE"), "AND");
//      Utility.getLikeCond("A", "CUSTOMER_SPELL", queryParam.get("CUSTOMER_SPELL"), "AND");
//      Utility.getLikeCond("A", "CUSTOMER_NAME", queryParam.get("CUSTOMER_NAME"), "AND");
      List<Object> params = new ArrayList<Object>();
      if(!StringUtils.isNullOrEmpty(queryParam.get("customerCode"))){
          sql.append(" and A.CUSTOMER_CODE like ? ");
          params.add("%"+queryParam.get("customerCode")+"%");
      }
      if(!StringUtils.isNullOrEmpty(queryParam.get("customerName"))){
          sql.append(" and A.CUSTOMER_NAME like ? ");
          params.add("%"+queryParam.get("customerName")+"%");
      }
      if(!StringUtils.isNullOrEmpty(queryParam.get("oemTag"))){
          sql.append(" and A.OEM_TAG like ? ");
          params.add("%"+queryParam.get("oemTag")+"%");
      }
        if(!StringUtils.isNullOrEmpty(queryParam.get("CUSTOMER_TYPE_CODE"))){
            sql.append(" and  A.CUSTOMER_TYPE_CODE=? ");
            params.add(queryParam.get("CUSTOMER_TYPE_CODE"));
        }
        String isNetHarm = queryParam.get("IS_NET_HARM");//FDICT_REPLACEINSERT_TYPE  fdict_replaceinsert_type:10561002
        if(isNetHarm != null && isNetHarm.equals("12781001")){
            sql.append( " and A.IS_VALID = '12781001'   ");
        }
        String isOemTag = queryParam.get("IS_OEM_TAG");
        if(isOemTag != null && isOemTag.equals("是")){
            sql.append( " and A.OEM_TAG = '12781001'   ");
        }else if(isOemTag != null && isOemTag.equals("否")){
            sql.append( " and A.OEM_TAG = '12781002'   ");
        }
        sql.append("   ORDER BY A.CUSTOMER_CODE  ");
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), params);
        return pageInfoDto;
    }
    /**
     * 保存调拨入库单
    * @author yujiangheng
    * @date 2017年4月27日
    * @param listPartAllocateInItemDto
    * @throws ServiceBizException
    * (non-Javadoc)
     * @throws ParseException 
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#savePartAllocateIn(com.yonyou.dms.part.domains.DTO.stockmanage.ListPartAllocateInItemDto)
     */
    @Override
    public String savePartAllocateIn(ListPartAllocateInItemDto listPartAllocateInItemDto) throws ServiceBizException{
        System.out.println(listPartAllocateInItemDto.toString());
        //设置主表的对象属性
        PartAllocateInDto partAllocateInDTO=new PartAllocateInDto();
        setPartAllocateInDto(partAllocateInDTO,listPartAllocateInItemDto);
        System.out.println(partAllocateInDTO.getStockInDate());//
        String allocateNo= listPartAllocateInItemDto.getAllocateInNo();
        String isIdleAllocation=listPartAllocateInItemDto.getIsIdleAllocation();
        String isNetTransfer=listPartAllocateInItemDto.getIsNetTransfer();
        System.out.println(listPartAllocateInItemDto.getAllocateInNo());
        //判断有没有调拨单号
        if(listPartAllocateInItemDto.getAllocateInNo()!=null){//有调拨单号
            //判断删除是否为空
            if(!"".equals(listPartAllocateInItemDto.getItems())&&listPartAllocateInItemDto.getItems()!=null){
                //将删除的元素删除
                String items =listPartAllocateInItemDto.getItems();
                 String items1=items.substring(items.indexOf(",")+1);
                String[] split = items1.split(",");
                System.out.println(split);
                for (String item : split){
                    if(!"N".equals(item)){
                        PartAllocateInItemPO partAllocateInItemPO=PartAllocateInItemPO.findById(Long.parseLong(item));
                        Boolean result= partAllocateInItemPO.delete();
                        System.out.println("____________________"+result);
                    }
                }
            }
          //更新主表
            PartAllocateInPO partAllocateInPO=
                    PartAllocateInPO.findByCompositeKeys(allocateNo,FrameworkUtil.getLoginInfo().getDealerCode());
            partAllocateInPO.saveIt();
        }else{
            System.out.println("//新建调拨单号  //主表新增--->子表新增");
            PartAllocateInPO partAllocateInPO=new PartAllocateInPO();
            setPartAllocateInPO(partAllocateInPO,partAllocateInDTO);
            allocateNo=partAllocateInDTO.getAllocateInNo();
            partAllocateInPO.saveIt();
        }
        System.out.println(allocateNo);
        for(PartAllocateInItemDto partAllocateInItemDto:listPartAllocateInItemDto.getDms_part_allocate_in()){
            String storageCode=partAllocateInItemDto.getStorageCode();
            System.out.println("storageCode:"+storageCode);
            String partNo=partAllocateInItemDto.getPartNo();
            System.out.println("partNo:"+partNo);
            Double inAmount=partAllocateInItemDto.getInAmount();
            System.out.println("inAmount:"+inAmount);
            Double inPrice=partAllocateInItemDto.getInPrice();
            System.out.println("inPrice:"+inPrice);
            String unitCode=partAllocateInItemDto.getUnitCode();
            System.out.println("unitCode:"+unitCode);
            //校验传入的数据格式是否正确（非呆滞调拨入库的配件不能入A类仓库/调拨入库不允许负入库）
            if(isIdleAllocation=="12781002"&&storageCode.indexOf("A",1)==-1){//不是呆滞调拨，仓库为A类仓库
                throw new ServiceBizException("非呆滞调拨入库的配件不能入A类仓库！");
            }
                   if(!"N".equals(partAllocateInItemDto.getItemId())&&"X".equals(partAllocateInItemDto.getFlag())){
                        System.out.println("----------------------------"+partAllocateInItemDto.getItemId());
                        System.out.println("自采件调拨（修改--保存）");
                        System.out.println(partAllocateInItemDto.toString());
                        PartAllocateInItemPO partAllocateInItemPO=
                                PartAllocateInItemPO.findById(Long.parseLong(partAllocateInItemDto.getItemId()));
                        partAllocateInItemPO.setString("ALLOCATE_IN_NO", allocateNo);
                        partAllocateInItemPO.setString("PART_NAME",partAllocateInItemDto.getPartName());
                        partAllocateInItemPO.setString("STORAGE_CODE",storageCode);
                        partAllocateInItemPO.setString("STORAGE_POSITION_CODE", partAllocateInItemDto.getStoragePositionCode());
                        partAllocateInItemPO.setDouble("IN_PRICE",inPrice);
                        partAllocateInItemPO.setString("PART_BATCH_NO",partAllocateInItemDto.getPartBatchNo());
                        partAllocateInItemPO.setDouble("IN_QUANTITY", partAllocateInItemDto.getInQuantity());
                        partAllocateInItemPO.setDouble("IN_AMOUNT",inAmount);
                        partAllocateInItemPO.saveIt();
                    }else if("N".equals(partAllocateInItemDto.getItemId())){
                        System.out.println("自采件调拨（新增--保存）/网内调拨（新增--保存）");
                        System.out.println(partAllocateInItemDto.toString());
                        PartAllocateInItemPO partAllocateInItemPO=new PartAllocateInItemPO();
                        partAllocateInItemPO.setString("ITEM_ID", null);
                        partAllocateInItemPO.setString("ALLOCATE_IN_NO", allocateNo);
                        partAllocateInItemPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
                        partAllocateInItemPO.setString("STORAGE_CODE",storageCode);
                        partAllocateInItemPO.setString("STORAGE_POSITION_CODE", partAllocateInItemDto.getStoragePositionCode());
                        partAllocateInItemPO.setString("PART_NO",partAllocateInItemDto.getPartNo());
                        partAllocateInItemPO.setString("PART_NAME",partAllocateInItemDto.getPartName());
                        partAllocateInItemPO.setString("PART_BATCH_NO",partAllocateInItemDto.getPartBatchNo());
                        partAllocateInItemPO.setString("UNIT_CODE",partAllocateInItemDto.getUnitCode());
                        partAllocateInItemPO.setDouble("IN_PRICE", inPrice);
                        partAllocateInItemPO.setDouble("IN_QUANTITY", partAllocateInItemDto.getInQuantity());
                        partAllocateInItemPO.set("IN_AMOUNT", inAmount);
                        partAllocateInItemPO.saveIt();
                    }
        }
        return allocateNo;
    }
  
    
    /*   
    //1.新建网内调拨（新增--保存）
//    if(isNetTransfer.equals("12781001")&&isIdleAllocation.equals("12781002")){
//        //通过上端下发的网内调拨配件需求，给其配置仓库，保存时生成调拨单
//        System.out.println("网内调拨（新增--保存）");
//    }else if(isNetTransfer.equals("12781002")&&isIdleAllocation.equals("12781002")){
//    }
    //新建配件调拨明细条目   //2.新建自采件调拨（新增--保存）
    PartAllocateInItemPO partAllocateInItemPO=new PartAllocateInItemPO(); 
    partAllocateInItemPO.setString("ALLOCATE_IN_NO", partAllocateInDTO.getAllocateInNo());
    partAllocateInItemPO.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
    partAllocateInItemPO.setString("STORAGE_CODE",storageCode);
    partAllocateInItemPO.setString("STORAGE_POSITION_CODE", partAllocateInItemDto.getStoragePositionCode());
    partAllocateInItemPO.setString("PART_NO",partAllocateInItemDto.getPartNo());
    partAllocateInItemPO.setString("PART_NAME",partAllocateInItemDto.getPartName());
    partAllocateInItemPO.setString("UNIT_CODE",partAllocateInItemDto.getUnitCode());
    partAllocateInItemPO.setDouble("IN_PRICE", inPrice);
    partAllocateInItemPO.setDouble("IN_QUANTITY", partAllocateInItemDto.getInQuantity());
    partAllocateInItemPO.set("IN_AMOUNT", inAmount);
    partAllocateInItemPO.saveIt();*/
    /* //呆滞调拨 修改--保存//网内调拨 修改--保存
    if(listPartAllocateInItemDto.getIsIdleAllocation().equals("12781001")|| 
            listPartAllocateInItemDto.getIsNetTransfer().equals("12781001")){
           
        System.out.println("呆滞调拨（修改--保存）||网内调拨（修改--保存）");
      
        检验该配件是否在该仓库中
       *  StringBuffer sb=new StringBuffer("select ITEM_ID,  from tt_part_Allocate_in_item  ");
        sb.append("WHERE Allocate_IN_No='"+listPartAllocateInItemDto.getAllocateInNo()+"' ");
        sb.append("AND STORAGE_CODE='"+partAllocateInItemDto.getStorageCode()+"'");
        //更新
        PartAllocateInItemPO partAllocateInItemPO=PartAllocateInItemPO.findById(partAllocateInItemDto.getItemId());
        partAllocateInItemPO.setString("ALLOCATE_IN_NO", allocateNo);
        partAllocateInItemPO.setString("STORAGE_CODE",storageCode);
        partAllocateInItemPO.setString("STORAGE_POSITION_CODE", partAllocateInItemDto.getStoragePositionCode());
        partAllocateInItemPO.setDouble("IN_PRICE", inPrice);
        partAllocateInItemPO.set("IN_AMOUNT", inAmount);
        partAllocateInItemPO.saveIt();
    }else { //自采件调拨（新增--保存/修改--保存）/网内调拨（新增--保存）
       }
*/  
    /**
     * 设置对象属性1
    * TODO description
    * @author yujiangheng
    * @date 2017年4月28日
    * @param partAllocateInPO
    * @param partAllocateInDTO
     * @throws ParseException 
     */
    private void setPartAllocateInPO(PartAllocateInPO partAllocateInPO, PartAllocateInDto partAllocateInDTO){
        partAllocateInPO.setString("DEALER_CODE",partAllocateInDTO.getDealerCode());
        partAllocateInPO.setString("ALLOCATE_IN_NO",partAllocateInDTO.getAllocateInNo());
        partAllocateInPO.setString("CUSTOMER_CODE",partAllocateInDTO.getCustomerCode());
        partAllocateInPO.setString("CUSTOMER_NAME",partAllocateInDTO.getCustomerName());
        partAllocateInPO.setString("STOCK_IN_DATE",partAllocateInDTO.getStockInDate());
        partAllocateInPO.setString("STOCK_IN_VOUCHER", partAllocateInDTO.getStockInVoucher());
        partAllocateInPO.setString("HANDLER",partAllocateInDTO.getHandler());
        partAllocateInPO.setString("IS_IDLE_ALLOCATION",partAllocateInDTO.getIsIdleAllocation());
        partAllocateInPO.setString("IS_NET_TRANSFER",partAllocateInDTO.getIsNetTransfer());
        partAllocateInPO.setString("REMARK",partAllocateInDTO.getRemark());
        partAllocateInPO.setInteger("IS_FINISHED",partAllocateInDTO.getIsFinished());
    }
    /**
     * 设置调拨入库单号表字段属性
    * TODO description
    * @author yujiangheng
    * @date 2017年4月28日
    * @param partAllocateInDTO
    * @param listPartAllocateInItemDto
     * @throws ParseException 
     */
    private void setPartAllocateInDto(PartAllocateInDto partAllocateInDTO, ListPartAllocateInItemDto listPartAllocateInItemDto)  {
        partAllocateInDTO.setDealerCode(FrameworkUtil.getLoginInfo().getDealerCode());
        partAllocateInDTO.setCustomerCode(listPartAllocateInItemDto.getCustomerCode());
        if(listPartAllocateInItemDto.getAllocateInNo()==null){
            CommonNoService CommonNo=new CommonNoServiceImpl();
            String SystemOrderNo=CommonNo.getSystemOrderNo(CommonConstants.PART_ALLOCATE_IN_PREFIX);
            partAllocateInDTO.setAllocateInNo(SystemOrderNo);
        }else{
            partAllocateInDTO.setAllocateInNo(listPartAllocateInItemDto.getAllocateInNo());
        }
        partAllocateInDTO.setCustomerName(listPartAllocateInItemDto.getCustomerName());
        partAllocateInDTO.setStockInDate(listPartAllocateInItemDto.getStockInDate());
        
        partAllocateInDTO.setStockInVoucher(listPartAllocateInItemDto.getStockInVoucher());
        partAllocateInDTO.setHandler(FrameworkUtil.getLoginInfo().getEmployeeNo());
        partAllocateInDTO.setIsIdleAllocation(listPartAllocateInItemDto.getIsIdleAllocation());
        partAllocateInDTO.setIsNetTransfer(listPartAllocateInItemDto.getIsNetTransfer());
        partAllocateInDTO.setRemark(listPartAllocateInItemDto.getRemark());
        partAllocateInDTO.setIsFinished(12781002);        
    }
    /**
     * 查询配件仓库库存是否存在
    * @author yujiangheng
    * @date 2017年4月27日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#queryStoragePartOne(java.util.Map)
     */
    @Override
    public Map<String,Object> queryStoragePartOne(Map<String, String> queryParam) throws ServiceBizException {
        //先查询某仓库中是否有该配件，
        StringBuilder sb1= new StringBuilder("SELECT  DEALER_CODE,PART_NO,STOCK_QUANTITY,STORAGE_POSITION_CODE  FROM  TM_PART_STOCK  ");
          sb1.append("WHERE   STORAGE_CODE='"+queryParam.get("STORAGE_CODE")+"'  AND PART_NO='"+queryParam.get("PART_NO")+"'");
          sb1.append("AND DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
        Map<String,Object>  AllocateInNo=DAOUtil.findFirst(sb1.toString(), null);
        return AllocateInNo;
    }
    /**
     *根据仓库代码查询库位
    * @author yujiangheng
    * @date 2017年4月28日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#queryStorageAndPart(java.util.Map)
     */
    @Override
    public Map<String,Object> queryStoragePosition(Map<String, String> queryParam) throws ServiceBizException {
        //先查询某仓库中是否有该配件，
        StringBuilder sb1= new StringBuilder("SELECT  STORAGE_CODE,STOCK_QUANTITY,STORAGE_POSITION_CODE  FROM  TM_PART_STOCK  ");
          sb1.append("WHERE   ADN STORAGE_CODE='"+queryParam.get("STORAGE_CODE")+"'");
          Map<String,Object>  StorageAndPart=DAOUtil.findFirst(sb1.toString(), null);
        return StorageAndPart;
    }
    /**
     * 查询网络调拨单
    * @author yujiangheng
    * @date 2017年5月2日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#queryAllocateOutNetItem(java.util.Map)
     */
    @Override
    public PageInfoDto queryAllocateOutNet(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer(" SELECT DEALER_CODE,ALLOCATE_OUT_NO,TO_CUSTOMER_CODE,TO_CUSTOMER_NAME,FINISHED_DATE,");
        sb.append(" OUT_AMOUNT,HANDLER,STATUS FROM TT_PART_ALLOCATE_OUT_NET WHERE STATUS=14991001");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("allocateOutNo"))){
            sb.append(" and  ALLOCATE_OUT_NO LIKE ? ");
            params.add("%"+queryParam.get("allocateOutNo")+"%");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), params);
      return pageInfoDto;
    }
    /**
     * 查询网络调拨单明细
    * @author yujiangheng
    * @date 2017年5月2日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#queryAllocateOutNetItem(java.util.Map)
     */
    @Override
    public PageInfoDto queryAllocateOutNetItem(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sb = new StringBuffer(" SELECT A.ALLOCATE_OUT_NO,A.FROM_CUSTOMER_CODE,A.TO_CUSTOMER_NAME,A.TO_CUSTOMER_CODE,A.FROM_CUSTOMER_NAME,");
        sb.append(" A.DEALER_CODE,A.STATUS,B.STORAGE_CODE,B.STORAGE_POSITION_CODE,B.PART_BATCH_NO,B.PART_NO, ");
        sb.append("A.FINISHED_DATE,A.HANDLER,B.PART_NAME,B.UNIT_CODE,B.OUT_QUANTITY IN_QUANTITY,B.OUT_PRICE IN_PRICE,B.COST_PRICE,B.COST_AMOUNT,B.OUT_AMOUNT IN_AMOUNT  ");
        sb.append(" FROM TT_PART_ALLOCATE_OUT_NET A ");
        sb.append(" LEFT JOIN TT_PART_ALLOCATE_OUT_DETAIL_NET B ON (A.ALLOCATE_OUT_NO = B.ALLOCATE_OUT_NO AND A.DEALER_CODE = B.DEALER_CODE) ");
        sb.append(" WHERE A.DEALER_CODE = '"+FrameworkUtil.getLoginInfo().getDealerCode()+"'  ");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("netAllocateNo"))){
            sb.append(" and  A.ALLOCATE_OUT_NO = ? ");
            params.add(queryParam.get("netAllocateNo"));
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), params);
      return pageInfoDto;
    }
    
    /**
     * 分厂调拨单明细
    * @author yujiangheng
    * @date 2017年5月3日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#QueryBranchFactoryAllocateItem(java.util.Map)
     */
//    @Override
//    public List<Map> QueryBranchFactoryAllocateItem(Map<String, String> queryParam) throws ServiceBizException {
//        String allocateNo = queryParam.get("ALLOCATE_OUT_NO");
//        String fromEntity=queryParam.get("FROM_ENTITY");
//        String rate=queryParam.get("RATE");
//        //校验是否加锁，如果加锁，则锁定单号
//        String lockName=Utility.
//                selLockerName("LOCK_USER", "tt_part_allocate_out_source", "ALLOCATE_OUT_NO", queryParam.get("ALLOCATE_OUT_NO"));
//        if(lockName.length()!=0){
//            //用户锁定
//           int lockFlag= Utility.updateByLocker("tt_part_allocate_out_source", FrameworkUtil.getLoginInfo().getUserId().toString(),
//                                                "ALLOCATE_OUT_NO",  queryParam.get("ALLOCATE_OUT_NO"),"LOCK_USER" );
//           if(lockFlag==1){
//               throw  new ServiceBizException("该单号已被"+lockName+"锁定！");
//           }
//        }
//        /*
//         * 业务描述：查询分工厂调拨业务，根据调拨单号查询明细
//         *1.a工厂掉给b,如果b工厂没有该配件信息，则给前台返回提示信息.
//         * 2.b工厂有该配件，则在有该配件的仓库，任意选一仓库，做调拨入库
//         * SELECT DEALER_CODE,ALLOCATE_OUT_NO,FROM_ENTITY,STORAGE_CODE,STORAGE_POSITION_CODE,PART_BATCH_NO,PART_NO,
//PART_NAME,UNIT_CODE,OUT_QUANTITY,COST_PRICE,COST_AMOUNT,OUT_PRICE,OUT_AMOUNT FROM tt_part_alloc_out_i_srce 
//WHERE  FROM_ENTITY='2100000 ' AND ALLOCATE_OUT_NO='21212212'
//         */
//        LinkedList<PartAllocateInItemDto> linkedlist=new LinkedList<PartAllocateInItemDto>();//正常
//        LinkedList<PartAllocateInItemDto> linkedlistP=new LinkedList<PartAllocateInItemDto>();//库存,基本信息中没有的
//        StringBuilder sb=new StringBuilder(" SELECT *  FROM tt_part_alloc_out_i_srce ");
//        sb.append(" where  FROM_ENTITY='"+fromEntity+"'");
//        sb.append( "AND ALLOCATE_OUT_NO='"+allocateNo+"'");
//        List<Map>  list= DAOUtil.findAll(sb.toString(), null); 
//        if (list != null && list.size() > 0) {
//            for (int i = 0; i < list.size(); i++)  {//循环明细表里面的内容
//                TtPartAllocOutISrceDTO  ttPartAllocOutISrceDTO = new TtPartAllocOutISrceDTO();
//                //设置对象属性
//                setTtPartAllocOutISrceDTO(ttPartAllocOutISrceDTO,list.get(i));
//               // System.out.println("________________"+ttPartAllocOutISrceDTO.toString());
//              //在配件基本信息里查询，分工厂是否有该配件
//                StringBuilder sb1=new StringBuilder(" SELECT *  FROM TM_PART_INFO ");
//                sb1.append( "WHERE PART_NO='"+ttPartAllocOutISrceDTO.getPartNo()+"'");
//                List<Map>  partInfo= DAOUtil.findAll(sb1.toString(), null);
//                System.out.println(partInfo);
//                if (partInfo != null && partInfo.size() > 0){
//                    //在配件库存中查询，该配件在分工厂哪个仓库，多个则任意选一个仓库做调拨入库
//                    StringBuilder sb2=new StringBuilder(" SELECT *  FROM TM_PART_STOCK ");
//                    sb2.append( "WHERE PART_NO='"+ttPartAllocOutISrceDTO.getPartNo()+"'");
//                    List<Map>  partStock= DAOUtil.findAll(sb2.toString(), null);
//                    System.out.println(partStock);
//                       if(partStock!=null && partStock.size()>0) {
//                           // 判断查询的是否存在 OEM仓库 有则返回 位置 k
//                           int m = 0;
//                           for (int k = 0;k<partStock.size();k++){
//                               Map  map=partStock.get(k);
//                               if (map.get("STORAGE_CODE") != null && !"".equals(map.get("STORAGE_CODE")) && 
//                                       map.get("STORAGE_CODE").equals("OEMK") ){
//                                   m = k;
//                                   break;
//                               }
//                           }
//                           String storageCode=(String)partStock.get(m).get("STORAGE_CODE");
//                           String storagePositionCode=(String)partStock.get(m).get("STORAGE_POSITION_CODE");
//                           PartAllocateInItemDto partAllocateInItemDto=new PartAllocateInItemDto();
//                           setPartAllocateInItemDto(partAllocateInItemDto,ttPartAllocOutISrceDTO);
//                           partAllocateInItemDto.setStorageCode(storageCode);
//                           partAllocateInItemDto.setStoragePositionCode(storagePositionCode);
//                           partAllocateInItemDto.setInPrice(Double.parseDouble(ttPartAllocOutISrceDTO.getOutPrice())/(1+Utility.getDouble(rate)));
//                           partAllocateInItemDto.setInAmount(partAllocateInItemDto.getInPrice()*partAllocateInItemDto.getInQuantity());
//                           partAllocateInItemDto.setDownTag(Integer.parseInt(partInfo.get(0).get("DOWN_TAG")+""));
//                           /*
//                           inItemPO.setInPrice(sourcePO.getOutPrice()/(1+Utility.getDouble(rate)));
//                           inItemPO.setInAmount(inItemPO.getInPrice()*inItemPO.getInQuantity());
//                           inItemPO.setDownTag(infoPO.getDownTag());
//                            */
//                           List listnormal=new ArrayList();
//                           listnormal.add(partAllocateInItemDto);
//                           linkedlist.addAll(listnormal);
//                         //  return linkedlist;
//                       }else{
//                         //分工厂配件库存中没有该配件,返回给前端
//                           StringBuffer sb3 = new StringBuffer("SELECT A.OUT_PRICE/(1+"+rate+") AS IN_PRICE,");
//                           sb3.append( "(A.OUT_PRICE/(1+"+rate+")*A.OUT_QUANTITY) AS IN_AMOUNT,A.OUT_QUANTITY AS IN_QUANTITY,");
//                           sb3.append("A.OUT_PRICE AS IN_PRICE,A.OUT_AMOUNT AS IN_AMOUNT,A.ALLOCATE_OUT_NO AS ALLOCATE_IN_NO,A.*, ");
//                           sb3.append( "12781001 AS SHORT_PART,12781002 AS SHORT_INFO FROM TT_PART_ALLOC_OUT_I_SRCE  A");
//                           sb3.append(" WHERE A.DEALER_CODE='");
//                           List<Map>  b3= DAOUtil.findAll(sb3.toString(), null);
////                           for(Map list:b3 ){
////                               
////                           }
//                           
//                         //  liststock=factory.queryBranchAllocateNotStock(conn, entityCode, allocatNo, sourcePO.getPartNo(),Utility.getDouble(rate),fromEntity);
//                        //   linkedlistP.addAll(liststock);
//                           
//                       }
//                    }else{
//                    }
//                }
//        }
//        
//         return null;
//    }
    /**
     * 设置PartAllocateInItemDto 对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年5月5日
    * @param ttPartAllocOutISrceDTO
     */
    private void setPartAllocateInItemDto(PartAllocateInItemDto partAllocateInItemDto,
                                          TtPartAllocOutISrceDTO ttPartAllocOutISrceDTO) {
        partAllocateInItemDto.setDealerCode(ttPartAllocOutISrceDTO.getDealerCode());
        partAllocateInItemDto.setAllocateInNo(ttPartAllocOutISrceDTO.getAllocateOutNo());
      //  partAllocateInItemDto.setItemId(ttPartAllocOutISrceDTO.getItemId());
        partAllocateInItemDto.setPartBatchNo(ttPartAllocOutISrceDTO.getPartBatchNo());
        partAllocateInItemDto.setPartName(ttPartAllocOutISrceDTO.getPartName());
        partAllocateInItemDto.setPartNo(ttPartAllocOutISrceDTO.getPartNo());
        partAllocateInItemDto.setUnitCode(ttPartAllocOutISrceDTO.getUnitCode());
        partAllocateInItemDto.setInQuantity(Float.parseFloat(ttPartAllocOutISrceDTO.getOutQuantity()));
        partAllocateInItemDto.setCostPrice(Double.parseDouble(ttPartAllocOutISrceDTO.getCostPrice()));
        partAllocateInItemDto.setCostAmount(Double.parseDouble(ttPartAllocOutISrceDTO.getCostAmount()));
        partAllocateInItemDto.setInPrice(Double.parseDouble(ttPartAllocOutISrceDTO.getOutPrice()));
        partAllocateInItemDto.setInAmount(Double.parseDouble(ttPartAllocOutISrceDTO.getOutAmount()));
        partAllocateInItemDto.setInAmount(Double.parseDouble(ttPartAllocOutISrceDTO.getOutAmount()));
    }
/**
   * 设置TtPartAllocOutISrceDTO 对象属性
  * TODO description
  * @author yujiangheng
  * @date 2017年5月5日
  * @param ttPartAllocOutISrceDTO
  * @param tempMap
   */
    private void setTtPartAllocOutISrceDTO(TtPartAllocOutISrceDTO ttPartAllocOutISrceDTO, Map tempMap) {
        ttPartAllocOutISrceDTO.setDealerCode((String)tempMap.get("DEALER_CODE"));
        ttPartAllocOutISrceDTO.setAllocateOutNo((String)tempMap.get("ALLOCATE_OUT_NO"));
       // ttPartAllocOutISrceDTO.setItemId((String)tempMap.get("ITEM_ID"));
        ttPartAllocOutISrceDTO.setDealerCode((String)tempMap.get("DEALER_CODE"));
        ttPartAllocOutISrceDTO.setFromEntity((String)tempMap.get("FROM_ENTITY"));
        ttPartAllocOutISrceDTO.setStorageCode((String)tempMap.get("STORAGE_CODE"));
        ttPartAllocOutISrceDTO.setStoragePositionCode((String)tempMap.get("STORAGE_POSITION_CODE"));
        ttPartAllocOutISrceDTO.setPartBatchNo((String)tempMap.get("PART_BATCH_NO"));
        ttPartAllocOutISrceDTO.setPartNo((String)tempMap.get("PART_NO"));
        ttPartAllocOutISrceDTO.setPartName((String)tempMap.get("PART_NAME"));
        ttPartAllocOutISrceDTO.setUnitCode((String)tempMap.get("UNIT_CODE"));
       ttPartAllocOutISrceDTO.setOutQuantity(tempMap.get("OUT_QUANTITY")+"");
        ttPartAllocOutISrceDTO.setCostPrice(tempMap.get("COST_PRICE")+"");
        ttPartAllocOutISrceDTO.setCostAmount(tempMap.get("COST_AMOUNT")+"");
        ttPartAllocOutISrceDTO.setOutPrice(tempMap.get("OUT_PRICE")+"");
        ttPartAllocOutISrceDTO.setOutAmount(tempMap.get("OUT_AMOUNT")+"");
    }
    /**
     * 查询分厂调拨
    * @author yujiangheng
    * @date 2017年5月3日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#QueryBranchFactoryAllocate(java.util.Map)
     */
    @Override
    public PageInfoDto QueryBranchFactoryAllocate(Map<String, String> queryParam) throws ServiceBizException {
      //  业务描述：查询调拨临时表中的调拨数据，查询本分工厂entity_code未入账的单据(调拨主表)
        StringBuffer sb = new StringBuffer(" SELECT DEALER_CODE,ALLOCATE_OUT_NO,FROM_ENTITY,STOCK_OUT_DATE,IS_FINISHED,COST_AMOUNT,OUT_AMOUNT,");
        sb.append(" IS_PAYOFF,HANDLER,LOCK_USER,CREDENCE FROM tt_part_allocate_out_source WHERE IS_FINISHED=12781002");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("allocateOutNo"))){
            sb.append(" and  ALLOCATE_OUT_NO LIKE ? ");
            params.add("%"+queryParam.get("allocateOutNo")+"%");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(), params);
      return pageInfoDto;
    }
    /**
     * 作废功能：此功能操作的前提是调拨单已经存在
    * @author yujiangheng
    * @date 2017年5月4日
    * @param customerCode
    * @param allocateInNo
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#deleteAllocateIn(java.lang.String, java.lang.String)
     */
    @Override
    public void deleteAllocateIn(String customerCode, String allocateInNo) throws ServiceBizException {
        //不是分厂调拨单
        //删除调拨单的子表中关于某个调拨单的所有信息
       StringBuilder sb=new StringBuilder(" DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
       sb.append("AND ALLOCATE_IN_NO='"+allocateInNo+"'");
       PartAllocateInItemPO.delete(sb.toString(), null);//sql语句为where 后面的部分
        //删除一条调拨单信息
       PartAllocateInPO.delete(sb.toString(), null);
       
        // 如果是分工厂调拨的单子，则需要把临时调拨主单isfinished改为否12781002
       StringBuilder sb1=new StringBuilder(" DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
       sb1.append("AND ALLOCATE_IN_NO='"+allocateInNo+"'  AND CUSTOMER_CODE='"+customerCode+"'");
       TtPartAllocateOutSourcePO.delete(sb1.toString(), null);
       
    }
    /**
     * 调拨入库入账
    * @author yujiangheng
    * @date 2017年5月5日
    * @param allocateInNo
     * @throws Exception 
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#accountPartAllocateIn(java.lang.String)
     */
    @Override
    public void accountPartAllocateIn(Map<String,String>queryparams) throws Exception {
        //查询月周期是否完成
        QueryMonthPeriodIsFinished();
        //业务描述: 调拨入库入账
        AccountPartAllocateIn( queryparams);
        //调拨入库生成凭证
        performExecute(queryparams.get("ALLOCATE_IN_NO"));
    }
    /**
     * 调拨入库生成凭证
    * TODO description
    * @author yujiangheng
    * @date 2017年5月9日
    * @param allocateInNo
     * @throws Exception 
     */
    private void performExecute(String allocateInNo) throws Exception {
        String dealerCode =FrameworkUtil.getLoginInfo().getDealerCode();
        long userId = FrameworkUtil.getLoginInfo().getUserId();
        TmDefaultParaDTO  defaultpo = new TmDefaultParaDTO();
        defaultpo.setItemCode(CommonConstants.FINANCE_ITEM_CODE);
        defaultpo.setDealerCode(dealerCode);
       StringBuilder sb=new StringBuilder("select DEFAULT_VALUE,DEALER_CODE,ITEM_CODE,ITEM_DESC from Tm_Default_Para");
       sb.append(" where DEALER_CODE='"+defaultpo.getDealerCode()+"' AND ITEM_CODE='"+defaultpo.getItemCode()+"' ");
       Map<String,Object> defaultParam=DAOUtil.findFirst(sb.toString(), null);
       if(defaultParam!=null&& CommonConstants.DICT_IS_YES.equalsIgnoreCase(defaultParam.get("DEFAULT_VALUE")+"") ) {
            //获取开关设置
            TmDefaultParaDTO  dp = new TmDefaultParaDTO();
            dp.setItemCode(CommonConstants.REPAIR_CESS_ITEM_CODE);
            dp.setDealerCode(dealerCode);
            StringBuilder sb1=new StringBuilder("select DEFAULT_VALUE,DEALER_CODE,ITEM_CODE,ITEM_DESC from Tm_Default_Para");
            sb1.append(" where DEALER_CODE='"+defaultpo.getDealerCode()+"' AND ITEM_CODE='"+defaultpo.getItemCode()+"' ");
            Map<String,Object> DefaultParam=DAOUtil.findFirst(sb1.toString(), null);
            float  cess =  Float.parseFloat(DefaultParam.get("DEFAULT_VALUE")+"");
           PartAllocateInItemDto paiiic=new PartAllocateInItemDto();
            paiiic.setDealerCode(dealerCode);
            paiiic.setAllocateInNo(allocateInNo);
            StringBuilder sb2=new StringBuilder(" SELECT ITEM_ID,DEALER_CODE,ALLOCATE_IN_NO,STORAGE_CODE,");
            sb2.append("PART_BATCH_NO,PART_NO,PART_NAME,UNIT_CODE,IN_QUANTITY,COST_PRICE,COST_AMOUNT,IN_PRICE,IN_AMOUNT  FROM tt_part_allocate_in_item ");//TtPartAllocateInItem
            sb2.append( "WHERE DEALER_CODE='"+paiiic.getDealerCode()+"' AND ALLOCATE_IN_NO='"+paiiic.getAllocateInNo()+"' AND D_KEY='"+CommonConstants.D_KEY+"' ");
            List<Map>  list= DAOUtil.findAll(sb2.toString(), null);
            for(int i=0;i<list.size();i++) {
                Map paiii=list.get(i);
                TtAccountsTransFlowPO  po  = new  TtAccountsTransFlowPO();
                 po.set("TRANS_ID",null);
                 po.setString("ORG_CODE",dealerCode);
                 po.setString("DEALER_CODE",dealerCode);          
                 po.setDate("TRANS_DATE",Utility.getCurrentTimestamp());                    
                 po.setInteger("TRANS_TYPE",Utility.getInt(CommonConstants.DICT_BUSINESS_TYPE_PART_ALLOCATE_IN));
                 po.setDouble("TAX_AMOUNT",Double.parseDouble((paiii.get("IN_AMOUNT")+""))/(1F-cess));
                 po.setDouble("NET_AMOUNT",paiii.get("IN_AMOUNT"));
                 po.setString("BUSINESS_NO",paiii.get("ALLOCATE_IN_NO"));                  
                 po.setInteger("IS_VALID",Utility.getInt(CommonConstants.DICT_IS_YES));
                 po.setInteger("EXEC_NUM",0);
                 po.setInteger("EXEC_STATUS",Utility.getInt(CommonConstants.DICT_EXEC_STATUS_NOT_EXEC));//未生产
                 po.saveIt();
            }
        }
        
        
        
        
    }
    /**
     * 业务描述: 调拨入库入账
    * TODO description
    * @author yujiangheng
     * @throws Exception 
    * @date 2017年5月5日
     */
    private void AccountPartAllocateIn(Map<String,String>queryparams) throws Exception {
        String allocateInNo=queryparams.get("ALLOCATE_IN_NO");
        String finishedDate=queryparams.get("FINISHED_DATE");
        String isIdleAllocation=queryparams.get("IS_IDLE_ALLOCATION");
        //页面参数： ALLOCATE_IN_NO  FINISHED_DATE   IS_IDLE_ALLOCATION
        
        //声明变量1
        double costAmountBeforeA = 0;   //批次表入账前成本
        double costAmountBeforeB = 0;   //库存表入账前成本
        double costAmountAfterA = 0;    //批次表入账后成本
        double costAmountAfterB = 0;    //库存表入账后成本
        String customerCode = "";//add by xubufeng 2012-09-03
        // 后台参数：取得工厂编号，用户ID，经销商代码
        String dealerCode =FrameworkUtil.getLoginInfo().getDealerCode();
        String empNo = FrameworkUtil.getLoginInfo().getEmployeeNo();
        long userId = FrameworkUtil.getLoginInfo().getUserId();
        if (finishedDate==null || finishedDate=="" )  {//如果为空，则报错停止下面执行
            throw new ServiceBizException("入账提示:入帐日期不能为空 ！");
        }
       // logger.debug("*********入帐日期必须是当天及当天以前*********");
        //今天之后的日期,提示“不允许编写今天之后的”，彻底结束
        if ( Utility.TodayCompareFormatDate(finishedDate)<0 ) {
            throw new ServiceBizException("入账提示：入帐日期不能是今天之后的日期！");
        }
        if (allocateInNo == null || dealerCode == null || "".equals(allocateInNo.trim())  || "".equals(dealerCode.trim()))  {
            throw new ServiceBizException("丢失主键!");
        }
       // 功能描述：查询业务表 sheetTable 关联Tm_part_info 
        List<Map> listcheck=getNonOemPartList( dealerCode, "TT_PART_ALLOCATE_IN_ITEM","ALLOCATE_IN_NO", allocateInNo);
        if (listcheck != null && listcheck.size() > 0){
            logger.debug("list size :"+listcheck.size());
            String errPart = "";
            for (int i = 0;i<listcheck.size() ;i++ ){
               Map map=listcheck.get(i);
                if (errPart.equals("")){
                    errPart = map.get("PART_NO")+"";
                }else{
                    errPart = errPart + ", "+map.get("PART_NO")+"";
                }  
                logger.debug("errPart :"+errPart);
            }
            if (!errPart.equals("")){
                throw new ServiceBizException("保存错误:"+ errPart+" 非OEM配件不允许入OEM库,请重新操作!");
            }   
        }    
        
        /**
         * update wanghui 2010.02.21 reason
         * 相同账号分别调出同一张未入账的调拨入库单，点入账造成流水账中该入库单，有2条重复的流水账记录, 判断该入库单是否已经入账
         * flag==12781002没有入账；反之已经入账
         */
        String sheetNoType="ALLOCATE_IN_NO";
        String sheetType="TT_PART_ALLOCATE_IN";
        //校验配件是否已经入账 wanghui 
//        String flag=Utility.checkIsFinished(allocateInNo, dealerCode, sheetType, sheetNoType);
//        if (flag.equals(CommonConstants.DICT_IS_YES)) {
//          //  logger.debug("入库单号:" + allocateInNo + "已经入账，不能重复入账！");
////          String message = "调拨入库单号:" + allocateInNo + "已经入账，不能重复入账！";
//            throw new ServiceBizException("调拨入库单号:{"+allocateInNo+"}已经入账，不能重复入账！");
//        }
       // System.out.println("______________________________"+allocateInNo);
        if (allocateInNo != null && allocateInNo.length() > 0){
            
          //add by jll 2015-04-14   需要理解
//            actionContext.setStringValue("STOCK_IN_NO", allocateInNo);
//            actionContext.setStringValue("FLAG", "2");//调拨入库上报DCS
            
          Double totalCostAmount = 0D;    //成本总金额 add by xubufeng 2012-09-03 
          StringBuilder sb=new StringBuilder(" SELECT ITEM_ID,DEALER_CODE,ALLOCATE_IN_NO,STORAGE_CODE,");
          sb.append("PART_BATCH_NO,PART_NO,PART_NAME,UNIT_CODE,IN_QUANTITY,COST_PRICE,COST_AMOUNT,IN_PRICE,IN_AMOUNT  FROM tt_part_allocate_in_item ");//TtPartAllocateInItem
          sb.append( "WHERE DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND ALLOCATE_IN_NO='"+allocateInNo+"'");
          List<Map>  newlist= DAOUtil.findAll(sb.toString(), null);
          if (newlist != null && newlist.size() > 0 ) {
              for (int i = 0; i < newlist.size(); i++) {
                  //声明变量2
                  double costPriceStock = 0;//库存成本单价
                  double costAmountStock = 0;//库存成本金额
                  float stockQuantity = 0;//库存数量
                  float stockQuantityNew = 0;//入帐修改后的库存数量
                  double costPriceNew = 0;//入帐修改后的库存成本单价                  
                  double itemCostAmount = 0;//业务单据领料出库成本金额
                  double itemCostPrice = 0;//业务单句出库成本单价
                  costAmountBeforeA = 0;  //批次表入账前成本
                  costAmountBeforeB = 0;  //库存表入账前成本
                  costAmountAfterA = 0;   //批次表入账后成本
                  costAmountAfterB = 0;   //库存表入账后成本
                 Map newInitem= newlist.get(i);
                // System.out.println("__________________________________________"+ newInitem);
                // System.out.println("__________________________________________"+ Double.parseDouble( newInitem.get("COST_AMOUNT")+""));
                 totalCostAmount += Double.parseDouble( newInitem.get("COST_AMOUNT")+"");//add by xubufeng 2012-09-03
                 
                 StringBuilder sb1=new StringBuilder(" SELECT DEALER_CODE,STORAGE_CODE,PART_BATCH_NO,");
                 sb1.append("PART_NO,PART_NAME,UNIT_CODE,STOCK_QUANTITY,COST_PRICE,COST_AMOUNT  FROM tm_part_stock_item ");//TmPartStockItemPO
                 sb1.append( "WHERE DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"' AND PART_NO='");
                 sb1.append(newInitem.get("PART_NO")+"' and STORAGE_CODE ='"+newInitem.get("STORAGE_CODE")+"'");
                 Map<String,Object>  listitemnow= DAOUtil.findFirst(sb1.toString(), null);
                 if (listitemnow != null && listitemnow.size() > 0) {
                     if (listitemnow.get("COST_PRICE") != null  && !"".equals(listitemnow.get("COST_PRICE").toString())) {
                         costPriceStock =Double.parseDouble( listitemnow.get("COST_PRICE")+"");//入帐前成本单价
                        // System.out.println("______________________"+costPriceStock);
                         itemCostPrice = costPriceStock;
                     }
                     if (listitemnow.get("STOCK_QUANTITY") != null&& !"".equals(listitemnow.get("STOCK_QUANTITY").toString())) {
                         stockQuantity =Float.parseFloat( listitemnow.get("STOCK_QUANTITY")+"");//入帐前库存数量
                         itemCostAmount = costPriceStock * Float.parseFloat(newInitem.get("IN_QUANTITY")+"");
                     //    System.out.println("______________________"+itemCostAmount);
                         itemCostAmount = Utility.round(Double.toString(itemCostAmount), 2);
                     //    System.out.println("______________________"+itemCostAmount);
                         
                     }
                     if (listitemnow.get("COST_AMOUNT") != null&& !"".equals(listitemnow.get("COST_AMOUNT").toString())){
                         costAmountStock = Double.parseDouble(listitemnow.get("COST_AMOUNT")+"");//入帐前成本金额
                         costAmountBeforeA = Double.parseDouble(listitemnow.get("COST_AMOUNT")+"");//批次表入帐前成本金额 
                     }
                 }
                 PartStockDTO stockPOCon=new PartStockDTO();
                 stockPOCon.setDealerCode(dealerCode);
                 stockPOCon.setPART_NO(newInitem.get("PART_NO")+"");
                 stockPOCon.setSTORAGE_CODE(newInitem.get("STORAGE_CODE")+"");
                 StringBuilder sb2= new StringBuilder("SELECT DEALER_CODE, PART_NO, STORAGE_CODE,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_CODE,STOCK_QUANTITY,SALES_PRICE,CLAIM_PRICE,LIMIT_PRICE,LATEST_PRICE,INSURANCE_PRICE,COST_PRICE,COST_AMOUNT,");
                 sb2.append("MAX_STOCK,MIN_STOCK,BORROW_QUANTITY,LEND_QUANTITY,LOCKED_QUANTITY,PART_MODEL_GROUP_CODE_SET,PART_SPE_TYPE,IS_SUGGEST_ORDER,MONTHLY_QTY_FORMULA,LAST_STOCK_IN,");
                 sb2.append("  LAST_STOCK_OUT,FOUND_DATE,PART_MAIN_TYPE,ISAUTO_MAXMIN_STOCK,SAFE_STOCK,REMARK  FROM  TM_PART_STOCK  ");
                 sb2.append( "WHERE DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
                 sb2.append("   AND STORAGE_CODE='"+stockPOCon.getSTORAGE_CODE()+"'");
                 sb2.append("   AND PART_NO='"+stockPOCon.getPART_NO()+"'");
               Map<String,Object>  stocknow=DAOUtil.findFirst(sb2.toString(), null);
               if (stocknow != null && stocknow.size() > 0) {
                   if (stocknow.get("COST_AMOUNT")  != null && !"".equals(stocknow.get("COST_AMOUNT"))){
                       costAmountBeforeB =Double.parseDouble(stocknow.get("COST_AMOUNT")+"") ; //库存表入账前成本金额
                   }
               }
               
             //更新子表的成本价格和成本金额
               PartAllocateInItemDto item = new PartAllocateInItemDto();
               item.setDealerCode(dealerCode);
               item.setItemId(newInitem.get("ITEM_ID")+"");
               //System.out.println("______________________"+item.getItemId());
               PartAllocateInItemPO partAllocateInItemPO= PartAllocateInItemPO.findById(newInitem.get("ITEM_ID"));
               if (dealerCode == null || "".equals(dealerCode.trim()) || item.getItemId().toString() == null || "".equals(item.getItemId().toString().trim())) {
                   throw new ServiceBizException("丢失主键!");
               }
             //  System.out.println("______________________"+itemCostPrice);
              partAllocateInItemPO.setDouble("COST_PRICE", itemCostPrice);
             //  System.out.println("______________________"+itemCostAmount);
               partAllocateInItemPO.setDouble("COST_AMOUNT", itemCostAmount);
               partAllocateInItemPO.saveIt();
               
             //更改库存和批次表中成本和数量
               
               TmPartStockItemPO tmPartStockItemPO=TmPartStockItemPO.findByCompositeKeys
                       (dealerCode,newInitem.get("PART_NO")+"",newInitem.get("STORAGE_CODE")+"",newInitem.get("PART_BATCH_NO")+"");
               TmPartStockItemDTO stockItem=new TmPartStockItemDTO();
               stockItem.setPartBatchNo(newInitem.get("PART_BATCH_NO")+"");
               stockItem.setPartNo(newInitem.get("PART_NO")+"");
               System.out.println("______________________"+stockItem.getPartNo());
               stockItem.setStorageCode(newInitem.get("STORAGE_CODE")+"");
             //  System.out.println("______________________"+newInitem.get("IN_QUANTITY"));//newInitem.getStockQuantity
               stockItem.setStockQuantity(Float.parseFloat(newInitem.get("IN_QUANTITY")+""));
               stockItem.setCostAmount(Double.parseDouble(newInitem.get("IN_AMOUNT")+""));//入库取业务单据里不含税入库金额
               System.out.println("______________________"+stockItem);
               calCostPriceOut( dealerCode,  stockItem);  // 更新配件库存数量成本单价成本金额(调拨入库，配件报溢)
              
            // 更改配件库存信息
               PartStockDTO stockPO = new PartStockDTO();
               stockPOCon.setDealerCode(dealerCode);
               stockPOCon.setPART_NO(newInitem.get("PART_NO")+"");
               stockPOCon.setSTORAGE_CODE(newInitem.get("STORAGE_CODE")+"");
               
               stockPO.setLAST_STOCK_IN(new Date(System.currentTimeMillis()));
               if (dealerCode == null || "".equals(dealerCode.trim()) || stockPOCon.getSTORAGE_CODE() == null
                       || "".equals(stockPOCon.getSTORAGE_CODE().trim()) || stockPOCon.getPART_NO() == null
                       || "".equals(stockPOCon.getPART_NO().trim())) {
                   throw new ServiceBizException("丢失主键!");
               }
               TmPartStockPO tmPartStockPO=TmPartStockPO.
                       findByCompositeKeys(stockPOCon.getDealerCode(),stockPOCon.getPART_NO(),stockPOCon.getSTORAGE_CODE());
               tmPartStockPO.setDate("LAST_STOCK_IN", stockPO.getLAST_STOCK_IN());
               tmPartStockPO.saveIt();
               
               StringBuilder sb3= new StringBuilder("SELECT DEALER_CODE, PART_NO, STORAGE_CODE,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_CODE,STOCK_QUANTITY,SALES_PRICE,CLAIM_PRICE,LIMIT_PRICE,LATEST_PRICE,INSURANCE_PRICE,COST_PRICE,COST_AMOUNT,");
               sb3.append("MAX_STOCK,MIN_STOCK,BORROW_QUANTITY,LEND_QUANTITY,LOCKED_QUANTITY,PART_MODEL_GROUP_CODE_SET,PART_SPE_TYPE,IS_SUGGEST_ORDER,MONTHLY_QTY_FORMULA,LAST_STOCK_IN,");
               sb3.append("  LAST_STOCK_OUT,FOUND_DATE,PART_MAIN_TYPE,ISAUTO_MAXMIN_STOCK,SAFE_STOCK,REMARK   FROM  TM_PART_STOCK  WHERE DEALER_CODE='"+FrameworkUtil.getLoginInfo().getDealerCode()+"'");
               sb3.append("   AND STORAGE_CODE='"+stockPOCon.getSTORAGE_CODE()+"'");
               sb3.append("   AND PART_NO='"+stockPOCon.getPART_NO()+"'");
               System.out.println(sb3.toString());
             Map<String,Object>  listStockAfter=DAOUtil.findFirst(sb3.toString(), null);
               
               //List listStockAfter = POFactory.select(conn, stockPOCon);
               if (listStockAfter != null && listStockAfter.size() > 0){
                   if (listStockAfter.get("COST_AMOUNT") != null && !"".equals(listStockAfter.get("COST_AMOUNT").toString())){
                       costAmountAfterB =Double.parseDouble( listStockAfter.get("COST_AMOUNT")+""); //库存表入账后成本金额
                   }
               }
               
            // 更改配件库存批次信息
               TmPartStockItemDTO stockItemPOCon = new TmPartStockItemDTO();
               TmPartStockItemDTO stockItemPO = new TmPartStockItemDTO();
               stockItemPOCon.setDealerCode(dealerCode);
               System.out.println("_______________"+newInitem.get("PART_NO"));
               stockItemPOCon.setPartNo(newInitem.get("PART_NO")+"");
               stockItemPOCon.setPartBatchNo(newInitem.get("PART_BATCH_NO")+"");
               stockItemPOCon.setStorageCode(newInitem.get("STORAGE_CODE")+"");
               stockItemPO.setLastStockIn(new Date(System.currentTimeMillis()));
               System.out.println("_______________"+stockItemPOCon.toString());
               //POFactory.update(conn, stockItemPOCon, stockItemPO);
               TmPartStockItemPO tmPartStockItemPO1=TmPartStockItemPO.findByCompositeKeys(stockItemPOCon.getDealerCode(),
                    stockItemPOCon.getPartNo(),stockItemPOCon.getStorageCode(),stockItemPOCon.getPartBatchNo());
               tmPartStockItemPO1.setDate("LAST_STOCK_IN", stockItemPO.getLastStockIn());
               tmPartStockItemPO1.saveIt();
               
               StringBuilder sb4= new StringBuilder("SELECT  DEALER_CODE,PART_NO,STORAGE_CODE,PART_BATCH_NO,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_CODE,STOCK_QUANTITY,");
               sb4.append("SALES_PRICE,CLAIM_PRICE,LIMIT_PRICE,LATEST_PRICE,INSURANCE_PRICE,COST_PRICE,COST_AMOUNT,BORROW_QUANTITY,PART_STATUS,LEND_QUANTITY,LAST_STOCK_IN,");
               sb4.append("LAST_STOCK_OUT,FOUND_DATE,REMARK  FROM  TM_PART_STOCK_ITEM  WHERE DEALER_CODE='"+stockItemPOCon.getDealerCode()+"'");
               sb4.append("   AND STORAGE_CODE='"+stockItemPOCon.getStorageCode()+"'");
               sb4.append("   AND PART_NO='"+stockItemPOCon.getPartNo()+"'");
               sb4.append("   AND PART_BATCH_NO='"+stockItemPOCon.getPartBatchNo()+"'");
               List<Map>  listItemAfter=DAOUtil.findAll(sb4.toString(), null);
               if (listItemAfter != null && listItemAfter.size() > 0){
                   Map itemAfter =  listItemAfter.get(0);
                   if (itemAfter.get("COST_AMOUNT") != null && !"".equals(itemAfter.get("COST_AMOUNT").toString())){
                       costAmountAfterA = Double.parseDouble( itemAfter.get("COST_AMOUNT")+"");   //批次表入账后成本金额
                   }
               }
             //向配件流水账添加记录
               TtPartFlowDTO flow = new TtPartFlowDTO();
               flow.setCostAmount(itemCostAmount);
               flow.setCostPrice(itemCostPrice);
               //从调拨入库主表查客户代码名称
               PartAllocateInDto inCon = new PartAllocateInDto();
               inCon.setDealerCode(dealerCode);
               inCon.setAllocateInNo(allocateInNo);
//               PartAllocateInPO partAllocateInPO=PartAllocateInPO.findByCompositeKeys(inCon.getAllocateInNo(),inCon.getDealerCode());
//               partAllocateInPO.getString("");
             // partAllocateInPO
               StringBuilder sb5= new StringBuilder("SELECT  DEALER_CODE,ALLOCATE_IN_NO,CUSTOMER_CODE,CUSTOMER_NAME,STOCK_IN_DATE,STOCK_IN_VOUCHER,BEFOE_TAX_AMOUNT,TOTAL_AMOUNT,");
               sb5.append("IS_PAYOFF,IS_PAYOFF_FINANCE,CREDENCE_FINANCE,F_PAY_OFF_DATE,LOCK_USER,CREDENCE,PAY_OFF_DATE,PAY_OFF_TAX,F_PAY_OFF_TAX,");
               sb5.append("IS_FINISHED,HANDLER,FINISHED_DATE, REMARK,IS_IDLE_ALLOCATION,IS_NET_TRANSFER,ALLOCATE_OUT_NO FROM  tt_part_allocate_in  WHERE DEALER_CODE='"+inCon.getDealerCode()+"'");
               sb5.append("   AND ALLOCATE_IN_NO='"+inCon.getAllocateInNo()+"'");
               List<Map>  lin=DAOUtil.findAll(sb5.toString(), null);
               Map Allocate=null;
               if (lin != null && lin.size() > 0)
               {
                   Allocate = lin.get(0);
                   customerCode = Allocate.get("CUSTOMER_CODE")+"";//add by xubufeng 2012-09-03
                   flow.setCustomerCode(Allocate.get("CUSTOMER_CODE")+"");
                   flow.setCustomerName(Allocate.get("CUSTOMER_NAME")+"");
               }
               
               flow.setDealerCode(dealerCode);
               flow.setFlowId(null);
               double amount =Utility.add("1", Utility.getDefaultValue("2034"));
               String strAmount = Double.toString(amount);
               System.out.println("_____________________________"+ newInitem.get("IN_AMOUNT")+"");
               flow.setInOutNetAmount(Double.parseDouble( newInitem.get("IN_AMOUNT")+"")); // 不含税金额
               flow.setInOutNetPrice(Double.parseDouble( newInitem.get("IN_PRICE")+""));// 不含税价格
               flow.setInOutTaxedPrice(Utility.mul(newInitem.get("IN_PRICE")+"", strAmount)); // 含税单价
               flow.setInOutTaxedAmount(Utility.mul(newInitem.get("IN_AMOUNT")+"", strAmount)); // 含税金额
               flow.setInOutTag(Utility.getInt(CommonConstants.DICT_IS_NO));
               flow.setInOutType(Utility.getInt(CommonConstants.DICT_IN_OUT_TYPE_ALLOCATE_IN));
               flow.setOperateDate(Utility.getCurrentDateTime());
               flow.setOperator(empNo);
               flow.setPartBatchNo(newInitem.get("PART_BATCH_NO")+"");
               flow.setPartName(newInitem.get("PART_NAME")+"");
               flow.setPartNo(newInitem.get("PART_NO")+"");
               flow.setSheetNo(allocateInNo);
               flow.setStockInQuantity(Float.parseFloat(newInitem.get("IN_QUANTITY")+""));
               // 取当前配件库存
               TmPartStockItemDTO newItem = new TmPartStockItemDTO();
               
               StringBuilder sb6= new StringBuilder("SELECT  DEALER_CODE,PART_NO,STORAGE_CODE,PART_BATCH_NO,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_CODE,STOCK_QUANTITY,");
               sb6.append("SALES_PRICE,CLAIM_PRICE,LIMIT_PRICE,LATEST_PRICE,INSURANCE_PRICE,COST_PRICE,COST_AMOUNT,BORROW_QUANTITY,PART_STATUS,LEND_QUANTITY,LAST_STOCK_IN,");
               sb6.append("LAST_STOCK_OUT,FOUND_DATE,REMARK  FROM  TM_PART_STOCK_ITEM  WHERE DEALER_CODE='"+stockItemPOCon.getDealerCode()+"'");
               sb6.append("   AND STORAGE_CODE='"+stockItemPOCon.getStorageCode()+"'");
               sb6.append("   AND PART_NO='"+stockItemPOCon.getPartNo()+"'");
               sb6.append("   AND PART_BATCH_NO='"+stockItemPOCon.getPartBatchNo()+"'");
               List<Map>  itemlist=DAOUtil.findAll(sb6.toString(), null);
               if(!itemlist.isEmpty()){//CHRYSLER-138 【配件】调拨入库报错
                   Map map=itemlist.get(0);
                   newItem.setStockQuantity(Float.parseFloat(map.get("STOCK_QUANTITY")+""));
                   newItem.setCostPrice(Double.parseDouble(map.get("COST_PRICE")+""));
               }
               stockQuantityNew = newItem.getStockQuantity();
               flow.setStockQuantity(stockQuantityNew);
               flow.setStorageCode(newInitem.get("STORAGE_CODE")+"");
               flow.setCostAmountBeforeA(costAmountBeforeA);
               flow.setCostAmountBeforeB(costAmountBeforeB);
               flow.setCostAmountAfterA(costAmountAfterA);
               flow.setCostAmountAfterB(costAmountAfterB);
               PartFlowPO partFlowPO=new PartFlowPO(); 
               setPartFlowPO(partFlowPO,flow);
               partFlowPO.saveIt();
               costPriceNew = newItem.getCostPrice();
            // 自然月结报表
               TtPartMonthReportDTO db = new TtPartMonthReportDTO();
               db.setPartBatchNo(newInitem.get("PART_BATCH_NO")+"");
               db.setPartNo(newInitem.get("PART_NO")+"");
               db.setPartName(newInitem.get("PART_NAME")+"");
               db.setStorageCode(newInitem.get("STORAGE_CODE")+"");
               db.setInQuantity(Float.parseFloat(newInitem.get("IN_QUANTITY")+""));
               db.setStockInAmount(Double.parseDouble(newInitem.get("IN_AMOUNT")+""));
             //参数没加公用方法
               db.setOpenQuantity(stockQuantity);//入帐前库存数量
               db.setOpenPrice(costPriceStock);//入帐钱库存成本单价
               db.setOpenAmount(costAmountStock);//入帐前成本金额
               db.setClosePrice(costPriceNew);//入帐后成本单价
               TtPartMonthReportPO monthReportPOFactory = new TtPartMonthReportPO();
               
               createOrUpdate(db,dealerCode);
               
            // 会计月报表
//               TtPartPeriodReportPOFactory ttPartPeriod = new TtPartPeriodReportPOFactory();
//               TmAccountingCyclePOFactory tmAccounting = new TmAccountingCyclePOFactory();
          //     TmAccountingCycleDTO cycle = new TmAccountingCycleDTO();
               Map<String,Object> cycle = getAccountCyclePO(dealerCode);//TmAccountingCycleDTO
               TtPartPeriodReportDTO period = new TtPartPeriodReportDTO();
               period.setPartBatchNo(newInitem.get("PART_BATCH_NO")+"");
               period.setPartNo(newInitem.get("PART_NO")+"");
               period.setPartName(newInitem.get("PART_NAME")+"");
               period.setStorageCode(newInitem.get("STORAGE_CODE")+"");
               period.setInQuantity(Float.parseFloat(newInitem.get("IN_QUANTITY")+""));
               period.setStockInAmount(Double.parseDouble(newInitem.get("IN_AMOUNT")+""));
               period.setAllocateInCount(Float.parseFloat(newInitem.get("IN_QUANTITY")+""));
               period.setAllocateInAmount(Double.parseDouble(newInitem.get("IN_AMOUNT")+""));
//               period.setCreateBy(userId);
//               period.setUpdateBy(userId);
               //方法还未加
               period.setOpenPrice(costPriceStock);//入帐前成本单价
               period.setOpenQuantity(stockQuantity);//入帐前库存数量
               period.setOpenAmount(costAmountStock);//入帐前成本金额
               period.setClosePrice(costPriceNew);//入帐后成本单价
               //end
               ttPartPeriodcreateOrUpdate(period, cycle, dealerCode);
              }
          }
        //更新主表的入账状态
          PartAllocateInDto in = new PartAllocateInDto();
          in.setIsFinished(Utility.getInt(CommonConstants.DICT_IS_YES));
          in.setFinishedDate(new Date(System.currentTimeMillis()));
//          in.setUpdateBy(userId);
//          in.setUpdateDate(Utility.getCurrentDateTime());
          PartAllocateInPO inCon = PartAllocateInPO.findByCompositeKeys(allocateInNo,dealerCode);
          if (dealerCode == null || "".equals(dealerCode.trim()) || allocateInNo == null || "".equals(allocateInNo.trim())){
              throw new ServiceBizException("丢失主键!");
          }
          inCon.setInteger("IS_FINISHED", in.getIsFinished());
          inCon.setDate("FINISHED_DATE", in.getFinishedDate());
          inCon.saveIt();
          
          
        //add by xubufeng 2012-09-03 start
          //生成应付款单据--调拨入库
          if(Utility.testString(allocateInNo)){
              TtPaymentInfoDTO payment = new TtPaymentInfoDTO();
              payment.setItemId(null);
              payment.setDealerCode(dealerCode);
              payment.setBusinessNo(allocateInNo);
              payment.setBusinessType(Integer.valueOf("14051004"));
              if(Utility.testString(customerCode)){
                  payment.setCustomerNo(customerCode);
              }
              payment.setPaymentAmount(totalCostAmount);
//              payment.setCreateBy(userId);
//              payment.setCreateDate(new Date());
              TtPaymentInfoPO ttPaymentInfoPO=new TtPaymentInfoPO();
              ttPaymentInfoPO.setInteger("ITEM_ID", payment.getItemId());
              ttPaymentInfoPO.setString("DEALER_CODE",payment.getDealerCode());
              ttPaymentInfoPO.setString("BUSINESS_NO", payment.getBusinessNo());
              ttPaymentInfoPO.setInteger("BUSINESS_TYPE", payment.getBusinessType());
              ttPaymentInfoPO.saveIt();
          }
          //add by xubufeng 2012-09-03 end
          
        }
    }
    /**
    * TODO description
    * @author yujiangheng
    * @date 2017年5月8日
    * @param db
    * @param account
    * @param dealerCode
     * @throws Exception 
     */
    private void ttPartPeriodcreateOrUpdate(TtPartPeriodReportDTO db,
                                            Map<String, Object> account, String dealerCode) throws Exception {
        
        StringBuffer sbSelect = new StringBuffer();
        StringBuffer sbSqlUpdate = new StringBuffer();
        StringBuffer sbSqlInsert = new StringBuffer();
        logger.debug("SSSSSSSSSSSSSSSSSSSSS" + db.getBuyInAmount());
        sbSelect.append(" SELECT DEALER_CODE" + " FROM TT_PART_PERIOD_REPORT");
        sbSelect.append( " WHERE DEALER_CODE = '" + dealerCode + "'" + " AND STORAGE_CODE = ?");
        sbSelect.append(" AND PART_NO = ?" + " AND REPORT_YEAR = ?" + " AND REPORT_MONTH = ?");
        sbSelect.append( " AND PART_BATCH_NO = ?" + " AND D_KEY = " + CommonConstants.D_KEY);
        
        sbSqlUpdate.append(" UPDATE TT_PART_PERIOD_REPORT");
        sbSqlUpdate.append(" SET IN_QUANTITY = CASE WHEN IN_QUANTITY IS NULL THEN 0 ELSE IN_QUANTITY END + ?,");
        sbSqlUpdate.append(" STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" BUY_IN_COUNT = CASE WHEN BUY_IN_COUNT IS NULL THEN 0 ELSE BUY_IN_COUNT END + ?,");
        sbSqlUpdate.append(" BUY_IN_AMOUNT = CASE WHEN BUY_IN_AMOUNT IS NULL THEN 0 ELSE BUY_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_IN_COUNT = CASE WHEN ALLOCATE_IN_COUNT IS NULL THEN 0 ELSE ALLOCATE_IN_COUNT END + ?,");
        sbSqlUpdate.append( " ALLOCATE_IN_AMOUNT = CASE WHEN ALLOCATE_IN_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" OTHER_IN_COUNT = CASE WHEN OTHER_IN_COUNT IS NULL THEN 0 ELSE OTHER_IN_COUNT END + ?,");
        sbSqlUpdate.append(" OTHER_IN_AMOUNT = CASE WHEN OTHER_IN_AMOUNT IS NULL THEN 0 ELSE OTHER_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" PROFIT_IN_COUNT = CASE WHEN PROFIT_IN_COUNT IS NULL THEN 0 ELSE PROFIT_IN_COUNT END + ?,");
        sbSqlUpdate.append( " PROFIT_IN_AMOUNT = CASE WHEN PROFIT_IN_AMOUNT IS NULL THEN 0 ELSE PROFIT_IN_AMOUNT END + ?,");
        sbSqlUpdate.append( " OUT_QUANTITY = CASE WHEN OUT_QUANTITY IS NULL THEN 0 ELSE OUT_QUANTITY END + ?,");
        sbSqlUpdate.append(" STOCK_OUT_COST_AMOUNT = CASE WHEN STOCK_OUT_COST_AMOUNT IS NULL THEN 0 ELSE STOCK_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" OUT_AMOUNT = CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT END + ?,");
        sbSqlUpdate.append( " REPAIR_OUT_COUNT = CASE WHEN REPAIR_OUT_COUNT IS NULL THEN 0 ELSE REPAIR_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" REPAIR_OUT_COST_AMOUNT = CASE WHEN REPAIR_OUT_COST_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" REPAIR_OUT_SALE_AMOUNT = CASE WHEN REPAIR_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" SALE_OUT_COUNT = CASE WHEN SALE_OUT_COUNT IS NULL THEN 0 ELSE SALE_OUT_COUNT END + ?,");
        sbSqlUpdate.append( " SALE_OUT_COST_AMOUNT = CASE WHEN SALE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" SALE_OUT_SALE_AMOUNT = CASE WHEN SALE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append( " INNER_OUT_COUNT = CASE WHEN INNER_OUT_COUNT IS NULL THEN 0 ELSE INNER_OUT_COUNT END + ?,");
        sbSqlUpdate.append( " INNER_OUT_COST_AMOUNT = CASE WHEN INNER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" INNER_OUT_SALE_AMOUNT = CASE WHEN INNER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_OUT_COUNT = CASE WHEN ALLOCATE_OUT_COUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_OUT_COST_AMOUNT = CASE WHEN ALLOCATE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_OUT_SALE_AMOUNT = CASE WHEN ALLOCATE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" OTHER_OUT_COUNT = CASE WHEN OTHER_OUT_COUNT IS NULL THEN 0 ELSE OTHER_OUT_COUNT END + ?,");
        sbSqlUpdate.append( " OTHER_OUT_COST_AMOUNT = CASE WHEN OTHER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append( " OTHER_OUT_SALE_AMOUNT = CASE WHEN OTHER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" LOSS_OUT_COUNT = CASE WHEN LOSS_OUT_COUNT IS NULL THEN 0 ELSE LOSS_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" LOSS_OUT_AMOUNT = CASE WHEN LOSS_OUT_AMOUNT IS NULL THEN 0 ELSE LOSS_OUT_AMOUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_IN_COUNT = CASE WHEN TRANSFER_IN_COUNT IS NULL THEN 0 ELSE TRANSFER_IN_COUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_IN_AMOUNT = CASE WHEN TRANSFER_IN_AMOUNT IS NULL THEN 0 ELSE TRANSFER_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_OUT_COUNT = CASE WHEN TRANSFER_OUT_COUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_OUT_COST_AMOUNT = CASE WHEN TRANSFER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" UPHOLSTER_OUT_COUNT = CASE WHEN UPHOLSTER_OUT_COUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COUNT END + ?,");
        sbSqlUpdate.append( " UPHOLSTER_OUT_COST_AMOUNT = CASE WHEN UPHOLSTER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" UPHOLSTER_OUT_SALE_AMOUNT = CASE WHEN UPHOLSTER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append( " CLOSE_QUANTITY =  CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY  END + ?-?," );
        sbSqlUpdate.append(" CLOSE_PRICE = ?," );
        sbSqlUpdate.append( " CLOSE_AMOUNT =  CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT  END + ?-? "   );                 
        sbSqlUpdate.append( " WHERE DEALER_CODE = ?");
        sbSqlUpdate.append( " AND REPORT_YEAR = ?" + " AND REPORT_MONTH = ?");
        sbSqlUpdate.append( " AND STORAGE_CODE = ?" + " AND PART_BATCH_NO = ?" + " AND PART_NO = ?");
        sbSqlUpdate.append( " AND D_KEY = " + CommonConstants.D_KEY);
        
        sbSqlInsert.append(" INSERT INTO TT_PART_PERIOD_REPORT");
        sbSqlInsert.append(" (REPORT_YEAR, REPORT_MONTH,STORAGE_CODE, PART_BATCH_NO,PART_NO,DEALER_CODE,PART_NAME, ");
        sbSqlInsert.append(" IN_QUANTITY, STOCK_IN_AMOUNT, BUY_IN_COUNT, BUY_IN_AMOUNT,ALLOCATE_IN_COUNT,");
        sbSqlInsert.append( " ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT, PROFIT_IN_COUNT, PROFIT_IN_AMOUNT,");
        sbSqlInsert.append(" OUT_QUANTITY, STOCK_OUT_COST_AMOUNT, OUT_AMOUNT,");
        sbSqlInsert.append(" REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT,REPAIR_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" INNER_OUT_COUNT, INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT, ALLOCATE_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" OTHER_OUT_COUNT, OTHER_OUT_COST_AMOUNT, OTHER_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" LOSS_OUT_COUNT, LOSS_OUT_AMOUNT, TRANSFER_IN_COUNT,TRANSFER_IN_AMOUNT,TRANSFER_OUT_COUNT,TRANSFER_OUT_COST_AMOUNT,");
        sbSqlInsert.append(" OPEN_QUANTITY, OPEN_PRICE, OPEN_AMOUNT,UPHOLSTER_OUT_COUNT,UPHOLSTER_OUT_COST_AMOUNT,UPHOLSTER_OUT_SALE_AMOUNT, CLOSE_QUANTITY,CLOSE_PRICE,CLOSE_AMOUNT");
        sbSqlInsert.append(" ) VALUES(" + "  ?, ?, ?, ?, ?, ?, ?," + "  ?, ?, ?, ?, ?,");
        sbSqlInsert.append("  ?, ?, ?, ?, ?," + "  ?, ?, ?, " + "  ?, ?, ?, " + "  ?, ?, ?, ");
        sbSqlInsert.append("  ?, ?, ?, " + "  ?, ?,  ?, ?, ?,  ?, ?, ?, ?,?, ?, ?, ?, ?, ?, ?, ?,?,?, ?, ? )");
        
        //执行sql
        logger.debug("::Select SQL :=" + sbSelect);
        logger.debug("::Insert SQL :=" + sbSqlInsert);
        logger.debug("::Update SQL :=" + sbSqlUpdate);
        
        if (db != null){
            if (db.getOpenQuantity() == null)  db.setOpenQuantity(0f);
            if (db.getOpenPrice() == null)   db.setOpenPrice(0d);
            if (db.getOpenAmount() == null)   db.setOpenAmount(0d);
            if (db.getInQuantity() == null)    db.setInQuantity(0f);
            if (db.getStockInAmount() == null)    db.setStockInAmount(0d);
            if (db.getBuyInCount() == null)     db.setBuyInCount(0f);
            if (db.getBuyInAmount() == null)     db.setBuyInAmount(0d);
            if (db.getAllocateInAmount() == null)    db.setAllocateInAmount(0d);
            if (db.getAllocateInCount() == null)   db.setAllocateInCount(0f);
            if (db.getOtherInCount() == null)    db.setOtherInCount(0f);
            if (db.getOtherInAmount() == null)    db.setOtherInAmount(0d);
            if (db.getProfitInCount() == null)   db.setProfitInCount(0f);
            if (db.getProfitInAmount() == null)   db.setProfitInAmount(0d);
            if (db.getOutQuantity() == null)  db.setOutQuantity(0f);
            if (db.getStockOutCostAmount() == null)       db.setStockOutCostAmount(0d);
            if (db.getOutAmount() == null)   db.setOutAmount(0d);
            if (db.getRepairOutCount() == null)     db.setRepairOutCount(0f);
            if (db.getRepairOutCostAmount() == null)     db.setRepairOutCostAmount(0d);
            if (db.getRepairOutSaleAmount() == null)    db.setRepairOutSaleAmount(0d);
            if (db.getSaleOutCount() == null)     db.setSaleOutCount(0f);
            if (db.getSaleOutCostAmount() == null)      db.setSaleOutCostAmount(0d);
            if (db.getSaleOutSaleAmount() == null)   db.setSaleOutSaleAmount(0d);
            if (db.getInnerOutCount() == null)   db.setInnerOutCount(0f);
            if (db.getInnerOutCostAmount() == null)      db.setInnerOutCostAmount(0d);
            if (db.getInnerOutSaleAmount() == null)       db.setInnerOutSaleAmount(0d);
            if (db.getAllocateOutCount() == null)    db.setAllocateOutCount(0f);
            if (db.getAllocateOutCostAmount() == null)      db.setAllocateOutCostAmount(0d);
            if (db.getAllocateOutSaleAmount() == null)    db.setAllocateOutSaleAmount(0d);
            if (db.getOtherOutCount() == null)  db.setOtherOutCount(0f);
            if (db.getOtherOutCostAmount() == null)   db.setOtherOutCostAmount(0d);
            if (db.getOtherOutSaleAmount() == null)    db.setOtherOutSaleAmount(0d);
            if (db.getLossOutCount() == null)   db.setLossOutCount(0f);
            if (db.getLossOutAmount() == null)     db.setLossOutAmount(0d);
            if (db.getCloseQuantity() == null)    db.setCloseQuantity(0f);
            if (db.getClosePrice() == null)    db.setClosePrice(0d);
            if (db.getCloseAmount() == null)    db.setCloseAmount(0d);
            if (db.getTransferInAmount() == null)   db.setTransferInAmount(0d);
            if (db.getTransferOutCostAmount() == null)     db.setTransferOutCostAmount(0d);
            if (db.getTransferInCount() == null)    db.setTransferInCount(0f);
            if (db.getTransferOutCount() == null)    db.setTransferOutCount(0f);
            if(db.getUpholsterOutCount()==null){      db.setUpholsterOutCount(0f);
            }
            if(db.getUpholsterOutCostAmount()==null){
                db.setUpholsterOutCostAmount(0d);
            }
            if(db.getUpholsterOutSaleAmount()==null){
                db.setUpholsterOutSaleAmount(0d);
            }
            List<Object> queryParam=new ArrayList<Object>();
            queryParam.add(db.getStorageCode());
            queryParam.add( db.getPartNo());
            queryParam.add(Utility.fullSpaceBuffer2(account.get("YEAR")+"", 4));
            queryParam.add(Utility.fullSpaceBuffer2(account.get("PERIODS")+"", 2));
            queryParam.add(db.getPartBatchNo());
            Map<String,Object>rs1=DAOUtil.findFirst(sbSelect.toString(), queryParam);
//            int rs1=Base.exec(sbSelect.toString(), 
//                              db.getStorageCode(), db.getPartNo(), 
//                              Utility.fullSpaceBuffer2(account.get("YEAR")+"", 4)
//                              , Utility.fullSpaceBuffer2(account.get("PERIODS")+"", 2), db.getPartBatchNo());
//            logger.debug(rs1+"：检测结果");//检测结果
            if (rs1!=null) {
               Base.exec(sbSqlUpdate.toString(), 
              //STOCK_IN_COUNT,STOCK_IN_AMOUNT,BUY_IN_COUNT,BUY_IN_AMOUNT,ALLOCATE_IN_COUNT,          
                 Math.round(db.getInQuantity()*100)*0.01
                , Math.round(db.getStockInAmount() * 100) * 0.01
                , Math.round(db.getBuyInCount()*100)*0.01
                , Math.round(db.getBuyInAmount() * 100) * 0.01
                , Math.round(db.getAllocateInCount()*100)*0.01

                //ALLOCATE_IN_AMOUNT,OTHER_IN_COUNT,OTHER_IN_AMOUNT,PROFIT_IN_COUNT,PROFIT_IN_AMOUNT,                       
                , Math.round(db.getAllocateInAmount() * 100) * 0.01
                , Math.round(db.getOtherInCount()*100)*0.01
                , Math.round(db.getOtherInAmount() * 100) * 0.01
                , Math.round(db.getProfitInCount()*100)*0.01
                , Math.round(db.getProfitInAmount() * 100) * 0.01

                //STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT,REPAIR_OUT_COUNT,REPAIR_OUT_COST_AMOUNT,                       
                , Math.round(db.getOutQuantity()*100)*0.01
                , Math.round(db.getStockOutCostAmount() * 100) * 0.01
                , Math.round(db.getOutAmount() * 100) * 0.01
                , Math.round(db.getRepairOutCount()*100)*0.01
                , Math.round(db.getRepairOutCostAmount() * 100) * 0.01

                //REPAIR_OUT_SALE_AMOUNT,SALE_OUT_COUNT,SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT,                      
                , Math.round(db.getRepairOutSaleAmount() * 100) * 0.01
                , Math.round(db.getSaleOutCount()*100)*0.01
                , Math.round(db.getSaleOutCostAmount() * 100) * 0.01
                , Math.round(db.getSaleOutSaleAmount() * 100) * 0.01
                , Math.round(db.getInnerOutCount()*100)*0.01

                //INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT,ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT,ALLOCATE_OUT_SALE_AMOUNT,                     
                , Math.round(db.getInnerOutCostAmount() * 100) * 0.01
                , Math.round(db.getInnerOutSaleAmount() * 100) * 0.01
                , Math.round(db.getAllocateOutCount()*100)*0.01
                , Math.round(db.getAllocateOutCostAmount() * 100) * 0.01
                , Math.round(db.getAllocateOutSaleAmount() * 100) * 0.01

                //OTHER_OUT_COUNT,OTHER_OUT_COST_AMOUNT,OTHER_OUT_SALE_AMOUNT,LOSS_OUT_COUNT,LOSS_OUT_AMOUNT,                       
                , Math.round(db.getOtherOutCount()*100)*0.01
                , Math.round(db.getOtherOutCostAmount() * 100) * 0.01
                , Math.round(db.getOtherOutSaleAmount() * 100) * 0.01
                , Math.round(db.getLossOutCount()*100)*0.01
                , Math.round(db.getLossOutAmount() * 100) * 0.01
                , Math.round(db.getTransferInCount()*100)*0.01
                , Math.round(db.getTransferInAmount() * 100) * 0.01
                , Math.round(db.getTransferOutCount()*100)*0.01
                , Math.round(db.getTransferOutCostAmount() * 100) * 0.01
                
//              UPHOLSTER_OUT_COUNT
                , Math.round(db.getUpholsterOutCount()*100)*0.01
               , Math.round(db.getUpholsterOutCostAmount()*100)*0.01
                , Math.round(db.getUpholsterOutSaleAmount()* 100) * 0.01

                , Math.round(db.getInQuantity()*100)*0.01
                , Math.round(db.getOutQuantity()*100)*0.01
                , Math.round(db.getClosePrice() * 10000) * 0.0001
                , Math.round(db.getStockInAmount()*100)*0.01
                , Math.round(db.getStockOutCostAmount()*100)*0.01
                
                
                

                //UPDATE_BY,UPDATE_DATE,PART_PERIOD_REPORT_ID
           /*  psUpdate.setLong(43, db.getUpdateBy());
                 psUpdate.setTimestamp(44, Utility.getCurrentTimestamp());
         */
                , dealerCode
                ,  Utility.fullSpaceBuffer2(account.get("YEAR")+"", 4)
                , Utility.fullSpaceBuffer2(account.get("PERIODS")+"", 2)
                , db.getStorageCode()
                , db.getPartBatchNo()
                , db.getPartNo());
                logger.debug(this.getClass() + "--------->psUpdate over!");
             //   psUpdate.executeUpdate();
                logger.debug(this.getClass() + "--------->psUpdate Execute over!");
                
                
            }else{
              //InsertSQL的?的次序：
                // REPORT_YEAR, REPORT_MONTH,STOCK_IN_COUNT, STOCK_IN_AMOUNT, BUY_IN_COUNT  
                logger.debug("REPORT_YEAR:" +  Utility.fullSpaceBuffer2(account.get("YEAR")+"", 4));
                logger.debug("REPORT_MONTH:" +   Utility.fullSpaceBuffer2(account.get("PERIODS")+"", 2));
               Base.exec(sbSqlInsert.toString(), 
                 Utility.fullSpaceBuffer2(account.get("YEAR")+"", 4)
                , Utility.fullSpaceBuffer2(account.get("PERIODS")+"", 2)
                , db.getStorageCode()
                , db.getPartBatchNo()
                , db.getPartNo()
                , dealerCode
                , db.getPartName()

                , Math.round(db.getInQuantity()*100)*0.01
                , Math.round(db.getStockInAmount()*100)*0.01
                , Math.round(db.getBuyInCount()*100)*0.01

                //BUY_IN_AMOUNT,ALLOCATE_IN_COUNT, ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT                       
                , Math.round(db.getBuyInAmount()*100)*0.01
                , Math.round(db.getAllocateInCount()*100)*0.01
                , Math.round(db.getAllocateInAmount()*100)*0.01
                , Math.round(db.getOtherInCount()*100)*0.01
                , Math.round(db.getOtherInAmount()*100)*0.01

                //PROFIT_IN_COUNT, PROFIT_IN_AMOUNT,STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT                      
                , Math.round(db.getProfitInCount()*100)*0.01
                , Math.round(db.getProfitInAmount()*100)*0.01
                //psInsert.setDouble(18, Math.round(db.getSaleOutCount()*100)*0.01);
                , Math.round(db.getOutQuantity()*100)*0.01
                , Math.round(db.getStockOutCostAmount()*100)*0.01
               , Math.round(db.getOutAmount()*100)*0.01

                //REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT,REPAIR_OUT_SALE_AMOUNT, SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT                     
                , Math.round(db.getRepairOutCount()*100)*0.01
                , Math.round(db.getRepairOutCostAmount()*100)*0.01
                , Math.round(db.getRepairOutSaleAmount()*100)*0.01
                , Math.round(db.getSaleOutCount()*100)*0.01
                , Math.round(db.getSaleOutCostAmount()*100)*0.01

                //SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT, INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT, ALLOCATE_OUT_COUNT                     
                , Math.round(db.getSaleOutSaleAmount()*100)*0.01
                , Math.round(db.getInnerOutCount()*100)*0.01
                , Math.round(db.getInnerOutCostAmount()*100)*0.01
                , Math.round(db.getInnerOutSaleAmount()*100)*0.01
                , Math.round(db.getAllocateOutCount()*100)*0.01

                //ALLOCATE_OUT_COST_AMOUNT,ALLOCATE_OUT_SALE_AMOUNT,OTHER_OUT_COUNT,OTHER_OUT_COST_AMOUNT,OTHER_OUT_SALE_AMOUNT                         
                , Math.round(db.getAllocateOutCostAmount()*100)*0.01
                , Math.round(db.getAllocateOutSaleAmount()*100)*0.01
                , Math.round(db.getOtherOutCount()*100)*0.01
                , Math.round(db.getOtherOutCostAmount()*100)*0.01
                , Math.round(db.getOtherOutSaleAmount()*100)*0.01

                //LOSS_OUT_COUNT,LOSS_OUT_AMOUNT,CREATE_BY,CREATE_DATE, ORG_CODE                    
                , Math.round(db.getLossOutCount()*100)*0.01
                , Math.round(db.getLossOutAmount()*100)*0.01
//                psInsert.setLong(38, db.getCreateBy());
//                psInsert.setDate(39, new java.sql.Date(System.currentTimeMillis()));
                , Math.round(db.getTransferInCount()*100)*0.01
                , Math.round(db.getTransferInAmount()*100)*0.01
                , Math.round(db.getTransferOutCount()*100)*0.01
                , Math.round(db.getTransferOutCostAmount()*100)*0.01
                
                , Math.round(db.getOpenQuantity()*100)*0.01
                , Math.round(db.getOpenPrice() * 10000) * 0.0001
                , Math.round(db.getOpenAmount()*100)*0.01
                
                //UPHOLSTER_OUT_COUNT add by jll 2011-09-09
                , Math.round(db.getUpholsterOutCount()*100)*0.01
                , Math.round(db.getUpholsterOutCostAmount() * 100) * 0.01
                , Math.round(db.getUpholsterOutSaleAmount()*100)*0.01
                
                , getSubF(getAddF(db.getOpenQuantity(),db.getInQuantity()),db.getOutQuantity())
                , Math.round(db.getClosePrice() * 10000) * 0.0001
                , getSubD(getAddD(db.getOpenAmount(),db.getStockInAmount()),db.getStockOutCostAmount()));
                logger.debug(this.getClass() + "------->psInsert set over!");
                logger.debug("db.getDealerId()--->" + dealerCode);
                //psInsert.executeUpdate();
                logger.debug(this.getClass() + "------->psInsert Execute over!");
            }
        }
        logger.debug(this.getClass() + "createOrUpdate over!");
        createOrUpdateDaily(db, dealerCode);
        logger.debug(this.getClass() + "createOrUpdateDaily over!");
        
        
        
    }
    /**
     * 此方法只供 方法ttPartPeriodcreateOrUpdate() 调用
    * TODO description
    * @author yujiangheng
    * @date 2017年5月8日
    * @param db
    * @param dealerCode
     * @throws Exception 
     */
    private void createOrUpdateDaily(TtPartPeriodReportDTO db, String dealerCode) throws Exception {
        StringBuffer sbSelect = new StringBuffer();
        StringBuffer sbSqlUpdate = new StringBuffer();
        StringBuffer sbSqlInsert = new StringBuffer();
        Timestamp timestamp = Utility.getCurrentTimestamp();
        String CurDate = timestamp.toString().substring(0, 10);
        
        sbSelect.append(" SELECT DEALER_CODE" + " FROM TT_PART_DAILY_REPORT");
        sbSelect.append(" WHERE DEALER_CODE = '" + dealerCode + "'" + " AND STORAGE_CODE = ?");
        sbSelect.append(" AND REPORT_DATE = ?" + " AND D_KEY = " + CommonConstants.D_KEY);
        
        sbSqlUpdate.append(" UPDATE TT_PART_DAILY_REPORT");
        sbSqlUpdate.append( " SET IN_QUANTITY = CASE WHEN IN_QUANTITY IS NULL THEN 0 ELSE IN_QUANTITY END + ?,");
        sbSqlUpdate.append(" STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" BUY_IN_COUNT = CASE WHEN BUY_IN_COUNT IS NULL THEN 0 ELSE BUY_IN_COUNT END + ?,");
        sbSqlUpdate.append(" BUY_IN_AMOUNT = CASE WHEN BUY_IN_AMOUNT IS NULL THEN 0 ELSE BUY_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_IN_COUNT = CASE WHEN ALLOCATE_IN_COUNT IS NULL THEN 0 ELSE ALLOCATE_IN_COUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_IN_AMOUNT = CASE WHEN ALLOCATE_IN_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_IN_AMOUNT END + ?,");
        sbSqlUpdate.append( " OTHER_IN_COUNT = CASE WHEN OTHER_IN_COUNT IS NULL THEN 0 ELSE OTHER_IN_COUNT END + ?,");
        sbSqlUpdate.append(" OTHER_IN_AMOUNT = CASE WHEN OTHER_IN_AMOUNT IS NULL THEN 0 ELSE OTHER_IN_AMOUNT END + ?,");
        sbSqlUpdate.append(" PROFIT_IN_COUNT = CASE WHEN PROFIT_IN_COUNT IS NULL THEN 0 ELSE PROFIT_IN_COUNT END + ?,");
        sbSqlUpdate.append( " PROFIT_IN_AMOUNT = CASE WHEN PROFIT_IN_AMOUNT IS NULL THEN 0 ELSE PROFIT_IN_AMOUNT END + ?,");
        sbSqlUpdate.append( " OUT_QUANTITY = CASE WHEN OUT_QUANTITY IS NULL THEN 0 ELSE OUT_QUANTITY END + ?,");
        sbSqlUpdate.append(" STOCK_OUT_COST_AMOUNT = CASE WHEN STOCK_OUT_COST_AMOUNT IS NULL THEN 0 ELSE STOCK_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" OUT_AMOUNT = CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT END + ?,");
        sbSqlUpdate.append( " REPAIR_OUT_COUNT = CASE WHEN REPAIR_OUT_COUNT IS NULL THEN 0 ELSE REPAIR_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" REPAIR_OUT_COST_AMOUNT = CASE WHEN REPAIR_OUT_COST_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" REPAIR_OUT_SALE_AMOUNT = CASE WHEN REPAIR_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE REPAIR_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append( " SALE_OUT_COUNT = CASE WHEN SALE_OUT_COUNT IS NULL THEN 0 ELSE SALE_OUT_COUNT END + ?,");
        sbSqlUpdate.append( " SALE_OUT_COST_AMOUNT = CASE WHEN SALE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append( " SALE_OUT_SALE_AMOUNT = CASE WHEN SALE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE SALE_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" INNER_OUT_COUNT = CASE WHEN INNER_OUT_COUNT IS NULL THEN 0 ELSE INNER_OUT_COUNT END + ?,");
        sbSqlUpdate.append( " INNER_OUT_COST_AMOUNT = CASE WHEN INNER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append( " INNER_OUT_SALE_AMOUNT = CASE WHEN INNER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE INNER_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_OUT_COUNT = CASE WHEN ALLOCATE_OUT_COUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_OUT_COST_AMOUNT = CASE WHEN ALLOCATE_OUT_COST_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" ALLOCATE_OUT_SALE_AMOUNT = CASE WHEN ALLOCATE_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE ALLOCATE_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" OTHER_OUT_COUNT = CASE WHEN OTHER_OUT_COUNT IS NULL THEN 0 ELSE OTHER_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" OTHER_OUT_COST_AMOUNT = CASE WHEN OTHER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" OTHER_OUT_SALE_AMOUNT = CASE WHEN OTHER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE OTHER_OUT_SALE_AMOUNT END + ?,");
        sbSqlUpdate.append(" LOSS_OUT_COUNT = CASE WHEN LOSS_OUT_COUNT IS NULL THEN 0 ELSE LOSS_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" LOSS_OUT_AMOUNT = CASE WHEN LOSS_OUT_AMOUNT IS NULL THEN 0 ELSE LOSS_OUT_AMOUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_IN_COUNT = CASE WHEN TRANSFER_IN_COUNT IS NULL THEN 0 ELSE TRANSFER_IN_COUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_IN_AMOUNT = CASE WHEN TRANSFER_IN_AMOUNT IS NULL THEN 0 ELSE TRANSFER_IN_AMOUNT END + ?,");
        sbSqlUpdate.append( " TRANSFER_OUT_COUNT = CASE WHEN TRANSFER_OUT_COUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COUNT END + ?,");
        sbSqlUpdate.append(" TRANSFER_OUT_COST_AMOUNT = CASE WHEN TRANSFER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE TRANSFER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" UPHOLSTER_OUT_COUNT = CASE WHEN UPHOLSTER_OUT_COUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COUNT END + ?,");
        sbSqlUpdate.append( " UPHOLSTER_OUT_COST_AMOUNT = CASE WHEN UPHOLSTER_OUT_COST_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_COST_AMOUNT END + ?,");
        sbSqlUpdate.append(" UPHOLSTER_OUT_SALE_AMOUNT = CASE WHEN UPHOLSTER_OUT_SALE_AMOUNT IS NULL THEN 0 ELSE UPHOLSTER_OUT_SALE_AMOUNT END + ?,"    );                 
        sbSqlUpdate.append(" CLOSE_QUANTITY = CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY END + ?-?,");
        sbSqlUpdate.append(" CLOSE_AMOUNT = CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT END + ?-? ");
        sbSqlUpdate.append(" WHERE DEALER_CODE = ?");
        sbSqlUpdate.append(" AND REPORT_DATE = ?" + " AND STORAGE_CODE = ?" + " AND D_KEY = "+CommonConstants.D_KEY );

        sbSqlInsert.append(" INSERT INTO TT_PART_DAILY_REPORT");
        sbSqlInsert.append(" (REPORT_DATE,STORAGE_CODE, DEALER_CODE, ");
        sbSqlInsert.append(" IN_QUANTITY, STOCK_IN_AMOUNT, BUY_IN_COUNT, BUY_IN_AMOUNT,ALLOCATE_IN_COUNT,");
        sbSqlInsert.append(" ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT, PROFIT_IN_COUNT, PROFIT_IN_AMOUNT,");
        sbSqlInsert.append( " OUT_QUANTITY, STOCK_OUT_COST_AMOUNT, OUT_AMOUNT,");
        sbSqlInsert.append( " REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT,REPAIR_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" INNER_OUT_COUNT, INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT,");
        sbSqlInsert.append( " ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT, ALLOCATE_OUT_SALE_AMOUNT,");
        sbSqlInsert.append( " OTHER_OUT_COUNT, OTHER_OUT_COST_AMOUNT, OTHER_OUT_SALE_AMOUNT,");
        sbSqlInsert.append(" LOSS_OUT_COUNT, LOSS_OUT_AMOUNT,TRANSFER_IN_COUNT,");
        sbSqlInsert.append("TRANSFER_IN_AMOUNT,TRANSFER_OUT_COUNT,TRANSFER_OUT_COST_AMOUNT,");
        sbSqlInsert.append("  UPHOLSTER_OUT_COUNT,UPHOLSTER_OUT_COST_AMOUNT,UPHOLSTER_OUT_SALE_AMOUNT, ");
                        //  + " OPEN_QUANTITY,OPEN_AMOUNT,CLOSE_QUANTITY,CLOSE_AMOUNT "
        sbSqlInsert.append(" CLOSE_QUANTITY,CLOSE_AMOUNT,OPEN_QUANTITY,OPEN_AMOUNT ");
        sbSqlInsert.append(" ) VALUES( ?, ?, ?, ?, ?, ?, ?, ?,?, ?, ?,  ?, ?, ?,  ?, ?, ?,  ?, ?, ?,   ?, ?, ?, ?, ?, ?,  ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ");
        sbSqlInsert.append( "(SELECT SUM(STOCK_QUANTITY)  from tm_part_stock  where storage_code=? and dealer_code=?),");
        sbSqlInsert.append( "(SELECT SUM(COST_AMOUNT)  from tm_part_stock  where storage_code=? and dealer_code=?),");
        sbSqlInsert.append( "(SELECT SUM(STOCK_QUANTITY)  from tm_part_stock  where storage_code=? and dealer_code=?)+?-?,");
        sbSqlInsert.append( "(SELECT SUM(COST_AMOUNT)  from tm_part_stock  where storage_code=? and dealer_code=?)+?-? )");
        
        logger.debug("::Select SQL :=" + sbSelect);
        logger.debug("::Insert SQL :=" + sbSqlInsert);
        logger.debug("::Update SQL :=" + sbSqlUpdate);
        if (db != null){
            if (db.getOpenQuantity() == null) db.setOpenQuantity(0f);
            if (db.getOpenPrice() == null)  db.setOpenPrice(0d);
            if (db.getOpenAmount() == null)    db.setOpenAmount(0d);
            if (db.getInQuantity() == null)   db.setInQuantity(0f);
            if (db.getStockInAmount() == null)  db.setStockInAmount(0d);
            if (db.getBuyInCount() == null)    db.setBuyInCount(0f);
            if (db.getBuyInAmount() == null)    db.setBuyInAmount(0d);
            if (db.getAllocateInAmount() == null)  db.setAllocateInAmount(0d);
            if (db.getAllocateInCount() == null)    db.setAllocateInCount(0f);
            if (db.getOtherInCount() == null)   db.setOtherInCount(0f);
            if (db.getOtherInAmount() == null)     db.setOtherInAmount(0d);
            if (db.getProfitInCount() == null)    db.setProfitInCount(0f);
            if (db.getProfitInAmount() == null)   db.setProfitInAmount(0d);
            if (db.getOutQuantity() == null)    db.setOutQuantity(0f);
            if (db.getStockOutCostAmount() == null)    db.setStockOutCostAmount(0d);
            if (db.getOutAmount() == null)    db.setOutAmount(0d);
            if (db.getRepairOutCount() == null)    db.setRepairOutCount(0f);
            if (db.getRepairOutCostAmount() == null)   db.setRepairOutCostAmount(0d);
            if (db.getRepairOutSaleAmount() == null)   db.setRepairOutSaleAmount(0d);
            if (db.getSaleOutCount() == null)   db.setSaleOutCount(0f);
            if (db.getSaleOutCostAmount() == null)    db.setSaleOutCostAmount(0d);
            if (db.getSaleOutSaleAmount() == null)    db.setSaleOutSaleAmount(0d);
            if (db.getInnerOutCount() == null)    db.setInnerOutCount(0f);
            if (db.getInnerOutCostAmount() == null)  db.setInnerOutCostAmount(0d);
            if (db.getInnerOutSaleAmount() == null)    db.setInnerOutSaleAmount(0d);
            if (db.getAllocateOutCount() == null)  db.setAllocateOutCount(0f);
            if (db.getAllocateOutCostAmount() == null)    db.setAllocateOutCostAmount(0d);
            if (db.getAllocateOutSaleAmount() == null)    db.setAllocateOutSaleAmount(0d);
            if (db.getOtherOutCount() == null)   db.setOtherOutCount(0f);
            if (db.getOtherOutCostAmount() == null)   db.setOtherOutCostAmount(0d);
            if (db.getOtherOutSaleAmount() == null)   db.setOtherOutSaleAmount(0d);
            if (db.getLossOutCount() == null)     db.setLossOutCount(0f);
            if (db.getLossOutAmount() == null)    db.setLossOutAmount(0d);
            if (db.getCloseQuantity() == null)    db.setCloseQuantity(0f);
            if (db.getClosePrice() == null)    db.setClosePrice(0d);
            if (db.getCloseAmount() == null)   db.setCloseAmount(0d);
            if (db.getTransferInAmount() == null)     db.setTransferInAmount(0d);
            if (db.getTransferOutCostAmount() == null)   db.setTransferOutCostAmount(0d);
            if (db.getTransferInCount() == null)    db.setTransferInCount(0f);
            if (db.getTransferOutCount() == null)   db.setTransferOutCount(0f);
            if(db.getUpholsterOutCount()==null){
                db.setUpholsterOutCount(0f);
            }
            if(db.getUpholsterOutCostAmount()==null){
                db.setUpholsterOutCostAmount(0d);
            }
            if(db.getUpholsterOutSaleAmount()==null){
                db.setUpholsterOutSaleAmount(0d);
            }
            logger.debug(db.getStorageCode());
            logger.debug(CurDate);
//            psSelect.setString(1, db.getStorageCode());
//            psSelect.setString(2, CurDate);
            logger.debug(this.getClass() + "------->psSelect.setString over!");
//            rs = psSelect.executeQuery();
            List<Object> queryParam=new ArrayList<Object>();
            queryParam.add(db.getStorageCode());
            queryParam.add(CurDate);
            Map<String,Object> rs1=DAOUtil.findFirst(sbSelect.toString(), queryParam);
          //  System.out.println("______________________________-"+rs1.toString());
            //   int rs1=Base.exec(sbSelect.toString(),db.getStorageCode(),CurDate);
            if (rs1!=null){
            //    System.out.println("______________________________-"+sbSqlUpdate.toString());
                Base.exec(sbSqlUpdate.toString(),
              //STOCK_IN_COUNT,STOCK_IN_AMOUNT,BUY_IN_COUNT,BUY_IN_AMOUNT,ALLOCATE_IN_COUNT,          
                 Math.round(db.getInQuantity()*100)*0.01
                , Math.round(db.getStockInAmount() * 100) * 0.01
                , Math.round(db.getBuyInCount()*100)*0.01
                , Math.round(db.getBuyInAmount() * 100) * 0.01
                , Math.round(db.getAllocateInCount()*100)*0.01
                //ALLOCATE_IN_AMOUNT,OTHER_IN_COUNT,OTHER_IN_AMOUNT,PROFIT_IN_COUNT,PROFIT_IN_AMOUNT,                       
                , Math.round(db.getAllocateInAmount() * 100) * 0.01
               , Math.round(db.getOtherInCount()*100)*0.01
               , Math.round(db.getOtherInAmount() * 100) * 0.01
                , Math.round(db.getProfitInCount()*100)*0.01
                ,Math.round(db.getProfitInAmount() * 100) * 0.01
                //STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT,REPAIR_OUT_COUNT,REPAIR_OUT_COST_AMOUNT,                       
                , Math.round(db.getOutQuantity()*100)*0.01
                , Math.round(db.getStockOutCostAmount() * 100) * 0.01
                , Math.round(db.getOutAmount() * 100) * 0.01
                , Math.round(db.getRepairOutCount()*100)*0.01
                , Math.round(db.getRepairOutCostAmount() * 100) * 0.01
                //REPAIR_OUT_SALE_AMOUNT,SALE_OUT_COUNT,SALE_OUT_COST_AMOUNT,SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT,                      
                , Math.round(db.getRepairOutSaleAmount() * 100) * 0.01
                , Math.round(db.getSaleOutCount()*100)*0.01
                , Math.round(db.getSaleOutCostAmount() * 100) * 0.01
                , Math.round(db.getSaleOutSaleAmount() * 100) * 0.01
                , Math.round(db.getInnerOutCount()*100)*0.01
                //INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT,ALLOCATE_OUT_COUNT,ALLOCATE_OUT_COST_AMOUNT,ALLOCATE_OUT_SALE_AMOUNT,                     
               , Math.round(db.getInnerOutCostAmount() * 100) * 0.01
               , Math.round(db.getInnerOutSaleAmount() * 100) * 0.01
                , Math.round(db.getAllocateOutCount()*100)*0.01
               , Math.round(db.getAllocateOutCostAmount() * 100) * 0.01
                , Math.round(db.getAllocateOutSaleAmount() * 100) * 0.01

                //OTHER_OUT_COUNT,OTHER_OUT_COST_AMOUNT,OTHER_OUT_SALE_AMOUNT,LOSS_OUT_COUNT,LOSS_OUT_AMOUNT,                       
                , Math.round(db.getOtherOutCount()*100)*0.01
                , Math.round(db.getOtherOutCostAmount() * 100) * 0.01
                , Math.round(db.getOtherOutSaleAmount() * 100) * 0.01
                , Math.round(db.getLossOutCount()*100)*0.01
                , Math.round(db.getLossOutAmount() * 100) * 0.01
                , Math.round(db.getTransferInCount()*100)*0.01
                , Math.round(db.getTransferInAmount() * 100) * 0.01
                , Math.round(db.getTransferOutCount()*100)*0.01
                , Math.round(db.getTransferOutCostAmount() * 100) * 0.01

//              UPHOLSTER_OUT_COUNT
                , Math.round(db.getUpholsterOutCount()*100)*0.01
                , Math.round(db.getUpholsterOutCostAmount()*100)*0.01
                , Math.round(db.getUpholsterOutSaleAmount()* 100) * 0.01
                

                , Math.round(db.getInQuantity()*100)*0.01
                , Math.round(db.getOutQuantity()*100)*0.01
                , Math.round(db.getStockInAmount() * 100) * 0.01
                , Math.round(db.getStockOutCostAmount() * 100) * 0.01

                //UPDATE_BY,UPDATE_DATE,PART_PERIOD_REPORT_ID
//                psUpdate.setLong(42, db.getUpdateBy());
//                psUpdate.setTimestamp(43, Utility.getCurrentTimestamp());
                , dealerCode , CurDate , db.getStorageCode());
              logger.debug("db.getInQuantity()"+db.getInQuantity());
              logger.debug("db.getOutQuantity()"+db.getOutQuantity());
                logger.debug(this.getClass() + "--------->psUpdate over!");
             //   psUpdate.executeUpdate();
                logger.debug(this.getClass() + "--------->psUpdate Execute over!");
            }else{
              //  System.out.println("___________________"+sbSqlInsert.toString());
                Base.exec(sbSqlInsert.toString(),
              //InsertSQL的?的次序：
                // REPORT_YEAR, REPORT_MONTH,STOCK_IN_COUNT, STOCK_IN_AMOUNT, BUY_IN_COUNT  
                 CurDate, db.getStorageCode(), dealerCode
                , Math.round(db.getInQuantity()*100)*0.01
                , Math.round(db.getStockInAmount()*100)*0.01
                , Math.round(db.getBuyInCount()*100)*0.01
                //BUY_IN_AMOUNT,ALLOCATE_IN_COUNT, ALLOCATE_IN_AMOUNT, OTHER_IN_COUNT,OTHER_IN_AMOUNT                       
                , Math.round(db.getBuyInAmount()*100)*0.01
                , Math.round(db.getAllocateInCount()*100)*0.01
                , Math.round(db.getAllocateInAmount()*100)*0.01
                ,Math.round(db.getOtherInCount()*100)*0.01
                ,Math.round(db.getOtherInAmount()*100)*0.01
                //PROFIT_IN_COUNT, PROFIT_IN_AMOUNT,STOCK_OUT_COUNT,STOCK_OUT_COST_AMOUNT,STOCK_OUT_AMOUNT                      
                , Math.round(db.getProfitInCount()*100)*0.01
                , Math.round(db.getProfitInAmount()*100)*0.01
                , Math.round(db.getSaleOutCount()*100)*0.01
                , Math.round(db.getStockOutCostAmount()*100)*0.01
                , Math.round(db.getOutAmount()*100)*0.01
                //REPAIR_OUT_COUNT, REPAIR_OUT_COST_AMOUNT,REPAIR_OUT_SALE_AMOUNT, SALE_OUT_COUNT, SALE_OUT_COST_AMOUNT                     
                , Math.round(db.getRepairOutCount()*100)*0.01
                , Math.round(db.getRepairOutCostAmount()*100)*0.01
                , Math.round(db.getRepairOutSaleAmount()*100)*0.01
                , Math.round(db.getSaleOutCount()*100)*0.01
                , Math.round(db.getSaleOutCostAmount()*100)*0.01
                //SALE_OUT_SALE_AMOUNT,INNER_OUT_COUNT, INNER_OUT_COST_AMOUNT,INNER_OUT_SALE_AMOUNT, ALLOCATE_OUT_COUNT                     
                , Math.round(db.getSaleOutSaleAmount()*100)*0.01
                , Math.round(db.getInnerOutCount()*100)*0.01
                , Math.round(db.getInnerOutCostAmount()*100)*0.01
                , Math.round(db.getInnerOutSaleAmount()*100)*0.01
                , Math.round(db.getAllocateOutCount()*100)*0.01
                //ALLOCATE_OUT_COST_AMOUNT,ALLOCATE_OUT_SALE_AMOUNT,OTHER_OUT_COUNT,OTHER_OUT_COST_AMOUNT,OTHER_OUT_SALE_AMOUNT                         
                , Math.round(db.getAllocateOutCostAmount()*100)*0.01
                , Math.round(db.getAllocateOutSaleAmount()*100)*0.01
                , Math.round(db.getOtherOutCount()*100)*0.01
                , Math.round(db.getOtherOutCostAmount()*100)*0.01
                , Math.round(db.getOtherOutSaleAmount()*100)*0.01
                //LOSS_OUT_COUNT,LOSS_OUT_AMOUNT,CREATE_BY,CREATE_DATE, ORG_CODE                    
                , Math.round(db.getLossOutCount()*100)*0.01
                , Math.round(db.getLossOutAmount()*100)*0.01
//                psInsert.setLong(34, db.getCreateBy()
//                psInsert.setDate(35, new java.sql.Date(System.currentTimeMillis())
                , Math.round(db.getTransferInCount()*100)*0.01
               , Math.round(db.getTransferInAmount()*100)*0.01
                , Math.round(db.getTransferOutCount()*100)*0.01
                , Math.round(db.getTransferOutCostAmount()*100)*0.01
                //UPHOLSTER_OUT_COUNT add by jll 2011-09-09
                , Math.round(db.getUpholsterOutCount()*100)*0.01
                , Math.round(db.getUpholsterOutCostAmount() * 100) * 0.01
                , Math.round(db.getUpholsterOutSaleAmount()*100)*0.01
                , db.getStorageCode()  , dealerCode , db.getStorageCode()
                , dealerCode  , db.getStorageCode() , dealerCode
//              psInsert.setDouble(46, db.getInQuantity());
//              psInsert.setDouble(47, db.getOutQuantity());
                , Math.round(db.getOutQuantity()*100)*0.01
                , Math.round(db.getInQuantity()*100)*0.01
                , db.getStorageCode() , dealerCode
//              psInsert.setDouble(50, db.getStockInAmount());
//              psInsert.setDouble(51, db.getStockOutCostAmount());
                , Math.round(db.getStockOutCostAmount()*100)*0.01
                , Math.round(db.getStockInAmount()*100)*0.01);
                logger.debug(this.getClass() + "------->psInsert set over!");
                logger.debug("db.getDealerId()--->" + dealerCode);
               // psInsert.executeUpdate();
                logger.debug(this.getClass() + "------->psInsert Execute over!");
            }
        }
        logger.debug(this.getClass() + "createOrUpdate over!");
        logger.debug(this.getClass() + "createOrUpdateDaily over!");
    }
    /**
     * 
    * TODO description
    * @author yujiangheng
    * @date 2017年5月8日
    * @param dealerCode
    * @return
     */
    private Map<String,Object> getAccountCyclePO(String dealerCode) {
        StringBuilder sql=new StringBuilder("SELECT B_YEAR,PERIODS,BEGIN_DATE,END_DATE,IS_EXECUTED,DEALER_CODE ");
        sql.append( " FROM TM_ACCOUNTING_CYCLE WHERE  DEALER_CODE = '"+dealerCode+"'  ");
        sql.append(  " AND now()  BETWEEN BEGIN_DATE AND END_DATE");
        Map<String,Object> result=DAOUtil.findFirst(sql.toString(), null);
        return result;
    }
    /**
     * 
    * TODO description
    * @author yujiangheng
    * @date 2017年5月6日
    * @param db
    * @param dealerCode
     */
    private void createOrUpdate(TtPartMonthReportDTO db, String dealerCode) {
        StringBuffer sbSql = new StringBuffer();
        StringBuffer sbSqlCreate = new StringBuffer();
        StringBuffer sbSqlUpdate = new StringBuffer();
        String year = Utility.getYear();
        String month = Utility.getMonth();
        sbSql.append("SELECT DEALER_CODE FROM TT_PART_MONTH_REPORT ");
        sbSql.append(" WHERE  DEALER_CODE = '" + dealerCode);
        sbSql.append("'  AND STORAGE_CODE=? AND PART_BATCH_NO=? AND PART_NO=? AND D_KEY = ");
        sbSql.append( CommonConstants.D_KEY + " AND REPORT_YEAR='" + year + "' AND REPORT_MONTH= '");
        sbSql.append( month + "' FOR UPDATE ");

        sbSqlCreate.append(" INSERT INTO TT_PART_MONTH_REPORT");
        sbSqlCreate.append(" (REPORT_YEAR, REPORT_MONTH,");
        sbSqlCreate.append(" STORAGE_CODE,PART_BATCH_NO, PART_NO, PART_NAME, ");
        sbSqlCreate.append( " IN_QUANTITY, STOCK_IN_AMOUNT,");
        sbSqlCreate.append(" INVENTORY_QUANTITY, INVENTORY_AMOUNT, OUT_QUANTITY,");
        sbSqlCreate.append( " OUT_AMOUNT,  DEALER_CODE,");
        sbSqlCreate.append(" OPEN_QUANTITY, OPEN_PRICE, OPEN_AMOUNT, CLOSE_QUANTITY,CLOSE_PRICE,CLOSE_AMOUNT");
        sbSqlCreate.append(" )" + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?, ?, ?, ?, ?, ? )");

        sbSqlUpdate.append(" UPDATE TT_PART_MONTH_REPORT");
        sbSqlUpdate.append(" SET IN_QUANTITY = CASE  WHEN IN_QUANTITY IS NULL THEN 0  ELSE IN_QUANTITY END + ?,");
        sbSqlUpdate.append(" STOCK_IN_AMOUNT = CASE WHEN STOCK_IN_AMOUNT IS NULL THEN 0 ELSE STOCK_IN_AMOUNT  END + ?,");
        sbSqlUpdate.append(" INVENTORY_QUANTITY = CASE  WHEN INVENTORY_QUANTITY IS NULL  THEN 0 ELSE INVENTORY_QUANTITY END + ?,");
        sbSqlUpdate.append( " INVENTORY_AMOUNT = CASE WHEN INVENTORY_AMOUNT IS NULL THEN 0 ELSE INVENTORY_AMOUNT END + ?,");
        sbSqlUpdate.append( " OUT_QUANTITY =  CASE  WHEN OUT_QUANTITY IS NULL  THEN 0  ELSE OUT_QUANTITY END + ?,");
        sbSqlUpdate.append( " OUT_AMOUNT =  CASE WHEN OUT_AMOUNT IS NULL THEN 0 ELSE OUT_AMOUNT  END + ?,");
        sbSqlUpdate.append(" CLOSE_QUANTITY =  CASE WHEN CLOSE_QUANTITY IS NULL THEN 0 ELSE CLOSE_QUANTITY  END + ?-?,");
        sbSqlUpdate.append( " CLOSE_PRICE = ?,");
        sbSqlUpdate.append(" CLOSE_AMOUNT =  CASE WHEN CLOSE_AMOUNT IS NULL THEN 0 ELSE CLOSE_AMOUNT  END + ?-? ");
        sbSqlUpdate.append(  " WHERE DEALER_CODE = ? ");
        sbSqlUpdate.append( " AND REPORT_YEAR = ?" + " AND REPORT_MONTH = ?");
        sbSqlUpdate.append( " AND STORAGE_CODE = ?" + " AND PART_BATCH_NO = ?");
        sbSqlUpdate.append(" AND PART_NO = ?" + " AND D_KEY = " + CommonConstants.D_KEY);
      
       
        if (db != null) {
            if (db.getInQuantity() == null) db.setInQuantity(0f);
            if (db.getStockInAmount() == null) db.setStockInAmount(0d);
            if (db.getInventoryQuantity() == null) db.setInventoryQuantity(0f);
            if (db.getInventoryAmount() == null) db.setInventoryAmount(0d);
            if (db.getOutQuantity() == null) db.setOutQuantity(0f);
            if (db.getOutAmount() == null)  db.setOutAmount(0d);
            if (db.getOpenAmount() == null) db.setOpenAmount(0d);
            if (db.getOpenPrice() == null)    db.setOpenPrice(0d);
            if (db.getOpenQuantity() == null)     db.setOpenQuantity(0f);
            if (db.getCloseAmount() == null)       db.setCloseAmount(0d);
            if (db.getClosePrice() == null)    db.setClosePrice(0d);
            if (db.getCloseQuantity() == null)     db.setCloseQuantity(0f);
            //sbSql的？次序STORAGE_CODE,PART_NO 第一条sql执行
            List<Object> queryParam=new ArrayList<Object>();
            queryParam.add(db.getStorageCode().trim());
            queryParam.add(db.getPartBatchNo());
            queryParam.add(db.getPartNo());
            Map<String,Object>rs1=DAOUtil.findFirst(sbSql.toString(), queryParam);
//            int rs1=Base.exec(sbSql.toString(), ,,);
//            logger.debug(rs1+"");
            /* 检测执行结果
            logger.debug(sbSqlUpdate.toString());
            logger.debug(sbSqlCreate.toString());
            */
            if (rs1!=null){
                //sbSqlUpdate的？次序
                Base.exec(sbSqlUpdate.toString(), 
                Math.round(db.getInQuantity() * 100) * 0.01, Math.round(db.getStockInAmount() * 100) * 0.01
              , Math.round(db.getInventoryQuantity() * 100) * 0.01
              , Math.round(db.getInventoryAmount() * 100) * 0.01
              , Math.round(db.getOutQuantity() * 100) * 0.01
               , Math.round(db.getOutAmount() * 100) * 0.01
               , Math.round(db.getInQuantity() * 100) * 0.01
               , Math.round(db.getOutQuantity() * 100) * 0.01
                , Math.round(db.getClosePrice() * 10000) * 0.0001
                , Math.round(db.getStockInAmount() * 100) * 0.01
                , Math.round(db.getOutAmount() * 100) * 0.01
                , dealerCode
                ,year,month, db.getStorageCode(), db.getPartBatchNo(), db.getPartNo());
                logger.debug("db.getClosePrice():-------------------------------" + db.getClosePrice());
                logger.debug("Math.rounddb.getClosePrice():-------------------------------" + Math.round(db.getClosePrice() * 10000) * 0.0001);
                logger.debug("----------------->psUpdate set over!");
                logger.debug("----------------->psUpdate execute over!");
            }
            else{
                //sbSqlCreate的？次序
                TtPartMonthReportDTO mrpo = new TtPartMonthReportDTO();
                Base.exec(sbSqlCreate.toString(), year, month, db.getStorageCode(), db.getPartBatchNo() ,
                 db.getPartNo()  , db.getPartName(), Math.round(db.getInQuantity() * 100) * 0.01
                , Math.round(db.getStockInAmount() * 100) * 0.01, Math.round(db.getInventoryQuantity() * 100) * 0.01
                , Math.round(db.getInventoryAmount() * 100) * 0.01  , Math.round(db.getOutQuantity() * 100) * 0.01
               , Math.round(db.getOutAmount() * 100) * 0.01 , dealerCode , Math.round(db.getOpenQuantity() * 100) * 0.01
                , Math.round(db.getOpenPrice() * 10000) * 0.0001 , Math.round(db.getOpenAmount() * 100) * 0.01
                , getSubF(getAddF(db.getOpenQuantity(), db.getInQuantity()), db.getOutQuantity())
                , Math.round(db.getClosePrice() * 10000) * 0.0001
                , getSubD(getAddD(db.getOpenAmount(), db .getStockInAmount()), db.getOutAmount()));                 
                logger.debug("----------------->psCreate set over!");
                logger.debug("----------------->psCreate execute over!");
            }
        }
    }
    
    //一下四个静态方法为 方法createOrUpdate所使用
    private static Double getAddD(Double v1, Double v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 != null)
            s1 = v1.toString();
        if (v2 != null)
            s2 = v2.toString();
        return Utility.round(new Double(Utility.add(s1, s2)).toString(), 2);
    }

    private static Double getSubD(Double v1, Double v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 != null)
            s1 = v1.toString();
        if (v2 != null)
            s2 = v2.toString();
        return Utility.round(new Double(Utility.sub(s1, s2)).toString(), 2);
    }

    private static Double getAddF(Float v1, Float v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 != null)
            s1 = v1.toString();
        if (v2 != null)
            s2 = v2.toString();
        double d = Utility.round(new Double(Utility.add(s1, s2)).toString(), 2);
        return d;
    }

    private static Double getSubF(Double v1, Float v2) {
        String s1 = "0";
        String s2 = "0";
        if (v1 != null)
            s1 = v1.toString();
        if (v2 != null)
            s2 = v2.toString();
        double d = Utility.round(new Double(Utility.sub(s1, s2)).toString(), 2);
        return d;
    }
    
    
    
    
    
    /**
     * 设置PartFlowPO 对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年5月6日
    * @param partFlowPO
    * @param flow
     */
    private void setPartFlowPO(PartFlowPO partFlowPO, TtPartFlowDTO flow) {
        partFlowPO.setString("CUSTOMER_CODE",flow.getCustomerCode());
        partFlowPO.setString("CUSTOMER_NAME",flow.getCustomerName());
        partFlowPO.setString("PART_NO",flow.getPartNo());
        partFlowPO.setString("STORAGE_CODE",flow.getStorageCode());
        partFlowPO.setString("SHEET_NO",flow.getSheetNo());
        partFlowPO.setString("PART_NAME",flow.getPartName());
        partFlowPO.setString("LICENSE",flow.getLicense());
        partFlowPO.setString("DEALER_CODE",flow.getDealerCode());
        partFlowPO.setString("OPERATOR",flow.getOperator());
        partFlowPO.setString("PART_BATCH_NO",flow.getPartBatchNo());
        partFlowPO.setFloat("STOCK_QUANTITY", flow.getStockQuantity());
        partFlowPO.setFloat("STOCK_IN_QUANTITY", flow.getStockInQuantity());
        partFlowPO.setDouble("COST_AMOUNT_BEFORE_A", flow.getCostAmountBeforeA());
        partFlowPO.setDouble("COST_AMOUNT_BEFORE_B", flow.getCostAmountBeforeB());
        partFlowPO.setDouble("COST_AMOUNT_AFTER_A", flow.getCostAmountAfterA());
        partFlowPO.setDouble("COST_AMOUNT_AFTER_B", flow.getCostAmountAfterB());
        partFlowPO.setDouble("IN_OUT_TAXED_PRICE", flow.getInOutTaxedPrice());
        partFlowPO.setDouble("IN_OUT_TAXED_AMOUNT", flow.getInOutTaxedAmount());
        partFlowPO.setDouble("IN_OUT_NET_PRICE", flow.getInOutNetPrice());
        partFlowPO.setDouble("IN_OUT_NET_AMOUNT", flow.getInOutNetAmount());
        partFlowPO.setDouble("COST_PRICE", flow.getCostPrice());
        partFlowPO.setDouble("COST_AMOUNT", flow.getCostAmount());
        partFlowPO.setDate("OPERATE_DATE", flow.getOperateDate());
        partFlowPO.setLong("FLOW_ID", flow.getFlowId());
        partFlowPO.setInteger("IN_OUT_TAG", flow.getInOutTag());
        partFlowPO.setInteger("IN_OUT_TYPE", flow.getInOutType());
    }
    /**
     * @discription更新配件库存数量成本单价成本金额(调拨入库，配件报溢)
     * @author qmb
     * @date Nov 26, 2007 9:00:00 AM
     * @param entityCode
     * @param stockItemPO
     * @param conn
     * @throws Exception 
     */
    private void calCostPriceOut( String dealerCode, TmPartStockItemDTO stockItemPO)  throws Exception {
        String defaultValue = Utility.getDefaultValue("2035");// 是否使用批次
        StringBuffer sql = new StringBuffer("");
        StringBuffer sqlItem = new StringBuffer("");
        double rate = Utility.add("1", Utility.getDefaultValue("2034"));
        logger.debug("税率:" + rate);
        /*
UPDATE TM_PART_STOCK SET STOCK_QUANTITY =  IFNULL(STOCK_QUANTITY, 0)  + 1.0 ,   COST_PRICE = CASE 
 WHEN ( IFNULL(STOCK_QUANTITY, 0)  + 1.0 ) > 0 AND ( IFNULL(COST_AMOUNT, 0)  +  123.0 )>= 0  
    THEN ROUND(( IFNULL(COST_AMOUNT, 0)  +  123.0 ) /(  IFNULL(STOCK_QUANTITY, 0)  + 1.0 ),4)
     ELSE (CASE WHEN  IFNULL(LATEST_PRICE, 0) =0    THEN  COST_PRICE 
        -- (SELECT COST_PRICE FROM TM_PART_STOCK WHERE PART_NO='04861746AB' AND STORAGE_CODE='CKB2' AND D_KEY=0 AND DEALER_CODE='2100000') 
        ELSE LATEST_PRICE/1.17 END) END ,  
 COST_AMOUNT =  IFNULL(COST_AMOUNT, 0)  +  123.0  
 WHERE DEALER_CODE = '2100000'  AND PART_NO = '04861746AB'  AND STORAGE_CODE = 'CKB2'  AND D_KEY = 0
         */
        if (defaultValue.equals(CommonConstants.DICT_IS_NO)) { //不使用批次的时候
            sql.append(" UPDATE TM_PART_STOCK SET STOCK_QUANTITY = " +Utility.getChangeNull("", "STOCK_QUANTITY", 0));
            sql.append(" + "+ stockItemPO.getStockQuantity() + " ," + " COST_PRICE = CASE WHEN (" +Utility.getChangeNull("", "STOCK_QUANTITY", 0)+  " + ");
            sql.append( stockItemPO.getStockQuantity() + " ) > 0 AND (" + Utility.getChangeNull("", "COST_AMOUNT", 0)+  " + "  + " ");
            sql.append( stockItemPO.getCostAmount() + " )>= 0  THEN round((" +  Utility.getChangeNull("", "COST_AMOUNT", 0));
            sql.append(" +  "  + stockItemPO.getCostAmount() + " )"  + " /( " +Utility.getChangeNull("", "STOCK_QUANTITY", 0));
            sql.append(    " + "+ stockItemPO.getStockQuantity()+ " ),4)  ");
            sql.append(    " ELSE (CASE WHEN " + Utility.getChangeNull("", "LATEST_PRICE", 0)+ "=0   ");
            sql.append( "THEN  COST_PRICE   ELSE LATEST_PRICE/"+rate+"  END)END , "  + " COST_AMOUNT = " );
            sql.append(  Utility.getChangeNull("", "COST_AMOUNT", 0)+ " +  " + stockItemPO.getCostAmount() + " ");
            
            sqlItem.append(" UPDATE TM_PART_STOCK_ITEM SET STOCK_QUANTITY = " +Utility.getChangeNull("", "STOCK_QUANTITY", 0));
            sqlItem.append( " + " + stockItemPO.getStockQuantity() + " ,"+ " COST_PRICE = CASE WHEN (" +Utility.getChangeNull("", "STOCK_QUANTITY", 0));
            sqlItem.append(  " + " + stockItemPO.getStockQuantity()+ " ) > 0 AND (" +Utility.getChangeNull("", "COST_AMOUNT", 0));
            sqlItem.append(  " + " + " "+ stockItemPO.getCostAmount() + " )>= 0  THEN round((" +Utility.getChangeNull("", "COST_AMOUNT", 0));
            sqlItem.append(" +  "+ stockItemPO.getCostAmount()+ " )" + " /( " +Utility.getChangeNull("", "STOCK_QUANTITY", 0)+ " + ");
            sqlItem.append( stockItemPO.getStockQuantity() + " ),4) ELSE (CASE WHEN " +Utility.getChangeNull("", "LATEST_PRICE", 0));
            sqlItem.append( "=0 THEN (SELECT COST_PRICE FROM TM_PART_STOCK WHERE PART_NO='" + stockItemPO.getPartNo()  + "' AND STORAGE_CODE='");
            sqlItem.append( stockItemPO.getStorageCode() + "' AND D_KEY="   + CommonConstants.D_KEY  + " AND DEALER_CODE='" + dealerCode);
            sqlItem.append( "') ELSE LATEST_PRICE/"+rate+" END) END , "+ " COST_AMOUNT = " +Utility.getChangeNull("", "COST_AMOUNT", 0));
            sqlItem.append(" +  " + stockItemPO.getCostAmount() + " ");
        }
        else
        { //使用批次的情况下
            sql.append(" UPDATE TM_PART_STOCK SET STOCK_QUANTITY = " +Utility.getChangeNull("", "STOCK_QUANTITY", 0));
            sql.append(" + " + stockItemPO.getStockQuantity() + " ," + " COST_PRICE = LATEST_PRICE , " + " COST_AMOUNT = " );
            sql.append( Utility.getChangeNull("", "COST_AMOUNT", 0)+" -  " + stockItemPO.getCostAmount() + " ");
            
            sqlItem.append(" UPDATE TM_PART_STOCK_ITEM SET STOCK_QUANTITY = " + Utility.getChangeNull("", "STOCK_QUANTITY", 0));
            sqlItem.append(  " + "+ stockItemPO.getStockQuantity()  + " ," + " COST_PRICE = LATEST_PRICE , "  + " COST_AMOUNT = " );
            sqlItem.append( Utility.getChangeNull("", "COST_AMOUNT", 0)+" -  "   + stockItemPO.getCostAmount() + " ");
        }
        sql.append(" WHERE DEALER_CODE = '" + dealerCode + "'  AND PART_NO = '");
        sql.append(stockItemPO.getPartNo() + "' " + " AND STORAGE_CODE = '");
        sql.append( stockItemPO.getStorageCode() + "' " + " AND D_KEY = " + CommonConstants.D_KEY);

        sqlItem.append(" WHERE DEALER_CODE = '" + dealerCode + "'  AND PART_NO = '");
        sqlItem.append( stockItemPO.getPartNo() + "' " + " AND STORAGE_CODE = '");
        sqlItem.append(stockItemPO.getStorageCode() + "' " + " AND PART_BATCH_NO = '");
        sqlItem.append(stockItemPO.getPartBatchNo() + "' " + " AND D_KEY = " + CommonConstants.D_KEY);
        System.out.println(sql.toString());
        Base.exec(sql.toString());
        Base.exec(sqlItem.toString());
//            pg = conn.prepareStatement(sql.toString());
//            ps = conn.prepareStatement(sqlItem.toString());
            logger.debug("sql= " + sql);
            logger.debug("sqlItem= " + sqlItem);
            //记录方法被执行的次数
//            CommonSqlMethod.methodPofactory("TmPartStockItemPOFactory", "calCostPriceOut", sql.toString());
//            CommonSqlMethod.methodPofactory("TmPartStockItemPOFactory", "calCostPriceOut", sqlItem.toString());
//            pg.executeUpdate();
//            ps.execute();

    }
    
    
    /**
     * 功能描述：查询业务表 sheetTable 关联Tm_part_info 
    * TODO description
    * @author yujiangheng
    * @date 2017年5月6日
    * @param dealerCode
    * @param string
    * @param string2
    * @param allocateInNo
     */
    private List getNonOemPartList(String dealerCode, String sheetTable, String sheetName, String allocateInNo) {
        if (!Utility.getDefaultValue(CommonConstants.DEFAULT_PARA_OEM_PART_IN_CHECK+"").equals(CommonConstants.DICT_IS_YES)) {
            return null;
        }
        StringBuilder sql = new StringBuilder(" select A.PART_NO,A.PART_NAME,A.STORAGE_CODE from  "+sheetTable+" A " );
        sql.append( " left join TM_PART_INFO B on (A.part_NO = B.PART_NO AND A.DEALER_CODE = B.DEALER_CODE) " );     
        sql.append( " WHERE A.DEALER_CODE = '"+dealerCode+"' " +  " AND B.DOWN_TAG =  "+CommonConstants.DICT_IS_NO+" ");        
        sql.append(   " AND A.STORAGE_CODE ='OEMK'"+   " AND A."+sheetName+" = '"+allocateInNo+"'");  
        return DAOUtil.findAll(sql.toString(), null);
    }
    /**
     * 
    * TODO description
    * @author yujiangheng
    * @date 2017年5月5日
     */
    private void QueryMonthPeriodIsFinished() {
        
        //查询本月的报表是否完成
     //   List<Map> partMonth=Utility.isFinishedThisMonth();
        if (Utility.isFinishedThisMonth()==null) {
          throw new ServiceBizException("当前配件月报没有正确执行!");
        }   
        //查询当前时间的会计周期是否做过月结
        if (Utility.getIsFinished()==null) {
            throw new ServiceBizException("当前配件会计月报没有正确执行!");
          }   
    }
    /**
     * 查询配件车型组
    * @author yujiangheng
    * @date 2017年5月9日
    * @return
    * @throws ServiceBizException
    * @throws Exception
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#findPartModelGroup()
     */
    @Override
    public PageInfoDto findPartModelGroup() throws ServiceBizException, Exception {
        StringBuffer sb = new StringBuffer("SELECT DEALER_CODE,PART_MODEL_GROUP_CODE,PART_MODEL_GROUP_NAME ");
        sb.append( "FROM TM_PART_MODEL_GROUP");
        return DAOUtil.pageQuery(sb.toString(), null);
    }
    /**
     * 维护配件库存
    * @author yujiangheng
    * @date 2017年5月10日
    * @param partInfoDTO
    * @return
    * @throws Exception
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.stockmanage.PartAllocateInService#addPartStock(java.util.Map)
     */
    @Override
    public void addPartStock(Map partInfoDTO) throws Exception,ServiceBizException {
        TmPartInfoPO partInfo = new TmPartInfoPO();
        TmPartStockPO partstock = new TmPartStockPO();
        partstock=this.setPartStocksPO(partstock,partInfo, partInfoDTO);
        StringBuilder sb= new StringBuilder("select * from tm_part_stock where PART_NO=? and STORAGE_CODE=?");
        List<String> params = new ArrayList<String>();
        params.add(partstock.getString("PART_NO"));
        params.add(partstock.getString("STORAGE_CODE"));
        List<Map> list = DAOUtil.findAll(sb.toString(), params);
        if(!CommonUtils.isNullOrEmpty(list)){
            throw new ServiceBizException("配件代码已存在,不能重复！");
        }
        if (!StringUtils.isNullOrEmpty(partstock.getString("DOWN_TAG"))) {
            if ((partstock.getString("DOWN_TAG")).equals("OEMK")) {
                StringBuilder sqlsb= new StringBuilder("select * from tm_part_info where PART_NO=? and DOWN_TAG=12781001");
                List<String> paramss = new ArrayList<String>();
                params.add(partstock.getString("PART_NO"));
                List<Map> lists = DAOUtil.findAll(sqlsb.toString(), paramss);
                if(CommonUtils.isNullOrEmpty(lists)){
                    throw new ServiceBizException("非OEM配件不允许入OEM仓库!");
                }
            }
        }
        partstock.saveIt();
        if (Utility.getDefaultValue("2035") .equals(CommonConstants.DICT_IS_NO)) {// 如果不使用批次
            StringBuilder sb1= new StringBuilder("select * from tm_part_stock_item where PART_NO=? and STORAGE_CODE=? and D_Key=?");
            List<String> param = new ArrayList<String>();
            param.add(partstock.getString("PART_NO"));
            param.add(partstock.getString("STORAGE_CODE"));
            param.add(CommonConstants.DEFAULT_PARA_PART_DELETE_KEY+"");
            Map<String,Object> list1 = DAOUtil.findFirst(sb1.toString(), param);
            if (list1 != null&& list1.size() > 0) {
                logger.debug("更新配件批次表 , 原来存在删除的数据 :");
                TmPartStockItemPO tmPartStockItem=TmPartStockItemPO.
                        findByCompositeKeys(list1.get("DEALER_CODE"),list1.get("PART_NO"),list1.get("STORAGE_CODE"),list1.get("PART_BATCH_NO"));               
                setTmPartStockItem(tmPartStockItem,partInfoDTO);
                tmPartStockItem.saveIt();
            } else {
                TmPartStockItemPO tmPartStockItemPO=new TmPartStockItemPO();
                setTmPartStockItemPO(tmPartStockItemPO,partInfoDTO);
                tmPartStockItemPO.saveIt();
            }
        }
    }
    /**
     * 新增时设置TmPartStockItemPO 对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年5月9日
    * @param tmPartStockItemPO
    * @param list1
     * @throws Exception 
     */
    private void setTmPartStockItemPO(TmPartStockItemPO stockItem, Map list1) throws Exception {
        System.out.println("______________________"+list1.toString());
        /**
         * 
{PART_NO=5665, PART_NAME=电脑, SPELL_CODE=DN, MIN_STOCK=0, MAX_STOCK=0, SAFE_STOCK=0, INSTRUCT_PRICE=1698.91,
 CLAIM_PRICE=1433.36, MIN_PACKAGE=0,LIMIT_PRICE=1689.91, SALES_PRICE=1698.91, ADD_RATE=0, INSURANCE_PRICE=0,
 STORAGE_CODE=CKA6, PART_GROUP_CODE=11361002, UNIT_CODE=1001, PART_MAIN_TYPE=13661002}

         */
        stockItem.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
        stockItem.setString("PART_BATCH_NO",CommonConstants.defaultBatchName);
        if(!StringUtils.isNullOrEmpty(list1.get("PART_NO"))){
            stockItem.setString("PART_NO",list1.get("PART_NO")+"");
        }
        if(!StringUtils.isNullOrEmpty(list1.get("STORAGE_CODE"))){
            stockItem.setString("STORAGE_CODE",list1.get("STORAGE_CODE")+"");
        }
        
        if(!StringUtils.isNullOrEmpty(list1.get("BORROW_QUANTITY"))){
            stockItem.setFloat("BORROW_QUANTITY",Utility.getFloat(list1.get("BORROW_QUANTITY")+""));
        }
        if(!StringUtils.isNullOrEmpty(list1.get("CLAIM_PRICE"))){
            stockItem.setDouble("CLAIM_PRICE",Utility.getDouble(list1.get("CLAIM_PRICE")+""));
        }
        if(!StringUtils.isNullOrEmpty(list1.get("COST_PRICE"))){
            stockItem.setDouble("COST_PRICE",Utility .getDouble(list1.get("COST_PRICE")+""));
        }
        if(!StringUtils.isNullOrEmpty(list1.get("COST_AMOUNT"))){
            stockItem.setDouble("COST_AMOUNT",Utility .getDouble(list1.get("COST_AMOUNT")+""));
        }
       
        if(!StringUtils.isNullOrEmpty(list1.get("LAST_PRICE"))){
            stockItem.setDouble("LAST_PRICE",Utility.getDouble(list1.get("LAST_PRICE")+""));
        }
        stockItem.setDate("LAST_STOCK_IN",new Date(System .currentTimeMillis()));
        if(!StringUtils.isNullOrEmpty(list1.get("LEND_QUANTITY"))){
            stockItem.setFloat("LEND_QUANTITY",Utility.getFloat(list1.get("LEND_QUANTITY")+""));
        }
        if(!StringUtils.isNullOrEmpty(list1.get("LIMIT_PRICE"))){
            stockItem.setDouble("LIMIT_PRICE",Utility.getDouble(list1.get("LIMIT_PRICE")+""));
        }
        if(!StringUtils.isNullOrEmpty(list1.get("PART_GROUP_CODE"))){
            stockItem.setInteger("PART_GROUP_CODE",Utility .getInt(list1.get("PART_GROUP_CODE")+""));
        }  
        if(!StringUtils.isNullOrEmpty(list1.get("PART_NAME"))){
            stockItem.setString("PART_NAME",list1.get("PART_NAME")+"");
        }
        
        
        if(!StringUtils.isNullOrEmpty(list1.get("REMARK"))){
            stockItem.setString("REMARK",list1.get("REMARK")+"");
        }
        if(!StringUtils.isNullOrEmpty(list1.get("SALES_PRICE"))){
            stockItem.setDouble("SALES_PRICE",Utility.getDouble(list1.get("SALES_PRICE")+""));
        }
        if(!StringUtils.isNullOrEmpty(list1.get("SPELL_CODE"))){
            stockItem.setString("SPELL_CODE",list1.get("SPELL_CODE")+"");
        }
        if(!StringUtils.isNullOrEmpty(list1.get("STOCK_QUANTITY"))){
            stockItem.setFloat("STOCK_QUANTITY",Utility.getFloat(list1.get("STOCK_QUANTITY")+""));
        }
       
        if(!StringUtils.isNullOrEmpty(list1.get("STORAGE_POSITION_CODE"))){
            stockItem.setString("STORAGE_POSITION_CODE",list1.get("STORAGE_POSITION_CODE")+"");
        }
        if(!StringUtils.isNullOrEmpty(list1.get("UNIT_CODE"))){
            stockItem.setString("UNIT_CODE",list1.get("UNIT_CODE")+"");
        }
        if(!StringUtils.isNullOrEmpty(list1.get("PART_STATUS"))){
            stockItem.setInteger("PART_STATUS",Utility .getInt(list1.get("PART_STATUS")+""));
        }
        if(!StringUtils.isNullOrEmpty(list1.get("INSURANCE_PRICE"))){
            stockItem.setDouble("INSURANCE_PRICE",Utility.getDouble(list1.get("INSURANCE_PRICE")+""));
        }
    }
    /**
     * 修改时时设置TmPartStockItemPO 对象属性
    * TODO description
    * @author yujiangheng
    * @date 2017年5月9日
    * @param tmPartStockItem
    * @param list1
     * @throws Exception 
     */
    private void setTmPartStockItem(TmPartStockItemPO stockItem, Map list1) throws Exception {

        stockItem.setString("PART_BATCH_NO",CommonConstants.defaultBatchName);
        //   System.out.println(list1.get("BORROW_QUANTITY"));
           if(!StringUtils.isNullOrEmpty(list1.get("BORROW_QUANTITY"))){
               stockItem.setFloat("BORROW_QUANTITY",Utility.getFloat(list1.get("BORROW_QUANTITY")+""));
           }
           if(!StringUtils.isNullOrEmpty(list1.get("CLAIM_PRICE"))){
               stockItem.setDouble("CLAIM_PRICE",Utility.getDouble(list1.get("CLAIM_PRICE")+""));
           }
           if(!StringUtils.isNullOrEmpty(list1.get("COST_PRICE"))){
               stockItem.setDouble("COST_PRICE",Utility .getDouble(list1.get("COST_PRICE")+""));
           }
           if(!StringUtils.isNullOrEmpty(list1.get("COST_AMOUNT"))){
               stockItem.setDouble("COST_AMOUNT",Utility .getDouble(list1.get("COST_AMOUNT")+""));
           }
           if(!StringUtils.isNullOrEmpty(list1.get("BORROW_QUANTITY"))){
               stockItem.setString("DEALER_CODE",list1.get("DEALER_CODE")+"");
           }else{
               stockItem.setString("DEALER_CODE",FrameworkUtil.getLoginInfo().getDealerCode());
           }
           if(!StringUtils.isNullOrEmpty(list1.get("LAST_PRICE"))){
               stockItem.setDouble("LAST_PRICE",Utility.getDouble(list1.get("LAST_PRICE")+""));
           }
           stockItem.setDate("LAST_STOCK_IN",new Date(System .currentTimeMillis()));
           if(!StringUtils.isNullOrEmpty(list1.get("LEND_QUANTITY"))){
               stockItem.setFloat("LEND_QUANTITY",Utility.getFloat(list1.get("LEND_QUANTITY")+""));
           }
           if(!StringUtils.isNullOrEmpty(list1.get("LIMIT_PRICE"))){
               stockItem.setDouble("LIMIT_PRICE",Utility.getDouble(list1.get("LIMIT_PRICE")+""));
           }
           if(!StringUtils.isNullOrEmpty(list1.get("PART_GROUP_CODE"))){
               stockItem.setInteger("PART_GROUP_CODE",Utility .getInt(list1.get("PART_GROUP_CODE")+""));
           }  
           if(!StringUtils.isNullOrEmpty(list1.get("PART_NAME"))){
               stockItem.setString("PART_NAME",list1.get("PART_NAME")+"");
           }
           
           if(!StringUtils.isNullOrEmpty(list1.get("PART_NO"))){
               stockItem.setString("PART_NO",list1.get("PART_NO")+"");
           }
           if(!StringUtils.isNullOrEmpty(list1.get("REMARK"))){
               stockItem.setString("REMARK",list1.get("REMARK")+"");
           }
           if(!StringUtils.isNullOrEmpty(list1.get("SALES_PRICE"))){
               stockItem.setDouble("SALES_PRICE",Utility.getDouble(list1.get("SALES_PRICE")+""));
           }
           if(!StringUtils.isNullOrEmpty(list1.get("SPELL_CODE"))){
               stockItem.setString("SPELL_CODE",list1.get("SPELL_CODE")+"");
           }
           if(!StringUtils.isNullOrEmpty(list1.get("STOCK_QUANTITY"))){
               stockItem.setFloat("STOCK_QUANTITY",Utility.getFloat(list1.get("STOCK_QUANTITY")+""));
           }
           if(!StringUtils.isNullOrEmpty(list1.get("STORAGE_CODE"))){
               stockItem.setString("STORAGE_CODE",list1.get("STORAGE_CODE")+"");
           }
           if(!StringUtils.isNullOrEmpty(list1.get("STORAGE_POSITION_CODE"))){
               stockItem.setString("STORAGE_POSITION_CODE",list1.get("STORAGE_POSITION_CODE")+"");
           }
           if(!StringUtils.isNullOrEmpty(list1.get("UNIT_CODE"))){
               stockItem.setString("UNIT_CODE",list1.get("UNIT_CODE")+"");
           }
           if(!StringUtils.isNullOrEmpty(list1.get("PART_STATUS"))){
               stockItem.setInteger("PART_STATUS",Utility .getInt(list1.get("PART_STATUS")+""));
           }
           if(!StringUtils.isNullOrEmpty(list1.get("INSURANCE_PRICE"))){
               stockItem.setDouble("INSURANCE_PRICE",Utility.getDouble(list1.get("INSURANCE_PRICE")+""));
           }
    }
    /**
     * 设置属性
     * @author dingchaoyu
     * @date 2016年8月25日
     * @param partStock
     * @param partStockDTO
     * @return
     * @throws ServiceBizException
     */
    private TmPartStockPO setPartStocksPO(TmPartStockPO partStock,TmPartInfoPO partinfo,Map partStockDTO)throws ServiceBizException{
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("STORAGE_CODE"))){
            partStock.setString("STORAGE_CODE", partStockDTO.get("STORAGE_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("STORAGE_POSITION_CODE"))){
            partStock.setString("STORAGE_POSITION_CODE", partStockDTO.get("STORAGE_POSITION_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_NO"))){
            partStock.setString("PART_NO", partStockDTO.get("PART_NO"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_NAME"))){
            partStock.setString("PART_NAME", partStockDTO.get("PART_NAME"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("SPELL_CODE"))){
            partStock.setString("SPELL_CODE", partStockDTO.get("SPELL_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("INSTRUCT_PRICE"))){
            partinfo.setDouble("INSTRUCT_PRICE", partStockDTO.get("INSTRUCT_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("CLAIM_PRICE"))){
            partStock.setDouble("CLAIM_PRICE", partStockDTO.get("CLAIM_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MAX_STOCK"))){
            partStock.setDouble("MAX_STOCK", partStockDTO.get("MAX_STOCK"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("SAFE_STOCK"))){
            partStock.setDouble("SAFE_STOCK", partStockDTO.get("SAFE_STOCK"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("LATEST_PRICE"))){
            partStock.setDouble("LATEST_PRICE", partStockDTO.get("LATEST_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PRODUCTING_AREA"))){
            partinfo.setString("PRODUCTING_AREA", partStockDTO.get("PRODUCTING_AREA"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MIN_PACKAGE"))){
            partinfo.setDouble("MIN_PACKAGE", partStockDTO.get("MIN_PACKAGE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MIN_STOCK"))){
            partStock.setDouble("MIN_STOCK", partStockDTO.get("MIN_STOCK"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("UNIT_CODE"))){
            partStock.setString("UNIT_CODE", partStockDTO.get("UNIT_CODE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("SALES_PRICE"))){
            partStock.setDouble("SALES_PRICE", partStockDTO.get("SALES_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_MAIN_TYPE"))){
            partStock.setDouble("PART_MAIN_TYPE", partStockDTO.get("PART_MAIN_TYPE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_SPE_TYPE"))){
            partStock.setDouble("PART_SPE_TYPE", partStockDTO.get("PART_SPE_TYPE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("INSURANCE_PRICE"))){
            partinfo.setDouble("INSURANCE_PRICE", partStockDTO.get("INSURANCE_PRICE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("REMARK"))){
            partStock.setString("REMARK", partStockDTO.get("REMARK"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("PART_STATUS"))){
            partStock.setDouble("PART_STATUS", partStockDTO.get("PART_STATUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("IS_STORAGE_SALE"))){
            partinfo.setInteger("IS_STORAGE_SALE", partStockDTO.get("IS_STORAGE_SALE"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("IS_SUGGEST_ORDER"))){
            partStock.setDouble("IS_SUGGEST_ORDER", partStockDTO.get("IS_SUGGEST_ORDER"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MONTHLY_QTY_FORMULA"))){
            partStock.setDouble("MONTHLY_QTY_FORMULA", partStockDTO.get("MONTHLY_QTY_FORMULA"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("JAN_MODULUS"))){
            partStock.setDouble("JAN_MODULUS", partStockDTO.get("JAN_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("FEB_MODULUS"))){
            partStock.setDouble("FEB_MODULUS", partStockDTO.get("FEB_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MAR_MODULUS"))){
            partStock.setDouble("MAR_MODULUS", partStockDTO.get("MAR_MODULUS")); 
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("APR_MODULUS"))){
            partStock.setDouble("APR_MODULUS", partStockDTO.get("APR_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("MAY_MODULUS"))){
            partStock.setDouble("MAY_MODULUS", partStockDTO.get("MAY_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("JUN_MODULUS"))){
            partStock.setDouble("JUN_MODULUS", partStockDTO.get("JUN_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("JUL_MODULUS"))){
            partStock.setDouble("JUL_MODULUS", partStockDTO.get("JUL_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("AUG_MODULUS"))){
            partStock.setDouble("AUG_MODULUS", partStockDTO.get("AUG_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("SEP_MODULUS"))){
            partStock.setDouble("SEP_MODULUS", partStockDTO.get("SEP_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("OCT_MODULUS"))){
            partStock.setDouble("OCT_MODULUS", partStockDTO.get("OCT_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("NOV_MODULUS"))){
            partStock.setDouble("NOV_MODULUS", partStockDTO.get("NOV_MODULUS"));
        }
        if(!StringUtils.isNullOrEmpty(partStockDTO.get("DEC_MODULUS"))){
            partStock.setDouble("DEC_MODULUS", partStockDTO.get("DEC_MODULUS"));
        }
        return partStock;
    }
    @Override
    public Map<String, Object> queryPartInfo(Map<String, String> queryParam) throws ServiceBizException {
      /*  
       SELECT DEALER_CODE,PART_NO,PART_NAME,SPELL_CODE,PART_GROUP_CODE,UNIT_NAME,
        UNIT_CODE,OPTION_NO,BRAND,CLAIM_PRICE,REMARK,PROVIDER_CODE,PART_STATUS,MIN_PACKAGE,
        PRODUCTING_AREA,PART_MAIN_TYPE,IS_FREEZE,DOWN_TAG,ABC_TYPE,IS_ACC,IS_STORAGE_SALE,
        OEM_TAG,QAN_RULES,PROVIDER_NAME,PART_TYPE,IS_SJV,IS_MOP,IS_C_SALE,IS_C_PURCHASE,
        IS_COMMON_IDENTITY,REPORT_WAY,IS_BACK FROM  TM_PART_info
*/      StringBuilder sqlsb= new StringBuilder(" SELECT *  FROM  TM_PART_info ");
        sqlsb.append("where part_no='"+queryParam.get("PART_NO")+" '" );
        Map<String,Object> lists = DAOUtil.findFirst(sqlsb.toString(), null);
        return lists;
    }
    
    
}
