package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS069Dao;
import com.yonyou.dms.DTO.gacfca.SEDMS069Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 呆滞件下架or变更数量上报接口
 * @author luoyang
 * @return 1 success 0 fail
 *
 */
@Service
public class SEDCS069CloudImpl extends BaseCloudImpl implements SEDCS069Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS069CloudImpl.class);
	
	@Autowired
	SEDCS069Dao dao;

	@Override
	public String handleExecutor(List<SEDMS069Dto> list) throws Exception {
		String msg = "1";
		logger.info("====呆滞件下架or变更数量上报接口(SEDCS069)接收开始===="); 
		for (SEDMS069Dto dto : list) {
			try {
				insertData(dto);
			} catch (Exception e) {
				logger.error("呆滞件下架or变更数量上报接口(SEDCS069)接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====呆滞件下架or变更数量上报接口(SEDCS069)接收成功====");
		logger.info("====呆滞件下架or变更数量上报接口(SEDCS069)接收结束===="); 
		return msg;
	}

	private void insertData(SEDMS069Dto dto) {
		Map<String, Object> map = dao.getSaDcsDealerCode(dto.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE"));
		//根据经销商+配件代码+仓库查询总的可申请数
		List<Map> countList = dao.queryCount(dealerCode,dto.getPartNo(),dto.getStorageCode());
		
		//根据经销商+配件代码+仓库查询发布信息
		List<Map> poList = dao.queryPo(dealerCode,dto.getPartNo(),dto.getStorageCode());
		
		int applySum = 0;
		if(countList != null && !countList.isEmpty()){		
			applySum = countList.get(0).get("APPLY_NUMBER") !=null?Integer.parseInt(countList.get(0).get("APPLY_NUMBER").toString()):0;//总可申请数
		}
		
		float dmsStock = dto.getStockQuantity();
		int restDms = (int)dmsStock;//DMS上报剩余库存数
		int rest = applySum - restDms;//可减数
		int dcsRest = 0;//剩余可申请书
		if(restDms<=0){
			dao.updateStatueAll(dealerCode,dto.getPartNo(),dto.getStorageCode());
		}else{
			if(poList.size()>0){
				for(int i=0;i<poList.size();i++){
					int applyNum = poList.get(i).get("APPLY_NUMBER") !=null?Integer.parseInt(poList.get(i).get("APPLY_NUMBER").toString()):0;//可申请数
					if(applyNum <= rest){
						//当可申请数小于可减数时，可申请数为0，更新该发布数据可申请数并下架，可减数为可减数-可申请数
						dcsRest = rest-applyNum;
						dao.updateStatue(Long.parseLong(poList.get(i).get("ITEM_ID").toString()));
						rest = dcsRest;
					}else{
						if(rest <= 0){
							break;
						}else{
							dcsRest = applyNum-rest;
							dao.updateNum(dcsRest,Long.parseLong(poList.get(i).get("ITEM_ID").toString()));
							rest = 0;
						}
					}
				}
			}
		}
	}

}
