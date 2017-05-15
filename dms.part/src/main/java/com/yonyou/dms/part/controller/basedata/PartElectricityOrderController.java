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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDcsPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.part.service.basedata.PartElectricityOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 电商订单确认
 * @author ZhaoZ
 *@date 2017年3月22日
 */
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/partElectricityOrder")
public class PartElectricityOrderController extends BaseController{
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
    @Autowired
	private PartElectricityOrderService partElectricityService;
    
    @Autowired
   	private ExcelGenerator excelService;
    /**
  	 * 订单查询
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/queryECOrder",method = RequestMethod.GET)
  	@ResponseBody
  	public PageInfoDto queryECOrder(@RequestParam Map<String, String> queryParams) {
  		 logger.info("=====订单查询=====");
  		
  		 return partElectricityService.queryECOrderInfo(queryParams);
  		
  	}
  	
	
	/**
	 * 导出
	 */
	@RequestMapping(value="/downloadInfo",method = RequestMethod.GET)
	@ResponseBody
	public void downloadTemple(HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParams) {
		logger.info("============导出===============");
		List<Map> dealerList = partElectricityService.queryDownLoad(queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("电商订单确认查询导出",dealerList);
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("SAP_ORDER_NO", "SAP订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_NO", "订单号"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", " 订单类型"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("REPORT_DATE", "提报时间"));
		exportColumnList.add(new ExcelExportColumn("IS_DIRECT", "是否车主直销"));
		exportColumnList.add(new ExcelExportColumn("DEAL_ORDER_AFFIRM_DATE", "确认时间"));
		exportColumnList.add(new ExcelExportColumn("EC_ORDER_NO", "电商订单号"));
		exportColumnList.add(new ExcelExportColumn("IS_AFFIRM", "确认状态",ExcelDataType.Oem_Dict));
		excelService.generateExcel(excelData, exportColumnList, "电商订单确认.xls", request, response);
	}
	
	
	 /**
	  * 配件订单回显信息
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/orderCheckInit/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> orderCheckInit(@PathVariable(value = "id") BigDecimal id) {
		logger.info("=====配件订单回显信息=====");
		Map<String, Object> mapA = partElectricityService.findDealerInfoByOrderId(id);
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
	 * 配件信息
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/queryPartDetail/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartDetail(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====配件信息=====");
		
		 return partElectricityService.queryPartInfo(id);
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
		
		 return partElectricityService.checkHidtoryInfo(id);
		 
	}
	
	/**
	 * 处理历史
	 * @param queryParams
	 * @throws Exception
	 */
	@RequestMapping(value="/ttPtEcOrderHistory/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto ttPtEcOrderHistory(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("=====处理历史=====");
		TtPtOrderDcsPO po = TtPtOrderDcsPO.findFirst("ORDER_ID = ?", id);
		String sql = "SELECT * FROM TT_PT_EC_ORDER_HISTORY_dcs TA WHERE TA.EC_ORDER_NO = "+po.getString("EC_ORDER_NO")+"";
		return OemDAOUtil.pageQuery(sql, null);
	}
	
	/**
	 * 确认
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/confirmOrder/{id}/{no}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> confirmOrder(@PathVariable(value = "id") BigDecimal id,@PathVariable(value = "id") String no,UriComponentsBuilder uriCB){
		logger.info("==================确认================");
		partElectricityService.confirmOrder(id);
		partElectricityService.insertPtEcOrderHistory(no, "电商订单确认", "");
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/confirmOrder").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
}
