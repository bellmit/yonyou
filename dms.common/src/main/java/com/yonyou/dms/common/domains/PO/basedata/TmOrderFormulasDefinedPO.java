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
@Table("TM_ORDER_FORMULAS_DEFINED")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TmOrderFormulasDefinedPO extends BaseModel {

}
