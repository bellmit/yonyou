
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.function
*
* @File name : SystemParamConstants.java
*
* @Author : jcsi
*
* @Date : 2016年9月22日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年9月22日    jcsi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.function.common;



/**
* 基本参数  
* @author jcsi
* @date 2016年9月22日
*/

public class SystemParamConstants {
    
    //参数类型（缺省税率）
    public static final Long PARAM_TYPE_BASICOM=new Long("1019");
    
    //参数CODE(缺省税率)
    public static final String BASICOM_CODE="basicom_taxrate";
    
    //缺省仓库
    public static final Long PARAM_TYPE_STORAGE=new Long("1023");
    
    //缺省仓库（参数code）
    public static final String STORAGE_CODE="Repair_warehouse";
    
    //工单应收额是否自动结清
    public static final Long PARAM_TYPE_AMOUNTRECEIVABLE=new Long("1027");
    
    public static final String REPAIR_AMOUNTRECEIVABLE="Repair_amountReceivable";
    
    //提前多少分钟以上进厂,视为提前进厂?
    public static final Long PARAM_TYPE_AHEADMINUTEINFACTORY=new Long("1036");
    
    //提前进厂CODe
    public static final String AHEADMINUTEINFACTORY_CODE="Repair_aheadMinuteInfactory";
      
    //延迟进厂Code
    public static final String DELAYMINUTEINFACTORY_CODE="Repair_delayMinuteInfactory";
    
    //参数类型（缺省税率）
    public static final Long PARAM_TYPE_VEHICLE_HANDCAR=new Long("1020");
    
    //参数CODE(缺省税率)
    public static final String VEHICLE_HANDCAR_CODE="vehicle_handcar";

}
