package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * @Description:GCS导入车辆下发接口（DCS -> DMS）
 * GCS完成召回车辆导入DCS后，DCS更改召回车辆完成状态并下发DMS，
 * DMS接收到GCS完成召回标示时，取消召回车辆在DMS端召回提示
 * @author xuqinqin 
 */
public interface SADCS076Cloud  extends BaseCloud{

	public String sendData(List<Map<String, String>> bizParams) throws ServiceBizException;
	
}
