/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author yangjie
 *
 */
@Table("Tt_Part_Period_Report")
@CompositePK({"DEALER_CODE","REPORT_MONTH","REPORT_YEAR","STORAGE_CODE","PART_BATCH_NO","PART_NO"})
public class TtPartPeriodReportPO extends BaseModel {

}
