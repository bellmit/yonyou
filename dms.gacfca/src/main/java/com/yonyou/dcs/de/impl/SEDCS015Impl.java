package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS015Dao;
import com.yonyou.dcs.de.SEDCS015;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SEDCS015DTO;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SEDCS015Impl  extends BaseImpl  implements  SEDCS015 {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS015Impl.class);
	
	@Autowired
	SEDCS015Dao dao;

	/**
	 * 全量下发
	 * @throws Exception 
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
		}catch (Exception e) {
			re="2";
			logger.error(e.getMessage(), e);
		}
		return re;
	}
	/**
	 * 多选下发
	 * @param array
	 * @throws Exception 
	 */
	@Override
	public String sendMoreInfo(String array) throws ServiceBizException {
		String re = "1";
		try {
			logger.info("====SEDCS015   维修工时多选下发开始====");
			send(array);
			logger.info("====SEDCS015   维修工时多选下发结束====");
		} catch (Exception e) {
			re="2";
			logger.info("================SEDCS015  车系限价下发异常====================");
		}
		return re;
	}
	/**
	 * DE消息发送
	 * @param array
	 * @throws Exception 
	 */
	private String send(String array) throws Exception {
		try {
			//下发的数据
			List<SEDCS015DTO> dataList = dao.queryMoreInfo(array);
			if(null!=dataList && dataList.size()>0){
				Map<String, Serializable> body = DEUtil.assembleBody(dataList);
				sendAllMsg("SEDCS015", body);
				logger.info("SEDCS015  维修工时发送成功======size："+dataList.size());
			}else{
				logger.info("SEDCS015  发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.info("SEDCS015  维修工时下发失败======");
			logger.error(t.getMessage(), t);
		} 
		return null;
	}
}