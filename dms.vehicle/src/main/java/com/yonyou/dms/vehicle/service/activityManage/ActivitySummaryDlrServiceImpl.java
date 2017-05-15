package com.yonyou.dms.vehicle.service.activityManage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.activityManage.ActivitySummaryDlrDao;
import com.yonyou.dms.vehicle.domains.DTO.activityManage.ActivitySummaryDTO;
import com.yonyou.dms.vehicle.domains.PO.ActivityManage.TtAsActivityeEvaluateDcsPO;

/**
* @author liujiming
* @date 2017年4月6日
*/
@Service
public class ActivitySummaryDlrServiceImpl implements ActivitySummaryDlrService{
	
	@Autowired
	private ActivitySummaryDlrDao asdDao;
	
	
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto activitySummaryDlrQuery(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = asdDao.getActivitySummaryDlrQuery(queryParam);
		return pageInfoDto;
	}


	/**
	 * 维护查询
	 */
	@Override
	public Map activitySummaryDlrDetailQuery(String activityCode, String activityName, Integer inAmount,Long activityId) throws ServiceBizException {
		Map resultMap = new HashMap<>();
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode = loginInfo.getDealerCode();
		resultMap.put("ACTIVITY_ID", activityId);
		resultMap.put("ACTIVITY_NAME", activityName);
		resultMap.put("IN_AMOUNT", inAmount);
		List<Map> listSBN = asdDao.queryVehicleSummarySBN(activityCode, dealerCode);
		for(int i=0; i<listSBN.size(); i++){
			resultMap.put("IN_GUARANTEE", listSBN.get(i).get("sbn"));
		}
		List<Map> listSBW = asdDao.queryVehicleSummarySBW(activityCode, dealerCode);
		for(int i=0; i<listSBW.size(); i++){
			resultMap.put("OUT_GUARANTEE", listSBW.get(i).get("sbw"));
		}
		
		return resultMap;
	}

	/**
	 * 上报
	 */
	@Override
	public void activitySummaryUpdateSave(ActivitySummaryDTO asDto) throws ServiceBizException {
		Map map = asdDao.getActivityDetailQuery(asDto);
		Integer days = Integer.parseInt(map.get("DAYS").toString());
		
		//获取当前用户
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerId = loginInfo.getDealerId().toString();
		Map areaMap = asdDao.getLocalArea(dealerId);
		
		if(5 >0){
			TtAsActivityeEvaluateDcsPO savePo = new TtAsActivityeEvaluateDcsPO();
			savePo.set("ACTIVITY_ID", asDto.getActivityId());
			savePo.set("DEALER_ID", loginInfo.getDealerId());
			savePo.set("REGION", areaMap.get("PROVINCE_ID").toString());
			savePo.set("IN_AMOUNT", asDto.getInAmount());
			savePo.set("IN_GUARANTEE", asDto.getInGuarantee());
			savePo.set("OUT_GUARANTEE", asDto.getOutGuarantee());
			savePo.set("EVALUATE", asDto.getEvaluate());
			savePo.set("MEASURES", asDto.getMeasures());
			savePo.set("STATUS", OemDictCodeConstants.IF_TYPE_YES);
			savePo.set("CREATE_BY", loginInfo.getDealerId());
			savePo.setTimestamp("UPDATE_DATE", new Date(System.currentTimeMillis()));
			savePo.saveIt();
		}else{
			
		}

	}

}






