package com.infoeai.eai.action.bsuv.lms;

import com.infoeai.eai.wsServer.bsuv.lms.TrailCustVO;

public interface DCSTOLMS008 {
	public TrailCustVO[] sendBSUVCustFollowUpInfo(String from, String to) throws Exception;
}
