package com.infoeai.eai.action.ncserp;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.ncserp.common.ISAPOutBoundCommon;
import com.infoeai.eai.action.ncserp.impl.CLAMExecutorImpl;
import com.infoeai.eai.action.ncserp.impl.YORDExecutor;
import com.infoeai.eai.action.ncserp.impl.YOUIExecutor;
import com.infoeai.eai.action.ncserp.impl.ZASSExecutor;
import com.infoeai.eai.action.ncserp.impl.ZCASExecutor;
import com.infoeai.eai.action.ncserp.impl.ZCBCExecutor;
import com.infoeai.eai.action.ncserp.impl.ZPDFExecutor;
import com.infoeai.eai.action.ncserp.impl.ZPDPExecutor;
import com.infoeai.eai.action.ncserp.impl.ZRGIExecutor;
import com.infoeai.eai.action.ncserp.impl.ZSHPExecutor;
import com.infoeai.eai.action.ncserp.impl.ZSTAExecutor;
import com.infoeai.eai.action.ncserp.impl.ZSWRExecutor;
import com.infoeai.eai.action.ncserp.impl.ZVFCExecutor;
import com.infoeai.eai.action.ncserp.impl.ZVP8Executor;
import com.infoeai.eai.common.ReadFileTxt;
import com.infoeai.eai.common.com.parsexml.DocumentXml;
import com.infoeai.eai.dao.ctcai.SI12Dao;
import com.infoeai.eai.wsServer.SapDcsService.SapOutboundVO;
import com.yonyou.dms.function.utils.common.CommonUtils;


/***
 * 功能说明：改功能主要是对用webService传过来的结果分别调用各自接口的处理方法
 * @author Benzc 
 * @date 2017年4月24日
 */
@Service
public class SI01v2Impl extends BaseService implements SI01v2{
	public static Logger logger = LoggerFactory.getLogger(SI01v2Impl.class);
	SI12Dao dao = new SI12Dao();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	
	@Autowired
	ZVP8Executor zvp8;
	@Autowired
	ZVFCExecutor zvfc;
	@Autowired
	ZSWRExecutor zswr;
	@Autowired
	ZSTAExecutor zsta;
	@Autowired
	ZSHPExecutor zshp;
	@Autowired
	ZRGIExecutor zrgi;
	@Autowired
	ZPDPExecutor zpdp;
	@Autowired
	ZPDFExecutor zpdf;
	@Autowired
	ZCBCExecutor zcbc;
	@Autowired
	ZCASExecutor zcas;
	@Autowired
	ZASSExecutor zass;
	@Autowired
	YOUIExecutor youi;
	@Autowired
	YORDExecutor yord;
	
