package com.infoeai.eai.action.ncserp.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.infoeai.eai.action.ncserp.common.ISAPOutBoundCommon;
import com.infoeai.eai.action.ncserp.intf.ISAPExecutor;
import com.infoeai.eai.action.ncserp.intf.ISAPOutBoundVO;
import com.infoeai.eai.dao.ctcai.SICommonDao;
import com.infoeai.eai.po.TtVehicleBackPO;
import com.infoeai.eai.po.TtWrClaimPO;
import com.infoeai.eai.vo.TmVhclMaterialGroupVO;
import com.infoeai.eai.wsServer.SapDcsService.SapOutboundVO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialGroupRPO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialPO;
import com.yonyou.dms.common.domains.PO.basedata.TiNodeDetialPO;
import com.yonyou.dms.common.domains.PO.basedata.TmAllotSupportPO;
import com.yonyou.dms.common.domains.PO.basedata.TmCtcaiVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtResourceRemarkPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourceDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourcePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCustomerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsFactoryOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsInspectionPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsNvdrPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsSalesReportInvoicePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsSalesReportPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.common.domains.PO.basedata.TtWrRepairPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class YOUIExecutorImpl extends BaseService implements YOUIExecutor{
    
	private static Logger logger = LoggerFactory.getLogger(YOUIExecutorImpl.class);
	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	Calendar sysDate = Calendar.getInstance();

	@Autowired
	SICommonDao siComDAO;
	
	/**
	 * 接口执行入口
	 */
	@Override
	public String execute(ISAPOutBoundVO dto) throws Exception {
		
		logger.info("========== 【YOUI接口】执行开始 ==========");
		boolean flag = false;//处理结果   false:失败　　true：成功
		Long nodeId = null;
		SapOutboundVO sapOutboundDTO = (SapOutboundVO) dto;
		String messg = null;
		logger.info("========== 【YOUI接口】事务开启 ==========");
		beginDbService();
		try {
			logger.info("========== 【YOUI接口】插入记录日志表开始 ==========");
			nodeId = ISAPOutBoundCommon.insertNodeDetail(sapOutboundDTO);
			logger.info("========== 【YOUI接口】插入记录日志表结束 ==========");
			String vin = sapOutboundDTO.getVin();  //车架号
			
			//数据校验
			if (swt2Dcs01Check(nodeId, sapOutboundDTO)) {
				
				// 判断车辆是否存在
				List<TmVehiclePO> vehiclePoList = TmVehiclePO.findBySQL("SELECT * FROM TM_VEHICLE_DEC WHERE VIN=?", vin);
				if (null != vehiclePoList && vehiclePoList.size() > 0) {
					flag = updateMethod(sapOutboundDTO, nodeId);
				} else {
					flag = insertMethod(sapOutboundDTO, nodeId);
				}
			}else {
				logger.info("========== 【YOUI接口】校验失败 ==========");
			}
			
			// 期货资源预定
			int total = ISAPOutBoundCommon.methodToFuturesResource(vin);

			logger.info("========== 【YOUI接口】事务提交 ==========");
			dbService.endTxn(true);
			
			logger.info("========== 调用期货资源设定规则结束，共有" + total + "条数据进入公共资源池 ==========");
			
		} catch (Throwable e) {
			logger.error(e.getMessage(), e);
			logger.info("========== 【YOUI接口】事务回滚 ==========");
			dbService.endTxn(false);
			//messg = Utility.getErrorStack(e, EAIConstant.ERROR_STACK_MAX_LENGTH);
			throw new Exception("========== 异常日志插入 =========="+e);
		}finally {
			logger.info("========== 【YOUI接口】事务清理 ==========");
			Base.detach();
			dbService.clean();
			if (messg != null && !messg.equals("")) {
				logger.info("========== 【YOUI接口】插入或更新业务表失败，记录错误信息到接口日志表开始 ==========");
				//EaiUtil.updTiNodeDetial(nodeId, messg);
				logger.info("========== 【YOUI接口】插入或更新业务表失败，记录错误信息到接口日志表完成 ==========");
			}
		}
		logger.info("========== 【YOUI接口】执行结束 ==========");
		//返回处理结果
		if (flag) {
			return "02";
		} else {
			return "01";
		}
	}
    
	/**
	 * 验证是否是YOUI
	 */
	@Override
	public boolean isValid(ISAPOutBoundVO dto) throws Exception {
		try {
			return "YOUI".equals(dto.getActionCode());
		} catch (Exception e) {
			throw new Exception("========== 【YOUI接口】actionCode验证处理异常==========" + e);
		}
	}
	
	/**
	 * 数据验证
	 * @param nodeId
	 * @param sapOutboundVO
	 * @param factory
	 * @return
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	private boolean swt2Dcs01Check(Long nodeId, SapOutboundVO sapOutboundDTO) {
		boolean falg = false;
		// 准备更新当前节点的导入失败信息
		TiNodeDetialPO currentNode = TiNodeDetialPO.findById(nodeId);
		currentNode.setString("IMPORT_FLAG", "10041002");
		String remark = null;
		if (null == sapOutboundDTO.getVin() || "".equals(sapOutboundDTO.getVin())) {
			remark = "vin码为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getVin().length() != 17) {
			remark = sapOutboundDTO.getVin() + "的vin码长度与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}
		
		if (null == sapOutboundDTO.getActionCode() || "".equals(sapOutboundDTO.getActionCode())) {
			remark = sapOutboundDTO.getVin() + "的交易代码为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} 
		
		if (null == sapOutboundDTO.getActionDate() || "".equals(sapOutboundDTO.getActionDate())) {
			remark = sapOutboundDTO.getVin() + "的交易日期为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getActionDate().length() != 8) {
			remark = sapOutboundDTO.getVin() + "的交易日期长度与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}
		
		if (null == sapOutboundDTO.getActionTime() || "".equals(sapOutboundDTO.getActionTime())) {
			remark = sapOutboundDTO.getVin() + "的交易时间为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getActionTime().length() != 6) {
			remark = sapOutboundDTO.getVin() + "的交易时间长度与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}
		
		if (null == sapOutboundDTO.getPrimaryStatus()
				|| "".equals(sapOutboundDTO.getPrimaryStatus())) {
			remark = sapOutboundDTO.getVin() + "的主要状态为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getPrimaryStatus().length() > 10) {
			remark = sapOutboundDTO.getVin() + "的主要状态长度与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}
		
		if (null == sapOutboundDTO.getSecondaryStatus()
				|| "".equals(sapOutboundDTO.getSecondaryStatus())) {
			remark = sapOutboundDTO.getVin() + "的第二状态为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getSecondaryStatus().length() > 8) {
			remark = sapOutboundDTO.getVin() + "的第二状态长度与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}

		if (null == sapOutboundDTO.getModel() || "".equals(sapOutboundDTO.getModel())) {
			remark = sapOutboundDTO.getVin() + "的车型为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getModel().length() > 30) {
			remark = sapOutboundDTO.getVin() + "的车型长度与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}

		if (null == sapOutboundDTO.getModelyear() || "".equals(sapOutboundDTO.getModelyear())) {
			remark = sapOutboundDTO.getVin() + "的车型年份为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getModelyear().length() != 4) {
			remark = sapOutboundDTO.getVin() + "的车型年份长度与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}

		if (null == sapOutboundDTO.getCharacteristicColour() || "".equals(sapOutboundDTO.getCharacteristicColour())) {
			remark = sapOutboundDTO.getVin() + "的颜色为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getCharacteristicColour().length() > 4) {
			remark = sapOutboundDTO.getVin() + "的颜色与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}

		if (null == sapOutboundDTO.getCharacteristicTrim() || "".equals(sapOutboundDTO.getCharacteristicTrim())) {
			remark = sapOutboundDTO.getVin() + "的配饰为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getCharacteristicTrim().length() > 4) {
			remark = sapOutboundDTO.getVin() + "的配饰与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}

		if (null == sapOutboundDTO.getCharacteristicFactoryStandardOptions()
				|| "".equals(sapOutboundDTO.getCharacteristicFactoryStandardOptions())) {
			remark = sapOutboundDTO.getVin() + "的标准配置为空";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getCharacteristicFactoryStandardOptions().length() > 100) {
			remark = sapOutboundDTO.getVin() + "的标准配置与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}

		if (null != sapOutboundDTO.getCharacteristicFactoryOptions()
				&& sapOutboundDTO.getCharacteristicFactoryOptions().length() > 100) {
			remark = sapOutboundDTO.getVin() + "的其他配置与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}

		if (null != sapOutboundDTO.getCharacteristicLocalOptions()
				&& sapOutboundDTO.getCharacteristicLocalOptions().length() > 100) {
			remark = sapOutboundDTO.getVin() + "的本地配置与接口定义不一致";
			logger.info("=============" + remark + "=================");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}
		
		// 售达方和发票号码进行单独校验
		if (null == sapOutboundDTO.getSoldto() || "".equals(sapOutboundDTO.getSoldto())) {
			remark = sapOutboundDTO.getVin() + "的售达方为空";
			logger.info("========== " + remark + " ==========");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getSoldto().length() > 10) {
			remark = sapOutboundDTO.getVin() + "的售达方长度与接口定义不一致";
			logger.info("========== " + remark + " ==========");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}

		// 发票号码校验
		if (null == sapOutboundDTO.getInvoiceNumber() || "".equals(sapOutboundDTO.getInvoiceNumber())) {
			remark = sapOutboundDTO.getVin() + "的发票号码为空";
			logger.info("========== " + remark + " ==========");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		} else if (sapOutboundDTO.getInvoiceNumber().length() > 12) {
			remark = sapOutboundDTO.getVin() + "的发票号码长度与接口定义不一致";
			logger.info("========== " + remark + " ==========");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}
		
		// 发动机号校验
		if (null == sapOutboundDTO.getEngineNumber() || !(sapOutboundDTO.getEngineNumber().length() == 10)) {
			remark = sapOutboundDTO.getVin() + "的发动机号长度与接口定义不一致";
			logger.info("========== " + remark + " ==========");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
			return falg;
		}
		
		//一次开票次数校验
		String vin = sapOutboundDTO.getVin();
		List<Object> list = new ArrayList<Object>();
		list.add(vin);
		List<Map> tvList = OemDAOUtil.findAll("SELECT * FROM tm_vehicle_dec WHERE VIN=?", list);
		int zbilNumber = 0;
		if (null != tvList && tvList.size() > 0 && !CommonUtils.checkNull(tvList.get(0).get("ZBI_NUMBER")).equals("")) {
			zbilNumber = (int) tvList.get(0).get("ZBI_NUMBER");//一次开票次数
		}
		if(zbilNumber>1){
			remark = sapOutboundDTO.getVin() + "多次开票（退车），非车辆节点范围【中进车款确认】、【中进发车出库】、【已验收】、【已实销】";
			logger.info("========== " + remark + " ==========");
			currentNode.setString("REMARK", remark);
			currentNode.saveIt();
//			return falg;
		}
		return true;
	}
	
	/**
	 * 功能说明：插入车辆
	 * @param zvfcVO
	 * @param nodeId
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "rawtypes", "static-access" })
	private boolean insertMethod(SapOutboundVO sapOutboundDTO, Long nodeId) throws Exception {
		try {
			// 查找唯一的orgId
			List<TmOrgPO> orgPoList = TmOrgPO.find(" ORG_TYPE = ? AND PARENT_ORG_ID = ? ", OemDictCodeConstants.ORG_TYPE_OEM, -1L);
			Long orgId = 0l;
			if(null != orgPoList && orgPoList.size() > 0){
				orgId = orgPoList.get(0).getLong("ORG_ID");
			}
			
			/* 1.操作车辆表 */
			TmVehiclePO vehicle = new TmVehiclePO();
			vehicle.setInteger("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_OEM);  // 主机厂
			vehicle.setLong("ORG_ID", orgId);
			
			// 通过指定的字段获取唯一的物料id
			Map<String, String> passMap = new HashMap<String, String>();
			passMap.put("model", sapOutboundDTO.getModel());
			passMap.put("modelYear", sapOutboundDTO.getModelyear());
			passMap.put("colour", sapOutboundDTO.getCharacteristicColour());
			passMap.put("trim", sapOutboundDTO.getCharacteristicTrim());
			passMap.put("factoryStandardOptions",sapOutboundDTO.getCharacteristicFactoryStandardOptions());
			passMap.put("factoryOptions", sapOutboundDTO.getCharacteristicFactoryOptions());
			passMap.put("localOptions", sapOutboundDTO.getCharacteristicLocalOptions());
			Long materialId = siComDAO.getMaterialId(passMap);
			
			// 准备更新当前节点的导入失败信息
			TiNodeDetialPO currentNode = TiNodeDetialPO.findById(nodeId);
			currentNode.setString("IMPORT_FLAG", "10041002");
			String remark = null;
			
			if (materialId.equals(new Long(11111))) {
				
				logger.info("=============产生新的物料ID=================");
				MaterialPO material = new MaterialPO();
				
				// 获取车款信息
				TmVhclMaterialGroupVO materialGroup = siComDAO.getGroup4Id(passMap);
				if (null == materialGroup) {
					remark = "通过组合信息返回无效的车款信息";
					logger.info("=============" + remark + "=================");
					currentNode.setString("REMARK", remark);
					currentNode.saveIt();
					return false;
				}
				material.setString("MATERIAL_CODE", materialGroup.getGroupCode());
				material.setString("MATERIAL_NAME", materialGroup.getGroupName());
				material.setString("TRIM_CODE", sapOutboundDTO.getCharacteristicTrim());
				material.setString("TRIM_NAME", siComDAO.getNameByCode(sapOutboundDTO.getCharacteristicTrim(), "trimCode"));
				material.setString("COLOR_CODE", sapOutboundDTO.getCharacteristicColour());
				material.setString("COLOR_NAME", siComDAO.getNameByCode(sapOutboundDTO.getCharacteristicColour(), "colourCode"));
				material.setInteger("STATUS", 10011001);
				material.setLong("COMPANY_ID", new Long("2010010100070674"));
				material.setLong("CREATE_BY", new Long(80000001));
				material.setDate("CREATE_DATE", sysDate.getTime());
				material.saveIt();
				
				// 插入物料关系表
				MaterialGroupRPO groupR = new MaterialGroupRPO();
				Long id = siComDAO.getNewId();
				groupR.setLong("ID", id);
				groupR.setLong("MATERIAL_ID", material.getLongId());
				groupR.setLong("MATERIAL_GROUP", materialGroup.getGroupId());
				groupR.setLong("CREATE_BY", new Long(80000001));
				groupR.setDate("CREATE_DATE", sysDate.getTime());
				groupR.saveIt();
				// 车辆表赋值
				vehicle.setLong("MATERIAL_ID", material.getLongId());
			} else {
				vehicle.setLong("MATERIAL_ID", materialId);
			}
			vehicle.setString("MODEL_YEAL", sapOutboundDTO.getModelyear());
			vehicle.setString("VIN", sapOutboundDTO.getVin());

			int nodeStatus = ISAPOutBoundCommon.getCodeId(sapOutboundDTO.getActionCode());
			vehicle.setString("NODE_STATUS", nodeStatus);
			vehicle.setInteger("LOCK_STATUS", 20961001);
			Date nodeDate = siComDAO.dataTime(sapOutboundDTO.getActionDate(),sapOutboundDTO.getActionTime()); // 节点时间
			vehicle.setDate("NODE_DATE", nodeDate);
			vehicle.setLong("CREATE_BY", new Long(80000001));
			vehicle.setDate("CREATE_DATE", sysDate.getTime());
			vehicle.setInteger("LIFE_CYCLE", 11521002);
			//截取后八位
			vehicle.setString("ENGINE_NO", ISAPOutBoundCommon.getFromatEngineNumber(sapOutboundDTO.getEngineNumber())); // 发动机号
			vehicle.setDate("PRODUCT_DATE", sdf.parse(sapOutboundDTO.getProductionDate())); // 车辆表生产日期
			vehicle.setDate("ZBIL_DATE", nodeDate); // ZBIL开票时间
			// 车辆用途 XML传输为NULL时默认为NW程序不做查询
			String vehicleUsage = null;
			try {
				if (!"NW".equals(sapOutboundDTO.getVehicleUsage())) {
					vehicleUsage = siComDAO.getRelationIdInfo(sapOutboundDTO.getVehicleUsage(), "2084");
				} else {
					vehicleUsage = "null";
				}
			} catch (Exception e) {
				throw new Exception("VehicleUsage 查询错误出错，原因：车辆代码不正确" + "(" + sapOutboundDTO.getVehicleUsage() + ")", e.getCause());
			}
			
			vehicle.setString("VEHICLE_USAGE", vehicleUsage);
			
			// 取消车辆备注
			TtResourceRemarkPO conResourceRemark = TtResourceRemarkPO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
			conResourceRemark.setInteger("REMARK", 0);
			conResourceRemark.setLong("UPDATE_BY", new Long(80000002));
			conResourceRemark.setDate("UPDATE_DATE", new Date());
			conResourceRemark.saveIt();
            
			//中进中进采购价与零售价
			if (sapOutboundDTO.getWholesalePrice() != null && sapOutboundDTO.getWholesalePrice().length() > 0) {
				String wholesalePrice = sapOutboundDTO.getWholesalePrice().split("\\.")[0];
				vehicle.setDouble("WHOLESALE_PRICE", Double.valueOf(wholesalePrice));
			}
			if (sapOutboundDTO.getStandardPrice() != null && sapOutboundDTO.getStandardPrice().length() > 0) {
				String standardPrice = sapOutboundDTO.getStandardPrice().split("\\.")[0];
				vehicle.setDouble("RETAIL_PRICE", Double.valueOf(standardPrice));
			}
			if (null != sapOutboundDTO.getPortofdestination()) {
				if (sapOutboundDTO.getPortofdestination().equals("13922")) {
					vehicle.setString("VPC_PORT", "13921001");
				} else if (sapOutboundDTO.getPortofdestination().equals("20992")) {
					vehicle.setString("VPC_PORT", "13921002");
				} else if (sapOutboundDTO.getPortofdestination().equals("78015")) {
					vehicle.setString("VPC_PORT", "13921003");
				} else if (sapOutboundDTO.getPortofdestination().equals("78014")) {
					vehicle.setString("VPC_PORT", "13921004");
				}
			}
			vehicle.setInteger("ZBIL_NUMBER", 1);//一次开票次数
			vehicle.saveIt();
			
			/* 2.操作工厂订单表 */
			TtVsFactoryOrderPO facOrder = new TtVsFactoryOrderPO();
			//Long facOrderId = Long.parseLong(SequenceManager.getSequence(null));
			//facOrder.setLong("COMMON_DETAIL_ID", facOrderId);
			facOrder.setString("NODE", nodeStatus);
			facOrder.setDate("NODE_DATE", nodeDate);
			facOrder.setString("VIN", sapOutboundDTO.getVin());
			facOrder.setInteger("PRIMARY_STATUS", Integer.parseInt(siComDAO.getCodeId(sapOutboundDTO.getPrimaryStatus(), "主要状态")));
			facOrder.setInteger("SECONDARY_STATUS", Integer.parseInt(siComDAO.getCodeId(sapOutboundDTO.getSecondaryStatus(), "第二状态")));
			facOrder.setLong("MATERIAL_ID",materialId);
			facOrder.setLong("CREATE_BY",new Long(80000001));
			facOrder.setDate("CREATE_DATE", sysDate.getTime());
			facOrder.setString("SOLD_TO", sapOutboundDTO.getSoldto()); // 售达方
			facOrder.setLong("INVOICE_NUMBER", new Long(sapOutboundDTO.getInvoiceNumber())); // 发票号码
			facOrder.setDate("INVOICE_DATE", nodeDate); // ZBIL开票时间
			facOrder.saveIt();
			
			/* 3.操作记录表：详细车籍 */
			TtVsVhclChngPO vhclChng = new TtVsVhclChngPO();
			//Long vhclId = Long.parseLong(SequenceManager.getSequence(null));
			//vhclChng.setLong("VHCL_CHANGE_ID", vhclId);
			vhclChng.setLong("VEHICLE_ID",vehicle.getLongId());
			vhclChng.setInteger("CHANGE_CODE", nodeStatus);
			vhclChng.setDate("CHANGE_DATE", nodeDate);
			vhclChng.setLong("CREATE_BY", new Long(80000001));
			vhclChng.setDate("CREATE_DATE", sysDate.getTime());
			vhclChng.saveIt();
			
			return true;
		} catch (Exception e) {
			throw new Exception(e);
		}
	}
	
	/**
	 * 功能说明：修改车辆
	 * @param zvfcVO
	 * @param nodeId
	 * @throws ParseException 
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	private boolean updateMethod(SapOutboundVO sapOutboundDTO, Long nodeId) throws Exception {
		SimpleDateFormat sdfNodeDate = new SimpleDateFormat("yyyyMMddHHmmss");
		
		/* 更新操作之前判断车辆表节点时间 */
		TmVehiclePO currentVeh = TmVehiclePO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
		
		Date velNodeDate = currentVeh.getTimestamp("NODE_DATE");
		long historyDate = Long.parseLong(sdfNodeDate.format(velNodeDate));
		Date nodeDate = siComDAO.dataTime(sapOutboundDTO.getActionDate(),sapOutboundDTO.getActionTime()); // 当前节点时间
		long currentDate = Long.parseLong(sdfNodeDate.format(nodeDate));
