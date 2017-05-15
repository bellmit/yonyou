package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.VehicleShippingDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SADMS001 {

    public int getSADMS001(List<VehicleShippingDto> voList,String dealerCode) throws ServiceBizException;

}
