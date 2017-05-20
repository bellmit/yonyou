package com.yonyou.dms.vehicle.controller.oldPart;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.common.domains.DTO.basedata.TtOpOldpartPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TmpGcsImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.oldPart.TtOpGcsImpDTO;
import com.yonyou.dms.vehicle.domains.DTO.threePack.ForecastImportDto;
import com.yonyou.dms.vehicle.domains.PO.oldPart.TmpGcsImpPO;
import com.yonyou.dms.vehicle.service.oldPart.GcsBaoDanManageService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;
@Controller
@TxnConn
@RequestMapping("/gcsbd")
public class GcsBaoDanManageController  extends BaseController {
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	@Autowired
	GcsBaoDanManageService rbservice;
	
    @Autowired
    SystemParamService paramService;
    
	@Autowired
    private ExcelRead<TmpGcsImpDTO>  excelReadService;
	
	@Autowired
	ExcelGenerator excelService;
	
	//查询
	@RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public PageInfoDto queryDefeatReason(@RequestParam Map<String, String> queryParam) throws Exception {
    	logger.info("查询GCS保单信息");
        PageInfoDto pageInfoDto=rbservice.findGcs(queryParam);
        return pageInfoDto;
    }
	//下载
    @SuppressWarnings("rawtypes")
	@RequestMapping(value = "/export/excel", method = RequestMethod.GET)
	public void exportCarownerInfo(@RequestParam Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		System.out.println("下载方法");
		List<Map> resultList = rbservice.queryEmpInfoforExport(queryParam);
		Map<String, List<Map>> excelData = new HashMap<String, List<Map>>();
		excelData.put("GCS保单信息管理", resultList);
		// 生成excel 文件
		List<ExcelExportColumn> exportColumnList = new ArrayList<ExcelExportColumn>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("REPAIR_NO", "工单号"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "旧件代码"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "旧件名称"));
		exportColumnList.add(new ExcelExportColumn("PRICE", "单价"));
		exportColumnList.add(new ExcelExportColumn("PART_COUNT", "数量"));
		exportColumnList.add(new ExcelExportColumn("SUBTOTAL", "小计"));
		exportColumnList.add(new ExcelExportColumn("IS_MAPPING", "是否匹配成功",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "导入时间"));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_DATE", "审核 通过日期"));
		excelService.generateExcel(excelData, exportColumnList, "GCS保单信息管理.xls", request, response);

	}
	 /**
     * 下载导入模板
     */
    @RequestMapping(value = "/export/{type}",method = RequestMethod.GET)
    public void donwloadTemplte(@PathVariable(value = "type") String type,HttpServletRequest request,HttpServletResponse response){
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            //查询对应的参数
            BasicParametersDTO paramDto = paramService.queryBasicParameterByTypeandCode(ParamCodeConstants.TEMPLATE_DOWNLOAD, type);
            Resource resource = new ClassPathResource(paramDto.getParamValue()); 
            //获取文件名称
            FrameHttpUtil.setExportFileName(request,response, resource.getFilename());
            
            response.addHeader("Content-Length", "" + resource.getFile().length());
            
            bis = new BufferedInputStream(resource.getInputStream());
            byte[] bytes = new byte[1024];
            bos = new BufferedOutputStream(response.getOutputStream());
            while((bis.read(bytes))!=-1){
                bos.write(bytes);
            }
            bos.flush();
        } catch (Exception e) {
            throw new ServiceBizException("下载模板失败,请与管理员联系",e);
        }finally{
            IOUtils.closeStream(bis);
            IOUtils.closeStream(bos);
        }
    }
	
	/**
	 * 导入临时表
	 * @param importFile
	 * @param forecastImportDto
	 * @param uriCB
	 * @return
	 * @throws Exception
	 */
		 	@RequestMapping(value = "/import", method = RequestMethod.POST)
		 	@ResponseBody
		    public ArrayList<TmpGcsImpDTO> importforecastAudit(@RequestParam final Map<String,String> queryParam,@RequestParam(value = "file") MultipartFile importFile,ForecastImportDto forecastImportDto, UriComponentsBuilder uriCB) throws Exception {
		 		logger.info("============GCS保单导入===============");
		 		TmpGcsImpPO.deleteAll();
		         // 解析Excel 表格(如果需要进行回调)
		         ImportResultDto<TmpGcsImpDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,new AbstractExcelReadCallBack<TmpGcsImpDTO>(TmpGcsImpDTO.class,new ExcelReadCallBack<TmpGcsImpDTO>() {
		             @Override
		             public void readRowCallBack(TmpGcsImpDTO rowDto, boolean isValidateSucess) {
		                 try{
		                     // 只有全部是成功的情况下，才执行数据库保存
		                     if(isValidateSucess){
		                     	rbservice.insertTmpVsProImpAudit(rowDto);
		                     	ImportResultDto<TmpGcsImpDTO> importResultList =
		                    			rbservice.checkData(rowDto);
		                     	if(importResultList.getErrorList() != null){
		                    		throw new ServiceBizException("导入出错,请见错误列表",importResultList.getErrorList()) ;
		                    	}
		                     }
		                 }catch(Exception e){
		              //  	 throw e;
		                		throw new ServiceBizException(e) ;
		                 }
		             }
		         }));
		         if(importResult.isSucess()){
		             return importResult.getDataList();
		         }else{
		             throw new ServiceBizException("导入出错,请见错误列表",importResult.getErrorList()) ;
		         }
		     }
			/**
			 * 
			* @Title: oemSelectTmpYearPlan 
			* @Description: 临时表数据回显 
			* @param @param year
			* @param @param queryParam
			* @param @return    设定文件 
			* @return PageInfoDto    返回类型 
			* @throws
			 */
			@RequestMapping(value="/oemgcs",method = RequestMethod.GET)
			@ResponseBody
			public List<Map> oemSelectTmpYearPlan(@RequestParam Map<String, String> queryParam) {
				logger.info("===========GCS上传（查询待插入数据） ===============");
				//确认后查询待插入的数据
				List<Map> list = rbservice.oemSelectTmpYearPlan(queryParam);
				for(int i=0;i<list.size();i++){
					Map map = list.get(i);
				}
				return list;
			}
			/**
			 * GCS导入
		 	 * @throws ParseException 
			 */
			@RequestMapping(value="/checkImportGcs",method = RequestMethod.POST)
			@ResponseBody
		    public ResponseEntity<TtOpGcsImpDTO> importExcel(UriComponentsBuilder uriCB,
		    		 @Valid TtOpGcsImpDTO ypDTO,
					@RequestParam Map<String,String> queryParam) throws ParseException {
				try{
					SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
					LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					List<Map> tmpList = rbservice.oemSelectTmpYearPlan(queryParam);	
					TtOpGcsImpDTO po1 = new TtOpGcsImpDTO();
					for(int i=0;i<tmpList.size();i++){				
						Map<String,Object> map = tmpList.get(i);	
						TtOpGcsImpDTO po = new TtOpGcsImpDTO();
						po.setDealerCode(map.get("Dealer_Code").toString());
						po.setDealerName(map.get("Dealer_Name").toString());
						po.setRepairNo(map.get("Repair_No").toString());
						po.setVin(map.get("vin").toString());
						po.setPaymentDate(sdf.parse(map.get("PAYMENT_DATE").toString()));
						po.setPartCode(map.get("PART_CODE").toString());
						po.setPartName(map.get("PART_NAME").toString());
						po.setPrice(new Double(map.get("PRICE").toString()));
						po.setPartCount(new Long(map.get("Part_count").toString()));
						po.setSubtotal(new Double(map.get("SUBTOTAL").toString()));
						po.setCreateBy(loginInfo.getUserId());
						po.setCreateDate(new Date());
						String claimNumber= map.get("Repair_No").toString();
						List<Map> listtp=rbservice.mappingOldPart(map.get("Dealer_Code").toString(),claimNumber ,map.get("Part_Code").toString());
						int len=listtp.size();
						int count=Integer.parseInt((String) map.get("Part_Count"));
						if(len>0&&len>=count){
							for(int i1=0;i1<count;i1++){
								TtOpOldpartPO toop1=new TtOpOldpartPO();
								TtOpOldpartPO topValue=new TtOpOldpartPO();
								toop1.setLong("Oldpart_Id",Long.parseLong(listtp.get(i1).get("OLDPART_ID").toString()));
								topValue.setDate("ClaimAudit_Date",sdf.parse(map.get("Payment_Date").toString()));//索赔审核通过时间
								topValue.setString("Oldpart_Type",OemDictCodeConstants.OP_TYPE_RETURN);//更改为返还件
								topValue.saveIt();
							}
							po.setIsMapping(10041001);//匹配成功
						}else{
							po.setIsMapping(10041002);//匹配否
						}
						rbservice.setImpPO(po);	
					}
				}catch (Exception e) {
					throw e;
					}
					return null;
					
			}
}
