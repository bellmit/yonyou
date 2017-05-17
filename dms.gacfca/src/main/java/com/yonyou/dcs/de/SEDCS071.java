package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.DiscountCouponVO;
import com.yonyou.dcs.dao.SEDCS071Dao;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.DiscountCouponDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:获取可用卡券信息同步(DMS->DCS->DMS)
 * @author xuqinqin 
 */
@Service
public class SEDCS071  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS071.class);
	
	@Autowired
	SEDCS071Dao dao ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取可用卡券信息同步******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			for (Entry<String, Serializable> entry : bodys.entrySet()) {
				DiscountCouponVO dto = new DiscountCouponVO();
				dto = (DiscountCouponVO) entry.getValue();
				
				//查询DCS端经销商是否存在
				Map<String, Object> dcsDealer = null;
				String dealerCode = "";
				if(Utility.testIsNotNull(dto.getEntityCode())){
					 dcsDealer = dao.getSaDcsDealerCode(dto.getEntityCode());
					 dealerCode = dcsDealer.get("DEALER_CODE").toString();
				}
				String vin = dto.getVin();
				
				logger.info("SEDCS071同步接口接收到VIN：" + vin+"和经销商："+dealerCode);
				
				//查询卡券使用状态为使用中跟未使用的个数
				List<Map<String, Object>> listCount = dao.countVehicle(vin);
				int countNum = listCount.size();
				
				//当countNum大于1时，下发使用中的卡券，否则下发未使用的卡券
				LinkedList<DiscountCouponDTO> dataList = dao.queryVehicleDto(vin,countNum);
				if (dataList == null || dataList.size() <= 0) {
					logger.info("没有卡券信息");
					return wrapperMsg(dataList);
				}
				
				logger.info("存在卡券信息，下发");
				DEMessage rmsg = wrapperMsg(dataList);
				logger.info("====获取可用卡券信息结束====");
				return rmsg;
			}		
		}  catch(Throwable t) {
			logger.info("*************************** 获取可用卡券信息同步出错******************************");
			t.printStackTrace();
		} 
		logger.info("*************************** 成功获取获取可用卡券信息同步******************************");
		return null;
	}
	/**
	 * @param dtos
	 * @param msg
	 * @return
	 */
	private DEMessage wrapperMsg(LinkedList<DiscountCouponDTO> dataList) {
		if (dataList != null && dataList.size() > 0) {
			List<DiscountCouponVO> vos = new ArrayList<>();
			setVos(vos,dataList);
			HashMap<String, Serializable> body = DEUtil.assembleBody(vos);
			if (body != null && body.size() > 0) {
				DEUtil de = new DEUtil();
				DEMessage rmsg = null;
				try {
					logger.info("开始下发SEDMS071");
					rmsg=assembleDEMessage("SEDMS071", body);
					logger.info("结束下发SEDMS071");
				} catch (Exception e) {
					logger.info("获取可用卡券信息失败", e);
				}
				return rmsg;
			}
		}
		return null;
	}
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<DiscountCouponVO> vos, LinkedList<DiscountCouponDTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			DiscountCouponDTO dto = dataList.get(i);
			DiscountCouponVO vo = new DiscountCouponVO();
			vo.setCardNo(dto.getCardNo());
			vo.setCardName(dto.getCardName());
			vo.setCardType(dto.getCardType());
			vo.setSubType(dto.getSubType());
			vo.setCardValue(dto.getCardValue());
			vo.setIssueDate(dto.getIssueDate());
			vo.setStartDate(dto.getStartDate());
			vo.setEndDate(dto.getEndDate());
			vo.setUseDealer(dto.getUseDealer());
			vo.setUseProject(dto.getUseProject());
			vo.setUseStatus(dto.getUseStatus());
			vo.setSameOverlapCount(dto.getSameOverlapCount());
			vo.setIsDifferentOverlap(dto.getIsDifferentOverlap());
			vo.setEntityCode(dto.getEntityCode());
			vo.setVin(dto.getVin());
			vos.add(vo);
		}
	}
}
