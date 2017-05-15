package com.yonyou.dms.retail.service.rebate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.retail.dao.rebate.RebateSumDao;
/**
 * 
* @ClassName: RebateSumServiceImpl 
* @Description: 经销商返利核算汇总（OEM）
* @author zhengzengliang 
* @date 2017年4月10日 下午3:03:03 
*
 */
@SuppressWarnings({"rawtypes","unchecked"})
@Service
public class RebateSumServiceImpl implements RebateSumService{
	@Autowired
	RebateSumDao dao;
	
	@Autowired
	ExcelGenerator excelService;

	@Override
	public PageInfoDto getRebateSum(Map<String, String> queryParam) {
		return dao.findRebateSum(queryParam);
	}

	@Override
	public List<Map> queryEmpInfoforExport(Map<String, String> queryParam) throws Exception {
	
		return dao.queryEmpInfoforExport(queryParam);
	}

	@Override
	public List<Map> queryRebateSumMX(Map<String, String> queryParam) throws Exception {
		List<Map> map=dao.queryDetailRebdInfo(queryParam);
		map.addAll(dao.queryDetailRebsInfo(queryParam));
		return map;
	}

	@Override
	public List<Map> getRebateSumMX(Map<String, String> queryParam) {
		
		return dao.findRebateSumMX(queryParam);
	}

	@Override
	public List<Map> queryDetailDownSt(String logId, String drlFlag, String dealerCode,
			LoginInfoDto logonUser) throws ServiceBizException {
		List<Map> list = dao.queryDetailDownSt(logId, drlFlag, dealerCode, logonUser);
		return list;
	}

	@Override
	public List<Map> queryDetailDownDy(String logId, String drlFlag, String dealerCode,
			LoginInfoDto logonUser) throws ServiceBizException {
		List<Map> list = dao.queryDetailDownDy(logId, drlFlag, dealerCode, logonUser);
		return list;
	}

	@Override
	public void addExcelExportColumn(List<Map> sList, List<Map> dList, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
	//	List<String[]> list = new ArrayList<String[]>();
		
		Map<String,Object> mapHead=new HashMap<String,Object>();
		
		Map<String,Object> mapValue=new HashMap<String,Object>();
		
		List dynHhead = new ArrayList();
		
		if(dList.size()>0){
			for(int m=0;m<dList.size();m++){
				Map<String,Object> recordDyn=dList.get(m);
				mapValue.put(recordDyn.get("REBATE_ID")+"_"+recordDyn.get("DYNAMIC_TITLE"), recordDyn.get("DYNAMIC_NAME"));
				if(mapHead.containsKey(recordDyn.get("DYNAMIC_TITLE"))){
					continue;
				}else{
					mapHead.put(recordDyn.get("DYNAMIC_TITLE").toString(), recordDyn.get("DYNAMIC_NAME"));
				}
			}
		}
		Set<String> headSet=mapHead.keySet();
		Iterator it=headSet.iterator();
		while(it.hasNext()){
			dynHhead.add(it.next());
		}
		/*if(sList.size()>0){
			Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
			excelData.put("返利核算明细下载", sList);
			// 生成excel 文件
			List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
			exportColumnList.add(new ExcelExportColumn("BUSINESS_POLICY_NAME", "商务政策"));
			exportColumnList.add(new ExcelExportColumn("APPLICABLE_TIME", "适用时段"));
			exportColumnList.add(new ExcelExportColumn("RELEASE_DATE", "发布时间"));
			exportColumnList.add(new ExcelExportColumn("START_MONTH", "起始月"));
			exportColumnList.add(new ExcelExportColumn("END_MONTH", "结束月"));
			exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
			exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
			exportColumnList.add(new ExcelExportColumn("NOMAL_BONUS", "常规奖金"));
			exportColumnList.add(new ExcelExportColumn("SPECIAL_BONUS", "特批奖金"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_EST", "追溯奖金（估算）"));
			exportColumnList.add(new ExcelExportColumn("BACK_BONUSES_DOWN", "追溯奖金（下发）"));
			exportColumnList.add(new ExcelExportColumn("NEW_INCENTIVES", "新经销商返利"));
			int t=1;
			if(dList.size()>0){
				for(int k=0;k<dynHhead.size();k++){
					for(int p=0;p<mapValue.size();p++){
						if(dynHhead.get(k).equals(dList.get(p).get("DYNAMIC_TITLE"))){
							listTemp[15+t]=(String) mapValue.get(record.get("REBATE_ID")+"_"+dList.get(p).get("DYNAMIC_TITLE"));
						}
					}
					t++;
				}
				
			}
			excelService.generateExcel(excelData, exportColumnList, "返利核算明细下载"+ getCurrentTime(10)+".xls", request, response);
		} */
		

	}
	
	/**
	 * @param i
	 * @return String
	 * @author  richard
	 */
	public static  String getCurrentTime(int i) {
		SimpleDateFormat formatter;
		Date currentTime_1 = new java.util.Date();
		if (i == 0) //for filename
			{
			formatter = new SimpleDateFormat("yyyy-MM-dd-HH");
		} else if (i == 1) {
			formatter = new SimpleDateFormat("yyyy-MM-dd");
		} else if (i == 3) {
			formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		} else if (i == 4) {
			formatter = new SimpleDateFormat("HH:mm");
		} else if (i == 5) {
			formatter = new SimpleDateFormat("HH:mm:ss");
		} else if (i == 6) {
			formatter = new SimpleDateFormat("yyyy");
		} else if (i == 7) {
			formatter = new SimpleDateFormat("MM");
		} else if (i == 8) {
			formatter = new SimpleDateFormat("dd");
		} else if (i == 9) {
			formatter = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
		} else if (i == 10) {
			formatter = new SimpleDateFormat("yyyyMMdd");
		} else if (i == 11) {
			formatter = new SimpleDateFormat("yyyyMMddHHmmss");
		} else if (i == 12) {
			formatter = new SimpleDateFormat("yyMMdd");
		} else if (i == 13) {
			formatter = new SimpleDateFormat("yyMMddHHmmss");
		} else {
			formatter = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");
		}

		String dateString = formatter.format(currentTime_1);
		return dateString;
	}

	@Override
	public PageInfoDto pageQueryDetailDownSt(String logId, String drlFlag, String dealerCode, LoginInfoDto logonUser)
			throws ServiceBizException {
		PageInfoDto pageInfoDto = dao.pageQueryDetailDownSt(logId, drlFlag, dealerCode, logonUser);
		return pageInfoDto;
	}

}
