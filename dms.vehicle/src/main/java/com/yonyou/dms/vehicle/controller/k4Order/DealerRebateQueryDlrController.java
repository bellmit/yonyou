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
import com.yonyou.dms.vehicle.dao.k4Order.DealerRebateQueryDlrDao;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 *
 * 
 * @author liujiming
 * @date 2017年3月15日
 */
@Controller
@TxnConn
@RequestMapping("/dealerRebateDlr")
public class DealerRebateQueryDlrController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DealerRebateQueryDlrController.class);

	@Autowired
	private ExcelGenerator excelService;

	@Autowired
	private DealerRebateQueryDlrDao deaRebDlrDao;

	/**
	 * 返利使用明细 查询
	 * 
	 * @param queryParam
	 *            查询条件
	 * @return pageInfoDto 查询结果
	 */
	@RequestMapping(value = "/rebate/Query", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto orderDealerRebateDlrQuery(@RequestParam Map<String, String> queryParam) {
		// System.out.println("queryInitController");
		logger.info("============经销商返利管理>返利发放（经销商）查询03==============");
		PageInfoDto pageInfoDto = deaRebDlrDao.getDealerRebateDlrQueryList(queryParam);

		return pageInfoDto;

	}

	/**
	 * 返利发放查询下载 *
	 * 
	 * @param queryParam
	 *            查询条件
	 */
	@RequestMapping(value = "/rebate/Download", method = RequestMethod.GET)
	public void orderDealerRebateDlrQueryDownload(@RequestParam Map<String, String> queryParam,
			HttpServletRequest request, HttpServletResponse response) {
		logger.info("============经销商返利管理>返利发放（经销商） 下载03===============");
		List<Map> orderList = deaRebDlrDao.getDealerRebateDlrDownloadList(queryParam);
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		String format = df.format(new Date());
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("返利发放查询下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "返利发放日期"));
		exportColumnList.add(new ExcelExportColumn("REBATE_TYPE", "返利类型"));
		exportColumnList.add(new ExcelExportColumn("REBATE_CODE", "返利代码"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("REBATE_AMOUNT", "返利金额"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		excelService.generateExcel(excelData, exportColumnList, "返利发放查询下载" + format + ".xls", request, response);

	}

}
