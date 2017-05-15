package com.infoeai.eai.action.ncserp.intf;

public interface ISAPExecutor {
	public String execute(ISAPOutBoundVO dto) throws Exception;

	public boolean isValid(ISAPOutBoundVO dto) throws Exception;
	
}
