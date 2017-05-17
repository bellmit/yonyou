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

import com.infoservice.dms.cgcsl.vo.SEDCSP03VO;
import com.infoservice.dms.cgcsl.vo.SEDCSP03VOPartInfoVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SEDCSP03Cloud;
import com.yonyou.dms.DTO.gacfca.SEDCSP03DTO;
import com.yonyou.dms.DTO.gacfca.SEDCSP03VOPartInfoDTO;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:配件订货上报接口 
 * @author xuqinqin 
 */
@Service
public class SEDCSP03  extends BaseImpl  implements DEAction{
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP03.class);
	
	@Autowired
	SEDCSP03Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取上报的配件订货数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SEDCSP03DTO> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** 获取上报的配件订货数据出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** 成功获取上报的配件订货数据******************************");
		return null;
	}
	/**
	 * 数据转换
	 * @param dtoList
	 * @param bodys
	 */
	private void setDTO(LinkedList<SEDCSP03DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SEDCSP03VO vo = new SEDCSP03VO();
			SEDCSP03DTO dto = new SEDCSP03DTO();
			vo = (SEDCSP03VO)entry.getValue();
			BeanUtils.copyProperties(vo, dto);
			LinkedList<SEDCSP03VOPartInfoVO> partList=vo.getTbInputPosition();//明细信息
			if(null!=partList&&partList.size()>0){
				LinkedList<SEDCSP03VOPartInfoDTO> partDtoList=new LinkedList<>();
				for(int i=0;i<partList.size();i++){
					SEDCSP03VOPartInfoVO partVo=partList.get(i);
					SEDCSP03VOPartInfoDTO partDto=new SEDCSP03VOPartInfoDTO();
					BeanUtils.copyProperties(partVo, partDto);
					partDtoList.add(partDto);
				}
				dto.setTbInputPosition(partDtoList);
			}
			dtoList.add(dto);
		}
	}
}
