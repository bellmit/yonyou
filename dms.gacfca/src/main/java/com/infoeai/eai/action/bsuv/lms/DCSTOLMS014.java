package com.infoeai.eai.action.bsuv.lms;

import com.infoeai.eai.wsServer.bsuv.lms.CloseOutOrderDcsToLmsVO;

public interface DCSTOLMS014 {
	public CloseOutOrderDcsToLmsVO[] dealerSure(String FROM,String TO) throws Exception;
}
