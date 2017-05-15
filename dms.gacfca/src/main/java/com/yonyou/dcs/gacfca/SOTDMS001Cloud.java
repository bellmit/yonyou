package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;

import com.yonyou.dms.DTO.gacfca.PtDlyInfoDto;

public interface SOTDMS001Cloud {

	LinkedList<PtDlyInfoDto> getDataList();

	String execute();

}
