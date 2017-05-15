package com.yonyou.dms.vehicle.service.claimMgr;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.claimMgr.OldPartQueryDlrDao;

/**
 * 旧件查询
 * @author zhiahongmiao 
 *
 */

@Service
public class OldPartQueryDlrServiceImp implements OldPartQueryDlrService{
	@Autowired
	OldPartQueryDlrDao oldPartQueryDlrDao;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto MVSFamilyMaintainQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = oldPartQueryDlrDao.MVSFamilyMaintainQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 索赔类型下拉框
	 */
	@Override
	public List<Map> ClaimType(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return oldPartQueryDlrDao.GetMVCCheXi(queryParams);
	}
	/**
	 * 下载
	 */
	@Override
	public void OldPartQueryDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		// TODO Auto-generated method stub
		List<Map> orderList = oldPartQueryDlrDao.OldPartQueryDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("旧件查询", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("PART_CODE", "零件代码"));
		exportColumnList.add(new ExcelExportColumn("PART_NAME", "零件名称"));
		exportColumnList.add(new ExcelExportColumn("CLAIM_NO", "索赔单号"));
		exportColumnList.add(new ExcelExportColumn("CLAIM_TYPE", "索赔类型"));		
		exportColumnList.add(new ExcelExportColumn("RO_NO", "工单号"));		
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));		
		exportColumnList.add(new ExcelExportColumn("MAKE_DATE", "申请日期"));	
		excelService.generateExcel(excelData, exportColumnList, "旧件查询.xls", request, response);

	}

}
