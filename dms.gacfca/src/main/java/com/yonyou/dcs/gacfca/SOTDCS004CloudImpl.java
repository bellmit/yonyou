package com.yonyou.dcs.gacfca;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsNTestDriveDTO;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsNTestDrivePO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SOTDCS004CloudImpl
 * Description: 创建客户信息（试乘试驾）(DMS新增)接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午4:51:25
 */
@Service
public class SOTDCS004CloudImpl extends BaseCloudImpl implements SOTDCS004Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsNTestDriveDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("====创建客户信息（试乘试驾）(DMS新增)接收开始====");
		for (TiDmsNTestDriveDTO entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("创建客户信息（试乘试驾）(DMS新增)数据接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====创建客户信息（试乘试驾）(DMS新增)数据接收结束===="); 
		return msg;
	}

	public void insertData(TiDmsNTestDriveDTO vo) throws Exception {
		// Map<String, Object> map =
		// deCommonDao.getSaDcsDealerCode(vo.getEntityCode());
		// String dealerCode = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		TiDmsNTestDrivePO insertPO = new TiDmsNTestDrivePO();
		insertPO.setString("UNIQUENESS_ID", vo.getUniquenessId());// DMS客户唯一ID
		insertPO.setLong("FCA_ID", vo.getFCAID());// app客户唯一ID
		insertPO.setDate("TEST_DRIVE_TIME", vo.getTestDriveTime());// 试驾时间
		insertPO.setString("TEST_BRAND_ID", vo.getTestBrandId());// 试驾的品牌
		insertPO.setString("TEST_MODEL_ID", vo.getTestModelId());// 试驾的车型
		insertPO.setString("TEST_CAR_STYLE_ID", vo.getTestCarStyleId());// 试驾的车款
		insertPO.setString("DRIVE_ROAD_DIC_ID", vo.getDriveRoadDicId());// 试驾路线
		insertPO.setString("IDENTIFICATION_NO", vo.getIdentificationNo());// 身份证号
		insertPO.setString("DEALER_CODE", vo.getDealerCode());// 经销商代码
		insertPO.setString("DEALER_USER_ID", vo.getDealerUserId());// 销售人员ID
		insertPO.setTimestamp("CREATE_DATE", vo.getCreateDate());// 创建日期
		insertPO.setString("IS_SEND", "0");// 同步标志
		insertPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);// 创建者
		// 插入数据
		insertPO.insert();
	}

}
