package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SaleMaterialPriceDao;
import com.yonyou.dcs.dao.SaleVehicleShippingDao;
import com.yonyou.dms.DTO.gacfca.VehicleShippingDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SADMS001Coud;
/**
 * 
* @ClassName: SADCS001CloudImpl 
* @Description: 车辆发运信息下发
* @author zhengzengliang 
* @date 2017年4月11日 下午4:43:19 
*
 */
@Service
public class SADCS001CloudImpl extends BaseCloudImpl implements SADCS001Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS001CloudImpl.class);
	
	@Autowired
	SaleMaterialPriceDao saleMaterialPriceDao ;
	
	@Autowired
	SADMS001Coud sadms001;
	
	@Autowired
	SaleVehicleShippingDao saleBaseDao ;
	
	@Autowired
	DeCommonDao deCommonDao;
	
	@Override
	public String execute(String dealerCode, String dealerId, String vehicleId) throws ServiceBizException {
		logger.info("================车辆发运信息下发执行开始（SADCS001）====================");

		sendData(dealerCode,dealerId,vehicleId);
		logger.info("================车辆发运信息下发执行结束（SADCS001）====================");
		return null;
	}
	
	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	public List<String> sendData(String dealerCode, String dealerId, String vehicleId){
		// 错误消息
		List<String> errCodes = new ArrayList<String>();
		try {
			// 获取货运单信息
			LinkedList<VehicleShippingDto> list = getDataList(dealerId,vehicleId);
			// 获取店端经销商代码信息
			Map dmsDealer = deCommonDao.getDmsDealerCode(dealerCode);
			// 店端代码存在且货运单存在的场合，下发
			if ((!StringUtils.isNullOrEmpty(dmsDealer.get("DMS_CODE"))) && list != null && list.size()>0) {
				int flag = sadms001.getSADMS001(list,dmsDealer.get("DMS_CODE").toString());
				if(flag==1){
					logger.info("================车辆发运信息下发成功（SADCS001）====================");
					// 修改车辆为发运下发状态，即将车辆表的IS_SEND更新为1
					saleBaseDao.updateVehicle(dealerId, vehicleId);
				}else{
					logger.info("================车辆发运信息下发失败（SADCS001）====================");
					errCodes.add(dealerCode);
				}
			}else{
				//经销商无业务范围
				logger.info("================车辆发运信息下发经销商无数据(SADCS001)====================");
				errCodes.add(dealerCode);
			}
		} catch (Exception e) {
			logger.info("================车辆发运信息下发异常（SADCS001）====================");
			errCodes.add(dealerCode);
		}
		return errCodes;
	}

	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public LinkedList<VehicleShippingDto> getDataList(String dealerId, String vehicleId)
			throws ServiceBizException {
		/**
		 *  获取数据逻辑：
		 * 	判断经销商是否为空
		 * 	  不为空、根据经销商查询业务范围重新给groupId赋值
		 * 	 为空      、判断groupId是否为空：不为空根据GroupID取物料信息，为空取所有
		 * 	 发送逻辑：每个经销商一次查一次
		 *  原有逻辑：
		 *  判断选中的业务范围都存在选中或所有经销商的业务范围
		 *  否则另外存储为Map
		 *  发送逻辑：分为两种情况发送
		 */
		
		LinkedList<VehicleShippingDto>  vos = saleBaseDao.queryVehicleShipInfo2(dealerId, vehicleId);
		
		if (null == vos || vos.size() == 0) {
			return null;
		}

		return vos;
	}

}
