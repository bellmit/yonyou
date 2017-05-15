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
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackPtitemRelationDTO;
import com.yonyou.dms.vehicle.service.threePack.TtThreepackPtitemRelationService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/relation")
public class TtThreepackPtitemRelationController extends BaseController{
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	TtThreepackPtitemRelationService tservice;
	
	@Autowired
    private ExcelRead<TtThreepackPtitemRelationDTO>  excelReadService;
	
	 @RequestMapping(method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询配件设定信息");
	        PageInfoDto pageInfoDto=tservice.findthreePack(queryParam);
	        return pageInfoDto;
	    }
	 
	 @RequestMapping(value = "/searchAllRelation",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> searchAllRelation(@RequestParam Map<String, String> queryParam) throws Exception {
	    	logger.info("查询所有配件信息");
	    	List<Map> pageInfoDto=tservice.findAllRelation(queryParam);
	        return pageInfoDto;
	    }
	    @RequestMapping(value = "/add",method = RequestMethod.POST)
	    public ResponseEntity<TtThreepackPtitemRelationDTO> addMaterialGroup(@RequestBody @Valid TtThreepackPtitemRelationDTO tcDto, UriComponentsBuilder uriCB) {
	    	logger.info("新增配件信息");
	        Long id = tservice.addMinclass(tcDto);
	        MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/relation/add/{id}").buildAndExpand(id).toUriString());
	        return new ResponseEntity<TtThreepackPtitemRelationDTO>(tcDto, headers, HttpStatus.CREATED);

	    }
	    
	    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
		public ResponseEntity<TtThreepackPtitemRelationDTO> ModifyTcBank(@PathVariable("id") Long id,@RequestBody TtThreepackPtitemRelationDTO tcDto,UriComponentsBuilder uriCB) {
			logger.info("修改配件信息");
	    	tservice.modifyMinclass(id, tcDto);
			MultiValueMap<String,String> headers = new HttpHeaders();  
			headers.set("Location", uriCB.path("/relation/{id}").buildAndExpand(id).toUriString());  
			return new ResponseEntity<TtThreepackPtitemRelationDTO>(headers, HttpStatus.CREATED);
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
	    public ArrayList<TtThreepackPtitemRelationDTO> importforecastAudit(@RequestParam final Map<String,String> queryParam,@RequestParam(value = "file") MultipartFile importFile,ForecastImportDto forecastImportDto, UriComponentsBuilder uriCB) throws Exception {
	 		logger.info("============配件信息导入===============");
	         // 解析Excel 表格(如果需要进行回调)
	         ImportResultDto<TtThreepackPtitemRelationDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<TtThreepackPtitemRelationDTO>(TtThreepackPtitemRelationDTO.class,new ExcelReadCallBack<TtThreepackPtitemRelationDTO>() {
	             @Override
	             public void readRowCallBack(TtThreepackPtitemRelationDTO rowDto, boolean isValidateSucess) {
	                 try{
	                     // 只有全部是成功的情况下，才执行数据库保存
	                     if(isValidateSucess){
	                     	ImportResultDto<TtThreepackPtitemRelationDTO> importResultList =
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
	    /**
	     * 项目名称
	     * @param params
	     * @return
	     */
	    @RequestMapping(value="/itemName/{itemNo}",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> selectitemName(@PathVariable(value = "itemNo") String itemNo){	    	
	    	List<Map> itemlist = tservice.selectitemName(itemNo);
	        return itemlist;
	    }
	    /**
	     * 配件名称
	     * @param params
	     * @return
	     */
	    @RequestMapping(value="/partName/{partCode}",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> selectpartName(@PathVariable(value = "partCode") String partCode){	    	
	    	List<Map> itemlist = tservice.selectpartName(partCode);
	        return itemlist;
	    }
	    /**
	     * 小类下拉框
	     * @param params
	     * @return
	     */
	    @RequestMapping(value="/itemMin/{itemNo}",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> selectitemMin(@PathVariable(value = "itemNo") String itemNo) {
	        List<Map> itemlist = tservice.selectItemMin(itemNo);
	        return itemlist;
	    }
	    /**
	     * 小类名称
	     * @param params
	     * @return
	     */
	    @RequestMapping(value="/itemMinName/{minclassNo}",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> selectitemMinName(@PathVariable(value = "minclassNo") String minclassNo) {
	    	List<Map> itemlist = tservice.selectmMinName(minclassNo);
	        return itemlist;
	    }
	    
	    @RequestMapping(value = "/delete/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void deleteCharge(@PathVariable("id") Long id,UriComponentsBuilder uriCB) throws ServiceBizException{
	        tservice.deleteChargeById(id);
	    }
}
