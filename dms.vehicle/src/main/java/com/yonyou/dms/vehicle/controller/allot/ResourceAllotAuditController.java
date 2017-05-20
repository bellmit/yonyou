package com.yonyou.dms.vehicle.controller.allot;

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

import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.service.allot.ResourceAllotAuditService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 资源分配审核
 * @author 夏威
 * 2017-04-27
 */
@SuppressWarnings("rawtypes")
@Controller
@TxnConn
@RequestMapping("/resourceAllotAudit")
public class ResourceAllotAuditController extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(ResourceAllotAuditController.class);
	
	@Autowired
	ResourceAllotAuditService service;
	 
	@Autowired
	private ExcelGenerator      excelService;
	
	/**
	 * 资源分配审核区域初始化
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/areaInit", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getArea() {
		logger.info("==============资源分配审核区域初始化=============");
		List<Map> list = service.getArea();
		return list;
	}
	/**
	 * 资源分配审核车系初始化
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/seriesInit", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getSeries() {
		logger.info("==============资源分配审核车系初始化=============");
		List<Map> list = service.getSeries();
		return list;
	}
	
	/**
	 * 资源分配审核日期初始化
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/allotDateInit", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getAllotDate() {
		logger.info("==============资源分配审核日期初始化=============");
		List<Map> list = service.getAllotDate("0");
		if(list.size()==0){
			list = service.getAllotDate("1");
		}
		return list;
	}
	/**
	 * 
	* @Description: 资源分配审核
	* @param @param queryParam
	* @param @return    设定文件 
	* @return PageInfoDto    返回类型 
	* @throws
	 */
	@RequestMapping(value="/audit/search",method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> orderTopQueryDetialInfoListQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("=============资源分配审核查询==============");
		Map<String, Object> result = service.findAll(queryParam);
		return result;
	}
	
	/**
	 * 资源分配审核下载
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/excel", method = RequestMethod.GET)
	public void downLoadList(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
            HttpServletResponse response) throws Exception {
		logger.info("============资源分配审核下载===============");
		Map<String, Object> result = service.findAll(queryParam);
		Map<String, List<Map>> excelData = service.getExcelData(result);
	    Map<String, List<ExcelExportColumn>> exportColumnList = service.getColumnData(result);
	    excelService.generateExcel(excelData, exportColumnList, "资源分配审核.xls", request,response);
	}
	
}
