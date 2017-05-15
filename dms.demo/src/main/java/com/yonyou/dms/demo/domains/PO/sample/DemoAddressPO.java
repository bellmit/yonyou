package com.yonyou.dms.demo.domains.PO.sample;

import org.javalite.activejdbc.annotations.BelongsTo;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
/**
 * 
* ADDRESS MODLE
* @author zhangxc
* @date 2016年6月30日
 */
@Table("TMP_TM_ADDRESS")
@IdName("ADDRESS_ID")
@BelongsTo(parent = DemoUserPO.class, foreignKeyName = "user_id")
public class DemoAddressPO extends BaseModel{
}
