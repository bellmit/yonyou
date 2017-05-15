/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : IDServiceImpl.java
*
* @Author : LuZhen
*
* @Date : 2016年3月8日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年3月8日    LuZhen    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.function.utils.common;

/*
*
* @author LuZhen
* IDServiceImpl
* @date 2016年3月8日
*/

public class IDUtils {
    
    private static final String TASK_ID="T";
    
    private static final String MESSAGE_ID="M";
    
    public static String getMessageId()  {
        return TASK_ID+StringUtils.getRandomUUID().toString();
    }

    public static String getTaskId() {
        return MESSAGE_ID+StringUtils.getRandomUUID().toString();
    }

}
