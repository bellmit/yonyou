package com.infoeai.eai.action.bsuv.lms;

import com.infoeai.eai.wsServer.bsuv.lms.CloseOutOrderDcsToLmsVO;

public interface DCSTOLMS010 {
	public CloseOutOrderDcsToLmsVO[] orderPeiCheSuccessState(String FROM,String TO) throws Exception;
}