	@SuppressWarnings("unused")
	public String setClaimsXMLToVO(Document doc) throws Exception {
		String returnResult = "01";// 返回02表示成功,01表示失败
		Map<String, String> mapHead = null;
		Map<String, String> mapItem = null;
		List<Map<String, String>> returnList = new ArrayList<Map<String, String>>();
		CLAMExecutorImpl clamExe = new CLAMExecutorImpl();
		try {
			logger.info("====SI01v2 is begin====");
			beginDbService();
			
			Element root = doc.getDocumentElement();
			NodeList node = doc.getElementsByTagName("ZRFI_WTY_CN_S");
			for (int i = 0; i < node.getLength(); i++) {
				Node no = node.item(i);
				NodeList list = no.getChildNodes();
				for (int ij = 0; ij < list.getLength(); ij++) {
					if (list.item(ij).getNodeName().trim().equals("")
							|| list.item(ij).getNodeName().startsWith("#")
							|| list.item(ij).getNodeName() == null) {
						continue;
					}
					if ("HEAD".equals(list.item(ij).getNodeName())) {
						Node headNode = list.item(ij);
						NodeList headList = headNode.getChildNodes();
						// System.out.println(list.item(ij).getNodeName()+"============="+list.item(ij).getTextContent());
						mapHead = new HashMap<String, String>();
						logger
						.info("================================Head Name "
								+ ij
								+ "=================================");
						for (int hj = 0; hj < headList.getLength(); hj++) {
							if (headList.item(hj).getNodeName().trim().equals(
									"")
									|| headList.item(hj).getNodeName()
											.startsWith("#")
									|| headList.item(hj).getNodeName() == null) {
								continue;
							}

							mapHead.put(headList.item(hj).getNodeName(),
									headList.item(hj).getTextContent());
							String name = headList.item(hj).getNodeName();
							String value = headList.item(hj).getTextContent();
							System.out.println(name + "=============" + value);
							logger.info(name + "===" + value);
						}
					}
					if ("ITEM".equals(list.item(ij).getNodeName())) {
						Node itemNode = list.item(ij); // ITEM
						NodeList itemList = itemNode.getChildNodes();
						for (int ik = 0; ik < itemList.getLength(); ik++) {
							if (itemList.item(ik).getNodeName().trim().equals(
									"")
									|| itemList.item(ik).getNodeName()
											.startsWith("#")
									|| itemList.item(ik).getNodeName() == null) {
								continue;
							}

							Node ZRFINode = itemList.item(ik);// ZRFI_WTY_ITEM_CN_S
							NodeList ZRFIList = ZRFINode.getChildNodes();
							mapItem = new HashMap<String, String>();
							logger
									.info("============================================Item Name "
											+ ik
											+ "=======================================");
							for (int k = 0; k < ZRFIList.getLength(); k++) {
								if (ZRFIList.item(k).getNodeName().trim()
										.equals("")
										|| ZRFIList.item(k).getNodeName()
												.startsWith("#")
										|| ZRFIList.item(k).getNodeName() == null) {
									continue;
								}

								mapItem.put(ZRFIList.item(k).getNodeName(),
										ZRFIList.item(k).getTextContent());
								String name = ZRFIList.item(k).getNodeName();
								String value = ZRFIList.item(k)
										.getTextContent();
								System.out.println(name + "============="
										+ value);
								logger.info(name + "===" + value);

							}
							returnList.add(mapItem);
							logger
									.info("=============================Item Name中第 "
											+ ik
											+ "条数据=======================================");
						}
					}
				}
				// //////
				clamExe.insertNodeDetail(returnList, mapHead);
				returnList.clear();
				logger.info("=============================第 " + i
						+ "组数据插入成功=======================================");
			}
			returnResult = "02";
			dbService.endTxn(true);
			/****************************** 结束事物 *********************/
		} catch (Exception e) {
			logger
			.info("====================CLAM解析XML赋值VO异常=========================");
			logger.error(e.getMessage(), e);
			returnResult = "01";
			dbService.endTxn(false);
		} finally {
			logger.info("====SI01v2 ddUExecute is finish====");
		}
		logger.info("====================执行setXMLToVO，返回值：" + returnResult
				+ "=========================");
		return returnResult;
	}
	
