package com.yonyou.dms.part.controller.basedata;
import java.util.ArrayList;
import java.util.HashMap;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.ListToolDefineDTO;
import com.yonyou.dms.part.domains.DTO.basedata.ToolDefineDTO;
import com.yonyou.dms.part.domains.DTO.basedata.UnitDTO;
import com.yonyou.dms.part.service.basedata.ToolDefineService;
import com.yonyou.f4.mvc.annotation.TxnConn;


import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.FrameworkUtil;
/**
 * 工具定义
* TODO description
* @author yujiangheng
* @date 2017年4月6日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/ToolDefine")
public class ToolDefineController {
 // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(ToolDefineController.class);
    @Autowired
    private ToolDefineService toolDefineService;
    
    @Autowired
    private ExcelGenerator excelService;//导出接口
    /*
    * 查询导出excel
    * @author zhengcong
    * @date 2017年3月29日
    */
   @RequestMapping(value="/export",method = RequestMethod.GET)
   public void excel(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                     HttpServletResponse response){
       List<Map> resultList =toolDefineService.queryToExport(queryParam);//查询出的数据
       Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();  //导出数据
       excelData.put(FrameworkUtil.getLoginInfo().getDealerShortName()+"_工具定义", resultList);
       List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>(); //设置导出格式
       exportColumnList.add(new ExcelExportColumn("TOOL_CODE","代码"));
       exportColumnList.add(new ExcelExportColumn("TOOL_NAME","名称"));
       exportColumnList.add(new ExcelExportColumn("TOOL_SPELL","工具拼音"));
       exportColumnList.add(new ExcelExportColumn("UNIT_NAME","单位"));
       exportColumnList.add(new ExcelExportColumn("TOOL_TYPE_NAME","工具类别"));
       exportColumnList.add(new ExcelExportColumn("POSITION","存放位置"));
       exportColumnList.add(new ExcelExportColumn("LEND_QUANTITY","借进数量"));
       exportColumnList.add(new ExcelExportColumn("STOCK_QUANTITY","库存数量"));
       exportColumnList.add(new ExcelExportColumn("CAPITAL_ASSERTS_MANAGE_NO","固定资产编号"));
       exportColumnList.add(new ExcelExportColumn("PRINCIPAL_NAME","负责人"));
       exportColumnList.add(new ExcelExportColumn("DATE_VALIDITY","使用年限"));
       excelService.generateExcelForDms(excelData, exportColumnList, FrameworkUtil.getLoginInfo().getDealerShortName()+"_工具定义.xls", request, response);
 
       
   }
    /**
     * 根据查询条件查询工具定义信息
    * TODO description
    * @author yyujiangheng
    * @date 2017年4月1日
    * @param storeSQL
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto AccountPeriodSql(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
        PageInfoDto pageInfoDto = toolDefineService.searchToolDefine(queryParam);
        //System.out.println(pageInfoDto.toString());
        return pageInfoDto;
    }  
    /**
     * 直接查出所有员工的数据
    * TODO description
    * @author yujiangheng
    * @date 2017年4月1日
    * @param 
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(value="/Employee/Select",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getAllSelect() throws ServiceBizException{
       List<Map> all = toolDefineService.getAllSelect();
        return all;
    }  
    /**
     * 根据toolCode查询一条数据
    * TODO description
    * @author yujiangheng
    * @date 2017年4月17日
    * @param toolCode
    * @param uriCB
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(value = "/{TOOL_CODE}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findByToolCode(@PathVariable("TOOL_CODE") String toolCode,
                                                UriComponentsBuilder uriCB) throws ServiceBizException{
        Map<String,Object> toolDefine = toolDefineService.findByToolCode(toolCode);
       // System.out.println(toolDefine.toString());
      return toolDefine;  
    }
    /**
     * 保存所有数据
    * TODO description
    * @author yujiangheng 
    * @date 2017年4月17日
    * @param listToolDefineDTO
    * @param uriCB
    * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    @ResponseStatus(HttpStatus.CREATED)
    public void saveAll(@RequestBody ListToolDefineDTO listToolDefineDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
       for(ToolDefineDTO tool:listToolDefineDTO.getDms_toolsType()){
            System.out.println(tool.toString());
        }
        //System.out.println("______________________"+listToolDefineDTO.toString());
       toolDefineService.saveAll(listToolDefineDTO.getDms_toolsType());
      /*  MultiValueMap<String,String> headers = new HttpHeaders();  
        return new ResponseEntity<UnitDTO>(unitDTO,headers, HttpStatus.CREATED);  */
       //输出测试
   
   }
    
    
    
    
    
}
