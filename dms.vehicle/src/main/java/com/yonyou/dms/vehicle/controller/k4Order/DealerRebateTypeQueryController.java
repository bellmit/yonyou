package com.yonyou.dms.vehicle.controller.k4Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.javalite.activejdbc.LazyList;
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
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4Order.DealerRebateTypeQueryDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.TtVsRebateTypeDTO;
import com.yonyou.dms.vehicle.domains.PO.k4Order.TtVsRebateTypePO;
import com.yonyou.f4.mvc.annotation.TxnConn;

/**
 * @author liujiming
 * @date 2017年3月16日
 */
@Controller
@TxnConn
@RequestMapping("/dealerRebateType")
public class DealerRebateTypeQueryController {

	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DealerRebateTypeQueryController.class);

	@Autowired
	private DealerRebateTypeQueryDao dlrRebTypeDao;

	/**
	 * 返利类型 查询
	 * 
	 * @param queryParam
	 *            查询条件
	 * @return pageInfoDto 查询结果
	 */
	@RequestMapping(value = "/rebateType/Query", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto dealerRebateTypeQuery(@RequestParam Map<String, String> queryParam) {
		// System.out.println("queryInitController");
		logger.info("============经销商返利管理>返利类型维护06==============");
		PageInfoDto pageInfoDto = dlrRebTypeDao.getDealerRebateTypeQueryList(queryParam);

		return pageInfoDto;
	}

	/**
	 * 返利使用明细 查询By CODE_ID
	 * 
	 * @param queryParam
	 *            查询条件
	 * @return pageInfoDto 查询结果
	 */
	@RequestMapping(value = "/rebateType/{CODE_ID}", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> dealerRebateTypeQueryByCodeId(@PathVariable(value = "CODE_ID") Long codeId) {
		// System.out.println("queryInitController");
		logger.info("============经销商返利管理>返利类型维护  编辑06==============");
		Map<String, Object> map = new HashMap<String, Object>();
		TtVsRebateTypePO po = TtVsRebateTypePO.findFirst(" CODE_ID = ?", codeId);
		map.put("CODE_ID", po.getString("CODE_ID"));
		map.put("CODE_DESC", po.getString("CODE_DESC"));
		map.put("STATUS", po.getString("STATUS"));
		return map;
	}

	/**
	 * 返利类型修改
	 * 
	 * @date 2017年3月16日
	 * @return
	 */
	@RequestMapping(value = "/updateType", method = RequestMethod.PUT)
	public ResponseEntity<TtVsRebateTypeDTO> updateRebateType(@RequestBody @Valid TtVsRebateTypeDTO tvrtDto,
			UriComponentsBuilder uriCB) {
		logger.info("============返利类型修改 06===============");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		LazyList<TtVsRebateTypePO> list = TtVsRebateTypePO.find("Code_Desc=?", tvrtDto.getCodeDesc().trim());
		if (list.size() == 0) {
			TtVsRebateTypePO po = TtVsRebateTypePO.findFirst(" CODE_ID = ?", tvrtDto.getCodeId());
			LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);

			String rebateCode = dlrRebTypeDao.oemMaxRebateCodeQuery();
			po.set("CODE_DESC", tvrtDto.getCodeDesc());
			po.set("STATUS", tvrtDto.getStatus());
			po.setInteger("UPDATE_BY", loginInfo.getUserId());
			po.setTimestamp("UPDATE_DATE", format);
			po.setString("TYPE_CODE", rebateCode);
			po.saveIt();
		} else {
			for (TtVsRebateTypePO po : list) {
				po.get("Code_Desc");
				TtVsRebateTypePO.update("UPDATE_DATE=?,Code_Desc=?", "Code_Desc=?", format, po.get("Code_Desc"),
						po.get("Code_Desc"));
				// throw new ServiceBizException("返利类型" + po.get("Code_Desc") +
				// "已存在");
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/dealerRebateType/addType").buildAndExpand().toUriString());
		return new ResponseEntity<TtVsRebateTypeDTO>(headers, HttpStatus.CREATED);
	}

	/**
	 * 返利类型新增
	 * 
	 * @date 2017年3月16日
	 * @return
	 */
	@RequestMapping(value = "/addType", method = RequestMethod.POST)
	public ResponseEntity<TtVsRebateTypeDTO> addRebateType(@RequestBody @Valid TtVsRebateTypeDTO tvrtDto,
			UriComponentsBuilder uriCB) {
		logger.info("============返利类型新增 06===============");

		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		String rebateCode = dlrRebTypeDao.oemMaxRebateCodeQuery();
		LazyList<TtVsRebateTypePO> list = TtVsRebateTypePO.find("Code_Desc=?", tvrtDto.getCodeDesc().trim());
		if (list.size() == 0) {
			TtVsRebateTypePO po = new TtVsRebateTypePO();
			po.set("CODE_DESC", tvrtDto.getCodeDesc());
			po.setInteger("CREATE_BY", loginInfo.getUserId());
			po.setTimestamp("CREATE_DATE", format);
			po.setString("TYPE_CODE", rebateCode);
			po.setInteger("STATUS", tvrtDto.getStatus());
			po.saveIt();
		} else {
			for (TtVsRebateTypePO po : list) {
				po.get("Code_Desc");
				throw new ServiceBizException("返利类型" + po.get("Code_Desc") + "已存在");
			}
		}
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/dealerRebateType/addType").buildAndExpand().toUriString());
		return new ResponseEntity<TtVsRebateTypeDTO>(headers, HttpStatus.CREATED);

	}

	/**
	 * 返利类下拉选 查询
	 * 
	 * @param queryParam
	 *            查询条件
	 * @return tenantMapping 查询结果
	 */
	@RequestMapping(value = "/rebateType/selectList", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getRebateTypeList(@RequestParam Map<String, String> queryParam) {
		logger.info("=====返利类型加载=====");
		List<Map> tenantMapping = dlrRebTypeDao.getDealerRebateTypeList(queryParam);
		return tenantMapping;
	}
}
