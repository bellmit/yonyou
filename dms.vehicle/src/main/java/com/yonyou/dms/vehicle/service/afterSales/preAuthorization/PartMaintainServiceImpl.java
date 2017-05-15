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
import com.yonyou.dms.vehicle.dao.afterSales.preAuthorization.PartMaintainDAO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovalptDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovalptPO;

/**
 * 预授权监控配件维护
 * @author Administrator
 *
 */
@Service
public class PartMaintainServiceImpl implements PartMaintainService{
	@Autowired
	PartMaintainDAO  partMaintainDAO;
/**
 * 预授权监控配件维护查询
 */
	@Override
	public PageInfoDto PartMaintainQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return partMaintainDAO.PartMaintainQuery(queryParam);
	}
	/**
	 * 删除
	 */
@Override
public void delete(Long id) {
	TtWrForeapprovalptPO ptPo = TtWrForeapprovalptPO.findById(id);
   	if(ptPo!=null){
	   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
	   ptPo.saveIt();
   	}
}
/**
 * 查询所有配件代码
 */
@Override
public List<Map> getAll(Map<String, String> queryParam) {
	// TODO Auto-generated method stub
	return partMaintainDAO.getAll(queryParam);
}
/**
 * 新增
 */


public Long add(TtWrForeapprovalptDTO ptdto) {
	TtWrForeapprovalptPO ptPo=new TtWrForeapprovalptPO();
	    setApplyPo(ptPo,ptdto);
	    return ptPo.getLongId();
}
private void setApplyPo(TtWrForeapprovalptPO ptPo, TtWrForeapprovalptDTO ptdto) {
	   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
	      if(!CommonUtils.isNullOrEmpty( getApplyDataBy(ptdto))){
	            throw new ServiceBizException("已存在此 预授权监控配件维护数据！新增失败！");
	        } 
	        else{
	   if(ptdto.getPartCode()!=null&&ptdto.getPartName()!=null){
		   ptPo.setLong("OEM_COMPANY_ID", loginInfo.getCompanyId());
		   ptPo.setString("PART_CODE", ptdto.getPartCode());
		   ptPo.setString("PART_NAME",ptdto.getPartName());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
		   ptPo.setDate("CREATE_DATE", new Date());
		   ptPo.setInteger("IS_DEL",0);
		   ptPo.saveIt();	
	  
	   }else{
		   throw new ServiceBizException("未填写完整重要信息，请输入！"); 
	   }
	        }
	      
}
public List<Map> getApplyDataBy(TtWrForeapprovalptDTO ptdto) throws ServiceBizException {
    StringBuilder sqlSb = new StringBuilder("  SELECT part_code from TT_WR_FOREAPPROVALPT_dcs where 1=1");
    List<Object> params = new ArrayList<>();
    sqlSb.append(" and  part_code=?");
    params.add(ptdto.getPartCode());
    List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
    return applyList;
}   






}