	public String setXMLToVO(List<Map<String, String>> xmlList)
			throws Exception {
		String returnResult = "01"; // 返回02表示成功,01表示失败
		ReadFileTxt rdt = new ReadFileTxt();
		// 读取5000多台车的配置文件
		String vehicle5000 = rdt
				.readFile("E:/Interface/vehicle5000.txt", "GBK");
		try {
			logger.info("====XML赋值到VO======");
			logger.info("====XMLSIZE:=======" + xmlList.size());
			if (xmlList != null && xmlList.size() > 0) {
				SapOutboundVO[] sapOutVo = new SapOutboundVO[1];
				for (int i = 0; i < xmlList.size(); i++) {
					Map<String, String> map = xmlList.get(i);
					if (map != null && map.size() > 0) {
						SapOutboundVO outDto = new SapOutboundVO();
						outDto.setActionCode(map.get("ActionCode"));
						outDto.setActionDate(map.get("ActionDate"));
						outDto.setActionTime(map.get("ActionTime"));
						outDto.setEngineNumber(map.get("EngineNumber"));
						outDto.setProductionDate(map.get("ProductionDate"));
						outDto.setPrimaryStatus(map.get("PrimaryStatus"));
						outDto.setSecondaryStatus(map.get("SecondaryStatus"));
						outDto.setShipDate(map.get("ShipDate"));
						outDto.setEta(map.get("Eta"));
						// 截取车型
						String model = CommonUtils.checkNull(map.get("Model")
								.trim().replace("-8BL", ""));
						if (vehicle5000.contains(CommonUtils.checkNull(map.get(
								"Vin").trim()))) {
							model = model + "-B";
						}
						outDto.setModel(model);
						outDto.setModelyear(map.get("Modelyear"));
						outDto.setCharacteristicColour(map
								.get("CharacteristicColour"));
						outDto.setCharacteristicTrim(map
								.get("CharacteristicTrim"));
						outDto.setCharacteristicFactoryStandardOptions(map
								.get("CharacteristicFactoryStandardOptions"));
						outDto.setCharacteristicFactoryOptions(map
								.get("CharacteristicFactoryOptions"));
						outDto.setCharacteristicLocalOptions(map
								.get("CharacteristicLocalOptions"));
						outDto.setSoldto(map.get("Soldto"));
						outDto.setInvoiceNumber(map.get("InvoiceNumber"));
						outDto.setVehicleUsage(map.get("VehicleUsage"));
						outDto.setStandardPrice(map.get("StandardPrice"));
						outDto.setWholesalePrice(map.get("WholesalePrice"));
						outDto
								.setPortofdestination(map
										.get("Portofdestination"));
						outDto.setVin(map.get("Vin"));
						sapOutVo[0] = outDto;
						logger.info("====outVo:====" + outDto);
						returnResult = this.ddUExecute(sapOutVo);
					}
				}
			}
			
			logger.info("====SI01v2 getXMLToVO() is END====");
		} catch (Exception e) {
			logger.info("==============XML赋值VO失败===================");
			logger.error(e.getMessage(), e);
			returnResult = "01";
		} finally {
			logger.info("====SI01v2 getXMLToVO() is finish====");

		}
		logger.info("---------------执行setXMLToVO，返回值：" + returnResult
				+ "----------------------");
		return returnResult;
	}
	
