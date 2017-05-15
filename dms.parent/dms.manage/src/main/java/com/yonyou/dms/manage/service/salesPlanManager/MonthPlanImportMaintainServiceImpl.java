package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;
import com.yonyou.dms.manage.dao.salesPlanManager.MonthPlanImportMaintainDao;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyPlanDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsMonthlyPlanPO;
/**
 * 
* @ClassName: MonthPlanImportMaintainServiceImpl 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月14日 上午10:07:20 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class MonthPlanImportMaintainServiceImpl implements MonthPlanImportMaintainService{

	@Autowired
	private MonthPlanImportMaintainDao monthPlanImportMaintainDao ;
	
	@Override
	public List<Map> getSeriesCode() throws ServiceBizException {
		List<Map> list = monthPlanImportMaintainDao.getSeriesCode();
		return list;
	}

	@Override
	public void ImportDataRecord(TmpVsMonthlyPlanDTO rowDto, List<Map> seriesCodeList
			) throws ServiceBizException {
		Map<String,String> queryParam = new HashMap<String,String>();
		queryParam.put("planYearName", rowDto.getPlanYear());
		queryParam.put("planMonthName", rowDto.getPlanMonth());
		queryParam.put("planTypeName", rowDto.getPlanType());
		// 删除月度目标临时表中数据
		monthPlanImportMaintainDao.deleteTmpVsMonthlyPlanByUserId();
		
		StringBuilder sqlSb = new StringBuilder();
		List<Object> params2 = new ArrayList<Object>();
        sqlSb.append("SELECT td.* FROM tm_dealer td WHERE 1=1 AND td.DEALER_CODE = ? \n" );
        params2.add(rowDto.getDealerCode());
		List<Map> dealerlist = OemDAOUtil.findAll(sqlSb.toString(), params2);
		
		String dealerCode = rowDto.getDealerCode();	// 经销商代码
		
		/*
		 * 查找出对应经销商代码是否出现重复数据
		 */
		int count = 0;
		if (dealerCode.equals(rowDto.getDealerCode())) {
			count++;
		}
		
		if (dealerlist.size() == 0) {
			//验证经销商是否存在
			TmpVsMonthlyPlanPO tvmpPO = new TmpVsMonthlyPlanPO();
			monthPlanImportMaintainDao.setTmpVsMonthlyPlan(tvmpPO, rowDto, queryParam);
			tvmpPO.insert();
		} else if(count > 1) {	// 检查是否存在重复数据
			TmpVsMonthlyPlanPO tvmpPO = new TmpVsMonthlyPlanPO();
			monthPlanImportMaintainDao.setTmpVsMonthlyPlan2(tvmpPO, rowDto, queryParam);
			tvmpPO.insert();
		} else {
			/*
			 * 写入临时表
			 */
			TmpVsMonthlyPlanPO tvmpPO = new TmpVsMonthlyPlanPO();
			monthPlanImportMaintainDao.setTmpVsMonthlyPlan3(tvmpPO, rowDto, queryParam);
			tvmpPO.insert();
		}
		
	}

	@Override
	public List<Map> allMessageQuery(int type, LoginInfoDto loginInfo) throws ServiceBizException {
		List<Map> list = monthPlanImportMaintainDao.allMessageQuery(type, loginInfo);
		return list;
	}

	@Override
	public List<Map> selectTtVsMonthlyPlan(TmpVsMonthlyPlanDTO rowDto) throws ServiceBizException {
		List<Map> list = monthPlanImportMaintainDao.selectTtVsMonthlyPlan(rowDto);
		return list;
	}

	@Override
	public List<Map<String, String>> parsingSeriesJson(Map<String, Object> tmp) 
			throws ServiceBizException {
			List<Map<String, String>> list = new ArrayList<Map<String,String>>();
			
			String jsonStr =  tmp.get("SERIES_NUM_JSON").toString();
			
			List<TmpVsMonthlyPlanDTO> tvmpList =  JSONUtil.jsonToList(jsonStr, TmpVsMonthlyPlanDTO.class);
			for(int i =0 ; i < tvmpList.size(); i++){
				String seriesCode = tvmpList.get(i).getSeries().toString();
				
				List<Map> polist = monthPlanImportMaintainDao.selectTmVhclMaterialGroup(seriesCode);
	
				Map<String, String> map = new HashMap<String, String>();
				map.put("groupId", polist.get(0).get("GROUP_ID").toString());
				map.put("num", tvmpList.get(i).getNum());
				list.add(map);
			}
			
			return list;
	}

	@Override
	public String getPlayMonth(String planMonth) throws ServiceBizException {
		List<Map> list = monthPlanImportMaintainDao.getPlayMonth(planMonth);
		String playMonth = "";
		if(list.size() > 0){
			playMonth = list.get(0).get("CODE_CN_DESC").toString();
		}
		return playMonth;
	}
	
	
	
	

}
