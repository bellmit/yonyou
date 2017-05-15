package com.infoeai.eai.action.bsuv.lms;

import com.infoeai.eai.wsServer.bsuv.lms.EcCheckFeedBackVO;

public interface DCSTOLMS007 {
	public EcCheckFeedBackVO[] sendBSUVCueCheckResult(String from,String to) throws Exception;
}
