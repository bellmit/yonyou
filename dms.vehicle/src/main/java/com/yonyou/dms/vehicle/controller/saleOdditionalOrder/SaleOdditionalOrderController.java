package com.yonyou.dms.vehicle.controller.saleOdditionalOrder;

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
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.salesOdditionalOrder.SalesOdditionalOrderDTO;
import com.yonyou.dms.vehicle.service.saleOdditionalOrder.SaleOdditionalOrderService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 服务订单Controller
 * @author Benzc
 * @date 2017年3月8日
 */
@Controller
@TxnConn
@RequestMapping("/ordermanage/salesOdditionalOrder")
@SuppressWarnings("rawtypes")
public class SaleOdditionalOrderController extends BaseController{
	
	@Autowired
	private SaleOdditionalOrderService saleOdditionalOrderService;
	
	@Autowired
    private CommonNoService  commonNoService;
	
	/**
	 * 服务订单首页分页查询
	 * @author Benzc
	 * @date 2017年3月8日
	 */
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryStockLog(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = saleOdditionalOrderService.QuerySaleOdditionalOrder(queryParam);
        return pageInfoDto;
    }
	
	/**
	 * 根据客户编号查询客户信息
	 * @author Benzc
	 * @date 2017年3月17日
	 */
	@RequestMapping(value="/customer",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCustomer(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = saleOdditionalOrderService.QueryCustomer(queryParam);
        return pageInfoDto;
    }
	
	/**
	 * 根据销售单号查询订单信息
	 * @author Benzc
	 * @date 2017年3月17日
	 */
	@RequestMapping(value="/selectSalesOrder",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySalesOrder(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = saleOdditionalOrderService.QuerySalesOrder(queryParam);
        return pageInfoDto;
    }
	
	/**
     * 服务订单新增
     * @author Benzc
     * @date 2017年3月22日
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SalesOdditionalOrderDTO> addSalesOdditionalOrder(@RequestBody  SalesOdditionalOrderDTO salesOrderDTO,UriComponentsBuilder uriCB) {
    	String soNo = "";
        if(!StringUtils.isNullOrEmpty(salesOrderDTO.getSoNo())){
            soNo=salesOrderDTO.getSoNo();
            System.err.println(soNo);
        }else{
            soNo =commonNoService.getSystemOrderNo(CommonConstants.SAL_ZZXSDH);
            System.err.println(soNo);
        }
        saleOdditionalOrderService.addSalesOdditionalOrder(salesOrderDTO,soNo);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/ordermanage/salesOdditionalOrder").buildAndExpand().toUriString());  
        return new ResponseEntity<SalesOdditionalOrderDTO>(salesOrderDTO,headers, HttpStatus.CREATED);  

    }
    
    /**
     * 服务订单修改
     * @author Benzc
     * @date 2017年4月6日
     */

    @RequestMapping(value = "/{soNo}", method = RequestMethod.PUT)
    public ResponseEntity<SalesOdditionalOrderDTO> modifyOdditionalOrderInfo(@PathVariable("soNo") String soNo,
                                                                      @RequestBody  SalesOdditionalOrderDTO salesOrderDTO,
                                                                      UriComponentsBuilder uriCB) {
    	saleOdditionalOrderService.modifyOdditionalOrderInfo(soNo, salesOrderDTO);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/ordermanage/salesOdditionalOrder/{soNo}").buildAndExpand(soNo).toUriString());
        return new ResponseEntity<SalesOdditionalOrderDTO>(headers, HttpStatus.CREATED);
    }
	
	/**
	 * 工时单价下拉框
	 * @author Benzc
	 * @date 2017年3月22日
	 */
	@RequestMapping(value = "/hourPrice/dicts",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> priceSelect(@RequestParam Map<String,String> queryParam) {
        List<Map> pricelist = saleOdditionalOrderService.selectPrice(queryParam);
        return pricelist;
    }
	
	/**
     * 服务项目编辑页面查询
     * @author Benzc
     * @date 2017年3月23日
     */
	@RequestMapping(value = "/{id}/serviceProject", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryIntent(@PathVariable("id") String id) {
        List<Map> service = saleOdditionalOrderService.queryServiceProject(id);
        return service;
    }
	
	/**
	 * 装潢项目分页查询
	 * @author Benzc
	 * @date 2017年3月28日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/decrodateProject", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDecrodateProject(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = saleOdditionalOrderService.queryDecrodateProject(queryParam);
        return pageInfoDto;
    }
	
	/**
     * 根据ID查询服务订单信息
     * @author Benzc
     * @date 2017年3月29日
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> querySalesOdditionalOrderInfoByid(@PathVariable(value = "id") String id) {
    	Map<String,Object> salesPo = saleOdditionalOrderService.querySalesOdditionalOrderInfoByid(id);
        return salesPo;
    }
    
    /**
     * 装潢项目信息查询
     * @author Benzc
     * @date 2017年3月29日
     * @param queryParam
     * @return
     */
	@RequestMapping(value = "/{id}/Upholster", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryUpholster(@PathVariable("id") String id) {
        List<Map> intent = saleOdditionalOrderService.queryUpholster(id);
        return intent;
    }
	
	/**
     * 精品材料信息查询
     * @author Benzc
     * @date 2017年4月6日
     * @param queryParam
     * @return
     */
	@RequestMapping(value = "/{id}/Part", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryPart(@PathVariable("id") String id) {
        List<Map> intent = saleOdditionalOrderService.queryPart(id);
        return intent;
    }
	
	/**
     * 服务项目信息查询
     * @author Benzc
     * @date 2017年3月29日
     * @param queryParam
     * @return
     */
	@RequestMapping(value = "/{id}/Service", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryService(@PathVariable("id") String id) {
        List<Map> intent = saleOdditionalOrderService.queryService(id);
        return intent;
    }
	
	/**
     * 查询优惠模式信息
     * @author Benzc
     * @date 2017年3月29日
     */
	@RequestMapping(value = "/discount",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> storeSelect(@RequestParam Map<String,String> queryParam){
    	List<Map> storelist = saleOdditionalOrderService.discountSelect(queryParam);
    	return storelist;
    }
	
	/**
	 * 精品材料分页查询
	 * @author Benzc
	 * @date 2017年3月30日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/part", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPart(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = saleOdditionalOrderService.queryPart(queryParam);
        return pageInfoDto;
    }
	
	/**
     * 装潢项目信息查询
     * @author Benzc
     * @date 2017年4月7日
     */
	@RequestMapping(value = "/salesOddPart", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySalesOrderPart(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = saleOdditionalOrderService.querySalesOrderPart(queryParam);
        return pageInfoDto;
    }

	/**
     * 装潢项目信息查询
     * @author Benzc
     * @date 2017年4月7日
     */
	@RequestMapping(value = "/{id}/partDetail", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> querySalesPart(@PathVariable("id") String id) {
        List<Map> intent = saleOdditionalOrderService.querySalesPart(id);
        return intent;
    }
	
	/**
     * 订单车辆信息查询
     * @author Benzc
     * @date 2017年4月12日
     */
	@RequestMapping(value = "/{id}/querySalesOddBySoNo", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> querySalesOddBySoNo(@PathVariable("id") String id) {
		System.err.println(id);
        List<Map> intent = saleOdditionalOrderService.querySalesOddBySoNo(id);
        return intent;
    }

}
