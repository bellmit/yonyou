
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : OrderLabourDetailServiceImpl.java
*
* @Author : rongzoujie
*
* @Date : 2016年9月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月20日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.repair.service.repairDispatching;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.commonAS.domains.DTO.order.RoRepairProDTO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.domains.DTO.repairDispatching.OrderDispatchDetailTransFormDTO;
import com.yonyou.dms.repair.domains.PO.repairDispatching.OrderDispatchDetailPO;
import com.yonyou.dms.repair.domains.PO.repairDispatching.OrderLabourDetailPO;

/**
 * TODO description
 * 
 * @author rongzoujie
 * @date 2016年9月20日
 */
@Service
public class OrderLabourDetailServiceImpl implements OrderLabourDetailService {
    
    @Autowired
    private OperateLogService operateLogService;
    
    
    /**
     * 查询工单维修项目明细
     * 
     * @author rongzoujie
     * @date 2016年9月20日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#queryRepair(java.util.Map)
     */
    @Override
    public PageInfoDto queryRepairProDetail(Long orderWorderId) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = getQuerySql(orderWorderId, params);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql, params);
        return pageInfoDto;
    }

    /**
     * 查询工单维修项目明细sql
     * 
     * @author rongzoujie
     * @date 2016年9月23日
     * @param queryParam
     * @param params
     * @return
     */
    private String getQuerySql(Long orderWorderId, List<Object> params) {
        StringBuilder sql = new StringBuilder("SELECT t2.ASSIGN_ID,t2.FACT_LABOUR_HOUR,t1.RO_LABOUR_ID,t1.DEALER_CODE,t1.LABOUR_CODE,t1.LABOUR_NAME,t1.LOCAL_LABOUR_CODE,t1.LOCAL_LABOUR_NAME,t1.LABOUR_AMOUNT,t1.ASSIGN_LABOUR_HOUR,t4.CHARGE_PARTITION_NAME,");
        sql.append("( case when t1.ASSIGN_TAG = 1 then '已派' when t1.ASSIGN_TAG = 2 then '合派' ELSE '未派' end ) as ASSIGN_TAG,group_concat(t3.EMPLOYEE_NAME) as TECHNICIAN");
        sql.append(" FROM tt_ro_labour t1 left join tt_ro_assign t2 on t1.RO_LABOUR_ID = t2.RO_LABOUR_ID and t1.DEALER_CODE = t2.DEALER_CODE LEFT JOIN tm_employee t3 on t3.EMPLOYEE_NO = t2.TECHNICIAN AND t1.DEALER_CODE = t3.DEALER_CODE LEFT JOIN tm_charge_partition t4 on t1.DEALER_CODE = t4.DEALER_CODE and t1.CHARGE_PARTITION_CODE = t4.CHARGE_PARTITION_CODE where 1=1 and t1.RO_ID = ? GROUP BY RO_LABOUR_ID");
        params.add(orderWorderId);
        return sql.toString();
    }
    
    /**
     * 完工页面的表格查询
     */
    @Override
    public PageInfoDto queryRepairProDetailForFinish(Long orderWorderId) throws ServiceBizException {
        List<Object> params = new ArrayList<Object>();
        String sql = getQuerySqlForFinish(orderWorderId, params);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql, params);
        return pageInfoDto;
    }
    
    /**
     * 完工页面的表格查询sqll
     * @param orderWorderId
     * @param params
     * @return
     */
    private String getQuerySqlForFinish(Long orderWorderId, List<Object> params) {
        StringBuilder sql = new StringBuilder("SELECT t2.ASSIGN_ID,t2.FACT_LABOUR_HOUR,t1.RO_LABOUR_ID,t1.DEALER_CODE,t1.LABOUR_CODE,t1.LABOUR_NAME,t1.LOCAL_LABOUR_CODE,t1.LOCAL_LABOUR_NAME,t1.LABOUR_AMOUNT,t1.ASSIGN_LABOUR_HOUR,t1.CHARGE_PARTITION_CODE,");
        sql.append("( case when t1.ASSIGN_TAG = 1 then '已派' when t1.ASSIGN_TAG = 2 then '合派' ELSE '未派' end ) as ASSIGN_TAG,group_concat(t3.EMPLOYEE_NAME) as TECHNICIAN");
        sql.append(" FROM tt_ro_labour t1 left join tt_ro_assign t2 on t1.RO_LABOUR_ID = t2.RO_LABOUR_ID and t1.DEALER_CODE = t2.DEALER_CODE LEFT JOIN tm_employee t3 on t3.EMPLOYEE_NO = t2.TECHNICIAN AND t1.DEALER_CODE = t3.DEALER_CODE where 1=1 and t1.RO_ID = ? and (t1.ASSIGN_TAG = 1 or t1.ASSIGN_TAG = 2) GROUP BY RO_LABOUR_ID");
        params.add(orderWorderId);
        return sql.toString();
    }
    

    /**
     * 添加维修项目明细
     * 
     * @author rongzoujie
     * @date 2016年9月23日
     * @param roReProDto
     * @param roId
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#addReapirProDetail(com.yonyou.dms.commonAS.domains.DTO.order.RoRepairProDTO,
     * java.lang.Long)
     */
    @Override
    public void addReapirProDetail(RoRepairProDTO roReProDto, Long roId) throws ServiceBizException {
        OrderLabourDetailPO orderLabourDetailPO = new OrderLabourDetailPO();
        setAddReapirProDetail(orderLabourDetailPO, roReProDto, roId);
        orderLabourDetailPO.saveIt();
    }

    /**
     * 字段设置
     * 
     * @author rongzoujie
     * @date 2016年9月23日
     * @param orderLabourDetailPO
     * @param roReProDto
     * @param roId
     */
    private void setAddReapirProDetail(OrderLabourDetailPO orderLabourDetailPO, RoRepairProDTO roReProDto, Long roId) {
        orderLabourDetailPO.setLong("RO_ID", roId);
        orderLabourDetailPO.setString("RO_NO", getRepairRONO(roId));
        if (!StringUtils.isNullOrEmpty(roReProDto.getChargePartitionCode())) {
//            String chargePartitionCode = getChargePatitionCode(roReProDto.getchargePartitionName());
//            if (!StringUtils.isNullOrEmpty(chargePartitionCode)) {
                orderLabourDetailPO.setString("CHARGE_PARTITION_CODE", roReProDto.getChargePartitionCode());
//            }
        }
        String labourCode = roReProDto.getLabourCode();
        String modelGroupCode = roReProDto.getModeGroup();
        int repairTypeInt = getRepairTypeInt(labourCode, modelGroupCode);

        orderLabourDetailPO.setString("LABOUR_CODE", roReProDto.getLabourCode());
        orderLabourDetailPO.setString("LABOUR_NAME", roReProDto.getLabourName());
        orderLabourDetailPO.setDouble("STD_LABOUR_HOUR", Double.parseDouble(roReProDto.getStdHour().toString()));
        if(!StringUtils.isNullOrEmpty(roReProDto.getAssignLabourHour())){
	        orderLabourDetailPO.setDouble("ASSIGN_LABOUR_HOUR",
	                                      Double.parseDouble(roReProDto.getAssignLabourHour().toString()));
        }
        orderLabourDetailPO.setDouble("LABOUR_PRICE",
                                      Double.parseDouble(roReProDto.getWorkHourSinglePrice().toString()));
        orderLabourDetailPO.setDouble("LABOUR_AMOUNT", Double.parseDouble(roReProDto.getWorkHourPrice().toString()));
        orderLabourDetailPO.setDouble("DISCOUNT", Double.parseDouble(roReProDto.getDiscountRate().toString()));
        orderLabourDetailPO.setDouble("AFTER_DISCOUNT_AMOUNT",
                                      Double.parseDouble(roReProDto.getReceiveMoney().toString()));
        orderLabourDetailPO.setString("MODEL_LABOUR_CODE", roReProDto.getModeGroup());
        orderLabourDetailPO.setString("LOCAL_LABOUR_CODE", roReProDto.getLocalLabourCode());
        orderLabourDetailPO.setString("LOCAL_LABOUR_NAME", roReProDto.getLocalLabourName());
        orderLabourDetailPO.setInteger("REPAIR_TYPE_CODE", repairTypeInt);
    }

    /**
     * 查找工单号
     * 
     * @author rongzoujie
     * @date 2016年9月22日
     * @param roid
     * @return
     */
    private String getRepairRONO(Long roid) {
        StringBuilder sql = new StringBuilder("select RO_NO,DEALER_CODE from TT_REPAIR_ORDER where 1=1 ");
        sql.append("and RO_ID = ?");
        List<Object> params = new ArrayList<Object>();
        params.add(roid);
        return DAOUtil.findFirst(sql.toString(), params).get("RO_NO").toString();
    }

    /**
     * 查询维修项目中的维修类型Integer
     * 
     * @author rongzoujie
     * @date 2016年9月22日
     * @param labourCode
     * @param modelGroupCode
     * @return
     */
    private Integer getRepairTypeInt(String labourCode, String modelGroupCode) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("select repair_type_code,DEALER_CODE from tm_labour where 1=1");
        sql.append(" and LABOUR_CODE = ?");
        params.add(labourCode);
        sql.append(" and MODEL_LABOUR_CODE =?");
        params.add(modelGroupCode);
        String repairCodeStr = DAOUtil.findFirst(sql.toString(), params).get("repair_type_code").toString();
        return Integer.parseInt(repairCodeStr);
    }

    /**
     * 获取收费区分的code TODO description
     * 
     * @author rongzoujie
     * @date 2016年9月23日
     * @param chargePartitionName
     * @return
     */
    private String getChargePatitionCode(String chargePartitionName) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT dealer_code,charge_partition_code FROM tm_charge_partition WHERE 1 = 1");
        sql.append(" and CHARGE_PARTITION_NAME = ?");
        params.add(chargePartitionName);
        List<Map> result = DAOUtil.findAll(sql.toString(), params);
        if (result.size() > 0) {
            return result.get(0).get("CHARGE_PARTITION_CODE").toString();
        }
        return null;
    }

    /**
     * 根据id获取维修项目信息
     * 
     * @author ZhengHe
     * @date 2016年9月26日
     * @param id
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#queryReapirPro(java.lang.Long)
     */

    @Override
    public List<Map> queryReapirPro(Long id) throws ServiceBizException {
        StringBuffer sqlsb = new StringBuffer("SELECT trl.RO_LABOUR_ID,trl.DEALER_CODE,trl.RO_ID,trl.RO_NO,trl.REPAIR_TYPE_CODE,trl.CHARGE_PARTITION_CODE,trl.LABOUR_CODE,trl.LABOUR_NAME,");
        sqlsb.append("trl.LOCAL_LABOUR_CODE,trl.LOCAL_LABOUR_NAME,trl.STD_LABOUR_HOUR,trl.ASSIGN_LABOUR_HOUR,trl.LABOUR_PRICE,trl.LABOUR_AMOUNT,trl.DISCOUNT,");
        sqlsb.append("trl.AFTER_DISCOUNT_AMOUNT,trl.TROUBLE_DESC,trl.TECHNICIAN,trl.WORKER_TYPE_CODE,trl.REMARK,trl.ASSIGN_TAG,trl.ACTIVITY_CODE,trl.PACKAGE_CODE,trl.MODEL_LABOUR_CODE,");
        sqlsb.append(" tcp.CHARGE_PARTITION_NAME, ");
        sqlsb.append(" rt.REPAIR_TYPE_NAME AS REPAIR_TYPE_NAME,(trl.LABOUR_AMOUNT-AFTER_DISCOUNT_AMOUNT) as DIS_LABOUR_AMOUNT ");
        sqlsb.append(" from tt_ro_labour as trl ");
        sqlsb.append(" left join tm_charge_partition tcp on tcp.DEALER_CODE=trl.DEALER_CODE and tcp.CHARGE_PARTITION_CODE=trl.CHARGE_PARTITION_CODE ");
        sqlsb.append("left join  tm_repair_type rt on trl.DEALER_CODE=rt.DEALER_CODE and trl.REPAIR_TYPE_CODE=rt.REPAIR_TYPE_CODE ");
        sqlsb.append(" where 1=1 ");
        sqlsb.append(" and trl.RO_ID=?");
        List<Long> queryParam = new ArrayList<Long>();
        queryParam.add(id);
        List<Map> trlList = new ArrayList<Map>();
        trlList = DAOUtil.findAll(sqlsb.toString(), queryParam);
        return trlList;
    }

    /**
     * 添加所有派工工单明细
     * 
     * @author rongzoujie
     * @date 2016年10月10日
     * @param roId
     * @throws ServiceBizException
     */
    public void addAllOrderLabourDetail(OrderDispatchDetailTransFormDTO oDDTFD) throws ServiceBizException {
        Long roId = Long.parseLong(oDDTFD.getSelectRepairOrder());
        StringBuilder sql = new StringBuilder("select dealer_code,ro_labour_id from tt_ro_labour where ro_id = ?");
        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(roId);
        List<Map> repairPro = DAOUtil.findAll(sql.toString(), queryParam);
        if (repairPro.size() > 0) {
            for (int i = 0; i < repairPro.size(); i++) {
                OrderDispatchDetailPO orderDispatchDetailPO = new OrderDispatchDetailPO();
                Long labourId = Long.parseLong(repairPro.get(i).get("ro_labour_id").toString());
                setAddOrderLabourDetail(orderDispatchDetailPO, oDDTFD, labourId);
                orderDispatchDetailPO.saveIt();
                setRepairProDetail(labourId);
            }
        }
        Long roid = Long.parseLong(oDDTFD.getSelectRepairOrder().toString());
        setOrderAssign(roid);
        setWorkerOrderRoStatus(roid);
    }

    /**
     * 添加选择派工工单明细
     * 
     * @author rongzoujie
     * @date 2016年9月27日
     * @param oDDTFD
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#addOrderLabourDetail(com.yonyou.dms.repair.domains.DTO.repairDispatching.OrderDispatchDetailTransFormDTO)
     */
    @Override
    public void addOrderLabourDetail(OrderDispatchDetailTransFormDTO oDDTFD) throws ServiceBizException {
        String repairProIdsStr = oDDTFD.getSelectRepairPro();
        if (repairProIdsStr.indexOf("-") == -1) {
            OrderDispatchDetailPO oDDPO = new OrderDispatchDetailPO();
            setAddOrderLabourDetail(oDDPO, oDDTFD, Long.parseLong(oDDTFD.getSelectRepairPro()));
            oDDPO.saveIt();
            setRepairProDetail(Long.parseLong(oDDTFD.getSelectRepairPro()));
        } else {
            String[] repairProIds = oDDTFD.getSelectRepairPro().split("-");
            for (int i = 0; i < repairProIds.length; i++) {
                OrderDispatchDetailPO oDDPO = new OrderDispatchDetailPO();
                setAddOrderLabourDetail(oDDPO, oDDTFD, Long.parseLong(repairProIds[i]));
                oDDPO.saveIt();
                setRepairProDetail(Long.parseLong(repairProIds[i]));
            }
        }
        Long roid = Long.parseLong(oDDTFD.getSelectRepairOrder().toString());
        setWorkerOrderRoStatus(roid);
        setOrderAssign(roid);
    }
    
    /**
     * 设置工单状态已派工
     * @param roId
     */
    private void setWorkerOrderRoStatus(Long roId){
    	RepairOrderPO repairOrderPO = RepairOrderPO.findById(roId);
    	repairOrderPO.setLong("RO_STATUS",12151002);
    	repairOrderPO.saveIt();
    }
    /**
     * 设置派工项目窗口派工标志
     * 
     * @author rongzoujie
     * @date 2016年10月9日
     * @param roLabourId
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#addOrderLabourDetail(com.yonyou.dms.repair.domains.DTO.repairDispatching.OrderDispatchDetailTransFormDTO)
     */
    @Override
    public void setRepairProDetail(Long roLabourId)throws ServiceBizException {
        OrderLabourDetailPO orderLabourDetailPO = OrderLabourDetailPO.findById(roLabourId);
        // 计算维修项目下的维修派工工单明细数量
        StringBuilder sql = new StringBuilder("select DEALER_CODE,RO_ID,ro_labour_id from tt_ro_assign where ro_labour_id = ?");
        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(roLabourId);
        List<Map> dispatchDetail = DAOUtil.findAll(sql.toString(), queryParam);
        if (dispatchDetail.size() > 1) {
            orderLabourDetailPO.setLong("ASSIGN_TAG", 2);// 合派
        } else if (dispatchDetail.size() == 1) {
            orderLabourDetailPO.setLong("ASSIGN_TAG", 1);// 已派
        } else {
            orderLabourDetailPO.setLong("ASSIGN_TAG", null);// 未派工
        }
        orderLabourDetailPO.saveIt();
        StringBuilder sqlsb = new StringBuilder("select DEALER_CODE,RO_ID,RO_LABOUR_ID from tt_ro_labour where 1=1 and RO_LABOUR_ID = ?");
        List<Object> queryParams = new ArrayList<Object>();
        queryParams.add(roLabourId);
        Map roMap = DAOUtil.findFirst(sqlsb.toString(), queryParams);
        setOrderAssign(Long.parseLong(roMap.get("RO_ID").toString()));
    }

    /**
     * 设置派工工单明细添加字段 TODO description
     * 
     * @author rongzoujie
     * @date 2016年9月27日
     */
    private void setAddOrderLabourDetail(OrderDispatchDetailPO oDDPO, OrderDispatchDetailTransFormDTO oDDTFD,
                                         Long roLabourId) {
        Long roId = Long.parseLong(oDDTFD.getSelectRepairOrder());
        oDDPO.setLong("RO_ID", roId);
        oDDPO.setLong("RO_LABOUR_ID", roLabourId);
        oDDPO.setLong("REPAIR_POSITION_ID", oDDTFD.getDispatchPositions());
        oDDPO.setLong("WORKER_TYPE_ID", oDDTFD.getWorkTypeId());
        oDDPO.setString("TECHNICIAN", oDDTFD.getDispatchTechnicianSelect());
        oDDPO.setString("ASSIGN_LABOUR_HOUR", oDDTFD.getAssignLabourHourForDisPatch());
        oDDPO.setTimestamp("ITEM_START_TIME", oDDTFD.getItemStartTime());
        oDDPO.setTimestamp("ESTIMATE_END_TIME", oDDTFD.getEstimateEndTime());
        if (!StringUtils.isNullOrEmpty(oDDTFD.getFinishUser())) {
            oDDPO.setString("CHECKER", oDDTFD.getFinishUser());
        }
        oDDPO.setDate("ASSIGN_TIME", new Date());

    }

    /**
     * 维修派工工单明细
     * 
     * @author rongzoujie
     * @date 2016年9月28日
     * @param queryParam
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#queryRepairProDispatchDteail(java.util.Map)
     */
    @Override
    public PageInfoDto queryRepairProDispatchDteail(Long roLabourId) throws ServiceBizException {
        List<Object> queryParam = new ArrayList<Object>();
        StringBuilder sql = new StringBuilder("SELECT t1.RO_LABOUR_ID,t1.DEALER_CODE,t2.EMPLOYEE_NAME,t3.LABOUR_POSITION_NAME,t4.WORKER_TYPE_NAME,t1.ASSIGN_LABOUR_HOUR,t1.ASSIGN_TIME,t1.ITEM_START_TIME,t1.ESTIMATE_END_TIME,t1.ITEM_END_TIME,t1.FINISHED_TAG,t1.CHECKER,t1.ASSIGN_ID FROM tt_ro_assign t1");
        sql.append(" LEFT JOIN tm_employee t2 ON t2.EMPLOYEE_NO = t1.TECHNICIAN");
        sql.append(" LEFT JOIN tm_repair_position t3 on t3.REPAIR_POSITION_ID = T1.REPAIR_POSITION_ID");
        sql.append(" LEFT JOIN tm_worker_type t4 on t1.WORKER_TYPE_ID = t4.WORKER_TYPE_ID");
        sql.append(" where 1=1 and t1.RO_LABOUR_ID = ?");
        queryParam.add(roLabourId);
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sql.toString(), queryParam);
        return pageInfoDto;
    }

    /**
     * 更新派工完工标志
     * 
     * @author rongzoujie
     * @date 2016年10月3日
     * @param roLabourId
     * @param finishDispatchIds
     * @param unfinishDispatchIds
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#setFinishDisPatch(java.lang.Long,
     * java.lang.String, java.lang.String)
     */
    @Override
    public void setFinishDisPatch(String finishDispatchIds, String unfinishDispatchIds) throws ServiceBizException {
        if (!StringUtils.isNullOrEmpty(finishDispatchIds)) {
            setFinishDisPatchSql(finishDispatchIds, true);
        }
        if (!StringUtils.isNullOrEmpty(unfinishDispatchIds)) {
            setFinishDisPatchSql(unfinishDispatchIds, false);
        }
    }

    private void setFinishDisPatchSql(String DispatchIdsStr, boolean finishFlag) {
        String[] finishDispatchIdsArr = DispatchIdsStr.split("-");
        for (int i = 0; i < finishDispatchIdsArr.length; i++) {
            OrderDispatchDetailPO orderDispatchDetailPO = OrderDispatchDetailPO.findById(Long.parseLong(finishDispatchIdsArr[i]));
            if (finishFlag) {
                orderDispatchDetailPO.setLong("FINISHED_TAG", 10131001);
                orderDispatchDetailPO.setDate("ITEM_END_TIME", new Date());
            } else {
                orderDispatchDetailPO.setLong("FINISHED_TAG", null);
                orderDispatchDetailPO.setDate("ITEM_END_TIME", null);
            }

            orderDispatchDetailPO.saveIt();
        }

    }
    
    private void setDeleteOrderStatus(Long roId){
    	StringBuilder sql = new StringBuilder("select DEALER_CODE,ASSIGN_ID from tt_ro_assign where ro_id = ? ");
    	List<Object> param = new ArrayList<Object>();
    	param.add(roId);
    	List<Map> result = DAOUtil.findAll(sql.toString(), param);
    	if(result.size()<=0){
    		RepairOrderPO repairOrderPO = RepairOrderPO.findById(roId);
    		repairOrderPO.setInteger("RO_STATUS",DictCodeConstants.NEW_CREATE_STATUS);
    		repairOrderPO.saveIt();
    	}
    }
    
    /**
     * 删除派工明细
     * 
     * @author rongzoujie
     * @date 2016年10月6日
     * @param assignId
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#detleteDispatchDetail(java.lang.Long)
     */
    @Override
    public void detleteDispatchDetail(Long assignId) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("select dealer_code,ro_labour_id,ro_id from tt_ro_assign where assign_id = ?");
        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(assignId);
        Map result = DAOUtil.findFirst(sql.toString(), queryParam);
        Long labourId = Long.parseLong(result.get("RO_LABOUR_ID").toString());
        Long roId = Long.parseLong(result.get("RO_ID").toString());
        OrderDispatchDetailPO orderDispatchDetailPO = OrderDispatchDetailPO.findById(assignId);
        orderDispatchDetailPO.deleteCascadeShallow();
        setDeleteOrderStatus(roId);
