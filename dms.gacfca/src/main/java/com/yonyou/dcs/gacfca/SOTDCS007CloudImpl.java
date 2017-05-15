package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsNCultivateDto;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsNCultivatePO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SOTDCS005CloudImpl
 * Description: 创建客户信息（客户培育）(DMS新增)接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 下午5:35:20
 * result msg 1：成功 0：失败
 */
@Service
public class SOTDCS007CloudImpl extends BaseCloudImpl implements SOTDCS007Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS056CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsNCultivateDto> dtoList) throws Exception {
		String msg = "1";
		logger.info("====创建客户信息（客户培育）(DMS新增)接收开始===="); 
		for (TiDmsNCultivateDto entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("创建客户信息（客户培育）(DMS新增)数据接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====创建客户信息（客户培育）(DMS新增)接收结束===="); 
		return msg;
	}
	
	public void insertData(TiDmsNCultivateDto vo) throws Exception {
		TiDmsNCultivatePO insertPO = new TiDmsNCultivatePO();
		insertPO.setString("UNIQUENESS_ID", vo.getUniquenessId());// DMS客户唯一ID
		insertPO.setLong("FCA_ID", vo.getFCAID());// app客户唯一ID
		insertPO.setTimestamp("COMM_DATE", vo.getCommDate());// 沟通日期
		insertPO.setString("COMM_TYPE", vo.getCommType());// 沟通方式
		insertPO.setString("COMM_CONTENT", vo.getCommContent());// 沟通内容
		insertPO.setString("FOLLOW_OPP_LEVEL_ID", vo.getFollowOppLevelId());// 跟进后客户级别
		insertPO.setDate("NEXT_COMM_DATE", vo.getNextCommDate());// 下次沟通日期
		insertPO.setString("NEXT_COMM_CONTENT", vo.getNextCommContent());// 下次沟通内容
		insertPO.setString("DEALER_CODE", vo.getEntityCode());// 经销商代码
		insertPO.setString("DEALER_USER_ID", vo.getDealerUserId());// 销售人员ID
		insertPO.setTimestamp("CREATE_DATE", vo.getCreateDate());// 创建日期
		insertPO.setString("IS_SEND", "0");// 同步标志
		insertPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);// 创建者
		// 插入数据
		insertPO.insert();
	}

}
