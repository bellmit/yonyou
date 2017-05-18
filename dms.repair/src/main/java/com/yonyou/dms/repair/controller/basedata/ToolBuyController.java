
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.repair
 *
 * @File name : ColorController.java
 *
 * @Author : DuPengXin
 *
 * @Date : 2016年8月11日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月11日    DuPengXin   1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.repair.controller.basedata;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.domains.DTO.basedata.ListToolBuyItemDTO;
import com.yonyou.dms.repair.service.basedata.ToolBuyService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 *工具采购入库
 * @author Benzc
 * @date 2016年12月22日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/ToolBuy")
public class ToolBuyController extends BaseController {

    @Autowired
    private ToolBuyService toolBuyService;
    @Autowired
    private ExcelGenerator excelService;//导出接口
   /**
    * 工具采购查询
   * TODO description
   * @author yujiangheng
   * @date 2017年4月20日
   * @param queryParam
   * @return
    */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto SearchToolBuyInfo(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = toolBuyService.SearchToolBuyInfo(queryParam);
        return pageInfoDto;
    }
    /**
     * 工具采购明细查询
    * TODO description
    * @author yujiangheng
    * @date 2017年4月20日
    * @param queryParam
    * @return
     */
     @RequestMapping(value="/item/{BUY_NO}",method = RequestMethod.GET)
     @ResponseBody
     public PageInfoDto SearchToolBuyItem(@RequestParam String buyNo) {
         PageInfoDto pageInfoDto = toolBuyService.SearchToolBuyItem(buyNo);
         return pageInfoDto;
     }
     /*
      * 查询导出excel
      * @author zhengcong
      * @date 2017年3月29日
      */
     @RequestMapping(value="/export/{BUY_NO}",method = RequestMethod.GET)
     public void excel(@RequestParam String buyNo, HttpServletRequest request,
                       HttpServletResponse response){
         List<Map> resultList =toolBuyService.queryToExport(buyNo);//查询出的数据
         Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();  //导出数据
         excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_采购入库", resultList);
         List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>(); //设置导出格式
         exportColumnList.add(new ExcelExportColumn("TOOL_CODE","工具代码代码"));
         exportColumnList.add(new ExcelExportColumn("TOOL_NAME","工具名称"));
         exportColumnList.add(new ExcelExportColumn("UNIT_NAME","单位"));
         exportColumnList.add(new ExcelExportColumn("UNIT_PRICE","单价"));
         exportColumnList.add(new ExcelExportColumn("QUANTITY","数量"));
         exportColumnList.add(new ExcelExportColumn("AMOUNT","金额"));
         excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_采购入库.xls", request, response);
   
         
     }
    
    /**
     * 直接查出所有部门信息
    * TODO description
    * @author yujiangheng
    * @date 2017年4月1日
    * @param 
    * @return
    * @throws ServiceBizException
     */
    @SuppressWarnings({ "rawtypes" })
    @RequestMapping(value="/Employee/Select",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAllSelect() throws ServiceBizException{
    List<Map> all = toolBuyService.getAllSelect();
        return all;
    }  
     /**
      *    保存
     * TODO description
     * @author yujiangheng
     * @date 2017年4月24日
     * @param listToolBuyItemDTO
     * @param uriCB
     * @throws ServiceBizException
     * @throws ParseException
      */
    @RequestMapping(value="/save",method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void    saveToolBuy(@RequestBody ListToolBuyItemDTO listToolBuyItemDTO,UriComponentsBuilder uriCB)throws ServiceBizException, ParseException{
     //  System.out.println("------------------------------------"+listToolBuyItemDTO.toString());
        toolBuyService.saveToolBuy(listToolBuyItemDTO);
    }
    
    /**
     * 入账： 更新购买单状态（购买单号、是否执行为 ：是 、出库/入库状态：12161001）、创建流水帐 、工具采购查询
     */
    @RequestMapping(value="/enterRecord",method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void    AddAccount(@RequestParam String buyNo,UriComponentsBuilder uriCB)throws ServiceBizException, ParseException{
        toolBuyService.AddAccount(buyNo);
    }
    
    
    
    
    
}
