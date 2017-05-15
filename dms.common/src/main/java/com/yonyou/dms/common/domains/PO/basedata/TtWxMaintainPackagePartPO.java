package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 预约保养套餐配件 
 * @author Administrator
 *
 */
@Table("TT_WX_MAINTAIN_PACKAGE_PART")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtWxMaintainPackagePartPO extends BaseModel {

}
