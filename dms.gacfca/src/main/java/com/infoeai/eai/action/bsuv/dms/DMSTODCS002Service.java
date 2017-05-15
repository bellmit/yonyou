/**
 * 
 */
package com.infoeai.eai.action.bsuv.dms;

import java.util.List;

import com.infoeai.eai.wsServer.bsuv.lms.DMSTODCS002VO;

/**
 * @author Administrator
 *
 */
public interface DMSTODCS002Service {
	public String handleExecutor(List<DMSTODCS002VO> dtoList) throws Exception;
	
	public void execute(List<DMSTODCS002VO> voList) throws Exception;
}
