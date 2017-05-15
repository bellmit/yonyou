
/** 
 *Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @Project Name : dms.vehicle
 *
 * @File name : StockInController.java
 *
 * @Author : yll
 *
 * @Date : 2016年9月18日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2016年9月18日    yll    1.0
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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.CommonNoService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.basedata.InspectionAboutDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInDTO;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.StockInImportDTO;
import com.yonyou.dms.vehicle.service.basedata.StockInService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 整车入库控制类
 * 
 * @author yll
 * @date 2016年9月18日
 */
@Controller
@TxnConn
@RequestMapping("/vehicleStock/stockIn")
@SuppressWarnings("rawtypes")
public class StockInController extends BaseController {

	@Autowired
	private StockInService stockInService;
	@Autowired
	private ExcelGenerator excelService;
	@Autowired
	private ExcelRead<StockInImportDTO> excelReadService;
	@Autowired
	private CommonNoService commonNoService;

	/**
	 * 整车入库查询 TODO 整车入库查询
	 * 
	 * @author yangjie
	 * @date 2017年1月5日
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto queryStockIn(@RequestParam Map<String, String> queryParam) {
		PageInfoDto pageInfoDto = stockInService.QueryStockIn(queryParam);
		return pageInfoDto;
	}

	/**
	 * 根据SE_NO查询数据 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param sNo
	 * @return
	 */
	@RequestMapping(value = "/showList/{sNo}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findById(@PathVariable String sNo) {
		String no = sNo.substring(4, sNo.length());
		if (!StringUtils.isNullOrEmpty(no)) {
			String[] split = no.split(":");
			Map<String, String> queryParam = new HashMap<String, String>();
			queryParam.put("sNo", split[0]);
			if (split.length > 1) {
				queryParam.put("inType", split[1]);
				queryParam.put("createdBy", split[2]);
			}
			List<Map> pageInfoDto = stockInService.queryStockInDetails(queryParam);
			return pageInfoDto;
		}
		return null;
	}

