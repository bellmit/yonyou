package com.infoeai.eai.action.bsuv.lms;

import com.infoeai.eai.wsServer.bsuv.lms.ResaleOrderDcsToLmsVO;

public interface DCSTOLMS009 {
	public ResaleOrderDcsToLmsVO[] reSaleOrderInfo(String FROM,String TO) throws Exception;
}
