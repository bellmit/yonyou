package com.infoeai.eai.action.k4;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.po.TiK4VehiclePO;
import com.infoeai.eai.po.TmMaterialPricePO;
import com.infoeai.eai.vo.S0001VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVehicleNodeHistoryPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 接口：S0001
 * 方向：SAP > DCS
 * 描述：总装上线
 * 		 总装下线
 * 		 质检完成
 * 		 工厂在库
 * 		 内部结算成功
 * 		 内部结算失败
 * 		 工厂质量扣留
 * 		 工厂质量扣留解除
 * 		 销售公司质量扣留
 * 		 销售公司质量扣留解除
 * 		 延迟发运
 * 		 取消延迟发运
 * 		 取消零售上报
 * 		 删除车辆
 * 频率：30MIN
 */
@Service
public class S0001Impl extends BaseService implements S0001{

	private static Logger logger = LoggerFactory.getLogger(S0001Impl.class);
	
	@Autowired
	K4SICommonDao dao;
	
	/**
	 * 设置数据接收字段
	 * @param xmlList
	 * @return
	 * @throws Exception
	 */
	public List<S0001VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {
		
		List<S0001VO> voList = new ArrayList<S0001VO>();
		
		try {
			
			logger.info("==========S0001 getXMLToVO() is START==========");
			
			logger.info("==========XML赋值到VO==========");
			
			logger.info("==========XMLSIZE:==========" + xmlList.size());
			
			if (xmlList != null && xmlList.size() > 0) {
				
				for (int i = 0; i < xmlList.size(); i++) {
					
					Map<String, String> map = xmlList.get(i);
					
					if (map != null && map.size() > 0) {
						
						S0001VO outVo = new S0001VO();
						outVo.setActionCode(map.get("ACTION_CODE"));					// 交易代码
						outVo.setActionDate(map.get("ACTION_DATE"));					// 交易日期
						outVo.setActionTime(map.get("ACTION_TIME"));					// 交易时间
						outVo.setVin(map.get("VIN"));									// 车架号
						outVo.setMainStatus(map.get("MAIN_STATUS"));					// 主要状态
						outVo.setSecondStatus(map.get("SECOND_STATUS"));				// 第二状态
						outVo.setPredictOfflineDate(map.get("PREDICT_OFFLINE_DATE"));	// 预计下线日期
						outVo.setPredictStorageDate(map.get("PREDICT_STORAGE_DATE"));	// 预计入库日期
						outVo.setModelCode(map.get("MODEL_CODE"));						// 车型
						outVo.setModelYear(map.get("MODEL_YEAR"));						// 年款
						outVo.setColorCode(map.get("COLOR_CODE"));						// 颜色
						outVo.setTrimCode(map.get("TRIM_CODE"));						// 内饰
						outVo.setStandardOption(map.get("STANDARD_OPTION"));			// 标准配置
						outVo.setFactoryOptions(map.get("FACTORY_OPTIONS"));			// 其他配置
						outVo.setLocalOption(map.get("LOCAL_OPTION"));					// 本地配置
						outVo.setSpecialSeries(map.get("SPECIAL_SERIES"));				// Special Series
						outVo.setFactoryCode(map.get("FACTORY_CODE"));					// 工厂
						outVo.setWarehouseCode(map.get("WAREHOUSE_CODE"));				// 库存地点
						outVo.setRemark(map.get("REMARK"));								// 备注
						outVo.setEngineNo(map.get("ENGINE_NO"));						// 发动机号
						outVo.setQualifiedNo(map.get("QUALIFIED_NO"));					// 合格证号
						outVo.setProductDate(map.get("PRODUCT_DATE"));					// 生产日期
						outVo.setBlockDate(map.get("BLOCK_DATE")); 						// 冻结日期
						outVo.setBlockReason(map.get("BLOCK_REASON"));					// 冻结原因
						outVo.setExpectedUnblockDate(map.get("EXPECTED_UNBLOCK_DATE"));	// 预计解冻日期	
						outVo.setActualUnblockDate(map.get("ACTUAL_UNBLOCK_DATE"));		// 实际解冻日期	
						outVo.setCompanyCode(map.get("COMPANY_CODE"));					// 公司代码
						outVo.setMOnlineDate(map.get("M_ONLINE_DATE"));					// 总装上线日期	
						outVo.setMOfflineDate(map.get("M_OFFLINE_DATE"));				// 总装下线日期	
						outVo.setMQualityDate(map.get("M_QUALITY_DATE"));				// 质检完成日期	
						outVo.setMStorageDate(map.get("M_STORAGE_DATE"));				// 入MJV销售库日期	
						outVo.setRowId(map.get("ROW_ID"));								// ROW_ID
						
						voList.add(outVo);
						
						logger.info("==========outVo:==========" + outVo);
						
					}
				}
			}
	
			logger.info("==========S0001 getXMLToVO() is END==========");
			
		} catch (Throwable e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new ServiceBizException("S0001 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S0001 getXMLToVO() is finish==========");
		}
		
		return voList;
	}

	/**
	 * S0001 接口执行入口
	 */
	public List<returnVO> execute(List<S0001VO> voList) throws Throwable{
		
		logger.info("==========S0001 is begin==========");
		
		List<returnVO> retVoList = new ArrayList<returnVO>();
		
		String[] returnVo = null;

		/******************** 开启事物 ********************/
//		POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);
		beginDbService();
		
		try {
			
			for (int i = 0; i < voList.size(); i++) {
				
				returnVo = S0001Check(voList.get(i));	// 校验 S0001VO 数据
				
				if (null == returnVo) {
					
					k4BusinessProcess(voList.get(i));	// S0001 数据业务处理逻辑
					
				} else {
					
					/*
					 * 车辆接口表错误数据插入
					 */
					TiK4VehiclePO po = new TiK4VehiclePO();
//					po.setIfId(new Long(SequenceManager.getSequence("")));				// 接口ID
					po.setString("ACTION_CODE",voList.get(i).getActionCode());					// 交易代码
					po.setString("ACTION_DATE",voList.get(i).getActionDate());					// 交易日期
					po.setString("ACTION_TIME",voList.get(i).getActionTime());					// 交易时间
					po.setString("MAIN_STATUS",voList.get(i).getMainStatus());					// 主要状态
					po.setString("SECOND_STATUS",voList.get(i).getSecondStatus());				// 第二状态
					po.setString("PREDICT_OFFLINE_DATE",voList.get(i).getPredictOfflineDate());	// 预计下线日期
					po.setString("PREDICT_STORAGE_DATE",voList.get(i).getPredictStorageDate());	// 预计入库日期
					po.setString("MODEL_CODE",voList.get(i).getModelCode());						// 车型
					po.setString("MODEL_YEAR",voList.get(i).getModelYear());						// 车型年份
					po.setString("COLOR_CODE",voList.get(i).getColorCode());						// 颜色
					po.setString("TRIM_CODE",voList.get(i).getTrimCode());						// 配饰
					po.setString("VIN",voList.get(i).getVin());									// 车架号
					po.setString("FACTORY_OPTIONS",voList.get(i).getFactoryOptions());			// 其他配置
					po.setString("STANDARD_OPTION",voList.get(i).getStandardOption());			// 标准配置
					po.setString("LOCAL_OPTION",voList.get(i).getLocalOption());					// 本地配置
					po.setString("SPECIAL_SERIES",voList.get(i).getSpecialSeries());				// Special Series
					po.setString("FACTORY_CODE",voList.get(i).getFactoryCode());					// 工厂
					po.setString("WAREHOUSE_CODE",voList.get(i).getWarehouseCode());				// 库存地点
					po.setString("REMARK",voList.get(i).getRemark());							// 备注
					po.setString("ENGINE_NO",voList.get(i).getEngineNo());						// 发动机号
					po.setString("QUALIFIED_NO",voList.get(i).getQualifiedNo());					// 合格证号
					po.setString("PRODUCT_DATE",voList.get(i).getProductDate());					// 生产日期
					po.setString("BLOCK_DATE",voList.get(i).getBlockDate());						// 冻结日期
					po.setString("BLOCK_REASON",voList.get(i).getBlockReason());					// 冻结原因
					po.setString("EXPECTED_UNBLOCK_DATE",voList.get(i).getExpectedUnblockDate());	// 预计解冻日期
					po.setString("ACTUAL_UNBLOCK_DATE",voList.get(i).getActualUnblockDate());		// 实际解冻日期	
					po.setString("COMPANY_CODE",voList.get(i).getCompanyCode());					// 公司代码
					po.setString("M_ONLINE_DATE",voList.get(i).getMOnlineDate());					// 总装上线日期	
					po.setString("M_OFFLINE_DATE",voList.get(i).getMOfflineDate());				// 总装下线日期	
					po.setString("M_QUALITY_DATE",voList.get(i).getMQualityDate());				// 质检完成日期	
					po.setString("M_STORAGE_DATE",voList.get(i).getMStorageDate());				// 入MJV销售库日期	
					po.setString("ROW_ID",voList.get(i).getRowId());								// ROW_ID
					po.setString("IS_RESULT",OemDictCodeConstants.IF_TYPE_NO.toString());						// 是否成功（否）
					po.setString("IS_MESSAGE",returnVo[1]);										// 错误校验内容
					po.setLong("CREATE_BY",OemDictCodeConstants.K4_S0001);									// 创建人ID
					po.setTimestamp("CREATE_DATE",new Date());										// 创建日期
					po.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_00);									// 逻辑删除
					po.saveIt();
					
					/*
					 * 返回错误信息
					 */
					returnVO retVo = new returnVO();
					retVo.setOutput(returnVo[0]);
					retVo.setMessage(returnVo[1]);
					retVoList.add(retVo);
					
					logger.info("==========S0001 返回不合格数据==========RowId==========" + returnVo[0]);
					
					logger.info("==========S0001 返回不合格数据==========Message==========" + returnVo[1]);
					
				}
			}

			dbService.endTxn(true);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new ServiceBizException("S0001业务处理异常！"+e);
		} finally {
			logger.info("==========S0001 is finish==========");
			Base.detach();
			dbService.clean();
		}
		
