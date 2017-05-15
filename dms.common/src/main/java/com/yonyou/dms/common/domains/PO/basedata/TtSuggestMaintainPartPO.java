/** 
 *Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
 * This software is published under the terms of the Yonyou Software
 * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 *
 * @Author : zhengcong
 *
 * @Date : 2017年5月3日
 *
----------------------------------------------------------------------------------
 *     Date       Who       Version     Comments
 * 1. 2017年5月3日    zhengcong    1.0
 *
 *
 *
 *
----------------------------------------------------------------------------------
 */
package com.yonyou.dms.common.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.BaseModel;

/**
 * 工单维修建议-配件PO
 * 
 * @author zhengcong
 * @date 2017年3月21日
 */
@Table("TT_SUGGEST_MAINTAIN_PART")
@IdName("SUGGEST_MAINTAIN_PART_ID")
public class TtSuggestMaintainPartPO extends BaseModel {

}
