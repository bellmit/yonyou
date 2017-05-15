/*
* Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
*
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : cmol.common.framework
*
* @File name : DALException.java
*
* @Author : zhangxianchao
*
* @Date : 2016年2月14日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年2月14日    zhangxianchao    1.0
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
* DALException
* @date 2016年2月14日
*/

public class DALException extends RuntimeException {

  
    	
    /*
    *
    * @author zhangxianchao
    * DALException
    * @date 2016年2月14日
    * @param string
    */
    	
    public DALException(Exception e){
       super(e);
    }

    /*
    *
    * @author zhangxianchao
    * DALException
    * @date 2016年2月14日
    * @param string
    */
    	
    public DALException(String str){
        super(str);
    }

    /*
    *
    * @author zhangxianchao
    * DALException
    * @date 2016年2月15日
    * @param string
    * @param e
    */
    	
    public DALException(String str, Exception e){
       super(str,e);
    }

    private static final long serialVersionUID = 1L;

}
