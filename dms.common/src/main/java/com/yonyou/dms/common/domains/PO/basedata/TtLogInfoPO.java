/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author sqh
 *
 */
@Table("TT_LOG_INFO")
@CompositePK({"ITEM_ID","DEALER_CODE","MAIN_BILL_NO","BUS_TABLE_NAME"}) //联合主键
public class TtLogInfoPO extends BaseModel{

}