		return retVoList;

	}

	/**
	 * S0001数据校验逻辑
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private String[] S0001Check(S0001VO vo) throws Exception {
		
		logger.info("==========S0001 校验逻辑开始==========");
		
		String[] returnVo = new String[2];
		
		if (vo.getActionCode().equals("ZASS")) {	// 车辆节点：总装上线
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 预计下线日期非空校验
			if (CheckUtil.checkNull(vo.getPredictOfflineDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "预计下线日期为空";
				return returnVo;
			}
			
			// 预计下线日期长度校验
			if (vo.getPredictOfflineDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "预计下线日期长度格式与接口定义不一致(YYYYMMDD)：" + vo.getPredictOfflineDate();
				return returnVo;
			}
			
			// 预计入库日期如果不为空
			if (!CheckUtil.checkNull(vo.getPredictStorageDate())) {
				
				// 预计入库日期长度校验
				if (vo.getPredictStorageDate().length() != 8) {
					returnVo[0] = vo.getRowId();
					returnVo[1] = "预计入库日期长度格式与接口定义不一致(YYYYMMDD)：" + vo.getPredictStorageDate();
					return returnVo;
				}
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 工厂非空校验
			if (CheckUtil.checkNull(vo.getFactoryCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "工厂为空";
				return returnVo;
			}
			
			// 公司代码非空校验
			if (CheckUtil.checkNull(vo.getCompanyCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "公司代码为空";
				return returnVo;
			}
			
			// 总装上线日期非空校验
			if (CheckUtil.checkNull(vo.getMOnlineDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装上线日期为空";
				return returnVo;
			}
			
			// 总装上线日期长度校验
			if (vo.getMOnlineDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装上线日期长度与接口定义不一致：" + vo.getMOnlineDate();
				return returnVo;
			}
			
		} else if (vo.getActionCode().equals("ZVGO")) {	// 总装下线
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 工厂非空校验
			if (CheckUtil.checkNull(vo.getFactoryCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "工厂为空";
				return returnVo;
			}
			
			// 公司代码非空校验
			if (CheckUtil.checkNull(vo.getCompanyCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "公司代码为空";
				return returnVo;
			}
			
			// 总装上线日期非空校验
			if (CheckUtil.checkNull(vo.getMOnlineDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装上线日期为空";
				return returnVo;
			}
			
			// 总装上线日期长度校验
			if (vo.getMOnlineDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装上线日期长度与接口定义不一致：" + vo.getMOnlineDate();
				return returnVo;
			}
			
			// 总装下线日期非空校验
			if (CheckUtil.checkNull(vo.getMOfflineDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装下线日期为空";
				return returnVo;
			}
			
			// 总装下线日期长度校验
			if (vo.getMOfflineDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装下线日期长度与接口定义不一致：" + vo.getMOfflineDate();
				return returnVo;
			}
			
		} else if (vo.getActionCode().equals("ZVQP")) {	// 质检完成
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 工厂非空校验
			if (CheckUtil.checkNull(vo.getFactoryCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "工厂为空";
				return returnVo;
			}
			
			// 公司代码非空校验
			if (CheckUtil.checkNull(vo.getCompanyCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "公司代码为空";
				return returnVo;
			}
			
			// 总装上线日期非空校验
			if (CheckUtil.checkNull(vo.getMOnlineDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装上线日期为空";
				return returnVo;
			}
			
			// 总装上线日期长度校验
			if (vo.getMOnlineDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装上线日期长度与接口定义不一致：" + vo.getMOnlineDate();
				return returnVo;
			}
			
			// 总装下线日期非空校验
			if (CheckUtil.checkNull(vo.getMOfflineDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装下线日期为空";
				return returnVo;
			}
			
			// 总装下线日期长度校验
			if (vo.getMOfflineDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装下线日期长度与接口定义不一致：" + vo.getMOfflineDate();
				return returnVo;
			}
			
			// 质检完成日期非空校验
			if (CheckUtil.checkNull(vo.getMQualityDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "质检完成日期为空";
				return returnVo;
			}
			
			// 质检完成日期长度校验
			if (vo.getMQualityDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "质检完成日期长度与接口定义不一致：" + vo.getMQualityDate();
				return returnVo;
			}
			
		} else if (vo.getActionCode().equals("ZVGR")) {	// 工厂在库
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 工厂非空校验
			if (CheckUtil.checkNull(vo.getFactoryCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "工厂为空";
				return returnVo;
			}
			
			// 库存地点非空校验
			/*if (CheckUtil.checkNull(vo.getWarehouseCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "库存地点为空";
				return returnVo;
			}*/
			
			// 发动机号非空校验
			if (CheckUtil.checkNull(vo.getEngineNo())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "发动机号为空";
				return returnVo;
			}
			
			// 合格证号非空校验
			if (CheckUtil.checkNull(vo.getQualifiedNo())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "合格证号为空";
				return returnVo;
			}
			
			// 生产日期非空校验
			if (CheckUtil.checkNull(vo.getProductDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "生产日期为空";
				return returnVo;
			}
			
			// 生产日期长度校验
			if (vo.getProductDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "生产日期长度与接口定义不一致：" + vo.getProductDate();
				return returnVo;
			}
			
			// 公司代码非空校验
			if (CheckUtil.checkNull(vo.getCompanyCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "公司代码为空";
				return returnVo;
			}
			
			// 总装上线日期非空校验
			if (CheckUtil.checkNull(vo.getMOnlineDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装上线日期为空";
				return returnVo;
			}
			
			// 总装上线日期长度校验
			if (vo.getMOnlineDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装上线日期长度与接口定义不一致：" + vo.getMOnlineDate();
				return returnVo;
			}
			
			// 总装下线日期非空校验
			if (CheckUtil.checkNull(vo.getMOfflineDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装下线日期为空";
				return returnVo;
			}
			
			// 总装下线日期长度校验
			if (vo.getMOfflineDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "总装下线日期长度与接口定义不一致：" + vo.getMOfflineDate();
				return returnVo;
			}
			
			// 质检完成日期非空校验
			if (CheckUtil.checkNull(vo.getMQualityDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "质检完成日期为空";
				return returnVo;
			}
			
			// 质检完成日期长度校验
			if (vo.getMQualityDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "质检完成日期长度与接口定义不一致：" + vo.getMQualityDate();
				return returnVo;
			}
			
			// 入MJV销售库日期非空校验
			if (CheckUtil.checkNull(vo.getMStorageDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "入MJV销售库日期为空";
				return returnVo;
			}
			
			// 入MJV销售库日期长度校验
			if (vo.getMStorageDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "入MJV销售库日期长度与接口定义不一致：" + vo.getMStorageDate();
				return returnVo;
			}
			
		} else if (vo.getActionCode().equals("ZIKO")) {	// 内部结算失败
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 主要状态是否有效
//			Map<String, Object> mainStatusMap = dao.mainStatusQuery(vo.getMainStatus());
//			if (null == mainStatusMap) {
//				returnVo[0] = vo.getRowId();
//				returnVo[1] = "主要状态无效";
//				return returnVo;
//			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
		} else if (vo.getActionCode().equals("ZINV")) {	// 内部结算成功
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 主要状态是否有效
//			Map<String, Object> mainStatusMap = dao.mainStatusQuery(vo.getMainStatus());
//			if (null == mainStatusMap) {
//				returnVo[0] = vo.getRowId();
//				returnVo[1] = "主要状态无效";
//				return returnVo;
//			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
		} else if (vo.getActionCode().equals("ZBB1")) {	// 工厂质量扣留
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 工厂非空校验
			if (CheckUtil.checkNull(vo.getFactoryCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "工厂为空";
				return returnVo;
			}
			
			// 库存地点非空校验
			/*if (CheckUtil.checkNull(vo.getWarehouseCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "库存地点为空";
				return returnVo;
			}*/
			
			// 冻结日期非空校验
			if (CheckUtil.checkNull(vo.getBlockDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "冻结日期为空";
				return returnVo;
			}
			
			// 冻结日期长度校验
			if (vo.getBlockDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "冻结日期长度与接口定义不一致(YYYYMMDD)：" + vo.getBlockDate();
				return returnVo;
			}
			
			// 预计解冻日期如果不为空
			if (!CheckUtil.checkNull(vo.getExpectedUnblockDate())) {

				// 预计解冻日期长度校验
				if (vo.getExpectedUnblockDate().length() != 8) {
					returnVo[0] = vo.getRowId();
					returnVo[1] = "预计解冻日期长度格式与接口定义不一致(YYYYMMDD)：" + vo.getExpectedUnblockDate();
					return returnVo;
				}
				
				// 预计解冻日期有效性校验
				if (DateUtil.yyyyMMdd2DateCheck(vo.getExpectedUnblockDate())) {
					returnVo[0] = vo.getRowId();
					returnVo[1] = "预计解冻日期内容错误，无法转换为日期格式：" + vo.getExpectedUnblockDate();
					return returnVo;
				}
			}
			
		} else if (vo.getActionCode().equals("ZBBR")) {	// 工厂质量扣留解除
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 工厂非空校验
			if (CheckUtil.checkNull(vo.getFactoryCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "工厂为空";
				return returnVo;
			}
			
			// 库存地点非空校验
			/*if (CheckUtil.checkNull(vo.getWarehouseCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "库存地点为空";
				return returnVo;
			}*/
			
			// 实际解冻日期非空校验
			if (CheckUtil.checkNull(vo.getActualUnblockDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "实际解冻日期为空";
				return returnVo;
			}
			
			// 实际解冻日期长度校验
			if (vo.getActualUnblockDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "实际解冻日期长度与接口定义不一致(YYYYMMDD)：" + vo.getActualUnblockDate();
				return returnVo;
			}
			
			// 实际解冻日期有效性校验
			if (DateUtil.yyyyMMdd2DateCheck(vo.getActualUnblockDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "实际解冻日期内容错误，无法转换为日期格式：" + vo.getActualUnblockDate();
				return returnVo;
			}
			
		} else if (vo.getActionCode().equals("ZMBL")) {	// 销售公司质量扣留
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 工厂非空校验
			if (CheckUtil.checkNull(vo.getFactoryCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "工厂为空";
				return returnVo;
			}
			
			// 库存地点非空校验
			/*if (CheckUtil.checkNull(vo.getWarehouseCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "库存地点为空";
				return returnVo;
			}*/
			
			// 冻结日期非空校验
			if (CheckUtil.checkNull(vo.getBlockDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "冻结日期为空";
				return returnVo;
			}
			
			// 冻结日期长度校验
			if (vo.getBlockDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "冻结日期长度与接口定义不一致(YYYYMMDD)：" + vo.getBlockDate();
				return returnVo;
			}
			
			// 预计解冻日期如果不为空
			if (!CheckUtil.checkNull(vo.getExpectedUnblockDate())) {

				// 预计解冻日期长度校验
				if (vo.getExpectedUnblockDate().length() != 8) {
					returnVo[0] = vo.getRowId();
					returnVo[1] = "预计解冻日期长度格式与接口定义不一致(YYYYMMDD)：" + vo.getExpectedUnblockDate();
					return returnVo;
				}
				
				// 预计解冻日期有效性校验
				if (DateUtil.yyyyMMdd2DateCheck(vo.getExpectedUnblockDate())) {
					returnVo[0] = vo.getRowId();
					returnVo[1] = "预计解冻日期内容错误，无法转换为日期格式：" + vo.getExpectedUnblockDate();
					return returnVo;
				}
			}
			
		} else if (vo.getActionCode().equals("ZMUB")) {	// 销售公司质量扣留解除
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 工厂非空校验
			if (CheckUtil.checkNull(vo.getFactoryCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "工厂为空";
				return returnVo;
			}
			
			// 库存地点非空校验
			/*if (CheckUtil.checkNull(vo.getWarehouseCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "库存地点为空";
				return returnVo;
			}*/
			
			// 实际解冻日期非空校验
			if (CheckUtil.checkNull(vo.getActualUnblockDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "实际解冻日期为空";
				return returnVo;
			}
			
			// 实际解冻日期长度校验
			if (vo.getActualUnblockDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "实际解冻日期长度与接口定义不一致(YYYYMMDD)：" + vo.getActualUnblockDate();
				return returnVo;
			}
			
			// 实际解冻日期有效性校验
			if (DateUtil.yyyyMMdd2DateCheck(vo.getActualUnblockDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "实际解冻日期内容错误，无法转换为日期格式：" + vo.getActualUnblockDate();
				return returnVo;
			}
			
		} else if (vo.getActionCode().equals("ZDF1")) {	// 延迟发运
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 冻结日期非空校验
			if (CheckUtil.checkNull(vo.getBlockDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "冻结日期为空";
				return returnVo;
			}
			
			// 冻结日期长度校验
			if (vo.getBlockDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "冻结日期长度与接口定义不一致(YYYYMMDD)：" + vo.getBlockDate();
				return returnVo;
			}
			
			// 预计解冻日期如果不为空
			if (!CheckUtil.checkNull(vo.getExpectedUnblockDate())) {

				// 预计解冻日期长度校验
				if (vo.getExpectedUnblockDate().length() != 8) {
					returnVo[0] = vo.getRowId();
					returnVo[1] = "预计解冻日期长度格式与接口定义不一致(YYYYMMDD)：" + vo.getExpectedUnblockDate();
					return returnVo;
				}
				
				// 预计解冻日期有效性校验
				if (DateUtil.yyyyMMdd2DateCheck(vo.getExpectedUnblockDate())) {
					returnVo[0] = vo.getRowId();
					returnVo[1] = "预计解冻日期内容错误，无法转换为日期格式：" + vo.getExpectedUnblockDate();
					return returnVo;
				}
			}
			
		} else if (vo.getActionCode().equals("ZDP1")) {	// 取消延迟发运
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 车型代码非空校验
			if (CheckUtil.checkNull(vo.getModelCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "车型代码为空";
				return returnVo;
			}
			
			// 年款非空校验
			if (CheckUtil.checkNull(vo.getModelYear())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款为空";
				return returnVo;
			}
			
			// 年款长度校验
			if (vo.getModelYear().length() != 4) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "年款长度与接口定义不一致：" + vo.getModelYear();
				return returnVo;
			}
			
			// 颜色代码非空校验
			if (CheckUtil.checkNull(vo.getColorCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "颜色代码为空";
				return returnVo;
			}
			
			// 内饰代码非空校验
			if (CheckUtil.checkNull(vo.getTrimCode())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "内饰代码为空";
				return returnVo;
			}
			
			// 车型、年款、颜色、内饰是否为有效物料数据
			if (doMaterialCheck(vo.getModelCode(), vo.getModelYear(), vo.getColorCode(), vo.getTrimCode()) 
					== OemDictCodeConstants.IF_TYPE_NO.intValue()) {
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "无效物料 车型：" + vo.getModelCode() + 
									"，年款：" + vo.getModelYear() + 
									"，颜色：" + vo.getColorCode() + 
									"，内饰：" + vo.getTrimCode();
				return returnVo;
			}
			
			// 实际解冻日期非空校验
			if (CheckUtil.checkNull(vo.getActualUnblockDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "实际解冻日期为空";
				return returnVo;
			}
			
			// 实际解冻日期长度校验
			if (vo.getActualUnblockDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "实际解冻日期长度与接口定义不一致(YYYYMMDD)：" + vo.getActualUnblockDate();
				return returnVo;
			}
			
			// 实际解冻日期有效性校验
			if (DateUtil.yyyyMMdd2DateCheck(vo.getActualUnblockDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "实际解冻日期内容错误，无法转换为日期格式：" + vo.getActualUnblockDate();
				return returnVo;
			}
			
		} else if (vo.getActionCode().equals("ZRGZ")) {	// 取消零售上报
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
			// 主要状态非空校验
			if (CheckUtil.checkNull(vo.getMainStatus())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "主要状态为空";
				return returnVo;
			}
			
			// 主要状态是否有效
//			Map<String, Object> mainStatusMap = dao.mainStatusQuery(vo.getMainStatus());
//			if (null == mainStatusMap) {
//				returnVo[0] = vo.getRowId();
//				returnVo[1] = "主要状态无效";
//				return returnVo;
//			}

		} else if (vo.getActionCode().equals("VDEL")) {	// 删除车辆
			
			// ROW_ID非空校验
			if (CheckUtil.checkNull(vo.getRowId())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "ROW_ID 为空";
				return returnVo;
			}
			
			// VIN号非空校验
			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号为空";
				return returnVo;
			}
			
			// VIN号长度校验
			if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "VIN号长度与接口定义不一致：" + vo.getVin();
				return returnVo;
			}
			
			// 交易日期非空校验
			if (CheckUtil.checkNull(vo.getActionDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易日期长度校验
			if (vo.getActionDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期长度与接口定义不一致：" + vo.getActionDate();
				return returnVo;
			}
			
			// 交易时间非空校验
			if (CheckUtil.checkNull(vo.getActionTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易日期为空";
				return returnVo;
			}
			
			// 交易时间长度校验
			if (vo.getActionTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "交易时间长度与接口定义不一致：" + vo.getActionTime();
				return returnVo;
			}
			
		} else if (vo.getActionCode().length() != 4) {
			
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交易代码长度与接口定义不一致：" + vo.getActionCode();
			return returnVo;
			
		} else if (null == vo.getActionCode() || vo.getActionCode().equals("")) {
			
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交易代码为空：" + vo.getActionCode();
			return returnVo;
			
		} else {
			
			returnVo[0] = vo.getRowId();
			returnVo[1] = "交易代码无效：" + vo.getActionCode();
			return returnVo;
			
		}

		logger.info("==========S0001 校验逻辑结束==========");
		return null;
	}

	/*
	 * S0001 数据业务处理逻辑
	 */
	@SuppressWarnings("rawtypes")
	public void k4BusinessProcess(S0001VO vo) throws Exception {
		
		logger.info("==========S0001 业务处理逻辑开始==========");
		
		/*
		 * 判断 VIN 是否存在：
		 *		如果不存在，则创建车辆信息和车辆状态变更历史信息；
		 * 		如果存在，则检查接口的 ACTION_DATE + ACTION_TIME 与系统已有的时间点比较
		 *		若接口 ACTION_DATE + ACTION_TIME 早于 DCS NODE_DATE，则仅新增车辆状态变更历史
		 * 		否则更新车辆主数据表的车辆节点状态、车辆生命周期及时间，和新增车辆状态变更历史
		 */
		
		// K4 车辆接口表数据插入
		TiK4VehiclePO po = new TiK4VehiclePO();
		po.setString("ACTION_CODE", vo.getActionCode());					// 交易代码
		po.setString("ACTION_DATE", vo.getActionDate());					// 交易日期
		po.setString("ACTION_TIME", vo.getActionTime());					// 交易时间
		po.setString("MAIN_STATUS", vo.getMainStatus());					// 主要状态
		po.setString("SECOND_STATUS", vo.getSecondStatus());				// 第二状态
		po.setString("PREDICT_OFFLINE_DATE", vo.getPredictOfflineDate());	// 预计下线日期
		po.setString("PREDICT_STORAGE_DATE", vo.getPredictStorageDate());	// 预计入库日期
		po.setString("MODEL_CODE", vo.getModelCode());						// 车型
		po.setString("MODEL_YEAR", vo.getModelYear());						// 车型年份
		po.setString("COLOR_CODE", vo.getColorCode());						// 颜色
		po.setString("TRIM_CODE", vo.getTrimCode());						// 配饰
		po.setString("VIN", vo.getVin());									// 车架号
		po.setString("FACTORY_OPTIONS", vo.getFactoryOptions());			// 其他配置
		po.setString("STANDARD_OPTION", vo.getStandardOption());			// 标准配置
		po.setString("LOCAL_OPTION", vo.getLocalOption());					// 本地配置
		po.setString("SPECIAL_SERIES", vo.getSpecialSeries());				// Special Series
		po.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
		po.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
		po.setString("REMARK", vo.getRemark());								// 备注
		po.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
		po.setString("QUALIFIED_NO", vo.getQualifiedNo());					// 合格证号
		po.setString("PRODUCT_DATE", vo.getProductDate());					// 生产日期
		po.setString("BLOCK_DATE", vo.getBlockDate());						// 冻结日期
		po.setString("BLOCK_REASON", vo.getBlockReason());					// 冻结原因
		po.setString("EXPECTED_UNBLOCK_DATE", vo.getExpectedUnblockDate());	// 预计解冻日期
		po.setString("ACTUAL_UNBLOCK_DATE", vo.getActualUnblockDate());		// 实际解冻日期	
		po.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
		po.setString("M_ONLINE_DATE", vo.getMOnlineDate());					// 总装上线日期	
		po.setString("M_OFFLINE_DATE", vo.getMOfflineDate());				// 总装下线日期	
		po.setString("M_QUALITY_DATE", vo.getMQualityDate());				// 质检完成日期	
		po.setString("M_STORAGE_DATE", vo.getMStorageDate());				// 入MJV销售库日期	
		po.setString("ROW_ID", vo.getRowId());								// ROW_ID
		po.setString("IS_RESULT", OemDictCodeConstants.IF_TYPE_YES.toString());		// 是否成功（是）
		po.setString("IS_MESSAGE", "校验通过");								// 消息
		po.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);						// 创建人ID
		po.setDate("CREATE_DATE", new Date());							// 创建日期
		po.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);						// 逻辑删除
		po.saveIt();
		
		if (vo.getActionCode().equals("ZASS")) {	// 车辆节点：总装上线
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期(SAP_DATE)
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 总装上线日期
			Date mOnlineDate = DateUtil.yyyyMMdd2Date(vo.getMOnlineDate());	
			Timestamp mOnlineTimestamp = new Timestamp(mOnlineDate.getTime());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);				// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));		// 预计下线日期
				vehiclePo.setString("PREDICT_STORAGE_DATE", vo.getPredictStorageDate());	// 预计入库日期
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
						+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
						+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
						+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));
				if(tmpList != null && tmpList.size()>0){
				   vehiclePo.setLong("WHOLESALE_PRICE", tmpList.get(0).getLong("WHOLESALE_PRICE")); //批售价格
				}
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());							// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_01);// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", mOnlineDate);								// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_01);		// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());								// 备注
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);				// 创建人ID
				vehiclePo.setDate("CREATE_DATE", new Date());								// 创建日期
				vehiclePo.setInteger("VER", 0);												// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);											// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);				// 逻辑删除
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());						// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
				vehiclePo.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZASS_DATE", mOnlineTimestamp);	// 总装上线日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
				params.put("changeName", "总装上线");
				params.put("changeDate", mOnlineTimestamp);
				params.put("changeDesc", "总装上线");
				params.put("mainStatusCode", vo.getModelCode());
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			} else {
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {
					
					/*
					 * 情况：交易日期大于车辆节点日期
					 * 行为：更新车辆主数据表、更新车辆节点日期记录表、新增车辆节点变更记录表数据
					 */
					
					// 更新车辆主数据表
					TmVehiclePO setVehiclePo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					setVehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));	// 预计下线日期
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
																					+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
																					+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
																					+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setVehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));// 物料ID
//					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));
					if(tmpList != null && tmpList.size()>0){
						setVehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE")); //批售价格
					}

					setVehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_01);	// 车辆节点日期
					setVehiclePo.setDate("NODE_DATE", mOnlineDate);							// 节点更新日期
					setVehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_01);			// 车辆生命周期
					setVehiclePo.setString("REMARK", vo.getRemark());						// 车辆备注信息
					setVehiclePo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehiclePo.setDate("UPDATE_DATE", new Date());
					setVehiclePo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);
					setVehicleNodeHistoryPo.setTimestamp("ZASS_DATE", mOnlineTimestamp);	// 总装上线日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
					params.put("changeName", "总装上线");
					params.put("changeDate", mOnlineTimestamp);
					params.put("changeDesc", "总装上线");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				} else {
					
					/*
					 * 情况：交易日期小于车辆节点日期
					 * 行为：更新车辆节点日期记录表、新增车辆节点变更记录表数据
					 */
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);
					setVehicleNodeHistoryPo.setTimestamp("ZASS_DATE", mOnlineTimestamp);	// 总装上线日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
					params.put("changeName", "总装上线");
					params.put("changeDate", mOnlineTimestamp);
					params.put("changeDesc", "总装上线");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				}
				
			}
			 
		} else if (vo.getActionCode().equals("ZVGO")) {	// 车辆节点：总装下线
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期(SAP_DATE)
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 总装上线日期
			Date mOnlineDate = DateUtil.yyyyMMdd2Date(vo.getMOnlineDate());	
			Timestamp mOnlineTimestamp = new Timestamp(mOnlineDate.getTime());
			
			// 总装下线日期
			Date mOfflineDate = DateUtil.yyyyMMdd2Date(vo.getMOfflineDate());	
			Timestamp mOfflineTimestamp = new Timestamp(mOfflineDate.getTime());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
				vehiclePo.setString("QUALIFIED_NO", vo.getQualifiedNo());					// 合格证号
				vehiclePo.setDate("PREDICT_ODDLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));		// 预计下线日期
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
																				+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
																				+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
																				+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				 
				vehiclePo.setDate("OFFLINE_DATE", mOfflineDate);							// 下线日期（总装下线日期）
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_02);			// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", mOfflineDate);							// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_01);					// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());							// 备注
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);						// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());							// 创建日期
				vehiclePo.setInteger("VER", 0);									// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);								// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);							// 逻辑删除
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());						// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
				vehiclePo.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZASS_DATE", mOnlineTimestamp);	// 总装上线日期
				vehicleNodeHistory.setTimestamp("ZVGO_DATE", mOfflineTimestamp);	// 总装下线日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 补充车辆节点变更记录表数据
				Map<String, Object> params1 = new HashMap<String, Object>();
				params1.put("vin", vo.getVin());
				params1.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
				params1.put("changeName", "总装上线");
				params1.put("changeDate", mOnlineTimestamp);
				params1.put("changeDesc", "总装上线");
				params1.put("mainStatusCode", vo.getMainStatus());
				params1.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params1);
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params2 = new HashMap<String, Object>();
				params2.put("vin", vo.getVin());
				params2.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_02);
				params2.put("changeName", "总装下线");
				params2.put("changeDate", mOfflineTimestamp);
				params2.put("changeDesc", "总装下线，入在制库，整车下生产线");
				params2.put("mainStatusCode", vo.getMainStatus());
				params2.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params2);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {
					
					/*
					 * 情况：交易日期大于车辆节点日期
					 * 行为：更新车辆主数据表、更新车辆节点日期记录表、新增车辆节点变更记录表数据
					 */
					
					// 更新车辆主数据表
					TmVehiclePO setVehiclePo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
																					+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
																					+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
																					+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setVehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));
					if(tmpList != null && tmpList.size()>0){
						setVehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					setVehiclePo.setString("ENGINE_NO", vo.getEngineNo());					// 发动机号
					setVehiclePo.setString("QUALIFIED_NO", vo.getQualifiedNo());			// 合格证号
					setVehiclePo.setDate("OFFLINE_DATE", mOfflineDate);					// 下线日期（总装下线日期）
					setVehiclePo.setLong("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_02);	// 车辆节点日期
					setVehiclePo.setDate("NODE_DATE", mOfflineDate);						// 节点更新日期
					setVehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_01);			// 车辆生命周期
					setVehiclePo.setString("REMARK", vo.getRemark());						// 车辆备注信息
					setVehiclePo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehiclePo.setDate("UPDATE_DATE", new Date());
					setVehiclePo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);
					setVehicleNodeHistoryPo.setTimestamp("ZASS_DATE", mOnlineTimestamp);	// 总装上线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVGO_DATE", mOfflineTimestamp);	// 总装下线日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params1 = new HashMap<String, Object>();
					params1.put("vin", vo.getVin());
					params1.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
					params1.put("changeName", "总装上线");
					params1.put("changeDate", mOnlineTimestamp);
					params1.put("changeDesc", "总装上线");
					params1.put("mainStatusCode", vo.getMainStatus());
					params1.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params1);
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("vin", vo.getVin());
					params2.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_02);
					params2.put("changeName", "总装下线");
					params2.put("changeDate", mOfflineTimestamp);
					params2.put("changeDesc", "总装下线，入在制库，整车下生产线");
					params2.put("mainStatusCode", vo.getMainStatus());
					params2.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params2);
					
				} else {
					
					/*
					 * 情况：交易日期小于车辆节点日期
					 * 行为：更新车辆节点日期记录表、新增车辆节点变更记录表数据
					 */
					
					// 更新车辆主数据表
					TmVehiclePO setVehiclePo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					setVehiclePo.setString("ENGINE_NO", vo.getEngineNo());					// 发动机号
					setVehiclePo.setString("QUALIFIED_NO", vo.getQualifiedNo());			// 合格证号
					setVehiclePo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehiclePo.setDate("UPDATE_DATE", new Date());
					setVehiclePo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);
					setVehicleNodeHistoryPo.setTimestamp("ZASS_DATE", mOnlineTimestamp);	// 总装上线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVGO_DATE", mOfflineTimestamp);	// 总装下线日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params1 = new HashMap<String, Object>();
					params1.put("vin", vo.getVin());
					params1.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
					params1.put("changeName", "总装上线");
					params1.put("changeDate", mOnlineTimestamp);
					params1.put("changeDesc", "总装上线");
					params1.put("mainStatusCode", vo.getMainStatus());
					params1.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params1);
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("vin", vo.getVin());
					params2.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_02);
					params2.put("changeName", "总装下线");
					params2.put("changeDate", mOfflineTimestamp);
					params2.put("changeDesc", "总装下线，入在制库，整车下生产线");
					params2.put("mainStatusCode", vo.getMainStatus());
					params2.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params2);
					
				}
				
			}
			
		} else if (vo.getActionCode().equals("ZVQP")) {	// 车辆节点：质检完成
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期(SAP_DATE)
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 总装上线日期
			Date mOnlineDate = DateUtil.yyyyMMdd2Date(vo.getMOnlineDate());	
			Timestamp mOnlineTimestamp = new Timestamp(mOnlineDate.getTime());
			
			// 总装下线日期
			Date mOfflineDate = DateUtil.yyyyMMdd2Date(vo.getMOfflineDate());	
			Timestamp mOfflineTimestamp = new Timestamp(mOfflineDate.getTime());
			
			// 质检完成日期
			Date mQualityDate = DateUtil.yyyyMMdd2Date(vo.getMQualityDate());	
			Timestamp mQualityTimestamp = new Timestamp(mQualityDate.getTime());
			
			// 质检完成日期
			Date mStorageDate = DateUtil.yyyyMMdd2Date(vo.getMStorageDate());	
			Timestamp mStorageTimestamp = new Timestamp(mStorageDate.getTime());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));		// 预计下线日期
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
										+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
										+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
										+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_03);			// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", mQualityDate);							// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_01);					// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());							// 备注
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);						// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());							// 创建日期
				vehiclePo.setInteger("VER", 0);											// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);											// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);							// 逻辑删除
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());						// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
				vehiclePo.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("CEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZASS_DATE", mOnlineTimestamp);		// 总装上线日期
				vehicleNodeHistory.setTimestamp("ZVGO_DATE", mOfflineTimestamp);	// 总装下线日期
				vehicleNodeHistory.setTimestamp("ZVQP_DATE", mQualityTimestamp);	// 质检完成日期
				vehicleNodeHistory.setTimestamp("Zvgr_Date", mStorageTimestamp);	// 工厂在库日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 补充车辆节点变更记录表数据
				Map<String, Object> params1 = new HashMap<String, Object>();
				params1.put("vin", vo.getVin());
				params1.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
				params1.put("changeName", "总装上线");
				params1.put("changeDate", mOnlineTimestamp);
				params1.put("changeDesc", "总装上线");
				params1.put("mainStatusCode", vo.getMainStatus());
				params1.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params1);
				
				// 补充车辆节点变更记录表数据
				Map<String, Object> params2 = new HashMap<String, Object>();
				params2.put("vin", vo.getVin());
				params2.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_02);
				params2.put("changeName", "总装下线");
				params2.put("changeDate", mOfflineTimestamp);
				params2.put("changeDesc", "总装下线，入在制库，整车下生产线");
				params2.put("mainStatusCode", vo.getMainStatus());
				params2.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params2);
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params3 = new HashMap<String, Object>();
				params3.put("vin", vo.getVin());
				params3.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_03);
				params3.put("changeName", "质检完成");
				params3.put("changeDate", mQualityTimestamp);
				params3.put("changeDesc", "交车，完成质量检查");
				params3.put("mainStatusCode", vo.getMainStatus());
				params3.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params3);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {
					
					/*
					 * 情况：交易日期大于车辆节点日期
					 * 行为：更新车辆主数据表、更新车辆节点日期记录表、新增车辆节点变更记录表数据
					 */
					
					// 更新车辆主数据表
					TmVehiclePO setVehiclePo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
												+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
												+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
												+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setVehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));
					if(tmpList != null && tmpList.size()>0){
						setVehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					setVehiclePo.setLong("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_03);	// 车辆节点日期
					setVehiclePo.setDate("NODE_DATE", mQualityDate);						// 节点更新日期
					setVehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_01);			// 车辆生命周期
					setVehiclePo.setString("REMARK", vo.getRemark());						// 车辆备注信息
					setVehiclePo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehiclePo.setDate("UPDATE_DATE", new Date());
					setVehiclePo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZASS_DATE", mOnlineTimestamp);	// 总装上线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVGO_DATE", mOfflineTimestamp);	// 总装下线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVQP_DATE", mQualityTimestamp);	// 质检完成日期
					setVehicleNodeHistoryPo.setTimestamp("Zvgr_Date", mStorageTimestamp);	// 工厂在库日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("CREATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params1 = new HashMap<String, Object>();
					params1.put("vin", vo.getVin());
					params1.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
					params1.put("changeName", "总装上线");
					params1.put("changeDate", mOnlineTimestamp);
					params1.put("changeDesc", "总装上线");
					params1.put("mainStatusCode", vo.getMainStatus());
					params1.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params1);
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("vin", vo.getVin());
					params2.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_02);
					params2.put("changeName", "总装下线");
					params2.put("changeDate", mOfflineTimestamp);
					params2.put("changeDesc", "总装下线，入在制库，整车下生产线");
					params2.put("mainStatusCode", vo.getMainStatus());
					params2.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params2);
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params3 = new HashMap<String, Object>();
					params3.put("vin", vo.getVin());
					params3.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_03);
					params3.put("changeName", "质检完成");
					params3.put("changeDate", mQualityTimestamp);
					params3.put("changeDesc", "交车，完成质量检查");
					params3.put("mainStatusCode", vo.getMainStatus());
					params3.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params3);

				} else {
					
					/*
					 * 情况：交易日期小于车辆节点日期
					 * 行为：更新车辆节点日期记录表、新增车辆节点变更记录表数据
					 */
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ?", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZASS_DATE", mOnlineTimestamp);	// 总装上线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVGO_DATE", mOfflineTimestamp);	// 总装下线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVQP_DATE", mQualityTimestamp);	// 质检完成日期
					setVehicleNodeHistoryPo.setTimestamp("Zvgr_Date", mStorageTimestamp);	// 工厂在库日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("CREATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params1 = new HashMap<String, Object>();
					params1.put("vin", vo.getVin());
					params1.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
					params1.put("changeName", "总装上线");
					params1.put("changeDate", mOnlineTimestamp);
					params1.put("changeDesc", "总装上线");
					params1.put("mainStatusCode", vo.getMainStatus());
					params1.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params1);
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("vin", vo.getVin());
					params2.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_02);
					params2.put("changeName", "总装下线");
					params2.put("changeDate", mOfflineTimestamp);
					params2.put("changeDesc", "总装下线，入在制库，整车下生产线");
					params2.put("mainStatusCode", vo.getMainStatus());
					params2.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params2);
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params3 = new HashMap<String, Object>();
					params3.put("vin", vo.getVin());
					params3.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_03);
					params3.put("changeName", "质检完成");
					params3.put("changeDate", mQualityTimestamp);
					params3.put("changeDesc", "交车，完成质量检查");
					params3.put("mainStatusCode", vo.getMainStatus());
					params3.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params3);
					
				}

			}
			
		} else if (vo.getActionCode().equals("ZVGR")) {	// 车辆节点：工厂在库
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期(SAP_DATE)
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 总装上线日期
			Date mOnlineDate = DateUtil.yyyyMMdd2Date(vo.getMOnlineDate());	
			Timestamp mOnlineTimestamp = new Timestamp(mOnlineDate.getTime());
			
			// 总装下线日期
			Date mOfflineDate = DateUtil.yyyyMMdd2Date(vo.getMOfflineDate());	
			Timestamp mOfflineTimestamp = new Timestamp(mOfflineDate.getTime());
			
			// 质检完成日期
			Date mQualityDate = DateUtil.yyyyMMdd2Date(vo.getMQualityDate());	
			Timestamp mQualityTimestamp = new Timestamp(mQualityDate.getTime());
			
			// 入MJV销售库日期
			Date mStorageDate = DateUtil.yyyyMMdd2Date(vo.getMStorageDate());
			Timestamp mStorageTimestamp = new Timestamp(mStorageDate.getTime());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				Date predictOfflineDate = DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate());
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", predictOfflineDate);			// 预计下线日期
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+") VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
										+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
										+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
										+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
