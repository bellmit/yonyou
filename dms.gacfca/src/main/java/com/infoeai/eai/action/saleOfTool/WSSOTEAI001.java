package com.infoeai.eai.action.saleOfTool;


import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * DCS->EAI
 * 发送展厅销售人员信息接口
 * @author luoyang
 *
 */
public interface WSSOTEAI001 {
	
	public void doGet(ServletRequest req, ServletResponse res) throws ServletException, Exception;
	
	public void doPost(ServletRequest req, ServletResponse resp) throws ServletException, Exception;

}
