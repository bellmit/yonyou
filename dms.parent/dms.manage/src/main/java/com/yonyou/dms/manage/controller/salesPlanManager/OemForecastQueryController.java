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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.ForecastImportDto;
import com.yonyou.dms.manage.domains.DTO.salesPlanManager.OemForecastAuditDTO;
import com.yonyou.dms.manage.domains.PO.salesPlanManager.TmpVsProImpInfoAuditPO;
import com.yonyou.dms.manage.service.salesPlanManager.OemForecastService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 
* @ClassName: OemForecastController 
* @Description: 生产订单管理控制层
* @author zhengzengliang
* @date 2017年2月7日 下午3:43:21 
*
 */
@Controller
@TxnConn
@RequestMapping("/forecastAudit")
@SuppressWarnings({"rawtypes"})
public class OemForecastQueryController {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);

	@Autowired
	private OemForecastService oemForecastService;
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
    private ExcelRead<OemForecastAuditDTO>      excelReadService;
	
	/**
	 * 
	* @Title: getYearList 
	* @Description:  生产订单审核（审核年）
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getYearList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getYearList() {
		logger.info("============生产订单审核获取年===============");
		List<Map> yearList = oemForecastService.getYearList();
		return yearList;
	}
	
	/**
	 * 
	* @Title: getTaskCode 
	* @Description:  生产订单审核（获取任务编号）
	* @param @param yearId 年份
	* @param @param monthId 月份Id（关联 系统固定代码表）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/year/{yearId}/month/{monthId}/taskCode",
			method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getTaskCode(@PathVariable("yearId") String yearId,
			@PathVariable("monthId") String monthId,
			@RequestParam Map<String,String> queryParam) {
		logger.info("============生产订单审核获取任务编号===============");
		queryParam.put("yearcode", yearId);
		queryParam.put("monthcode", monthId);
		List<Map> taskCodeList = oemForecastService.getTaskCode(queryParam);
		return taskCodeList;
	}
	
	/**
	 * 
	* @Title: forecastAuditQuery 
	* @Description:  生产订单审核查询
	* @param @param queryParam 包含年，月，任务编号
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastAuditQuery",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto forecastAuditQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============生产订单审核查询===============");
		if(StringUtils.isNullOrEmpty(queryParam.get("taskCodeName"))){
			return new PageInfoDto();
		}
		PageInfoDto pageInfoDto = oemForecastService.getOTDForecastQueryList(queryParam);
		return pageInfoDto;
	}
	
	 /**
	  * 
	 * @Title: forecastAuditDatas 
	 * @Description: 生产订单审核导出 
	 * @param @param queryParam
	 * @param @param request
	 * @param @param response
	 * @param @throws Exception    设定文件 
	 * @return void    返回类型 
	 * @throws
	  */
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void forecastAuditDatas(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		logger.info("============生产订单审核导出===============");
        List<Map> resultList = oemForecastService.queryforecastAuditDataExport(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("生产订单审核信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("BIG_AREA","大区"));
        exportColumnList.add(new ExcelExportColumn("SMALL_AREA","小区"));
        exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
        exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
        exportColumnList.add(new ExcelExportColumn("TASK_ID","任务编号"));
        exportColumnList.add(new ExcelExportColumn("SERIES_CODE","车系代码"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系名称"));
        exportColumnList.add(new ExcelExportColumn("MODEL_CODE","CPOS"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型名称"));
        exportColumnList.add(new ExcelExportColumn("GROUP_CODE","车款代码"));
        exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车款名称"));
        exportColumnList.add(new ExcelExportColumn("COLOR_CODE","颜色代码"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色描述"));
        exportColumnList.add(new ExcelExportColumn("TRIM_CODE","内饰代码"));
        exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰描述"));
        exportColumnList.add(new ExcelExportColumn("DETAIL_ID","DETAILID"));
        exportColumnList.add(new ExcelExportColumn("MATERIAL_ID","物料ID"));
        exportColumnList.add(new ExcelExportColumn("FORECAST_ID","FORECASTID"));
        exportColumnList.add(new ExcelExportColumn("REQUIRE_NUM","需求数量"));
        exportColumnList.add(new ExcelExportColumn("CONFIRM_NUM","OTD确认数量"));
        excelService.generateExcel(excelData, exportColumnList,"生产需求审核下载"+Utility.getCurrentTime(10)+".xls", request, response);
    }
	
	/**
	 * 
	* @Title: importforecastAudit 
	* @Description: 生产订单审核导入 
	* @param @param importFile
	* @param @param forecastImportDto
	* @param @param uriCB
	* @param @return
	* @param @throws Exception    设定文件 
	* @return List<OemForecastAuditDTO>    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/importforecastAudit", method = RequestMethod.POST)
    @ResponseBody
    public List<OemForecastAuditDTO> importforecastAudit(
    		@RequestParam final Map<String,String> queryParam,
    		@RequestParam(value = "file") MultipartFile importFile,
    		ForecastImportDto forecastImportDto,
    		UriComponentsBuilder uriCB) throws Exception {
		logger.info("============生产订单审核导入===============");
        // 解析Excel 表格(如果需要进行回调)
        ImportResultDto<OemForecastAuditDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<OemForecastAuditDTO>(OemForecastAuditDTO.class,new ExcelReadCallBack<OemForecastAuditDTO>() {
            @Override
            public void readRowCallBack(OemForecastAuditDTO rowDto, boolean isValidateSucess) {
                try{
                    logger.debug("bigArea:"+rowDto.getBigArea());
                    // 只有全部是成功的情况下，才执行数据库保存
                    if(isValidateSucess){
                    	//清空数据
//                    	TmpVsProImpInfoAuditPO tvpiaPo=new TmpVsProImpInfoAuditPO();
                    	TmpVsProImpInfoAuditPO.deleteAll();
    					
    					// 将数据插入临时表
    					long sTime=System.currentTimeMillis();
    					oemForecastService.insertTmpVsProImpAudit(rowDto);
    					
    					logger.info("生产需求审核导入插入临时表数据,用时："+String.format("%.2f",(System.currentTimeMillis()-sTime)*0.001)+"秒");
    					long startTime=System.currentTimeMillis();
//    					String taskId = queryParam.get("");
//    					List<ExcelErrors> errorList =checkData(taskId);
    					logger.info("生产需求审核导入检验数据,用时："+String.format("%.2f",(System.currentTimeMillis()-startTime)*0.001)+"秒");
    					
    						//验证通过
    						/*String dIds="";
    						String mIds="";
    						String cNums="";
    						String fIds="";
    						List<Map<String,Object>> tmpList = dao.getTmVsProImpAudit();
    						if(tmpList!=null && tmpList.size()>0){
    							for (int i = 0; i < tmpList.size(); i++) {
    								if(i<tmpList.size()-1){
    									dIds+=CommonUtils.checkNull(tmpList.get(i).get("DETAIL_ID"))+",";
    									mIds+=CommonUtils.checkNull(tmpList.get(i).get("MATERIAL_ID"))+",";
    									cNums+=CommonUtils.checkNull(tmpList.get(i).get("CONFIRM_NUM"))+",";
    									fIds+=CommonUtils.checkNull(tmpList.get(i).get("FORECAST_ID"))+",";
    								}else{
    									dIds+=CommonUtils.checkNull(tmpList.get(i).get("DETAIL_ID"));
    									mIds+=CommonUtils.checkNull(tmpList.get(i).get("MATERIAL_ID"));
    									cNums+=CommonUtils.checkNull(tmpList.get(i).get("CONFIRM_NUM"));
    									fIds+=CommonUtils.checkNull(tmpList.get(i).get("FORECAST_ID"));
    								}
    							}
    						}*/

    						/*act.setOutData("n3year",year);
    						act.setOutData("nowMonth",month);
    						act.setOutData("ftaskId",taskId);
    						act.setOutData("type", 2);//导入完毕
    						act.setOutData("dIds", dIds);
    						act.setOutData("mIds", mIds);
    						act.setOutData("cNums", cNums);
    						act.setOutData("fIds", fIds);
    						act.setForword(forecastOTDAudit);*/
    						
    				}
                    
                }catch(Exception e){
                    throw e;
                }
            }
        }));
        logger.debug("param:" + forecastImportDto.getFileParam());
        if(importResult.isSucess()){
            return importResult.getDataList();
        }else{
            throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
        }
    }
	
	/**
	 * 
	* @Title: forecastQueryInit 
	* @Description: 生产订单查询（查询年） 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastQueryInit",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> forecastQueryInit() {
		logger.info("============生产订单查询获取年===============");
		List<Map> yearList = oemForecastService.forecastQueryInit();
		return yearList;
	}
	
	/**
	 * 
	* @Title: getMonthPlanTaskNoList2 
	* @Description: 生产订单查询（获取任务编号）
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastQueryInit/month/getMonthPlanTaskNoList2",
			method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getMonthPlanTaskNoList2(
			@RequestParam Map<String,String> queryParam) {
		logger.info("============生产订单查询获取任务编号===============");
		List<Map> yearList = oemForecastService.getMonthPlanTaskNoList2();
		return yearList;
	}
	
	/**
	 * 
	* @Title: forecastQueryTotal 
	* @Description:生产订单查询(大区汇总)  
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastQueryTotal",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto forecastQueryTotal(@RequestParam Map<String, String> queryParam) {
		logger.info("============生产订单查询(大区汇总)===============");
		PageInfoDto pageInfoDto = oemForecastService.forecastQueryTotal(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: forecastQueryTotal2 
	* @Description:  生产订单查询（全国汇总）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastQueryTotal2",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto forecastQueryTotal2(@RequestParam Map<String, String> queryParam) {
		logger.info("============生产订单查询(全国汇总)===============");
		PageInfoDto pageInfoDto = oemForecastService.getOemForecastQueryTotalList2(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: forecastQueryDetail 
	* @Description: 生产订单查询（明细查询）
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/forecastQueryDetail",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto forecastQueryDetail(@RequestParam Map<String, String> queryParam) {
		logger.info("============生产订单查询(明细查询)===============");
		PageInfoDto pageInfoDto = oemForecastService.getOemForecastQueryDetailList(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: findNoHandInDealer 
	* @Description:生产订单查询（未提报组织）  
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/findNoHandInDealer",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findNoHandInDealer(@RequestParam Map<String, String> queryParam) {
		logger.info("============生产订单查询(未提报组织)===============");
		PageInfoDto pageInfoDto = oemForecastService.findNoHandInDelears2(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: forecastTotalDownload 
	* @Description: 生产订单查询（大区汇总下载） 
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/forecastTotalDownload/export/excel", method = RequestMethod.GET)
    public void forecastTotalDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		logger.info("===========生产订单查询（大区汇总下载）==============");
        List<Map> resultList = oemForecastService.forecastTotalDownload(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("生产订单查询（大区汇总下载）信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("ORG_NAME","区域"));
        exportColumnList.add(new ExcelExportColumn("SERIES_CODE","车系代码"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系名称"));
        exportColumnList.add(new ExcelExportColumn("MODEL_CODE","车型代码"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型名称"));
        exportColumnList.add(new ExcelExportColumn("GROUP_CODE","车款代码"));
        exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车款名称"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
        exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
        exportColumnList.add(new ExcelExportColumn("REQUIRE_NUM","需求数量"));
        exportColumnList.add(new ExcelExportColumn("CONFIRM_NUM","OTD确认数量"));
        exportColumnList.add(new ExcelExportColumn("REPORT_NUM","上报数量"));
        exportColumnList.add(new ExcelExportColumn("PAY_NUM","支付定金数量"));
        excelService.generateExcel(excelData, exportColumnList,"生产需求大区汇总下载.xls", request, response);
    }
	
	/**
	 * 
	* @Title: forecastTotalDownload2 
	* @Description: 生产订单查询（全国汇总下载） 
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/forecastTotalDownload2/export/excel", method = RequestMethod.GET)
    public void forecastTotalDownload2(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		logger.info("===========生产订单查询（全国汇总下载）==============");
        List<Map> resultList = oemForecastService.forecastTotalDownload2(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("生产订单查询（全国汇总下载）信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("SERIES_CODE","车系代码"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系名称"));
        exportColumnList.add(new ExcelExportColumn("MODEL_CODE","车型代码"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型名称"));
        exportColumnList.add(new ExcelExportColumn("GROUP_CODE","车款代码"));
        exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车款名称"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
        exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
        exportColumnList.add(new ExcelExportColumn("REQUIRE_NUM","需求数量"));
        exportColumnList.add(new ExcelExportColumn("CONFIRM_NUM","OTD确认数量"));
        exportColumnList.add(new ExcelExportColumn("REPORT_NUM","上报数量"));
        exportColumnList.add(new ExcelExportColumn("PAY_NUM","支付定金数量"));
        excelService.generateExcel(excelData, exportColumnList,"生产订单查询（全国汇总下载）.xls", request, response);
    }
	
	/**
	 * 
	* @Title: forecastTotalDownload 
	* @Description: 生产订单查询（明细下载） 
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/forecastDetailDownload/export/excel", method = RequestMethod.GET)
    public void forecastDetailDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		logger.info("===========生产订单查询（明细下载）==============");
        List<Map> resultList = oemForecastService.forecastDetailDownload(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("生产订单查询（明细下载）信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("PORG_NAME","大区"));
        exportColumnList.add(new ExcelExportColumn("ORG_NAME","小区"));
        exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
        exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME","经销商名称"));
        exportColumnList.add(new ExcelExportColumn("SERIES_CODE","车系代码"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系名称"));
        exportColumnList.add(new ExcelExportColumn("MODEL_CODE","车型代码"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型名称"));
        exportColumnList.add(new ExcelExportColumn("GROUP_CODE","车款代码"));
        exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车款名称"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
        exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
        exportColumnList.add(new ExcelExportColumn("REQUIRE_NUM","需求数量"));
        exportColumnList.add(new ExcelExportColumn("CONFIRM_NUM","OTD确认数量"));
        exportColumnList.add(new ExcelExportColumn("REPORT_NUM","上报数量"));
        exportColumnList.add(new ExcelExportColumn("PAY_NUM","支付定金数量"));
        excelService.generateExcel(excelData, exportColumnList,"生产订单查询（明细下载）.xls", request, response);
    }
	
	/**
	 * 
	* @Title: noHandInDealerDownload 
	* @Description: 生产订单查询（未提报组织下载） 
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/noHandInDealerDownload/export/excel", method = RequestMethod.GET)
    public void noHandInDealerDownload(@RequestParam Map<String, String> queryParam,
    		HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("===========生产订单查询（未提报组织下载）==============");
        List<Map> resultList = oemForecastService.noHandInDealerDownload(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("生产订单查询（未提报组织下载）信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("SERIAL_NUMBER","序号"));
        exportColumnList.add(new ExcelExportColumn("SWT_CODE","SAP代码"));
        exportColumnList.add(new ExcelExportColumn("REGION","大区"));
        exportColumnList.add(new ExcelExportColumn("COMMUNITY","小区"));
        exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
        exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
        exportColumnList.add(new ExcelExportColumn("LINK_MAN","联系人"));
        exportColumnList.add(new ExcelExportColumn("FOUND_DATE","开业时间"));
        exportColumnList.add(new ExcelExportColumn("LINK_MAN_MOBILE","联系电话"));
        excelService.generateExcel(excelData, exportColumnList,"生产订单查询（未提报组织下载）.xls", request, response);
    }
	
	/**
	 * 
	* @Title: getMonthForecastYearList 
	* @Description: 生产订单序列号跟踪(OTD)（获取年） 
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/getMonthForecastYearList",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getMonthForecastYearList() {
		logger.info("===========生产订单号序列号跟踪(OTD)（获取年） ==============");
		List<Map> yearList = oemForecastService.getMonthForecastYearList();
		return yearList;
	}
	
	/**
	 * 
	* @Title: getTaskCode 
	* @Description: 生产订单序列号跟踪(OTD)（获取任务编号） 
	* @param @param yearId
	* @param @param monthId
	* @param @param queryParam
	* @param @return    设定文件 
	* @return List<Map>    返回类型 
	* @throws
	 */
	@RequestMapping(value="/year/{yearId}/month/{monthId}/getReportTaskNoList",
			method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getReportTaskNoList(@PathVariable("yearId") String yearId,
			@PathVariable("monthId") String monthId,
			@RequestParam Map<String,String> queryParam) {
		logger.info("===========生产订单号序列号跟踪(OTD)（获取任务编号）  =============="); 
		queryParam.put("yearcode", yearId);
		queryParam.put("monthcode", monthId);
		List<Map> taskCodeList = oemForecastService.getReportTaskNoList(queryParam);
		return taskCodeList;
	}
	
	/**
	 * 
	* @Title: OTDfindBySerialNumber 
	* @Description:生产订单序列号跟踪(OTD)（查询）  
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/OTDfindBySerialNumber",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto OTDfindBySerialNumber(@RequestParam Map<String, String> queryParam) {
		logger.info("============生产订单号序列号跟踪(OTD)（查询） ===============");
		PageInfoDto pageInfoDto = oemForecastService.OTDfindBySerialNumber(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 
	* @Title: OemForecastQueryDownload 
	* @Description:  生产订单序列号跟踪(OTD)(下载)
	* @param @param queryParam
	* @param @param request
	* @param @param response
	* @param @throws Exception    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@RequestMapping(value = "/OemForecastQueryDownload/export/excel",
			method = RequestMethod.GET)
    public void OemForecastQueryDownload(@RequestParam Map<String, String> queryParam,
    		HttpServletRequest request,
                                  HttpServletResponse response) throws Exception {
		logger.info("===========生产订单序列号跟踪(OTD)(下载)==============");
        List<Map> resultList = oemForecastService.getOemForecastQueryQueryListOtd(queryParam);
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("生产订单序列号跟踪(OTD)信息", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("BIG_AREA","大区"));
        exportColumnList.add(new ExcelExportColumn("SMALL_AREA","小区"));
        exportColumnList.add(new ExcelExportColumn("DEALER_CODE","经销商代码"));
        exportColumnList.add(new ExcelExportColumn("DEALER_NAME","经销商名称"));
        exportColumnList.add(new ExcelExportColumn("MONTH","月份"));
        exportColumnList.add(new ExcelExportColumn("TASK_ID","任务编号"));
        exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
        exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
        exportColumnList.add(new ExcelExportColumn("MODEL_YEAR","车款"));
        exportColumnList.add(new ExcelExportColumn("GROUP_NAME","年款"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
        exportColumnList.add(new ExcelExportColumn("TRIM_NAME","内饰"));
        exportColumnList.add(new ExcelExportColumn("PON","PON号"));
        exportColumnList.add(new ExcelExportColumn("STATUS","状态"));
        excelService.generateExcel(excelData, exportColumnList,"生产订单序列号跟踪(OTD).xls", request, response);
    }
	
	
	
}