//				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
				
				// update_date 20160112 by maxiang begin..
				vehiclePo.setString("QUALIFIED_NO", vo.getQualifiedNo());					// 合格证号
				// update_date 20160112 by maxiang end..
				
				if (!vo.getProductDate().equals("00000000")) {
					Date productDate = DateUtil.yyyyMMdd2Date(vo.getProductDate());
					vehiclePo.setDate("PRODUCT_DATE", productDate);							// 生产日期
				}
				
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_04);			// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", mStorageDate);							// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);					// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());							// 备注
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);						// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());							// 创建日期
				vehiclePo.setInteger("VER", 0);											// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);											// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);							// 逻辑删除
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());						// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
				vehiclePo.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZASS_DATE", mOnlineTimestamp);	// 总装上线日期
				vehicleNodeHistory.setTimestamp("ZVGO_DATE", mOfflineTimestamp);	// 总装下线日期
				vehicleNodeHistory.setTimestamp("ZVQP_DATE", mQualityTimestamp);	// 质检完成日期
				vehicleNodeHistory.setTimestamp("ZVGR_DATE", mStorageTimestamp);	// 工厂在库日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 补充车辆节点变更记录表数据
				Map<String, Object> params1 = new HashMap<String, Object>();
				params1.put("vin", vo.getVin());
				params1.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
				params1.put("changeName", "总装上线");
				params1.put("changeDate", mOnlineTimestamp);
				params1.put("changeDesc", "总装上线");
				params1.put("mainStatusCode", vo.getMainStatus());
				params1.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params1);
				
				// 补充车辆节点变更记录表数据
				Map<String, Object> params2 = new HashMap<String, Object>();
				params2.put("vin", vo.getVin());
				params2.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_02);
				params2.put("changeName", "总装下线");
				params2.put("changeDate", mOfflineTimestamp);
				params2.put("changeDesc", "总装下线，入在制库，整车下生产线");
				params2.put("mainStatusCode", vo.getMainStatus());
				params2.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params2);
				
				// 补充车辆节点变更记录表数据
				Map<String, Object> params3 = new HashMap<String, Object>();
				params3.put("vin", vo.getVin());
				params3.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_03);
				params3.put("changeName", "质检完成");
				params3.put("changeDate", mQualityTimestamp);
				params3.put("changeDesc", "交车，完成质量检查");
				params3.put("mainStatusCode", vo.getMainStatus());
				params3.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params3);
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params4 = new HashMap<String, Object>();
				params4.put("vin", vo.getVin());
				params4.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_04);
				params4.put("changeName", "工厂在库");
				params4.put("changeDate", mStorageTimestamp);
				params4.put("changeDesc", "入成品库，入销售库");
				params4.put("mainStatusCode", vo.getMainStatus());
				params4.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params4);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {
					
					/*
					 * 情况：交易日期大于车辆节点日期
					 * 行为：更新车辆主数据表、更新车辆节点日期记录表、新增车辆节点变更记录表数据
					 */
					
					// 更新车辆主数据表
					TmVehiclePO setVehiclePo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
												+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
												+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
												+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setVehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					if(tmpList != null && tmpList.size()>0){
						setVehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					setVehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());			// 工厂代码
					setVehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());		// 仓库代码
					setVehiclePo.setString("ENGINE_NO", vo.getEngineNo());					// 发动机号
					// update_date 20160112 by maxiang begin..
					setVehiclePo.setString("QUALIFIED_NO", vo.getQualifiedNo());			// 合格证号
					// update_date 20160112 by maxiang end..
					
					if (!vo.getProductDate().equals("00000000")) {
						Date productDate = DateUtil.yyyyMMdd2Date(vo.getProductDate());
						setVehiclePo.setDate("PRODUCT_DATE", productDate);					// 生产日期
					}
					
					setVehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_04);	// 车辆节点日期
					setVehiclePo.setDate("NODE_DATE", mStorageDate);						// 节点更新日期
					setVehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);			// 车辆生命周期
					setVehiclePo.setString("REMARK", vo.getRemark());						// 车辆备注信息
					setVehiclePo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehiclePo.setDate("UPDATE_DATE", new Date());
					setVehiclePo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZASS_DATE", mOnlineTimestamp);		// 总装上线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVGO_DATE", mOfflineTimestamp);	// 总装下线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVQP_DATE", mQualityTimestamp);	// 质检完成日期
					setVehicleNodeHistoryPo.setTimestamp("ZVGR_DATE", mStorageTimestamp);	// 工厂在库日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("CREATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params1 = new HashMap<String, Object>();
					params1.put("vin", vo.getVin());
					params1.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
					params1.put("changeName", "总装上线");
					params1.put("changeDate", mOnlineTimestamp);
					params1.put("changeDesc", "总装上线");
					params1.put("mainStatusCode", vo.getMainStatus());
					params1.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params1);
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("vin", vo.getVin());
					params2.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_02);
					params2.put("changeName", "总装下线");
					params2.put("changeDate", mOfflineTimestamp);
					params2.put("changeDesc", "总装下线，入在制库，整车下生产线");
					params2.put("mainStatusCode", vo.getMainStatus());
					params2.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params2);
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params3 = new HashMap<String, Object>();
					params3.put("vin", vo.getVin());
					params3.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_03);
					params3.put("changeName", "质检完成");
					params3.put("changeDate", mQualityTimestamp);
					params3.put("changeDesc", "交车，完成质量检查");
					params3.put("mainStatusCode", vo.getMainStatus());
					params3.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params3);
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params4 = new HashMap<String, Object>();
					params4.put("vin", vo.getVin());
					params4.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_04);
					params4.put("changeName", "工厂在库");
					params4.put("changeDate", mStorageTimestamp);
					params4.put("changeDesc", "入成品库，入销售库");
					params4.put("mainStatusCode", vo.getMainStatus());
					params4.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params4);

				} else {
					
					/*
					 * 情况：交易日期小于车辆节点日期
					 * 行为：更新车辆节点日期记录表、新增车辆节点变更记录表数据
					 */
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZASS_DATE", mOnlineTimestamp);		// 总装上线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVGO_DATE", mOfflineTimestamp);	// 总装下线日期
					setVehicleNodeHistoryPo.setTimestamp("ZVQP_DATE", mQualityTimestamp);	// 质检完成日期
					setVehicleNodeHistoryPo.setTimestamp("ZVGR_DATE", mStorageTimestamp);	// 工厂在库日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("CREATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params1 = new HashMap<String, Object>();
					params1.put("vin", vo.getVin());
					params1.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_01);
					params1.put("changeName", "总装上线");
					params1.put("changeDate", mOnlineTimestamp);
					params1.put("changeDesc", "总装上线");
					params1.put("mainStatusCode", vo.getMainStatus());
					params1.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params1);
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params2 = new HashMap<String, Object>();
					params2.put("vin", vo.getVin());
					params2.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_02);
					params2.put("changeName", "总装下线");
					params2.put("changeDate", mOfflineTimestamp);
					params2.put("changeDesc", "总装下线，入在制库，整车下生产线");
					params2.put("mainStatusCode", vo.getMainStatus());
					params2.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params2);
					
					// 补充车辆节点变更记录表数据
					Map<String, Object> params3 = new HashMap<String, Object>();
					params3.put("vin", vo.getVin());
					params3.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_03);
					params3.put("changeName", "质检完成");
					params3.put("changeDate", mQualityTimestamp);
					params3.put("changeDesc", "交车，完成质量检查");
					params3.put("mainStatusCode", vo.getMainStatus());
					params3.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params3);
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params4 = new HashMap<String, Object>();
					params4.put("vin", vo.getVin());
					params4.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_04);
					params4.put("changeName", "工厂在库");
					params4.put("changeDate", mStorageTimestamp);
					params4.put("changeDesc", "入成品库，入销售库");
					params4.put("mainStatusCode", vo.getMainStatus());
					params4.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params4);
					
				}
				
			}
			
		} else if (vo.getActionCode().equals("ZIKO")) {	// 车辆节点：内部结算失败
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 将交易日期与时间拼接后转换为 Date 类型
			String actionDate = vo.getActionDate() + vo.getActionTime();
			Date nodeDate = DateUtil.yyyyMMddHHmmss2Date(actionDate);
			Timestamp changeDate = new Timestamp(nodeDate.getTime());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				Date predictOfflineDate = DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate());
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", predictOfflineDate);			// 预计下线日期
				
				// update_date 20151217 by maxiang begin..
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
										+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
										+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
										+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				// update_date 20151217 by maxiang end..
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_05);			// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", nodeDate);							// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);					// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());							// 备注
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);						// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());							// 创建日期
				vehiclePo.setInteger("VER", 0);											// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);											// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);							// 逻辑删除
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());						// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
				vehiclePo.saveIt();
				
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZIKO_DATE", changeDate);	// 内部结算失败日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 新增车辆节点变更记录表数据
//				Map<String, Object> mainStatus = dao.mainStatusQuery(vo.getMainStatus());
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_05);
				params.put("changeName", "内部结算失败");
				params.put("changeDate", changeDate);
				params.put("changeDesc", "SSAP向MSAP采购车辆失败");
				params.put("mainStatusCode", vo.getMainStatus());
