package com.infoeai.eai.action.ws.technicalsupport.service;

import org.springframework.stereotype.Service;

import com.infoeai.eai.wsServer.tsdealer.TsDealerVO;

@Service
public interface TS01 {
	
	public TsDealerVO[] getTmDealerService(String updateDate) throws Exception;

}
