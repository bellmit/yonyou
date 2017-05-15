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
import com.infoeai.eai.po.TiEcHitSinglePO;
import com.infoeai.eai.wsServer.bsuv.lms.DCSTODMS001VO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.common.Util.CheckUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;

/**
 * @author Administrator
 *@Description: 官网订单线索撞单（接收DMS接口）
 */
@Service
public class DMSTODCS001ServiceImpl  extends BaseService implements DMSTODCS001Service {
	private static final Logger logger = LoggerFactory.getLogger(DMSTODCS001ServiceImpl.class);
	
	private Date startTime = new Date();	// 记录接口开始执行的时间
	private Integer dataSize = 0;	// 数据数量
	private Integer status = OemDictCodeConstants.IF_TYPE_NO;	// 默认失败
	private String exceptionMsg = "";	// 错误日志
	
	/**
	 * 接口执行入口
	 * @throws Exception 
	 */
	@Override
	public String handleExecutor(List<DCSTODMS001VO> dtoList) throws Exception{
			logger.info("========== 官网订单线索撞单校验结果接收DMS：执行开始 ==========");
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
			
			logger.info("========== 官网订单线索撞单校验结果接收DMS：执行失败 ==========");
			
		} finally {
			msg = "1";
			logger.info("========== 官网订单线索撞单校验结果接收DMS：执行结束 ==========");
			Base.detach();
			dbService.clean();//清除事务
			
			beginDbService();
			try {
				// 记录日志表
				CommonBSUV.insertTtEcOrderHistory(this.getClass().getName(), "官网订单线索撞单接口：DMS->DCS", startTime, dataSize, status, exceptionMsg, null, null, new Date());
				dbService.endTxn(true);
			} catch (Exception e2) {
				dbService.endTxn(false);
				logger.info("================官网订单线索撞单接口：DMS->DCS=========");
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
	private void saveTiTable(List<DCSTODMS001VO> dtoList) throws Exception {
		
		logger.info("========== 官网订单线索撞单校验结果接收DMS：写入接口表开始 ==========");
		
		// 循环更新数据
		for (int i=0;i<dtoList.size();i++) {
			
			DCSTODMS001VO vo = dtoList.get(i);
			
			String result = doCheckData(vo);
			
			if (result.equals("")) {
				
				/*
				 * 校验成功
				 */
				
				List list=TiEcHitSinglePO.findBySQL("select  *  from TI_EC_HIT_SINGLE_DCS where dealer_code=? and tel=? and is_del=? ",new Object[]{vo.getEntityCode(),vo.getTel(),OemDictCodeConstants.IS_DEL_00});
				TiEcHitSinglePO setPo = new TiEcHitSinglePO();
				if(list!=null&&list.size()>0){
					setPo.setInteger("is_hit_single",vo.getIsHitSingle());
					setPo.setString("SOLD_BY",vo.getSoldBy());
					setPo.setString("SOLD_MOBILE",vo.getSoldMobile());
					setPo.setInteger("result",OemDictCodeConstants.IF_TYPE_YES);
					setPo.setString("update_by",DEConstant.DE_CREATE_BY);
					setPo.setDate("update_date",new Date());
					setPo.saveIt();
				}
				
				
			} else {
				
				/*
				 * 校验失败
				 */
				List list=TiEcHitSinglePO.findBySQL("select  *  from TI_EC_HIT_SINGLE_DCS where dealer_code=? and tel=? and is_del=? ",new Object[]{vo.getEntityCode(),vo.getTel(),OemDictCodeConstants.IS_DEL_00});
				TiEcHitSinglePO setPo = new TiEcHitSinglePO();
				if(list!=null&&list.size()>0){
					setPo.setInteger("is_hit_single",vo.getIsHitSingle());
					setPo.setString("SOLD_BY",vo.getSoldBy());
					setPo.setString("SOLD_MOBILE",vo.getSoldMobile());
					setPo.setInteger("result",OemDictCodeConstants.IF_TYPE_NO);
					setPo.setString("MESSAGE",result);
					setPo.setString("update_by",DEConstant.DE_CREATE_BY);
					setPo.setDate("update_date",new Date());
					setPo.saveIt();
				}
				
				
			}
			
		}
		
		logger.info("========== 官网订单线索撞单校验结果接收DMS：写入接口表结束 ==========");
		
	}
	
	/**
	 * 数据校验
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public String doCheckData(DCSTODMS001VO vo) throws Exception {
		
		String result = "";
		
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
		
		// 是否撞单标识非空校验
		if (null == vo.getIsHitSingle() || vo.getIsHitSingle().toString().trim().equals("")) {
			result = "[IS_HIT_SINGLE]是否撞单标识为空";
			return result;
		}
		
		// 是否撞单标识有效性校验
		if (vo.getIsHitSingle() != 12781001 && vo.getIsHitSingle() != 12781002) {
			result = "[IS_HIT_SINGLE]是否撞单标识无效";
			return result;
		}
		
		if (!CheckUtil.checkNull(result)) {
			logger.info("========== 官网订单线索撞单校验结果接收DMS：" + result + " ==========");
		} else {
			logger.info("========== 官网订单线索撞单校验结果接收DMS：数据校验通过 ==========");
		}
		
		return result;
		
	}
	
	/**
	 * 测试接入口
	 * @param args
	 */
	public static void main(String[] args) {
		
		try {
			
			DMSTODCS001Service dmstodcs001 = new DMSTODCS001ServiceImpl();
			
			List<DCSTODMS001VO> voList = new ArrayList<DCSTODMS001VO>();
			
			for (int i = 10; i <= 11; i++) {
				
				DCSTODMS001VO vo = new DCSTODMS001VO();
				vo.setDealerCode("CJ770700");	// DMS经销商代码
				vo.setDealerCodeName("北京港龙");	// 经销商名称
				vo.setTel("13999999999");	// 客户联系电话
				vo.setSoldBy("huyu");	// 销售顾问
				vo.setSoldMobile("13888888888");	// 销售顾问手机号
				vo.setIsHitSingle(12781001);	// 是否撞单标识(12781001：是;12781002：否)
				vo.setMessage("insert测试byhuyu");	// 校验结果（错误描述）
				voList.add(vo);
				
			}
			
			dmstodcs001.execute(voList);
			
		} catch (Exception e) {
		}
	}
	
	/**
	 * 处理测试VO
	 * @param voList
	 * @throws Exception
	 */
	@SuppressWarnings({ "unused", "rawtypes" })
	public void execute(List<DCSTODMS001VO> voList) throws Exception {
			
		// 循环更新数据
		for (int i = 0; i < voList.size(); i++) {
			
			DCSTODMS001VO vo = voList.get(i);
			
			String result = doCheckData(vo);
			
	if (result.equals("")) {
				
				/*
				 * 校验成功
				 */
				
				List list=TiEcHitSinglePO.findBySQL("select  *  from TI_EC_HIT_SINGLE_DCS where dealer_code=? and tel=? and is_del=? ",new Object[]{vo.getEntityCode(),vo.getTel(),OemDictCodeConstants.IS_DEL_00});
				TiEcHitSinglePO setPo = new TiEcHitSinglePO();
				if(list!=null&&list.size()>0){
					setPo.setInteger("is_hit_single",vo.getIsHitSingle());
					setPo.setString("SOLD_BY",vo.getSoldBy());
					setPo.setString("SOLD_MOBILE",vo.getSoldMobile());
					setPo.setInteger("result",OemDictCodeConstants.IF_TYPE_YES);
					setPo.setString("update_by",DEConstant.DE_CREATE_BY);
					setPo.setDate("update_date",new Date());
					setPo.saveIt();
				}
				
				
			} else {
				
				/*
				 * 校验失败
				 */
				List<TiEcHitSinglePO> list=TiEcHitSinglePO.findBySQL("select  *  from TI_EC_HIT_SINGLE_DCS where dealer_code=? and tel=? and is_del=? ",new Object[]{vo.getEntityCode(),vo.getTel(),OemDictCodeConstants.IS_DEL_00});
				TiEcHitSinglePO setPo = new TiEcHitSinglePO();
				if(list!=null&&list.size()>0){
					setPo.setInteger("is_hit_single",vo.getIsHitSingle());
					setPo.setString("SOLD_BY",vo.getSoldBy());
					setPo.setString("SOLD_MOBILE",vo.getSoldMobile());
					setPo.setInteger("result",OemDictCodeConstants.IF_TYPE_NO);
					setPo.setString("MESSAGE",result);
					setPo.setString("update_by",DEConstant.DE_CREATE_BY);
					setPo.setDate("update_date",new Date());
					setPo.saveIt();
				}
				
				
			}
			
		}
	}
	
	
	
	
	
	

}