//				params.put("mainStatusDesc", mainStatus.get("MAIN_STATUS_DESC"));
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {

					// 如果交易时间大于最新节点时间，则更新车辆主数据表
					TmVehiclePO setPo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					
					// update_date 20151217 by maxiang begin..
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
												+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
												+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
												+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setPo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					if(tmpList != null && tmpList.size()>0){
						setPo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					// update_date 20151217 by maxiang end..
					
					setPo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_05);
					setPo.setDate("NODE_DATE", nodeDate);								// 节点更新日期
					setPo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);
					setPo.setString("REMARK", vo.getRemark());
					setPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setPo.setDate("UPDATE_DATE", new Date());
					setPo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZIKO_DATE", changeDate);	// 内部结算失败日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
//					Map<String, Object> mainStatus = dao.mainStatusQuery(vo.getMainStatus());
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_05);
					params.put("changeName", "内部结算失败");
					params.put("changeDate", changeDate);
					params.put("changeDesc", "SSAP向MSAP采购车辆失败");
					params.put("mainStatusCode", vo.getMainStatus());
//					params.put("mainStatusDesc", mainStatus.get("MAIN_STATUS_DESC"));
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				} else {
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setDate("ZIKO_DATE", changeDate);	// 内部结算失败日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
//					Map<String, Object> mainStatus = dao.mainStatusQuery(vo.getMainStatus());
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_05);
					params.put("changeName", "内部结算失败");
					params.put("changeDate", changeDate);
					params.put("changeDesc", "SSAP向MSAP采购车辆失败");
					params.put("mainStatusCode", vo.getMainStatus());
