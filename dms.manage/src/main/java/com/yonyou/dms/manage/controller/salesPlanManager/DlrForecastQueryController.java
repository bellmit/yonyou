package com.yonyou.dms.manage.controller.salesPlanManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TtVsMonthlyForecastDTO;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.TtVsMonthlyForecastDetailColorDTO;
import com.yonyou.dms.manage.service.salesPlanManager.DlrForecastQueryService;
import com.yonyou.dms.manage.service.salesPlanManager.OemForecastService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 
* @ClassName: DlrForecastQueryController 
* @Description:  生产订单管理
* @author zhengzengliang
* @date 2017年2月17日 下午5:32:11 
*
 */
@SuppressWarnings({"rawtypes"})
@Controller
@TxnConn
@RequestMapping("/dlrForecast")
public class DlrForecastQueryController {

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private DlrForecastQueryService dlrForecastQueryService;
	
	@Autowired
	private OemForecastService oemForecastService;
	
	@Autowired
	private ExcelGenerator excelService;
	
	/**
	 * 
	* @Title: getDealerMonthPlanYearList 
	* @Description: 生产订单确认上报 （获取任务编号）
	* @param @param yearId
	* @param @param monthId
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/year/{yearId}/month/{monthId}/getDealerMonthPlanTaskNoList",
			method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getDealerMonthPlanYearList(@PathVariable("yearId") String yearId,
			@PathVariable("monthId") String monthId,
			@RequestParam Map<String,String> queryParam) {
		logger.info("===========生产订单确认上报 （获取任务编号）  =============="); 
		queryParam.put("yearcode", yearId);
		queryParam.put("monthcode", monthId);
		List<Map> taskCodeList = dlrForecastQueryService.getDealerMonthPlanYearList(queryParam);
		return taskCodeList;
	}
	
	/**
	 * 
	* @Title: forecastQuery2 
	* @Description: 生产订单确认上报 （查询） 
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastQuery2",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto forecastQuery2(@RequestParam Map<String, String> queryParam) {
		logger.info("============生产订单确认上报 （查询）===============");
		PageInfoDto pageInfoDto = dlrForecastQueryService.getDlrForecastQueryList2(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: modifyforecastOTDSubmit 
	* @Description:  生产订单确认上报（确认订单）
	* @param @param taskId
	* @param @param tvmfDTO
	* @param @param uriCB
	* @param @return    设定文件 
	* @return ResponseEntity<TtVsMonthlyForecastDTO>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/forecastQueryInit2", method = RequestMethod.PUT)
    public ResponseEntity<TtVsMonthlyForecastDTO> forecastQueryInit2(
    		@RequestBody @Valid TtVsMonthlyForecastDetailColorDTO ttVsMonthlyForecastDetailColorDTO) {
        logger.debug("-------生产订单确认上报（确认订单） detailColorIds:" + ttVsMonthlyForecastDetailColorDTO.getDetailColorIds()+"----------");
        dlrForecastQueryService.modifyVsMonthlyForecastDetailColor(ttVsMonthlyForecastDetailColorDTO);
        
        return new ResponseEntity<>( HttpStatus.CREATED);
    }
	
	/**
	 * 
	* @Title: OTDfindBySerialNumber 
	* @Description: 生产订单序列号跟踪(经销商)（查询）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/DlrfindBySerialNumber",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto DlrfindBySerialNumber(@RequestParam Map<String, String> queryParam) {
		logger.info("============生产订单号序列号跟踪(OTD)（查询） ===============");
		PageInfoDto pageInfoDto = dlrForecastQueryService.DlrfindBySerialNumber(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: OTDfindBySerialNumberDownload 
	* @Description:  生产订单序列号跟踪(经销商)（下载）
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/OTDfindBySerialNumberDownload/export/excel",
			method = RequestMethod.GET)
    public void OTDfindBySerialNumberDownload(@RequestParam Map<String, String> queryParam,
    		HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		logger.info("=========== 生产订单号序列号跟踪(下载)==============");
		// 生产订单号序列号跟踪(经销商)(OEM)（下载）公用一个SQL
        List<Map> resultList = oemForecastService.getOemForecastQueryQueryListOtd(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put(" 生产订单号序列号跟踪(经销商)信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("MONTH","月份"));
        exportColumnList.add(new ExcelExportColumn("TASK_ID","任务编号"));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
        exportColumnList.add(new ExcelExportColumn("GROUP_NAME","年款"));
        exportColumnList.add(new ExcelExportColumn("MODEL_YEAR","车款"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
        exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
        exportColumnList.add(new ExcelExportColumn("PON","PON号"));
        exportColumnList.add(new ExcelExportColumn("STATUS","状态"));
        excelService.generateExcel(excelData, exportColumnList,"生产需求序列号跟踪(DLR)下载"+ Utility.getCurrentTime(10)+".xls", request, response);
    }
	
	
	
}
