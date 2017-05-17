package com.yonyou.dms.vehicle.controller.basicManage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;
import javax.xml.rpc.ServiceException;

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

import com.yonyou.dms.DTO.gacfca.CLDMS009Dto;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.gacfca.CLDMS009Coud;
import com.yonyou.dms.vehicle.controller.materialManager.MaterialController;
import com.yonyou.dms.vehicle.dao.basicManage.CompeteModelMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.basicManage.TmCompeteBrandDTO;
import com.yonyou.dms.vehicle.service.basicManage.CompeteModelMaintainService;
import com.yonyou.f4.mvc.annotation.TxnConn;

@Controller
@TxnConn
@RequestMapping("/competeModelMaintain")
public class CompeteModelMaintainController {
	
	private static final Logger logger = LoggerFactory.getLogger(MaterialController.class);
	private  Map<Long, List<String>> errors = new HashMap<Long, List<String>>();
	
	@Autowired
	private CompeteModelMaintainService service;
	
	@Autowired
	private CompeteModelMaintainDao dao;
	
	@Autowired
	private CLDMS009Coud cldms009;
	
	/**
	 * 竞品品牌查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryList(@RequestParam Map<String, String> queryParam) {
		logger.info("============竞品品牌查询===============");
		PageInfoDto pageInfoDto = service.queryList(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 竞品品牌新增
	 * @param mgDto
	 * @param uriCB
	 * @return
	 */
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TmCompeteBrandDTO> addCompeteModelMaintain(@RequestBody @Valid TmCompeteBrandDTO mgDto, UriComponentsBuilder uriCB) {
    	logger.info("============竞品品牌新增===============");
    	/**
		 * 当前登录信息
		 */
        Long id = service.addCompeteModelMaintain(mgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/users/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(mgDto, headers, HttpStatus.CREATED);

    }
    

	/**
     * 根据ID 获取品牌信息
     * @param id BRAND_ID
     * @return
     */

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> findCompeteModelMaintainDetail(@PathVariable(value = "id") Long id) {
    	logger.info("============根据ID  获取品牌信息===============");
    	Map<String, Object> map = service.queryDetail(id);
        return map;
    }
    
    /**
     * 竞品品牌修改
     * @param id
     * @param mgDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public ResponseEntity<TmCompeteBrandDTO> ModifyCompeteModelMaintain(@PathVariable("id") Long id, @RequestBody @Valid TmCompeteBrandDTO mgDto,
                                              UriComponentsBuilder uriCB) {
    	logger.info("============竞品品牌修改===============");
    	service.ModifyCompeteModelMaintain(id, mgDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/users/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<>(headers, HttpStatus.CREATED);
    }
    
    /**
	 * 竞品品牌下发
	 */
    @RequestMapping(value = "/sent", method = RequestMethod.PUT)
	public ResponseEntity<CLDMS009Dto> sentModCompeteInfo(UriComponentsBuilder uriCB) {
		/**  当前登录信息 **/
	    LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		try {
			LinkedList<CLDMS009Dto> dcl = assembleDealerCode();
			int msg = cldms009.getCLDMS009(dcl,loginInfo.getDealerCode());
			if(msg==0){
				throw new ServiceException("贴息利率信息下发失败！");
			}
			Long id = loginInfo.getDealerId();
			MultiValueMap<String, String> headers = new HttpHeaders();
	        headers.set("Location", uriCB.path("/users/{id}").buildAndExpand(id).toUriString());
	        return new ResponseEntity<>(headers, HttpStatus.CREATED);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
    /**
	* @Title: assembleDealerCode 
	* @Description:  
	* @return List<String> 经销商代码列表
	* @throws
	 */
	private LinkedList<CLDMS009Dto> assembleDealerCode() throws Exception {
		List<Map<String, Object>> dealerList = dao.getSentDealerList();
		LinkedList<CLDMS009Dto> dcls = new LinkedList<>();
		CLDMS009Dto dcl = new CLDMS009Dto();
		for(int i=0;i<dealerList.size();i++){
			String dealerCode = (String) dealerList.get(i).get("DEALER_CODE");
			dcl.setDealerCode(dealerCode);
			dcls.add(dcl);
		}
		return dcls;
	}
	
	/**
	 * 
	* @Title: recordError 
	* @Description: TODO(记录错误的发送失败的经销商) 
	* @param @param dealerCodes    设定文件 
	* @return void    返回类型 
	* @throws
	 */
	@SuppressWarnings("unused")
	private void recordError(List<String> dealerCodes) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<String> err = errors.get(loginInfo.getUserId());
		if (null != err && err.size() > 0) {
			err.clear();//清空错误记录
		}
		errors.put(loginInfo.getUserId(), dealerCodes);
	}
}
