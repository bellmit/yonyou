/**
 * 
 */
package com.yonyou.dms.part.controller.basedata;

import java.util.HashMap;
import java.util.List;
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
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.part.domains.DTO.basedata.ReportPayOffDTO;
import com.yonyou.dms.part.service.basedata.ReportPayOffService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author yangjie
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/reportPayOff")
@SuppressWarnings("rawtypes")
public class ReportPayOffController extends BaseController {

	@Autowired
	private ReportPayOffService reportPayOffService;
	
	/**
	 * 查询报溢单
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllList(@RequestParam Map<String, String> queryParam){
		return reportPayOffService.findAllList(queryParam);
	}
	
	/**
	 * 根据id查询报溢单
	 * @return
	 */
	@RequestMapping(value="/findById/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map findListByNo(@PathVariable String id){
		//锁定单号
		Utility.updateByLocker("Tt_Part_Profit", FrameworkUtil.getLoginInfo().getUserId().toString(), "PROFIT_NO", id, "LOCK_USER");
		return reportPayOffService.findById(id);
	}
	
	/**
	 * 根据报溢单号查询报溢明细
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findItemByPartProfit/{id}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findItemByPartProfit(@PathVariable String id){
		//锁定单号
				Utility.updateByLocker("Tt_Part_Profit", FrameworkUtil.getLoginInfo().getUserId().toString(), "PROFIT_NO", id, "LOCK_USER");
		return reportPayOffService.findItemByPartProfit(id);
	}
	
	/**
	 * 查询盘点单
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/findInventroy",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllInventroy(@RequestParam Map<String, String> queryParam){
		return reportPayOffService.findAllInventroy(queryParam);
	}
	
	/**
	 * 根据盘点单号查询报溢明细
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findItemByInventroy/{id}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findItemByInventroy(@PathVariable String id){
		//锁定单号
		Utility.updateByLocker("tt_part_inventory", FrameworkUtil.getLoginInfo().getUserId().toString(), "INVENTORY_NO", id, "LOCK_USER");
		return reportPayOffService.findItemByInventroy(id);
	}
	
	/**
	 * 新增时查询配件信息1
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/findPartForAdd",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllPartInfo(@RequestParam Map<String, String> queryParam){
		return reportPayOffService.findAllPartInfo(queryParam);
	}
	
	/**
	 * 新增时查询配件信息2
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/findPartForAddC/{storageCode}/{partNo}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllPartInfoC(@PathVariable String storageCode,@PathVariable String partNo){
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("STORAGE_CODE", storageCode);
		queryParam.put("PART_NO", partNo);
		return reportPayOffService.findAllPartInfoC(queryParam);
	}
	
	/**
	 * 配件车型组下拉框
	 * @return
	 */
	@RequestMapping(value="/findPartModelGroup",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findPartModelGroup(){
		return reportPayOffService.findPartModelGroup();
	}
	
	/**
	 * 授权仓库下拉框
	 * @return
	 */
	@RequestMapping(value="/findStorageCode",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findStorageCode(){
		return reportPayOffService.findStorageCode();
	}
	
	/**
	 * 保存按钮
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/btnSave",method = RequestMethod.POST)
	public ResponseEntity<String> btnSave(@RequestBody ReportPayOffDTO dto, UriComponentsBuilder uriCB){
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/basedata/reportPayOff").buildAndExpand().toUriString());
		return new ResponseEntity<String>(reportPayOffService.btnSave(dto), headers, HttpStatus.CREATED);
	}
	
	/**
	 * 入账按钮
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/btnAccount",method = RequestMethod.POST)
	public ResponseEntity<String> btnAccount(@RequestBody ReportPayOffDTO dto, UriComponentsBuilder uriCB){
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/basedata/reportPayOff").buildAndExpand().toUriString());
		return new ResponseEntity<String>(reportPayOffService.btnAccount(dto), headers, HttpStatus.CREATED);
	}
	
	/**
	 * 作废按钮
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/{partProfitId}",method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void btnDelete(@PathVariable String partProfitId){
		reportPayOffService.btnDelete(partProfitId);
	}
	
	/**
	 * 加锁用户
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateByLocker/{profitNo}", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateLocker(@PathVariable(value="profitNo") String id){
		int locker = Utility.updateByLocker("Tt_Part_Profit", FrameworkUtil.getLoginInfo().getUserId().toString(), "PROFIT_NO", id, "LOCK_USER");
		return locker;
	}
	
	/**
	 * 解锁用户-报溢单
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateUnLocker/{profitNo}", method = RequestMethod.POST)
	@ResponseBody
	public String updateUnLocker(@PathVariable(value="profitNo") String id){
		String[] noValue = {id};
		String locker = Utility.updateByUnLock("Tt_Part_Profit", FrameworkUtil.getLoginInfo().getUserId().toString(), "PROFIT_NO", noValue , "LOCK_USER");
		return locker;
	}
	
	/**
	 * 解锁用户-盘点单
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateUnLockerI/{profitNo}", method = RequestMethod.POST)
	@ResponseBody
	public String updateUnLockerI(@PathVariable(value="profitNo") String id){
		String[] noValue = {id};
		String locker = Utility.updateByUnLock("tt_part_inventory", FrameworkUtil.getLoginInfo().getUserId().toString(), "INVENTORY_NO", noValue , "LOCK_USER");
		return locker;
	}
}
