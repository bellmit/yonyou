package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsNTestDriveCarDto;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsNTestDriveCarPO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SOTDCS014CloudImpl extends BaseCloudImpl implements SOTDCS014Cloud {

	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);
	
	@Override
	public String handleExecutor(List<TiDmsNTestDriveCarDto> dto) throws Exception {
		String msg = "1";
		logger.info("====更新客户信息（试乘试驾）(DMS更新)接收开始===="); 
		for (TiDmsNTestDriveCarDto entry : dto) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("更新客户信息（试乘试驾）(DMS更新)数据接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====更新客户信息（试乘试驾）(DMS更新)接收结束===="); 
		return msg;
	}
	
	public void insertData(TiDmsNTestDriveCarDto vo) throws Exception {
		// Map<String, Object> map =
		// deCommonDao.getDcsCompanyCode(vo.getDealerCode());
		// String dealerCode = String.valueOf(map.get("DCS_CODE"));//上报经销商信息
		TiDmsNTestDriveCarPO insertPO = new TiDmsNTestDriveCarPO();
		insertPO.setString("DEALER_USER_ID", vo.getDealerUserId());// 销售人员ID
		insertPO.setString("DEALER_CODE", vo.getDealerCode());// 经销商代码
		insertPO.setString("VIN_CODE", vo.getVinCode());// 车辆识别码
		insertPO.setString("LICENSE_CODE", vo.getLicenseCode());// 车牌号
		insertPO.setString("CAR_STATURS", vo.getCarStaturs());// 状态
		insertPO.setString("MODEL_ID", vo.getModelId());// DMS 车型ID
		insertPO.setString("MODEL_NAME", vo.getModelName());// DMS 车型名称
		insertPO.setString("IS_SEND", "0");// 同步标志
		insertPO.setTimestamp("CREATE_DATE", vo.getCreateDate());// 创建时间
		insertPO.setTimestamp("UPDATE_DATE", vo.getUpdateDate());// 更新时间
		insertPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);// 创建者
		// 插入数据
		insertPO.insert();
	}

}
