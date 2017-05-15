
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : NewStockInServiceImpl.java
 *
 * @Author : yll
 *
 * @Date : 2016年12月8日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年12月8日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.service.stockManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInOutDto;

/**
 * 整车入库实现类
 * @author yll
 * @date 2016年12月8日
 */
@Service
@SuppressWarnings("unchecked")
public class NewStockInServiceImpl  implements NewStockInService{

    @Autowired
    private CommonNoService commonNoService;
    /**
     * 入库信息查询
     * @author yll
     * @date 2016年12月8日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.NewStockInService#queryStockIn(java.util.Map)
     */
    @Override
    public PageInfoDto queryStockIn(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        String sql = this.getSQl();
        StringBuffer sb=new StringBuffer(sql);
        this.setWhere(queryParam, queryList, sb);
        PageInfoDto pageInfo = DAOUtil.pageQuery(sb.toString(), queryList);
        return pageInfo;
    }

    /**
     * 根据id查询记录
     * @author yll
     * @date 2016年12月8日
     * @param id
     * @return
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.NewStockInService#queryStockInById(java.lang.Long)
     */
    @Override
    public Map<String, Object> queryStockInById(Long id) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        String sql = getSQl();
        StringBuffer sb=new StringBuffer(sql);
        sb.append(" and ios.IN_OUT_STOCK_ID=?");
        queryList.add(id);
        return DAOUtil.findFirst(sb.toString(), queryList);
    }

    /**
     * 设置查询条件
     * 
     * @author yll
     * @date 2016年12月6日
     * @param queryParam
     * @param queryList
     */

    public void setWhere(Map<String, String> queryParam, List<Object> queryList, StringBuffer sb) {
        if (!StringUtils.isNullOrEmpty(queryParam.get("vbTyp"))) {
            sb.append(" and ios.IN_DELIVERY_TYPE = ?");
            queryList.add(queryParam.get("vbTyp"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("bussinessNo"))) {
            sb.append(" and ios.BUSSINESS_NO like ?");
            queryList.add("%" + queryParam.get("bussinessNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("bussiness_stardate"))) {
            sb.append(" and ios.BUSSINESS_DATE >= ?");
            queryList.add(DateUtil.parseDefaultDate(queryParam.get("bussiness_stardate")));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("bussiness_endate"))) {
            sb.append(" and ios.BUSSINESS_DATE < ?");
            queryList.add(DateUtil.addOneDay(queryParam.get("bussiness_endate")));
        }

        if(!StringUtils.isNullOrEmpty(queryParam.get("isEntry"))){
            sb.append(" and ios.IS_IN_OUT_STOCK=?");
            queryList.add(Integer.parseInt(queryParam.get("isEntry")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("seNO"))){
            sb.append(" and ios.IN_OUT_STOCK_NO like ?");
            queryList.add("%"+queryParam.get("seNO")+"%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and tvs.VIN like ?");
            queryList.add("%" + queryParam.get("vin") + "%");
        }
    }

    /**
     * 车辆入库SQl
     * 
     * @author yll
     * @date 2016年12月6日
     * @param queryParam
     * @param queryList
     * @return
     */

    public String getSQl() {
        StringBuffer sb = new StringBuffer(" SELECT ios.IN_OUT_STOCK_ID,ios.DEALER_CODE,ios.IN_DELIVERY_TYPE, tvs.VIN, ios.IS_IN_OUT_STOCK, ts.STORAGE_NAME, ios.STORAGE_POSITION_CODE,\n");
        sb.append("ios.IN_OUT_STOCK_NO, ios.BUSSINESS_NO, ios.BUSSINESS_DATE, tvp.PRODUCT_CODE, tvp.PRODUCT_NAME\n");
        sb.append("FROM tt_vs_in_out_stock ios\n");
        sb.append("LEFT JOIN tm_vs_stock tvs ON tvs.VS_STOCK_ID = ios.VS_STOCK_ID\n");
        sb.append("LEFT JOIN tm_vs_product tvp ON tvp.PRODUCT_ID = tvs.PRODUCT_ID\n");
        sb.append("LEFT JOIN tm_store ts ON ts.STORAGE_CODE = ios.STORAGE_CODE AND tvs.DEALER_CODE=ts.DEALER_CODE\n");
        sb.append("where 1=1\n");
        return sb.toString();

    }

    /**
     * 单个编辑验收
     * @author yll
     * @date 2016年12月8日
     * @param id
     * @param stockInOutDTO
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.vehicle.service.stockManage.NewStockInService#modifyStockIn(java.lang.Long, com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInOutDto)
     */
    @Override
    public void modifyStockIn(Long id, StockInOutDto stockInOutDTO) throws ServiceBizException {/*
        StockInOutPO so =StockInOutPO.findById(id);
        so.setString("STORAGE_CODE", stockInOutDTO.getStorageCode());//仓库代码
        so.setString("STORAGE_POSITION_CODE", stockInOutDTO.getStoragePositionCode());//库位代码
        so.setInteger("IS_IN_OUT_STOCK", DictCodeConstants.STATUS_IS_YES);
        so.saveIt();
        String busNo=(String) so.get("BUSSINESS_NO");//业务单号
        Long stockId= Long.parseLong(so.get("VS_STOCK_ID")+"");//查到库存表id
        Integer type= Integer.parseInt(so.get("IN_DELIVERY_TYPE")+"");//查到业务类型
        Integer marStatus=0;
        //根据库存id和业务类型取到验收表里的运损状态
        StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,MAR_STATUS  from tm_vehicle_inspect WHERE 1=1");
        List<Object> params = new ArrayList<Object>();
        sqlSb.append(" and BUSSINESS_NO=?");
        params.add(busNo);
        sqlSb.append(" and VB_TYPE=?");
        params.add(type);
        List<Map> map = Base.findAll(sqlSb.toString(),params.toArray());
        if(!CommonUtils.isNullOrEmpty(map)){
            marStatus= Integer.parseInt(map.get(0).get("MAR_STATUS")+"");
        }

        InvtrMaTncePO invtrMaTncePO=InvtrMaTncePO.findById(stockId);
        invtrMaTncePO.setInteger("OWN_STOCK_STATUS",DictCodeConstants.DISPATCHED_STATUS_INSTOCK);//更新车辆库存状态为‘在库’
        invtrMaTncePO.setString("STORAGE_CODE", stockInOutDTO.getStorageCode());//仓库代码
        invtrMaTncePO.setString("STORAGE_POSITION_CODE", stockInOutDTO.getStoragePositionCode());//库位代码
        invtrMaTncePO.setInteger("ENTRY_TYPE",type);//入库类型，入库单没有这个字段，这里暂时默认
        invtrMaTncePO.setInteger("IS_LOCK", DictCodeConstants.STATUS_IS_NOT);//是否锁定，默认为否
        invtrMaTncePO.setInteger("IS_TEST_DRIVE",DictCodeConstants.STATUS_IS_NOT);//是否试驾车，默认为否
        invtrMaTncePO.setDate("FIRST_STOCK_IN_DATE", new Date());//首次入库日期
        invtrMaTncePO.setDate("LATEST_STOCK_IN_DATE", new Date());//最后入库日期
        if(!StringUtils.isNullOrEmpty(marStatus)){
            invtrMaTncePO.setInteger("TRAFFIC_MAR_STATUS", marStatus);//运损状态
        }
        invtrMaTncePO.saveIt();

    */}
    /**
     * 批量入库
    * @author yll
    * @date 2016年12月9日
    * @param stockInListDTO
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.vehicle.service.stockManage.NewStockInService#inbounds(com.yonyou.dms.vehicle.domains.DTO.basedata.StockInListDTO)
     */
    @Override
    public void inbounds( StockInOutDto stockInOutDTO) throws ServiceBizException {/*
        String[] ids = stockInOutDTO.getIds().split(",");
        for(int i=0;i<ids.length;i++){
            Long id = Long.parseLong(ids[i]);
            StockInOutPO so=StockInOutPO.findById(id);
            so.setString("STORAGE_CODE", stockInOutDTO.getStorageCode());//仓库代码
            so.setInteger("IS_IN_OUT_STOCK", DictCodeConstants.STATUS_IS_YES);
            so.saveIt();
            String busNo=(String) so.get("BUSSINESS_NO");//业务单号
            Long stockId= Long.parseLong(so.get("VS_STOCK_ID")+"");//查到库存表id
            Integer type= Integer.parseInt(so.get("IN_DELIVERY_TYPE")+"");//查到业务类型
            Integer marStatus=0;
            //根据库存id和业务类型取到验收表里的运损状态
            StringBuilder sqlSb = new StringBuilder("select DEALER_CODE,MAR_STATUS  from tm_vehicle_inspect WHERE 1=1");
            List<Object> params = new ArrayList<Object>();
            sqlSb.append(" and BUSSINESS_NO=?");
            params.add(busNo);
            sqlSb.append(" and VB_TYPE=?");
            params.add(type);
            List<Map> map = Base.findAll(sqlSb.toString(),params.toArray());
            if(!CommonUtils.isNullOrEmpty(map)){
                marStatus= Integer.parseInt(map.get(0).get("MAR_STATUS")+"");
            }

            InvtrMaTncePO invtrMaTncePO=InvtrMaTncePO.findById(stockId);
            invtrMaTncePO.setInteger("OWN_STOCK_STATUS",DictCodeConstants.DISPATCHED_STATUS_INSTOCK);//更新车辆库存状态为‘在库’
            invtrMaTncePO.setString("STORAGE_CODE", stockInOutDTO.getStorageCode());//仓库代码
            invtrMaTncePO.setInteger("ENTRY_TYPE",type);//入库类型，入库单没有这个字段，这里暂时默认
            invtrMaTncePO.setInteger("IS_LOCK", DictCodeConstants.STATUS_IS_NOT);//是否锁定，默认为否
            invtrMaTncePO.setInteger("IS_TEST_DRIVE",DictCodeConstants.STATUS_IS_NOT);//是否试驾车，默认为否
            invtrMaTncePO.setDate("FIRST_STOCK_IN_DATE", new Date());//首次入库日期
            invtrMaTncePO.setDate("LATEST_STOCK_IN_DATE", new Date());//最后入库日期
            if(!StringUtils.isNullOrEmpty(marStatus)){
                invtrMaTncePO.setInteger("TRAFFIC_MAR_STATUS", marStatus);//运损状态
            }
            invtrMaTncePO.saveIt();
        }
    */}

    @Override
    public List<Map> exportStockInInfo(Map<String, String> queryParam) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        String sql = this.getSQl();
        StringBuffer sb = new StringBuffer(sql);
        this.setWhere(queryParam, queryList, sb);
        List<Map> resultList = DAOUtil.findAll(sb.toString(), queryList);
        return resultList;
    }

    @Override
    public List<Map> queryStockInByIds(String ids) throws ServiceBizException {
        List<Object> queryList = new ArrayList<Object>();
        String sql = getSQl();
        StringBuffer sb=new StringBuffer(sql);
        if(!StringUtils.isNullOrEmpty(ids)){
            String[] idList=ids.split(",");
            sb.append(" and ios.IN_OUT_STOCK_ID in (");
            for(int i=0;i<idList.length-1;i++){
                sb.append("?,");
            }
            sb.append("?");
            sb.append(")");
            for(int i=0;i<idList.length;i++){
                queryList.add(Long.parseLong(idList[i]));
            }
        }
        return DAOUtil.findAll(sb.toString(), queryList);
    }
    
    
}
