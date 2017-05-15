package com.yonyou.dms.schedule.task.common;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.schedule.domains.DTO.SADMS054Dto;
import com.yonyou.dms.schedule.service.impl.SADMS054Service;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 定时上报克莱斯勒明检和神秘信息
 * @author wangliang
 * @date 2017年2月20日
 */
@TxnConn
@Component
public class SADMS054 extends TenantSingletonTask{
	 private static final Logger logger = LoggerFactory.getLogger(SADMS054.class);
	 @Autowired
	 SADMS054Service testService;
	 @Override
	 public void execute() throws Exception {
		LinkedList<SADMS054Dto> dtoList = testService.getSADMS054();
		System.out.println("SADMS054====================开始");
		if(dtoList != null && dtoList.size() > 0) {
			for(int i= 0;i<dtoList.size();i++) {
				SADMS054Dto dto = dtoList.get(i);
				System.out.println("条数"+dtoList.size());
				System.out.println(dto.getDealerCode()+"/n"+dto.getDealerName()+"/n"+dto.getExecAuthor()+"/n"+dto.getPhone()+"/n"+dto.getIsInputDms()+"/n"+dto.getPhone());
				
			}
		}
		System.out.println("SADMS054====================结束");
	}

}
