package com.infoeai.eai.action.bsuv.lms;

import com.infoeai.eai.wsServer.bsuv.lms.ResaleOrderDcsToLmsVO;

public interface DCSTOLMS013 {
	public ResaleOrderDcsToLmsVO[] orderSalesInfo(String FROM,String TO) throws Exception;
}