	public String ddUExecute(Object request) throws Exception {
		// 对通过的数据解析
		String returnResult = "01"; // 返回02表示成功,01表示失败
		try {
			logger.info("====SI01v2 is begin====");
			// POContext.beginTxn(DBService.getInstance().getDefTxnManager(),
			// -1);
			SapOutboundVO[] si_rq = (SapOutboundVO[]) request;
			String inputActionCode = null;
			if (si_rq != null && si_rq.length > 0) {
				inputActionCode = si_rq[0].getActionCode();
				if (inputActionCode != null && inputActionCode.length() > 0) {
					if ("ZVFC".equals(inputActionCode)) {
//						returnResult = executeAction(si_rq, new ZVFCExecutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (zvfc.isValid(si_rq[i])) {
								returnResult = zvfc.execute(si_rq[i]);
							}
						}
					} else if ("ZCAS".equals(inputActionCode)) {
//						returnResult = executeAction(si_rq, new ZCASExecutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (zcas.isValid(si_rq[i])) {
								returnResult = zcas.execute(si_rq[i]);
							}
						}
					} else if ("ZASS".equals(inputActionCode)) {
//						returnResult = executeAction(si_rq, new ZASSExecutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (zass.isValid(si_rq[i])) {
								returnResult = zass.execute(si_rq[i]);
							}
						}
					} else if ("ZSHP".equals(inputActionCode)) {
//						returnResult = executeAction(si_rq, new ZSHPExcutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (zshp.isValid(si_rq[i])) {
								returnResult = zshp.execute(si_rq[i]);
							}
						}
					} else if ("ZSTA".equals(inputActionCode)) {
//						returnResult = executeAction(si_rq, new ZSTAExecutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (zsta.isValid(si_rq[i])) {
								returnResult = zsta.execute(si_rq[i]);
							}
						}
					} else if ("ZCBC".equals(inputActionCode)) {
//						returnResult = executeAction(si_rq, new ZCBCExecutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (zcbc.isValid(si_rq[i])) {
								returnResult = zcbc.execute(si_rq[i]);
							}
						}
					} else if ("ZVP8".equals(inputActionCode)) {
						for (int i = 0; i < si_rq.length; i++) {
							if (zvp8.isValid(si_rq[i])) {
								returnResult = zvp8.execute(si_rq[i]);
							}
						}
					} else if ("ZPDP".equals(inputActionCode)) {// sap
//						returnResult = executeAction(si_rq, new ZPDPExecutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (zpdp.isValid(si_rq[i])) {
								returnResult = zpdp.execute(si_rq[i]);
							}
						}
					} else if ("ZPDF".equals(inputActionCode)) {
//						returnResult = executeAction(si_rq, new ZPDFExecutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (zpdf.isValid(si_rq[i])) {
								returnResult = zpdf.execute(si_rq[i]);
							}
						}
					} else if ("YOUI".equals(inputActionCode)) {// sap and DDu
//						returnResult = executeAction(si_rq, new YOUIExecutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (youi.isValid(si_rq[i])) {
								returnResult = youi.execute(si_rq[i]);
							}
						}
					} else if ("ZSWR".equals(inputActionCode)) {// sap

					} else if ("ZRGI".equals(inputActionCode)) {// sap

					} else if ("YORD".equals(inputActionCode)) {
//						returnResult = executeAction(si_rq, new YORDExecutorImpl());
						for (int i = 0; i < si_rq.length; i++) {
							if (yord.isValid(si_rq[i])) {
								returnResult = yord.execute(si_rq[i]);
							}
						}
					}
				} else {
					ISAPOutBoundCommon.insertNodeDetail(si_rq[0]);
					logger
							.info("===================ActionCode is null==========================");
				}
				logger.info("---------------执行ddUExecute，走完业务判断返回值："
						+ returnResult + "----------------------");
			}
			// POContext.endTxn(true);
			/****************************** 结束事物 *********************/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnResult = "01";
			// POContext.endTxn(false);
		} finally {
			logger.info("====SI01v2 ddUExecute is finish====");
			// POContext.cleanTxn();
		} 
		logger.info("---------------执行9个ddUExecute，final返回值：" + returnResult
				+ "----------------------");
		return returnResult;
	}
	
	public String execute(Object request) throws Exception {
		// 对通过的数据解析
		String returnResult = "01"; // 返回02表示成功,01表示失败
		try {
			logger.info("====SI01v2 is begin====");
			// POContext.beginTxn(DBService.getInstance().getDefTxnManager(),
			// -1);
			SapOutboundVO[] si_rq = (SapOutboundVO[]) request;
			String inputActionCode = null;
			if (si_rq != null && si_rq.length > 0) {
				inputActionCode = si_rq[0].getActionCode();

				if ("ZVFC".equals(inputActionCode)) {

				} else if ("ZCAS".equals(inputActionCode)) {

				} else if ("ZASS".equals(inputActionCode)) {

				} else if ("ZSHP".equals(inputActionCode)) {

				} else if ("ZSTA".equals(inputActionCode)) {

				} else if ("ZCBC".equals(inputActionCode)) {

				} else if ("ZVP8".equals(inputActionCode)) {

				} else if ("ZPDP".equals(inputActionCode)) {// sap
//					returnResult = executeAction(si_rq, new ZPDPExecutorImpl());
					for (int i = 0; i < si_rq.length; i++) {
						if (zpdp.isValid(si_rq[i])) {
							returnResult = zpdp.execute(si_rq[i]);
						}
					}
				} else if ("ZPDF".equals(inputActionCode)) {

				} else if ("YOUI".equals(inputActionCode)) {// sap and DDu
//					returnResult = executeAction(si_rq, new YOUIExecutorImpl());
					for (int i = 0; i < si_rq.length; i++) {
						if (youi.isValid(si_rq[i])) {
							returnResult = youi.execute(si_rq[i]);
						}
					}
				} else if ("ZSWR".equals(inputActionCode)) {// sap
//					returnResult = executeAction(si_rq, new ZSWRExecutorImpl());
					for (int i = 0; i < si_rq.length; i++) {
						if (zswr.isValid(si_rq[i])) {
							returnResult = zswr.execute(si_rq[i]);
						}
					}
				} else if ("ZRGI".equals(inputActionCode)) {// sap
//					returnResult = executeAction(si_rq, new ZRGIExecutorImpl());
					for (int i = 0; i < si_rq.length; i++) {
						if (zrgi.isValid(si_rq[i])) {
							returnResult = zrgi.execute(si_rq[i]);
						}
					}
				} else if ("YORD".equals(inputActionCode)) {

				}
				logger.info("---------------执行4个execute，走完业务判断返回值："
						+ returnResult + "----------------------");
			}
			// POContext.endTxn(true);
			/****************************** 结束事物 *********************/
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			returnResult = "01";
			// POContext.endTxn(false);
		} finally {
			logger.info("====SI01v2 execute is finish====");
			// POContext.cleanTxn();
		}
		logger.info("---------------执行4个execute，final返回值：" + returnResult
				+ "----------------------");
		return returnResult;
	}
	
