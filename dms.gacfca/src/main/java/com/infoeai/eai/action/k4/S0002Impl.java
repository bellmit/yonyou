package com.infoeai.eai.action.k4;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.vo.S0002XmlVO;
import com.yonyou.dcs.dao.CommonDAO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TiK4VsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtOrderSendTimeManagePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

@Service
public class S0002Impl extends BaseService implements S0002 {
	
	private static final Logger logger = LoggerFactory.getLogger(S0002Impl.class);
	
	Calendar sysDate = Calendar.getInstance();
	
	@Override
	public List<S0002XmlVO> getInfo() throws Exception {
		logger.info("============S0002经销商SO导入处理开始==================");
		
		List<S0002XmlVO> s0002List = null;
		
		try {
			
			dbService.beginTxn();	// 开启事物
			
			s0002List = getS0002Info();	// 订单数据抽取
			
//			if (null != s0002List && s0002List.size() > 0) {
//				
//				// 迭代集合将SO创建失败原因清空
//				for (int i = 0; i < s0002List.size(); i++) {
//					
//					TtVsOrderPO conOrderPo = new TtVsOrderPO();
//					conOrderPo.setOrderNo(s0002List.get(i).getORDER_NO());
//					
//					TtVsOrderPO setOrderPo = new TtVsOrderPO();
//					setOrderPo.setSoCrFailureReason(" ");
//					
//					factory.update(conOrderPo, setOrderPo);
//				}
//			}
			
			dbService.endTxn(true);	// 结束事物
			
			logger.info("============S0002经销商SO导入处理结束==================");
			
			return s0002List;
			
		} catch (Throwable e) {
			dbService.endTxn(false);
			throw new Exception("============S0002查询数据异常==================" + e.getMessage(), e.getCause());
		} finally {
			dbService.clean();
		}
	}
	
