
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RepairOrderPO.java
*
* @Author : ZhengHe
*
* @Date : 2016年8月10日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月10日    ZhengHe    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 会员配件项目流水表
* TODO description
* @author chenwei
* @date 2017年4月18日
 */

@Table("TT_MEMBER_PART_FLOW")
@CompositePK({"DEALER_CODE","ITEM_ID"})
public class TtMemberPartFlowPO extends BaseModel{

}
