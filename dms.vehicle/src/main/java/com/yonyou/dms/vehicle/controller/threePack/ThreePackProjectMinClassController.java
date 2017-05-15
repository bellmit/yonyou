package com.yonyou.dms.vehicle.controller.threePack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.domains.DTO.threePack.ForecastImportDto;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackItemMinclassDTO;
import com.yonyou.dms.vehicle.service.threePack.ThreepackItemMinclassService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/minclass")
public class ThreePackProjectMinClassController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	ThreepackItemMinclassService tservice;
	
	@Autowired
    private ExcelRead<TtThreepackItemMinclassDTO>  excelReadService;
	
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("查询三包小类设定信息");
        PageInfoDto pageInfoDto=tservice.findthreePack(queryParam);
        return pageInfoDto;
    }
    
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<TtThreepackItemMinclassDTO> addMaterialGroup(@RequestBody @Valid TtThreepackItemMinclassDTO tcDto, UriComponentsBuilder uriCB) {
    	logger.info("新增三包小类信息");
        Long id = tservice.addMinclass(tcDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/minclass/add/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<TtThreepackItemMinclassDTO>(tcDto, headers, HttpStatus.CREATED);

    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TtThreepackItemMinclassDTO> ModifyTcBank(@PathVariable("id") Long id,@RequestBody TtThreepackItemMinclassDTO tcDto,UriComponentsBuilder uriCB) {
		logger.info("修改三包小类信息");
    	tservice.modifyMinclass(id, tcDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/minclass/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<TtThreepackItemMinclassDTO>(headers, HttpStatus.CREATED);
	}
    
    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String ,Object> findById(@PathVariable(value = "id") Long id){
    	Map<String ,Object> map=new HashMap<String ,Object>();
    	List<Map> model= tservice.findById(id);
    	map =  model.get(0);
        return map;
    }
    
    @RequestMapping(value = "/import", method = RequestMethod.POST)
 	@ResponseBody
    public ArrayList<TtThreepackItemMinclassDTO> importforecastAudit(@RequestParam final Map<String,String> queryParam,@RequestParam(value = "file") MultipartFile importFile,ForecastImportDto forecastImportDto, UriComponentsBuilder uriCB) throws Exception {
 		logger.info("============三包小类信息导入===============");
         // 解析Excel 表格(如果需要进行回调)
         ImportResultDto<TtThreepackItemMinclassDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<TtThreepackItemMinclassDTO>(TtThreepackItemMinclassDTO.class,new ExcelReadCallBack<TtThreepackItemMinclassDTO>() {
             @Override
             public void readRowCallBack(TtThreepackItemMinclassDTO rowDto, boolean isValidateSucess) {
                 try{
                     // 只有全部是成功的情况下，才执行数据库保存
                     if(isValidateSucess){
                     	ImportResultDto<TtThreepackItemMinclassDTO> importResultList =
                    			tservice.checkData(rowDto);
                     	if(importResultList != null){
                    		throw new ServiceBizException("导入出错,请见错误列表",importResultList.getErrorList()) ;
                    	}else{
                    		tservice.insert(rowDto);
                    	}
                     }
                 }catch(Exception e){
                	 throw new ServiceBizException(e) ;
                 }
             }
         }));
         logger.debug("param:" + forecastImportDto.getFileParam());
         if(importResult.isSucess()){
             return importResult.getDataList();
         }else{
             throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
         }
     }
    /**
     * 项目类型下拉框
     * @param params
     * @return
     */
    @RequestMapping(value="/item",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> selectCharge(Map<String, String> params) {
        List<Map> itemlist = tservice.selectItem(params);
        return itemlist;
    }
    
    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCharge(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
        tservice.deleteChargeById(id);
    }
}
