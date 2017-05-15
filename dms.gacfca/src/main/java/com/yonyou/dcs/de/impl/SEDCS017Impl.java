package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS017Dao;
import com.yonyou.dcs.de.SEDCS017;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SEDCS017DTO;
@Service
public class SEDCS017Impl  extends BaseImpl  implements  SEDCS017 {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS017Impl.class);
	
	@Autowired
	SEDCS017Dao dao;

	/**
	 * 全量下发
	 * @throws Exception 
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
						List<SEDCS017DTO> tlrmvolist = dao.queryAllInfo(i+1,groupType);
						send(tlrmvolist);
					}
				}else{
					for(int d=0;d<j+1;d++){
						List<SEDCS017DTO> tlrmvolist = dao.queryAllInfo(d+1,groupType);
						send(tlrmvolist);
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
	 * DE消息发送
	 * @param array
	 * @throws Exception 
	 */
	private String send(List<SEDCS017DTO> dataList) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				Map<String, Serializable> body = DEUtil.assembleBody(dataList);
				sendAllMsg("SEDCS017", body);
				logger.info("SEDCS017  维修工时参数发送成功======size："+dataList.size());
			}else{
				logger.info("SEDCS015  发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.info("SEDCS017  维修工时参数下发失败======size："+dataList.size());
			logger.error(t.getMessage(), t);
		} 
		return null;
	}
}