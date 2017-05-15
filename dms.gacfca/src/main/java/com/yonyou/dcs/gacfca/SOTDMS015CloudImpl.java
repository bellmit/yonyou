package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.PtShiporderDao;
import com.yonyou.dms.DTO.gacfca.PtDlyInfoDetailDto;
import com.yonyou.dms.DTO.gacfca.PtDlyInfoDto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 更新客户信息（金融报价）APP更新
 * @author luoyang
 *
 */
@Service
public class SOTDMS015CloudImpl extends BaseCloudImpl implements SOTDMS015Cloud  {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS015CloudImpl.class);
	
	@Autowired
	PtShiporderDao dao;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================更新客户信息（金融报价）APP更新下发执行开始（SOTDMS015）====================");
		LinkedList<PtDlyInfoDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================更新客户信息（金融报价）APP更新下发执行结束（SOTDMS015）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(PtDlyInfoDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<PtDlyInfoDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作 接口调用
//				int flag = sotdms003.SOTDMS003();
				if(flag==1){
					logger.info("================更新客户信息（金融报价）APP更新下发成功（SOTDMS015）====================");
				}else{
					logger.info("================更新客户信息（金融报价）APP更新下发失败（SOTDMS015）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================更新客户信息（金融报价）APP更新下发经销商无业务范围（SOTDMS015）====================");
			}
		} catch (Exception e) {
			logger.info("================更新客户信息（金融报价）APP更新下发异常（SOTDMS015）====================");
		}
		
	}

	@Override
	public LinkedList<PtDlyInfoDto> getDataList() throws ServiceBizException {
		LinkedList<PtDlyInfoDto> vos = null;
		LinkedList<PtDlyInfoDto> finalDlyInfoList = new LinkedList<>();
		try {
			vos = dao.queryPtShiporderPOInfo();
			
			if (null == vos || vos.size() == 0) {
				return null;
			}else{
				for (int i = 0; i < vos.size(); i++) {
					PtDlyInfoDto dto = vos.get(i);
					LinkedList<PtDlyInfoDetailDto> list = dao.queryPtShiporderDetailInfo(dto.getDealerCode(), dto.getDeliverynote(), dto.getElinkorderno(), dto.getInvoiccreationdate());
					// 组装成hashmap
					HashMap<Integer, PtDlyInfoDetailDto> detailHashMap = new HashMap<Integer, PtDlyInfoDetailDto>();
					for (int n = 0; n < list.size(); n++) {
						PtDlyInfoDetailDto detailVO = (PtDlyInfoDetailDto) list.get(n);
						detailHashMap.put(n, detailVO);
					}
					dto.setPtDlyInfoDetailList(detailHashMap);
					finalDlyInfoList.add(dto);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = finalDlyInfoList==null?0:finalDlyInfoList.size();
		return finalDlyInfoList;
	}

}
