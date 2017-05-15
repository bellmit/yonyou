package com.yonyou.dms.vehicle.service.afterSales.warranty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.afterSales.warranty.SpecialOperateDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrOperateDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrOperatePO;

@Service
public class SpecialOperateServiceImpl implements SpecialOperateService {
	@Autowired
	SpecialOperateDao specialOperateDao;
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Override
	public PageInfoDto query(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = specialOperateDao.query(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 新增
	 */
	@Override
	public void add(TtWrOperateDTO dto) throws  ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (!StringUtils.isNullOrEmpty(dto.getOptCode())) {
			TtWrOperatePO po = TtWrOperatePO.findFirst("STATUS=10011001 and OPT_CODE = ?", dto.getOptCode());
			if(po!=null){
				po.setString("INVOICE_CODE", dto.getInvoiceCode());
				po.setInteger("IS_SP", OemDictCodeConstants.IF_TYPE_YES);
				po.setLong("UPDATE_BY", loginInfo.getUserId());
				po.setDate("UPDATE_DATE", new Date());
				flag=po.saveIt();
			}else {
				throw new ServiceBizException("该操作代码无效或不存在，新增失败!");
			}
		} else {
			throw new ServiceBizException("未填写完整重要信息，请输入！");
		}
		if(flag){
		}else{
			throw new ServiceBizException("新增失败!");
		}
	}
	
	/**
	 * 修改
	 */
	@Override
	public void update(TtWrOperateDTO dto) throws  ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(dto.getId())){
			TtWrOperatePO po = TtWrOperatePO.findFirst("ID = ?", dto.getId());
			if(po!=null){
				Integer isSp;
				if(dto.getStatus().equals(OemDictCodeConstants.STATUS_ENABLE)){
					isSp=OemDictCodeConstants.IF_TYPE_YES;
				}else {
					isSp=OemDictCodeConstants.IF_TYPE_NO;
				}
				po.setString("INVOICE_CODE", dto.getInvoiceCode());
				po.setInteger("IS_SP", isSp);
				po.setLong("UPDATE_BY", loginUser.getUserId());
				po.setDate("UPDATE_DATE", new Date());
				flag=po.saveIt();
			}
			if(flag){
			}else{
				throw new ServiceBizException("更新失败!");
			}
		}
	}
	
	/**
	 * 下载
	 */
	@Override
	public void download(Map<String, String> queryParam, HttpServletRequest request,
		HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = specialOperateDao.getDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("特殊操作代码维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("OPT_CODE", "操作代码"));
		exportColumnList.add(new ExcelExportColumn("OPT_NAME_CN", "操作描述"));
		exportColumnList.add(new ExcelExportColumn("INVOICE_CODE", "发票号"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态" ));
		excelService.generateExcel(excelData, exportColumnList, "特殊操作代码维护.xls", request, response);
	}
}
