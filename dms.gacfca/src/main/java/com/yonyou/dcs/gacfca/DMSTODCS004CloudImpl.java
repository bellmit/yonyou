package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.po.TiDealerRelationPO;
import com.infoeai.eai.po.TiEcPotentialCustomerPO;
import com.yonyou.dcs.dao.AssignmentDao;
import com.yonyou.dcs.dao.CommonDAO;
import com.yonyou.dcs.dao.DMSTODCS004Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.DMSTODCS004Dto;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.common.domains.PO.basedata.TiEcRetailOrderCallDcsPo;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * DMS -> DCS
 * DMS上报CALL车订单到DCS，自动生成官网订单类型批售订单，订单状态为待经销商订单确认
 * @author luoyang
 * 传输频率：1次/30Minute				
 * 传输上限：ALL	
 * return msg 0 error 1 success	
 *
 */
@Service 
public class DMSTODCS004CloudImpl extends BaseCloudImpl implements DMSTODCS004Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS004CloudImpl.class);
	private Date startTime = new Date();	// 记录接口开始执行的时间
	private Integer dataSize = 0;	// 数据数量
	private Integer status = OemDictCodeConstants.IF_TYPE_NO;	// 默认失败
	private String exceptionMsg = "";	// 错误日志
	
	@Autowired
	DMSTODCS004Dao dao;
	@Autowired
	AssignmentDao assignmentDao;

	/**
	 * 接口执行入口
	 */
	@Override
	public String handleExecutor(List<DMSTODCS004Dto> list) throws Exception {
		String msg = "1";
		logger.info("====零售订单CALL车接收DMS(DMSTODCS004):执行开始====");
		dbService();
		try {
			if(list != null && !list.isEmpty()){
				dataSize = list.size();// 数据数量				
				saveTiTable(list);	// 接收数据写入接口表
			}
			status = OemDictCodeConstants.IF_TYPE_YES;	// 执行成功
			dbService.endTxn(true);
		} catch (Exception e) {
			msg = "0";
			exceptionMsg = CommonBSUV.getErrorInfoFromException(e);
			status = OemDictCodeConstants.IF_TYPE_NO;//执行失败
			logger.info("====零售订单CALL车接收DMS(DMSTODCS004):执行失败====");
			logger.info(CommonBSUV.getErrorInfoFromException(e));
			dbService.endTxn(false);
		} finally {			
			logger.info("========== 零售订单CALL车接收DMS(DMSTODCS004)：执行结束 ==========");			
			// 记录日志表
			Base.detach();
			dbService.clean();
			
			try {
				beginDbService();
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "零售订单CALL车接口：DMS->DCS", startTime, dataSize, status, exceptionMsg, null, null, new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
			}finally{
				Base.detach();
				dbService.clean();
				beginDbService();
			}
		}
		return msg;
	}

	/**
	 *  写入接口表
	 * @param msg
	 * @throws Exception
	 */
	private void saveTiTable(List<DMSTODCS004Dto> list) throws Exception {
		logger.info("========== 零售订单CALL车接收DMS(DMSTODCS004)：写入接口表开始 ==========");
		//零售订单CALL车接收DMS(DMSTODCS004)
		for(DMSTODCS004Dto dto : list){
			String result = doCheckData(dto);
			if("".equals(result)){
				TiEcRetailOrderCallDcsPo po = new TiEcRetailOrderCallDcsPo();
				po.setString("EC_ORDER_NO",dto.getEcOrderNo()); // 官网订单号
				po.setString("DEALER_CODE",CommonBSUV.getDealerCode(dto.getDealerCode()));	// DCS端经销商代码
				po.setString("ENTITY_CODE",dto.getDealerCode());	// DMS端经销商代码
				po.setString("BRAND_CODE",dto.getBrandCode());	// 品牌代码
				po.setString("SERIES_CODE",dto.getSeriesCode());	// 车系代码
				po.setString("MODEL_CODE",dto.getModelCode());	// 车型代码
				po.setString("GROUP_CODE",dto.getGroupCode());	// 车款代码
				po.setString("MODEL_YEAR",dto.getModelYear());	// 年款
				po.setString("COLOR_CODE",dto.getColorCode());	// 颜色代码
				po.setString("TRIM_CODE",dto.getTrimCode());	// 内饰代码
				po.setTimestamp("CALL_DATE",dto.getCallDate());	// CALL车日期
				po.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_YES);	// 校验成功
				po.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);	// 创建人ID
				po.setTimestamp("CREATE_DATE",new Date());	// 创建日期
				po.saveIt();
				createOrder(dto);
			}else{
				TiEcRetailOrderCallDcsPo po = new TiEcRetailOrderCallDcsPo();
				po.setString("EC_ORDER_NO",dto.getEcOrderNo()); // 官网订单号
				po.setString("DEALER_CODE",CommonBSUV.getDealerCode(dto.getDealerCode()));	// DCS端经销商代码
				po.setString("ENTITY_CODE",dto.getDealerCode());	// DMS端经销商代码
				po.setString("BRAND_CODE",dto.getBrandCode());	// 品牌代码
				po.setString("SERIES_CODE",dto.getSeriesCode());	// 车系代码
				po.setString("MODEL_CODE",dto.getModelCode());	// 车型代码
				po.setString("GROUP_CODE",dto.getGroupCode());	// 车款代码
				po.setString("MODEL_YEAR",dto.getModelYear());	// 年款
				po.setString("COLOR_CODE",dto.getColorCode());	// 颜色代码
				po.setString("TRIM_CODE",dto.getTrimCode());	// 内饰代码
				po.setTimestamp("CALL_DATE",dto.getCallDate());	// CALL车日期
				po.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_NO);	// 校验成功
				po.setString("MESSAGE", result);
				po.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);	// 创建人ID
				po.setTimestamp("CREATE_DATE",new Date());	// 创建日期
				po.saveIt();
			}
		}
		logger.info("========== 零售订单CALL车接收DMS(DMSTODCS004)：写入接口表结束 ==========");
	}

	/**
	 * 数据校验
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private String doCheckData(DMSTODCS004Dto dto) {
		String result = "";
		
		// 官网订单号非空校验
		if (CheckUtil.checkNull(dto.getEcOrderNo())) {
			result = "[EC_ORDER_NO]官网订单号为空";
			return result;
		}
		
		// DMS经销商号非空校验
		if (CheckUtil.checkNull(dto.getDealerCode())) {
			result = "[ENTITY_CODE]DMS端经销商代码为空";
			return result;
		}
		
		// CALL车日期非空校验
		if (null == dto.getCallDate()) {
			result = "[CALL_DATE]CALL车日期为空";
			return result;
		}
		
		// 是否存在重复官网订单号校验
		List<TiEcRetailOrderCallDcsPo> ecOrderNoCheckPo1List = TiEcRetailOrderCallDcsPo.find("EC_ORDER_NO = ? AND RESULT = ? AND IS_DEL = ?", 
				dto.getEcOrderNo(),OemDictCodeConstants.IF_TYPE_YES,OemDictCodeConstants.IS_DEL_00);
		if (null != ecOrderNoCheckPo1List && ecOrderNoCheckPo1List.size() > 0) {
			result = "[EC_ORDER_NO]该官网订单号已存在CALL车记录：" + dto.getEcOrderNo();
			return result;
		}
		
		// 官网订单号有效性校验
		List<TiEcPotentialCustomerPO> ecOrderNoCheckPo2List = TiEcPotentialCustomerPO.find("EC_ORDER_NO = ? AND RESULT = ? AND IS_DEL = ?", 
				dto.getEcOrderNo(),OemDictCodeConstants.IF_TYPE_YES,OemDictCodeConstants.IS_DEL_00);
		if (null == ecOrderNoCheckPo2List || ecOrderNoCheckPo2List.size() <= 0) {
			result = "[EC_ORDER_NO]官网订单号无效：" + dto.getEcOrderNo();
			return result;
		}
		
		// DMS经销商代码有效性校验
		List<TiDealerRelationPO> dealerCodeCheckPoList = TiDealerRelationPO.find("DMS_CODE = ?", dto.getDealerCode());
		if (null == dealerCodeCheckPoList || dealerCodeCheckPoList.size() <= 0) {
			result = "[ENTITY_CODE]DMS经销商代码无效：" + dto.getDealerCode();
			return result;
		}
		
		if (!CheckUtil.checkNull(result)) {
			logger.info("========== 零售订单CALL车接收DMS：" + result + " ==========");
		} else {
			logger.info("========== 零售订单CALL车接收DMS：数据校验通过 ==========");
		}
		
		return result;
	}
	
	/**
	 * 数据校验
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private void createOrder(DMSTODCS004Dto dto) throws Exception {
		/*
		 * 配置官网类型批售订单
		 * [ORDER_NO]以W开头
		 */

		TtVsOrderPO orderPo = new TtVsOrderPO();
		orderPo.setString("ORDER_NO",getOrdNoSeq("W"));	// 生成官网类型批售订单号
		orderPo.setString("EC_ORDER_NO",dto.getEcOrderNo());	// 官网订单号
		orderPo.setLong("OEM_COMPANY_ID",Long.parseLong(OemDictCodeConstants.OEM_ACTIVITIES));
		orderPo.setInteger("ORDER_STATUS",OemDictCodeConstants.SALE_ORDER_TYPE_05);	// 订单状态    [70031005：已指派]
		orderPo.setInteger("ORDER_TYPE",OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05);	// 订单类型    [90161005：商城订单]
		orderPo.setTimestamp("ORDER_DATE",new Date());	// 订单日期
		orderPo.setInteger("VEHICLE_USE",OemDictCodeConstants.VEHICLE_USAGE_TYPE_68);	// 车辆用途
		orderPo.setLong("DEALER_ID",CommonBSUV.getDealerIdByEntityCode(dto.getDealerCode()));
		
		
		// 写入订单周
		List<Map> workWeekList = assignmentDao.selectCurrentWeek();
		if (null != workWeekList && workWeekList.size() > 0) {
			orderPo.setInteger("ORDER_MONTH",workWeekList.get(0).get("WORK_MONTH"));
			orderPo.setInteger("ORDER_YEAR",workWeekList.get(0).get("WORK_YEAR"));
			orderPo.setInteger("ORDER_WEEK",workWeekList.get(0).get("WORK_WEEK"));
		}
		
		// 根据官网订单号获取物料ID
		long materialId = dao.getMaterialId(logger, dto.getEcOrderNo());
		
		if (materialId != 0l) {
			orderPo.setLong("MATERAIL_ID",materialId); // 物料ID
			orderPo.setDouble("WHOLESALE_PRICE",CommonDAO.getBasePrice(materialId)); // 工厂批发价格
			orderPo.setDouble("MSRP_PRICE",CommonDAO.getMsrpPrice(materialId)); // MSRP价格
		} else {
			orderPo.setLong("MATERAIL_ID",0L); // 物料ID 该物料ID无效
		}
		
		orderPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_00);
		orderPo.setLong("CREATE_BY",DEConstant.DE_CREATE_BY);
		orderPo.setTimestamp("CREATE_DATE",new Date());
		
		orderPo.saveIt();
		
		CommonDAO.insertHistory(orderPo.getLong("ORDER_ID"), orderPo.getInteger("ORDER_STATUS"), "生成官网类型批售订单", "", DEConstant.DE_CREATE_BY, "零售CALL车上报");
		
	}
	
	public static String getOrdNoSeq(String type) {
		
		// 如果 type 参数不正确返回空
		if (!"M".equals(type) && // 常规订单
			!"E".equals(type) && // 紧急订单
			!"D".equals(type) && // 直销订单
			!"A".equals(type) && // 指派订单
			!"W".equals(type)) { // 官网订单
			return "";
		}
		
		// 定义订单号
		String orderNo = type;
		TtVsOrderPO po = new TtVsOrderPO();
		po.saveIt();
		Long no = po.getLongId();
		po.delete();
		String newStr = String.valueOf(no);
		String appendZero = "";
		
		// 位数不足5位自动往左补零
		if (newStr.length() < 5) {
			for (int i = 5; i > newStr.length(); i--) {
				appendZero += "0";
			}
			newStr = appendZero + newStr;
		}
		
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		orderNo = orderNo + sdf.format(date);
		orderNo = orderNo + newStr;
		
		return orderNo;
		
	}


}
