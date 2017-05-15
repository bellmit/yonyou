package com.yonyou.dcs.gacfca;

import java.util.HashMap;
import java.util.LinkedList;

import com.yonyou.dms.DTO.gacfca.SEDMS054DTO;

/**
 * 
 * Title:SEDCS054Cloud
 * Description: 克莱斯勒明检和神秘客业务需求BRD文档  下发
 * @author DC
 * @date 2017年4月10日 下午8:28:55
 */
public interface SEDCS054Cloud {
	
	public String execute() throws Exception;
	
	public LinkedList<SEDMS054DTO> getDataList() throws Exception;
	
	public HashMap<String,String> sendData() throws Exception;
}
