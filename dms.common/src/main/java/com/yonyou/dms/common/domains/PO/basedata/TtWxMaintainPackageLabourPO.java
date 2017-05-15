package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description :预约保养维修项目 
 * @author Administrator
 *
 */
@Table("TT_WX_MAINTAIN_PACKAGE_LABOUR")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtWxMaintainPackageLabourPO extends BaseModel {

}
