package com.yonyou.dms.vehicle.controller.afterSales.custcomVehicle;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.custcomVehicle.TtVsCustomerDTO;
import com.yonyou.dms.vehicle.service.afterSales.custcomVehicle.CustcomVehicleManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 客户资料维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/custcomVehicleManage")
public class CustcomVehicleManageController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	CustcomVehicleManageService   custcomVehicleManageService;
	/**
	 * 客户资料查询
	 */
	@RequestMapping(value="/custcomVehicleSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybasicCode(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============客户资料查询=============");
        PageInfoDto pageInfoDto =custcomVehicleManageService.CustcomVehicleQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 客户资料下载
	 */
	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
		logger.info("============客户资料下载===============");
		custcomVehicleManageService.download(queryParam, response, request);
	}
	/**
	 * 修改信息回显
	 */
    @RequestMapping(value = "/getCustcomVehicle/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getBasicParamsById(@PathVariable(value = "id") Long id){
    	logger.info("=====代码维护信息回显=====");
    	Map m=custcomVehicleManageService.getShuju(id);
        return m;
    }
    /**
     * 查询所在行业类别
     */
  @RequestMapping(value = "/hangye", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> HangyeQueryBig(){
    	logger.info("=====查询所在行业类别big=====");
    	List<Map> hanglist = custcomVehicleManageService.searchIndustryBig();
    	return hanglist;
    }
  @RequestMapping(value = "/{parentCode}/hangye", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> HangyeQuery2(@PathVariable String parentCode,@RequestParam Map<String, String> queryParams){
    	logger.info("=====查询所在行业类别smal=====");
    	List<Map> hanglist = custcomVehicleManageService.searchIndustry(parentCode);
    	return hanglist;
    }
  /**
   * 查询所有省份
   */
  @RequestMapping(value = "/province", method = RequestMethod.GET)
  @ResponseBody
  public List<Map> ProvinceQuery(){
  	logger.info("===== 查询所有省份=====");
  	List<Map> hanglist = custcomVehicleManageService.queryProvince();
  	return hanglist;
  }

  /**
   * 根据省份查市
   */
  @RequestMapping(value = "/{parentId}/city", method = RequestMethod.GET)
  @ResponseBody
  public List<Map> CityQuery(@PathVariable Long parentId,@RequestParam Map<String, String> queryParams){
  	logger.info("===== 根据省份查市=====");
  	List<Map> hanglist = custcomVehicleManageService.queryCity(parentId);
  	return hanglist;
  }
  /**
   * 根据市查县
   */
  @RequestMapping(value = "/{parentId}/town", method = RequestMethod.GET)
  @ResponseBody
  public List<Map> TownQuery(@PathVariable Long parentId,@RequestParam Map<String, String> queryParams){
  	logger.info("=====根据市查县=====");
  	List<Map> hanglist = custcomVehicleManageService.queryTowm(parentId);
  	return hanglist;
  }
  /**
   * 通过id进行修改客户资料
   */
  @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
  @ResponseBody
  public ResponseEntity<TtVsCustomerDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtVsCustomerDTO dto,UriComponentsBuilder uriCB){
  	logger.info("===== 通过id进行修改客户资料=====");
  	custcomVehicleManageService.edit(id,dto);
  	MultiValueMap<String, String> headers = new HttpHeaders();
      headers.set("Location", uriCB.path("/custcomVehicleManage/edit").buildAndExpand().toUriString());
      return new ResponseEntity<TtVsCustomerDTO>(headers, HttpStatus.CREATED);  	
  }
  
}
