/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.web
*
* @File name : DictDto.java
*
* @Author : rongzoujie
*
* @Date : 2016年7月19日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年7月19日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.framework.domains.DTO.baseData;


/**
 * DCS常量缓存DTO
 * @author 夏威
 * 2017-2-16
 */
public class OemDictDto {
	private Integer code_id;
	private String type_name;
	private String code_desc;
	private Integer status;
	
	public String getType_name() {
		return type_name;
	}

	public void setType_name(String type_name) {
		this.type_name = type_name;
	}

	public String getCode_desc() {
		return code_desc;
	}

	public void setCode_desc(String code_desc) {
		this.code_desc = code_desc;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getCode_id() {
		return code_id;
	}

	public void setCode_id(Integer code_id) {
		this.code_id = code_id;
	}


}
