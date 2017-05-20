
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : repairOrderService.java
 *
 * @Author : ZhengHe
 *
 * @Date : 2016年8月10日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月10日    ZhengHe    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.service.order;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TtRoRepairPartPO;
import com.yonyou.dms.commonAS.domains.DTO.order.RepairOrderDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.RoManageDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtAccountsTransFlowDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtMemCardActiDetailDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartObligatedDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartObligatedItemDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtPartPeriodReportDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.TtRoRepairPartDTO;

/**
 * 工单接口
 * @author chenwei
 * @date 2017年4月1日
 */

public interface WithDrawStuffService {
	//维修领料之前，打开工单查询界面
	public List<Map> checkRoDetail(Map<String, String> queryParams)throws  ServiceBizException;
	
	public PageInfoDto queryMaintainPicking(Map<String,String> queryParam) throws ServiceBizException; //根据条件查询
	/**
	 * 工单退料查询
	* TODO description
	* @author chenwei
	* @date 2017年5月2日
	* @return
	* @throws ServiceBizException
	 */
	public PageInfoDto queryRepairOrderByCommitClose(Map<String, Object> queryParam) throws ServiceBizException;
	
	/**
     * 维修项目明细查询
    * TODO description
    * @author chenwei
    * @date 2017年5月3日
    * @return
    * @throws ServiceBizException
     */
    public PageInfoDto queryTtRoLabourList(Map<String, Object> queryParam) throws ServiceBizException;
	
	
	public List<Map> checkRepairOrderLock(Map<String, String> queryParams) throws ServiceBizException;
	
	public void lockRepairOrder(String roNo, RepairOrderDTO repairOrderto) throws ServiceBizException;
	
	public PageInfoDto queryPartInfo(Map<String, String> queryParam)  throws ServiceBizException;
	
	/**
	 * 维修领料 查询借料和预留
	* TODO description
	* @author chenwei
	* @date 2017年5月3日
	* @param queryParam
	* @return
	* @throws ServiceBizException
	 */
	public PageInfoDto queryBorrowPartInfo(Map<String, Object> queryParam)  throws ServiceBizException;
	
	public PageInfoDto queryPartStock(Map<String, String> queryParam)  throws ServiceBizException;
	
	public void modifyByItemId(Long ItemId, TtRoRepairPartDTO cudto) throws ServiceBizException;///根据customerCode修改
	
	/**
	 * 修改 维修材料费 tt_repair_order工单表
	* TODO description
	* @author chenwei
	* @date 2017年5月15日
	* @param roNo
	* @param cudto
	* @throws ServiceBizException
	 */
	public void reCalcRepairAmount(String roNo, RepairOrderDTO cudto) throws ServiceBizException;///修改
	
    public TtRoRepairPartPO addTtRoRepairPart(TtRoRepairPartDTO cudto)throws ServiceBizException;///新增
    
    public List<Map> selectEmployees(Map<String, String> queryParam) throws ServiceBizException;//查询employee表 领料人
    
    public List<Map> selectRepairOrder(Map<String, Object> queryParams) throws ServiceBizException;//查询维修工单是否是已结算或者已提交结算
    
    public String selectDefaultPara(Map<String, Object> queryParams) throws ServiceBizException;//查询缺省业务参数表
    
    public List<Map> selectRoRepairPart(Map<String, Object> queryParam) throws ServiceBizException;//查询工单维修配件明细
    
    public List<TtRoRepairPartDTO> changeMapToRoRepairPart(List<Map> queryParams);
    
    public List<Map> selectTtActivity(Map<String, String> queryParam) throws ServiceBizException;//查询服务活动主信息
    
    //根据工单号,项目代码,维修组合,查询维修项目ID
    public List<Map> queryRoLabourByCodeAndRoNo(String dealerCode,String labourCode,String modelLabourCode,String packageCode,String roNo,String labourName) throws ServiceBizException;
    
    public void deleteRoRepairPart(Long id) throws ServiceBizException;
    
    public List<Map> getNonOemPartListOutReturn(String fieldName, String sheetTable, String sheetName, String sheetNo, String quantityFieldName) throws ServiceBizException;
    
    public List<Map> queryManage(Map<String, Object> queryParam) throws ServiceBizException;//查询工单辅料管理费
    
    public void addTtRoManage(RoManageDTO cudto) throws ServiceBizException;//新增工单辅料管理费
    
    public void deleteTtRoManage(Map<String, Object> deleteParams) throws ServiceBizException;//刪除工单辅料管理费
    
    public void modifyRepairOrderByParams(List updateParams) throws ServiceBizException;//修改维修工单表
    
    public void updateRepairOrder(String sqlStr, String sqlWhere) throws ServiceBizException;//封装 修改维修工单表
    
    public List<Map> findRoLabourList(Map<String, Object> queryParams) throws ServiceBizException;//工单维修项目明细
    
    public List<Map> findRoAddItem(Map<String, Object> queryParams) throws ServiceBizException;//工单附加项目明细
    
    public void deleteShortPart(Map<String, Object> deleteParams) throws ServiceBizException;//刪除配件缺料记录表
    
    public List<Map> queryMonthCycle(Map<String, Object> queryParam) throws ServiceBizException;//查询配件自然月月结记录
    
