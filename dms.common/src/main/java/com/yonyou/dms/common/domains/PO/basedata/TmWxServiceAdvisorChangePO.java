package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 
 * @author Administrator
 *
 */
@Table("TM_WX_SERVICE_ADVISOR_CHANGE")
@CompositePK({"VIN","DEALER_CODE"})
public class TmWxServiceAdvisorChangePO extends BaseModel{

}
