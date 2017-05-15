package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 限价车系及维修类型
 * 
 * @author chenwei
 * @date 2017年3月27日
 */
@Table("TM_LIMIT_SERIES_DATAINFO")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class LimitSeriesDatainfoPO extends BaseModel{

}
