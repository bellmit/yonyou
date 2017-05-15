package com.yonyou.dcs.gacfca;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS017Dao;
import com.yonyou.dms.DTO.gacfca.SEDCS017DTO;
@Service
public class SEDCS017CloudImpl extends BaseCloudImpl implements SEDCS017Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS017CloudImpl.class);
	
	@Autowired
	SEDCS017Dao dao;
	
	/**
	 * 全量下发
	 */
	@Override
	public String sendAllInfo(int groupType){
		String re = "1";
		try {
			logger.info("====SEDCS017        维修工时参数全量下发开始====");
			List<Map> count = dao.countDo(groupType);
			int f = count.size()%200;
			int j = count.size()/200;
			logger.info("====SEDCS017      维修工时参数全量下发次数J===="+j);
			if (null == count || count.size() == 0) {
				logger.info("==== SEDCS017      维修工时参数全量下发结束====,无数据");
			}else{
				if(f == 0){
					for(int i=0;i<j;i++){
						List<SEDCS017DTO> datalist = dao.queryAllInfo(i+1,groupType);
						send(datalist);
					}
				}else{
					for(int d=0;d<j+1;d++){
						List<SEDCS017DTO> datalist = dao.queryAllInfo(d+1,groupType);
						send(datalist);
					}
				}
				logger.info("====维修工时参数全量下发SEDCS017完成  更新状态====");
				dao.updateSend();
			}
		}catch (Exception e) {
			re="2";
			logger.error(e.getMessage(), e);
		}
		return re;
	}
	/**
	 * 数据发送
	 */
	public void send(List<SEDCS017DTO> datalist){
		//下发的经销商 全网下发
		List<String> dealerList=dao.getAllDmsCode(1);
		if(null!=datalist && datalist.size()>0&&null!=dealerList && dealerList.size()>0){
			for(int i=0;i<dealerList.size();i++){
				//下发操作
//				int flag = (datalist,dealerList.get(i));
//				if(flag==1){
//					logger.info("====================SEDCS017Cloud  维修工时参数下发成功========================");
//				}else{
//					logger.info("================SEDCS017Cloud  维修工时参数下发失败====================");
//				}
			}

		}else{
			logger.info("====SEDCS017Cloud  维修工时参数下发结束====,无数据！ ");
		}
		logger.info("====SEDCS017Cloud  维修工时参数下发结束====,下发了(" + datalist.size() + ")条数据");
	}
}
