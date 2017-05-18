package com.yonyou.dms.vehicle.service.claimManage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.claimManage.DBLockDao;

/**
* @author liujm
* @date 2017年5月11日
*/

@Service
public class DBLockServiceImpl implements DBLockService{
	
	//合格证更新业务
	public static final String CERT_INFO_MODIFY = "CERT_INFO_MODIFY";
	//合格证更改申请
	public static final String CERT_CHANGE_APPLY = "CERT_CHANGE_APPLY";
	//索赔单自动审核
	public static final String CLAIM_AUTO_CHECK = "CLAIM_AUTO_CHECK";
	//回运旧件抵扣
	public static final String OLDPART_DEDUCT = "OLDPART_DEDUCT";
	//抵扣单结算
	public static final String DEDUCT_BALANCE = "DEDUCT_BALANCE";
	//抵扣单结算锁标识
	public static final String DEDUCT_BALANCE_LOCK ="10001";
	//结算单结算
	public static final String BILL_BALANCE = "BILL_BALANCE";
	//结算单结算锁标识
	public static final String BILL_BALANCE_LOCK ="20000";
	//结算单通知财务
	public static final String BILL_TO_FINANCE = "BILL_TO_FINANCE";
	//结算单通知财务锁标识
	public static final String BILL_TO_FINANCE_LOCK ="30000";	
	//索赔单人工审核(保修)
	public static final String CLAIM_TO_CHECK = "CLAIM_TO_CHECK";
	//索赔单人工审核锁标识(保修)
	public static final String CLAIM_TO_MAINTAINCHECK_LOCK ="40001";	
	//索赔单人工审核(保养)
	public static final String CLAIM_TO_MAINTAINCHECK = "CLAIM_TO_MAINTAINCHECK";
	//索赔单人工审核锁标识(保养)
	public static final String CLAIM_TO_CHECK_LOCK ="40000";	
	//结算单财务审核
	public static final String CLAIM_FINANCE_CHECK = "CLAIM_FINANCE_CHECK";
	//结算单财务审核锁标识
	public static final String CLAIM_FINANCE_CHECK_LOCK ="50000";
	//结算单通知服务商开票
	public static final String BILL_NOTICE ="BILL_NOTICE";
	//结算单通知服务商开票锁标识
	public static final String BILL_NOTICE_LOCK ="60000";	
	//结算单财务审核
	public static final String OLD_FINANCE_CHECK = "OLD_FINANCE_CHECK";
	//结算单财务审核锁标识
	public static final String OLD_FINANCE_CHECK_LOCK ="70000";
	
	//抵扣结算单通知服务商开票
	public static final String OLD_BILL_NOTICE ="OLD_BILL_NOTICE";
	//抵扣结算单通知服务商开票锁标识
	public static final String OLD_BILL_NOTICE_LOCK ="80000";
	
	//保养索赔保存提报
	public static final String MAINTENCE_SUBMIT = "MAINTENCE_SUBMIT";
	//保养索赔保存提报锁标识
	public static final String MAINTENCE_SUBMIT_LOCK ="90000";
	//索赔保存提报
	public static final String WARRANTY_SUBMIT = "WARRANTY_SUBMIT";
	//索赔保存提报锁标识
	public static final String WARRANTY_SUBMIT_LOCK ="100000";	
	//PDI索赔保存提报
	public static final String PDI_WARRANTY_SUBMIT = "PDI_WARRANTY_SUBMIT";
	//PDI索赔保存提报锁标识
	public static final String PDI_WARRANTY_SUBMIT_LOCK ="110000";	
	//NWS索赔保存提报
	public static final String NWS_WARRANTY_SUBMIT = "NWS_WARRANTY_SUBMIT";
	//NWS索赔保存提报锁标识
	public static final String NWS_WARRANTY_SUBMIT_LOCK ="120000";	
	//FQS文件生成
	public static final String FQS_FILE_CREATE = "FQS_FILE_CREATE";
	//FQS文件生成锁标识
	public static final String FQS_FILE_CREATE_LOCK ="130000";
	//LON文件生成
	public static final String LON_FILE_CREATE = "LON_FILE_CREATE";
	//LON文件生成锁标识
	public static final String LON_FILE_CREATE_LOCK ="140000";
	//LON文件读取
	public static final String LON_FILE_READ = "LON_FILE_READ";
	//LON文件读取锁标识
	public static final String LON_FILE_READ_LOCK ="150000";
	//LON文件读取
	public static final String ACTIVITY_VHCL_SEND = "ACTIVITY_VHCL_SEND";
	//LON文件读取锁标识
	public static final String ACTIVITY_VHCL_SEND_LOCK ="160000";
	//现场报告锁审核
	public static final String FIELD_REPORT_APPROVE = "FIELD_REPORT_APPROVE";
	//现场报告锁审核标识
	public static final String FIELD_REPORT_APPROVE_LOCK ="170000";
	/**
     * 销售 START
     */
	
