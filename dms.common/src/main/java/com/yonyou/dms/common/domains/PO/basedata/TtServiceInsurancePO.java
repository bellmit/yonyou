package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 服务-保险
 * @author Benzc
 * @date 2017年4月15日
 *
 */
@Table("TT_SERVICE_INSURANCE")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtServiceInsurancePO extends BaseModel{

}
