/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.function
*
* @File name : JsonSerializeException.java
*
* @Author : zhangxianchao
*
* @Date : 2016年2月24日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月24日    zhangxianchao    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.function.exception;

/*
*
* @author zhangxianchao
* JsonSerializeException
* @date 2016年2月24日
*/

public class JsonSerializeException extends RuntimeException {

    /*
     * @author zhangxianchao JsonSerializeException
     * @date 2016年2月24日
     * @param e
     */

    public JsonSerializeException(Exception e){
        super(e);
    }

    /**
     * 
     */
    private static final long serialVersionUID = 7807894278848938989L;

}