	/**
	 * 车辆入库导出
	 * 
	 * @author yll
	 * @date 2016年9月20日
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel/{sNo}", method = RequestMethod.GET)
	public void exportStockIn(@PathVariable String sNo, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Map<String, String> queryParam = new HashMap<String, String>();
		queryParam.put("sNo", sNo);
		List<Map> resultList = stockInService.queryStockInDetails(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("车辆入库明细", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("IS_FINISHED", "是否入账", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("INSPECTION_RESULT", "验收结果", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE", "产品代码"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_CODE", "仓库"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE", "库位"));
		exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
		exportColumnList.add(new ExcelExportColumn("KEY_NUMBER", "钥匙编号"));
		exportColumnList.add(new ExcelExportColumn("PO_NO", "采购订单编号"));
		exportColumnList.add(new ExcelExportColumn("PURCHASE_PRICE", "采购价格"));
		exportColumnList.add(new ExcelExportColumn("VS_PURCHASE_DATE", "采购日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("ADDITIONAL_COST", "附加成本"));
		exportColumnList.add(new ExcelExportColumn("DELIVERYMAN_NAME", "送车人姓名"));
		exportColumnList.add(new ExcelExportColumn("DELIVERYMAN_PHONE", "送车人电话"));
		exportColumnList.add(new ExcelExportColumn("SHIPPER_NAME", "承运商名称"));
		exportColumnList.add(new ExcelExportColumn("SHIPPER_LICENSE", "承运车牌号"));
		exportColumnList.add(new ExcelExportColumn("SHIPPING_ADDRESS", "收货地址"));
		exportColumnList.add(new ExcelExportColumn("SHIPPING_ORDER_NO", "发货单号"));
		exportColumnList.add(new ExcelExportColumn("VENDOR_CODE", "供应单位代码"));
		exportColumnList.add(new ExcelExportColumn("VENDOR_NAME", "供应单位名称"));
		exportColumnList.add(new ExcelExportColumn("INSPECTOR", "验收人员"));
		exportColumnList.add(new ExcelExportColumn("MAR_STATUS", "质损状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CONSIGNER_CODE", "委托单位代码"));
		exportColumnList.add(new ExcelExportColumn("CONSIGNER_NAME", "委托单位名称"));
		exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NUMBER", "合格证号"));
		exportColumnList.add(new ExcelExportColumn("HAS_CERTIFICATE", "是否有合格证", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("MANUFACTURE_DATE", "生产日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("SHIPPING_DATE", "发车日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("FACTORY_DATE", "出厂日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("INSPECTION_DATE", "验收日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("ARRIVING_DATE", "预计送到日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("OEM_TAG", "OEM标志", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("ARRIVED_DATE", "实际送到日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		exportColumnList.add(new ExcelExportColumn("PRODUCTING_AREA", "产地"));
		exportColumnList.add(new ExcelExportColumn("EXHAUST_QUANTITY", "排气量"));
		exportColumnList.add(new ExcelExportColumn("VSN", "VSN"));
		exportColumnList.add(new ExcelExportColumn("DISCHARGE_STANDARD", "排放标准"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		excelService.generateExcelForDms(excelData, exportColumnList,
				"车辆入库明细_" + FrameworkUtil.getLoginInfo().getDealerName() + ".xls", request, response);
	}
	
	/**
	 * 车辆入库导导入模板
	 * 
	 * @author yll
	 * @date 2016年9月20日
	 * @param queryParam
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	@RequestMapping(value = "/export/excel/exportStockInModel", method = RequestMethod.GET)
	public void exportStockInModel(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		List<Map> resultList = new ArrayList<Map>();
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("车辆入库明细", resultList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("IS_FINISHED", "是否入账", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("INSPECTION_RESULT", "验收结果", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE", "产品代码"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_CODE", "仓库"));
		exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE", "库位"));
		exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
		exportColumnList.add(new ExcelExportColumn("KEY_NUMBER", "钥匙编号"));
		exportColumnList.add(new ExcelExportColumn("PO_NO", "采购订单编号"));
		exportColumnList.add(new ExcelExportColumn("PURCHASE_PRICE", "采购价格"));
		exportColumnList.add(new ExcelExportColumn("VS_PURCHASE_DATE", "采购日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("ADDITIONAL_COST", "附加成本"));
		exportColumnList.add(new ExcelExportColumn("DELIVERYMAN_NAME", "送车人姓名"));
		exportColumnList.add(new ExcelExportColumn("DELIVERYMAN_PHONE", "送车人电话"));
		exportColumnList.add(new ExcelExportColumn("SHIPPER_NAME", "承运商名称"));
		exportColumnList.add(new ExcelExportColumn("SHIPPER_LICENSE", "承运车牌号"));
		exportColumnList.add(new ExcelExportColumn("SHIPPING_ADDRESS", "收货地址"));
		exportColumnList.add(new ExcelExportColumn("SHIPPING_ORDER_NO", "发货单号"));
		exportColumnList.add(new ExcelExportColumn("VENDOR_CODE", "供应单位代码"));
		exportColumnList.add(new ExcelExportColumn("VENDOR_NAME", "供应单位名称"));
		exportColumnList.add(new ExcelExportColumn("INSPECTOR", "验收人员"));
		exportColumnList.add(new ExcelExportColumn("MAR_STATUS", "质损状态", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("CONSIGNER_CODE", "委托单位代码"));
		exportColumnList.add(new ExcelExportColumn("CONSIGNER_NAME", "委托单位名称"));
		exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NUMBER", "合格证号"));
		exportColumnList.add(new ExcelExportColumn("HAS_CERTIFICATE", "是否有合格证", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("MANUFACTURE_DATE", "生产日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("SHIPPING_DATE", "发车日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("FACTORY_DATE", "出厂日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("INSPECTION_DATE", "验收日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("ARRIVING_DATE", "预计送到日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("OEM_TAG", "OEM标志", ExcelDataType.Dict));
		exportColumnList.add(new ExcelExportColumn("ARRIVED_DATE", "实际送到日期", CommonConstants.SIMPLE_DATE_FORMAT));
		exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
		exportColumnList.add(new ExcelExportColumn("PRODUCTING_AREA", "产地"));
		exportColumnList.add(new ExcelExportColumn("EXHAUST_QUANTITY", "排气量"));
		exportColumnList.add(new ExcelExportColumn("VSN", "VSN"));
		exportColumnList.add(new ExcelExportColumn("DISCHARGE_STANDARD", "排放标准"));
		exportColumnList.add(new ExcelExportColumn("MODEL_YEAR", "年款"));
		excelService.generateExcel(excelData, exportColumnList,
				"车辆入库明细_" + FrameworkUtil.getLoginInfo().getDealerName() + ".xls", request, response);
	}

	/**
	 * 上传文件
	 * 
	 * @throws Exception
	 **/
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public List<StockInImportDTO> importList(@RequestParam(value = "file") MultipartFile importFile,
			UriComponentsBuilder uriCB) throws Exception {
		final String sd_no = commonNoService.getSystemOrderNo(CommonConstants.SD_NO);
		final List<Map> list = stockInService.findStorageCode();// 查询仓库代码,仓库名称
		// 解析Excel 表格(如果需要进行回调)
		ImportResultDto<StockInImportDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
				new AbstractExcelReadCallBack<StockInImportDTO>(StockInImportDTO.class,
						new ExcelReadCallBack<StockInImportDTO>() {
							@Override
							public void readRowCallBack(StockInImportDTO rowDto, boolean isValidateSucess) {
								try {
									// 保存入库信息,只有全部是成功的情况下，才执行数据库保存
									if (isValidateSucess) {
										for (Map map : list) {
											if (map.get("STORAGE_NAME") != null) {
												if (map.get("STORAGE_NAME").toString().trim()
														.equals(rowDto.getStorage_code().trim())) {
													rowDto.setStorage_code(map.get("STORAGE_CODE").toString());
													System.err.println(map.get("STORAGE_CODE").toString() + ":"
															+ rowDto.getStorage_code().trim());
													break;
												}
											}
										}
										rowDto.setSe_no(sd_no);
										stockInService.addInfo(rowDto);
									}
								} catch (Exception e) {
									throw e;
								}
							}
						}));

