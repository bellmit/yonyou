package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.yonyou.dms.gacfca.SADMS114;
import com.yonyou.dms.DTO.gacfca.PayingBankDTO;
import com.yonyou.dms.common.domains.PO.basedata.TcBankPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.f4.common.database.DBService;

@Service
public class SADMS064CloudImpl extends BaseCloudImpl implements SADMS064Cloud {
	
	@Autowired DBService dbService;
	
	@Autowired SADMS114 SADMS114;
	
	private static final Logger logger = LoggerFactory.getLogger(SADMS064CloudImpl.class);

	@SuppressWarnings({ "static-access", "rawtypes" })
	@Override
	public void sendData(Long bankId,String dealerCode) throws Exception {
		try {
			logger.info("************************** 合作银行下发开始 ********************");
			dbService.beginTxn(getTenantId());
			List<PayingBankDTO> vos = queryPolicyApplyDateInfo(bankId);
			Map map = OemBaseDAO.getDmsDealerCode(dealerCode);
			SADMS114.getSSADMS114(map.get("DMS_CODE").toString(), vos);
			logger.info("#########合作银行下发,下发了(" + vos.size() + ")条数据#####################");
			logger.info("************************** 合作银行下发结束 ********************");
			//状态修改,修改成已下发
			Integer btcCode = vos.get(0).getBankCode();
			TcBankPO updatePo = new TcBankPO();
			updatePo.update("IS_SEND = ? ,SEND_BY = ? ,SEND_DATE = ? , UPDATE_BY = ?, UPDATE_DATE = ? ", "BTC_CODE = ?", 
					1,FrameworkUtil.getLoginInfo().getUserId(),new Date(),11111111L,new Date(),btcCode);
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(false);
			logger.info("##############SADMS064合作银行下发异常#################");
			throw new ServiceBizException(e);
		} finally {
			try{
				dbService.clean();
			}catch(Exception e){
				logger.info(e.getMessage(),e);
			}	
		}
		
	}
	
	@SuppressWarnings("rawtypes")
	private List<PayingBankDTO> queryPolicyApplyDateInfo(Long bankId) {
		StringBuffer sql = new StringBuffer();
		sql.append("select     \n");
		sql.append("    TC.ID,    \n");
		sql.append("    TC.BANK_NAME,    \n");
		sql.append("    TC.STATUS,    \n");
		sql.append("    TC.UPDATE_STATUS,    \n");
		sql.append("    TC.BTC_CODE    \n");
		sql.append("FROM TC_BANK TC    \n");
		sql.append("WHERE TC.ID="+ bankId +"   \n");
		sql.append("and (TC.IS_SEND = 0 or TC.IS_SEND IS null)   \n");
		List<Map> map = Base.findAll(sql.toString());
		List<PayingBankDTO> list = wrapperVO(map);
		return list;
	}
	
	@SuppressWarnings("rawtypes")
	protected List<PayingBankDTO> wrapperVO(List<Map> ps) {
		if(ps!=null && ps.size()>0){
			List<PayingBankDTO> List = new ArrayList<>();
			PayingBankDTO vo = new PayingBankDTO();
			for(int i=0;i<ps.size();i++){
				Map rs = ps.get(i);
				try {
					vo.setBankName(rs.get("BANK_NAME").toString());
					vo.setStatus((Integer) rs.get("STATUS"));
					vo.setBankCode((Integer) rs.get("BTC_CODE"));
					vo.setUpdateStatus((Integer) rs.get("UPDATE_STATUS"));
				} catch (Exception e) {
					throw new ServiceBizException(e);
				}
				List.add(vo);
			}
			return List;
		}else{
			return null;
		}
	}

	@Override
	public String handleExecute() throws ServiceBizException {
		return null;
	}

}