//					params.put("mainStatusDesc", mainStatus.get("MAIN_STATUS_DESC"));
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				}
				
			}
			
		} else if (vo.getActionCode().equals("ZINV")) {	// 车辆节点：内部结算成功
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 将交易日期与时间拼接后转换为 Date 类型
			String actionDate = vo.getActionDate() + vo.getActionTime();
			Date nodeDate = DateUtil.yyyyMMddHHmmss2Date(actionDate);
			Timestamp changeDate = new Timestamp(nodeDate.getTime());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));		// 预计下线日期
				
				// update_date 20151217 by maxiang begin..
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
						+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
						+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
						+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				
				// update_date 20151217 by maxiang end..
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_06);			// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", nodeDate);							// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);					// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());							// 备注
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);						// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());							// 创建日期
				vehiclePo.setInteger("VER", 0);											// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);											// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);							// 逻辑删除
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());						// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
				vehiclePo.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZINV_DATE", changeDate);	// 内部结算成功日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_06);
				params.put("changeName", "内部结算成功");
				params.put("changeDate", changeDate);
				params.put("changeDesc", "SSAP向MSAP采购车辆成功");
				params.put("mainStatusCode", vo.getMainStatus());
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {

					// 如果交易时间大于最新节点时间，则更新车辆主数据表
					TmVehiclePO setPo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
							+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
							+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
							+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setPo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					if(tmpList != null && tmpList.size()>0){
						setPo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					setPo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
					setPo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_06);
					setPo.setDate("NODE_DATE", nodeDate);								// 节点更新日期
					setPo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);
					setPo.setString("REMARK", vo.getRemark());
					setPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setPo.setDate("UPDATE_DATE", new Date());
					setPo.saveIt();
