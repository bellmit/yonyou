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

import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.service.salesPlanManager.MonthPlanMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: MonthPlanMaintainController 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午2:24:18 
*
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/monthPlanMaintain")
public class MonthPlanMaintainController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private MonthPlanMaintainService monthPlanMaintainService ;
	
	@Autowired
	private ExcelGenerator excelService;
	
	/**
	 * 
	* @Title: getDealerMonthPlanYearList 
	* @Description: 月度任务查询（经销商端）年列表 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/monthPlanInfoQueryInit",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> monthPlanInfoQueryInit() {
		logger.info("============月度任务查询（经销商端）年列表===============");
		List<Map> yearList = monthPlanMaintainService.getMonthPlanYearList();
		return yearList;
	}
	
	/**
	 * 
	* @Title: getSeasonList 
	* @Description:  月度任务查询（经销商端）季度列表
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getSeasonList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getSeasonList() {
		logger.info("============月度任务查询（经销商端）季度列表 ===============");
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
	* @Title: getNowSeasonList 
	* @Description: 获取当前季度
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getNowSeasonList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getNowSeasonList() {
		logger.info("============月度任务查询（经销商端）当前季度 ===============");
		List<Map> list = monthPlanMaintainService.getNowSeasonList();
		return list;
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
	@RequestMapping(value="/monthPlanDetialInfoListQuery",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto monthPlanDetialInfoListQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============月度任务查询（车厂端）===============");
		PageInfoDto pageInfoDto = monthPlanMaintainService.oemQueryMonthPlanDetialInfoList(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: monthPlanDealerDownLoad 
	* @Description: 月度任务查询   （下载 ）经销商
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/monthPlanDetialInfoDownLoad/export/excel", method = RequestMethod.GET)
    public void monthPlanDetialInfoDownLoad(@RequestParam Map<String, String> queryParam,
    		HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============月度任务下载(车厂端) ===============");
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
        List<Map> resultList = monthPlanMaintainService.monthPlanDetialInfoDownLoad(queryParam);
        resultList.size();
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("经销商月度任务明细下载", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
        exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME","经销商名称"));
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
        excelService.generateExcel(excelData, exportColumnList,"月度任务下载"+Utility.getCurrentTime(10)+".xls", request, response);
    }
	
	
	

}
