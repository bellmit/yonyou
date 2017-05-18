package com.yonyou.dms.gacfca;



import com.yonyou.dms.function.exception.ServiceBizException;

public interface SADMS096Coud {
 //   public void modifyUser(Long id) throws ServiceBizException;
    
    public String getSADMS096(String applyNo,String userName,String remark) throws ServiceBizException;
}
