package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 配件追溯
* TODO description
* @author chenwei
* @date 2017年5月1日
 */
@Table("TM_PART_BACK")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TmPartBackPO extends BaseModel{

}
