
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : LendAndReturnServiceImpl.java
*
* @Author : yangjie
*
* @Date : 2017年1月12日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年1月12日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.service.stockManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.vehicle.domains.PO.basedata.VsStockLendItemPO;
import com.yonyou.dms.vehicle.domains.PO.basedata.VsStockLendPO;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2017年1月12日
 */

@Service
@SuppressWarnings({"rawtypes"})
public class LendAndReturnServiceImpl implements LendAndReturnService {

    
    /**
    * @author yangjie
    * @date 2017年1月13日
    * @param slNo
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.stockManage.LendAndReturnService#findAllItem(java.lang.String)
    */
    	
    @Override
    public List<Map> findAllItem(String slNo) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DEALER_CODE,0 AS IS_SELECTED,SL_NO,VIN,STORAGE_CODE,STORAGE_POSITION_CODE,ADDITIONAL_COST,PURCHASE_PRICE,MAR_STATUS,");
        sb.append(" DIRECTIVE_PRICE,LEND_DATE,BORROW_BY,RETURN_DATE,RETURNED_BY,IS_RETURNED,IS_FINISHED,FINISHED_DATE,");
        sb.append(" FINISHED_BY FROM TT_VS_STOCK_LEND_ITEM WHERE DEALER_CODE=? AND D_KEY=? ");
        List<Object> query = new ArrayList<Object>();
        query.add(FrameworkUtil.getLoginInfo().getDealerCode());
        query.add(CommonConstants.D_KEY);
        sqlToLike(sb, slNo, "SL_NO", null);
        return DAOUtil.findAll(sb.toString(), query);
    }
    
    
    /**
    * @author yangjie
    * @date 2017年1月13日
    * @param query
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.stockManage.LendAndReturnService#findAllDetails(java.util.Map)
    */
    	
    @Override
    public PageInfoDto findAllDetails(Map<String, String> query){
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT DEALER_CODE,SL_NO,SHEET_CREATE_DATE,SHEET_CREATED_BY,CASE WHEN IS_ALL_FINISHED=12781001 THEN 10571001 ELSE 10571002 END AS IS_ALL_FINISHED,CASE WHEN IS_ALL_RETURNED=12781001 THEN 10571001 ELSE 10571002 END AS IS_ALL_RETURNED,REMARK ");
        sb.append(" FROM TT_VS_STOCK_LEND WHERE DEALER_CODE=? AND D_KEY=? ");
        sqlToLike(sb, query.get("slNo"), "SL_NO", null);
        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(FrameworkUtil.getLoginInfo().getDealerCode());
        queryParam.add(CommonConstants.D_KEY);
        return DAOUtil.pageQuery(sb.toString(), queryParam);
    }
    
    
    /**
    * @author yangjie
    * @date 2017年1月13日
    * @param queryParam
    * @return
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.stockManage.LendAndReturnService#findAllVehicleInfo(java.util.Map)
    */
    	
    @Override
    public PageInfoDto findAllVehicleInfo(Map<String, String> queryParam) {
        StringBuffer sb = new StringBuffer();
        sb.append("SELECT A.DEALER_CODE,A.VIN,A.PRODUCT_CODE,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.ADDITIONAL_COST,A.PURCHASE_PRICE,");
        sb.append("A.MAR_STATUS,B.DIRECTIVE_PRICE,A.IS_PURCHASE_RETURN,A.IS_PRICE_ADJUSTED FROM TM_VS_STOCK A ");
        sb.append(" LEFT JOIN TT_SALES_ORDER B ON A.VIN=B.VIN AND A.SO_NO=B.SO_NO AND A.DEALER_CODE=B.DEALER_CODE ");
        sb.append(" AND A.D_KEY=B.D_KEY WHERE A.DEALER_CODE=? AND A.D_KEY=? AND A.STOCK_STATUS=?");
        sb.append(" AND A.DISPATCHED_STATUS=? AND A.IS_VIP=? AND A.IS_CONSIGNED=? ");
        List<Object> query = new ArrayList<Object>();
        query.add(FrameworkUtil.getLoginInfo().getDealerCode());
        query.add(CommonConstants.D_KEY);
        query.add(DictCodeConstants.DISPATCHED_STATUS_INSTOCK);
        query.add(DictCodeConstants.NOT_DISPATCHED_STATUS);
        query.add(DictCodeConstants.IS_NOT);
        query.add(DictCodeConstants.IS_NOT);
        sqlToLike(sb, queryParam.get("vin"), "VIN", "A");
        sqlToLike(sb, queryParam.get("storageCode"), "STORAGE_CODE", "A");
        sqlToLike(sb, queryParam.get("productCode"), "PRODUCT_CODE", "A");
        return DAOUtil.pageQuery(sb.toString(), query);
    }
    
    
    /**
    * @author yangjie
    * @date 2017年1月13日
    * @param map
    * @param po
    * @param flag
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.stockManage.LendAndReturnService#addOrEditItem(java.util.Map, com.yonyou.dms.vehicle.domains.PO.basedata.VsStockLendPO, java.lang.Boolean)
    */
    	
    @Override
    public void addOrEditItem(Map<String, Object> map,Boolean flag){
        if (flag) {//新增
            VsStockLendPO po = new VsStockLendPO();
            po.setString("SL_NO", map.get("slNo").toString());
            po.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode()); 
            po.setInteger("IS_ALL_FINISHED", DictCodeConstants.IS_YES);
            po.setInteger("IS_ALL_RETURNED", DictCodeConstants.IS_NOT);
            po.setDate("SHEET_CREATE_DATE", new Date());
            po.setString("SHEET_CREATED_BY", FrameworkUtil.getLoginInfo().getUserName());
            if (map.get("remark")!=null) {
                po.setString("REMARK", map.get("remark").toString());
            }
            po.saveIt();
        }else{//修改
            VsStockLendPO vsl = VsStockLendPO.findByCompositeKeys( FrameworkUtil.getLoginInfo().getDealerCode(),map.get("slNo").toString());
            vsl.setDate("SHEET_CREATE_DATE", new Date());
            if (map.get("remark")!=null) {
                vsl.setString("REMARK", map.get("remark").toString());
            }
            vsl.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserName());
            vsl.saveIt();
        }
    }
    
    
    /**
    * @author yangjie
    * @date 2017年1月13日
    * @param map
    * @param po
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.stockManage.LendAndReturnService#addOrEditDetails(java.util.Map, com.yonyou.dms.vehicle.domains.PO.basedata.VsStockLendItemPO)
    */
    	
    @Override
    public void addOrEditDetails(Map<String, Object> map){
        String[] vins = map.get("vins").toString().split(";");
        String sql = "select DEALER_CODE,VIN from TT_VS_STOCK_LEND_ITEM where sl_no='"+map.get("slNo").toString()+"'";
        List<Map> list = DAOUtil.findAll(sql, null);
        if (list.size()>0) {//修改
            for (Map mp : list) {//将vin存入list集合用于判断前台传过来的vin是否在数据库中
                for (int i = 0; i < vins.length; i++) {
                    if (mp.containsValue(vins[i])) {//代表VIN存在数据库中
                        VsStockLendItemPO vsli = VsStockLendItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),map.get("slNo").toString(),mp.get("VIN").toString());
                        vsli.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserName());
                        vsli.setInteger("IS_FINISHED", DictCodeConstants.IS_YES);
                        vsli.setString("FINISHED_BY", FrameworkUtil.getLoginInfo().getUserName());
                        vsli.setDate("FINISHED_DATE", new Date());
                        vsli.saveIt();
                        
                        VsStockPO vs = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),mp.get("VIN").toString());
                        
                        // 新增日志信息
                        VsStockLogPO log = new VsStockLogPO();
                        Map<String, Object> query = new HashMap<String,Object>();
                        query.put("vin", mp.get("VIN").toString());
                        query.put("productCode", vs.get("PRODUCT_CODE"));
                        query.put("storageCode", vs.get("STORAGE_CODE"));
                        query.put("storagePositionCode", vs.get("STORAGE_POSITION_CODE"));
                        query.put("purchasePrice", vs.get("PURCHASE_PRICE"));
                        query.put("marStatus", vs.get("MAR_STATUS"));
                        query.put("type", "edit");
                        MapToPoForVsStockLog(log, query);
                        log.saveIt();
                    }else{
                        VsStockLendItemPO po = new VsStockLendItemPO();
                        po.setString("VIN", vins[i]);
                        po.setString("SL_NO", map.get("slNo").toString());
                        VsStockPO vs = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),vins[i]);
                        vs.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
                        vs.setString("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_LEND_TO);
                        vs.setDate("FIRST_STOCK_IN_DATE", new Date());
                        vs.setDate("LATEST_STOCK_IN_DATE",  new Date());
                        vs.setString("LAST_STOCK_IN_BY", FrameworkUtil.getLoginInfo().getUserId());
                        vs.saveIt();//修改车辆库库表里 库存状态为 借出
                        
                        po.setString("STORAGE_CODE", vs.get("STORAGE_CODE"));
                        po.setString("STORAGE_POSITION_CODE", vs.get("STORAGE_POSITION_CODE"));
                        po.setDouble("ADDITIONAL_COST", vs.get("ADDITIONAL_COST"));
                        po.setDouble("PURCHASE_PRICE", vs.get("PURCHASE_PRICE"));
                        po.setInteger("MAR_STATUS", vs.get("MAR_STATUS"));
                        po.setDouble("DIRECTIVE_PRICE", vs.get("DIRECTIVE_PRICE"));
                        po.setDate("LEND_DATE", new Date());
                        po.setString("BORROW_BY", map.get("borrowBy").toString());
                        po.setInteger("IS_FINISHED", DictCodeConstants.IS_YES);
                        po.setString("FINISHED_BY", FrameworkUtil.getLoginInfo().getUserName());
                        po.saveIt();
                        
                        // 新增日志信息
                        VsStockLogPO log = new VsStockLogPO();
                        Map<String, Object> query = new HashMap<String,Object>();
                        query.put("vin", vins[i]);
                        query.put("productCode", vs.get("PRODUCT_CODE"));
                        query.put("storageCode", vs.get("STORAGE_CODE"));
                        query.put("storagePositionCode", vs.get("STORAGE_POSITION_CODE"));
                        query.put("purchasePrice", vs.get("PURCHASE_PRICE"));
                        query.put("marStatus", vs.get("MAR_STATUS"));
                        query.put("type", "add");
                        MapToPoForVsStockLog(log, query);
                        log.saveIt();
                    }
                }
            }
        }else{//新增
            for (int i = 0; i < vins.length; i++) {
                VsStockLendItemPO po = new VsStockLendItemPO();
                po.setString("VIN", vins[i]);
                po.setString("SL_NO", map.get("slNo").toString());
                VsStockPO vs = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),vins[i]);
                vs.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
                vs.setString("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_LEND_TO);
                vs.setDate("FIRST_STOCK_IN_DATE", new Date());
                vs.setDate("LATEST_STOCK_IN_DATE",  new Date());
                vs.setString("LAST_STOCK_IN_BY", FrameworkUtil.getLoginInfo().getUserId());
                vs.saveIt();//修改车辆库库表里 库存状态为 借出
                
                po.setString("STORAGE_CODE", vs.get("STORAGE_CODE"));
                po.setString("STORAGE_POSITION_CODE", vs.get("STORAGE_POSITION_CODE"));
                po.setDouble("ADDITIONAL_COST", vs.get("ADDITIONAL_COST"));
                po.setDouble("PURCHASE_PRICE", vs.get("PURCHASE_PRICE"));
                po.setInteger("MAR_STATUS", vs.get("MAR_STATUS"));
                po.setDouble("DIRECTIVE_PRICE", vs.get("DIRECTIVE_PRICE"));
                po.setDate("LEND_DATE", new Date());
                po.setString("BORROW_BY", map.get("borrowBy").toString());
                po.setInteger("IS_FINISHED", DictCodeConstants.IS_YES);
                po.setString("FINISHED_BY", FrameworkUtil.getLoginInfo().getUserName());
                po.saveIt();
                
                // 新增日志信息
                VsStockLogPO log = new VsStockLogPO();
                Map<String, Object> query = new HashMap<String,Object>();
                query.put("vin", vins[i]);
                query.put("productCode", vs.get("PRODUCT_CODE"));
                query.put("storageCode", vs.get("STORAGE_CODE"));
                query.put("storagePositionCode", vs.get("STORAGE_POSITION_CODE"));
                query.put("purchasePrice", vs.get("PURCHASE_PRICE"));
                query.put("marStatus", vs.get("MAR_STATUS"));
                query.put("type", "add");
                MapToPoForVsStockLog(log, query);
                log.saveIt();
            }
        }
    }
    
    /**
     * 借出归还日志
    * TODO description
    * @author yangjie
    * @date 2017年1月13日
    * @param po
    * @param map
     */
    public void MapToPoForVsStockLog(VsStockLogPO po, Map<String, Object> map) {
        po.setString("DEALER_CODE", FrameworkUtil.getLoginInfo().getDealerCode());
        po.setString("VIN", map.get("vin"));
        po.setString("PRODUCT_CODE", map.get("productCode"));
        po.setDate("OPERATE_DATE", new Date());
        po.setInteger("OPERATION_TYPE", DictCodeConstants.DICT_ASCLOG_VEHICLE_MANAGE);
        if (map.get("storageCode") != null && map.get("storageCode") != "") {
            po.setString("STORAGE_CODE", map.get("storageCode"));
        }
        po.setString("STORAGE_POSITION_CODE", map.get("storagePositionCode"));
        po.setDouble("PURCHASE_PRICE", map.get("purchasePrice"));
        po.setInteger("STOCK_STATUS", DictCodeConstants.DICT_STORAGE_STATUS_LEND_TO);
        po.setInteger("DISPATCHED_STATUS", DictCodeConstants.NOT_DISPATCHED_STATUS);
        po.setString("OPERATED_BY", FrameworkUtil.getLoginInfo().getUserName());

        VsStockPO vs = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(), map.get("vin"));
        if (vs != null) {
            po.setDouble("ADDITIONAL_COST", vs.getDouble("ADDITIONAL_COST"));
            po.setInteger("IS_SECONDHAND", vs.getInteger("IS_SECONDHAND"));
            po.setInteger("IS_VIP", vs.getInteger("IS_VIP"));
            po.setInteger("IS_TEST_DRIVE_CAR", vs.getInteger("IS_TEST_DRIVE_CAR"));
            po.setInteger("IS_CONSIGNED", vs.getInteger("IS_CONSIGNED"));
            po.setInteger("IS_PROMOTION", vs.getInteger("IS_PROMOTION"));
            po.setInteger("IS_PURCHASE_RETURN", vs.getInteger("IS_PURCHASE_RETURN"));
            po.setInteger("IS_PRICE_ADJUSTED", vs.getInteger("IS_PRICE_ADJUSTED"));
            po.setString("ADJUST_REASON", vs.getString("ADJUST_REASON"));
            po.setDouble("OLD_DIRECTIVE_PRICE", vs.getDouble("OLD_DIRECTIVE_PRICE"));
            po.setDouble("DIRECTIVE_PRICE", vs.getDouble("DIRECTIVE_PRICE"));
            po.setInteger("OEM_TAG", vs.getInteger("OEM_TAG"));
            po.setInteger("DISPATCHED_STATUS", vs.getInteger("DISPATCHED_STATUS"));
            po.setInteger("D_KEY", vs.getInteger("D_KEY"));
        }

        po.setInteger("OPERATION_TYPE", DictCodeConstants.DICT_ASCLOG_VEHICLE_MANAGE);

    }
    
    
    /**
    * @author yangjie
    * @date 2017年1月13日
    * @param map
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.stockManage.LendAndReturnService#btnReturn(java.util.Map)
    */
    	
    @Override
    public void btnReturn(Map<String, Object> map){
        //归还单明细修改
        VsStockLendItemPO po = VsStockLendItemPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),map.get("slNo").toString(),map.get("vin").toString());
        po.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserName());
        po.setDate("RETURN_DATE", new Date());
        po.setString("RETURNED_BY", map.get("returnedBy").toString());
        po.setInteger("IS_RETURNED", DictCodeConstants.IS_YES);
        po.setDate("FINISHED_DATE", new Date());
        po.setString("FINISHED_BY", FrameworkUtil.getLoginInfo().getUserName());
        po.saveIt();
        
        //修改车辆库存表库存状态为入库
        VsStockPO vs = VsStockPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),map.get("vin").toString());
        vs.setString("OWNED_BY", FrameworkUtil.getLoginInfo().getUserId());
        vs.setInteger("STOCK_STATUS", DictCodeConstants.DISPATCHED_STATUS_INSTOCK);
        vs.setDate("LATEST_STOCK_IN_DATE", new Date());
        vs.setString("LAST_STOCK_IN_BY", FrameworkUtil.getLoginInfo().getUserId());
        vs.saveIt();
        
        // 新增日志信息
        VsStockLogPO log = new VsStockLogPO();
        Map<String, Object> query = new HashMap<String,Object>();
        query.put("vin", map.get("vin").toString());
        query.put("productCode", vs.get("PRODUCT_CODE"));
        query.put("storageCode", vs.get("STORAGE_CODE"));
        query.put("storagePositionCode", vs.get("STORAGE_POSITION_CODE"));
        query.put("purchasePrice", vs.get("PURCHASE_PRICE"));
        query.put("marStatus", vs.get("MAR_STATUS"));
        query.put("type", "add");
        MapToPoForVsStockLog(log, query);
        log.saveIt();
        
        //判断明细表是否全部归还
        String sql = "select DEALER_CODE,IS_RETURNED from TT_VS_STOCK_LEND_ITEM where SL_NO='"+map.get("slNo").toString()+"'";
        List<Map> list = DAOUtil.findAll(sql, null);
        Boolean flag = true;//判断是否全部归还
        for (Map map2 : list) {
            if (map2.get("IS_RETURNED")==null||Long.parseLong(map2.get("IS_RETURNED").toString())==DictCodeConstants.IS_NOT) {
                flag = false;
            }
        }
        
        if (flag) {
            VsStockLendPO vsl = VsStockLendPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),map.get("slNo").toString());
            vsl.setInteger("IS_ALL_RETURNED", DictCodeConstants.IS_YES);
            vsl.saveIt();
        }
    }

    /**
     * SQL判断IN关键字 TODO description
     * 
     * @author yangjie
     * @date 2017年1月8日
     * @param sb
     * @param ids
     * @param field
     * @param alias
     */
    public void sqlToIn(StringBuffer sb, String ids, String field, String alias) {
        if (StringUtils.isNotBlank(ids)) {
            String[] items = ids.split(",");
            sb.append("AND ");
            if (StringUtils.isNotBlank(alias)) {
                sb.append(alias + "." + field);
            } else {
                sb.append(field);
            }
            sb.append(" IN (");
            for (int i = 0; i < items.length; i++) {
                if (StringUtils.isNotBlank(items[i])) {
                    sb.append("'" + items[i] + "'");
                    if (i != items.length - 1) {
                        sb.append(",");
                    }
                }
            }
        }
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
    public void sqlToLike(StringBuffer sb, String param, String field, String alias) {
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
    public void sqlToEquals(StringBuffer sb, String param, String field, String alias) {
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
    public void sqlToDate(StringBuffer sb, String begin, String end, String field, String alias) {
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
    
}
