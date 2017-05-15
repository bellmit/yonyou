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
import com.infoeai.eai.vo.TmVhclMaterialGroupVO;
import com.infoeai.eai.wsServer.SapDcsService.SapOutboundVO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialGroupRPO;
import com.yonyou.dms.common.domains.PO.basedata.MaterialPO;
import com.yonyou.dms.common.domains.PO.basedata.TiNodeDetialPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsFactoryOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;

/**
 * @author Benzc
 * @date 2017-04-26
 * ZPDF
 * PDI检查失败
 */
@Service
public class ZPDFExecutorImpl extends BaseService implements ZPDFExecutor {
    
	private static Logger logger = LoggerFactory.getLogger(ZPDFExecutorImpl.class);
	Calendar sysDate = Calendar.getInstance();

	@Autowired
	SICommonDao siComDAO;
	
	/**
	 * 向接口表和业务表写数据
	 */
	@Override
	public String execute(ISAPOutBoundVO dto) throws Exception {
		logger.info("============ZPDF处理开始==================");
		boolean flag = false;//处理结果   false:失败　　true：成功
		Long nodeId = null;
		SapOutboundVO sapOutboundDTO = (SapOutboundVO) dto;
		String messg = null;
		beginDbService();
		try {
			logger.info("=============插入记录日志表开始============");
			nodeId = ISAPOutBoundCommon.insertNodeDetail(sapOutboundDTO);
			logger.info("=============插入记录日志表结束============");
			String vin = sapOutboundDTO.getVin();
			// 如果校验成功，直接操作业务表
			if (swt2Dcs01Check(nodeId, sapOutboundDTO)) {
				// 判断车辆是否存在
				List<TmVehiclePO> flagVehicleList = TmVehiclePO.findBySQL("SELECT * FROM TM_VEHICLE_DEC WHERE VIN=?", vin);
				if (null!=flagVehicleList && flagVehicleList.size()>0) {
					// 做更新操作
					flag = updateMethod(sapOutboundDTO, nodeId);
				} else {
					// 做插入操作
					flag = insertMethod(sapOutboundDTO, nodeId);
				}
			}else {
				logger.info("============ZPDF校验失败==================");
			}
			// 期货资源预定
			int total = ISAPOutBoundCommon.methodToFuturesResource(vin);
			dbService.endTxn(true);
			logger.info("-----调用期货资源设定规则结束,共有" + total + "条数据进入公共资源池-----");
		} catch (Throwable e) {
			logger.info("=============ZPDF处理异常============");
			logger.error(e.getMessage(), e);
			dbService.endTxn(false);
			//messg = Utility.getErrorStack(e, EAIConstant.ERROR_STACK_MAX_LENGTH);
			throw new Exception("============ZPDF处理异常=================="+e);
		}finally {
			Base.detach();
			dbService.clean();
			if(messg != null && !messg.equals("")){
				logger.info("=============ZPDF插入或更新业务表失败，记录错误信息到接口日志表开始============");
				//EaiUtil.updTiNodeDetial(nodeId, messg);
				logger.info("=============ZPDF插入或更新业务表失败，记录错误信息到接口日志表完成============");
			}
		}
		logger.info("============ZPDF处理结束==================");
		//返回处理结果
		if (flag) {
			return "02";
		} else {
			return "01";
		}
	}
	
	/**
	 * actionCode验证
	 */
	@Override
	public boolean isValid(ISAPOutBoundVO vo) throws Exception{
		try {
			return "ZPDF".equals(vo.getActionCode());
		} catch (Exception e) {
			throw new Exception("============ZPDF的actionCode验证处理异常=================="+e);
		}
	}
    
