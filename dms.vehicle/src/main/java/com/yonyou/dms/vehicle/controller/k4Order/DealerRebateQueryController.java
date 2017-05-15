package com.yonyou.dms.vehicle.controller.k4Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.dao.k4Order.DealerRebateQueryDao;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *
 * 
 * @author liujiming
 * @date 2017年3月13日
 */
@Controller
@TxnConn
@RequestMapping("/dealerRebateQuery")
public class DealerRebateQueryController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DealerRebateQueryController.class);

	@Autowired
	private DealerRebateQueryDao dealerRebDao;

	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 返利发放查询 汇总查询
	 * 
	 * @param queryParam
	 *            查询条件
	 * @return pageInfoDto 查询结果
	 */
	@RequestMapping(value = "/dealerRebate/totalQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto orderInfoTotalQuery(@RequestParam Map<String, String> queryParam) {
		// System.out.println("queryInitController");
		logger.info("============经销商返利管理>返利发放汇总查询02==============");
		PageInfoDto pageInfoDto = dealerRebDao.getDealerRebateTotalQueryList(queryParam);

		return pageInfoDto;

	}

	/**
	 * 返利发放查询 下载
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	@RequestMapping(value = "/dealerRebate/detailDownload", method = RequestMethod.GET)
	public void orderInfoTotalQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============经销商返利管理>返利发放查询下载02===============");
		List<Map> orderList = dealerRebDao.getDealerRebateDetailDownloadList(queryParam);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String format = df.format(new Date());
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("返利发放查询下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("REBATE_CODE", "返利代码"));
		exportColumnList.add(new ExcelExportColumn("REBATE_NAME", "返利名称"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "上传日期"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("REBATE_TOTAL", "上传金额"));
		exportColumnList.add(new ExcelExportColumn("REBATE_USE", "使用金额"));
		exportColumnList.add(new ExcelExportColumn("REMAIN_MONEY", "余额"));
		excelService.generateExcel(excelData, exportColumnList, "返利发放查询下载" + format + ".xls", request, response);

	}

	/**
	 * 返利发放查询汇总 下载
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	@RequestMapping(value = "/dealerRebate/totalDownload", method = RequestMethod.GET)
	public void totalDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============经销商返利管理>返利发放查询下载02===============");
		List<Map> orderList = dealerRebDao.getDealerRebatetotalDownloadList(queryParam);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String format = df.format(new Date());
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("返利发放查询下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("REBATE_CODE", "返利代码"));
		exportColumnList.add(new ExcelExportColumn("REBATE_NAME", "返利名称"));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "上传日期"));
		exportColumnList.add(new ExcelExportColumn("REBATE_TOTAL", "上传金额"));
		exportColumnList.add(new ExcelExportColumn("REBATE_USE", "使用金额"));
		exportColumnList.add(new ExcelExportColumn("REMAIN_MONEY", "余额"));
		excelService.generateExcel(excelData, exportColumnList, "返利发放汇总查询下载" + format + ".xls", request, response);

	}

	/**
	 * 返利发放查询 明细查询
	 * 
	 * @param queryParam
	 *            查询条件
	 * @return pageInfoDto 查询结果
	 */
	@RequestMapping(value = "/dealerRebate/detailQuery", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto orderInfoDetailQuery(@RequestParam Map<String, String> queryParam) {
		logger.info("============经销商返利管理>返利发放明细查询02==============");
		PageInfoDto pageInfoDto = dealerRebDao.getDealerRebateDetailQueryList(queryParam);
		return pageInfoDto;
	}

}