    public List<Map> queryAccountingCycle(Map<String, Object> queryParam) throws ServiceBizException;//查询会计周期
    
    /**
     * 判断当前登陆用户是否有：低于成本价出库的权限
     * @throws Exception 
     */
    public List<Map> checkUserRights(Map<String, Object> queryParam) throws ServiceBizException;
    
    /**
     * 判断出库价格是否小于成本价
     * @throws Exception 
     */
    public List checkCostSize(Map<String, Object> queryParam) throws ServiceBizException;
    
    /**
     * 配件基本信息
    * TODO description
    * @author chenwei
    * @date 2017年4月21日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    public List<Map> queryPartInfoList(Map<String, Object> queryParam) throws ServiceBizException;
    /**
     * 查询配件仓库
    * TODO description
    * @author chenwei
    * @date 2017年4月22日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    public List<Map> queryStorageList(Map<String, Object> queryParam) throws ServiceBizException;
    
    /**
     * 查询配件会计月报表
    * TODO description
    * @author chenwei
    * @date 2017年4月24日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    public List<Map> queryPartPeriodReportList(Map<String, Object> queryParam) throws ServiceBizException;
    
    /**
     * 更新配件会计月报表
    * TODO description
    * @author chenwei
    * @date 2017年4月24日
    * @param sqlStr
    * @param sqlWhere
    * @throws ServiceBizException
     */
    public int modifyPartPeriodReportByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException;
    
    /**
     * 新增配件会计月报表
    * TODO description
    * @author chenwei
    * @date 2017年4月24日
    * @param modeldto
    * @return
    * @throws ServiceBizException
     */
    public void addPartMonthReport(TtPartPeriodReportDTO modeldto) throws ServiceBizException;
    
    /**
     * 查询配件日报表
    * TODO description
    * @author chenwei
    * @date 2017年4月24日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    public List<Map> queryPartDailyReportList(Map<String, Object> queryParam) throws ServiceBizException;
    
    /**
     * 更新配件日报表
    * TODO description
    * @author chenwei
    * @date 2017年4月24日
    * @param sqlStr
    * @param sqlWhere
    * @throws ServiceBizException
     */
    public int modifyPartDailyReportByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException;
    
    /**
     * 新增配件日报表
    * TODO description
    * @author chenwei
    * @date 2017年4月24日
    * @param modeldto
    * @return
    * @throws ServiceBizException
     */
    public void addPartDailyReport(String sqlStr, List params) throws ServiceBizException;
    
    /**
     * 
     * @param conn
     * @param entityCode
     * @param memActivityCode
     * @param flag 标识正常配件与退料配件
     * @return
     * @throws Exception
     */
    public List<Map> queryMemActivityByCode(String roNo,String memActivityCode,String flag)throws ServiceBizException;
    
   /**
    * 会员卡活动
   * TODO description
   * @author chenwei
   * @date 2017年4月25日
   * @param queryParam
   * @return
   * @throws ServiceBizException
    */
    public List<Map> queryMemberCardActivityByCode(Map<String, Object> queryParam)throws ServiceBizException;
    
    /**
     * 会员卡活动
    * TODO description
    * @author chenwei
    * @date 2017年4月25日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
    public int modifyMemberCardActivityByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException;
    
    /**
     * 会员活动使用明细
    * TODO description
    * @author chenwei
    * @date 2017年4月25日
    * @param deleteParams
    * @throws ServiceBizException
     */
    public void deleteMemberCardActivity(TtMemCardActiDetailDTO deleteParams) throws ServiceBizException;
    
    /**
     * 配件预留单
    * TODO description
    * @author chenwei
    * @date 2017年4月30日
    * @param queryParam
    * @return
    * @throws ServiceBizException
     */
     public List<Map> queryTtPartObligatedByObject(TtPartObligatedDTO queryParam)throws ServiceBizException;
     
     /**
      * 配件预留单明細
     * TODO description
     * @author chenwei
     * @date 2017年4月30日
     * @param queryParam
     * @return
     * @throws ServiceBizException
      */
      public List<Map> queryTtPartObligatedItemByObject(TtPartObligatedItemDTO queryParam)throws ServiceBizException;
      
      public List<TtPartObligatedItemDTO> changeMapToTtPartObligatedItem(List<Map> list);
      
      /**
       * 配件预留单明細
      * TODO description
      * @author chenwei
      * @date 2017年4月25日
      * @param queryParam
      * @return
      * @throws ServiceBizException
       */
      public int modifyTtPartObligatedItemByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException;
      
      /**
       * 财务凭证事务查询
      * TODO description
      * @author chenwei
      * @date 2017年4月24日
      * @param modeldto
      * @return
      * @throws ServiceBizException
       */
      public void addTtAccountsTransFlow(TtAccountsTransFlowDTO modeldto) throws ServiceBizException;
      
      /**
       * 更新配件追溯表
      * TODO description
      * @author chenwei
      * @date 2017年5月1日
      * @param sqlStr
      * @param sqlWhere
      * @param paramsList
      * @return
      * @throws ServiceBizException
       */
      public int modifyTmPartBackDTOByParams(String sqlStr, String sqlWhere, List paramsList) throws ServiceBizException;
}
