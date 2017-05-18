package com.yonyou.dcs.de.impl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.yonyou.dcs.de.SEDCSP04;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TtPtOrderDTO;
import com.yonyou.dms.common.Util.MailSender;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverDetailDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDcsPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PartCommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SEDCSP04Impl  implements SEDCSP04 {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP04Impl.class);
	private String MAIL_TITLE = "配件订货系统通知邮件:配件订货上报SAP异常";
	@Autowired
	PartCommonDao dao ;

	@Override
	public String sendDateSAP() throws Exception {
		logger.info("====PartOrderReportSAP is begin====");
		List<TtPtOrderDTO> resultList = new ArrayList<TtPtOrderDTO>();
		String errorInfo = "";
		String excString="";
		int num = 0; //经销商配件个数
		Integer ifType=1;   // 默认成功
		
		try {
			com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZLVSDWS_GF21_IN_BindingStub stub = 
					new com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZLVSDWS_GF21_IN_BindingStub();
			com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZLVSDWS_GF21_IN_Service lmsService = 
					new com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZLVSDWS_GF21_IN_ServiceLocator();
			stub = (com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZLVSDWS_GF21_IN_BindingStub)lmsService.getZLVSDWS_GF21_IN();
			logger.info("----------------调用对方服务地址："+lmsService.getZLVSDWS_GF21_INAddress()+"----------------");
			resultList = getPartOrderReportInfo();
			
			List<TtPtOrderDTO> dtolist = new ArrayList<TtPtOrderDTO>();
			
			 //存在数据
			if(resultList.size()>0) {
				//由于对方方法只能接受单个对象传递，只能用循环
				for(int i=0;i<resultList.size();i++) {
					//查询SAP信息返回后信息
					TtPtOrderDTO dto = resultList.get(i);
					TtPtOrderDTO newDto = new TtPtOrderDTO();
					String IRefNumber = dto.getOrderNo();//订单号
					//更新结果
					StringBuffer upSqlV=new StringBuffer();
					//参数
					List<Object> params = new ArrayList<Object>();
					try {
						if (IRefNumber !=null && IRefNumber.length() > 35){
							IRefNumber = IRefNumber.substring(0,35);
						}
						String IChange = "";////是否在未授权店私自更换 
						if (OemDictCodeConstants.IF_TYPE_YES.toString().equals(dto.getIsRepairByself().toString())) {
							IChange = "X";
						}
						// 因为接口现在IDealerUsr长度为15位，所以截取掉前面2位'20'
						String IDealerUsr = dto.getDealerUser();//特约店用户
						if (IDealerUsr !=null && IDealerUsr.length() > 15){
							IDealerUsr = IDealerUsr.substring(2);
						}
						String IElecCode = dto.getElecCode();//电子编号																																																																																																																			
						if (IElecCode !=null && IElecCode.length() > 10){
							IElecCode = IElecCode.substring(0,10);
						}
						String IEmerg = "";//是否通过应急启动
						if (OemDictCodeConstants.IF_TYPE_YES.toString().equals(dto.getIsEmerg().toString())) {
							IEmerg = "X";
						}
						String IKeyCode = dto.getKeyCode();//锁芯机械码/CD序列码
						if (IKeyCode !=null && IKeyCode.length() > 30){
							IKeyCode = IKeyCode.substring(0,30);
						}
						String IMarca = OemDictCodeConstants.IMARCA;//品牌
						String IMechCode = dto.getMechCode();//机械代码
						if (IMechCode !=null && IMechCode.length() > 10){
							IMechCode = IMechCode.substring(0,10);
						}
						String IName1 = dto.getCustomerName();//客户姓名
						if (IName1 !=null && IName1.length() > 28){
							IName1 = IName1.substring(0,28);
						}
						String INote = dto.getLeaveWord();//留言
						if (INote !=null && INote.length() > 200){
							INote = INote.substring(0,200);
							logger.info("====SEDCSP04 截取数据留言板===================="+INote.substring(0,200));
						}
						String IOrderType = dao.getOrderTypeByIauart(dto.getOrderType().toString());//订单类型
						String ISigni = dto.getLicenseNo();//车牌号
						if (ISigni !=null && ISigni.length() > 20){
							ISigni = ISigni.substring(0,20);
						}
						String ITel = dto.getCustomerPhone();//客户手机号码
						if (ITel !=null && ITel.length() > 20){
							ITel = ITel.substring(0,20);
						}
						String IVinCode = dto.getVin();//vin
						if (IVinCode !=null && IVinCode.length() > 35){
							IVinCode = IVinCode.substring(0,35);
						}
						String IWerks = OemDictCodeConstants.IWERKS;//Plant (Own or External)
						String IYj = dto.getIyj();//累计维修时间
						if (IYj !=null && IYj.length() > 5){
							IYj = IYj.substring(0,5);
						}
						String IZzcliente = dao.getSAPCodeByPartDealerId(dto.getDealerId());//经销code
	
						
						logger.info("====SEDCSP04 is IKeyCode===="+IKeyCode);
						logger.info("====SEDCSP04 is IMarca===="+IMarca);
						logger.info("====SEDCSP04 is IMechCode===="+IMechCode);
						logger.info("====SEDCSP04 is IName1===="+IName1);
						logger.info("====SEDCSP04 is INote====="+INote);
						logger.info("====SEDCSP04 is IOrderType===="+IOrderType);
						logger.info("====SEDCSP04 is ISigni===="+ISigni);
						logger.info("====SEDCSP04 is ITel===="+ITel);
						logger.info("====SEDCSP04 is IVinCode===="+IVinCode);
						logger.info("====SEDCSP04 is IWerks===="+IWerks);
						logger.info("====SEDCSP04 is IYj===="+IYj);
						logger.info("====SEDCSP04 is IZzcliente===="+IZzcliente);
						logger.info("====SEDCSP04 is IRefNumber===="+IRefNumber);
						
						
						com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfZbapiret11Holder tbBapireturn = new 
								com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfZbapiret11Holder();
						com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfZlvsdwsGf21InHolder tbInput = 
								new com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfZlvsdwsGf21InHolder();
						
						String sql="select * from tt_pt_order_detail_dcs where order_id = "+dto.getOrderId();
						
						List<Map> listOrderDetailPO = OemDAOUtil.findAll(sql, null);
						
						if(listOrderDetailPO.size() > 0) {
							com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZlvsdwsGf21In Gf21In[] = 
									new com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZlvsdwsGf21In[listOrderDetailPO.size()];
							for(int k = 0;k<listOrderDetailPO.size();k++) {
								Map dPO = listOrderDetailPO.get(k);
								com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZlvsdwsGf21In in = 
										new com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZlvsdwsGf21In();
								String PartNo = CommonUtils.checkNull(dPO.get("PART_CODE")) ;//物料号
								if (PartNo !=null && PartNo.length() > 18){
									PartNo = PartNo.substring(0,18);
								}								
								in.setPartNo(PartNo);//物料号
								String ReqQty = CommonUtils.checkNull(dPO.get("ORDER_NUM"));//需求量
								if (ReqQty !=null && ReqQty.length() > 4){
									ReqQty ="9999";
								}
								in.setReqQty(ReqQty);//需求量
								Gf21In[k] = in;
								logger.info("====SEDCSP04 is PartNo===="+CommonUtils.checkNull(dPO.get("PART_CODE")));
								logger.info("====SEDCSP04 is ReqQty===="+CommonUtils.checkNull(dPO.get("ORDER_NUM")));
							}
							tbInput.value = Gf21In;
						}
						com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfZlvsdwsGf21PriHolder tbOutput = new 
								com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfZlvsdwsGf21PriHolder();
						com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfBapiret1Holder tbReturn = new 
								com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.holders.TableOfBapiret1Holder();
						String vbeln = null;
						
						vbeln = stub.zlvsdwsGf21In(IChange,IDealerUsr,IElecCode, IEmerg,IKeyCode,IMarca, 
						    	IMechCode,IName1,INote,IOrderType,IRefNumber,ISigni,ITel,IVinCode,IWerks,IYj,IZzcliente, 
						    	tbBapireturn,tbInput,tbOutput,tbReturn);
						
						if (null != vbeln && !vbeln.equals("")) {//成功
						
							upSqlV.append("SAP_ORDER_NO = ?");
							upSqlV.append("DCS_SEND_RESULT = ?");
							upSqlV.append("DCS_SEND_MSG = ?");
							upSqlV.append("IS_DCS_SEND = ?");
							upSqlV.append("IS_DCS_DOWN = ?");
							params.add(vbeln);
							params.add(OemDictCodeConstants.IF_TYPE_YES);
							params.add("成功");
							params.add(OemDictCodeConstants.IF_TYPE_YES);//是上报
							params.add(OemDictCodeConstants.IF_TYPE_NO);//未下发
							
							newDto.setDcsSendMsg("成功");
							newDto.setSapOrderNo(vbeln);
							
							//第三方类型的订货生成直发交货单
							if (OemDictCodeConstants.PART_ORDER_TYPE_05.toString().equals(dto.getOrderType())) {//T.O.
								boolean create=true;
								String sql1="select * from tt_pt_deliver_dcs where sap_order_no = "+dto.getOrderId();
								
								List<Map> lpd = OemDAOUtil.findAll(sql, null);
								if (lpd != null && lpd.size() > 0) {
									create=false;
								}
								if(create){
									TtPtDeliverPO ttPtDeliverPO = new TtPtDeliverPO();
									ttPtDeliverPO.setLong("DEALER_ID",dto.getDealerId());
									ttPtDeliverPO.setString("DELIVER_NO",dao.getDeliverNo());//交货单号
									ttPtDeliverPO.setString("DMS_ORDER_NO",dto.getOrderNo());//DMS订单编号
									ttPtDeliverPO.setString("SAP_ORDER_NO",vbeln);//SAP订单编号
									DecimalFormat  df   = new DecimalFormat("######0.00");//double数据转换  只保留两位
									Double amount = dto.getOrderBalance()*OemDictCodeConstants.PARTBASEPRICE_TAXRATE;
									ttPtDeliverPO.setDouble("AMOUNT",Utility.getDouble(df.format(amount))); //总价
									ttPtDeliverPO.setDouble("NET_PRICE",dto.getOrderBalance());//净价
									Double taxAmount = dto.getOrderBalance()*(OemDictCodeConstants.PARTBASEPRICE_TAXRATE-0.1);
									ttPtDeliverPO.setDouble("TAX_AMOUNT",Utility.getDouble(df.format(taxAmount)));//税额
									ttPtDeliverPO.setDouble("TRANS_AMOUNT",0d);//运费   第三方是0
									ttPtDeliverPO.setInteger("IS_DCS_SEND",OemDictCodeConstants.IF_TYPE_NO);//未下发
									ttPtDeliverPO.setTimestamp("TRANS_DATE",new Date(System.currentTimeMillis()));//运单日期 
									ttPtDeliverPO.setTimestamp("ARRIVE_DATE",new Date(System.currentTimeMillis()));//预计到店日期 
									ttPtDeliverPO.setTimestamp("DELIVER_DATE",new Date(System.currentTimeMillis()));//交货单创建时间
									ttPtDeliverPO.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
									ttPtDeliverPO.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));;
									ttPtDeliverPO.setInteger("DELIVER_STATUS",OemDictCodeConstants.PART_DELIVER_STATUS_01);
									ttPtDeliverPO.insert();
									
									//明细
									if (tbOutput != null && tbOutput.value.length > 0) {
										//订单明细
										if (null != listOrderDetailPO && listOrderDetailPO.size() > 0) {
											//新增明细
											for (int j=0;j<listOrderDetailPO.size();j++) {
												Map orderDetailPo = listOrderDetailPO.get(j);
												String partCode = CommonUtils.checkNull(orderDetailPo.get("PART_CODE"));
												TtPtDeliverDetailDcsPO dPo = new TtPtDeliverDetailDcsPO();
												dPo.setString("PART_ID", CommonUtils.checkNull(orderDetailPo.get("PART_ID")));//保存配件主键
												dPo.setString("PART_NAME", CommonUtils.checkNull(orderDetailPo.get("PART_NAME")));//配件名称
												dPo.setString("PART_CODE",partCode);
												dPo.setLong("INIT_PLAN_NNUM", Long.parseLong(CommonUtils.checkNull(orderDetailPo.get("ORDER_NUM"))));//原计划量
												dPo.setLong("PLAN_NUM",Long.parseLong(CommonUtils.checkNull(orderDetailPo.get("ORDER_NUM"))));//计划量
												dPo.setDouble("DISCOUNT", Double.parseDouble(CommonUtils.checkNull(orderDetailPo.get("DISCOUNT"))));//单个折扣
												dPo.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
												dPo.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
												dPo.setString("ORDER_NO",vbeln);
												for(int k=0;k<tbOutput.value.length;k++) {
													com.infoeai.eai.wsClient.parts.lvmPartsOrderCreation.ZlvsdwsGf21Pri in = tbOutput.value[k];
													if (in.getMatnr().equals(partCode)) {
														logger.info("====SEDCSP04 is NetPrice===="+in.getNetwr().doubleValue());
														logger.info("====SEDCSP04 is DeliverAmount(===="+in.getKbetr().doubleValue());
														dPo.setDouble("NET_PRICE",in.getKbetr().doubleValue());//净价
														dPo.setDouble("INSTIR_PRICE",in.getKbetr().doubleValue());//入库单价
														dPo.setDouble("DELIVER_AMOUNT",in.getNetwr().doubleValue());//运单总额
													}
												}
												dPo.insert();
											}
									
										}
									} else {//如SAP未返回明细则用订单明细填充
										//订单明细
										if (null != listOrderDetailPO && listOrderDetailPO.size() > 0) {
											//新增明细
											for (int j=0;j<listOrderDetailPO.size();j++) {
												Map orderDetailPo = listOrderDetailPO.get(j);
												String partCode = CommonUtils.checkNull(orderDetailPo.get("PART_CODE"));
												TtPtDeliverDetailDcsPO dPo = new TtPtDeliverDetailDcsPO();
												dPo.setString("PART_ID", CommonUtils.checkNull(orderDetailPo.get("PART_ID")));//保存配件主键
												dPo.setString("PART_NAME", CommonUtils.checkNull(orderDetailPo.get("PART_NAME")));//配件名称
												dPo.setString("PART_CODE",partCode);
												dPo.setLong("INIT_PLAN_NNUM", Long.parseLong(CommonUtils.checkNull(orderDetailPo.get("ORDER_NUM"))));//原计划量
												dPo.setLong("PLAN_NUM",Long.parseLong(CommonUtils.checkNull(orderDetailPo.get("ORDER_NUM"))));//计划量
												dPo.setDouble("DISCOUNT", Double.parseDouble(CommonUtils.checkNull(orderDetailPo.get("DISCOUNT"))));//单个折扣
												dPo.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
												dPo.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
												dPo.setString("ORDER_NO",vbeln);
												dPo.setDouble("NET_PRICE",orderDetailPo.get("NO_TAX_PRICE"));//净价
												dPo.setDouble("INSTIR_PRICE",orderDetailPo.get("NO_TAX_PRICE"));//入库单价
												dPo.setDouble("DELIVER_AMOUNT",orderDetailPo.get("NO_TAX_AMOUNT"));//运单总额
												dPo.insert();
											}
											
										}
									}
								}
							}
						} else {//失败
							logger.info("========SEDCSP04 执行异常=====订单【"+IRefNumber+"】上报SAP异常：==========="+tbReturn.value[0].getMessage());
							errorInfo = tbReturn.value[0].getType() + ":" +tbReturn.value[0].getMessage() + "\n";
							upSqlV.append("DCS_SEND_MSG = ?");
							params.add(tbReturn.value[0].getMessage());
							upSqlV.append("DCS_SEND_RESULT = ?");
							params.add(OemDictCodeConstants.IF_TYPE_NO);
							//失败自动重传
							upSqlV.append("IS_DCS_SEND");
							params.add(OemDictCodeConstants.IF_TYPE_NO);
							//邮件通知
							Properties props = new Properties();
							props.load(Thread.currentThread().getContextClassLoader()
									.getResourceAsStream("mail.properties"));
							MailSender tt = MailSender.INSTANCE;
							tt.sendMail(props.getProperty("partmail"), MAIL_TITLE, "订单【"+IRefNumber+"】上报SAP异常："+tbReturn.value[0].getMessage()+"\n 该邮件为系统自动发出，无需回复。", null);
						}
						upSqlV.append("DCS_SEND_DATE = ?");
						params.add(new Date(System.currentTimeMillis()));//DCS传到SAP的时间
						
						//更新条件
						StringBuffer upSqlC=new StringBuffer();
						upSqlC.append("ORDER_ID = ?");
						//参数
						params.add(dto.getOrderId());

						TtPtOrderDcsPO.update(upSqlV.toString(), upSqlC.toString(), params.toArray());

					
						if(!"".equals(dto.getEcOrderNo()) && dto.getEcOrderNo() != null){
							newDto.setEcOrderNo(dto.getEcOrderNo());
							dtolist.add(newDto);
						}	
						
					  } catch (Exception e) {
						    logger.info("========SEDCSP04 执行异常=====订单【"+IRefNumber+"】上报SAP异常：==========="+e.getMessage());
						    upSqlV.append("DCS_SEND_RESULT = ?");
							params.add(OemDictCodeConstants.IF_TYPE_NO);
							upSqlV.append("DCS_SEND_MSG = ?");
							params.add("接口执行异常");
							//失败自动重传
							upSqlV.append("IS_DCS_SEND = ?");
							params.add("接口执行异常");
							//邮件通知
							Properties props = new Properties();
							props.load(Thread.currentThread().getContextClassLoader()
									.getResourceAsStream("mail.properties"));
							MailSender tt = MailSender.INSTANCE;
							tt.sendMail(props.getProperty("partmail"), MAIL_TITLE, "订单【"+IRefNumber+"】上报SAP异常："+e.getMessage()+"\n 该邮件为系统自动发出，无需回复。", null);
							upSqlV.append("DCS_SEND_DATE = ?");
							params.add(new Date(System.currentTimeMillis()));//DCS传到SAP的时间
							
							//更新条件
							StringBuffer upSqlC=new StringBuffer();
							upSqlC.append("ORDER_ID = ?");
							TtPtOrderDcsPO.update(upSqlV.toString(), upSqlC.toString(), params.toArray());
							
					  }
					  if(resultList.get(i).getOrderType().equals(OemDictCodeConstants.PART_ORDER_TYPE_10)){
						  CommonBSUV.insertPtEcOrderHistory(resultList.get(i).getEcOrderNo(), "经销商批售推送", errorInfo);
						  num++;
					  }
					}
				if(num>0){
					//调用电商订单反馈接口
//					BSTODCS001 bs = new BSTODCS001();
//					bs.ElectricitySuppliersOrderFeedback(polist);
				}
				
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			excString=CommonBSUV.getErrorInfoFromException(e);
			ifType=2;
		}finally{
			logger.info("====PartOrderReportSAP is finish====");
			if(num >0){
				CommonBSUV.insertTiEcInterfaceHistory("SEDCSP04", "经销商批售推送SAP","(DCS -> SAP)",excString,num,ifType);
			}
		}
		return null;
	}

	/**
	 * 获取数据
	 * @return
	 */
	private List<TtPtOrderDTO> getPartOrderReportInfo() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT TPO.ORDER_ID,   \n");
		sql.append("       TPO.COMMONALITY_ID,   \n");
		sql.append("       TPO.BIG_ORG_ID,   \n");
		sql.append("       TPO.ORG_ID,   \n");
		sql.append("       TPO.DEALER_ID,   \n");
		sql.append("       TPO.ORDER_NO,   \n");
		sql.append("       TPO.ORDER_TYPE,   \n");
		sql.append("       TPO.VIN,   \n");
		sql.append("       TPO.ELEC_CODE,   \n");
		sql.append("       TPO.MECH_CODE,   \n");
		sql.append("       TPO.CUSTOMER_NAME,   \n");
		sql.append("       TPO.CUSTOMER_PHONE,   \n");
		sql.append("       TPO.LICENSE_NO,   \n");
		sql.append("       TPO.REPORT_TYPE,   \n");
		sql.append("       TPO.REPORT_DATE,   \n");
		sql.append("       TPO.KEY_CODE,   \n");
		sql.append("       TPO.IS_EMERG,   \n");
		sql.append("       TPO.IS_REPAIR_BYSELF,   \n");
		sql.append("       TPO.LEAVE_WORD,   \n");
		sql.append("       TPO.CREDIT_BALANCE,   \n");
		sql.append("       TPO.ORDER_BALANCE,   \n");
		sql.append("       TPO.DIF_BALANCE,   \n");
		sql.append("       TPO.ORDER_STATUS,   \n");
		sql.append("       TPO.EC_ORDER_NO,   \n");
		sql.append("       TPO.IYJ,   \n");
		sql.append("       TPO.DEALER_USER   \n");
		sql.append(" FROM TT_PT_ORDER_DCS TPO WHERE (TPO.IS_DCS_SEND = "+OemDictCodeConstants.IF_TYPE_NO+" or TPO.IS_DCS_SEND IS NULL) \n");
		sql.append(" AND TPO.ORDER_STATUS = "+OemDictCodeConstants.PART_ORDER_STATUS_04+"  \n");
		sql.append(" AND TPO.ORDER_TYPE <> "+OemDictCodeConstants.PART_ORDER_TYPE_09+"    \n");
		sql.append(" LIMIT 100 \n");
		logger.info("====PartOrderReportSAP is sql===="+sql);
		List<Map> listmap=OemDAOUtil.findAll(sql.toString(), null);
		List<TtPtOrderDTO> list= new ArrayList<TtPtOrderDTO>();
		if(null!=listmap&&listmap.size()>0){
			for(Map map:listmap){
				TtPtOrderDTO bean = new TtPtOrderDTO();
				bean.setOrderId(Long.parseLong(CommonUtils.checkNull(map.get("ORDER_ID"))));
				bean.setCommonalityId(Long.parseLong(CommonUtils.checkNull(map.get("COMMONALITY_ID"))));
				bean.setBigOrgId(Long.parseLong(CommonUtils.checkNull(map.get("BIG_ORG_ID"))));
				bean.setOrgId(Long.parseLong(CommonUtils.checkNull(map.get("ORG_ID"))));
				bean.setDealerId(Long.parseLong(CommonUtils.checkNull(map.get("DEALER_ID"))));
				bean.setOrderNo(CommonUtils.checkNull(map.get("ORDER_NO")));
				bean.setOrderType(Integer.parseInt(CommonUtils.checkNull(map.get("ORDER_TYPE"))));
				bean.setVin(CommonUtils.checkNull(map.get("VIN")));
				bean.setElecCode(CommonUtils.checkNull(map.get("ELEC_CODE")));
				bean.setMechCode(CommonUtils.checkNull(map.get("MECH_CODE")));
				bean.setCustomerName(CommonUtils.checkNull(map.get("CUSTOMER_NAME")));
				bean.setCustomerPhone(CommonUtils.checkNull(map.get("CUSTOMER_PHONE")));
				bean.setLicenseNo(CommonUtils.checkNull(map.get("LICENSE_NO")));
				bean.setReportType(Integer.parseInt(CommonUtils.checkNull(map.get("REPORT_TYPE"))));
				bean.setReportDate(CommonUtils.parseDate(CommonUtils.checkNull(map.get("REPORT_DATE"))));
				bean.setKeyCode(CommonUtils.checkNull(map.get("KEY_CODE")));
				bean.setIsEmerg(Integer.parseInt(CommonUtils.checkNull(map.get("IS_EMERG"))));
				bean.setIsRepairByself(Integer.parseInt(CommonUtils.checkNull(map.get("IS_REPAIR_BYSELF"))));
				bean.setLeaveWord(CommonUtils.checkNull(map.get("LEAVE_WORD")));
				bean.setCreditBalance(Double.parseDouble(CommonUtils.checkNull(map.get("CREDIT_BALANCE"))));
				bean.setOrderBalance(Double.parseDouble(CommonUtils.checkNull(map.get("ORDER_BALANCE"))));
				bean.setDifBalance(Double.parseDouble(CommonUtils.checkNull(map.get("DIF_BALANCE"))));
				bean.setOrderStatus(Integer.parseInt(CommonUtils.checkNull(map.get("ORDER_STATUS"))));
				bean.setEcOrderNo(CommonUtils.checkNull(map.get("EC_ORDER_NO")));
				list.add(bean);
			}
		}
		return list;
	}

}
