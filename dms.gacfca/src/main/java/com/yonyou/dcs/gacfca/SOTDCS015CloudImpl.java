package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.LinkedList;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerStatusDto;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsUCustomerStatusPO;

/**
 * DMS->DCS
 * 更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新
 * return msg 0 error 1 success
 * @author luoyang
 *
 */
@Service
public class SOTDCS015CloudImpl extends BaseCloudImpl implements SOTDCS015Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDCS015CloudImpl.class);

	@Override
	public String handleExecutor(LinkedList<TiDmsUCustomerStatusDto> dtoList) throws Exception {
		String msg = "1";
		logger.info("====更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新接收开始====");
		beginDbService();
		try {
			for(TiDmsUCustomerStatusDto dto:dtoList){
				try {
					boolean flag = insertData(dto);
					if(!flag){
						logger.error("更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新接收失败");
						msg = "0";
					}
				} catch (Exception e) {
					e.printStackTrace();
					logger.error("更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新接收异常", e);
					msg = "0";
				}
			}
			logger.info("====更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新接收结束===="); 
			dbService.endTxn(true);
		} catch (Exception e) {
			dbService.endTxn(false);
		}finally {
			Base.detach();
			dbService.clean();
		}
		return msg;
		
	}
	
	public boolean insertData(TiDmsUCustomerStatusDto dto){
		Date currentTime = new Date();
//		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TiDmsUCustomerStatusPO po = new TiDmsUCustomerStatusPO();
		po.setString("DEALER_USER_ID", dto.getDealerUserID());// 销售人员ID
		po.setString("DEALER_CODE", dto.getDealerCode());// 经销商代码
		if(dto.getFCAID() != null){
			po.setLong("FCA_ID", dto.getFCAID());
		}
		po.setString("UNIQUENESS_ID", dto.getUniquenessID());
		po.setString("OPP_LEVEL_ID", dto.getOppLevelID());// 车牌号
		po.setString("GIVE_UP_TYPE", dto.getGiveUpType());// 状态
		po.setString("COMPARE_CAR", dto.getCompareCar());// DMS 车型ID
		po.setString("GIVE_UP_REASON", dto.getGiveUpReason());// 休眠原因
		po.setTimestamp("GIVE_UP_DATE", dto.getGiveUpDate());// DMS 车型名称
		po.setTimestamp("BUY_CAR_DATE", dto.getBuyCarDate());
		po.setTimestamp("ORDER_DATE", dto.getOrderDate());
		po.setString("IS_SEND", "0");// 同步标志
		po.setTimestamp("CREATE_DATE", currentTime);
		po.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);
		boolean flag = po.saveIt();
		return flag;
	}

}
