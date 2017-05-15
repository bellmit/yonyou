package com.yonyou.dms.schedule.task.common;

import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.common.domains.DTO.basedata.SADMS063Dto;
import com.yonyou.dms.schedule.service.impl.SADMS063Service;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;
/**
 * 定时上报留存订单等信息
 * 
 * */
@TxnConn
@Component
public class SADMS063 extends TenantSingletonTask{
    private static final Logger logger = LoggerFactory.getLogger(SADMS063.class);
    @Autowired
    SADMS063Service planTestService;
    
    @Override
    public void execute() throws Exception {
        // 获取dms所需上报的留存订单的数据信息
        LinkedList<SADMS063Dto> dtoList = planTestService.getSADMS063();
        if(dtoList!=null && dtoList.size()>0){
            for(int i=0;i<dtoList.size();i++){
                SADMS063Dto dto=dtoList.get(i);
                System.out.println("SADMS063====================================");
                System.out.println("条数"+dtoList.size());
                System.out.println(dto.getDealerCode()+"/n"+ dto.getSeriesCode()+"/n"+dto.getSalesLcreplace()
                +"/n"+dto.getSubmitTime());
                        
            }
        }
    }
 


}
