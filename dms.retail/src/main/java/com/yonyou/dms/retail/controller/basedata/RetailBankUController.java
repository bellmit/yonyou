package com.yonyou.dms.retail.controller.basedata;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.domains.DTO.baseData.BasicParametersDTO;
import com.yonyou.dms.framework.service.baseData.SystemParamService;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.service.excel.ExcelRead;
import com.yonyou.dms.framework.service.excel.ExcelReadCallBack;
import com.yonyou.dms.framework.service.excel.impl.AbstractExcelReadCallBack;
import com.yonyou.dms.framework.util.http.FrameHttpUtil;
import com.yonyou.dms.function.common.ParamCodeConstants;
import com.yonyou.dms.function.domains.DTO.ImportResultDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.io.IOUtils;
import com.yonyou.dms.retail.domains.DTO.basedata.TmLoanRatMaintainDTO;
import com.yonyou.dms.retail.domains.DTO.basedata.TmRetalDiscountImportTempDTO;
import com.yonyou.dms.retail.domains.PO.basedata.TmRetalDiscountImportTempPO;
import com.yonyou.dms.retail.service.basedata.RetailUimportService;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.mvc.controller.BaseController;

/**
 * 销售折扣上传
 * 
 * @author Administrator
 *
 */
@Controller
@TxnConn
@RequestMapping("/import")
public class RetailBankUController extends BaseController {

	@Autowired
	SystemParamService paramService;

	@Autowired
	ExcelGenerator excelService;

	@Autowired
	private ExcelRead<TmRetalDiscountImportTempDTO> excelReadService;

	@Autowired
	RetailUimportService ruservice;

	/**
	 * 下载
	 */
	@RequestMapping(value = "/{type}", method = RequestMethod.GET)
	public void donwloadTemplte(@PathVariable(value = "type") String type, HttpServletRequest request,
			HttpServletResponse response) {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			// 查询对应的参数
			BasicParametersDTO paramDto = paramService
					.queryBasicParameterByTypeandCode(ParamCodeConstants.TEMPLATE_DOWNLOAD, type);
			Resource resource = new ClassPathResource(paramDto.getParamValue());
			// 获取文件名称
			FrameHttpUtil.setExportFileName(request, response, resource.getFilename());

			response.addHeader("Content-Length", "" + resource.getFile().length());

			bis = new BufferedInputStream(resource.getInputStream());
			byte[] bytes = new byte[1024];
			bos = new BufferedOutputStream(response.getOutputStream());
			while ((bis.read(bytes)) != -1) {
				bos.write(bytes);
			}
			bos.flush();
		} catch (Exception e) {
			throw new ServiceBizException("下载模板失败,请与管理员联系", e);
		} finally {
			IOUtils.closeStream(bis);
			IOUtils.closeStream(bos);
		}
	}

	/**
	 * 销售折扣上传
	 */
	@RequestMapping(value = "/import", method = RequestMethod.POST)
	@ResponseBody
	public List<TmRetalDiscountImportTempDTO> yearPlanExcelOperate(@RequestParam final Map<String, String> queryParam,
			@RequestParam(value = "file") MultipartFile importFile, TmRetalDiscountImportTempDTO forecastImportDto,
			UriComponentsBuilder uriCB) throws ServiceBizException{

		TmRetalDiscountImportTempPO.deleteAll();
		try {
			ImportResultDto<TmRetalDiscountImportTempDTO> importResult = excelReadService.analyzeExcelFirstSheet(importFile,
					new AbstractExcelReadCallBack<TmRetalDiscountImportTempDTO>(TmRetalDiscountImportTempDTO.class));
			if(!importResult.isSucess()){
				throw new ServiceBizException("导入出错,请见错误列表", importResult.getErrorList());
			}else{
				ArrayList<TmRetalDiscountImportTempDTO> dataList = importResult.getDataList();
				for(int i = 0;i<dataList.size();i++){
					TmRetalDiscountImportTempDTO rowDto = dataList.get(i);
					// 向临时表插入数据
					rowDto.setRYear(queryParam.get("year"));
					rowDto.setRMonth(queryParam.get("monthName"));
					ruservice.insertTmpVsYearlyPlan(rowDto);
				}
				// 校验临时表数据
				ImportResultDto<TmRetalDiscountImportTempDTO> importResultList = ruservice.checkData();
				if(importResultList.getErrorList() != null && importResultList.getErrorList().size() > 0){
					throw new ServiceBizException("导入出错,请见错误列表", importResultList.getErrorList());
				}else{
					boolean flag = ruservice.save();
					if(!flag){
						throw new ServiceBizException("上传失败，请检查数据然后重试!");
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
