package com.yonyou.dms.repair.controller.basedata;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.repair.service.basedata.InsuranceTypeService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

@Controller
@TxnConn
@RequestMapping("/basedata/insuranceType")
public class InsuranceTypeController extends BaseController{
	@Autowired
	private InsuranceTypeService insuranceTypeService;

	/*******************************************************品牌*******************************************************/
	
	/**
	 * 根据查询条件返回对应的保险信息
	 * @author sqh
	 * @date 2017年3月23日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryInsuranceType(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = insuranceTypeService.queryInsuranceType(queryParam);
		return pageInfoDto;
	}
}
