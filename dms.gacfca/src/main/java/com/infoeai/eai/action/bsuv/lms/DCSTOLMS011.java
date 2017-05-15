package com.infoeai.eai.action.bsuv.lms;

import com.infoeai.eai.wsServer.bsuv.lms.CloseOutOrderDcsToLmsVO;

public interface DCSTOLMS011 {
	public CloseOutOrderDcsToLmsVO[] orderDepartChuKuState(String FROM,String TO) throws Exception;
}
