package com.yonyou.dcs.gacfca;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS015Dao;
import com.yonyou.dms.DTO.gacfca.SEDCS015DTO;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SEDCS015CloudImpl extends BaseCloudImpl implements SEDCS015Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS015CloudImpl.class);
	
	@Autowired
	SEDCS015Dao dao;
	
	/**
	 * 全量下发
	 */
	@Override
	public String sendAllInfo(){
		String re = "1";
		String array = "";
		try {
			logger.info("====SEDCS015Cloud  维修工时全量下发开始====");
			List<Map> count = dao.countDo();
			int f = count.size()%1000;
			int j = count.size()/1000;
			logger.info("====SEDCS015Cloud  维修工时全量下发,SIZE="+count.size()+"j="+j+"f="+f);
			if (null == count || count.size() == 0) {
				logger.info("====SEDCS015Cloud  维修工时全量下发结束====,无数据");
			}else{
				if(f == 0){
					for(int i=0;i<j;i++){
						send(array);
					}	
				}else{
					for(int i=0;i<j+1;i++){
						send(array);
					}
				}
				logger.info("================SEDCS015Cloud  维修工时全量下发结束====================");
			}
		} catch (Exception e) {
			re = "2";
			logger.info("================SEDCS015Cloud  维修工时全量下发异常====================");
		}
		return re;
	}
	
	/**
	 * 多选下发
	 */
	@Override
	public String sendMoreInfo(String array) throws ServiceBizException {
		String re = "1";
		try {
			logger.info("====SEDCS015Cloud  维修工时多选下发开始====");
			send(array);
			logger.info("====SEDCS015Cloud  维修工时多选下发结束====");
		} catch (Exception e) {
			re = "2";
			logger.info("================SEDCS015Cloud  维修工时多选下发异常====================");
		}
		return re;
	}
	/**
	 * 数据发送
	 */
	public void send(String array){
		//下发的经销商 全网下发
		List<String> dealerList=dao.getAllDmsCode(1);
		LinkedList<SEDCS015DTO> tlrmvolist = dao.queryMoreInfo(array);
		if(null!=tlrmvolist && tlrmvolist.size()>0&&null!=dealerList && dealerList.size()>0){
			for(int i=0;i<dealerList.size();i++){
				//下发操作
//				int flag = (tlrmvolist,dealerList.get(i));
//				if(flag==1){
//					logger.info("====================SEDCS015Cloud  维修工时下发成功========================");
//				}else{
//					logger.info("================SEDCS015Cloud  维修工时下发失败====================");
//				}
			}

		}else{
			logger.info("====SEDCS015Cloud  维修工时下发结束====,无数据！ ");
		}
		//更新下发状态,下发时间
		dao.updateStatue(array);
		logger.info("====SEDCS015Cloud  维修工时下发结束====,下发了(" + tlrmvolist.size() + ")条数据");
	}
}
