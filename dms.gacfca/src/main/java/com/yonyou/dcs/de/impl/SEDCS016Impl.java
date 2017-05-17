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
import com.infoservice.dms.cgcsl.vo.SEDCS016VO;
import com.yonyou.dcs.dao.SEDCS016Dao;
import com.yonyou.dcs.de.SEDCS016;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.SEDCS015DTO;
import com.yonyou.dms.DTO.gacfca.SEDCS016DTO;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SEDCS016Impl  extends BaseImpl  implements  SEDCS016 {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS016Impl.class);
	
	@Autowired
	SEDCS016Dao dao;

	/**
	 * 全量下发
	 * @throws Exception 
	 */
	@Override
	public String doSend(){
		String re = "1";
		try {
			logger.info("====SEDCS016   行管经销商下发开始====");
			//下发数据查询
			List<SEDCS016DTO> list = dao.queryAllInfo();
			
			if (null == list || list.size() == 0) {
				logger.info("====SEDCS016  行管经销商下发结束====,无数据");
			}else{
				for(int i =0;i<list.size();i++){
					LinkedList<SEDCS016DTO> dataList = new LinkedList<SEDCS016DTO>();
					
					SEDCS016DTO dto = list.get(i);
					
					dataList.add(dto);
					//给指定经销商发送该经销商数据
					send(dataList,list.get(i).getEntityCode());
				}
				//更新下发状态,下发时间
				dao.updateSend();
				
				logger.info("====SEDCS016    行管经销商下发结束====,下发了(" + list.size() + ")条数据");
			}
		} catch (Exception e) {
			re = "2";
			logger.info("================SEDCS016    行管经销商下发异常====================");
		}
		return re;
	}
	/**
	 * DE消息发送
	 * @param array
	 * @throws Exception 
	 */
	private String send(LinkedList<SEDCS016DTO> dataList, String dmsCodes) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<SEDCS016VO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				if(!"".equals(dmsCodes)){
					sendAMsg("SEDCS016", dmsCodes, body);
					logger.info("SEDCS016    行管经销商发送成功======size："+dataList.size());
				}else{
					logger.info("SEDCS016   行管经销商下发失败======size："+dataList.size());
					throw new ServiceBizException("经销商没有维护对应关系");
				}
			}else{
				logger.info("SEDCS016  发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.error(t.getMessage(), t);
		} 
		return null;
	}
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<SEDCS016VO> vos, LinkedList<SEDCS016DTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			SEDCS016DTO dto = dataList.get(i);
			SEDCS016VO vo = new SEDCS016VO();
			vo.setEntityCode(dto.getEntityCode());
			vo.setDealerShortname(dto.getDealerShortname());
			vo.setIsRestrict(dto.getIsRestrict());
			vo.setIsOther(dto.getIsOther());
			vos.add(vo);
		}
	}
}