package com.yonyou.dms.schedule.service.impl;

import java.util.LinkedList;

import com.yonyou.dms.schedule.domains.DTO.PlanTestDriveDTO;

public interface PlanTestDriveDetailService {
	public LinkedList<PlanTestDriveDTO> getTestDriveDetail();
}
