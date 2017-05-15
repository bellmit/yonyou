package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.common.domains.DTO.basedata.TtCustomerVehicleListDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerVehicleListPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：上报：创建客户信息（置换需求）DMS新增和更新客户信息（置换需求）DMS更新
 * 
 * @author
 *
 */
public interface SOTDCS005 {
	public int getSOTDCS005(String cusno, String status,List<TtCustomerVehicleListPO> PO,List<TtCustomerVehicleListDTO> DTO) throws ServiceBizException;
}
