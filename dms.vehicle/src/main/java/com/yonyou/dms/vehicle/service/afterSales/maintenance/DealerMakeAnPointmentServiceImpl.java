package com.yonyou.dms.vehicle.service.afterSales.maintenance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.afterSales.maintenance.DealerMakeAnPointmentDao;
import com.yonyou.dms.vehicle.domains.DTO.afterSales.maintenance.TmWxReserverItemDefDTO;
import com.yonyou.dms.vehicle.domains.PO.afterSales.maintenance.TmWxReserverItemDefPO;

/**
 * 微信预约时段空闲度设定
 * @author Administrator
 *
 */
@Service
public class DealerMakeAnPointmentServiceImpl implements DealerMakeAnPointmentService{
	@Autowired
	DealerMakeAnPointmentDao   dealerMakeAnPointmentDao;

	/**
	 * 微信预约时段空闲度设定
	 */
	
	@Override
	public PageInfoDto DealerMakeAnPointmentQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return dealerMakeAnPointmentDao.DealerMakeAnPointmentQuery(queryParam);
	}

	/**
	 * 微信预约时段空闲度设定修改
	 */
	
	public void edit(TmWxReserverItemDefDTO dto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		List<Map> list = dto.getDms_table();
		if(list!=null && list.size()>0){
			for(Map map:list){
				if(map.get("RESERVE_NUM_LIMIT")!=null){
					TmWxReserverItemDefPO tpddPo = TmWxReserverItemDefPO.findById(map.get("RESERVE_DESC"));
					
					tpddPo.setLong("RESERVE_NUM_LIMIT", map.get("RESERVE_NUM_LIMIT"));
					tpddPo.setLong("UPDATE_BY", loginInfo.getUserId());
				//	tpddPo.setString("DEALER_CODE",loginInfo.getDealerCode());
					tpddPo.setDate("UPDATE_DATE", new Date());  
					tpddPo.setInteger("IS_DEL",0);
				    tpddPo.saveIt();
					}else{
						  throw new ServiceBizException("未填写可接受预约的数量，请输入！"); 
					}	
			}
		}
		}
	
}
