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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.repair.service.basedata.RepairGroupService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author yangjie
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/group")
@SuppressWarnings("rawtypes")
public class RepairGroupController extends BaseController {

	@Autowired
	private RepairGroupService repairGroupService;
	
	/**
	 * 查询维修组合主表
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/findGroupItem",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findGroupItem(@RequestParam Map<String, String> queryParam){
		return repairGroupService.findGroupItem(queryParam);
	}
	
	/**
	 * 查询维修项目
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/findRepairProject/{packageCode}/{packageName}/{modelLabourCode}",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findRepairProject(@PathVariable String packageCode,@PathVariable String packageName,@PathVariable String modelLabourCode){
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("packageCode", StringUtils.isNullOrEmpty(packageCode)?"":packageCode);
		queryParam.put("packageName", StringUtils.isNullOrEmpty(packageName)?"":packageName);
		queryParam.put("modelLabourCode", "1".equals(modelLabourCode)?"":modelLabourCode);
		return repairGroupService.findRepairProject(queryParam);
	}
	
	/**
	 * 查询维修配件
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/findRepairPart/{packageCode}/{packageName}/{modelLabourCode}",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findRepairPart(@PathVariable String packageCode,@PathVariable String packageName,@PathVariable String modelLabourCode){
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("packageCode", StringUtils.isNullOrEmpty(packageCode)?"":packageCode);
		queryParam.put("packageName", StringUtils.isNullOrEmpty(packageName)?"":packageName);
		queryParam.put("modelLabourCode", "1".equals(modelLabourCode)?"":modelLabourCode);
		return repairGroupService.findRepairPart(queryParam);
	}
}