	/**
	 * 数据验证
	 * @param nodeId
	 * @param sapOutboundVO
	 * @param factory
	 * @return
	 */
	@SuppressWarnings("static-access")
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
		return true;
	}
    
	/**
	 * 功能说明：插入车辆
	 * @param zvfcVO
	 * @param nodeId
	 * @throws Exception 
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	private boolean insertMethod(SapOutboundVO sapOutboundDTO, Long nodeId) throws Exception {
		try {
			/* 1.操作车辆表 */
			TmVehiclePO vehicle = new TmVehiclePO();
			//Long vehicleId = Long.parseLong(SequenceManager.getSequence(null));
			//vehicle.setLong("VEHICLE_ID", vehicleId);
			vehicle.setInteger("ORG_TYPE", 10191001);
			// 查找唯一的orgId
			List<Object> queryList = new ArrayList<Object>();
			queryList.add(10191001);
			queryList.add(new Long(-1));
			List<Map> list = OemDAOUtil.findAll("SELECT * FROM TM_ORG WHERE ORG_TYPE=? AND PARENT_ORG_ID=?", queryList);
			Map map = list.get(0);
			Long orgId = (Long) map.get("ORG_ID");
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
//			currentNode.setLong("SEQUENCE_ID", nodeId);
//			TiNodeDetialPO conNodeDetialPO = new TiNodeDetialPO();
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
//					conNodeDetialPO.findBySQL("SELECT * FROM TI_NODE_DETAIL_DCS WHERE REMARK=?",remark);
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
				material.setTimestamp("CREATE_DATE", sysDate.getTime());
				material.saveIt();
				// 插入物料关系表
				MaterialGroupRPO groupR = new MaterialGroupRPO();
				Long id = siComDAO.getNewId();
				groupR.setLong("ID", id);
				groupR.setLong("MATERIAL_ID", material.getLongId());
				groupR.setLong("MATERIAL_GROUP", materialGroup.getGroupId());
				groupR.setLong("CREATE_BY", new Long(80000001));
				groupR.setTimestamp("CREATE_DATE", sysDate.getTime());
				groupR.saveIt();
				// 车辆表赋值
				vehicle.setLong("MATERIAL_ID", material.getLongId());
			} else {
				vehicle.setLong("MATERIAL_ID", materialId);
			}
			vehicle.setString("MODEL_YEAL", sapOutboundDTO.getModelyear());
			vehicle.setString("VIN", sapOutboundDTO.getVin());
