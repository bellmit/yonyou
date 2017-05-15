package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.TiAppUSalesQuotasDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SOTDMS017 {
    public int getSOTDMS017(List<TiAppUSalesQuotasDto> dtList) throws ServiceBizException;
}
