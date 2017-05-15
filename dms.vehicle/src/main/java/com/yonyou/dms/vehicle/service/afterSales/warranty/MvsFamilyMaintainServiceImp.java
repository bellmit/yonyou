package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.salesPlanManage.TmVhclMaterialGroupPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.MvsFamilyMaintainDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TmVhclMaterialGroupDTO;
import com.yonyou.dms.vehicle.domains.DTO.threePack.TtThreepackWarnParaDTO;

/**
 * MVS 家族维护
 * @author zhiahongmiao 
 *
 */

@Service
public class MvsFamilyMaintainServiceImp implements MvsFamilyMaintainService{
	@Autowired
	MvsFamilyMaintainDao mvcFamilyMaintainDao;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 查询菲亚特车系
	 */
	@Override
	public List<Map> GetMVCCheXi(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return mvcFamilyMaintainDao.GetMVCCheXi(queryParams);
	}
	/**
	 * 关联车型
	 */
	@Override
	public List<Map> getGroupCodes(Long seriesId) throws ServiceBizException {
		// TODO Auto-generated method stub
		return mvcFamilyMaintainDao.getGroupCodes(seriesId);
	}
	/**
	 * 查询
	 */
	@Override
	public PageInfoDto MVSFamilyMaintainQuery(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = mvcFamilyMaintainDao.MVSFamilyMaintainQuery(queryParam);
		return pageInfoDto;
	}
	/**
	 * 下载
	 */
	@Override
	public void MVSFamilyMaintainDownload(Map<String, String> queryParam, HttpServletRequest request,HttpServletResponse response) throws ServiceBizException {
		// TODO Auto-generated method stub
		List<Map> orderList = mvcFamilyMaintainDao.MVSFamilyMaintainDownload(queryParam);   	
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("MVS 家族维护下载", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("GROUP_CODE", "车型代码"));
		exportColumnList.add(new ExcelExportColumn("MVS", "MVS"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("UPDATE_BY", "更新人"));		
		exportColumnList.add(new ExcelExportColumn("UPDATE_AT", "更新时间"));		
		excelService.generateExcel(excelData, exportColumnList, "MVS 家族维护下载.xls", request, response);

	}
	/**
	 * 添加（查询相关数据进行修改）
	 */
	@Override
	public Long MVSAdd(TmVhclMaterialGroupDTO tvmgDto) throws ServiceBizException {
		// TODO Auto-generated method stub
		return 	mvcFamilyMaintainDao.MVSAdd(tvmgDto);
	}
	/**
	 * 回显
	 */
	@Override
	public TmVhclMaterialGroupPO findMVS(Long id) throws ServiceBizException {
		// TODO Auto-generated method stub
		TmVhclMaterialGroupPO po = mvcFamilyMaintainDao.findMVS(id);
		return po;
	}
	/**
	 * 修改
	 */
	@Override
	public Long MVSUpdate(TmVhclMaterialGroupDTO tvmgDto) throws ServiceBizException {
		// TODO Auto-generated method stub
		return mvcFamilyMaintainDao.MVSUpdate(tvmgDto);
	}

}
