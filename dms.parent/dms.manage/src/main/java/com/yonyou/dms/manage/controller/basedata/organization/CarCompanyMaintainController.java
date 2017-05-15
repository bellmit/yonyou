package com.yonyou.dms.manage.controller.basedata.organization;

import java.util.Map;

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
import com.yonyou.dms.manage.domains.DTO.basedata.organization.CarCompanyDTO;
import com.yonyou.dms.manage.service.basedata.organization.CarCompanyMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 车厂公司维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/organization/carCompanyMaintain")
public class CarCompanyMaintainController {
	
	@Autowired
	private CarCompanyMaintainService carService;
	
	private static final Logger logger = LoggerFactory.getLogger(CarCompanyMaintainController.class);
	
	/**
	 * 首页初始化
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String, String> param){
		logger.info("==================车厂公司维护查询================");
		PageInfoDto dto = carService.searchCompanyInfo(param);
		return dto;		
	}
	
	/**
	 * 新增数据保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addCarCompany", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<CarCompanyDTO> addCarCompany(@RequestBody CarCompanyDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================车厂公司维护新增================");
		carService.addCarCompany(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addCarCompany").buildAndExpand().toUriString());
        return new ResponseEntity<CarCompanyDTO>(headers, HttpStatus.CREATED);  	
	}
	
	/**
	 * 修改页面数据回显
	 * @param companyId
	 * @return
	 */
	@RequestMapping(value = "/{companyId}", method = RequestMethod.GET)
	@ResponseBody
	public CarCompanyDTO editCarCompanyInit(@PathVariable Long companyId){
		logger.info("==================车厂公司维护修改数据回显================");
		CarCompanyDTO dto = carService.editCarCompanyInit(companyId);
		return dto;
		
	}
	
	/**
	 * 修改数据保存
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editCarCompany", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<CarCompanyDTO> editCarCompany(@RequestBody CarCompanyDTO dto,UriComponentsBuilder uriCB){
		logger.info("==================车厂公司维护修改================");
		carService.editCarCompany(dto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/addCarCompany").buildAndExpand().toUriString());
        return new ResponseEntity<CarCompanyDTO>(headers, HttpStatus.CREATED);
	}
	

}
