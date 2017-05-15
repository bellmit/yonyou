package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 配件销售报价单
* TODO description
* @author chenwei
* @date 2017年5月4日
 */
@Table("TT_SALES_QUOTE")
@CompositePK({"DEALER_CODE","SALES_QUOTE_NO"})
public class TtSalesQuotePO extends BaseModel {

}
