/**
 * 
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @author Administrator
 *
 */

@Table("TM_MODEL_LABOUR")
@CompositePK({ "MODEL_LABOUR_CODE","DEALER_CODE" })
public class TmModelLabourPO extends BaseModel {

}
