package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVehicleTransferPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.dao.vehicleAllotDao.DealerAllotApplyDao;

@Service
public class DealerAllotApplyServiceImpl implements DealerAllotApplyService {
	
	@Autowired
	private DealerAllotApplyDao applyDao;

	@Override
	public PageInfoDto search(Map<String, String> param) {
		PageInfoDto dto = applyDao.search(param);
		return dto;
	}

	@Override
	public Map<String, String> vehicleTransfersApply(String inDealerCode, String reason, String vehicleId) {
		String[] vehicleIds = vehicleId.split(",");
		Map<String,String> map = new HashMap<String,String>();
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		boolean flag = false;
		/*
		 * 效验经销商业务范围
		 */
		String vins = "";
		for(int i = 0; i < vehicleIds.length;i++){
			if(i == 0){
				vehicleId = vehicleIds[i];
			}else{
				vehicleId = vehicleId + "," +vehicleIds[i];
			}
		}
		List<Map> getBusinessScope = applyDao.getBusinessScope(vehicleId, inDealerCode);
		if(null != getBusinessScope && getBusinessScope.size() > 0){
			for (int i = 0; i < getBusinessScope.size(); i++) {
				if (getBusinessScope.get(i).get("BUSINESS_SCOPE").equals("FALSE")) {
					vins += getBusinessScope.get(i).get("VIN") + ",";
				}
			}
			map.put("code", "false");
			map.put("msg", "VIN号["+vins+"]业务范围不符!");
			return map;
		}else if(loginUser.getDealerCode().equals(inDealerCode)){
			map.put("code", "false");
			map.put("msg", "调入经销商与调出经销商是同一经销商，请重新选择!");
			return map;
		}else{
			List<TmDealerPO>  dealerList = applyDao.getByCode(inDealerCode,OemDictCodeConstants.DEALER_TYPE_DVS);
			if(dealerList != null && dealerList.size() > 0 ){
				List<TmDealerPO>  list = applyDao.getByCode(inDealerCode);
				if(list != null && list.size() > 0){
					TmDealerPO dealerPo = (TmDealerPO) list.get(0);
					int transRegionalFlag = this.transRegionalQuery(loginUser.getDealerCode(), inDealerCode);
					for(int i = 0; i< vehicleIds.length; i++){
						List<TtVsVehicleTransferPO> tList = applyDao.getTransferByIds(loginUser.getDealerId(),Long.parseLong(vehicleIds[i]));
						if(tList != null && tList.size() > 0){
							for(int j = 0; j < tList.size(); j++){
								TtVsVehicleTransferPO tpo = tList.get(j);
								tpo.setLong("IN_DEALER_ID", dealerPo.getLong("DEALER_ID"));// 调入经销商
								tpo.setInteger("CHECK_STATUS", OemDictCodeConstants.TRANSFER_CHECK_STATUS_01);// 审核状态
								tpo.setTimestamp("TRANSFER_DATE", currentTime);
								tpo.setTimestamp("CREATE_DATE", currentTime);
								tpo.setLong("CREATE_BY", loginUser.getUserId());
								tpo.setTimestamp("UPDATE_DATE", currentTime);
								tpo.setLong("UPDATE_BY", loginUser.getUserId());
								tpo.setString("TRANSFER_REASON", reason);
								tpo.setInteger("TRANS_REGIONAL_FLAG", transRegionalFlag);
								flag = tpo.saveIt();
								if(!flag){
									map.put("code", "false");
									map.put("msg", "调拨失败!");
									return map;
								}
								Long transferId = tpo.getLongId();
								//删除tt_vs_vehicle_transfer_chk表
//								applyDao.delTransferChk(transferId);
								TtVsVehicleTransferPO.delete("TRANSFER_ID = ? ", transferId);
							}
						}else{
							TtVsVehicleTransferPO tpo = new TtVsVehicleTransferPO();
							tpo.setLong("OUT_DEALER_ID", loginUser.getDealerId());
							tpo.setLong("VEHICLE_ID", Long.parseLong(vehicleIds[i]));
							tpo.setLong("IN_DEALER_ID", dealerPo.getLong("DEALER_ID"));// 调入经销商
							tpo.setInteger("CHECK_STATUS", OemDictCodeConstants.TRANSFER_CHECK_STATUS_01);// 审核状态
							tpo.setTimestamp("TRANSFER_DATE", currentTime);
							tpo.setTimestamp("CREATE_DATE", currentTime);
							tpo.setLong("CREATE_BY", loginUser.getUserId());
							tpo.setString("TRANSFER_REASON", reason);
							tpo.setInteger("TRANS_REGIONAL_FLAG", transRegionalFlag);
							flag = tpo.saveIt();
							if(!flag){
								map.put("code", "false");
								map.put("msg", "调拨失败!");
								return map;
							}
						}
						map.put("code", "true");
						map.put("msg", "操作成功!");
					}
				}else{
					map.put("code", "false");
					map.put("msg", "经销商代码不存在!");
				}
			}else{
				map.put("code", "false");
				map.put("msg", "经销商代码不存在!");
			}
			
		}

		return map;
	}

	private int transRegionalQuery(String outDealerCode, String inDealerCode) {
		List<Map> out = applyDao.regionalInfoQuery(outDealerCode);
		String outSmallOrgCode = "";
		String outBigOrgCode = "";
		
		List<Map> in = applyDao.regionalInfoQuery(inDealerCode);
		String inSmallOrgCode = "";
		String inBigOrgCode = "";
		
		if (null != out && out.size() > 0) {
			outSmallOrgCode = (String) out.get(0).get("S_ORG_CODE");
			outBigOrgCode = (String) out.get(0).get("B_ORG_CODE");
		}
		
		if (null != in && in.size() > 0) {
			inSmallOrgCode = (String) in.get(0).get("S_ORG_CODE");
			inBigOrgCode = (String) in.get(0).get("B_ORG_CODE");
		}
		
		/*
		 * 小区内调拨
		 */
		if (outSmallOrgCode.equals(inSmallOrgCode)) {
			return 0;
		}
		
		/*
		 * 小区间调拨
		 */
		if (!outSmallOrgCode.equals(inSmallOrgCode) && outBigOrgCode.equals(inBigOrgCode)) {
			return 1;
		}
		
		/*
		 * 大区间调拨
		 */
		if (!outSmallOrgCode.equals(inSmallOrgCode) && !outBigOrgCode.equals(inBigOrgCode)) {
			return 2;
		}
		
		return 4;
	}

	@Override
	public Map<String,Object> getDealerResult(String dealerCode) {
		List<TmDealerPO>  list = applyDao.getByCode(dealerCode);
		TmDealerPO po = list.get(0);
		Map<String,Object> map = po.toMap();
		return map;
	}

	@Override
	public Map<String, String> checkDealer(String dealerCode) {
		Map<String, String> map = new HashMap<String,String>();
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(dealerCode.equals(loginUser.getDealerCode())){
			map.put("code", "false");
			map.put("msg", "调入经销商与调出经销商是同一经销商，请重新选择!");
		}else{			
			List<TmDealerPO>  list = applyDao.getByCode(dealerCode);
			if(list != null && !list.isEmpty()){
			}else{
				map.put("code", "false");
				map.put("msg", "经销商代码不存在!");			
			}
		}
		return map;
	}

}
