package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

/**
 * 销售订单表组
 * @author DC
 * 2017-1-16
 */
@Table("TM_ORDER_CANCEL_REMARKS")
@IdName("CANCEL_REMARKS_ID")
public class TmOrderCancelRemarksPO extends OemBaseModel{

}
