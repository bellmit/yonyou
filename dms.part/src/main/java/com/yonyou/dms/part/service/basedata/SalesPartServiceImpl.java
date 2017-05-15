/**
 * 
 */
package com.yonyou.dms.part.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.basedata.TMLimitSeriesDatainfoDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtSalesPartItemDTO;
import com.yonyou.dms.common.domains.DTO.customer.TmVehicleDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartItemPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.basedata.TtSalesPartDTO;

/**
 * TODO description
 * 
 * @author chenwei
 * @date 2017年5月4日
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class SalesPartServiceImpl implements SalesPartService {

    @Override
    public Boolean insertTtSalesQuotePO(TtSalesPartPO salesPartPO) throws ServiceBizException {
        return salesPartPO.saveIt();
    }

    @Override
    public Boolean insertTtSalesQuoteItemPO(TtSalesPartItemPO salesPartItemPO) throws ServiceBizException {
        return salesPartItemPO.saveIt();
    }

    @Override
    public PageInfoDto findAllSalesPartInfo(Map<String, String> map) throws ServiceBizException {
        StringBuilder sql = new StringBuilder(" SELECT A.REMARK,A.CONSULTANT,A.CREATED_BY,A.VIN,A.DEALER_CODE, A.SALES_PART_NO, A.RO_NO, A.CUSTOMER_CODE, A.CUSTOMER_NAME, A.SALES_PART_AMOUNT, A.LOCK_USER,A.SO_NO,A.VER");
        sql.append(" from TT_SALES_PART A LEFT JOIN TT_REPAIR_ORDER B ON A.DEALER_CODE=B.DEALER_CODE AND A.RO_NO=B.RO_NO AND A.SALES_PART_NO=B.SALES_PART_NO WHERE 1=1 ");
        sql.append(" AND A.D_KEY = ").append(CommonConstants.D_KEY);
        sql.append(Utility.getLikeCond("A", "RO_NO", map.get("orderNo"), "AND"));
        sql.append(Utility.getLikeCond("A", "SO_NO", map.get("soNo"), "AND"));
        sql.append(Utility.getLikeCond("A", "SALES_PART_NO", map.get("salesNo"), "AND"));
        if(Utility.testString(map.get("flag")) && DictCodeConstants.DICT_IS_CHECKED.equals(map.get("flag"))){
            sql.append(" AND (SELECT B.IS_FINISHED FROM TT_SALES_PART_ITEM B WHERE B.SALES_PART_NO = A.SALES_PART_NO LIMIT 1) = "+DictCodeConstants.DICT_IS_YES+" AND A.BALANCE_STATUS ="+DictCodeConstants.DICT_IS_NO+" ");
        }else{
            sql.append(" AND (B.RO_STATUS = "+DictCodeConstants.DICT_RO_STATUS_TYPE_ON_REPAIR );
            sql.append(" OR (B.RO_NO IS NULL and a.balance_status = ").append(DictCodeConstants.DICT_IS_NO).append("))");
            sql.append(" And A.sales_part_no in (select d.sales_part_no from ( ");
            sql.append(" select max(B.ITEM_ID) as item_id, A.SALES_PART_NO FROM TT_SALES_PART A left join TT_SALES_PART_ITEM  B on B.SALES_PART_NO = A.SALES_PART_NO and A.DEALER_CODE=B.DEALER_CODE AND A.DEALER_CODE='");
            sql.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' ");
            sql.append(" group by A.SALES_PART_NO) d left join TT_SALES_PART_ITEM c on c.item_id=d.item_id and c.DEALER_CODE = '");
            sql.append(FrameworkUtil.getLoginInfo().getDealerCode()).append("'  where (c.is_finished<>12781001) or (c.is_finished is null)) ");
        }
        sql.append(" order by a.sales_part_no  ");
        return DAOUtil.pageQuery(sql.toString(), null);
    }

    @Override
    public PageInfoDto queryPartStock(Map<String, String> map) throws ServiceBizException {
        //TmPartStockItemPOFactory.queryStockInfo(....)
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
        String ghStorage = "";
        String myremark = Utility.getStrLikeCond("A", "REMARK", map.get("remark"));
        if("12781002".equals(Utility.getDefaultValue("5433"))){
            ghStorage = " and TS.CJ_TAG=12781001 ";
        }else{
            ghStorage = " and  1=1 ";
        }
        if (!StringUtils.isNullOrEmpty(map.get("brand"))){
            partBrand = " and B.BRAND =  '" + map.get("brand") + "'   ";
        }
        else{
            partBrand = " and  1=1 ";
        }
        if (DictCodeConstants.DICT_IS_CHECKED.equals(map.get("isSalePriceBigger"))){
            salePrice = " and A.SALES_PRICE > 0   ";
        }
        else{
            salePrice = " and  1=1 ";
        }
        if (DictCodeConstants.DICT_IS_CHECKED.equals(map.get("isCheck")))
        {
            stockCount = " and (A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY -C.LOCKED_QUANTITY)  > 0 ";
        }
        else
        {
            stockCount = " and  1=1 ";
        }
        position = Utility.getLikeCond("A", "STORAGE_POSITION_CODE", map.get("positionCode"), "AND");
        spell = Utility.getLikeCond("A", "SPELL_CODE", map.get("spellCode"), "AND");
        if (!StringUtils.isNullOrEmpty(map.get("storageCode"))){
            stor = " and A.STORAGE_CODE = '" + map.get("storageCode") + "' ";
        }
        else {
            stor = " and  1 = 1 ";
        }
        no = Utility.getLikeCond("A", "PART_NO", map.get("partNo"), "AND");
        name = Utility.getLikeCond("A", "PART_NAME", map.get("partName"), "AND");
        if (!StringUtils.isNullOrEmpty(map.get("groupCode"))){
            group = " and A.PART_GROUP_CODE = " + map.get("groupCode") + " ";
        }
        else{
            group = " and  1 = 1 ";
        }
        /**
         * 维修领料,配件销售,车间借料,内部领用,调拨出库,借出登记,配件移库,配件报损,配件预留界面新增查询配件，过滤掉已经停用的配件
         */
        partModel = Utility.getLikeCond("C", "PART_MODEL_GROUP_CODE_SET", map.get("model"), "AND");
        StringBuilder sql = new StringBuilder(" select A.NODE_PRICE,A.INSURANCE_PRICE, A.DEALER_CODE, A.PART_NO,TS.CJ_TAG, A.STORAGE_CODE, A.PART_BATCH_NO,A.STORAGE_POSITION_CODE, A.PART_NAME");
        sql.append(" ,A.SPELL_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, A.CLAIM_PRICE, B.LIMIT_PRICE, B.INSTRUCT_PRICE, A.LATEST_PRICE");
        sql.append(",ROUND(A.COST_PRICE,4) AS COST_PRICE, A.COST_AMOUNT, A.BORROW_QUANTITY, A.PART_STATUS,C.LOCKED_QUANTITY, A.LEND_QUANTITY");
        sql.append(",A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT,B.DOWN_TAG,B.OPTION_NO");
        sql.append(",A.VER, A.PART_GROUP_CODE,  C.PART_MODEL_GROUP_CODE_SET");
        sql.append(",(A.STOCK_QUANTITY + A.BORROW_QUANTITY - ifnull(A.LEND_QUANTITY, 0) ) AS USEABLE_QUANTITY");
        sql.append(",CASE WHEN (SELECT 1 FROM TM_MAINTAIN_PART CC WHERE  CC.PART_NO = B.PART_NO ");
        sql.append(" AND CC.DEALER_CODE = B.DEALER_CODE ) >0 THEN ").append(DictCodeConstants.DICT_IS_YES);
        sql.append(" ELSE ").append(DictCodeConstants.DICT_IS_NO).append(" END  AS IS_MAINTAIN ");
        sql.append(" from TM_PART_STOCK_ITEM  A  LEFT OUTER JOIN (");
        sql.append(" SELECT B.CHILD_ENTITY AS DEALER_CODE, A.PART_NO,A.LIMIT_PRICE,A.INSTRUCT_PRICE,A.DOWN_TAG,A.OPTION_NO ");
        sql.append(" FROM TM_PART_INFO A INNER JOIN TM_ENTITY_RELATIONSHIP B ON A.DEALER_CODE = B.PARENT_ENTITY AND B.BIZ_CODE = 'TM_PART_INFO'");
        sql.append(") B ON (A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO = B.PART_NO ) ");
        sql.append(" LEFT OUTER JOIN TM_PART_STOCK C ON (A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO AND A.STORAGE_CODE=C.STORAGE_CODE) ");
        sql.append(" LEFT JOIN TM_STORAGE TS ON  A.DEALER_CODE=TS.DEALER_CODE AND A.STORAGE_CODE=TS.STORAGE_CODE WHERE A.PART_STATUS<>");
        sql.append(DictCodeConstants.DICT_IS_YES).append(" AND A.DEALER_CODE = '").append(FrameworkUtil.getLoginInfo().getDealerCode()).append("' ");
        sql.append(partModel).append(" AND C.D_KEY = ").append(CommonConstants.D_KEY).append(stor).append(no).append(name);
        sql.append(ghStorage).append(group).append(position).append(spell).append(salePrice).append(stockCount).append(partBrand).append(myremark);
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
        return DAOUtil.pageQuery(sql.toString(), null);
    }


    @Override
    public List<Map> selectRepairOrder(Map<String, Object> queryParams) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sqlSb = new StringBuilder("select RO_NO as roNo,DEALER_CODE as dealerCode,D_KEY as dKey,REPAIR_TYPE_CODE as repairTypeCode,BRAND as brand,SERIES as series,ro_status as roStatus from TT_REPAIR_ORDER tt where 1=1 ");
        if (!StringUtils.isNullOrEmpty(queryParams.get("roNo"))) {
            sqlSb.append(" AND RO_NO = ? ");
            params.add(queryParams.get("roNo"));
        }
        if (!StringUtils.isNullOrEmpty(queryParams.get("dKey"))) {
            sqlSb.append(" AND  D_KEY = ?");
            params.add(queryParams.get("dKey"));
        }
        return DAOUtil.findAll(sqlSb.toString(), params);
    }
    
    @Override
    public List<Map> queryLimitSeriesDatainfo(TMLimitSeriesDatainfoDTO tmLSDPo) throws ServiceBizException {
        // TODO Auto-generated method stub
        List<Object> list = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,LIMIT_PRICE_RATE,ITEM_ID,BRAND_CODE,REPAIR_TYPE_CODE,SERIES_CODE,IS_VALID,OEM_TAG,D_KEY FROM TM_LIMIT_SERIES_DATAINFO where 1=1 ");
        if(StringUtils.isNullOrEmpty(tmLSDPo.getRepairTypeCode())){
            sql.append(" AND REPAIR_TYPE_CODE = ? ");
            list.add(tmLSDPo.getRepairTypeCode());
        }
        if(StringUtils.isNullOrEmpty(tmLSDPo.getBrandCode())){
            sql.append(" AND BRAND_CODE = ? ");
            list.add(tmLSDPo.getBrandCode());
        }
        if(StringUtils.isNullOrEmpty(tmLSDPo.getSeriesCode())){
            sql.append(" AND SERIES_CODE = ? ");
            list.add(tmLSDPo.getSeriesCode());
        }
        if(StringUtils.isNullOrEmpty(tmLSDPo.getIsValid())){
            sql.append(" AND IS_VALID = ? ");
            list.add(tmLSDPo.getIsValid());
        }
        if(StringUtils.isNullOrEmpty(tmLSDPo.getOemTag())){
            sql.append(" AND OEM_TAG = ? ");
            list.add(tmLSDPo.getOemTag());
        }
        if(StringUtils.isNullOrEmpty(tmLSDPo.getdKey())){
            sql.append(" AND D_KEY = ? ");
            list.add(tmLSDPo.getdKey());
        }
        return DAOUtil.findAll(sql.toString(), list);
    }
    
    @Override
    public List<Map> querySalesPartList(TtSalesPartDTO salesPartDPo) throws ServiceBizException {
        // TODO Auto-generated method stub
        List<Object> list = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,SALES_PART_NO,SO_NO,CUSTOMER_CODE,CUSTOMER_NAME,SALES_PART_AMOUNT FROM TT_SALES_PART where 1=1 ");
        if(StringUtils.isNullOrEmpty(salesPartDPo.getRoNo())){
            sql.append(" AND RO_NO = ? ");
            list.add(salesPartDPo.getRoNo());
        }
        if(StringUtils.isNullOrEmpty(salesPartDPo.getdKey())){
            sql.append(" AND D_KEY = ? ");
            list.add(salesPartDPo.getdKey());
        }
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public List<Map> queryTmVehicleList(TmVehicleDTO tmVehicleDTO) throws ServiceBizException {
        List<Object> list = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,BRAND,VIN,OWNER_NO,CUSTOMER_NO,OWNER_NO_OLD,LICENSE,ENGINE_NO FROM TM_VEHICLE where 1=1 ");
        if(StringUtils.isNullOrEmpty(tmVehicleDTO.getVin())){
            sql.append(" AND VIN = ? ");
            list.add(tmVehicleDTO.getVin());
        }
        return DAOUtil.findAll(sql.toString(), list);
    }

    @Override
    public Float querySumQuantity(String partNo, String salesPartNo, String storageCode, String storagePositionCode, String partBatchNo) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select sum(part_quantity) as quantity,dealer_code from tt_sales_part_item where 1=1 ");
        if (!StringUtils.isNullOrEmpty(partNo)) {
            sql.append("AND PART_NO=  '").append(partNo).append("' ");
        }
        if (!StringUtils.isNullOrEmpty(salesPartNo)) {
            sql.append("  AND (sales_part_no= '"+salesPartNo+"' or old_sales_part_no = '"+salesPartNo+"') ");
        }
        if (!StringUtils.isNullOrEmpty(storageCode)) {
            sql.append(" AND storage_code = '"+storageCode+"' ");
        }
        if (!StringUtils.isNullOrEmpty(storagePositionCode)) {
            sql.append(" AND STORAGE_POSITION_CODE = '"+storagePositionCode+"'");
        }
        if (!StringUtils.isNullOrEmpty(partBatchNo)) {
            sql.append(" AND part_batch_no= '"+partBatchNo+"'");
        }
        List<Map> list = DAOUtil.findAll(sql.toString(), null);
        if(StringUtils.isNullOrEmpty(list)) return 0.0F;
        Map map = list.get(0);
        if(StringUtils.isNullOrEmpty(map)) return 0.0F;
        return Float.valueOf(map.get("quantity").toString());
    }

    @Override
    public String insertTtSalesPartItemPO(TtSalesPartItemDTO salesPartItemDTO) throws ServiceBizException {
        TtSalesPartItemPO itemPo = new TtSalesPartItemPO();
        setTtSalesPartItemPO(itemPo,salesPartItemDTO);
        if(!itemPo.saveIt()) return null;
        return itemPo.getString("sales_part_no");
    }
    
    /**
     * 设置TroubleDescPO属性
     * 
     * @author chenwei
     * @date 2017年3月24日
     * @param typo
     * @param pyto
     */

    public void setTtSalesPartItemPO(TtSalesPartItemPO typo, TtSalesPartItemDTO pyto) {
        typo.setString("DEALER_CODE", pyto.getDealerCode());
        typo.setInteger("BATCH_NO", pyto.getBatchNo());
        typo.setString("CHARGE_PARTITION_CODE", pyto.getChargePartitionCode());
        typo.setFloat("DISCOUNT", pyto.getDiscount());
        typo.setString("MANAGE_SORT_CODE", pyto.getManageSortCode());
        typo.setString("PART_BATCH_NO", pyto.getPartBatchNo());
        typo.setString("PART_NAME", pyto.getPartName());
        typo.setString("PART_NO", pyto.getPartNo());
        typo.setFloat("PART_QUANTITY", pyto.getPartQuantity());
        typo.setDouble("PART_SALES_PRICE", pyto.getPartSalesPrice());
        typo.setDouble("PART_SALES_AMOUNT", pyto.getPartSalesAmount());
        typo.setDouble("PART_COST_PRICE", pyto.getPartCostPrice());
        typo.setDouble("PART_COST_AMOUNT", pyto.getPartCostAmount());
        typo.setDouble("SALES_AMOUNT", pyto.getSalesAmount());
        typo.setFloat("SALES_DISCOUNT", pyto.getSalesDiscount());
        typo.setString("RECEIVER", pyto.getReceiver());
        typo.setString("OLD_SALES_PART_NO", pyto.getOldSalesPartNo());
        typo.setString("SALES_PART_NO", pyto.getSalesPartNo());
        typo.setDate("SEND_TIME", pyto.getSendTime());
        typo.setString("STORAGE_CODE", pyto.getStorageCode());
        typo.setString("STORAGE_POSITION_CODE", pyto.getStoragePositionCode());
        typo.setString("UNIT_CODE", pyto.getUnitCode());
        typo.setFloat("PRICE_RATE", pyto.getPriceRate());
        typo.setString("PRICE_TYPE", pyto.getPriceType());
        typo.setInteger("IS_DISCOUNT", pyto.getIsDiscount());
    }
    

}
