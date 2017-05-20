package com.yonyou.dms.manage.service.salesPlanManager;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.function.utils.jsonSerializer.JSONUtil;
import com.yonyou.dms.manage.dao.salesPlanManager.MonthPlanImportMaintainDao;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TmpVsMonthlyPlanDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsMonthlyPlanPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsMonthlyPlanDetailPO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TtVsMonthlyPlanPO;
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
	public TmpVsMonthlyPlanDTO ImportDataRecord(TmpVsMonthlyPlanDTO rowDto, List<Map> seriesCodeList, ArrayList<TmpVsMonthlyPlanDTO> dataList
			) throws ServiceBizException {
		Map<String,String> queryParam = new HashMap<String,String>();
		queryParam.put("planYearName", rowDto.getPlanYear());
		queryParam.put("planMonthName", rowDto.getPlanMonth());
		queryParam.put("planTypeName", rowDto.getPlanType());
		// 删除月度目标临时表中数据
		monthPlanImportMaintainDao.deleteTmpVsMonthlyPlanByUserId();
		
		StringBuilder sqlSb = new StringBuilder();
        sqlSb.append("SELECT td.* FROM tm_dealer td WHERE 1=1 AND td.DEALER_CODE = '"+rowDto.getDealerCode()+"' \n" );
		List<Map> dealerlist = OemDAOUtil.findAll(sqlSb.toString(),null);
		
		String dealerCode = rowDto.getDealerCode();	// 经销商代码
		
		/*
		 * 查找出对应经销商代码是否出现重复数据
		 */
		int count = 0;
		for (int j = 0; j < dataList.size(); j++) {
			if (dealerCode.equals(dataList.get(j).getDealerCode())) {
				count++;
			}
		}
		
		if (dealerlist.size() == 0) {
			//验证经销商是否存在
			rowDto.setErrorMsg("验证失败：经销商不存在！");
			TmpVsMonthlyPlanPO tvmpPO = new TmpVsMonthlyPlanPO();
			monthPlanImportMaintainDao.setTmpVsMonthlyPlan(tvmpPO, rowDto, queryParam);
			tvmpPO.insert();
		} else if(count > 1) {	
			// 检查是否存在重复数据			
			rowDto.setErrorMsg("验证失败：经销商代码重复！");
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
		
		return rowDto;
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

	@Override
	public ArrayList<TmpVsMonthlyPlanDTO> monthPlanInfoImport(ImportResultDto<TmpVsMonthlyPlanDTO> importResult,Map<String,String> queryParam) {
		ArrayList<TmpVsMonthlyPlanDTO> dataList = importResult.getDataList();		
		ArrayList<TmpVsMonthlyPlanDTO> errorList = importResult.getErrorList();
		for(int k = 0; k < dataList.size(); k++){
			TmpVsMonthlyPlanDTO rowDto = dataList.get(k);
			// 只有全部是成功的情况下，才执行数据库保存
        	List<Map> seriesCodeList = getSeriesCode();
        	//向临时表插入数据
        	rowDto.setPlanYear(queryParam.get("planYearName"));
        	rowDto.setPlanMonth(queryParam.get("planMonthName"));
        	rowDto.setPlanType(queryParam.get("planTypeName"));
        	TmpVsMonthlyPlanDTO dto = ImportDataRecord(rowDto, seriesCodeList,dataList);
        	if(!StringUtils.isNullOrEmpty(dto.getErrorMsg())){
        		errorList.add(dto);
        	}
        	//获取当前用户
    		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    		// 查询此次导入数据验证后的结果
    		List<Map> allMessageBoole = allMessageQuery(1,loginInfo);
        	
    		if (allMessageBoole.size() < 1) {	// 此次上传数据全部正常
    			// 查询此次导入数据验证后的结果
				List<Map> allMessage = allMessageQuery(2,loginInfo);	
				
				/*
				 * 删除该年月上传过的历史数据
				 */
				List<Map> po1list = selectTtVsMonthlyPlan(rowDto);
				for (int i = 0; i < po1list.size(); i++) {
					TtVsMonthlyPlanDetailPO.delete(" PLAN_ID = ?", po1list.get(i).get("PLAN_ID"));
				}
				TmDealerPO dealer = TmDealerPO.findFirst("DEALER_CODE = ?", rowDto.getDealerCode());
				TtVsMonthlyPlanPO.delete(" PLAN_YEAR = ? AND PLAN_MONTH = ? AND PLAN_TYPE = ? AND PLAN_VER = ?  AND BUSINESS_TYPE = ? AND DEALER_ID = ?  AND TASK_ID is ?", rowDto.getPlanYear(), rowDto.getPlanMonth(), rowDto.getPlanType(),1,OemDictCodeConstants.GROUP_TYPE_IMPORT,dealer.getLong("DEALER_ID"),null);
				
				/*
				 * 写入新数据
				 */
				for (int i = 0; i < allMessage.size(); i++) {
					TtVsMonthlyPlanPO planPO = new TtVsMonthlyPlanPO();
					planPO.set("OEM_COMPANY_ID", loginInfo.getCompanyId());
					planPO.set("PLAN_YEAR", Integer.parseInt(rowDto.getPlanYear()));
					planPO.set("PLAN_MONTH", rowDto.getPlanMonth());
					planPO.set("PLAN_TYPE", Integer.parseInt(rowDto.getPlanType()));
					planPO.set("DEALER_ID", allMessage.get(i).get("DEALER_ID"));
					planPO.set("ORG_TYPE", OemDictCodeConstants.ORG_TYPE_DEALER);
					planPO.set("ORG_ID", loginInfo.getOrgId());
					planPO.set("STATUS", OemDictCodeConstants.PLAN_MANAGE_02);
					planPO.set("VER", new Integer(0));
					planPO.set("PLAN_VER", 1);
					planPO.setTimestamp("CREATE_DATE", new Date(System.currentTimeMillis()));
					planPO.set("CREATE_BY", loginInfo.getUserId());
					planPO.set("BUSINESS_TYPE",OemDictCodeConstants.GROUP_TYPE_IMPORT);
					planPO.saveIt();
					Long planId = planPO.getLong("id");
					
					//转换JSON字符串
					List<Map<String, String>> seriesList = parsingSeriesJson(allMessage.get(i));

					for (int j = 0; j < seriesList.size(); j++) {
						TtVsMonthlyPlanDetailPO detailPO = new TtVsMonthlyPlanDetailPO();
						detailPO.set("PLAN_ID", planId);
						detailPO.set("MATERIAL_GROUPID", Long.parseLong(seriesList.get(j).get("groupId")));
						detailPO.set("SALE_AMOUNT", Integer.parseInt(seriesList.get(j).get("num")));
						detailPO.set("CREATE_BY", loginInfo.getUserId());
						detailPO.set("CREATE_DATE", new Date());
						detailPO.saveIt();
					}
				}
				
			}
		}
		if(errorList != null && !errorList.isEmpty()){
			throw new ServiceBizException("导入出错,请见错误列表", errorList);
		}
		return dataList;
		
	}
	
	
	
	

}
