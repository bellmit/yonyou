package com.yonyou.dms.vehicle.service.claimManage;

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
import com.yonyou.dms.vehicle.dao.claimManage.ClaimStatusSearchDealerDao;

/**
* @author liujm
* @date 2017年4月28日
*/

@Service
public class ClaimStatusSearchDealerServiceImpl implements ClaimStatusSearchDealerService {
	
	@Autowired
	private ExcelGenerator excelService;
	
	
	@Autowired
	private ClaimStatusSearchDealerDao cssdDao;
	
	
	
	
	/**
	 * 主页面查询
	 */
	@Override
	public PageInfoDto queryClaimStatusSearchDealer(Map<String, String> queryParam) throws ServiceBizException {
		PageInfoDto pageInfoDto = cssdDao.getClaimStatusSearchDealerQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 下载
	 */
	@Override
	public void downloadClaimStatusSearchDealer(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> downloadList = cssdDao.getClaimStatusSearchDealerDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("索赔申请单状态跟踪下载", downloadList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();

		exportColumnList.add(new ExcelExportColumn("CLAIM_NO", "索赔单号"));
		exportColumnList.add(new ExcelExportColumn("RO_NO", "维修工单号"));
		exportColumnList.add(new ExcelExportColumn("CLAIM_TYPE", "索赔类型"));		
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));		
		exportColumnList.add(new ExcelExportColumn("DEALER_NAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("MODEL", "车型"));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CLAIM_CATEGORY", "类别", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("SUBMIT_COUNT", "提报次数"));
		exportColumnList.add(new ExcelExportColumn("APPLY_DATE", "索赔申请日期"));
		exportColumnList.add(new ExcelExportColumn("PASS_DATE", "审核通过日期"));
		excelService.generateExcel(excelData, exportColumnList, "索赔申请单状态跟踪下载.xls", request, response);

		
	}
	
	/**
	 * 索赔申请单状态跟踪  审核历史
	 */
	@Override
	public PageInfoDto claimAuditQueryDetail(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = cssdDao.getClaimAuditQueryDetail(claimId);
		return pageInfoDto;
	}
	
	/**
	 * 索赔申请单状态跟踪  附件列表
	 */
	@Override
	public PageInfoDto accessoryQueryList(Long claimId) throws ServiceBizException {
		PageInfoDto pageInfoDto = cssdDao.getAccessoryQueryList(claimId);
		return pageInfoDto;
	}
	
	/**
	 * 获取索赔类型列表
	 */
	@Override
	public List<Map> queryClaimTypeList(Integer type) throws ServiceBizException {
		List<Map> resultList = cssdDao.getQueryClaimTypeList( type);
		return resultList;
	}
	

}
