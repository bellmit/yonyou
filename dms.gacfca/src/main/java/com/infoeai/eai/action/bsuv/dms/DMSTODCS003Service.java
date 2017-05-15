/**
 * 
 */
package com.infoeai.eai.action.bsuv.dms;

import java.util.List;

import com.infoeai.eai.wsServer.bsuv.lms.DMSTODCS003VO;

/**
 * @author Administrator
 *
 */
public interface DMSTODCS003Service {
	public String handleExecutor(List<DMSTODCS003VO> dtoList) throws Exception;
	
	public void execute(List<DMSTODCS003VO> voList) throws Exception;
}
