package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.PayingBankDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SADCS114Cloud
 * Description: 合作银行下发
 * @author DC
 * @date 2017年4月25日 下午6:36:47
 */
public interface SADCS114Cloud {
	
	public String execute(Long bankId) throws ServiceBizException;
	
	public LinkedList<PayingBankDTO> getDataList(Long bankId) throws ServiceBizException;

}
