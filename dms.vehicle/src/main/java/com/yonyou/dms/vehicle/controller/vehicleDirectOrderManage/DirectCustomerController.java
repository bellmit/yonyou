package com.yonyou.dms.vehicle.controller.vehicleDirectOrderManage;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.controller.vehicleAllot.DealerAllotApplyController;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TtBigCustomerDirectDTO;
import com.yonyou.dms.vehicle.domains.PO.retailReportQuery.TtBigCustomerDirectPO;
import com.yonyou.dms.vehicle.service.vehicleDirectOrderManage.DirectCustomerService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 直销客户管理
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/directCustomer")
public class DirectCustomerController {
	@Autowired
	DirectCustomerService  customerService;
	private static final Logger logger = LoggerFactory.getLogger(DealerAllotApplyController.class);
   /**
   * 直销客户查询
    */
	   @RequestMapping(value="/directCustomerSearch",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto directCustomerSer(@RequestParam Map<String, String> queryParam) {
	    	logger.info("============== 直销客户查询=============");
	        PageInfoDto pageInfoDto =customerService.directCustomerQuery(queryParam);
	        return pageInfoDto;  
	    }
	   /**
	    * 下载
	    */
		@RequestMapping(value = "/download", method = RequestMethod.GET)
		public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
				HttpServletResponse response) {
			logger.info("============资源备注下载下载===============");
			customerService.download(queryParam, response, request);
		}
		/**
		 * 查询所有有效银行
		 */
		@RequestMapping(value="/brankSearch",method = RequestMethod.GET)
		@ResponseBody
		public List<Map> brankSelect() {
			logger.info("==============查询所有有效银行=============");
			List<Map> banklist = customerService.queryBank();
			return banklist;
		}
		/**
		 * 新增直销客户信息
		 */
	    @RequestMapping(value = "/addDirectCustomer",method = RequestMethod.POST)
	    public ResponseEntity<TtBigCustomerDirectDTO> addDirectCustomer(@RequestBody TtBigCustomerDirectDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("==============新增直销客户信息=============");
	    	Long id =customerService.addDirectCustomer(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/directCustomer/addDirectCustomer").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }
	    /**
	     * 通过id删除直销客户信息
	     */
	    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteDirectCustomer(@PathVariable("id") Long id, UriComponentsBuilder uriCB, TtBigCustomerDirectDTO ptdto) {
	    	logger.info("=====通过id删除直销客户信息=====");
	    	customerService.deleteDirectCustomerById(id, ptdto);
	    }
	    /**
	     * 通过id进行回显信息
	     */
	    
	    @RequestMapping(value = "/getDirectCustomer/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> getDirectCustomerById(@PathVariable(value = "id") Long id){
	    	logger.info("=====通过id进行回显信息=====");
	    	TtBigCustomerDirectPO ptPo=customerService.getDirectCustomerById(id);
	        return ptPo.toMap();
	    }
		
}