
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.vehicle
*
* @File name : InspectPdiPO.java
*
* @Author : yll
*
* @Date : 2016年9月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月22日    yll    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.vehicle.domains.PO.basedata;

import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

import com.yonyou.dms.framework.domain.OemBaseModel;

/**
 * PDI检查单
 * 
 * @author yll
 * @date 2016年9月22日
 */
@Table("tt_pdi_inspect")
@IdName("PDI_INSPECT_ID")
public class InspectPdiPO extends OemBaseModel {

}
