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

public class DictDto {
	private Integer code_id;
	private String code_cn_desc;
	private String code_en_desc;
	private Integer status;

	public String getCode_en_desc() {
		return code_en_desc;
	}

	public void setCode_en_desc(String code_en_desc) {
		this.code_en_desc = code_en_desc;
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

	public String getCode_cn_desc() {
		return code_cn_desc;
	}

	public void setCode_cn_desc(String code_cn_desc) {
		this.code_cn_desc = code_cn_desc;
	}

}
