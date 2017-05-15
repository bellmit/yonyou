package com.yonyou.dms.vehicle.controller.afterSales.warranty;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

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

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmVhclMaterialGroupDTO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.MvsFamilyMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * MVS 家族维护
 * @author zhiahongmiao 
 *
 */
@Controller
@TxnConn
@RequestMapping("/MvsFamilyMaintain")
public class MvsFamilyMaintainController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	MvsFamilyMaintainService  mvsFamilyMaintainService;
	/**
	* 相关菲亚特车系
	*/
    @RequestMapping(value="/GetMVCCheXi",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> GetAllCheXing(@RequestParam Map<String, String> queryParams){
    	logger.info("-----------------------相关菲亚特车系-----------------------");
    	List<Map> tenantMapping = mvsFamilyMaintainService.GetMVCCheXi(queryParams);
        return tenantMapping;
    }
    /**
     * 关联车型
     */
    @RequestMapping(value="/{seriesId}/getGroupCodes",method = RequestMethod.GET)
  	@ResponseBody
  	public List<Map> getGroupCodes(@PathVariable(value = "seriesId") Long seriesId) {
  		 logger.info("-----------------------关联车型-----------------------");
  		 return mvsFamilyMaintainService.getGroupCodes(seriesId);
  	}
    /**
     * 查询
     */
    @RequestMapping(value="/Query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto MVSFamilyMaintainQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("-----------------------MVS 家族维护查询-----------------------");
    	PageInfoDto pageInfoDto = mvsFamilyMaintainService.MVSFamilyMaintainQuery(queryParam);   	
        return pageInfoDto;               
    }
    /**
	 *下载
	 */
	@RequestMapping(value = "/Download", method = RequestMethod.GET)
    @ResponseBody
    public void returnToFactoryVehicleSeraceDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("-----------------------MVS 家族维护下载-----------------------");
    	mvsFamilyMaintainService.MVSFamilyMaintainDownload(queryParam, request, response);
	}
	/**
	 * 新增(查询出相应数据进行修改)
	 */
	 @RequestMapping(value = "/MvsFamilyMaintainUpByGroup",method = RequestMethod.POST)
	    public ResponseEntity<TmVhclMaterialGroupDTO> MVSAdd(@RequestBody @Valid TmVhclMaterialGroupDTO tvmgDto, UriComponentsBuilder uriCB) {
	    	logger.info("-----------------------新增MVS 家族维护（查询出相应数据进行修改）-------------------------------");
	    	Long id = mvsFamilyMaintainService.MVSAdd(tvmgDto);
	        MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/MvsFamilyMaintain/MvsFamilyMaintainUpByGroup").buildAndExpand(id).toUriString());
	        return new ResponseEntity<TmVhclMaterialGroupDTO>(tvmgDto, headers, HttpStatus.CREATED);

	    }
 	/**
     * 回显信息
     */
	@RequestMapping(value="/updateQueryInit/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> findMVS(@PathVariable(value = "id") Long id) {
		logger.info("=====修改 MVS 家族维护回显=====");
		TmVhclMaterialGroupPO tvmg = mvsFamilyMaintainService.findMVS(id);
		return tvmg.toMap();	
	}
	/**
	 * 修改
	 */
	@RequestMapping(value = "/MvsFamilyMaintainUp",method = RequestMethod.POST)
    public ResponseEntity<TmVhclMaterialGroupDTO> MVSUpdate(@RequestBody @Valid TmVhclMaterialGroupDTO tvmgDto, UriComponentsBuilder uriCB) {
    	logger.info("-----------------------修改MVS 家族维护-------------------------------");
    	Long id = mvsFamilyMaintainService.MVSUpdate(tvmgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/MvsFamilyMaintain/MvsFamilyMaintainUp").buildAndExpand(id).toUriString());
        return new ResponseEntity<TmVhclMaterialGroupDTO>(tvmgDto, headers, HttpStatus.CREATED);

    }
}
