package com.yonyou.dms.vehicle.service.afterSales.basicDataMgr;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.basicDataMgr.LabourMaintainDAO;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.basicDataMgr.TtWrForeapprovallabDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.basicDataMgr.TtWrForeapprovallabPO;

/**
 * 预授权维修项目维护
 * @author Administrator
 *
 */
@Service
public class LabourMaintainServiceImpl implements LabourMaintainService{
	@Autowired
	LabourMaintainDAO  labourMaintainDAO;

	/**
	 * 预授权维修项目维护查询
	 */
	@Override
	public PageInfoDto labourMaintainQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return labourMaintainDAO.labourMaintainQuery(queryParam);
	}
/**
 * 查询所有车系
 */
	@Override
	public List<Map> getAllCheXing(Map<String, String> queryParams) throws ServiceBizException {
		// TODO Auto-generated method stub
		return labourMaintainDAO.getAllCheXing(queryParams);
	}
	
	/**
	 * 删除预授权维修项目
	 */
@Override
public void delete(Long id) {
	TtWrForeapprovallabPO ptPo = TtWrForeapprovallabPO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
	   	}
}
/**
 * 通过查询工时信息新增
 */
	@Override
	public List<Map> getAll(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return labourMaintainDAO.getAll(queryParam);
	}
/*@Override
public Long add(TtWrForeapprovallabDTO ptdto) {
	// TODO Auto-generated method stub
	return ;
}*/

/**
 * 新增
 * @param ptdto
 * @return
 */
public Long add(TtWrForeapprovallabDTO ptdto) {
	TtWrForeapprovallabPO ptPo=new TtWrForeapprovallabPO();
		    setApplyPo(ptPo,ptdto);
		    return ptPo.getLongId();
}
	private void setApplyPo(TtWrForeapprovallabPO ptPo, TtWrForeapprovallabDTO ptdto) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		   if(ptdto.getWrgroupId()!=null&&ptdto.getLabourCode()!=null&&ptdto.getLabourDesc()!=null){
			   ptPo.setLong("OEM_COMPANY_ID",loginInfo.getCompanyId());
			   ptPo.setLong("WRGROUP_ID", ptdto.getWrgroupId());
			   ptPo.setString("LABOUR_CODE",ptdto.getLabourCode());
			   ptPo.setString("LABOUR_DESC",ptdto.getLabourDesc());
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());  
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.saveIt();
		   }else{
			   throw new ServiceBizException("信息不完整，不能进行新增！"); 
		   }
	
	}

	
	
	
}
