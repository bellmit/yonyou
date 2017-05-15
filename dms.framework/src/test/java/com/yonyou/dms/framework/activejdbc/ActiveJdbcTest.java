package com.yonyou.dms.framework.activejdbc;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.javalite.activejdbc.Base;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActiveJdbcTest {
	final static Logger logger = LoggerFactory.getLogger(ActiveJdbcTest.class);
	
	@BeforeClass
	public static void init(){
		Base.open("com.mysql.jdbc.Driver", "jdbc:mysql://localhost/fordtest", "root", "root");
		
	}   
	/**
	 * 测试createIt 方法
	 */
	@Test
    public void testCreateIt() {
		Customer customer = Customer.createIt("CUSTOMER_NAME", "zxc_createit", "AGE", "21","SALARY", "303.22","BIRTHDAY", "1987-2-24","EXISTS_TIME", "1987-2-24 12:32:23");
		Assert.assertNotNull(customer.getId());
		Customer c1 = Customer.findById(customer.getId());
		Assert.assertNotNull(c1);
		
		Customer customer2 = null;
		try {
			customer2 = Customer.createIt("CUSTOMER_NAME", "zxc_createit", "AGE", 21,"SALARY", 303.22,"BIRTHDAY", new SimpleDateFormat("yyyy-mm-dd").parse("1987-01-27"),"EXISTS_TIME", new Date());
			Assert.assertNotNull(customer2.getId());
			Customer c2 = Customer.findById(customer2.getId());
	        Assert.assertNotNull(c2.getId());
		} catch (ParseException e) {
			logger.error(e.getMessage(),e);
		}
    }
	
	/**
	 * 测试create 方法,对于Create 方法不会返回ID
	 */
	/*@Test
    public void testCreate() {
		
		boolean isSucess = Customer.("CUSTOMER_NAME", "zxc_create", "AGE", "21","SALARY", "303.22","BIRTHDAY", "1987-2-24","EXISTS_TIME", "1987-2-24 12:32:23");
		if(isSucess){
			
		}else{
			
		}
		Assert.assertNotNull(customer.getId());
		Customer c1 = Customer.findById(customer.getId());
		Assert.assertNotNull(c1);
		Customer customer2 = null;
		try {
			customer2 = Customer.create("CUSTOMER_NAME", "zxc_create", "AGE", 21,"SALARY", 303.22,"BIRTHDAY", new SimpleDateFormat("yyyy-mm-dd").parse("1987-01-27"),"EXISTS_TIME", new Date());
			Assert.assertNotNull(customer2.getId());
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Customer c2 = Customer.findById(customer2.getId());
		Assert.assertNotNull(c2.getId());
    }*/
	
	/**
	 * 测试create 方法,对于Create 方法不会返回ID
	 * @throws ParseException 
	 */
	@Test
    public void testSave() throws ParseException {
		Customer customer = new Customer();
		customer.setString("CUSTOMER_NAME", "zxc_save");
		customer.setInteger("AGE", 21);
		customer.setDouble("SALARY", 303.22);
		customer.setDate("BIRTHDAY", new SimpleDateFormat("yyyy-mm-dd").parse("1987-2-24"));
		customer.setDate("EXISTS_TIME", new SimpleDateFormat("yyyy-mm-dd HH:mm:ss").parse("1987-2-24 12:32:23"));
		boolean isSucess = customer.save();
		if(isSucess){
			logger.debug("id:"+customer.getId());
		}else{
			logger.debug(customer.errors().toString());
		}
		
    }
	
	
	@AfterClass
	public static void deleteAll(){
		
	}
	
}
