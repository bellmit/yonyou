package com.yonyou.dms.schedule.service.impl;

import java.util.LinkedList;

import com.yonyou.dms.schedule.domains.DTO.PlanTestDriveMonthDTO;

public interface PlanTestDriveMonthReportService {
	public LinkedList<PlanTestDriveMonthDTO> getTestDriveMonthReport();
}
