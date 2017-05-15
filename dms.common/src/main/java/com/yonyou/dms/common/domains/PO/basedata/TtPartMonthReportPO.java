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
@Table("TT_PART_MONTH_REPORT")
@CompositePK({"DEALER_CODE","REPORT_YEAR","REPORT_MONTH","STORAGE_CODE","PART_BATCH_NO","PART_NO"})
public class TtPartMonthReportPO extends BaseModel {

}
