
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderServiceImpl.java
*
* @Author : zhanshiwei
*
* @Date : 2017年4月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月14日    zhanshiwei    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.service.partOrder;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmPartInfoPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.framework.util.dao.DbFunctionConvertUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderItemDTO;
import com.yonyou.dms.part.domains.PO.partOrder.TtPtDmsOrderItemPO;
import com.yonyou.dms.part.domains.PO.partOrder.TtPtDmsOrderPO;

/**
 * 配件订货
 * 
 * @author zhanshiwei
 * @date 2017年4月14日
 */
@Service
public class PartPlaceanOrderServiceImpl implements PartPlaceanOrderService {

    @Autowired
    private CommonNoService commonNoService;

    @Override
    public PageInfoDto queryAllocateInOrders(Map<String, String> queryParam) throws ServiceBizException, Exception {
        String begin = queryParam.get("BEGIN_DATE");
        String end = queryParam.get("END_DATE");
        String orderNo = queryParam.get("ORDER_NO");
        String customerName = queryParam.get("CUSTOMER_NAME");
        String orderType = queryParam.get("PART_ORDER_TYPE");
        String starTime = Utility.getDateCond("", "ORDER_DATE", begin, end);
        StringBuffer sql = new StringBuffer("");
        String endTime = "";
        String name = "";
        String type = "";
        String number = "";
        if (!StringUtils.isNullOrEmpty(customerName)) {
            name = Utility.getLikeCond(null, "CUSTOMER_NAME", customerName, "AND");
        } else {
            name = " and  1 = 1 ";
        }

        if (!StringUtils.isNullOrEmpty(orderNo)) {
            number = Utility.getLikeCond(null, "ORDER_NO", orderNo, "AND");
        } else {
            number = " and  1 = 1 ";
        }

        if (!StringUtils.isNullOrEmpty(orderType)) {
            type = " and PART_ORDER_TYPE =  " + orderType + "  ";
        } else {
            type = " and  1 = 1 ";
        }
        sql.append(" select IS_VALID,REMARK,DEALER_CODE, ORDER_NO, OEM_ORDER_NO, ORDER_SUM, MAIN_ORDER_TYPE, "
                   + " PART_ORDER_TYPE, DOE, PDC, IS_LACK_GOODS, CUSTOMER_CODE, CUSTOMER_NAME, "
                   + " ITEM_COUNT, FILLIN_TIME, ORDER_DATE, GKFWB_DATE, SEND_MODE, CONTACTOR_NAME,STORAGE_CODE, "
                   + " CREATED_AT,SUBMIT_TIME, ORDER_STATUS, LOCK_USER,IS_ACHIEVE,IS_UPLOAD,GROUP_CODE,LEAD_TIME,IS_BO,ELEC_CODE,MECH_CODE,KEY_CODE,EMERG,a.CHANGE,VIN,OWNER_NAME,MOBILE,LICENSE,SHEET_NO,CODE_ORDER_ONE_ID,CODE_ORDER_ONE_URL,CODE_ORDER_TWO_ID,CODE_ORDER_TWO_URL,CODE_ORDER_THREE_ID,CODE_ORDER_THREE_URL "
                   + " from TT_PT_DMS_ORDER a " + "WHERE  1=1 " + " and (ORDER_STATUS= "
                   + DictCodeConstants.DICT_PORS_NOT_REPORT + " or ORDER_STATUS= " + DictCodeConstants.DICT_PORS_REJECT
                   + ") and coalesce(IS_VALID,0)<>12781002 " + " AND D_KEY = " + CommonConstants.D_KEY + "  "
                   + " AND IS_ACHIEVE = " + Utility.getInt(DictCodeConstants.DICT_IS_NO) + starTime + endTime + type
                   + name + number + " order by CREATED_AT desc ");
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), params);
    }

    /**
     * 业务描述：配件仓库三包缺料查询
     * 
     * @author zhanshiwei
     * @date 2017年4月17日
     * @param queryParam
     * @return
     * @throws ServiceBizException
     * @throws Exception (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#QueryDmsSanBaoOrderPartInfo(java.util.Map)
     */

    @Override
    public PageInfoDto QueryDmsSanBaoOrderPartInfo(Map<String, String> queryParam) throws ServiceBizException,
                                                                                   Exception {
        String license = queryParam.get("LICENSE");
        String sheetNo = queryParam.get("SHEET_NO");
        String startDate = queryParam.get("START_DATE");
        String endDate = queryParam.get("END_DATE");
        String customerName = queryParam.get("CUSTOMER_NAME");
        String vin = queryParam.get("VIN");
        String partNo = queryParam.get("PART_NO");
        String orderPlanStatus = queryParam.get("ORDER_PLAN_STATUS");
        String date = "";
        String eDate = "";
        date = Utility.getDateCond("A", "CREATE_DATE", startDate, endDate);
        int pageSize = StringUtils.isNullOrEmpty(queryParam.get("PAGE_SIZE")) ? 0 : Integer.parseInt(queryParam.get("PAGE_SIZE"));
        String sql = " SELECT  12781002 AS IS_SELECTED,f.is_sjv,f.is_mop,a.ORDER_GOODS_STATUS,A.IS_BO,B.STOCK_QUANTITY,0 AS CHOSE,  A.SHORT_ID,A.ELINK_NO, A.DEALER_CODE, A.STORAGE_CODE, A.PART_NO, A.PART_NAME, "
                     + " A.STORAGE_POSITION_CODE, e.OWNER_NAME,A.IN_OUT_TYPE, A.SHORT_TYPE, A.SHEET_NO, A.CLOSE_STATUS, "
                     + " A.IS_URGENT, A.LICENSE, A.SHORT_QUANTITY, A.HANDLER, A.CUSTOMER_NAME, A.PHONE, "
                     + " A.SEND_TIME, A.CREATED_BY, A.CREATED_AT, A.UPDATED_BY, A.UPDATED_AT, A.VER, "
                     + "  D.STATUS_DESC AS OUT_TYPE, C.USER_NAME AS SENDER_NAME, "
                     + "  (B.STOCK_QUANTITY+B.BORROW_QUANTITY-B.LEND_QUANTITY) AS REAL_QUANTITY ,F.MIN_PACKAGE,F.UNIT_CODE,E.VIN,F.REGULAR_PRICE,F.URGENT_PRICE,  CASE 12481005   WHEN 13591001  THEN F.REGULAR_PRICE ELSE F.URGENT_PRICE  END UNIT_PRICE "
                     + " FROM TT_SHORT_PART A " + " left join TM_PART_STOCK B " + " on A.DEALER_CODE = B.DEALER_CODE  "
                     + "    and A.STORAGE_CODE = B.STORAGE_CODE and A.PART_NO = B.PART_NO " + " left join TM_USER C "
                     + " on A.DEALER_CODE = C.DEALER_CODE and A.CREATED_BY = C.USER_ID "
                     + " left join TM_SYSTEM_STATUS D " + " on A.IN_OUT_TYPE = D.STATUS_CODE "
                     + " left join tt_repair_order E on A.DEALER_CODE = E.DEALER_CODE and A.SHEET_NO = E.RO_NO "
                     + " left join (" + CommonConstants.VM_PART_INFO
                     + ")F ON F.DEALER_CODE = A.DEALER_CODE AND F.D_KEY = A.D_KEY AND F.PART_NO = A.PART_NO "
                     + " WHERE 1=1" + " AND A.D_KEY = " + DictCodeConstants.D_KEY + " "
                     + " AND coalesce(F.OEM_TAG,0) = " + DictCodeConstants.DICT_IS_YES + " "
                     + " AND E.SCHEME_STATUS IS NOT NULL AND E.SCHEME_STATUS != 0 " + date + eDate;

        if (sheetNo != null && !"".equals(sheetNo)) {
            sql += " AND A.SHEET_NO= '" + sheetNo + "' ";
        }
        if (Utility.testString(license)) {
            sql += " AND A.license like '%" + license + "%' ";
        }
        if (Utility.testString(customerName)) {
            sql += " AND e.OWNER_NAME like '%" + customerName + "%' ";
        }
        if (Utility.testString(vin)) {
            sql += " AND e.vin like '%" + vin + "%' ";
        }
        if (!StringUtils.isNullOrEmpty(partNo)) {
            sql += " AND a.PART_NO like '%" + partNo + "%' ";
        }
        if (Utility.testString(orderPlanStatus)) {
            sql += " AND a.ORDER_GOODS_STATUS =" + orderPlanStatus + " ";
        }
        sql = sql + " ORDER BY    A.SHEET_NO DESC ";
        if (pageSize > 0) {
            sql = sql + " FETCH FIRST " + pageSize + " ROWS ONLY ";
        }
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), params);
    }

    /**
     * 创建配件订单
     * 
     * @author zhanshiwei
     * @date 2017年4月18日
     * @param ttPtOrderIntenDto
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#CreateNewDMSOrder(com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderItemDTO)
     */

    @Override
    public List<TtPtDmsOrderPO> CreateNewDMSOrder(TtPtDmsOrderItemDTO ttPtOrderIntenDto,
                                                  String orderNo) throws ServiceBizException {
        // 配件订单DMS
        List<TtPtDmsOrderPO> orderInfo = DAOUtil.findByDealer(TtPtDmsOrderPO.class, "order_No=?", orderNo);
        String str1 = orderNo.substring(0, 2);
        String id = "";
        if (str1.equals("PO")) {
            id = commonNoService.GetBillNo(CommonConstants.SRV_DDBH);
        }
        if (str1.equals("PK")) {
            id = commonNoService.GetBillNo(CommonConstants.SRV_DDBH);
        }
        // 配件订单DMS
        TtPtDmsOrderPO queryOrder = orderInfo.get(0);

        TtPtDmsOrderPO newqueryOrder = new TtPtDmsOrderPO();
        newqueryOrder.setString("ORDER_NO", id);
        newqueryOrder.setString("CONTACTOR_NAME", queryOrder.getString("CONTACTOR_NAME"));
        newqueryOrder.setString("CUSTOMER_CODE", queryOrder.getString("CUSTOMER_CODE"));
        newqueryOrder.setString("CUSTOMER_NAME", queryOrder.getString("CUSTOMER_NAME"));
        newqueryOrder.setString("ITEM_COUNT", queryOrder.getString("ITEM_COUNT"));
        newqueryOrder.setInteger("ORDER_STATUS", DictCodeConstants.DICT_PORS_NOT_REPORT);
        newqueryOrder.setString("ORDER_SUM", queryOrder.getString("ORDER_SUM"));
        newqueryOrder.setString("REMARK", queryOrder.getString("REMARK"));
        newqueryOrder.setInteger("SEND_MODE", queryOrder.getInteger("SEND_MODE"));
        newqueryOrder.setInteger("MAIN_ORDER_TYPE", queryOrder.getInteger("MAIN_ORDER_TYPE"));
        newqueryOrder.setInteger("PART_ORDER_TYPE", queryOrder.getInteger("PART_ORDER_TYPE"));
        newqueryOrder.setString("DOE", queryOrder.getString("DOE"));
        newqueryOrder.setString("PDC", queryOrder.getString("PDC"));
        newqueryOrder.setInteger("IS_LACK_GOODS", queryOrder.getInteger("IS_LACK_GOODS"));
        newqueryOrder.setInteger("IS_ACHIEVE", DictCodeConstants.DICT_IS_NO);
        newqueryOrder.setInteger("IS_UPLOAD", DictCodeConstants.DICT_IS_NO);
        newqueryOrder.setInteger("IS_SIGNED", DictCodeConstants.DICT_IS_NO);
        newqueryOrder.setInteger("LEAD_TIME", queryOrder.getInteger("LEAD_TIME"));
        newqueryOrder.setInteger("IS_BO", queryOrder.getInteger("IS_BO"));
        newqueryOrder.setInteger("IS_VALID", DictCodeConstants.DICT_IS_YES);
        newqueryOrder.saveIt();

        List<TtPtDmsOrderItemPO> itemInfo = DAOUtil.findByDealer(TtPtDmsOrderItemPO.class, "D_KEY=? and ORDER_NO=?",
                                                                 CommonConstants.D_KEY, orderNo);
        for (int i = 0; i <= itemInfo.size() - 1; i++) {
            TtPtDmsOrderItemPO queryItem = itemInfo.get(i);
            TtPtDmsOrderItemPO newqueryItem = new TtPtDmsOrderItemPO();
            newqueryItem.setString("ORDER_NO", id);
            newqueryItem.setString("D_KEY", queryItem.getString("D_KEY"));
            newqueryItem.setString("UNIT_PRICE", queryItem.getString("UNIT_PRICE"));
            newqueryItem.setString("PART_NO", queryItem.getString("PART_NO"));
            newqueryItem.setString("STORAGE_CODE", queryItem.getString("STORAGE_CODE"));
            newqueryItem.setString("QUOTA_COUNT", queryItem.getString("QUOTA_COUNT"));
            newqueryItem.setString("IS_LACK_GOODS", queryItem.getString("IS_LACK_GOODS"));
            newqueryItem.setString("COUNT", queryItem.getString("COUNT"));
            newqueryItem.setString("UNIT_CODE", queryItem.getString("UNIT_CODE"));
            newqueryItem.setString("PART_NAME", queryItem.getString("PART_NAME"));
            newqueryItem.setString("ONGOING_COUNT", queryItem.getString("ONGOING_COUNT"));
            newqueryItem.setString("TOTAL_PRICES", queryItem.getString("TOTAL_PRICES"));
            newqueryItem.setString("SIGNATURE_COUNT", queryItem.getString("SIGNATURE_COUNT"));
            newqueryItem.setString("DELAY_COUNT", queryItem.getString("DELAY_COUNT"));
            newqueryItem.setString("MODEL_CODE", queryItem.getString("MODEL_CODE"));
            newqueryItem.saveIt();

        }

        List<TtPtDmsOrderPO> neworderInfo = DAOUtil.findByDealer(TtPtDmsOrderPO.class, "D_KEY=? and ORDER_NO=?",
                                                                 CommonConstants.D_KEY, id);
        return neworderInfo;
    }

    /**
     * 业务描述:查询配件订单明细
     * 
     * @author zhanshiwei
     * @date 2017年4月9日
     * @param queryParam
     * @param orderNo
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#queryDetail(java.util.Map, java.lang.String)
     */

    @Override
    public List<Map> queryDetail(Map<String, String> queryParam, String orderNo) throws ServiceBizException {
        return queryHighPrice(orderNo);
    }

    /**
     * @author zhanshiwei
     * @date 2017年4月9日
     * @param no
     * @return
     */

    @SuppressWarnings("rawtypes")
    public List<Map> queryHighPrice(String no) {
        StringBuffer sql = new StringBuffer("");

        sql.append(" select A.DETAIL_REMARK,'' AS CAN_CHANGE,0.0 AS SUGGEST_QUANTITY,A.SHORT_ID, A.IS_LACK_GOODS, A.ITEM_ID, A.ORDER_NO, A.DEALER_CODE, A.STORAGE_CODE, A.PART_NO, A.PART_NAME, "
                   + " A.MODEL_CODE,C.IS_MOP,C.IS_SJV, A.UNIT_CODE, A.COUNT, A.DELAY_COUNT, A.UNIT_PRICE, A.SIGNATURE_COUNT, "
                   + " A.ONGOING_COUNT, A.QUOTA_COUNT, A.TOTAL_PRICES, B.STOCK_QUANTITY,(B.STOCK_QUANTITY "
                   + "+ B.BORROW_QUANTITY - B.LEND_QUANTITY  - B.LOCKED_QUANTITY) AS USEABLE_STOCK, C.MIN_PACKAGE, "
                   + "  b.SALES_PRICE, 0.0 AS QUANTIYT_COUNT, "
                   + " b.CLAIM_PRICE, b.LIMIT_PRICE, b.LATEST_PRICE, b.COST_PRICE, b.COST_AMOUNT, b.MAX_STOCK, "
                   + " b.MIN_STOCK, b.BORROW_QUANTITY, b.LEND_QUANTITY, b.LOCKED_QUANTITY, b.PART_STATUS, "
                   + " b.LAST_STOCK_IN, b.LAST_STOCK_OUT, b.FOUND_DATE, b.REMARK, "
                   + " (case when C.IS_ACC =12781001 then C.IS_ACC ELSE 12781002 end) as IS_THINGS ,"
                   + " (case when C.DOWN_TAG=12781001 then C.DOWN_TAG ELSE 12781002 end) as DOWN_TAG ,B.PROVIDER_NAME"
                   + " ,a.ARKTX,a.NETWR,a.TOTAL,a.OPNETWR,a.OPTOTAL,'' as Is_Stock,a.NO_TAX_PRICE,a.SINGLE_Discount, "
                   + "  (case when a.HAS_CHANGE =12781001 then '10571001' ELSE 12781002 end) as HAS_CHANGE"
                   + " from TT_PT_DMS_ORDER_ITEM A "
                   + " LEFT JOIN TM_PART_STOCK B ON (A.PART_NO = B.PART_NO AND A.STORAGE_CODE = B.STORAGE_CODE AND A.DEALER_CODE = B.DEALER_CODE ) "
                   + " left join (" + CommonConstants.VM_PART_INFO
                   + ") C ON (A.DEALER_CODE = C.DEALER_CODE AND A.PART_NO = C.PART_NO )" + " WHERE  1=1 "
                   + " AND A.D_KEY =  " + CommonConstants.D_KEY + " and A.ORDER_NO = '" + no + "' ");
        List<Object> queryParam = new ArrayList<Object>();
        return DAOUtil.findAll(sql.toString(), queryParam);
    }

    /**
     * 业务描述:配件出入小计
     * 
     * @author zhanshiwei
     * @date 2017年4月21日
     * @param queryParam
     * @param partNo
     * @param storageCode
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#queryDmsPartInOutSub(java.util.Map,
     * java.lang.String, java.lang.String)
     */

    @Override
    public PageInfoDto queryDmsPartInOutSub(Map<String, String> queryParam, String partNo,
                                            String storageCode) throws ServiceBizException {
        return queryInOutSub(queryParam, partNo, storageCode);
    }

    /**
     * 配件出入库小计
     * 
     * @author zhanshiwei
     * @date 2017年4月21日
     * @param queryParam
     * @param partNo
     * @param storageCode
     * @return
     */

    public PageInfoDto queryInOutSub(Map<String, String> queryParam, String partNo, String storageCode) {
        StringBuffer sql = new StringBuffer("");
        sql.append(" SELECT  DEALER_CODE,PART_NO, D_KEY, STORAGE_CODE,REPORT_YEAR AS YEAR,REPORT_MONTH AS MONTH,IN_QUANTITY AS OUT_QUANTITY, "
                   + " OUT_QUANTITY AS IN_QUANTITY  FROM  TT_PART_PERIOD_REPORT " + " WHERE PART_NO = '" + partNo + "' "
                   + " AND STORAGE_CODE = '" + storageCode + "' " + " AND D_KEY = " + CommonConstants.D_KEY);
        List<Object> queryParam1 = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), queryParam1);
    }

    /**
     * 业务描述:查询配件替代品
     * 
     * @author zhanshiwei
     * @date 2017年4月21日
     * @param partNo
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#queryDmsPartOption(java.lang.String)
     */

    @Override
    public List<Map> queryDmsPartOption(String partNo) throws ServiceBizException {

        return queryOption(partNo);
    }

    /**
     * 查询配件替代品信息
     * 
     * @author zhanshiwei
     * @date 2017年4月21日
     * @param partNo
     * @return
     */

    public List<Map> queryOption(String partNo) {
        StringBuffer sql = new StringBuffer("");
        String number = "";

        if (partNo != null && partNo.trim().length() != 0) {
            number = " and A.OPTION_NO =  '" + partNo + "'  ";
        } else {
            number = " and 1 = 1 ";
        }
        ;
        sql.append("SELECT A.PART_NAME AS OPTION_NAME, BB.OPTION_QUANTITY FROM (" + CommonConstants.VM_PART_INFO
                   + ") A " + " LEFT JOIN (SELECT PART_NO, SUM(STOCK_QUANTITY)  AS OPTION_QUANTITY  "
                   + " FROM TM_PART_STOCK WHERE DEALER_CODE='" + FrameworkUtil.getLoginInfo().getDealerCode()
                   + "'  GROUP BY PART_NO) BB " + " ON (BB.PART_NO = A.PART_NO) " + " WHERE A.DEALER_CODE = '"
                   + FrameworkUtil.getLoginInfo().getDealerCode() + "' " + " AND A.D_KEY =  " + CommonConstants.D_KEY
                   + number);
        List<Object> queryParam1 = new ArrayList<Object>();
        return DAOUtil.findAll(sql.toString(), queryParam1, false);
    }

    /**
     * 业务描述:配件订单以及明细信息维护
     * 
     * @author zhanshiwei
     * @date 2017年4月22日
     * @param ttptdmsorderdto
     * @throws Exception
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#maintainDmsPtOrder(com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO)
     */

    @Override
    public  Map maintainDmsPtOrder(TtPtDmsOrderDTO ttptdmsorderdto) throws Exception {
        return performExecute(ttptdmsorderdto);
       
    }

    /**
     * @author zhanshiwei
     * @date 2017年4月22日
     * @param ptoOrderdto
     * @return
     * @throws Exception
     */

    protected Map performExecute(TtPtDmsOrderDTO ptoOrderdto) throws Exception {
        String status = ptoOrderdto.getStatus();
        TtPtDmsOrderPO oder = new TtPtDmsOrderPO();

        String No = "";
        String id = "";
        // 主表插入
        if (status != null && "A".equals(status.trim())) {

            oder.setString("CONTACTOR_NAME", ptoOrderdto.getContactorName());
            oder.setString("CUSTOMER_CODE", ptoOrderdto.getCustomerCode());
            oder.setString("CUSTOMER_NAME", ptoOrderdto.getCustomerName());
            oder.setTimestamp("FILLIN_TIME", ptoOrderdto.getFillinTime());
            oder.setTimestamp("GKFWB_DATE", ptoOrderdto.getGkfwbDate());
            oder.setInteger("ITEM_COUNT", ptoOrderdto.getItemCount());
            oder.setString("LOCK_USER", ptoOrderdto.getLockUser());
            oder.setTimestamp("ORDER_DATE", new Date());
            oder.setString("ORDER_NO", commonNoService.getSystemOrderNo("PO")
                                       + FrameworkUtil.getLoginInfo().getDealerCode().substring(2));

            oder.setDouble("ORDER_SUM", ptoOrderdto.getOrderSum());
            oder.setString("REMARK", ptoOrderdto.getRemark());
            oder.setInteger("SEND_MODE", ptoOrderdto.getSendMode());
            oder.setTimestamp("SUBMIT_TIME", ptoOrderdto.getSubmitTime());
            oder.setString("OEM_ORDER_NO", ptoOrderdto.getOemOrderNo());

            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getElecCode())) {
                oder.setString("ELEC_CODE", ptoOrderdto.getElecCode());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getMechCode())) {
                oder.setString("MECH_CODE", ptoOrderdto.getMechCode());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getKeyCode())) {
                oder.setString("KEY_CODE", ptoOrderdto.getKeyCode());
            }
          
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getEmerg())) {
                oder.setString("EMERG", ptoOrderdto.getEmerg());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getVin())) {
                oder.setString("VIN", ptoOrderdto.getVin());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getOwnerName())) {
                oder.setString("OWNER_NAME", ptoOrderdto.getOwnerName());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getMobile())) {
                oder.setString("MOBILE", ptoOrderdto.getMobile());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getLicense())) {
                oder.setString("LICENSE", ptoOrderdto.getLicense());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getSheetNo())) {
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getCodeOrderOneId())) {
                oder.setString("CODE_ORDER_ONE_ID", ptoOrderdto.getCodeOrderOneId());
            }

            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getCodeOrderOneUrl())) {
                oder.setString("CODE_ORDER_ONE_URL", ptoOrderdto.getCodeOrderOneUrl());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getCodeOrderTwoId())) {
                oder.setString("CODE_ORDER_TWO_ID", ptoOrderdto.getCodeOrderTwoId());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getCodeOrderTwoUrl())) {
                oder.setString("CODE_ORDER_TWO_ID", ptoOrderdto.getCodeOrderTwoUrl());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getCodeOrderThreeId())) {
                oder.setString("CODE_ORDER_THREE_ID", ptoOrderdto.getCodeOrderThreeId());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getCodeOrderThreeUrl())) {
                oder.setString("CODE_ORDER_THREE_URL", ptoOrderdto.getCodeOrderThreeUrl());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getMainOrderType())) {
                oder.setString("MAIN_ORDER_TYPE", ptoOrderdto.getMainOrderType());
            }
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getPartOrderType())) {
                oder.setString("PART_ORDER_TYPE", ptoOrderdto.getPartOrderType());
            }
            oder.setString("DOE", ptoOrderdto.getDoe());
            oder.setString("PDC", ptoOrderdto.getPdc());
            oder.setInteger("IS_LACK_GOODS", ptoOrderdto.getIsLackGoods());
            oder.setInteger("IS_ACHIEVE", DictCodeConstants.DICT_IS_NO);
            oder.setInteger("ORDER_STATUS", DictCodeConstants.DICT_PORS_NOT_REPORT);
            if (Utility.testString(ptoOrderdto.getOrderStatus())) {
                if (Utility.testString(ptoOrderdto.getIsAchieve())
                    && CommonConstants.DICT_IS_YES.equals(ptoOrderdto.getIsAchieve())) {
                    if (ptoOrderdto.getOrderStatus() == Utility.getInt(DictCodeConstants.DICT_PORS_REJECT)) {
                        // oder.setInteger("ORDER_STATUS", ptoOrderdto.getOrderStatus());
                    }
                }
            }
            oder.setInteger("IS_UPLOAD", ptoOrderdto.getIsUpload());
            oder.setInteger("IS_SIGNED", DictCodeConstants.DICT_IS_NO);
            oder.setString("REMARK", ptoOrderdto.getRemark());
            oder.setString("GROUP_CODE", ptoOrderdto.getGroupCode());
            oder.setInteger("LEAD_TIME", ptoOrderdto.getLeadTime());
            oder.setInteger("IS_BO", ptoOrderdto.getIsBo());
            oder.setString("STORAGE_CODE", ptoOrderdto.getStorageCode());
            oder.saveIt();
            if (!StringUtils.isNullOrEmpty(ptoOrderdto.getChange())) {
                oder.setString("CHANGE", ptoOrderdto.getChange());
            }
            String strTmp = DbFunctionConvertUtil.reservedWordDeal(oder.toUpdate());
            System.err.println(strTmp);
            Base.exec(strTmp);
        }
        if (status != null && "U".equals(status.trim())) {

            List<TtPtDmsOrderPO> lsitoder = TtPtDmsOrderPO.findBySQL("select * from TT_PT_DMS_ORDER where 1=1 and ORDER_NO=? and DEALER_CODE=?", ptoOrderdto.getOrderNo(),FrameworkUtil.getLoginInfo().getDealerCode());
            if (CommonUtils.isNullOrEmpty(lsitoder)) {
                throw new ServiceBizException("数据异常!");
            }
            oder = lsitoder.get(0);
            if (!StringUtils.isNullOrEmpty(oder.getInteger("IS_UPLOAD"))
                && DictCodeConstants.DICT_IS_YES.equals(String.valueOf(oder.getInteger("IS_UPLOAD")))) {
                throw new ServiceBizException("该订单已上报,无法操作!");
            }

            if (!StringUtils.isNullOrEmpty(oder.getInteger("IS_ACHIEVE"))
                && DictCodeConstants.DICT_IS_YES.equals(String.valueOf(oder.getInteger("IS_ACHIEVE")))) {
                throw new ServiceBizException("该订单已完成,无法操作!");
            }
            oder.setString("ORDER_NO", ptoOrderdto.getOrderNo());
            oder.setString("CONTACTOR_NAME", ptoOrderdto.getContactorName());
            oder.setString("CUSTOMER_CODE", ptoOrderdto.getCustomerCode());
            oder.setString("CUSTOMER_NAME", ptoOrderdto.getCustomerName());
            oder.setString("ELEC_CODE", ptoOrderdto.getElecCode());
            oder.setString("MECH_CODE", ptoOrderdto.getMechCode());
            oder.setString("KEY_CODE", ptoOrderdto.getKeyCode());
            oder.setString("CHANGE", ptoOrderdto.getChange());
            oder.setString("EMERG", ptoOrderdto.getEmerg());
            oder.setString("VIN", ptoOrderdto.getVin());
            oder.setString("OWNER_NAME", ptoOrderdto.getOwnerName());
            oder.setString("MOBILE", ptoOrderdto.getMobile());
            oder.setString("LICENSE", ptoOrderdto.getLicense());
            oder.setString("SHEET_NO", ptoOrderdto.getSheetNo());
            oder.setString("CODE_ORDER_ONE_ID", ptoOrderdto.getCodeOrderOneId());
            oder.setString("CODE_ORDER_ONE_URL", ptoOrderdto.getCodeOrderOneUrl());
            oder.setString("CODE_ORDER_TWO_ID", ptoOrderdto.getCodeOrderTwoId());
            oder.setString("CODE_ORDER_TWO_URL", ptoOrderdto.getCodeOrderTwoUrl());
            oder.setString("CODE_ORDER_THREE_ID", ptoOrderdto.getCodeOrderThreeId());
            oder.setString("CODE_ORDER_THREE_URL", ptoOrderdto.getCodeOrderThreeUrl());
            oder.setTimestamp("FILLIN_TIME", ptoOrderdto.getFillinTime());
            oder.setTimestamp("GKFWB_DATE", ptoOrderdto.getGkfwbDate());
            // 并发会造成，金额读写错误update wanghui 2020.02-22
            // 在修改时取数据库中最新的明细条数更新主表项数字段
            oder.setInteger("ITEM_COUNT", this.computeOrderSize(ptoOrderdto.getOrderNo()));

            oder.setString("LOCK_USER", ptoOrderdto.getLockUser());
            oder.setTimestamp("ORDER_DATE", ptoOrderdto.getOrderDate());
            // oder.setInteger("ORDER_STATUS", ptoOrderdto.getOrderStatus());

            if (Utility.testString(oder.getString("ORDER_STATUS"))) {
                if (Utility.testString(oder.getString("IS_ACHIEVE"))
                    && DictCodeConstants.DICT_IS_YES.equals(oder.getString("IS_ACHIEVE"))) {
                    if (oder.getInteger("ORDER_STATUS") == 13611006) {
                        oder.setInteger("ORDER_STATUS", 13611001);
                    }
                }
            }
            oder.setDouble("ORDER_SUM", ptoOrderdto.getOrderSum());
            oder.setInteger("PART_ORDER_TYPE", ptoOrderdto.getPartOrderType());
            oder.setString("REMARK", ptoOrderdto.getRemark());
            oder.setInteger("SEND_MODE", ptoOrderdto.getSendMode());
            oder.setTimestamp("SUBMIT_TIME", ptoOrderdto.getSubmitTime());
            oder.setString("OEM_ORDER_NO", ptoOrderdto.getOemOrderNo());
            oder.setString("MAIN_ORDER_TYPE", ptoOrderdto.getMainOrderType());
            oder.setString("DOE", ptoOrderdto.getDoe());
            oder.setString("PDC", ptoOrderdto.getPdc());
            oder.setInteger("IS_LACK_GOODS", ptoOrderdto.getIsLackGoods());
            oder.setInteger("IS_ACHIEVE", ptoOrderdto.getIsAchieve());
            oder.setString("GROUP_CODE", ptoOrderdto.getGroupCode());
            oder.setInteger("LEAD_TIME", ptoOrderdto.getLeadTime());
            oder.setString("STORAGE_CODE", ptoOrderdto.getStorageCode());
            String strTmp = DbFunctionConvertUtil.reservedWordDeal(oder.toUpdate());
            System.err.println(strTmp);
            Base.exec(strTmp);

        }
        if (status != null && "D".equals(status.trim())) {

        }
        No = oder.getString("ORDER_NO");
        // 子表操作状态
        if (!CommonUtils.isNullOrEmpty(ptoOrderdto.getPartOrderItem())) {
            for (int i = 0; i < ptoOrderdto.getPartOrderItem().size(); i++) {
                TtPtDmsOrderItemDTO ItemDto = ptoOrderdto.getPartOrderItem().get(i);
                TtPtDmsOrderItemPO Item = TtPtDmsOrderItemPO.findById(ItemDto.getItemId());
                if (Item == null) {
                    Item = new TtPtDmsOrderItemPO();
                }
                Item.setString("ORDER_NO", No);
                Item.setDouble("COUNT", ItemDto.getCount());
                Item.setDouble("DELAY_COUNT", ItemDto.getDelayCount());
                Item.setString("MODEL_CODE", ItemDto.getModelCode());
                Item.setDouble("ONGOING_COUNT", ItemDto.getOngoingCount());
                Item.setString("PART_NAME", ItemDto.getPartName());
                Item.setString("PART_NO", ItemDto.getPartNo());
                Item.setDouble("QUOTA_COUNT", ItemDto.getQuotaCount());
                Item.setDouble("SIGNATURE_COUNT", ItemDto.getSignatureCount());
                Item.setDouble("TOTAL_PRICES", ItemDto.getTotalPrices());
                Item.setString("UNIT_CODE", ItemDto.getUnitCode());
                Item.setDouble("UNIT_PRICE", ItemDto.getUnitPrice());
                Item.setString("STORAGE_CODE", ItemDto.getStorageCode());
                if (!StringUtils.isNullOrEmpty(ItemDto.getOrderItemStatus())) {
                    Item.setInteger("ORDER_ITEM_STATUS", ItemDto.getOrderItemStatus());
                }
                Item.setInteger("IS_LACK_GOODS", ItemDto.getIsLackGoods());
                Item.setString("ARKTX", ItemDto.getArktx());
                Item.setDouble("NETWR", ItemDto.getNetwr());
                Item.setDouble("TOTAL", ItemDto.getTotal());
                Item.setDouble("OPNETWR", ItemDto.getOpnetwr());
                Item.setBigDecimal("OPTOTAL", ItemDto.getOptotal());
                if (!StringUtils.isNullOrEmpty(ItemDto.getHasChange())) {
                    Item.setInteger("HAS_CHANGE", 12781001);
                }

                Item.setInteger("IS_STOCK", ItemDto.getIsStock());
                Item.setDouble("NO_TAX_PRICE", ItemDto.getNoTaxPrice());
                Item.setDouble("SINGLE_DISCOUNT", ItemDto.getSingleDiscount());
                Item.setFloat("MIN_PACKAGE", ItemDto.getMinPackage());
                Item.setString("SHORT_ID", ItemDto.getShortId());
                if (!StringUtils.isNullOrEmpty(ItemDto.getDetailRemark())) {
                    Item.setString("DETAIL_REMARK", ItemDto.getDetailRemark());
                }
                Item.saveIt();
            }
        }
        // 子表删除操作
        if (!StringUtils.isNullOrEmpty(ptoOrderdto.getOrderItems())) {
            String[] ids = ptoOrderdto.getOrderItems().split(",");
            for (int i = 0; i < ids.length; i++) {
                String itemId = ids[i];
                LazyList<TtPtDmsOrderItemPO> item = DAOUtil.findByDealer(TtPtDmsOrderItemPO.class,
                                                                         "ITEM_ID=? and D_KEY=?", itemId,
                                                                         CommonConstants.D_KEY);
                TtPtDmsOrderItemPO itemPO = item.get(i);
                itemPO.setInteger("D_KEY", 1);
                itemPO.saveIt();
            }
        }
        return oder.toMap();
    }

    /**
     * // 根据order_no计算明细项数回写主表
     * 
     * @author zhanshiwei
     * @date 2017年4月24日
     * @param orderNo
     * @return
     * @throws Exception
     */

    public int computeOrderSize(String orderNo) throws Exception {
        int count = 0;

        StringBuffer sql = new StringBuffer("");
        sql.append("select count(*) as \"size\" from TT_PT_DMS_ORDER_ITEM where 1=1 and DEALER_CODE='"
                   + FrameworkUtil.getLoginInfo().getDealerCode() + "'AND ORDER_NO='" + orderNo + "'");
        List<Object> queryParam1 = new ArrayList<Object>();
        List<Map> result = DAOUtil.findAll(sql.toString(), queryParam1, false);
        if (!CommonUtils.isNullOrEmpty(result)) {
            count = Integer.parseInt(result.get(0).get("size").toString());
        }

        return count;
    }

    /**
     * 查询配件订货公式
     * 
     * @author zhanshiwei
     * @date 2017年4月25日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#queryDmsPartOrderFormula(java.util.Map)
     */

    @Override
    public List<Map> queryDmsPartOrderFormula(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        sql.append("SELECT DEALER_CODE,ITEM_ID,ORDER_PLAN_FORMULAS_NAME,FORMULAS_CODE,IS_AVAILABLE,EXP FROM TM_ORDER_FORMULAS_DEFINED WHERE 1=1 ");
        sql.append(" and IS_AVAILABLE = " + DictCodeConstants.DICT_IS_YES + "");
        return DAOUtil.findAll(sql.toString(), new ArrayList<Object>());
    }

    /**
     * 查询建议采购
     * 
     * @author zhanshiwei
     * @date 2017年4月25日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#queryDmsSuggestOrder(java.util.Map)
     */

    @Override
    public List<Map> queryDmsSuggestOrder(Map<String, String> queryParam) throws ServiceBizException {
        String mainSql = "";
        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
        String ids = queryParam.get("STORAGE_CODE");
        String sqlstr = queryParam.get("SQLSTR");
        String groupcode = queryParam.get("PART_GROUP_CODE");
        String storageCodeSql = "";
        String sql2 = "";
        if (ids != null) {
            String[] storageCode = ids.split(",");
            for (int s = 0; s < storageCode.length; s++) {
                storageCodeSql = storageCodeSql + " OR S.STORAGE_CODE='" + storageCode[s] + "'";
            }
            storageCodeSql = storageCodeSql + " AND (1!=1 " + storageCodeSql + ")";
        }
        mainSql = "SELECT 12781002 AS IS_SELECTED,P.PART_GROUP_CODE,P.OEM_TAG,P.REGULAR_PRICE,P.URGENT_PRICE,P.PLAN_PRICE,"
                  + "0.0 AS IS_LACK_GOODS,0.0 AS COUNT,0.0 AS DELAY_COUNT,0.0 AS TOTAL_PRICES,"
                  + "0.0 AS QUOTA_COUNT,0.0 AS ONGOING_COUNT ,"
                  + "(S.STOCK_QUANTITY + S.BORROW_QUANTITY - S.LEND_QUANTITY  - S.LOCKED_QUANTITY) AS USEABLE_STOCK,"
                  + " S.SALES_PRICE, 0.0 AS QUANTIYT_COUNT,  S.CLAIM_PRICE, S.LIMIT_PRICE, S.LATEST_PRICE, S.COST_PRICE,"
                  + "S.COST_AMOUNT,  S.BORROW_QUANTITY, S.LEND_QUANTITY, S.LOCKED_QUANTITY, S.PART_STATUS,  S.LAST_STOCK_IN,"
                  + "S.LAST_STOCK_OUT, S.FOUND_DATE, S.REMARK, S.STORAGE_CODE,S.PART_NO,S.PART_NAME, S.DEALER_CODE,S.MONTHLY_QTY_FORMULA,"
                  + "S.IS_SUGGEST_ORDER, S.STOCK_QUANTITY,S.MIN_STOCK,S.MAX_STOCK, P.IS_SJV,P.IS_MOP,p.MIN_PACKAGE,0.0 as SIGNATURE_COUNT,"
                  + sqlstr + " as  SUGGEST_QUANTITY" + " FROM (select * from TM_PART_STOCK where DEALER_CODE='"
                  + entityCode + "')S" + " left join (select * from (" + CommonConstants.VM_PART_INFO
                  + ") C where DEALER_CODE='" + entityCode + "')P"
                  + " ON S.DEALER_CODE=P.DEALER_CODE AND S.PART_NO=P.PART_NO" + " where 1=1 and S.PART_GROUP_CODE="
                  + groupcode + storageCodeSql + " and s.PART_STATUS=" + DictCodeConstants.DICT_IS_YES
                  + " and s.IS_SUGGEST_ORDER=" + DictCodeConstants.DICT_IS_YES;

        sql2 = "select * from ( " + mainSql + " ) a where SUGGEST_QUANTITY>0  AND A.PART_GROUP_CODE=" + groupcode
               + " AND A.OEM_TAG=12781001 ";
        return DAOUtil.findAll(sql2.toString(), new ArrayList<Object>());
    }

    /**
     * 库存配件缺料查询
     * 
     * @author zhanshiwei
     * @date 2017年4月25日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#queryDmsPartInfoAboutOrder(java.util.Map)
     */

    @Override
    public PageInfoDto queryDmsPartInfoAboutOrder(Map<String, String> queryParam) throws ServiceBizException {
        String storageCode = queryParam.get("storageCode");
        String partNo = queryParam.get("partNo");
        String startDate = queryParam.get("START_DATE");
        String endDate = queryParam.get("END_DATE");
        String shortType = queryParam.get("shortType");
        String mainOrderType = queryParam.get("MAIN_ORDER_TYPE");
        String partOrderType = queryParam.get("partOrderType");
        String pdc = queryParam.get("PDC");
        String procurementCode = queryParam.get("MAIN_ORDER_TYPE");
        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();

        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT " + DictCodeConstants.DICT_IS_NO + " AS IS_SELECTED, A.DEALER_CODE,"
                   + " P.procurement_code,A.PART_NO,C.IS_SJV,C.IS_MOP,A.STORAGE_CODE,A.PART_NAME ,SUM(A.SHORT_QUANTITY) AS SHORT_QUANTITY ,"
                   + " (B.STOCK_QUANTITY + B.BORROW_QUANTITY - B.LEND_QUANTITY - B.LOCKED_QUANTITY) AS USEABLE_STOCK , "
                   + " B.STOCK_QUANTITY ,B.LATEST_PRICE , B.BORROW_QUANTITY , B.LEND_QUANTITY , B.LOCKED_QUANTITY ,"
                   + " B.COST_PRICE , B.COST_AMOUNT , B.LAST_STOCK_IN , B.LAST_STOCK_OUT,C.REGULAR_PRICE, "
                   + " C.PART_MODEL_GROUP_CODE_SET , C.MIN_PACKAGE ,C.UNIT_CODE ,  C.MAX_STOCK , C.MIN_STOCK ,C.PLAN_PRICE, "
                   + " CASE " + partOrderType + " WHEN " + DictCodeConstants.DICT_PART_ORDER_TYPE_STOCK
                   + " THEN C.REGULAR_PRICE ELSE C.URGENT_PRICE END UNIT_PRICE " + " FROM TT_SHORT_PART A "
                   + " LEFT JOIN TM_PART_PDC P ON A.PART_NO=P.PART_NO AND P.DEALER_CODE=A.DEALER_CODE "
                   + " LEFT JOIN TM_PART_STOCK B ON B.DEALER_CODE = A.DEALER_CODE AND B.D_KEY = A.D_KEY AND "
                   + " B.PART_NO = A.PART_NO AND A.STORAGE_CODE = B.STORAGE_CODE " + " LEFT JOIN ("
                   + CommonConstants.VM_PART_INFO
                   + ") C ON  C.DEALER_CODE = A.DEALER_CODE AND C.D_KEY = A.D_KEY AND C.PART_NO = A.PART_NO "
                   + " WHERE A.DEALER_CODE = '" + entityCode + "' AND A.D_KEY = " + CommonConstants.D_KEY + "");
        sql.append(Utility.getLikeCond("A", "STORAGE_CODE", storageCode, "AND"));
        sql.append(Utility.getLikeCond("A", "PART_NO", partNo, "AND"));
        if (procurementCode != null && !"".equals(procurementCode)) {
            sql.append(" and P.procurement_code ='" + procurementCode + "'");
        }
        if (pdc != null && !"".equals(pdc)) {
            sql.append(" and P.PDC ='" + pdc + "'" + " AND P.pdc_Available=" + DictCodeConstants.DICT_IS_YES + "");
        }
        if (Utility.testString(partOrderType)) {
            if ("12481010".equals(partOrderType) || "12481011".equals(partOrderType)) {
                sql.append(" and (C.PART_GROUP_CODE=11361008  ) ");
            } else if ("12481005".equals(partOrderType) || "12481006".equals(partOrderType)
                       || "12481007".equals(partOrderType)) {
                sql.append(" and COALESCE(C.PART_GROUP_CODE,0)<>11361010 and COALESCE(C.PART_GROUP_CODE,0)<>11361008   ");
            } else if ("12481009".equals(partOrderType)) {
                sql.append(" and (C.PART_GROUP_CODE=11361010  ) ");
            }
            sql.append(" and c.DOWN_TAG=12781001 and COALESCE(c.PART_STATUS,0)<>12781001 ");

            sql.append("  AND NOT EXISTS ( SELECT 1 from tt_repair_order E  WHERE   A.DEALER_CODE = E.DEALER_CODE AND A.SHEET_NO = E.RO_NO  AND E.SCHEME_STATUS IS NOT NULL AND E.SCHEME_STATUS != 0 )");
        }
        if (shortType != null) sql.append(" AND A.SHORT_TYPE = " + shortType);
        sql.append(Utility.getDateCond("", "SEND_TIME", startDate, endDate));
        sql.append(" group by P.procurement_code, A.PART_NO, A.STORAGE_CODE,A.PART_NAME ,B.STOCK_QUANTITY ,B.LATEST_PRICE ,B.BORROW_QUANTITY ,"
                   + "B.LEND_QUANTITY ,c.is_sjv,c.is_mop,B.LOCKED_QUANTITY ,B.COST_PRICE , B.COST_AMOUNT , B.LAST_STOCK_IN ,B.LAST_STOCK_OUT,C.REGULAR_PRICE,"
                   + "C.PART_MODEL_GROUP_CODE_SET , C.MIN_PACKAGE ,C.UNIT_CODE ,C.MAX_STOCK , C.MIN_STOCK ,C.PLAN_PRICE, C.REGULAR_PRICE, C.URGENT_PRICE");

        List<Object> params = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), params);
    }

    @Override
    public PageInfoDto queryPartSalesHistory(Map<String, String> queryParam, String partNo, String storageCode) {
        String storage = "";
        String no = "";
        String code = "";
        String customerCode = queryParam.get("CUSTOMER_CODE");
        StringBuffer sql = new StringBuffer("");
        if (storageCode != null && !storageCode.equals("")) {
            storage = " and A.STORAGE_CODE  = '" + storageCode + "'  ";
        } else {
            storage = " and  1 = 1 ";
        }
        if (partNo != null && !partNo.equals("")) {
            no = " and A.PART_NO  = '" + partNo + "'  ";
        } else {
            no = " and  1 = 1 ";
        }
        if (customerCode != null && !customerCode.equals("")) {
            code = " and B.CUSTOMER_CODE = '" + customerCode + "'  ";
        } else {
            code = " and  1 = 1 ";
        }
        sql.append("SELECT A.DEALER_CODE,A.PART_NO, A.PART_NAME, A.PART_QUANTITY, A.PART_SALES_PRICE, A.PART_SALES_AMOUNT, "
                   + " B.CUSTOMER_NAME, B.CUSTOMER_CODE, A.SALES_PART_NO,A.SENDER,  A.PART_COST_PRICE, A.PART_COST_AMOUNT, "
                   + " A.FINISHED_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, " + " A.SEND_TIME "
                   + " FROM TT_SALES_PART_ITEM A LEFT JOIN TT_SALES_PART B ON A.SALES_PART_NO = B.SALES_PART_NO AND A.DEALER_CODE=B.DEALER_CODE "
                   + "WHERE 1=1" + "  AND A.IS_FINISHED = " + DictCodeConstants.DICT_IS_YES + " " + " AND A.D_KEY = "
                   + CommonConstants.D_KEY + "  " + storage + no + code);
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), params);
    }

    /**
     * 业务描述: 查询符合条件的库存配件
     * 
     * @author zhanshiwei
     * @date 2017年4月27日
     * @param queryParam
     * @return (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#queryDmsPartForOrder(java.util.Map)
     */

    @Override
    public PageInfoDto queryDmsPartForOrder(Map<String, String> queryParam) {
        if (!StringUtils.isNullOrEmpty(queryParam.get("listpartNo"))) {
            return queryPartOrderReplace(queryParam);
        } else {
            return queryDmsStockForOrder(queryParam);
        }
    }

    /**
     * 根据条件查询配件库存详细信息(DMS产品方法）
     * 
     * @author zhanshiwei
     * @date 2017年4月28日
     * @param queryParam
     * @return
     */
    public PageInfoDto queryDmsStockForOrder(Map<String, String> queryParam) {
        String storageCode = queryParam.get("storageCode");
        String brand = queryParam.get("BRAND");
        String partNo = queryParam.get("partNo");
        String name = queryParam.get("partName");
        String spellCode = queryParam.get("spellCode");
        String positionCode = queryParam.get("storagePositionCode");
        String model = queryParam.get("partModelGroupCodeSet");
        String partType = queryParam.get("partOrderType");
        String mainType = queryParam.get("MAIN_ORDER_TYPE");
        String procurementCode = queryParam.get("MAIN_ORDER_TYPE");
        String stockStandard = queryParam.get("STOCK_STANDARD");
        String partGroupCode = queryParam.get("PART_GROUP_CODE");
        String pageSize = null;// Utility.getDefaultValue("1065");
        String isQuantityNot0 = queryParam.get("IS_QUANTITY_NOT_0");
        String isPriceNot0 = queryParam.get("IS_PRICE_NOT_0");
        String flag = queryParam.get("FLAG");// add by xubufeng 2012-11-06 只能查出下发并且是用品的配件
        String pdcAvailable = queryParam.get("PDC_AVAILABLE");// PDC库存是否可用
        String inventoryStatus = queryParam.get("PROCUREMENT_CODE");// 采购代码
        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();

        StringBuffer sql = new StringBuffer("");
        String stor = "";
        String pdcCon = "";
        String no = "";
        String position = "";
        String spell = "";
        String mainTy = "";
        String partName = "";
        String partModel = "";
        String partBrand = "";
        String pdcString = "";
        String procuermentString = "";
        String avaString = "";
        String priceString = "";
        position = Utility.getLikeCond("A", "STORAGE_POSITION_CODE", positionCode, "AND");
        spell = Utility.getLikeCond("A", "SPELL_CODE", spellCode, "AND");
        stor = Utility.getLikeCond("A", "STORAGE_CODE", storageCode, "AND");
        no = Utility.getLikeCond("B", "PART_NO", partNo, "AND");

        if (mainType != null && !mainType.equals("")) {
            mainTy = " and P.PROCUREMENT_CODE = '" + mainType + "'  ";
        } else {
            mainTy = " and  1 = 1 ";
        }
        if (brand != null && brand.trim().length() > 0) {
            partBrand = " and CD.BRAND_CODE =  '" + brand + "'   ";
        } else {
            partBrand = " and  1=1 ";
        }
        partName = Utility.getLikeCond("B", "PART_NAME", name, "AND"); // and A.PART_NAME = name
        partModel = Utility.getLikeCond("B", "PART_MODEL_GROUP_CODE_SET", model, "AND");

        pdcCon = " (select INVENTORY_STATUS,PDC_AVAILABLE,part_no,DEALER_CODE,procurement_code,PDC from TM_PART_PDC  ";
        pdcCon = pdcCon + " group by part_no,DEALER_CODE,procurement_code,PDC,PDC_AVAILABLE,INVENTORY_STATUS) ";
        pdcString = " and  1 = 1 ";
        avaString = " and  1 = 1 ";
        sql.append("select * from (select a2.* ,@rownum:=@rownum+1 AS rn from ( ");

        if (partType != null && partType.trim().length() > 0) { // partType 为必传字段
            if (Utility.getDefaultValue("1195") != null
                && Utility.getDefaultValue("1195").equals(DictCodeConstants.DICT_IS_YES)) {
                priceString = " (case when " + partType + " = " + DictCodeConstants.DICT_DMS_PART_ORDER_TYPE_JJ + "  "
                              + " then b.URGENT_PRICE when " + partType + " = "
                              + DictCodeConstants.DICT_DMS_PART_ORDER_TYPE_BX
                              + " then b.MAINTAIN_PRICE else b.REGULAR_PRICE end ) as price,  ";
            } else priceString = " b.URGENT_PRICE as price,  ";
            sql.append(" SELECT b.is_SJV,B.is_MOP,p.PROCUREMENT_CODE,p.PDC_AVAILABLE,p.INVENTORY_STATUS,B.URGENT_PRICE,B.REGULAR_PRICE,A.DEALER_CODE, B.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, B.PART_NAME, "
                       + " A.SPELL_CODE, A.PART_GROUP_CODE, B.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, "
                       + " A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK, "
                       + " A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.PART_STATUS, "
                       + " A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER, "
                       + "  B.OPTION_NO, B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE, B.PART_MODEL_GROUP_CODE_SET,B.PLAN_PRICE,B.SALE_CODE,  "
                       + " B.PART_NAME_EN, B.IS_FREEZE,"
                       + " (case when B.IS_ACC =12781001 then B.IS_ACC ELSE 12781002 end) as IS_THINGS ,"
                       + " (case when B.DOWN_TAG=12781001 then B.DOWN_TAG ELSE 12781002 end) as DOWN_TAG ,B.PROVIDER_NAME,"
                       + priceString
                       + " D.OPTION_STOCK,  B.INSTRUCT_PRICE,B.MAINTAIN_PRICE,P.PDC,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK "
                       + " FROM (" + CommonConstants.VM_PART_INFO + ") B  LEFT OUTER JOIN  TM_PART_STOCK A "
                       + " ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  "
                       + "  and 1=1 AND A.D_KEY = 0 )" + " LEFT OUTER JOIN " + pdcCon
                       + " P ON B.PART_NO=P.PART_NO AND B.DEALER_CODE=P.DEALER_CODE  "
                       + " LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C GROUP BY DEALER_CODE,PART_NO ) D "
                       + " ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO ) left join ("
                       + CommonConstants.VM_BRAND + ") cd on b.brand=cd.BRAND_CODE and b.DEALER_CODE=cd.DEALER_CODE "
                       + "WHERE  B.DEALER_CODE = '" + entityCode + "' " + " AND B.D_KEY =  " + CommonConstants.D_KEY

                       + " AND coalesce(a.PART_STATUS,0) <> " + DictCodeConstants.DICT_IS_YES + avaString + stor + no
                       + position + spell

                       + partName + partBrand + partModel);

            if (stockStandard != null && stockStandard.equals(DictCodeConstants.DICT_IS_CHECKED)) {
                sql.append("  and (A.STOCK_QUANTITY-COALESCE(A.MIN_STOCK,0))<=0  ");
            }
            if (Utility.testString(flag)) {
                if ("12781001".equals(flag)) {
                    sql.append(" AND b.DOWN_TAG=" + DictCodeConstants.DICT_IS_YES + "  ");
                } else {
                    sql.append(" and  ( B.MAIN_ORDER_TYPE<>" + DictCodeConstants.DICT_MAIN_ORDER_TYPE_REF
                               + "  or B.MAIN_ORDER_TYPE is null   ) ");
                }

            }

            if (isQuantityNot0 != null && isQuantityNot0.equals(DictCodeConstants.DICT_IS_CHECKED)) {
                sql.append("  AND A.STOCK_QUANTITY > 0 ");
            }
            if (isPriceNot0 != null && isPriceNot0.equals(DictCodeConstants.DICT_IS_CHECKED)) {
                if (partType.equals(DictCodeConstants.DICT_DMS_PART_ORDER_TYPE_JJ))
                    sql.append("  AND B.URGENT_PRICE > 0 ");
                else if (partType.equals(DictCodeConstants.DICT_DMS_PART_ORDER_TYPE_BX))
                    sql.append("  AND B.MAINTAIN_PRICE > 0 ");
                else sql.append("  AND B.REGULAR_PRICE > 0 ");
            }
            if (partGroupCode != null && partGroupCode.trim().length() > 0) {
                sql.append("  and   b.PART_GROUP_CODE= " + partGroupCode + " ");
            } else {
                sql.append("   and COALESCE(b.PART_GROUP_CODE,0)<>11361008  and COALESCE(b.PART_GROUP_CODE,0)<>11361010 ");
            }
            // 存货状态，默认订单分类
            if (inventoryStatus != null && !"".equals(inventoryStatus.trim())) {
                sql.append(" AND P.PROCUREMENT_CODE='" + inventoryStatus + "' ");
            }
            sql.append(")  a2,(select @rownum :=0) rna  )  a1 ");
            // sql.append(")  a2,(select @rownum :=0) rna  )  a1 where a1.rn <=" + pageSize + " and a1.rn>0");
            /// --END
        }

        List<Object> params = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), params);
    }

    /**
     * @author zhanshiwei
     * @date 2017年4月27日
     * @param entityCode
     * @param partNo
     * @return
     */

    public TmPartInfoPO getReplaceParts(String entityCode, String partNo) {
        TmPartInfoPO infoPOcc = null;

        List<TmPartInfoPO> infoList = TmPartInfoPO.findBySQL("select * from TM_PART_INFO where DEALER_CODE=? and D_KEY=? and PART_NO=?",
                                                             entityCode, CommonConstants.D_KEY, partNo);
        if (infoList != null && infoList.size() > 0) {
            infoPOcc = (TmPartInfoPO) infoList.get(0);
        }
        return infoPOcc;
    }

    /**
     * 业务描述：配件订货计划界面根据配件代码查找替代件
     * 
     * @author zhanshiwei
     * @date 2017年4月27日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#queryDmsDeliverPartsReplace(java.util.Map)
     */

    @Override
    public List<Object> queryDmsDeliverPartsReplace(Map<String, String> queryParam) throws ServiceBizException {
        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
        String partNo = queryParam.get("PART_NO");
        List<Object> list = new ArrayList<>();
        String groupCode = Utility.getGroupEntity(entityCode, "TM_PART_INFO");
        TmPartInfoPO infoPO = getReplaceParts(entityCode, partNo);
        if (infoPO.getString("OPTION_NO") != null && !"".equals(infoPO.getString("OPTION_NO"))) {
            String tpartNo1[] = infoPO.getString("OPTION_NO").split(";");
            if (tpartNo1 != null && tpartNo1.length > 0) {
                for (int i = 0; i < tpartNo1.length; i++) {
                    TmPartInfoPO infoPO2cc = this.getReplaceParts(groupCode, tpartNo1[i]);
                    if (infoPO2cc != null) {
                        list.add(infoPO2cc.getString("PART_NO"));
                    }
                }
            }

        }
        return list;
    }

    /**
     * 查询配件替代件(配件订货计划)
     * 
     * @author zhanshiwei
     * @date 2017年4月28日
     * @param partsNo
     * @param pdc
     * @param mainOrderType
     * @param partOrderType
     * @return
     */

    public PageInfoDto queryPartOrderReplace(Map<String, String> queryParam) {
        String pdc = queryParam.get("PDC");
        String mainOrderType = queryParam.get("mainOrderType");
        String partsNo = queryParam.get("partsNo");
        String partOrderType = queryParam.get("partOrderType");
        String entityCode = FrameworkUtil.getLoginInfo().getDealerCode();
        StringBuffer sql = new StringBuffer("");
        String mainTy = "";
        String pdcCon = "";
        String pdcString = "";
        if (pdc != null && !"".equals(pdc)) {
            pdcCon = "TM_PART_PDC";
            pdcString = " and P.PDC ='" + pdc + "' ";
        } else {
            pdcCon = " (select part_no,DEALER_CODE,procurement_code,PDC,PDC_AVAILABLE from TM_PART_PDC group by part_no,DEALER_CODE,procurement_code,PDC,PDC_AVAILABLE) ";
            pdcString = " AND 1=1 ";
        }
        if (mainOrderType != null && !mainOrderType.equals("")) {
            mainTy = " and P.PROCUREMENT_CODE = '" + mainOrderType + "'  ";
        }
        sql.append(" SELECT B.IS_SJV,B.IS_MOP,B.URGENT_PRICE,B.REGULAR_PRICE,P.procurement_code,A.DEALER_CODE, B.PART_NO, A.FOUND_DATE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.PART_NAME, "
                   + " A.SPELL_CODE, A.PART_GROUP_CODE, A.UNIT_CODE, A.STOCK_QUANTITY, A.SALES_PRICE, "
                   + " A.CLAIM_PRICE, A.LIMIT_PRICE, A.LATEST_PRICE, A.COST_PRICE, A.COST_AMOUNT, A.MAX_STOCK, "
                   + " A.MIN_STOCK, A.BORROW_QUANTITY, A.LEND_QUANTITY, A.LOCKED_QUANTITY, A.PART_STATUS, "
                   + " A.LAST_STOCK_IN, A.LAST_STOCK_OUT, A.REMARK, A.VER, "
                   + "  B.OPTION_NO, B.PRODUCTING_AREA, B.BRAND, B.MIN_PACKAGE, B.PART_MODEL_GROUP_CODE_SET,B.PLAN_PRICE,  "
                   + " B.PART_NAME_EN, B.IS_FREEZE, (case when " + partOrderType + " = "
                   + DictCodeConstants.DICT_PART_ORDER_TYPE_STOCK + "  " + " or " + partOrderType + " = "
                   + DictCodeConstants.DICT_PART_ORDER_TYPE_VOR
                   + " then b.REGULAR_PRICE else b.URGENT_PRICE end ) as price,  "
                   + " D.OPTION_STOCK, B.MAINTAIN_PRICE,b.SALE_CODE,(CASE WHEN B.IS_ACC =12781001 THEN B.IS_ACC ELSE 12781002  END ) AS IS_THINGS,( CASE  WHEN B.DOWN_TAG=12781001 THEN B.DOWN_TAG  ELSE 12781002 END) AS DOWN_TAG,b.PROVIDER_NAME, B.INSTRUCT_PRICE,P.PDC_AVAILABLE,P.PDC,(A.STOCK_QUANTITY + A.BORROW_QUANTITY - A.LEND_QUANTITY - A.LOCKED_QUANTITY) AS USEABLE_STOCK "
                   + " FROM (" + CommonConstants.VM_PART_INFO + ") B  LEFT OUTER JOIN  TM_PART_STOCK A "
                   + " ON ( A.DEALER_CODE = B.DEALER_CODE AND A.PART_NO= B.PART_NO  " + "  and 1=1 )"
                   + " LEFT OUTER JOIN " + pdcCon + " P ON A.PART_NO=P.PART_NO AND A.DEALER_CODE=P.DEALER_CODE  "
                   + " LEFT OUTER JOIN (select DEALER_CODE, part_no, sum(C.STOCK_QUANTITY) AS OPTION_STOCK FROM TM_PART_STOCK C where c.part_status<>"
                   + DictCodeConstants.DICT_IS_YES + " GROUP BY DEALER_CODE,PART_NO ) D "
                   + " ON ( A.DEALER_CODE = D.DEALER_CODE AND A.PART_NO= D.PART_NO AND B.OPTION_NO = D.PART_NO ) left join ("
                   + CommonConstants.VM_BRAND + ") cd on b.brand=cd.brand_name " + "WHERE  " + " B.DEALER_CODE = '"
                   + entityCode + "' " + " AND B.D_KEY =  " + CommonConstants.D_KEY + mainTy + pdcString
                   + " and COALESCE(a.part_status,0)<>" + DictCodeConstants.DICT_IS_YES + " and b.DOWN_TAG= "
                   + DictCodeConstants.DICT_IS_YES);
        if (Utility.testString(partOrderType)) {
            if (DictCodeConstants.DICT_DMS_PART_ORDER_TYPE_E_CODE.equals(partOrderType)
                || DictCodeConstants.DICT_DMS_PART_ORDER_TYPE_S_CODE.equals(partOrderType)) {
                sql.append(" AND COALESCE(B.PART_GROUP_CODE,0)= " + DictCodeConstants.DICT_PART_CLASS_CHASSIS_NUMBER);
            } else if (DictCodeConstants.DICT_DMS_PART_ORDER_TYPE_TO.equals(partOrderType)) {
                sql.append(" AND COALESCE(B.PART_GROUP_CODE,0)= " + DictCodeConstants.DICT_PART_CLASS_THE_THIRD);
            } else {
                sql.append(" AND COALESCE(B.PART_GROUP_CODE,0) <>" + DictCodeConstants.DICT_PART_CLASS_CHASSIS_NUMBER
                           + " AND COALESCE(B.PART_GROUP_CODE,0)<> " + DictCodeConstants.DICT_PART_CLASS_THE_THIRD);
            }
        }

        if (!StringUtils.isNullOrEmpty(partsNo)) {
            String[] partsNos = partsNo.split(",");
            sql.append(" AND ( ");
            for (int i = 0; i < partsNos.length; i++) {
                sql.append(" B.PART_NO='" + partsNos[i] + "' ");
                if (i < (partsNos.length - 1)) {
                    sql.append(" OR ");
                }
                sql.append(" ) ");
            }

        }

        List<Object> params = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), params);
    }

    /**
     * @author zhanshiwei
     * @date 2017年5月1日
     * @param ttptdmsorderdto
     * @throws ServiceBizException
     * @throws Exception (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartPlaceanOrderService#deleteDmsPtOrderPlan(com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO)
     */

    @Override
    public int deleteDmsPtOrderPlan(TtPtDmsOrderDTO ttptdmsorderdto) throws ServiceBizException, Exception {
        String orderNo = ttptdmsorderdto.getOrderNo();
        String remark = ttptdmsorderdto.getRemark();
        if (this.checkPartOrderIsUpload(orderNo) == 1) {
            throw new ServiceBizException("保存错误,无法操作!");
        }

        TtPtDmsOrderPO orderPOCc = DAOUtil.findFirstByDealer(TtPtDmsOrderPO.class, "D_KEY=? and ORDER_NO=?",
                                                             CommonConstants.D_KEY, orderNo);
        if (orderPOCc != null) {
            orderPOCc.setString("REMARK", remark);
            orderPOCc.setInteger("IS_VALID", DictCodeConstants.DICT_IS_NO);
            orderPOCc.saveIt();
            return 1;
        }
        return 0;
    }

    public int checkPartOrderIsUpload(String partOrderNo) {
        List<TtPtDmsOrderPO> listReturn = DAOUtil.findByDealer(TtPtDmsOrderPO.class, "D_KEY=? and ORDER_NO=?",
                                                               CommonConstants.D_KEY, partOrderNo);
        if (listReturn != null && listReturn.size() > 0) {
            TtPtDmsOrderPO roPO = listReturn.get(0);
            if (DictCodeConstants.DICT_IS_YES.equals(String.valueOf(roPO.get("IS_UPLOAD")))) {
                return 1;
            } else {
                return 0;
            }
        }
        return 0;

    }
}
