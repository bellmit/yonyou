package com.infoeai.eai.action.k4;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.dao.k4.K4SICommonDao;
import com.infoeai.eai.po.TmMaterialPricePO;
import com.infoeai.eai.vo.S0003VO;
import com.infoeai.eai.vo.returnVO;
import com.yonyou.dcs.dao.CommonDAO;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.common.Util.DateUtil;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TiK4VsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TiK4VsSoReturnPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVehicleNodeHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class S0003Impl extends BaseService implements S0003 {
	
	private static final Logger logger = LoggerFactory.getLogger(S0003Impl.class);
	
	@Autowired
	K4SICommonDao dao;

	/**
	 * S0001 接口执行入口
	 */
	@Override
	public List<returnVO> execute(List<S0003VO> voList) throws Throwable {
		logger.info("==========S0003 is begin==========");

		List<returnVO> retVoList = new ArrayList<returnVO>();

		String[] returnVo = null;

		// 开启事物
		beginDbService();	// 开启事物
		
		try {
			
			for (int i = 0; i < voList.size(); i++) {
				
				S0003VO s0003 = new S0003VO();
				s0003 = voList.get(i);
				
				returnVo = s0003Check(s0003);	// 校验S0003数据逻辑
				
				if (returnVo == null) {
					
					// 插入接口表
					s0003.setIsResult(OemDictCodeConstants.IF_TYPE_YES.toString());
					this.saveTiTable(s0003);	
					
					// 一下是业务处理方法方法参数是循环出voList的vo
					k4BusinessProcess(s0003);
					
				} else {
					
					// 插入接口表
					s0003.setIsResult(OemDictCodeConstants.IF_TYPE_NO.toString());
					s0003.setIsMessage(returnVo[1]);
					this.saveTiTable(s0003);
					
					// 返回错误信息
					returnVO retVo = new returnVO();
					retVo.setOutput(returnVo[0]);
					retVo.setMessage(returnVo[1]);
					retVoList.add(retVo);
					
					logger.info("==========S0003 返回不合格数据==========RowId==========" + returnVo[0]);
					
					logger.info("==========S0003 返回不合格数据==========Message==========" + returnVo[1]);
					
				}
			}

			dbService.endTxn(true);
			
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			e.printStackTrace();
			dbService.endTxn(false);
			throw new Exception("S0003业务处理异常！" + e);
		} finally {
			logger.info("====S0003 is finish====");
			Base.detach();
			dbService.clean();
		}
		
		return retVoList;
	}

	/**
	 * 设置数据接收字段
	 * @param xmlList
	 * @return
	 * @throws Exception
	 */
	@Override
	public List<S0003VO> setXMLToVO(List<Map<String, String>> xmlList) throws Exception {
		List<S0003VO> voList = new ArrayList<S0003VO>();

		try {

			logger.info("==========S0003 getXMLToVO() is START==========");
			
			logger.info("==========XML赋值到VO==========");
			
			logger.info("==========XMLSIZE:==========" + xmlList.size());
			
			if (xmlList != null && xmlList.size() > 0) {
				
				for (int i = 0; i < xmlList.size(); i++) {
					
					Map<String, String> map = xmlList.get(i);
					
					if (map != null && map.size() > 0) {
						
						S0003VO outVo = new S0003VO();
						outVo.setOrderNo(map.get("ORDER_NO"));	// 订单号
						outVo.setSoNo(map.get("SO_NO"));	// 销售订单号
						outVo.setCreateDate(map.get("CREATE_DATE"));	// 订单创建日期
						outVo.setCreateTime(map.get("CREATE_TIME"));	// 订单创建时间
						outVo.setVin(map.get("VIN"));	// 车架号
						outVo.setOrderAmount(map.get("ORDER_AMOUNT"));	// 最终付款金额(含税)
						outVo.setRebateAmount(map.get("REBATE_AMOUNT"));	// 使用返利金额
						outVo.setSoCrResult(map.get("SO_CR_RESULT"));	// SO创建是否成功
						outVo.setSoCrResultDec(map.get("SO_CR_RESULT_DEC"));	// SO创建消息文本
						outVo.setRowId(map.get("ROW_ID"));	// ROW_ID
						voList.add(outVo);
						
						logger.info("==========outVo:==========" + outVo);
						
					}
				}
			}

			logger.info("==========S0003 getXMLToVO() is END==========");
			
		} catch (Throwable e) {
			logger.info("==========XML赋值VO失败==========");
			logger.error(e.getMessage(), e);
			throw new Exception("S0003 XML转换处理异常！" + e);
		} finally {
			logger.info("==========S0003 getXMLToVO() is finish==========");
		}
		return voList;
	}
	
	/*
	 * S0003数据校验逻辑
	 */
	private String[] s0003Check(S0003VO vo) {
		
		logger.info("==========S0003 校验逻辑开始==========");
		
		// 将错误的数据rowID放入returnVo[0],message放入returnVo[1]中
		String[] returnVo = new String[2];
		
		// 校验订单号（经销商PO号）是否为空
		if (CheckUtil.checkNull(vo.getOrderNo())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "1-经销商PO号为空值";
			return returnVo;
		}
		
		// 校验订单号（经销商PO号）是否有效
		List<TtVsOrderPO> orderList = TtVsOrderPO.find("ORDER_NO = ? ", vo.getOrderNo());
		Integer orderStatus = 0;
		if (null != orderList && orderList.size() > 0) {
			// 订单号有效，则取得该订单的订单状态
			orderStatus = orderList.get(0).getInteger("ORDER_STATUS");
		} else {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "1-经销商PO号在订单表中不存在";
			return returnVo;
		}

		// SO创建是否成功
		if (CheckUtil.checkNull(vo.getSoCrResult())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "1-SO创建是否成功为空值";
			return returnVo;
		} else if ("0".equals(vo.getSoCrResult())) {	// 返回结果为0表示成功判断传过来的VIN是否符合要求

			if (CheckUtil.checkNull(vo.getOrderAmount())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "1-最终付款金额为空值";
				return returnVo;
			}

			if (CheckUtil.checkNull(vo.getCreateDate())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "1-订单创建日期为空值";
				return returnVo;
			} else if (vo.getCreateDate().length() != 8) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "1-订单创建日期长度与接口定义不一致";
				return returnVo;
			}

			if (CheckUtil.checkNull(vo.getCreateTime())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "1-订单创建时间为空值";
				return returnVo;
			} else if (vo.getCreateTime().length() != 6) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "1-订单创建时间长度与接口定义不一致";
				return returnVo;
			}

			if (CheckUtil.checkNull(vo.getVin())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "1-SO创建成功vin码不能为空值";
				return returnVo;
			} else if (vo.getVin().length() != 17) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "1-SO创建成功" + vo.getVin() + "的vin码长度与接口定义不一致";
				return returnVo;
			}

			if (CheckUtil.checkNull(vo.getSoNo())) {
				returnVo[0] = vo.getRowId();
				returnVo[1] = "1-SO创建成功销售订单号为空值";
				return returnVo;
			}
			
			// 校验当前订单状态是否可做执行扣款
			if (orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_06.intValue() &&
				orderStatus != OemDictCodeConstants.SALE_ORDER_TYPE_16.intValue()) {
				
				/*
				 * SALE_ORDER_TYPE_06 = 70031006;	// 已确认
				 * SALE_ORDER_TYPE_16 = 70031016;	// 发票取消
				 * 订单状态非以上两个，则返回错误信息
				 */
				
				returnVo[0] = vo.getRowId();
				returnVo[1] = "1-不符合当前订单状态(" + orderStatus + ")";
				return returnVo;
			}
		}

		if (CheckUtil.checkNull(vo.getRowId())) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "1-ROWID为空值";
			return returnVo;
		}

		if (null != vo.getSoCrResultDec() && !"".equals(vo.getSoCrResultDec()) && vo.getSoCrResultDec().length() > 100) {
			returnVo[0] = vo.getRowId();
			returnVo[1] = "1-SO创建消息文本的长度超过接口定义";
			return returnVo;
		}

		logger.info("==========S0003 校验逻辑结束==========");
		
		return null;
	}
	
	/*
	 * S0003数据业务处理逻辑
	 */
	public void k4BusinessProcess(S0003VO s0003Vo) throws Throwable {
		
		logger.info("==========S0003 业务处理逻辑开始==========");
		
		String crResult = s0003Vo.getSoCrResult();

		String reason = null;
		
		if ("0".equals(crResult)) {
			
			String actionDate = s0003Vo.getCreateDate() + s0003Vo.getCreateTime();
			Date nodeDate = DateUtil.yyyyMMddHHmmss2Date(actionDate);
			Timestamp changeDate = new Timestamp(nodeDate.getTime());
			
			Long vehicleId = null;
			TmVehiclePO vehiclePO = new TmVehiclePO();
			
			// 更新车辆表的车辆用途和车辆节点状态
			List<TtVsOrderPO> ordList = TtVsOrderPO.find("ORDER_NO = ? ", s0003Vo.getOrderNo());
			TtVsOrderPO orderPo = ordList.get(0);
			
			// 查询物料
			List<TmMaterialPricePO> materialPricePoList = TmMaterialPricePO.find("MATERIAL_ID = ? ", ordList.get(0).getLong("MATERAIL_ID"));
			
			// 根据传来的VIN，查询该车辆信息
			List<TmVehiclePO> vehList = TmVehiclePO.find("VIN = ? ", s0003Vo.getVin());
			
			// 若未查询到该车辆数据，就新增车辆，否则更新
			if (null != vehList && vehList.size() > 0) {
				
				// 更新车辆表数据
				vehiclePO = vehList.get(0);
				vehicleId = vehiclePO.getLong("VEHICLE_ID");
				
				TmVehiclePO po = TmVehiclePO.findFirst("VIN = ? ", s0003Vo.getVin());
				po.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_11);
				po.setDate("NODE_DATE", nodeDate);
				po.setDate("ZDRL_DATE", nodeDate);//add by huyu 2016-2-16
				po.setString("VEHICLE_USAGE", orderPo.getInteger("VEHICLE_USE").toString());
				po.setLong("DEALER_ID", orderPo.getLong("DEALER_ID"));
				if (null != materialPricePoList && materialPricePoList.size() > 0) {
					po.setDouble("RETAIL_PRICE", materialPricePoList.get(0).getDouble("MSRP"));// 车辆表写入零售价格
				}
				po.saveIt();
				
				// 更新车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO setVehicleNodeHistoryPo = TtVehicleNodeHistoryPO.findFirst("VEHICLE_ID = ? ", vehicleId);// 车辆ID
				setVehicleNodeHistoryPo.setTimestamp("YSOR_DATE", changeDate);	// 执行扣款日期
				setVehicleNodeHistoryPo.setInteger("IS_DEL", 0);
				setVehicleNodeHistoryPo.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0003);
				setVehicleNodeHistoryPo.setDate("UPDATE_DATE", new Date());
				setVehicleNodeHistoryPo.saveIt();
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", s0003Vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_11);
				params.put("changeName", "执行扣款");
				params.put("changeDate", changeDate);
				params.put("changeDesc", "VIN码匹配及扣款成功");
				params.put("createBy", OemDictCodeConstants.K4_S0003);
				dao.insertVehicleChange(params);
				
			} else {
				
				// 新增车辆表数据
				vehiclePO.setString("VIN", s0003Vo.getVin());
				vehiclePO.setInteger("NODE_STATUS", OemDictCodeConstants.K4_VEHICLE_NODE_11);
				vehiclePO.setDate("NODE_DATE", nodeDate);
				vehiclePO.setDate("ZDRL_DATE", nodeDate);	// add by huyu 2016-2-16
				vehiclePO.setLong("MATERIAL_ID", orderPo.getLong("MATERIAL_ID"));// add by maxiang 2016-06-01
				vehiclePO.setString("VEHICLE_USAGE", orderPo.getInteger("VEHICLE_USE").toString());
				vehiclePO.setLong("DEALER_ID", orderPo.getLong("DEALER_ID"));
				vehiclePO.setInteger("CREATE_BY", OemDictCodeConstants.K4_S0003);
				vehiclePO.setDate("CREATE_DATE", new Date());
				if (null != materialPricePoList && materialPricePoList.size() > 0) {
					vehiclePO.setDouble("RETAIL_PRICE", materialPricePoList.get(0).getDouble("MSRP"));// 车辆表写入零售价格
				}
				vehiclePO.saveIt();
				
				// 新增车辆节点日期记录表（存储日期）
				TtVehicleNodeHistoryPO vehicleNodeHistory = new TtVehicleNodeHistoryPO();
				vehicleNodeHistory.setLong("VEHICLE_ID", vehicleId);
				vehicleNodeHistory.setTimestamp("YSOR_DATE", changeDate);	// 执行扣款日期
				vehicleNodeHistory.setInteger("IS_DEL", 0);
				vehicleNodeHistory.setLong("CREATE_BY", OemDictCodeConstants.K4_S0003);
				vehicleNodeHistory.setDate("CREATE_DATE", new Date());
				vehicleNodeHistory.saveIt();
				
				// 新增车辆节点变更记录表数据
				Map<String, Object> params = new HashMap<String, Object>();
				params.put("vin", s0003Vo.getVin());
				params.put("changeCode", OemDictCodeConstants.K4_VEHICLE_NODE_11);
				params.put("changeName", "执行扣款");
				params.put("changeDate", changeDate);
				params.put("changeDesc", "VIN码匹配及扣款成功");
				params.put("createBy", OemDictCodeConstants.K4_S0003);
				dao.insertVehicleChange(params);
				
			}
			
			// 更新订单表
			TtVsOrderPO vsOrder = TtVsOrderPO.findFirst("ORDER_NO = ? ", s0003Vo.getOrderNo());
			vsOrder.setString("SO_NO", s0003Vo.getSoNo());
			vsOrder.setString("VIN", s0003Vo.getVin());
			vsOrder.setDouble("ORDER_AMOUNT", Utility.getDouble(s0003Vo.getOrderAmount()));
			vsOrder.setDouble("REBATE_AMOUNT", Utility.getDouble(s0003Vo.getRebateAmount()));
			vsOrder.setDate("SO_CREATE_DATE", nodeDate);
			vsOrder.setInteger("ORDER_STATUS", OemDictCodeConstants.SALE_ORDER_TYPE_07);
			vsOrder.setString("SO_CR_FAILURE_REASON", " ");	// SO创建失败原因
			vsOrder.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0003);
			vsOrder.setDate("UPDATE_DATE", new Date());
			vsOrder.setLong("VEHICLE_ID", vehList.get(0).getLong("VEHICLE_ID"));
			vsOrder.setDate("ALLOT_VEHICLE_DATE", nodeDate);
			vsOrder.setDate("ALLOT_VEHICLE_DATE", nodeDate);
			
			// 付款方式
			String paymentType = orderPo.getInteger("PAYMENT_TYPE").toString();
			if (paymentType.equals(OemDictCodeConstants.K4_PAYMENT_02.toString()) ||	// 广汽汇理汽车金融有限公司
				paymentType.equals(OemDictCodeConstants.K4_PAYMENT_03.toString()) ||	// 菲亚特汽车金融有限责任公司
				paymentType.equals(OemDictCodeConstants.K4_PAYMENT_04.toString()) ||	// 兴业银行
				paymentType.equals(OemDictCodeConstants.K4_PAYMENT_05.toString()) ||	// 交通银行
				paymentType.equals(OemDictCodeConstants.K4_PAYMENT_07.toString())) {	// 建行融资
				// 融资
				vsOrder.setInteger("FINANCING_STATUS", OemDictCodeConstants.FINANCING_STATUS_02);	// 融资成功
			} else {
				// 现金
				if (!orderPo.getInteger("VEHICLE_USE").toString().equals(OemDictCodeConstants.VEHICLE_USAGE_TYPE_68.toString())) {//add by huyu 2016-7-22
					vsOrder.setInteger("FINANCING_STATUS", OemDictCodeConstants.FINANCING_STATUS_05);	// 财务审批通过
				}
			}
			
			vsOrder.saveIt();

			// 国产车订单接口表更新值
			TiK4VsOrderPO setTiK4VsOrder = TiK4VsOrderPO.findFirst("ORDER_NO = ? ", s0003Vo.getOrderNo());	// 订单号
			setTiK4VsOrder.setString("SO_NO", s0003Vo.getSoNo());	// 销售订单号
			setTiK4VsOrder.setString("VIN", s0003Vo.getVin());	// 车架号
			setTiK4VsOrder.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0003);	// 更新人ID
			setTiK4VsOrder.setDate("UPDATE_DATE", new Date());	// 更新日期
			setTiK4VsOrder.saveIt();
			
			// update_date 20151208 by maxiang begin..
			CommonDAO.insertHistory(ordList.get(0).getLong("ORDER_ID"), OemDictCodeConstants.SALE_ORDER_TYPE_07, "扣款成功", "SAP", OemDictCodeConstants.K4_S0003, "");
			// update_date 20151208 by maxiang end..

		} else if ("5".equals(crResult)) {
				
			// 根据订单号将融资请款状态改为：融资请款
			TtVsOrderPO setTtVsOrder = TtVsOrderPO.findFirst("ORDER_NO", s0003Vo.getOrderNo());
			setTtVsOrder.setString("SO_NO", s0003Vo.getSoNo());
			setTtVsOrder.setString("VIN", s0003Vo.getVin());
			setTtVsOrder.setInteger("FINANCING_STATUS", OemDictCodeConstants.FINANCING_STATUS_01);	// 融资请款状态[融资请款中]
			setTtVsOrder.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0003);
			setTtVsOrder.setDate("UPDATE_DATE", new Date());
			setTtVsOrder.saveIt();
			
		} else if ("6".equals(crResult)) {
			//add by huyu 2016-7-22
			// 根据订单号将融资请款状态改为：财务审批中
			TtVsOrderPO setTtVsOrder = TtVsOrderPO.findFirst("ORDER_NO", s0003Vo.getOrderNo());
			setTtVsOrder.setString("SO_NO", s0003Vo.getSoNo());
			setTtVsOrder.setString("VIN", s0003Vo.getVin());
			setTtVsOrder.setInteger("FINANCING_STATUS", OemDictCodeConstants.FINANCING_STATUS_04);	// 财务审批中
			setTtVsOrder.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0003);
			setTtVsOrder.setDate("UPDATE_DATE", new Date());
			setTtVsOrder.saveIt();
			//end by huyu
		} else {
			
			// SO创建失败更新订单表发送状态和经销商SO导入接口表的发送状态更新成为发送等待重新发送
			
			if ("1".equals(crResult)) {
				reason = "余额不足";
			} else if ("2".equals(crResult)) {
				reason = "无库存";
			} else if ("3".equals(crResult)) {
				reason = s0003Vo.getSoCrResultDec();
			} else if ("4".equals(crResult)) {
				reason = "无库存且余额不足";
			}
			
			// 失败原因、订单号、更新日期
			dao.updateOrder(reason, s0003Vo.getOrderNo());
			
			// 国产车订单接口表更新条件
			// 国产车订单接口表更新值
			TiK4VsOrderPO setTiK4VsOrder = TiK4VsOrderPO.findFirst("ORDER_NO = ? ", s0003Vo.getOrderNo());	// 订单号
			setTiK4VsOrder.setString("IS_RESULT", OemDictCodeConstants.IF_TYPE_NO.toString());	// 结果标识
			setTiK4VsOrder.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0003);	// 更新人ID
			setTiK4VsOrder.setDate("UPDATE_DATE", new Date());	// 更新日期
			setTiK4VsOrder.saveIt();
				
		}

		logger.info("==========S0003 业务处理逻辑结束==========");
		
	}
	
	/**
	 * 写入接口表数据
	 * @param vo
	 */
	public void saveTiTable(S0003VO vo) {

		TiK4VsSoReturnPO soReturn = new TiK4VsSoReturnPO();
		soReturn.setString("ORDER_NO", vo.getOrderNo());	// 订单号
		soReturn.setString("SO_NO", vo.getSoNo());	// 销售订单号
		soReturn.setString("CREATE_DATE", vo.getCreateDate());	// 订单创建日期
		soReturn.setString("CREATE_TIME", vo.getCreateTime());	// 订单创建时间
		soReturn.setString("VIN", vo.getVin());	// 车架号
		soReturn.setString("ORDER_AMOUNT", vo.getOrderAmount());	// 最终付款金额(含税)
		soReturn.setString("REBATE_AMOUNT", vo.getRebateAmount());	// 使用返利金额
		soReturn.setString("SO_CR_RESULT", vo.getSoCrResult());	// SO创建是否成功
		soReturn.setString("SO_CR_RESULT_DEC", vo.getSoCrResultDec());	// SO创建消息文本
		soReturn.setString("ROW_ID", vo.getRowId());	// ROW_ID
		soReturn.setLong("CREATE_BY", OemDictCodeConstants.K4_S0003);	// 创建人ID
		soReturn.setDate("CREATE_DATE2", new Date());	// 创建日期
		soReturn.setInteger("IS_DEL", OemDictCodeConstants.IS_DEL_00);	// 逻辑删除标识
		soReturn.setString("IS_RESULT", vo.getIsResult());	// 结果标识
		soReturn.setString("IS_MESSAGE", vo.getIsMessage());	// 校验未通过返回消息
		soReturn.saveIt();
	}

}
