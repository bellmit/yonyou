package com.yonyou.dms.gacfca;

import java.text.ParseException;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TiDmsNTestDriveCarDto;
import com.yonyou.dms.common.domains.PO.basedata.TmModelPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SOTDCS014CoudImpl implements SOTDCS014Coud {

	@Override
	public LinkedList<TiDmsNTestDriveCarDto> getSOTDCS014(String[] driveCarId, String[] itemIdCar, String[] modelCode,
			String[] testCarType, String[] vin, String[] license, String[] status, String[] abateStarttime,
			String[] abateStoptime, String[] remarkCar, String[] status2) throws ServiceBizException, ParseException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		// 根据开关来判断是否执行程序5434
		String defautParam = Utility.getDefaultValue(dealerCode);
		if (defautParam != null && defautParam.equals(DictCodeConstants.DICT_IS_NO)) {

		}
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		LinkedList dmsTDCarList = new LinkedList();
		if (status2 != null && status2.length > 0) {
			for (int j = 0; j < status2.length; j++) {
				TiDmsNTestDriveCarDto dto = new TiDmsNTestDriveCarDto();
				TmModelPO tmModelPO = null;
				TmModelPO tmModelNewPO = null;
//				TmSeriesPo tmSeriesPO = null;
//				TmSeriesPo tmSeriesNewPO = null;
				List tmModelPOList = null;
				List tmSeriesPOList = null;
				if ("A".equals(status2[j])) {
					dto = new TiDmsNTestDriveCarDto();
					if (vin[j] != null) {
						dto.setVinCode(vin[j]);
					}
					if (modelCode[j] != null) {
						tmModelPOList = tmModelPO.findBySQL(" DEALER_CODE=? AND MODEL_CODE=? ",
								new Object[] { dealerCode, modelCode[j] });
						if (tmModelPOList != null && tmModelPOList.size() > 0) {
							tmModelNewPO = (TmModelPO) tmModelPOList.get(0);
						}
						if (tmModelNewPO.getString("MODEL_CODE") != null) {
							dto.setModelId(tmModelNewPO.getString("MODEL_CODE"));
						}
						if (tmModelNewPO.getString("MODEL_NAME") != null) {
							dto.setModelId(tmModelNewPO.getString("MODEL_NAME"));
						}
					}
					if (license[j] != null) {
						dto.setLicenseCode(license[j]);
						dto.setCreateDate(Utility.getCurrentDateTime());
						dto.setDealerUserId(userId + "");
						dto.setDealerCode(dealerCode);
						dto.setCarStaturs(status[j]);
						dmsTDCarList.add(dto);
					}
				}
			}
		}
		return dmsTDCarList;
	}

}
