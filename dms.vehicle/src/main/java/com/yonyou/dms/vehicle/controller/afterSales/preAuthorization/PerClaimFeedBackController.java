package com.yonyou.dms.vehicle.controller.afterSales.preAuthorization;

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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalFeedbackDTO;
import com.yonyou.dms.vehicle.service.afterSales.preAuthorization.PerClaimFeedBackService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 索赔预授权反馈
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/perClaimFeedBack")
public class PerClaimFeedBackController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	PerClaimFeedBackService  perClaimFeedBackService;
	 /**
	  * 查询索陪预授权反馈
	  * @param queryParam
	  * @return
	  */
	@RequestMapping(value="/perClaimFeedBackSearch",method = RequestMethod.GET)
    @ResponseBody
   public PageInfoDto perClaimFeedSearch(@RequestParam Map<String, String> queryParam) {
   	logger.info("============== 查询索陪预授权反馈=============");
       PageInfoDto pageInfoDto =perClaimFeedBackService.PerClaimFeedBack(queryParam);
       return pageInfoDto;  
   }
	/**
	 * 通过id查询基本信息
	 * @param id
	 * @return
	 */
	  @RequestMapping(value = "/getJiBenXinXi/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> getCustomerById(@PathVariable(value = "id") Long id){
	    	logger.info("=====通过id查询基本信息=====");
	    	Map m=perClaimFeedBackService.getPerClaimFeed(id);
	        return m;
	    }
	  /**
	   * 项目信息查询
	   */
		@RequestMapping(value="/XiangMuXinXiSearch/{id}",method = RequestMethod.GET)
	    @ResponseBody
	   public PageInfoDto XiangMuXinXiSearch(@RequestParam Map<String, String> queryParam,@PathVariable(value = "id") Long id) {
	   	logger.info("============== 项目信息查询=============");
	       PageInfoDto pageInfoDto =perClaimFeedBackService.XiangMuXinXi(queryParam, id);
	       return pageInfoDto;  
	   }
		/**
		 * 添加反馈信息
		 */
		@RequestMapping(value ="/addFanKui",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TtWrForeapprovalFeedbackDTO> addBasicParams(@RequestBody TtWrForeapprovalFeedbackDTO ptdto,UriComponentsBuilder uriCB){
	    	logger.info("=====  添加反馈信息=====");
	    	Long id =perClaimFeedBackService.addFanKui(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        headers.set("Location", uriCB.path("/perClaimFeedBack/addFanKui").buildAndExpand(id).toUriString());  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }
		
		/**
		 * 通过id查询附件信息
		 */
		  @RequestMapping(value = "/getFuJian/{id}", method = RequestMethod.GET)
		    @ResponseBody
		    public Map<String,Object> getFuJianById(@PathVariable(value = "id") Long id){
		    	logger.info("=====通过id查询附件信息=====");
		    	Map m=perClaimFeedBackService.getFuJian(id);
		        return m;
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
				perClaimFeedBackService.uploadFiles(importFile);
		      
		 	}
			/**
			 * 查询反馈信息
			 */
		    
			  @RequestMapping(value = "/getFeedBackById/{id}", method = RequestMethod.GET)
			    @ResponseBody
			    public Map<String,Object> getOtherFeeById(@PathVariable(value = "id") Long id){
			    	logger.info("=====查询反馈信息=====");
			    	Map m=perClaimFeedBackService.getFunKui(id);
			        return m;
			    }
			
}
