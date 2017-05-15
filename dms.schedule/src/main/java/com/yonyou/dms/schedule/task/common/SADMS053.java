package com.yonyou.dms.schedule.task.common;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.schedule.domains.DTO.BigCustomerVisitItemDto;
import com.yonyou.dms.schedule.service.impl.SADMS053Service;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 定时上报克莱斯勒明检和神秘信息
 * 
 * @author wangliang
 * @date 2017年2月20日
 */
@TxnConn
@Component
public class SADMS053 extends TenantSingletonTask {
	private static final Logger logger = LoggerFactory.getLogger(SADMS053.class);
	@Autowired
	SADMS053Service testService;

	@Override
	public void execute() throws Exception {
		LinkedList<BigCustomerVisitItemDto> dtoList = testService.getSADMS053();
		/*int k = 0;
		if (dtoList != null && "".equals(dtoList)) {
			for (int i = 0; i < dtoList.size(); i++) {
				LinkedList<BigCustomerVisitIntentDto> resultDetailList = dtoList.get(i)
						.getBigCustomerVisitIntentDtoList();
				if (resultDetailList != null && resultDetailList.size() > 0) {

					for (int j = 0; j < resultDetailList.size(); j++) {
						k = resultDetailList.size();
						k++;
					}
				}
			}

			for (int i = 0; i < dtoList.size(); i++) {
				LinkedList<BigCustomerVisitIntentDto> resultDetailList = dtoList.get(i)
						.getBigCustomerVisitIntentDtoList();
				if (resultDetailList != null && resultDetailList.size() > 0) {

					for (int j = 0; j < resultDetailList.size(); j++) {
						System.out.println("======================resultDetailList");
						BigCustomerVisitIntentDto idto = resultDetailList.get(j);
						System.out.println("条数" + k);
						System.out.println(idto.getDealerCode() + "\n" + idto.getIntentBrand() + "\n"
								+ idto.getIntentSeries() + "\n" + idto.getIntentProduct());
						System.out.println("======================resultDetailList");
					}
				}
			}
		}*/
		System.out.println("SADMS053====================开始");
		if (dtoList != null && dtoList.size() > 0) {
			for (int i = 0; i < dtoList.size(); i++) {
				BigCustomerVisitItemDto dto = dtoList.get(i);
				System.out.println("条数" + dtoList.size());
				System.out.println(dto.getDealerCode() + "\n" + dto.getCustomerContactsName() + "\n"
						+ dto.getPolicyType() + "\n" + dto.getVisitDate());
			}
		}
		System.out.println("SADMS053====================结束");
	}

}
