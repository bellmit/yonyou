package com.yonyou.dms.vehicle.controller.afterSales.custcomVehicle;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TtVsSalesReportDTO;
import com.yonyou.dms.vehicle.service.afterSales.custcomVehicle.VehicleInvoiceDateManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车辆开票日期维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/vehicleInvoiceDateManage")
public class VehicleInvoiceDateManageController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	VehicleInvoiceDateManageService   vehicleInvoiceDateManageService;
	/**
	 * 车辆开票日期维护查询
	 */
	@RequestMapping(value="/InvoiceDateManageSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybasicCode(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============车辆开票日期维护查询=============");
        PageInfoDto pageInfoDto =vehicleInvoiceDateManageService.VehicleInvoiceDateQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 车辆开票日期维护下载
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============车辆开票日期维护下载===============");
		vehicleInvoiceDateManageService.download(queryParam, response, request);
	}
	/**
	 * 车辆开票日期修改信息回显
	 */
	  @RequestMapping(value = "/getVehicleInvoiceDate/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> VehicleInvoiceDateById(@PathVariable(value = "id") Long id){
	    	logger.info("=====车辆开票日期修改信息回显=====");
	    	Map m=vehicleInvoiceDateManageService.getShuju(id);
	        return m;
	    }
	  /**
	   * 通过id进行修改车辆开票日期
	   */
	  @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
	  @ResponseBody
	  public ResponseEntity<TtVsSalesReportDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtVsSalesReportDTO dto,UriComponentsBuilder uriCB){
	  	logger.info("===== 通过id进行修改车辆开票日期=====");
	  	vehicleInvoiceDateManageService.edit(id,dto);
	  	MultiValueMap<String, String> headers = new HttpHeaders();
	      headers.set("Location", uriCB.path("/vehicleInvoiceDateManage/edit").buildAndExpand().toUriString());
	      return new ResponseEntity<TtVsSalesReportDTO>(headers, HttpStatus.CREATED);  	
	  }
	
	
	
}
