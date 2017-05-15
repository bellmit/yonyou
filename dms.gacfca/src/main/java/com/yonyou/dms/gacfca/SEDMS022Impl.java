package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS024Cloud;
import com.yonyou.dms.DTO.gacfca.SEDMS022DTO;
import com.yonyou.dms.common.domains.PO.basedata.SoInvoicePO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class SEDMS022Impl implements SEDMS022 {
	private static final Logger logger = LoggerFactory.getLogger(SEDMS022Impl.class);
	@Autowired
	SADCS024Cloud SADCS024Cloud;

	@Override
	public int getSEDMS022(String itemId, String vin, String photo) throws ServiceBizException {
		String msg = "1";
		try {
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = df.format(new Date());

			LinkedList<SEDMS022DTO> resList = new LinkedList<SEDMS022DTO>();
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();

			if (!StringUtils.isNullOrEmpty(vin)) {
				if (!StringUtils.isNullOrEmpty(photo)) {
					SEDMS022DTO dto = new SEDMS022DTO();
					dto.setVin(vin);
					dto.setFileId(photo);
					dto.setFileUrl(photo);
					dto.setDealerCode(dealerCode);
					dto.setDownTimestamp(new Date());
					dto.setIsValid(10011001);
					resList.add(dto);
					logger.info("=================发票补扫上报开始=============SEDMS022");
					msg = SADCS024Cloud.handleExecutor(resList);
					logger.info("=================发票补扫上报结束=============SEDMS022");
					SoInvoicePO tsiPO = SoInvoicePO.findByCompositeKeys(itemId, dealerCode);
					if (!StringUtils.isNullOrEmpty(photo) && !StringUtils.isNullOrEmpty(tsiPO)) {

						tsiPO.setString("FILE_ID", photo);
						tsiPO.setString("FILE_URL", photo);
						// 等待识别:17181002
						tsiPO.setString("IDENTIFY_STATUS", 17181002);

					}
					if (tsiPO != null && tsiPO.getString("FEE_VEHICLE_RESCAN_TIMES") != null) {
						tsiPO.setString("FEE_VEHICLE_RESCAN_TIMES", tsiPO.getString("FEE_VEHICLE_RESCAN_TIMES") + 1);
					}
					tsiPO.saveIt();
				} else {

					System.err.println("发票上传信息为空，退出");
					msg = "2";
					throw new ServiceBizException("发票上传信息为空，请重新查询后再试！");
				}
			} else {
				msg = "3";
				System.err.println("车辆VIN为空，退出");
				throw new ServiceBizException("车辆VIN为空，请重新查询后再试！");
			}

			return Integer.parseInt(msg);

		} catch (Exception e) {
			logger.info("=================发票补扫上报失败=============SEDMS022");
			return 0;
		}
	}
}
