package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.TmMarketActivityDao;
import com.yonyou.dms.DTO.gacfca.TmMarketActivityDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.CLDMS004Coud;
import com.yonyou.f4.common.database.DBService;
/**
 * 
* @ClassName: CLDCS004CloudImpl 
* @Description: 市场活动（活动主单、车型清单）
* @author zhengzengliang 
* @date 2017年4月6日 上午11:21:41 
*
 */
@Service
public class CLDCS004CloudImpl extends BaseCloudImpl implements CLDCS004Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(CLDCS004CloudImpl.class);
	
	@Autowired 
	DBService dbService;
	
	@Autowired
	TmMarketActivityDao tmMarketActivityDao ;
	
	@Autowired
	CLDMS004Coud cldms004 ;
	
	@Autowired
	DeCommonDao deCommonDao;

	@Override
	public String execute(List<String> dealerList, String[] groupIds) throws ServiceBizException {
		logger.info("================车型价格信息下发执行开始（CLDCS004）====================");
		if(null==dealerList || dealerList.size()==0){
			dealerList = tmMarketActivityDao.getAllDcsCode(0);
		}
		for (int i = 0; i < dealerList.size(); i++) {
			String dealerCode = dealerList.get(i);
			sendData(getDataList(),dealerCode);
		}
		logger.info("================车型价格信息下发执行结束（CLDCS004）====================");
		return null;
	}

	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	public void sendData(LinkedList<TmMarketActivityDto> list, String dealerCode){
		try {
			// 店端经销商代码取得
			Map dmsDealer = deCommonDao.getDmsDealerCode(dealerCode);
			// 店端代码存在且业务范围内物料存在的场合，下发物料组
			if ((!StringUtils.isNullOrEmpty(dmsDealer.get("DMS_CODE"))) && list != null && list.size()>0) {
				
				int flag = cldms004.getCLDMS004(list,dmsDealer.get("DMS_CODE").toString());
				if(flag==1){
					logger.info("================车辆价格信息下发成功（CLDCS004）====================");
					//将TM_MARKET_ACTIVITY中 is_down=0或者null的字段更新为1
					tmMarketActivityDao.updateTmMarketActivityDownStatus();
				}else{
					logger.info("================车辆价格信息下发失败（CLDCS004）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================车辆价格信息下发经销商无业务范围（CLDCS004）====================");
			}
		} catch (Exception e) {
			logger.info("================车辆价格信息下发异常（CLDCS004）====================");
		}
	}

	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public LinkedList<TmMarketActivityDto> getDataList() throws ServiceBizException {
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
		LinkedList<TmMarketActivityDto> vos = null;
		vos = tmMarketActivityDao.queryAllTmMarketActivity();
		Integer size = vos==null?0:vos.size();
		logger.info("================下发数据大小："+ size +"====================");
		return vos;
	}

}
