package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.ActivityResultDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.ActivityResultDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtWrActivityVehicleCompleteDcsPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class HMCISE05CloudImpl extends BaseCloudImpl implements HMCISE05Cloud {
	private static final Logger logger = LoggerFactory.getLogger(HMCISE05CloudImpl.class);
	@Autowired
	ActivityResultDao dao ;
	
	@Override
	public String receiveData(List<ActivityResultDTO> dtos) throws Exception {
		String msg = "1";
		
		try {
			logger.info("*************** HMCISE05活动车辆完工上报接收开始 *******************");
			for (ActivityResultDTO vo : dtos) {
				insertData(vo);
			}
			logger.info("*************** HMCISE05活动车辆完工上报完成 ********************");
			
		} catch (Exception e) {
			logger.error("*************** HMCISE05活动车辆完工上报异常 *****************", e);
			msg = "0";
			throw new ServiceBizException(e);
		} 
		return msg;
	}
	/**
	 * 保存活动车辆完工上报信息
	 * @param vo
	 * @throws Exception
	 */
	public synchronized void insertData(ActivityResultDTO vo) throws Exception {
		//根据下端的EntityCode查询上端对应的DealerCode
		Map<String, Object> map = dao.getSeDcsDealerCode(vo.getEntityCode());
		Long dealerId = Long.parseLong(String.valueOf(map.get("DEALER_ID")));//售后接口用户
		
		TtWrActivityVehicleCompleteDcsPO po = null;
		
		//根据VIN 活动代码 特约店代码 查询活动车辆完工上报信息
		List<Map> vinList = dao.findActVehComp(vo.getVin(), vo.getActivityCode());
		if (null == vinList || vinList.size() <= 0) {//不存在活动车辆完工上报信息
			po = new TtWrActivityVehicleCompleteDcsPO();
			po.setString("ACTIVITY_CODE", vo.getActivityCode());
			
			if (map!=null && map.size()>0) {
				String dealerCode = map.get("DEALER_CODE").toString();
				po.setString("DEALER_CODE",dealerCode);
			}
			po.setString("DEALER_NAME", vo.getRealEntityName());
			po.setString("VIN", vo.getVin());
			po.setDate("EXECUTE_DATE", vo.getCampaignDate());
			po.setInteger("IS_CLAIM", OemDictCodeConstants.IF_TYPE_NO);// 未索赔
			po.setInteger("DOWN_SATUS",0);// 未下发状态
			po.setInteger("IS_DEL",0);
			po.setBigDecimal("CREATE_BY",DEConstant.DE_CREATE_BY);
			po.setDate("CREATE_DATE",vo.getDownTimestamp());
			po.saveIt();
			
		} else {//存在活动车辆完工上报信息
			Long id=Long.parseLong(CommonUtils.checkNull(vinList.get(0).get("ID"), "0L"));
			po=TtWrActivityVehicleCompleteDcsPO.findById(id);
			po.setInteger("IS_CLAIM", OemDictCodeConstants.IF_TYPE_NO);// 未索赔
			po.setInteger("DOWN_SATUS",0);// 未下发状态
			po.setInteger("IS_DEL",0);
			po.setBigDecimal("UPDATE_BY",DEConstant.DE_CREATE_BY);
			po.setDate("UPDATE_DATE",vo.getDownTimestamp());
			po.saveIt();// 更新活动车辆完工上报信息
		}
	}
	
}
