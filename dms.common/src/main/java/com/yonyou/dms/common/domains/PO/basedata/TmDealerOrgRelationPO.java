package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

/**
 * 销售订单表组
 * @author DC
 * 2017-1-16
 */
@Table("TM_DEALER_ORG_RELATION")
@IdName("RELATION_ID")
public class TmDealerOrgRelationPO extends OemBaseModel{

}