		if (importResult.isSucess()) {
			return importResult.getDataList();
		} else {
			throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
		}
	}

	/**
	 * 新增入库信息 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月18日
	 * @param stockInDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/addStockIn", method = RequestMethod.POST)
	public ResponseEntity<StockInDTO> addStockIn(@RequestBody StockInDTO stockInDto, UriComponentsBuilder uriCB) {
		StockInDTO save = stockInService.btnSave(stockInDto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/vehicleStock/stockIn").buildAndExpand().toUriString());
		return new ResponseEntity<StockInDTO>(save, headers, HttpStatus.CREATED);
	}

	/**
	 * 修改入库信息 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月20日
	 * @param stockInDto
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/editStockIn", method = RequestMethod.PUT)
	public ResponseEntity<StockInDTO> editStockIn(@RequestBody StockInDTO stockInDto, UriComponentsBuilder uriCB) {
		StockInDTO save = stockInService.btnSave(stockInDto);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/vehicleStock/stockIn").buildAndExpand().toUriString());
		return new ResponseEntity<StockInDTO>(save, headers, HttpStatus.CREATED);
	}

	/**
	 * 查询车辆库存信息 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月17日
	 * @param query
	 * @return
	 */
	@RequestMapping(value = "/findAllVehicleInfo", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllVehicle(@RequestParam Map<String, String> map) {
		Boolean flag = false;
		if ("1".equals(map.get("flag"))) {
			flag = true;
		}
		return stockInService.findAllVehicle(map, flag);
	}
	
	   /**
     * 验收时查询入库信息 TODO description
     * 
     * @author LGQ
     * @date 
     * @param vin seNo
     * @return
     */
    @RequestMapping(value = "/{vin}/And/{seNo}", method = RequestMethod.GET)
    @ResponseBody
    public Map findByVinAndSeNo(@PathVariable(value = "vin") String vin,@PathVariable(value = "seNo") String seNo) {
        System.err.println("asd");
        return stockInService.findByVinAndSeNo(vin,seNo);
    }

	/**
	 * 验收时查询入库信息 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param vin
	 * @return
	 */
	@RequestMapping(value = "/{vin}", method = RequestMethod.GET)
	@ResponseBody
	public Map findByVin(@PathVariable(value = "vin") String vin) {
		System.err.println("asd");
		return stockInService.findStockInInfo(vin);
	}

	/**
	 * 验收操作 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param vin
	 * @param seNo
	 * @param inspectionAboutDTO
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/{SE_NO}/{VIN}", method = RequestMethod.POST)
	public ResponseEntity<InspectionAboutDTO> btnInspection(@PathVariable(value = "VIN") String vin,
			@PathVariable(value = "SE_NO") String seNo, @RequestBody InspectionAboutDTO inspectionAboutDTO,
			UriComponentsBuilder uriCB) {
		System.err.println(inspectionAboutDTO.getSeNo());
		stockInService.addOrEditInpectionInfo(inspectionAboutDTO);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/vehicleStock/stockIn").buildAndExpand().toUriString());
		return new ResponseEntity<InspectionAboutDTO>(new InspectionAboutDTO(), headers, HttpStatus.CREATED);
	}

	/**
	 * 删除操作 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年1月22日
	 * @param id
	 */
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void deleteItem(@PathVariable String id) {
		System.err.println(id);
		stockInService.delInpectionInfo(id);
	}

	/**
	 * 车辆入库 TODO description
	 * 
	 * @author yangjie
	 * @date 2017年2月3日
	 * @param vins
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/btnStockIn/{seNo}/{sheetCreatedBy}/{vin}", method = RequestMethod.POST)
	public ResponseEntity<String> btnStockIn(@PathVariable String seNo, @PathVariable String sheetCreatedBy,
			@PathVariable String vin, UriComponentsBuilder uriCB) {
		Map<String, String> queryParam = new HashMap<String, String>();
		String[] split = vin.split(",");
		String vins = "";
		List<Map> items = new ArrayList<Map>();
		if (split.length > 1) {// 批量
			queryParam.put("sNo", seNo);
			queryParam.put("createdBy", sheetCreatedBy);
			for (int i = 0; i < split.length; i++) {
				if (i == split.length - 1) {
					vins += "'" + split[i] + "'";
				} else {
					vins += "'" + split[i] + "',";
				}
			}
			queryParam.put("vin", vins);
			items = stockInService.queryStockInDetailsForBatch(queryParam);
		} else {// 单个
			if (!StringUtils.isNullOrEmpty(split[0])) {
				queryParam.put("sNo", seNo);
				queryParam.put("createdBy", sheetCreatedBy);
				queryParam.put("vin", split[0]);
				items = stockInService.queryStockInDetails(queryParam);
			}
		}
		if (items.size() > 0) {
			for (Map map : items) {// 前台传过来的仓库名称转化为仓库代码
				String code = map.get("A_S_C").toString();
				map.put("STORAGE_CODE", code);
			}
		}
		stockInService.btnStockIn(items, seNo, null, sheetCreatedBy);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location", uriCB.path("/vehicleStock/stockIn/showList/{sNo}").buildAndExpand(seNo).toUriString());
		return new ResponseEntity<String>(null, headers, HttpStatus.CREATED);
	}

	/**
	 * 查询质损信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findInspectionInfo/{vin}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findInspectionInfo(@PathVariable String vin) {
		return stockInService.findAllInpectionList(vin);
	}

	/**
	 * 删除入库单信息
	 * 
	 * @param seNo
	 * @param vin
	 */
	@RequestMapping(value = "/delDetails/{seNo}/{vin}", method = RequestMethod.DELETE)
	@ResponseStatus(HttpStatus.NO_CONTENT)
	public void delDetails(@PathVariable String seNo, @PathVariable String vin) {
		stockInService.delDetailsInfo(seNo, vin);
	}

	/**
	 * 批量验收用查询入库子表
	 * 
	 * @param queryParam
	 * @return
	 */
	@RequestMapping(value = "/InspectionFindDetails", method = RequestMethod.GET)
	@ResponseBody
	public PageInfoDto findAllDetails(@RequestParam Map<String, String> queryParam) {
		return stockInService.findAllDetailsForInspect(queryParam);
	}

	/**
	 * 批量验收操作
	 * 
	 * @param map
	 * @param uriCB
	 * @return
	 */
	@RequestMapping(value = "/allInspect", method = RequestMethod.PUT)
	public ResponseEntity<String> allInspection(@RequestBody Map<String, String> map, UriComponentsBuilder uriCB) {
		stockInService.btnAllInspect(map);
		MultiValueMap<String, String> headers = new HttpHeaders();
		headers.set("Location",
				uriCB.path("/vehicleStock/stockIn/showList/{sNo}").buildAndExpand(map.get("seNo")).toUriString());
		return new ResponseEntity<String>(null, headers, HttpStatus.CREATED);
	}

	/**
	 * 打印
	 * 
	 * @param vin
	 * @return
	 */
	@RequestMapping(value = "/loadPrintInfo/{seNo}/{vins}", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findPrintInfo(@PathVariable String seNo, @PathVariable String vins) {
		String[] vin = vins.split(",");
		String item = "";
		for (int i = 0; i < vin.length; i++) {
			if (i == vin.length - 1) {// 表示最后一条
				item += "'" + vin[i] + "'";
			} else {
				item += "'" + vin[i] + "',";
			}
		}
		return stockInService.findPrintAbout(seNo, item);
	}

	/**
	 * 查询排放标准
	 * 
	 * @return
	 */
	@RequestMapping(value = "/findDischargeStandard", method = RequestMethod.GET)
	@ResponseBody
	public List<Map> findDischargeStandard() {
		return stockInService.findDischargeStandard();
	}

	/**
	 * PDI校验
	 * 
	 * @return
	 */
	@RequestMapping(value = "/checkPDI/{vin}/{prodectCode}", method = RequestMethod.GET)
	@ResponseBody
	public String checkPDI(@PathVariable String vin, @PathVariable(value = "prodectCode") String seno) {
		String[] split = vin.split(",");
		String vins = "";
		for (int i = 0; i < split.length; i++) {
			String productCode = stockInService.findProductCode(split[i], seno);
			String checkPDI = stockInService.checkPDI(split[i], productCode);
			if (!StringUtils.isNullOrEmpty(checkPDI)) {
				if (i != split.length - 1) {
					vins += checkPDI + ",";
				} else {
					vins += checkPDI;
				}
			}
		}
		return vins;
	}
}