//		int nodeStatus = Integer.parseInt(siComDAO.getCodeId(factory,sapOutboundVO.getActionCode(), "节点状态")); // 节点状态
		int nodeStatus = ISAPOutBoundCommon.getCodeId(sapOutboundDTO.getActionCode());
		
		// 准备更新当前节点的导入失败信息
		TiNodeDetialPO currentNode = TiNodeDetialPO.findById(nodeId);
		
		if((currentDate > historyDate)){
			
			//将退车信息写入退车业务表 add by huyu 2016-7-20
//			saveHistory(sapOutboundVO,currentVeh,nodeDate);
			
			//退车操作
//			rollbackVehicle(sapOutboundDTO,currentVeh,nodeDate);
			//end by huyu 2016-7-20
			
			/* 1.更新车辆表 */
			TmVehiclePO conVehc = TmVehiclePO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
			conVehc.setInteger("NODE_STATUS", nodeStatus);
			conVehc.setDate("NODE_DATE", nodeDate);
			
			// 通过指定的字段获取唯一的物料id
			Map<String, String> passMap = new HashMap<String, String>();
			passMap.put("model", sapOutboundDTO.getModel());
			passMap.put("modelYear", sapOutboundDTO.getModelyear());
			passMap.put("colour", sapOutboundDTO.getCharacteristicColour());
			passMap.put("trim", sapOutboundDTO.getCharacteristicTrim());
			passMap.put("factoryStandardOptions",sapOutboundDTO.getCharacteristicFactoryStandardOptions());
			passMap.put("factoryOptions", sapOutboundDTO.getCharacteristicFactoryOptions());
			passMap.put("localOptions", sapOutboundDTO.getCharacteristicLocalOptions());
			Long materialId = siComDAO.getMaterialId(passMap);
			
			//TiNodeDetailDcsPO conNodeDetialPO = new TiNodeDetailDcsPO();
			currentNode.setString("IMPORT_FLAG", "10041002");
			String remark = null;
			
			if (materialId.equals(new Long(11111))) {
				
				logger.info("=============产生新的物料ID=================");
				MaterialPO material = new MaterialPO();
//				Long tempMatId = siComDAO.getNewMaterialId();
//				material.setLong("MATERIAL_ID", tempMatId);
				// 获取车款信息
				TmVhclMaterialGroupVO materialGroup = siComDAO.getGroup4Id(passMap);
				if (null == materialGroup) {
					remark = "通过组合信息返回无效的车款信息";
					logger.info("=============" + remark + "=================");
					currentNode.setString("REMARK", remark);
					currentNode.saveIt();
					return false;
				}
				material.setString("MATERIAL_GROUP", materialGroup.getGroupCode());
				material.setString("MATERIAL_NAME", materialGroup.getGroupName());
				material.setString("TRIM_CODE", sapOutboundDTO.getCharacteristicTrim());
				material.setString("COLOR_CODE", sapOutboundDTO.getCharacteristicColour());
				material.setString("TRIM_NAME", siComDAO.getNameByCode(sapOutboundDTO.getCharacteristicTrim(), "trimCode"));
				material.setString("COLOR_NAME", siComDAO.getNameByCode(sapOutboundDTO.getCharacteristicColour(), "colourCode"));
				material.setInteger("STATUS", 10011001);
				material.setLong("COMPANY_ID", new Long("2010010100070674"));
				material.setLong("CREATE_BY", new Long(80000001));
				material.setDate("CREATE_DATE", sysDate.getTime());
				material.saveIt();
				
				// 插入物料关系表
				MaterialGroupRPO groupR = new MaterialGroupRPO();
				Long id = siComDAO.getNewId();
				groupR.setLong("ID", id);
				groupR.setLong("MATERIAL_ID", material.getLongId());
				groupR.setLong("MATERIAL_GROUP", materialGroup.getGroupId());
				groupR.setLong("CREATE_BY", new Long(80000001));
				groupR.setDate("CREATE_DATE", sysDate.getTime());
				groupR.saveIt();
				conVehc.setLong("MATERIAL_ID", material.getLongId());
			} else {
				conVehc.setLong("MATERIAL_ID", materialId);
			}
			conVehc.setString("MODEL_YEAR", sapOutboundDTO.getModelyear());
		    conVehc.setInteger("LIFE_CYCLE", 11521002);
		    //截取后八位
		    conVehc.setString("ENGINE_NO", ISAPOutBoundCommon.getFromatEngineNumber(sapOutboundDTO.getEngineNumber())); // 发动机号
		    conVehc.setDate("PRODUCT_DATE", sdf.parse(sapOutboundDTO.getProductionDate())); // 车辆表生产日期
		    conVehc.setDate("ZBIL_DATE", nodeDate); // ZBIL开票时间
			// 车辆用途
			String vehicleUsage = null;
			try{
			  if(!"NW".equals(sapOutboundDTO.getVehicleUsage())){
				vehicleUsage = siComDAO.getRelationIdInfo(sapOutboundDTO.getVehicleUsage(), "2084");
			  }else{
				  vehicleUsage = "null";
			  }
			}catch (Exception e) {
				throw new Exception("VehicleUsage 查询错误出错，原因：车辆代码不正确"+"("+sapOutboundDTO.getVehicleUsage()+")",e.getCause());
			}
			
			conVehc.setString("VEHICLE_USAGE", vehicleUsage);
			
			// 取消车辆备注
			TtResourceRemarkPO conResourceRemark = TtResourceRemarkPO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
			conResourceRemark.setInteger("REMARK", 0);
			conResourceRemark.setLong("UPDATE_BY", new Long(80000002));
			conResourceRemark.setDate("UPDATE_DATE", new Date());
			conResourceRemark.saveIt();
            
			//中进中进采购价与零售价
			if (sapOutboundDTO.getWholesalePrice() != null && sapOutboundDTO.getWholesalePrice().length() > 0) {
				String wholesalePrice = sapOutboundDTO.getWholesalePrice().split("\\.")[0];
				conVehc.setDouble("WHOLESALE_PRICE", Double.valueOf(wholesalePrice));
			}
			if (sapOutboundDTO.getStandardPrice() != null && sapOutboundDTO.getStandardPrice().length() > 0) {
				String standardPrice = sapOutboundDTO.getStandardPrice().split("\\.")[0];
				conVehc.setDouble("RETAIL_PRICE", Double.valueOf(standardPrice));
			}
			if (null != sapOutboundDTO.getPortofdestination()) {
				if (sapOutboundDTO.getPortofdestination().equals("13922")) {
					conVehc.setString("VPC_PORT", "13921001");
				} else if (sapOutboundDTO.getPortofdestination().equals("20992")) {
					conVehc.setString("VPC_PORT", "13921002");
				} else if (sapOutboundDTO.getPortofdestination().equals("78015")) {
					conVehc.setString("VPC_PORT", "13921003");
				} else if (sapOutboundDTO.getPortofdestination().equals("78014")) {
					conVehc.setString("VPC_PORT", "13921004");
				}
			}
			
			int zbilNumber = conVehc.getInteger("ZBIL_NUMBER")==0?1:conVehc.getInteger("ZBIL_NUMBER");//获取当前一次开票次数(退车时会修改一次开票次数，需重新获取下)
			conVehc.setInteger("ZBIL_NUMBER", zbilNumber);//一次开票次数
			conVehc.setLong("UPDATE_BY", new Long(80000002));
			conVehc.setDate("UPDATE_DATE", sysDate.getTime());
			conVehc.saveIt();
			
			/* 2.操作工厂订单表 */
			TtVsFactoryOrderPO facOrder = TtVsFactoryOrderPO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
			facOrder.setInteger("NODE", nodeStatus);
			facOrder.setTimestamp("NODE_DATE",nodeDate);
			facOrder.setInteger("PRIMARY_STATUS", Integer.parseInt(siComDAO.getCodeId(sapOutboundDTO.getPrimaryStatus(), "主要状态")));
			facOrder.setInteger("SECONDARY_STATUS", Integer.parseInt(siComDAO.getCodeId(sapOutboundDTO.getSecondaryStatus(), "第二状态")));
			facOrder.setLong("MATERIAL_ID", materialId);
			facOrder.setLong("INVOICE_NUMBER", new Long(sapOutboundDTO.getInvoiceNumber())); // 发票号码
			facOrder.setTimestamp("INVOICE_DATE", nodeDate); // ZDRL开票时间
			facOrder.setLong("UPDATE_BY", new Long(80000002));
			facOrder.setTimestamp("UPDATE_DATE", sysDate.getTime());
			facOrder.saveIt();
			
			/* 3.操作记录表：详细车籍 */
			logger.info("==========详细车籍记录开始==========");
			logger.info("==========当前节点："+currentVeh.getInteger("NODE_STATUS")+"==========");
			if(!StringUtils.isNullOrEmpty(currentVeh.getInteger("NODE_STATUS")+"")){
				if(currentVeh.getInteger("NODE_STATUS")==11511016 ||	//已实销节点下退车
						currentVeh.getInteger("NODE_STATUS")==11511015 ||	//经销商验收节点下退车
						currentVeh.getInteger("NODE_STATUS")==11511010 || //中进车款确认
						currentVeh.getInteger("NODE_STATUS")==11511012){	//发车出库
					logger.info("==========当前退车节点："+nodeStatus+"==========");
					//退车流程先写入退车记录，再写入一次开票记录
					//操作记录表：详细车籍(退车入库 )
					TtVsVhclChngPO vhclChng = new TtVsVhclChngPO();
					vhclChng.setLong("VEHICLE_ID", conVehc.getLongId());
					vhclChng.setString("CHANGE_CODE", 11511014);
					vhclChng.setDate("CHANGE_DATE", nodeDate);
					vhclChng.setLong("CREATE_BY", new Long(80000001));
					vhclChng.setDate("CREATE_DATE", sysDate.getTime());
					vhclChng.saveIt();
					//操作记录表：详细车籍(一次开票)
					TtVsVhclChngPO vhclChng1 = new TtVsVhclChngPO();
					vhclChng1.setLong("VEHICLE_ID", conVehc.getLongId());
					vhclChng1.setString("CHANGE_CODE", nodeStatus);
					vhclChng1.setDate("CHANGE_DATE", nodeDate);
					vhclChng1.setLong("CREATE_BY", new Long(80000001));
					vhclChng1.setDate("CREATE_DATE", sysDate.getTime());
					vhclChng1.saveIt();
				}else{
					logger.info("==========退回一次开票前当前节点："+currentVeh.get("NODE_STATUS")+"==========");
					if(currentVeh.getInteger("NODE_STATUS")==11511018 ||	//ZRL1-资源已分配
							currentVeh.getInteger("NODE_STATUS")==11511019 ||	//ZDRR-经销商订单确认
							currentVeh.getInteger("NODE_STATUS")==11511024 ||	//ZRL1-资源已分配
							currentVeh.getInteger("NODE_STATUS")==11511025){	//ZDRR-经销商订单确认
						logger.info("==========当前退回一次开票节点1："+currentVeh.getInteger("NODE_STATUS")+"==========");
						logger.info("==========当前退回一次开票VIN："+sapOutboundDTO.getVin()+"==========");
						if(!StringUtils.isNullOrEmpty(sapOutboundDTO.getVin())){
							//退车后将订单状态置为【已撤单】
							StringBuffer sql = new StringBuffer();
							sql.append("update TT_VS_ORDER set	\n");
							sql.append("	ORDER_STATUS='"+OemDictCodeConstants.ORDER_STATUS_09+"'	\n");//【已撤单】
							sql.append("	where VIN='"+sapOutboundDTO.getVin()+"'	\n");
							OemDAOUtil.execBatchPreparement(sql.toString(), null);
						}
					}
					//操作记录表：详细车籍(一次开票)
					TtVsVhclChngPO vhclChng = new TtVsVhclChngPO();
					vhclChng.setLong("VEHICLE_ID", conVehc.getLongId());
					vhclChng.setString("CHANGE_CODE", nodeStatus);
					vhclChng.setDate("CHANGE_DATE", nodeDate);
					vhclChng.setLong("CREATE_BY", new Long(80000001));
					vhclChng.setDate("CREATE_DATE", sysDate.getTime());
					vhclChng.saveIt();
				}
			}
			return true;
		} else{
			currentNode.setString("IMPORT_FLAG", "10041001");
			currentNode.setString("REMARK", "当前节点时间小于或等于上次节点时间，该记录不做导入操作!");
			currentNode.saveIt();
			return false;
		}
	}
	
	/**
	 * 将退车信息写入退车业务表
	 * @param sapOutboundVO
	 * @param currentVeh
	 * @param vehicleUsage
	 * @param nodeStatus
	 * @param nodeDate
	 */
	private void saveHistory(SapOutboundVO sapOutboundDTO,TmVehiclePO currentVeh,Date nodeDate) throws Exception{
		logger.info("==========将退车信息写入退车业务表开始==========");
		TtVehicleBackPO tvb = new TtVehicleBackPO();
		List<TtVsCommonResourcePO> tvcrList = TtVsCommonResourcePO.find(" VEHICLE_ID = ? ", currentVeh.getLong("VEHICLE_ID"));
		if(null != tvcrList && tvcrList.size()>0){
			logger.info("==========resourceType=="+tvcrList.get(0).getInteger("RESOURCE_TYPE")+"========");
			if(tvcrList.get(0).getInteger("RESOURCE_TYPE")!=null){
				tvb.setString("RESOURCE_BELONG", tvcrList.get(0).getInteger("RESOURCE_TYPE"));//资源归属
			}
		}
		logger.info("==========vin=="+sapOutboundDTO.getVin()+"========");
		List<TtVsOrderPO> orderList = TtVsOrderPO.find(" VIN = ? AND IS_DEL = ? ", sapOutboundDTO.getVin(), 0);
		logger.info("==========orderList=="+orderList.size()+"========");
		if(null != orderList && orderList.size()>0){
			tvb.setString("ALLOT_TYPE", orderList.get(0).getString("ORDER_TYPE"));//分配类型
			tvb.setTimestamp("ORDER_CONFIRM_DATE", orderList.get(0).getTimestamp("DEAL_ORDER_AFFIRM_DATE"));//订单确认日期
			tvb.setInteger("PAYMENT_TYPE", orderList.get(0).getInteger("PAYMENT_TYPE"));//付款方式
		}
		tvb.setLong("DEALER_ID", currentVeh.getLong("DEALER_ID"));//经销商ID
		List<TmDealerPO> dealerList = TmDealerPO.find(" DEALER_ID = ? ", currentVeh.getLong("DEALER_ID"));
		if(null != dealerList && dealerList.size()>0){
			tvb.setString("DEALER_CODE", dealerList.get(0).getString("DEALER_CODE"));//经销商代码
		}
		tvb.setString("VIN", sapOutboundDTO.getVin());//车架号
		if (!StringUtils.isNullOrEmpty(currentVeh.getInteger("VPC_PORT"))) {
			tvb.setInteger("VPC_PORT", currentVeh.getInteger("VPC_PORT"));//VPC港口
		}
		if (!StringUtils.isNullOrEmpty(currentVeh.getString("VEHICLE_USAGE"))) {
			tvb.setInteger("VEHICLE_USAGE", currentVeh.getInteger("VEHICLE_USAGE"));//车辆用途
		}
		
		List<TmAllotSupportPO> supportList = TmAllotSupportPO.find(" VIN = ? ", sapOutboundDTO.getVin());
		if(null != supportList && supportList.size()>0){
			tvb.setString("MISSION", supportList.get(0).getString("IS_SUPPORT"));//任务内/外
		}
		List<TtResourceRemarkPO> remarkList = TtResourceRemarkPO.find(" VIN = ? ", sapOutboundDTO.getVin());
		if(null!=remarkList&&remarkList.size()>0){
			tvb.setString("ALLOT_REMARK", remarkList.get(0).getString("REMARK").toString());//车辆分配备注
			tvb.setString("OTHER_REMARK", remarkList.get(0).getString("OTHER_REMARK"));//其他备注
			tvb.setInteger("IS_LOCK", remarkList.get(0).getInteger("IS_LOCK"));//是否锁定[是:1][否:0]
		}
		
		tvb.setInteger("VEHICLE_NODE", currentVeh.getInteger("NODE_STATUS"));//车辆节点
		List<TmCtcaiVehiclePO> ctcaiVehicleList = TmCtcaiVehiclePO.find(" VIN = ? ", sapOutboundDTO.getVin());
		if(null!=ctcaiVehicleList&&ctcaiVehicleList.size()>0){
			tvb.setTimestamp("PAYMENT_DATE", ctcaiVehicleList.get(0).getTimestamp("PAYMENG_DATE"));//付款日期
			tvb.setTimestamp("DELIVERY_DATE", ctcaiVehicleList.get(0).getTimestamp("START_SHIPMENT_DATE"));//起运时间
			tvb.setString("CANCEL_SCAN_ATT", ctcaiVehicleList.get(0).getString("CLORDER_SCANNING_NO"));//关单扫描件
			tvb.setString("CHECK_SCAN_ATT", ctcaiVehicleList.get(0).getString("SCORDER_SCANNING_NO"));//商检单扫描件
		}
		List<TtVsInspectionPO> tviList = TtVsInspectionPO.find(" VEHICLE_ID = ? ", currentVeh.getLong("VEHICLE_ID"));
		if(null!=tviList&&tviList.size()>0){
			tvb.setTimestamp("DEALER_RECEIVE_DATE", tviList.get(0).getTimestamp("ARRIVE_DATE"));//经销商收车日期
		}
		List<TtVsNvdrPO> nvdrList = TtVsNvdrPO.find(" VIN = ? AND IS_DEL = ? ", sapOutboundDTO.getVin(), 0);
		if(null!=nvdrList&&nvdrList.size()>0){
			tvb.setTimestamp("RETAIL_DELIVERY_DATE", nvdrList.get(0).getTimestamp("CREATE_DATE"));//零售交车日期
		}
		tvb.setTimestamp("BACK_DATE", nodeDate);//退车时间（取开票日期）
		logger.info("==========materialId=="+currentVeh.getLong("MATERIAL_ID")+"========");
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT * FROM (" + OemBaseDAO.getVwMaterialSql() + ") WHERE MATERIAL_ID="+currentVeh.getLong("MATERIAL_ID"));
		List<Map> vwList= OemDAOUtil.findAll(sb.toString(), null);
		if(null!=vwList&&vwList.size()>0){
			logger.info("==========seriesCode=="+vwList.get(0).get("SERIES_CODE")+"========");
			tvb.setString("SERIES_CODE", CommonUtils.checkNull(vwList.get(0).get("SERIES_CODE")));//车系代码
		}
		tvb.setLong("MATERIAL_ID", currentVeh.getLong("MATERIAL_ID"));//物料ID
		tvb.setLong("CREATE_BY", new Long(80000001));
		tvb.setTimestamp("CREATE_DATE", sysDate.getTime());
		tvb.saveIt();
		logger.info("==========将退车信息写入退车业务表结束==========");
	}
	
	/**
	 * 退车操作
	 * @param sapOutboundVO
	 * @param currentVeh
	 * @param nodeStatus
	 * @param nodeDate
	 * @throws Exception
	 */
	private void rollbackVehicle(SapOutboundVO sapOutboundDTO,TmVehiclePO currentVeh,Date nodeDate) throws Exception{
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		logger.info("==========退车操作开始==========");
		logger.info("==========车辆ID:"+currentVeh.get("VEHICLE_ID")+"==========");
		logger.info("==========车辆VIN:"+sapOutboundDTO.getVin()+"==========");
		logger.info("==========车辆当前节点:"+currentVeh.get("NODE_STATUS")+"==========");
		logger.info("==========车辆当前节点时间:"+sdf.format(nodeDate)+"==========");
		int zbilNumber = ((Long) currentVeh.get("ZBIL_NUMBER")).intValue()==0?1:((int)currentVeh.get("ZBIL_NUMBER"));//获取当前一次开票次数
		logger.info("==========当前一次开票次数:"+currentVeh.get("ZBIL_NUMBER")+"==========");
		String nodeStatus = CommonUtils.checkNull(currentVeh.get("NODE_STATUS"));
		if(!StringUtils.isNullOrEmpty(nodeStatus)){
			if(nodeStatus.equals("11511016")){//已实销节点下退车
				//将退车信息写入退车业务表
				saveHistory(sapOutboundDTO,currentVeh,nodeDate);
				
				/* 1.更新车辆表 */
				TmVehiclePO setVehiclePo= TmVehiclePO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				setVehiclePo.setInteger("ZBIL_NUMBER", zbilNumber+1);//一次开票次数
				setVehiclePo.setLong("DEALER_ID", 0L);//退回到【一次开票节点】时需置为空
				setVehiclePo.setInteger("IS_SEND", 0);//【下发状态】改成未下发
				setVehiclePo.setLong("UPDATE_BY", new Long(80000002));
				setVehiclePo.setDate("UPDATE_DATE", sysDate.getTime());
				setVehiclePo.saveIt();
				
				//a)实销上报表：逻辑删除、写入退车标识、写入退车时间
				TtVsSalesReportPO salseReportKey = TtVsSalesReportPO.findFirst(" VEHICLE_ID = ? ", currentVeh.getLong("VEHICLE_ID"));
				salseReportKey.setInteger("IS_DEL", 1);
				salseReportKey.setInteger("IS_BACK", 1);//是否退车[0:否][1:是]
				salseReportKey.setDate("BACK_DATE", nodeDate);//退车日期
				salseReportKey.setLong("UPDATE_BY", 2222222222l);
				salseReportKey.setDate("UPDATE_DATE", new Date());
				salseReportKey.saveIt();
				//b)发票扫描表：逻辑删除
				TtVsSalesReportInvoicePO invoiceKey = TtVsSalesReportInvoicePO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				invoiceKey.setInteger("IS_DEL", 1);
				invoiceKey.setDate("UPDATE_DATE", new Date());
				invoiceKey.saveIt();
				//c)客户信息表：逻辑删除
				String vehicleId = CommonUtils.checkNull(currentVeh.getLong("VEHICLE_ID"));
				if(!StringUtils.isNullOrEmpty(vehicleId)){
					List<TtVsSalesReportPO> salesReportList = TtVsSalesReportPO.find(" VEHICLE_ID = ? and STATUS = ?", currentVeh.getLong("VEHICLE_ID"), 10011001);
					//保客推荐老客户在客户表不存在情况下过滤以下逻辑             2016-4-21bug版本
					if(salesReportList != null && salesReportList.size() > 0){
						String ctmId = CommonUtils.checkNull(salesReportList.get(0).getLong("CTM_ID"));
						logger.info("==========CTM_ID:"+ctmId+"==========");
						if(!StringUtils.isNullOrEmpty(ctmId)){
							TtVsCustomerPO customerKey = TtVsCustomerPO.findFirst(" CTM_ID = ? ", salesReportList.get(0).getLong("CTM_ID"));
							customerKey.setInteger("IS_DEL", 1);
							customerKey.setLong("UPDATE_BY", 2222222222l);
							customerKey.setDate("UPDATE_DATE", new Date());
							customerKey.saveIt();
						}
					}
				}
				//d)零售上报表：逻辑删除
				TtVsNvdrPO vndrKey = TtVsNvdrPO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				vndrKey.setInteger("IS_DEL", 1);
				vndrKey.setLong("UPDATE_BY", 2222222222l);
				vndrKey.setDate("UPDATE_DATE", new Date());
				vndrKey.saveIt();
				//e)车辆验收表：逻辑删除
				TtVsInspectionPO inspectionKey = TtVsInspectionPO.findFirst(" VEHICLE_ID = ? ", currentVeh.getLong("VEHCILE_ID"));
				inspectionKey.setInteger("IS_DEL", 1);
				inspectionKey.setLong("UPDATE_BY", 2222222222l);
				inspectionKey.setDate("UPDATE_DATE", new Date());
				inspectionKey.saveIt();
				//f)维修工单表：逻辑删除
				TtWrRepairPO twrKey = TtWrRepairPO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				twrKey.setInteger("IS_DEL", 1);
				twrKey.setLong("UPDATE_BY", 2222222222l);
				twrKey.setDate("UPDATE_DATE", new Date());
				twrKey.saveIt();
				//f)索赔表：逻辑删除
				TtWrClaimPO twcKey = TtWrClaimPO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				twcKey.setInteger("IS_DEL", 1);
				twcKey.setLong("UPDATE_BY", 2222222222l);
				twcKey.setDate("UPDATE_DATE", new Date());
				twcKey.saveIt();
				//g)车辆表：车辆节点更新为一次开票，更新车辆节点日期
				// 设置公共已取消
				OemDAOUtil.execBatchPreparement("update TT_VS_COMMON_RESOURCE set UPDATE_BY=2222222222,UPDATE_DATE=now(),STATUS="
									+ OemDictCodeConstants.COMMON_RESOURCE_STATUS_03 + " where VEHICLE_ID="+currentVeh.getLong("VEHICLE_ID"), null);
				
				OemDAOUtil.execBatchPreparement("update TT_VS_COMMON_RESOURCE_DETAIL set STATUS=" + OemDictCodeConstants.STATUS_DISABLE
									+ " where VEHICLE_ID="+currentVeh.getLong("VEHICLE_ID"), null);
				
				//取消车辆备注
				TtResourceRemarkPO conResourceRemark = TtResourceRemarkPO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				conResourceRemark.setString("REMARK", 0);//车辆分配备注
				conResourceRemark.setString("SPECIAL_REMARK", 0);//OTD备注
				conResourceRemark.setString("OTHER_REMARK", "-");//其它备注
				conResourceRemark.setLong("UPDATE_BY", new Long(80000002));
				conResourceRemark.setDate("UPDATE_DATE", new Date());
				conResourceRemark.saveIt();
				
				if(!StringUtils.isNullOrEmpty(sapOutboundDTO.getVin())){
					//退车后将订单状态置为【已撤单】
					OemDAOUtil.execBatchPreparement("update TT_VS_ORDER set	IS_DEL=1 where VIN='"+sapOutboundDTO.getVin()+"'", null);
					//退车后将一次开票节点后的字段置空
					OemDAOUtil.execBatchPreparement("update TM_CTCAI_VEHICLE set IS_DEL=1 where VIN='"+sapOutboundDTO.getVin()+"'", null);
				}
			}else if(nodeStatus.equals(OemDictCodeConstants.VEHICLE_NODE_15.toString())){//经销商验收节点下退车

				//将退车信息写入退车业务表
				saveHistory(sapOutboundDTO,currentVeh,nodeDate);
				
				/* 1.更新车辆表 */
				TmVehiclePO conVehiclePo = TmVehiclePO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				conVehiclePo.setInteger("ZBIL_NUMBER",zbilNumber+1);//一次开票次数
				conVehiclePo.setLong("DEALER_ID",0L);//退回到【一次开票节点】时需置为空
				conVehiclePo.setInteger("IS_SEND",0);//【下发状态】改成未下发
				conVehiclePo.setLong("UPDATE_BY",new Long(80000002));
				conVehiclePo.setTimestamp("UPDATE_DATE",sysDate.getTime());
				conVehiclePo.saveIt();
				
				//a)车辆验收表：逻辑删除
				TtVsInspectionPO inspectionValue = TtVsInspectionPO.findFirst(" VEHICLE_ID = ? ", currentVeh.getLong("VEHICLE_ID"));
				inspectionValue.setInteger("IS_DEL",1);
				inspectionValue.setLong("UPDATE_BY",22222222L);
				inspectionValue.setTimestamp("UPDATE_DATE",new Date());
				inspectionValue.saveIt();
				//b)车辆表：车辆节点更新为一次开票，更新车辆节点日期
				// 设置公共已取消
				OemDAOUtil.execBatchPreparement("update TT_VS_COMMON_RESOURCE set UPDATE_BY = 2222222222, UPDATE_DATE = now(), STATUS = "
									+ OemDictCodeConstants.COMMON_RESOURCE_STATUS_03
									+ " where VEHICLE_ID=" + currentVeh.getLong("VEHICLE_ID"), null);
		
				// 公共资源明细设为无效
				OemDAOUtil.execBatchPreparement("update TT_VS_COMMON_RESOURCE_DETAIL set STATUS = " + OemDictCodeConstants.STATUS_DISABLE
									+ " where VEHICLE_ID=" + currentVeh.getLong("VEHICLE_ID"), null);
				
				//取消车辆备注
				TtResourceRemarkPO desResourceRemark=TtResourceRemarkPO.findFirst("", sapOutboundDTO.getVin());
				desResourceRemark.setInteger("REMARK",0);//车辆分配备注
				desResourceRemark.setInteger("SPECIAL_REMARK",0);//OTD备注
				desResourceRemark.setString("OTHER_REMARK","-");//其它备注
				desResourceRemark.setLong("UPDATE_BY",new Long(80000002));
				desResourceRemark.setTimestamp("UPDATE_DATE",new Date());
				desResourceRemark.saveIt();
				
				if(!StringUtils.isNullOrEmpty(sapOutboundDTO.getVin())){
					//退车后将订单状态置为【已撤单】
					OemDAOUtil.execBatchPreparement("update TT_VS_ORDER set	IS_DEL=1 where VIN='"+sapOutboundDTO.getVin()+"'", null);
					//退车后将一次开票节点后的字段置空
					OemDAOUtil.execBatchPreparement("update TM_CTCAI_VEHICLE set IS_DEL=1 where VIN='"+sapOutboundDTO.getVin()+"'", null);
				}
			}else if(nodeStatus.equals("11511010") || //中进车款确认
					 nodeStatus.equals("11511012")){ //发车出库
				
				//将退车信息写入退车业务表
				saveHistory(sapOutboundDTO,currentVeh,nodeDate);
				
				/* 1.更新车辆表 */
				
				TmVehiclePO tvPO = TmVehiclePO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				tvPO.setString("ZBIL_NUMBER", zbilNumber+1);//一次开票次数
				tvPO.setLong("DEALER_ID", 0L);//退回到【一次开票节点】时需置为空
				tvPO.setInteger("IS_SEND", 0);//【下发状态】改成未下发
				tvPO.setLong("UPDATE_BY", new Long(80000002));
				tvPO.setTimestamp("UPDATE_DATE", sysDate.getTime());
				tvPO.saveIt();
				
				//a)车辆表：车辆节点更新为一次开票，更新车辆节点日期
				// 设置公共已取消
				OemDAOUtil.execBatchPreparement("update TT_VS_COMMON_RESOURCE set UPDATE_BY=2222222222,UPDATE_DATE=now(),STATUS="
								+ OemDictCodeConstants.COMMON_RESOURCE_STATUS_03
								+ " where VEHICLE_ID=" + currentVeh.getLong("VEHICLE_ID"), null);
		
				// 公共资源明细设为无效
				OemDAOUtil.execBatchPreparement(
						"update TT_VS_COMMON_RESOURCE_DETAIL set STATUS=" + OemDictCodeConstants.STATUS_DISABLE
								+ " where VEHICLE_ID=" + currentVeh.getLong("VEHICLE_ID"), null);

				//取消车辆备注
				TtResourceRemarkPO conResourceRemark = TtResourceRemarkPO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				conResourceRemark.setString("REMARK", 0);//车辆分配备注
				conResourceRemark.setString("SPECIAL_REMARK", 0);//OTD备注
				conResourceRemark.setString("OTHER_REMARK", "-");//其它备注
				conResourceRemark.setLong("UPDATE_BY", new Long(80000002));
				conResourceRemark.setTimestamp("UPDATE_DATE", new Date());
				conResourceRemark.saveIt();
				
				if(!StringUtils.isNullOrEmpty(sapOutboundDTO.getVin())){
					//退车后将订单状态置为【已撤单】
					OemDAOUtil.execBatchPreparement("update TT_VS_ORDER set IS_DEL=1 where VIN='"+sapOutboundDTO.getVin()+"'", null);
					//退车后将一次开票节点后的字段置空
					OemDAOUtil.execBatchPreparement("update TM_CTCAI_VEHICLE set IS_DEL=1 where VIN='"+sapOutboundDTO.getVin()+"'", null);
				}
			}else{
				//其他节点和一次开票节点次数大于1 不做处理，
			}
		}
		logger.info("==========退车操作结束==========");
	}

}
 