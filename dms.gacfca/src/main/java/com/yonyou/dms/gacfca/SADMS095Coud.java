package com.yonyou.dms.gacfca;

import com.yonyou.dms.common.domains.DTO.basedata.InvoiceRefundDTO;
import com.yonyou.dms.function.exception.ServiceBizException;


public interface SADMS095Coud {
    public String getSADMS095(InvoiceRefundDTO irdto) throws ServiceBizException;
}
