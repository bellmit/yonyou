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
@Table("tt_pre_receive_money")
@CompositePK({ "DEALER_CODE", "PRE_RECEIVE_NO" })
public class TTPreReceiveMoneyPO extends BaseModel{

}
