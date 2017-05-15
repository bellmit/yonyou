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



@Table("tm_brand")
@CompositePK({"DEALER_CODE","BRAND_CODE"})
public class TmBrandPo extends BaseModel {

}
