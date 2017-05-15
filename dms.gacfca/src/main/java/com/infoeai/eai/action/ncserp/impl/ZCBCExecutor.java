package com.infoeai.eai.action.ncserp.impl;

import com.infoeai.eai.action.ncserp.intf.ISAPOutBoundVO;

public interface ZCBCExecutor {
	public String execute(ISAPOutBoundVO dto) throws Exception;

	public boolean isValid(ISAPOutBoundVO dto) throws Exception;
}
