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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.part.domains.DTO.basedata.PartDeliverDTO;
import com.yonyou.dms.part.service.basedata.PartDeliverManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 交货单查询
 * @author ZhaoZ
 *@date 2017年3月22日
 */
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/partDeliverManage")
public class PartDeliverManageController extends BaseController{
	// 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(PartInfoController.class);
    @Autowired
	private PartDeliverManageService partDeliverService;
    @Autowired
  	private ExcelGenerator excelService;
    /**
  	 * 直发交货单查询
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/checkOrderInfo",method = RequestMethod.GET)
  	@ResponseBody
  	public PageInfoDto checkOrderInfo(@RequestParam Map<String, String> queryParams) {
  		 logger.info("=====直发交货单查询=====");
  		
  		 return partDeliverService.checkOrderPartInfo(queryParams);
  		
  	}
  	
  	 /**
	  * 直发交货单修改回显信息
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/deliverOrderUpdateInit/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> deliverOrderUpdateInit(@PathVariable(value = "id") BigDecimal id) {
		logger.info("=====直发交货单修改回显信息=====");
		Map<String, Object> mapA = partDeliverService.findDeliverInfoByDeliverId(id);
		return mapA;
	}
	
	 /**
	  * 接货清单信息查询
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/findAcceptInfoByDeliverId/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAcceptInfoByDeliverId(@PathVariable(value = "id") BigDecimal id) {
		logger.info("=====直发交货单修改回显信息=====");
		return  partDeliverService.findAcceptInfoByDeliverId(id);
		
	}
	
	 /**
	  * 接货清单信息查询dlr
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/findDelivertInfoByDeliverId/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findDelivertInfoByDeliverId(@PathVariable(value = "id") BigDecimal id) {
		logger.info("=====直发交货单修改回显信息=====");
		return  partDeliverService.findDelivertInfoByDeliverId(id);
		
	}
	/**
	 * 保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/saveDeliveryOrder/{id}", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<PartDeliverDTO> saveDeliveryOrder(@RequestBody PartDeliverDTO dto,UriComponentsBuilder uriCB,@PathVariable(value = "id") BigDecimal id){
		logger.info("==================保存================");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		partDeliverService.saveDeliveryOrder(dto,id,loginInfo);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/saveDeliveryOrder").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
	 /**
	  * 交货单明细查询
	  * @param queryParams
	  * @throws Exception
	  */
	@RequestMapping(value="/queryOrderInfo",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryOrderInfo(@RequestParam Map<String, String> queryParams) {
		logger.info("=====交货单明细查询=====");
		return  partDeliverService.queryOrderInfo(queryParams);
		
	}
	/**
  	 * 货运单管查詢
  	 * @param queryParams
  	 * @throws Exception
  	 */
  	@RequestMapping(value="/queryDeliverInitInfo",method = RequestMethod.GET)
  	@ResponseBody
  	public PageInfoDto queryDeliverInitInfo(@RequestParam Map<String, String> queryParams) {
  		 logger.info("=====货运单管查詢=====");
  		
  		 return partDeliverService.queryDeliverInit(queryParams);
  		
  	}
  	
  	/**
	 * 信息导出
	 */
	@RequestMapping(value="/downloadDeliver/{deliverId}/{code}",method = RequestMethod.GET)
	@ResponseBody
	public void downloadDeliver(HttpServletRequest request,HttpServletResponse response,
			@RequestParam Map<String, String> queryParams,@PathVariable(value = "deliverId") String deliverId,
			@PathVariable(value = "code") String code) {
		logger.info("============信息导出===============");
		List<Map> dealerList = partDeliverService.exeDeliverInfo(deliverId,code,queryParams);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("货运单导出",dealerList);
		if("2".equals(code)){
			exportColumnList.add(new ExcelExportColumn("LINE_NO", "行政项目"));
			exportColumnList.add(new ExcelExportColumn("PART_CODE", "配件编码"));
			exportColumnList.add(new ExcelExportColumn("PART_NAME", "配件编码名称"));
			exportColumnList.add(new ExcelExportColumn("PLAN_NUM", "计划量"));
			exportColumnList.add(new ExcelExportColumn("NET_PRICE", "净价"));
			exportColumnList.add(new ExcelExportColumn("DELIVER_AMOUNT", "运单总额"));
			exportColumnList.add(new ExcelExportColumn("DISCOUNT", "折扣金额"));
		 }
		if("1".equals(code)){
			exportColumnList.add(new ExcelExportColumn("DELIVER_NO", "交货单号"));
			exportColumnList.add(new ExcelExportColumn("DMS_ORDER_NO", "DMS订单编码"));
			exportColumnList.add(new ExcelExportColumn("SAP_ORDER_NO", "SAP订单编号"));
			exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型",ExcelDataType.Oem_Dict));
			exportColumnList.add(new ExcelExportColumn("DELIVER_DATE", "交货单创建日期"));
			exportColumnList.add(new ExcelExportColumn("TRANS_NO", "运单号"));
			exportColumnList.add(new ExcelExportColumn("ARRIVED_DATE", "预计到店日期"));
			exportColumnList.add(new ExcelExportColumn("DELIVER_STATUS", "状态",ExcelDataType.Oem_Dict));
		 }
		
		
		excelService.generateExcel(excelData, exportColumnList, "货运单.xls", request, response);
	}
	
	/**
	 * 收货确认   接口
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/verifyDeliver", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<?> verifyDeliver(UriComponentsBuilder uriCB){
		logger.info("==================收货确认================");
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/verifyDeliver").buildAndExpand().toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
	
	}
	
}
