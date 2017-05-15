package com.yonyou.dms.manage.service.basedata.paramManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.manage.dao.paramManage.paramManageDao;
import com.yonyou.dms.manage.domains.DTO.basedata.ParamManageDTO;
import com.yonyou.dms.manage.domains.PO.basedata.TmBusinessDateDealerPO;
import com.yonyou.dms.manage.domains.PO.basedata.TmBusinessDatePO;
import com.yonyou.dms.manage.domains.PO.basedata.TmBusinessParamPercentPO;

@Service
public class paramManageServiceImpl implements paramManageService {
	
	@Autowired
	private paramManageDao dao;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		String type = param.get("paramType");
		PageInfoDto dto = null;
		if(StringUtils.isNotBlank(type)){
			//销售预测比例下限
			if("1".equals(type)){
				dto = dao.salesForecastProportionQuery(param);
			}else if("4".equals(type)){
			//配额转换率下限
				dto = dao.quotaConversionRateQuery(param);
			} 
		}
		return dto;
	}

	@Override
	public Map findForecastProportion(Long seriesId) {
		Map map = dao.salesForecastProportionQuery(seriesId);
		return map;
	}

	@Override
	public void editForecastProportion(ParamManageDTO dto) {
		Date currentTime = new Date();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmBusinessParamPercentPO po = TmBusinessParamPercentPO.findFirst("MATERIAL_GROUP_ID = ? AND PARA_TYPE = ?", dto.getMaterialGroupId(),dto.getParaType());
		if(po == null){
			po = new TmBusinessParamPercentPO();
			po.setLong("MATERIAL_GROUP_ID", dto.getMaterialGroupId());
			po.setInteger("PARA_TYPE", dto.getParaType());
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setTimestamp("CREATE_DATE", currentTime);
		}
		if(StringUtils.isNotBlank(dto.getRemark())){		
			po.setString("REMARK", dto.getRemark());
		}
		if(dto.getMachtUpper() != null){			
			po.setBigDecimal("MACHT_UPPER", dto.getMachtUpper());
		}
		if(dto.getMachtUpper() != null){			
			po.setBigDecimal("MACHT_LOWER", dto.getMachtLower());
		}
		if(dto.getTransUpperRate() != null){
			po.setBigDecimal("TRANS_UPPER_RATE", dto.getTransUpperRate());
		}
		if(dto.getTransLowerRate() != null){
			po.setBigDecimal("TRANS_LOWER_RATE", dto.getTransLowerRate());
		}
		po.setLong("UPDATE_BY", loginInfo.getUserId());
		po.setTimestamp("UPDATE_DATE", currentTime);
		boolean flag = po.saveIt();
		System.out.println(flag);
	}

	@Override
	public Map findConversionRate(Long modelId) {
		Map map = dao.quotaConversionRateQuery(modelId);
		return map;
	}

	@Override
	public Map saveOrderRate(Map<String, String> param) {
		Map<String,Object> map = new HashMap<>();
		Date currentTime = new Date();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String orderRate = param.get("orderRate");
		List<TmBusinessParamPercentPO> list = TmBusinessParamPercentPO.find("PARA_TYPE = ? ", OemDictCodeConstants.PARAM_TYPE_07);
		if(list != null && !list.isEmpty()){
			for(int i = 0; i < list.size(); i++){
				TmBusinessParamPercentPO po = list.get(i);
				po.setBigDecimal("ORDER_RATE", Float.parseFloat(orderRate));
				po.setLong("UPDATE_BY", loginInfo.getUserId());
				po.setTimestamp("UPDATE_DATE", currentTime);
				po.saveIt();
			}
		}else{
			TmBusinessParamPercentPO po = new TmBusinessParamPercentPO();
			po.setBigDecimal("ORDER_RATE", Float.parseFloat(orderRate));
			po.setInteger("PARA_TYPE", OemDictCodeConstants.PARAM_TYPE_07);
			po.setBigDecimal("MACHT_LOWER", 0f);
			po.setBigDecimal("MACHT_UPPER", 0f);
			po.setBigDecimal("ADJUST_RATE", 0f);
			po.setBigDecimal("TRANS_LOWER_RATE", 0f);
			po.setBigDecimal("TRANS_UPPER_RATE", 0f);
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setTimestamp("CREATE_DATE", currentTime);
			po.setLong("PARAM_ID", this.getLongId());
			po.saveIt();
		}
		map.put("code", true);
		map.put("msg", "保存成功！");
		return map;
	}
	
	private Long getLongId(){
		int fourNo = (int) (Math.random()*9000+1000);//随机4位数
		SimpleDateFormat df = new SimpleDateFormat("yyyyMMdd");//设置日期格式
		String timeNow = df.format(new Date());// new Date()为获取当前系统时间
		String id = fourNo + timeNow;
		return Long.parseLong(id);
	}

	@Override
	public ParamManageDTO getOrderRate() {
		ParamManageDTO dto = new ParamManageDTO();
		String orderRate = null;
		List<TmBusinessParamPercentPO> list = TmBusinessParamPercentPO.find("PARA_TYPE = ? ", OemDictCodeConstants.PARAM_TYPE_07);
		if(list != null && !list.isEmpty()){
			Integer data = list.get(0).getInteger("ORDER_RATE");
			orderRate = String.valueOf(data);
		}else{
			orderRate = String.valueOf(0d);
		}
		dto.setOrderRate(Float.parseFloat(orderRate));
		
		List<TmBusinessDatePO> dealerList = TmBusinessDatePO.find("PARAM_TYPE = ?", OemDictCodeConstants.PARAM_TYPE_02);
		if(dealerList != null && !dealerList.isEmpty()){
			TmBusinessDatePO dp = dealerList.get(0);
			dto.setForceMonth(dp.getInteger("FORCE_MONTH"));
			dto.setStartDay(dp.getInteger("START_DAY"));
			dto.setStartHour(dp.getInteger("START_HOUR"));
			dto.setEndDay(dp.getInteger("END_DAY"));
			dto.setEndHour(dp.getInteger("END_HOUR"));
		}
		
		List<TmBusinessDatePO> orgList = TmBusinessDatePO.find("PARAM_TYPE = ?", OemDictCodeConstants.PARAM_TYPE_03);
		if(orgList != null && !orgList.isEmpty()){
			TmBusinessDatePO dp = orgList.get(0);
			dto.setForceMonth1(dp.getInteger("FORCE_MONTH"));
			dto.setStartDay1(dp.getInteger("START_DAY"));
			dto.setStartHour1(dp.getInteger("START_HOUR"));
			dto.setEndDay1(dp.getInteger("END_DAY"));
			dto.setEndHour1(dp.getInteger("END_HOUR"));
		}
		return dto;
	}

	@Override
	public List<Map> getDealerLeft() {
		Long paraId = -1L;
		List<TmBusinessDatePO> list = TmBusinessDatePO.find("PARAM_TYPE = ?", OemDictCodeConstants.PARAM_TYPE_02);
		if(list != null && !list.isEmpty()){
			TmBusinessDatePO dp = list.get(0);
			paraId = dp.getLongId();
		}
		return dao.getDealerLeft(paraId);
	}

	@Override
	public List<Map> getDealerRight() {
		Long paraId = -1L;
		List<TmBusinessDatePO> list = TmBusinessDatePO.find("PARAM_TYPE = ?", OemDictCodeConstants.PARAM_TYPE_02);
		if(list != null && !list.isEmpty()){
			TmBusinessDatePO dp = list.get(0);
			paraId = dp.getLongId();
		}
		return dao.getDealerRight(paraId);
	}

	@Override
	public void saveDealerAndDate(String ids, String date, Integer type) {
		boolean flag = false;
		Date currentTime = new Date();
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<TmBusinessDatePO> list = TmBusinessDatePO.find("PARAM_TYPE = ?", type);
		String[] dates = date.split(",");
		String forceMonth = dates[0];
		String startDay = dates[1];
		String startHour = dates[2];
		String endDay = dates[3];
		String endHour = dates[4];
		TmBusinessDatePO dp = null;
		if(list != null && !list.isEmpty()){
			dp = list.get(0);
		}else{
			dp = new TmBusinessDatePO();
		}
		if(StringUtils.isNotBlank(forceMonth)){
			dp.setBigDecimal("FORCE_MONTH", Integer.parseInt(forceMonth));
		}
		if(StringUtils.isNotBlank(forceMonth)){
			dp.setBigDecimal("START_DAY", Integer.parseInt(startDay));
		}
		if(StringUtils.isNotBlank(forceMonth)){
			dp.setBigDecimal("START_HOUR", Integer.parseInt(startHour));
		}
		if(StringUtils.isNotBlank(forceMonth)){
			dp.setBigDecimal("END_DAY", Integer.parseInt(endDay));
		}
		if(StringUtils.isNotBlank(forceMonth)){
			dp.setBigDecimal("END_HOUR", Integer.parseInt(endHour));
		}
		dp.setBigDecimal("PARAM_TYPE", type);
		dp.setBigDecimal("STATUS", OemDictCodeConstants.STATUS_ENABLE);
		dp.setLong("UPDATE_BY", loginInfo.getUserId());
		dp.setTimestamp("UPDATE_DATE", currentTime);
		flag = dp.saveIt();
		Long paraId = dp.getLongId();
		
		//保存经销商
		if(!"-1".equals(ids)){
			TmBusinessDateDealerPO.delete("PARA_ID = ? ", paraId);
			String[] dealerIds = ids.split(",");
			for(String dealerId : dealerIds){
				if(StringUtils.isNotBlank(dealerId)){
					TmBusinessDateDealerPO bdp = new TmBusinessDateDealerPO();
					bdp.setBigDecimal("GROUP_ID", Long.parseLong(dealerId));
					bdp.setLong("PARA_ID", paraId);
					bdp.setLong("CREATE_BY", loginInfo.getUserId());
					bdp.setTimestamp("CREATE_DATE", currentTime); 
					flag = bdp.saveIt();
				}
			}
		}else{
			TmBusinessDateDealerPO.delete("PARA_ID = ? ", paraId);
		}
		
	}

	@Override
	public List<Map> getOrgLeft() {
		Long paraId = -1L;
		List<TmBusinessDatePO> list = TmBusinessDatePO.find("PARAM_TYPE = ?", OemDictCodeConstants.PARAM_TYPE_03);
		if(list != null && !list.isEmpty()){
			TmBusinessDatePO dp = list.get(0);
			paraId = dp.getLongId();
		}
		return dao.getOrgLeft(paraId);
	}

	@Override
	public List<Map> getOrgRight() {
		Long paraId = -1L;
		List<TmBusinessDatePO> list = TmBusinessDatePO.find("PARAM_TYPE = ?", OemDictCodeConstants.PARAM_TYPE_03);
		if(list != null && !list.isEmpty()){
			TmBusinessDatePO dp = list.get(0);
			paraId = dp.getLongId();
		}
		return dao.getOrgRight(paraId);
	}


}
