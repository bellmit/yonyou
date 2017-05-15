package com.infoeai.eai.action.ncserp.impl;

import java.util.List;

import com.infoeai.eai.vo.SAPInboundVO;

public interface ZPODSAPExecutor {
	public List<SAPInboundVO> getInfo() throws Exception;
	public List<SAPInboundVO> updateVoMethod(List<SAPInboundVO> list_zrgs) throws Exception;
}
