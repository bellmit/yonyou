/**
 * 
 */
package com.yonyou.dms.repair.controller.basedata;

import java.util.List;
import java.util.Map;

import javax.validation.Valid;

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
import com.yonyou.dms.common.domains.DTO.customer.OwnerDTO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.repair.service.basedata.QueryByLinsenceService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author sqh
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/queryByLinsence")
public class QueryByLinsenceController extends BaseController{

	@Autowired
	private QueryByLinsenceService queryByLinsenceService;
	@Autowired
	private CommonNoService commonNoService;
	
	
	@RequestMapping(value="/license",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryByLinsence(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = queryByLinsenceService.queryByLinsence(queryParam);
		return pageInfoDto;
	}
	
	@SuppressWarnings("unchecked")
	@RequestMapping(value="/license2",method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryByLinsence2(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		@SuppressWarnings("rawtypes")
		List<Map> list = queryByLinsenceService.queryByLinsence2(queryParam);
		if(list.size()>0) return list.get(0);
		else return null;
	}
	
	@SuppressWarnings({ "rawtypes" })
	@RequestMapping(value="/license3",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryByLinsence3(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		List<Map> list = queryByLinsenceService.queryByLinsence2(queryParam);
		if(list.size()>0) return list;
		else return null;
	}
	
	@RequestMapping(value="/owner",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto QueryOwnerByNoOrSpell(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = queryByLinsenceService.QueryOwnerByNoOrSpell(queryParam);
		return pageInfoDto;
	}
	
	@RequestMapping(value="/queryTripleInfoByVin",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryTripleInfoByVin(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = queryByLinsenceService.queryTripleInfoByVin(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 根据vin编号回显查询
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/{vin}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryOwnerById(@PathVariable(value = "vin") String vin) throws ServiceBizException {
		List<Map> result = queryByLinsenceService.queryOwnerNOByVin(vin);
		return result.get(0);
	}
	/**
	 * 查询优惠券
	 * 
	 * @author
	 * @param id
	 * @throws ServiceBizException
	 */
	@RequestMapping(value="/wechat",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto querywechatcardmessageRO(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = queryByLinsenceService.querywechatcardmessageRO(queryParam);
		return pageInfoDto;
	}
	
	/**
	* 通过传入信息车主信息进行新增操作
	* @return 新增车主信息
	 */
	@RequestMapping(value="/addOwner",method = RequestMethod.POST)
	public ResponseEntity<OwnerDTO> addOwner(@RequestBody @Valid OwnerDTO ownerDTO,UriComponentsBuilder uriCB) {
	      String ownerNo = commonNoService.getSystemOrderNo(CommonConstants.OWNER_PREFIX);
	      ownerDTO.setOwnerNo(ownerNo);
	      queryByLinsenceService.addOwner(ownerDTO);
		 MultiValueMap<String, String> headers = new HttpHeaders();
	     headers.set("Location", uriCB.path("/basedata/queryByLinsence/addOwner").buildAndExpand(ownerDTO.getOwnerNo()).toUriString());
	     return new ResponseEntity<OwnerDTO>(ownerDTO, headers, HttpStatus.CREATED);

	}
	
    /**
     * 作废工单
     * 
     * @author sqh
     * @date 2017年5月8日
     * @param queryParam
     * @throws Exception 
     */
    @RequestMapping(value="/deleteRepairOrder",method=RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRepairOrder(@RequestParam Map<String, String> queryParam) throws Exception{
    	queryByLinsenceService.deleteRepairOrder(queryParam);
    }
    
    /**
     * 优惠折扣授权
     * 
     * @author sqh
     * @date 2017年5月10日
     * @param queryParam
     * @throws Exception 
     */
    @RequestMapping(value="/conferDiscountMode",method=RequestMethod.GET)
    @ResponseBody
    public void conferDiscountMode(@RequestParam Map<String, String> queryParam) throws Exception{
    	queryByLinsenceService.conferDiscountMode(queryParam);
    }
    
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/queryRoLabourByRoNO/{id}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> queryRoLabourByRoNO(@PathVariable(value = "id") String id) {
		List<Map> list = queryByLinsenceService.querySalesPartByRoNO(id);
		return list;
	}
    
    @RequestMapping(value = "/CheckActivityOem", method = RequestMethod.GET)
	@ResponseBody
	public int CheckActivityOem(@RequestParam Map<String, String> queryParam) {
		return queryByLinsenceService.CheckActivityOem(queryParam);
	}
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	@RequestMapping(value = "/queryIsRestrict", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryIsRestrict(@RequestParam Map<String, String> queryParam) {
    	List<Map> list = queryByLinsenceService.queryIsRestrict(queryParam);
		return list.get(0);
	}
    
    @SuppressWarnings({ "rawtypes", "unchecked" })
	@RequestMapping(value = "/queryOEMTAG/{vin}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> queryOEMTAG(@PathVariable(value = "vin") String vin) {
		List<Map> list = queryByLinsenceService.queryOEMTAG(vin);
		return list.get(0);
	}
    
   	@RequestMapping(value = "/queryLabourCode", method = RequestMethod.GET)
   	@ResponseBody
   	public String queryLabourCode(@RequestParam Map<String, String> queryParam) {
   		return queryByLinsenceService.queryLabourCode(queryParam);
   	}
   	
   	@RequestMapping(value = "/searchRepairOrder",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchRepairOrder(@RequestParam Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = queryByLinsenceService.searchRepairOrder(queryParam);
		return pageInfoDto;
	}
}
