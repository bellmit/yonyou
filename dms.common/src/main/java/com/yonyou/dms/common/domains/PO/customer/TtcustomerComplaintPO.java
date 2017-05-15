package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 客户投诉
 * @author Administrator
 *
 */
@Table("TT_CUSTOMER_COMPLAINT")
@CompositePK({ "DEALER_CODE", "COMPLAINT_NO" })
public class TtcustomerComplaintPO extends BaseModel{

}
