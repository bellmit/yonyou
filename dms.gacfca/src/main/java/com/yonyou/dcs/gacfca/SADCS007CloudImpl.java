package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DealersVehicleTransferDao;
import com.yonyou.dms.DTO.gacfca.SA007Dto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SADMS007Coud;
import com.yonyou.f4.common.database.DBService;

/**
 * 
* @ClassName: SADCS007CloudImpl 
* @Description: 经销商之间车辆调拨下发
* @author zhengzengliang 
* @date 2017年4月12日 下午7:19:37 
*
 */
@SuppressWarnings("rawtypes")
@Service
public class SADCS007CloudImpl implements SADCS007Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS007CloudImpl.class);
	
	@Autowired
	DealersVehicleTransferDao dao ;
	
	@Autowired SADMS007Coud sadms007 ;
	
	@Autowired DBService dbService;
	
	@Override
	public String execute(List<Long> dealerIds, Long vehicleId, Long inDealerId, Long outDealerId)
			throws Exception {
		logger.info("====经销商之间车辆调拨下发开始====");
//		dbService();
		try {
			//获取需要下发的数据
			List<SA007Dto> vos = getDataList(vehicleId, inDealerId, outDealerId) ;
			if (null == vos || vos.size() == 0) {
				return null;
			}
			sendData(dealerIds, vos);
//			dbService.endTxn(true);
		} catch (Exception e) {
//			dbService.endTxn(false);
			e.printStackTrace();
			logger.info("================经销商之间车辆调拨数据下发异常（SADCS007）====================");
		} finally {
//			dbService.clean();
			logger.info("====经销商之间车辆调拨下发结束====");
		}
		return null;
	}

	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public List<SA007Dto> getDataList(Long vehicleId, Long inDealerId, Long outDealerId)
			throws ServiceBizException {
		List<SA007Dto> vos = dao.queryVehicleTransferInfo(vehicleId,inDealerId,outDealerId);
		if (null == vos || vos.size() == 0) {
			return null;
		}
		return vos;
	}
	
	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	public void sendData(List<Long> dealerIds,List<SA007Dto> vos){
		try {
			//第二步：循环获取经销商 所有拥有的业务范围数据并下发
			for (Long dealerId : dealerIds) {
				Map dmsDealer = dao.getDmsDealerCode(dealerId);
				if(!StringUtils.isNullOrEmpty(dmsDealer.get("DMS_CODE"))){
					int flag = sadms007.getSADMS007(dmsDealer.get("DMS_CODE").toString(),vos);
					if(flag==1){
						logger.info("================经销商之间车辆调拨数据下发成功（SADMS007）====================");
					}else{
						logger.info("================经销商之间车辆调拨数据下发失败（SADMS007）====================");
					}
				}else{
					//经销商无业务范围
					logger.info("================经销商之间车辆调拨数据下发经销商无业务范围（SADMS007）====================");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("================经销商之间车辆调拨数据下发异常（SADMS007）====================");
		}
	}

}
