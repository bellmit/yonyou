package com.yonyou.dms.part.domains.DTO.stockmanage;
import java.util.List;
/**
 * 
* TODO description
* @author yujiangheng
* @date 2017年4月26日
 */
public class ListPartAllocateInItemDto {
    /*
     * 
        String allocateInNo ：TT_PART_ALLOCATE_IN.ALLOCATE_IN_NO");//调拨入库单号
        String lockUser ：TT_PART_ALLOCATE_IN.LOCK_USER");//锁定人
         String handler ：TT_PART_ALLOCATE_IN.HANDLER");//经手人
               String customerName ：TT_PART_ALLOCATE_IN.CUSTOMER_NAME");//供应商名称
        String customerCode ：TT_PART_ALLOCATE_IN.CUSTOMER_CODE");//供应商代码
          String remark ：TT_PART_ALLOCATE_IN.REMARK");//备注
        String isOutNet :TT_PART_ALLOCATE_IN.IS_NET_TRANSFER");//是否网内调拨
        String isIdleAllocation：TT_PART_ALLOCATE_IN.IS_IDLE_ALLOCATION");//是否呆滞调拨
        String stockInDate：TT_PART_ALLOCATE_IN.STOCK_IN_DATE");//入库日期
        String stockInVoucher ：TT_PART_ALLOCATE_IN.STOCK_IN_VOUCHER");//入库凭证
        
        
        String status ：TT_PART_ALLOCATE_IN.UPDATE_STATUS");//状态
        String allocateOutNo :ALLOCATE_OUT_NO");//调拨出库单号
         String credence ；TT_PART_ALLOCATE_IN.CREDENCE");//付款凭证
        String fromEntiy ：FROM_ENTITY");//来自哪个供应商（CustomerCode）
        String oldAllocateNo ：FACTORY_ALLOCATE_OUT_NO");//旧的调拨单号
        String befoeTaxAmount ：TT_PART_ALLOCATE_IN.BEFOE_TAX_AMOUNT"); //入库不含税金额
        String totalAmount：TT_PART_ALLOCATE_IN.TOTAL_AMOUNT");  // 增加字段 总的金额
        String isPayoff ：TT_PART_ALLOCATE_IN.IS_PAYOFF");//是否付讫
     */
    private String items;
   private  List<PartAllocateInItemDto> dms_part_allocate_in;
   private String allocateInNo;//调拨入库单号
   private String customerCode;//供应商代码
   private String customerName;//供应商名称
   private String stockInDate;//入库日期
   private String stockInVoucher;//入库凭证
   private String handler;//经手人
   private String lockUser;//锁定人
   private String isIdleAllocation;//是否呆滞调拨
   private String isNetTransfer;//是否网内调拨
   private String remark;//备注
   
   private String status;
   private String allocateOutNo;
   private String oldAllocateNo;
   private String credence;
   private String fromEntiy;
  
   private String befoeTaxAmount;
   private String totalAmount;
   private String isPayoff;
   
  

@Override
public String toString() {
    return "ListPartAllocateInItemDto [items=" + items + ", dms_part_allocate_in=" + dms_part_allocate_in
           + ", allocateInNo=" + allocateInNo + ", customerCode=" + customerCode + ", customerName=" + customerName
           + ", stockInDate=" + stockInDate + ", stockInVoucher=" + stockInVoucher + ", handler=" + handler
           + ", lockUser=" + lockUser + ", isIdleAllocation=" + isIdleAllocation + ", isNetTransfer=" + isNetTransfer
           + ", remark=" + remark + ", status=" + status + ", allocateOutNo=" + allocateOutNo + ", oldAllocateNo="
           + oldAllocateNo + ", credence=" + credence + ", fromEntiy=" + fromEntiy + ", befoeTaxAmount="
           + befoeTaxAmount + ", totalAmount=" + totalAmount + ", isPayoff=" + isPayoff + "]";
}


public String getItems() {
    return items;
}


public void setItems(String items) {
    this.items = items;
}

public String getHandler() {
    return handler;
}


public void setHandler(String handler) {
    this.handler = handler;
}


public String getLockUser() {
    return lockUser;
}


public void setLockUser(String lockUser) {
    this.lockUser = lockUser;
}


public String getStatus() {
    return status;
}


public void setStatus(String status) {
    this.status = status;
}


public String getAllocateOutNo() {
    return allocateOutNo;
}


public void setAllocateOutNo(String allocateOutNo) {
    this.allocateOutNo = allocateOutNo;
}


public String getOldAllocateNo() {
    return oldAllocateNo;
}


public void setOldAllocateNo(String oldAllocateNo) {
    this.oldAllocateNo = oldAllocateNo;
}


public String getCredence() {
    return credence;
}


public void setCredence(String credence) {
    this.credence = credence;
}


public String getFromEntiy() {
    return fromEntiy;
}


public void setFromEntiy(String fromEntiy) {
    this.fromEntiy = fromEntiy;
}


public String getBefoeTaxAmount() {
    return befoeTaxAmount;
}


public void setBefoeTaxAmount(String befoeTaxAmount) {
    this.befoeTaxAmount = befoeTaxAmount;
}


public String getTotalAmount() {
    return totalAmount;
}


public void setTotalAmount(String totalAmount) {
    this.totalAmount = totalAmount;
}


public String getIsPayoff() {
    return isPayoff;
}


public void setIsPayoff(String isPayoff) {
    this.isPayoff = isPayoff;
}



public List<PartAllocateInItemDto> getDms_part_allocate_in() {
    return dms_part_allocate_in;
}



public void setDms_part_allocate_in(List<PartAllocateInItemDto> dms_part_allocate_in) {
    this.dms_part_allocate_in = dms_part_allocate_in;
}


public String getAllocateInNo() {
    return allocateInNo;
}

public void setAllocateInNo(String allocateInNo) {
    this.allocateInNo = allocateInNo;
}

public String getCustomerCode() {
    return customerCode;
}

public void setCustomerCode(String customerCode) {
    this.customerCode = customerCode;
}

public String getCustomerName() {
    return customerName;
}

public void setCustomerName(String customerName) {
    this.customerName = customerName;
}







public String getStockInDate() {
    return stockInDate;
}



public void setStockInDate(String stockInDate) {
    this.stockInDate = stockInDate;
}


public String getStockInVoucher() {
    return stockInVoucher;
}

public void setStockInVoucher(String stockInVoucher) {
    this.stockInVoucher = stockInVoucher;
}

public String getIsIdleAllocation() {
    return isIdleAllocation;
}

public void setIsIdleAllocation(String isIdleAllocation) {
    this.isIdleAllocation = isIdleAllocation;
}

public String getIsNetTransfer() {
    return isNetTransfer;
}

public void setIsNetTransfer(String isNetTransfer) {
    this.isNetTransfer = isNetTransfer;
}

public String getRemark() {
    return remark;
}

public void setRemark(String remark) {
    this.remark = remark;
}
   
   
   
    
}
