package com.infoeai.eai.action.ncserp.impl;

import com.infoeai.eai.action.ncserp.intf.ISAPOutBoundVO;

public interface ZPDFExecutor {
	public String execute(ISAPOutBoundVO dto) throws Exception;

	public boolean isValid(ISAPOutBoundVO dto) throws Exception;
}
