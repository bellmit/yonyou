/**
 * 
 */
package com.yonyou.dms.manage.controller.basedata;

import java.util.Map;

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

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.manage.domains.DTO.basedata.FormulaDefineDTO;
import com.yonyou.dms.manage.service.basedata.FormulaDefineService;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * @author yangjie
 *
 */
@Controller
@TxnConn
@RequestMapping("/formulaDefine")
@SuppressWarnings("rawtypes")
public class FormulaDefineController {

	@Autowired
	private FormulaDefineService formulaDefineService;

	/**
	 * 查询列表
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/findAll", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllList(@RequestParam Map<String, String> queryParam) {
		return formulaDefineService.findAll(queryParam);
	}

	/**
	 * 新增操作
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ResponseEntity<String> addFormula(@RequestBody FormulaDefineDTO dto, UriComponentsBuilder uriCB) {
		formulaDefineService.addFormulaDefine(dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/formulaDefine/findAll").buildAndExpand("").toUriString());
		return new ResponseEntity<String>(null, headers, HttpStatus.CREATED);
	}

	/**
	 * 修改前显示
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/findById/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map findByItemId(@PathVariable String id) {
		return formulaDefineService.findById(id);
	}

	/**
	 * 修改操作
	 * 
	 * @param dto
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<String> editFormula(@PathVariable String id, @RequestBody FormulaDefineDTO dto,
			UriComponentsBuilder uriCB) {
		formulaDefineService.editFormulaDefine(id, dto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/formulaDefine/findAll").buildAndExpand("").toUriString());
		return new ResponseEntity<String>(null, headers, HttpStatus.CREATED);
	}
	
	/**
	 * 删除操作
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteFormula(@PathVariable String id) {
		formulaDefineService.deleteFormulaById(id);
	}
}
