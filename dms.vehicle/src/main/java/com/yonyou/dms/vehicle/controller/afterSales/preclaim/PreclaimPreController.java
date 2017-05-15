package com.yonyou.dms.vehicle.controller.afterSales.preclaim;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovalPO;
import com.yonyou.dms.vehicle.service.afterSales.preclaim.PreclaimPreService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔预授权维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/preclaimPre")
public class PreclaimPreController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	PreclaimPreService  preclaimPreService;
	//索赔预授权维护查询
	@RequestMapping(value="/preclaimPreSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto  preclaimPreSearch(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============索赔预授权维护查询=============");
        PageInfoDto pageInfoDto =preclaimPreService.PreclaimPreQuery(queryParam);
        return pageInfoDto;  
    }
	//驳回意见查询信息回显
	  @RequestMapping(value = "/getPreclaimPreById/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> getTmLimiteById(@PathVariable(value = "id") Long id){
	    	logger.info("====驳回意见查询信息回显=====");
	    	TtWrForeapprovalPO ptPo=preclaimPreService.getPreclaimPreById(id);
	        return ptPo.toMap();
	    } 
	  //删除
	   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	   @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
		   logger.info("===== 删除索赔预授权维护维护信息=====");
		   preclaimPreService.delete(id);
		    }
	   
	   //上报
	   @RequestMapping(value = "/commit/{id}", method = RequestMethod.DELETE)
	   @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void Commit(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
		   logger.info("===== 上报索赔预授权维护=====");
		   preclaimPreService.preclaimCommit(id);
		    }
	   
		//查询所有项目信息
	   @RequestMapping(value="/getAllGongShi",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> GetAllGongShi(@RequestParam Map<String, String> queryParam){
	    	logger.info("=====查询所有项目信息=====");
	    	List<Map> gongShiMapping = preclaimPreService.getGongshi(queryParam);
	        return gongShiMapping;
	    }
	   
		/**
		 * 文件上传
		 * @param importFile
		 * @param uriCB
		 */
			@RequestMapping(value = "/uploadFiles", method = RequestMethod.POST)
			 @ResponseStatus(HttpStatus.NO_CONTENT)
		 	public void uploadFiles(@RequestParam(value = "file") MultipartFile importFile,
		 			UriComponentsBuilder uriCB){
				logger.info("================== （上传）================");
				preclaimPreService.uploadFiles(importFile);
		      
		 	}
			
			//通过id查询附件信息
			   @RequestMapping(value="/getFuJian/{id}",method = RequestMethod.GET)
			    @ResponseBody
			    public PageInfoDto  preclaimPreSearch(@RequestParam Map<String, String> queryParam,@PathVariable(value = "id") Long id) {
			    	logger.info("==============通过id查询附件信息=============");
			        PageInfoDto pageInfoDto =preclaimPreService.getFuJian(queryParam, id);
			        return pageInfoDto;  
			    }
			   
			   //索赔预授权维护修改
			   @RequestMapping(value = "/edit/{id}/{type}", method = RequestMethod.PUT)
			    @ResponseBody
			    public ResponseEntity<TtWrForeapprovalDTO> edit(@PathVariable(value = "id") Long id,@PathVariable(value = "type") String type,@RequestBody TtWrForeapprovalDTO dto,UriComponentsBuilder uriCB){
			    	logger.info("===== 索赔预授权维护修改=====");
			    	preclaimPreService.edit(id,dto,type);
			    	MultiValueMap<String, String> headers = new HttpHeaders();
			        headers.set("Location", uriCB.path("/preclaimPre/edit").buildAndExpand().toUriString());
			        return new ResponseEntity<TtWrForeapprovalDTO>(headers, HttpStatus.CREATED);  	
			    }
			   
			 //通过dealer_code获取该公司基本信息 
			   @RequestMapping(value = "/getPreclaimPre", method = RequestMethod.GET)
			   @ResponseBody
			   public  Map getCustomer(){
			   	logger.info("=====通过dealer_code获取该公司基本信息=====");
			   	 Map m=preclaimPreService.getPreclaimPre();
			       return m;
			   }
			   
			   //新增
			   @RequestMapping(value ="/add/{type}",method = RequestMethod.POST)
			    @ResponseBody
			    public ResponseEntity<TtWrForeapprovalDTO> addForeapproval(@PathVariable(value = "type") String type,@RequestBody TtWrForeapprovalDTO ptdto,UriComponentsBuilder uriCB){
				   logger.info("===== 新增=====");
				   preclaimPreService.add(ptdto,type);
			        MultiValueMap<String,String> headers = new HttpHeaders();  
			        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
			    }	
	  
}
