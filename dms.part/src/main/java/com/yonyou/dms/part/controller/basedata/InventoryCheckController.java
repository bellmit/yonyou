/**
 * 
 */
package com.yonyou.dms.part.controller.basedata;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.part.domains.DTO.basedata.InventoryCheckDTO;
import com.yonyou.dms.part.domains.DTO.basedata.InventoryItemDTO;
import com.yonyou.dms.part.service.basedata.InventoryCheckService;
import com.yonyou.dms.part.service.basedata.ReportPayOffService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * @author yangjie
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/inventoryCheck")
@SuppressWarnings("rawtypes")
public class InventoryCheckController extends BaseController {

	@Autowired
	private InventoryCheckService inventoryCheckService;
	
	@Autowired
	private ReportPayOffService reportPayOffService;

	/**
	 * 查询盘点单信息
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllInventoryInfo(@RequestParam Map<String, String> map){
		return inventoryCheckService.findAllInventoryInfo(map);
	}
	
	/**
	 * 根据盘点单号查询盘点单明细
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findItemById/{id}",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findAllInventoryItemInfoById(@PathVariable String id){
		return inventoryCheckService.findAllInventoryItemInfoById(id);
	}
	
	/**
	 * 根据盘点单号查询盘点单信息
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/findinventoryFirst/{id}",method = RequestMethod.GET)
	@ResponseBody
	public Map findInventoryFirst(@PathVariable String id){
		return inventoryCheckService.findinventoryFirst(id);
	}
	
	/**
	 * 查询配件信息-新增配件时
	 * @param param
	 * @return
	 */
	@RequestMapping(value="/findPartInfo",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findPartInfo(@RequestParam Map<String, String> param){
		return inventoryCheckService.findPartInfo(param);
	}
	
	/**
	 * 查询授权仓库
	 * @return
	 */
	@RequestMapping(value="/findStorageCode",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findStorageCode(){
		return reportPayOffService.findStorageCode();
	}
	
	/**
	 * 生成空盘点单
	 * @return
	 */
	@RequestMapping(value="/saveBlankInventoryNo",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String saveBlankInventoryNo(){
		return inventoryCheckService.saveBlankInventoryNo();
	}
	
	/**
	 * 保存按钮
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/saveInventoryInfo",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String saveInventoryInfo(@RequestBody InventoryItemDTO dto){
		return inventoryCheckService.saveInventoryInfo(dto);
	}
	
	/**
	 * 新增盘点单
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/addNewInventoryNo",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String addNewInventoryNo(@RequestBody InventoryCheckDTO dto){
		return inventoryCheckService.addNewInventoryNo(dto);
	}
	
	/**
	 * 盘点确认按钮
	 * @param dto
	 * @return
	 */
	@RequestMapping(value="/btnConfirm",method = RequestMethod.POST)
	@ResponseStatus(HttpStatus.CREATED)
	public String btnConfirm(@RequestBody InventoryItemDTO dto){
		return inventoryCheckService.btnConfirm(dto);
	}
	
	/**
	 * 作废按钮
	 * @param dto
	 */
	@RequestMapping(value="/btnDel",method = RequestMethod.DELETE)
	public void btnDel(@RequestBody InventoryItemDTO dto){
		inventoryCheckService.btnDel(dto);
	}
	
	/**
	 * 加锁用户
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateByLocker/{inventoryNo}", method = RequestMethod.POST)
	@ResponseBody
	public Integer updateLocker(@PathVariable(value="inventoryNo") String id){
		int locker = Utility.updateByLocker("tt_part_inventory", FrameworkUtil.getLoginInfo().getUserId().toString(), "INVENTORY_NO", id, "LOCK_USER");
		return locker;
	}
	
	/**
	 * 解锁用户
	 * @param dto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/updateUnLocker/{inventoryNo}", method = RequestMethod.POST)
	@ResponseBody
	public String updateUnLocker(@PathVariable(value="inventoryNo") String id){
		String[] noValue = {id};
		String locker = Utility.updateByUnLock("tt_part_inventory", FrameworkUtil.getLoginInfo().getUserId().toString(), "INVENTORY_NO", noValue , "LOCK_USER");
		return locker;
	}
}
