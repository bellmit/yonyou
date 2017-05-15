package com.yonyou.dms.vehicle.controller.stockLog;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VsInspectionMarPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.service.stockLog.StockLogService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 库存日志Controller
 * @author Benzc
 * @date 2017年2月13日
 */
@Controller
@TxnConn
@RequestMapping("/vehicleStock/StockLog")
@SuppressWarnings("rawtypes")
public class StockLogController extends BaseController{
	
	 @Autowired
	 private StockLogService stockLogService;
	
	/**
     * 库存日志查询
     * @author Benzc
     * @date 2017年2月3日
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryStockLog(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = stockLogService.QueryStockLog(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 根据ID 获取车辆库存的信息
     * 
     * @author Benzc
     * @date 2017年2月14日
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getStockLogById(@PathVariable(value = "id") String id) {
    	VsStockLogPO stocklog = stockLogService.getStockLogById(id);
        return stocklog.toMap();
    }
    
    /**
     * 根据车辆VIN和日期  查询车辆信息
     * @author Benzc
     * @date 2017年2月14日
     */
    @RequestMapping(value = "/{id}/vehicle", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryUserAddressPage(@RequestParam Map<String, String> queryParam,@PathVariable("id") String id) {
    	System.err.println(id);
        PageInfoDto vehicleList = stockLogService.QueryStockLogVehicle(queryParam,id);
        return vehicleList;
    }
    
    /**
     * 根据车辆VIN 查询明细
     * @author Benzc
     * @date 2017年2月15日
     */
    @RequestMapping(value = "/{id}/detail", method = RequestMethod.GET)
    @ResponseBody
    public Map queryVehicleByVin(@RequestParam Map<String, String> queryParam,@PathVariable(value = "id") String id) {
        List<Map> result = stockLogService.queryVehicleByVin(queryParam, id);
        return result.get(0);
    }
    
    /**
     * 库存日志查询
     * @author Benzc
     * @date 2017年2月15日
     */
    @RequestMapping(value = "/{id}/InspectionMar",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryInspectionMar(@RequestParam Map<String, String> queryParam,@PathVariable(value = "id") String id) {
        PageInfoDto pageInfoDto = stockLogService.queryInspectionMar(queryParam,id);
        return pageInfoDto;
    }
    
    /**
     * 根据VIN查询整车质损明细
     * @author Benzc
     * @date 2017年2月16日
     */
    @RequestMapping(value = "/{id}/Mar",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryMar(@RequestParam Map<String, String> queryParam,@PathVariable(value = "id") String id) {
        PageInfoDto pageInfoDto = stockLogService.queryMar(queryParam,id);
        return pageInfoDto;
    }
    
    /**
     * 根据ID 获取质损附件信息
     * 
     * @author Benzc
     * @date 2017年2月16日
     */
    @RequestMapping(value = "/{itemId}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMarAttachmentById(@PathVariable(value = "itemId") String itemId) {
    	VsInspectionMarPO InspectionMar = stockLogService.getInspectionMarById(itemId);
        return InspectionMar.toMap();
    }
    
    /**
     * 查询整车质损附件
     * @author Benzc
     * @date 2017年2月16日
     */
    @RequestMapping(value = "/{itemId}/MarAttachment",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryMarAttachment(@RequestParam Map<String, String> queryParam,@PathVariable(value = "itemId") String itemId) {
        PageInfoDto pageInfoDto = stockLogService.queryMarAttachment(queryParam,itemId);
        return pageInfoDto;
    }
    

}
