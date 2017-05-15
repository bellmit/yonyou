package com.yonyou.dms.schedule.task.common;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yonyou.dms.common.domains.DTO.basedata.SA013Dto;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.schedule.service.impl.SADMS013Service;
import com.yonyou.f4.mvc.annotation.TxnConn;
import com.yonyou.f4.schedule.task.TenantSingletonTask;

/**
 * 
 * 
 * @author wangliang
 * @date 2017年2月20日
 */
@TxnConn
@Component
public class SADMS013 extends TenantSingletonTask {
	private static final Logger logger = LoggerFactory.getLogger(SADMS013.class);
	
	@Autowired
	SADMS013Service SADMS013Service;
	@Override
	public void execute() throws Exception {
	    try {
	        StringBuffer sb = new StringBuffer("SELECT * from tm_asc_basicinfo");
	        List<Object> queryList = new ArrayList<Object>();
	        List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
	        if(result!=null&&result.size()>0){
	            for(int j=0;j<result.size();j++){
	                LinkedList<SA013Dto> list = SADMS013Service.getSADMS013(result.get(j).get("ENTITY_CODE").toString());
	                System.out.println("SADMS013================开始");
	                //LinkedList<SA013Dto> list = testService.getSADMS013();
	               if(list != null && list.size() > 0 ) {
	                    for (int i = 0;i<list.size();i++) {
	                        SA013Dto dto = list.get(i);
	                        System.out.println("条数" + list.size());
	                        System.out.println(dto.getDealerCode()+"\n"+ dto.getWalkIn()+"\n"+dto.getNoOfSc()+"\n"+dto.getCurrentDate()+"\n"+dto.getCallIn());
	                    }
	                }
	                System.out.println("SADMS013================结束");
	            }
	           
	        }
	   
            
        } catch (Exception e) {
            // TODO: handle exception
        }
	
	}

}
