package com.yonyou.dms.part.controller.basedata;

import java.util.List;
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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.ToolsTypeDTO;
import com.yonyou.dms.part.domains.DTO.basedata.UnitDTO;
import com.yonyou.dms.part.service.basedata.ToolsTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 工具类别
* TODO description
* @author yujiangheng
* @date 2017年4月12日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/ToolsType")
public class ToolsTypeController {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);
    @Autowired
    private ToolsTypeService toolsTypeService;
    /**
     * 根据查询条件查询工具类别信息
    * TODO description
    * @author yyujiangheng
    * @date 2017年4月1日
    * @param storeSQL
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchToolsType(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
        PageInfoDto pageInfoDto = toolsTypeService.searchToolsType(queryParam);
        return pageInfoDto;
    }  
    ///dms.web/part/rest/basedata/ToolsType
 /**
  * 新增工具类别
  * @author yujiangheng
  * @date 2016年6月30日
  * */
 @RequestMapping(method = RequestMethod.POST)
 @ResponseBody
 public ResponseEntity<ToolsTypeDTO> addStore(@RequestBody ToolsTypeDTO toolsTypeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
     toolsTypeService.addToolsType(toolsTypeDTO);
     MultiValueMap<String,String> headers = new HttpHeaders();  
     return new ResponseEntity<ToolsTypeDTO>(toolsTypeDTO,headers, HttpStatus.CREATED);  
}  
 /**
  * 根据toolTypeCode和dealerCode查询工具类别信息
  * @author zhengcong
  * @date 2017年3月21日
  */
 @RequestMapping(value = "/{TOOL_TYPE_CODE}", method = RequestMethod.GET)
 @ResponseBody
 public Map<String,String> findByUnitCode(@PathVariable("TOOL_TYPE_CODE") String toolTypeCode,
                                             UriComponentsBuilder uriCB) throws ServiceBizException{
     Map<String,String>toolsType= toolsTypeService.findBytoolTypeCode(toolTypeCode);
   return toolsType;  
 }   
 /**
  * 根据toolTypeCode和dealerCode修改工具类别信息
  * @author yujiangheng
  * @date 2017年3月21日
  */
 @RequestMapping(value = "/{TOOL_TYPE_CODE}", method = RequestMethod.PUT)
 @ResponseBody
 //@ResponseStatus(HttpStatus.CREATED) 
 public ResponseEntity<ToolsTypeDTO> updateStore(@PathVariable("TOOL_TYPE_CODE") String toolTypeCode,
                                            @RequestBody ToolsTypeDTO toolsTypeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
     toolsTypeService.updateToolsType(toolTypeCode, toolsTypeDTO);
     MultiValueMap<String,String> headers = new HttpHeaders();  
     headers.set("Location", uriCB.path("/{TOOL_TYPE_CODE}").buildAndExpand(toolTypeCode).toUriString());  
     return new ResponseEntity<ToolsTypeDTO>(headers, HttpStatus.CREATED);  
 }
 /**
  * 直接查出所有的数据用于生成下拉选
 * TODO description
 * @author yyujiangheng
 * @date 2017年4月1日
 * @param storeSQL
 * @return
 * @throws ServiceBizException
  */
 @RequestMapping(value="/Select",method = RequestMethod.GET)
 @ResponseBody
 public List<Map> getAllSelect() throws ServiceBizException{
    List<Map> all = toolsTypeService.getAllSelect();
 //  System.out.println(pageInfoDto);
     return all;
 }  
}
