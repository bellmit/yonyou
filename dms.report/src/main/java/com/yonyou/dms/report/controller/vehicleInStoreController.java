package com.yonyou.dms.report.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.report.service.impl.vehicleInStoreService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 车辆入库明细表
 * @author Benzc
 * @date 2017年1月17日
 */
@Controller
@TxnConn
@RequestMapping("/basedata/vehicleInStore")
public class vehicleInStoreController extends BaseController{
	
	@Autowired
    private vehicleInStoreService vehicleinstoreservice;
	
	@Autowired
    private ExcelGenerator excelService;
	
	/**
	 * 车辆入库明细分页查询
	 * @param queryParam
	 * @return
	 */
    @RequestMapping(value = "/inStoreSearch", method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryVehicleSales(@RequestParam Map<String, String> queryParam) {
        PageInfoDto pageInfoDto = vehicleinstoreservice.queryVehicleInStore(queryParam);
        return pageInfoDto;
    }
    
    /**
     * 查询仓库信息
     * @author Benzc
     * @date 2017年1月17日
     */
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/store",method = RequestMethod.GET)
    @ResponseBody
    public List<Map> storeSelect(@RequestParam Map<String,String> queryParam){
    	List<Map> storelist = vehicleinstoreservice.storeSelect(queryParam);
    	return storelist;
    }
    
    /**
     * 导出
     * @author Benzc
     * @date 2017年1月17日
     * @param queryParam
     * @param request
     * @param response
     * @throws Exception
     */
    @SuppressWarnings("rawtypes")
    @RequestMapping(value = "/export", method = RequestMethod.GET)
    public void exportVisitingRecord(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
                                     HttpServletResponse response) throws Exception {
    	List<Map> resultList = vehicleinstoreservice.queryInStoreExport(queryParam);
    	Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
        excelData.put("车辆入库明细表", resultList);
        List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
        
        // 生成excel 文件
        exportColumnList.add(new ExcelExportColumn("INSTOCK_DATE", "入库日期"));
        exportColumnList.add(new ExcelExportColumn("SE_NO", "入库编号"));
        exportColumnList.add(new ExcelExportColumn("MODEL_NAME", "车型"));
        exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "颜色"));
        exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
        exportColumnList.add(new ExcelExportColumn("CERTIFICATE_NUMBER", "合格证号"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_NAME", "仓库名称"));
        exportColumnList.add(new ExcelExportColumn("VEH_STATUS", "库存状态"));
        exportColumnList.add(new ExcelExportColumn("VENDOR_CODE", "供应单位编码"));
        exportColumnList.add(new ExcelExportColumn("VENDOR_NAME", "供应单位名称"));
        exportColumnList.add(new ExcelExportColumn("PURCHASE_PRICE", "采购价格（含税）"));
        exportColumnList.add(new ExcelExportColumn("SALES_ADVICE_PRICE", "销售指导价"));
        exportColumnList.add(new ExcelExportColumn("STORAGE_POSITION_CODE", "库位代码"));
        exportColumnList.add(new ExcelExportColumn("PRODUCT_CODE", "产品代码"));
        exportColumnList.add(new ExcelExportColumn("ENGINE_NO", "发动机号"));
        exportColumnList.add(new ExcelExportColumn("KEY_NUMBER", "钥匙编号"));
        exportColumnList.add(new ExcelExportColumn("ADDITIONAL_COST", "附加成本"));
        exportColumnList.add(new ExcelExportColumn("MANUFACTURE_DATE", "生产日期"));
        exportColumnList.add(new ExcelExportColumn("FACTORY_DATE", "出厂日期"));
        exportColumnList.add(new ExcelExportColumn("PO_NO", "采购订单编号"));
        exportColumnList.add(new ExcelExportColumn("VSN", "VSN"));
        exportColumnList.add(new ExcelExportColumn("EXHAUST_QUANTITY", "排气量"));
        exportColumnList.add(new ExcelExportColumn("DISCHARGE_STANDARD", "排气标准"));
        exportColumnList.add(new ExcelExportColumn("REMARK", "备注"));
        exportColumnList.add(new ExcelExportColumn("PRODUCTING_AREA", "产地"));
        exportColumnList.add(new ExcelExportColumn("INSPECTION_CONSIGNED", "是否代验收"));
        exportColumnList.add(new ExcelExportColumn("IS_DIRECT", "是否直销"));
        exportColumnList.add(new ExcelExportColumn("HAS_CERTIFICATE", "是否有合格证"));
        exportColumnList.add(new ExcelExportColumn("SHIPPING_DATE", "发车日期"));
        exportColumnList.add(new ExcelExportColumn("ARRIVING_DATE", "预计发送到日期"));
        exportColumnList.add(new ExcelExportColumn("DELIVERYMAN_NAME", "送车人姓名"));
        exportColumnList.add(new ExcelExportColumn("DELIVERYMAN_PHONE", "送车人电话"));
        exportColumnList.add(new ExcelExportColumn("SHIPPER_LICENSE", "承运车牌号"));
        exportColumnList.add(new ExcelExportColumn("SHIPPER_NAME", "承运商名称"));
        exportColumnList.add(new ExcelExportColumn("VS_PURCHASE_DATE", "采购日期"));
        exportColumnList.add(new ExcelExportColumn("SHIPPING_ADDRESS", "收货地址"));
        exportColumnList.add(new ExcelExportColumn("CONSIGNER_CODE", "委托单位代码"));
        exportColumnList.add(new ExcelExportColumn("CONSIGNER_NAME", "委托单位名称"));
        
        excelService.generateExcelForDms(excelData, exportColumnList, "车辆入库明细表.xls", request, response);
    }

}
