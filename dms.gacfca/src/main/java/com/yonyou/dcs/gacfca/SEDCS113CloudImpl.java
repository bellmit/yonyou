package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.po.TiRepairOrderPO;
import com.infoeai.eai.po.TiRoAddItemPO;
import com.infoeai.eai.po.TiRoLabourPO;
import com.infoeai.eai.po.TiRoRepairPartPO;
import com.infoeai.eai.po.TiSalesPartItemPO;
import com.infoeai.eai.po.TiSalesPartPO;
import com.yonyou.dcs.dao.SEDCS112Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.OpRepairOrderDTO;
import com.yonyou.dms.DTO.gacfca.RoAddItemDetailDTO;
import com.yonyou.dms.DTO.gacfca.RoLabourDetailDTO;
import com.yonyou.dms.DTO.gacfca.RoRepairPartDetailDTO;
import com.yonyou.dms.DTO.gacfca.SalesPartDetailDTO;
import com.yonyou.dms.DTO.gacfca.SalesPartItemDetailDTO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SEDCS113CloudImpl extends BaseCloudImpl implements SEDCS113Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS113CloudImpl.class);
	private String dealerCode = "";
	
	@Autowired
	SEDCS112Dao dao ;

	@Override
	public String handleExecutor(List<OpRepairOrderDTO> dtos) throws Exception {
		String msg = "1";
		beginDbService();
		try {
			logger.info("====SEDCS113Cloud 未结算工单(作废)接收上报开始====");
			saveTiTable(dtos);
			logger.info("====SEDCS113Cloud 未结算工单(作废)接收上报结束====");
			dbService.endTxn(true);
		} catch (Exception e) {
			logger.error("SEDCS113Cloud  未结算工单(作废)接收上报接收失败", e);
			msg = "0";
			dbService.endTxn(false);
		} finally{
			Base.detach();
			dbService.clean();
		}
		logger.info("***************************SEDCS113Cloud  成功获取上报的未结算工单(作废)******************************");
		return msg;
	}

	// 处理上报数据
	private void saveTiTable(List<OpRepairOrderDTO> dtos) throws Exception {
		logger.info("##################### 开始处理未结算工单(作废)数据 ############################");
		for (OpRepairOrderDTO dto : dtos) {
			if (dto == null) {
				throw new ServiceBizException("OpRepairOrderDTO信息为空！");
			}
			Map<String, Object> dcsInfoMap = dao.getSaDcsDealerCode(dto.getEntityCode());
			//工单状态(1新建，0作废)
//			String isStatus = CommonUtils.checkNull(dto.getIsStatus());
			if (dcsInfoMap == null) {
				throw new ServiceBizException("DCS没有【" + dto.getEntityCode() + "】经销商信息！");
			}
			Map<String, Object> vinMap = dao.getVehiceVin(dto.getVin());// 查询下端上报的VIN在上端是否存在，如果存在，则添加，否则不添加
			if (vinMap != null) {
				/**
				 * 1:先报新建状态的未结算工单后报作废的未结算工单：新建直接保存到相关业务表（数据库里没有），后报作废的工单根据dealerCode，roNo修改
				 * 2:先报作废的未结算工单后报新建状态的未结算工单：作废直接插入，后新建不管
				 */
				TiRepairOrderPO repairOrder = new TiRepairOrderPO();
				dealerCode = CommonUtils.checkNull(dcsInfoMap.get("DEALER_CODE")).equals("")?"":dcsInfoMap.get("DEALER_CODE").toString();
				String sql="SELECT * FROM TI_REPAIR_ORDER_DCS WHERE RO_NO = ? AND DEALER_CODE = ?";
				List <Object> params= new ArrayList<Object>();
				params.add(dto.getRoNo());
				params.add(dealerCode);
				List<Map> tiRepairOrderList=OemDAOUtil.findAll(sql, params);
				logger.info("====未结算工单(作废)接收上报工单数据大小===="+tiRepairOrderList.size());
				if(null!=tiRepairOrderList&&tiRepairOrderList.size()>0){
					String isStatus=CommonUtils.checkNull(tiRepairOrderList.get(0).get("IS_STATUS"));
					logger.info("====未结算工单(作废)接收上报工单状态===="+isStatus);
					if(isStatus.equals("1")){
						if(dto.getIsStatus()==0){
							logger.info("====未结算工单(作废)接收上报工单修改===="+isStatus);
							//根据dealerCode，roNo修改
							//参数
							List<Object> uparams = new ArrayList<Object>();
							uparams.add(dto.getIsStatus());//工单状态（1：新建，0：作废）
							uparams.add(dto.getDeleteDate());//作废时间
							uparams.add(DEConstant.DE_UPDATE_BY);
							uparams.add(new Date(System.currentTimeMillis()));
							uparams.add(dealerCode);
							uparams.add(dto.getRoNo());
							TiRepairOrderPO.update("IS_STATUS = ? AND DELETE_DATE = ? AND UPDATE_BY = ? AND UPDATE_DATE = ?", "DEALER_CODE = ? AND RO_NO = ?", uparams)
;
						}
					}
				}else{
					logger.info("====未结算工单(作废)接收上报工单接收====");
					// 数据库里没有数据：新增新建和作废状态的工单主表及明细信息
					insertTiRepairOrder(dto);// 工单主表
					insertTiRoAddItem(dto);// 工单附加项目明细
					insertTiRoLabour(dto);// 工单维修项目明细
					insertTiRoRepairPart(dto);// 工单维修配件明细
					insertTiSalesPart(dto);// 配件销售单明细
				}
			}
			
			/**
			 * 根据工单主表的dealerCode，roNo判断，如果存在则且状态为新建，则删除工单表及其五个字表，如果不存在直接保存
			 */
			dealerCode = CommonUtils.checkNull(dcsInfoMap.get("DEALER_CODE")).equals("") ? "": dcsInfoMap.get("DEALER_CODE").toString();
			String roNo = dto.getRoNo();
			List<Map> tiRepairOrderList = dao.select(dealerCode, roNo);
			logger.info("====未结算工单(作废)接收上报工单数量====" + tiRepairOrderList.size());
			if (tiRepairOrderList != null && tiRepairOrderList.size() > 0) {
				TiRepairOrderPO tro = (TiRepairOrderPO) tiRepairOrderList.get(0);
				String status = CommonUtils.checkNull(tiRepairOrderList.get(0).get("IS_STATUS"));
				logger.info("====未结算工单(作废)接收上报工单状态====" + status);
				logger.info("====删除已接受未结算工单(作废)数据====" + status);
				dao.delSalesPartItem(dealerCode, roNo);
				dao.delRepairTable(dealerCode, roNo);
			}
			logger.info("====未结算工单(作废)接收上报工单接收====");
		
		}
	}
	/**
	 * 工单主表
	 * @param dto
	 * @throws Exception
	 */
	public void insertTiRepairOrder(OpRepairOrderDTO dto) throws Exception{
		logger.info("=============工单主表(新增)============");
		TiRepairOrderPO tiRepairOrder = new TiRepairOrderPO();
		tiRepairOrder.setString("DEALER_CODE",dealerCode);
		tiRepairOrder.setString("RO_NO",dto.getRoNo());
		tiRepairOrder.setString("SALES_PART_NO",dto.getSalesPartNo());
		tiRepairOrder.setString("BOOKING_ORDER_NO",dto.getBookingOrderNo());
		tiRepairOrder.setString("ESTIMATE_NO",dto.getEstimateNo());
		tiRepairOrder.setString("SO_NO",dto.getSoNo());
		tiRepairOrder.setInteger("RO_NO",dto.getRoType());
		tiRepairOrder.setString("REPAIR_TYPE_CODE",dto.getRepairTypeCode());
		tiRepairOrder.setString("REPAIR_TYPE_CODE",dto.getOtherRepairType());
		tiRepairOrder.setInteger("VEHICLE_TOP_DESC",dto.getVehicleTopDesc());
		tiRepairOrder.setString("SEQUENCE_NO",dto.getSequenceNo());
		tiRepairOrder.setString("PRIMARY_RO_NO",dto.getPrimaryRoNo());
		tiRepairOrder.setString("INSURATION_NO",dto.getInsurationNo());
		tiRepairOrder.setString("INSURATION_CODE",dto.getInsurationCode());
		tiRepairOrder.setInteger("IS_CUSTOMER_IN_ASC",dto.getIsCustomerInAsc());
		tiRepairOrder.setInteger("IS_SEASON_CHECK",dto.getIsSeasonCheck());
		tiRepairOrder.setInteger("OIL_REMAIN",dto.getOilRemain());
		tiRepairOrder.setInteger("IS_WASH",dto.getIsWash());
		tiRepairOrder.setInteger("IS_TRACE",dto.getIsTrace());
		tiRepairOrder.setInteger("TRACE_TIME",dto.getTraceTime());
		tiRepairOrder.setString("NO_TRACE_REASON",dto.getNoTraceReason());
		tiRepairOrder.setInteger("NEED_ROAD_TEST",dto.getNeedRoadTest());
		tiRepairOrder.setString("RECOMMEND_EMP_NAME",dto.getRecommendEmpName());
		tiRepairOrder.setString("RECOMMEND_CUSTOMER_NAME",dto.getRecommendCustomerName());
		tiRepairOrder.setString("SERVICE_ADVISOR",dto.getServiceAdvisor());
		tiRepairOrder.setString("SERVICE_ADVISOR_ASS",dto.getServiceAdvisorAss());
		tiRepairOrder.setInteger("RO_STATUS",dto.getRoStatus());
		tiRepairOrder.setTimestamp("RO_CREATE_DATE",dto.getRoCreateDate());
		tiRepairOrder.setTimestamp("END_TIME_SUPPOSED",dto.getEndTimeSupposed());
		tiRepairOrder.setString("CHIEF_TECHNICIAN",dto.getChiefTechnician());
		tiRepairOrder.setString("OWNER_NO",dto.getOwnerNo());
		tiRepairOrder.setString("OWNER_NAME",dto.getOwnerName());
		tiRepairOrder.setInteger("OWNER_PROPERTY",dto.getOwnerProperty());
		tiRepairOrder.setString("LICENSE",dto.getLicense());
		tiRepairOrder.setString("VIN",dto.getVin());
		tiRepairOrder.setString("ENGINE_NO",dto.getEngineNo());
		tiRepairOrder.setString("BRAND",dto.getBrand());
		tiRepairOrder.setString("SERIES",dto.getSeries());
		tiRepairOrder.setString("MODEL",dto.getModel());
		tiRepairOrder.setDouble("IN_MILEAGE",dto.getInMileage());
		tiRepairOrder.setDouble("OUT_MILEAGE",dto.getOutMileage());
		tiRepairOrder.setInteger("IS_CHANGE_ODOGRAPH",dto.getIsChangeOdograph());
		tiRepairOrder.setDouble("CHANGE_MILEAGE",dto.getChangeMileage());
		tiRepairOrder.setDouble("TOTAL_CHANGE_MILEAGE",dto.getTotalChangeMileage());
		tiRepairOrder.setDouble("TOTAL_MILEAGE",dto.getTotalMileage());
		tiRepairOrder.setString("DELIVERER",dto.getDeliverer());
		tiRepairOrder.setInteger("DELIVERER_GENDER",dto.getDelivererGender());
		tiRepairOrder.setString("DELIVERER_PHONE",dto.getDelivererPhone());
		tiRepairOrder.setString("DELIVERER_MOBILE",dto.getDelivererMobile());
		tiRepairOrder.setString("FINISH_USER",dto.getFinishUser());
		tiRepairOrder.setInteger("WAIT_INFO_TAG",dto.getWaitInfoTag());
		tiRepairOrder.setInteger("WAIT_PART_TAG",dto.getWaitPartTag());
		tiRepairOrder.setInteger("COMPLETE_TAG",dto.getCompleteTag());
		tiRepairOrder.setTimestamp("COMPLETE_TIME",dto.getForBalanceTime());
		tiRepairOrder.setInteger("DELIVERY_TAG",dto.getDeliveryTag());
		tiRepairOrder.setTimestamp("DELIVERY_DATE",dto.getDeliveryDate());
		tiRepairOrder.setString("DELIVERY_USER",dto.getDeliveryUser());
		tiRepairOrder.setFloat("LABOUR_PRICE",dto.getLabourPrice());
		tiRepairOrder.setDouble("LABOUR_AMOUNT",dto.getLabourAmount());
		tiRepairOrder.setDouble("REPAIR_PART_AMOUNT",dto.getRepairPartAmount());
		tiRepairOrder.setDouble("SALES_PART_AMOUNT",dto.getSalesPartAmount());
		tiRepairOrder.setDouble("ADD_ITEM_AMOUNT",dto.getAddItemAmount());
		tiRepairOrder.setDouble("OVER_ITEM_AMOUNT",dto.getOverItemAmount());
		tiRepairOrder.setDouble("REPAIR_AMOUNT",dto.getRepairAmount());
//		tiRepairOrder.setDouble("RO_NO",dto.getRepairPartAmount());
		tiRepairOrder.setDouble("ESTIMATE_AMOUNT",dto.getEstimateAmount());
		tiRepairOrder.setDouble("BALANCE_AMOUNT",dto.getBalanceAmount());
		tiRepairOrder.setDouble("RECEIVE_AMOUNT",dto.getReceiveAmount());
		tiRepairOrder.setDouble("SUB_OBB_AMOUNT",dto.getSubObbAmount());
		tiRepairOrder.setDouble("DERATE_AMOUNT",dto.getDerateAmount());
		tiRepairOrder.setDouble("FIRST_ESTIMATE_AMOUNT",dto.getFirstEstimateAmount());
		tiRepairOrder.setInteger("TRACE_TAG",dto.getTraceTag());
		tiRepairOrder.setString("REMARK",dto.getRemark());
		tiRepairOrder.setString("REMARK1",dto.getRemark1());
		tiRepairOrder.setString("REMARK2",dto.getRemark2());
		tiRepairOrder.setString("TEST_DRIVER",dto.getTestDriver());
		tiRepairOrder.setTimestamp("PRINT_RO_TIME",dto.getPrintRoTime());
		tiRepairOrder.setInteger("RO_CHARGE_TYPE",dto.getRoChargeType());
		tiRepairOrder.setTimestamp("PRINT_RP_TIME",dto.getPrintRpTime());
		tiRepairOrder.setTimestamp("ESTIMATE_BEGIN_TIME",dto.getEstimateBeginTime());
		tiRepairOrder.setInteger("IS_ACTIVITY",dto.getIsActivity());
		tiRepairOrder.setInteger("IS_CLOSE_RO",dto.getIsCloseRo());
		tiRepairOrder.setInteger("IS_MAINTAIN",dto.getIsMaintain());
		tiRepairOrder.setString("CUSTOMER_DESC",dto.getCustomerDesc());
		tiRepairOrder.setInteger("RO_SPLIT_STATUS",dto.getRoSplitStatus());
		tiRepairOrder.setString("MEMBER_NO",dto.getMemberNo());
		tiRepairOrder.setInteger("IN_REASON",dto.getInReason());
		tiRepairOrder.setInteger("MODIFY_NUM",dto.getModifyNum());
		tiRepairOrder.setInteger("QUOTE_END_ACCURATE",dto.getQuoteEndAccurate());
		tiRepairOrder.setInteger("CUSTOMER_PRE_CHECK",dto.getCustomerPreCheck());
		tiRepairOrder.setInteger("CHECKED_END",dto.getCheckedEnd());
		tiRepairOrder.setInteger("EXPLAINED_BALANCE_ACCOUNTS",dto.getExplainedBalanceAccounts());
		tiRepairOrder.setInteger("NOT_ENTER_WORKSHOP",dto.getNotEnterWorkshop());
		tiRepairOrder.setInteger("IS_TAKE_PART_OLD",dto.getIsTakePartOld());
		tiRepairOrder.setString("LOCK_USER",dto.getLockUser());
		tiRepairOrder.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
		tiRepairOrder.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
		tiRepairOrder.setTimestamp("CANCELLED_DATE",dto.getCancelledDate());
		tiRepairOrder.setInteger("REPAIR_STATUS",dto.getRepairStatus());//工单状态
		tiRepairOrder.setInteger("IS_STATUS",1);//作废(1新建，0作废)
		tiRepairOrder.setTimestamp("DELETE_DATE",dto.getDeleteDate());//作废时间
		tiRepairOrder.setString("RO_TROUBLE_DESC",dto.getRoTroubleDesc());
		tiRepairOrder.setInteger("ACTIVITY_TRACE_TAG",dto.getActivityTraceTag());
		tiRepairOrder.setInteger("IS_LARGESS_MAINTAIN",dto.getIsLargessMaintain());
		tiRepairOrder.setInteger("IS_UPDATE_END_TIME_SUPPOSED",dto.getIsUpdateEndTimeSupposed());
		tiRepairOrder.setInteger("IS_SYSTEM_QUOTE",dto.getIsSystemQuote());
		tiRepairOrder.setInteger("NOT_INTEGRAL",dto.getNotIntegral());
		tiRepairOrder.setInteger("PAYMENT",dto.getPayment());
		tiRepairOrder.setString("OCCUR_INSURANCE_NO",dto.getOccurInsuranceNo());
		tiRepairOrder.setDouble("DS_FACT_BALANCE",dto.getDsFactBalance());
		tiRepairOrder.setDouble("DS_AMOUNT",dto.getDsAmount());
		tiRepairOrder.setTimestamp("CASE_CLOSED_DATE",dto.getCaseClosedDate());
		tiRepairOrder.setInteger("OLDPART_TREAT_MODE",dto.getOldpartTreatMode());
		tiRepairOrder.setTimestamp("OLDPART_TREAT_DATE",dto.getOldpartTreatDate());
		tiRepairOrder.setString("OLDPART_REMARK",dto.getOldpartRemark());
		tiRepairOrder.setInteger("IS_OLD_PART",dto.getIsOldPart());
		tiRepairOrder.setString("SETTLEMENT_REMARK",dto.getSettlementRemark());
		tiRepairOrder.setTimestamp("SETTLE_COL_DATE",dto.getSettleColDate());
		tiRepairOrder.setInteger("SETTLEMENT_STATUS",dto.getSettlementStatus());
		tiRepairOrder.setDouble("SETTLEMENT_AMOUNT",dto.getSettlementAmount());
		tiRepairOrder.setString("DELIVER_BILL_MAN",dto.getDeliverBillMan());
		tiRepairOrder.setTimestamp("DELIVER_BILL_DATE",dto.getDeliverBillDate());
		tiRepairOrder.setInteger("INSURANCE_OVER",dto.getInsuranceOver());
		tiRepairOrder.setTimestamp("PRINT_SEND_TIME",dto.getPrintSendTime());
		tiRepairOrder.setString("RECEPTION_NO",dto.getReceptionNo());
		tiRepairOrder.setString("LABOUR_POSITION_CODE",dto.getLabourPositionCode());
		tiRepairOrder.setDouble("MEM_ACTI_TOTAL_AMOUNT",dto.getMemActiTotalAmount());
		tiRepairOrder.setTimestamp("TROUBLE_OCCUR_DATE",dto.getTroubleOccurDate());
		tiRepairOrder.setInteger("RO_CLAIM_STATUS",dto.getRoClaimStatus());
		tiRepairOrder.setInteger("IS_PURCHASE_MAINTENANCE",dto.getIsPurchaseMaintenance());
		tiRepairOrder.setString("CONFIG_CODE",dto.getConfigCode());
		tiRepairOrder.setInteger("IS_QS",dto.getIsQs());
		tiRepairOrder.setInteger("IS_TRIPLE_GUARANTEE",dto.getIsTripleGuarantee());
		tiRepairOrder.setString("EWORKSHOP_REMARK",dto.getEworkshopRemark());
		tiRepairOrder.setTimestamp("DDCN_UPDATE_DATE",dto.getDdcnUpdateDate());
		tiRepairOrder.setInteger("IS_TRIPLE_GUARANTEE_BEF",dto.getIsTripleGuaranteeBef());
		tiRepairOrder.setInteger("IS_PRINT",dto.getIsPrint());
		tiRepairOrder.setInteger("WORKSHOP_STATUS",dto.getWorkshopStatus());
		tiRepairOrder.setTimestamp("PRINT_CAR_TIME",dto.getPrintCarTime());
		tiRepairOrder.setTimestamp("LAST_MAINTENANCE_DATE",dto.getLastMaintenanceDate());
		tiRepairOrder.setDouble("LAST_MAINTENANCE_MILEAGE",dto.getLastMaintenanceMileage());
		tiRepairOrder.setInteger("IS_SERIOUS_TROUBLE",dto.getIsSeriousTrouble());
		tiRepairOrder.setInteger("IS_ABANDON_ACTIVITY",dto.getIsAbandonActivity());
		tiRepairOrder.setInteger("SCHEME_STATUS",dto.getSchemeStatus());
		tiRepairOrder.setInteger("SERIOUS_SAFETY_STATUS",dto.getSeriousSafetyStatus());
		tiRepairOrder.setInteger("IS_TIMEOUT_SUBMIT",dto.getIsTimeoutSubmit());
		tiRepairOrder.setInteger("IN_BINDING_STATUS",dto.getInBindingStatus());
		tiRepairOrder.setInteger("OUT_BINDING_STATUS",dto.getOutBindingStatus());
		tiRepairOrder.setString("CARD_NO",dto.getCardNo());//卡券编号
		tiRepairOrder.setDouble("CARD_VOUCHER_DISCOUNT",dto.getCardAmount());//卡券折扣金额
		tiRepairOrder.setString("STANDARD_PACKAGE_CODE",dto.getStandardPackageCode());//保养套餐代码  zhm
		tiRepairOrder.insert();
	}
	
	/**
	 * 工单附加项目明细
	 * @param dto
	 * @throws Exception
	 */
	public void insertTiRoAddItem(OpRepairOrderDTO dto) throws Exception{
		logger.info("=============工单附加项目明细============");
		LinkedList<RoAddItemDetailDTO> roAddItemDtoList = dto.getRoAddItemVoList();//工单附加项目明细
		if(roAddItemDtoList != null && roAddItemDtoList.size()>0){
			for(int i=0;i<roAddItemDtoList.size();i++){
				RoAddItemDetailDTO roAddItemDetaildto = (RoAddItemDetailDTO) roAddItemDtoList.get(i);
				String sql="SELECT * FROM TI_RO_ADD_ITEM_DCS WHERE ITEM_ID = ? AND DEALER_CODE = ?";
				List <Object> params= new ArrayList<Object>();
				params.add(roAddItemDetaildto.getItemId());
				params.add(dealerCode);
				List<Map> list=OemDAOUtil.findAll(sql, params);
				//根据itemId和dealerCode来判断，如果存在则新增，否则则修改
				if(null!= list && list.size()>0){
					logger.info("=============工单附加项目明细（修改）============");
					//更新value
					StringBuffer upSqlV=new StringBuffer();
					upSqlV.append("RO_NO = ?");
					upSqlV.append("MANAGE_SORT_CODE = ?");
					upSqlV.append("ADD_ITEM_CODE = ?");
					upSqlV.append("ADD_ITEM_NAME = ?");
					upSqlV.append("ADD_ITEM_AMOUNT = ?");
					upSqlV.append("CHARGE_PARTITION_CODE = ?");
					upSqlV.append("ACTIVITY_CODE = ?");
					upSqlV.append("REMARK = ?");
					upSqlV.append("DISCOUNT = ?");
					upSqlV.append("DDCN_UPDATE_DATE = ?");
					upSqlV.append("UPDATE_BY = ?");
					upSqlV.append("UPDATE_DATE = ?");
							
					//更新条件
					StringBuffer upSqlC=new StringBuffer();
					upSqlC.append("ITEM_ID = ?");
					upSqlC.append("DEALER_CODE = ?");
					
					//参数
					List<Object> uparams = new ArrayList<Object>();
					uparams.add(roAddItemDetaildto.getRoNo());
					uparams.add(roAddItemDetaildto.getManageSortCode());
					uparams.add(roAddItemDetaildto.getAddItemCode());
					uparams.add(roAddItemDetaildto.getAddItemName());
					uparams.add(roAddItemDetaildto.getAddItemAmount());
					uparams.add(roAddItemDetaildto.getChargePartitionCode());
					uparams.add(roAddItemDetaildto.getActivityCode());
					uparams.add(roAddItemDetaildto.getRemark());
					uparams.add(roAddItemDetaildto.getDiscount());
					uparams.add(roAddItemDetaildto.getDdcnUpdateDate());
					uparams.add(DEConstant.DE_UPDATE_BY);
					uparams.add(new Date(System.currentTimeMillis()));
					
					uparams.add(roAddItemDetaildto.getItemId());
					uparams.add(dealerCode);

					TiRoAddItemPO.update(upSqlV.toString(), upSqlC.toString(), uparams.toArray());

				}else{
					logger.info("=============工单附加项目明细（新增）============");
					TiRoAddItemPO tiRoAddItemPO = new TiRoAddItemPO();
					tiRoAddItemPO.setLong("ITEM_ID",roAddItemDetaildto.getItemId());
					tiRoAddItemPO.setString("DEALER_CODE",dealerCode);
					tiRoAddItemPO.setString("RO_NO",roAddItemDetaildto.getRoNo());
					tiRoAddItemPO.setString("MANAGE_SORT_CODE",roAddItemDetaildto.getManageSortCode());
					tiRoAddItemPO.setString("ADD_ITEM_CODE",roAddItemDetaildto.getAddItemCode());
					tiRoAddItemPO.setString("ADD_ITEM_NAME",roAddItemDetaildto.getAddItemName());
					tiRoAddItemPO.setDouble("ADD_ITEM_AMOUNT",roAddItemDetaildto.getAddItemAmount());
					tiRoAddItemPO.setString("CHARGE_PARTITION_CODE",roAddItemDetaildto.getChargePartitionCode());
					tiRoAddItemPO.setString("ACTIVITY_CODE",roAddItemDetaildto.getActivityCode());
					tiRoAddItemPO.setString("REMARK",roAddItemDetaildto.getRemark());
					tiRoAddItemPO.setDouble("DISCOUNT",roAddItemDetaildto.getDiscount());
					tiRoAddItemPO.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
					tiRoAddItemPO.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
					tiRoAddItemPO.setTimestamp("DDCN_UPDATE_DATE",roAddItemDetaildto.getDdcnUpdateDate());
					tiRoAddItemPO.insert();
				}
			}
		}
	}
	
	/**
	 * 工单维修项目明细
	 * @param dto
	 * @throws Exception
	 */
	public void insertTiRoLabour(OpRepairOrderDTO dto) throws Exception{
		logger.info("=============工单维修项目明细============");
		LinkedList<RoLabourDetailDTO> roLabourDtoList = dto.getRoLabourVoList();//工单维修项目明细
		if(roLabourDtoList != null && roLabourDtoList.size()>0){
			for(int i=0;i<roLabourDtoList.size();i++){
				RoLabourDetailDTO roLabourDetaildto = (RoLabourDetailDTO)roLabourDtoList.get(i);
				String sql="SELECT * FROM TI_RO_LABOUR_DCS WHERE ITEM_ID = ? AND DEALER_CODE = ?";
				List <Object> params= new ArrayList<Object>();
				params.add(roLabourDetaildto.getItemId());
				params.add(dealerCode);
				List<Map> list=OemDAOUtil.findAll(sql, params);
				//根据itemId和dealerCode来判断，如果存在则新增，否则则修改
				if(null!= list && list.size()>0){
					logger.info("=============工单维修项目明细（修改）============");
					//更新value
					StringBuffer upSqlV=new StringBuffer();
					upSqlV.append("RO_NO = ?");
					upSqlV.append("CHARGE_PARTITION_CODE = ?");
					upSqlV.append("TROUBLE_CAUSE = ?");
					upSqlV.append("TROUBLE_DESC = ?");
					upSqlV.append("LOCAL_LABOUR_CODE = ?");
					upSqlV.append("LOCAL_LABOUR_NAME = ?");
					upSqlV.append("LABOUR_CODE = ?");
					upSqlV.append("LABOUR_PRICE = ?");
					upSqlV.append("LABOUR_NAME = ?");
					upSqlV.append("STD_LABOUR_HOUR = ?");
					upSqlV.append("LABOUR_AMOUNT = ?");
					upSqlV.append("MANAGE_SORT_CODE = ?");
					upSqlV.append("TECHNICIAN = ?");
					upSqlV.append("WORKER_TYPE_CODE = ?");
					upSqlV.append("REMARK = ?");
					upSqlV.append("DISCOUNT = ?");
					upSqlV.append("INTER_RETURN = ?");
					upSqlV.append("NEEDLESS_REPAIR = ?");
					upSqlV.append("PRE_CHECK = ?");
					upSqlV.append("CONSIGN_EXTERIOR = ?");
					upSqlV.append("ASSIGN_LABOUR_HOUR = ?");
					upSqlV.append("ASSIGN_TAG = ?");
					upSqlV.append("ACTIVITY_CODE = ?");
					upSqlV.append("PACKAGE_CODE = ?");
					upSqlV.append("REPAIR_TYPE_CODE = ?");
					upSqlV.append("MODEL_LABOUR_CODE = ?");
					upSqlV.append("ADD_TAG = ?");
					upSqlV.append("CLAIM_LABOUR = ?");
					upSqlV.append("CLAIM_AMOUNT = ?");
					upSqlV.append("REASON = ?");
					upSqlV.append("STD_LABOUR_HOUR_SAVE = ?");
					upSqlV.append("CARD_ID = ?");
					upSqlV.append("SPRAY_AREA = ?");
					upSqlV.append("POS_CODE = ?");
					upSqlV.append("POS_NAME = ?");
					upSqlV.append("APP_CODE = ?");
					upSqlV.append("APP_NAME = ?");
					upSqlV.append("IS_TRIPLE_GUARANTEE = ?");
					upSqlV.append("NO_WARRANTY_CONDITION = ?");
					upSqlV.append("WAR_LEVEL = ?");
					upSqlV.append("INFIX_CODE = ?");
					upSqlV.append("LABOUR_TYPE = ?");
					upSqlV.append("DDCN_UPDATE_DATE = ?");
					upSqlV.append("RO_LABOUR_TYPE = ?");
					upSqlV.append("UPDATE_BY = ?");
					upSqlV.append("UPDATE_DATE = ?");
					
					//更新条件
					StringBuffer upSqlC=new StringBuffer();
					upSqlC.append("ITEM_ID = ?");
					upSqlC.append("DEALER_CODE = ?");
					
					//参数
					List<Object> uparams = new ArrayList<Object>();
					uparams.add(roLabourDetaildto.getRoNo());
					uparams.add(roLabourDetaildto.getChargePartitionCode());
					uparams.add(roLabourDetaildto.getTroubleCause());
					uparams.add(roLabourDetaildto.getTroubleDesc());
					uparams.add(roLabourDetaildto.getLocalLabourCode());
					uparams.add(roLabourDetaildto.getLocalLabourName());
					uparams.add(roLabourDetaildto.getLabourCode());
					uparams.add(roLabourDetaildto.getLabourPrice());
					uparams.add(roLabourDetaildto.getLabourName());
					uparams.add(roLabourDetaildto.getStdLabourHour());
					uparams.add(roLabourDetaildto.getLabourAmount());
					uparams.add(roLabourDetaildto.getManageSortCode());
					uparams.add(roLabourDetaildto.getTechnician());
					uparams.add(roLabourDetaildto.getWorkerTypeCode());
					uparams.add(roLabourDetaildto.getRemark());
					uparams.add(roLabourDetaildto.getDiscount());
					uparams.add(roLabourDetaildto.getInterReturn());
					uparams.add(roLabourDetaildto.getNeedlessRepair());
					uparams.add(roLabourDetaildto.getPreCheck());
					uparams.add(roLabourDetaildto.getConsignExterior());
					uparams.add(roLabourDetaildto.getAssignLabourHour());
					uparams.add(roLabourDetaildto.getAssignTag());
					uparams.add(roLabourDetaildto.getActivityCode());
					uparams.add(roLabourDetaildto.getPackageCode());
					uparams.add(roLabourDetaildto.getRepairTypeCode());
					uparams.add(roLabourDetaildto.getModelLabourCode());
					uparams.add(roLabourDetaildto.getAddTag());
					uparams.add(roLabourDetaildto.getClaimLabour());
					uparams.add(roLabourDetaildto.getClaimAmount());
					uparams.add(roLabourDetaildto.getReason());
					uparams.add(roLabourDetaildto.getStdLabourHourSave());
					uparams.add(roLabourDetaildto.getCardId());
					uparams.add(roLabourDetaildto.getSprayArea());
					uparams.add(roLabourDetaildto.getPosCode());
					uparams.add(roLabourDetaildto.getPosName());
					uparams.add(roLabourDetaildto.getAppCode());
					uparams.add(roLabourDetaildto.getAppName());
					uparams.add(roLabourDetaildto.getIsTripleGuarantee());
					uparams.add(roLabourDetaildto.getNoWarrantyCondition());
					uparams.add(roLabourDetaildto.getWarLevel());
					uparams.add(roLabourDetaildto.getInfixCode());
					uparams.add(roLabourDetaildto.getLabourType());
					uparams.add(roLabourDetaildto.getDdcnUpdateDate());
					uparams.add(roLabourDetaildto.getRoLabourType());
					uparams.add(DEConstant.DE_UPDATE_BY);
					uparams.add(new Date(System.currentTimeMillis()));
					
					uparams.add(roLabourDetaildto.getItemId());
					uparams.add(dealerCode);

					TiRoLabourPO.update(upSqlV.toString(), upSqlC.toString(), uparams.toArray());
				
				}else{
					logger.info("=============工单维修项目明细（新增）============");
					TiRoLabourPO tiRoLabourPO = new TiRoLabourPO();
					tiRoLabourPO.setLong("ITEM_ID",roLabourDetaildto.getItemId());
					tiRoLabourPO.setString("DEALER_CODE",dealerCode);
					tiRoLabourPO.setString("RO_NO",roLabourDetaildto.getRoNo());
					tiRoLabourPO.setString("CHARGE_PARTITION_CODE",roLabourDetaildto.getChargePartitionCode());
					tiRoLabourPO.setString("TROUBLE_CAUSE",roLabourDetaildto.getTroubleCause());
					tiRoLabourPO.setString("TROUBLE_DESC",roLabourDetaildto.getTroubleDesc());
					tiRoLabourPO.setString("LOCAL_LABOUR_CODE",roLabourDetaildto.getLocalLabourCode());
					tiRoLabourPO.setString("LOCAL_LABOUR_NAME",roLabourDetaildto.getLocalLabourName());
					tiRoLabourPO.setString("LABOUR_CODE",roLabourDetaildto.getLabourCode());
					tiRoLabourPO.setDouble("LABOUR_PRICE",roLabourDetaildto.getLabourPrice());
					tiRoLabourPO.setString("LABOUR_NAME",roLabourDetaildto.getLabourName());
					tiRoLabourPO.setDouble("STD_LABOUR_HOUR",roLabourDetaildto.getStdLabourHour());
					tiRoLabourPO.setDouble("LABOUR_AMOUNT",roLabourDetaildto.getLabourAmount());
					tiRoLabourPO.setString("MANAGE_SORT_CODE",roLabourDetaildto.getManageSortCode());
					tiRoLabourPO.setString("TECHNICIAN",roLabourDetaildto.getTechnician());
					tiRoLabourPO.setString("WORKER_TYPE_CODE",roLabourDetaildto.getWorkerTypeCode());
					tiRoLabourPO.setString("REMARK",roLabourDetaildto.getRemark());
					tiRoLabourPO.setDouble("DISCOUNT",roLabourDetaildto.getDiscount());
					tiRoLabourPO.setInteger("INTER_RETURN",roLabourDetaildto.getInterReturn());
					tiRoLabourPO.setInteger("NEEDLESS_REPAIR",roLabourDetaildto.getNeedlessRepair());
					tiRoLabourPO.setInteger("PRE_CHECK",roLabourDetaildto.getPreCheck());
					tiRoLabourPO.setInteger("CONSIGN_EXTERIOR",roLabourDetaildto.getConsignExterior());
					tiRoLabourPO.setDouble("ASSIGN_LABOUR_HOUR",roLabourDetaildto.getAssignLabourHour());
					tiRoLabourPO.setInteger("ASSIGN_TAG",roLabourDetaildto.getAssignTag());
					tiRoLabourPO.setString("ACTIVITY_CODE",roLabourDetaildto.getActivityCode());
					tiRoLabourPO.setString("PACKAGE_CODE",roLabourDetaildto.getPackageCode());
					tiRoLabourPO.setString("REPAIR_TYPE_CODE",roLabourDetaildto.getRepairTypeCode());
					tiRoLabourPO.setString("MODEL_LABOUR_CODE",roLabourDetaildto.getModelLabourCode());
					tiRoLabourPO.setInteger("ADD_TAG",roLabourDetaildto.getAddTag());
					tiRoLabourPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
					tiRoLabourPO.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
					tiRoLabourPO.setDouble("CLAIM_LABOUR",roLabourDetaildto.getClaimLabour());
					tiRoLabourPO.setDouble("CLAIM_AMOUNT",roLabourDetaildto.getClaimAmount());
					tiRoLabourPO.setInteger("REASON",roLabourDetaildto.getReason());
					tiRoLabourPO.setDouble("STD_LABOUR_HOUR_SAVE",roLabourDetaildto.getStdLabourHourSave());
					tiRoLabourPO.setLong("CARD_ID",roLabourDetaildto.getCardId());
					tiRoLabourPO.setString("SPRAY_AREA",roLabourDetaildto.getSprayArea());
					tiRoLabourPO.setString("POS_CODE",roLabourDetaildto.getPosCode());
					tiRoLabourPO.setString("POS_NAME",roLabourDetaildto.getPosName());
					tiRoLabourPO.setString("APP_CODE",roLabourDetaildto.getAppCode());
					tiRoLabourPO.setString("APP_NAME",roLabourDetaildto.getAppName());
					tiRoLabourPO.setInteger("IS_TRIPLE_GUARANTEE",roLabourDetaildto.getIsTripleGuarantee());
					tiRoLabourPO.setString("NO_WARRANTY_CONDITION",roLabourDetaildto.getNoWarrantyCondition());
					tiRoLabourPO.setInteger("WAR_LEVEL",roLabourDetaildto.getWarLevel());
					tiRoLabourPO.setString("INFIX_CODE",roLabourDetaildto.getInfixCode());
					tiRoLabourPO.setInteger("LABOUR_TYPE",roLabourDetaildto.getLabourType());
					tiRoLabourPO.setTimestamp("DDCN_UPDATE_DATE",roLabourDetaildto.getDdcnUpdateDate());
					tiRoLabourPO.setInteger("RO_LABOUR_TYPE",roLabourDetaildto.getRoLabourType());
					tiRoLabourPO.insert();
				}
			}
		}
	}

	/**
	 * 工单维修配件明细
	 * @param dto
	 * @throws Exception
	 */
	public void insertTiRoRepairPart(OpRepairOrderDTO dto) throws Exception{
		logger.info("=============工单维修配件明细============");
		LinkedList<RoRepairPartDetailDTO> roRepairPartdtoList = dto.getRoRepairPartVoList();//工单维修配件明细
		if(roRepairPartdtoList != null && roRepairPartdtoList.size()>0){
			for(int j=0;j<roRepairPartdtoList.size();j++){
				RoRepairPartDetailDTO roRepairPartDetaildto = (RoRepairPartDetailDTO) roRepairPartdtoList.get(j);
				String sql="SELECT * FROM TI_RO_REPAIR_PART_DCS WHERE ITEM_ID = ? AND DEALER_CODE = ?";
				List <Object> params= new ArrayList<Object>();
				params.add(roRepairPartDetaildto.getItemId());
				params.add(dealerCode);
				List<Map> list=OemDAOUtil.findAll(sql, params);
				//根据itemId和dealerCode来判断，如果存在则新增，否则则修改
				if(null!= list && list.size()>0){
					logger.info("=============工单维修配件明细（修改）============");
					//更新value
					StringBuffer upSqlV=new StringBuffer();
					upSqlV.append("PART_NO = ?");
					upSqlV.append("PART_NAME = ?");
					upSqlV.append("STORAGE_CODE = ?");
					upSqlV.append("CHARGE_PARTITION_CODE = ?");
					upSqlV.append("STORAGE_POSITION_CODE = ?");
					upSqlV.append("UNIT_CODE = ?");
					upSqlV.append("PART_BATCH_NO = ?");
					upSqlV.append("MANAGE_SORT_CODE = ?");
					upSqlV.append("OUT_STOCK_NO = ?");
					upSqlV.append("PRICE_TYPE = ?");
					upSqlV.append("IS_MAIN_PART = ?");
					upSqlV.append("PART_QUANTITY = ?");
					upSqlV.append("PRICE_RATE = ?");
					upSqlV.append("OEM_LIMIT_PRICE = ?");
					upSqlV.append("PART_COST_PRICE = ?");
					upSqlV.append("PART_SALES_PRICE = ?");
					upSqlV.append("PART_SALES_PRICE = ?");
					upSqlV.append("PART_COST_AMOUNT = ?");
					upSqlV.append("PART_SALES_AMOUNT = ?");
					upSqlV.append("SENDER = ?");
					upSqlV.append("RECEIVER = ?");
					upSqlV.append("SEND_TIME = ?");
					upSqlV.append("IS_FINISHED = ?");
					upSqlV.append("BATCH_NO = ?");
					upSqlV.append("ACTIVITY_CODE = ?");
					upSqlV.append("PRE_CHECK = ?");
					upSqlV.append("DISCOUNT = ?");
					upSqlV.append("REPAIR_TYPE_CODE = ?");
					upSqlV.append("NON_ONE_OFF = ?");
					upSqlV.append("ADD_TAG = ?");
					upSqlV.append("REASON = ?");
					upSqlV.append("CARD_ID = ?");
					upSqlV.append("FROM_TYPE = ?");
					upSqlV.append("DXP_DATE = ?");
					upSqlV.append("LABOUR_NAME = ?");
					upSqlV.append("RO_NO = ?");
					upSqlV.append("OTHER_PART_COST_PRICE = ?");
					upSqlV.append("OTHER_PART_COST_AMOUNT = ?");
					upSqlV.append("POS_CODE = ?");
					upSqlV.append("POS_NAME = ?");
					upSqlV.append("PART_INFIX = ?");
					upSqlV.append("WAR_LEVEL = ?");
					upSqlV.append("DDCN_UPDATE_DATE = ?");
					upSqlV.append("IS_OLDPART_TREAT = ?");
					upSqlV.append("PART_CATEGORY = ?");
					upSqlV.append("UPDATE_BY = ?");
					upSqlV.append("UPDATE_DATE = ?");
					
					//更新条件
					StringBuffer upSqlC=new StringBuffer();
					upSqlC.append("ITEM_ID = ?");
					upSqlC.append("DEALER_CODE = ?");
					
					//参数
					List<Object> uparams = new ArrayList<Object>();
					uparams.add(roRepairPartDetaildto.getPartNo());
					uparams.add(roRepairPartDetaildto.getPartName());
					uparams.add(roRepairPartDetaildto.getStorageCode());
					uparams.add(roRepairPartDetaildto.getChargePartitionCode());
					uparams.add(roRepairPartDetaildto.getStoragePositionCode());
					uparams.add(roRepairPartDetaildto.getUnitCode());
					uparams.add(roRepairPartDetaildto.getPartBatchNo());
					uparams.add(roRepairPartDetaildto.getManageSortCode());
					uparams.add(roRepairPartDetaildto.getOutStockNo());
					uparams.add(roRepairPartDetaildto.getPriceType());
					uparams.add(roRepairPartDetaildto.getIsMainPart());
					uparams.add(roRepairPartDetaildto.getPartQuantity());
					uparams.add(roRepairPartDetaildto.getPriceRate());
					uparams.add(roRepairPartDetaildto.getOemLimitPrice());
					uparams.add(roRepairPartDetaildto.getPartCostPrice());
					uparams.add(roRepairPartDetaildto.getPartSalesPrice());
					uparams.add(roRepairPartDetaildto.getPartCostAmount());
					uparams.add(roRepairPartDetaildto.getPartSalesAmount());
					uparams.add(roRepairPartDetaildto.getSender());
					uparams.add(roRepairPartDetaildto.getReceiver());
					uparams.add(roRepairPartDetaildto.getSendTime());
					uparams.add(roRepairPartDetaildto.getIsFinished());
					uparams.add(roRepairPartDetaildto.getBatchNo());
					uparams.add(roRepairPartDetaildto.getActivityCode());
					uparams.add(roRepairPartDetaildto.getPreCheck());
					uparams.add(roRepairPartDetaildto.getDiscount());
					uparams.add(roRepairPartDetaildto.getRepairTypeCode());
					uparams.add(roRepairPartDetaildto.getNonOneOff());
					uparams.add(roRepairPartDetaildto.getAddTag());
					uparams.add(roRepairPartDetaildto.getReason());
					uparams.add(roRepairPartDetaildto.getCardId());
					uparams.add(roRepairPartDetaildto.getFromType());
					uparams.add(roRepairPartDetaildto.getDxpDate());
					uparams.add(roRepairPartDetaildto.getLabourName());
					uparams.add(roRepairPartDetaildto.getRoNo());
					uparams.add(roRepairPartDetaildto.getOtherPartCostPrice());
					uparams.add(roRepairPartDetaildto.getOtherPartCostAmount());
					uparams.add(roRepairPartDetaildto.getPosCode());
					uparams.add(roRepairPartDetaildto.getPosName());
					uparams.add(roRepairPartDetaildto.getPartInfix());
					uparams.add(roRepairPartDetaildto.getWarLevel());
					uparams.add(roRepairPartDetaildto.getDdcnUpdateDate());
					uparams.add(roRepairPartDetaildto.getIsOldpartTreat());
					uparams.add(roRepairPartDetaildto.getPartCategory());
					
					uparams.add(DEConstant.DE_UPDATE_BY);
					uparams.add(new Date(System.currentTimeMillis()));
					
					uparams.add(roRepairPartDetaildto.getItemId());
					uparams.add(dealerCode);

					TiRoRepairPartPO.update(upSqlV.toString(), upSqlC.toString(), uparams.toArray());
					
				}else{
					logger.info("=============工单维修配件明细（新增）============");
					TiRoRepairPartPO tiRoRepairPartPO = new TiRoRepairPartPO();
					tiRoRepairPartPO.setLong("ITEM_ID",roRepairPartDetaildto.getItemId());
					tiRoRepairPartPO.setString("DEALER_CODE",dealerCode);
					tiRoRepairPartPO.setString("PART_NO",roRepairPartDetaildto.getPartNo());
					tiRoRepairPartPO.setString("PART_NAME",roRepairPartDetaildto.getPartName());
					tiRoRepairPartPO.setString("STORAGE_CODE",roRepairPartDetaildto.getStorageCode());
					tiRoRepairPartPO.setString("CHARGE_PARTITION_CODE",roRepairPartDetaildto.getChargePartitionCode());
					tiRoRepairPartPO.setString("STORAGE_POSITION_CODE",roRepairPartDetaildto.getStoragePositionCode());
					tiRoRepairPartPO.setString("UNIT_CODE",roRepairPartDetaildto.getUnitCode());
					tiRoRepairPartPO.setString("PART_BATCH_NO",roRepairPartDetaildto.getPartBatchNo());
					tiRoRepairPartPO.setString("MANAGE_SORT_CODE",roRepairPartDetaildto.getManageSortCode());
					tiRoRepairPartPO.setString("OUT_STOCK_NO",roRepairPartDetaildto.getOutStockNo());
					tiRoRepairPartPO.setInteger("PRICE_TYPE",roRepairPartDetaildto.getPriceType());
					tiRoRepairPartPO.setInteger("IS_MAIN_PART",roRepairPartDetaildto.getIsMainPart());
					tiRoRepairPartPO.setFloat("PART_QUANTITY",roRepairPartDetaildto.getPartQuantity());
					tiRoRepairPartPO.setFloat("PRICE_RATE",roRepairPartDetaildto.getPriceRate());
					tiRoRepairPartPO.setDouble("OEM_LIMIT_PRICE",roRepairPartDetaildto.getOemLimitPrice());
					tiRoRepairPartPO.setDouble("PART_COST_PRICE",roRepairPartDetaildto.getPartCostPrice());
					tiRoRepairPartPO.setDouble("PART_SALES_PRICE",roRepairPartDetaildto.getPartSalesPrice());
					tiRoRepairPartPO.setDouble("PART_COST_AMOUNT",roRepairPartDetaildto.getPartCostAmount());
					tiRoRepairPartPO.setDouble("PART_SALES_AMOUNT",roRepairPartDetaildto.getPartSalesAmount());
					tiRoRepairPartPO.setString("SENDER",roRepairPartDetaildto.getSender());
					tiRoRepairPartPO.setString("RECEIVER",roRepairPartDetaildto.getReceiver());
					tiRoRepairPartPO.setTimestamp("SEND_TIME",roRepairPartDetaildto.getSendTime());
					tiRoRepairPartPO.setInteger("IS_FINISHED",roRepairPartDetaildto.getIsFinished());
					tiRoRepairPartPO.setInteger("BATCH_NO",roRepairPartDetaildto.getBatchNo());
					tiRoRepairPartPO.setString("ACTIVITY_CODE",roRepairPartDetaildto.getActivityCode());
					tiRoRepairPartPO.setInteger("PRE_CHECK",roRepairPartDetaildto.getPreCheck());
					tiRoRepairPartPO.setDouble("DISCOUNT",roRepairPartDetaildto.getDiscount());
					tiRoRepairPartPO.setString("REPAIR_TYPE_CODE",roRepairPartDetaildto.getRepairTypeCode());
					tiRoRepairPartPO.setInteger("NON_ONE_OFF",roRepairPartDetaildto.getNonOneOff());
					tiRoRepairPartPO.setInteger("ADD_TAG",roRepairPartDetaildto.getAddTag());
					tiRoRepairPartPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
					tiRoRepairPartPO.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
					tiRoRepairPartPO.setString("REASON",roRepairPartDetaildto.getReason());
					tiRoRepairPartPO.setLong("CARD_ID",roRepairPartDetaildto.getCardId());
					tiRoRepairPartPO.setInteger("FROM_TYPE",roRepairPartDetaildto.getFromType());
					tiRoRepairPartPO.setTimestamp("DXP_DATE",roRepairPartDetaildto.getDxpDate());
					tiRoRepairPartPO.setString("LABOUR_NAME",roRepairPartDetaildto.getLabourName());
					tiRoRepairPartPO.setString("RO_NO",roRepairPartDetaildto.getRoNo());
					tiRoRepairPartPO.setDouble("OTHER_PART_COST_PRICE",roRepairPartDetaildto.getOtherPartCostPrice());
					tiRoRepairPartPO.setDouble("OTHER_PART_COST_AMOUNT",roRepairPartDetaildto.getOtherPartCostAmount());
					tiRoRepairPartPO.setString("POS_CODE",roRepairPartDetaildto.getPosCode());
					tiRoRepairPartPO.setString("POS_NAME",roRepairPartDetaildto.getPosName());
					tiRoRepairPartPO.setString("PART_INFIX",roRepairPartDetaildto.getPartInfix());
					tiRoRepairPartPO.setInteger("WAR_LEVEL",roRepairPartDetaildto.getWarLevel());
					tiRoRepairPartPO.setTimestamp("DDCN_UPDATE_DATE",roRepairPartDetaildto.getDdcnUpdateDate());
					tiRoRepairPartPO.setInteger("IS_OLDPART_TREAT",roRepairPartDetaildto.getIsOldpartTreat());
					tiRoRepairPartPO.setString("PART_CATEGORY",roRepairPartDetaildto.getPartCategory());
					tiRoRepairPartPO.insert();
				}
			}
		}
	}

	/**
	 * 配件销售单明细
	 * @param dto
	 * @throws Exception
	 */
	public void insertTiSalesPart(OpRepairOrderDTO dto) throws Exception{
		logger.info("=============配件销售单明细============");
		LinkedList<SalesPartDetailDTO> salesPartdtoList = dto.getSalesPartVoList();//配件销售单明细
		if(salesPartdtoList != null && salesPartdtoList.size()>0){
			for(int i=0;i<salesPartdtoList.size();i++){
				
				SalesPartDetailDTO salesPartDetaildto = (SalesPartDetailDTO)salesPartdtoList.get(i);
				String sql="SELECT * FROM TI_SALES_PART_DCS WHERE SALES_PART_NO = ? AND DEALER_CODE = ?";
				List <Object> params= new ArrayList<Object>();
				params.add(salesPartDetaildto.getSalesPartNo());
				params.add(dealerCode);
				List<Map> list=OemDAOUtil.findAll(sql, params);
				//根据salesPartNo和dealerCode来判断，如果存在则新增，否则则修改
				if(null!= list && list.size()>0){
					logger.info("=============配件销售单明细（修改）============");
					//更新value
					StringBuffer upSqlV=new StringBuffer();
					upSqlV.append("RO_NO = ?");
					upSqlV.append("SO_NO = ?");
					upSqlV.append("CUSTOMER_CODE = ?");
					upSqlV.append("CUSTOMER_NAME = ?");
					upSqlV.append("SALES_PART_AMOUNT = ?");
					upSqlV.append("BALANCE_STATUS = ?");
					upSqlV.append("CONSULTANT = ?");
					upSqlV.append("REMARK = ?");
					upSqlV.append("REMARK1 = ?");
					upSqlV.append("LOCK_USER = ?");
					upSqlV.append("DDCN_UPDATE_DATE = ?");
					upSqlV.append("VIN = ?");
					upSqlV.append("UPDATE_BY = ?");
					upSqlV.append("UPDATE_DATE = ?");
					
					//更新条件
					StringBuffer upSqlC=new StringBuffer();
					upSqlC.append("SALES_PART_NO = ?");
					upSqlC.append("DEALER_CODE = ?");
					
					//参数
					List<Object> uparams = new ArrayList<Object>();
					uparams.add(dto.getRoNo());
					uparams.add(dto.getSoNo());
					uparams.add(salesPartDetaildto.getCustomerCode());
					uparams.add(salesPartDetaildto.getCustomerName());
					uparams.add(salesPartDetaildto.getSalesPartAmount());
					uparams.add(salesPartDetaildto.getBalanceStatus());
					uparams.add(salesPartDetaildto.getConsultant());
					uparams.add(salesPartDetaildto.getRemark());
					uparams.add(salesPartDetaildto.getRemark1());
					uparams.add(salesPartDetaildto.getLockUser());
					uparams.add(salesPartDetaildto.getDdcnUpdateDate());
					uparams.add(salesPartDetaildto.getVin());
					
					uparams.add(DEConstant.DE_UPDATE_BY);
					uparams.add(new Date(System.currentTimeMillis()));
					
					uparams.add(salesPartDetaildto.getSalesPartNo());
					uparams.add(dealerCode);

					TiSalesPartPO.update(upSqlV.toString(), upSqlC.toString(), uparams.toArray());
				}else{
					logger.info("=============配件销售单明细（新增）============");
					TiSalesPartPO tiSalesPartPO = new TiSalesPartPO();
					tiSalesPartPO.setString("DEALER_CODE",dealerCode);
					tiSalesPartPO.setString("SALES_PART_NO",salesPartDetaildto.getSalesPartNo());
					tiSalesPartPO.setString("RO_NO",dto.getRoNo());
					tiSalesPartPO.setString("SO_NO",dto.getSoNo());
					tiSalesPartPO.setString("CUSTOMER_CODE",salesPartDetaildto.getCustomerCode());
					tiSalesPartPO.setString("CUSTOMER_NAME",salesPartDetaildto.getCustomerName());
					tiSalesPartPO.setDouble("SALES_PART_AMOUNT",salesPartDetaildto.getSalesPartAmount());
					tiSalesPartPO.setInteger("BALANCE_STATUS",salesPartDetaildto.getBalanceStatus());
					tiSalesPartPO.setString("CONSULTANT",salesPartDetaildto.getConsultant());
					tiSalesPartPO.setString("REMARK",salesPartDetaildto.getRemark());
					tiSalesPartPO.setString("REMARK1",salesPartDetaildto.getRemark1());
					tiSalesPartPO.setString("LOCK_USER",salesPartDetaildto.getLockUser());
					tiSalesPartPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
					tiSalesPartPO.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
					tiSalesPartPO.setTimestamp("DDCN_UPDATE_DATE",salesPartDetaildto.getDdcnUpdateDate());
					tiSalesPartPO.setString("VIN",salesPartDetaildto.getVin());
					tiSalesPartPO.insert();
				}
			}	
			insertTiSalesPartItem(dto);
		}
	}

	/**
	 * 配件销售单明细
	 * @param dto
	 * @throws Exception
	 */
	public void insertTiSalesPartItem(OpRepairOrderDTO dto) throws Exception{
		logger.info("=============配件销售单明细============");
		LinkedList<SalesPartItemDetailDTO> salesPartItemdtoList = dto.getSalesPartItemVoList();//配件销售单明细
		if(salesPartItemdtoList != null && salesPartItemdtoList.size()>0){
			for(int j=0;j<salesPartItemdtoList.size();j++){
				SalesPartItemDetailDTO salesPartItemDetaildto = (SalesPartItemDetailDTO)salesPartItemdtoList.get(j);
				String sql="SELECT * FROM TI_SALES_PART_ITEM_DCS WHERE ITEM_ID = ? AND DEALER_CODE = ?";
				List <Object> params= new ArrayList<Object>();
				params.add(salesPartItemDetaildto.getItemId());
				params.add(dealerCode);
				List<Map> list=OemDAOUtil.findAll(sql, params);
				//根据itemId和dealerCode来判断，如果存在则新增，否则则修改
				if(null!= list && list.size()>0){
					logger.info("=============配件销售单明细（修改）============");
					//更新value
					StringBuffer upSqlV=new StringBuffer();
					upSqlV.append("SALES_PART_NO = ?");
					upSqlV.append("STORAGE_CODE = ?");
					upSqlV.append("STORAGE_POSITION_CODE = ?");
					upSqlV.append("PART_NO = ?");
					upSqlV.append("PART_BATCH_NO = ?");
					upSqlV.append("PART_NAME = ?");
					upSqlV.append("PART_QUANTITY = ?");
					upSqlV.append("BATCH_NO = ?");
					upSqlV.append("DISCOUNT = ?");
					upSqlV.append("PRICE_TYPE = ?");
					upSqlV.append("OEM_LIMIT_PRICE = ?");
					upSqlV.append("CHARGE_PARTITION_CODE = ?");
					upSqlV.append("MANAGE_SORT_CODE = ?");
					upSqlV.append("UNIT_CODE = ?");
					upSqlV.append("PART_COST_PRICE = ?");
					upSqlV.append("PART_SALES_PRICE = ?");
					upSqlV.append("PART_COST_AMOUNT = ?");
					upSqlV.append("PART_SALES_AMOUNT = ?");
					upSqlV.append("IS_FINISHED = ?");
					upSqlV.append("FINISHED_DATE = ?");
					upSqlV.append("SENDER = ?");
					upSqlV.append("RECEIVER = ?");
					upSqlV.append("SEND_TIME = ?");
					upSqlV.append("IS_DISCOUNT = ?");
					upSqlV.append("DXP_DATE = ?");
					upSqlV.append("OTHER_PART_COST_PRICE = ?");
					upSqlV.append("OTHER_PART_COST_AMOUNT = ?");
					upSqlV.append("OLD_SALES_PART_NO = ?");
					upSqlV.append("SALES_AMOUNT = ?");
					upSqlV.append("SALES_DISCOUNT = ?");
					upSqlV.append("DDCN_UPDATE_DATE = ?");
					upSqlV.append("UPDATE_BY = ?");
					upSqlV.append("UPDATE_DATE = ?");
					
					//更新条件
					StringBuffer upSqlC=new StringBuffer();
					upSqlC.append("ITEM_ID = ?");
					upSqlC.append("DEALER_CODE = ?");
					
					//参数
					List<Object> uparams = new ArrayList<Object>();
					uparams.add(salesPartItemDetaildto.getSalesPartNo());
					uparams.add(salesPartItemDetaildto.getStorageCode());
					uparams.add(salesPartItemDetaildto.getStoragePositionCode());
					uparams.add(salesPartItemDetaildto.getPartNo());
					uparams.add(salesPartItemDetaildto.getPartBatchNo());
					uparams.add(salesPartItemDetaildto.getPartName());
					uparams.add(salesPartItemDetaildto.getPartQuantity());
					uparams.add(salesPartItemDetaildto.getBatchNo());
					uparams.add(salesPartItemDetaildto.getDiscount());
					uparams.add(salesPartItemDetaildto.getPriceType());
					uparams.add(salesPartItemDetaildto.getPriceRate());
					uparams.add(salesPartItemDetaildto.getOemLimitPrice());
					uparams.add(salesPartItemDetaildto.getChargePartitionCode());
					uparams.add(salesPartItemDetaildto.getManageSortCode());
					uparams.add(salesPartItemDetaildto.getUnitCode());
					uparams.add(salesPartItemDetaildto.getPartCostPrice());
					uparams.add(salesPartItemDetaildto.getPartSalesPrice());
					uparams.add(salesPartItemDetaildto.getPartCostAmount());
					uparams.add(salesPartItemDetaildto.getPartSalesAmount());
					uparams.add(salesPartItemDetaildto.getIsFinished());
					uparams.add(salesPartItemDetaildto.getFinishedDate());
					uparams.add(salesPartItemDetaildto.getSender());
					uparams.add(salesPartItemDetaildto.getReceiver());
					uparams.add(salesPartItemDetaildto.getSendTime());
					uparams.add(salesPartItemDetaildto.getIsDiscount());
					uparams.add(salesPartItemDetaildto.getDxpDate());
					uparams.add(salesPartItemDetaildto.getOtherPartCostPrice());
					uparams.add(salesPartItemDetaildto.getOtherPartCostAmount());
					uparams.add(salesPartItemDetaildto.getOldSalesPartNo());
					uparams.add(salesPartItemDetaildto.getSalesAmount());
					uparams.add(salesPartItemDetaildto.getSalesDiscount());
					uparams.add(salesPartItemDetaildto.getDdcnUpdateDate());
					uparams.add(DEConstant.DE_UPDATE_BY);
					uparams.add(new Date(System.currentTimeMillis()));
					
					uparams.add(salesPartItemDetaildto.getSalesPartNo());
					uparams.add(dealerCode);

					TiSalesPartItemPO.update(upSqlV.toString(), upSqlC.toString(), uparams.toArray());
				}else{
					logger.info("=============配件销售单明细（新增）============");
					TiSalesPartItemPO tiSalesPartItemPO = new TiSalesPartItemPO();
					tiSalesPartItemPO.setLong("ITEM_ID",salesPartItemDetaildto.getItemId());
					tiSalesPartItemPO.setString("DEALER_CODE",dealerCode);
					tiSalesPartItemPO.setString("SALES_PART_NO",salesPartItemDetaildto.getSalesPartNo());
					tiSalesPartItemPO.setString("STORAGE_CODE",salesPartItemDetaildto.getStorageCode());
					tiSalesPartItemPO.setString("STORAGE_POSITION_CODE",salesPartItemDetaildto.getStoragePositionCode());
					tiSalesPartItemPO.setString("PART_NO",salesPartItemDetaildto.getPartNo());
					tiSalesPartItemPO.setString("PART_BATCH_NO",salesPartItemDetaildto.getPartBatchNo());
					tiSalesPartItemPO.setString("PART_NAME",salesPartItemDetaildto.getPartName());
					tiSalesPartItemPO.setFloat("PART_QUANTITY",salesPartItemDetaildto.getPartQuantity());
					tiSalesPartItemPO.setString("BATCH_NO",salesPartItemDetaildto.getBatchNo());
					tiSalesPartItemPO.setDouble("DISCOUNT",salesPartItemDetaildto.getDiscount());
					tiSalesPartItemPO.setInteger("PRICE_TYPE",salesPartItemDetaildto.getPriceType());
					tiSalesPartItemPO.setFloat("PRICE_RATE",salesPartItemDetaildto.getPriceRate());
					tiSalesPartItemPO.setDouble("OEM_LIMIT_PRICE",salesPartItemDetaildto.getOemLimitPrice());
					tiSalesPartItemPO.setString("CHARGE_PARTITION_CODE",salesPartItemDetaildto.getChargePartitionCode());
					tiSalesPartItemPO.setString("MANAGE_SORT_CODE",salesPartItemDetaildto.getManageSortCode());
					tiSalesPartItemPO.setString("UNIT_CODE",salesPartItemDetaildto.getUnitCode());
					tiSalesPartItemPO.setDouble("PART_COST_PRICE",salesPartItemDetaildto.getPartCostPrice());
					tiSalesPartItemPO.setDouble("PART_SALES_PRICE",salesPartItemDetaildto.getPartSalesPrice());
					tiSalesPartItemPO.setDouble("PART_COST_AMOUNT",salesPartItemDetaildto.getPartCostAmount());
					tiSalesPartItemPO.setDouble("PART_SALES_AMOUNT",salesPartItemDetaildto.getPartSalesAmount());
					tiSalesPartItemPO.setInteger("IS_FINISHED",salesPartItemDetaildto.getIsFinished());
					tiSalesPartItemPO.setTimestamp("FINISHED_DATE",salesPartItemDetaildto.getFinishedDate());
					tiSalesPartItemPO.setString("SENDER",salesPartItemDetaildto.getSender());
					tiSalesPartItemPO.setString("RECEIVER",salesPartItemDetaildto.getReceiver());
					tiSalesPartItemPO.setTimestamp("SEND_TIME",salesPartItemDetaildto.getSendTime());
					tiSalesPartItemPO.setInteger("IS_DISCOUNT",salesPartItemDetaildto.getIsDiscount());
					tiSalesPartItemPO.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
					tiSalesPartItemPO.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
					tiSalesPartItemPO.setTimestamp("DXP_DATE",salesPartItemDetaildto.getDxpDate());
					tiSalesPartItemPO.setDouble("OTHER_PART_COST_PRICE",salesPartItemDetaildto.getOtherPartCostPrice());
					tiSalesPartItemPO.setDouble("OTHER_PART_COST_AMOUNT",salesPartItemDetaildto.getOtherPartCostAmount());
					tiSalesPartItemPO.setString("OLD_SALES_PART_NO",salesPartItemDetaildto.getOldSalesPartNo());
					tiSalesPartItemPO.setDouble("SALES_AMOUNT",salesPartItemDetaildto.getSalesAmount());
					tiSalesPartItemPO.setDouble("SALES_DISCOUNT",salesPartItemDetaildto.getSalesDiscount());
					tiSalesPartItemPO.setTimestamp("DDCN_UPDATE_DATE",salesPartItemDetaildto.getDdcnUpdateDate());
					tiSalesPartItemPO.insert();
				}
			}
		}
	}

}
