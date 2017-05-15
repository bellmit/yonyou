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
import com.yonyou.dms.manage.service.salesPlanManager.DlrYearPlanQuerySercvice;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: DlrYearPlanQueryController 
* @Description: 目标任务管理
* @author zhengzengliang 
* @date 2017年3月2日 下午6:34:46 
*
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/dlrYearPlanQuery")
public class DlrYearPlanQueryController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private DlrYearPlanQuerySercvice dlrYearPlanQuerySercvice ;
	
	@Autowired
	private ExcelGenerator excelService;
	
	/**
	 * 
	* @Title: yearPlanQueryInit 
	* @Description: 年度目标查询   年列表
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/yearPlanQueryInit",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> yearPlanQueryInit() {
		logger.info("============年度目标查询年列表===============");
		List<Map> yearList = dlrYearPlanQuerySercvice.getDealerPlanYearList();
		return yearList;
	}
	
	/**
	 * 
	* @Title: yearPlanQuery 
	* @Description: 年度目标查询 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/yearPlanQuery",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto yearPlanQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============年度目标查询===============");
		PageInfoDto pageInfoDto = dlrYearPlanQuerySercvice.getDlrYearPlanQueryList(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: yearPlanDownload 
	* @Description:  年度目标查询（下载）
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/yearPlanDownload/export/excel", method = RequestMethod.GET)
    public void yearPlanDownload(@RequestParam Map<String, String> queryParam,
    		HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============年度目标查询  （下载）===============");
        List<Map> resultList = dlrYearPlanQuerySercvice.yearPlanDownload(queryParam);
        resultList.size();
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("年度目标明细下载", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("BORD_NAME","品牌"));
        exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车系"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT1","1月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT2","2月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT3","3月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT4","4月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT5","5月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT6","6月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT7","7月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT8","8月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT9","9月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT10","10月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT11","11月"));
        exportColumnList.add(new ExcelExportColumn("AMOUNT12","12月"));
        exportColumnList.add(new ExcelExportColumn("TOTAL_AMOUNT","合计"));
        excelService.generateExcel(excelData, exportColumnList,"年度目标明细下载.xls", request, response);
    }
	
	
	

}
