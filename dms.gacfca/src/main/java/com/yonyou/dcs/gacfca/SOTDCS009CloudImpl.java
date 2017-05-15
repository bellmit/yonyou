package com.yonyou.dcs.gacfca;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsUTestDriveDTO;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsUTestDrivePO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SOTDCS009CloudImpl implements SOTDCS009Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsUTestDriveDTO> dto) throws Exception {
		String msg = "1";
		logger.info("====更新客户信息（试乘试驾）(DMS更新)接收开始===="); 
		for (TiDmsUTestDriveDTO entry : dto) {
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

	public void insertData(TiDmsUTestDriveDTO vo) throws Exception {
		/**
		   Map<String, Object> map =
		   deCommonDao.getSaDcsDealerCode(vo.getEntityCode()); String dealerCode
		   = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		 */
		TiDmsUTestDrivePO insertPO = new TiDmsUTestDrivePO();
		insertPO.setString("UNIQUENESS_ID", vo.getUniquenessID());// DMS客户唯一ID
		if (null != vo.getFCAID()) {
			insertPO.setInteger("FCA_ID", new Long(vo.getFCAID()));// DMS客户唯一ID
		}
		insertPO.setDate("TEST_DRIVE_TIME", vo.getTestDriveTime());// 试驾时间
		insertPO.setString("TEST_BRAND_ID", vo.getTestBrandId());// 试驾的品牌
		insertPO.setString("TEST_MODE_ID", vo.getTestModelID());// 试驾的车型
		insertPO.setString("TEST_CAR_STYLE_ID", vo.getTestCarStyleID());// 试驾的车款
		insertPO.setString("DRIVE_ROAD_DIC_ID", vo.getDriveRoadDicID());// 试驾路线
		insertPO.setString("IDENTIFICATION_NO", vo.getIdentificationNO());// 身份证号
		insertPO.setString("DEALER_CODE", vo.getDealerCode());// 经销商代码
		insertPO.setString("DEALER_USER_ID", vo.getDealerUserID());// 销售人员ID
		insertPO.setTimestamp("UPDATE_DATE", vo.getUpdateDate());// 创建日期
		insertPO.setString("IS_SEND", "0");// 同步标志
		insertPO.setLong("UPDATE_BY", DEConstant.DE_UPDATE_BY);// 创建者
		// 插入数据
		insertPO.insert();

	}

}
