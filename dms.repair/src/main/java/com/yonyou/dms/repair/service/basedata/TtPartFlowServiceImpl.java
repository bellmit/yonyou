
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.report
 *
 * @File name : PartFlowServiceImpl.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年8月22日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月22日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.basedata;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.commonAS.domains.DTO.basedata.PartFlowDTO;
import com.yonyou.dms.commonAS.domains.PO.basedata.PartFlowPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.SystemParamConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.basedata.TtRoRepairPartDTO;

/**
 * 配件流水账查询实现类
 * @author DuPengXin
 * @date 2016年8月22日
 */
@Service
@SuppressWarnings("rawtypes")
public class TtPartFlowServiceImpl implements TtPartFlowService{

    @Autowired
    private SystemParamService systemParamService;
    
    /**
     * 查询库存信息
    * @author DuPengXin
    * @date 2016年8月29日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.TtPartFlowService.service.impl.PartFlowService#queryPartflows(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryPartflows(Map<String, String> queryParam) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT\n")
                .append(" t.PART_STOCK_ID,")
                .append("t.DEALER_CODE ,")
                .append(" t.STORAGE_CODE ,\n")
                .append(" m.STORAGE_NAME ,\n")
                .append(" t.STORAGE_POSITION_CODE ,\n")
                .append(" t.PART_CODE ,\n")
                .append(" t.PART_NAME ,\n")
                .append(" t.STOCK_QUANTITY ,\n")
                .append(" t.BORROW_QUANTITY ,\n")
                .append(" t.LEND_QUANTITY ,\n")
                .append(" t.STOCK_QUANTITY + t.BORROW_QUANTITY - t.LEND_QUANTITY - t.LOCKED_QUANTITY as aaa ,\n")
                .append(" t.LAST_STOCK_IN ,\n")
                .append(" t.LAST_STOCK_OUT \n")
                .append("  FROM\n")
                .append("  tt_part_stock t\n")
                .append("  LEFT JOIN tm_store m ON t.STORAGE_CODE = m.STORAGE_CODE and t.DEALER_CODE=m.DEALER_CODE \n")
                .append("  WHERE 1=1 \n");
        List<Object> params = new ArrayList<Object>();
        if(!StringUtils.isNullOrEmpty(queryParam.get("storageMark"))){
            BasicParametersDTO bpDto=systemParamService.queryBasicParameterByTypeandCode(SystemParamConstants.PARAM_TYPE_STORAGE, SystemParamConstants.STORAGE_CODE);
            sb.append(" and t.STORAGE_CODE = ? ");
            params.add(bpDto.getParamValue());
        }else{
            if(!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))){
                sb.append(" and t.STORAGE_CODE = ? ");
                params.add(queryParam.get("storageCode"));
            }else{
                //默认全选时的仓库权限
                LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
                sb.append(" and t.STORAGE_CODE IN (");
                sb.append( loginInfo.getPurchaseDepot()).append(")");
            }
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partCode"))){
            sb.append(" and t.PART_CODE like ?");
            params.add("%"+queryParam.get("partCode")+"%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("partName"))){
            sb.append(" and t.PART_NAME like ?");
            params.add("%"+queryParam.get("partName")+"%");
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
        return pageInfoDto;
    }

    
    /**
     * 配件流水明细查询
    * @author DuPengXin
    * @date 2016年8月26日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.TtPartFlowService.service.impl.PartFlowService#queryPartdetails(java.util.Map)
    */
    	
