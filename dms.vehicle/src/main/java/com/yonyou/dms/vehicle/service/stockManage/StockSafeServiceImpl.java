
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : StockSafeServiceImpl.java
*
* @Author : yangjie
*
* @Date : 2016年12月28日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月28日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.service.stockManage;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.common.domains.PO.monitor.OperateLogPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VsStockEntryItemPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 库存维护service
 * 
 * @author yangjie
 * @date 2016年12月28日
 */
@Service
@SuppressWarnings({ "rawtypes", "unchecked" })
public class StockSafeServiceImpl implements StockSafeService {

    /**
     * @author yangjie
     * @date 2016年12月28日
     * @param queryParam
     * @return 遍历所有结果集
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#findAll(java.util.Map)
     */
    @Override
    public PageInfoDto findAll(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = "";
        if (StringUtils.isNotBlank(queryParam.get("STOCK_STATUS"))
            && String.valueOf(DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY).equals(queryParam.get("STOCK_STATUS"))) {
            sql = findAllByOnWay(null, queryParam);
        } else {
            sql = findAllByNotOnWay(null, queryParam);
        }
        return OemDAOUtil.pageQuery(sql, params);
    }

    /**
     * TODO 查询库存为在途车辆
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param params
     * @param queryParam
     * @return
     */
    public String findAllByOnWay(List<Object> params, Map<String, String> queryParam) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT COALESCE(tr.color_name,B.color_name) AS color_name,b.brand_name,b.series_name,b.model_name,b.config_name,db.dealer_shortname,"
                  + DictCodeConstants.STATUS_IS_NOT + " AS is_lock," + DictCodeConstants.STATUS_IS_NOT);
        sb.append(" AS is_refitting_car,a.ec_order_no,c.vendor_code,c.po_no,b.year_model AS model_year,b.oil_type,IFNULL(b.wholesale_directive_price,0) as wholesale_directive_price,a.is_direct,a.engine_no,a.vs_purchase_date,b.product_name,a.product_code,0 AS is_selected,a.vin,a.remark,'' AS storage_code,'' AS storage_position_code,b.brand_code,b.series_code,");
        sb.append("b.model_code,b.config_code,b.color_code,b.product_type, '' AS dispatched_status, '"
                  + DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY);
        sb.append("' AS stock_status, a.purchase_price as purchase_price,a.additional_cost,a.manufacture_date, '' AS last_stock_in_by,a.key_number, a.certificate_number,");
        sb.append("CASE WHEN a.has_certificate=" + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS has_certificate,'' AS mar_status," + DictCodeConstants.STATUS_IS_NOT);
        sb.append(" AS is_secondhand, a.oem_tag, " + DictCodeConstants.STATUS_IS_NOT + " AS is_vip,");
        sb.append(DictCodeConstants.STATUS_IS_NOT + " AS is_test_drive_car,");
        sb.append("a.is_consigned," + DictCodeConstants.STATUS_IS_NOT);
        sb.append(" AS  is_promotion,'' AS  is_purchase_return,'' AS first_stock_in_date,a.factory_date,'' AS stock_in_type,'' AS se_no,'' AS stock_out_type,'' AS sd_no,'' AS stock_out_date ,a.dealer_code ,''  AS storage_code_name,b.oem_directive_price as oem_directive_price,b.directive_price as directive_price,'' AS   vsn ,COALESCE(B.exhaust_quantity,'')  AS exhaust_quantity ,0 AS discharge_standard,'' AS warranty_number, a.supervise_type, a.financial_bill_no FROM TT_VS_SHIPPING_NOTIFY A ");
        sb.append("LEFT JOIN (SELECT tx.*,tk.color_name AS color_name,tq.BRAND_NAME AS BRAND_NAME,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_NAME AS CONFIG_NAME FROM (" + CommonConstants.VM_VS_PRODUCT);
        sb.append(") tx LEFT JOIN tm_brand tq ON tx.BRAND_CODE = tq.BRAND_CODE  AND tx.DEALER_CODE = tq.dealer_code LEFT JOIN tm_color tk ON tx.color_code = tk.color_code AND tx.DEALER_CODE = tk.dealer_code LEFT JOIN tm_series ts ON tx.BRAND_CODE = ts.BRAND_CODE AND tx.DEALER_CODE = ts.dealer_code AND tx.SERIES_CODE = ts.SERIES_CODE LEFT JOIN tm_model tm ON tx.SERIES_CODE = tm.SERIES_CODE AND tx.BRAND_CODE = tm.BRAND_CODE AND tx.DEALER_CODE = tm.dealer_code AND tx.MODEL_CODE = tm.MODEL_CODE LEFT JOIN tm_configuration tc ON tx.SERIES_CODE = tc.SERIES_CODE AND tx.BRAND_CODE = tc.BRAND_CODE AND tx.DEALER_CODE = tc.dealer_code AND tx.MODEL_CODE = tc.MODEL_CODE AND tx.CONFIG_CODE = tc.CONFIG_CODE ");
        sb.append(") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE LEFT JOIN tm_color tr ON b.color_code=tr.color_code ");
        sb.append("LEFT JOIN TT_VS_STOCK_ENTRY_ITEM C ON C.DEALER_CODE=A.DEALER_CODE AND A.VIN=C.VIN LEFT JOIN tm_dealer_basicinfo db ON db.dealer_code=A.DEALER_CODE ");
        sb.append("WHERE A.DEALER_CODE IN (SELECT SHARE_ENTITY FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(") m WHERE m.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode());
        sb.append("' AND m.biz_code = 'UNIFIED_VIEW' ) ");
        sb.append("AND A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND A.D_KEY="+ CommonConstants.D_KEY);
        sb.append("  AND A.VIN NOT IN (SELECT VIN FROM TM_VS_STOCK WHERE DEALER_CODE IN (SELECT SHARE_ENTITY FROM ("+ CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(") t WHERE t.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode()+ "' AND t.biz_code = 'UNIFIED_VIEW' ) ");
        sb.append(" AND DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND D_KEY=" + CommonConstants.D_KEY + " ) ");
        this.sqlToLike(sb, queryParam.get("VIN"), "vin", "A");
        this.sqlToLike(sb, queryParam.get("PRODUCT_CODE"), "product_code", "A");
        this.sqlToLike(sb, queryParam.get("PRODUCT_NAME"), "product_name", "B");
        this.sqlToEquals(sb, queryParam.get("BRAND_CODE"), "brand_code", "B");
        this.sqlToEquals(sb, queryParam.get("SERIES_CODE"), "series_code", "B");
        this.sqlToEquals(sb, queryParam.get("MODEL_CODE"), "model_code", "B");
        this.sqlToEquals(sb, queryParam.get("VENDOR_CODE"), "vendor_code", "C");
        this.sqlToEquals(sb, queryParam.get("COLOR_CODE"), "color_code", "B");
        this.sqlToEquals(sb, queryParam.get("CONFIG_CODE"), "config_code", "B");
        this.sqlToEquals(sb, queryParam.get("IS_CONSIGNING"), "is_consigning", "A");
        this.sqlToEquals(sb, queryParam.get("HAS_CERTIFICATE"), "has_certificate", "A");
        this.sqlToEquals(sb, queryParam.get("KEY_NUMBER"), "key_number", "A");
        return sb.toString();
    }

    /**
     * TODO 查询库存为在途车辆-excel
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param params
     * @param queryParam
     * @return
     */
    public String findAllByOnWayToExcel(List<Object> params, Map<String, String> queryParam) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT COALESCE(tr.color_name,B.color_name) AS color_name,b.brand_name as brand_name,b.series_name as series_name,b.model_name as model_name,b.config_name as config_name,db.dealer_shortname AS dealer_shortname,'否' AS is_lock,'否' AS is_refitting_car,a.ec_order_no AS ec_order_no,c.vendor_code AS vendor_code,c.po_no AS po_no,b.year_model AS model_year,b.oil_type AS oil_type,IFNULL(b.wholesale_directive_price) as wholesale_directive_price,a.is_direct as is_direct,a.engine_no as engine_no,a.vs_purchase_date as vs_purchase_date,b.product_name AS product_name,a.product_code AS product_code,0 AS is_selected,a.vin AS vin,a.remark AS remark,'' AS storage_code,'' AS storage_position_code,b.brand_code as brand_code,b.series_code as series_code,");
        sb.append("b.model_code as model_code,b.config_code AS config_code,b.color_code AS color_code,b.product_type as product_type, '' AS dispatched_status, '否' AS stock_status, a.purchase_price as purchase_price,a.additional_cost as additional_cost,a.manufacture_date as manufacture_date, '' AS last_stock_in_by,a.key_number, a.certificate_number as certificate_number,");
        sb.append("CASE WHEN a.has_certificate=" + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN '是' ELSE '否' END AS has_certificate,'' AS mar_status,'否' AS is_secondhand,CASE WHEN a.oem_tag="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN '是' ELSE '否' END as oem_tag, '否' AS is_vip,'否' AS is_test_drive_car,");
        sb.append("a.is_consigned,'否' AS  is_promotion,'' AS  is_purchase_return,'' AS first_stock_in_date,a.factory_date as factory_date,'' AS stock_in_type,'' AS se_no,'' AS stock_out_type,'' AS sd_no,'' AS stock_out_date ,a.dealer_code as dealer_code ,''  AS storage_code_name,b.oem_directive_price as oem_directive_price,b.directive_price as directive_price,'' AS   vsn ,COALESCE(B.exhaust_quantity,'')  AS exhaust_quantity ,0 AS discharge_standard,'' AS warranty_number, a.supervise_type as supervise_type, a.financial_bill_no as financial_bill_no FROM TT_VS_SHIPPING_NOTIFY A ");
        sb.append("LEFT JOIN (SELECT tx.*,tk.color_name AS color_name,tq.BRAND_NAME AS BRAND_NAME,tq.SERIES_NAME AS SERIES_NAME,tq.MODEL_NAME AS MODEL_NAME,tq.CONFIG_NAME AS CONFIG_NAME FROM ("
                  + CommonConstants.VM_VS_PRODUCT);
        sb.append(") tx LEFT JOIN (SELECT tb.DEALER_CODE,tb.BRAND_CODE AS BRAND_CODE,tb.BRAND_NAME AS BRAND_NAME,ts.SERIES_CODE AS SERIES_CODE,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_CODE AS MODEL_CODE,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_CODE AS CONFIG_CODE,tc.CONFIG_NAME AS CONFIG_NAME FROM tm_brand tb ");
        sb.append(" LEFT JOIN tm_series ts ON tb.BRAND_CODE=ts.BRAND_CODE LEFT JOIN tm_model tm ON ts.SERIES_CODE=tm.SERIES_CODE LEFT JOIN tm_configuration tc ON tm.MODEL_CODE=tc.MODEL_CODE ) tq ON tx.BRAND_CODE=tq.BRAND_CODE AND tx.SERIES_CODE=tq.SERIES_CODE AND tx.MODEL_CODE=tq.MODEL_CODE AND tx.CONFIG_CODE=tq.CONFIG_CODE LEFT JOIN tm_color tk ON tx.color_code=tk.color_code");
        sb.append(") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE LEFT JOIN tm_color tr ON a.color_code=tr.color_code ");
        sb.append("LEFT JOIN TT_VS_STOCK_ENTRY_ITEM C ON C.DEALER_CODE=A.DEALER_CODE AND A.VIN=C.VIN LEFT JOIN tm_dealer_basicinfo db ON db.dealer_code=A.DEALER_CODE ");
        sb.append("WHERE A.DEALER_CODE IN (SELECT SHARE_ENTITY FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(") m WHERE m.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode()
                  + "' AND m.biz_code = 'UNIFIED_VIEW' ) ");
        sb.append("AND A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND A.D_KEY="+ CommonConstants.D_KEY);
        sb.append("  AND A.VIN NOT IN (SELECT VIN FROM TM_VS_STOCK WHERE DEALER_CODE IN (SELECT SHARE_ENTITY FROM ("+ CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(") t WHERE t.DEALER_CODE ='" + FrameworkUtil.getLoginInfo().getDealerCode()
                  + "' AND t.biz_code = 'UNIFIED_VIEW' ) ");
        sb.append(" AND DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND D_KEY="
                  + CommonConstants.D_KEY + " ) ");
        this.sqlToLike(sb, queryParam.get("VIN"), "vin", "A");
        this.sqlToLike(sb, queryParam.get("PRODUCT_CODE"), "product_code", "A");
        this.sqlToLike(sb, queryParam.get("PRODUCT_NAME"), "product_name", "B");
        this.sqlToEquals(sb, queryParam.get("BRAND_CODE"), "brand_code", "B");
        this.sqlToEquals(sb, queryParam.get("SERIES_CODE"), "series_code", "B");
        this.sqlToEquals(sb, queryParam.get("MODEL_CODE"), "model_code", "B");
        this.sqlToEquals(sb, queryParam.get("VENDOR_CODE"), "vendor_code", "C");
        this.sqlToEquals(sb, queryParam.get("COLOR_CODE"), "color_code", "B");
        this.sqlToEquals(sb, queryParam.get("CONFIG_CODE"), "config_code", "B");
        this.sqlToEquals(sb, queryParam.get("IS_CONSIGNING"), "is_consigning", "A");
        this.sqlToEquals(sb, queryParam.get("HAS_CERTIFICATE"), "has_certificate", "A");
        this.sqlToEquals(sb, queryParam.get("KEY_NUMBER"), "key_number", "A");
        return sb.toString();
    }

    /**
     * TODO 查询库存为非在途车辆 findAllByNotOnWay
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param params
     * @param queryParam
     * @return
     */
    public String findAllByNotOnWay(List<Object> params, Map<String, String> queryParam) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT COALESCE(tr.color_name,B.color_name) AS color_name,b.brand_name,b.series_name,b.model_name,b.config_name,db.dealer_shortname,c.po_no,c.vendor_code,a.ec_order_no,CASE WHEN a.is_lock="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS is_lock,a.is_refitting_car,IFNULL(b.wholesale_directive_price,0) as wholesale_directive_price,b.oil_type,a.is_direct,a.engine_no,a.vs_purchase_date,b.product_name,b.year_model AS model_year,b.product_code,0 AS is_selected,a.vin,a.remark,a.storage_code,a.storage_position_code,b.brand_code,b.series_code,");
        sb.append("b.model_code,b.config_code,COALESCE(a.color_code,b.color_code) AS color_code,b.product_type, a.dispatched_status, a.stock_status,");
        sb.append("a.purchase_price,a.additional_cost,a.manufacture_date, a.last_stock_in_by,a.key_number,a.certificate_number,CASE WHEN a.has_certificate="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS has_certificate,a.mar_status,CASE WHEN a.is_secondhand="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS is_secondhand, a.oem_tag,a.factory_date,");
        sb.append("CASE WHEN a.is_vip=" + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS is_vip,CASE WHEN a.is_test_drive_car=" + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS is_test_drive_car,a.is_consigned,CASE WHEN a.is_promotion="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS is_promotion,a.is_purchase_return,a.first_stock_in_date,a.stock_in_type,a.se_no,a.stock_out_type,a.sd_no,(CASE WHEN a.first_stock_out_date IS NULL THEN a.latest_stock_out_date ELSE a.first_stock_out_date END) AS stock_out_date ");
        sb.append(",a.dealer_code ,vst.storage_name AS storage_code_name,b.oem_directive_price,CASE WHEN is_price_adjusted=" + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN a.directive_price ELSE b.directive_price END AS directive_price,a.vsn,COALESCE(B.exhaust_quantity,a.exhaust_quantity)  AS exhaust_quantity,a.discharge_standard,a.warranty_number,a.supervise_type,a.financial_bill_no FROM TM_VS_STOCK A ");
        sb.append("LEFT JOIN (SELECT tx.*,tk.color_name AS color_name,tq.BRAND_NAME AS BRAND_NAME,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_NAME AS CONFIG_NAME FROM ("
                  + CommonConstants.VM_VS_PRODUCT);
        sb.append(") tx LEFT JOIN tm_brand tq ON tx.BRAND_CODE = tq.BRAND_CODE  AND tx.DEALER_CODE = tq.dealer_code LEFT JOIN tm_color tk ON tx.color_code = tk.color_code AND tx.DEALER_CODE = tk.dealer_code LEFT JOIN tm_series ts ON tx.BRAND_CODE = ts.BRAND_CODE AND tx.DEALER_CODE = ts.dealer_code AND tx.SERIES_CODE = ts.SERIES_CODE LEFT JOIN tm_model tm ON tx.SERIES_CODE = tm.SERIES_CODE AND tx.BRAND_CODE = tm.BRAND_CODE AND tx.DEALER_CODE = tm.dealer_code AND tx.MODEL_CODE = tm.MODEL_CODE LEFT JOIN tm_configuration tc ON tx.SERIES_CODE = tc.SERIES_CODE AND tx.BRAND_CODE = tc.BRAND_CODE AND tx.DEALER_CODE = tc.dealer_code AND tx.MODEL_CODE = tc.MODEL_CODE AND tx.CONFIG_CODE = tc.CONFIG_CODE ");
        sb.append(") B ON A.DEALER_CODE=B.dealer_code AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE LEFT JOIN tm_color tr ON a.color_code=tr.color_code  AND a.dealer_code = tr.dealer_code  LEFT JOIN ("
                  + CommonConstants.VM_STORAGE);
        sb.append(") vst ON vst.dealer_code=A.DEALER_CODE AND vst.storage_code=a.storage_code LEFT JOIN TT_VS_STOCK_ENTRY_ITEM C ON C.dealer_code=A.DEALER_CODE AND A.VIN=C.VIN AND C.SE_NO=A.SE_NO ");
        sb.append("LEFT JOIN tm_dealer_basicinfo db ON db.dealer_code=A.DEALER_CODE WHERE A.DEALER_CODE IN (SELECT SHARE_ENTITY FROM ("
                  + CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(") t WHERE t.dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode()
                  + "' AND t.biz_code = 'UNIFIED_VIEW' ) AND A.dealer_code = '"
                  + FrameworkUtil.getLoginInfo().getDealerCode() + "' AND A.D_KEY=" + CommonConstants.D_KEY + " ");
        this.sqlToLike(sb, queryParam.get("PRODUCT_CODE"), "product_code", "A");
        this.sqlToLike(sb, queryParam.get("PRODUCT_NAME"), "product_name", "B");
        this.sqlToEquals(sb, queryParam.get("STOCK_STATUS"), "stock_status", "A");
        this.sqlToEquals(sb, queryParam.get("DEALER_CODE"), "dealer_code", "db");
        this.sqlToEquals(sb, queryParam.get("STOCK_IN_TYPE"), "stock_in_type", "A");
        this.sqlToEquals(sb, queryParam.get("STOCK_OUT_TYPE"), "stock_out_type", "A");
        this.sqlToDate(sb, queryParam.get("OUTBEGIN"), queryParam.get("OUTEND"),
                       "CASE WHEN A.FIRST_STOCK_OUT_DATE IS  NULL THEN A.LATEST_STOCK_OUT_DATE ELSE A.LATEST_STOCK_OUT_DATE END ",
                       null);
        this.sqlToEquals(sb, queryParam.get("STORAGE_CODE"), "storage_code", "A");
        // if (!StringUtils.isNotBlank(queryParam.get("storage_code"))) {
        // String storageCodeitem = this.getStorageByUserId(FrameworkUtil.getLoginInfo().getDealerCode(),
        // FrameworkUtil.getLoginInfo().getUserId());
        // if (storageCodeitem.length() > 0) {
        // sb.append(" AND (");
        // String[] storageTure = storageCodeitem.split(",");
        // int count = storageTure.length;
        // for (int i = 0; i < count; i++) {
        // sb.append(" A.STORAGE_CODE=" + storageTure[i] + " ");
        // if (i < (count - 1)) {
        // sb.append(" OR ");
        // }
        // }
        // sb.append(" ) ");
        // } else sb.append(" AND A.STORAGE_CODE= ''");
        // }
        this.sqlToEquals(sb, queryParam.get("BRAND_CODE"), "brand_code", "B");
        this.sqlToEquals(sb, queryParam.get("SERIES_CODE"), "series_code", "B");
        this.sqlToEquals(sb, queryParam.get("MODEL_CODE"), "model_code", "B");
        this.sqlToEquals(sb, queryParam.get("VENDOR_CODE"), "vendor_code", "C");
        this.sqlToEquals(sb, queryParam.get("COLOR_CODE"), "color_code", "A");
        this.sqlToLike(sb, queryParam.get("VIN"), "vin", "A");
        this.sqlToEquals(sb, queryParam.get("STOCK_STATUS"), "stock_status", "A");
        this.sqlToEquals(sb, queryParam.get("MAR_STATUS"), "mar_status", "A");
        this.sqlToEquals(sb, queryParam.get("DISPATCHED_STATUS"), "dispatched_status", "A");
        this.sqlToEquals(sb, queryParam.get("IS_REFITTING_CAR"), "is_Refitting_Car", "A");
        this.sqlToEquals(sb, queryParam.get("IS_VIP"), "IS_VIP", "A");
        this.sqlToEquals(sb, queryParam.get("OEM_TAG"), "OEM_TAG", "A");
        this.sqlToEquals(sb, queryParam.get("IS_TEST_DRIVE_CAR"), "IS_TEST_DRIVE_CAR", "A");
        this.sqlToEquals(sb, queryParam.get("IS_CONSIGNED"), "IS_CONSIGNED", "A");
        this.sqlToEquals(sb, queryParam.get("IS_PURCHASE_RETURN"), "IS_PURCHASE_RETURN", "A");
        this.sqlToEquals(sb, queryParam.get("IS_SECONDHAND"), "IS_SECONDHAND", "A");
        this.sqlToEquals(sb, queryParam.get("HAS_CERTIFICATE"), "HAS_CERTIFICATE", "A");
        this.sqlToLike(sb, queryParam.get("KEY_NUMBER"), "KEY_NUMBER", "A");
        this.sqlToEquals(sb, queryParam.get("IS_PROMOTION"), "IS_PROMOTION", "A");
        this.sqlToEquals(sb, queryParam.get("CONFIG_CODE"), "CONFIG_CODE", "B");
        this.sqlToDate(sb, queryParam.get("INBEGIN"), queryParam.get("INEND"), "FIRST_STOCK_IN_DATE", "A");
        return sb.toString();
    }

    /**
     * TODO 查询库存为非在途车辆 findAllByNotOnWay
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param params
     * @param queryParam
     * @return
     */
    public String findAllByNotOnWayToExcel(List<Object> params, Map<String, String> queryParam) {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT COALESCE(tr.color_name,B.color_name) AS color_name,b.brand_name as brand_name,b.series_name as series_name,b.model_name as model_name,b.config_name as config_name,db.dealer_shortname,c.po_no AS po_no,c.vendor_code,a.ec_order_no AS ec_order_no,CASE WHEN a.is_lock="
                  + DictCodeConstants.STATUS_IS_YES + " THEN '是' ELSE '否' END AS is_lock,CASE WHEN a.is_refitting_car="
                  + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN '是' ELSE '否' END AS is_refitting_car,IFNULL(b.wholesale_directive_price,0) AS wholesale_directive_price ,b.oil_type,a.is_direct,a.engine_no AS engine_no,a.vs_purchase_date AS vs_purchase_date,b.product_name AS product_name,b.year_model AS model_year,b.product_code AS product_code,0 AS is_selected,a.vin AS vin,a.remark AS remark,a.storage_code,a.storage_position_code AS storage_position_code,b.brand_code AS brand_code,b.series_code AS series_code,");
        sb.append("b.model_code AS model_code,b.config_code AS config_code,COALESCE(a.color_code,b.color_code) AS color_code,b.product_type, a.dispatched_status, a.stock_status AS stock_status,");
        sb.append("a.purchase_price AS purchase_price,a.additional_cost,a.manufacture_date AS manufacture_date, a.last_stock_in_by,a.key_number AS key_number,a.certificate_number AS certificate_number,CASE WHEN a.has_certificate="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN '是' ELSE '否' END AS has_certificate,a.mar_status AS mar_status,CASE WHEN a.is_secondhand="+ DictCodeConstants.STATUS_IS_YES );
        sb.append(" THEN '是' ELSE '否' END AS is_secondhand,CASE WHEN a.oem_tag="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN '是' ELSE '否' END AS oem_tag,a.factory_date AS factory_date,");
        sb.append("CASE WHEN a.is_vip=" + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN '是' ELSE '否' END AS is_vip,CASE WHEN a.is_test_drive_car=" + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN '是' ELSE '否' END AS is_test_drive_car,CASE WHEN a.is_consigned=");
        sb.append(DictCodeConstants.STATUS_IS_YES + " THEN '是' ELSE '否' END AS is_consigned,CASE WHEN a.is_promotion="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN '是' ELSE '否' END AS is_promotion,a.is_purchase_return,a.first_stock_in_date AS first_stock_in_date,a.stock_in_type AS stock_in_type,a.se_no AS se_no,a.stock_out_type AS stock_out_type,a.sd_no AS sd_no,(CASE WHEN a.first_stock_out_date IS NULL THEN a.latest_stock_out_date ELSE a.first_stock_out_date END) AS stock_out_date ");
        sb.append(",a.dealer_code ,vst.storage_name AS storage_code_name,b.oem_directive_price AS oem_directive_price,CASE WHEN is_price_adjusted="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN a.directive_price ELSE b.directive_price END AS directive_price,a.vsn AS vsn,COALESCE(B.exhaust_quantity,a.exhaust_quantity) as exhaust_quantity,a.discharge_standard AS discharge_standard,a.warranty_number,a.supervise_type,a.financial_bill_no FROM TM_VS_STOCK A ");
        sb.append("LEFT JOIN (SELECT tx.*,tk.color_name AS color_name,tq.BRAND_NAME AS BRAND_NAME,tq.SERIES_NAME AS SERIES_NAME,tq.MODEL_NAME AS MODEL_NAME,tq.CONFIG_NAME AS CONFIG_NAME FROM ("
                  + CommonConstants.VM_VS_PRODUCT);
        sb.append(") tx LEFT JOIN (SELECT tb.DEALER_CODE,tb.BRAND_CODE AS BRAND_CODE,tb.BRAND_NAME AS BRAND_NAME,ts.SERIES_CODE AS SERIES_CODE,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_CODE AS MODEL_CODE,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_CODE AS CONFIG_CODE,tc.CONFIG_NAME AS CONFIG_NAME FROM tm_brand tb ");
        sb.append(" LEFT JOIN tm_series ts ON tb.BRAND_CODE=ts.BRAND_CODE LEFT JOIN tm_model tm ON ts.SERIES_CODE=tm.SERIES_CODE LEFT JOIN tm_configuration tc ON tm.MODEL_CODE=tc.MODEL_CODE ) tq ON tx.BRAND_CODE=tq.BRAND_CODE AND tx.SERIES_CODE=tq.SERIES_CODE AND tx.MODEL_CODE=tq.MODEL_CODE AND tx.CONFIG_CODE=tq.CONFIG_CODE LEFT JOIN tm_color tk ON tx.color_code=tk.color_code");
        sb.append(") B ON A.DEALER_CODE=B.dealer_code AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE LEFT JOIN tm_color tr ON a.color_code=tr.color_code  LEFT JOIN ("
                  + CommonConstants.VM_STORAGE);
        sb.append(") vst ON vst.dealer_code=A.DEALER_CODE AND vst.storage_code=a.storage_code LEFT JOIN TT_VS_STOCK_ENTRY_ITEM C ON C.dealer_code=A.DEALER_CODE AND A.VIN=C.VIN AND C.SE_NO=A.SE_NO ");
        sb.append("LEFT JOIN tm_dealer_basicinfo db ON db.dealer_code=A.DEALER_CODE WHERE A.DEALER_CODE IN (SELECT SHARE_ENTITY FROM ("
                  + CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(") t WHERE t.dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode());
        sb.append("' AND t.biz_code = 'UNIFIED_VIEW' ) AND A.dealer_code = '"+ FrameworkUtil.getLoginInfo().getDealerCode());
        sb.append("' AND A.D_KEY=" + CommonConstants.D_KEY + " ");
        this.sqlToLike(sb, queryParam.get("PRODUCT_CODE"), "product_code", "A");
        this.sqlToLike(sb, queryParam.get("PRODUCT_NAME"), "product_name", "B");
        this.sqlToEquals(sb, queryParam.get("STOCK_STATUS"), "stock_status", "A");
        this.sqlToEquals(sb, queryParam.get("DEALER_CODE"), "dealer_code", "db");
        this.sqlToEquals(sb, queryParam.get("STOCK_IN_TYPE"), "stock_in_type", "A");
        this.sqlToEquals(sb, queryParam.get("STOCK_OUT_TYPE"), "stock_out_type", "A");
        this.sqlToDate(sb, queryParam.get("OUTBEGIN"), queryParam.get("OUTEND"),
                       "CASE WHEN A.FIRST_STOCK_OUT_DATE IS  NULL THEN A.LATEST_STOCK_OUT_DATE ELSE A.LATEST_STOCK_OUT_DATE END ",
                       null);
        this.sqlToEquals(sb, queryParam.get("STORAGE_CODE"), "storage_code", "A");
        // if (!StringUtils.isNotBlank(queryParam.get("storage_code"))) {
        // String storageCodeitem = this.getStorageByUserId(FrameworkUtil.getLoginInfo().getDealerCode(),
        // FrameworkUtil.getLoginInfo().getUserId());
        // if (storageCodeitem.length() > 0) {
        // sb.append(" AND (");
        // String[] storageTure = storageCodeitem.split(",");
        // int count = storageTure.length;
        // for (int i = 0; i < count; i++) {
        // sb.append(" A.STORAGE_CODE=" + storageTure[i] + " ");
        // if (i < (count - 1)) {
        // sb.append(" OR ");
        // }
        // }
        // sb.append(" ) ");
        // } else sb.append(" AND A.STORAGE_CODE= ''");
        // }
        this.sqlToEquals(sb, queryParam.get("BRAND_CODE"), "brand_code", "B");
        this.sqlToEquals(sb, queryParam.get("SERIES_CODE"), "series_code", "B");
        this.sqlToEquals(sb, queryParam.get("MODEL_CODE"), "model_code", "B");
        this.sqlToEquals(sb, queryParam.get("VENDOR_CODE"), "vendor_code", "C");
        this.sqlToEquals(sb, queryParam.get("COLOR_CODE"), "color_code", "A");
        this.sqlToLike(sb, queryParam.get("VIN"), "vin", "A");
        this.sqlToEquals(sb, queryParam.get("STOCK_STATUS"), "stock_status", "A");
        this.sqlToEquals(sb, queryParam.get("MAR_STATUS"), "mar_status", "A");
        this.sqlToEquals(sb, queryParam.get("DISPATCHED_STATUS"), "dispatched_status", "A");
        this.sqlToEquals(sb, queryParam.get("IS_REFITTING_CAR"), "is_Refitting_Car", "A");
        this.sqlToEquals(sb, queryParam.get("IS_VIP"), "IS_VIP", "A");
        this.sqlToEquals(sb, queryParam.get("OEM_TAG"), "OEM_TAG", "A");
        this.sqlToEquals(sb, queryParam.get("IS_TEST_DRIVE_CAR"), "IS_TEST_DRIVE_CAR", "A");
        this.sqlToEquals(sb, queryParam.get("IS_CONSIGNED"), "IS_CONSIGNED", "A");
        this.sqlToEquals(sb, queryParam.get("IS_PURCHASE_RETURN"), "IS_PURCHASE_RETURN", "A");
        this.sqlToEquals(sb, queryParam.get("IS_SECONDHAND"), "IS_SECONDHAND", "A");
        this.sqlToEquals(sb, queryParam.get("HAS_CERTIFICATE"), "HAS_CERTIFICATE", "A");
        this.sqlToLike(sb, queryParam.get("KEY_NUMBER"), "KEY_NUMBER", "A");
        this.sqlToEquals(sb, queryParam.get("IS_PROMOTION"), "IS_PROMOTION", "A");
        this.sqlToEquals(sb, queryParam.get("CONFIG_CODE"), "CONFIG_CODE", "B");
        this.sqlToDate(sb, queryParam.get("INBEGIN"), queryParam.get("INEND"), "FIRST_STOCK_IN_DATE", "A");
        return sb.toString();
    }

    /**
     * TODO 拼接sql语句模糊查询
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param param 查询条件
     * @param field 查询的字段
     * @param alias 表的别名
     * @return
     */
    public void sqlToLike(StringBuilder sb, String param, String field, String alias) {
        if (StringUtils.isNotBlank(param)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" LIKE '%" + param + "%' ");
        }
    }

    /**
     * TODO 拼接sql语句等量查询
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param param 查询条件
     * @param field 查询的字段
     * @param alias 表的别名
     * @return
     */
    public void sqlToEquals(StringBuilder sb, String param, String field, String alias) {
        if (StringUtils.isNotBlank(param)) {
            sb.append(" AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" = '" + param + "' ");
        }
    }

    /**
     * TODO 拼接sql语句时间查询(单个字段)
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param begin 开始时间
     * @param end 结束时间
     * @param field 查询的字段
     * @param alias 表的别名
     * @return
     */
    public void sqlToDate(StringBuilder sb, String begin, String end, String field, String alias) {
        if (StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
            sb.append(" AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" between '" + begin + "' AND '" + end + "' ");
        } else if (StringUtils.isNotBlank(begin) && !StringUtils.isNotBlank(end)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" >= '" + begin + "' ");
        } else if (!StringUtils.isNotBlank(begin) && StringUtils.isNotBlank(end)) {
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" <= '" + end + "' ");
        }
    }

    /**
     * 根据用户ID查询出他能操作的仓库 TODO description
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param dealerCode
     * @param userId
     * @return
     */
    public String getStorageByUserId(String dealerCode, Long userId) {
        String str = "";
        String sql = "select CTRL_CODE from tm_user_ctrl where dealer_code = ? and user_id = ?";
        List queryParam = new ArrayList();
        queryParam.add(dealerCode);
        queryParam.add(userId);
        List<Map> result = DAOUtil.findAll(sql, queryParam);
        if (result.size() > 0) {
            for (int i = 0; i < result.size(); i++) {
                Map map = result.get(i);
                if (i > 0) {
                    str = str + ",";
                }
                str = str + "'" + map.get("CTRL_CODE").toString().substring(4, map.get("CTRL_CODE").toString().length())
                      + "'";
            }
        }
        if ("".equals(str)) return "''";
        else return str;
    }

    /**
     * 查询经销商简称下拉框
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#findDealerInfo()
     */
    @Override
    public List<Map> findDealerInfo() throws ServiceBizException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT a.share_entity AS dealer_code,a.BIZ_CODE,a.RELATIONSHIP_MODE,b.DEALER_SHORTNAME,b.DEALER_NAME FROM ("
                  + CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(")  a LEFT JOIN TM_dealer_BASICINFO  b ON b.dealer_code=a.share_entity WHERE   a.dealer_code='"
                  + FrameworkUtil.getLoginInfo().getDealerCode() + "' GROUP BY b.DEALER_SHORTNAME");
        List<Map> list = DAOUtil.findAll(sb.toString(), null);
        return list;
    }

    /**
     * 根据经销商编号查询仓库
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#findStorageInfo(java.lang.String)
     */
    @Override
    public List<Map> findStorageInfo(String id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT A.* FROM (" + CommonConstants.VM_STORAGE);
        sb.append(") A LEFT JOIN tm_user_ctrl B ON B.DEALER_CODE = A.DEALER_CODE AND '4010'||A.STORAGE_CODE = B.CTRL_CODE");
        sb.append(" WHERE A.DEALER_CODE = '" + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
        sb.append(" AND (A.CJ_TAG!=" + DictCodeConstants.IS_YES
                  + " OR A.CJ_TAG IS NULL) AND  A.STORAGE_NAME IS NOT NULL AND A.STORAGE_NAME !=''");
        List<Map> list = DAOUtil.findAll(sb.toString(), null);
        return list;
    }

    /**
     * 下拉框查询颜色
     * 
     * @author yangjie
     * @date 2016年12月28日
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#findColor()
     */
    @Override
    public List<Map> findColor() throws ServiceBizException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT * FROM (" + CommonConstants.VM_COLOR);
        sb.append(") A WHERE A.DEALER_CODE IN (SELECT SHARE_ENTITY FROM (" + CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(") B WHERE B.DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode()
                  + "' AND B.BIZ_CODE = 'UNIFIED_VIEW')");
        List<Map> list = DAOUtil.findAll(sb.toString(), null);
        return list;
    }

    /**
     * 批量修改实现
     * 
     * @author yangjie
     * @date 2016年12月31日
     * @param list
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#batchModify(java.util.List)
     */
    @Override
    public Integer batchModify(List<Map<String, String>> list) throws ServiceBizException {
        if (list.size() != 0) {
        	try {
        		for (Map<String, String> map : list) {
        			VsStockPO po = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
        					map.get("vin"));
					setMapToPO(map, po);
						
					// 实时判断库存状态
					VsStockPO vsp = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),map.get("vin"));
					if(vsp!=null){
						if(vsp.getInteger("STOCK_STATUS")!=0&&vsp.getInteger("STOCK_STATUS")==DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE){
							if(vsp.getString("WARRANTY_NUMBER")!=null){
								po.setString("WARRANTY_NUMBER", vsp.getString("WARRANTY_NUMBER"));
								TmVehiclePO tvp = TmVehiclePO.findFirst("VIN = ?", map.get("vin"));
								tvp.setString("WARRANTY_NUMBER", vsp.getString("WARRANTY_NUMBER"));
								tvp.saveIt();
							}
						}
					}
        			po.saveIt();
        		}
        		return 0;//修改成功
			} catch (Exception e) {
        		return 1;//修改异常
			}
        } else {
        	return 2;//请选择修改项
        }
    }

    /**
     * TODO 将map数据存入po对象
     * 
     * @author yangjie
     * @date 2016年12月31日
     * @param map
     * @param po
     */
    public void setMapToPO(Map<String, String> map, VsStockPO po){
        po.setString("VIN", map.get("vin"));
        po.setString("IS_LOCK", map.get("isLock"));
        po.setString("IS_VIP", map.get("isVip"));
        po.setString("IS_TEST_DRIVE_CAR", map.get("isTestDriveCar"));
        po.setString("HAS_CERTIFICATE", map.get("hasCertificate"));
        po.setString("IS_PROMOTION", map.get("isPromotion"));
        po.setString("IS_SECONDHAND", map.get("isSecondhand"));
        po.setString("MAR_STATUS", map.get("marStatus"));
        po.setString("ENGINE_NO", map.get("engineNo"));
        po.setString("CERTIFICATE_NUMBER", map.get("certificateNumber"));
        po.setString("KEY_NUMBER", map.get("keyNumber"));
        po.set("VS_PURCHASE_DATE", new Timestamp(Long.parseLong(map.get("vsPurchaseDate"))));
        po.setString("PURCHASE_PRICE", map.get("purchasePrice"));
        po.setString("REMARK", map.get("remark"));
    }

    /**
     * TODO 根据VIN查询详细信息
     * 
     * @author yangjie
     * @date 2016年12月31日
     * @param vin 主键
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#findByVin(java.lang.String)
     */
    @Override
    public Map<String, Object> findByVin(String vin) throws ServiceBizException {
        StringBuilder sb = new StringBuilder();
        sb.append("SELECT DISTINCT COALESCE(tmc.color_name, b.color_name) AS color_name,b.brand_name,b.series_name,b.model_name,b.config_name,db.dealer_shortname,c.po_no,c.vendor_code,a.ec_order_no,CASE WHEN a.is_lock="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS is_lock,a.is_refitting_car,IFNULL(b.wholesale_directive_price,0) as wholesale_directive_price,b.oil_type,a.is_direct,a.engine_no,a.vs_purchase_date,b.product_name,b.year_model AS model_year,b.product_code,0 AS is_selected,a.vin,a.remark,a.storage_code,a.storage_position_code,b.brand_code,b.series_code,");
        sb.append("b.model_code,b.config_code,COALESCE(a.color_code,b.color_code) AS color_code,b.product_type, a.dispatched_status, a.stock_status,");
        sb.append("a.purchase_price,a.additional_cost,a.manufacture_date, a.last_stock_in_by,a.key_number,a.certificate_number,CASE WHEN a.has_certificate="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS has_certificate,a.mar_status,CASE WHEN a.is_secondhand=");
        sb.append(DictCodeConstants.STATUS_IS_YES + " THEN 10571001 END AS is_secondhand, a.oem_tag,a.factory_date,");
        sb.append("CASE WHEN a.is_vip=" + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS is_vip,CASE WHEN a.is_test_drive_car=" + DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS is_test_drive_car,a.is_consigned,CASE WHEN a.is_promotion="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN 10571001 END AS is_promotion,a.is_purchase_return,a.first_stock_in_date,a.stock_in_type,a.se_no,a.stock_out_type,a.sd_no,(CASE WHEN a.first_stock_out_date IS NULL THEN a.latest_stock_out_date ELSE a.first_stock_out_date END) AS stock_out_date ");
        sb.append(",a.dealer_code ,vst.storage_name AS storage_code_name,b.oem_directive_price,CASE WHEN is_price_adjusted="+ DictCodeConstants.STATUS_IS_YES);
        sb.append(" THEN a.directive_price ELSE b.directive_price END AS directive_price,a.vsn,COALESCE(B.exhaust_quantity,a.exhaust_quantity) as exhaust_quantity,a.discharge_standard,a.warranty_number,a.supervise_type,a.financial_bill_no FROM TM_VS_STOCK A LEFT JOIN tm_color tmc ON a.color_code = tmc.color_code ");
        sb.append("LEFT JOIN (SELECT tx.*,tk.color_name AS color_name,tq.BRAND_NAME AS BRAND_NAME,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_NAME AS CONFIG_NAME  FROM ("
                  + CommonConstants.VM_VS_PRODUCT);
        sb.append(") tx LEFT JOIN tm_brand tq ON tx.BRAND_CODE = tq.BRAND_CODE AND tx.DEALER_CODE = tq.dealer_code LEFT JOIN tm_color tk ON tx.color_code = tk.color_code AND tx.DEALER_CODE = tk.dealer_code LEFT JOIN tm_series ts ON tx.BRAND_CODE = ts.BRAND_CODE AND tx.DEALER_CODE = ts.dealer_code AND tx.SERIES_CODE = ts.SERIES_CODE LEFT JOIN tm_model tm ON tx.SERIES_CODE = tm.SERIES_CODE AND tx.BRAND_CODE = tm.BRAND_CODE AND tx.DEALER_CODE = tm.dealer_code AND tx.MODEL_CODE = tm.MODEL_CODE  ");
        sb.append(" LEFT JOIN tm_configuration tc ON tx.SERIES_CODE = tc.SERIES_CODE AND tx.BRAND_CODE = tc.BRAND_CODE AND tx.DEALER_CODE = tc.dealer_code AND tx.MODEL_CODE = tc.MODEL_CODE AND tx.CONFIG_CODE = tc.CONFIG_CODE ");
        sb.append(") B ON A.DEALER_CODE=B.dealer_code AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE LEFT JOIN ("
                  + CommonConstants.VM_STORAGE);
        sb.append(") vst ON vst.dealer_code=A.DEALER_CODE AND vst.storage_code=a.storage_code LEFT JOIN TT_VS_STOCK_ENTRY_ITEM C ON C.dealer_code=A.DEALER_CODE AND A.VIN=C.VIN AND C.SE_NO=A.SE_NO ");
        sb.append("LEFT JOIN tm_dealer_basicinfo db ON db.dealer_code=A.DEALER_CODE WHERE A.DEALER_CODE IN (SELECT SHARE_ENTITY FROM ("
                  + CommonConstants.VM_ENTITY_SHARE_WITH);
        sb.append(") t WHERE t.dealer_code ='" + FrameworkUtil.getLoginInfo().getDealerCode());
        sb.append("' AND t.biz_code = 'UNIFIED_VIEW' ) AND A.dealer_code = '"+ FrameworkUtil.getLoginInfo().getDealerCode() + "' AND A.D_KEY=" + CommonConstants.D_KEY + " ");
        sb.append(" and A.vin = ? ");
        List queryParam = new ArrayList();
        queryParam.add(vin);
        return DAOUtil.findFirst(sb.toString(), queryParam);
    }

    /**
     * TODO 查询所有品牌
     * 
     * @author yangjie
     * @date 2016年12月31日
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#findAllBrand()
     */
    @Override
    public List<Map> findAllTree(Map query) throws ServiceBizException {
        List<Map> result = new ArrayList();
        Map first = new HashMap();
        first.put("id", "yj");
        first.put("parent", "#");
        first.put("text", "根据品牌车系车型查询");
        result.add(first);
        
        StringBuilder sqlsb = new StringBuilder("SELECT DEALER_CODE,BRAND_CODE AS id,'yj' AS parent,BRAND_NAME AS text FROM tm_brand where 1=1 and IS_VALID = "
                                                + DictCodeConstants.STATUS_IS_YES);
        if (query.get("brandCode")!=""&&query.get("brandCode")!=null) {
            sqlToEquals(sqlsb, query.get("brandCode").toString(), "BRAND_CODE", null);
        }

        StringBuilder sqlsb1 = new StringBuilder("SELECT ts.DEALER_CODE,ts.SERIES_CODE AS id,ts.BRAND_CODE AS parent,ts.SERIES_NAME AS text FROM tm_series ts,tm_brand tb where ts.brand_code=tb.brand_code and ts.IS_VALID = "
                                                 + DictCodeConstants.STATUS_IS_YES + " AND tb.IS_VALID="
                                                 + DictCodeConstants.STATUS_IS_YES);
        if (query.get("brandCode")!=""&&query.get("brandCode")!=null&&query.get("seriesCode")!=""&&query.get("seriesCode")!=null) {
            sqlToEquals(sqlsb1, query.get("brandCode").toString(), "BRAND_CODE", "ts");
            sqlToEquals(sqlsb1, query.get("seriesCode").toString(), "SERIES_NAME", "ts");
        }else if(query.get("brandCode")!=""&&query.get("brandCode")!=null){
            sqlToEquals(sqlsb1, query.get("brandCode").toString(), "BRAND_CODE", "ts");
        }
        
        StringBuilder sqlsb2 = new StringBuilder("SELECT DISTINCT tm.DEALER_CODE,tm.MODEL_CODE AS id,tm.SERIES_CODE AS parent,tm.MODEL_NAME AS text FROM tm_model tm,tm_series ts,tm_brand tb where tm.series_code=ts.series_code and ts.brand_code=tb.brand_code and tm.IS_VALID = "
                                                 + DictCodeConstants.STATUS_IS_YES + " AND ts.IS_VALID="
                                                 + DictCodeConstants.STATUS_IS_YES + " AND tb.IS_VALID="
                                                 + DictCodeConstants.STATUS_IS_YES);
        if (query.get("brandCode")!=""&&query.get("brandCode")!=null&&query.get("seriesCode")!=""&&query.get("seriesCode")!=null&&query.get("modelCode")!=""&&query.get("modelCode")!=null) {
            sqlToEquals(sqlsb2, query.get("brandCode").toString(), "BRAND_CODE", "tm");
            sqlToEquals(sqlsb2, query.get("seriesCode").toString(), "SERIES_CODE", "tm");
            sqlToEquals(sqlsb2, query.get("modelCode").toString(), "MODEL_CODE", "tm");
        }else if(query.get("brandCode")!=""&&query.get("brandCode")!=null&&query.get("seriesCode")!=""&&query.get("seriesCode")!=null){
            sqlToEquals(sqlsb2, query.get("brandCode").toString(), "BRAND_CODE", "tm");
            sqlToEquals(sqlsb2, query.get("seriesCode").toString(), "SERIES_CODE", "tm");
        }else if(query.get("brandCode")!=""&&query.get("brandCode")!=null){
            sqlToEquals(sqlsb2, query.get("brandCode").toString(), "BRAND_CODE", "tm");
        }

        result.addAll(DAOUtil.findAll(sqlsb.toString(), null));
        result.addAll(DAOUtil.findAll(sqlsb1.toString(), null));
        result.addAll(DAOUtil.findAll(sqlsb2.toString(), null));
        for (Map map : result) {
            map.remove("DEALER_CODE");
            map.put("id", map.get("id").toString().toUpperCase());
            map.put("parent", map.get("parent").toString().toUpperCase());
        }
        return result;
    }

    /**
     * 查询所有产品信息
     * 
     * @author yangjie
     * @date 2017年1月2日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#findAllProduct(java.util.Map)
     */
    @Override
    public PageInfoDto findAllProduct(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT A.*,A.DIRECTIVE_PRICE AS VEHICLE_PRICE,PACKAGE.WS_GROUP_CODE FROM (SELECT tx.*,tk.color_name AS color_name,tq.BRAND_NAME AS BRAND_NAME,ts.SERIES_NAME AS SERIES_NAME,tm.MODEL_NAME AS MODEL_NAME,tc.CONFIG_NAME AS CONFIG_NAME  FROM ("
                                             + CommonConstants.VM_VS_PRODUCT);
        sb.append(") tx LEFT JOIN tm_brand tq ON tx.BRAND_CODE = tq.BRAND_CODE AND tx.DEALER_CODE = tq.dealer_code LEFT JOIN tm_color tk ON tx.color_code = tk.color_code AND tx.DEALER_CODE = tk.dealer_code ");
        sb.append("LEFT JOIN tm_series ts ON tx.BRAND_CODE = ts.BRAND_CODE AND tx.DEALER_CODE = ts.dealer_code AND tx.SERIES_CODE = ts.SERIES_CODE LEFT JOIN tm_model tm ON tx.SERIES_CODE = tm.SERIES_CODE AND tx.BRAND_CODE = tm.BRAND_CODE AND tx.DEALER_CODE = tm.dealer_code AND tx.MODEL_CODE = tm.MODEL_CODE ");
        sb.append(" LEFT JOIN tm_configuration tc ON tx.SERIES_CODE = tc.SERIES_CODE AND tx.BRAND_CODE = tc.BRAND_CODE AND tx.DEALER_CODE = tc.dealer_code AND tx.MODEL_CODE = tc.MODEL_CODE AND tx.CONFIG_CODE = tc.CONFIG_CODE ");
        sb.append(" ) A LEFT JOIN TM_ACT_FLEET_GROUP_PACKAGE PACKAGE ON A.DEALER_CODE = PACKAGE.DEALER_CODE AND A.CONFIG_CODE = PACKAGE.CONFIG_CODE ");
        sb.append(" WHERE  1=1  AND A.DEALER_CODE = " + FrameworkUtil.getLoginInfo().getDealerCode()
                  + "  AND A.D_KEY = " + CommonConstants.D_KEY + " AND A.IS_VALID  = " + DictCodeConstants.STATUS_IS_YES
                  + " ");
        sqlToLike(sb, queryParam.get("productCode"), "PRODUCT_CODE", "A");
        
        Boolean flag = true;
        if (StringUtils.isNotBlank(queryParam.get("code"))) {
            if (!queryParam.get("code").toLowerCase().equals("yj")) {
                sb.append(" AND BRAND_CODE = '"+queryParam.get("code")+"' OR SERIES_CODE = '"+queryParam.get("code")+"' OR model_code = '"+queryParam.get("code")+"'");
                flag = false;
            }
        }
        
        if(flag){
            sqlToEquals(sb, queryParam.get("brandCode"), "BRAND_CODE", "A");
            sqlToEquals(sb, queryParam.get("seriesCode"), "SERIES_CODE", "A");
            sqlToEquals(sb, queryParam.get("modelCode"), "MODEL_CODE", "A");
            sqlToEquals(sb, queryParam.get("configCode"), "CONFIG_CODE", "A");
        }
       
        if (StringUtils.isNotBlank(queryParam.get("min")) && StringUtils.isNotBlank(queryParam.get("max"))) {
            sb.append(" AND A.DIRECTIVE_PRICE between " + queryParam.get("min") + " AND " + queryParam.get("max")
                      + " ");
        } else if (StringUtils.isNotBlank(queryParam.get("min")) && !StringUtils.isNotBlank(queryParam.get("max"))) {
            sb.append(" AND A.DIRECTIVE_PRICE >=" + queryParam.get("min") + " ");
        } else if (!StringUtils.isNotBlank(queryParam.get("min")) && StringUtils.isNotBlank(queryParam.get("max"))) {
            sb.append(" AND A.DIRECTIVE_PRICE <=" + queryParam.get("max") + " ");
        }
        
        
        
        return DAOUtil.pageQuery(sb.toString(), null);
    }

    /**
     * excel导出查询方法
     * 
     * @author yangjie
     * @date 2017年1月2日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#querySafeToExport(java.util.Map)
     */
    @Override
    public List<Map> querySafeToExport(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = "";
        if (StringUtils.isNotBlank(queryParam.get("STOCK_STATUS"))
            && String.valueOf(DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY).equals(queryParam.get("STOCK_STATUS"))) {
            sql = findAllByOnWayToExcel(null, queryParam);
        } else {
            sql = findAllByNotOnWayToExcel(null, queryParam);
        }
        List<Map> list = DAOUtil.findAll(sql, params);
        for (Map map : list) {
            if (map.get("MAR_STATUS") != null && map.get("MAR_STATUS") != "") {
                if (Integer.parseInt(map.get("MAR_STATUS").toString()) == DictCodeConstants.MAR_STATUS_YES) {
                    map.put("MAR_STATUS", "正常");
                } else if (Integer.parseInt(map.get("MAR_STATUS").toString()) == DictCodeConstants.MAR_STATUS_NOT) {
                    map.put("MAR_STATUS", "质损");
                } else {
                    map.put("DISPATCHED_STATUS", "");
                }
            }

            if (map.get("stock_status") != null && map.get("stock_status") != "") {
                if (Integer.parseInt(map.get("stock_status").toString()) == DictCodeConstants.DICT_STORAGE_STATUS_IN_STORAGE) {
                    map.put("stock_status", "在库");
                } else if (Integer.parseInt(map.get("stock_status").toString()) == DictCodeConstants.DICT_STORAGE_STATUS_ON_WAY) {
                    map.put("stock_status", "在途");
                } else if (Integer.parseInt(map.get("stock_status").toString()) == DictCodeConstants.DICT_STORAGE_STATUS_MOVE_OUT) {
                    map.put("stock_status", "出库");
                } else {
                    map.put("stock_status", "借出");
                }
            }

            if (map.get("dispatched_status") != null && map.get("dispatched_status") != "") {
                if (Integer.parseInt(map.get("dispatched_status").toString()) == DictCodeConstants.ALREADY_DISPATCHED_STATUS) {
                    map.put("dispatched_status", "已配车");
                } else if (Integer.parseInt(map.get("dispatched_status").toString()) == DictCodeConstants.NOT_DISPATCHED_STATUS) {
                    map.put("dispatched_status", "未配车");
                } else if (Integer.parseInt(map.get("dispatched_status").toString()) == DictCodeConstants.ALREADY_SO_STATUS_08) {
                    map.put("dispatched_status", "已交车");
                } else if (Integer.parseInt(map.get("dispatched_status").toString()) == DictCodeConstants.FORWARDED_TO_CONFIRM) {
                    map.put("dispatched_status", "已交车确认");
                } else {
                    map.put("dispatched_status", "");
                }
            }

            if (map.get("stock_in_type") != null && map.get("stock_in_type") != "") {
                if (Integer.parseInt(map.get("stock_in_type").toString()) == DictCodeConstants.DICT_STOCK_IN_TYPE_BUY_NEW_VEHICLE) {
                    map.put("stock_in_type", "新车采购入库");
                } else if (Integer.parseInt(map.get("stock_in_type").toString()) == DictCodeConstants.DICT_STOCK_IN_TYPE_SALE_UNTREAD) {
                    map.put("stock_in_type", "销售退回入库");
                } else {
                    map.put("stock_in_type", "");
                }
            }

            if (map.get("stock_out_type") != null && map.get("stock_out_type") != "") {
                if (Integer.parseInt(map.get("stock_out_type").toString()) == DictCodeConstants.DICT_STOCK_OUT_TYPE_SALE) {
                    map.put("stock_out_type", "销售出库");
                } else if (Integer.parseInt(map.get("stock_out_type").toString()) == DictCodeConstants.DICT_STOCK_OUT_TYPE_UNTREAD) {
                    map.put("stock_out_type", "采购退回出库");
                }  else {
                    map.put("stock_out_type", "");
                }
            }
        }
        return list;
    }

    /**
     * 修改配置方法
     * 
     * @author yangjie
     * @date 2017年1月2日
     * @param queryParam
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.StockSafeService#editConfig(java.util.Map)
     */
    @Override
    public void editConfig(Map<String, String> queryParam) throws ServiceBizException {
//        VsStockPO po = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
//                                                     queryParam.get("vin"));
//        po.setString("PRODUCT_CODE", queryParam.get("productCode"));
//        System.out.println(queryParam.get("colorCode"));
//        po.setString("COLOR_CODE", queryParam.get("colorCode"));
//        po.saveIt();
    	//反响校验车是否在库，和配车状态(未配车或已配车)
    	String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
    	VsStockPO stockPO = VsStockPO.findByCompositeKeys(dealerCode,queryParam.get("vin"));
    	if (stockPO!=null&&stockPO.getInteger("D_KEY")==CommonConstants.D_KEY) {
    		String seNo = stockPO.getString("SE_NO");//入库单号
//    		String dispatch = String.valueOf(stockPO.getInteger("DISPATCHED_STATUS"));
    		if(StringUtils.isNotEmpty(queryParam.get("vin"))){
    			//修改车辆库存日志
    			VsStockLogPO logPOCon = VsStockLogPO.first("VIN = ? AND D_KEY = "+CommonConstants.D_KEY, queryParam.get("vin"));
    			if(logPOCon!=null){
    				logPOCon.setLong("ITEM_ID", null);
    				logPOCon.setString("PRODUCT_CODE", queryParam.get("productCode"));
    				logPOCon.saveIt();
    			}
    			//修改车辆库存
    			VsStockPO stockPOu = VsStockPO.findByCompositeKeys(dealerCode,queryParam.get("vin"));
    			if(stockPOu!=null&&stockPOu.getInteger("D_KEY")==CommonConstants.D_KEY){
    				stockPOu.setString("PRODUCT_CODE", queryParam.get("productCode"));
    				stockPOu.setString("COLOR_CODE", queryParam.get("colorCode"));
    				stockPOu.saveIt();
    			}
    			//修改入库单
    			if(StringUtils.isNotEmpty(seNo)){
    				VsStockEntryItemPO entryItemPO = VsStockEntryItemPO.findByCompositeKeys(dealerCode,seNo,queryParam.get("vin"));
    				if(entryItemPO!=null){
    					entryItemPO.setString("PRODUCT_CODE", queryParam.get("productCode"));
    					entryItemPO.setString("COLOR_CODE", queryParam.get("colorCode"));
    					entryItemPO.saveIt();
    				}
    			}
    		}
		}
    	
    	//更改产品代码的日志操作
    	OperateLogPO operateLog = new OperateLogPO();
    	operateLog.setString("OPERATE_CONTENT","车辆VIN："+queryParam.get("vin")+" 整车更改产品代码:原产品代码【"+queryParam.get("oldProductCode")+"】新产品代码【"+queryParam.get("productCode")+"】");
		operateLog.setInteger("OPERATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_CHANGE_PROPERTY));
		operateLog.setString("OPERATOR",FrameworkUtil.getLoginInfo().getEmployeeNo());
		operateLog.setString("DEALER_CODE",dealerCode);
		operateLog.setString("REMARK","");
		operateLog.saveIt();
		
		//更改颜色代码的日志操作
		operateLog = new OperateLogPO();
    	operateLog.setString("OPERATE_CONTENT","车辆VIN："+queryParam.get("vin")+" 整车更改颜色代码:原颜色代码【"+queryParam.get("oldColorCode")+"】新颜色代码【"+queryParam.get("colorCode")+"】");
		operateLog.setInteger("OPERATE_TYPE",Integer.parseInt(DictCodeConstants.DICT_VEHICLE_STORAGE_TYPE_CHANGE_PROPERTY));
		operateLog.setString("OPERATOR",FrameworkUtil.getLoginInfo().getEmployeeNo());
		operateLog.setString("DEALER_CODE",dealerCode);
		operateLog.setString("REMARK","");
		operateLog.saveIt();
    }
}
