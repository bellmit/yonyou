package com.yonyou.dcs.gacfca;

import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsNSwapDto;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsNSwapPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SOTDCS005CloudImpl
 * Description: 创建客户信息（置换需求）(DMS新增)接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午5:35:20
 * result msg 1：成功 0：失败
 */
@Service
public class SOTDCS005CloudImpl extends BaseCloudImpl implements SOTDCS005Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);
	
	@Override
	public String handleExecutor(List<TiDmsNSwapDto> dtoList) throws Exception {
		String msg = "1";
		System.out.println(dtoList.size());
		logger.info("====试乘试驾分析数据上报接收开始===="); 
		for (TiDmsNSwapDto entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("试乘试驾分析数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====试乘试驾分析数据上报接收结束===="); 
		return msg;
	}

	public void insertData(TiDmsNSwapDto vo) throws Exception {
		// Map<String, Object> map =
		// deCommonDao.getSaDcsDealerCode(vo.getEntityCode());
		// String dealerCode = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		TiDmsNSwapPO insertPO = new TiDmsNSwapPO();
		insertPO.setString("UNIQUENESS_ID", vo.getUniquenessId());// DMS客户唯一ID
		insertPO.setLong("FCA_ID", vo.getFCAID());// app客户唯一ID
		insertPO.setString("OWN_BRAND_ID", vo.getOwnBrandId());// 现有品牌ID
		insertPO.setString("OWN_MODEL_ID", vo.getOwnModelId());// 现有车型ID
		insertPO.setString("OWN_CAR_STYLE_ID", vo.getOwnCarStyleId());// 现有车款ID
		insertPO.setString("OWN_CAR_COLOR", vo.getOwnCarColor());// 现有车色
		insertPO.setString("VIN_CODE", vo.getVinCode());// 现有的车辆VIN
		insertPO.setDate("LICENCELSSUE_DATE", vo.getLicencelssueDate());// 上牌时间
		insertPO.setLong("TRAVLLED_DISTANCE", vo.getTravlledDistance());// 里程数
		insertPO.setLong("IS_ESTIMATED", vo.getIsEstimated());// 是否评估
		insertPO.setLong("ESTIMATED_PRICE", vo.getEstimatedPrice());// 评估金额
		insertPO.setString("ESTIMATED_ONE", vo.getEstimatedOne());// 评估报告1
		insertPO.setString("ESTIMATED_TWO", vo.getEstimatedTwo());// 评估报告2
		insertPO.setString("DRIVE_LICENSE", vo.getDriveLicense());// 旧车行驶证
		insertPO.setString("DEALER_CODE", vo.getEntityCode());// 经销商代码
		insertPO.setString("DEALER_USER_ID", vo.getDealerUserId());// 销售人员ID
		insertPO.setTimestamp("CREATE_DATE", vo.getCreateDate());// 创建日期
		insertPO.setString("IS_SEND", "0");// 同步标志
		insertPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);// 创建者
		// 插入数据
		insertPO.insert();
	}

}
