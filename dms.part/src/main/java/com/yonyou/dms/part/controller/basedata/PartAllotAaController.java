package com.yonyou.dms.part.controller.basedata;


import java.math.BigDecimal;
import java.text.SimpleDateFormat;
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

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TTtPtAllotOutDcsPO;
import com.yonyou.dms.part.service.basedata.PartAllotAaService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 配件订单管理
 * @author ZhaoZ
 * @date 2017年3月24日 
 */
@Controller
@TxnConn
@RequestMapping("/partAllot")
public class PartAllotAaController {
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartAllotAaController.class);
   
    @Autowired
	private PartAllotAaService partAllotService;
    @Autowired
	private ExcelGenerator excelService;
    /**
	 * 配件调拨单查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryAllotInfo",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryAllotInfo(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件调拨单查询=====");
		 PageInfoDto dto = partAllotService.queryAllot(queryParams);
		 return partAllotService.queryAllot(queryParams);
		
	}
	
	/**
	 * 配件调拨单下载
	 * @param request
	 * @param response
	 * @param queryParams
	 */
	@RequestMapping(value="/download",method = RequestMethod.GET)
	@ResponseBody
	public void download(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============配件调拨单下载===============");
		List<Map> dealerList = partAllotService.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("配件调拨单导出",dealerList);
		exportColumnList.add(new ExcelExportColumn("FROM_DLR_CODE", "调出经销商代码"));
		exportColumnList.add(new ExcelExportColumn("OUT_DEALER_SHORTNAME", "调出经销商名称"));
		exportColumnList.add(new ExcelExportColumn("TO_DLR_CODE", "调入经销商代码"));
		exportColumnList.add(new ExcelExportColumn("IN_DEALER_SHORTNAME", "调入经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ALLOCATE_TYPE", "调拨类型"));
		exportColumnList.add(new ExcelExportColumn("OUT_NO", "调出单号"));
		exportColumnList.add(new ExcelExportColumn("IN_NO", "调入单号"));
		exportColumnList.add(new ExcelExportColumn("OUT_AMOUNT", "出库金额"));
		exportColumnList.add(new ExcelExportColumn("COST_AMOUNT", "出库成本金额"));
		exportColumnList.add(new ExcelExportColumn("IN_AMOUNT", "入库金额"));
		exportColumnList.add(new ExcelExportColumn("DNP_AMOUNT", "含税DNP金额"));
		exportColumnList.add(new ExcelExportColumn("MSRP_AMOUNT", "含税MSRP金额"));
		exportColumnList.add(new ExcelExportColumn("OUT_DATE", "出库日期"));
		exportColumnList.add(new ExcelExportColumn("IN_DATE", "入库日期"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态"));	
		
		excelService.generateExcel(excelData, exportColumnList, "配件调拨单.xls", request, response);
	}
	
	/**
	  * 配件调拨单查询明细
	  * @param queryParams
	  * @throws Exception
	  */
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/queryDetailInfo/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryDetailInfo(@PathVariable(value = "id") BigDecimal id) {
		logger.info("=====直发交货单修改回显信息=====");
		SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd");
		Map<String, Object> mapA = new HashMap<String, Object>();
		Map<String, Object> mapC = new HashMap<String, Object>();
		TTtPtAllotOutDcsPO out = TTtPtAllotOutDcsPO.findById(id);
		Map<String, Object> mapB = null;
		if(out!=null){
			mapB = out.toMap();
			String sql = "SELECT DISTINCT DEALER_SHORTNAME outDealerName FROM TM_DEALER WHERE DEALER_CODE = '"+out.getString("FROM_DLR_CODE")+"'";
			mapA = OemDAOUtil.findFirst(sql, null);
			mapA.put("outDate", formatter.format(out.getDate("OUT_DATE")));
			String sql1 = "SELECT DISTINCT DEALER_SHORTNAME inDealerName FROM TM_DEALER WHERE DEALER_CODE = '"+out.getString("TO_DLR_CODE")+"'";
			mapC = OemDAOUtil.findFirst(sql1, null);
			
		}
		mapA.putAll(mapC);
		if(mapB!=null){
			mapB.putAll(mapA);
			return mapB;
		}
	    
		return mapA;
	}
	
	
	 /**
	  * 配件调拨单查询明细
	  * @param queryParams
	  * @throws Exception
	  */
	 @RequestMapping(value="/getAllotOutDeInfo/{id}",method = RequestMethod.GET)
	 @ResponseBody
	 public PageInfoDto getAllotOutDeInfo(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====配件调拨单查询明细=====");
			
		 return partAllotService.queryAllotOutDeInfo(id);
			
	}
	
	
	 /**
	  * 配件调拨单明细查询
	  * @param queryParams
	  * @return
	  */
	 @RequestMapping(value="/queryPartAllotDetailInfo",method = RequestMethod.GET)
		@ResponseBody
	 public PageInfoDto queryPartAllotDetailInfo(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件调拨单明细查询=====");
			
		 return partAllotService.partAllotDetailInfo(queryParams);
	}
	
	
	
	   /**
		 * 配件调拨单明细下载
		 * @param request
		 * @param response
		 * @param queryParams
		 */
		@RequestMapping(value="/downAllotDetailInfo",method = RequestMethod.GET)
		@ResponseBody
		public void downAllotDetailInfo(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
			logger.info("============配件调拨单明细下载===============");
			List<Map> dealerList = partAllotService.queryDownLoad(queryParams);
			List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		
			Map<String, List<Map>> excelData = new HashMap<>();
			excelData.put("配件调拨单导出",dealerList);
			exportColumnList.add(new ExcelExportColumn("FROM_DLR_CODE", "调出经销商代码"));
			exportColumnList.add(new ExcelExportColumn("OUT_DEALER_SHORTNAME", "调出经销商名称"));
			exportColumnList.add(new ExcelExportColumn("TO_DLR_CODE", "调入经销商代码"));
			exportColumnList.add(new ExcelExportColumn("IN_DEALER_SHORTNAME", "调入经销商名称"));
			exportColumnList.add(new ExcelExportColumn("ALLOCATE_TYPE", "调拨类型"));
			exportColumnList.add(new ExcelExportColumn("OUT_NO", "出库单号"));
			exportColumnList.add(new ExcelExportColumn("IN_NO", "入库单号"));
			exportColumnList.add(new ExcelExportColumn("PART_NO", "配件代码"));
			exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
			exportColumnList.add(new ExcelExportColumn("UNIT_NAME", "单位"));
			exportColumnList.add(new ExcelExportColumn("OUT_QUANTITY", "数量"));
			exportColumnList.add(new ExcelExportColumn("OUT_STORAGE_CODE", "出库仓库"));
			exportColumnList.add(new ExcelExportColumn("OUT_PRICE", "出库单价"));
			exportColumnList.add(new ExcelExportColumn("OUT_AMOUNT", "出库金额"));
			exportColumnList.add(new ExcelExportColumn("COST_PRICE", "出库成本单价"));
			exportColumnList.add(new ExcelExportColumn("COST_AMOUNT", "出库成本金额"));
			exportColumnList.add(new ExcelExportColumn("DNP_PRICE", "含税DNP单价"));
			exportColumnList.add(new ExcelExportColumn("DNP_AMOUNT", "含税DNP行价"));
			exportColumnList.add(new ExcelExportColumn("MSRP_PRICE", "含税MSRP单价"));
			exportColumnList.add(new ExcelExportColumn("MSRP_AMOUNT", "含税MSRP行价"));
			exportColumnList.add(new ExcelExportColumn("IN_STORAGE_CODE", "入库仓库"));
			exportColumnList.add(new ExcelExportColumn("IN_PRICE", "入库单价"));
			exportColumnList.add(new ExcelExportColumn("IN_AMOUNT", "入库金额"));
			exportColumnList.add(new ExcelExportColumn("OUT_DATE", "出库日期"));
			exportColumnList.add(new ExcelExportColumn("IN_DATE", "入库日期"));
			exportColumnList.add(new ExcelExportColumn("STATUS", "状态"));	
			
			excelService.generateExcel(excelData, exportColumnList, "配件调拨单明细.xls", request, response);
		}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
