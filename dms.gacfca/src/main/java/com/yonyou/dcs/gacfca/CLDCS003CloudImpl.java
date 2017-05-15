package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SaleMaterialGroupDao;
import com.yonyou.dcs.dao.SaleMaterialPriceDao;
import com.yonyou.dms.DTO.gacfca.ProductModelPriceDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.CLDMS003;

/**
 * 
* @ClassName: CLDCS003CloudImpl 
* @Description: 车型价格信息下发
* @author zhengzengliang 
* @date 2017年4月5日 下午2:22:33 
*
 */
@Service
public class CLDCS003CloudImpl extends BaseCloudImpl implements CLDCS003Cloud{

	private static final Logger logger = LoggerFactory.getLogger(CLDCS003CloudImpl.class);
	
	@Autowired
	SaleMaterialPriceDao saleMaterialPriceDao ;
	
	@Autowired
	CLDMS003 cldms003 ;
	
	@Autowired 
	SaleMaterialGroupDao dao;
	
	@Autowired
	DeCommonDao deCommonDao;
	
	@Override
	public String execute(List<String> dealerList, String[] groupIds) throws ServiceBizException {
		logger.info("================车型价格信息下发执行开始（CLDCS003）====================");
		if(null==dealerList || dealerList.size()==0){
			dealerList = saleMaterialPriceDao.getAllDcsCode(0);
		}
		for (int i = 0; i < dealerList.size(); i++) {
			String dealerCode = dealerList.get(i);
			sendData(getDataList(groupIds,dealerCode),dealerCode);
		}
		logger.info("================车型价格信息下发执行结束（CLDCS003）====================");
		return null;
	}
	
	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	public void sendData(LinkedList<ProductModelPriceDTO> list , String dealerCode){
		try {
			// 店端经销商代码取得
			Map dmsDealer = deCommonDao.getDmsDealerCode(dealerCode);
			// 店端代码存在且业务范围内物料存在的场合，下发物料组
			if ((!StringUtils.isNullOrEmpty(dmsDealer.get("DMS_CODE"))) && list != null && list.size()>0) {
				int flag = cldms003.getCLDMS003(list,dmsDealer.get("DMS_CODE").toString());
				if(flag==1){
					logger.info("================车辆价格信息下发成功（CLDCS003）====================");
				}else{
					logger.info("================车辆价格信息下发失败（CLDCS003）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================车辆价格信息下发经销商无业务范围（CLDCS003）====================");
			}
		} catch (Exception e) {
			logger.info("================车辆价格信息下发异常（CLDCS003）====================");
		}
	}

	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public LinkedList<ProductModelPriceDTO> getDataList(String[] groupId, String dealerCode) throws ServiceBizException {
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
		LinkedList<ProductModelPriceDTO> vos = null;
		if(!"".equals(CommonUtils.checkNull(dealerCode))){
			//剔除选中的车系中不属于经销商范围的车系
			groupId = dao.getDealerByGroupId(groupId,dealerCode);
			if(null != groupId && groupId.length!=0){
				vos = saleMaterialPriceDao.selectMaterialPriceInfo(groupId);
			}
		}else{
			if(null != groupId && groupId.length!=0){
				vos = saleMaterialPriceDao.selectMaterialPriceInfo(groupId);
			}else{
				vos = saleMaterialPriceDao.queryMaterialPriceInfo();
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================经销商:"+dealerCode+"下发数据大小："+ size +"====================");
		return vos;
	}
	

}
