
/** 
*Copyright 2017 Yonyou Auto Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.part
*
* @File name : ObsoleteDataReportDTO.java
*
* @Author : Administrator
*
* @Date : 2017年4月18日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月18日    Administrator    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/

package com.yonyou.dms.part.domains.DTO.basedata;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * TODO description
 * 
 * @author Administrator
 * @date 2017年4月18日
 */

public class ObsoleteDataReportDTO {

    private String                          contacts;

    private String                          phone;

    private String                          address;

    private List<ObsoleteDataReportListDTO> addressList;

    
    /**
    * @author Administrator
    * @date 2017年4月19日
    * @return
    * (non-Javadoc)
    * @see java.lang.Object#toString()
    */
    	
    @Override
    public String toString() {
        return "ObsoleteDataReportDTO [contacts=" + contacts + ", phone=" + phone + ", address=" + address
               + ", addressList=" + addressList + "]";
    }


    /**
     * @return the contacts
     */
    public String getContacts() {
        return contacts;
    }

    /**
     * @param contacts the contacts to set
     */
    public void setContacts(String contacts) {
        this.contacts = contacts;
    }

    /**
     * @return the phone
     */
    public String getPhone() {
        return phone;
    }

    /**
     * @param phone the phone to set
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the addressList
     */
    public List<ObsoleteDataReportListDTO> getAddressList() {
        return addressList;
    }

    /**
     * @param addressList the addressList to set
     */
    public void setAddressList(List<ObsoleteDataReportListDTO> addressList) {
        this.addressList = addressList;
    }

}
