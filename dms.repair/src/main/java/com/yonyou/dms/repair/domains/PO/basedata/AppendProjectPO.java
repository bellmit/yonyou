
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : AppendProjectPO.java
*
* @Author : zhongsw
*
* @Date : 2016年8月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月19日    zhongsw    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.PO.basedata;

import org.javalite.activejdbc.annotations.CompositePK;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
* 附加项目定义表
* @author zhongsw
* @date 2016年8月19日
*/
@Table("tm_add_item")
@CompositePK({"DEALER_CODE","ADD_ITEM_CODE"})
public class AppendProjectPO extends BaseModel {

}
