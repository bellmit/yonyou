package com.yonyou.dms.vehicle.controller.afterSales.partMgr;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtPartCeilingPriceDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtPartCeilingPricePO;
import com.yonyou.dms.vehicle.service.afterSales.partMgr.PartCeilingPriceService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 配件限价
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/partCeilingPrice")
public class PartCeilingPriceController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	PartCeilingPriceService  partCeilingPriceService;
	@Autowired
	ExcelGenerator excelService;
	
    @Autowired
    SystemParamService paramService;
	
	/**
	 * 配件限价查询
	 */
	@RequestMapping(value="/partCeilingPriceSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto partCeilingPriceQuery(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============配件限价查询=============");
        PageInfoDto pageInfoDto =partCeilingPriceService.partCeilingPriceQuery(queryParam);
        return pageInfoDto;  
    }
	 /**
     * 下载模板
     */
    @RequestMapping(value = "/{type}",method = RequestMethod.GET)
    public void donwloadTemplte(@PathVariable(value = "type") String type,HttpServletRequest request,HttpServletResponse response,@RequestParam Map<String, String> queryParam){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        if(queryParam.get("ceilingPriceScope").toString()!=""){
        	 try {
        if(Long.parseLong(queryParam.get("ceilingPriceScope").toString())==10051002){
        	String tt="quanwangdownLoad";
        	  try {
                  //查询对应的参数
                  BasicParametersDTO paramDto = paramService.queryBasicParameterByTypeandCode(ParamCodeConstants.TEMPLATE_DOWNLOAD, tt);
                  Resource resource = new ClassPathResource(paramDto.getParamValue()); 
                  //获取文件名称
                  FrameHttpUtil.setExportFileName(request,response, resource.getFilename());
                  
                  response.addHeader("Content-Length", "" + resource.getFile().length());
                  
                  bis = new BufferedInputStream(resource.getInputStream());
                  byte[] bytes = new byte[1024];
                  bos = new BufferedOutputStream(response.getOutputStream());
                  while((bis.read(bytes))!=-1){
                      bos.write(bytes);
                  }
                  bos.flush();
              } catch (Exception e) {
                  throw new ServiceBizException("下载模板失败,请与管理员联系",e);
              }finally{
                  IOUtils.closeStream(bis);
                  IOUtils.closeStream(bos);
              }
        }else  if(Long.parseLong(queryParam.get("ceilingPriceScope").toString())==10051001){
        try {
            //查询对应的参数
            BasicParametersDTO paramDto = paramService.queryBasicParameterByTypeandCode(ParamCodeConstants.TEMPLATE_DOWNLOAD, type);
            Resource resource = new ClassPathResource(paramDto.getParamValue()); 
            //获取文件名称
            FrameHttpUtil.setExportFileName(request,response, resource.getFilename());
            
            response.addHeader("Content-Length", "" + resource.getFile().length());
            
            bis = new BufferedInputStream(resource.getInputStream());
            byte[] bytes = new byte[1024];
            bos = new BufferedOutputStream(response.getOutputStream());
            while((bis.read(bytes))!=-1){
                bos.write(bytes);
            }
            bos.flush();
        } catch (Exception e) {
            throw new ServiceBizException("下载模板失败,请与管理员联系",e);
        }finally{
            IOUtils.closeStream(bis);
            IOUtils.closeStream(bos);
        }
    }
        } catch (Exception e) {
            throw new ServiceBizException("请选择限价范围！",e);
        }
        	 
        }
    }
	/**
	 * 信息回显
	 */
    @RequestMapping(value = "/getPartCeilingPriceById/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getTmLimiteById(@PathVariable(value = "id") Long id){
    	logger.info("==== 配件限价信息回显=====");
    	TtPartCeilingPricePO ptPo=partCeilingPriceService.getTmLimiteById(id);
        return ptPo.toMap();
    }   
    /**
     * 查询明细~配件限价列表
     */
	@RequestMapping(value="/partCeilingPriceQuery/{CEILING_PRICE_CODE}/{CEILING_PRICE_SCOPE}",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto partCeilingPriceQuery2(@RequestParam Map<String, String> queryParam,  @PathVariable(value = "CEILING_PRICE_CODE")  String  priceCode, @PathVariable(value = "CEILING_PRICE_SCOPE") String priceScope) {
    	logger.info("==============查询明细~配件限价列表=============");
        PageInfoDto pageInfoDto =partCeilingPriceService.CeilingPriceQuery(queryParam,priceCode,priceScope);
        return pageInfoDto;  
    }
	
	/**
	 * 删除
	 */
	
    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
    	logger.info("===== 删除配件限价=====");
    	partCeilingPriceService.delete(id);
    }
    /**
     * 新增
     */
    @RequestMapping(value ="/addPartCeilingPrice",method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TtPartCeilingPriceDTO> addPartCeilingPrice(@RequestBody TtPartCeilingPriceDTO ptdto,UriComponentsBuilder uriCB){
    	logger.info("=====  新增预授权其他费用维护=====");
    	partCeilingPriceService.add(ptdto);
        MultiValueMap<String,String> headers = new HttpHeaders();  
        headers.set("Location", uriCB.path("/partCeilingPrice/addPartCeilingPrice").buildAndExpand().toUriString());  
        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
    }
    /**
     * 修改
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TtPartCeilingPriceDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtPartCeilingPriceDTO dto,UriComponentsBuilder uriCB){
    	logger.info("===== 修改配件限价=====");
    	partCeilingPriceService.update(id,dto);
    	MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/partCeilingPrice/edit").buildAndExpand().toUriString());
        return new ResponseEntity<TtPartCeilingPriceDTO>(headers, HttpStatus.CREATED);  	
    }
    /**
     * 下发
     */
    @RequestMapping(value = "/xiafa/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void xiafa(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
    	logger.info("===== 下发配件限价=====");
    	partCeilingPriceService.delete(id);
    }


}
