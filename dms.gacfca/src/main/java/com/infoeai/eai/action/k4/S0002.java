package com.infoeai.eai.action.k4;

import java.util.List;
import com.infoeai.eai.vo.S0002XmlVO;

public interface S0002 {
	
	public List<S0002XmlVO> getInfo() throws Exception;

	List<S0002XmlVO> updateVoMethod(List<S0002XmlVO> list_s0002) throws Exception;

	
}
