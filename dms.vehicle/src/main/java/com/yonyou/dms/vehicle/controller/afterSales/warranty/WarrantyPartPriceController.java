package com.yonyou.dms.vehicle.controller.afterSales.warranty;

import java.math.BigDecimal;
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
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrPartPriceDTO;
import com.yonyou.dms.vehicle.service.afterSales.warranty.WarrantyPartPriceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 零部件保修价维护
 * @author zhanghongyi 
 *
 */
@Controller
@TxnConn
@RequestMapping("/warrantyPartPrice")
public class WarrantyPartPriceController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	WarrantyPartPriceService  warrantyPartPriceService;
	
    /**
     * 查询
     */
    @RequestMapping(value="/query",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto query(@RequestParam Map<String, String> queryParam) {
    	logger.info("----------------------零部件保修价维护-----------------------");
    	PageInfoDto pageInfoDto = warrantyPartPriceService.query(queryParam);   	
        return pageInfoDto;               
    }
    
	/**
	 * 新增
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<TtWrPartPriceDTO> add(@RequestBody TtWrPartPriceDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------零部件保修价新增-----------------------");
		Long id = warrantyPartPriceService.add(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/DealerLevelMain/add").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(dto, headers, HttpStatus.CREATED);
	}
	
 	/**
	 * 修改初始化
	 */
	@RequestMapping(value="/queryWarrantyPartPrice/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryWarrantyPartPrice(@PathVariable(value = "id") BigDecimal id) {
		 logger.info("----------------------零部件保修价修改-----------------------");
		 StringBuilder sql = new StringBuilder();
		 sql.append("select TWPP.ID,TWPP.MODE_CODE,TWPP.RATE,TVMG.GROUP_NAME,TWPP.MVS,TWPP.STATUS \n");
		 sql.append("from TT_WR_PART_PRICE_DCS TWPP left OUTER JOIN tm_vhcl_material_group TVMG \n");
		 sql.append("on TWPP.MODE_CODE=TVMG.GROUP_CODE \n");
		 sql.append("where TWPP.ID = "+id+" \n");
		 Map<String, Object> mapA = OemDAOUtil.findFirst(sql.toString(), null);
		 return mapA;
	}
	
	/**
	 * 修改保存
	 */
	@RequestMapping(value = "/update", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtWrPartPriceDTO> update(@RequestBody TtWrPartPriceDTO dto, UriComponentsBuilder uriCB) {
		logger.info("----------------------零部件保修价修改保存-----------------------");
		warrantyPartPriceService.update(dto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
	    headers.set("Location", uriCB.path("/update").buildAndExpand().toUriString());
	    return new ResponseEntity<>(headers, HttpStatus.CREATED);
	}
	
	/**
	* 获取MVS
	*/
    @RequestMapping(value="/getMVS/{seriesCode}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getMVS(@RequestParam Map<String, String> queryParams,@PathVariable("seriesCode") String seriesCode){
    	logger.info("==========获取MVS==========");
    	logger.info("==========seriesCode=========="+seriesCode);
    	List<Map> mapping = warrantyPartPriceService.getMVS(queryParams,seriesCode);
        return mapping;
    }
    
	/**
	 * 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/download", method = RequestMethod.GET)
	public void download(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============零部件保修价下载  ===============");
    	warrantyPartPriceService.download(queryParam, request, response);
	}
}
