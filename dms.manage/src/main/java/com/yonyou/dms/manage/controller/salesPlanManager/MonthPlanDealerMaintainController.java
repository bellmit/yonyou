package com.yonyou.dms.manage.controller.salesPlanManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.service.salesPlanManager.MonthPlanDealerMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: MonthPlanDealerMaintainController 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午2:24:18 
*
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/monthPlanDealerMaintain")
public class MonthPlanDealerMaintainController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private MonthPlanDealerMaintainService monthPlanDealerMaintainService ;
	
	@Autowired
	private ExcelGenerator excelService;
	
	/**
	 * 
	* @Title: getDealerMonthPlanYearList 
	* @Description: 月度任务查询  年列表 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getDealerMonthPlanYearList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getDealerMonthPlanYearList() {
		logger.info("============月度任务查询  年列表===============");
		List<Map> yearList = monthPlanDealerMaintainService.getDealerMonthPlanYearList();
		return yearList;
	}
	
	/**
	 * 
	* @Title: getSeasonList 
	* @Description:  月度任务查询   季度列表
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getSeasonList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getSeasonList() {
		logger.info("============月度任务查询  季度列表 ===============");
		List<Map> seasonList = new ArrayList<Map>();
		Map<String,Object> seasonMap = new HashMap<String,Object>();
		seasonMap.put("SEASON", "第一季度");
		seasonList.add(seasonMap);
		Map<String,Object> seasonMap2 = new HashMap<String,Object>();
		seasonMap2.put("SEASON", "第二季度");
		seasonList.add(seasonMap2);
		Map<String,Object> seasonMap3 = new HashMap<String,Object>();
		seasonMap3.put("SEASON", "第三季度");
		seasonList.add(seasonMap3);
		Map<String,Object> seasonMap4 = new HashMap<String,Object>();
		seasonMap4.put("SEASON", "第四季度");
		seasonList.add(seasonMap4);
		return seasonList;
	}
	
	/**
	 * 
	* @Title: monthPlanDealerQuery 
	* @Description:月度任务查询 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/monthPlanDealerQuery",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto monthPlanDealerQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============月度任务查询 (经销商)===============");
		PageInfoDto pageInfoDto = monthPlanDealerMaintainService.dealearQueryMonthPlanDealerInfoList(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: monthPlanDealerDownLoad 
	* @Description: 月度任务查询   （下载 ）
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/monthPlanDealerDownLoad/export/excel", method = RequestMethod.GET)
    public void monthPlanDealerDownLoad(@RequestParam Map<String, String> queryParam,
    		HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============月度任务查询   （下载 ）===============");
		String season = new String();
		if(!StringUtils.isNullOrEmpty(queryParam.get("seasonName"))){
			season = queryParam.get("seasonName").toString();
		}
		if("第一季度".equals(season)){
			season = "1";
		}else if("第二季度".equals(season)){
			season = "2";
		}else if("第三季度".equals(season)){
			season = "3";
		}else if("第四季度".equals(season)){
			season = "4";
		}
		String one = null,two = null,three = null;
		if(season.equals("1")){
			one = "1";two = "2";three = "3";
		}else if(season.equals("2")){
			one = "4";two = "5";three = "6";
		}else if(season.equals("3")){
			one = "7";two = "8";three = "9";
		}else if(season.equals("4")){
			one = "10";two = "11";three = "12";
		}
        List<Map> resultList = monthPlanDealerMaintainService.monthPlanDealerDownLoad(queryParam);
        resultList.size();
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("月度任务明细下载", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车系"));
        if(one != null){
        	exportColumnList.add(new ExcelExportColumn("ONE",one+"月"));
        }else{
        	exportColumnList.add(new ExcelExportColumn("ONE","月"));
        }
        if(two != null){
        	exportColumnList.add(new ExcelExportColumn("TWO",two+"月"));
        }else{
        	exportColumnList.add(new ExcelExportColumn("TWO","月"));
        }
        if(three != null){
        	exportColumnList.add(new ExcelExportColumn("THREE",three+"月"));
        }else{
        	exportColumnList.add(new ExcelExportColumn("THREE","月"));
        }
        exportColumnList.add(new ExcelExportColumn("TOTAL","季度任务汇总"));
        excelService.generateExcel(excelData, exportColumnList,"月度任务明细下载.xls", request, response);
    }
	
	
	

}
