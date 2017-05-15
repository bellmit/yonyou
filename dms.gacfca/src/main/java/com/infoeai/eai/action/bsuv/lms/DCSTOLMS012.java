package com.infoeai.eai.action.bsuv.lms;

import com.infoeai.eai.wsServer.bsuv.lms.CloseOutOrderDcsToLmsVO;

public interface DCSTOLMS012 {
	public CloseOutOrderDcsToLmsVO[] orderCheckRuKuState(String FROM,String TO) throws Exception;
}
