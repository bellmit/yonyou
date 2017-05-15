package com.yonyou.dms.vehicle.controller.afterSales.basicDataMgr;

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
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainLabourDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainPackageDTO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrMaintainPartDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainLabourPO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrMaintainPartPO;
import com.yonyou.dms.vehicle.service.afterSales.basicDataMgr.MaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * 保养套餐维护
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/maintain")
public class MaintainController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	MaintainService   maintainService;
	
	//得到所有车型
	   @RequestMapping(value="/GetAllCheXing",method = RequestMethod.GET)
	    @ResponseBody
	    public List<Map> GetAllCheXing(@RequestParam Map<String, String> queryParams){
	    	logger.info("=====查询得到所有车型=====");
	    	List<Map> tenantMapping = maintainService.getAllCheXing(queryParams);
	        return tenantMapping;
	    }
	   
	   /**
	    * 查询保养套餐维护
	    */
		@RequestMapping(value="/maintainSearch",method = RequestMethod.GET)
	    @ResponseBody
	    public PageInfoDto MaintainSearch(@RequestParam Map<String, String> queryParam) {
	    	logger.info("============== 查询保养套餐维护=============");
	        PageInfoDto pageInfoDto =maintainService.MaintainQuery(queryParam);
	        return pageInfoDto;  
	    }
		
		/**
		 * 查询所有工时信息
		 */
		   @RequestMapping(value="/getAllGongShi",method = RequestMethod.GET)
		    @ResponseBody
		    public List<Map> GetAllGongShi(@RequestParam Map<String, String> queryParam){
		    	logger.info("=====查询所有工时信息=====");
		    	List<Map> gongShiMapping = maintainService.getGongshi(queryParam);
		        return gongShiMapping;
		    }
		   
			/**
			 * 查询所有零件信息
			 */
			   @RequestMapping(value="/getAllLingJian",method = RequestMethod.GET)
			    @ResponseBody
			    public List<Map> GetAllLingJian(@RequestParam Map<String, String> queryParam){
			    	logger.info("=====查询所有零件信息=====");
			    	List<Map>  LingJianMapping = maintainService.getLingJian(queryParam);
			        return LingJianMapping;
			    }
			   /**
			    * 新增
			    * @param ptdto
			    * @param uriCB
			    * @return
			    */
			   @RequestMapping(value ="/add",method = RequestMethod.POST)
			    @ResponseBody
			    public ResponseEntity<TtWrMaintainPackageDTO> addLabourt(@RequestBody TtWrMaintainPackageDTO ptdto,UriComponentsBuilder uriCB){
			    	logger.info("===== 新增=====");
			    	maintainService.add(ptdto);
			        MultiValueMap<String,String> headers = new HttpHeaders();  
			        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
			    }	
			   /**
			    * 删除保养套餐
			    */
			   @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
			    @ResponseStatus(HttpStatus.NO_CONTENT)
			    public void delete(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
			    	logger.info("=====删除保养套餐=====");
			    	maintainService.delete(id);
			    }
			   /**
			    * 信息回显
			    */
			   @RequestMapping(value = "/getMaintain/{id}", method = RequestMethod.GET)
			    @ResponseBody
			    public Map<String,Object> getMaintainById(@PathVariable(value = "id") Long id){
			    	logger.info("=====保养套餐维护信息回显=====");
			   
			    	Map m3=maintainService.getBaoYang(id);
			    	
			        return m3;
			    }
			   /**
			    * 保养套餐项目信息
			    * @param queryParam
			    * @param id
			    * @return
			    */
			   @RequestMapping(value="/getMaintain2/{id}",method = RequestMethod.GET)
			    @ResponseBody
			    public PageInfoDto getMaintainById2(@RequestParam Map<String, String> queryParam,@PathVariable(value = "id") Long id) {
			    	logger.info("============== 保养套餐项目信息=============");
			        PageInfoDto pageInfoDto =maintainService.getXiangMu(queryParam,id);
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
			        PageInfoDto pageInfoDto =maintainService.getTaoCan(queryParam,id);
			        return pageInfoDto;  
			    }
			   
			   
			   /**
			    *  保养套餐修改
			    */
			   @RequestMapping(value = "/edit/{id}", method = RequestMethod.PUT)
			    @ResponseBody
			    public ResponseEntity<TtWrMaintainPackageDTO> edit(@PathVariable(value = "id") Long id,@RequestBody TtWrMaintainPackageDTO dto,UriComponentsBuilder uriCB){
			    	logger.info("===== 保养套餐修改=====");
			    	maintainService.edit(id,dto);
			    	MultiValueMap<String, String> headers = new HttpHeaders();
			        headers.set("Location", uriCB.path("/maintain/edit").buildAndExpand().toUriString());
			        return new ResponseEntity<TtWrMaintainPackageDTO>(headers, HttpStatus.CREATED);  	
			    }
			   
			   
		/**
			   * 保养套餐项目信息回显
			   * @param id
			   * @return
			   */
			   @RequestMapping(value = "/getMaintainById2/{id}", method = RequestMethod.GET)
			    @ResponseBody
			    public Map<String,Object> getMaintainById2(@PathVariable(value = "id") Long id){
			    	logger.info("=====保养套餐项目信息回显=====");
			    	TtWrMaintainLabourPO  labour=  maintainService.getXiangMu(id);
			    	return labour.toMap();
			    }
			
			  /**
			    * 保养套餐零件列表信息回显
			    * @param id
			    * @return
			    */
			   @RequestMapping(value = "/getMaintainById3/{id}", method = RequestMethod.GET)
			    @ResponseBody
			    public Map<String,Object> getMaintainById3(@PathVariable(value = "id") Long id){
			    	logger.info("=====保养套餐零件列表信息回显=====");
			    	TtWrMaintainPartPO  nPart=  maintainService.getTaoCan(id);
			    	return nPart.toMap();
			    }
			   
			   /**
			    * 删除工时信息
			    * @param id
			    * @param uriCB
			    */
			   @RequestMapping(value = "/gongshi/{id}", method = RequestMethod.DELETE)
			    @ResponseStatus(HttpStatus.NO_CONTENT)
			    public void delete2(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
			    	logger.info("=====删除工时信息=====");
			    	maintainService.delete2(id);
			    }
			   /**
			    * 删除零件信息
			    * @param id
			    * @param uriCB
			    */
			   @RequestMapping(value = "/lingJian/{id}", method = RequestMethod.DELETE)
			    @ResponseStatus(HttpStatus.NO_CONTENT)
			    public void delete3(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
			    	logger.info("=====删除零件信息=====");
			    	maintainService.delete3(id);
			    }
			   /**
			    * 修改工时信息
			    */
			   @RequestMapping(value = "/editLabour/{id}", method = RequestMethod.PUT)
			    @ResponseBody
			    public ResponseEntity<TtWrMaintainLabourDTO> editLabour(@PathVariable(value = "id") Long id,@RequestBody TtWrMaintainLabourDTO dto,UriComponentsBuilder uriCB){
			    	logger.info("===== 修改工时信息=====");
			    	maintainService.editLabour(id,dto);
			    	MultiValueMap<String, String> headers = new HttpHeaders();
			        headers.set("Location", uriCB.path("/maintain/getMaintainById2").buildAndExpand().toUriString());
			        return new ResponseEntity<TtWrMaintainLabourDTO>(headers, HttpStatus.CREATED);  	
			    }
			   
			   /**
			    * 修改零件信息
			    */
			   @RequestMapping(value = "/editPart/{id}", method = RequestMethod.PUT)
			    @ResponseBody
			    public ResponseEntity<TtWrMaintainPartDTO> editPart(@PathVariable(value = "id") Long id,@RequestBody TtWrMaintainPartDTO dto,UriComponentsBuilder uriCB){
			    	logger.info("=====修改零件信息=====");
			    	maintainService.editPart(id,dto);
			    	MultiValueMap<String, String> headers = new HttpHeaders();
			        headers.set("Location", uriCB.path("/maintain/getMaintainById3").buildAndExpand().toUriString());
			        return new ResponseEntity<TtWrMaintainPartDTO>(headers, HttpStatus.CREATED);  	
			    }   
			   
			   /**
			    * 复制
			    */
			   @RequestMapping(value ="/fuZhi/{id}",method = RequestMethod.POST)
			    @ResponseBody
			    public ResponseEntity<TtWrMaintainPackageDTO> addfuZhi(@RequestBody TtWrMaintainPackageDTO ptdto,@PathVariable(value = "id") Long id,UriComponentsBuilder uriCB){
			    	logger.info("===== 复制=====");
			    	maintainService.add2(ptdto,id);
			        MultiValueMap<String,String> headers = new HttpHeaders();  
			        return new ResponseEntity<>(ptdto,headers, HttpStatus.CREATED);  
			    }
			   /**
			    * 下发
			    */
			   @RequestMapping(value = "/xiafa/{id}", method = RequestMethod.DELETE)
			    @ResponseStatus(HttpStatus.NO_CONTENT)
			    public void xiaFa(@PathVariable("id") Long id, UriComponentsBuilder uriCB) {
			    	logger.info("=====下发=====");
			    	maintainService.xiaFa(id);
			    }
			   
}
