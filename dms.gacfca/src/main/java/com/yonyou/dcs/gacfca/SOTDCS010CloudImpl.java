package com.yonyou.dcs.gacfca;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsUSwapDto;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsUSwapPO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SOTDCS010CloudImpl extends BaseCloudImpl implements SOTDCS010Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsUSwapDto> dto) throws Exception {
		String msg = "1";
		logger.info("====更新客户信息（试乘试驾）(DMS更新)接收开始===="); 
		for (TiDmsUSwapDto entry : dto) {
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
	
	public void insertData(TiDmsUSwapDto vo) throws Exception {
		/*
		 * Map<String, Object> map =
		 * deCommonDao.getSaDcsDealerCode(vo.getEntityCode()); String dealerCode
		 * = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		 */
		TiDmsUSwapPO insertPO = new TiDmsUSwapPO();
		insertPO.setString("UNIQUENESS_ID", vo.getUniquenessID());// DMS客户唯一ID
		if (null != vo.getFCAID()) {
			insertPO.setInteger("FCA_ID", new Long(vo.getFCAID()));// DMS客户唯一ID
		}
		insertPO.setString("OWN_BRAND_ID", vo.getOwnBrandID());// 现有品牌ID
		insertPO.setString("OWN_MODEL_ID", vo.getOwnModelID());// 现有车型ID
		insertPO.setString("OWN_CAR_STYLE_ID", vo.getOwnCarStyleID());// 现有车款ID
		insertPO.setString("OWN_CAR_COLOR", vo.getOwnCarColor());// 现有车色
		insertPO.setString("VIN_CODE", vo.getVINCode());// 现有的车辆VIN
		insertPO.setDate("LICENCELSSUE_DATE", vo.getLicenceIssueDate());// 上牌时间
		if (null != vo.getTravlledDistance()) {
			insertPO.setLong("TRAVLLED_DISTANCE", new Long(vo.getTravlledDistance()));// 里程数
		}
		if (null != vo.getIsEstimated()) {
			insertPO.setLong("IS_ESTIMATED", new Long(vo.getIsEstimated()));// 是否评估
		}
		if (null != vo.getEstimatedPrice()) {
			insertPO.setLong("ESTIMATED_PRICE", new Long(vo.getEstimatedPrice()));// 评估金额
		}
		insertPO.setString("ESTIMATED_ONE", vo.getEstimatedOne());// 评估报告1
		insertPO.setString("ESTIMATED_TWO", vo.getEstimatedTwo());// 评估报告2
		insertPO.setString("DRIVE_LICENSE", vo.getDriveLicense());// 旧车行驶证
		insertPO.setString("DEALER_CODE", vo.getEntityCode());// 经销商代码
		insertPO.setString("DEALER_USER_ID", vo.getDealerUserID());// 销售人员ID
		insertPO.setTimestamp("UPDATE_DATE", vo.getUpdateDate());// 创建日期
		insertPO.setString("IS_SEND", "0");// 同步标志
		insertPO.setLong("UPDATE_BY", DEConstant.DE_UPDATE_BY);//
		// 插入数据
		insertPO.insert();

	}

}
