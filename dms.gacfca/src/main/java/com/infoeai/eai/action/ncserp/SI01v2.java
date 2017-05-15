package com.infoeai.eai.action.ncserp;

import java.util.List;
import java.util.Map;

import org.w3c.dom.Document;

import com.yonyou.dms.common.domains.DTO.SiTestDto;

/**
 * 功能说明：改功能主要是对用webService传过来的结果分别调用各自接口的处理方法
 * @author Benzc
 * @date 2017年4月24日
 */
public interface SI01v2 {
	public String ddUExecute(Object request) throws Exception;
	
	public String execute(Object request) throws Exception;
	
	public String setXMLToVO(List<Map<String, String>> xmlList)
			throws Exception;
	public String setClaimsXMLToVO(Document doc) throws Exception;

	public void testSi01v2(Map<String, String> messageMap)throws Exception;
}
