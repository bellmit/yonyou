package com.yonyou.dcs.gacfca;

import java.util.List;

import com.infoservice.dms.cgcsl.vo.PoCusWholeRepayClryslerVO;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @ClassName: SADCS015_1Cloud 
 * @Description: TODO(大客户报备返利审批数据下发) 
 * @author xuqinqin
 */
public interface SADCS015_1Cloud extends BaseCloud{
	
	public String sendData(String wsNo, String dealerCode) throws ServiceBizException;
	
	public List<PoCusWholeRepayClryslerVO> getVoDataList(String wsNo, String dealerCode) throws ServiceBizException;
}
