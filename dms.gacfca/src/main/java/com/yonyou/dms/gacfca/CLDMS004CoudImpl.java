/**
 * 
 */
package com.yonyou.dms.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TmMarketActivityDto;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignPlanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCampaignSeriesPO;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 *  0 失败  1 成功
 * 业务描述：OEM下发市场活动
 * @author xhy
 * @date 2017年2月16日
 */
@Service
public class CLDMS004CoudImpl implements CLDMS004Coud {

	@SuppressWarnings({ "rawtypes" })
	@Override
	public int getCLDMS004(LinkedList<TmMarketActivityDto> voList,String dealerCode) throws ServiceBizException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			
			if (voList != null && voList.size() > 0){
				for (int i = 0; i < voList.size(); i++){
					TmMarketActivityDto dto = new TmMarketActivityDto();
					dto = (TmMarketActivityDto)voList.get(i);
					//TtCampaignPlanPO po1 = new TtCampaignPlanPO();
					TtCampaignPlanPO po2 = new TtCampaignPlanPO();
					TtCampaignSeriesPO po3 = new TtCampaignSeriesPO();
					TtCampaignSeriesPO po4 = new TtCampaignSeriesPO();
					
//					po1.setString("CAMPAIGN_CODE",dto.getMarketNo());
//					po1.setString("DEALER_CODE",dealerCode);
//					po1.setLong("D_KEY",CommonConstants.D_KEY);
					List<Map> list=Base.findAll("select *  from tt_campaign_plan where DEALER_CODE=? AND CAMPAIGN_CODE=? ", new Object[]{dealerCode,dto.getMarketNo()});
					
					
					
					if (null != list && list.size()>0){
						StringBuffer sql=new StringBuffer("");
						sql.append(" UPDATE tt_campaign_plan SET CAMPAIGN_NAME='"+dto.getMarketName()+"',START_DATE='"+dto.getStartDate()+"',END_DATE='"+dto.getEndDate()+"'");
						sql.append(" , CAMPAIGN_BUDGET='"+dto.getMarketFee()+"',CAMPAIGN_PERFORM_TYPE='"+Utility.getInt(DictCodeConstants.DICT_CAMPAIGN_PERFORM_TYPE_OEM)+"'");
						sql.append(" ,CUR_AUDITING_STATUSE='"+Utility.getInt(DictCodeConstants.DICT_MARKET_DEPT_AUDIT_STATUS_1003)+"',OEM_TAG='"+12781001+"',UPDATED_BY='"+1L+"'");
						sql.append(" WHERE DEALER_CODE='"+dealerCode+"' AND CAMPAIGN_CODE='"+dto.getMarketNo()+"'AND D_KEY='"+0+"'");
						Base.exec(sql.toString());
						
					}else {
						
						po2.setString("CAMPAIGN_NAME",dto.getMarketName());
						po2.setDate("BEGIN_DATE",dto.getStartDate());
						po2.setDate("END_DATE",dto.getEndDate());
						po2.setLong("CAMPAIGN_BUDGET",dto.getMarketFee());
						po2.setLong("D_KEY",CommonConstants.D_KEY);
						if(!StringUtils.isNullOrEmpty(dealerCode)) {
							po2.setString("DEALER_CODE",dealerCode);
						}
						
						po2.setLong("CAMPAIGN_PERFORM_TYPE",Utility.getInt(DictCodeConstants.DICT_CAMPAIGN_PERFORM_TYPE_OEM));
						po2.setLong("CUR_AUDITING_STATUS",Utility.getInt(DictCodeConstants.DICT_MARKET_DEPT_AUDIT_STATUS_1003));
						po2.setLong("OEM_TAG",12781001);
						po2.setString("CAMPAIGN_CODE",dto.getMarketNo());
						po2.setString("CREATED_BY",1L);
						//po2.setDate("CREATED_AT", Utility.getCurrentDateTime());
						po2.insert();
						
					}
					String sql="DELETE FROM tt_campaign_series WHERE CAMPAIGN_CODE='"+dto.getMarketNo()+"' AND DEALER_CODE='"+dealerCode+"'";
					Base.exec(sql);
//					po3.setString("CAMPAIGN_CODE",dto.getMarketNo());
//					po3.setString("DEALER_CODE",dealerCode);
//					po3.deleteCascadeShallow();
					
					//po4.setItemId(POFactory.getLongPriKey(conn, po4));
					po4.setString("CAMPAIGN_CODE",dto.getMarketNo());
					po4.setString("DEALER_CODE",dealerCode);
					po4.setString("MODEL_CODE",dto.getModelCode());
					po4.setString("SERIES_CODE",dto.getSeriesCode());
					po4.setString("CREATED_BY",1L);
					//po4.setDate("CREATED_AT",new Date());
					po4.insert();
	
				}
			}
			
			return 1;		
		}catch(Exception e){
			return 0;
			
		}
		
		
	}

}
