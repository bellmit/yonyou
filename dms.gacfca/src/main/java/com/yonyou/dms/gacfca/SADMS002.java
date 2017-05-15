package com.yonyou.dms.gacfca;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：车辆验收信息上报
 * 
 * @author Benzc
 * @date 2017年1月5日
 * 
 */
public interface SADMS002 {
	
	public String getSADMS002(String seNo,List<Map> items)throws ServiceBizException;

}
