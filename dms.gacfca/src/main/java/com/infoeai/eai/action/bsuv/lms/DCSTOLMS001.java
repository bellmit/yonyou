package com.infoeai.eai.action.bsuv.lms;

import java.util.Date;

import com.infoeai.eai.wsServer.bsuv.lms.basicManagerService.MaterialVO;

public interface DCSTOLMS001 {
	public MaterialVO[]  execute(Date from ,Date to ) throws Exception;
}
