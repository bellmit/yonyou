
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.part
 *
 * @File name : SalesPartController.java
 *
 * @Author : jcsi
 *
 * @Date : 2016年8月4日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年8月4日    Administrator    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.part.controller.stockmanage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.commonAS.domains.PO.basedata.PartSalesPo;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartSalesDto;
import com.yonyou.dms.part.domains.DTO.stockmanage.PartSalesItemDto;
import com.yonyou.dms.part.service.basedata.PartStockService;
import com.yonyou.dms.part.service.stockmanage.PartSalesService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 销售出库 
 * @author jcsi
 * @date 2016年8月4日
 */
@Controller
@TxnConn
@RequestMapping("/stockmanage/salesparts")
@SuppressWarnings({ "rawtypes", "unchecked" })
public class PartSalesController extends BaseController{

	@Autowired
	private PartSalesService partSalesService;

	@Autowired
	private CommonNoService commonNoService;

	@Autowired
	private ExcelRead<PartSalesItemDto>      excelReadService;

	@Autowired
	private PartStockService partStockService;
	/**
	 * 查询
	 * @author jcsi
	 * @date 2016年8月4日
	 * @param param
	 */
	@RequestMapping(method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto search(@RequestParam Map<String, String> param){
		return partSalesService.search(param);
	}

	/**
	 * 删除
	 * @author jcsi
	 * @date 2016年8月8日
	 * @param id
	 */
	@RequestMapping(value="{id}",method=RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.CREATED)
	public void deleteById(@PathVariable("id") Long id ){
		partSalesService.deleteById(id);
	}

	/**
	 * 新增
	 * @author jcsi
	 * @date 2016年8月9日
	 * @param salesPartDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Map<String,Object>> addPartAllocateOut(@RequestBody @Valid PartSalesDto salesPartDto,UriComponentsBuilder uriCB){
		PartSalesPo spPo=partSalesService.addSalesAndItem(commonNoService.getSystemOrderNo(CommonConstants.PART_SALES_OUT_PREFIX),salesPartDto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/stockmanage/salesparts/{id}").buildAndExpand(spPo.getLongId()).toUriString());
		return new ResponseEntity<Map<String,Object>>(spPo.toMap(), headers, HttpStatus.CREATED);
	}

	/**
	 * 根据id查询
	 * @author jcsi
	 * @date 2016年8月9日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Map findSalesById(@PathVariable Long id){
		return partSalesService.findSalesById(id);
	}


	/**
	 * 打印销售出库单查询
	 * @author DuPengXin
	 * @date 2016年12月8日
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/{id}/print",method = RequestMethod.GET)
	@ResponseBody
	public Map getPrint(@PathVariable(value = "id") Long id) {
		List<Map> list = partSalesService.getPartSalesPrint(id);
		Map map=list.get(0);
		map.put("printdata", new Date());
		return map;
	}


	/**
	 * 打印销售出库单
	 * @author DuPengXin
	 * @date 2016年12月8日
	 * @param id
	 * @return
	 */

	@RequestMapping(value = "/{id}/salespartprint",method = RequestMethod.GET)
	@ResponseBody
	public List<Map> getPartBuyPrint(@PathVariable(value = "id") Long id) {
		List<Map> list = partSalesService.getPartSalesPrint(id);
		return list;
	}
	/**
	 * 根据主表id查询字表信息
	 * @author jcsi
	 * @date 2016年8月9日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}/salespartitems",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findSalesItemByPartId(@PathVariable Long id){
		return partSalesService.findSalesItemByPartId(id);
	}

	/**
	 * 更新 
	 * @author jcsi
	 * @date 2016年8月9日
	 * @param id
	 * @param spDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value="/{id}/salespartitems",method=RequestMethod.PUT)
	public ResponseEntity<PartSalesDto> updateSalesAndItemById(@PathVariable Long id,@RequestBody @Valid PartSalesDto spDto,UriComponentsBuilder uriCB){
		partSalesService.editSalesAndItem(id, spDto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/stockmanage/salesparts/{id}").buildAndExpand(id).toUriString());
		return new ResponseEntity<PartSalesDto>(spDto,headers, HttpStatus.CREATED);
	}

	/**
	 * 入账 
	 * @author jcsi
	 * @date 2016年8月9日
	 * @param id
	 */
	@RequestMapping(value="/{id}/orderstatus")
	@ResponseBody
	public void updateStatusById(@PathVariable Long id){
		partSalesService.updateStatusById(id);
	}

	/**
	 * 退货明细查询 
	 * @author jcsi
	 * @date 2016年8月12日
	 * @param id
	 */
	@RequestMapping(value="/{id}/salesreturn")
	@ResponseBody
	public PageInfoDto searchSalesReturn(@PathVariable Long id){
		return  partSalesService.searchSalesReturn(id);
	}
	/**
	 * 退货
	 * @author jcsi
	 * @date 2016年8月24日
	 * @param id
	 * @param salesPartDto
	 */
	@RequestMapping(value="/{id}/salesreturnsub")
	@ResponseBody
	public void salesReturnSub(@PathVariable Long id,@RequestBody PartSalesDto salesPartDto){
		partSalesService.salesReturnSub(id, salesPartDto);
	}

	/**
	 * 根据工单号查询销售明细
	 * @author jcsi
	 * @date 2016年8月24日
	 * @param roNo
	 * @return
	 */
	@RequestMapping(value="/{roId}/items",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto searchItemByRoNo(@PathVariable Long roId){
		return partSalesService.searchItemByRoNo(roId);
	}


	/**
	 * 打印工单
	 * @author ZhengHe
	 * @date 2016年11月3日
	 * @param roId
	 * @return
	 */

	@RequestMapping(value="/{roId}/salesItems",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> searchSalesItemByRoId(@PathVariable Long roId){
		return partSalesService.searchSalesItemByRoId(roId);
	}

	/**
	 * 导入
	 * @author jcsi
	 * @date 2016年9月1日
	 * @param file
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public List<Map<String,Object>> ImportSalesItem(@RequestParam MultipartFile file) throws IOException{
		final List<Map<String,Object>> result=new ArrayList<Map<String,Object>>();
		// 解析Excel 表格(如果需要进行回调)
		ImportResultDto<PartSalesItemDto> importResult = excelReadService.analyzeExcelFirstSheet(file,new AbstractExcelReadCallBack<PartSalesItemDto>(PartSalesItemDto.class,new ExcelReadCallBack<PartSalesItemDto>() {
			@Override
			public void readRowCallBack(PartSalesItemDto rowDto, boolean isValidateSucess) {
				try{
					if(partStockService.partNoOrStorageExist(rowDto.getPartNo(),"partno")){
						throw new ServiceBizException("配件代码不存在，导入失败！");
					}
					if(partStockService.partNoOrStorageExist(rowDto.getStorageCode(),"storagecode")){
						throw new ServiceBizException("仓库代码不存在，导入失败！");
					}
					//验证成功
					if(isValidateSucess){
						Map<String,Object> item=new HashMap<String,Object>();
						item.put("isFinishedShow", DictCodeConstants.STATUS_IS_NOT); //是否入账
						item.put("partCodeShow", rowDto.getPartNo());   //配件代码
						item.put("partNameShow", rowDto.getPartName());   //配件名称
						item.put("storageCodeShow", rowDto.getStorageCode());  //仓库名称
						item.put("storagePositionCodeShow", rowDto.getStoragePositionCode());  //库位
						item.put("unitShow", rowDto.getUnit());   //计量单位
						item.put("canNumShow", rowDto.getPartQuantity());  //出库数量
						item.put("salesPriceShow", rowDto.getPartSalesPrice());  //销售单价
						item.put("salesAmountShow", rowDto.getPartSalesAmount());  //销售金额
						//item.put("salesAmountShow", rowDto.getSalesAmount());  //销售金额
						//折扣率为空时，默认为1
						if(StringUtils.isNullOrEmpty(rowDto.getDiscount())){
							item.put("disCountShow", 1);  //折扣率
						}else{
							item.put("disCountShow", rowDto.getDiscount());
						} 
						item.put("priceTieShow", 1);  //价格系数
						item.put("disAmountShow", rowDto.getSalesAmount()); //折后金额
						result.add(item);
					}
				}catch(Exception e){
					throw e;
				}
			}         
		}));

		if(importResult.isSucess()){
			return result;
		}else{
			throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
		}
	}

	/**
	 * 配件销售单查询(费用结算)
	 * @author jcsi
	 * @date 2016年10月13日
	 * @param queryParam
	 * @return
	 */

	@RequestMapping(value="/partSales/balance",method=RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findPartSales(@RequestParam Map<String,String> queryParam){
		return partSalesService.findPartSales(queryParam);
	}

	/**
	 * 根据销售单号查询销售单
	 * @author jcsi
	 * @date 2016年11月16日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/partSalesByNo/balance",method=RequestMethod.GET)
	@ResponseBody
	public List<Map> findBySalesNo(@RequestParam Map<String,String> queryParam){
		return partSalesService.findBySalesNo(queryParam);
	}

}
