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
import com.yonyou.dms.repair.domains.DTO.basedata.MainDressTypeDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.OtherCostDefineDTO;
import com.yonyou.dms.repair.domains.DTO.basedata.SubDressTypeDTO;
import com.yonyou.dms.repair.service.basedata.DressService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/basedata/Dress")
public class DressController {
        // 定义日志接口
        private static final Logger logger = LoggerFactory.getLogger(DressController.class);
        @Autowired
        private  DressService dressService;
        /**
         * 查询主分类信息
        * TODO description
        * @author yujiangheng
        * @date 2017年4月12日
        * @param queryParam
        * @return
        * @throws ServiceBizException
         */
        @RequestMapping(method = RequestMethod.GET)
        @ResponseBody
        public PageInfoDto searchMainDress(@RequestParam Map<String, String> queryParam) throws ServiceBizException{
            PageInfoDto pageInfoDto = dressService.searchMainDress(queryParam);
            return pageInfoDto;
        }  
        /**
         * 查询二级分类信息
        * TODO description
        * @author yujiangheng
        * @date 2017年4月12日
        * @param queryParam
        * @return
        * @throws ServiceBizException
         */
        @RequestMapping( value="{MAIN_GROUP_CODE}",method = RequestMethod.GET)
        @ResponseBody
        public PageInfoDto searchSubDress(@PathVariable("MAIN_GROUP_CODE") String mainGroupCode) throws ServiceBizException{
            PageInfoDto pageInfoDto = dressService.searchSubDress(mainGroupCode);
            return pageInfoDto;
        }  
        
        /**
         * 新增主分类
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
         public ResponseEntity<MainDressTypeDTO> addMainDress(@RequestBody MainDressTypeDTO mainDressTypeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
             dressService.addMainDress(mainDressTypeDTO);
             MultiValueMap<String,String> headers = new HttpHeaders();  
             return new ResponseEntity<MainDressTypeDTO>(mainDressTypeDTO,headers, HttpStatus.CREATED);  
        }
         /**
          * 查询一个主分类
         * TODO description
         * @author yangjie
         * @date 2017年4月10日
         * @param unitCode
         * @param uriCB
         * @return
         * @throws ServiceBizException
          */
         @RequestMapping(value = "/get/{MAIN_GROUP_CODE}", method = RequestMethod.GET)
         @ResponseBody
         public Map<String,String> findByMainGroupCode(@PathVariable("MAIN_GROUP_CODE")String mainGroupCode,
                                                     UriComponentsBuilder uriCB) throws ServiceBizException{
             Map<String,String> mainGroup = dressService.findByMainGroup(mainGroupCode);
            // System.out.println(unit.toString());
           return mainGroup;  
         }
         /**
          * 根据mainGroupCode和dealerCode修改主分类信息
          * @author yujiangheng
          * @date 2017年3月21日
          */
         @RequestMapping(value = "/{MAIN_GROUP_CODE}", method = RequestMethod.PUT)
         @ResponseBody
         public ResponseEntity<OtherCostDefineDTO> updateMainDress(@PathVariable("MAIN_GROUP_CODE") String mainGroupCode,@RequestBody 
       MainDressTypeDTO mainDressTypeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
             dressService.updateMainDress(mainGroupCode, mainDressTypeDTO);
             MultiValueMap<String,String> headers = new HttpHeaders();  
             headers.set("Location", uriCB.path("/{MAIN_GROUP_CODE}").buildAndExpand(mainGroupCode).toUriString());  
             return new ResponseEntity<OtherCostDefineDTO>(headers, HttpStatus.CREATED);  
         }
        
         
         /**
          * 新增二级分类
         * TODO description
         * @author yujiangheng
         * @date 2017年4月10日
         * @param unitDTO
         * @param uriCB
         * @return
         * @throws ServiceBizException
          */
          @RequestMapping(value="/{MAIN_GROUP_CODE}",method = RequestMethod.POST)
          @ResponseBody
          public ResponseEntity<SubDressTypeDTO> addSubDress(@PathVariable("MAIN_GROUP_CODE") String mainGroupCode,
 @RequestBody SubDressTypeDTO subDressTypeDTO,UriComponentsBuilder uriCB) throws ServiceBizException{
              dressService.addSubDress(mainGroupCode,subDressTypeDTO);
              MultiValueMap<String,String> headers = new HttpHeaders();  
              return new ResponseEntity<SubDressTypeDTO>(subDressTypeDTO,headers, HttpStatus.CREATED);  
         }
          /**
           * 查询一个二级分类
          * TODO description
          * @author yangjie
          * @date 2017年4月10日
          * @param unitCode
          * @param uriCB
          * @return
          * @throws ServiceBizException
           */
          @RequestMapping(value = "/get/{MAIN_GROUP_CODE}/{SUB_GROUP_CODE}", method = RequestMethod.GET)
          @ResponseBody
          public Map<String,String> findByMainGroupCodeAndSubGroupCode(@PathVariable("MAIN_GROUP_CODE")String mainGroupCode,
 @PathVariable("SUB_GROUP_CODE")String subGroupCode, UriComponentsBuilder uriCB) throws ServiceBizException{
              Map<String,String> subGroup = dressService.findSubGroup(mainGroupCode,subGroupCode);
            return subGroup;  
          }
          /**
           * 根据subGroupCode mainGroupCode和dealerCode修改二级分类信息
           * @author yujiangheng
           * @date 2017年3月21日
           */
          @RequestMapping(value = "/{MAIN_GROUP_CODE}/{SUB_GROUP_CODE}", method = RequestMethod.PUT)
          @ResponseBody
          public ResponseEntity<SubDressTypeDTO> updateSubDress(@PathVariable("MAIN_GROUP_CODE") String mainGroupCode,                                                                 
            @PathVariable("SUB_GROUP_CODE")String subGroupCode,@RequestBody  SubDressTypeDTO subDressTypeDTO,
           UriComponentsBuilder uriCB) throws ServiceBizException{
              dressService.updateSubDress(mainGroupCode, subGroupCode,subDressTypeDTO);
              MultiValueMap<String,String> headers = new HttpHeaders();  
         //     headers.set("Location", uriCB.path("/{MAIN_GROUP_CODE}/{SUB_GROUP_CODE}").buildAndExpand(mainGroupCode).toUriString());  
              return new ResponseEntity<SubDressTypeDTO>(headers, HttpStatus.CREATED);  
          }
         
        
        
        
        
        
        
        
    
    
    
}
