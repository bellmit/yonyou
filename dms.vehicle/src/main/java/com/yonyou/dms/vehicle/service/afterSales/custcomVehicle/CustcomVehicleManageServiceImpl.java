package com.yonyou.dms.vehicle.service.afterSales.custcomVehicle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtVsCustomerPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.dao.afterSales.custcomVehicle.CustcomVehicleManageDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.custcomVehicle.TtVsCustomerDTO;

/**
 * 客户资料维护
 * @author Administrator
 *
 */
@Service
public class CustcomVehicleManageServiceImpl implements CustcomVehicleManageService{
	@Autowired
	CustcomVehicleManageDao  custcomVehicleManageDao;
	@Autowired
	private ExcelGenerator excelService;
	/**
	 * 客户资料查询
	 */

	@Override
	public PageInfoDto CustcomVehicleQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return custcomVehicleManageDao.CustcomVehicleQuery(queryParam);
	}
     /**
      * 客户资料下载
      */
	@Override
	public void download(Map<String, String> queryParam,HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = custcomVehicleManageDao.download(queryParam);
		// TODO
		excelData.put("资源分配备注下载", (List<Map>) list);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("CTM_NAME", "客户姓名"));
		exportColumnList.add(new ExcelExportColumn("CTM_TYPE", "顾客类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("SALES_DATE", "客户创建时间"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_BY", "修改人"));
		exportColumnList.add(new ExcelExportColumn("UPDATE_DATE", "修改时间"));
		excelService.generateExcel(excelData, exportColumnList, "资源分配备注下载.xls", request, response);
	}
	/**
	 * 修改信息回显
	 */
	@Override
	public Map getShuju(Long id) {
		 Map<String, Object> m=new HashMap<String, Object>();
    	 List<Map> list=custcomVehicleManageDao.getShuju(id);
    	 m= list.get(0);
    	   return m;
	}
	/**
	 * 大类别
	 */
	@Override
	public List<Map> searchIndustryBig() {
		// TODO Auto-generated method stub
		return custcomVehicleManageDao.searchIndustryBig();
	}
	
	/**
	 * 小类别
	 */
	@Override
	public List<Map> searchIndustry(String parentCode) {
		// TODO Auto-generated method stub
		return custcomVehicleManageDao.searchIndustry(parentCode);
	}
	/**
	 * 省
	 */
	@Override
	public List<Map> queryProvince() {
		// TODO Auto-generated method stub
		return custcomVehicleManageDao.queryProvince();
	}
	/**
	 * 市
	 */
	@Override
	public List<Map> queryCity(Long parentId) {
		// TODO Auto-generated method stub
		return custcomVehicleManageDao.queryCity(parentId);
	}
	/**
	 * 县
	 */
	@Override
	public List<Map> queryTowm(Long parentId) {
		// TODO Auto-generated method stub
		return custcomVehicleManageDao.queryTowm(parentId);
	}
	
	/**
	 * 进行信息修改
	 */
	@Override
	public void edit(Long id, TtVsCustomerDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtVsCustomerPO   po=TtVsCustomerPO.findById(id);
		 po.setString("CTM_NAME", dto.getCtmName());
		 po.setInteger("CTM_TYPE", dto.getCtmType());
		 po.setInteger("CARD_TYPE", dto.getCardType());
		 po.setString("CARD_NUM", dto.getCardNum());
		 //得到groupCode
		 po.setInteger("SEX", dto.getSex());
		 po.setInteger("INCOME", dto.getIncome());
		 po.setDate("BIRTHDAY", dto.getBirthday());
		 po.setInteger("IS_MARRIED",dto.getIsMarried());
		 po.setString("MAIN_PHONE",dto.getMainPhone());//手机号码
		 po.setString("OTHER_PHONE",dto.getOtherPhone());//电话号码
		 
		 
		 po.setString("INDUSTRY_FIRST",dto.getIndustryFirst());//大行业类型
		 po.setString("INDUSTRY_SECOND",dto.getIndustrySecond());//小行业类型


		 
		 po.setLong("PROVINCE",dto.getProvince());	//省	
		 po.setLong("CITY",dto.getCity());//市
		 po.setLong("TOWN",dto.getTown());//县
		
		 po.setString("ADDRESS",dto.getAddress());//地址
		 po.setString("POST_CODE",dto.getPostCode());//邮编
		 po.setString("EMAIL",dto.getEmail());	//邮件
		 
		 
		 po.setDate("MAINTAIN_DATE", new Date());
		 po.setString("MAINTAIN_BY", loginInfo.getUserName());
		 po.setLong("UPDATE_BY", loginInfo.getUserId());
		 po.setDate("UPDATE_DATE", new Date());
		 po.setInteger("VER",0);
		 po.setInteger("IS_DEL",0);	
		  po.saveIt();
		
	}
	
	

}
