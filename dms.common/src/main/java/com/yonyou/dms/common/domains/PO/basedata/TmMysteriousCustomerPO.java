package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
/**
* @author wangliang
* @date 2017年2月14日
*/

@Table("tm_mysterious_customer")
@CompositePK({"DEALER_CODE","MYSTERIOUS_ID"})
public class TmMysteriousCustomerPO extends BaseModel{
	
}
