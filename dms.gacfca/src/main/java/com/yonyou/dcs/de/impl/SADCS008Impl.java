package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.LinkManListVO;
import com.infoservice.dms.cgcsl.vo.SalesOrderVO;
import com.infoservice.dms.cgcsl.vo.SecondCarListVo;
import com.yonyou.dcs.de.SADCS008;
import com.yonyou.dcs.gacfca.SADCS008Cloud;
import com.yonyou.dms.DTO.gacfca.LinkManListDto;
import com.yonyou.dms.DTO.gacfca.SalesOrderDto;
import com.yonyou.dms.DTO.gacfca.SecondCarListDto;
import com.yonyou.f4.de.DEException;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
@Service
public class SADCS008Impl extends BaseImpl  implements DEAction,SADCS008 {
	
	@Autowired
	SADCS008Cloud cloud008;
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS008Impl.class);
	@Override
	public DEMessage execute(DEMessage deMsg) throws DEException {
		logger.info("===============经销商车辆实销数据上报接收开始(SADCS008)==============");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<SalesOrderDto> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud008.receiveDate(dtoList);
		} catch(Throwable t) {
			t.printStackTrace();
		} finally {
		}
		logger.info("===============经销商车辆实销数据上报接收结束(SADCS008)==============");
		return null;
	}
	
	/**
	 * 数据转换
	 * @param dto
	 * @param bodys
	 */
	private void setDTO(LinkedList<SalesOrderDto> dtoList, Map<String, Serializable> bodys) {
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			SalesOrderVO vo = new SalesOrderVO();
			SalesOrderDto dto = new SalesOrderDto();
			vo = (SalesOrderVO) entry.getValue();
			LinkedList<LinkManListVO> listVO = vo.getLinkManList();
			LinkedList<SecondCarListVo> sencondCarList = vo.getSecondCarList();
			dto.setDealerCode(vo.getEntityCode());
			BeanUtils.copyProperties(vo, dto);
			//转换联系人信息
			if(null!=listVO && listVO.size()>0){
				LinkedList<LinkManListDto> listDto = new LinkedList<>();
				for (int i = 0; i < listVO.size(); i++) {
					LinkManListDto linkManDto = new LinkManListDto();
					LinkManListVO linkManVo=  vo.getLinkManList().get(i);
					BeanUtils.copyProperties(linkManVo, linkManDto);
					listDto.add(linkManDto);
				}
				dto.setLinkManList(listDto);
			}
			//转换二手车信息
			if(null!=sencondCarList && sencondCarList.size()>0){
				LinkedList<SecondCarListDto> listDto = new LinkedList<>();
				for (int i = 0; i < sencondCarList.size(); i++) {
					SecondCarListDto SecondCarDto = new SecondCarListDto();
					SecondCarListVo SecondCarVo=  sencondCarList.get(i);
					BeanUtils.copyProperties(SecondCarVo, SecondCarDto);
					listDto.add(SecondCarDto);
				}
				dto.setSecondCarList(listDto);
			}
			dtoList.add(dto);
		}
	}
	
	

}
