package com.yonyou.dms.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.report.service.impl.RemainingOrderDetailService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/keepOrder/remainingOrderDetail")
public class RemainingOrderDetailController extends BaseController {

    @Autowired
    private RemainingOrderDetailService remainingOrderDetailService;

    @Autowired
    private ExcelGenerator     excelService;
    
    /**
    * 表格查询
    * @author zhanshiwei
    * @date 2017年2月10日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping( method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryRemainingOrderDetail(@RequestParam Map<String, String> queryParam) {
        PageInfoDto id = remainingOrderDetailService.queryRemainingOrderDetail(queryParam);
        return id;
    }

    
    /**
    * 查询表表格列
    * @author zhanshiwei
    * @date 2017年2月10日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value = "/quryModel", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryModel(@RequestParam Map<String, String> queryParam) {
        List<Map> listopetypeMap = remainingOrderDetailService.queryModel(queryParam);
        return listopetypeMap;
    }
    
    
    /**
    * 库存查询
    * @author zhanshiwei
    * @date 2017年2月10日
    * @param queryParam
    * @return
    */
    	
    @RequestMapping(value="/item", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryOrderDetail(@RequestParam Map<String, String> queryParam) {
        PageInfoDto id = remainingOrderDetailService.queryOrderDetail(queryParam);
        return id;
    }
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export/excel", method = RequestMethod.GET)
    public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                   HttpServletResponse response) throws Exception {
        List<Map> resultList = remainingOrderDetailService.queryRemainingOrderDetailList(queryParam);
        List<Map> listopetypeMap = remainingOrderDetailService.queryModel(queryParam);
        List<Map> rows = resultList;
        Map values = new HashMap();
        values.put("USER_NAME", "合计");
        for(Map map:listopetypeMap){
            values.put(map.get("CODE").toString(), 0);
            //exportColumnList.add(new ExcelExportColumn(map.get("CODE").toString(), map.get("NAME").toString()));
        }
        values.put("countSOLD", 0);
        for(Map map:listopetypeMap){
            
            for (Map map1 : rows) { 
                
                values.put(map.get("CODE").toString(), Double.parseDouble(map1.get(map.get("CODE").toString()).toString())+ Double.parseDouble(values.get(map.get("CODE").toString()).toString()));  
            }
            
        }
        for (Map map : rows) {
            values.put("countSOLD", Double.parseDouble(map.get("countSOLD").toString())+ Double.parseDouble(values.get("countSOLD").toString()));
        }
        rows.add(values);
        
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车辆进厂月报", rows);
        // 生成excel 文件
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        exportColumnList.add(new ExcelExportColumn("USER_NAME", "销售顾问"));
       
        for(Map map:listopetypeMap){
            exportColumnList.add(new ExcelExportColumn(map.get("CODE").toString(), map.get("NAME").toString()));
        }
        exportColumnList.add(new ExcelExportColumn("countSOLD", "合计"));
        excelService.generateExcel(excelData, exportColumnList,  FrameworkUtil.getLoginInfo().getDealerName()+"_留存订单简表.xls", request, response);
    }
}
