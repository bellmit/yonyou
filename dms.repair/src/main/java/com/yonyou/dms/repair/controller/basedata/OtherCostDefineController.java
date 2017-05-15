package com.yonyou.dms.repair.controller.basedata;
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
import com.yonyou.dms.repair.domains.DTO.basedata.OtherCostDefineDTO;
import com.yonyou.dms.repair.service.basedata.OtherCostDefineService;
import com.yonyou.f4.mvc.annotation.TxnConn;
/**
 * 其他成本
* TODO description
* @author yujiangheng
* @date 2017年4月10日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/OtherCostDefine")
public class OtherCostDefineController {
    // 定义日志接口
    private static final Logger logger = LoggerFactory.getLogger(OtherCostDefineController.class);
    
    @Autowired
    private OtherCostDefineService otherCostDefineService;
   /**
    * 根据条件查询其他成本信息
   * TODO description
   * @author yujiangheng
   * @date 2017年4月10日
   * @param queryParam
   * @return
   * @throws ServiceBizException
    */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto searchOtherCost(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
        PageInfoDto pageInfoDto = otherCostDefineService.searchOtherCost(queryParam);
        return pageInfoDto;
    }  
   /**
    * 新增其他成本
   * TODO description
   * @author yujiangheng
   * @date 2017年4月10日
   * @param unitDTO
   * @param uriCB
   * @return
   * @throws ServiceBizException
    */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<OtherCostDefineDTO> addStore(@RequestBody OtherCostDefineDTO otherCostDefineDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        otherCostDefineService.addOtherCostDefine(otherCostDefineDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        return new ResponseEntity<OtherCostDefineDTO>(otherCostDefineDTO,headers, HttpStatus.CREATED);  
   }
    /**
     * 
    * TODO description
    * @author yangjie
    * @date 2017年4月10日
    * @param unitCode
    * @param uriCB
    * @return
    * @throws ServiceBizException
     */
    @RequestMapping(value = "/{OTHER_COST_CODE}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,String> findByOtherCostCode(@PathVariable("OTHER_COST_CODE") String otherCostCode ,
                                                UriComponentsBuilder uriCB) throws ServiceBizException{
        Map<String,String> otherCost = otherCostDefineService.findByOtherCostCode(otherCostCode);
      return otherCost;  
    }
    /**
     * 根据unitCode和dealerCode修改信息
     * @author yujiangheng
     * @date 2017年3月21日
     */
    @RequestMapping(value = "/{OTHER_COST_CODE}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<OtherCostDefineDTO> updateOtherCost(@PathVariable("OTHER_COST_CODE") String otherCostCode ,
@RequestBody OtherCostDefineDTO otherCostDefineDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
        otherCostDefineService.updateOtherCost(otherCostCode, otherCostDefineDTO);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/{OTHER_COST_CODE}").buildAndExpand(otherCostCode).toUriString());  
        return new ResponseEntity<OtherCostDefineDTO>(headers, HttpStatus.CREATED);  
    }

    
    
}
