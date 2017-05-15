package com.yonyou.dcs.gacfca;

import java.sql.Connection;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;

import com.yonyou.dms.common.domains.PO.basedata.MsgIdPO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.service.TenantDealerMappingService;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.f4.common.database.DBService;

/**
 * 
 * @author 夏威
 * 2017-03-27
 */
@SuppressWarnings("rawtypes")
public class BaseCloudImpl implements BaseCloud{
	
	@Autowired 
	DBService dbService;
	/**
	 * 根据上端dealerId查询上端dealerCode
	 * @param dealerId  上端dealerId
	 * @return dealerCode   上端经销商代码
	 */
	public String getDealerCode(Long dealerId){
		String dealerCode = null;
		TmDealerPO po = TmDealerPO.findById(dealerId);
		dealerCode = CommonUtils.checkNull(po.getString("DEALER_CODE"));
		return dealerCode;
	}
	
	@Autowired
    private TenantDealerMappingService tenantDealerSerivce;
    
    
    
	/**
	 * 获取租户节点
	 * @param tenantId
	 */
	public String getTenantId() {
		Map<String,Map<String,String>> tenantDealerMaping = tenantDealerSerivce.getAll();
		return tenantDealerMaping.get(OemDictCodeConstants.OEM_LOGING_DEALERCODE).get("TENANT_ID");
	}
	
	public void beginDbService(){
		try {
			if (dbService.isTenantMode()) {
			    dbService.beginTxn(getTenantId());
			    Connection conn = dbService.openConn(getTenantId());
			    Base.attach(conn);
			} else {
				dbService.beginTxn();
				Connection conn = dbService.openConn(getTenantId());
				Base.attach(conn);
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("开启事务异常");
		}
	}
	public void dbService(){
		try {
			if(dbService.isTenantMode()) {
				dbService.beginTxn(getTenantId());
			} else {
				dbService.beginTxn();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException("开启事务异常");
		}
	}
	
	/**
	 * 获取Msg_id
	 */
	public Long getMsgId(){
		beginDbService();
		MsgIdPO po = new MsgIdPO();
		po.saveIt();
		return po.getLongId();
	}

	@Override
	public List<String> getAllDmsCode(Integer sendType) throws Exception {
		List<String> list = null;
		beginDbService();
		try {
			list = OemBaseDAO.getAllDmsCode(sendType);
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(true);
		}finally {
			Base.detach();
			dbService.clean();
		}
		return list;
	}

	@Override
	public List<String> getAllDcsCode(Integer sendType) throws Exception {
		List<String> list = null;
		beginDbService();
		try {
			list = OemBaseDAO.getAllDcsCode(sendType);
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(true);
		}finally {
			Base.detach();
			dbService.clean();
		}
		return list;
	}

	@Override
	public Map<String, Object> getDmsDealerCodeForDealerId(Long dealerId) throws Exception {
		Map<String, Object> tesult = null;
		beginDbService();
		try {
			tesult = OemBaseDAO.getDmsDealerCodeForDealerId(dealerId);
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(true);
		}finally {
			Base.detach();
			dbService.clean();
		}
		return tesult;
	}

	@Override
	public Map<String, Object> getAllDmsDealerCodeForDealerId(Long dealerId) throws Exception {
		Map<String, Object> tesult = null;
		beginDbService();
		try {
			tesult = OemBaseDAO.getAllDmsDealerCodeForDealerId(dealerId);
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(true);
		}finally {
			Base.detach();
			dbService.clean();
		}
		return tesult;
	}

	@Override
	public Map getSeDcsDealerCode(String dealerCode) throws Exception {
		Map tesult = null;
		beginDbService();
		try {
			tesult = OemBaseDAO.getSeDcsDealerCode(dealerCode);
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(true);
		}finally {
			Base.detach();
			dbService.clean();
		}
		return tesult;
	}

	@Override
	public Map getSaDcsDealerCode(String dealerCode) throws Exception {
		Map tesult = null;
		beginDbService();
		try {
			tesult = OemBaseDAO.getSaDcsDealerCode(dealerCode);
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(true);
		}finally {
			Base.detach();
			dbService.clean();
		}
		return tesult;
	}

	@Override
	public Map getDmsDealerCode(String dealerCode) throws Exception {
		Map tesult = null;
		beginDbService();
		try {
			tesult = OemBaseDAO.getDmsDealerCode(dealerCode);
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(true);
		}finally {
			Base.detach();
			dbService.clean();
		}
		return tesult;
	}
	
	
	
}
