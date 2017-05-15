/**
 * 
 */
package com.infoeai.eai.action.bsuv.dms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.infoeai.eai.action.BaseService;
import com.infoeai.eai.action.bsuv.common.CommonBSUV;
import com.infoeai.eai.po.TiEcPotentialCustomerPO;
import com.infoeai.eai.wsServer.bsuv.lms.DMSTODCS002VO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * @author Administrator
 ** 接口编号：DMSTODCS002				
 * 接口名称：官网订单跟进反馈（接收DMS接口）				
 * 接口说明：官网客户到达DMS后的下一次跟进信息上报给DCS				
 * 传输方向：上报 DMS -> DCS				
 * 传输方式：DE				
 * 传输频率：1次/Day				
 * 传输上限：ALL						
 *
 */
@Service
public class DMSTODCS002ServiceImpl extends BaseService implements DMSTODCS002Service {
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS002ServiceImpl.class);
	
	private Date startTime = new Date();	// 记录接口开始执行的时间
	private Integer dataSize = 0;	// 数据数量
	private Integer status = OemDictCodeConstants.IF_TYPE_NO;	// 默认失败
	private String exceptionMsg = "";	// 错误日志
	
	/**
	 * 接口执行入口
	 */ 
	@Override
	public String handleExecutor(List<DMSTODCS002VO> dtoList) throws Exception {
		logger.info("========== 官网订单跟进反馈接收DMS：执行开始 ==========");
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
		
		logger.info("========== 官网订单跟进反馈接收DMS：执行失败 ==========");
		
	} finally {
		msg = "1";
		logger.info("========== 官网订单跟进反馈接收DMS：执行结束 ==========");
		Base.detach();
		dbService.clean();//清除事务
		
		beginDbService();
		try {
			// 记录日志表
			CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "官网订单跟进反馈接口：DMS->DCS", startTime, dataSize, status, exceptionMsg, null, null, new Date());	
			dbService.endTxn(true);
		} catch (Exception e2) {
			dbService.endTxn(false);
			logger.info("================官网订单跟进反馈接口：DMS->DCS=========");
		}finally {
			Base.detach();
			dbService.clean();
		}
	}
	
	return msg;
	}
	
	/**
	 * 接收数据写入接口表
	 * @param bodys
	 * @throws Exception
	 */
	private void saveTiTable(List<DMSTODCS002VO> dtoList) throws Exception {

		logger.info("========== 官网订单跟进反馈接收DMS：写入接口表开始 ==========");
		
		// 循环写入数据，DCS经销商代码需要转换DMS经销商代码
		for (int i=0;i<dtoList.size();i++) {
			
			DMSTODCS002VO vo = dtoList.get(i);
			
			String result = doCheckData(vo);
			
//			if (result.equals("")) {
//				/*
//				 * 校验成功
//				 */
//				TiEcFollowUpFeedbackPO po = new TiEcFollowUpFeedbackPO();
//				po.setString("EC_ORDER_NO",vo.getEcOrderNo());	// 官网订单号
//				po.setString("DEALER_CODE",CommonBSUV.getDealerCode(vo.getEntityCode()));	// DCS端经销商代码
//				po.setString("ENTITY_CODE",vo.getEntityCode()); // DMS端经销商代码
//				po.setDate("TRAIL_DATE",vo.getTrailDate()); // 跟进日期
//				po.setString("TEL",vo.getTel()); // 客户联系电话
//				po.setInteger("send_lms",OemDictCodeConstants.IS_DEL_00); // 发送LMS状态 [0：未发送][1：已发送][2：发送失败]
//				po.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_YES); // 校验结果  [10041001：成功][10041002：失败]
//				po.setString("CREATE_BY",DEConstant.DE_CREATE_BY); // 创建人ID
//				po.setDate("CREATE_DATE",new Date()); // 创建时间
//				po.saveIt();
//				
//			}else{
//				/*
//				 * 校验失败
//				 */
//				TiEcFollowUpFeedbackPO po = new TiEcFollowUpFeedbackPO();
//				po.setString("EC_ORDER_NO",vo.getEcOrderNo());	// 官网订单号
//				po.setString("DEALER_CODE",CommonBSUV.getDealerCode(vo.getEntityCode()));	// DCS端经销商代码
//				po.setString("ENTITY_CODE",vo.getEntityCode()); // DMS端经销商代码
//				po.setDate("TRAIL_DATE",vo.getTrailDate()); // 跟进日期
//				po.setString("TEL",vo.getTel()); // 客户联系电话
//				po.setInteger("send_lms",OemDictCodeConstants.IS_DEL_00); // 发送LMS状态 [0：未发送][1：已发送][2：发送失败]
//				po.setInteger("RESULT",OemDictCodeConstants.IF_TYPE_YES); // 校验结果  [10041001：成功][10041002：失败]
//				po.setString("MESSAGE",result);	// 校验失败信息
//				po.setString("CREATE_BY",DEConstant.DE_CREATE_BY); // 创建人ID
//				po.setDate("CREATE_DATE",new Date()); // 创建时间
//				po.saveIt();
//			}
				
		}
		
		logger.info("========== 官网订单跟进反馈接收DMS：写入接口表结束 ==========");
		
	}
	
	/**
	 * 数据校验
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String doCheckData(DMSTODCS002VO vo) throws Exception {
		
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
		// 客户联系电话非空校验
		if (CheckUtil.checkNull(vo.getTel())) {
			result = "[TEL]客户联系电话为空";
			return result;
		}
		// 跟进日期非空校验
		if (null == vo.getTrailDate()) {
			result = "[TRAIL_DATE]跟进日期为空";
			return result;
		}
		// 是否存在重复官网订单号校验
//		TiEcRetailOrderCallPO ecOrderNoCheckPo1 = new TiEcRetailOrderCallPO();
//		ecOrderNoCheckPo1.setEcOrderNo(vo.getEcOrderNo());
//		ecOrderNoCheckPo1.setResult(Constant.IF_TYPE_YES);
//		ecOrderNoCheckPo1.setIsDel(Constant.IS_DEL_00);
//		List<TiEcRetailOrderCallPO> ecOrderNoCheckPo1List = factory.select(ecOrderNoCheckPo1);
//		if (null != ecOrderNoCheckPo1List && ecOrderNoCheckPo1List.size() > 0) {
//			result = "[EC_ORDER_NO]该官网订单号已存在CALL车记录：" + vo.getEcOrderNo();
//			return result;
//		}
		
		// 官网订单号有效性校验
		List<TiEcPotentialCustomerPO> ecOrderNoCheckPo2List =TiEcPotentialCustomerPO.findBySQL("select  *  from TI_EC_POTENTIAL_CUSTOMER_DCS where EC_ORDER_NO=? AND RESULT=? AND IS_TEL=?",new Object[]{vo.getEcOrderNo(),OemDictCodeConstants.IF_TYPE_YES,OemDictCodeConstants.IS_DEL_00}) ;
		if (null == ecOrderNoCheckPo2List || ecOrderNoCheckPo2List.size() <= 0) {
			result = "[EC_ORDER_NO]官网订单号无效：" + vo.getEcOrderNo();
			return result;
		}
		
		// DMS经销商代码有效性校验
//		TiDealerRelationPO dealerCodeCheckPo = new TiDealerRelationPO();
//		dealerCodeCheckPo.setDmsCode(vo.getEntityCode());
//		List<TiDealerRelationPO> dealerCodeCheckPoList = factory.select(dealerCodeCheckPo);
//		if (null == dealerCodeCheckPoList || dealerCodeCheckPoList.size() <= 0) {
//			result = "[ENTITY_CODE]DMS经销商代码无效：" + vo.getEntityCode();
//			return result;
//		}
		
		if (!CheckUtil.checkNull(result)) {
			logger.info("========== 官网订单跟进反馈接收DMS：" + result + " ==========");
		} else {
			logger.info("========== 官网订单跟进反馈接收DMS：数据校验通过 ==========");
		}
		
		return result;
		
	}
	
	/**
	 * 接口测试入口
	 * @param args
	 */
	public static void main(String[] args) {

		
		
		Date startTime = new Date();	// 记录接口开始执行的时间
		Integer dataSize = 0;	// 数据数量
		Integer status = OemDictCodeConstants.IF_TYPE_NO;	// 默认失败
		String exceptionMsg = "";	// 错误日志
		
		DMSTODCS002Service dmstodcs002 = new DMSTODCS002ServiceImpl();
		
		try {
			
			List<DMSTODCS002VO> voList = new ArrayList<DMSTODCS002VO>();
			
			for (int i = 0; i <= 3; i++) {
				DMSTODCS002VO vo = new DMSTODCS002VO();
				vo.setEntityCode("CJ770700");	// DMS端经销商代码
				vo.setEcOrderNo("EC16051000" + i);	// 官网订单号
				vo.setTrailDate(Utility.getCurrentDateTime());	// 跟进日期
				vo.setTel("1353344556" + i);	// 客户联系电话
				voList.add(vo);
			}
			
			dmstodcs002.execute(voList);
			
		} catch (Exception e) {
			
			logger.info("========== 官网订单跟进反馈接收DMS：执行失败 ==========");
			
			status = OemDictCodeConstants.IF_TYPE_NO;	// 执行失败
			exceptionMsg = CommonBSUV.getErrorInfoFromException(e);

			logger.error(e.getMessage(), e);	// 异常处理
			
		} finally {
			
			logger.info("========== 官网订单跟进反馈接收DMS：执行结束 ==========");
			
			
			// 记录日志表
			CommonBSUV.insertTtEcOrderHistory(dmstodcs002.getClass().getName(), "官网订单跟进反馈接口：DMS->DCS", startTime, dataSize, status, exceptionMsg, null, null, new Date());
			
		}
	}
	
	@Override
	public void execute(List<DMSTODCS002VO> voList) throws Exception {
	for (int i = 0; i < voList.size(); i++) {
			
			DMSTODCS002VO vo = voList.get(i);
//			TiEcFollowUpFeedbackPO po = new TiEcFollowUpFeedbackPO();
//			Long id = new Long(SequenceManager.getSequence(null));
//			po.setId(id);	// 主键ID
//			po.setEcOrderNo(vo.getEcOrderNo());	// 官网订单号
//			po.setDealerCode(CommonBSUV.getDealerCode(vo.getEntityCode()));	// DCS端经销商代码
//			po.setTrailDate(vo.getTrailDate());	// 跟进日期
//			po.setTel(vo.getTel());	// 客户联系电话
//			po.setSendLms(0);	// 发送LMS状态    [0：未发送][1：已发送][2：发送失败]
//			po.setResult(Constant.IF_TYPE_YES);	// 校验结果    [10041001：成功][10041002：失败]
//			po.setCreateBy(DEConstant.DE_CREATE_BY);	// 创建人ID
//			po.setCreateDate(new Date());	// 创建时间
//			factory.insert(po);
		}

	}

}
