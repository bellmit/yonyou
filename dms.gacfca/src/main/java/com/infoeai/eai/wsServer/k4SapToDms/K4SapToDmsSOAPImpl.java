/**
 * K4SapToDmsSOAPImpl.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.infoeai.eai.wsServer.k4SapToDms;

import java.util.List;
import java.util.Map;

import com.infoeai.eai.action.k4.S0001;
import com.infoeai.eai.action.k4.S0003;
import com.infoeai.eai.action.k4.S0004;
import com.infoeai.eai.action.k4.S0005;
import com.infoeai.eai.action.k4.S0006;
import com.infoeai.eai.action.k4.S0007;
import com.infoeai.eai.action.k4.S0010;
import com.infoeai.eai.action.k4.S0011;
import com.infoeai.eai.action.k4.S0012;
import com.infoeai.eai.action.k4.S0013;
import com.infoeai.eai.action.k4.S0014;
import com.infoeai.eai.action.k4.S0015;
import com.infoeai.eai.action.k4.S0016;
import com.infoeai.eai.common.com.parsexml.DocumentXml;
import com.infoeai.eai.vo.S0001VO;
import com.infoeai.eai.vo.S0003VO;
import com.infoeai.eai.vo.S0004VO;
import com.infoeai.eai.vo.S0005VO;
import com.infoeai.eai.vo.S0006VO;
import com.infoeai.eai.vo.S0007VO;
import com.infoeai.eai.vo.S0010VO;
import com.infoeai.eai.vo.S0011VO;
import com.infoeai.eai.vo.S0012VO;
import com.infoeai.eai.vo.S0013VO;
import com.infoeai.eai.vo.S0014VO;
import com.infoeai.eai.vo.S0015VO;
import com.infoeai.eai.vo.S0016VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;

public class K4SapToDmsSOAPImpl implements com.infoeai.eai.wsServer.k4SapToDms.K4SapToDms_PortType {

	public java.lang.String sendDataToDMS(java.lang.String sapData) throws java.rmi.RemoteException {

		DocumentXml docXML = new DocumentXml();

		String outPut = null;

		try {

			List<Map<String, String>> dataList = docXML.k4ParserXml(sapData);

			String interFaceCode = docXML.k4GetInterfaceCode(sapData);

			if ("S0001".equals(interFaceCode)) {
				S0001 s1 = (S0001)ApplicationContextHelper.getBeanByType(S0001.class);
				List<S0001VO> voList = s1.setXMLToVO(dataList);
				List<returnVO> retVoList = s1.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0003".equals(interFaceCode)) {
				S0003 s3 = (S0003)ApplicationContextHelper.getBeanByType(S0003.class);
				List<S0003VO> voList = s3.setXMLToVO(dataList);
				List<returnVO> retVoList = s3.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0004".equals(interFaceCode)) {
				S0004 s4 = (S0004)ApplicationContextHelper.getBeanByType(S0004.class);
				List<S0004VO> voList = s4.setXMLToVO(dataList);
				List<returnVO> retVoList = s4.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0005".equals(interFaceCode)) {
				S0005 s5 = (S0005)ApplicationContextHelper.getBeanByType(S0005.class);
				List<S0005VO> voList = s5.setXMLToVO(dataList);
				List<returnVO> retVoList = s5.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0006".equals(interFaceCode)) {
				S0006 s6 = (S0006)ApplicationContextHelper.getBeanByType(S0006.class);
				List<S0006VO> voList = s6.setXMLToVO(dataList);
				List<returnVO> retVoList = s6.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0007".equals(interFaceCode)) {
				S0007 s7 = (S0007)ApplicationContextHelper.getBeanByType(S0007.class);
				List<S0007VO> voList = s7.setXMLToVO(dataList);
				List<returnVO> retVoList = s7.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0010".equals(interFaceCode)) {
				S0010 s10 = (S0010)ApplicationContextHelper.getBeanByType(S0010.class);
				List<S0010VO> voList = s10.setXMLToVO(dataList);
				List<returnVO> retVoList = s10.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0011".equals(interFaceCode)) {
				S0011 s11 = (S0011)ApplicationContextHelper.getBeanByType(S0011.class);
				List<S0011VO> voList = s11.setXMLToVO(dataList);
				List<returnVO> retVoList = s11.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0012".equals(interFaceCode)) {
				S0012 s12 = (S0012)ApplicationContextHelper.getBeanByType(S0012.class);
				List<S0012VO> voList = s12.setXMLToVO(dataList);
				List<returnVO> retVoList = s12.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0013".equals(interFaceCode)) {
				S0013 s13 = (S0013)ApplicationContextHelper.getBeanByType(S0013.class);
				List<S0013VO> voList = s13.setXMLToVO(dataList);
				List<returnVO> retVoList = s13.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0014".equals(interFaceCode)) {
				S0014 s14 = (S0014)ApplicationContextHelper.getBeanByType(S0014.class);
				List<S0014VO> voList = s14.setXMLToVO(dataList);
				List<returnVO> retVoList = s14.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0015".equals(interFaceCode)) {
				S0015 s15 = (S0015)ApplicationContextHelper.getBeanByType(S0015.class);
				List<S0015VO> voList = s15.setXMLToVO(dataList);
				List<returnVO> retVoList = s15.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

			if ("S0016".equals(interFaceCode)) {
				S0016 s16 = (S0016)ApplicationContextHelper.getBeanByType(S0016.class);
				List<S0016VO> voList = s16.setXMLToVO(dataList);
				List<returnVO> retVoList = s16.execute(voList);
				outPut = this.returnXml(retVoList, interFaceCode);
			}

		} catch (Throwable e) {
			e.printStackTrace();
		}
		return outPut;
	}

	public String returnXml(List<returnVO> retList, String InterfaceCode) {

		String rowId = null;

		String isMessage = null;

		StringBuffer result = new StringBuffer();

		result.append("<?xml version=\"1.0\" encoding=\"utf-8\"?> <IF_RETURN>");
		result.append("<INTERFACE_CODE>");
		result.append(InterfaceCode);
		result.append("</INTERFACE_CODE>");

		if (retList != null && retList.size() > 0) {

			result.append("<IS_RESULT>");
			result.append("N");
			result.append("</IS_RESULT>");

			for (int i = 0; i < retList.size(); i++) {

				returnVO retVo = new returnVO();
				retVo = retList.get(i);
				rowId = retVo.getOutput();
				isMessage = retVo.getMessage();
				result.append("<ITEM>");
				result.append("<ROW_ID>");
				result.append(rowId);
				result.append("</ROW_ID>");
				result.append("<IS_MESSAGE>");
				result.append(isMessage);
				result.append("</IS_MESSAGE>");
				result.append("</ITEM>");
			}

		} else {
			result.append("<IS_RESULT>");
			result.append("Y");
			result.append("</IS_RESULT>");
		}

		result.append("</IF_RETURN>");
		return result.toString();
	}
}
