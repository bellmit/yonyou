package com.yonyou.dms.gacfca;

import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.ServiceVehicleDTO;

/**
 * @description 车主车辆新增车辆同步获取厂端数据
 * @author Administrator
 *
 */
public interface SEDMS004Coud {
	LinkedList<ServiceVehicleDTO> getSEDMS004(String dealerCode,String vin,String timeOut);
}
