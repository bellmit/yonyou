package com.yonyou.dms.vehicle.controller.stockManage;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.stockManage.SalesPricePromotionService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 促销价格维护Controller
 * @author Zhl
 * @date 2017年3月06日
 */

@Controller
@TxnConn
@RequestMapping("/stockManage/salesPricePromotion")
public class SalesPricePromotionController extends BaseController{
	
	 @Autowired
	 private SalesPricePromotionService salesPricePromotionService;

		/**
	     * 促销价格查询
		 * @author Zhl
		 * @date 2017年3月06日
	     */
	    @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryStockLog(@RequestParam Map<String, String> queryParam) {
	        PageInfoDto pageInfoDto = salesPricePromotionService.QuerySalesPrice(queryParam);
	        return pageInfoDto;
	    }
	    
	    /**
		 * 批量修改促销价格
		 * @param map
		 * @param uriCB
		 * @return
		 */ 
	    @RequestMapping(value = "/editsalesPrice", method = RequestMethod.PUT)
	    public ResponseEntity<Map<String, String>> editsalesPrice(@RequestBody Map<String, String> map,UriComponentsBuilder uriCB) {
					salesPricePromotionService.editsalesPrice(map);	
					MultiValueMap<String, String> headers = new HttpHeaders();
					headers.set("Location", uriCB.path("/stockManage/salesPricePromotion").buildAndExpand().toUriString());
					return new ResponseEntity<Map<String, String>>(headers, HttpStatus.CREATED);

		 }
	    
	    
}