//	private String executeAction(SapOutboundVO[] sapOutboundVOArray,
//			ISAPExecutor sapExecutor) throws Exception {
//		String result = "02";
//		for (int i = 0; i < sapOutboundVOArray.length; i++) {
//			if (sapExecutor.isValid(sapOutboundVOArray[i])) {
//				result = sapExecutor.execute(sapOutboundVOArray[i]);
//			}
//		}
//		return result;
//	}
	
	/*
	 * //索赔接口测试方法 public static void main(String[] args) throws IOException {
	 * ContextUtil.loadConf(); String returnResult = EAIConstant.DEAL_FAIL; try
	 * { File xmlFiles = new File("D:/GETXML/NEW"); ; File[]
	 * allFiles=xmlFiles.listFiles(new FileFilter() {//过滤掉目录
	 * 
	 * @Override public boolean accept(File f) { return f.isFile()?true:false; }
	 * }); for(int i=0;i<allFiles.length;i++){ DocumentXml docXml = new
	 * DocumentXml(); Document doc = docXml.getDocument(allFiles[i]); SI01v2
	 * si01 = new SI01v2(); returnResult = si01.setClaimsXMLToVO(doc);
	 * logger.info("===========业务数据操作返回结果： " + returnResult ); }
	 * 
	 * 
	 * 
	 * } catch (Exception e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } }
	 */

	// 9个接口测试方法
	public static void main(String[] args) throws IOException {
		//ContextUtil.loadConf();
//		ApplicationContext ct =new ClassPathXmlApplicationContext("applicationContext_resource.xml");
//		DBService dbService = (DBService) ct.getBean("DBService");
//		TenantService tenantService = ct.getBean(TenantService.class);
		String returnResult = "01";
		try {
			File xmlFiles = new File("E:/Interface/ueai/ZVP8");
			File[] allFiles = xmlFiles.listFiles(new FileFilter() {// 过滤掉目录
						public boolean accept(File f) {
							return f.isFile() ? true : false;
						}
					});
			for (int i = 0; i < allFiles.length; i++) {
				DocumentXml docXml = new DocumentXml();
				List<Map<String, String>> xmlList = docXml
						.parserXml(allFiles[i]);
				logger.info("=====================开始执行文件：================= "
						+ allFiles[i]);
				logger.info("===========返回结果：========= " + xmlList.toString());
				SI01v2Impl si01 = new SI01v2Impl();
				returnResult = si01.setXMLToVO(xmlList);
				logger.info("===========业务数据操作返回结果：" + returnResult);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void testSi01v2(Map<String, String> map) throws Exception {
		String returnResult = "01";
		try {
			File xmlFiles = new File(map.get("dduPath"));
			File[] allFiles = xmlFiles.listFiles(new FileFilter() {// 过滤掉目录
						public boolean accept(File f) {
							return f.isFile() ? true : false;
						}
					});
			for (int i = 0; i < allFiles.length; i++) {
				DocumentXml docXml = new DocumentXml();
				List<Map<String, String>> xmlList = docXml
						.parserXml(allFiles[i]);
				logger.info("=====================开始执行文件：================= "
						+ allFiles[i]);
				logger.info("===========返回结果：========= " + xmlList.toString());
				returnResult = setXMLToVO(xmlList);
				logger.info("===========业务数据操作返回结果：" + returnResult);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
