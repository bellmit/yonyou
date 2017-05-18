package com.yonyou.dms.gacfca;

import java.sql.Timestamp;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.RepairOrderSchemeDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.RepairOrderPO;
import com.yonyou.dms.function.common.CommonConstants;

/**
 * @description 三包状态下发
 * @author Administrator
 *
 */
@Service
public class SEDMS011CoudImpl implements SEDMS011Coud {
	final Logger logger = Logger.getLogger(SEDMS011CoudImpl.class);

	@Override
	public int getSEDMS011(String dealerCode, List<RepairOrderSchemeDTO> contentList) {
		logger.info("==========SEDMS011Impl执行===========");
		try {
			if (contentList != null && !contentList.isEmpty()){
				for (RepairOrderSchemeDTO repairOrderSchemeDTO : contentList){
					if (Utility.testString(repairOrderSchemeDTO.getRoNo()) && Utility.testString(dealerCode)
							&& repairOrderSchemeDTO.getSchemeStatus() != null && repairOrderSchemeDTO.getSchemeStatus() > 0){
						StringBuffer sql = new StringBuffer();
						switch(repairOrderSchemeDTO.getSchemeStatus()){
						case 40431004: //待审核
							sql.append(" SCHEME_STATUS = " + Utility.getInt(CommonConstants.DICT_SCHEME_STATUS_ADUITING));
							break;
						case 40431003:	//审核拒绝
							sql.append(" SCHEME_STATUS = " + Utility.getInt(CommonConstants.DICT_SCHEME_STATUS_REJUST));
							break;
						case 40431002: //审核调整
							sql.append(" SCHEME_STATUS = " + Utility.getInt(CommonConstants.DICT_SCHEME_STATUS_ADJUST));
							break;
						case 40431001://审核通过
							Timestamp timeStamp = Utility.getCurrentTimestamp();
							sql.append(" SCHEMESTATUS = " + Utility.getInt(CommonConstants.DICT_SCHEME_STATUS_SUCCESS));
							sql.append(" ,RO_CREATE_DATE = " + timeStamp);
							sql.append(" ,END_TIME_SUPPOSED = " + timeStamp);
							sql.append(" ,ESTIMATE_BEGIN_TIME = " + timeStamp);
							break;
						}
						sql.append(", remark1 = 经销商处理意见："+repairOrderSchemeDTO.getDealerOpinoin()+"；  审核结果描述："+repairOrderSchemeDTO.getAduitRemark());
						logger.debug("update RepairOrderPO " + sql.toString() + " where RO_NO = "+repairOrderSchemeDTO.getRoNo()+" and DEALER_CODE = "+dealerCode+" and D_KEY = "+repairOrderSchemeDTO.getRoNo());
						RepairOrderPO.update(sql.toString(), 
								"RO_NO = ? and DEALER_CODE = ? and D_KEY = ?", repairOrderSchemeDTO.getRoNo(),dealerCode,CommonConstants.D_KEY);
					}
				}
			}
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			logger.debug(e);
			return 0;
		}finally{
			logger.info("==========SEDMS011Impl结束===========");
		}
	}
}
