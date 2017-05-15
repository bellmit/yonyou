package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 服务-牌照办理结果
 * @author Benzc
 * @date 2017年4月15日
 */
@Table("TT_SERVICE_NUMBER_PLATE")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtServiceNumberPlatePO extends BaseModel{

}
