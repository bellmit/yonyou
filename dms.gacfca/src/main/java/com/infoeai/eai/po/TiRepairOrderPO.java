package com.infoeai.eai.po;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

@Table("TI_REPAIR_ORDER_DCS")
@CompositePK({"DEALER_CODE","RO_NO"})
public class TiRepairOrderPO extends OemBaseModel {

}
