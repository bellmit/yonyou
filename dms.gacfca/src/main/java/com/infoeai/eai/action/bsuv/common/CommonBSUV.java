package com.infoeai.eai.action.bsuv.common;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.infoeai.eai.po.TiDealerRelationPO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.common.domains.PO.basedata.TiEcInterfaceHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtEcOrderHistoryDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtEcOrderHistoryPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.f4.common.database.DBService;

/**
 * BSUV官网业务公共方法
 * @author 20160216
 */
public class CommonBSUV {
	
//	private static POFactory factory = POFactoryBuilder.getInstance();

	/**
	 * 将 e.printStackTrace() 转换成 String 输出
	 * @param e
	 * @return
	 */
	public static String getErrorInfoFromException(Exception e) {
		
		try {
			
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			
			String result = "\r\n" + sw.toString() + "\r\n";
			
			if (result.length() > 2000) {
				result = result.substring(0, 2000);
			}
			
			return result;
			
		} catch (Exception ex) {
			return "bad getErrorInfoFromException..";
		}
	}
	
	/**
	 * 历史表信息记录
	 * @param className
	 * @param classReamrk
	 * @param startDate
	 * @param dataSize
	 * @param status
	 * @param exceptionMsg
	 * @param feedbackStatus
	 * @param feedbackMsg
	 * @param endDate
	 * @throws Exception
	 */
	public static void insertTtEcOrderHistory(String className,
			String classRemark, Date startTime, Integer dataSize,
			Integer status, String exceptionMsg, String feedbackStatus,
			String feedbackMsg, Date endTime) {
		
		try {
			TtEcOrderHistoryDcsPO po = new TtEcOrderHistoryDcsPO();
			po.setString("CLASS_NAME",className);	// 类名
			po.setString("CLASS_REMARK",classRemark);	// 类名描述
			po.setTimestamp("EXECUTION_START_TIME",startTime);	// 执行开始时间
			po.setInteger("FILE_SIZE",dataSize);	// 执行数据数量
			po.setInteger("EXECUTION_STATUS",status);	// 执行状态     [10041001：成功][10041002：失败]
			
			if (status.toString().equals(OemDictCodeConstants.IF_TYPE_NO.toString())) {
				po.setString("EXCEPTION_MSG",exceptionMsg);	// 异常信息
			}
			
			po.setString("FEEDBACK_STATUS",feedbackStatus);	// 反馈结果代码
			po.setString("FEEDBACK_MSG",feedbackMsg);	// 反馈结果信息
			po.setTimestamp("EXECUTION_END_TIME",endTime);	// 执行结束时间
			po.setLong("CREATE_BY",DEConstant.DE_UPDATE_BY);	// 创建人
			po.setTimestamp("CREATE_DATE",new Date());	// 创建时间
			po.saveIt();			
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
		}
		
	}
	
	/**
	 * 配件订单历史记录
	 * @param ecOrderNo
	 * @param operatMessage
	 * @param reamrk
	 * @param remark
	 * @param userId
	 */
	public static void insertPtEcOrderHistory(String ecOrderNo,String operatMessage,String reamrk){
		try {
			TtPtEcOrderHistoryPO po = new TtPtEcOrderHistoryPO();
			po.setString("EC_ORDER_NO", ecOrderNo);
			po.setString("OPERAT_MESSAGE",operatMessage);
			po.setString("REAMRK",reamrk);
			po.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
			po.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
			po.insert();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	/**
	 * 配件接口记录
	 * @param interfaceCode
	 * @param interfaceName
	 * @param interfaceDirection
	 * @param exMessage
	 * @param interfaceSize
	 * @param sendStatus
	 */
	public static void insertTiEcInterfaceHistory(String interfaceCode, String interfaceName, String interfaceDirection,
			String exMessage, Integer interfaceSize, Integer sendStatus) {
		try {
			TiEcInterfaceHistoryPO po = new TiEcInterfaceHistoryPO();
			po.setString("INTERFACE_CODE",interfaceCode);//接口代码
			po.setString("INTERFACE_NAME",interfaceName);//接口名称
			po.setString("INTERFACE_DIRECTION",interfaceDirection);//传输方向
			po.setString("exMessage",exMessage);//失败原因
			po.setInteger("INTERFACE_SIZE",interfaceSize);//传输大小
			po.setInteger("SEND_STATUS",sendStatus);//发送状态(0失败1成功2异常)
			po.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
			po.setTimestamp("CREATE_DATE",new Date(System.currentTimeMillis()));
			po.insert();
			
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			
		}
	}
	
	/**
	 * 获取DCS经销商代码
	 * @param EntityCode
	 * @return
	 */
	public static String getDealerCode(String EntityCode) throws Exception {
		
		List<TiDealerRelationPO> list = TiDealerRelationPO.find("DMS_CODE = ?", EntityCode);
		// 如果有匹配值就返回DCS经销商代码，否则返回空字符串
		if (null != list && list.size() > 0) {
			return list.get(0).getString("DCS_CODE");
		} else {
			return "";
		}
		
	}
	
	/**
	 * 根据经销商代码获取经销商ID
	 * @param dealerCode
	 * @return
	 */
	public static long getDealerIdByEntityCode(String entityCode) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append(" \n");
		sql.append("SELECT D.DEALER_ID -- 经销商ID \n");
		sql.append("  FROM TM_DEALER D, TI_DEALER_RELATION R \n");
		sql.append(" WHERE D.DEALER_CODE = R.DCS_CODE \n");
		sql.append("   AND R.DMS_CODE = '" + entityCode + "' \n");

		Map map = OemDAOUtil.findFirst(sql.toString(), null);
		
		if (null != map && map.size() > 0) {
			return Long.parseLong(map.get("DEALER_ID").toString());
		} else {
			return 0l;
		}
		
	}
	
}
