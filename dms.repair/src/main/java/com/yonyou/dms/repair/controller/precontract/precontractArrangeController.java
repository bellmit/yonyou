package com.yonyou.dms.repair.controller.precontract;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.repair.service.Precontract.PrecontractArrangeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/precontract/chooseVin")
public class precontractArrangeController extends BaseController {
    
    @Autowired
    private  PrecontractArrangeService precontractArrangeService;
    /**
     * VIN选择
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryStockLog(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = precontractArrangeService.QueryChooseVin(queryParam);
        return pageInfoDto;
    }
    /**
     * 维修类型
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value = "/getBookingTypes/item" ,method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getBookingTypes(){
        return precontractArrangeService.getBookingTypes();
    }
    /**
     * 查询是否有预约单号
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value="/{vin}/{license}/item",method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> findById(@PathVariable String vin,@PathVariable String license, UriComponentsBuilder uriCB){
        String booking= precontractArrangeService.findBookingRecord(vin,license);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location",uriCB.path("/precontract/chooseVin").buildAndExpand().toUriString());
        return new ResponseEntity<String>(booking, headers, HttpStatus.CREATED);
    }
    
    /**
     * 查询预约单号
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value="/{vin}/{license}",method=RequestMethod.GET)
    @ResponseBody
    public PageInfoDto findBookingRecord(@PathVariable String vin,@PathVariable String license, UriComponentsBuilder uriCB){
        PageInfoDto pageInfoDto =precontractArrangeService.findBookingOrder(vin,license);
        return pageInfoDto;
    }
    
    
    /**
     * 查询推荐单位
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value="/selectOwner",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySelectOwner(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = precontractArrangeService.QuerySelectOwner(queryParam);
        return pageInfoDto;
    }
    
    
    
    /**
     * 查询推荐人
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value="/selectEmployee",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querySelectEmployee(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = precontractArrangeService.QueryselectEmployee(queryParam);
        return pageInfoDto;
    }
    
    
    @RequestMapping(value="/partStocks",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryPartInfos(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = precontractArrangeService.queryPartStocks(queryParam);
        return pageInfoDto;
    }
    /**
     * 查询预约情况
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value="/bookingLimit/getLimit",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLimit(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = precontractArrangeService.queryLimit(queryParam);
        return pageInfoDto;
    }
    /**
     * 查询预约情况
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value="/bookingLimit/getLimitorder",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryLimitorder(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = precontractArrangeService.queryLimitorder(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 查询预约项目
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value="/{bookingOrderNo}/bookingpart",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybookingpart(@PathVariable String bookingOrderNo, UriComponentsBuilder uriCB) {
        PageInfoDto pageInfoDto = precontractArrangeService.querybookingpart(bookingOrderNo);
        return pageInfoDto;
    }
    
    /**
     * 查询预约配件
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value="/{bookingOrderNo}/bookingpartitem",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybookingpartitem(@PathVariable String bookingOrderNo, UriComponentsBuilder uriCB) {
        PageInfoDto pageInfoDto = precontractArrangeService.querybookingpartitem(bookingOrderNo);
        return pageInfoDto;
    }
    
    /**
     * 查询预约附件项目
     * @author Zhl
     * @date 2017年4月06日
     */
    @RequestMapping(value="/{bookingOrderNo}/bookingitem",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybookingitem(@PathVariable String bookingOrderNo, UriComponentsBuilder uriCB) {
        PageInfoDto pageInfoDto = precontractArrangeService.querybookingitem(bookingOrderNo);
        return pageInfoDto;
    }
}
