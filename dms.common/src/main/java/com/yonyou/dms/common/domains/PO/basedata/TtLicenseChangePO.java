package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* @description 车牌变更记录
* @author Administrator
*/
@Table("tt_license_change")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtLicenseChangePO extends BaseModel{

}
