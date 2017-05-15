package com.infoeai.eai.action.k4;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public interface k4DcsToSap {

	public void doGet(ServletRequest request, ServletResponse response) throws IOException, ServletException;

	public void doPost(ServletRequest request, ServletResponse response) throws IOException, ServletException;
}
