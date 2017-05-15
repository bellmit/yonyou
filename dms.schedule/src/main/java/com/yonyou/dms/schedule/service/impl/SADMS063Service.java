package com.yonyou.dms.schedule.service.impl;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.common.domains.DTO.basedata.SADMS063Dto;




public interface SADMS063Service {
    public List<Map> querySubmitReportRemain(String beginDate,String endDate);
    public LinkedList<SADMS063Dto> getSADMS063();
}
