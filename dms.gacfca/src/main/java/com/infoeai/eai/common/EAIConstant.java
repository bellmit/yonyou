/**
 * Copyright (c) 2007-2009 Infoservicee Corp. 2007-2009,All Rights Reserved.
 * This software is published under the Infoservice EAI team * License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 *
 * @File name:  InterfaceConstant.java
 * @Create on:  2013-5-21
 * @Author   :  dengweili 
 *
 * @ChangeList
 * ---------------------------------------------------
 * Date         Editor              ChangeReasons
 *
 *
 */
package com.infoeai.eai.common;

/**
 * 接口常量
 * @author dengweili 
 * 20130521
 *
 */
public class EAIConstant {
	//swt接口名
	public static final String INTERFACT_SI01 = "SI01";//swt->dcs工厂订单车辆状态跟踪-01
	public static final String INTERFACT_SI02 = "SI02";//swt->dcs工厂订单车辆状态跟踪-02
	public static final String INTERFACT_SI03 = "SI03";//dcs->swt销售订单导入
	
	//ctcai接口名
	public static final String INTERFACT_SI04 = "SI04";//dcs->ctcai经销商返利明细导入
	public static final String INTERFACT_SI05 = "SI05";//ctcai->dcs车辆返利使用结果回传
	public static final String INTERFACT_SI06 = "SI06";//ctcai->dcs红票返利使用结果回传

	//e-link接口名
	public static final String INTERFACT_PARTSMASTER = "SI07";//elink->dcs配件主数据信息
	public static final String INTERFACT_SALESXN = "SI08";//elink->dcs货运单及发票信息
	public static final String INTERFACT_SUPERSESSION = "SI09";//elink->dcs配件替换件信息
	public static final String INTERFACT_GSPDISC = "SI10";//elink->dcs配件价格信息
	
	//TXT文件解析类
	public static final String SWT_PARSE01_TXT = "com.infoeai.eai.common.parsetxt.SWTParse01";//针对接口si01情况一、si03
	public static final String SWT_PARSE02_TXT = "com.infoeai.eai.common.parsetxt.SWTParse02";//针对接口si02情况二
	public static final String CTCAI_PARSE01_TXT = "com.infoeai.eai.common.parsetxt.CTCAIParse01";//针对接口si04，si05
	public static final String CTCAI_PARSE02_TXT = "com.infoeai.eai.common.parsetxt.CTCAIParse02";//针对接口si06
	
	//XML文件解析类
	public static final String XML_PARSE_PARTSMASTER = "com.infoeai.eai.common.parsexml.PartsMasterDataParse";//配件主数据接口解析类
	public static final String XML_PARSE_SALESXN = "com.infoeai.eai.common.parsexml.SalesxnParse";//货运单及发票信息接口解析类
	public static final String XML_PARSE_SUPERSESSION = "com.infoeai.eai.common.parsexml.SupersessionParse";//配件替换件解析类
	public static final String XML_PARSE_GSPDISC = "com.infoeai.eai.common.parsexml.GspdiscParse";//配件价格信息
	
	//生产文件状态：0-成功，1-失败
	public static final int WRITE_SUCCESS = 0;
	public static final int WRITE_FAIL = 1;
	
	//ISAPOutBound处理返回结果
	public static final String DEAL_SUCCESS = "02";//处理成功
	public static final String DEAL_FAIL = "01";//处理失败
	
	public static final int ERROR_STACK_MAX_LENGTH =3000;
}
