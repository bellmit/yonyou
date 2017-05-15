/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TtActivityResultDto;
import com.yonyou.dms.common.domains.PO.basedata.TtActivityResultPO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * @author Administrator
 *
 */
@Service
public class SEDMS055Impl implements SEDMS055 {
	final Logger logger = Logger.getLogger(SEDMS055Impl.class);

	@Override
	public int getSEDMS055(List<TtActivityResultDto> voList, String dealerCode) throws ServiceBizException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		try {
			TtActivityResultPO arPO2 = new TtActivityResultPO();
			;
			TtActivityResultDto activityVO = null;
			if (dealerCode == null) {
				return 0;
			}
			if (voList != null && voList.size() > 0) {

				for (int i = 0; i < voList.size(); i++) {

					// activityVO=new TtActivityResultDto();
					activityVO = (TtActivityResultDto) voList.get(i);
					List<Map> list = Base.findAll(
							"SELECT *  FROM TT_ACTIVITY_RESULT WHERE DEALER_CODE=? AND ACTIVITY_CODE=? AND VIN=? AND D_KEY=?",
							new Object[] { dealerCode, activityVO.getActivityCode(), activityVO.getVin(),
									CommonConstants.D_KEY });
					if (list != null && list.size() > 0) {
						StringBuffer sql = new StringBuffer("");
						Map map = list.get(0);

						Date downDate = (Date) map.get("DOWN_TIMESTAMP");
						if (downDate != null && downDate.getTime() >= activityVO.getDownTimestamp().getTime()) {
							logger.debug("============>>DE下发时序不对，更新失败！");
							continue;// 跳出本次循环
						} else {
							sql.append("UPDATE TT_ACTIVITY_RESULT SET ACTIVITY_NAME='" + activityVO.getActivityName()
									+ "', DEALER_NAME='" + activityVO.getDealerName() + "' ");
							if (!StringUtils.isNullOrEmpty(activityVO.getCampaignDate())) {
								sql.append(" , CAMPAIGN_DATE='" + Utility.getTimeStamp(activityVO.getCampaignDate())
										+ "'");// 服务活动完成时间
							}
							sql.append(" , DOWN_TIMESTAMP ='" + df.format(activityVO.getDownTimestamp()) + "'");
							sql.append(" , UPDATE_BY ='" + 1L + "' , UPDATE_AT='" + format + "' where DEALER_CODE='"
									+ dealerCode + "' AND ACTIVITY_CODE='" + activityVO.getActivityCode()
									+ "' AND VIN='" + activityVO.getVin() + "' AND D_KEY='" + CommonConstants.D_KEY
									+ "' ");
							Base.exec(sql.toString());
						}

					} else {
						arPO2.setString("DEALER_CODE", dealerCode);
						arPO2.setInteger("D_KEY", CommonConstants.D_KEY);
						arPO2.setString("ACTIVITY_CODE", activityVO.getActivityCode());
						arPO2.setString("ACTIVITY_NAME", activityVO.getActivityName());
						arPO2.setString("DEALER_NAME", activityVO.getDealerName());
						if (Utility.testString(activityVO.getCampaignDate())) {
							arPO2.setTimestamp("CAMPAIGN_DATE", Utility.getTimeStamp(activityVO.getCampaignDate()));// 服务活动完成时间
						}

						arPO2.setString("VIN", activityVO.getVin());
						arPO2.setTimestamp("DOWN_TIMESTAMP", df.format(activityVO.getDownTimestamp()));

						arPO2.setLong("CREATE_BY", 1L);
						// arPO2.setCreateDate(Utility.getCurrentDateTime());
						arPO2.insert();

					}
				}
			}
			logger.debug("=====服务活动完成结果接收成功===========");
			return 1;
		} catch (Exception e) {
			return 0;
		}

	}

}
