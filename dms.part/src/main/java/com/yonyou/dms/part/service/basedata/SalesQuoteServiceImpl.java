/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.TtSalesQuoteDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtSalesQuoteItemDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartObligatedItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesQuoteItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesQuotePO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * TODO description
 * 
 * @author chenwei
 * @date 2017年5月4日
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SalesQuoteServiceImpl implements SalesQuoteService {

    /**
     * 根据销售报价单号、是否完成条件查询分页配件报价单记录
     * 
     * @author chenwei
     * @date 2017年5月4日
     * @param map
     * @return (non-Javadoc)
     * @see com.yonyou.dms.part.service.basedata.SalesQuoteService#findAllSalesQuoteInfo(java.util.Map)
     */
    @Override
    public PageInfoDto findAllSalesQuoteInfo(Map<String, String> map) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append(" select DEALER_CODE, SALES_QUOTE_NO, POPEDOM_ORDER_NO, CUSTOMER_CODE, ");
        sb.append(" COST_AMOUNT, OUT_AMOUNT, HANDLER, SALES_QUOTE_DATE, IS_FINISHED, ");
        sb.append(" FINISHED_DATE, LOCK_USER, CUSTOMER_NAME ");
        sb.append(" from TT_SALES_QUOTE ");
        sb.append(" where D_KEY = " + CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("quoteNo")))
            sb.append(" AND SALES_QUOTE_NO = '").append(map.get("quoteNo")).append("' ");
        if (!StringUtils.isNullOrEmpty(map.get("isFinished")))
            sb.append(" AND IS_FINISHED = ").append(map.get("isFinished"));
        sb.append(" ORDER BY SALES_QUOTE_DATE DESC "); 
        return DAOUtil.pageQuery(sb.toString(), null);
    }
    
    @Override
    public List<Map> findSalesQuoteList(Map<String, String> map) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append(" select DEALER_CODE, SALES_QUOTE_NO, POPEDOM_ORDER_NO, CUSTOMER_CODE, ");
        sb.append(" COST_AMOUNT, OUT_AMOUNT, HANDLER, SALES_QUOTE_DATE, IS_FINISHED, ");
        sb.append(" FINISHED_DATE, LOCK_USER, CUSTOMER_NAME ");
        sb.append(" from TT_SALES_QUOTE ");
        sb.append(" where D_KEY = " + CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("quoteNo")))
            sb.append(" AND SALES_QUOTE_NO = '").append(map.get("quoteNo")).append("' ");
        if (!StringUtils.isNullOrEmpty(map.get("isFinished")))
            sb.append(" AND IS_FINISHED = ").append(map.get("isFinished"));
        sb.append(" ORDER BY SALES_QUOTE_DATE DESC "); 
        return DAOUtil.findAll(sb.toString(), null);
    }

    @Override
    public String checkLockSalesQuote(String quote) throws ServiceBizException {
     // TODO Auto-generated method stub
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DEALER_CODE, LOCK_USER FROM TT_SALES_QUOTE WHERE SALES_QUOTE_NO= ? AND DEALER_CODE ='"
                   + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
        if (!StringUtils.isNullOrEmpty(quote)) {
            params.add(quote);
        }else return null;
        List<Map> list =  OemDAOUtil.findAll(sql.toString(), params);
        if(null == list) return null;
        Map map = list.get(0);
        if(StringUtils.isNullOrEmpty(map.get("LOCK_USER"))) return null;
        return map.get("LOCK_USER").toString();
    }
    
    @Override
    public String checkLockSalesPart(String salesPartNo) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DEALER_CODE, LOCK_USER FROM TT_SALES_PART WHERE SALES_PART_NO= ? AND DEALER_CODE ='"
                   + FrameworkUtil.getLoginInfo().getDealerCode() + "'");
        if (!StringUtils.isNullOrEmpty(salesPartNo)) {
            params.add(salesPartNo);
        }else return null;
        List<Map> list =  OemDAOUtil.findAll(sql.toString(), params);
        if(null == list) return null;
        Map map = list.get(0);
        if(StringUtils.isNullOrEmpty(map.get("LOCK_USER"))) return null;
        return map.get("LOCK_USER").toString();
    }

    @Override
    public List<Map> queryPartStockItem(Map<String, String> map) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT 'R' AS ITEM_UPDATE_STATUS,C.IS_FINISHED,A.DEALER_CODE,A.SALES_QUOTE_NO,A.PART_NO,A.STORAGE_CODE,A.PART_NAME,E.STORAGE_NAME,A.UNIT_CODE,A.COST_PRICE,A.OUT_QUANTITY,A.OUT_AMOUNT,A.STORAGE_POSITION_CODE ");
        sb.append(",(B.STOCK_QUANTITY + B.BORROW_QUANTITY - B.LEND_QUANTITY - B.LOCKED_QUANTITY) AS USEABLE_STOCK,D.DOWN_TAG ");
        sb.append(" from TT_SALES_QUOTE_ITEM A  ");
        sb.append("  left join TT_SALES_QUOTE C on A.DEALER_CODE=C.DEALER_CODE and A.SALES_QUOTE_NO=C.SALES_QUOTE_NO ");
        sb.append("  left join TM_PART_STOCK B on B.DEALER_CODE=A.DEALER_CODE AND A.PART_NO=B.PART_NO AND A.STORAGE_CODE=B.STORAGE_CODE  ");
        sb.append(" LEFT JOIN TM_PART_INFO D ON A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO = D.PART_NO ");
        sb.append(" LEFT JOIN TM_STORAGE E ON  A.DEALER_CODE = E.DEALER_CODE AND A.STORAGE_CODE=E.STORAGE_CODE ");
        sb.append(" where A.D_KEY = " + CommonConstants.D_KEY);
        if (!StringUtils.isNullOrEmpty(map.get("quoteNo")))
            sb.append(" AND A.SALES_QUOTE_NO = '").append(map.get("quoteNo")).append("' ");
        sb.append(" ORDER BY A.ITEM_ID "); // add by sf 2010-04-20
        return DAOUtil.findAll(sb.toString(), null);
    }

    @Override
    public PageInfoDto queryPartStock(Map<String, String> map) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        String a = Utility.getDefaultValue(String.valueOf(CommonConstants.DEFAULT_PARA_PART_RATE));
        double rate = 1 + Utility.getDouble(a);
        String stor = "";
        if(!StringUtils.isNullOrEmpty(map.get("storageCode"))){
            stor = " and A.STORAGE_CODE = '" + map.get("storageCode") + "' ";
        }else{
            stor = " and  1 = 1 ";
        }
         String no = Utility.getLikeCond("A", "PART_NO", map.get("partNo"), "AND");
        String name = Utility.getLikeCond("A", "PART_NAME", map.get("partName"), "AND");
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
        if("12781002".equals(Utility.getDefaultValue("5433"))){
            ghStorage = " and TS.CJ_TAG=12781001 ";
        }else{
            ghStorage = " and  1=1 ";
        }
        if (!StringUtils.isNullOrEmpty(map.get("groupCode"))){
        
            group = " and B.PART_GROUP_CODE = " + map.get("groupCode") + " ";
        }
        else{
            group = " and  1 = 1 ";
        }
        partModel = Utility.getLikeCond("A", "PART_MODEL_GROUP_CODE_SET", map.get("model"), "AND");
        position = Utility.getLikeCond("A", "STORAGE_POSITION_CODE", map.get("positionCode"), "AND");
        spell = Utility.getLikeCond("A", "SPELL_CODE", map.get("spellCode"), "AND");
        if (!StringUtils.isNullOrEmpty(map.get("sale")))
        {
            salePrice = " and A.SALES_PRICE > 0   ";
        }
        else
        {
            salePrice = " and  1=1 ";
        }
        partno = Utility.getLikeCond("B", "part_no", map.get("partNo"), "AND");
        if (!StringUtils.isNullOrEmpty(map.get("stock")))
        {
            stockCount = " and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY)>0 ";
        }
        else
        {
            stockCount = " and  1=1 ";
        }
        if (!StringUtils.isNullOrEmpty(map.get("brand")))
        {
            partBrand = " and B.BRAND =  '" + map.get("brand") + "'   ";
        }
        else
        {
            partBrand = " and  1=1 ";
        }
        if (!StringUtils.isNullOrEmpty(map.get("partInfixName"))){
            apartInfixName = " and B.PART_INFIX_NAME like '" + map.get("partInfixName") + "%' ";
        }
        aremark = Utility.getStrLikeCond("A", "REMARK", map.get("remark"));
        sql.append(" SELECT B.OPTION_NO,A.COST_PRICE*").append(rate).append("  AS NET_COST_PRICE,A.COST_AMOUNT*").append(rate);
        sql.append(" AS NET_COST_AMOUNT,B.ORI_PRO_CODE,A.IS_SUGGEST_ORDER,A.PART_MAIN_TYPE,A.PART_SPE_TYPE,A.DEALER_CODE, A.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, ");
        sql.append(" A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE,B.UNIT_NAME, A.STOCK_QUANTITY, A.SALES_PRICE, ");
        sql.append(" A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE*1.0000 COST_PRICE, A.COST_AMOUNT*1.0000 COST_AMOUNT, A.MAX_STOCK, ");
        sql.append(" A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY,TS.CJ_TAG, A.PART_STATUS, ");
        sql.append(" A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER, ");
        sql.append(" A.JAN_MODULUS,A.FEB_MODULUS,A.MAR_MODULUS,A.APR_MODULUS,A.MAY_MODULUS,A.JUN_MODULUS,A.JUL_MODULUS,A.AUG_MODULUS, ");
        sql.append(" A. SEP_MODULUS,A.OCT_MODULUS,A.NOV_MODULUS,A.DEC_MODULUS,A.MONTHLY_QTY_FORMULA, ");
        sql.append(" B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE,B.DOWN_TAG,B.FROM_ENTITY, ");
        sql.append(" D.OPTION_STOCK,A.INSURANCE_PRICE,B.INSTRUCT_PRICE, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,A.NODE_PRICE ");
        if(!StringUtils.isNullOrEmpty(Utility.getDefaultValue("1180")) && DictCodeConstants.DICT_IS_YES.equals(Utility.getDefaultValue("1180"))){
            sql.append(",B.PART_INFIX,F.POS_CODE,E.POS_NAME");
        }else{
            sql.append(",'' as PART_INFIX,'' as POS_CODE,'' as POS_NAME");
        }
        sql.append(" FROM TM_PART_STOCK A LEFT OUTER JOIN (");
        sql.append("SELECT A.DEALER_CODE,A.UNIT_NAME,A.PART_INFIX_NAME,A.BRAND,A.PART_NO,A.PART_GROUP_CODE,A.OPTION_NO,A.ORI_PRO_CODE,A.PRODUCTING_AREA,A.MIN_PACKAGE,A.DOWN_TAG");
        sql.append(",A.FROM_ENTITY,A.INSTRUCT_PRICE,A.PART_INFIX FROM TM_PART_INFO A INNER JOIN TM_ENTITY_RELATIONSHIP B ON A.DEALER_CODE = B.PARENT_ENTITY ");
        sql.append("AND B.BIZ_CODE = 'TM_PART_INFO'");
        sql.append(") B ");
        sql.append(" ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO) ");
        sql.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C ");
        sql.append(" WHERE DEALER_CODE='").append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' AND D_KEY=").append(CommonConstants.D_KEY);
        sql.append(" GROUP BY DEALER_CODE,PART_NO ) D ");
        sql.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.PART_NO = D.PART_NO) ");
        if(!StringUtils.isNullOrEmpty(Utility.getDefaultValue("1180")) && DictCodeConstants.DICT_IS_YES.equals(Utility.getDefaultValue("1180"))){
            sql.append(" LEFT JOIN TW_POS_INFIX_RELATION F ON A.DEALER_CODE = F.DEALER_CODE AND B.PART_INFIX  = F.PART_INFIX AND F.IS_VALID = ");
            sql.append(DictCodeConstants.DICT_IS_YES).append(" LEFT JOIN TW_MALFUNCTION_POSITION E ON e.is_valid=").append(DictCodeConstants.DICT_IS_YES);
            sql.append(" and A.DEALER_CODE = E.DEALER_CODE AND F.POS_CODE = E.POS_CODE ");
        }
        sql.append(" LEFT JOIN TM_STORAGE TS ON A.DEALER_CODE = TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE ");
        sql.append(" WHERE A.D_KEY =  ").append(CommonConstants.D_KEY);
        if(!StringUtils.isNullOrEmpty(map.get("model"))){
            sql.append(" AND A.PART_MODEL_GROUP_CODE_SET like ").append("%" + map.get("model") + "%");
        }
        sql.append(partModel).append(stor).append(no).append(name).append(ghStorage).append(group).append(position).append(spell).append(salePrice);
        sql.append(partno).append(stockCount).append(partBrand).append(apartInfixName).append(aremark);
        if (DictCodeConstants.DICT_IS_YES.equals(map.get("judgePartsRepair")))
        {
            sql.append(" AND A.PART_STATUS<>" + map.get("judgePartsRepair") + " ");
        }
        if (!StringUtils.isNullOrEmpty(map.get("partStatus")))
        {
            if (DictCodeConstants.DICT_IS_YES.equals(map.get("partStatus").trim()))
            {
                //查詢停用的配件
                sql.append(" AND A.PART_STATUS=" + map.get("partStatus") + " ");
            }else
            {
                //查沒有停用的
                sql.append(" AND A.PART_STATUS<>" + DictCodeConstants.DICT_IS_YES + " ");
            }
        }
        if (!StringUtils.isNullOrEmpty(map.get("isStopIsZero")))
        {
            if (DictCodeConstants.DICT_IS_YES.equals(map.get("isStopIsZero")))
            {
                //主数据停用本地库存为零的
                sql.append(" AND (B.PART_STATUS=" + DictCodeConstants.DICT_IS_YES
                        + "   AND  (A.STOCK_QUANTITY=0 OR A.STOCK_QUANTITY is null )) ");
            }
        }
      //add by dyz 2010.10.15 增加条件是否获取所有仓库配件
        if (!StringUtils.isNullOrEmpty(map.get("allPart")) || DictCodeConstants.DICT_IS_NO.equals(map.get("allPart")))
        {
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
        }
        if (!StringUtils.isNullOrEmpty(map.get("partMainType")))
        {
            sql.append(" AND A.PART_MAIN_TYPE =" + map.get("partMainType") + " ");
        }
        return DAOUtil.pageQuery(sql.toString(), null);
    }

    @Override
    public PageInfoDto queryPartInfo(Map<String, String> map) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        String number = "";
        String storage = "";
        if (!StringUtils.isNullOrEmpty(map.get("storageCode"))){
            storage = " and A.STORAGE_CODE = '" + map.get("storageCode") + "' ";
        }
        else{
            storage = " and  1 = 1 ";
        }
        if (!StringUtils.isNullOrEmpty(map.get("partNo"))){
            number = " and A.PART_NO =  '" + map.get("partNo") + "'  ";
        }
        else{
            number = " and 1 = 1 ";
        }
        sql.append(" SELECT '"+map.get("posCode")+"' as POS_CODE,'"+map.get("posName")+"' as POS_NAME,A.PART_MODEL_GROUP_CODE_SET, ").append(map.get("partQuantity"));
        sql.append("  as PART_QUANTITY,  A.DEALER_CODE, A.PART_NO, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME ");
        sql.append("  ,A.SPELL_CODE, A.UNIT_CODE,B.UNIT_NAME, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, A.PART_GROUP_CODE ");
        sql.append("  ,B.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE AS COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK,B.DOWN_TAG ");
        sql.append(" ,A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.LAST_STOCK_IN, A.PART_STATUS ");
        sql.append(" ,A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, A.MIN_STOCK ");
        sql.append(" ,B.OPTION_NO, A.NODE_PRICE, B.PLAN_PRICE, B.OEM_LIMIT_PRICE, B.URGENT_PRICE ");
        sql.append(" ,A.INSURANCE_PRICE, D.OPTION_STOCK, (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK,B.IS_BACK,B.PART_INFIX ");
        sql.append(" ,B.MIN_LIMIT_PRICE  FROM TM_PART_STOCK A  LEFT OUTER JOIN (");
        sql.append("SELECT A.DEALER_CODE,A.UNIT_NAME,A.PART_NO,A.LIMIT_PRICE,A.DOWN_TAG,A.INSTRUCT_PRICE,A.OPTION_NO,A.PART_MODEL_GROUP_CODE_SET,A.PLAN_PRICE,A.OEM_LIMIT_PRICE,A.URGENT_PRICE ");
        sql.append(",A.is_back,A.PART_INFIX,A.MIN_LIMIT_PRICE FROM TM_PART_INFO A INNER JOIN TM_ENTITY_RELATIONSHIP B ON  A.DEALER_CODE = B.PARENT_ENTITY ");
        sql.append(" AND B.BIZ_CODE = 'TM_PART_INFO') B ");
        sql.append(" ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO ) ");
        sql.append(" LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D ");
        sql.append(" ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO ) ");
        sql.append(" WHERE A.D_KEY =  ").append(CommonConstants.D_KEY).append(number).append(storage);
        return DAOUtil.pageQuery(sql.toString(), null);
    }
    
    @Override
    public List<Map> queryPartInfoList(Map<String, Object> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = getObjectSQL(queryParam, params);
        List<Map> resultList = DAOUtil.findAll(sql, params);
        return resultList;
    }
    
    /**
     * 封装sql
     * 
     * @author xukl
     * @date 2016年8月2日
     * @param queryParam
     * @param params
     * @return
     */

    private String getObjectSQL(Map<String, Object> queryParam, List<Object> params) throws ServiceBizException {

        StringBuilder sb = new StringBuilder(" SELECT DEALER_CODE,PART_NO,PART_NAME,DOWN_TAG,D_KEY,LIMIT_PRICE FROM tm_part_info WHERE 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParam.get("brand"))) {
            sb.append(" and BRAND = ?");
            params.add(queryParam.get("brand"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partCode"))) {
            sb.append(" and PART_NO like ?");
            params.add("%" + queryParam.get("partCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("downTag"))) {
            sb.append(" and DOWN_TAG = ?");
            params.add(queryParam.get("downTag"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("dKey"))) {
            sb.append(" and D_KEY = ?");
            params.add(queryParam.get("dKey"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partName"))) {
            sb.append(" and PART_NAME like ?");
            params.add("%" + queryParam.get("partName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("spellCode"))) {
            sb.append(" and SPELL_CODE like ?");
            params.add("%" + queryParam.get("spellCode") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partGroupCode"))) {
            sb.append(" and PART_GROUP_CODE = ?");
            params.add(queryParam.get("partGroupCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partInfix"))) {
            sb.append(" and PART_INFIX like ?");
            params.add("%" + queryParam.get("partInfix") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("partInfixName"))) {
            sb.append(" and PART_INFIX_NAME like ?");
            params.add("%" + queryParam.get("partInfixName") + "%");
        }
        return sb.toString();
    }

    
    @Override
    public void insertTtSalesQuotePO(TtSalesQuotePO ttSalesQuotePO) {
        ttSalesQuotePO.saveIt();
    }
    
    @Override
    public void insertTtSalesQuoteItemPO(TtSalesQuoteItemDTO ttSalesQuoteItemDTO) {
        TtSalesQuoteItemPO itemPo = new TtSalesQuoteItemPO();
        setDtoToPo(itemPo, ttSalesQuoteItemDTO);
        itemPo.saveIt();
    }
    
    public void setDtoToPo(TtSalesQuoteItemPO itemPo, TtSalesQuoteItemDTO itemDTO){
        itemPo.setString("SALES_QUOTE_NO", itemDTO.getSalesQuoteNo());
        itemPo.setString("CHARGE_PARTITION_CODE", itemDTO.getChargePartitionCode());
        itemPo.setDouble("COST_AMOUNT", itemDTO.getCostAmount());
        itemPo.setDouble("COST_PRICE", itemDTO.getCostPrice());
        itemPo.setDouble("OEM_LIMIT_PRICE", itemDTO.getOemLimitPrice());
        itemPo.setDouble("OUT_AMOUNT", itemDTO.getOutAmount());
        itemPo.setDouble("OUT_PRICE", itemDTO.getOutPrice());
        itemPo.setFloat("OUT_QUANTITY", itemDTO.getOutQuantity());
        itemPo.setString("PART_BATCH_NO", itemDTO.getPartBatchNo());
        itemPo.setString("PART_NAME", itemDTO.getPartName());
        itemPo.setString("PART_NO", itemDTO.getPartNo());
        itemPo.setFloat("PRICE_RATE", itemDTO.getPriceRate());
        itemPo.setInteger("PRICE_TYPE", itemDTO.getPriceType());
        itemPo.setString("STORAGE_CODE", itemDTO.getStorageCode());
        itemPo.setString("STORAGE_POSITION_CODE", itemDTO.getStoragePositionCode());
        itemPo.setString("UNIT_CODE", itemDTO.getUnitCode());
    }

    @Override
    public void deleteTtSalesQuoteItem(TtSalesQuoteItemDTO deleteParams) throws ServiceBizException {
        StringBuilder deleteSql = new StringBuilder(" SALES_QUOTE_NO = ");
        deleteSql.append(deleteParams.getSalesQuoteNo());
        deleteSql.append(" AND D_KEY = ");
        deleteSql.append(deleteParams.getDKey()).append(" ");
        DAOUtil.deleteByDealer(TtSalesQuoteItemPO.class, deleteSql.toString());
    }
    
    
    @Override
    public void updateTtSalesQuoteItem(Long itemId, TtSalesQuoteItemDTO itemDto) throws ServiceBizException{
        TtSalesQuoteItemPO lap = TtSalesQuoteItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),itemId);
        lap.setDouble("OUT_PRICE", itemDto.getOutPrice());
        lap.setFloat("OUT_QUANTITY", itemDto.getOutQuantity());
        lap.setDouble("OUT_AMOUNT", itemDto.getOutAmount());
        lap.setDouble("COST_AMOUNT", itemDto.getCostAmount());
        lap.saveIt();
    }

    @Override
    public void deleteTtSalesQuote(TtSalesQuoteDTO deleteParams) throws ServiceBizException {
        StringBuilder deleteSql = new StringBuilder(" SALES_QUOTE_NO = ");
        deleteSql.append(deleteParams.getSalesQuoteNo());
        deleteSql.append(" AND D_KEY = ");
        deleteSql.append(deleteParams.getdKey()).append(" ");
        DAOUtil.deleteByDealer(TtSalesQuotePO.class, deleteSql.toString());
    }
    
    /**
     * 更新报价单表
    * TODO description
    * @author chenwei
    * @date 2017年5月7日
    * @param salesNumber
    * @param ttSalesQuotepo
    * @throws ServiceBizException
     */
    @Override
    public void updateTtSalesQuote(String salesNumber, TtSalesQuoteDTO ttSalesQuotedto) throws ServiceBizException {
        // TODO Auto-generated method stub
        if(StringUtils.isNullOrEmpty(salesNumber))return ;
        TtSalesQuotePO lap = TtSalesQuotePO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),salesNumber);
        if(!StringUtils.isNullOrEmpty(ttSalesQuotedto.getCostAmount())){
            lap.setDouble("COST_AMOUNT", ttSalesQuotedto.getCostAmount());
        }
        if(!StringUtils.isNullOrEmpty(ttSalesQuotedto.getCustomerCode())){
            lap.setString("CUSTOMER_CODE", ttSalesQuotedto.getCustomerCode());
        }
        if(!StringUtils.isNullOrEmpty(ttSalesQuotedto.getFinishedDate())){
             lap.setDate("FINISHED_DATE", ttSalesQuotedto.getFinishedDate());
        }
        if(!StringUtils.isNullOrEmpty(ttSalesQuotedto.getHandler())){
            lap.setString("HANDLER", ttSalesQuotedto.getHandler());
        }
        if(!StringUtils.isNullOrEmpty(ttSalesQuotedto.getLockUser())){
            lap.setString("LOCK_USER", ttSalesQuotedto.getLockUser());
        }
        if(!StringUtils.isNullOrEmpty(ttSalesQuotedto.getOutAmount())){
            lap.setDouble("OUT_AMOUNT", ttSalesQuotedto.getOutAmount());
       }
        if(!StringUtils.isNullOrEmpty(ttSalesQuotedto.getPopedomOrderNo())){
            lap.setString("POPEDOM_ORDER_NO", ttSalesQuotedto.getPopedomOrderNo());
       }
        if(!StringUtils.isNullOrEmpty(ttSalesQuotedto.getSalesQuoteDate())){
            lap.setDate("SALES_QUOTE_DATE", ttSalesQuotedto.getSalesQuoteDate());
       }
        if(!StringUtils.isNullOrEmpty(ttSalesQuotedto.getIsFinished())){
            lap.setInteger("IS_FINISHED", ttSalesQuotedto.getIsFinished());
       }
        lap.saveIt();
    }
    
    @Override
    public int modifyTtSalesQuoteByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException {
        int record = 0;
        if(null == paramsList){
            record = TtSalesQuotePO.update(sqlStr, sqlWhere);
        }else{
            record = TtSalesQuotePO.update(sqlStr, sqlWhere, paramsList);
        }
        return record;
    }

    @Override
    public PageInfoDto QueryPartSalesSlip(Map<String, String> map) throws ServiceBizException {
        StringBuilder sql = new StringBuilder(" SELECT A.DEALER_CODE,A.SALES_PART_NO,A.RO_NO,A.SO_NO,A.CUSTOMER_CODE,A.CUSTOMER_NAME");
        sql.append(" ,A.SALES_PART_AMOUNT,A.BALANCE_STATUS,A.CONSULTANT,A.REMARK,A.LOCK_USER FROM TT_SALES_PART A LEFT JOIN TT_REPAIR_ORDER C");
        sql.append(" ON A.DEALER_CODE=C.DEALER_CODE AND A.RO_NO=C.RO_NO AND C.RO_STATUS = ").append(DictCodeConstants.DICT_RO_STATUS_TYPE_FOR_BALANCE);
        sql.append("  AND C.D_KEY = ").append(CommonConstants.D_KEY).append(" WHERE A.D_KEY = ").append(CommonConstants.D_KEY);
        sql.append(" AND (A.BALANCE_STATUS = ").append(DictCodeConstants.DICT_IS_NO).append(" OR A.BALANCE_STATUS IS NULL)");
        sql.append("  AND NOT EXISTS (SELECT ITEM_ID FROM TT_SALES_PART_ITEM B WHERE A.SALES_PART_NO = B.SALES_PART_NO AND A.DEALER_CODE = B.DEALER_CODE AND B.IS_FINISHED = ").append(DictCodeConstants.DICT_IS_NO).append(")");
        sql.append(" AND EXISTS (SELECT ITEM_ID FROM TT_SALES_PART_ITEM B WHERE A.SALES_PART_NO = B.SALES_PART_NO AND A.DEALER_CODE = B.DEALER_CODE AND B.IS_FINISHED = ").append(DictCodeConstants.DICT_IS_YES).append(")");
        if(!StringUtils.isNullOrEmpty(map.get("salesPartNo"))){
            sql.append(Utility.getLikeCond("A", "SALES_PART_NO", map.get("salesPartNo"), "AND"));
        }
        if(!StringUtils.isNullOrEmpty(map.get("customerCode"))){
            sql.append(Utility.getLikeCond("A", "CUSTOMER_CODE", map.get("customerCode"), "AND"));
        }
        sql.append(Utility.getLikeCond("c", "OWNER_NAME", map.get("ownerName"), "AND"));
        return DAOUtil.pageQuery(sql.toString(), null);
    }

    @Override
    public List<Map> queryPartSalesItem(Map<String, String> map) throws ServiceBizException {
        StringBuilder sql = new StringBuilder(" select '12781002' AS IS_SELECTED,'A' as Item_UPDATE_STATUS,A.IS_DISCOUNT,A.SALES_DISCOUNT,A.UNIT_CODE,A.DEALER_CODE,A.ITEM_ID,A.SALES_PART_NO,A.STORAGE_CODE");
        if(!StringUtils.isNullOrEmpty(map.get("amount")))
            sql.append(",A.PART_SALES_AMOUNT/(A.PART_COST_AMOUNT*(1+").append(Double.valueOf(map.get("amount"))).append(")) as ADD_RATE");
        sql.append(",A.STORAGE_POSITION_CODE,A.PART_NO,A.PART_BATCH_NO,A.PART_NAME,A.PART_QUANTITY as OUT_QUANTITY,A.PART_QUANTITY as PART_QUANTITY,A.BATCH_NO,A.PART_COST_PRICE as COST_PRICE,A.PART_COST_PRICE as PART_COST_PRICE");
        sql.append(",A.PART_SALES_PRICE,A.CREATED_BY,A.PART_COST_AMOUNT,A.PART_SALES_AMOUNT as OUT_AMOUNT,A.PART_SALES_AMOUNT as PART_SALES_AMOUNT,A.IS_FINISHED,A.FINISHED_DATE,A.SENDER,A.RECEIVER,A.SEND_TIME");
        sql.append(",B.down_tag,A.OLD_SALES_PART_NO from Tt_Sales_Part_Item A LEFT JOIN tm_part_info B on A.DEALER_CODE = B.DEALER_CODE and A.part_no = B.part_no");
        sql.append(" where 1=1 and a.D_Key=0  and a.Sales_Part_No='").append(map.get("salesPartNo")).append("' ");
        return DAOUtil.findAll(sql.toString(), null);
    }
    
    @Override
    public PageInfoDto QueryOwnerByNoOrSpell(Map<String, String> map) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        List<Object> queryList = new ArrayList<Object>();
        //sql.append(" SELECT B.LICENSE,A.*,B.VIN FROM (" + CommonConstants.VM_OWNER + ") A LEFT JOIN (" + CommonConstants.VM_VEHICLE + ") B ON A.DEALER_CODE=B.DEALER_CODE AND A.OWNER_NO=B.OWNER_NO WHERE A.DEALER_CODE= ? ");
        sql.append(" SELECT DISTINCT B.VIN,A.DEALER_CODE,A.OWNER_NO,A.OWNER_NAME,A.OWNER_PROPERTY,B.LICENSE,A.OWNER_SPELL,A.ADDRESS ");
        sql.append("FROM (");
        sql.append("SELECT C.ADDRESS ,C.OWNER_SPELL ,C.OWNER_PROPERTY,C.OWNER_NAME,C.OWNER_NO,C.DEALER_CODE FROM TM_OWNER C LEFT OUTER JOIN TM_ENTITY_RELATIONSHIP D");
        sql.append(" ON C.DEALER_CODE = D.PARENT_ENTITY AND D.BIZ_CODE = 'TM_OWNER'");
        sql.append(") A ");
        if(!StringUtils.isNullOrEmpty(map.get("isRepair")) && DictCodeConstants.DICT_IS_CHECKED.equals(map.get("isRepair"))){
            sql.append(" LEFT JOIN TT_REPAIR_ORDER B ON A.OWNER_NO=B.OWNER_NO AND A.DEALER_CODE=B.DEALER_CODE  ");
            sql.append(" WHERE B.RO_STATUS=").append(DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR);
        }else{
            sql.append(" LEFT JOIN ( ");
            sql.append(" SELECT E.VIN ,F.LICENSE,F.OWNER_NO,F.DEALER_CODE FROM TM_VEHICLE E LEFT OUTER JOIN TM_ENTITY_RELATIONSHIP F");
            sql.append(" ON E.DEALER_CODE = F.PARENT_ENTITY AND F.BIZ_CODE = 'TM_VEHICLE' ");
            sql.append(" ) B ON A.OWNER_NO=B.OWNER_NO AND A.DEALER_CODE=B.DEALER_CODE WHERE 1=1 ");
        }
        
        sql.append(Utility.getLikeCond("A", "OWNER_NO", map.get("ownerNo"), "AND"));
        sql.append(Utility.getLikeCond("A", "OWNER_SPELL", map.get("ownerSpell"), "AND"));
        sql.append(Utility.getLikeCond("A", "OWNER_NAME",  map.get("ownerName"), "AND"));
        sql.append(Utility.getLikeCond("B", "LICENSE", map.get("license"), "AND"));
        
        if(!StringUtils.isNullOrEmpty(map.get("vin"))){
            sql.append(" AND B.VIN = ").append(map.get("vin"));
        }
        
        PageInfoDto pageInfoDto = DAOUtil.pageQuery((sql == null ? null : sql.toString()), queryList);
        return pageInfoDto;
    }

    @Override
    public List<Map> findSalesQuoteItemList(Map<String, Object> map) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append(" select DEALER_CODE,ITEM_ID, SALES_QUOTE_NO, STORAGE_POSITION_CODE, STORAGE_CODE, ");
        sb.append(" PART_NO, PART_BATCH_NO, PART_NAME, UNIT_CODE,CHARGE_PARTITION_CODE, OUT_QUANTITY,PRICE_TYPE,PRICE_RATE,OEM_LIMIT_PRICE,COST_PRICE,COST_AMOUNT,OUT_PRICE,OUT_AMOUNT ");
        sb.append(" from TT_SALES_QUOTE_ITEM ");
        sb.append(" where 1=1 ");
        if (!StringUtils.isNullOrEmpty(map.get("quoteNo")))
            sb.append(" AND SALES_QUOTE_NO = '").append(map.get("quoteNo")).append("' ");
        if (!StringUtils.isNullOrEmpty(map.get("dKey")))
            sb.append(" AND D_KEY = ").append(map.get("dKey"));
        sb.append(" ORDER BY SALES_QUOTE_NO DESC "); 
        return DAOUtil.findAll(sb.toString(), null);
    }
    
    @Override
    public List<Map> findTmPartStockList(Map<String, Object> map) throws ServiceBizException {
        StringBuffer sb = new StringBuffer();
        sb.append(" select DEALER_CODE,PART_NO,STORAGE_CODE,STORAGE_POSITION_CODE,PART_NAME,SPELL_CODE,PART_GROUP_CODE,PART_STATUS,LIMIT_PRICE,INSURANCE_PRICE,LOCKED_QUANTITY ");
        sb.append(" from TM_PART_STOCK  where 1=1 ");
        if (!StringUtils.isNullOrEmpty(map.get("partNo")))
            sb.append(" AND PART_NO = '").append(map.get("partNo")).append("' ");
        if (!StringUtils.isNullOrEmpty(map.get("dKey")))
            sb.append(" AND D_KEY = ").append(map.get("dKey"));
        sb.append(" ORDER BY STORAGE_CODE,PART_NO DESC "); 
        return DAOUtil.findAll(sb.toString(), null);
    }

}
