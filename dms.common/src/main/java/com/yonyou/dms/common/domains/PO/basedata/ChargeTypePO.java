/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
 * @Author : zhengcong
 *
 * @Date : 2017年3月24日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年3月24日    zhengcong    1.0
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
 * 收费类别PO
* @author zhengcong
* @date 2017年3月24日
 */
@Table("tm_manage_type")
@CompositePK({"DEALER_CODE","MANAGE_SORT_CODE","IS_MANAGING"})
public class ChargeTypePO extends BaseModel{

}
