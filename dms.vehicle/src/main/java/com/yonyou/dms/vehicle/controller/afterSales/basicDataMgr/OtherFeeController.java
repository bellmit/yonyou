package com.yonyou.dms.vehicle.controller.afterSales.basicDataMgr;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrOtherfeeDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrOtherfeePO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.OtherFeeService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔其他费用设定
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/otherFee")
public class OtherFeeController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	OtherFeeService   otherFeeService;
	
	/**
	 * 索赔其他费用设定查询
	 */
	@RequestMapping(value="/otherFeeSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto otherFeeSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============索赔其他费用设定查询=============");
        PageInfoDto pageInfoDto =otherFeeService.ClaimTypeQuery(queryParam);
        return pageInfoDto;  
    }
	/**
	 * 修改时信息回显
	 */
    @RequestMapping(value = "/otherFeeById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getOtherFeeById(@PathVariable(value = "id") Long id){
    	logger.info("=====索赔其他费用设定修改时信息回显=====");
    	TtWrOtherfeePO ptPo=otherFeeService.getOtherFeeById(id);
        return ptPo.toMap();
    }
    /**
     * 更新
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtWrOtherfeeDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrOtherfeeDTO dto,UriComponentsBuilder uriCB){
    	logger.info("=====索赔其他费用设定更新=====");
    	otherFeeService.edit(id, dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/otherFee/edit").buildAndExpand().toUriString());
        return new ResponseEntity<TtWrOtherfeeDTO>(headers, HttpStatus.CREATED);  	
    }
    /**
     * 新增
     */
    @RequestMapping(value ="/addLabour",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TtWrOtherfeeDTO> addLabourt(@RequestBody TtWrOtherfeeDTO ptdto,UriComponentsBuilder uriCB){
    	logger.info("===== 新增索赔其他费用设定=====");
    	otherFeeService.add(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
    }	
	/**
	 * 删除
	 */
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
    	logger.info("=====删除索赔其他费用设定=====");
    	otherFeeService.delete(id);
    }
	
	
}
