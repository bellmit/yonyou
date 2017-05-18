package com.yonyou.dms.part.controller.basedata;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.part.domains.DTO.basedata.PartItemDTO;
import com.yonyou.dms.part.domains.DTO.basedata.PartMoveStorageDTO;
import com.yonyou.dms.part.service.basedata.PartMoveService;

/**
 * @description 配件移库
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/basedata/partmove")
public class PartMoveController extends BaseController{
	
	@Autowired
	private PartMoveService partMoveService;
	
	/**
	 * @description 根据条件查询移库单号
	 * @param transferNo
	 * @param transferDate
	 */
	@RequestMapping(value="/query/{id}",method = RequestMethod.GET)
	@ResponseBody
	public  PageInfoDto queryPartMoveInfos(@PathVariable(value="id")String transferNo){
		return partMoveService.getPartMoveInfos(transferNo);
	}
	
	/**
	 * @description 根据条件查询移库单号
	 * @param transferNo
	 * @param transferDate
	 */
	@RequestMapping(value="/query",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartMoveInfos(){
		return partMoveService.getPartMoveInfos(null);
	}
	
	/**
	 * @description 根据配件移库工单号查询 单子上的移库配件详细
	 * @param transferNo
	 */
	@RequestMapping(value="/queryPartInfos/{id}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartInfos(@PathVariable(value="id")String transferNo){
		return partMoveService.queryPartInfos(transferNo);
	}
	
	/**
	 * @description 查询配件库存信息
	 * @return
	 */
	@RequestMapping(value="/queryPartItem",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartItem(PartItemDTO partItemDTO){
		return partMoveService.queryPartItem(partItemDTO);
	}
	
	/**
	 * @description 查询配件的替换件
	 * @param partNo
	 * @return
	 */
	@RequestMapping(value="/queryPartReplace/{partNo}",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartReplace(@PathVariable String partNo){
		return partMoveService.queryPartReplace(partNo);
	}
	
	/**
	 * @description 判断旧仓库的配件能否移动到新仓库 
	 * @param oldStorageCode
	 * @param newStorageCode
	 * @return
	 */
	@RequestMapping(value="/checkStorageMove",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> checkStorageMove(String oldStorageCode,String newStorageCode){
		String msg = partMoveService.checkStorageMove(oldStorageCode,newStorageCode);
		Map<String,Object> result = new HashMap<>();
		result.put("isScan", msg);
		return result;
	}
	
	/**
	 * @description 查询配件扩展信息
	 * @param partNo
	 * @return
	 */
	@RequestMapping(value="/queryPartItemInfos/{partNo}/{storageCode}",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartItemInfos(@PathVariable(value="partNo")String partNo,@PathVariable(value="storageCode")String storageCode){
		return partMoveService.queryPartItemInfos(partNo,storageCode);
	}
	
	/**
	 * @description 新增移库配件
	 * @param 
	 * @return
	 */
	@RequestMapping(value="/addPartItemMove",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,String> addPartItemMove(@RequestBody PartMoveStorageDTO partMoveStorageDTO){
		String transferNo = partMoveService.addPartItemMove(partMoveStorageDTO);
		Map<String,String> map = new HashMap<>();
		map.put("result", "success");
		map.put("transferNo", transferNo);
		return map;
	}
	
	/**
	 * @description 作废移库单
	 * @param transferNo
	 * @return
	 */
	@RequestMapping(value="/delPartItemMove/{transferNo}",method=RequestMethod.DELETE)
	@ResponseBody
	public Map<String,String> delPartItemMove(@PathVariable(value="transferNo")String transferNo){
		String result = partMoveService.delPartItemMove(transferNo);
		Map<String,String> map = new HashMap<>();
		map.put("result",result);
		return map;
	}
	
	/**
	 * @description 配件出库
	 * @param transferNo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/partOutStorage/{transferNo}",method=RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> partOutStorag(@PathVariable(value="transferNo")String transferNo) throws Exception{
		return partMoveService.partOutStoragr(transferNo);
	}
	
	/**
	 * @description 查询打印头信息
	 * @param transferNo
	 * @return 
	 */
	@RequestMapping(value="/printPartMoveTitle/{transferNo}",method = RequestMethod.GET)
	@ResponseBody
	public Map printPartMoveTitle(@PathVariable(value="transferNo")String transferNo){
		 Map queryPrintInfo = partMoveService.printPartMoveTitle(transferNo);
		 return queryPrintInfo;
	}
	
	/**
	 * @description 查询打印表格信息
	 * @param transferNo
	 * @return
	 */
	@RequestMapping(value="/printPartMoveInfo/{transferNo}",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> printPartMoveInfo(@PathVariable(value="transferNo")String transferNo){
		return partMoveService.printPartMoveInfo(transferNo);
	}
	
	/**
	 * @description 查询符合条件的配件库存信息,批量查询
	 * @param storageCode
	 * @param stock
	 * @return
	 */
	@RequestMapping(value="/queryPartStockItem/{oldStorageCode}/{newStorageCode}",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryPartStockItem(@PathVariable(value="oldStorageCode")String oldStorageCode,@PathVariable(value="newStorageCode")String newStorageCode){
		return partMoveService.queryPartStockItem(oldStorageCode,newStorageCode);
	}
}
