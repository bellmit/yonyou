package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.OtherFeeDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * @description 附加项目下发
 * @author Administrator
 *
 */
public interface CLDMS011 {
	public int getCLDMS011(LinkedList<OtherFeeDTO> volist,String dealerCode) throws ServiceBizException;
}
