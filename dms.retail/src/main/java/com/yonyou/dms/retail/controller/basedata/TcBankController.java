package com.yonyou.dms.retail.controller.basedata;

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

import com.yonyou.dms.common.domains.PO.basedata.TcBankPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.retail.domains.DTO.basedata.TcBankDTO;
import com.yonyou.dms.retail.service.basedata.TcBankService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
/**
 * 合作银行维护
 * @author zhoushijie
 *
 */
@Controller
@TxnConn
@RequestMapping("/TcBankController")
public class TcBankController extends BaseController{

	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	TcBankService tbservice;

	/**
	 * 查询
	 * @param queryParam
	 * @return
	 */
    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) {
    	logger.info("查询合作银行信息");
        PageInfoDto pageInfoDto=tbservice.getQuerySql(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 新增
     * @param tcDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    public ResponseEntity<TcBankDTO> addMaterialGroup(@RequestBody @Valid TcBankDTO tcDto, UriComponentsBuilder uriCB) {
    	logger.info("新增合作银行信息");
        Long id = tbservice.addTcBank(tcDto);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/TcBankController/add/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<TcBankDTO>(tcDto, headers, HttpStatus.CREATED);

    }
    
    /**
     * 修改
     * @param id
     * @param tcDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<TcBankDTO> ModifyTcBank(@PathVariable("id") Long id,@RequestBody TcBankDTO tcDto,UriComponentsBuilder uriCB) {
		logger.info("修改合作银行信息");
    	tbservice.modifyTcBank(id, tcDto);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/TcBankController/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<TcBankDTO>(headers, HttpStatus.CREATED);
	}
    
    /**
     * 下发
     * @param id
     * @param tcDto
     * @param uriCB
     * @return
     */
    @RequestMapping(value = "/sent", method = RequestMethod.GET)
    public ResponseEntity<TcBankDTO> doSendEach(@RequestParam Map<String, String> queryParam,UriComponentsBuilder uriCB) {
		logger.info("合作银行信息下发");
		/**当前登录信息**/
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String dealerCode = loginInfo.getDealerCode();
		String id = queryParam.get("ID");
    	tbservice.doSendEach(Long.valueOf(id),dealerCode);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/TcBankController/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<TcBankDTO>(headers, HttpStatus.CREATED);
	}
    
    /**
     * 根据ID进行查询
     * @param id
     * @return
     */

    @RequestMapping(value="/{id}",method=RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> findById(@PathVariable(value = "id") Long id){
    	TcBankPO model= tbservice.findById(id);
        return model.toMap();
    }
}
