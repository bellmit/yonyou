
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.manage
*
* @File name : InvoiceYTypeDTO.java
*
* @Author : yangjie
*
* @Date : 2016年12月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年12月19日    yangjie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.manage.domains.DTO.basedata;

import java.io.Serializable;

/**
 * TODO description
 * 
 * @author yangjie
 * @date 2016年12月19日
 */

public class InvoiceYTypeDTO implements Serializable {

    private String code;

    private String name;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public InvoiceYTypeDTO(String code, String name){
        super();
        this.code = code;
        this.name = name;
    }

    public InvoiceYTypeDTO(){
        super();
    }
}