//					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZINV_DATE", changeDate);	// 内部结算成功日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_06);
					params.put("changeName", "内部结算成功");
					params.put("changeDate", changeDate);
					params.put("changeDesc", "SSAP向MSAP采购车辆成功");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				} else {
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZINV_DATE", changeDate);	// 内部结算成功日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_06);
					params.put("changeName", "内部结算成功");
					params.put("changeDate", changeDate);
					params.put("changeDesc", "SSAP向MSAP采购车辆成功");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				}
				
			}
			
		} else if (vo.getActionCode().equals("ZBB1")) {	// 车辆节点：工厂质量扣留
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 冻结日期
			Date blockDate = DateUtil.yyyyMMdd2Date(vo.getBlockDate());
			Timestamp blockTimestamp = new Timestamp(blockDate.getTime());
		
			// 预计解冻日期
			Date expectedUnblockDate = DateUtil.yyyyMMdd2Date(vo.getExpectedUnblockDate());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));		// 预计下线日期
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
						+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
						+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
						+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_07);			// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", blockDate);								// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);					// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());							// 备注
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());						// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
				vehiclePo.setString("BLOCK_REASON", vo.getBlockReason());					// 冻结原因
				vehiclePo.setString("EXPECTED_UNBLOCK_DATE", vo.getExpectedUnblockDate());	// 预计解冻日期
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);						// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());							// 创建日期
				vehiclePo.setInteger("VER", 0);											// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);											// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);							// 逻辑删除
				vehiclePo.saveIt();
				
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZBB1_DATE", blockTimestamp);	// 工厂质量扣留日期
				vehicleNodeHistory.setString("ZBB1_BLOCK_REASON", vo.getBlockReason());	// 冻结原因
				
				if (null != expectedUnblockDate) {
					Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
					vehicleNodeHistory.setTimestamp("ZBB1_EXPECTED_UNBLOCK_DATE", expectedUnblockTimestamp);	// 预计解冻日期
				}
				
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_07);
				params.put("changeName", "工厂质量扣留");
				params.put("changeDate", blockTimestamp);
				params.put("changeDesc", "车辆入工厂库后质量扣留");
				params.put("blockReason", vo.getBlockReason());
				
				if (null != expectedUnblockDate) {
					Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
					params.put("expectedUnblockDate", expectedUnblockTimestamp);	// 预计解冻日期
				}
	
				params.put("mainStatusCode", vo.getMainStatus());
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {

					// 如果交易时间大于最新节点时间，则更新车辆主数据表
					TmVehiclePO setPo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
							+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
							+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
							+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setPo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					if(tmpList != null && tmpList.size()>0){
						setPo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					setPo.setString("FACTORT_CODE", vo.getFactoryCode());
					setPo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());
					setPo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_07);
					setPo.setDate("NODE_DATE", blockDate);								// 节点更新日期
					setPo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);
					setPo.setString("REMARK", vo.getRemark());
					setPo.setString("BLOCK_REASON", vo.getBlockReason());	// 冻结原因
					setPo.setString("EXPECTED_UNBLOCK_DATE", vo.getExpectedUnblockDate());	// 预计解冻日期
					setPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setPo.setDate("UPDATE_DATE", new Date());
					setPo.saveIt();
					
