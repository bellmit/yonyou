package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SalesLeadsCustomerCreateDao;
import com.yonyou.dms.DTO.gacfca.SADMS003ForeDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SADMS003ForeCoud;

/**
 * 
* @ClassName: SADCS003ForeCloudImpl 
* @Description: DCC建档客户信息下发
* @author zhengzengliang 
* @date 2017年4月12日 下午5:29:06 
*
 */
@Service
public class SADCS003ForeCloudImpl extends BaseCloudImpl implements SADCS003ForeCloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS003ForeCloudImpl.class);
	
	@Autowired
	SalesLeadsCustomerCreateDao dao;
	
	@Autowired SADMS003ForeCoud SADMS003Fore;
	
	@Override
	public String execute() throws ServiceBizException {
		logger.info("====DCC建档客户信息下发SADCS003Fore开始====");
		List<SADMS003ForeDTO> dccVos = getDataList();
		if(null!=dccVos && dccVos.size()>0){
			sendData(dccVos);
			Integer size = dccVos==null?0:dccVos.size();
			logger.info("====DCC建档客户信息下发SADCS003Fore结束====,下发了(" + size + ")条数据");
		}else{
			//无下发数据
			logger.info("====DCC建档客户信息下发SADCS003Fore结束====,无数据");
		}
		return null;
	}

	/**
	 * 获取数据方法
	 * 供新老接口获取数据方法
	 */
	@Override
	public List<SADMS003ForeDTO> getDataList() throws ServiceBizException {
		List<SADMS003ForeDTO> dccVos = dao.querySalesLeadsCustomerCreatePOInfo();
		if (null == dccVos || dccVos.size() == 0) {
			logger.info("====DCC建档客户信息下发SADCS003Fore结束====,无数据");
			return null;
		}
		return dccVos;
	}
	
	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	public void sendData(List<SADMS003ForeDTO> list ){
		try {
			// 循环下发数据
			for (int i = 0; i < list.size(); i++) {
				List<SADMS003ForeDTO> voList = new ArrayList<>();
				String dealerCode = list.get(i).getDealerCode();
				// 店端经销商代码有效的场合
				if(!"".equals(CommonUtils.checkNull(dealerCode))){
					voList.add(list.get(i));
					int flag = 0;
					//调用下端接口
					flag = SADMS003Fore.getSADMS003Fore(voList, dealerCode);
					if(flag==1){
						logger.info("================数据下发成功:"+list.get(i).getNid()+"====================");
						//根据NID将 TI_SALES_LEADS_CUSTOMER;中 IS_SCAN=0或者null的字段更新为1
						dao.finishSalesLeadsCustomerSCANStatus(list.get(i).getNid());
					}else{
						logger.info("================数据下发失败:"+list.get(i).getNid()+"====================");
					}
				}else{
					logger.info("================数据下发失败:"+list.get(i).getNid()+"的店端经销商代码无效======");
				}
			}
		} catch (Exception e) {
			logger.info("================数据下发异常====================");
			e.printStackTrace();
		}
	}

}
