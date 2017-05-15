package com.yonyou.dms.web.controller.basedata.authority;

import java.util.HashMap;
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

import com.yonyou.dms.common.domains.PO.basedata.TcRolePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.web.domains.DTO.basedata.authority.OemRoleDTO;
import com.yonyou.dms.web.service.basedata.authority.OemRoleService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * oem角色权限维护
 * @author 夏威
 * @date 2017年2月28日
 */
@Controller
@TxnConn
@RequestMapping("/role")
public class RoleController extends BaseController{
	
private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
	
	@Autowired
	private OemRoleService service;
	
	
	/**
	 * 
	 * 角色信息查询
	 * @author 夏威
	 * @date 2017年2月27日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============角色信息查询===============");
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
        TcRolePO po = TcRolePO.findById(id);
        Map<String, Object> map = po.toMap();
        return map;
    }
    /**
     * 获取菜单树
     * @author xiawei
     * @date 2017年3月14日
     * @param id 用户ID
     * @return
     */
    
    @RequestMapping(value = "/menuData", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> menuData(String roleId,String roleType) {
    	logger.info("============获取菜单树===============");
    	Map<String, Object> map = service.getMenuData(roleId,roleType);
    	return map;
    }
    /**
     * 角色代码校验
     * @author xiawei
     * @date 2017年2月27日
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/checkRole/{roleCode}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> checkRole(@PathVariable(value = "roleCode") String roleCode) {
    	logger.info("============角色代码校验===============");
    	Map<String, Object> message = new HashMap<>();
    	service.checkRole(roleCode,message);
    	return message;
    }

    /**
     * 新增角色信息
     * @author xiawei
     * @date 2017年1月20日
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<OemRoleDTO> addRole(@RequestBody @Valid OemRoleDTO roleDto, UriComponentsBuilder uriCB) {
    	logger.info("============新增经销商用户===============");
        Long id = service.addRole(roleDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/role").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(roleDto, headers, HttpStatus.CREATED);

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
    public ResponseEntity<OemRoleDTO> modifyRole(@PathVariable("id") Long id, @RequestBody @Valid OemRoleDTO roleDto,
                                              UriComponentsBuilder uriCB) {
    	logger.info("============经销商用户修改===============");
    	service.modifyRole(id, roleDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/role").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
}
