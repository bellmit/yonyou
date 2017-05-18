package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppUSwapDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS014Coud {
    public int getSOTDMS014(List<TiAppUSwapDto> dtList) throws ServiceBizException;
}
