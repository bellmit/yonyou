package com.infoeai.eai.action.ncserp.impl;

import com.infoeai.eai.action.ncserp.intf.ISAPOutBoundVO;

public interface ZSWRExecutor {
	public String execute(ISAPOutBoundVO dto) throws Exception;

	public boolean isValid(ISAPOutBoundVO dto) throws Exception;
}
