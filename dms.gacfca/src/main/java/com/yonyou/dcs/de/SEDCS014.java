package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.SA012VO;
import com.infoservice.dms.cgcsl.vo.SA013VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SEDCS014Cloud;
import com.yonyou.dms.common.domains.DTO.basedata.SA012Dto;
import com.yonyou.dms.common.domains.DTO.basedata.SA013Dto;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:展厅流量报表接口
 * @author xuqinqin 
 */
@Service
public class SEDCS014  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS014.class);
	@Autowired
	SEDCS014Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取上报的展厅流量数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SA013Dto> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud.receiveDate(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** 获取上报的展厅流量数据出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** 成功获取上报的展厅流量数据******************************");
		return null;
	}
	private void setDTO(LinkedList<SA013Dto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SA013VO s3vo = new SA013VO();
			SA013Dto s3dto = new SA013Dto();
			s3vo = (SA013VO)entry.getValue();
			BeanUtils.copyProperties(s3vo, s3dto);
			
			LinkedList<SA012VO> s2voList = s3vo.getList();
			if(null!=s2voList && s2voList.size()>0){
				LinkedList<SA012Dto> s2dtoList = new LinkedList<>();
				for (int i = 0; i < s2voList.size(); i++) {
					SA012Dto s2dto = new SA012Dto();
					SA012VO s2vo=  s2voList.get(i);
					BeanUtils.copyProperties(s2vo, s2dto);
					s2dtoList.add(s2dto);
				}
				s3dto.setList(s2dtoList);
			}
			
			dtoList.add(s3dto);
		}
	}
}
