package com.infoeai.eai.action.ncserp.impl;

import java.util.List;

import com.infoeai.eai.vo.SAPInboundVO;

public interface ZRGSSAPExecutor {
	public List<SAPInboundVO> getInfo() throws Exception;
	public List<SAPInboundVO> updateVoMethod(List<SAPInboundVO> resultObj) throws Exception;
}
