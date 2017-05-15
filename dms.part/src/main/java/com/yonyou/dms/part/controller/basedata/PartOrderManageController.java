package com.yonyou.dms.part.controller.basedata;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDetailDcsPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.part.domains.DTO.basedata.TtPtOrderDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TtPtOrderDetailMopDcsPO;
import com.yonyou.dms.part.service.basedata.PartOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 配件订单管理
 * @author ZhaoZ
 * @date 2017年3月24日 
 */
@Controller
@TxnConn
@SuppressWarnings("all")
@RequestMapping("/partOrderManage")
public class PartOrderManageController {
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
   
    @Autowired
	private PartOrderService partOrderService;
    @Autowired
	private ExcelGenerator excelService;
    /**
	 * 配件订单审核查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/checkOrderInfo",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto checkOrderInfo(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件订单审核查询=====");
		
		 return partOrderService.checkOrderPartInfo(queryParams);
		
	}
	
	 
	 /**
	  * 配件订单审核回显信息
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/orderCheckInit/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> orderCheckInit(@PathVariable(value = "id") BigDecimal id) {
		logger.info("=====配件订单审核回显信息=====");
		Map<String, Object> mapA = partOrderService.findDealerInfoByOrderId(id);
		TtPtOrderDcsPO po = TtPtOrderDcsPO.findById(id);
		Map<String, Object> mapB = null;
		if(po!=null){
			mapB = po.toMap();
		}
		if(mapA == null){
			return mapB;	
		}
		mapA.putAll(mapB);
		return mapA;
	}
	
	/**
	  * 配件订单审核回显信息dlr
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/orderCheckInitDlr/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> orderCheckInitDlr(@PathVariable(value = "id") BigDecimal id) {
		logger.info("=====配件订单审核Dlr回显信息=====");
		String flag = "";
		Map<String, Object> mapA = partOrderService.findDealerInfoByOrderId(id);
		TtPtOrderDcsPO po = TtPtOrderDcsPO.findById(id);
		Map<String, Object> mapB = null;
		if(po!=null){
			mapB = po.toMap();
			Integer isMop = po.getInteger("IS_MOP_ORDER");
			if(isMop!=null){
				if(OemDictCodeConstants.IF_TYPE_YES.intValue()== isMop){
	    	    	flag = "1";
	    	   }else{
	    	    	flag = "0";
	    	   }
			 mapB.put("FLAG", flag);
			}
			 
		}
	
		if(mapA == null){
			return mapB;	
		}
		mapA.putAll(mapB);
		return mapA;
	}
 	/**
	 * 配件信息
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryPartDetail/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartDetail(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====配件信息=====");
		 List<Object> params = new ArrayList<Object>();
		 StringBuilder sql = new StringBuilder();
		 sql.append("   SELECT PART_CODE,PART_NAME,PACKAGE_NUM,ORDER_NUM, \n");
		 sql.append("   NO_TAX_PRICE,NO_TAX_AMOUNT,UNIT,DISCOUNT \n");
		 sql.append("   FROM tt_pt_order_detail_dcs TT WHERE TT.ORDER_ID = ? \n");
		 params.add(id);
		 return OemDAOUtil.pageQuery(sql.toString(), params);
	}
	
	/**
	 * 附件信息
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryPartFuJian/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartFuJian(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====附件信息=====");
		String sql = "SELECT * FROM tt_pt_order_att_dcs TA WHERE TA.ORDER_ID = "+id+"";
		return OemDAOUtil.pageQuery(sql, null);
	}
	
	/**
	 * 审核历史
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/checkHidtoryInfoList/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto checkHidtoryInfoList(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====审核历史=====");
		
		 return partOrderService.checkHidtoryInfo(id);
		 
	}
	/**
	 * 配件订单审核
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/checkAgree/{id}/{type}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtPtOrderDcsDTO> checkAgree(@RequestBody TtPtOrderDcsDTO dto,@PathVariable(value = "id") Long id,UriComponentsBuilder uriCB,
			@PathVariable(value = "type") String type){
		logger.info("==================配件订单审核================");
		partOrderService.checkAgreeService(id,type,dto);
		
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/saveDeliveryOrder").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	/**
	 * 信息导出
	 */
	@RequestMapping(value="/download",method = RequestMethod.GET)
	@ResponseBody
	public void download(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============信息导出===============");
		List<Map> dealerList = partOrderService.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("配件订单审核导出",dealerList);
		exportColumnList.add(new ExcelExportColumn("BIG_ORG_NAME", "大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "订单号"));
		exportColumnList.add(new ExcelExportColumn("SAP_ORDER_NO", "SAP订单号"));
		exportColumnList.add(new ExcelExportColumn("IS_ATT", "是否有附件",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("REPORT_DATE", "提报时间"));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUS", "状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("ELEC_CODE", "电子编码"));
		exportColumnList.add(new ExcelExportColumn("MECH_CODE", "机械代码"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "车主姓名"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_PHONE", "联系电话"));
		exportColumnList.add(new ExcelExportColumn("LICENSE_NO", "车牌号"));
		exportColumnList.add(new ExcelExportColumn("KEY_CODE", "锁芯机械码/CD序列号"));
		exportColumnList.add(new ExcelExportColumn("IS_EMERG", "是否通过应急启动",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("IS_REPAIR_BYSELF", "是否在未授权店私自更换",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("LEAVE_WORD", "留言"));
		
		excelService.generateExcel(excelData, exportColumnList, "配件订单审核.xls", request, response);
	}
	
	/**
	 * 查询经销商
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/SearchDealers",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto SearchDealers(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====查询经销商=====");
		
		 return partOrderService.getDealerList(queryParams);
		 
	}
	
	
	/**
	 * 配件订单查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryOrderInfo",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderInfo(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件订单查询=====");
		
		 return partOrderService.getOrderInfo(queryParams);
		 
	}
	/**
	 * 配件订单异常监控查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/orderInterFaceMonitorQuary",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto orderInterFaceMonitorQuary(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件订单异常监控查询=====");
		 return partOrderService.orderInterMonitorQuary(queryParams);
	}

	/**
	 * 重置
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/reset/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> reset(@RequestParam Map<String, String> queryParams,@PathVariable(value = "id") BigDecimal id,UriComponentsBuilder uriCB){
		logger.info("==================重置================");
		partOrderService.reset(id);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/reset").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
 	/**
	 * 发票信息查询
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryInvoice",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryInvoice(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====发票信息查询=====");
		
		 return partOrderService.queryInvoices(queryParams);
		
	}
	
	/**
	 * 配件发票查询 下载
	 */
	@RequestMapping(value="/downloadInvoice",method = RequestMethod.GET)
	@ResponseBody
	public void downloadInvoice(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============配件发票查询 下载===============");
		//List<Map> dealerList = partOrderService.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("配件发票信息导出",null);
		exportColumnList.add(new ExcelExportColumn("", "发票号"));
		exportColumnList.add(new ExcelExportColumn("", "开票日期"));
		exportColumnList.add(new ExcelExportColumn("", "客户"));
		exportColumnList.add(new ExcelExportColumn("", "发票金额"));
		exportColumnList.add(new ExcelExportColumn("", "消费税"));
		exportColumnList.add(new ExcelExportColumn("", "运费"));
		exportColumnList.add(new ExcelExportColumn("", "总价格"));
		excelService.generateExcel(excelData, exportColumnList, "配件发票信息.xls", request, response);
	}
	
	 /**
	  * 发票明细查询 
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/outputHeader",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> outputHeader() {
		logger.info("=====发票明细查询 =====");
		
		return null;
	}
	
	 /**
	  * 发票清单列表
	  * @throws Exception
	  */
	@RequestMapping(value="/listPutPotion",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto listPutPotion() {
		 logger.info("=====发票清单列表=====");
		
		 return new PageInfoDto();
		
	}
	
	/**
	 * 配件发票明细 下载
	 * 接口  內容沒寫
	 */
	@RequestMapping(value="/downloadInvoiceDetail",method = RequestMethod.GET)
	@ResponseBody
	public void downloadInvoiceDetail(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============配件发票查询 下载===============");
		//List<Map> dealerList = partOrderService.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("发票明细导出",null);
		
		excelService.generateExcel(excelData, exportColumnList, "发票明细.xls", request, response);
	}
	
	/**
	  * 配件Bo信息查询
	  * @throws Exception
	  */
	@RequestMapping(value="/queryBoOrder",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryBoOrder() {
		 logger.info("=====配件Bo信息查询=====");
		
		 return new PageInfoDto();
		
	}
	
	/**
	 * 配件Bo信息下载
	 */
	@RequestMapping(value="/downloadBoOrder",method = RequestMethod.GET)
	@ResponseBody
	public void downloadBoOrder(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============配件Bo信息下载===============");
		//List<Map> dealerList = partOrderService.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("配件Bo信息下载",null);
		
		excelService.generateExcel(excelData, exportColumnList, "配件Bo信息.xls", request, response);
	}
	
	/**
	 * 配件订单查询dlr
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryOrderInfoDlr",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderInfoDlr(@RequestParam Map<String, String> queryParams) {
		 logger.info("=====配件订单查询dlr=====");
		
		 return partOrderService.dlrQueryOrderInfo(queryParams);
		 
	}
	
	/**
	 * 配件订单查询dlr下载
	 */
	@RequestMapping(value="/downloadOrderDlr",method = RequestMethod.GET)
	@ResponseBody
	public void downloadOrderDlr(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============配件订单查询dlr下载===============");
		List<Map> dealerList = partOrderService.queryOrderDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("配件订单查询导出",dealerList);
		exportColumnList.add(new ExcelExportColumn("SAP_ORDER_NO", "SAP订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("IS_MOP_ORDER", "是否MOP件拆单",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ORDER_BALANCE", "订单总金额（万）"));
		exportColumnList.add(new ExcelExportColumn("IS_DIRECT_SELLING", "是否车主直销",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("IS_AFFIRM", "确认状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("DEAL_ORDER_AFFIRM_DATE", "确认时间"));
		exportColumnList.add(new ExcelExportColumn("ORDER_STATUS", "状态",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("EC_ORDER_NO", "电商订单号"));
		excelService.generateExcel(excelData, exportColumnList, "配件订单查询.xls", request, response);
	}
	
	/**
	 * 配件信息下载
	 */
	@RequestMapping(value="/downloadPart/{id}",method = RequestMethod.GET)
	@ResponseBody
	public void downloadPart(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "id") BigDecimal id) {
		logger.info("============配件订单查询dlr下载===============");
		List<Map> dealerList = new ArrayList<Map>();
		TtPtOrderDetailDcsPO po = TtPtOrderDetailDcsPO.findFirst("ORDER_ID = ?", id);
		Map map = po.toMap();
		if(!map.containsKey("package_num")){
			map.put("package_num", 0);
		}
		dealerList.add(po.toMap());
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("配件明细导出",dealerList);
		exportColumnList.add(new ExcelExportColumn("part_code", "配件编号"));
		exportColumnList.add(new ExcelExportColumn("part_name", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("package_num", "最小包装数"));
		exportColumnList.add(new ExcelExportColumn("order_num", "计划量"));
		exportColumnList.add(new ExcelExportColumn("no_tax_price", "不含税单价"));
		exportColumnList.add(new ExcelExportColumn("no_tax_amount", "不含税总额"));
		exportColumnList.add(new ExcelExportColumn("unit", "单位"));
		exportColumnList.add(new ExcelExportColumn("discount", "单个折扣"));
		excelService.generateExcel(excelData, exportColumnList, "配件明细.xls", request, response);
	}
	
	/**
	 * 配件信息下载
	 */
	@RequestMapping(value="/downloadPartMop/{id}",method = RequestMethod.GET)
	@ResponseBody
	public void downloadPartMop(HttpServletRequest request,HttpServletResponse response,@PathVariable(value = "id") BigDecimal id) {
		logger.info("============配件订单查询dlr下载===============");
		List<Map> dealerList = new ArrayList<Map>();
		Map map = TtPtOrderDetailMopDcsPO.findFirst("ORDER_ID = ?", id).toMap();
		dealerList.add(map);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("MOP配件导出",dealerList);
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件编号"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件名称"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NUM", "计划量"));
		
		excelService.generateExcel(excelData, exportColumnList, "MOP配件.xls", request, response);
	}
}
