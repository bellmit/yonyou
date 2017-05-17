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

import com.infoservice.dms.cgcsl.vo.OpRepairOrderVO;
import com.infoservice.dms.cgcsl.vo.RoAddItemDetailVO;
import com.infoservice.dms.cgcsl.vo.RoLabourDetailVO;
import com.infoservice.dms.cgcsl.vo.RoRepairPartDetailVO;
import com.infoservice.dms.cgcsl.vo.SalesPartDetailVO;
import com.infoservice.dms.cgcsl.vo.SalesPartItemDetailVO;
import com.yonyou.dcs.de.impl.BaseImpl;
import com.yonyou.dcs.gacfca.SEDCS113Cloud;
import com.yonyou.dms.DTO.gacfca.OpRepairOrderDTO;
import com.yonyou.dms.DTO.gacfca.RoAddItemDetailDTO;
import com.yonyou.dms.DTO.gacfca.RoLabourDetailDTO;
import com.yonyou.dms.DTO.gacfca.RoRepairPartDetailDTO;
import com.yonyou.dms.DTO.gacfca.SalesPartDetailDTO;
import com.yonyou.dms.DTO.gacfca.SalesPartItemDetailDTO;
import com.yonyou.f4.de.DEMessage;
import com.yonyou.f4.de.executer.DEAction;

/**
 * @Description:未结算工单(作废)上报接口
 * @author xuqinqin 
 */
@Service
public class SEDCS113  extends BaseImpl  implements DEAction {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS113.class);
	@Autowired
	SEDCS113Cloud cloud ;
	
	@Override
	public DEMessage execute(DEMessage deMsg) {
		logger.info("*************************** 开始获取上报的未结算工单(作废)数据******************************");
		try {
			Map<String, Serializable> bodys = deMsg.getBody();
			LinkedList<OpRepairOrderDTO> dtoList = new LinkedList<>();
			setDTO(dtoList,bodys);
			cloud.handleExecutor(dtoList);
		}  catch(Throwable t) {
			logger.info("*************************** 获取上报的未结算工单(作废)数据出错******************************");
			t.printStackTrace();
		} finally {
		}
		logger.info("*************************** 成功获取上报的未结算工单(作废)数据******************************");
		return null;
	}
	private void setDTO(LinkedList<OpRepairOrderDTO> dtoList, Map<String, Serializable> bodys){
		for (Entry<String, Serializable> entry : bodys.entrySet()) {
			OpRepairOrderVO vo = new OpRepairOrderVO();
			OpRepairOrderDTO dto = new OpRepairOrderDTO();
			vo = (OpRepairOrderVO)entry.getValue();
			BeanUtils.copyProperties(vo, dto);
			LinkedList<RoRepairPartDetailVO> roRPVoList=vo.getRoRepairPartVoList();//工单维修配件明细
			LinkedList<RoAddItemDetailVO> roAIVoList=vo.getRoAddItemVoList();//工单附加项目明细
			LinkedList<RoLabourDetailVO> roLaVoList=vo.getRoLabourVoList();//工单维修项目明细
			LinkedList<SalesPartDetailVO> sPVoList=vo.getSalesPartVoList();//配件销售单
			LinkedList<SalesPartItemDetailVO> sPIVoList=vo.getSalesPartItemVoList();//配件销售单明细
			//转换工单维修配件明细信息
			if(null!=roRPVoList && roRPVoList.size()>0){
				LinkedList<RoRepairPartDetailDTO> listDto = new LinkedList<>();
				for (int i = 0; i < roRPVoList.size(); i++) {
					RoRepairPartDetailDTO roRPDto = new RoRepairPartDetailDTO();
					RoRepairPartDetailVO roRPVo=  roRPVoList.get(i);
					BeanUtils.copyProperties(roRPVo, roRPDto);
					listDto.add(roRPDto);
				}
				dto.setRoRepairPartVoList(listDto);
			}
			//转换工单附加项目明细信息
			if(null!=roAIVoList && roAIVoList.size()>0){
				LinkedList<RoAddItemDetailDTO> listDto = new LinkedList<>();
				for (int i = 0; i < roAIVoList.size(); i++) {
					RoAddItemDetailDTO roAIDto = new RoAddItemDetailDTO();
					RoAddItemDetailVO roAIVo=  roAIVoList.get(i);
					BeanUtils.copyProperties(roAIVo, roAIDto);
					listDto.add(roAIDto);
				}
				dto.setRoAddItemVoList(listDto);
			}
			//转换工单维修项目明细信息
			if(null!=roLaVoList && roLaVoList.size()>0){
				LinkedList<RoLabourDetailDTO> listDto = new LinkedList<>();
				for (int i = 0; i < roLaVoList.size(); i++) {
					RoLabourDetailDTO roLaDto = new RoLabourDetailDTO();
					RoLabourDetailVO roLaVo=  roLaVoList.get(i);
					BeanUtils.copyProperties(roLaVo, roLaDto);
					listDto.add(roLaDto);
				}
				dto.setRoLabourVoList(listDto);
			}
			//转换配件销售单信息
			if(null!=sPVoList && sPVoList.size()>0){
				LinkedList<SalesPartDetailDTO> listDto = new LinkedList<>();
				for (int i = 0; i < sPVoList.size(); i++) {
					SalesPartDetailDTO sPDto = new SalesPartDetailDTO();
					SalesPartDetailVO sPVo=  sPVoList.get(i);
					BeanUtils.copyProperties(sPVo, sPDto);
					listDto.add(sPDto);
				}
				dto.setSalesPartVoList(listDto);
			}
			//转换配件销售单明细信息
			if(null!=sPIVoList && sPIVoList.size()>0){
				LinkedList<SalesPartItemDetailDTO> listDto = new LinkedList<>();
				for (int i = 0; i < roRPVoList.size(); i++) {
					SalesPartItemDetailDTO sPIDto = new SalesPartItemDetailDTO();
					SalesPartItemDetailVO sPIVo=  sPIVoList.get(i);
					BeanUtils.copyProperties(sPIVo, sPIDto);
					listDto.add(sPIDto);
				}
				dto.setSalesPartItemVoList(listDto);
			}
			dtoList.add(dto);
		}
	}
}
