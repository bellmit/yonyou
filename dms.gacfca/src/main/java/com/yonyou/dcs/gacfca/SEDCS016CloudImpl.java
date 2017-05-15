package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS016Dao;
import com.yonyou.dms.DTO.gacfca.SEDCS016DTO;
@Service
public class SEDCS016CloudImpl extends BaseCloudImpl implements SEDCS016Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS016CloudImpl.class);
	
	@Autowired
	SEDCS016Dao dao;
	
	@Override
	public String doSend(){
		String re = "1";
		try {
			logger.info("====SEDCS016Cloud  行管经销商下发开始====");
			//下发数据查询
			List<SEDCS016DTO> list = dao.queryAllInfo();
			
			if (null == list || list.size() == 0) {
				logger.info("====SEDCS016Cloud 行管经销商下发结束====,无数据");
			}else{
				for(int i =0;i<list.size();i++){
					List<SEDCS016DTO> dataList = new ArrayList<SEDCS016DTO>();
					
					//给指定经销商发送该经销商数据
					SEDCS016DTO dto = list.get(i);
					
					dataList.add(dto);
					//下发操作
//					int flag = (dataList,list.get(i).getEntityCode());
//					if(flag==1){
//						logger.info("====================SEDCS016Cloud  行管经销商下发成功========================");
//					}else{
//						logger.info("================SEDCS016Cloud  行管经销商下发失败====================");
//					}
				}
				//更新下发状态,下发时间
				dao.updateSend();
				
				logger.info("====SEDCS016Cloud 行管经销商下发结束====,下发了(" + list.size() + ")条数据");
			}
		} catch (Exception e) {
			re = "2";
			logger.info("================SEDCS016Cloud  行管经销商下发异常====================");
		}
		return re;
	}
}
