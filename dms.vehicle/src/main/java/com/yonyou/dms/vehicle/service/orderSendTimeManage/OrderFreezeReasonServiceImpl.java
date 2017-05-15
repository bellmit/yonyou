package com.yonyou.dms.vehicle.service.orderSendTimeManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmOrderfreezereasonPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.vehicle.dao.orderSendTimeManage.OrderFreezeReasonDao;
import com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage.TmOrderfreezereasonDTO;

/**
 * 截停原因维护
 * @author Administrator
 *
 */
@Service
public class OrderFreezeReasonServiceImpl implements OrderFreezeReasonService{
    @Autowired
   OrderFreezeReasonDao    orderFreezeReasonDao;
    /**
     * 截停原因查询
     * @param queryParam
     * @return
     */
	public PageInfoDto FreezeReasonQuery(Map<String, String> queryParam) {
		return orderFreezeReasonDao.OrderFreezeReasonQuery(queryParam);
	}
	
	@Override
	public String getAddOrderReasonId() {
		int fourNo = (int) (Math.random()*9000+1000);//随机4位数
		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");//设置日期格式
		String timeNow = df.format(new Date());// new Date()为获取当前系统时间
		String customerNo = "A"+timeNow+fourNo;
		return customerNo;
	}

	@Override
	public void add(TmOrderfreezereasonDTO dto) {
   	 	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
   	 	Date currentTime = new Date();
		TmOrderfreezereasonPO po = new TmOrderfreezereasonPO();
		po.setString("FREEZE_CODE", dto.getFreezeCode());
		po.setString("FREEZE_REASON", dto.getFreezeReason());
		if(dto.getStatus() != null){
			po.setInteger("STATUS", dto.getStatus());
		}
		po.setLong("CREATE_BY", loginInfo.getUserId());
		po.setTimestamp("CREATE_DATE", currentTime);
		boolean flag = po.saveIt();
	}

	@Override
	public Map<String, Object> findById(Long freezeId) {
		TmOrderfreezereasonPO po = TmOrderfreezereasonPO.findById(freezeId);
		return po.toMap();
	}

	@Override
	public void edit(TmOrderfreezereasonDTO dto) {
   	 	LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
   	 	Date currentTime = new Date();
		TmOrderfreezereasonPO po = TmOrderfreezereasonPO.findById(dto.getFreezeId());
		po.setString("FREEZE_CODE", dto.getFreezeCode());
		po.setString("FREEZE_REASON", dto.getFreezeReason());
		if(dto.getStatus() != null){
			po.setInteger("STATUS", dto.getStatus());
		}
		po.setLong("UPDATE_BY", loginInfo.getUserId());
		po.setTimestamp("UPDATE_DATE", currentTime);
		boolean flag = po.saveIt();
		
	}
}
