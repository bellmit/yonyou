package com.yonyou.dms.customer.controller.bigCustomerManage;

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

import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerPolicySeriesPO;
import com.yonyou.dms.customer.domains.DTO.bigCustomerManage.TtBigCustomerPolicySeriesDTO;
import com.yonyou.dms.customer.service.bigCustomerManage.BigCustomerPolicySeriesService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/bigCustomerPolicySeries")
@ResponseBody
/**
 * 政策车系定义
 * 
 * @author Administrator
 *
 */
public class BigCustomerPolicySeriesController extends BaseController {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	BigCustomerPolicySeriesService policySeriesService;

	/**
	 * 查询政策车系定义信息
	 */
	@RequestMapping(value = "/policySeriesSearch", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryApplyDate(@RequestParam Map<String, String> queryParam) {
		logger.info("==============查询政策车系定义信息=============");
		PageInfoDto pageInfoDto = policySeriesService.policySeriesQuery(queryParam);
		return pageInfoDto;
	}

	/**
	 * 查询所有品牌代码
	 */
	@RequestMapping(value = "/brandsdict", method = RequestMethod.GET)
	public List<Map> brandsSelect() {
		logger.info("==============查询所有品牌代码=============");
		List<Map> brandlist = policySeriesService.queryBrand();
		return brandlist;
	}

	/**
	 * 通过品牌查询车系
	 */
	@TxnConn
	@RequestMapping(value = "/{brandCode}/chexi", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> ChexiQuery(@PathVariable String brandCode, @RequestParam Map<String, String> queryParams) {
		logger.info("=====通过品牌查询车系=====");
		List<Map> brandlist = policySeriesService.queryChexi(brandCode, queryParams);
		return brandlist;
	}

	/**
	 * 通过id删除政策车系数据
	 * 
	 * @param id
	 * @param uriCB
	 * @param ptdto
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteChexi(@PathVariable("id") Long id, UriComponentsBuilder uriCB,
			TtBigCustomerPolicySeriesDTO ptdto) {
		logger.info("=====通过id删除政策车系数据=====");
		policySeriesService.deleteChexiById(id, ptdto);
	}

	/**
	 * 通过id查询车系回显数据
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/getChexi/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> getChexiById(@PathVariable(value = "id") Long id) {
		logger.info("=====通过id查询车系回显数据=====");
		TtBigCustomerPolicySeriesPO ptPo = policySeriesService.getChexiById(id);
		return ptPo.toMap();
	}

	/**
	 * 通过id修改车系数据
	 */
	@RequestMapping(value = "/modify/{id}", method = RequestMethod.PUT)
	@ResponseBody
	public ResponseEntity<TtBigCustomerPolicySeriesDTO> ModifyChexi(@PathVariable(value = "id") Long id,
			@RequestBody TtBigCustomerPolicySeriesDTO ptdto, UriComponentsBuilder uriCB) {
		logger.info("=====通过id修改车系数据=====");
		policySeriesService.modifyChexi(id, ptdto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/modify/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(ptdto, headers, HttpStatus.CREATED);
	}

	/**
	 * 新增车系数据信息
	 * 
	 * @param ptdto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addChexi", method = RequestMethod.POST)
	public ResponseEntity<TtBigCustomerPolicySeriesDTO> addChexi(@RequestBody TtBigCustomerPolicySeriesDTO ptdto,
			UriComponentsBuilder uriCB) {
		Long id = policySeriesService.addChexi(ptdto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/bigCustomerApplyDate/addChexi").buildAndExpand(id).toUriString());
		return new ResponseEntity<>(ptdto, headers, HttpStatus.CREATED);
	}

}
