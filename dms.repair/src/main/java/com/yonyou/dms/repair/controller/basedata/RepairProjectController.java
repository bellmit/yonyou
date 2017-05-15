/**
 * 
 */
package com.yonyou.dms.repair.controller.basedata;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.repair.service.basedata.RepairProjectService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author yangjie
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/repairProject")
@SuppressWarnings("rawtypes")
public class RepairProjectController extends BaseController {

	@Autowired
	private RepairProjectService repairProjectService;
	
	/**
	 * 查询项目车型组下拉框
	 * @return
	 */
	@RequestMapping(value="/findProjectModelList",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findProjectModelList(){
		return repairProjectService.findProjectModelList();
	}
	
	/**
	 * 根据车型组查询车型集合
	 * @return
	 */
	@RequestMapping(value="/findModelForInput",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,String> findModelForInput(){
		return repairProjectService.findModelForInput();
	}
	
	/**
	 * 查询维修项目树状图
	 * @return
	 */
	@RequestMapping(value="/findRepairProjectTree",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findRepairProjectTree(){
		return repairProjectService.findRepairProjectTree();
	}
	
	/**
	 * 查询维修项目列表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/findRepairProjectList",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findRepairProjectList(Map<String, String> queryParam){
		return repairProjectService.findRepairProjectList(queryParam);
	}
	
	/**
	 * 查询维修项目子表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/findRepairProjectItem/{modelLabourCode}/{labourCode}/{isOEM}/{vin}",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findRepairProjectItem(@PathVariable String modelLabourCode,@PathVariable String labourCode,@PathVariable String isOEM,@PathVariable String vin){
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("modelLabourCode", modelLabourCode);
		queryParam.put("labourCode", labourCode);
		queryParam.put("isOEM", isOEM);
		queryParam.put("vin", vin);
		queryParam.put("entityCode", FrameworkUtil.getLoginInfo().getDealerCode());
		return repairProjectService.findRepairProjectItem(queryParam);
	}
}
