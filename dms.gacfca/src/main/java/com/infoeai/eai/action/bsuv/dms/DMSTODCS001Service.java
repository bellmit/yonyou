/**
 * 
 */
package com.infoeai.eai.action.bsuv.dms;

import java.util.List;

import com.infoeai.eai.wsServer.bsuv.lms.DCSTODMS001VO;

/**
 * @author Administrator
 *
 */
public interface DMSTODCS001Service {
	public String handleExecutor(List<DCSTODMS001VO> dtoList) throws Exception;
	
	public void execute(List<DCSTODMS001VO> voList) throws Exception;
}
