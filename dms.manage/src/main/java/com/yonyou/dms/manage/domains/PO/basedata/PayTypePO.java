package com.yonyou.dms.manage.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
/**
 * 
 * @author Benzc
 * @date 2016年12月21日
 */
@Table("TM_PAY_TYPE")
@CompositePK({"DEALER_CODE","PAY_TYPE_CODE"})
public class PayTypePO  extends BaseModel{

}
