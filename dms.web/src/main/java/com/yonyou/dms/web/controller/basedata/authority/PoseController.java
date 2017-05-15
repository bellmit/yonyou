package com.yonyou.dms.web.controller.basedata.authority;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.manage.domains.DTO.basedata.OrganizationTreeDto;
import com.yonyou.dms.web.domains.DTO.basedata.authority.PoseDTO;
import com.yonyou.dms.web.service.basedata.authority.PoseService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * oem职位权限维护
 * @author 夏威
 * @date 2017年2月28日
 */
@Controller
@TxnConn
@RequestMapping("/pose")
public class PoseController extends BaseController{
	
private static final Logger logger = LoggerFactory.getLogger(PoseController.class);
	
	@Autowired
	private PoseService service;
	
	
	/**
	 * 
	 * 职位信息查询
	 * @author 夏威
	 * @date 2017年2月28日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============职位信息查询===============");
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	loginInfo.setRoleIds("");
		PageInfoDto pageInfoDto = service.queryList(queryParam);
		return pageInfoDto;
	}
	
    
    /**
     * 根据ID 获取职位信息
     * @author xiawei
     * @date 2017年2月28日
     * @param id 用户ID
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getById(@PathVariable(value = "id") Long id) {
    	logger.info("============根据职位ID查询职位信息===============");
        Map<String, Object> map = service.findByPose(id);
        return map;
    }
    /**
     * 根据ID 获取职位信息
     * @author xiawei
     * @date 2017年3月8日
     * @param id 用户ID
     * @return
     */
    
    @RequestMapping(value = "/saveRole/{roleIds}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> saveRoleId(@PathVariable(value = "roleIds") String id) {
    	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    	if(id.equals("0")){
    		loginInfo.setRoleIds("");
    	}else{
    		loginInfo.setRoleIds(id);
    	}
    	Map<String, Object> map = new HashMap<String, Object>();
    	map.put("STATUS", 1);
    	return map;
    }
    /**
     * 获取职位业务范围
     * @author xiawei
     * @date 2017年2月27日
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/seriesList/{poseId}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getSeriesList(@PathVariable(value = "poseId") String id,@RequestParam Map<String, String> queryParam) {
    	logger.info("============获取职位业务范围===============");
    	List<Map> list = service.getSeriesList(id);
    	return list;
    }
    /**
     * 获取角色信息
     * @author xiawei
     * @date 2017年2月27日
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/selectRole", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto selectRole(@RequestParam Map<String, String> queryParam) {
    	logger.info("============角色信息查询===============");
    	PageInfoDto list = service.selectRole(queryParam);
    	return list;
    }
    
    
    /**
     * 
    * 功能菜单树加载
    * @author 夏威
    * @date 2017年3月2日
    * @return
     */
    @RequestMapping(value="/getFuncList", method=RequestMethod.GET)
    @ResponseBody
	public List<OrganizationTreeDto> getFuncList(){
    	logger.info("============功能菜单树加载==================");
    	List<OrganizationTreeDto> orgList = new ArrayList<>(); 
    	List<Map> list = service.getFuncList();
    	for(int i=0;i<list.size();i++){
    			OrganizationTreeDto orgTreeOrg = new OrganizationTreeDto();
    			orgTreeOrg.setId((String)list.get(i).get("FUNC_ID").toString());
    			String parent = "#";
    			if(!StringUtils.isNullOrEmpty(list.get(i).get("PAR_FUNC_ID"))){
    				parent = (String)list.get(i).get("PAR_FUNC_ID").toString();
    			}
    			orgTreeOrg.setParent(parent);
    			orgTreeOrg.setText((String)list.get(i).get("FUNC_NAME").toString());
    			orgList.add(orgTreeOrg);
    	}
        return orgList;
	}
//    /**
//     * 职位代码校验
//     * @author xiawei
//     * @date 2017年3月8日
//     * @param id 用户ID
//     * @return
//     */
//    @RequestMapping(value = "/checkPose/{poseCode}", method = RequestMethod.GET)
//    @ResponseBody
//    public Map<String, Object> checkPose(@PathVariable(value = "poseCode") String poseCode) {
//    	logger.info("============用户职位代码校验===============");
//    	Map<String, Object> message = new HashMap<>();
//    	service.checkPose(poseCode,message);
//    	return message;
//    }
    
    /**
     * 职位代码校验
     * @author xiawei
     * @date 2017年3月8日
     * @param id 用户ID
     * @return
     */
    @RequestMapping(value = "/checkPose", method = RequestMethod.GET)
    @ResponseBody
    public String checkPose(@RequestParam Map<String,String> params) {
    	logger.info("============用户职位代码校验===============");
    	return service.checkPose(params);
    }

    /**
     * 新增职位信息
     * @author xiawei
     * @date 2017年3月8日
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<PoseDTO> addPose(@RequestBody @Valid PoseDTO poseDto, UriComponentsBuilder uriCB) {
    	logger.info("============新增职位信息===============");
        Long id = service.addPose(poseDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/pose").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(poseDto, headers, HttpStatus.CREATED);

    }

    /**
     * 修改职位信息
     * @author xiawei
     * @date 2017年3月10日
     * @param id
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<PoseDTO> modifyPose(@PathVariable("id") Long id, @RequestBody @Valid PoseDTO poseDto,
                                              UriComponentsBuilder uriCB) {
    	logger.info("============修改职位信息===============");
    	service.modifyPose(id, poseDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/pose").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    /**
     * 根据职位ID初始化角色信息
     * @author xiawei
     * @date 2017年1月17日
     * @param id
     * @param userDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    @ResponseBody
    public List<Map> getPoseRoles(@PathVariable("id") Long id,UriComponentsBuilder uriCB) {
    	logger.info("============根据职位ID初始化角色信息===============");
    	List<Map> list = service.getPoseRoles(id);
    	return list;
    }
}
