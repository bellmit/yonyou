package com.yonyou.dms.framework.activejdbc;

import org.javalite.activejdbc.Model;
import org.javalite.activejdbc.annotations.IdName;
import org.javalite.activejdbc.annotations.Table;

@IdName("CUSTOMER_ID")
@Table("customer")
public class Customer extends Model{
	
}
