package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsUCultivateDTO;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsUCultivatePO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SOTDCS012CloudImpl implements SOTDCS012Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsUCultivateDTO> dto) throws Exception {
		String msg = "1";
		logger.info("====更新客户信息（客户培育）(DMS更新)接收开始===="); 
		for (TiDmsUCultivateDTO entry : dto) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("更新客户信息（客户培育）(DMS更新)数据接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====更新客户信息（客户培育）(DMS更新)接收结束===="); 
		return msg;
	}
	
	public void insertData(TiDmsUCultivateDTO vo) throws Exception {
		/*
		 * Map<String, Object> map =
		 * deCommonDao.getSaDcsDealerCode(vo.getEntityCode()); String dealerCode
		 * = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
		 */
		TiDmsUCultivatePO insertPO = new TiDmsUCultivatePO();
		insertPO.setString("UNIQUENESS_ID", vo.getUniquenessID());// DMS客户唯一ID
		insertPO.setDate("COMM_DATE", vo.getCommData());// 沟通日期
		insertPO.setString("COMM_TYPE", vo.getCommType());// 沟通方式
		insertPO.setString("COMM_CONTENT", vo.getCommContent());// 沟通内容
		insertPO.setString("FOLLOW_OPP_LEVEL_ID", vo.getFollowOppLevelID());// 跟进后客户级别
		insertPO.setDate("NEXT_COMM_DATE", vo.getNextCommDate());// 下次沟通日期
		insertPO.setString("NEXT_COMM_CONTENT", vo.getNextCommContent());// 下次沟通内容
		insertPO.setDate("DEALER_CODE", vo.getDealerCode());// 经销商代码
		insertPO.setString("DEALER_USER_ID", vo.getDealerUserID());// 销售人员ID
		insertPO.setTimestamp("UPDATE_DATE", vo.getUpdateDate());// 创建日期
		insertPO.setString("IS_SEND", "0");// 同步标志
		insertPO.setLong("UPDATE_BY", DEConstant.DE_UPDATE_BY);// 创建者
		// 插入数据
		insertPO.insert();

	}

}
