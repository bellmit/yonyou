package com.yonyou.dms.vehicle.controller.k4Order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.vehicle.dao.k4Order.OrderInfoQueryAeraDao;
import com.yonyou.dms.vehicle.service.k4Order.OrderInfoQueryAeraService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
*
* 
* @author liujiming
* @date 2017年3月1日
*/
@Controller
@TxnConn
@RequestMapping("/orderInfoQueryAera")
public class OrderInfoQueryAeraController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(OrderInfoQueryAeraController.class);
	
	@Autowired
	private OrderInfoQueryAeraDao orderInfoDao;
	@Autowired
	private ExcelGenerator excelService;
	
	@Autowired
	private OrderInfoQueryAeraService  orderInfoService;
	
	
	
	/**
     *汇总查询	
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/orderInfo/totalQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto orderInfoAeraTotalQuery(@RequestParam Map<String, String> queryParam) {
    	//System.out.println("queryInitController");
    	logger.info("============整车订单汇总(区域)查询08==============");
    	PageInfoDto pageInfoDto = orderInfoDao.getOrderInfoTotalQueryList(queryParam);
    	List<Map> totalList = pageInfoDto.getRows();
    	int total1 = 0;
    	int total2 = 0;
    	int total3 = 0;
    	for(Map map : totalList){
    		if(map.get("TOTAL1")==null ){
    			map.put("TOTAL1", 0);
    		}
    		if(map.get("TOTAL2")==null ){
    			map.put("TOTAL2", 0);
    		}
    		if(map.get("TOTAL3")==null ){
    			map.put("TOTAL3", 0);
    		} 
    		total1  += Integer.parseInt(map.get("TOTAL1").toString());
    		total2  += Integer.parseInt(map.get("TOTAL2").toString());
    		total3  += Integer.parseInt(map.get("TOTAL3").toString());
    	}
    	Map<String, Object> totalMap = new HashMap<String,Object>();
    	totalMap.put("GROUP_NAME", "订单合计：");
    	totalMap.put("TOTAL1", total1);
    	totalMap.put("TOTAL2", total2);
    	totalMap.put("TOTAL3", total3);
    	if(totalList.size()>0){
    		totalList.add(totalMap);
    		pageInfoDto.setRows(totalList);
		}
    	
        return pageInfoDto;
        
        
    }
	
    /**
	 * 订单汇总查询 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/orderInfo/totalDownload", method = RequestMethod.GET)
	public void orderInfoAeraTotalQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============整车订单汇总(区域)下载08===============");
    	List<Map> orderList = orderInfoDao.getOrderInfoTotalDownloadList(queryParam);
    	int total1 = 0;
    	int total2 = 0;
    	int total3 = 0;
    	for(Map map : orderList){
    		if(map.get("TOTAL1")==null ){
    			map.put("TOTAL1", 0);
    		}
    		if(map.get("TOTAL2")==null ){
    			map.put("TOTAL2", 0);
    		}
    		if(map.get("TOTAL3")==null ){
    			map.put("TOTAL3", 0);
    		} 
    		total1  += Integer.parseInt(map.get("TOTAL1").toString());
    		total2  += Integer.parseInt(map.get("TOTAL2").toString());
    		total3  += Integer.parseInt(map.get("TOTAL3").toString());
    	}
    	Map<String, Object> totalMap = new HashMap<String,Object>();
    	totalMap.put("GROUP_NAME", "订单合计：");
    	totalMap.put("TOTAL1", total1);
    	totalMap.put("TOTAL2", total2);
    	totalMap.put("TOTAL3", total3);
    	if(orderList.size()>0){
    		orderList.add(totalMap);
		}
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("整车订单汇总下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("MODEL_CODE","车型代码"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME","车型描述"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("TOTAL1","提报数量"));
		exportColumnList.add(new ExcelExportColumn("TOTAL2","审核数量"));
		exportColumnList.add(new ExcelExportColumn("TOTAL3","发运数量"));		
		excelService.generateExcel(excelData, exportColumnList, "整车订单汇总下载.xls", request, response);
	
	}
	
    
    /**
     *	明细查询	
     * @param queryParam 查询条件
     * @return pageInfoDto 查询结果
     */
    @RequestMapping(value="/orderInfo/detailQuery",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto orderInfoAeraDetailQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("============整车订单明细(区域)查询08==============");
    	PageInfoDto pageInfoDto = orderInfoDao.getOrderInfoDetailQueryList(queryParam);
    
    	return pageInfoDto;
    }
    /**
	 * 订单明细查询 下载
	 * @param queryParam 查询条件
	 */
    @RequestMapping(value = "/orderInfo/detailDownload", method = RequestMethod.GET)
	public void orderInfoAeraDetailQueryDownload(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) {
    	logger.info("============整车订单明细(区域)下载08===============");
    	orderInfoService.findOrderDetailQueryDownload(queryParam, request, response); 
    	
    	}
    
    
    
    
    
    
    
    
	
}
