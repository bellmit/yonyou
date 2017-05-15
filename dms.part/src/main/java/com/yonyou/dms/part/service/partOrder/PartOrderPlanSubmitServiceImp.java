
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : PartOrderPlanSubmitServiceImp.java
*
* @Author : zhanshiwei
*
* @Date : 2017年5月3日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年5月3日    zhanshiwei    1.0
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

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO;
import com.yonyou.dms.part.domains.PO.partOrder.TtPtDmsOrderPO;

/**
 * 配件订货上报
 * 
 * @author zhanshiwei
 * @date 2017年5月3日
 */
@Service
public class PartOrderPlanSubmitServiceImp implements PartOrderPlanSubmitService {

    /**
     * 描述：配件订单查询，根据本地订单号
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanSubmitService#queryDmsPtOrderInfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryDmsPtOrderInfo(Map<String, String> queryParam) throws ServiceBizException {
        String orderNo = queryParam.get("orderNo");
        String orderType = queryParam.get("orderType");
        String partNo = queryParam.get("partNo");
        return queryPTOrder(orderNo, orderType, partNo);
    }

    /**
     * 根据本地订单号查询配件信息
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param orderNo
     * @param orderType
     * @param partNo
     * @return
     */

    public PageInfoDto queryPTOrder(String orderNo, String orderType, String partNo) {
        StringBuffer sql = new StringBuffer();
        sql.append(" SELECT " + DictCodeConstants.DICT_IS_NO
                   + " AS IS_SELECTED, A.IS_MOP_ORDER,A.DEALER_CODE,A.ORDER_NO,A.OEM_ORDER_NO,A.ORDER_SUM, "
                   + " A.MAIN_ORDER_TYPE,A.PART_ORDER_TYPE, A.DOE,A.PDC,A.IS_LACK_GOODS,A.CUSTOMER_CODE,A.CUSTOMER_NAME, "
                   + " A.ITEM_COUNT,A.FILLIN_TIME,A.ORDER_DATE,A.GKFWB_DATE,A.VIN, "
                   + " A.SEND_MODE,A.CONTACTOR_NAME,A.SUBMIT_TIME,A.REMARK,A.ORDER_STATUS,A.IS_ACHIEVE,A.IS_UPLOAD,A.LOCK_USER, "
                   + " A.D_KEY,A.CREATED_BY,A.CREATED_AT,A.UPDATED_BY,A.UPDATED_AT,A.VER "
                   + " FROM TT_PT_DMS_ORDER A LEFT JOIN (" + CommonConstants.VM_PART_CUSTOMER
                   + ") B ON A.DEALER_CODE = B.DEALER_CODE AND A.CUSTOMER_CODE = B.CUSTOMER_CODE "

                   + " WHERE 1=1  AND A.IS_ACHIEVE = " + DictCodeConstants.DICT_IS_YES
                   + " AND Coalesce(A.IS_UPLOAD,0) <> " + DictCodeConstants.DICT_IS_YES + " AND Coalesce(A.is_valid,"
                   + DictCodeConstants.DICT_IS_YES + ") = " + DictCodeConstants.DICT_IS_YES);
        if (!StringUtils.isNullOrEmpty(orderType)) {
            sql.append(" AND A.PART_ORDER_TYPE = " + orderType + " ");
        }
        if (!StringUtils.isNullOrEmpty(partNo)) {
            sql.append(" AND EXISTS (SELECT * FROM TT_PT_DMS_ORDER_ITEM C WHERE A.DEALER_CODE = C.DEALER_CODE "
                       + " AND A.ORDER_NO = C.ORDER_NO AND C.PART_NO LIKE '%" + partNo + "%')");
        }
        sql.append(Utility.getLikeCond("A", "ORDER_NO", orderNo, "AND"));

        List<Object> params = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), params);

    }

    /**
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param OrderNo
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanSubmitService#modifyDmsPtOrderInfo(java.lang.String)
     */

    @Override
    public Map<String, Object> modifyDmsPtOrderInfo(String OrderNo) throws ServiceBizException {
        TtPtDmsOrderPO ptdmsOrder = TtPtDmsOrderPO.findByCompositeKeys(OrderNo,
                                                                       FrameworkUtil.getLoginInfo().getDealerCode());
        return ptdmsOrder != null ? ptdmsOrder.toMap() : null;
    }

    /**
     * 业务描述：对订单备注的更新操作
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param orderNo
     * @param ttPtOrderIntenDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanSubmitService#modifyUpdateDmsPtOrderRemark(java.lang.String,
     * com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderItemDTO)
     */

    @Override
    public void modifyUpdateDmsPtOrderRemark(String orderNo, TtPtDmsOrderDTO ttPtOrderDto) throws ServiceBizException {
        TtPtDmsOrderPO ptdmsOrder = TtPtDmsOrderPO.findByCompositeKeys(orderNo,
                                                                       FrameworkUtil.getLoginInfo().getDealerCode());
        ptdmsOrder.setString("REMARK", ttPtOrderDto.getRemark());
        ptdmsOrder.saveIt();
    }

    /**
     * 业务描述：查询订单明细
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanSubmitService#queryDmsPtOrderItemInfo(java.util.Map)
     */

    @Override
    public PageInfoDto queryDmsPtOrderItemInfo(Map<String, String> queryParam) throws ServiceBizException {
        StringBuffer sql = new StringBuffer();
        getPtOrderItemSql(sql, queryParam);
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.pageQuery(sql.toString(), params);
    }

    /**
     * sql
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param sql
     * @param queryParam
     */

    public void getPtOrderItemSql(StringBuffer sql, Map<String, String> queryParam) {
        String orderNo = queryParam.get("partNo");

        sql.append(" SELECT " + DictCodeConstants.DICT_IS_NO
                   + " AS IS_SELECTED ,AA.DETAIL_REMARK,AA.NO_TAX_PRICE,AA.SINGLE_Discount,AA.NETWR,AA.TOTAL ,AA.OPNETWR,AA.OPTOTAL, cc.REPORT_WAY,CC.IS_SJV,CC.IS_MOP,C.STORAGE_POSITION_CODE,"
                   + " AA.ITEM_ID,AA.ORDER_NO,AA.DEALER_CODE,AA.STORAGE_CODE,AA.PART_NO,AA.PART_NAME,AA.MODEL_CODE, "
                   + " AA.UNIT_CODE,AA.COUNT,AA.DELAY_COUNT,AA.UNIT_PRICE,AA.SIGNATURE_COUNT,"
                   + "AA.ONGOING_COUNT,AA.QUOTA_COUNT,AA.ORDER_ITEM_STATUS,AA.ORDER_CANCEL_STATUS,AA.TOTAL_PRICES,"
                   + "AA.IS_LACK_GOODS,AA.D_KEY, AA.CREATED_BY,AA.CREATED_AT,AA.UPDATED_BY,AA.UPDATED_AT,AA.VER "

                   // DYZ 2010-05-20 增加查询在订量字段
                   + ",( SELECT SUM(A.COUNT) from TT_PT_DMS_ORDER_ITEM A LEFT JOIN TT_PT_DMS_ORDER B ON A.ORDER_NO = B.ORDER_NO"
                   + " WHERE (B.ORDER_STATUS = " + DictCodeConstants.DICT_PORS_DEALING + " OR B.ORDER_STATUS = "
                   + DictCodeConstants.DICT_PORS_REPORT
                   + ") AND  A.ORDER_NO = B.ORDER_NO AND A.PART_NO = AA.PART_NO ) AS ORDER_COUNT,"
                   + " (case when CC.IS_ACC = 12781001 then CC.IS_ACC ELSE 12781002 end) as IS_ACC,"
                   + " (case when CC.DOWN_TAG = 12781001 then CC.DOWN_TAG ELSE 12781002 end) as DOWN_TAG "
                   + " FROM TT_PT_DMS_ORDER_ITEM AA LEFT JOIN TM_PART_STOCK C "
                   + " ON (AA.DEALER_CODE = C.DEALER_CODE AND AA.STORAGE_CODE = C.STORAGE_CODE AND AA.PART_NO = C.PART_NO)"
                   + " LEFT JOIN (" + CommonConstants.VM_PART_INFO
                   + ")  CC ON CC.DEALER_CODE = AA.DEALER_CODE AND CC.PART_NO = AA.PART_NO "
                   + "WHERE 1=1  AND AA.D_KEY = " + DictCodeConstants.D_KEY);
        sql.append(Utility.getLikeCond(null, "ORDER_NO", orderNo, "AND"));

        if (!StringUtils.isNullOrEmpty(queryParam.get("IS_SJV"))) {
            sql.append(" and CC.IS_SJV=").append(DictCodeConstants.DICT_IS_NO).append("\n");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("IS_MOP"))) {
            sql.append(" and CC.IS_MOP=").append(DictCodeConstants.DICT_IS_YES).append("\n");
        }
    }

    /**
     * 导出
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param queryParam
     * @throws Exception (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanSubmitService#queryDmsPtOrderItemInfoExport(java.util.Map)
     */

    @Override
    public List<Map> queryDmsPtOrderItemInfoExport(Map<String, String> queryParam) throws Exception {
        StringBuffer sql = new StringBuffer();
        getPtOrderItemSql(sql, queryParam);
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sql.toString(), params);
    }

    /**
     * 业务描述：更新配件主表完成状态
     * 
     * @author zhanshiwei
     * @date 2017年5月3日
     * @param ttPtOrderDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanSubmitService#MaintainDmsPtOrderAchieve(com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO)
     */

    @Override
    public void MaintainDmsPtOrderAchieve(TtPtDmsOrderDTO ttPtOrderDto) throws ServiceBizException {
        String orderNo = ttPtOrderDto.getPtpartOrder().get(0).getItemOrderNos();
        if (StringUtils.isNullOrEmpty(orderNo)) {
            throw new ServiceBizException("保存错误!");
        }
        if (this.checkPartOrderIsUpload(orderNo) == 1) {
            throw new ServiceBizException("保存错误!该订单已完成,无法操作!");
        }
        TtPtDmsOrderPO ptdmsOrder = TtPtDmsOrderPO.findByCompositeKeys(orderNo,
                                                                       FrameworkUtil.getLoginInfo().getDealerCode());
        ptdmsOrder.setInteger("IS_ACHIEVE", DictCodeConstants.DICT_IS_NO);
        ptdmsOrder.saveIt();

    }

    /**
     * 功能描述：判断订单是否上报 1 : 上报 0 : 没有上报
     * 
     * @param conn
     * @param entityCode
     * @param partOrderNo
     * @return
     * @throws Exception
     */
    public int checkPartOrderIsUpload(String partOrderNo) {

        List<TtPtDmsOrderPO> listReturn = TtPtDmsOrderPO.findBySQL("select * from TT_PT_DMS_ORDER where ORDER_NO=? and DEALER_CODE=? and D_KEY=?",
                                                                   partOrderNo,
                                                                   FrameworkUtil.getLoginInfo().getDealerCode(),
                                                                   CommonConstants.D_KEY);

        if (listReturn != null && listReturn.size() > 0) {
            TtPtDmsOrderPO roPO = new TtPtDmsOrderPO();
            roPO = listReturn.get(0);
            if (DictCodeConstants.DICT_IS_YES.equals(String.valueOf(roPO.getInteger("IS_UPLOAD")))) {
                return 1;
            } else {
                return 0;
            }
        } else {
            return 0;
        }

    }

    /**
     * 业务描述：配件计划作废功能
     * 
     * @author zhanshiwei
     * @date 2017年5月4日
     * @param ttPtOrderDto
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanSubmitService#deleteDmsPtOrderPlan(com.yonyou.dms.part.domains.DTO.partOrder.TtPtDmsOrderDTO)
     */

    @Override
    public void deleteDmsPtOrderPlan(TtPtDmsOrderDTO ttPtOrderDto) throws ServiceBizException {
        String orderNo = ttPtOrderDto.getOrderNo();
        if (StringUtils.isNullOrEmpty(orderNo)) {
            throw new ServiceBizException("保存错误!");
        }
        if (this.checkPartOrderIsUpload(orderNo) == 1) {
            throw new ServiceBizException("保存错误!该订单已完成,无法操作!");
        }
        TtPtDmsOrderPO ptdmsOrder = TtPtDmsOrderPO.findByCompositeKeys(orderNo,
                                                                       FrameworkUtil.getLoginInfo().getDealerCode());
        ptdmsOrder.setInteger("IS_ACHIEVE", DictCodeConstants.DICT_IS_NO);
        ptdmsOrder.setString("REMARK", ttPtOrderDto.getRemark());
        ptdmsOrder.saveIt();
    }

    /**
     * @author zhanshiwei
     * @date 2017年5月4日
     * @param queryParam
     * @return
     * @throws Exception (non-Javadoc)
     * @see com.yonyou.dms.part.service.partOrder.PartOrderPlanSubmitService#queryMoDmsPtOrderItemInfoExport(java.util.Map)
     */

    @Override
    public List<Map> queryMoDmsPtOrderItemInfoExport(Map<String, String> queryParam) throws Exception {
        StringBuffer sql = new StringBuffer();
        getPtOrderItemSql(sql, queryParam);
        List<Object> params = new ArrayList<Object>();
        return DAOUtil.findAll(sql.toString(), params);
    }

}
