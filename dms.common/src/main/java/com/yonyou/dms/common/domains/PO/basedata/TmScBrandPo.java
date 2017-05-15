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
@Table("tm_sc_brand")
@CompositePK({"BRAND_CODE","DEALER_CODE"})
public class TmScBrandPo extends BaseModel  {

}
