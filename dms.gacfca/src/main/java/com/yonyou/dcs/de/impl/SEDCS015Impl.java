package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SEDCS015VO;
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
			LinkedList<SEDCS015DTO> dataList = dao.queryMoreInfo(array);
			if(null!=dataList && dataList.size()>0){
				List<SEDCS015VO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
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
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<SEDCS015VO> vos, LinkedList<SEDCS015DTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			SEDCS015DTO dto = dataList.get(i);
			SEDCS015VO vo = new SEDCS015VO();
			vo.setManhourCode(dto.getManhourCode());
			vo.setManhourName(dto.getManhourName());
			vo.setManhourEnglishName(dto.getManhourEnglishName());
			vo.setManhourNum(dto.getManhourNum());
			vo.setSeriesCode(dto.getSeriesCode());
			vo.setModelCode(dto.getModelCode());
			vo.setModelYear(dto.getModelYear());
			vo.setOneCode(dto.getOneCode());
			vo.setTwoCode(dto.getTwoCode());
			vo.setThreeCode(dto.getThreeCode());
			vo.setFourCode(dto.getFourCode());
			vo.setGroupType(dto.getGroupType());
			vo.setCliamNum(dto.getCliamNum());
			vos.add(vo);
		}
	}
}