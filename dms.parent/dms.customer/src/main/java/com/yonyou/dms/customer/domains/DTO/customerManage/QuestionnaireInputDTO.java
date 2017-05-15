package com.yonyou.dms.customer.domains.DTO.customerManage;

import java.util.List;
import java.util.Map;

@SuppressWarnings("rawtypes")
public class QuestionnaireInputDTO {
	private String questionnaireName; // 问卷选择
	private String traceStatus; // 跟踪状态
	private String taskRemark; // 备注
	private List<Map> dms_table2; // 问卷表
	private List<Map> dms_table3; // 跟踪信息表

	public String getQuestionnaireName() {
		return questionnaireName;
	}

	public void setQuestionnaireName(String questionnaireName) {
		this.questionnaireName = questionnaireName;
	}

	public String getTraceStatus() {
		return traceStatus;
	}

	public void setTraceStatus(String traceStatus) {
		this.traceStatus = traceStatus;
	}

	public String getTaskRemark() {
		return taskRemark;
	}

	public void setTaskRemark(String taskRemark) {
		this.taskRemark = taskRemark;
	}

	public List<Map> getDms_table2() {
		return dms_table2;
	}

	public void setDms_table2(List<Map> dms_table2) {
		this.dms_table2 = dms_table2;
	}

	public List<Map> getDms_table3() {
		return dms_table3;
	}

	public void setDms_table3(List<Map> dms_table3) {
		this.dms_table3 = dms_table3;
	}
}
