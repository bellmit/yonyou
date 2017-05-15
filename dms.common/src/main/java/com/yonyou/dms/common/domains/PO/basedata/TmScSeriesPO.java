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
@Table("tm_sc_series")
@CompositePK({"BRAND_CODE","DEALER_CODE","SERIES_CODE"})
public class TmScSeriesPO extends BaseModel {

}
