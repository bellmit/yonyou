package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.BusinessTypeDao;
import com.yonyou.dcs.dao.CommonDAO;
import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.DealerSaleStorageDao;
import com.yonyou.dcs.dao.SaleStockCheckDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.VsStockDetailListDto;
import com.yonyou.dms.DTO.gacfca.VsStockEntryItemDto;
import com.yonyou.dms.common.domains.PO.basedata.TiVehicleInspectionPO;
import com.yonyou.dms.common.domains.PO.basedata.TiWxVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsInspectionDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsInspectionPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsNvdrPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
/**
 * 
* @ClassName: SADCS002CloudImpl 
* @Description: 车辆验收上报（DMS验收后，上报验收明细给上端）
* @author zhengzengliang 
* @date 2017年4月11日 下午6:17:00 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class SADCS002CloudImpl extends BaseCloudImpl implements SADCS002Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS002CloudImpl.class);
	
	SimpleDateFormat sDate = new SimpleDateFormat("yyyyMMdd");
	
	@Autowired
	DeCommonDao deCommonDao;
	
	@Autowired
	SaleStockCheckDao stockDao;
	
	@Autowired
	DealerSaleStorageDao dao;
	
	@Override
	public String receiveDate(List<VsStockEntryItemDto> dtoList) throws Exception {
		logger.info("====数据上报接收开始===="); 
		String msg="1";
		for (VsStockEntryItemDto entry : dtoList) {
			try {
				boolean isValid = isDataValid(entry);
				if (isValid) {
					// 检查该车辆（VIN号）是否已验收 add by maxiang 20150109
					if (isTtVsInspectionExist(entry.getVin())) {
						insertStockData(entry);
					}
					// 获得车辆业务类别
					String businessType = BusinessTypeDao.getVehicleBusinessType("", entry.getVin());
					if (!"".equals(CommonUtils.checkNull(businessType))) {
						if (OemDictCodeConstants.GROUP_TYPE_DOMESTIC.equals(businessType)) {	// 国产车业务处理
							// 得到收车日期
							Date inspectionDate = new Date();
							if (entry.getInspectionDate() != null) {
								inspectionDate = entry.getInspectionDate();
							}
							String arriveDate = sDate.format(inspectionDate);
							Map resultMap = deCommonDao.getOrderByVin(entry.getVin());	// 根据车架号查询订单信息
							if (null != resultMap && resultMap.size() > 0) {
								// 根据车架号更新订单状态
								stockDao.updateTtVsOrder(resultMap);
								// 国产车验收信息写入接口表
								dao.insertTiK4VsNvdr(arriveDate, resultMap.get("DEALER_CODE").toString(), DEConstant.DE_CREATE_BY, resultMap.get("VIN").toString());
								// update_date 20151208 by maxiang begin.. 
								String ORDER_ID = resultMap.get("ORDER_ID").toString();
								if (null != ORDER_ID && !ORDER_ID.trim().equals("")) {
									Long orderId = Long.parseLong(ORDER_ID);
									// 新增订单历史记录
									CommonDAO.insertHistory(orderId, OemDictCodeConstants.SALE_ORDER_TYPE_12, "已收车", "", DEConstant.DE_CREATE_BY, entry.getInspectionPerson());
								}
								// update_date 20151208 by maxiang end..
							}
						} else if (OemDictCodeConstants.GROUP_TYPE_IMPORT.equals(businessType)) {	// 进口车业务处理
							if (isTiVehicleInspectionExist(entry.getVin())) {
								insertInspectionInfo(entry);
							}else{
								logger.info("==========车辆"+entry.getVin()+"已做过零售补传或车辆验收===========");
							}
						}
					}
				}
			} catch (Exception e) {
			    msg="0";
				logger.error("数据上报接收失败", e);
				throw new ServiceBizException(e);
			}
		}
		logger.info("====数据上报接收结束===="); 
		return msg;
	}
	
	private boolean isDataValid(VsStockEntryItemDto vo) throws Exception {
		Map map = deCommonDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerId = String.valueOf(map.get("DEALER_ID")); // 上报经销商信息
		Map vehicleExitMap = stockDao.getVehicleIdByVinForExist(vo.getVin(), dealerId);
		if (vehicleExitMap == null) { // 判读该车辆验收信息是否存在
			logger.info("====经销商车辆验收数据上报出错，没有满足验收条件的车辆，VIN为" + vo.getVin());
			return false;
		}
		return true;
	}
	
	/**
	 * 检查是否已验收（业务表）
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public boolean isTtVsInspectionExist(String vin) throws Exception {
		/*
		 * 通过VIN号查询该车辆的vehicle_id
		 */
		TmVehiclePO vehicle = new TmVehiclePO();
		vehicle.setString("VIN", vin);
		List<TmVehiclePO> vehicleList = stockDao.selectVehicleByVin(vehicle);
		if (vehicleList.size() > 0) {
			/*
			 * 查询该车辆是否存在于验收业务表
			 */
			TtVsInspectionPO inspection = new TtVsInspectionPO();
			inspection.setLong("VEHICLE_ID", vehicleList.get(0).getLong("VEHICLE_ID"));
			inspection.setInteger("IS_DEL", 0);
			List<TtVsInspectionPO> inspectionList = stockDao.selectTtVsInspection(inspection);
			return inspectionList.size() > 0 ? false : true;
			
		} else {
			
			return false;
			
		}
	}
	
	/**
	 * 保存上报的车辆验收数据信息
	 * @param vo
	 * @throws Exception
	 */
	public synchronized void insertStockData(VsStockEntryItemDto vo) throws Exception {
		Date nowDate = new Date();
		// 根据车架号查询车辆ID
		Map vehicleMap = stockDao.getVehicleIdByVin(vo.getVin());
		String vehicleId = vehicleMap.get("VEHICLE_ID").toString();
		TtVsInspectionPO inspectionPO = new TtVsInspectionPO();
		inspectionPO.setLong("VEHICLE_ID", Long.parseLong(vehicleId));	// 车辆ID
		inspectionPO.setString("INSPECTION_PERSON", vo.getInspectionPerson());	// 验收人员
		inspectionPO.setString("REMARK", vo.getRemark());	// 备注信息
		inspectionPO.setTimestamp("ARRIVE_DATE", vo.getInspectionDate());	// 收车日期
		inspectionPO.setTimestamp("CREATE_DATE", nowDate);	// 创建日期
		inspectionPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);	// 创建人ID
		inspectionPO.setInteger("DAMAGE_FLAG", 0);	// 是否质损（默认没有质损件）
		//新增两个字段 by wangJian 2016-07-05 start
		inspectionPO.setInteger("INSPECTION_RESULT", vo.getInspectionResult());//入库验收结果 13351001：验收未通过 13351002：验收已通过
		inspectionPO.setInteger("PDI_RESULT", vo.getPdiResult());//PDI检查结果   70161001：通过 70161002：不通过
		//end
		List<VsStockDetailListDto> detialList = vo.getVsStockDetialList();
		// 插入验收详细信息
		if (detialList != null) {
			if (detialList.size() > 0) {
				inspectionPO.setInteger("DAMAGE_FLAG", 1);	// 是否质损（有质损件）
			}
			inspectionPO.saveIt(); // 插入验收信息
			Long inspectId = inspectionPO.getLong("INSPECTION_ID");
			for (int i = 0; i < detialList.size(); i++) {
				// 写入车辆质损件信息
				TtVsInspectionDetailPO inspectionDetialPO = new TtVsInspectionDetailPO();
				VsStockDetailListDto detialVO =  detialList.get(i);
				inspectionDetialPO.setLong("INSPECTION_ID", inspectId);	// 验收明细ID
				inspectionDetialPO.setString("DAMAGE_DESC", detialVO.getDamageDesc());
				inspectionDetialPO.setString("DAMAGE_PART", detialVO.getDamagePart());
				inspectionDetialPO.setTimestamp("CREATE_DATE", nowDate);	// 创建日期
				inspectionDetialPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);	// 创建人ID
				inspectionDetialPO.saveIt();
			}
		}
		// 将车辆的品牌、车系、车型、颜色插入微信车辆接口表中 add by dengweili 20140319
		Map map = deCommonDao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
		// 将车辆的品牌、车系、车型、颜色插入微信车辆接口表中 add by baojie 20140404
		insertWxVehicleInfo(vo, inspectionPO.getLong("INSPECTION_ID"), dealerCode);
		// 获得车辆业务类别
		String businessType = BusinessTypeDao.getVehicleBusinessType(vehicleId, null);
		if (!"".equals(CommonUtils.checkNull(businessType))) {	// 物业类型非空校验
			if (OemDictCodeConstants.GROUP_TYPE_DOMESTIC.equals(businessType)) {	// 国产车业务处理
				// 更新车辆主数据表
				// 写入验收时间
				stockDao.updateTmVehicle(vo, vehicleId);
				// 更新车辆节点日期记录表（存储日期）
				stockDao.updateTtVehicleNodeHistory(vehicleId, vo);
				// 增加车籍信息
				TtVsVhclChngPO vehicleChangPO = new TtVsVhclChngPO();
				vehicleChangPO.setLong("VEHICLE_ID", Long.parseLong(vehicleId));
				vehicleChangPO.setInteger("CHANGE_CODE", OemDictCodeConstants.K4_VEHICLE_NODE_19);
				vehicleChangPO.setString("CHANGE_NAME", "验收入库");
				vehicleChangPO.setString("CHANGE_DESC", "经销商车辆验收");
				vehicleChangPO.setString("CHANGE_MEMO", "经销商车辆验收");
				vehicleChangPO.setTimestamp("CHANGE_DATE", nowDate);
				vehicleChangPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
				vehicleChangPO.setTimestamp("CREATE_DATE", nowDate);
				vehicleChangPO.saveIt();
			} else if (OemDictCodeConstants.GROUP_TYPE_IMPORT.equals(businessType)) {	// 进口车业务处理
				// 更新车辆主数据表
				stockDao.updateTmVehicle(vo, vehicleId);
				//更新节点车辆状态时间 add by luoyg
				TmVehicleNodeHistoryPO tmhPO = new TmVehicleNodeHistoryPO();
				tmhPO.setLong("VEHICLE_ID", vehicleId);
				List<TmVehicleNodeHistoryPO> tmhList = stockDao.selectTmVehicleNodeHistory(vehicleId);
				if(tmhList.size()==0){
					tmhPO.saveIt();
				}
				stockDao.updateTmVehicleNodeHistory(vehicleId);
				// 增加车籍信息
				TtVsVhclChngPO vehicleChangPO = new TtVsVhclChngPO();
				vehicleChangPO.setLong("VEHICLE_ID", Long.parseLong(vehicleId));
				vehicleChangPO.setInteger("CHANGE_CODE", OemDictCodeConstants.K4_VEHICLE_NODE_19);
				vehicleChangPO.setString("CHANGE_NAME", "验收入库");
				vehicleChangPO.setString("CHANGE_DESC", "经销商车辆验收");
				vehicleChangPO.setString("CHANGE_MEMO", "经销商车辆验收");
				vehicleChangPO.setTimestamp("CHANGE_DATE", nowDate);
				vehicleChangPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
				vehicleChangPO.setTimestamp("CREATE_DATE", nowDate);
				vehicleChangPO.saveIt();
			}
			
		} else {
			/*
			 * 业务类型为空，按照进口车业务处理
			 */
			// 更新车辆主数据表
			stockDao.updateTmVehicle(vo, vehicleId);
			//更新节点车辆状态时间 add by luoyg
			TmVehicleNodeHistoryPO tmhPO = new TmVehicleNodeHistoryPO();
			tmhPO.setLong("VEHICLE_ID", vehicleId);
			List<TmVehicleNodeHistoryPO> tmhList = stockDao.selectTmVehicleNodeHistory(vehicleId);
			if(tmhList.size()==0){
				tmhPO.saveIt();
			}
			stockDao.updateTmVehicleNodeHistory(vehicleId);
			// 增加车籍信息
			TtVsVhclChngPO vehicleChangPO = new TtVsVhclChngPO();
			vehicleChangPO.setLong("VEHICLE_ID", Long.parseLong(vehicleId));
			vehicleChangPO.setInteger("CHANGE_CODE", OemDictCodeConstants.K4_VEHICLE_NODE_19);
			vehicleChangPO.setString("CHANGE_NAME", "验收入库");
			vehicleChangPO.setString("CHANGE_DESC", "经销商车辆验收");
			vehicleChangPO.setString("CHANGE_MEMO", "经销商车辆验收");
			vehicleChangPO.setTimestamp("CHANGE_DATE", nowDate);
			vehicleChangPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
			vehicleChangPO.setTimestamp("CREATE_DATE", nowDate);
			vehicleChangPO.saveIt();
			
		}
	}
	
	private void insertWxVehicleInfo(VsStockEntryItemDto vo, Long inspectionId, String dealerCode) throws Exception {
		
		logger.info("====经销商车辆验收数据写入微信平台接口表，VIN为" + vo.getVin());
		TiWxVehiclePO wxVehicle = new TiWxVehiclePO();
			wxVehicle.setLong("INSPECTION_ID", inspectionId);
			wxVehicle.setString("DEALER_CODE", dealerCode);
			wxVehicle.setString("VIN", vo.getVin());
			wxVehicle.setString("BRAND_ID", vo.getBrand());
			wxVehicle.setString("SERIES_ID", vo.getSeries());
			wxVehicle.setString("MODEL_ID", vo.getModel());
			wxVehicle.setString("COLOR_ID", vo.getColor());
			wxVehicle.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
			wxVehicle.setTimestamp("CREATE_DATE", new Date());
			wxVehicle.setString("IS_SCAN", "0");
			// 判断是否验收过
			if (isWxVehicleExist(vo.getVin(), inspectionId)) {
				logger.info("====车辆VIN:" + vo.getVin() + "存在车辆验收历史数据,微信平台状态IsUpdate设为1");
				wxVehicle.setString("IS_UPDATE", "1");
			} else {
				logger.info("====车辆VIN:" + vo.getVin() + "不存在车辆验收历史数据,微信平台状态IsUpdate设为0");
				wxVehicle.setString("IS_UPDATE", "0");
			}
			wxVehicle.saveIt();
	}
	
	// 判断是否验收过
	public boolean isWxVehicleExist(String vin, Long inspectionId) throws Exception {
		List<Map> vehicleMap = stockDao.getWxVehicleExist(vin, inspectionId);
		return (vehicleMap != null && vehicleMap.size() > 0);
	}
	
	/**
	 * 检查是否已验收（接口表）
	 * @param vin
	 * @return
	 * @throws Exception
	 */
	public boolean isTiVehicleInspectionExist(String vin) throws Exception {
		/*
		 * 查询该车辆是否是做过车辆零售补传
		 */
		List<TtVsNvdrPO> nvdrList = stockDao.selectTtVsNvdr(vin );
		
		if (nvdrList.size() > 0) {
			
			return false;
			
		} else {
			
			/*
			 * 查询该车辆是否已存在于验收接口表
			 */
			List<TiVehicleInspectionPO> list = stockDao.selectTiVehicleInspectionByVin(vin);
			
			return list.size() > 0 ? false : true;
		}
		
	}
	
	private void insertInspectionInfo(VsStockEntryItemDto vo) throws Exception {
		// TODO Auto-generated method stub
		TiVehicleInspectionPO tiVehicleInspectionPO = new TiVehicleInspectionPO();
		tiVehicleInspectionPO.setString("ACTION_CODE", "ZDLD");
		tiVehicleInspectionPO.setString("ACTION_DATE", convertDate2Str(vo.getInspectionDate()));
		tiVehicleInspectionPO.setString("VIN", vo.getVin());
		Map map = deCommonDao.getDcsCompanyCode(vo.getDealerCode());
		tiVehicleInspectionPO.setString("DEALER_CODE", map.get("DCS_CODE").toString());
		tiVehicleInspectionPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
		tiVehicleInspectionPO.setTimestamp("CREATE_DATE", new Date());
		tiVehicleInspectionPO.setString("IS_SCAN", "0");
		tiVehicleInspectionPO.setString("IS_CTCAI_SCAN", "0");
		
		tiVehicleInspectionPO.saveIt();
	}
	
	private String convertDate2Str(Date date) {
		if (date != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return sdf.format(date);
		}
		return null;
	}
	
	

}