//			int nodeStatus = Integer.parseInt(siComDAO.getCodeId(factory,sapOutboundVO.getActionCode(), "节点状态")); // 节点状态
			int nodeStatus = ISAPOutBoundCommon.getCodeId(sapOutboundDTO.getActionCode());
			vehicle.setString("NODE_STATUS", nodeStatus);
			vehicle.setInteger("LOCK_STATUS", 20961001);
			vehicle.setInteger("LIFE_CYCLE", 11521002);
			Date nodeDate = siComDAO.dataTime(sapOutboundDTO.getActionDate(),sapOutboundDTO.getActionTime()); // 节点时间
			vehicle.setTimestamp("NODE_DATE", nodeDate);
			vehicle.setLong("CREATE_BY", new Long(80000001));
			vehicle.setTimestamp("CREATE_DATE", sysDate.getTime());
			// ZPDF时清空当前车辆的OrgStorageDate时间
						siComDAO.cealerOrgStorDate(sapOutboundDTO.getVin());
			/* 2.操作工厂订单表 */
			TtVsFactoryOrderPO facOrder = new TtVsFactoryOrderPO();
			//Long facOrderId = Long.parseLong(SequenceManager.getSequence(null));
			//facOrder.setLong("COMMON_DETAIL_ID", facOrderId);
			facOrder.setString("NODE", nodeStatus);
			facOrder.setTimestamp("NODE_DATE", nodeDate);
			facOrder.setString("VIN", sapOutboundDTO.getVin());
			facOrder.setInteger("PRIMARY_STATUS", Integer.parseInt(siComDAO.getCodeId(sapOutboundDTO.getPrimaryStatus(), "主要状态")));
			facOrder.setInteger("SECONDARY_STATUS", Integer.parseInt(siComDAO.getCodeId(sapOutboundDTO.getSecondaryStatus(), "第二状态")));
			facOrder.setLong("MATERIAL_ID",materialId);
			facOrder.setLong("CREATE_BY",new Long(80000001));
			facOrder.setTimestamp("CREATE_DATE", sysDate.getTime());
			facOrder.saveIt();
			/* 3.操作记录表：详细车籍 */
			TtVsVhclChngPO vhclChng = new TtVsVhclChngPO();
			//Long vhclId = Long.parseLong(SequenceManager.getSequence(null));
			//vhclChng.setLong("VHCL_CHANGE_ID", vhclId);
			vhclChng.setLong("VEHICLE_ID",vehicle.getLongId());
			vhclChng.setString("CHANGE_CODE", nodeStatus);
			vhclChng.setTimestamp("CHANGE_DATE", nodeDate);
			vhclChng.setLong("CREATE_BY", new Long(80000001));
			vhclChng.setTimestamp("CREATE_DATE", sysDate.getTime());
			vhclChng.saveIt();
			return true;
		} catch (Throwable e) {
			throw new Exception(e);
		}
	}
    
	/**
	 * * 功能说明：修改车辆
	 * @param zvfcVO
	 * @param nodeId
	 * @throws Exception 
	 */
	@SuppressWarnings({ "static-access", "rawtypes" })
	private boolean updateMethod(SapOutboundVO sapOutboundDTO, Long nodeId) throws Exception {
		try {
			SimpleDateFormat sdfNodeDate = new SimpleDateFormat("yyyyMMddHHmmss");
			/* 更新操作之前判断车辆表节点时间 */
			TmVehiclePO currentVeh = TmVehiclePO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
			Date velNodeDate = currentVeh.getTimestamp("NODE_DATE");
			long historyDate = Long.parseLong(sdfNodeDate.format(velNodeDate));
			Date nodeDate = siComDAO.dataTime(sapOutboundDTO.getActionDate(),sapOutboundDTO.getActionTime()); // 当前节点时间
			long currentDate = Long.parseLong(sdfNodeDate.format(nodeDate));
//			int nodeStatus = Integer.parseInt(siComDAO.getCodeId(factory,sapOutboundVO.getActionCode(), "节点状态")); // 节点状态
			int nodeStatus = ISAPOutBoundCommon.getCodeId(sapOutboundDTO.getActionCode());
			// 准备更新当前节点的导入失败信息
			TiNodeDetialPO currentNode = TiNodeDetialPO.findById(nodeId);
			if((currentDate > historyDate)){
				/* 1.更新车辆表 */
				TmVehiclePO conVehc = TmVehiclePO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				conVehc.setInteger("NODE_STATUS", nodeStatus);
				conVehc.setTimestamp("NODE_DATE", nodeDate);
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
//					Long tempMatId = siComDAO.getNewMaterialId();
//					material.setLong("MATERIAL_ID", tempMatId);
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
					material.setTimestamp("CREATE_DATE", sysDate.getTime());
					material.saveIt();
					// 插入物料关系表
					MaterialGroupRPO groupR = new MaterialGroupRPO();
					Long id = siComDAO.getNewId();
					groupR.setLong("ID", id);
					groupR.setLong("MATERIAL_ID", material.getLongId());
					groupR.setLong("MATERIAL_GROUP", materialGroup.getGroupId());
					groupR.setLong("CREATE_BY", new Long(80000001));
					groupR.setTimestamp("CREATE_DATE", sysDate.getTime());
					groupR.saveIt();
					conVehc.setLong("MATERIAL_ID", material.getLongId());
				} else {
					conVehc.setLong("MATERIAL_ID", materialId);
				}
				conVehc.setString("MODEL_YEAR", sapOutboundDTO.getModelyear());
			    conVehc.setInteger("LIFE_CYCLE", 11521002);
			    conVehc.setLong("UPDATE_BY", new Long(80000002));
				conVehc.setTimestamp("UPDATE_DATE", sysDate.getTime());
				conVehc.saveIt();
				siComDAO.cealerOrgStorDate(sapOutboundDTO.getVin());
				/* 2.操作工厂订单表 */
				TtVsFactoryOrderPO facOrder = TtVsFactoryOrderPO.findFirst(" VIN = ? ", sapOutboundDTO.getVin());
				facOrder.setInteger("NODE", nodeStatus);
				facOrder.setTimestamp("NODE_DATE",nodeDate);
				facOrder.setInteger("PRIMARY_STATUS", Integer.parseInt(siComDAO.getCodeId(sapOutboundDTO.getPrimaryStatus(), "主要状态")));
				facOrder.setInteger("SECONDARY_STATUS", Integer.parseInt(siComDAO.getCodeId(sapOutboundDTO.getSecondaryStatus(), "第二状态")));
				facOrder.setLong("MATERIAL_ID", materialId);
				facOrder.setLong("UPDATE_BY", new Long(80000002));
				facOrder.setTimestamp("UPDATE_DATE", sysDate.getTime());
				facOrder.saveIt();
				/* 3.操作记录表：详细车籍 */
				TtVsVhclChngPO vhclChng = new TtVsVhclChngPO();
				//Long vhclId = Long.parseLong(SequenceManager.getSequence(null));
				//vhclChng.setVhclChangeId(vhclId);
				vhclChng.setLong("VEHICLE_ID", currentVeh.getLongId());
				vhclChng.setString("CHANGE_CODE", nodeStatus);
				vhclChng.setTimestamp("CHANGE_DATE", nodeDate);
				vhclChng.setLong("CREATE_BY", new Long(80000001));
				vhclChng.setTimestamp("CREATE_DATE", sysDate.getTime());
				vhclChng.saveIt();
				return true;
			} else {
				currentNode.setString("IMPORT_FLAG", "10041001");
				currentNode.setString("REMARK", "当前节点时间小于或等于上次节点时间，该记录不做导入操作!");
				currentNode.saveIt();
				return false;
			}
		} catch (Exception e) {
			throw new Exception(e);
		}
		
	}
    
	

}
