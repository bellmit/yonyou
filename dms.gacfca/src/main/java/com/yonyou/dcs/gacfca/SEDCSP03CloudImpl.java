package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.po.TiRoAddItemPO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SEDCSP03DTO;
import com.yonyou.dms.DTO.gacfca.SEDCSP03VOPartInfoDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderAttDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDetailDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtPartBaseDcsPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PartCommonDao;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SEDCSP03CloudImpl extends BaseCloudImpl implements SEDCSP03Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP03CloudImpl.class);
	
	@Autowired
	PartCommonDao dao ;

	@Override
	public String receiveData(List<SEDCSP03DTO> dtos) throws Exception {
		String msg = "1";
		
		try {
			logger.info("====配件订货上报DCS开始====");
			saveTable(dtos);
			logger.info("====配件订货上报DCS结束====");
		} catch (Exception e) {
			logger.error("配件订货上报DCS失败", e);
			msg = "0";
			throw new ServiceBizException(e);
		} 
		logger.info("*************************** 成功获取上报的配件订货信息******************************");
		return msg;
	}

	/**
	 * 处理下端上报的数据
	 * @param vo
	 * @return
	 */
	private void saveTable(List<SEDCSP03DTO> dtos) throws Exception {
		logger.info("##################### 开始处理配件订货信息############################");
		for (SEDCSP03DTO dto : dtos) {
			String orderNo = dto.getOrderNo();
			Map<String, Object> dealerInfo = dao.getDealerInfoByEntityCode(dto.getEntityCode());////经销基本信息 dealer_id 小区id 大区id
			Long dealerId = Long.parseLong(CommonUtils.checkNull(dealerInfo.get("DEALER_ID")));
//			ttPtOrderPO.setMsgId(msgId);//保存上报的msg_id
			String sql="SELECT * FROM TT_PT_ORDER_DCS WHERE ORDER_NO = ? AND DEALER_ID = ?";
			List <Object> params= new ArrayList<Object>();
			params.add(orderNo);//订单号
			params.add(dealerId);//经销商代码
			List<Map> listTtPtOrderPO=OemDAOUtil.findAll(sql, params);
			Long orderId = null;
			if (null != listTtPtOrderPO && listTtPtOrderPO.size() > 0) {//修改
				orderId = Long.parseLong(CommonUtils.checkNull(listTtPtOrderPO.get(0).get("ORDER_ID")));
				//更新value
				StringBuffer upSqlV=new StringBuffer();
				upSqlV.append("ORDER_ID = ?");
				upSqlV.append("BIG_ORG_ID = ?");
				upSqlV.append("ORG_ID = ?");
				upSqlV.append("DEALER_ID = ?");
				upSqlV.append("ORDER_NO = ?");
				upSqlV.append("ORDER_TYPE = ?");
				upSqlV.append("VIN = ?");
				upSqlV.append("ELEC_CODE = ?");
				upSqlV.append("MECH_CODE = ?");
				upSqlV.append("CUSTOMER_NAME = ?");
				upSqlV.append("CUSTOMER_PHONE = ?");
				upSqlV.append("LICENSE_NO = ?");
				upSqlV.append("REPORT_TYPE = ?");
				upSqlV.append("REPORT_DATE = ?");
				upSqlV.append("KEY_CODE = ?");
				upSqlV.append("IS_EMERG = ?");
				upSqlV.append("IS_REPAIR_BYSELF = ?");
				upSqlV.append("LEAVE_WORD = ?");
				upSqlV.append("CREDIT_BALANCE = ?");
				upSqlV.append("ORDER_BALANCE = ?");
				upSqlV.append("DIF_BALANCE = ?");
				upSqlV.append("DMS_SEND_DATE = ?");
				upSqlV.append("IS_DCS_SEND = ?");
				upSqlV.append("IS_DCS_DOWN = ?");
				upSqlV.append("ORDER_STATUS = ?");
				upSqlV.append("IYJ = ?");
				upSqlV.append("DEALER_USER = ?");
				upSqlV.append("UPDATE_BY = ?");
				upSqlV.append("UPDATE_DATE = ?");
						
				//更新条件
				StringBuffer upSqlC=new StringBuffer();
				upSqlC.append("ORDER_NO = ?");
				upSqlC.append("DEALER_ID = ?");
				
				//参数
				List<Object> uparams = new ArrayList<Object>();
				uparams.add(Long.parseLong(CommonUtils.checkNull(dealerInfo.get("BIG_ORG_ID"))));//主键
				uparams.add(Long.parseLong(CommonUtils.checkNull(dealerInfo.get("BIG_ORG_ID"))));//大区
				uparams.add(Long.parseLong(CommonUtils.checkNull(dealerInfo.get("SMALL_ORG_ID"))));//小区
				uparams.add(dealerId);//经销id
				uparams.add(orderNo);//订单号码
				uparams.add(dto.getOrderType());//订单类型
				uparams.add(dto.getVin());//VIN
				uparams.add(dto.getElecCode());//电子编码
				uparams.add(dto.getMechCode());//机械代码
				uparams.add(dto.getCustomerName());//车主姓名
				uparams.add(dto.getCustomerPhone());
				uparams.add(dto.getLicenseNo());//车牌号
				uparams.add(dto.getReportType());//提报方式
				uparams.add(dto.getReportDate());//提报日期
				uparams.add(dto.getKeyCode());//锁芯机械码/CD序列码
				if (dto.getIsEmerg().toString().equals("12781001")){//是
					uparams.add(OemDictCodeConstants.IF_TYPE_YES);
				} else if (dto.getIsEmerg().toString().equals("12781002")) {//否
					uparams.add(OemDictCodeConstants.IF_TYPE_NO);
				}
				if (dto.getIsRepairByself().toString().equals("12781001")) {//是
					uparams.add(OemDictCodeConstants.IF_TYPE_YES);//是否在未授权店私自更换
				} else if (dto.getIsRepairByself().toString().equals("12781002")) {//否
					uparams.add(OemDictCodeConstants.IF_TYPE_NO);//是否在未授权店私自更换
				}
				uparams.add(dto.getLeaveWord());//留言
				uparams.add(dto.getCreditBalance());//信用余额
				uparams.add(dto.getOrderBalance());//本次提交金额
				uparams.add(dto.getDifBalance());//差异
				uparams.add(new Date(System.currentTimeMillis()));//dms上报时间
				uparams.add(OemDictCodeConstants.IF_TYPE_NO);//否上报
				uparams.add(OemDictCodeConstants.IF_TYPE_NO);//未下发
				//订单状态
				if (OemDictCodeConstants.PART_ORDER_TYPE_01.toString().equals(dto.getOrderType().toString()) || 
						OemDictCodeConstants.PART_ORDER_TYPE_08.toString().equals(dto.getOrderType().toString()) 
						) {// E-CODE 2  和  S-CODE 订单状态 直接待审核
					uparams.add(OemDictCodeConstants.PART_ORDER_STATUS_01);//待审核
				} else {
					uparams.add(OemDictCodeConstants.PART_ORDER_STATUS_04);//已通过
				}
				String vin = dto.getVin();
				if (null != vin && !"".equals(vin)) {//累计维修时间
					uparams.add(dao.getIyjByVin(vin));
				}
				uparams.add(dto.getDealerUser());//经销商人员
				uparams.add(DEConstant.DE_UPDATE_BY);
				uparams.add(new Date(System.currentTimeMillis()));
				
				uparams.add(orderNo);
				uparams.add(dealerId);

				TiRoAddItemPO.update(upSqlV.toString(), upSqlC.toString(), uparams.toArray());
				
			} else {//新增
				//配件订单表
				TtPtOrderDcsPO ttPtOrderPO = new TtPtOrderDcsPO();
				//公共资源ID
				ttPtOrderPO.setLong("ORDER_ID",Long.parseLong(CommonUtils.checkNull(dealerInfo.get("BIG_ORG_ID"))));//主键
				ttPtOrderPO.setLong("BIG_ORG_ID",Long.parseLong(CommonUtils.checkNull(dealerInfo.get("BIG_ORG_ID"))));//大区
				ttPtOrderPO.setLong("ORG_ID",Long.parseLong(CommonUtils.checkNull(dealerInfo.get("SMALL_ORG_ID"))));//小区
				ttPtOrderPO.setLong("DEALER_ID",dealerId);//经销id
				ttPtOrderPO.setString("ORDER_NO",orderNo);//订单号码
				ttPtOrderPO.setString("ORDER_TYPE",dto.getOrderType());//订单类型
				ttPtOrderPO.setString("VIN",dto.getVin());//VIN
				ttPtOrderPO.setString("ELEC_CODE",dto.getElecCode());//电子编码
				ttPtOrderPO.setString("MECH_CODE",dto.getMechCode());//机械代码
				ttPtOrderPO.setString("CUSTOMER_NAME",dto.getCustomerName());//车主姓名
				ttPtOrderPO.setString("CUSTOMER_PHONE",dto.getCustomerPhone());
				ttPtOrderPO.setString("LICENSE_NO",dto.getLicenseNo());//车牌号
				ttPtOrderPO.setInteger("REPORT_TYPE",dto.getReportType());//提报方式
				ttPtOrderPO.setTimestamp("REPORT_DATE",dto.getReportDate());//提报日期
				ttPtOrderPO.setString("KEY_CODE",dto.getKeyCode());//锁芯机械码/CD序列码
				if (dto.getIsEmerg().toString().equals("12781001")){//是
					ttPtOrderPO.setInteger("IS_EMERG",OemDictCodeConstants.IF_TYPE_YES);
				} else if (dto.getIsEmerg().toString().equals("12781002")) {//否
					ttPtOrderPO.setInteger("IS_EMERG",OemDictCodeConstants.IF_TYPE_NO);
				}
				if (dto.getIsRepairByself().toString().equals("12781001")) {//是
					ttPtOrderPO.setInteger("IS_REPAIR_BYSELF",OemDictCodeConstants.IF_TYPE_YES);//是否在未授权店私自更换
				} else if (dto.getIsRepairByself().toString().equals("12781002")) {//否
					ttPtOrderPO.setInteger("IS_REPAIR_BYSELF",OemDictCodeConstants.IF_TYPE_NO);//是否在未授权店私自更换
				}
				ttPtOrderPO.setString("LEAVE_WORD",dto.getLeaveWord());//留言
				ttPtOrderPO.setDouble("CREDIT_BALANCE",dto.getCreditBalance());//信用余额
				ttPtOrderPO.setDouble("ORDER_BALANCE",dto.getOrderBalance());//本次提交金额
				ttPtOrderPO.setDouble("DIF_BALANCE",dto.getDifBalance());//差异
				ttPtOrderPO.setTimestamp("DMS_SEND_DATE",new Date(System.currentTimeMillis()));//dms上报时间
				ttPtOrderPO.setInteger("IS_DCS_SEND",OemDictCodeConstants.IF_TYPE_NO);//否上报
				ttPtOrderPO.setInteger("IS_DCS_DOWN",OemDictCodeConstants.IF_TYPE_NO);//未下发
				//订单状态
				if (OemDictCodeConstants.PART_ORDER_TYPE_01.toString().equals(dto.getOrderType().toString()) || 
						OemDictCodeConstants.PART_ORDER_TYPE_08.toString().equals(dto.getOrderType().toString()) 
						) {// E-CODE 2  和  S-CODE 订单状态 直接待审核
					ttPtOrderPO.setInteger("ORDER_STATUS",OemDictCodeConstants.PART_ORDER_STATUS_01);//待审核
				} else {
					ttPtOrderPO.setInteger("ORDER_STATUS",OemDictCodeConstants.PART_ORDER_STATUS_04);//已通过
				}
				String vin = dto.getVin();
				if (null != vin && !"".equals(vin)) {//累计维修时间
					ttPtOrderPO.setString("IYJ",dao.getIyjByVin(vin));
				}
				ttPtOrderPO.setString("DEALER_USER",dto.getDealerUser());//经销商人员
				ttPtOrderPO.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
				ttPtOrderPO.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
				ttPtOrderPO.insert();
				
//				TtPtOrderDcsPO newPO=TtPtOrderDcsPO.findFirst("ORDER_NO = ? AND DEALER_ID = ?", orderNo,dealerId);
//				orderId = Long.parseLong(CommonUtils.checkNull(newPO.get("ORDER_ID")));
				orderId=ttPtOrderPO.getLong("ORDER_ID");
			}
			//明细
			LinkedList<SEDCSP03VOPartInfoDTO> listIn = dto.getTbInputPosition();
			if (listIn != null && listIn.size() > 0) {
				//把之前的明细的数据删除
				TtPtOrderDetailDcsPO.delete("ORDER_ID = ?", orderId);
				//新增明细
				for(int i=0;i<listIn.size();i++) {
					SEDCSP03VOPartInfoDTO in = listIn.get(i);
					String partCode = in.getPartCode();
					
					TtPtPartBaseDcsPO updatePo =TtPtPartBaseDcsPO.findFirst("PART_CODE = ?", partCode);
					Long partId=updatePo.getLong("ID");
					
					TtPtOrderDetailDcsPO dPo = new TtPtOrderDetailDcsPO();
					dPo.setLong("ORDER_ID",orderId);//配件主键
					dPo.setString("PART_CODE",in.getPartCode());//配件代码
					if (null != partId ) {
						dPo.setLong("PART_ID",partId);//保存配件主键
					}
					dPo.setString("PART_NAME",in.getPartName());//配件名称
					dPo.setDouble("PACKAGE_NUM",in.getPackageNum());//最小包装量
					dPo.setLong("ORDER_NUM",in.getOrderNum());//计划量
					dPo.setDouble("NO_TAX_PRICE",in.getNoTaxPrice());//不含税单价
					dPo.setDouble("NO_TAX_AMOUNT",in.getNoTaxAmount());//不含税总额
					dPo.setString("UNIT",Utility.getUnitName(in.getUnit()));//单位
					dPo.setDouble("DISCOUNT",in.getDiscount());//单个折扣
					dPo.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
					dPo.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
					dPo.insert();
					
				}
			}
			
			//删除附件
			TtPtOrderAttDcsPO.delete("ORDER_ID = ?", orderId);
			//第一个附件
			if (!CommonUtils.checkIsNull(dto.getCodeOrderOneId())) {
				TtPtOrderAttDcsPO tPo1 = new TtPtOrderAttDcsPO();
				
				tPo1.setLong("ORDER_ID",orderId);
				tPo1.setString("ATT_PATH",dto.getCodeOrderOneUrl());
				tPo1.setString("ATT_NAME","其他材料1"); 
				tPo1.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
				tPo1.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
				tPo1.insert();
			}
			//第二个附件
			if (!CommonUtils.checkIsNull(dto.getCodeOrderTwoId())) {
				TtPtOrderAttDcsPO tPo2 = new TtPtOrderAttDcsPO();
				tPo2.setLong("ORDER_ID",orderId);
				tPo2.setString("ATT_PATH",dto.getCodeOrderTwoUrl());
				tPo2.setString("ATT_NAME","其他材料2"); 
				tPo2.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
				tPo2.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
				tPo2.insert();
			}
			//第三个附件
			if (!CommonUtils.checkIsNull(dto.getCodeOrderThreeId())) {
				TtPtOrderAttDcsPO tPo3 = new TtPtOrderAttDcsPO();
				tPo3.setLong("ORDER_ID",orderId);
				tPo3.setString("ATT_PATH",dto.getCodeOrderThreeUrl());
				tPo3.setString("ATT_NAME","其他材料3"); 
				tPo3.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
				tPo3.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
				tPo3.insert();
			}
		}
		logger.info("##################### 成功处理配件订货信息############################");
	}

}
