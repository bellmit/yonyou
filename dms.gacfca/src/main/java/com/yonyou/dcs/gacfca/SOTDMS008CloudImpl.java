package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
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
import com.yonyou.dms.DTO.gacfca.TiAppNCultivateDto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.common.domains.PO.basedata.TiPtShiporderPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 创建客户信息（试驾车辆信息）APP新增
 * @author luoyang
 *
 */
@Service
public class SOTDMS008CloudImpl extends BaseCloudImpl implements SOTDMS008Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS008CloudImpl.class);
	
	@Autowired
	PtShiporderDao dao;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================创建客户信息（试驾车辆信息）APP新增下发执行开始（SOTDMS008）====================");
		LinkedList<PtDlyInfoDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================创建客户信息（试驾车辆信息）APP新增下发执行结束（SOTDMS008）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(PtDlyInfoDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<PtDlyInfoDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				// 将 IS_SCAN=0或者null的字段更新为1
				Date currentTime = new Date();
				LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				TiPtShiporderPO.update("IS_DOWN = 1,UPDATE_BY= ?,UPDATE_DATE= ?", "IS_DOWN = 0 OR IS_DOWN IS NULL ",loginUser.getUserId(), currentTime);
				if(flag==1){
					logger.info("================创建客户信息（试驾车辆信息）APP新增下发成功（SOTDMS008）====================");
				}else{
					logger.info("================创建客户信息（试驾车辆信息）APP新增下发失败（SOTDMS008）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================创建客户信息（试驾车辆信息）APP新增下发经销商无业务范围（SOTDMS008）====================");
			}
		} catch (Exception e) {
			logger.info("================创建客户信息（试驾车辆信息）APP新增下发异常（SOTDMS008）====================");
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
		return finalDlyInfoList;
	}

}
