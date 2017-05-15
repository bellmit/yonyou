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
@Table("TM_PROPOSAL_TRACK")
@CompositePK({ "DEALER_CODE", "ITEM_ID" })
public class ProposalTrackPO extends BaseModel{

}