//        setOrderAssign(roId);
        setRepairProDetail(labourId);
        
        StringBuilder sqlAssign = new StringBuilder("SELECT DEALER_CODE,RO_ID FROM `tt_ro_assign` where ro_labour_id = ?");
        List<Object> assignParam = new ArrayList<Object>();
        assignParam.add(labourId);
        List<Map> roIdList = DAOUtil.findAll(sqlAssign.toString(), assignParam);
        if(roIdList.size()>0){
            for(int i=0;i<roIdList.size();i++){
                Long roid = Long.parseLong(roIdList.get(i).get("RO_ID").toString());
                setOrderAssign(roid);
            }
        }
        
    }

    /**
     * 删除所有派工明细
     * 
     * @author rongzoujie
     * @date 2016年10月10日
     * @param roId
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#deleteAllDispatchDetail(java.lang.Long)
     */
    @Override
    public void deleteAllDispatchDetail(Long roId) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("select dealer_code,assign_id,ro_labour_id from tt_ro_assign where ro_id = ?");
        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(roId);
        List<Map> assignIds = DAOUtil.findAll(sql.toString(), queryParam);
        if (assignIds.size() > 0) {
            for (int i = 0; i < assignIds.size(); i++) {
                Long assignId = Long.parseLong(assignIds.get(i).get("assign_id").toString());
                OrderDispatchDetailPO orderDispatchDetailPO = OrderDispatchDetailPO.findById(assignId);
                orderDispatchDetailPO.deleteCascadeShallow();
                Long roLabourId = Long.parseLong(assignIds.get(i).get("ro_labour_id").toString());
                setRepairProDetail(roLabourId);
            }
        }
    }

    /**
     * 删除选择派工明细
     * 
     * @author rongzoujie
     * @date 2016年10月10日
     * @param labourIdStr
     * @throws ServiceBizException
     */
    public void deleteSelectDispatchDetail(String labourIdStr) throws ServiceBizException {
        if (labourIdStr.indexOf("-") == -1) {
            Long labourId = Long.parseLong(labourIdStr);
            setDispatchDetailByLabourId(labourId);
        } else {
            String[] labourIdArr = labourIdStr.split("-");
            for (int i = 0; i < labourIdArr.length; i++) {
                Long labourId = Long.parseLong(labourIdArr[i]);
                setDispatchDetailByLabourId(labourId);
            }
        }
    }

    /**
     * Help for 删除选择派工明细
     * 
     * @author rongzoujie
     * @date 2016年10月10日
     * @param labourId
     */
    public void setDispatchDetailByLabourId(Long labourId) {
        StringBuilder sql = new StringBuilder("SELECT DEALER_CODE,ASSIGN_ID FROM `tt_ro_assign` where ro_labour_id = ?");
        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(labourId);
        List<Map> assignIds = DAOUtil.findAll(sql.toString(), queryParam);
        if (assignIds.size() > 1) {
            for (int i = 0; i < assignIds.size(); i++) {
                Long assignId = Long.parseLong(assignIds.get(i).get("ASSIGN_ID").toString());
                OrderDispatchDetailPO orderDispatchDetailPO = OrderDispatchDetailPO.findById(assignId);
                orderDispatchDetailPO.deleteCascadeShallow();
            }
        }
        if (assignIds.size() == 1) {
            Long assignId = Long.parseLong(assignIds.get(0).get("ASSIGN_ID").toString());
            OrderDispatchDetailPO orderDispatchDetailPO = OrderDispatchDetailPO.findById(assignId);
            orderDispatchDetailPO.deleteCascadeShallow();
        }
        
        setRepairProDetail(labourId);

        //设置工单派工状态
        StringBuilder sqlAssign = new StringBuilder("SELECT DEALER_CODE,RO_ID FROM `tt_ro_assign` where ro_labour_id = ?");
        List<Object> assignParam = new ArrayList<Object>();
        assignParam.add(labourId);
        List<Map> roIdList = DAOUtil.findAll(sqlAssign.toString(), assignParam);
        if(roIdList.size()>0){
            for(int i=0;i<roIdList.size();i++){
                Long roid = Long.parseLong(roIdList.get(i).get("RO_ID").toString());
                setOrderAssign(roid);
                setDeleteOrderStatus(roid);
            }
        }
    }

    /**
     * 完工验收页面查询
     * 
     * @author rongzoujie
     * @date 2016年10月12日
     * @param roId
     * @return
     * @throws ServiceBizException (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#queryFinishWorkCheck(java.lang.Long)
     */
    public PageInfoDto queryFinishWorkCheck(Long roId) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT t1.ASSIGN_ID,t1.DEALER_CODE,t1.RO_ID,t2.LABOUR_CODE,t2.LABOUR_NAME,t2.LABOUR_AMOUNT,t3.CHARGE_PARTITION_NAME,t4.EMPLOYEE_NAME,t2.ASSIGN_LABOUR_HOUR,");
        sql.append("( case when t2.ASSIGN_TAG = 1 then '已派' when t2.ASSIGN_TAG = 2 then '合派' ELSE '未派' end ) as ASSIGN_TAG,");
        sql.append("t1.FINISHED_TAG,t1.CHECKER,t1.ITEM_END_TIME,t1.ITEM_START_TIME,(t2.ASSIGN_LABOUR_HOUR) as ITEM_HOUR FROM tt_ro_assign t1 ");
        sql.append("LEFT JOIN tt_ro_labour t2 ON t1.RO_LABOUR_ID = t2.RO_LABOUR_ID and t1.DEALER_CODE = t2.DEALER_CODE and t1.DEALER_CODE = t2.DEALER_CODE ");
        sql.append("LEFT JOIN TM_CHARGE_PARTITION t3 ON t3.CHARGE_PARTITION_CODE = t2.CHARGE_PARTITION_CODE and t1.DEALER_CODE = t3.DEALER_CODE ");
        sql.append("left JOIN tm_employee t4 on t1.TECHNICIAN = t4.EMPLOYEE_NO and t1.DEALER_CODE = t4.DEALER_CODE where 1=1 and t1.RO_ID = ?");
        List<Object> queryParam = new ArrayList<Object>();
        queryParam.add(roId);
        return DAOUtil.pageQuery(sql.toString(), queryParam);
    }

    /**
     * 更新实际工时
     * 
     * @author rongzoujie
     * @date 2016年10月12日
     * @param assignId
     * @param customerHour
     */
    public void setCustomerHour(Long assignId, Double customerHour) throws ServiceBizException {
        OrderDispatchDetailPO orderDispatchDetailPO = OrderDispatchDetailPO.findById(assignId);
        orderDispatchDetailPO.setDouble("FACT_LABOUR_HOUR", customerHour);
        orderDispatchDetailPO.saveIt();
    }

    /**
     * 设置检验人 TODO description
     * 
     * @author rongzoujie
     * @date 2016年10月13日
     * @param assignId
     * @param checker
     */
    public void setChecker(Long assignId, String checker,Object itemEndTime) throws ServiceBizException {
        OrderDispatchDetailPO orderDispatchDetailPO = OrderDispatchDetailPO.findById(assignId);
        orderDispatchDetailPO.setString("CHECKER", checker);
        orderDispatchDetailPO.setString("ITEM_END_TIME", itemEndTime);
        orderDispatchDetailPO.saveIt();
    }

    /**
     * 派工界面工单查询 TODO description
     * 
     * @author rongzoujie
     * @date 2016年10月14日
     * @param queryParam
     * @param titles
     * @return
     * @throws ServiceBizException
     */
    public PageInfoDto dispatchQueryRepairOrder(Map<String, String> queryParam,
                                                String... titles) throws ServiceBizException {
        StringBuilder sqlsb = new StringBuilder("SELECT t.ASSIGN_STATUS as ASSIGN_STATUS_CODE,te.EMPLOYEE_NAME,rt.REPAIR_TYPE_NAME,t.RO_ID,t.DEALER_CODE,t.RO_NO,t.RO_STATUS,t.RO_CREATE_DATE,t.RO_TYPE,t.REPAIR_TYPE_CODE AS REPAIR_TYPE_CODE,t.END_TIME_SUPPOSED,t.IN_MILEAGE,t.OUT_MILEAGE,t.FINISH_USER,t.COMPLETE_TIME,t.DELIVERER,tm.MODEL_NAME as MODEL_NAME,tv.LICENSE,e.EMPLOYEE_NAME AS SERVICE_ADVISOR_ASS,t.DELIVERER_MOBILE,");
        sqlsb.append("( case when t.ASSIGN_STATUS = 12271001 then '已派工' when t.ASSIGN_STATUS = 12271002 then '部分派工' else '未派工' end ) as ASSIGN_STATUS,");
        sqlsb.append("(case when t.COMPLETE_TIME is not null then 10131001 end) as COMPLETE_TAG,");
        sqlsb.append("t.WAIT_INFO_TAG,t.WAIT_PART_TAG from tt_repair_order t ");
        sqlsb.append("left join tm_employee e on t.SERVICE_ADVISOR_ASS=e.EMPLOYEE_NO and t.DEALER_CODE=e.DEALER_CODE ");
        sqlsb.append("left join tm_repair_type rt on t.DEALER_CODE=rt.DEALER_CODE and t.REPAIR_TYPE_CODE=rt.REPAIR_TYPE_CODE ");
        sqlsb.append("LEFT JOIN tm_vehicle tv ON t.DEALER_CODE=tv.DEALER_CODE AND tv.VEHICLE_ID=t.VEHICLE_ID ");
        sqlsb.append("LEFT JOIN tm_employee te on t.FINISH_USER = te.EMPLOYEE_NO and te.DEALER_CODE = t.DEALER_CODE ");
        sqlsb.append("LEFT JOIN tm_model tm ON t.DEALER_CODE=tm.DEALER_CODE AND tm.MODEL_ID=tv.MODEL_CODE ");
        sqlsb.append("LEFT join tm_owner tow on tow.DEALER_CODE=t.DEALER_CODE and tow.OWNER_ID=tv.OWNER_ID where 1=1 and t.RO_STATUS < 12151004");

        List<Object> params = new ArrayList<Object>();
        // 工单
        if (!StringUtils.isNullOrEmpty(queryParam.get("roNo"))
            && !StringUtils.isEquals(queryParam.get("roNo"), "{[RONO]}")) {
            sqlsb.append(" and t.RO_NO like ?");
            params.add("%" + queryParam.get("roNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
            sqlsb.append(" and tv.LICENSE like ?");
            params.add("%" + queryParam.get("license") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisorAss"))) {
            sqlsb.append(" and t.SERVICE_ADVISOR_ASS=?");
            params.add(queryParam.get("serviceAdvisorAss"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
            sqlsb.append(" and tv.CONTACTOR_NAME like ?");
            params.add("%" + queryParam.get("ownerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sqlsb.append(" and tv.VIN like ?");
            params.add("%" + queryParam.get("vin") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("roStatus"))) {
            sqlsb.append(" and t.RO_STATUS=?");
            params.add(Integer.parseInt(queryParam.get("roStatus")));
        }
        if (titles.length > 0) {
            if (StringUtils.isEquals(titles[0], "true")) {
                sqlsb.append(" and t.RO_CREATE_DATE>=? ");
                params.add(DateUtil.parseDefaultDate(queryParam.get("roCreateDateFrom")));
                sqlsb.append(" and t.RO_CREATE_DATE<?");
                params.add(DateUtil.addOneDay(queryParam.get("roCreateDateTo")));
            }
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))) {
            sqlsb.append(" and t.REPAIR_TYPE_CODE=?");
            params.add(queryParam.get("repairTypeCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("delivererMobile"))) {
            sqlsb.append(" and t.DELIVERER_MOBILE like ?");
            params.add("%" + queryParam.get("delivererMobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("delivererPhone"))) {
            sqlsb.append(" and t.DELIVERER_PHONE like ?");
            params.add("%" + queryParam.get("delivererPhone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("deliverer"))) {
            sqlsb.append(" and t.DELIVERER like ?");
            params.add("%" + queryParam.get("deliverer") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("startFrom"))){
            sqlsb.append(" and RO_CREATE_DATE>=? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("startFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("startTo"))){
            sqlsb.append(" and RO_CREATE_DATE<? ");
            params.add(DateUtil.addOneDay(queryParam.get("startTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("isCheck"))){
            String orderStatusStr = queryParam.get("isCheck").toString();
            String[] orderStatusArr = orderStatusStr.split(",");
            
            boolean searchMutilTag = false;
            for(int i=0;i<orderStatusArr.length;i++){
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_ALL){
                    searchMutilTag = true;
                }
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_SOME){
                    searchMutilTag = true;
                }
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_NO){
                    searchMutilTag = true;
                }
            }
            
            if(searchMutilTag){
                sqlsb.append(" and t.ASSIGN_STATUS in (");
                for(int i=0;i<orderStatusArr.length;i++){
                    sqlsb.append("?");
                    if((i+1) < orderStatusArr.length){
                        sqlsb.append(",");
                    }
                    params.add(orderStatusArr[i]);
                }
                sqlsb.append(")");
            }
            
            //待信标志
            for(int i=0;i<orderStatusArr.length;i++){
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_INFO){
                    sqlsb.append(" AND t.WAIT_INFO_TAG = "+DictCodeConstants.SYSTEM_PARAM_TYPE_JSJEYZFS);
                }
            }
            //待料标志
            for(int i=0;i<orderStatusArr.length;i++){
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_PART){
                    sqlsb.append(" AND t.WAIT_PART_TAG = "+DictCodeConstants.SYSTEM_PARAM_TYPE_JSJEYZFS);
                }
            }
            //竣工
            for(int i=0;i<orderStatusArr.length;i++){
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_COMPLETE){
                    sqlsb.append(" AND t.COMPLETE_TIME is not null ");
                }
            }
            //未派工
//            for (int i = 0; i < orderStatusArr.length; i++) {
//                if (Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_NO) {
//                    sqlsb.append(" AND t.ASSIGN_STATUS is null or t.ASSIGN_STATUS = "
//                                 + DictCodeConstants.ASSIGN_ORDER_NO);
//                }
//            }
        }
        PageInfoDto pageInfoDto = DAOUtil.pageQuery(sqlsb.toString(), params);
        return pageInfoDto;
    }

    /**
     * 设置维修工单派工状态以及工单状态
     * @date 2016年10月17日
     * @param roId
     * @throws ServiceBizException
      */
    public void setOrderAssign(Long roId) throws ServiceBizException {
        Long assignTag = 12271003L;

        StringBuilder sql = new StringBuilder("select COUNT(t.ASSIGN_TAG)AS assignNum,t.DEALER_CODE,COUNT(T.RO_ID)AS labourNum from tt_ro_labour t where ro_id = ?");
        List<Object> param = new ArrayList<Object>();
        param.add(roId);
        Map labourResult = DAOUtil.findFirst(sql.toString(), param);

        Long labourNum = Long.parseLong(labourResult.get("labourNum").toString());
        Long assignNum = Long.parseLong(labourResult.get("assignNum").toString());
        // 工单派工状态的三种状态
        if (labourNum != 0 && assignNum != 0) {
            if (assignNum == labourNum) {
                assignTag = 12271001L;
            } else if (labourNum > assignNum) {
                assignTag = 12271002L;
            }
        }
        
        RepairOrderPO repairOrderPO = RepairOrderPO.findById(roId);
        repairOrderPO.setInteger("ASSIGN_STATUS", assignTag);
//        if(assignNum == 0){
//        	repairOrderPO.setInteger("RO_STATUS",DictCodeConstants.NEW_CREATE_STATUS);
//        }
        repairOrderPO.saveIt();
    }
    
    /**
     *
     * 设置待料
    * @author rongzoujie
    * @date 2016年10月17日
    * @param roId
    * @throws ServiceBizException
     */
    public void setExpectedMaterial(Long roId) throws ServiceBizException {
        RepairOrderPO repairOrderPO = RepairOrderPO.findById(roId);
        repairOrderPO.setLong("WAIT_PART_TAG", DictCodeConstants.SYSTEM_PARAM_TYPE_JSJEYZFS);
        repairOrderPO.saveIt();
    }

    /**
     * 设置取消竣工 
     * @date 2016年10月17日
     * @param roId
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#cancelCompeleteWork(java.lang.Long)
      */
     @Override
     public void cancelCompeleteWork(Long roId) throws ServiceBizException {
         RepairOrderPO repairOrderPO = RepairOrderPO.findById(roId);
         String roNo = repairOrderPO.get("RO_NO").toString();
         repairOrderPO.setDate("COMPLETE_TIME", null);
         repairOrderPO.setDouble("OUT_MILEAGE", null);
         repairOrderPO.setString("FINISH_USER", null);
         repairOrderPO.setLong("RO_STATUS", 12151002L);
         repairOrderPO.saveIt();
         OperateLogDto operateLogDto=new OperateLogDto();
         operateLogDto.setOperateContent("取消竣工：工单号 【"+roNo+"】");
         operateLogDto.setOperateType(DictCodeConstants.LOG_REPAIR_MANAGEMENT);
         operateLogService.writeOperateLog(operateLogDto);
     }

     /**
      * 取消待料
     * @author rongzoujie
     * @date 2016年10月18日
     * @param roId
     * @throws ServiceBizException
     * (non-Javadoc)
     * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#cancelExpectedMaterial(java.lang.Long)
      */
    @Override
    public void cancelExpectedMaterial(Long roId) throws ServiceBizException {
        RepairOrderPO repairOrderPO = RepairOrderPO.findById(roId);
        repairOrderPO.setLong("WAIT_PART_TAG",null);
        repairOrderPO.saveIt();
    }
    
    /**
     * 待信
    * @author rongzoujie
    * @date 2016年10月18日
    * @param roId
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#setExpectedInfo(java.lang.Long)
     */
    @Override
    public void setExpectedInfo(Long roId) throws ServiceBizException {
        RepairOrderPO repairOrderPO = RepairOrderPO.findById(roId);
        repairOrderPO.setLong("WAIT_INFO_TAG",DictCodeConstants.SYSTEM_PARAM_TYPE_JSJEYZFS);
        repairOrderPO.saveIt();
    }
    
    /**
     * 取消待信
    * @author rongzoujie
    * @date 2016年10月18日
    * @param roId
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.repair.service.repairDispatching.OrderLabourDetailService#cancelExpectedInfo(java.lang.Long)
     */
    @Override
    public void cancelExpectedInfo(Long roId) throws ServiceBizException {
        RepairOrderPO repairOrderPO = RepairOrderPO.findById(roId);
        repairOrderPO.setLong("WAIT_INFO_TAG",null);
        repairOrderPO.saveIt();
    }
    
    /**
     *
     * 工单维修项目修改
    * @author rongzoujie
    * @date 2016年10月18日
    * @param roId
    * @return
     */
    public List<Map> queryLabourDetail(Long roId) throws ServiceBizException {
        StringBuilder sql = new StringBuilder("SELECT t1.DEALER_CODE,t1.RO_LABOUR_ID,");
        sql.append("t1.REPAIR_TYPE_CODE as repairType");
        sql.append(",t1.LABOUR_CODE as labourCode");
        sql.append(",t1.LABOUR_NAME as labourName");
        sql.append(",t1.STD_LABOUR_HOUR as stdHour");
        sql.append(",t1.ASSIGN_LABOUR_HOUR as assignLabourHour");
        sql.append(",t1.LABOUR_PRICE as workHourSinglePrice");
        sql.append(",t1.LABOUR_AMOUNT as workHourPrice");
        sql.append(",t1.DISCOUNT as discountRate");
        sql.append(",(t1.LABOUR_AMOUNT  * t1.DISCOUNT) as discountMoney,");
        sql.append("t1.AFTER_DISCOUNT_AMOUNT as receiveMoney");
        sql.append(",t4.WORKER_TYPE_NAME as workType");
        sql.append(",GROUP_CONCAT(t5.EMPLOYEE_NAME) as technician");
        sql.append(",( case when t1.ASSIGN_TAG = 1 then '已派' when t1.ASSIGN_TAG = 2 then '合派' ELSE '未派' end ) as assignTag");
        sql.append(",t1.TROUBLE_DESC as troubleDesc");
        sql.append(",t1.ACTIVITY_CODE as activityCode"); 
        sql.append(",t1.PACKAGE_CODE as packageCode"); 
        sql.append(",t6.MODEL_LABOUR_NAME as modelLabourName");
        sql.append(",t1.LOCAL_LABOUR_CODE as localLabourCode");
        sql.append(",t1.LOCAL_LABOUR_NAME as localLabourName");
        sql.append(",t1.CHARGE_PARTITION_CODE as chargePartitionCode");
        sql.append(",t1.TECHNICIAN as technicianNO");
        sql.append(",t1.WORKER_TYPE_CODE as workTypeCode");
        sql.append(",t1.ASSIGN_TAG as assignCode");
        sql.append(",t1.MODEL_LABOUR_CODE as modelLabourCode ");
        sql.append("FROM tt_ro_labour t1");
        sql.append(" LEFT JOIN TM_CHARGE_PARTITION t2 ON t1.DEALER_CODE = t2.DEALER_CODE and t2.CHARGE_PARTITION_CODE = t1.CHARGE_PARTITION_CODE ");
        sql.append("LEFT JOIN tm_repair_type t3 on t1.DEALER_CODE = t3.DEALER_CODE and t1.REPAIR_TYPE_CODE = t3.REPAIR_TYPE_CODE ");
        sql.append("LEFT JOIN tm_worker_type t4 on t1.DEALER_CODE = t4.DEALER_CODE and t1.WORKER_TYPE_CODE = t4.WORKER_TYPE_CODE ");
        sql.append("LEFT JOIN tm_model_group t6 on t1.DEALER_CODE = t6.DEALER_CODE and t1.MODEL_LABOUR_CODE = t6.MODEL_LABOUR_CODE ");
        sql.append("LEFT JOIN tt_ro_assign t7 on t1.DEALER_CODE = t7.DEALER_CODE and t1.RO_LABOUR_ID = t7.RO_LABOUR_ID ");
        sql.append("LEFT JOIN tm_employee t5 on t5.DEALER_CODE = t1.DEALER_CODE and t5.EMPLOYEE_NO = t7.TECHNICIAN ");
        sql.append("where 1=1 and t1.RO_ID = ?");
        sql.append(" group by t1.RO_LABOUR_ID");
        List<Object> params = new ArrayList<Object>();
        params.add(roId);
        
        List<Map> labourDetails = DAOUtil.findAll(sql.toString(), params);
        return labourDetails;
    }

    @Override
    public List<Map> queryRepairAssignForExport(Map<String, String> queryParam, String... titles) {
        StringBuilder sqlsb = new StringBuilder("SELECT te.EMPLOYEE_NAME,rt.REPAIR_TYPE_NAME,t.RO_ID,t.DEALER_CODE,t.RO_NO,t.RO_STATUS,t.RO_CREATE_DATE,t.RO_TYPE,t.REPAIR_TYPE_CODE AS REPAIR_TYPE_CODE,t.END_TIME_SUPPOSED,t.IN_MILEAGE,t.OUT_MILEAGE,t.FINISH_USER,t.COMPLETE_TIME,t.DELIVERER,tm.MODEL_NAME as MODEL_NAME,tv.LICENSE,e.EMPLOYEE_NAME AS SERVICE_ADVISOR_ASS,t.DELIVERER_MOBILE,");
        sqlsb.append("( case when t.ASSIGN_STATUS = 12271001 then '已派工' when t.ASSIGN_STATUS = 12271002 then '部分派工' else '未派工' end ) as ASSIGN_STATUS,");
        sqlsb.append("(case when t.COMPLETE_TIME is not null then '竣工' end) as COMPLETE_TAG,");
        sqlsb.append("(case when t.WAIT_INFO_TAG is not null then '待信' end) as WAIT_INFO_TAG,(case when t.WAIT_PART_TAG is not null then '待料' end) as WAIT_PART_TAG from tt_repair_order t ");
        sqlsb.append("left join tm_employee e on t.SERVICE_ADVISOR_ASS=e.EMPLOYEE_NO and t.DEALER_CODE=e.DEALER_CODE ");
        sqlsb.append("left join tm_repair_type rt on t.DEALER_CODE=rt.DEALER_CODE and t.REPAIR_TYPE_CODE=rt.REPAIR_TYPE_CODE ");
        sqlsb.append("LEFT JOIN tm_vehicle tv ON t.DEALER_CODE=tv.DEALER_CODE AND tv.VEHICLE_ID=t.VEHICLE_ID ");
        sqlsb.append("LEFT JOIN tm_employee te on t.FINISH_USER = te.EMPLOYEE_NO and te.DEALER_CODE = t.DEALER_CODE ");
        sqlsb.append("LEFT JOIN tm_model tm ON t.DEALER_CODE=tm.DEALER_CODE AND tm.MODEL_ID=tv.MODEL_CODE ");
        sqlsb.append("LEFT join tm_owner tow on tow.DEALER_CODE=t.DEALER_CODE and tow.OWNER_ID=tv.OWNER_ID where 1=1 and t.RO_STATUS < 12151004");

        List<Object> params = new ArrayList<Object>();
        // 工单
        if (!StringUtils.isNullOrEmpty(queryParam.get("roNo"))
            && !StringUtils.isEquals(queryParam.get("roNo"), "{[RONO]}")) {
            sqlsb.append(" and t.RO_NO like ?");
            params.add("%" + queryParam.get("roNo") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("license"))) {
            sqlsb.append(" and tv.LICENSE like ?");
            params.add("%" + queryParam.get("license") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("serviceAdvisorAss"))) {
            sqlsb.append(" and t.SERVICE_ADVISOR_ASS=?");
            params.add(queryParam.get("serviceAdvisorAss"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("ownerName"))) {
            sqlsb.append(" and tv.CONTACTOR_NAME like ?");
            params.add("%" + queryParam.get("ownerName") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sqlsb.append(" and tv.VIN like ?");
            params.add("%" + queryParam.get("vin") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("roStatus"))) {
            sqlsb.append(" and t.RO_STATUS=?");
            params.add(Integer.parseInt(queryParam.get("roStatus")));
        }
        if (titles.length > 0) {
            if (StringUtils.isEquals(titles[0], "true")) {
                sqlsb.append(" and t.RO_CREATE_DATE>=? ");
                params.add(DateUtil.parseDefaultDate(queryParam.get("roCreateDateFrom")));
                sqlsb.append(" and t.RO_CREATE_DATE<?");
                params.add(DateUtil.addOneDay(queryParam.get("roCreateDateTo")));
            }
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("repairTypeCode"))) {
            sqlsb.append(" and t.REPAIR_TYPE_CODE=?");
            params.add(queryParam.get("repairTypeCode"));
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("delivererMobile"))) {
            sqlsb.append(" and t.DELIVERER_MOBILE like ?");
            params.add("%" + queryParam.get("delivererMobile") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("delivererPhone"))) {
            sqlsb.append(" and t.DELIVERER_PHONE like ?");
            params.add("%" + queryParam.get("delivererPhone") + "%");
        }
        if (!StringUtils.isNullOrEmpty(queryParam.get("deliverer"))) {
            sqlsb.append(" and t.DELIVERER like ?");
            params.add("%" + queryParam.get("deliverer") + "%");
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("startFrom"))){
            sqlsb.append(" and RO_CREATE_DATE>=? ");
            params.add(DateUtil.parseDefaultDate(queryParam.get("startFrom")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("startTo"))){
            sqlsb.append(" and RO_CREATE_DATE<? ");
            params.add(DateUtil.addOneDay(queryParam.get("startTo")));
        }
        if(!StringUtils.isNullOrEmpty(queryParam.get("isCheck"))){
            String orderStatusStr = queryParam.get("isCheck").toString();
            String[] orderStatusArr = orderStatusStr.split(",");
            
            boolean searchMutilTag = false;
            for(int i=0;i<orderStatusArr.length;i++){
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_ALL){
                    searchMutilTag = true;
                }
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_SOME){
                    searchMutilTag = true;
                }
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_NO){
                    searchMutilTag = true;
                }
            }
            
            if(searchMutilTag){
                sqlsb.append(" and t.ASSIGN_STATUS in (");
                for(int i=0;i<orderStatusArr.length;i++){
                    sqlsb.append("?");
                    if((i+1) < orderStatusArr.length){
                        sqlsb.append(",");
                    }
                    params.add(orderStatusArr[i]);
                }
                sqlsb.append(")");
            }
            
            //待信标志
            for(int i=0;i<orderStatusArr.length;i++){
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_INFO){
                    sqlsb.append(" AND t.WAIT_INFO_TAG = "+DictCodeConstants.SYSTEM_PARAM_TYPE_JSJEYZFS);
                }
            }
            //待料标志
            for(int i=0;i<orderStatusArr.length;i++){
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_PART){
                    sqlsb.append(" AND t.WAIT_PART_TAG = "+DictCodeConstants.SYSTEM_PARAM_TYPE_JSJEYZFS);
                }
            }
            //竣工
            for(int i=0;i<orderStatusArr.length;i++){
                if(Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_COMPLETE){
                    sqlsb.append(" AND t.COMPLETE_TIME is not null ");
                }
            }
            //未派工
//            for (int i = 0; i < orderStatusArr.length; i++) {
//                if (Integer.parseInt(orderStatusArr[i]) == DictCodeConstants.ASSIGN_ORDER_NO) {
//                    sqlsb.append(" AND t.ASSIGN_STATUS is null or t.ASSIGN_STATUS = "
//                                 + DictCodeConstants.ASSIGN_ORDER_NO);
//                }
//            }
        }
        
        return DAOUtil.findAll(sqlsb.toString(), params);
    }

	@Override
	public void setFinishHour(Long assignId, String finishHour) throws ServiceBizException {
		OrderDispatchDetailPO orderDispatchDetailPO = OrderDispatchDetailPO.findById(assignId);
		orderDispatchDetailPO.setTimestamp("ITEM_END_TIME", DateUtil.parseDefaultDateTimeMin(finishHour));
		orderDispatchDetailPO.saveIt();
	}

}
