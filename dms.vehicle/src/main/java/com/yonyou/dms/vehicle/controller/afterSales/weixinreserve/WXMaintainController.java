package com.yonyou.dms.vehicle.controller.afterSales.weixinreserve;

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

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.weixinreserve.TmWxMaintainPackageDcsDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainLabourPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.weixinreserve.TmWxMaintainPartPO;
import com.yonyou.dms.vehicle.service.afterSales.weixinreserve.WXMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 微信保养套餐维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/wxMaintain")
public class WXMaintainController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	WXMaintainService  wxMaintainService;
	//微信保养套餐维护列表查询
	@RequestMapping(value="/wxMaintainSearch",method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto querybasicCode(@RequestParam Map<String, String> queryParam) {
    	logger.info("==============微信保养套餐维护列表查询=============");
        PageInfoDto pageInfoDto =wxMaintainService.WXMaintainQuery(queryParam);
        return pageInfoDto;  
    }
	
	//查询年款
    @RequestMapping(value="/nianKuan",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getModelYearList(){
    	logger.info("=====查询年款=====");
    	List<Map> nianKuanMapping = wxMaintainService.getModelYearList();
        return nianKuanMapping;
    }
    //通过年款查询车系
    @RequestMapping(value="/chexi/{modelYear}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getSeriesList(@PathVariable(value = "modelYear") String modelYear){
    	logger.info("=====通过年款查询车系=====");
    	List<Map> chexiMapping = wxMaintainService.getSeriesList(modelYear);
        return chexiMapping;
    } 
    //通过年款和车系查询排量信息
    @RequestMapping(value="/paiLiang/{modelYear}/{seriesCode}",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getEngineList(@PathVariable(value = "modelYear") String modelYear,@PathVariable(value = "seriesCode") String seriesCode){
    	logger.info("=====通过年款和车系查询排量信息=====");
    	List<Map> paiLiangMapping = wxMaintainService.getEngineList(modelYear, seriesCode);
        return paiLiangMapping;
    }  
    //微信保养套餐维护
	   @RequestMapping(value = "/wxMaintainDel/{id}", method = RequestMethod.DELETE)
	    @ResponseStatus(HttpStatus.NO_CONTENT)
	    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
	    	logger.info("=====删除微信保养套餐维护信息=====");
	    	wxMaintainService.delete(id);
	    }
	   
	   //得到工时列表信息
	   @RequestMapping(value="/getAllGongShi",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> GetAllGongShi(@RequestParam Map<String, String> queryParam){
	    	logger.info("=====查询所有工时信息=====");
	    	List<Map> gongShiMapping = wxMaintainService.getGongshi(queryParam);
	        return gongShiMapping;
	    }
	   //得到零件列表信息
	   @RequestMapping(value="/getAllLingJian",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> GetAllLingJian(@RequestParam Map<String, String> queryParam){
	    	logger.info("=====查询所有零件信息=====");
	    	List<Map>  LingJianMapping = wxMaintainService.getLingJian(queryParam);
	        return LingJianMapping;
	    }
	   
	   //新增保养套餐维护信息
	   @RequestMapping(value ="/add",method = RequestMethod.POST)
	    @ResponseBody
	    public ResponseEntity<TmWxMaintainPackageDcsDTO> addLabourt(@RequestBody TmWxMaintainPackageDcsDTO ptdto,UriComponentsBuilder uriCB){
		   logger.info("===== 新增=====");
	    	wxMaintainService.add(ptdto);
	        MultiValueMap<String,String> headers = new HttpHeaders();  
	        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
	    }	
	   
	   //套餐信息回显
	   @RequestMapping(value = "/getMaintain/{id}", method = RequestMethod.GET)
	    @ResponseBody
	    public Map<String,Object> getMaintainById(@PathVariable(value = "id") Long id){
	    	logger.info("=====微信保养套餐维护信息回显=====");
	   
	    	Map m3=wxMaintainService.getBaoYang(id);
	    	
	        return m3;
	    }
	   /**
	    *微信 保养套餐项目信息
	    * @param queryParam
	    * @param id
	    * @return
	    */
	   @RequestMapping(value="/getMaintain2/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto getMaintainById2(@RequestParam Map<String, String> queryParam,@PathVariable(value = "id") Long id) {
	    	logger.info("============== 保养套餐项目信息回显=============");
	        PageInfoDto pageInfoDto =wxMaintainService.getXiangMu(queryParam,id);
	        return pageInfoDto;  
	    }
	   
	   /**
	    *  保养套餐零件列表信息回显
	    * @param queryParam
	    * @param id
	    * @return
	    */
	   @RequestMapping(value="/getMaintain3/{id}",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto getMaintainById3(@RequestParam Map<String, String> queryParam,@PathVariable(value = "id") Long id) {
	    	logger.info("============== 保养套餐零件列表信息回显=============");
	        PageInfoDto pageInfoDto =wxMaintainService.getLingJian(queryParam,id);
	        return pageInfoDto;  
	    }  
	   
	   
		/**
		   * 微信保养套餐项目信息回显
		   * @param id
		   * @return
		   */
		   @RequestMapping(value = "/getMaintainById2/{id}", method = RequestMethod.GET)
		    @ResponseBody
		    public Map<String,Object> getMaintainById2(@PathVariable(value = "id") Long id){
		    	logger.info("=====微信保养套餐项目信息回显=====");
		    	TmWxMaintainLabourPO  labour=  wxMaintainService.getXiangMuById(id);
		    	return labour.toMap();
		    }
		
		  /**
		    * 微信保养套餐零件列表信息回显
		    * @param id
		    * @return
		    */
		   @RequestMapping(value = "/getMaintainById3/{id}", method = RequestMethod.GET)
		    @ResponseBody
		    public Map<String,Object> getMaintainById3(@PathVariable(value = "id") Long id){
		    	logger.info("=====微信保养套餐零件列表信息回显=====");
		    	TmWxMaintainPartPO  nPart=  wxMaintainService.getLingJianById(id);
		    	return nPart.toMap();
		    }
		   /**
		    * 修改微信保养套餐维护信息
		    */
		   @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
		    @ResponseBody
		    public ResponseEntity<TmWxMaintainPackageDcsDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TmWxMaintainPackageDcsDTO dto,UriComponentsBuilder uriCB){
		    	logger.info("===== 修改微信保养套餐维护信息=====");
		    	wxMaintainService.edit(id,dto);
		    	MultiValueMap<String, String> headers = new HttpHeaders();
		        headers.set("Location", uriCB.path("/wxMaintain/edit").buildAndExpand().toUriString());
		        return new ResponseEntity<TmWxMaintainPackageDcsDTO>(headers, HttpStatus.CREATED);  	
		    }
		   /**
		    * 复制微信保养套餐信息
		    */
		   @RequestMapping(value ="/fuZhi/{id}",method = RequestMethod.POST)
		    @ResponseBody
		    public ResponseEntity<TmWxMaintainPackageDcsDTO> addfuZhi(@RequestBody TmWxMaintainPackageDcsDTO ptdto,@PathVariable(value = "id") Long id,UriComponentsBuilder uriCB){
		    	logger.info("===== 复制=====");
		    	wxMaintainService.add2(ptdto,id);
		        MultiValueMap<String,String> headers = new HttpHeaders();  
		        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
		    }
		   
		   
	
}
