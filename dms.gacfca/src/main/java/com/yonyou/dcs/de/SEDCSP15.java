package com.yonyou.dcs.de;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.infoservice.dms.cgcsl.vo.SEDCSP15InfoVO;
import com.infoservice.dms.cgcsl.vo.SEDCSP15VO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SEDCSP15Cloud;
import com.yonyou.dms.DTO.gacfca.SEDCSP15DTO;
import com.yonyou.dms.DTO.gacfca.SEDCSP15InfoDTO;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * 到货索赔上报DCS
 * @author xuqinqin
 */
public class SEDCSP15 extends BaseImpl implements DEAction {
	
	@Autowired
	SEDCSP15Cloud cloud;
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP15.class);

	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("===============到货索赔上报接收开始(SEDCSP15)==============");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SEDCSP15DTO> dtoList = new LinkedList<SEDCSP15DTO>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		}catch (Throwable t) {
			logger.info("===============到货索赔上报接收异常(SEDCSP15)==============");
		}finally {
		}
		logger.info("===============到货索赔上报接收结束(SEDCSP15)==============");
		return null;
	}
	
	private void setDTO(LinkedList<SEDCSP15DTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SEDCSP15VO vo = new SEDCSP15VO();
			SEDCSP15DTO dto = new SEDCSP15DTO();
			vo = (SEDCSP15VO)entry.getValue();
			BeanUtils.copyProperties(vo, dto);
			LinkedList<SEDCSP15InfoVO> infoVoList=new LinkedList<SEDCSP15InfoVO>();//附件
			infoVoList=vo.getAttachList();
			if(null!=infoVoList&&infoVoList.size()>0){
				LinkedList<SEDCSP15InfoDTO> infoDtoList=new LinkedList<SEDCSP15InfoDTO>();
				for(int i=0;i<infoVoList.size();i++){
					SEDCSP15InfoVO infoVo=infoVoList.get(i);
					SEDCSP15InfoDTO infoDto=new SEDCSP15InfoDTO();
					BeanUtils.copyProperties(infoVo, infoDto);
					infoDtoList.add(infoDto);
				}
				dto.setAttachList(infoDtoList);
			}
			dtoList.add(dto);
		}
	}

}
