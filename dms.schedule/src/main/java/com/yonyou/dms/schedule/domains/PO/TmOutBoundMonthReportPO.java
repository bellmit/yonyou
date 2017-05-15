/**
 * 
 */
package com.yonyou.dms.schedule.domains.PO;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

/**
 * @author Administrator
 *
 */
@Table("tm_out_bound_month_report")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TmOutBoundMonthReportPO extends Model{

}