	//订单资源审核
	public static final String ORDER_RESOURCE_CHECK ="ORDER_RESOURCE_CHECK";
	//补贴审核
	public static final String ALLOWANCE_CHECK ="ALLOWANCE_CHECK";
	//订单取消
	public static final String ORDER_CANCEL ="ORDER_CANCEL";
	//手工配车
	public static final String MANUAL_MATCH_VEHICLE ="MANUAL_MATCH_VEHICLE";
	//车辆分配变更
	public static final String VEHICLE_MATCH_MOD ="VEHICLE_MATCH_MOD";
	//车辆出库
	public static final String VEHICLE_OUT_STORAGE ="VEHICLE_OUT_STORAGE";
	//退车入库
	public static final String RETURN_STORAGE ="RETURN_STORAGE";
	//开票申请
	public static final String TICKET_APPLY ="TICKET_APPLY";
	
	/**
     * 销售 END
     */
	/**
	 * 配件 START
	 */
	//配件销售订单，登记保存、订单分配、备货中控制
	public static final String PART_SALE_ORDER_LOCK = "PART_SALE_ORDER_LOCK";
	//配件销售订单，登记保存、订单分配、备货中控制
	public static final String PART_SALE_ORDER_LOCK_NO ="500000";	
	//配件发货完成
	public static final String PART_DELIVERY_OUT_LOCK = "PART_DELIVERY_OUT_LOCK";
	//配件发货完成
	public static final String PART_DELIVERY_OUT_LOCK_NO ="510000";
	//配件发货生成
	public static final String PART_DELIVERY_CREATE_LOCK = "PART_DELIVERY_CREATE_LOCK";
	/**
	 * 配件 END
	 */	
	
	
	@Autowired
	private DBLockDao lockDao;
	
	
	/**
	 * 取的数据库锁
	 * 注：该方法主动提交数据库事物，使用时最好调用前没有其他数据库操作
	 * @param lockId 锁标识
	 * @param businessType 业务类型 @see DBLockUtil.BTYPE_XX;
	 * @return boolean true:成功获得锁  false:资源正忙
	 * @throws Exception 
	 */
	@Override
	public boolean lock(String lockId, String businessType) throws ServiceBizException {
		boolean isLock = true;
		if(!Utility.testString(lockId) || !Utility.testString(businessType)){
			isLock = false;
			return isLock;
		}
				
		//取得锁
		boolean flag = lockDao.getLock(lockId, businessType);
		if(flag){
			isLock = false;
			return isLock;
		}
		//创建锁
		lockDao.createLock(lockId, businessType);
		

		return isLock;
	}
	/**
	 * 释放锁
	 * @param lockId 锁标识
	 * @param businessType 业务类型
	 */
	@Override
	public void freeLock(String lockId, String businessType) throws ServiceBizException {
		if(!Utility.testString(lockId) || !Utility.testString(businessType)){
			return;
		}
		lockDao.freeLock(lockId, businessType); 
		
	}

}
