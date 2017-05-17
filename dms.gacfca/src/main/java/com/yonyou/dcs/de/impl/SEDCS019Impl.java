package com.yonyou.dcs.de.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.LinkManListVO;
import com.infoservice.dms.cgcsl.vo.RecallServiceLabourVO;
import com.infoservice.dms.cgcsl.vo.RecallServicePartVO;
import com.infoservice.dms.cgcsl.vo.RecallServiceVO;
import com.infoservice.dms.cgcsl.vo.RecallServiceVehicleVO;
import com.infoservice.dms.cgcsl.vo.SEDCS017VO;
import com.yonyou.dcs.dao.SEDCS019Dao;
import com.yonyou.dcs.de.SEDCS019;
import com.yonyou.dcs.util.DEUtil;
import com.yonyou.dms.DTO.gacfca.LinkManListDto;
import com.yonyou.dms.DTO.gacfca.RecallServiceDTO;
import com.yonyou.dms.DTO.gacfca.RecallServiceLabourDTO;
import com.yonyou.dms.DTO.gacfca.RecallServicePartDTO;
import com.yonyou.dms.DTO.gacfca.RecallServiceVehicleDTO;
import com.yonyou.dms.DTO.gacfca.SEDCS017DTO;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SEDCS019Impl  extends BaseImpl  implements  SEDCS019 {
	private static final Logger logger = LoggerFactory.getLogger(SEDCS019Impl.class);
	
	@Autowired
	SEDCS019Dao dao;

	@Override
	public String doSend(String recallId){
		String re = "1";
		try {
			logger.info("====SEDCS019       召回活动下发开始====");
			//下发数据查询
			LinkedList<RecallServiceDTO> datalist = dao.queryAllInfo(recallId);
			//要发数据的经销商列表
			List<Map<String, Object>> listdealer = dao.queryDealer(recallId);
			List<String> dealerCodes = new ArrayList<String>();
			
			for (Map<String, Object> dealer : listdealer) {
				if (dealer!=null) {
					String dealerCode="";
					try {
						dealerCode=CommonUtils.checkNull(dealer.get("DEALER_CODE"));
						if (!"".equals(dealerCode)) {
							//可下发的经销商列表
							dealerCodes.add(dealerCode);
						}
					} catch (Exception e) {
						logger.error("Cann't send to " + dealerCode, e);
					}
				}
			}
			
			if (null == dealerCodes || dealerCodes.size() == 0) {
				logger.info("====SEDCS019    召回活动下发结束====,无数据");
			}else{
				//获取下架经销商
				for(int i =0;i<dealerCodes.size();i++){
					List <String> dmsCodes = new ArrayList<String>();
					Map<String, Object> dmsDealer = dao.getDmsDealerCode(dealerCodes.get(i));
					if (null==dmsDealer || dmsDealer.size() == 0) {
						logger.info("DMS端不存在编码"+dealerCodes.get(i)+"为 的经销商记录");
						continue;
					} else {
						dmsCodes.add(CommonUtils.checkNull(dmsDealer.get("DMS_CODE")));
					}
					
					send(datalist, dmsCodes);
					
				}
				
				//更新下发状态,下发时间
				dao.updateSend(recallId);
				
				logger.info("====SEDCS019  召回活动下发结束====,下发了(" + datalist.size() + ")条数据");
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
	private String send(LinkedList<RecallServiceDTO> dataList,List<String> dmsCodes) throws Exception {
		try {
			if(null!=dataList && dataList.size()>0){
				List<RecallServiceVO> vos = new ArrayList<>();
				setVos(vos,dataList);
				Map<String, Serializable> body = DEUtil.assembleBody(vos);
				sendMsg("SEDMS019",dmsCodes, body);
				logger.info("SEDCS019   召回活动发送成功======size："+dataList.size());
			}else{
				logger.info("SEDCS019   召回活动发送数据为空=====");
			}
		} catch (Throwable t) {	
			logger.info("SEDCS019   召回活动下发失败======size："+dataList.size());
			logger.error(t.getMessage(), t);
		} 
		return null;
	}
	/**
	 * 数据转换
	 * @param vos
	 * @param dataList
	 */
	private void setVos(List<RecallServiceVO> vos, LinkedList<RecallServiceDTO> dataList) {
		for (int i = 0; i < dataList.size(); i++) {
			RecallServiceDTO dto = dataList.get(i);
			RecallServiceVO maVo = new RecallServiceVO();//主VO
			
			maVo.setRecallNo(dto.getRecallNo());
			maVo.setRecallName(dto.getRecallName());
			maVo.setRecallTheme(dto.getRecallTheme());
			maVo.setRecallType(dto.getRecallType());
			maVo.setRecallStartDate(dto.getRecallStartDate());
			maVo.setRecallEndDate(dto.getRecallEndDate());
			maVo.setIsFixedCost(dto.getIsFixedCost());
			maVo.setManHourCost(dto.getManHourCost());
			maVo.setPartCost(dto.getPartCost());
			maVo.setOtherCost(dto.getOtherCost());
			maVo.setRecallExplain(dto.getRecallExplain());
			maVo.setReleaseTag(dto.getReleaseTag());
			maVo.setReleaseDate(dto.getReleaseDate());
			LinkedList<RecallServiceVehicleDTO> veDtoList=dto.getVehicleListDto();//车辆DTO
			LinkedList<RecallServicePartDTO> paDtoList=dto.getPartListDto();//配件DTO
			LinkedList<RecallServiceLabourDTO> laDtoList=dto.getLabourListDto();//工时DTO
			//转换车辆信息
			if(null!=veDtoList && veDtoList.size()>0){
				LinkedList<RecallServiceVehicleVO> veVoList=new LinkedList<RecallServiceVehicleVO>();//车辆VO
				for (int j = 0; j < veDtoList.size(); j++) {
					RecallServiceVehicleDTO veDtO=veDtoList.get(j);
					RecallServiceVehicleVO veVO=new RecallServiceVehicleVO();
					
					BeanUtils.copyProperties(veDtO, veVO);
					veVoList.add(veVO);
				}
				maVo.setVehicleListVO(veVoList);
			}
			//转换配件信息
			if(null!=paDtoList && paDtoList.size()>0){
				LinkedList<RecallServicePartVO> paVoList=new LinkedList<RecallServicePartVO>();//车辆VO
				for (int j = 0; j < paDtoList.size(); j++) {
					RecallServicePartDTO paDtO=paDtoList.get(j);
					RecallServicePartVO paVO=new RecallServicePartVO();
					
					BeanUtils.copyProperties(paDtO, paVO);
					paVoList.add(paVO);
				}
				maVo.setPartListVO(paVoList);
			}
			//转换工时信息
			if(null!=laDtoList && laDtoList.size()>0){
				LinkedList<RecallServiceLabourVO> laVoList=new LinkedList<RecallServiceLabourVO>();//车辆VO
				for (int j = 0; j < laDtoList.size(); j++) {
					RecallServiceLabourDTO laDtO=laDtoList.get(j);
					RecallServiceLabourVO laVO=new RecallServiceLabourVO();
					
					BeanUtils.copyProperties(laDtO, laVO);
					laVoList.add(laVO);
				}
				maVo.setLabourListVO(laVoList);
			}
			vos.add(maVo);
		}
	}
}