package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 销售订单-服务项目
 * @author Benzc
 * @date 2017年3月28日
 *
 */
@Table("TT_SO_SERVICE")
@CompositePK({"ITEM_ID","DEALER_CODE"})
public class TtSoServicePO extends BaseModel{

}
