package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.LazyList;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.LicenseChangeDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtLicenseChangePO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 计划任务       上传车牌变更信息
 * @author Administrator
 *
 */
@Service
public class SEDMS002CoudImpl implements SEDMS002Coud {
	
	final Logger logger = Logger.getLogger(SEDMS002CoudImpl.class);

	/**
	 * @description 计划任务       上传车牌变更信息
	 * @param dealerCode
	 */
	@Override
	public LinkedList<LicenseChangeDTO> getSEDMS002(String dealerCode) {
		logger.info("==========SEDMS002Impl执行===========");
		LinkedList<LicenseChangeDTO> resultList = new LinkedList<LicenseChangeDTO>();
		try {
			logger.debug("from TtLicenseChangePO DEALER_CODE = "+dealerCode);
			LazyList<TtLicenseChangePO> ttLicenseChangePOs = TtLicenseChangePO.findBySQL("DEALER_CODE = ?", dealerCode);
			if (ttLicenseChangePOs != null && !ttLicenseChangePOs.isEmpty()){
				for (TtLicenseChangePO ttLicenseChangePO : ttLicenseChangePOs) {
					LicenseChangeDTO licenseChangeDTO = new LicenseChangeDTO();
					licenseChangeDTO.setNewLicense(ttLicenseChangePO.getString("NEW_LICENSE"));
					licenseChangeDTO.setDealerCode(ttLicenseChangePO.getString("DEALER_CODE"));
					licenseChangeDTO.setVin(ttLicenseChangePO.getString("VIN"));
					licenseChangeDTO.setOldLicense(ttLicenseChangePO.getString("OLD_LICENSE"));
					resultList.add(licenseChangeDTO);
					TtLicenseChangePO cndUpdateLicenseChangePO = new TtLicenseChangePO();
					cndUpdateLicenseChangePO.setString("DEALER_CODE", dealerCode);
					cndUpdateLicenseChangePO.setString("ITEM_ID", ttLicenseChangePO.getInteger("ITEM_ID"));
					cndUpdateLicenseChangePO.setString("IS_UPLOAD", Utility.getInt(CommonConstants.DICT_IS_YES));
					cndUpdateLicenseChangePO.saveIt();
				}
			}
			return resultList;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return null;
		}finally{
			logger.info("==========SEDMS002Impl结束===========");
		}
	}
}
