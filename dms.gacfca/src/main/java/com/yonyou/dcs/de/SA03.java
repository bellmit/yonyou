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

import com.infoservice.dms.cgcsl.vo.VsStockEntryItemVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SA03Cloud;
import com.yonyou.dms.DTO.gacfca.VsStockEntryItemDto;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;
/**
 * @Description:调拨入库数据上传接收接口
 * @author xuqinqin 
 */
@Service
public class SA03  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SA03.class);
	@Autowired
	SA03Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** SA03开始获取调拨入库数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<VsStockEntryItemDto> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** SA03调拨入库数据上传出错******************************");
			t.printStackTrace();
			try {
			} catch(Exception e) {
				e.printStackTrace();
			}
		} finally {
		}
		logger.info("*************************** SA03成功获取调拨入库数据******************************");
		return null;
	}
	private void setDTO(LinkedList<VsStockEntryItemDto> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			VsStockEntryItemVO vo = new VsStockEntryItemVO();
			VsStockEntryItemDto dto = new VsStockEntryItemDto();
			vo = (VsStockEntryItemVO)entry.getValue();
			BeanUtils.copyProperties(vo, dto);
			dtoList.add(dto);
		}
	}
}
