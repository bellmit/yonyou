package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;


/**
* TODO description
* @author LiGaoqi
* @date 2017年2月8日
*/
@Table("TM_LABOUR_PRICE")
@CompositePK({"DEALER_CODE","LABOUR_PRICE_CODE"})
public class TmLabourPricePO extends BaseModel{

}
