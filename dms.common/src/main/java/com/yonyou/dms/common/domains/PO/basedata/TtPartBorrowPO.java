package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
/**
 * 借进登记主表
* TODO description
* @author yujiangheng
* @date 2017年5月11日
 */
@Table("TT_PART_BORROW")
@CompositePK({"BORROW_NO","DEALER_CODE"})
public class TtPartBorrowPO extends BaseModel{
//BORROW_NO, DEALER_CODE
}
