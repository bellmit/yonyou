/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author yangjie
 *	 整车库存-移库单
 */
@Table("TT_VS_STOCK_TRANSFER")
@CompositePK({"DEALER_CODE","ST_NO"})
public class TtVsStockTransferPO extends BaseModel {

}
