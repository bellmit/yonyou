/**
 * 
 */
package com.infoeai.eai.wsServer.DccmAppService;

/**
 * @author Administrator
 *
 */
@SuppressWarnings("serial")
public class SalesConsultantVo implements java.io.Serializable{
	  private java.lang.String userID;

	    private java.lang.String userName;

	    private java.lang.String mobile;

	    private java.lang.String email;

	    private java.lang.String roleID;

	    private java.lang.String roleName;

	    private java.lang.String dealerCode;

	    private java.lang.String createDate;

	    private int userStatus;

	    public SalesConsultantVo() {
	    }

	    public SalesConsultantVo(
	           java.lang.String userID,
	           java.lang.String userName,
	           java.lang.String mobile,
	           java.lang.String email,
	           java.lang.String roleID,
	           java.lang.String roleName,
	           java.lang.String dealerCode,
	           java.lang.String createDate,
	           int userStatus) {
	           this.userID = userID;
	           this.userName = userName;
	           this.mobile = mobile;
	           this.email = email;
	           this.roleID = roleID;
	           this.roleName = roleName;
	           this.dealerCode = dealerCode;
	           this.createDate = createDate;
	           this.userStatus = userStatus;
	    }


	    /**
	     * Gets the userID value for this SalesConsultantVo.
	     * 
	     * @return userID
	     */
	    public java.lang.String getUserID() {
	        return userID;
	    }


	    /**
	     * Sets the userID value for this SalesConsultantVo.
	     * 
	     * @param userID
	     */
	    public void setUserID(java.lang.String userID) {
	        this.userID = userID;
	    }


	    /**
	     * Gets the userName value for this SalesConsultantVo.
	     * 
	     * @return userName
	     */
	    public java.lang.String getUserName() {
	        return userName;
	    }


	    /**
	     * Sets the userName value for this SalesConsultantVo.
	     * 
	     * @param userName
	     */
	    public void setUserName(java.lang.String userName) {
	        this.userName = userName;
	    }


	    /**
	     * Gets the mobile value for this SalesConsultantVo.
	     * 
	     * @return mobile
	     */
	    public java.lang.String getMobile() {
	        return mobile;
	    }


	    /**
	     * Sets the mobile value for this SalesConsultantVo.
	     * 
	     * @param mobile
	     */
	    public void setMobile(java.lang.String mobile) {
	        this.mobile = mobile;
	    }


	    /**
	     * Gets the email value for this SalesConsultantVo.
	     * 
	     * @return email
	     */
	    public java.lang.String getEmail() {
	        return email;
	    }


	    /**
	     * Sets the email value for this SalesConsultantVo.
	     * 
	     * @param email
	     */
	    public void setEmail(java.lang.String email) {
	        this.email = email;
	    }


	    /**
	     * Gets the roleID value for this SalesConsultantVo.
	     * 
	     * @return roleID
	     */
	    public java.lang.String getRoleID() {
	        return roleID;
	    }


	    /**
	     * Sets the roleID value for this SalesConsultantVo.
	     * 
	     * @param roleID
	     */
	    public void setRoleID(java.lang.String roleID) {
	        this.roleID = roleID;
	    }


	    /**
	     * Gets the roleName value for this SalesConsultantVo.
	     * 
	     * @return roleName
	     */
	    public java.lang.String getRoleName() {
	        return roleName;
	    }


	    /**
	     * Sets the roleName value for this SalesConsultantVo.
	     * 
	     * @param roleName
	     */
	    public void setRoleName(java.lang.String roleName) {
	        this.roleName = roleName;
	    }


	    /**
	     * Gets the dealerCode value for this SalesConsultantVo.
	     * 
	     * @return dealerCode
	     */
	    public java.lang.String getDealerCode() {
	        return dealerCode;
	    }


	    /**
	     * Sets the dealerCode value for this SalesConsultantVo.
	     * 
	     * @param dealerCode
	     */
	    public void setDealerCode(java.lang.String dealerCode) {
	        this.dealerCode = dealerCode;
	    }


	    /**
	     * Gets the createDate value for this SalesConsultantVo.
	     * 
	     * @return createDate
	     */
	    public java.lang.String getCreateDate() {
	        return createDate;
	    }


	    /**
	     * Sets the createDate value for this SalesConsultantVo.
	     * 
	     * @param createDate
	     */
	    public void setCreateDate(java.lang.String createDate) {
	        this.createDate = createDate;
	    }


	    /**
	     * Gets the userStatus value for this SalesConsultantVo.
	     * 
	     * @return userStatus
	     */
	    public int getUserStatus() {
	        return userStatus;
	    }


	    /**
	     * Sets the userStatus value for this SalesConsultantVo.
	     * 
	     * @param userStatus
	     */
	    public void setUserStatus(int userStatus) {
	        this.userStatus = userStatus;
	    }

