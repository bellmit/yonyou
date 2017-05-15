/**
 * 
 */
package com.yonyou.dms.common.domains.PO.customer;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author sqh
 *
 */
@Table("TM_INS_PROPOSAL")
@CompositePK({ "DEALER_CODE", "PROPOSAL_CODE" })
public class InsProposalPO extends BaseModel{

}
