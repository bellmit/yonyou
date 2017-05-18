package com.yonyou.dms.customer.controller.customerManage;

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
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.customer.domains.DTO.customerManage.TestDriverDTO;
import com.yonyou.dms.customer.service.customerManage.TestDriverService;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 试乘试驾登记反馈
 * @author wangxin
 *
 */
@Controller
@TxnConn
@RequestMapping("/customerManage/testDriver")
public class TestDriverController extends BaseController {
	
	@Autowired
    private TestDriverService testdriverservice;

	/**
	 * 试乘试驾查询
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryTestDriver(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = testdriverservice.queryTestDriver(queryParam);
        return pageInfoDto;
    }
	
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryTestDriverByid(@PathVariable(value = "id") String id) {
    	Map<String, Object> map = testdriverservice.queryTestDriverByid(id);
        return map;
    }
    
    @RequestMapping(value = "/detail/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> queryTestDriverDetailByid(@PathVariable(value = "id") String id) {
    	Map<String, Object> map = testdriverservice.queryTestDriverDetailByid(id);
        return map;
    }
    	
    
    
    @RequestMapping(method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<TestDriverDTO> addTestDriver(@RequestBody @Valid TestDriverDTO testDriverDTO,
                                                           UriComponentsBuilder uriCB) {

        Long id = testdriverservice.addTestDriver(testDriverDTO);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/testDriver/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<TestDriverDTO>(testDriverDTO, headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<TestDriverDTO> updateTestDriver(@PathVariable("id") String id,
                                                                      @RequestBody @Valid TestDriverDTO testDriverDTO,
                                                                      UriComponentsBuilder uriCB) {

    	testdriverservice.updateTestDriver(id, testDriverDTO);
        MultiValueMap<String, String> headers = new HttpHeaders();
        headers.set("Location", uriCB.path("/customerManage/testDriver/{id}").buildAndExpand(id).toUriString());
        return new ResponseEntity<TestDriverDTO>(headers, HttpStatus.CREATED);
    }
    
    @RequestMapping(value = "/query/customerAndIntent" ,method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryCustomerAndIntent(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = testdriverservice.queryCustomerAndIntent(queryParam);
        return pageInfoDto;
    }
    
    
    
}
