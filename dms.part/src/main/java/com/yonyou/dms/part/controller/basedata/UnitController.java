package com.yonyou.dms.part.controller.basedata;
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
import org.springframework.web.util.UriComponentsBuilder;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.domains.DTO.basedata.UnitDTO;
import com.yonyou.dms.part.service.basedata.UnitService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 计量单位
* TODO description
* @author yujiangheng
* @date 2017年4月1日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/unit")
public class UnitController extends BaseController{
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);

    @Autowired
    private UnitService unitService;
    
    /**
     * 根据查询条件查询计量单位信息
    * TODO description
    * @author yyujiangheng
    * @date 2017年4月1日
    * @param storeSQL
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto storeSql(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
        PageInfoDto pageInfoDto = unitService.searchUnit(queryParam);
        return pageInfoDto;
    }  
    
 /**
  * 新增计量单位
  * @author yujiangheng
  * @date 2016年6月30日
  * */
 @RequestMapping(method = RequestMethod.POST)
 @ResponseBody
 public ResponseEntity<UnitDTO> addStore(@RequestBody UnitDTO unitDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
     unitService.addUnitPo(unitDTO);
     MultiValueMap<String,String> headers = new HttpHeaders();  
     return new ResponseEntity<UnitDTO>(unitDTO,headers, HttpStatus.CREATED);  
}
 
 
 
 /**
  * 根据unitCode和dealerCode查询户信息
  * @author zhengcong
  * @date 2017年3月21日
  */
 @RequestMapping(value = "/{UNIT_CODE}", method = RequestMethod.GET)
 @ResponseBody
 public Map<String,String> findByUnitCode(@PathVariable("UNIT_CODE") String unitCode,
                                             UriComponentsBuilder uriCB) throws ServiceBizException{
     Map<String,String> unit = unitService.findByUnitCode(unitCode);
     System.out.println(unit.toString());
   return unit;  
 }

 /**
  * 根据unitCode和dealerCode修改信息
  * @author yujiangheng
  * @date 2017年3月21日
  */
 @RequestMapping(value = "/{UNIT_CODE}", method = RequestMethod.PUT)
 @ResponseBody
 public ResponseEntity<UnitDTO> updateStore(@PathVariable("UNIT_CODE") String unitCode,@RequestBody UnitDTO unitdto,UriComponentsBuilder uriCB) throws ServiceBizException{
    unitService.updateUnit(unitCode, unitdto);
     MultiValueMap<String,String> headers = new HttpHeaders();  
     headers.set("Location", uriCB.path("/{UNIT_CODE}").buildAndExpand(unitCode).toUriString());  
     return new ResponseEntity<UnitDTO>(headers, HttpStatus.CREATED);  
 }

    
}