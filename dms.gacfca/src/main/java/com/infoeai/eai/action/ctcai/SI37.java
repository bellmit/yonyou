package com.infoeai.eai.action.ctcai;

import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.PO.basedata.TmpCancelOrderDcsPO;

public interface SI37 {
	
	public void execute(String in) throws Exception;
	
	public List<Map<String, String>> readXml(String strXml);
	
	public boolean insertTmpCancelOrderDcsPO(List<TmpCancelOrderDcsPO> list);

}