	/**
	 * 发送成功修改状态
	 * @param list_zpod
	 */
	public List<S0002XmlVO> updateVoMethod(List<S0002XmlVO> list_s0002) throws Exception {
		
		try {
			//******************************UPDATE 开启事物 *********************//*
			if (null != list_s0002 && list_s0002.size() > 0) {
				dbService.beginTxn();	// 开启事物
				for (int i = 0; i < list_s0002.size(); i++) {
					
					S0002XmlVO tempPO = new S0002XmlVO();
					tempPO = list_s0002.get(i);
					
					// 更新接口表的发送状态
					TiK4VsOrderPO s0002Po = TiK4VsOrderPO.findFirst("ORDER_NO = ? ", tempPO.getORDER_NO());
	//				s0002Po.setRequestDate(new Date().toString());
					s0002Po.setString("IS_RESULT", OemDictCodeConstants.IF_TYPE_YES.toString());
					s0002Po.setString("SORT_CODE", tempPO.getSORT_CODE());
					s0002Po.setString("ROW_ID", tempPO.getROW_ID());
					s0002Po.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0002);
					s0002Po.setDate("UPDATE_DATE", sysDate.getTime());
					s0002Po.saveIt();
					// 更新tt_vs_order表发送状态
					TtVsOrderPO ttOrderPO = TtVsOrderPO.findFirst("ORDER_NO = ? ", tempPO.getORDER_NO());
					ttOrderPO.setInteger("IS_SEND", OemDictCodeConstants.IF_TYPE_YES);
					ttOrderPO.setDate("SEND_DATE", new Date());
					ttOrderPO.setLong("UPDATE_BY", OemDictCodeConstants.K4_S0002);
					ttOrderPO.setDate("UPDATE_DATE", sysDate.getTime());
					ttOrderPO.saveIt();
				}
				dbService.endTxn(true);
				//******************************UPDATE 结束事物 *********************//*
			}
			logger.info("============S0002经销商SO导入处理更新方法结束==================");
			return null;
			
		} catch (Exception e) {
			dbService.endTxn(false);
			throw new Exception("============S0002修改状态处理异常==================" + e.getMessage());
		} finally {
			dbService.clean();
		}
	}
	
	
	@SuppressWarnings("rawtypes")
	public List<S0002XmlVO> getS0002Info() throws Exception {
		Integer[] workWY = CommonDAO.getNowWrokWeekAndYear();
		
		StringBuffer sql = new StringBuffer(" \n");
		sql.append("SELECT @rownum:=@rownum+1 rank, T.* From( \n");
		sql.append("SELECT O.IF_ID, -- 主键ID \n");
		sql.append("       @rownum:=0, \n");
		sql.append("       O.ORDER_NO, -- 订单号 \n");
		sql.append("       ROW_NUMBER() OVER() AS SORT_CODE, \n");
		sql.append("       O.CREATE_DATE, -- 订单创建日期 \n");
		sql.append("       O.CREATE_TIME, -- 订单创建时间 \n");
		sql.append("    -- O.DEALER_CODE, -- 经销商代码 \n");
		sql.append("       C1.FCA_CODE AS DEALER_CODE, -- SAP 经销商代码 \n");
		sql.append("    -- O.RECEIVES_DEALER_CODE, -- 送达方 \n"); 
		sql.append("       C2.FCA_CODE AS RECEIVES_DEALER_CODE, -- SAP 送达方经销商代码 \n");
		sql.append("       O.SAP_ORDER_TYPE, -- 订单类型 \n");
		sql.append("       O.PAYMENT_TYPE, -- 资金方式 \n");
		sql.append("       O.VIN, -- 车架号 \n");
		sql.append("       O.ORDER_NUM, -- 订购数量 \n");
		sql.append("       O.MODEL_CODE, -- 车型代码 \n");
		sql.append("       O.MODEL_YEAR, -- 年款 \n");
		sql.append("       O.COLOR_CODE, -- 颜色代码 \n");
		sql.append("       O.TRIM_CODE, -- 内饰代码 \n");
		sql.append("       O.STANDARD_OPTION, -- 标准配置 \n");
		sql.append("       O.FACTORY_OPTIONS, -- 其他配置 \n");
		sql.append("       O.LOCAL_OPTION, -- 本地配置 \n");
		sql.append("       O.SPECIAL_SERIES, -- 特殊车系 \n");
		sql.append("       CM.SAP_CODE AS VEHICLE_USE, -- 车辆用途 \n");
		sql.append("       O.REQUEST_DATE, -- 请求交货日期 \n");
		sql.append("       O.IS_REBATE, -- 是否使用返利 \n");
		sql.append("       O.SO_NO, -- 销售订单号 \n");
		
		sql.append("       IFNULL(CASE WHEN TRIM(O.ROW_ID) <> '' THEN O.ROW_ID ELSE NULL END, \n");
		sql.append("          concat('S0002',DATE_FORMAT(NOW(), '%y'),DATE_FORMAT(NOW(), '%m'),RIGHT(O.IF_ID, 9)))   AS ROW_ID \n");
		
		sql.append("  FROM TI_K4_VS_ORDER O \n");
		sql.append("  LEFT JOIN TC_CODE_K4_MAPPING CM ON CM.CODE_ID = O.VEHICLE_USE \n");
		sql.append(" INNER JOIN TM_DEALER D1 ON D1.DEALER_CODE = O.DEALER_CODE \n");
		sql.append(" INNER JOIN TM_DEALER D2 ON D2.DEALER_CODE = O.RECEIVES_DEALER_CODE \n");
		sql.append(" INNER JOIN TM_COMPANY C1 ON C1.COMPANY_ID = D1.COMPANY_ID \n");
		sql.append(" INNER JOIN TM_COMPANY C2 ON C2.COMPANY_ID = D2.COMPANY_ID \n");
		sql.append(" WHERE (C1.FCA_CODE IS NOT NULL OR C1.FCA_CODE <> '') \n");
		sql.append("   AND (C2.FCA_CODE IS NOT NULL OR C2.FCA_CODE <> '') \n");
		sql.append("   AND O.IS_RESULT = '" + OemDictCodeConstants.IF_TYPE_NO + "' \n");
		sql.append("   AND O.IS_DEL = '" + OemDictCodeConstants.IS_DEL_00 + "' \n");
		sql.append("   AND (O.IS_FREEZE <> '" + OemDictCodeConstants.IF_TYPE_YES + "' OR O.IS_FREEZE IS NULL) \n");

		if (controlWeekConditionCheck()) {
			
			// 计划订单周内发送订单
			sql.append("   AND (O.ORDER_YEAR < " + workWY[1] + " OR ( \n");
			sql.append("        O.ORDER_YEAR = "+ workWY[1]);
    		sql.append("   AND  O.ORDER_WEEK <= " + workWY[0] + ")) \n");
    	}
		
		sql.append(" ORDER BY CASE WHEN O.SO_NO = NULL THEN '0' ELSE '1' END DESC, \n");//CASE WHEN O.IS_TOP = '" + OemDictCodeConstants.IF_TYPE_YES + "' THEN '1' ELSE '0' END
		sql.append("          CASE WHEN O.IS_TOP = '" + OemDictCodeConstants.IF_TYPE_YES + "' THEN '1' ELSE '0' END DESC,  \n");
		sql.append("          O.TOP_DATE ASC, \n");
		sql.append("          O.ORDER_YEAR ASC, \n");
		sql.append("          O.ORDER_WEEK ASC, \n");
		
		// update date 20161020 by maxiang begin..
		sql.append("          (CASE O.SAP_ORDER_TYPE \n");
		sql.append("           WHEN '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_05 + "' THEN 1 -- 官网订单排序1 \n");
		sql.append("           WHEN '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_04 + "' THEN 2 -- 常规订单排序2 \n");
		sql.append("           WHEN '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_01 + "' THEN 3 -- 指派订单排序3 \n");
		sql.append("           WHEN '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_02 + "' THEN 4 -- 紧急订单排序4 \n");
		sql.append("           WHEN '" + OemDictCodeConstants.ORDER_TYPE_DOMESTIC_03 + "' THEN 5 -- 直销订单排序5 \n");
		sql.append("           ELSE 6 END), \n");
		// update date 20161020 by maxiang end..
		
		sql.append("          O.DEAL_ORDER_AFFIRM_DATE ASC \n");
		sql.append("         )T \n");
		logger.info("订单数据抽取SQL"+sql.toString());
		List<Map> listMap = OemDAOUtil.findAll(sql.toString(), null);
		List<S0002XmlVO> list = new ArrayList<S0002XmlVO>();
		if (null != list && list.size() > 0) {
			S0002XmlVO bean = new S0002XmlVO();
			for (int i=0;i<listMap.size();i++) {
				Map map = listMap.get(i);
				bean.setCOLOR_CODE(Utility.stringIsNull(map.get("COLOR_CODE").toString()));
				bean.setCREATE_DATE(Utility.stringIsNull(map.get("CREATE_DATE").toString()));
				bean.setCREATE_TIME(Utility.stringIsNull(map.get("CREATE_TIME").toString()));
				bean.setDEALER_CODE(Utility.stringIsNull(map.get("DEALER_CODE").toString()));	// SAP 经销商代码
				bean.setFACTORY_OPTIONS(Utility.stringIsNull(map.get("FACTORY_OPTIONS").toString()));
				bean.setIS_REBATE(Utility.stringIsNull(map.get("IS_REBATE").toString()));
				bean.setLOCAL_OPTION(Utility.stringIsNull(map.get("LOCAL_OPTION").toString()));
				bean.setMODEL_CODE(Utility.stringIsNull(map.get("MODEL_CODE").toString()));
				bean.setMODEL_YEAR(Utility.stringIsNull(map.get("MODEL_YEAR").toString()));
				bean.setORDER_NO(Utility.stringIsNull(map.get("ORDER_NO").toString()));
				bean.setORDER_NUM(Utility.stringIsNull(String.valueOf(map.get("ORDER_NUM"))));
				bean.setPAYMENT_TYPE(Utility.stringIsNull(map.get("PAYMENT_TYPE").toString()));
				bean.setRECEIVES_DEALER_CODE(Utility.stringIsNull(map.get("RECEIVES_DEALER_CODE").toString()));	// SAP 送达方经销商代码
				bean.setREQUEST_DATE(Utility.stringIsNull(map.get("REQUEST_DATE").toString()));
				bean.setROW_ID(Utility.stringIsNull(map.get("ROW_ID").toString()));
				bean.setSAP_ORDER_TYPE(Utility.stringIsNull(map.get("SAP_ORDER_TYPE").toString()));
				bean.setSO_NO(Utility.stringIsNull(map.get("SO_NO").toString()));
				bean.setSORT_CODE(Utility.stringIsNull(map.get("SORT_CODE").toString()));
				bean.setSTANDARD_OPTION(Utility.stringIsNull(map.get("STANDARD_OPTION").toString()));
				bean.setTRIM_CODE(Utility.stringIsNull(map.get("TRIM_CODE").toString()));
				bean.setVEHICLE_USE(Utility.stringIsNull(map.get("VEHICLE_USE").toString()));
                bean.setVIN(Utility.stringIsNull(map.get("VIN").toString()));
                bean.setSPECIAL_SERIES(Utility.stringIsNull(map.get("SPECIAL_SERIES").toString()));
                list.add(i, bean);
			}
		}
		
                    
		
		if (null == list || list.size() <= 0) {
			list = null;
		}
		
    	return list;
	}
	
	/**
	 * 根据订单发送时间设置，校验是否可发送未来周订单
	 * return:false 不做条件控制（发送包括未来周在内的所有订单）
	 * return:true 控制订单周期内的订单才可发送
	 */
	public boolean controlWeekConditionCheck() {
		
		boolean result = true;	// 定义返回结果
		
		// 查询订单发送时间设置集
		List<TtOrderSendTimeManagePO> list = TtOrderSendTimeManagePO.find("IS_DEL = "+0+" AND STATUS = ? ", OemDictCodeConstants.STATUS_ENABLE);
		
		if (null != list && list.size() > 0) {
			
			// 存在订单发送时间设置
			
			Calendar calendar = Calendar.getInstance();
			
			// 一周第一天是否为星期天
			boolean isFirstSunday = (calendar.getFirstDayOfWeek() == Calendar.SUNDAY);
			
			// 获取当前周、当前小时、当前分钟
			int currentWeek = calendar.get(Calendar.DAY_OF_WEEK);
			// 若一周第一天为星期天，则-1
			if (isFirstSunday) { currentWeek = currentWeek - 1; }
			
			int currentHour = calendar.get(Calendar.HOUR_OF_DAY);	// 当前小时
			int currentMinute = calendar.get(Calendar.MINUTE);	// 当前分钟
			
			int currentTime = 0;
			if ((currentMinute + "").length() == 1) {
				currentTime = Integer.parseInt(currentHour + "0" + currentMinute);
			} else {
				currentTime = Integer.parseInt(currentHour + "" + currentMinute);
			}
			
			for (int i = 0; i < list.size(); i++) {

				// 周
				int week = list.get(i).getInteger("WEEK");
				
				// 开始时间
				String startTimeStr = list.get(i).getString("START_TIME");
				String[] startTimeArray = startTimeStr.split(":");
				int startTime = Integer.parseInt(startTimeArray[0] + startTimeArray[1]);
				
				// 截停时间
				String stopTimeStr = list.get(i).getString("STOP_TIME");
				String[] stopTimeArray = stopTimeStr.split(":");
				int stopTime = Integer.parseInt(stopTimeArray[0] + stopTimeArray[1]);
				
				// 校验是否满足条件
				if (currentWeek == week && startTime <= currentTime && stopTime >= currentTime) {
					result = false;
					break;
				} else {
					result = true;
				}
				
			}
			
		} else {
			// 没有设置订单发送时间
			result = true;
		}
		
		return result;
	}


}
