package com.yonyou.dms.gacfca;

import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.PoCusWholeClryslerDto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：OEM下发批售客户审批状态
 * @date 2017年1月10日
 * @author Benzc
 *
 */
@Service
public interface OSD0401Coud {
	
	public int getOSD0401(List<PoCusWholeClryslerDto> dtList) throws ServiceBizException;

}
