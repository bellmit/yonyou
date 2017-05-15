package com.yonyou.dms.web.controller.basedata.authority;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.PO.basedata.TcUserPO;
import com.yonyou.dms.common.domains.PO.basedata.TmCompanyPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.web.domains.DTO.basedata.authority.SysUserDTO;
import com.yonyou.dms.web.service.basedata.authority.DealerUserService;
import com.yonyou.dms.web.service.basedata.authority.SysUserService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * oem经销商用户维护
 * @author 夏威
 * @date 2017年2月27日
 */
@Controller
@TxnConn
@RequestMapping("/dealerUsers")
public class DealerUserController extends BaseController{
	
private static final Logger logger = LoggerFactory.getLogger(DealerUserController.class);
	
	@Autowired
	private DealerUserService service;
	
	@Autowired 
	private SysUserService suService;
	
	
	/**
	 * 
	 * 用户查询
	 * @author 夏威
	 * @date 2017年2月27日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============经销商用户信息查询===============");
		PageInfoDto pageInfoDto = service.queryList(queryParam);
		return pageInfoDto;
	}
	
    
    /**
     * 根据ID 获取经销商用户信息
     * @author xiawei
     * @date 2017年2月27日
     * @param id 用户ID
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getById(@PathVariable(value = "id") Long id) {
    	logger.info("============经销商用户修改查询===============");
    	TcUserPO po = TcUserPO.findById(id);
        Map<String, Object> map = po.toMap();
        map.put("ADD_IDS", suService.getUserPoses(id));
        map.put("COMPANY_NAME", TmCompanyPO.findById(po.getLong("COMPANY_ID")).getString("COMPANY_NAME"));
        return map;
    }
    /**
     * 获取经销商用户职位信息
     * @author xiawei
     * @date 2017年2月27日
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/searchPose", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto getDealerUserPoseList(@RequestParam Map<String, String> queryParam) {
    	logger.info("============经销商职位信息查询===============");
    	PageInfoDto list = service.getPoseList(queryParam);
    	return list;
    }
    /**
     * 经销商用户账号校验
     * @author xiawei
     * @date 2017年2月27日
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/checkUser/{acnt}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> checkDealerUser(@PathVariable(value = "acnt") String acnt) {
    	logger.info("============经销商用户账号校验===============");
    	Map<String, Object> message = new HashMap<>();
    	suService.checkUser(acnt,message);
    	return message;
    }

    /**
     * 新增经销商用户
     * @author xiawei
     * @date 2017年1月20日
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<SysUserDTO> addDealerUser(@RequestBody @Valid SysUserDTO userDto, UriComponentsBuilder uriCB) {
    	logger.info("============新增经销商用户===============");
        Long id = service.addDealerUser(userDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/dealerUsers").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(userDto, headers, HttpStatus.CREATED);

    }

    /**
     * 根据ID 用户信息
     * @author xiawei
     * @date 2017年1月17日
     * @param id
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<SysUserDTO> modifyDealerUser(@PathVariable("id") Long id, @RequestBody @Valid SysUserDTO userDto,
                                              UriComponentsBuilder uriCB) {
    	logger.info("============经销商用户修改===============");
    	service.modifyDealerUser(id, userDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/dealerUsers").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 根据ID 获取经销商用户职位信息
     * @author xiawei
     * @date 2017年1月17日
     * @param id
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/getUserPoses/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getUserPoses(@PathVariable("id") Long id,UriComponentsBuilder uriCB) {
    	logger.info("============经销商用户职位信息查询===============");
    	List<Map> list = suService.getUserPoseList(id);
    	return list;
    }
}
