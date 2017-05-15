
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.retail
*
* @File name : FinanceShutOrderDTO.java
*
* @Author : Administrator
*
* @Date : 2017年2月15日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年2月15日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.retail.domains.DTO.ordermanage;

import java.util.List;

/**
 * TODO description
 * 
 * @author Administrator
 * @date 2017年2月15日
 */

public class FinanceShutOrderDTO {

    private List<FinanceShutTableDTO> intentList;
    private String    soNo;
    private String    soNos;

    public String getSoNos() {
		return soNos;
	}

	public void setSoNos(String soNos) {
		this.soNos = soNos;
	}

	public List<FinanceShutTableDTO> getIntentList() {
        return intentList;
    }

    public void setIntentList(List<FinanceShutTableDTO> intentList) {
        this.intentList = intentList;
    }

    
    public String getSoNo() {
        return soNo;
    }

    
    public void setSoNo(String soNo) {
        this.soNo = soNo;
    }


}
