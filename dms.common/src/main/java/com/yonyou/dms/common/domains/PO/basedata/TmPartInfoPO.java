package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * @description 配件基本信息Po
 * @author Administrator
 *
 */
@Table("TM_PART_INFO")
@CompositePK({"DEALER_CODE","PART_NO"})
public class TmPartInfoPO extends BaseModel {

}
