package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS003ForeReturnCloud;
import com.yonyou.dms.DTO.gacfca.SADMS003ForeDTO;
import com.yonyou.dms.DTO.gacfca.SADMS003ForeReturnDTO;
import com.yonyou.dms.common.Util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 业务描述：LMS建档校验 DCS转发过来下端判断下是否撞单给出反馈
 * @author Benzc
 * @date 2017年5月8日
 */
@Service
public class SADMS003ForeCoudImpl implements SADMS003ForeCoud{
	
	@Autowired SADCS003ForeReturnCloud SADCS003ForeReturnCloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SADMS003ForeCoudImpl.class);

	@SuppressWarnings("rawtypes")
	@Override
	public int getSADMS003Fore(List<SADMS003ForeDTO> voList,String dealerCode) throws ServiceBizException {
		try {
			logger.info("========================SADMS003Fore开始============================");
			System.err.println(dealerCode);
			List<SADMS003ForeReturnDTO> resultList = new ArrayList<SADMS003ForeReturnDTO>();
			//判断是否为空,循环操作，根据业务相应的修改数据
			if(voList != null && voList.size() > 0){
				for (int i = 0; i < voList.size(); i++){
					SADMS003ForeDTO dto = voList.get(i);
					if(dealerCode == null){
						logger.info("==================主键dealerCode丢失=====================");
						return 0;
					}
					SADMS003ForeReturnDTO retrunvo =new SADMS003ForeReturnDTO();
					retrunvo.setConflictedType("0");
					retrunvo.setNid(dto.getNid());
					retrunvo.setDealerCode(dealerCode);
					
					if(Utility.testString(dto.getPhone())){//没有联系方式的无效，有联系方式的用联系方式判断是否撞单 撞了返回msg
						List<Map> listcheck = Base.findAll("select * from Tm_Potential_Customer where ( Contactor_Mobile=" +dto.getPhone()+ " or Contactor_Phone= " +dto.getPhone()+ ")"				
								+" and DEALER_CODE='"+dealerCode+"' AND D_KEY="+CommonConstants.D_KEY +"  ");
						if(listcheck!=null && listcheck.size()>0){
							Map customerpo = listcheck.get(0);
							retrunvo.setConflictedType("1");
							retrunvo.setDmsCustomerNo(customerpo.get("CUSTOMER_NO").toString());
							retrunvo.setOpportunityLevelID((Integer)customerpo.get("INTENT_LEVEL"));
							if (customerpo.get("SOLD_BY")!=null && customerpo.get("SOLD_BY") != "0"){
								List<Map> userList = Base.findAll("SELECT * FROM TM_USER WHERE DEALER_CODE="+ dealerCode + " AND USER_ID=" +customerpo.get("SOLD_BY"));
								Map userpo = userList.get(0);
								if(userpo != null){
									retrunvo.setSalesConsultant(userpo.get("USER_NAME").toString());
								}else{
									retrunvo.setSalesConsultant("未知");
								}
							} else {
								retrunvo.setSalesConsultant("未知");
							}
						}
					}
					resultList.add(retrunvo);// 每一个都要给反馈 
				}
			}
			//调用厂端SADCS003ForeReturnCloud方法（DCC建档客户信息反馈接收）
			SADCS003ForeReturnCloud.receiveDate(resultList);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("====================SADMS003Fore异常==========================");
			return 0;
		}
		return 1;
	}

}
