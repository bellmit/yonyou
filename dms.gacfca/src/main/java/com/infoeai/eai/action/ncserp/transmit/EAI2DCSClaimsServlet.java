package com.infoeai.eai.action.ncserp.transmit;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface EAI2DCSClaimsServlet {
	public void doPost(ServletRequest request,
			  ServletResponse response)throws IOException,ServletException;
}
