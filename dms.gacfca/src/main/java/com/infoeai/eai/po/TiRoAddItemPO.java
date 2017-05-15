package com.infoeai.eai.po;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

@Table("TI_RO_ADD_ITEM_DCS")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class TiRoAddItemPO extends OemBaseModel {

}
