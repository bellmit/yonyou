package com.yonyou.dms.schedule.task.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.common.domains.DTO.basedata.SADCS050Dto;
import com.yonyou.dms.common.domains.PO.basedata.TmAscBasicinfoPO;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.schedule.domains.PO.TtCustomerIntentDetailPO;
import com.yonyou.dms.schedule.service.impl.SADMS050Service;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 二手车置换率月报周报
 * @author wangliang
 * @date 2017年3月15日
 */
@TxnConn
@Component
public class SADMS050 extends TenantSingletonTask{
	private static final Logger logger = LoggerFactory.getLogger(SADMS050.class);
	@Autowired
	SADMS050Service SADMS050Service;
	
	@Override
	public void execute() throws Exception {
	    logger.info("===========================开始SADMS050");
	    StringBuffer sb = new StringBuffer("SELECT * from tm_asc_basicinfo where 1=1");
       
        logger.info("===========================开始SADMS050");
        List<Map> result = Base.findAll(sb.toString());
        /*List<TmAscBasicinfoPO> inList = TmAscBasicinfoPO.findBySQL(
                                                         "select *  from  TM_ASC_BASICINFO where ENTITY_CODE!= ?",
                                                         new Object[] { "" });*/
        logger.info("===========================开始SADMS050");
        if(result!=null&&result.size()>0){
            logger.info("===========================开始SADMS050");
            for(int j=0;j<result.size();j++){
                LinkedList<SADCS050Dto> dtoList = SADMS050Service.getSADMS050(result.get(j).get("ENTITY_CODE").toString());
                System.out.println("SADMS050=================开始");
                if(dtoList != null && dtoList.size() > 0){
                    for(int i=0;i<dtoList.size();i++) {
                        SADCS050Dto dto = dtoList.get(i);
                        System.out.println("条数" + dtoList.size());
                        System.out.println(dto.getDealerCode()+"/n"+dto.getSeriesCode()+"/n"+dto.getDealRatio()+"/n"+dto.getIntentionNum());
                    }
                }
            }
        }
		System.out.println("SADMS050=================结束");
	}
}
