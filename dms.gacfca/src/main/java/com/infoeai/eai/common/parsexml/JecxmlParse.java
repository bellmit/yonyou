package com.infoeai.eai.common.parsexml;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.infoeai.eai.DTO.Dcs2Jec01DTO;
import com.infoeai.eai.DTO.Dcs2Jec02DTO;
import com.infoeai.eai.DTO.Dcs2Jec03DTO;
import com.infoeai.eai.DTO.JECAddNVhclMaintainDTO;
import com.infoeai.eai.DTO.JECCarDTO;
import com.infoeai.eai.DTO.JECMaintainDTO;
import com.infoeai.eai.DTO.JECRecommendDTO;
import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;
import com.yonyou.dms.function.utils.common.CommonUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class JecxmlParse {
	 DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	   
	/**
	 * 输入：POList业务对象
	 * 解析DCS->JEC文件，车辆实销信息导入
	 * 输出：XML字符串
	 * @throws ParserConfigurationException 
	 */
	public String addOwnerVehicleXMLParse(List<Dcs2Jec02DTO> list){
		XStream xstream = new XStream(new DomDriver("UTF-8"));//定义字符集
		StringBuffer strb  = new StringBuffer();;
		//dpo.getCars()
		strb.append("\n"+"<Root>");
		strb.append("\n"+"<Content>"+"\n");
		strb.append("<CarOwner>"+"\n");
		for(int i=0;i<list.size();i++){
		  Dcs2Jec02DTO dpo = list.get(i);
		  strb.append("<Code>"+CommonUtils.checkNull(dpo.getCode())+"</Code>"+"\n");
		  strb.append("<Name>"+CommonUtils.checkNull(dpo.getName())+"</Name>"+"\n");
		  strb.append("<idOrCompCode>"+CommonUtils.checkNull(dpo.getIdOrCompCode())+"</idOrCompCode>"+"\n");
		  strb.append("<email>"+CommonUtils.checkNull(dpo.getEmail())+"</email>"+"\n");
		  strb.append("<gender>"+CommonUtils.checkNull(dpo.getGender())+"</gender>"+"\n");
		  strb.append("<province>"+CommonUtils.checkNull(dpo.getProvince())+"</province>"+"\n");
		  strb.append("<city>"+CommonUtils.checkNull(dpo.getCity())+"</city>"+"\n");
		  strb.append("<address>"+CommonUtils.checkNull(dpo.getAddress())+"</address>"+"\n");
		  strb.append("<postCode>"+CommonUtils.checkNull(dpo.getPostCode())+"</postCode>"+"\n");
		  strb.append("<cellPhone>"+CommonUtils.checkNull(dpo.getCellPhone())+"</cellPhone>"+"\n");
		  strb.append("<homePhone>"+CommonUtils.checkNull(dpo.getHomePhone())+"</homePhone>"+"\n");
		  strb.append("<driveAge>"+CommonUtils.checkNull(dpo.getDriveAge())+"</driveAge>"+"\n");
		  strb.append("<hobby>"+CommonUtils.checkNull(dpo.getHobby())+"</hobby>"+"\n");
		  strb.append("<acceptPost>"+CommonUtils.checkNull(dpo.getAcceptPost())+"</acceptPost>"+"\n");
			//strb.append(xstream.toXML(dpo)+"\n");
			//car信息
			//strb.append("<Cars>"+"\n");
		  strb.append("<Cars>"+"\n");
			for(int j=0;j<dpo.getCars().size();j++){
				xstream.alias("Car", JECCarDTO.class);//定义别名	
				JECCarDTO jpo = dpo.getCars().get(j);
				strb.append(xstream.toXML(jpo)+"\n");
			}
			strb.append("</Cars>"+"\n");
			//Maintains信息
			strb.append("<Maintains>"+"\n");
			for(int k=0;k<dpo.getMaintains().size();k++){
				xstream.alias("Maintain", JECAddNVhclMaintainDTO.class);//定义别名		
				JECAddNVhclMaintainDTO jvpo = dpo.getMaintains().get(k);
				strb.append(xstream.toXML(jvpo)+"\n");			
			}
			strb.append("</Maintains>"+"\n");
		}
		strb.append("</CarOwner>"+"\n");
		strb.append("</Content>");
		strb.append("\n"+"</Root>");
		String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+strb.toString()+"";
		return str;
	}
	/**
	 * 输入：POList业务对象
	 * 解析DCS->JEC文件，车辆维修信息导入
	 * 输出：XML字符串
	 */
	public String updateOwnerVehicleXMLParse(List<Dcs2Jec03DTO> list) {
		XStream xstream = new XStream(new DomDriver("UTF-8"));//定义字符集
		StringBuffer strb  = new StringBuffer();
		strb.append("\n"+"<Root>");
		strb.append("\n"+"<Content>"+"\n");
		strb.append("<CarOwner>"+"\n");
		for(int i=0;i<list.size();i++){
			Dcs2Jec03DTO dpo = list.get(i);
			 strb.append("<Code>"+dpo.getCode()+"</Code>"+"\n");
			 strb.append("<Recommendations>"+"\n");
			 for(int j = 0;j<dpo.getRecommendations().size();j++){
				 JECRecommendDTO spo = dpo.getRecommendations().get(j);
				 strb.append("<Recommendation>"+"\n");
				 strb.append("<dmsMappingId>"+CommonUtils.checkNull(spo.getDmsMappingId())+"</dmsMappingId>"+"\n");
				 strb.append("<recommendDate>"+CommonUtils.checkNull(spo.getRecommendDate())+"</recommendDate>"+"\n");
				 strb.append("<recommendeeName>"+CommonUtils.checkNull(spo.getRecommendeeName())+"</recommendeeName>"+"\n");
				 strb.append("<recommendModel>"+CommonUtils.checkNull(spo.getRecommendModel())+"</recommendModel>"+"\n");
				 strb.append("</Recommendation>"+"\n");
			 }
			 strb.append("</Recommendations>"+"\n");
			 strb.append("<Maintains>"+"\n");
			 for(int k =0;k<dpo.getMaintains().size();k++){
				 xstream.alias("Maintain", JECMaintainDTO.class);//定义别名	
				 JECMaintainDTO jepo = dpo.getMaintains().get(i);
				 strb.append(xstream.toXML(jepo)+"\n");
			 }
			 strb.append("</Maintains>"+"\n");
			
		}
		strb.append("</CarOwner>"+"\n");
		strb.append("</Content>");
		strb.append("\n"+"</Root>");
		String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+strb.toString()+"";
		return str;
	}
	/**
	 * 输入：POList业务对象
	 * 解析DCS->JEC文件，车辆下线信息导入
	 * 输出：XML字符串
	 */
	public  String addNewVehicleXMLParse(List<Dcs2Jec01DTO> list) {
		StringBuffer strb  = new StringBuffer();
		strb.append("\n"+"<Root>"+"\n");
		strb.append("<Content>"+"\n");
		for(int i=0;i<list.size();i++){
			 Dcs2Jec01DTO spo = list.get(i);
			 strb.append("<Car>"+"\n");
			 strb.append("<trunkCode>"+CommonUtils.checkNull(spo.getTrunkCode())+"</trunkCode>"+"\n");
			 strb.append("<model>"+CommonUtils.checkNull(spo.getModel())+"</model>"+"\n");
			 strb.append("<style>"+CommonUtils.checkNull(spo.getStyle())+"</style>"+"\n");
			 strb.append("<buyDealer>"+CommonUtils.checkNull(spo.getBuyDealer())+"</buyDealer>"+"\n");
			 strb.append("</Car>"+"\n");
		}
		strb.append("</Content>"+"\n");
		strb.append("</Root>"+"\n");
		String str="<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+strb.toString()+"";
//		System.out.println(str);
		return str;
	}
   
	/**
	 * 输入：XML的字符串
	 * 解析JEC反馈结果
	 * 输出：
	 */
	public Map<String, String> readXml(String strXml) {
		Document doc = null;
		Map<String, String> returnValue = new HashMap<String, String>();
		try {
			doc = DocumentHelper.parseText(strXml);
			Element rootElt = doc.getRootElement(); // 获取根节点
			 Iterator iter = rootElt.elementIterator("Result"); // 获取根节点下的子节点
			 while (iter.hasNext()) {
				 Element recordEle = (Element) iter.next();
				 returnValue.put("StateCode", recordEle.elementTextTrim("StateCode"));
				 returnValue.put("ErrorInfo", recordEle.elementTextTrim("ErrorInfo"));
			 }
		}catch (Exception e) {
			System.out.println("插入数据异常："+e);
		}
		return returnValue;
	}
}
