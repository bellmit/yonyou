
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : InventoryMaTnceController.java
 *
 * @Author : yll
 *
 * @Date : 2016年9月14日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月14日    yll    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */

package com.yonyou.dms.vehicle.controller.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.basedata.InvtrMaTnceDTO;
import com.yonyou.dms.vehicle.service.basedata.InvtrMaTnceService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * InvtrMaTnceController
 * @author yll
 * @date 2016年9月14日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/vsstock")
public class InvtrMaTnceController extends BaseController{
	@Autowired
	InvtrMaTnceService invtrMaTnceService;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 查询
	 * @author xukl
	 * @date 2016年9月14日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value="/vin/items",method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto qryVIN(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = invtrMaTnceService.qryVIN(queryParam);
		return pageInfoDto;
	}

	/**
	 * 
	 * 车辆库存查询
	 * @author yll
	 * @date 2016年9月14日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryMasterData(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = invtrMaTnceService.QueryInvtrMaTnceData(queryParam);
		return pageInfoDto;
	}

	/**
	 * 
	 * 根据Id查询信息
	 * @author yll
	 * @date 2016年9月26日
	 * @param id
	 * @return
	 */
	@RequestMapping(value="/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> findById(@PathVariable Long id){
		
		Map<String,Object> map=invtrMaTnceService.getInvtrMaTnceById(id);//当前操作人为登录人，这里取employeeNo
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String empNO=loginInfo.getEmployeeNo();
		map.put("EMPLOYEE_NO", empNO);
		Integer isLock=(Integer) map.get("IS_LOCK");//是否锁定
		Integer testDrive=(Integer) map.get("IS_TEST_DRIVE");//是否试驾车
		  if(StringUtils.isNullOrEmpty(isLock)){
	        
	        	map.put("IS_LOCK", DictCodeConstants.STATUS_IS_NOT);//初始值为否
	        }
		  if(StringUtils.isNullOrEmpty(testDrive)){
	        	
	        	map.put("IS_TEST_DRIVE", DictCodeConstants.STATUS_IS_NOT);//初始值为否
	        }
		return map;

	}
	/**
	 * 
	 * 整车库存修改
	 * @author yll
	 * @date 2016年10月8日
	 * @param id
	 * @param invtrMaTnceDTO
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.PUT)
	public ResponseEntity<InvtrMaTnceDTO> ModifyInvtrMaTnce(@PathVariable("id") Long id,@RequestBody InvtrMaTnceDTO invtrMaTnceDTO,UriComponentsBuilder uriCB) {
		invtrMaTnceService.modifyInvtrMaTnce(id, invtrMaTnceDTO);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/vsstock/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<InvtrMaTnceDTO>(headers, HttpStatus.CREATED);  
	}
	/**
	 * 
	 * 修改配置
	 * @author yll
	 * @date 2016年10月8日
	 * @param id
	 * @param invtrMaTnceDTO
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editConfiguration/{id}", method = RequestMethod.PUT)
	public ResponseEntity<InvtrMaTnceDTO> ModifyConfiguration(@PathVariable("id") Long id,@RequestBody InvtrMaTnceDTO invtrMaTnceDTO,UriComponentsBuilder uriCB) {
		invtrMaTnceService.modifyConfiguration(id, invtrMaTnceDTO);
		MultiValueMap<String,String> headers = new HttpHeaders();  
		headers.set("Location", uriCB.path("/basedata/vsstock/editConfiguration/{id}").buildAndExpand(id).toUriString());  
		return new ResponseEntity<InvtrMaTnceDTO>(headers, HttpStatus.CREATED);  
	}
	/**
	 * 
	 * 整车库存导出
	 * @author yll
	 * @date 2016年10月8日
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportStockIn(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<Map> resultList =invtrMaTnceService.queryInvtrMaTnceForExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("车辆库存明细", resultList);
		//String[] keys = {"IS_LOCK","IS_TEST_DRIVE","PRODUCT_CODE","PRODUCT_NAME","STORAGE_CODE","VIN","OWN_STOCK_STATUS","DISPATCHED_STATUS","TRAFFIC_MAR_STATUS","BRAND_NAME","SERIES_NAME","MODEL_NAME","CONFIG_NAME","COLOR_NAME","OWNER_NAME","PURCHASE_PRICE","OEM_DIRECTIVE_PRICE","DIRECTIVE_PRICE","WHOLESALE_DIRECTIVE_PRICE","STORAGE_POSITION_CODE","ENTRY_TYPE","FIRST_STOCK_IN_DATE","LATEST_STOCK_IN_DATE","DELIVERY_TYPE","LATEST_STOCK_OUT_DATE","FACTORY_DATE","MANUFACTURE_DATE","ENGINE_NO","KEY_NUMBER","CERTIFICATE_NUMBER","IS_DIRECT","EXHAUST_QUANTITY","DISCHARGE_STANDARD","OEM_TAG","REMARK"};
		//String[] columnNames = { "是否锁定", "是否试驾车","产品代码","产品名称","仓库","VIN" ,"库存状态","配车状态","质损状态","品牌","车系","车型","配置","颜色","采购日期","采购价格","车厂指导价","销售指导价","批售指导价","车位","入库类型","首次入库日期","入库日期","出库类型","出库日期","出厂日期","生产日期","发动机号","钥匙号","合格证号","是否直销","排气量","排放标准","是否OEM","备注"};
		//生成excel 文件
		//excelService.generateExcel(excelData, keys, columnNames, "车辆库存明细.xls", response);

		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("IS_LOCK","是否锁定",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("IS_TEST_DRIVE","是否试驾车",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE","产品代码"));
		exportColumnList.add(new ExcelExportColumn("PRODUCT_NAME","产品名称"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_NAME","仓库"));
		exportColumnList.add(new ExcelExportColumn("VIN","VIN"));
		exportColumnList.add(new ExcelExportColumn("OWN_STOCK_STATUS","库存状态"));
		exportColumnList.add(new ExcelExportColumn("DISPATCHED_STATUS","配车状态"));
		exportColumnList.add(new ExcelExportColumn("TRAFFIC_MAR_STATUS","质损状态",ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("BRAND_NAME","品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME","车系"));
		exportColumnList.add(new ExcelExportColumn("MODEL_NAME","车型"));
		exportColumnList.add(new ExcelExportColumn("CONFIG_NAME","配置"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME","颜色"));
		exportColumnList.add(new ExcelExportColumn("PURCHASE_DATE","采购日期",CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("PURCHASE_PRICE","采购价格"));
		exportColumnList.add(new ExcelExportColumn("OEM_DIRECTIVE_PRICE","车厂指导价"));
		exportColumnList.add(new ExcelExportColumn("DIRECTIVE_PRICE","销售指导价"));
		exportColumnList.add(new ExcelExportColumn("WHOLESALE_DIRECTIVE_PRICE","批售指导价"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE","车位"));
		exportColumnList.add(new ExcelExportColumn("ENTRY_TYPE","入库类型"));
		exportColumnList.add(new ExcelExportColumn("FIRST_STOCK_IN_DATE","首次入库日期",CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("LATEST_STOCK_IN_DATE","入库日期",CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("DELIVERY_TYPE","出库类型"));
		exportColumnList.add(new ExcelExportColumn("LATEST_STOCK_OUT_DATE","出库日期",CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("FACTORY_DATE","出厂日期",CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("MANUFACTURE_DATE","生产日期",CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("ENGINE_NO","发动机号"));
		exportColumnList.add(new ExcelExportColumn("KEY_NUMBER","钥匙号"));
		exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NUMBER","合格证号"));
		exportColumnList.add(new ExcelExportColumn("IS_DIRECT","是否直销"));
		exportColumnList.add(new ExcelExportColumn("EXHAUST_QUANTITY","排气量"));
		exportColumnList.add(new ExcelExportColumn("DISCHARGE_STANDARD","排放标准"));
		exportColumnList.add(new ExcelExportColumn("OEM_TAG","是否OEM"));
		exportColumnList.add(new ExcelExportColumn("REMARK","备注"));
		excelService.generateExcel(excelData, exportColumnList, "车辆库存明细.xls", request, response);
	}

	/**
	 * 
	 * pdi明细保存
	 * @author yll
	 * @date 2016年9月28日
	 * @param id
	 * @param invtrMaTnceDTO
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/pdi/{vin}", method = RequestMethod.PUT)
	public ResponseEntity<InvtrMaTnceDTO> ModifyPdi(@PathVariable("vin") String vin, @RequestBody  InvtrMaTnceDTO invtrMaTnceDTO, UriComponentsBuilder uriCB) {
		invtrMaTnceService.modifyPDI(vin, invtrMaTnceDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/basedata/vsstock/pdi/{vin}").buildAndExpand(vin).toUriString());
		return new ResponseEntity<InvtrMaTnceDTO>(headers, HttpStatus.CREATED);
	}

}
