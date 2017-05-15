package com.infoeai.eai.action.ncserp.common;
/**
 * ISAPOutBound 通用的方法
 */
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import com.infoeai.eai.dao.FuturesResourceSettingDao;
import com.infoeai.eai.wsServer.SapDcsService.SapOutboundVO;
import com.yonyou.dms.common.domains.PO.basedata.TiNodeDetialPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;



public class ISAPOutBoundCommon {
	
	/**
	 * SAP导入时符合期货资源设定条件的直接进入公共资源池 
	 * 
	 */
	public static int methodToFuturesResource(String vin) throws Exception{
		FuturesResourceSettingDao dao = FuturesResourceSettingDao.getInstance();
		int total = 0;
		try {
			// 调用期货资源设定判断方法
			if (!vin.equals("")) {
				total += dao.checkFuturesResourceSetting(vin);
			}
			return total;
		} catch (SQLException e) {
			e.printStackTrace();
			throw new Exception(e);
		}
	}
	
	/**
	 * 功能说明：插入记录日志表
	 * 
	 * 在校验数据之前，将数据插入记录表(插入的时候默认记录成功导入，后面更新的时候修改未导入原因)
	 * @throws Exception 
	 * 
	 */
	public static Long insertNodeDetail(SapOutboundVO sapOutboundDTO) throws Exception {
		//sapOutboundVO send value
		try {
			TiNodeDetialPO nodePO = new TiNodeDetialPO();
			nodePO.setString("ACTION_CODE", sapOutboundDTO.getActionCode());
			nodePO.setString("ACTION_DATE", sapOutboundDTO.getActionDate());
			nodePO.setString("ACTION_TIME", sapOutboundDTO.getActionTime());
			nodePO.setString("VIN", sapOutboundDTO.getVin());
			//截取后八位
			//nodePO.setEngineNumber(getFromatEngineNumber(sapOutboundVO.getEngineNumber()));
			//接口日志表不管发动机号长度大于10还是小于10或等于10都插入。
			nodePO.setString("ENGINE_NUMBER", sapOutboundDTO.getEngineNumber());
			nodePO.setString("PRODUCTION_DATE", sapOutboundDTO.getProductionDate());
			nodePO.setString("PRIMARY_STATUS", sapOutboundDTO.getPrimaryStatus());
			nodePO.setString("SECONDARY_STATUS", sapOutboundDTO.getSecondaryStatus());
			nodePO.setString("SHIP_DATE", sapOutboundDTO.getShipDate());
			nodePO.setString("ETA", sapOutboundDTO.getEta());
			nodePO.setString("MODEL", sapOutboundDTO.getModel());
			nodePO.setString("MODEL_YEAR", sapOutboundDTO.getModelyear());
			nodePO.setString("COLOUR", sapOutboundDTO.getCharacteristicColour());
			nodePO.setString("TRIM_CODE", sapOutboundDTO.getCharacteristicTrim());
			nodePO.setString("STANDARD_OPTIONS", sapOutboundDTO.getCharacteristicFactoryStandardOptions());
			nodePO.setString("OTHER_OPTIONS", sapOutboundDTO.getCharacteristicFactoryOptions());
			nodePO.setString("LOCAL_OPTIONS", sapOutboundDTO.getCharacteristicLocalOptions());
			nodePO.setString("SOLD_TO", sapOutboundDTO.getSoldto());
			nodePO.setString("INVOICE_NO",sapOutboundDTO.getInvoiceNumber());
			nodePO.setString("VEHICLE_USAGE", sapOutboundDTO.getVehicleUsage());//车辆用途
			if (null != sapOutboundDTO.getStandardPrice()
					&& sapOutboundDTO.getStandardPrice().length() != 0) {
				nodePO.setDouble("RETAIL_PRICE", new Double(sapOutboundDTO.getStandardPrice()));
			}
			if (null != sapOutboundDTO.getWholesalePrice()
					&& sapOutboundDTO.getWholesalePrice().length() != 0) {
				nodePO.setDouble("WHOLESALE_PRICE", new Double(sapOutboundDTO.getWholesalePrice()));
			}
			nodePO.setString("VPC_PORT", sapOutboundDTO.getPortofdestination());
			
			//no sapOutboundVO send value
			/*Long nodeId = Long.parseLong(SequenceManager.getSequence(null));
			nodePO.setLong("SEQUENCE_ID", nodeId);*/
			nodePO.setDate("SCAN_DATE", Calendar.getInstance().getTime());//扫描时间
			nodePO.setString("IMPORT_FLAG", "10041001");
			nodePO.saveIt();
			
			// 返回成功标识
			return nodePO.getLongId();
		} catch (Exception e) {
			throw new Exception("============"+sapOutboundDTO.getActionCode()+"的actionCode,接口表处理异常=================="+e);
		}
		
	}
	
	/**
	 * 发动机号接口由原来的8位调整为10位，要求在数据接收时，能截取其后8位正常插入接口表及相关业务逻辑
	 * @return
	 */
	public static String getFromatEngineNumber(String engineNumber) {
		if (null != engineNumber && engineNumber.length()==10) {
			return engineNumber.substring(2, engineNumber.length());
		}
		return "";
	}
	
	/**
	 * 返回code_id
	 */
	@SuppressWarnings("rawtypes")
	public static Integer getCodeId(String actionCode) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT CODE_ID FROM TC_ACTIONCODE_MAPPING_DCS WHERE NSCERP_CODE = '"+actionCode+"' \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if (null == list || list.size()==0) {//如果是中的  或者没有值得  则返回null
			return null;
		}
		return (Integer) list.get(0).get("CODE_ID");
	}
	
	
	

}
