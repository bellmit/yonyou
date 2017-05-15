package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 优惠模式定义
 * @author Administrator
 *
 */
@Table("TM_DISCOUNT_MODE")
@CompositePK({"DEALER_CODE","DISCOUNT_MODE_CODE"})
public class TmDiscountModePO extends BaseModel{

}
