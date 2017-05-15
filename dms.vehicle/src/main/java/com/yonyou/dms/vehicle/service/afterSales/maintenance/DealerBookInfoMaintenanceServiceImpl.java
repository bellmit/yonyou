package com.yonyou.dms.vehicle.service.afterSales.maintenance;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.maintenance.DealerBookInfoMaintenanceDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.maintenance.TmWxReserveSpecialistDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.maintenance.TmWxReserveSpecialistPO;

/**
 * 经销商微信预约专员维护
 * @author Administrator
 *
 */
@Service
public class DealerBookInfoMaintenanceServiceImpl implements DealerBookInfoMaintenanceService{
	@Autowired
	DealerBookInfoMaintenanceDao  dealerBookInfoMaintenanceDao;
	/**
	 * 经销商微信预约专员查询
	 */
	@Override
	public PageInfoDto DealerBookInfoMaintenanceQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return dealerBookInfoMaintenanceDao.DealerBookInfoMaintenanceQuery(queryParam);
	}
/**
 * 经销商微信预约专员新增
 */
	@Override
	public Long addMaintenanceDealer(TmWxReserveSpecialistDTO ptdto) {
		  TmWxReserveSpecialistPO ptPo=new TmWxReserveSpecialistPO();
		    setApplyPo(ptPo,ptdto);
		    return ptPo.getLongId();
	}
	private void setApplyPo(TmWxReserveSpecialistPO ptPo, TmWxReserveSpecialistDTO ptdto) {
		   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		   if(ptdto.getName()!=null&&ptdto.getTelephone()!=null){
			   ptPo.setString("DEALER_CODE",loginInfo.getDealerCode());
			   ptPo.setString("NAME", ptdto.getName());
			   ptPo.setString("TELEPHONE",ptdto.getTelephone());
			   ptPo.setInteger("STATUS",OemDictCodeConstants.STATUS_ENABLE);
			   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
			   ptPo.setDate("UPDATE_DATE", new Date());
			   ptPo.setLong("CREATE_BY", loginInfo.getUserId());
			   ptPo.setDate("CREATE_DATE", new Date());  
			   ptPo.setInteger("VER",0);
			   ptPo.setInteger("IS_DEL",0);
			   ptPo.setInteger("IS_ARC",0);
			   ptPo.saveIt();
		   }else{
			   throw new ServiceBizException("未填写完整重要信息，请输入！"); 
		   }
	
	}
/**
 * 经销商微信预约专员修改信息
 */
	@Override
	public void edit(Long id, TmWxReserveSpecialistDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmWxReserveSpecialistPO ptPo = TmWxReserveSpecialistPO.findById(id);
		   ptPo.setString("NAME", ptdto.getName());
		   ptPo.setString("TELEPHONE",ptdto.getTelephone());
		   ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
		   ptPo.setDate("UPDATE_DATE", new Date());
		   ptPo.saveIt();
		
	}
/**
 * 经销商微信预约专员信息回显
 */
	@Override
	public TmWxReserveSpecialistPO getMaintenanceDealerById(Long id) {
		return TmWxReserveSpecialistPO.findById(id);
	}
/**
 * 经销商微信预约专员删除
 */
	@Override
	public void delete(Long id) {
		TmWxReserveSpecialistPO ptPo = TmWxReserveSpecialistPO.findById(id);
	   	if(ptPo!=null){
		   ptPo.setInteger("IS_DEL",OemDictCodeConstants.IS_DEL_01);
		   ptPo.saveIt();
	}
	
	}
	
	

}
