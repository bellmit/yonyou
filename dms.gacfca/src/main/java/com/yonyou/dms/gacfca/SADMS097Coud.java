package com.yonyou.dms.gacfca;

import java.util.List;

import com.yonyou.dms.DTO.gacfca.BigCustomerAuthorityApprovalDto;
import com.yonyou.dms.function.exception.ServiceBizException;

public interface SADMS097Coud {
    public int getSADMS097(List<BigCustomerAuthorityApprovalDto> dtList) throws ServiceBizException;
}