    @Override
    public PageInfoDto queryPartdetails(Map<String, String> queryParam,String storageCode) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT\n")
                .append(" tpf.FLOW_ID,")
                .append(" tpf.DEALER_CODE ,\n")
                .append(" tpf.STORAGE_CODE ,\n")
                .append(" ts.STORAGE_NAME ,\n")
                .append(" tpf.PART_NO ,\n")
                .append(" tpf.PART_NAME ,\n")
                .append(" tpf.OPERATE_DATE ,\n")
                .append(" tpf.IN_OUT_TYPE ,\n")
                .append(" tpf.SHEET_NO ,\n")
                .append(" tpf.STOCK_IN_QUANTITY ,\n")
                .append(" tpf.STOCK_OUT_QUANTITY,\n")
                .append(" tpf.STOCK_QUANTITY ,\n")
                .append(" tpf.COST_PRICE ,\n")
                .append(" tpf.COST_AMOUNT ,\n")
                .append(" tpf.IN_OUT_NET_PRICE ,\n")
                .append(" tpf.IN_OUT_NET_AMOUNT , \n")
                .append(" tpf.IN_OUT_TAXED_PRICE , \n")
                .append(" tpf.IN_OUT_TAXED_AMOUNT , \n")
                .append(" tpf.CUSTOMER_CODE , \n")
                .append(" tpf.CUSTOMER_TYPE,")
                .append("   CASE tpf.CUSTOMER_TYPE WHEN '10381001' THEN tpc.CUSTOMER_NAME WHEN '10381002' THEN tm.OWNER_NAME ELSE '其他' END as CUSTOMER_NAME,")
                .append(" te.EMPLOYEE_NAME \n")
                .append("  FROM \n")
                .append("  tt_part_flow tpf \n")
                .append("  LEFT JOIN tm_part_customer tpc ON tpf.CUSTOMER_CODE=tpc.CUSTOMER_CODE and tpf.DEALER_CODE=tpc.DEALER_CODE \n")
                .append("  LEFT JOIN tm_store ts ON tpf.STORAGE_CODE=ts.STORAGE_CODE and tpf.DEALER_CODE=ts.DEALER_CODE ")
                .append("  LEFT JOIN tm_employee te ON te.EMPLOYEE_NO=tpf.OPERATOR and te.DEALER_CODE=tpf.DEALER_CODE")
                .append("  LEFT JOIN tm_owner tm ON tpf.CUSTOMER_CODE=tm.OWNER_NO and tm.DEALER_CODE=tpf.DEALER_CODE")
                .append("  WHERE 1=1 and \n")
                .append(" tpf.STORAGE_CODE=? ");
        List<Object> params = new ArrayList<Object>();
        params.add(storageCode);
        if(!StringUtils.isNullOrEmpty(queryParam.get("operteDateFrom"))){
            sb.append(" and tpf.OPERATE_DATE >= ? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("operteDateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("operteDateTo"))){
            sb.append(" and tpf.OPERATE_DATE < ? ");
            params.add(DateUtil.addOneDay(queryParam.get("operteDateTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("inOutType"))){
            sb.append(" and tpf.IN_OUT_TYPE = ?");
            params.add(queryParam.get("inOutType"));
        }
        /*if(!StringUtils.isNullOrEmpty(queryParam.get("storageCode"))){
            sb.append(" and tpf.STORAGE_CODE = ?");
            params.add(queryParam.get("storageCode"));
        }*/
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sb.toString(),params);
        return pageInfoDto;
    }


    
    /**
     * 导出查询数据
    * @author DuPengXin
    * @date 2016年8月26日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.TtPartFlowService.service.impl.PartFlowService#queryPartdetailExport(java.util.Map)
    */
    	
    @Override
    public List<Map> queryPartdetailExport(Map<String, String> queryParam,String storageCode) throws ServiceBizException {
    	StringBuilder sb = new StringBuilder("SELECT\n")
                .append(" tpf.FLOW_ID,")
                .append(" tpf.DEALER_CODE ,\n")
                .append(" tpf.STORAGE_CODE ,\n")
                .append(" ts.STORAGE_NAME ,\n")
                .append(" tpf.PART_NO ,\n")
                .append(" tpf.PART_NAME ,\n")
                .append(" tpf.OPERATE_DATE ,\n")
                .append(" tpf.IN_OUT_TYPE ,\n")
                .append(" tpf.SHEET_NO ,\n")
                .append(" tpf.STOCK_IN_QUANTITY ,\n")
                .append(" tpf.STOCK_OUT_QUANTITY,\n")
                .append(" tpf.STOCK_QUANTITY ,\n")
                .append(" tpf.COST_PRICE ,\n")
                .append(" tpf.COST_AMOUNT ,\n")
                .append(" tpf.IN_OUT_NET_PRICE ,\n")
                .append(" tpf.IN_OUT_NET_AMOUNT , \n")
                .append(" tpf.IN_OUT_TAXED_PRICE , \n")
                .append(" tpf.IN_OUT_TAXED_AMOUNT , \n")
                .append(" tpf.CUSTOMER_CODE , \n")
                .append(" tpf.CUSTOMER_TYPE,")
                .append("   CASE tpf.CUSTOMER_TYPE WHEN '10381001' THEN tpc.CUSTOMER_NAME WHEN '10381002' THEN tm.OWNER_NAME ELSE '其他' END as CUSTOMER_NAME,")
                .append(" te.EMPLOYEE_NAME \n")
                .append("  FROM \n")
                .append("  tt_part_flow tpf \n")
                .append("  LEFT JOIN tm_part_customer tpc ON tpf.CUSTOMER_CODE=tpc.CUSTOMER_CODE and tpf.DEALER_CODE=tpc.DEALER_CODE \n")
                .append("  LEFT JOIN tm_store ts ON tpf.STORAGE_CODE=ts.STORAGE_CODE and tpf.DEALER_CODE=ts.DEALER_CODE ")
                .append("  LEFT JOIN tm_employee te ON te.EMPLOYEE_NO=tpf.OPERATOR and te.DEALER_CODE=tpf.DEALER_CODE")
                .append("  LEFT JOIN tm_owner tm ON tpf.CUSTOMER_CODE=tm.OWNER_NO and tm.DEALER_CODE=tpf.DEALER_CODE")
                .append("  WHERE 1=1 and \n")
                .append(" tpf.STORAGE_CODE=? ");
        List<Object> params = new ArrayList<Object>();
        params.add(storageCode);
        if(!StringUtils.isNullOrEmpty(queryParam.get("operteDateFrom"))){
            sb.append(" and tpf.OPERATE_DATE >= ? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("operteDateFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("operteDateTo"))){
            sb.append(" and tpf.OPERATE_DATE < ? ");
            params.add(DateUtil.addOneDay(queryParam.get("operteDateTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("inOutType"))){
            sb.append(" and tpf.IN_OUT_TYPE = ?");
            params.add(queryParam.get("inOutType"));
        }
        List<Map> resultList = DAOUtil.findAll(sb.toString(),params);
        return resultList;
    }


    
    /**
     * 根据ID查询
    * @author DuPengXin
    * @date 2016年10月25日
    * @param id
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.TtPartFlowService.service.impl.PartFlowService#queryPartflowsById(java.lang.Long)
    */
    	
    @Override
    public Map queryPartflowsById(Long id) throws ServiceBizException {
        StringBuilder sb = new StringBuilder("SELECT\n")
                .append(" t.PART_STOCK_ID,")
                .append("t.DEALER_CODE ,")
                .append(" t.STORAGE_CODE ,\n")
                .append(" m.STORAGE_NAME ,\n")
                .append(" t.STORAGE_POSITION_CODE ,\n")
                .append(" t.PART_CODE ,\n")
                .append(" t.PART_NAME ,\n")
                .append(" t.STOCK_QUANTITY ,\n")
                .append(" t.BORROW_QUANTITY ,\n")
                .append(" t.LEND_QUANTITY ,\n")
                .append(" t.STOCK_QUANTITY + t.BORROW_QUANTITY - t.LEND_QUANTITY - t.LOCKED_QUANTITY as aaa ,\n")
                .append(" t.LAST_STOCK_IN ,\n")
                .append(" t.LAST_STOCK_OUT \n")
                .append("  FROM\n")
                .append("  tt_part_stock t\n")
                .append("  LEFT JOIN tm_store m ON t.STORAGE_CODE = m.STORAGE_CODE and t.DEALER_CODE=m.DEALER_CODE \n")
                .append(" where 1=1 and PART_STOCK_ID=?");
        List<Object> params = new ArrayList<Object>();
        params.add(id);
        return DAOUtil.findFirst(sb.toString(), params);
    }


    @Override
    public void addTtPartFlow(PartFlowDTO cudto) throws ServiceBizException {
        PartFlowPO typo = new PartFlowPO();
        checkPartFlow(cudto);
        setPartFlow(typo,cudto);
        typo.saveIt();
    }
    
    /**
     * 新增之前检查是否已存在
     * 
     * @author chenwei
     * @date 2017年4月16日
     * @param pyto
     */

    public void checkPartFlow(PartFlowDTO pyto) {
        StringBuffer sb = new StringBuffer(
                "select DEALER_CODE,FLOW_ID from TT_PART_FLOW where 1=1  and FLOW_ID = ? ");
        List<Object> list = new ArrayList<Object>();
        list.add(pyto.getFlowId());
        List<Map> map = DAOUtil.findAll(sb.toString(), list);
        if (map.size() > 0) {
            throw new ServiceBizException("配件流水帐记录已经存在！");
        }
    }
    
    /**
     * 设置TroubleDescPO属性
     * 
     * @author chenwei
     * @date 2017年4月16日
     * @param typo
     * @param pyto
     */

    public void setPartFlow(PartFlowPO typo, PartFlowDTO pyto) {
        typo.setDouble("COST_AMOUNT", pyto.getCostAmount());
        typo.setDouble("COST_PRICE", pyto.getCostPrice());
        typo.setString("DEALER_CODE", pyto.getDealerCode());
        typo.setDouble("IN_OUT_NET_AMOUNT", pyto.getInOutNetAmount());
        typo.setDouble("IN_OUT_NET_PRICE", pyto.getInOutNetPrice());
        typo.setDouble("IN_OUT_TAXED_AMOUNT", pyto.getInOutTaxedAmount());
        typo.setDouble("IN_OUT_TAXED_PRICE", pyto.getInOutTaxedPrice());
        typo.setInteger("IN_OUT_TYPE", pyto.getInOutType());
        typo.setInteger("IN_OUT_TAG", pyto.getInOutTag());
        typo.setFloat("STOCK_OUT_QUANTITY", pyto.getStockOutQuantity());
        typo.setDate("OPERATE_DATE", pyto.getOperateDate());
        typo.setString("OPERATOR", pyto.getOperator());
        typo.setString("PART_BATCH_NO", pyto.getPartBatchNo());
        typo.setString("PART_NAME", pyto.getPartName());
        typo.setString("PART_NO", pyto.getPartNo());
        typo.setString("SHEET_NO", pyto.getSheetNo());
        typo.setFloat("STOCK_QUANTITY", pyto.getStockQuantity());
        typo.setString("STORAGE_CODE", pyto.getStorageCode());
        typo.setString("LICENSE", pyto.getLicense());
        typo.setString("CUSTOMER_CODE", pyto.getCustomerCode());
        typo.setString("CUSTOMER_NAME", pyto.getCustomerName());
    }

}
