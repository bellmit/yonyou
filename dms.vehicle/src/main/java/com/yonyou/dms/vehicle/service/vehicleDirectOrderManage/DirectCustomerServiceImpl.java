package com.yonyou.dms.vehicle.service.vehicleDirectOrderManage;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.vehicleDirectOrderManage.DirectCustomerDao;
import com.yonyou.dms.vehicle.domains.DTO.retailReportQuery.TtBigCustomerDirectDTO;
import com.yonyou.dms.vehicle.domains.PO.retailReportQuery.TtBigCustomerDirectPO;

/**
 * 直销客户管理
 * @author Administrator
 *
 */
@Service
public class DirectCustomerServiceImpl implements DirectCustomerService{
	@Autowired
	DirectCustomerDao  customerDao;
	@Autowired
	private ExcelGenerator excelService;
	
	/**
	 * 直销客户查询
	 * @param queryParam
	 * @return
	 */
	public PageInfoDto  directCustomerQuery(Map<String, String> queryParam){
		return customerDao.directCustomerQuery(queryParam);
	}
	
	
	/**
	 * 下载
	 */
	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = customerDao.download(queryParam);
		// TODO
		excelData.put("资源分配备注下载", (List<Map>) list);

		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("BIG_CUSTOMER_CODE", "大客户编码"));
		exportColumnList.add(new ExcelExportColumn("BIG_CUSTOMER_TYPE", "大客户名称"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NO", "直销客户编码"));
		exportColumnList.add(new ExcelExportColumn("CUSTOMER_NAME", "客户名称"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_ADDR", "联系地址"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_NAME", "联系人"));
		exportColumnList.add(new ExcelExportColumn("LINKMAN_TEL", "联系方式"));
		exportColumnList.add(new ExcelExportColumn("ID_TYPE", "证件类型",ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("ID_NO", "证件编号"));	
		excelService.generateExcel(excelData, exportColumnList, "资源分配备注下载.xls", request, response);
	}
	/**
	 * 查询所有有效银行
	 */
	public List<Map> queryBank() {
		return  customerDao.queryBank();
	}
	
	/**
	 * 新增直销客户信息
	 */
    public Long addDirectCustomer(TtBigCustomerDirectDTO ptdto) throws ServiceBizException {
    	
        if(!CommonUtils.isNullOrEmpty( getCustomerTel(ptdto))){
            throw new ServiceBizException("联系方式存在错误，请重新输入！");
        }
        
              if(!CommonUtils.isNullOrEmpty( getCustomerIdNo(ptdto))){
             	  throw new ServiceBizException("证件号码存在错误，请重新输入！");
               }
              
            	  if(!CommonUtils.isNullOrEmpty(getBankNo(ptdto))){
            		  throw new ServiceBizException("银行账号存在错误，请重新输入！"); 
            	  }
        TtBigCustomerDirectPO ptPo=new TtBigCustomerDirectPO();
          setApplyPo(ptPo,ptdto);
          ptPo.saveIt();
          return ptPo.getLongId();
      }



	private void setApplyPo(TtBigCustomerDirectPO ptPo, TtBigCustomerDirectDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		int fourNo = (int) (Math.random()*9000+1000);//随机4位数
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");//设置日期格式
		String timeNow = df.format(new Date());// new Date()为获取当前系统时间
		String customerNo = "ZX"+timeNow+fourNo;
  		 ptPo.setString("CUSTOMER_NO", customerNo);
          ptPo.setString("BIG_CUSTOMER_CODE", ptdto.getBigCustomerCode());
  		 ptPo.setString("BIG_CUSTOMER_TYPE", ptdto.getBigCustomerType());
          ptPo.setString("CUSTOMER_NAME", ptdto.getCustomerName());
          ptPo.setInteger("CUSTOMER_TYPE", ptdto.getCustomerType());
          ptPo.setString("LINKMAN_NAME", ptdto.getLinkmanName());
          ptPo.setString("LINKMAN_SEX", ptdto.getLinkmanSex());
          ptPo.setString("LINKMAN_TEL", ptdto.getLinkmanTel());
          ptPo.setString("LINKMAN_ADDR", ptdto.getLinkmanAddr());
          ptPo.setInteger("ID_TYPE", ptdto.getIdType());
          ptPo.setString("ID_NO", ptdto.getIdNo());
          ptPo.setString("BANK_NO", ptdto.getBankNo());
          ptPo.setInteger("PAYMENT_BANK", ptdto.getPaymentBank());
          ptPo.setString("REAMK", ptdto.getReamk());
          ptPo.setDate("CREATE_DATE", new Date());
          ptPo.setDate("UPDATE_DATE", new Date());
          ptPo.setLong("CREATE_BY", loginInfo.getUserId());
          ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
  
  	}
	
	/**
	 * 新增时判断联系方式是否存在
	 */
	
    public List<Map> getCustomerTel(TtBigCustomerDirectDTO ptdto) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("  select LINKMAN_TEL from TT_BIG_CUSTOMER_DIRECT where 1=1");
        List<Object> params = new ArrayList<>();
        sqlSb.append(" and  LINKMAN_TEL=?");
        params.add(ptdto.getLinkmanTel());
        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
        return applyList;
    }   
	/**
	 * 新增时判断证件号码是否存在
	 */
    public List<Map> getCustomerIdNo(TtBigCustomerDirectDTO ptdto) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("  select  ID_NO from TT_BIG_CUSTOMER_DIRECT where 1=1");
        List<Object> params = new ArrayList<>();
        sqlSb.append(" and  ID_NO = ? ");
        params.add(ptdto.getIdNo());
        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
        return applyList;
    }   
	/**
	 * 新增时判断银行卡号是否存在
	 */
    public List<Map> getBankNo(TtBigCustomerDirectDTO ptdto) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("  select  BANK_NO from TT_BIG_CUSTOMER_DIRECT where 1=1");
        List<Object> params = new ArrayList<>();
        sqlSb.append(" and  BANK_NO = ? ");
        params.add(ptdto.getBankNo());
        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
        return applyList;
    }   
    /**
     * 通过id删除直销客户信息
     */
	public void  deleteDirectCustomerById(Long id , TtBigCustomerDirectDTO ptdto) {
		TtBigCustomerDirectPO ptPo=TtBigCustomerDirectPO.findById(id);
		if(ptPo!=null){
			 ptPo.deleteCascadeShallow();
		}	
	}
	/**
	 * 通过id查询修改信息，进入修改页面
	 */
	@Override
	public TtBigCustomerDirectPO getDirectCustomerById(Long id) throws ServiceBizException {
	     return TtBigCustomerDirectPO.findById(id);
	}
    
}
