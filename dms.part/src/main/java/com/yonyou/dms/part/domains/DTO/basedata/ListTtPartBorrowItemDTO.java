package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;
import java.util.List;

public class ListTtPartBorrowItemDTO {
    private List<TtPartBorrowItemDTO>  dms_Borrow_Regi;
    private String borrowNo;
    private String customerName;
    private String customerCode;
    private Date borrowDate;
    private Double borrowTotalAmount;
    private String items;
    
   

    
    @Override
    public String toString() {
        return "ListTtPartBorrowItemDTO [dms_Borrow_Regi=" + dms_Borrow_Regi + ", borrowNo=" + borrowNo
               + ", customerName=" + customerName + ", customerCode=" + customerCode + ", borrowDate=" + borrowDate
               + ", borrowTotalAmount=" + borrowTotalAmount + ", items=" + items + "]";
    }




    public String getItems() {
        return items;
    }



    
    public void setItems(String items) {
        this.items = items;
    }



    public Double getBorrowTotalAmount() {
        return borrowTotalAmount;
    }

    
    public void setBorrowTotalAmount(Double borrowTotalAmount) {
        this.borrowTotalAmount = borrowTotalAmount;
    }

    public List<TtPartBorrowItemDTO> getDms_Borrow_Regi() {
        return dms_Borrow_Regi;
    }
    
    public void setDms_Borrow_Regi(List<TtPartBorrowItemDTO> dms_Borrow_Regi) {
        this.dms_Borrow_Regi = dms_Borrow_Regi;
    }
    
    public String getBorrowNo() {
        return borrowNo;
    }
    
    public void setBorrowNo(String borrowNo) {
        this.borrowNo = borrowNo;
    }
    
    public String getCustomerName() {
        return customerName;
    }
    
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }
    
    public String getCustomerCode() {
        return customerCode;
    }
    
    public void setCustomerCode(String customerCode) {
        this.customerCode = customerCode;
    }
    
    public Date getBorrowDate() {
        return borrowDate;
    }
    
    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }
    
}
