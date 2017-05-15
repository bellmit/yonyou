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
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackNovehicleDTO;
import com.yonyou.dms.vehicle.service.threePack.ThreePackNoVehicleService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/noVehicle")
public class ThreePackNoVehicleController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	ThreePackNoVehicleService tservice;
	
	@Autowired
    private ExcelRead<TtThreepackNovehicleDTO>  excelReadService;

	 @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询非三包车辆设定信息");
	        PageInfoDto pageInfoDto=tservice.findthreePack(queryParam);
	        return pageInfoDto;
	    }
	 
	 @RequestMapping(value = "/searchAllVIN",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto  searchAllVIN(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询所有VIN信息");
	    	PageInfoDto  pageInfoDto=tservice.findAllVIN(queryParam);
	        return pageInfoDto;
	    } 
	    @RequestMapping(value = "/add",method = RequestMethod.POST)
	    public ResponseEntity<TtThreepackNovehicleDTO> addMaterialGroup(@RequestBody @Valid TtThreepackNovehicleDTO tcDto, UriComponentsBuilder uriCB) {
	    	logger.info("新增非三包车辆信息");
	        Long id = tservice.addnoVehicle(tcDto);
	        MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/noVehicle/add/{id}").buildAndExpand(id).toUriString());
	        return new ResponseEntity<TtThreepackNovehicleDTO>(tcDto, headers, HttpStatus.CREATED);

	    }
	
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity<TtThreepackNovehicleDTO> ModifyTcBank(@PathVariable("id") Long id,@RequestBody TtThreepackNovehicleDTO tcDto,UriComponentsBuilder uriCB) {
			logger.info("修改非三包车辆信息");
	    	tservice.modifynoVehicle(id, tcDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/noVehicle/{id}").buildAndExpand(id).toUriString());  
			return new ResponseEntity<TtThreepackNovehicleDTO>(headers, HttpStatus.CREATED);
		}
	    
	    @RequestMapping(value="/{id}",method=RequestMethod.GET)
	    @ResponseBody
	    public Map<String ,Object> findById(@PathVariable(value = "id") Long id){
	    	Map<String ,Object> map=new HashMap<String ,Object>();
	    	List<Map> model= tservice.findById(id);
	    	map =  model.get(0);
	        return map;
	    }
	    @RequestMapping(value="/vin/{vin}",method=RequestMethod.GET)
	    @ResponseBody
	    public  List<Map> findByVin(@PathVariable(value = "vin") String vin){
	    	 List<Map>	map= tservice.findByVin(vin);
	        return map;
	    }
	    @RequestMapping(value = "/import", method = RequestMethod.POST)
	 	@ResponseBody
	    public ArrayList<TtThreepackNovehicleDTO> importforecastAudit(@RequestParam final Map<String,String> queryParam,@RequestParam(value = "file") MultipartFile importFile,ForecastImportDto forecastImportDto, UriComponentsBuilder uriCB) throws Exception {
	 		logger.info("============非三包车辆信息导入===============");
	         // 解析Excel 表格(如果需要进行回调)
	         ImportResultDto<TtThreepackNovehicleDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<TtThreepackNovehicleDTO>(TtThreepackNovehicleDTO.class,new ExcelReadCallBack<TtThreepackNovehicleDTO>() {
	             @Override
	             public void readRowCallBack(TtThreepackNovehicleDTO rowDto, boolean isValidateSucess) {
	                 try{
	                     // 只有全部是成功的情况下，才执行数据库保存
	                     if(isValidateSucess){
	                     	ImportResultDto<TtThreepackNovehicleDTO> importResultList =
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
	     * 删除
	     * @param id
	     * @param uriCB
	     * @throws ServiceBizException
	     */
	    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteCharge(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
	        tservice.deleteChargeById(id);
	    }
}
