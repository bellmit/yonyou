/**
 * 
 */
package com.infoeai.eai.action.bsuv.dms;

import java.util.Date;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.po.TiDealerRelationPO;
import com.infoeai.eai.po.TiEcPotentialCustomerPO;
import com.infoeai.eai.po.TiEcRetailSalePO;
import com.infoeai.eai.wsServer.bsuv.lms.DMSTODCS003VO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * @author Administrator
 ***
 * 接口编号：DMSTODCS003				
 * 接口名称：官网零售订单状态变更（接收DMS接口）				
 * 接口说明：DMS将官网零售订单（其中包括提交上报，取消上报，交车上报）上报给DCS				
 * 传输方向：上报 DMS -> DCS				
 * 传输方式：DE				
 * 传输频率：1次/Day				
 * 传输上限：ALL				
 *
 */
@Service
public class DMSTODCS003ServiceImpl extends BaseService implements DMSTODCS003Service {
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS003ServiceImpl.class);
	
	private Date startTime = new Date();	// 记录接口开始执行的时间
	private Integer dataSize = 0;	// 数据数量
	private Integer status = OemDictCodeConstants.IF_TYPE_NO;	// 默认失败
	private String exceptionMsg = "";	// 错误日志
	@Override
	public String handleExecutor(List<DMSTODCS003VO> dtoList) throws Exception {
		logger.info("========== 官网零售订单状态变更接收DMS：执行开始 ==========");
		String msg = "1";
		// 开启事务
		beginDbService();
		try {
			
			this.dataSize = dtoList.size();	// 数据数量
			saveTiTable(dtoList);	// 接收数据写入接口表
		
		
		status = OemDictCodeConstants.IF_TYPE_YES;	// 执行成功
		
		dbService.endTxn(true);	// 提交事务
		
	} catch (Exception e) {
		msg = "0";
		status = OemDictCodeConstants.IF_TYPE_NO;//执行失败
		
		exceptionMsg = CommonBSUV.getErrorInfoFromException(e);
		
		logger.info(CommonBSUV.getErrorInfoFromException(e));
		
		dbService.endTxn(false);//回滚事务
		
		logger.info("========== 官网零售订单状态变更接收DMS：执行失败 ==========");
		
	} finally {
		msg = "1";
		logger.info("========== 官网零售订单状态变更接收DMS：执行结束 ==========");
		Base.detach();
		dbService.clean();//清除事务
		
		
		beginDbService();
		try {
			// 记录日志表
			CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "官网零售订单状态变更接口：DMS->DCS", startTime, dataSize, status, exceptionMsg, null, null, new Date());	
			dbService.endTxn(true);
		} catch (Exception e2) {
			dbService.endTxn(false);
			logger.info("================官网零售订单状态变更接口：DMS->DCS=========");
		}finally {
			Base.detach();
			dbService.clean();
		}
	}
	
	return msg;
	}
	
	/**
	 * 写入接口表
	 * @param msg
	 * @throws Exception
	 */
	private void saveTiTable(List<DMSTODCS003VO> dtoList) throws Exception {
		
		logger.info("========== 官网零售订单状态变更接收DMS：写入接口表开始 ==========");
		
		// 循环写入数据，DCS经销商代码需要转换DMS经销商代码
		for (int i=0;i<dtoList.size();i++) {
			
			DMSTODCS003VO vo = dtoList.get(i);
			
			String result = doCheckData(vo);
			
			if (result.equals("")) {
				
				/*
				 * 校验成功
				 */
				
				TiEcRetailSalePO ecSalePO = new TiEcRetailSalePO();

				ecSalePO.setString("EC_ORDER_NO",vo.getEcOrderNo());	 	    // 官网订单号
				ecSalePO.setString("DEALER_CODE",CommonBSUV.getDealerCode(vo.getEntityCode())); // DCS端经销商代码
				ecSalePO.setString("ENTITY_CODE",vo.getEntityCode());			//DMS端经销商代码
				ecSalePO.setString("BRAND_CODE",vo.getBrandCode());			// 品牌代码
				ecSalePO.setString("SERIES_CODE",vo.getSeriesCode());  		// 车系代码
				ecSalePO.setString("GROUP_CODE",vo.getGroupCode());	 		// 车款代码
				ecSalePO.setString("MODEL_CODE",vo.getModelCode());			// 车型代码
				ecSalePO.setString("MODEL_YEAR",vo.getModelYear());	 		// 年款
				ecSalePO.setString("COLOR_CODE",vo.getColorCode());	 		// 颜色代码
				ecSalePO.setString("TRIM_CODE",vo.getTrimCode());		 		// 内饰代码
				ecSalePO.setInteger("ORDER_STATUS",vo.getOrderStatus());		// 订单类型  [13011015：经理审核中]、[13011020：财务审核中]、[13011025：交车确认中]、
																	//       [13011055：已取消]、[13011030：已交车确认]、[13011035：已完成]
				ecSalePO.setDate("DELIVER_DATE",vo.getDeliverDate());		// 交车日期
				ecSalePO.setDate("SUBMIT_DATE",vo.getSubmitDate());	 		// 提交日期
				ecSalePO.setDate("REVOKE_DATE",vo.getRevokeDate());  		// 取消日期
				ecSalePO.setDate("SALE_DATE",vo.getSaleDate());      		// 实销日期
				ecSalePO.setString("RETAIL_FINANCE",vo.getRetailFinance());  	// 零售金融
				ecSalePO.setInteger("ESC_COMFIRM_TYPE",vo.getEscComfirmType());	// 官网订单类型 [16001001：现车],[16001002：CALL车]
				ecSalePO.setFloat("DEPOSIT_AMOUNT",vo.getDepositAmount());  	// 定金金额
				ecSalePO.setDate("DEPOSIT_DATE",vo.getDepositDate());     	// 下定日期
				ecSalePO.setString("ID_CRAD",vo.getIdCrad());				    // 身份证
				ecSalePO.setString("TEL",vo.getTel());					    // 客户联系电话
				ecSalePO.setString("CUSTOMER_NAME",vo.getCustomerName());     // 客户姓名
				ecSalePO.setInteger("SEND_LMS",0); 						    // 发送LMS状态 [0：未发送][1：已发送][2：发送失败]
				ecSalePO.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_YES);           // 校验结果 [10041001：成功][10041002：失败]
				ecSalePO.setString("create_by",DEConstant.DE_CREATE_BY);      // 创建人ID
				ecSalePO.setDate("CREATE_DATE",new Date());				    // 创建时间
				ecSalePO.saveIt();
				
				
			} else {
				
				/*
				 * 校验失败
				 */
				
				TiEcRetailSalePO ecSalePO = new TiEcRetailSalePO();

				ecSalePO.setString("EC_ORDER_NO",vo.getEcOrderNo());	 	    // 官网订单号
				ecSalePO.setString("DEALER_CODE",CommonBSUV.getDealerCode(vo.getEntityCode())); // DCS端经销商代码
				ecSalePO.setString("ENTITY_CODE",vo.getEntityCode());			//DMS端经销商代码
				ecSalePO.setString("BRAND_CODE",vo.getBrandCode());			// 品牌代码
				ecSalePO.setString("SERIES_CODE",vo.getSeriesCode());  		// 车系代码
				ecSalePO.setString("GROUP_CODE",vo.getGroupCode());	 		// 车款代码
				ecSalePO.setString("MODEL_CODE",vo.getModelCode());			// 车型代码
				ecSalePO.setString("MODEL_YEAR",vo.getModelYear());	 		// 年款
				ecSalePO.setString("COLOR_CODE",vo.getColorCode());	 		// 颜色代码
				ecSalePO.setString("TRIM_CODE",vo.getTrimCode());		 		// 内饰代码
				ecSalePO.setInteger("ORDER_STATUS",vo.getOrderStatus());		// 订单类型  [13011015：经理审核中]、[13011020：财务审核中]、[13011025：交车确认中]、
																	//       [13011055：已取消]、[13011030：已交车确认]、[13011035：已完成]
				ecSalePO.setDate("DELIVER_DATE",vo.getDeliverDate());		// 交车日期
				ecSalePO.setDate("SUBMIT_DATE",vo.getSubmitDate());	 		// 提交日期
				ecSalePO.setDate("REVOKE_DATE",vo.getRevokeDate());  		// 取消日期
				ecSalePO.setDate("SALE_DATE",vo.getSaleDate());      		// 实销日期
				ecSalePO.setString("RETAIL_FINANCE",vo.getRetailFinance());  	// 零售金融
				ecSalePO.setInteger("ESC_COMFIRM_TYPE",vo.getEscComfirmType());	// 官网订单类型 [16001001：现车],[16001002：CALL车]
				ecSalePO.setFloat("DEPOSIT_AMOUNT",vo.getDepositAmount());  	// 定金金额
				ecSalePO.setDate("DEPOSIT_DATE",vo.getDepositDate());     	// 下定日期
				ecSalePO.setString("ID_CRAD",vo.getIdCrad());				    // 身份证
				ecSalePO.setString("TEL",vo.getTel());					    // 客户联系电话
				ecSalePO.setString("CUSTOMER_NAME",vo.getCustomerName());     // 客户姓名
				ecSalePO.setInteger("SEND_LMS",0); 						    // 发送LMS状态 [0：未发送][1：已发送][2：发送失败]
				ecSalePO.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_NO);            // 校验结果 [10041001：成功][10041002：失败]
				ecSalePO.setString("MESSAGE",result);						// 校验失败信息
				ecSalePO.setString("create_by",DEConstant.DE_CREATE_BY);      // 创建人ID
				ecSalePO.setDate("CREATE_DATE",new Date());				    // 创建时间
				ecSalePO.saveIt();
			}
			
		}
		
		logger.info("========== 官网零售订单状态变更接收DMS：写入接口表结束 ==========");

	}
	/**
	 * 数据校验
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String doCheckData(DMSTODCS003VO vo) throws Exception {
		
		String result = "";
		
		// 官网订单号非空校验
		if (CheckUtil.checkNull(vo.getEcOrderNo())) {
			result = "[EC_ORDER_NO]官网订单号为空";
			return result;
		}
		
		// DMS经销商号非空校验
		if (CheckUtil.checkNull(vo.getEntityCode())) {
			result = "[ENTITY_CODE]DMS端经销商代码为空";
			return result;
		}
		// 客户名称非空校验
		if (CheckUtil.checkNull(vo.getCustomerName())) {
			result = "[CUSTOMER_NAME]客户名称为空";
			return result;
		}
		// 客户联系电话非空校验
		if (CheckUtil.checkNull(vo.getTel())) {
			result = "[TEL]客户联系电话为空";
			return result;
		}
		// 身份证非空校验
		if (CheckUtil.checkNull(vo.getIdCrad())) {
			result = "[ID_CARD]身份证为空";
			return result;
		}
		// 订单类型非空校验
		if (CheckUtil.checkNull(vo.getOrderStatus().toString())) {
			result = "[ORDER_STATUS]订单类型为空";
			return result;
		}
		// 官网订单类型非空校验
		if (CheckUtil.checkNull(vo.getEscComfirmType().toString())) {
			result = "[ESC_COMFIRM_TYPE]官网订单类型为空";
			return result;
		}
		// 下定日期非空校验
		if (null == vo.getDepositDate()) {
			result = "[DEPOSIT_DATE]下定日期为空";
			return result;
		}
		
		// 官网订单号有效性校验
		List<TiEcPotentialCustomerPO> ecOrderNoCheckPo2List =TiEcPotentialCustomerPO.findBySQL("select  *  from TI_EC_POTENTIAL_CUSTOMER_DCS where EC_ORDER_NO=? AND RESULT=? AND IS_TEL=?",new Object[]{vo.getEcOrderNo(),OemDictCodeConstants.IF_TYPE_YES,OemDictCodeConstants.IS_DEL_00}) ;
		if (null == ecOrderNoCheckPo2List || ecOrderNoCheckPo2List.size() <= 0) {
			result = "[EC_ORDER_NO]官网订单号无效：" + vo.getEcOrderNo();
			return result;
		}
		
		// DMS经销商代码有效性校验
		List<TiDealerRelationPO> dealerCodeCheckPoList =TiDealerRelationPO.findBySQL("select  *  from Ti_Dealer_Relation where dms_code=? ", vo.getEntityCode());
		if (null == dealerCodeCheckPoList || dealerCodeCheckPoList.size() <= 0) {
			result = "[ENTITY_CODE]DMS经销商代码无效：" + vo.getEntityCode();
			return result;
		}
		
		if (!CheckUtil.checkNull(result)) {
			logger.info("========== 官网零售订单状态变更接收DMS：" + result + " ==========");
		} else {
			logger.info("========== 官网零售订单状态变更接收DMS：数据校验通过 ==========");
		}
		
		return result;
		
	}
	
	
	

	@Override
	public void execute(List<DMSTODCS003VO> voList) throws Exception {
		// TODO Auto-generated method stub

	}

}
