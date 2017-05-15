package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADMS065Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.OwnerVehicleDto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * DCS->DMS
 * 大客户直销生成售后资料
 * @author luoyang
 *
 */
@Service
public class SADMS065CloudImpl extends BaseCloudImpl implements SADMS065Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADMS065CloudImpl.class);
	
	@Autowired
	SADMS065Dao dao;

	@Override
	public String execute(String vinList) throws ServiceBizException {
		logger.info("================大客户直销生成售后资料下发执行开始（SADMS065）====================");
		vinList = vinList.replace("，", ",");
		vinList = vinList.replace(",", "','");
		LinkedList<OwnerVehicleDto> vos = getDataList(vinList);
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================大客户直销生成售后资料下发执行结束（SADMS065）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(OwnerVehicleDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<OwnerVehicleDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				if(flag==1){
					//将选择的直销订单库存状态改为【已出库】
//					TmVehiclePO.update("IS_STORAGE = ?,UPDATE_DATE = ?,UPDATE_DATE = ?", "VIN = ?", 
//							1,new Date(),DEConstant.DE_UPDATE_BY,dto.getVin());
					logger.info("================大客户直销生成售后资料下发成功（SADMS065）====================");
				}else{
					logger.info("================大客户直销生成售后资料下发失败（SADMS065）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================大客户直销生成售后资料下发经销商无业务范围（SADMS065）====================");
			}
		} catch (Exception e) {
			logger.info("================大客户直销生成售后资料下发异常（SADMS065）====================");
		}
		
	}

	@Override
	public LinkedList<OwnerVehicleDto> getDataList(String vinList)
			throws ServiceBizException {
		LinkedList<OwnerVehicleDto> vos = null;
		try {
			vos = dao.getVehicelOwnerInfo(vinList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}
	
	

}