//					 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZBB1_DATE", blockTimestamp);	// 工厂质量扣留日期
					setVehicleNodeHistoryPo.setString("ZBB1_BLOCK_REASON", vo.getBlockReason());	// 冻结原因
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						setVehicleNodeHistoryPo.setTimestamp("ZBB1_EXPECTED_UNBLOCK_DATE", expectedUnblockTimestamp);	// 预计解冻日期
					}

					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_07);
					params.put("changeName", "工厂质量扣留");
					params.put("changeDate", blockTimestamp);
					params.put("changeDesc", "车辆入工厂库后质量扣留");
					params.put("blockReason", vo.getBlockReason());
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						params.put("expectedUnblockDate", expectedUnblockTimestamp);	// 预计解冻日期
					}

					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				} else {
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZBB1_DATE", blockTimestamp);	// 工厂质量扣留日期
					setVehicleNodeHistoryPo.setString("ZBB1_BLOCK_REASON", vo.getBlockReason());	// 冻结原因
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						setVehicleNodeHistoryPo.setTimestamp("ZBB1_EXPECTED_UNBLOCK_DATE", expectedUnblockTimestamp);	// 预计解冻日期
					}

					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_07);
					params.put("changeName", "工厂质量扣留");
					params.put("changeDate", blockTimestamp);
					params.put("changeDesc", "车辆入工厂库后质量扣留");
					params.put("blockReason", vo.getBlockReason());
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						params.put("expectedUnblockDate", expectedUnblockTimestamp);	// 预计解冻日期
					}

					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				}
				
			}
			
		} else if (vo.getActionCode().equals("ZBBR")) {	// 车辆节点：工厂质量扣留解除
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 实际解冻日期
			Date actualUnblockDate = DateUtil.yyyyMMdd2Date(vo.getActualUnblockDate());
			Timestamp actualUnblockTimestamp = new Timestamp(actualUnblockDate.getTime());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));		// 预计下线日期
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
						+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
						+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
						+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_08);			// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", actualUnblockDate);								// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);					// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());							// 备注
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());						// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
				vehiclePo.setString("BLOCK_REASON", vo.getBlockReason());					// 冻结原因
				vehiclePo.setString("EXPECTED_UNBLOCK_DATE", vo.getExpectedUnblockDate());	// 预计解冻日期
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);						// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());							// 创建日期
				vehiclePo.setInteger("VER", 0);											// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);											// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);							// 逻辑删除
				vehiclePo.saveIt();
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZBBR_DATE", actualUnblockTimestamp);	// 工厂质量扣留解除日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_08);
				params.put("changeName", "工厂质量扣留解除");
				params.put("changeDate", actualUnblockTimestamp);
				params.put("changeDesc", "维修完成，工厂质量扣留车辆解冻");
				params.put("mainStatusCode", vo.getMainStatus());
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {

					// 如果交易时间大于最新节点时间，则更新车辆主数据表
					TmVehiclePO setPo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
							+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
							+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
							+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setPo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					if(tmpList != null && tmpList.size()>0){
						setPo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					setPo.setString("FACTORT_CODE", vo.getFactoryCode());
					setPo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());
					setPo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_08);
					setPo.setDate("NODE_DATE", actualUnblockDate);								// 节点更新日期
					setPo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);
					setPo.setString("REMARK", vo.getRemark());
					setPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setPo.setDate("UPDATE_DATE", new Date());
					setPo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZBBR_DATE", actualUnblockTimestamp);	// 工厂质量扣留解除日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_08);
					params.put("changeName", "工厂质量扣留解除");
					params.put("changeDate", actualUnblockTimestamp);
					params.put("changeDesc", "维修完成，工厂质量扣留车辆解冻");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				} else {
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZBBR_DATE", actualUnblockTimestamp);	// 工厂质量扣留解除日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_08);
					params.put("changeName", "工厂质量扣留解除");
					params.put("changeDate", actualUnblockTimestamp);
					params.put("changeDesc", "维修完成，工厂质量扣留车辆解冻");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				}
				
			}

		} else if (vo.getActionCode().equals("ZMBL")) {	// 车辆节点：销售公司质量扣留
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 冻结日期
			Date blockDate = DateUtil.yyyyMMdd2Date(vo.getBlockDate());
			Timestamp blockTimestamp = new Timestamp(blockDate.getTime());
			
			// 预计解冻日期
			Date expectedUnblockDate = DateUtil.yyyyMMdd2Date(vo.getExpectedUnblockDate());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));		// 预计下线日期
				
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
						+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
						+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
						+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());					// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());				// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());						// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_09);			// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", blockDate);								// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);					// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());							// 备注
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());						// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());					// 公司代码
				vehiclePo.setString("BLOCK_REASON", vo.getBlockReason());					// 冻结原因
				vehiclePo.setString("EXPECTED_UNBLOCK_DATE", vo.getExpectedUnblockDate());	// 预计解冻日期
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);						// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());							// 创建日期
				vehiclePo.setInteger("VER", 0);											// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);											// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);							// 逻辑删除
				vehiclePo.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZMBL_DATE", blockTimestamp);	// 销售公司质量扣留日期
				vehicleNodeHistory.setString("ZMBL_BLOCK_REASON", vo.getBlockReason());	// 冻结原因
				
				if (null != expectedUnblockDate) {
					Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
					vehicleNodeHistory.setTimestamp("ZBB1_EXPECTED_UNBLOCK_DATE",expectedUnblockTimestamp);	// 预计解冻日期
				}

				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_09);
				params.put("changeName", "销售公司质量扣留");
				params.put("changeDate", blockTimestamp);
				params.put("changeDesc", "车辆入销售公司库后质量扣留");
				params.put("blockReason", vo.getBlockReason());
				
				if (null != expectedUnblockDate) {
					Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
					params.put("expectedUnblockDate", expectedUnblockTimestamp);	// 预计解冻日期
				}
				
				params.put("mainStatusCode", vo.getMainStatus());
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {

					// 如果交易时间大于最新节点时间，则更新车辆主数据表
					TmVehiclePO setPo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
							+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
							+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
							+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setPo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					if(tmpList != null && tmpList.size()>0){
						setPo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					setPo.setString("FACTORT_CODE", vo.getFactoryCode());
					setPo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());
					setPo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_09);
					setPo.setDate("NODE_DATE", blockDate);								// 节点更新日期
					setPo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);
					setPo.setString("REMARK", vo.getRemark());
					setPo.setString("BLOCK_REASON", vo.getBlockReason());					// 冻结原因
					setPo.setString("EXPECTED_UNBLOCK_DATE", vo.getExpectedUnblockDate());	// 预计解冻日期
					setPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setPo.setDate("UPDATE_DATE", new Date());
					setPo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZMBL_DATE", blockTimestamp);	// 销售公司质量扣留日期
					setVehicleNodeHistoryPo.setString("ZMBL_BLOCK_REASON", vo.getBlockReason());	// 冻结原因
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						setVehicleNodeHistoryPo.setTimestamp("ZBB1_EXPECTED_UNBLOCK_DATE",expectedUnblockTimestamp);	// 预计解冻日期
					}

					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_09);
					params.put("changeName", "销售公司质量扣留");
					params.put("changeDate", blockTimestamp);
					params.put("changeDesc", "车辆入销售公司库后质量扣留");
					params.put("blockReason", vo.getBlockReason());
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						params.put("expectedUnblockDate", expectedUnblockTimestamp);	// 预计解冻日期
					}

					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				} else {
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZMBL_DATE", blockTimestamp);	// 销售公司质量扣留日期
					setVehicleNodeHistoryPo.setString("ZMBL_BLOCK_REASON", vo.getBlockReason());	// 冻结原因
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						setVehicleNodeHistoryPo.setTimestamp("ZBB1_EXPECTED_UNBLOCK_DATE",expectedUnblockTimestamp);	// 预计解冻日期
					}

					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_09);
					params.put("changeName", "销售公司质量扣留");
					params.put("changeDate", blockTimestamp);
					params.put("changeDesc", "车辆入销售公司库后质量扣留");
					params.put("blockReason", vo.getBlockReason());
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						params.put("expectedUnblockDate", expectedUnblockTimestamp);	// 预计解冻日期
					}
	
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				}
				
			}
			
		} else if (vo.getActionCode().equals("ZMUB")) {	// 车辆节点：销售公司质量扣留解除
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 实际解冻日期
			Date actualUnblockDate = DateUtil.yyyyMMdd2Date(vo.getActualUnblockDate());
			Timestamp actualUnblockTimestamp = new Timestamp(actualUnblockDate.getTime());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));		// 预计下线日期
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
						+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
						+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
						+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());						// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());					// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());								// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_10);	// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", actualUnblockDate);								// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);			// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());									// 备注
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());							// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());						// 公司代码
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);					// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());									// 创建日期
				vehiclePo.setInteger("VER", 0);													// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);												// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);					// 逻辑删除
				vehiclePo.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZMUB_DATE", actualUnblockTimestamp);	// 销售公司质量扣留解除日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_10);
				params.put("changeName", "销售公司质量扣留解除");
				params.put("changeDate", actualUnblockTimestamp);
				params.put("changeDesc", "维修完成，工厂质量扣留车辆解冻");
				params.put("mainStatusCode", vo.getMainStatus());
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {

					// 如果交易时间大于最新节点时间，则更新车辆主数据表
					TmVehiclePO setPo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
							+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
							+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
							+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setPo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					if(tmpList != null && tmpList.size()>0){
						setPo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					
					setPo.setString("FACTORT_CODE", vo.getFactoryCode());
					setPo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());
					setPo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_10);
					setPo.setDate("NODE_DATE", actualUnblockDate);								// 节点更新日期
					setPo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_02);
					setPo.setString("REMARK", vo.getRemark());
					setPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setPo.setDate("UPDATE_DATE", new Date());
					setPo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZMUB_DATE", actualUnblockTimestamp);	// 销售公司质量扣留解除日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_10);
					params.put("changeName", "销售公司质量扣留解除");
					params.put("changeDate", actualUnblockTimestamp);
					params.put("changeDesc", "维修完成，工厂质量扣留车辆解冻");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				} else {
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZMUB_DATE", actualUnblockTimestamp);	// 销售公司质量扣留解除日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_10);
					params.put("changeName", "销售公司质量扣留解除");
					params.put("changeDate", actualUnblockTimestamp);
					params.put("changeDesc", "维修完成，工厂质量扣留车辆解冻");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				}
				
			}
			
		} else if (vo.getActionCode().equals("ZDF1")) {	// 车辆节点：延迟发运
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 冻结日期
			Date blockDate = DateUtil.yyyyMMdd2Date(vo.getBlockDate());
			Timestamp blockTimestamp = new Timestamp(blockDate.getTime());
			
			// 预计解冻日期
			Date expectedUnblockDate = DateUtil.yyyyMMdd2Date(vo.getExpectedUnblockDate());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);														// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));				// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);							// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));						// 组织ID
				vehiclePo.setString("VIN", vo.getVin());														// 车架号
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));	// 预计下线日期
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
						+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
						+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
						+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());						// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());					// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());								// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_15);	// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", blockDate);										// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_03);			// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());									// 备注
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());							// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());						// 公司代码
				vehiclePo.setString("BLOCK_REASON", vo.getBlockReason());						// 冻结原因
				vehiclePo.setString("EXPECTES_UNBLOCK_DATE", vo.getExpectedUnblockDate());		// 预计解冻日期
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);					// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());									// 创建日期
				vehiclePo.setInteger("VER", 0);													// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);												// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);					// 逻辑删除
				vehiclePo.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZDF1_DATE", blockTimestamp);	// 延迟发运日期
				vehicleNodeHistory.setString("ZDF1_BLOCK_REASON", vo.getBlockReason());	// 冻结原因
				
				if (null != expectedUnblockDate) {
					Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
					vehicleNodeHistory.setTimestamp("ZDF1_EXPECTED_UNBLOCK_DATE", expectedUnblockTimestamp);	// 预计解冻日期
				}

				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_15);
				params.put("changeName", "延迟发运");
				params.put("changeDate", blockTimestamp);
				params.put("changeDesc", "发运前因质量问题短暂扣留");
				params.put("blockReason", vo.getBlockReason());
				
				if (null != expectedUnblockDate) {
					Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
					params.put("expectedUnblockDate", expectedUnblockTimestamp);	// 预计解冻日期
				}

				params.put("mainStatusCode", vo.getMainStatus());
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {

					// 如果交易时间大于最新节点时间，则更新车辆主数据表
					TmVehiclePO setPo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
//					
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
							+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
							+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
							+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setPo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					if(tmpList != null && tmpList.size()>0){
						setPo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					setPo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_15);
					setPo.setDate("NODE_DATE", blockDate);								// 节点更新日期
					setPo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_03);
					setPo.setString("REMARK", vo.getRemark());
					setPo.setString("BLOCK_REASON", vo.getBlockReason());					// 冻结原因
					setPo.setString("EXPECTED_UNBLOCK_DATE", vo.getExpectedUnblockDate());	// 预计解冻日期
					setPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setPo.setDate("UPDATE_DATE", new Date());
					setPo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZDF1_DATE", blockTimestamp);	// 延迟发运日期
					setVehicleNodeHistoryPo.setString("ZDF1_BLOCK_REASON", vo.getBlockReason());	// 冻结原因
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						setVehicleNodeHistoryPo.setTimestamp("ZDF1_EXPECTED_UNBLOCK_DATE", expectedUnblockTimestamp);	// 预计解冻日期
					}

					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_15);
					params.put("changeName", "延迟发运");
					params.put("changeDate", blockTimestamp);
					params.put("changeDesc", "发运前因质量问题短暂扣留");
					params.put("blockReason", vo.getBlockReason());
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						params.put("expectedUnblockDate", expectedUnblockTimestamp);	// 预计解冻日期
					}

					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				} else {
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZDF1_DATE", blockTimestamp);	// 延迟发运日期
					setVehicleNodeHistoryPo.setString("ZDF1_BLOCK_REASON", vo.getBlockReason());	// 冻结原因
					
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						setVehicleNodeHistoryPo.setTimestamp("ZDF1_EXPECTED_UNBLOCK_DATE", expectedUnblockTimestamp);	// 预计解冻日期
					}
					
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_15);
					params.put("changeName", "延迟发运");
					params.put("changeDate", blockTimestamp);
					params.put("changeDesc", "发运前因质量问题短暂扣留");
					params.put("blockReason", vo.getBlockReason());
					
					if (null != expectedUnblockDate) {
						Timestamp expectedUnblockTimestamp = new Timestamp(expectedUnblockDate.getTime());
						params.put("expectedUnblockDate", expectedUnblockTimestamp);	// 预计解冻日期
					}
	
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				}
				
			}
			
		} else if (vo.getActionCode().equals("ZDP1")) {	// 车辆节点：取消延迟发运
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息
			
			String vehicleIdStr = "", vehicleNodeDate = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
				vehicleNodeDate = vehicleInfo.get("NODEDATE").toString();	// 节点日期
			}
			
			// 交易日期
			String actionDate = vo.getActionDate() + vo.getActionTime();
			
			// 实际解冻日期
			Date actualUnblockDate = DateUtil.yyyyMMdd2Date(vo.getActualUnblockDate());
			Timestamp actualUnblockTimestamp = new Timestamp(actualUnblockDate.getTime());
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 新增车辆主数据表数据
				TmVehiclePO vehiclePo = new TmVehiclePO();
				vehiclePo.setLong("VEHICLE_ID", vehicleId);								// 车辆ID
				vehiclePo.setLong("OEM_COMPANY_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));	// OEM公司ID
				vehiclePo.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);					// 组织类型
				vehiclePo.setLong("ORG_ID", new Long(OemDictCodeConstants.OEM_ACTIVITIES));			// 组织ID
				vehiclePo.setString("VIN", vo.getVin());									// 车架号
				vehiclePo.setDate("PREDICT_OFFLINE_DATE", DateUtil.yyyyMMdd2Date(vo.getPredictOfflineDate()));		// 预计下线日期
				String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
						+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
						+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
						+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
				List<Map> materialList = OemDAOUtil.findAll(sql, null);
				vehiclePo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				//车辆表添加批售价格
				List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
				if(tmpList != null && tmpList.size()>0){
					vehiclePo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
				}
				
				vehiclePo.setString("FACTORY_CODE", vo.getFactoryCode());						// 工厂
				vehiclePo.setString("WAREHOUSE_CODE", vo.getWarehouseCode());					// 库存地点
				vehiclePo.setString("ENGINE_NO", vo.getEngineNo());								// 发动机号
				vehiclePo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_16);	// 车辆节点状态
				vehiclePo.setDate("NODE_DATE", actualUnblockDate);								// 节点更新日期
				vehiclePo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_03);			// 车辆生命周期
				vehiclePo.setString("REMARK", vo.getRemark());									// 备注
				vehiclePo.setString("MODEL_YEAR", vo.getModelYear());							// 年款
				vehiclePo.setString("COMPANY_CODE", vo.getCompanyCode());						// 公司代码
				vehiclePo.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);					// 创建人
				vehiclePo.setDate("CREATE_DATE", new Date());									// 创建日期
				vehiclePo.setInteger("VER", 0);													// 版本控制
				vehiclePo.setInteger("IS_ARC", 0);												// 归档标志
				vehiclePo.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);					// 逻辑删除
				vehiclePo.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("ZDP1_DATE", actualUnblockTimestamp);	// 销售公司质量扣留解除日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0001);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_16);
				params.put("changeName", "取消延迟发运");
				params.put("changeDate", actualUnblockTimestamp);
				params.put("changeDesc", "发运前因质量问题修复");
				params.put("mainStatusCode", vo.getMainStatus());
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			} else {	// 车辆已存在
				
				if (Long.parseLong(actionDate) > Long.parseLong(vehicleNodeDate)) {

					// 如果交易时间大于最新节点时间，则更新车辆主数据表
					TmVehiclePO setPo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
					
					String sql = ("SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+")VM WHERE VM.MODEL_CODE = '"+vo.getModelCode()+"'"
							+ "AND VM.MODEL_YEAR = '"+vo.getModelYear()+"'"
							+ "AND VM.COLOR_CODE = '"+vo.getColorCode()+"'"
							+ "AND VM.TRIM_CODE = '"+vo.getTrimCode()+"'");
					List<Map> materialList = OemDAOUtil.findAll(sql, null);
					setPo.setLong("MATERIAL_ID", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					//车辆表添加批售价格
					//车辆表添加批售价格
					List<TmMaterialPricePO> tmpList = TmMaterialPricePO.find("MATERIAL_ID = ? ", materialList.get(0).get("MATERIAL_ID"));	// 物料ID
					if(tmpList != null && tmpList.size()>0){
						setPo.setDouble("WHOLESALE_PRICE", tmpList.get(0).getDouble("BASE_PRICE"));//批售价格
					}
					
					setPo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_16);
					setPo.setDate("NODE_DATE", actualUnblockDate);						// 节点更新日期
					setPo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_03);
					setPo.setString("REMARK", vo.getRemark());
					setPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setPo.setDate("UPDATE_DATE", new Date());
					setPo.saveIt();
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZDP1_DATE", actualUnblockTimestamp);	// 取消延迟发运日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_16);
					params.put("changeName", "取消延迟发运");
					params.put("changeDate", actualUnblockTimestamp);
					params.put("changeDesc", "发运前因质量问题修复");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				} else {
					
					// 更新车辆节点日期记录表（存储日期）
					TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
					setVehicleNodeHistoryPo.setTimestamp("ZDP1_DATE", actualUnblockTimestamp);	// 取消延迟发运日期
					setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
					setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
					setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
					setVehicleNodeHistoryPo.saveIt();
					
					// 新增车辆节点变更记录表数据
					Map<String, Object> params = new HashMap<String, Object>();
					params.put("vin", vo.getVin());
					params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_16);
					params.put("changeName", "取消延迟发运");
					params.put("changeDate", actualUnblockTimestamp);
					params.put("changeDesc", "发运前因质量问题修复");
					params.put("mainStatusCode", vo.getMainStatus());
					params.put("createBy", OemDictCodeConstants.K4_S0001);
					dao.insertVehicleChange(params);
					
				}
				
			}
			
		} else if (vo.getActionCode().equals("ZRGZ")) {	// 车辆节点：取消零售上报
			
			// 需求待定
			
		} else if (vo.getActionCode().equals("VDEL")) {	// 车辆节点：删除车辆
			
			Map<String, Object> vehicleInfo = dao.vehicleInfoByVin(vo.getVin());	// 车辆信息

			String vehicleIdStr = "";
			Long vehicleId = 0l;
			if (null != vehicleInfo) {
				vehicleIdStr = vehicleInfo.get("VEHICLE_ID").toString();	
				vehicleId = Long.parseLong(vehicleIdStr);	// 车辆ID
			}
			
			if (null == vehicleInfo) {	// 车辆不存在
				
				// 将交易日期与时间拼接后转换为 Date 类型
				String actionDate = vo.getActionDate() + vo.getActionTime();
				Date nodeDate = DateUtil.yyyyMMddHHmmss2Date(actionDate);
				Timestamp changeDate = new Timestamp(nodeDate.getTime());
				
				// 如果交易时间大于最新节点时间，则更新车辆主数据表
				TmVehiclePO setPo = TmVehiclePO.findFirst("VIN = ? ", vo.getVin());
				setPo.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_21);
				setPo.setDate("NODE_DATE_", nodeDate);								// 节点更新日期
				setPo.setInteger("LIFE_CYCLE", OemDictCodeConstants.LIF_CYCLE_06);
				setPo.setString("REMARK", vo.getRemark());
				setPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
				setPo.setDate("UPDATE_DATE", new Date());
				setPo.saveIt();
				
				// 更新车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);	// 车辆ID
				setVehicleNodeHistoryPo.setDate("CDEL_DATE", changeDate);	// 车辆报废日期
				setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
				setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0001);
				setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
				setVehicleNodeHistoryPo.saveIt();
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_21);
				params.put("changeName", "删除车辆");
				params.put("changeDate", changeDate);
				params.put("changeDesc", "车辆已报废");
				params.put("mainStatusCode", vo.getMainStatus());
				params.put("createBy", OemDictCodeConstants.K4_S0001);
				dao.insertVehicleChange(params);
				
			}
					
		}
		
		logger.info("==============S0001 业务处理逻辑结束================");
		
	}
	
	/**
	 * 校验车型、年款、颜色、内饰是否为有效物料数据
	 * @param modelCode
	 * @param modelYear
	 * @param colorCode
	 * @param trimCode
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("rawtypes")
	private int doMaterialCheck
		(String modelCode, String modelYear, String colorCode, String trimCode) throws Exception {
		
		String sql = "SELECT * FROM ("+OemBaseDAO.getVwMaterialSql()+") VM WHERE VM.MODEL_CODE = '"+modelCode+"'"
						+ "AND VM.MODEL_YEAR = '"+modelYear+"'"
						+ "AND VM.COLOR_CODE = '"+colorCode+"'"
						+ "AND VM.TRIM_CODE = '"+trimCode+"'";
		List<Map> materialList = OemDAOUtil.findAll(sql, null);
		
		/*
		 * 校验车架号是否已存在
		 */
		if (materialList.size() > 0) {
			return OemDictCodeConstants.IF_TYPE_YES;	// 是
		} else {
			return OemDictCodeConstants.IF_TYPE_NO;		// 否
		}
	}
	
	/**
	 * S0001 接口测试入口
	 * @param args
	 * @throws Throwable 
	 */
	public static void main(String[] args) throws Throwable {
		
//		ContextUtil.loadConf();
			
		S0001Impl s0001 = new S0001Impl();
		
		List<S0001VO> voList = new ArrayList<S0001VO>();
		
		for (int i = 10; i <= 11; i++) {
			
			S0001VO vo = new S0001VO();
			
			vo.setActionCode("ZBB1");	// 交易代码
			vo.setActionDate("20161123");	// 交易日期
			vo.setActionTime("145130");	// 交易时间
			vo.setVin("TESTVIN20160110" + i);	// 车架号
			vo.setMainStatus("LSA5");	// 主要状态
			vo.setSecondStatus("");	// 第二状态
			vo.setPredictOfflineDate("20160111");	// 预计下线日期
			vo.setModelCode("K4JE7424L");	// 车型
			vo.setModelYear("2015");	// 年款
			vo.setColorCode("PAD");	// 颜色
			vo.setTrimCode("ALUC");	// 内饰
			vo.setStandardOption("");	// 标准配置
			vo.setFactoryOptions("");	// 其他配置
			vo.setLocalOption("");	// 本地配置
			vo.setFactoryCode("GF50");	// 工厂
			vo.setWarehouseCode(null);	// 库存地点
			vo.setRemark("车架号：TESTVIN20160110" + i);	// 备注
			vo.setEngineNo("8*FH00" + i);	// 发动机号
			vo.setQualifiedNo("QUALIFIED" + i);	// 合格证号
			vo.setProductDate("00000000");	// 生产日期
			vo.setBlockDate("20160120");	// 冻结日期
			vo.setBlockReason("车辆工厂质量扣留");	// 冻结原因
			vo.setExpectedUnblockDate(null);	// 预计解冻日期
			vo.setActualUnblockDate("00000001");	// 实际解冻日期
			vo.setCompanyCode("GF50");	// 公司代码
			vo.setMOnlineDate("20160110");	// 总装上线日期
			vo.setMOfflineDate("20160111");	// 总装下线日期
			vo.setMQualityDate("20160112");	// 质检完成日期
			vo.setMStorageDate("20160113");	// 入MJV销售库日期
			vo.setRowId("S0001160312000000" + i);	// ROW_ID
			
			voList.add(vo);
			
		}
		
		s0001.execute(voList);
			
		System.out.println("End..");
		
	}
}
