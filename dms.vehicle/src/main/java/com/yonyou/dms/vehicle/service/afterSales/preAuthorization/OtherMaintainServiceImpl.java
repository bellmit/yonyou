package com.yonyou.dms.vehicle.service.afterSales.preAuthorization;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.OtherMaintainDAO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalotheritemDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovalotheritemPO;

/**
 * 预授权其他费用维护
 * @author Administrator
 *
 */
@Service
public class OtherMaintainServiceImpl implements OtherMaintainService{
@Autowired
OtherMaintainDAO  otherMaintainDAO;

/**
 *  预授权其他费用维护查询
 */
@Override
public PageInfoDto OtherMaintainQuery(Map<String, String> queryParam) {
	// TODO Auto-generated method stub
	return otherMaintainDAO.OtherMaintainQuery(queryParam);
}

/**
 * 查询所有项目名称
 */
@Override
public List<Map> getAll() throws ServiceBizException {
	// TODO Auto-generated method stub
	return otherMaintainDAO.getAll();
}

/**
 * 删除
 */
@Override
public void delete(Long id) {
	TtWrForeapprovalotheritemPO ptPo = TtWrForeapprovalotheritemPO.findById(id);
   	if(ptPo!=null){
	   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
	   ptPo.saveIt();
   	}
	
}

/**
 * 新增
 */
public Long add(TtWrForeapprovalotheritemDTO ptdto) {
	TtWrForeapprovalotheritemPO ptPo=new TtWrForeapprovalotheritemPO();
	    setApplyPo(ptPo,ptdto);
	    return ptPo.getLongId();
}
private void setApplyPo(TtWrForeapprovalotheritemPO ptPo, TtWrForeapprovalotheritemDTO ptdto) {
	   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	      if(!CommonUtils.isNullOrEmpty( getApplyDataBy(ptdto))){
	            throw new ServiceBizException("已存在此费用维护数据！新增失败！");
	        } 
	        else{
	   if(ptdto.getItemCode()!=null){
		   ptPo.setLong("OEM_COMPANY_ID", loginInfo.getCompanyId());
		   ptPo.setString("ITEM_DESC", ptdto.getItemDesc());
		   ptPo.setString("ITEM_CODE",ptdto.getItemCode());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
		   ptPo.setDate("CREATE_DATE", new Date());
		   ptPo.setInteger("IS_DEL",0);
		   ptPo.saveIt();	
	  
	   }else{
		   throw new ServiceBizException("项目名称不能为空，请输入！"); 
	   }
	        }
	      
}
public List<Map> getApplyDataBy(TtWrForeapprovalotheritemDTO ptdto) throws ServiceBizException {
	 LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
    StringBuilder sqlSb = new StringBuilder("  SELECT item_code from TT_WR_FOREAPPROVALOTHERITEM_dcs where 1=1");
    List<Object> params = new ArrayList<>();
    sqlSb.append(" and  item_code=?");
    params.add(ptdto.getItemCode());
    sqlSb.append(" and  IS_DEL=?");
    params.add(OemDictCodeConstants.IS_DEL_00);
    sqlSb.append(" and  OEM_COMPANY_ID=?");
    params.add(loginInfo.getCompanyId());
    List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
    return applyList;
}   

}
