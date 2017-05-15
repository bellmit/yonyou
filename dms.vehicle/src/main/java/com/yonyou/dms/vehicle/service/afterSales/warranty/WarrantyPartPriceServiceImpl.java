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
import com.yonyou.dms.vehicle.dao.afterSales.warranty.WarrantyPartPriceDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.warranty.TtWrPartPriceDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.warranty.TtWrPartPriceDcsPO;

@Service
public class WarrantyPartPriceServiceImpl implements WarrantyPartPriceService {
	@Autowired
	WarrantyPartPriceDao warrantyPartPriceDao;
	
	@Autowired
	private ExcelGenerator excelService;
	
	@Override
	public PageInfoDto query(Map<String, String> queryParam) throws ServiceBizException {
		// TODO Auto-generated method stub
		PageInfoDto pageInfoDto = warrantyPartPriceDao.query(queryParam);
		return pageInfoDto;
	}
	
	/**
	 * 新增
	 */
	@Override
	public Long add(TtWrPartPriceDTO dto) throws  ServiceBizException {
		TtWrPartPriceDcsPO po = new TtWrPartPriceDcsPO();
		setPartPricePo(po, dto);
		return po.getLongId();
	}
	
	private void setPartPricePo(TtWrPartPriceDcsPO po, TtWrPartPriceDTO dto) {
		boolean flag = false;
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if (!StringUtils.isNullOrEmpty(dto.getMvs())) {
			LazyList<TtWrPartPriceDcsPO> listPO = TtWrPartPriceDcsPO.find("MVS = ?", dto.getMvs());
			if (listPO.size()>0) {
				throw new ServiceBizException("该MVS已维护！新增失败！");
			}
			po.setString("MODE_CODE", dto.getModeCode());
			po.setString("MVS", dto.getMvs());
			po.setDouble("RATE", dto.getRate());
			po.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
			po.setLong("CREATE_BY", loginInfo.getUserId());
			po.setDate("CREATE_DATE", new Date());
			po.setLong("UPDATE_BY", loginInfo.getUserId());
			po.setDate("UPDATE_DATE", new Date());
			flag=po.saveIt();
		} else {
			throw new ServiceBizException("未填写完整重要信息，请输入！");
		}
		if(flag){
		}else{
			throw new ServiceBizException("新增失败!");
		}
	}
	
	/**
	 * 保修类型修改
	 */
	@Override
	public void update(TtWrPartPriceDTO dto) throws  ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(dto.getId())){
			TtWrPartPriceDcsPO po = TtWrPartPriceDcsPO.findFirst("ID = ?", dto.getId());
			if(po!=null){
				po.setDouble("RATE", dto.getRate());
				po.setInteger("STATUS", dto.getStatus());
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
	 * 查询保修类型
	 */
	@Override
	public List<Map> getMVS(Map<String, String> queryParams,String seriesCode) throws ServiceBizException {
		// TODO Auto-generated method stub
		return warrantyPartPriceDao.getMVS(queryParams,seriesCode);
	}
	
	/**
	 * 下载
	 */
	@Override
	public void download(Map<String, String> queryParam, HttpServletRequest request,
		HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = warrantyPartPriceDao.getDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("零部件保修价维护", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("MVS", "MVS"));
		exportColumnList.add(new ExcelExportColumn("RATE", "费率"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态" ));
		excelService.generateExcel(excelData, exportColumnList, "零部件保修价维护.xls", request, response);
	}
}
