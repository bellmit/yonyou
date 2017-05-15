
/** 
*Copyright 2016 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.repair
*
* @File name : RepairPartsDTO.java
*
* @Author : rongzoujie
*
* @Date : 2016年8月31日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2016年8月31日    rongzoujie    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.repair.domains.DTO.basedata;

import com.yonyou.dms.framework.service.excel.ExcelColumnDefine;
import com.yonyou.dms.function.domains.DTO.DataImportDto;

/**
* TODO description
* @author rongzoujie
* @date 2016年8月31日
*/

public class RepairPartsDTO extends DataImportDto{
	@ExcelColumnDefine(value=6)
	private String labourCodeDesc;//维修项目代码
	private Long labourCode;
	private Long partId;
    private String storeCode;
    @ExcelColumnDefine(value=1)
    private String partNo;//配件代码
    @ExcelColumnDefine(value=2)
    private String partName;//配件名称
    @ExcelColumnDefine(value=3)
    private Double partQuantity;//配件数量
    @ExcelColumnDefine(value=5)
    private Double salesPrice;//销售价
    private Long labourId;//维修项目ID
    @ExcelColumnDefine(value=4)
    private Double stockCount;//库存数量
    @ExcelColumnDefine(value=7)
    private String modelLabourCode;//维修车型组代码
    
    public String getModelLabourCode() {
		return modelLabourCode;
	}


	public void setModelLabourCode(String modelLabourCode) {
		this.modelLabourCode = modelLabourCode;
	}


	public Long getPartId() {
        return partId;
    }

    
    public void setPartId(Long partId) {
        this.partId = partId;
    }


    
    public String getStoreCode() {
        return storeCode;
    }


    
    public void setStoreCode(String storeCode) {
        this.storeCode = storeCode;
    }


    
    public String getPartNo() {
        return partNo;
    }


    
    public void setPartNo(String partNo) {
        this.partNo = partNo;
    }


    
    public String getPartName() {
        return partName;
    }


    
    public void setPartName(String partName) {
        this.partName = partName;
    }
    
    public Double getSalesPrice() {
        return salesPrice;
    }


    
    public void setSalesPrice(Double salesPrice) {
        this.salesPrice = salesPrice;
    }


    
    public Long getLabourId() {
        return labourId;
    }


    
    public void setLabourId(Long labourId) {
        this.labourId = labourId;
    }


    
    public Double getStockCount() {
        return stockCount;
    }


    
    public void setStockCount(Double stockCount) {
        this.stockCount = stockCount;
    }


    
    public Double getPartQuantity() {
        return partQuantity;
    }

    public String getLabourCodeDesc() {
		return labourCodeDesc;
	}


	public void setLabourCodeDesc(String labourCodeDesc) {
		this.labourCodeDesc = labourCodeDesc;
	}

    
    public void setPartQuantity(Double partQuantity) {
        this.partQuantity = partQuantity;
    }
    
    public Long getLabourCode() {
  		return labourCode;
  	}


  	public void setLabourCode(Long labourCode) {
  		this.labourCode = labourCode;
  	}

}