	    private java.lang.Object __equalsCalc = null;
	    public synchronized boolean equals(java.lang.Object obj) {
	        if (!(obj instanceof SalesConsultantVo)) return false;
	        SalesConsultantVo other = (SalesConsultantVo) obj;
	        if (obj == null) return false;
	        if (this == obj) return true;
	        if (__equalsCalc != null) {
	            return (__equalsCalc == obj);
	        }
	        __equalsCalc = obj;
	        boolean _equals;
	        _equals = true && 
	            ((this.userID==null && other.getUserID()==null) || 
	             (this.userID!=null &&
	              this.userID.equals(other.getUserID()))) &&
	            ((this.userName==null && other.getUserName()==null) || 
	             (this.userName!=null &&
	              this.userName.equals(other.getUserName()))) &&
	            ((this.mobile==null && other.getMobile()==null) || 
	             (this.mobile!=null &&
	              this.mobile.equals(other.getMobile()))) &&
	            ((this.email==null && other.getEmail()==null) || 
	             (this.email!=null &&
	              this.email.equals(other.getEmail()))) &&
	            ((this.roleID==null && other.getRoleID()==null) || 
	             (this.roleID!=null &&
	              this.roleID.equals(other.getRoleID()))) &&
	            ((this.roleName==null && other.getRoleName()==null) || 
	             (this.roleName!=null &&
	              this.roleName.equals(other.getRoleName()))) &&
	            ((this.dealerCode==null && other.getDealerCode()==null) || 
	             (this.dealerCode!=null &&
	              this.dealerCode.equals(other.getDealerCode()))) &&
	            ((this.createDate==null && other.getCreateDate()==null) || 
	             (this.createDate!=null &&
	              this.createDate.equals(other.getCreateDate()))) &&
	            this.userStatus == other.getUserStatus();
	        __equalsCalc = null;
	        return _equals;
	    }

	    private boolean __hashCodeCalc = false;
	    public synchronized int hashCode() {
	        if (__hashCodeCalc) {
	            return 0;
	        }
	        __hashCodeCalc = true;
	        int _hashCode = 1;
	        if (getUserID() != null) {
	            _hashCode += getUserID().hashCode();
	        }
	        if (getUserName() != null) {
	            _hashCode += getUserName().hashCode();
	        }
	        if (getMobile() != null) {
	            _hashCode += getMobile().hashCode();
	        }
	        if (getEmail() != null) {
	            _hashCode += getEmail().hashCode();
	        }
	        if (getRoleID() != null) {
	            _hashCode += getRoleID().hashCode();
	        }
	        if (getRoleName() != null) {
	            _hashCode += getRoleName().hashCode();
	        }
	        if (getDealerCode() != null) {
	            _hashCode += getDealerCode().hashCode();
	        }
	        if (getCreateDate() != null) {
	            _hashCode += getCreateDate().hashCode();
	        }
	        _hashCode += getUserStatus();
	        __hashCodeCalc = false;
	        return _hashCode;
	    }

	    // Type metadata
	    private static org.apache.axis.description.TypeDesc typeDesc =
	        new org.apache.axis.description.TypeDesc(SalesConsultantVo.class, true);

	    static {
	        typeDesc.setXmlType(new javax.xml.namespace.QName("http://www.example.org/DccmAppService/", "SalesConsultantVo"));
	        org.apache.axis.description.ElementDesc elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("userID");
	        elemField.setXmlName(new javax.xml.namespace.QName("", "userID"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(false);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("userName");
	        elemField.setXmlName(new javax.xml.namespace.QName("", "userName"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(false);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("mobile");
	        elemField.setXmlName(new javax.xml.namespace.QName("", "mobile"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(false);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("email");
	        elemField.setXmlName(new javax.xml.namespace.QName("", "email"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(false);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("roleID");
	        elemField.setXmlName(new javax.xml.namespace.QName("", "roleID"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(false);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("roleName");
	        elemField.setXmlName(new javax.xml.namespace.QName("", "roleName"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(false);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("dealerCode");
	        elemField.setXmlName(new javax.xml.namespace.QName("", "dealerCode"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(false);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("createDate");
	        elemField.setXmlName(new javax.xml.namespace.QName("", "createDate"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "string"));
	        elemField.setNillable(false);
	        typeDesc.addFieldDesc(elemField);
	        elemField = new org.apache.axis.description.ElementDesc();
	        elemField.setFieldName("userStatus");
	        elemField.setXmlName(new javax.xml.namespace.QName("", "userStatus"));
	        elemField.setXmlType(new javax.xml.namespace.QName("http://www.w3.org/2001/XMLSchema", "int"));
	        elemField.setNillable(false);
	        typeDesc.addFieldDesc(elemField);
	    }

	    /**
	     * Return type metadata object
	     */
	    public static org.apache.axis.description.TypeDesc getTypeDesc() {
	        return typeDesc;
	    }

	    /**
	     * Get Custom Serializer
	     */
	    public static org.apache.axis.encoding.Serializer getSerializer(
	           java.lang.String mechType, 
	           java.lang.Class _javaType,  
	           javax.xml.namespace.QName _xmlType) {
	        return 
	          new  org.apache.axis.encoding.ser.BeanSerializer(
	            _javaType, _xmlType, typeDesc);
	    }

	    /**
	     * Get Custom Deserializer
	     */
	    public static org.apache.axis.encoding.Deserializer getDeserializer(
	           java.lang.String mechType, 
	           java.lang.Class _javaType,  
	           javax.xml.namespace.QName _xmlType) {
	        return 
	          new  org.apache.axis.encoding.ser.BeanDeserializer(
	            _javaType, _xmlType, typeDesc);
	    }

}
