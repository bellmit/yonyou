package com.yonyou.dms.vehicle.controller.servicesTransacting;

import java.util.List;
import java.util.Map;

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
import com.yonyou.dms.vehicle.domains.DTO.servicesTransacting.ServicesTransactingDTO;
import com.yonyou.dms.vehicle.service.servicesTransacting.ServicesTransactingService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 服务项目办理Controller
 * @author Benzc
 * @date 2017年4月13日
 */
@Controller
@TxnConn
@RequestMapping("/ordermanage/servicesTransacting")
public class ServicesTransactingController extends BaseController {
	
	@Autowired
	private ServicesTransactingService servicesTransactingService;
	
	/**
	 * 服务项目办理首页分页查询
	 * @author Benzc
	 * @date 2017年4月13日
	 */
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryServicesTransacting(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = servicesTransactingService.QueryServicesTransacting(queryParam);
        return pageInfoDto;
    }
	
	/**
	 * 险种查询
	 * @author Benzc
	 * @date 2017年4月14日
	 */
	@RequestMapping(value ="/insurance",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryInsurance(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = servicesTransactingService.QueryInsurance(queryParam);
        return pageInfoDto;
    }
	
	/**
     * 查询险种下拉框
     * @author Benzc
     * @date 2017年4月14日             
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/selectInsurance", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qrySelectInsurance() {
        List<Map> list = servicesTransactingService.QuerySelectInsurance();
        return list;
    }
    
    /**
     * 查询保险公司下拉框
     * @author Benzc
     * @date 2017年4月14日             INSURATION
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/selectiInsuration", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> qrySelectInsuration() {
        List<Map> list = servicesTransactingService.QuerySelectInsuratione();
        return list;
    }
    
    /**
	 * 根据订单编号查询服务项目
	 * @author Benzc
	 * @date 2017年4月14日
	 */
	@RequestMapping(value = "/{id}/service" , method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryService(@PathVariable("id") String id) {
        PageInfoDto pageInfoDto = servicesTransactingService.QueryService(id);
        return pageInfoDto;
    }
	
	/**
	 * 根据订单编号查询保险办理结果
	 * @author Benzc
	 * @date 2017年4月14日
	 */
	@RequestMapping(value = "/{vin}/insurance" , method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryInsurance(@PathVariable("vin") String vin) {
        PageInfoDto pageInfoDto = servicesTransactingService.QueryInsurance(vin);
        return pageInfoDto;
    }
	
	/**
	 * 根据订单编号查询税费办理结果
	 * @author Benzc
	 * @date 2017年4月15日
	 */
	@RequestMapping(value = "/{vin}/tax" , method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryTax(@PathVariable("vin") String vin) {
        PageInfoDto pageInfoDto = servicesTransactingService.QueryTax(vin);
        return pageInfoDto;
    }
	
	/**
	 * 根据订单编号查询牌照办理结果
	 * @author Benzc
	 * @date 2017年4月15日
	 */
	@RequestMapping(value = "/{vin}/license" , method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLicense(@PathVariable("vin") String vin) {
        PageInfoDto pageInfoDto = servicesTransactingService.QueryLicense(vin);
        return pageInfoDto;
    }
	
	/**
	 * 根据订单编号查询贷款办理结果
	 * @author Benzc
	 * @date 2017年4月15日
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/{vin}/loan" , method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryLoan(@PathVariable("vin") String vin) {
        return servicesTransactingService.QueryLoan(vin);
    }
	
	/**
     * 服务项目办理执行保存
     * @author Benzc
     * @date 2017年4月15日
     */
    @RequestMapping(value = "/{soNo}/{vin}" , method = RequestMethod.POST)
    public ResponseEntity<ServicesTransactingDTO> addSalesOdditionalOrder(@PathVariable("soNo") String soNo,@PathVariable("vin") String vin,
    								@RequestBody  ServicesTransactingDTO servicesTransactingDTO,UriComponentsBuilder uriCB) {
    	System.err.println(soNo);
    	System.err.println(vin);
    	servicesTransactingService.addServiceTransacting(servicesTransactingDTO,soNo,vin);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/servicesTransacting").buildAndExpand().toUriString());  
        return new ResponseEntity<ServicesTransactingDTO>(servicesTransactingDTO,headers, HttpStatus.CREATED);  

    }

}
