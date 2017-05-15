package com.yonyou.dms.demo.domains.PO.sample;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;
/**
 * 
* User MODEL
* @author zhangxc
* @date 2016年6月30日
 */
@Table("TMP_tm_users")
@IdName("USER_ID")
public class DemoUserPO extends BaseModel{
}
