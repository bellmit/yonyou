package com.infoeai.eai.common.com.parsexml;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.log4j.Logger;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class DocumentXml {
	/**
	 * 公共xml解析方法 return:List<String>
	 * 
	 * @throws DocumentException
	 **/

	private static Logger logger = Logger.getLogger(DocumentXml.class);

	public List<Map<String, String>> parserXml(String fileName) {
		// 定义返回结果集
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);
			NodeList employees = document.getChildNodes();// 获取XML内容
			for (int i = 0; i < employees.getLength(); i++) {
				Node employee = employees.item(i);
				NodeList employeeInfo = employee.getChildNodes();// 获取XML子菜单
				for (int j = 0; j < employeeInfo.getLength(); j++) {
					Map<String, String> map = new HashMap<String, String>();
					Node node = employeeInfo.item(j);
					NodeList employeeMeta = node.getChildNodes();// 获取XML数据
					if (employeeMeta.getLength() != 0) {// 判断itme是否为空，空值不插入List
						for (int k = 0; k < employeeMeta.getLength(); k++) {

							if (employeeMeta.item(k).getNodeName().trim().equals("")
									|| employeeMeta.item(k).getNodeName().startsWith("#")
									|| employeeMeta.item(k).getNodeName() == null) {
								continue;
							}

							map.put(employeeMeta.item(k).getNodeName(), employeeMeta.item(k).getTextContent());

						}
						returnList.add(map);
					}
				}
			}
			logger.info("=================== XML解析完毕==========================");
		} catch (Throwable e) {
			logger.info("=================== XML解析异常==========================");
			logger.error(e.getMessage(), e);
		}
		return returnList;
	}

	/*
	 * K4接收xml文件解析返回接口名称
	 */
	public String k4GetInterfaceCode(String inputString) {
		// 定义返回结果集
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		String interfaceCode = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
			Document document = db.parse(is);
			NodeList employees = document.getChildNodes();// 获取XML内容
			for (int i = 0; i < employees.getLength(); i++) { // root菜单
				Node employee = employees.item(i);
				NodeList employeeInfo = employee.getChildNodes();// 获取XML子菜单
				for (int j = 0; j < employeeInfo.getLength(); j++) {// 二级菜单
					Map<String, String> map = new HashMap<String, String>();
					if (j == 0) {
						interfaceCode = employeeInfo.item(j).getTextContent();
						continue;
					}
				}
			}
			System.out.println("解析完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return interfaceCode;
	}

	/*
	 * K4接收xml文件解析
	 */
	public List<Map<String, String>> k4ParserXml(String inputString) {
		// 定义返回结果集
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		try {

			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			InputStream is = new ByteArrayInputStream(inputString.getBytes("UTF-8"));
			Document document = db.parse(is);
			NodeList employees = document.getChildNodes();// 获取XML内容
			for (int i = 0; i < employees.getLength(); i++) { // root菜单
				Node employee = employees.item(i);
				NodeList employeeInfo = employee.getChildNodes();// 获取XML子菜单
				for (int j = 0; j < employeeInfo.getLength(); j++) {// 二级菜单
					if (j == 0) {
						continue;
					}
					Map<String, String> map = new HashMap<String, String>();
					Node node = employeeInfo.item(j);
					NodeList employeeMeta = node.getChildNodes();// 获取XML数据
					if (employeeMeta.getLength() != 0) {// 判断itme是否为空，空值不插入List
						for (int k = 0; k < employeeMeta.getLength(); k++) {

							if (employeeMeta.item(k).getNodeName().trim().equals("")
									|| employeeMeta.item(k).getNodeName().startsWith("#")
									|| employeeMeta.item(k).getNodeName() == null) {
								continue;
							}

							map.put(employeeMeta.item(k).getNodeName(), employeeMeta.item(k).getTextContent());

						}
						returnList.add(map);
					}
				}
			}
			System.out.println("解析完毕");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return returnList;
	}

	public List<Map<String, String>> parserXml(ByteArrayInputStream ins) {
		// 定义返回结果集
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(ins);
			NodeList employees = document.getChildNodes();// 获取XML内容
			for (int i = 0; i < employees.getLength(); i++) {
				Node employee = employees.item(i);
				NodeList employeeInfo = employee.getChildNodes();// 获取XML子菜单
				for (int j = 0; j < employeeInfo.getLength(); j++) {
					Map<String, String> map = new HashMap<String, String>();
					Node node = employeeInfo.item(j);
					NodeList employeeMeta = node.getChildNodes();// 获取XML数据
					if (employeeMeta.getLength() != 0) {// 判断itme是否为空，空值不插入List
						for (int k = 0; k < employeeMeta.getLength(); k++) {

							if (employeeMeta.item(k).getNodeName().trim().equals("")
									|| employeeMeta.item(k).getNodeName().startsWith("#")
									|| employeeMeta.item(k).getNodeName() == null) {
								continue;
							}

							map.put(employeeMeta.item(k).getNodeName(), employeeMeta.item(k).getTextContent());

						}
						returnList.add(map);
					}
				}
			}
			logger.info("=================== XML解析完毕==========================");
		} catch (Throwable e) {
			logger.info("=================== XML解析异常==========================");
			logger.error(e.getMessage(), e);
		}
		return returnList;
	}

	public List<Map<String, String>> parserXml(File fileName) {
		// 定义返回结果集
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();
			Document document = db.parse(fileName);
			NodeList employees = document.getChildNodes();// 获取XML内容
			for (int i = 0; i < employees.getLength(); i++) {
				Node employee = employees.item(i);
				NodeList employeeInfo = employee.getChildNodes();// 获取XML子菜单
				for (int j = 0; j < employeeInfo.getLength(); j++) {
					Map<String, String> map = new HashMap<String, String>();
					Node node = employeeInfo.item(j);
					NodeList employeeMeta = node.getChildNodes();// 获取XML数据
					if (employeeMeta.getLength() != 0) {// 判断itme是否为空，空值不插入List
						for (int k = 0; k < employeeMeta.getLength(); k++) {

							if (employeeMeta.item(k).getNodeName().trim().equals("")
									|| employeeMeta.item(k).getNodeName().startsWith("#")
									|| employeeMeta.item(k).getNodeName() == null) {
								continue;
							}

							map.put(employeeMeta.item(k).getNodeName(), employeeMeta.item(k).getTextContent());

						}
						returnList.add(map);
					}
				}
			}
			logger.info("=================== XML解析完毕==========================");
		} catch (Throwable e) {
			logger.info("=================== XML解析异常==========================");
			logger.error(e.getMessage(), e);
		}
		return returnList;
	}

	public Document getDocument(File fileName) {
		Document document = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			document = db.parse(fileName);

			System.out.println("获取Document");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return document;

	}

	public Document getDocument(ByteArrayInputStream fileName) {
		Document document = null;
		try {
			DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder db = dbf.newDocumentBuilder();

			document = db.parse(fileName);

			System.out.println("获取Document");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		return document;

	}

}
