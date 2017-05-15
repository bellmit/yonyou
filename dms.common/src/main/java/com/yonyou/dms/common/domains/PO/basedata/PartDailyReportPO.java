package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 配件日报表
 * 
 * @author chenwei
 * @date 2017年4月24日
 */
@Table("TT_PART_DAILY_REPORT")
@CompositePK({"DEALER_CODE","REPORT_DATE,STORAGE_CODE"})
public class PartDailyReportPO extends BaseModel{

}
