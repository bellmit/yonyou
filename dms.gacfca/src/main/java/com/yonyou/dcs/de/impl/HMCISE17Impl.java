package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.ClaimRejectedVO;
import com.yonyou.dcs.de.HMCISE17;
import com.yonyou.dcs.gacfca.HMCISE17Cloud;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.ClaimRejectedDTO;
import com.yonyou.dms.common.domains.DTO.basedata.TtWrClaimDcsDTO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class HMCISE17Impl extends BaseImpl implements HMCISE17 {
	
	private static final Logger logger = LoggerFactory.getLogger(HMCISE17Impl.class);
	
	@Autowired
	HMCISE17Cloud cloud;
	
	@Override
	public String sendAllData(TtWrClaimDcsDTO dto){
		try {
			LinkedList<ClaimRejectedDTO> dataListlist=cloud.getDataList( dto);
			List<String> entityCodeList=cloud.getEntityCodeList(dto);
			for (int i = 0; i < entityCodeList.size(); i++) {
				String entityCode = entityCodeList.get(i);
				send(dataListlist,entityCode);
			}
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}
	/**
	 * DE消息发送
	 * @param dataList
	 * @param dealerCode
	 * @throws Exception 
	 */
	private String send(LinkedList<ClaimRejectedDTO> dataList, String entityCode) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<ClaimRejectedVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				if(!"".equals(entityCode)){
					sendAMsg("HMCISE17", entityCode, body);
					logger.info("HMCISE17售后服务索赔单删除发送成功=====entityCode"+entityCode+"===size："+dataList.size());
				}else{
					logger.info("HMCISE17售后服务索赔单删除下发失败=====entityCode"+entityCode+"===size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("HMCISE17发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} finally {
		}
		return null;
	}
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<ClaimRejectedVO> vos, LinkedList<ClaimRejectedDTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			ClaimRejectedDTO dto = dataList.get(i);
			ClaimRejectedVO vo = new ClaimRejectedVO();
			vo.setRoNo(dto.getRoNo());
			vo.setRoStatus(dto.getRoStatus());
			vo.setIsDel(dto.getIsDel());
			vo.setDownTimestamp(new Date(System.currentTimeMillis()));
			vo.setEntityCode(dto.getDealerCode());
			vos.add(vo);
		}
	}
}
