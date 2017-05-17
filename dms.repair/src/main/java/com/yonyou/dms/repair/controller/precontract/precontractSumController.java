package com.yonyou.dms.repair.controller.precontract;

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
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.service.Precontract.PrecontractArrangeService;
import com.yonyou.dms.repair.service.Precontract.PrecontractSumService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;


@Controller
@TxnConn
@RequestMapping("/precontractSum/queryPreSum")
public class precontractSumController extends BaseController{
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(precontractSumController.class);
    @Autowired
    private PrecontractSumService precontractSumService;
    @Autowired
    private ExcelGenerator excelService;//导出接口
    /**
     * 查询服务专员下拉
    * TODO description
    * @author yujiangheng
    * @date 2017年4月1日
    * @param 
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(value="/Service/Select",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAllServiceSelect() throws ServiceBizException{
       List<Map> all = precontractSumService.getAllServiceSelect();
        return all;
    }  
    
    /**
     * 查询指定技师下拉
    * TODO description
    * @author yujiangheng
    * @date 2017年4月1日
    * @param 
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(value="/Technician/Select",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAllTechnicianSelect() throws ServiceBizException{
       List<Map> all = precontractSumService.getAllTechnicianSelect();
        return all;
    }  
    /**
     * 查询
    * TODO description
    * @author yujiangheng
    * @date 2017年5月16日
    * @param queryParam
    * @return
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public List<Map> queryStockLog(@RequestParam Map<String, String> queryParam) {
        System.out.println("______________________________"+queryParam);
        List<Map> pageInfoDto = precontractSumService.QueryPrecontractSum(queryParam);
        return pageInfoDto;
    }
    /*
     * 查询导出excel
     * @author zhengcong
     * @date 2017年3月29日
     */
    @RequestMapping(value="/export",method = RequestMethod.GET)
    public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                      HttpServletResponse response){
        List<Map> resultList =precontractSumService.QueryPrecontractSum(queryParam);//查询出的数据
        Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();  //导出数据
        excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_预约汇总表", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>(); //设置导出格式
       // exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME","经销商"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NO","员工编号"));
        exportColumnList.add(new ExcelExportColumn("EMPLOYEE_NAME","服务专员"));
        exportColumnList.add(new ExcelExportColumn("BOOKING_RATE","预约比率"));
        exportColumnList.add(new ExcelExportColumn("AVAILABILE_BOOKING_RATE","有效预约比率"));
        exportColumnList.add(new ExcelExportColumn("SUCESS_RATE","成功率"));
        exportColumnList.add(new ExcelExportColumn("REPAIR_ORDER_COUNT","总工单台次"));
        exportColumnList.add(new ExcelExportColumn("PRECON_COUNT","预约台次"));
        exportColumnList.add(new ExcelExportColumn("IN_ON_TIME","准时进厂台次"));
        exportColumnList.add(new ExcelExportColumn("IN_BEFORE_TIME","提前进厂台次"));
        exportColumnList.add(new ExcelExportColumn("IN_AFTER_TIME","延迟进厂台次"));
        exportColumnList.add(new ExcelExportColumn("IN_CANCEL","取消进厂台次"));
        exportColumnList.add(new ExcelExportColumn("IN_NOT","未进厂台次"));
        excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_预约汇总表.xls", request, response);
    }
    
}