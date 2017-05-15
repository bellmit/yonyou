package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppUTestDriveDao;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.DTO.gacfca.TiAppUTestDriveDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppUCustomerInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TiAppUTestDrivePO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 更新客户信息（试乘试驾）APP更新
 * @author luoyang
 *
 */
@Service
public class SOTDMS013CloudImpl extends BaseCloudImpl implements SOTDMS013Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS013CloudImpl.class);
	
	@Autowired
	TiAppUTestDriveDao dao;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================更新客户信息（试乘试驾）APP更新下发执行开始（SOTDMS013）====================");
		LinkedList<TiAppUTestDriveDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================更新客户信息（试乘试驾）APP更新下发执行结束（SOTDMS013）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(TiAppUTestDriveDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				List<TiAppUTestDriveDto> list = new ArrayList<>();
				list.add(dto);
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				// IS_SEND字段更新为1	
				Date currentTime = new Date();
				LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				TiAppUTestDrivePO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "CUSTOMER_ID = ?",
						"1",loginUser.getUserId(), currentTime,dto.getTestDriveId());
				if(flag==1){
					logger.info("================更新客户信息（试乘试驾）APP更新下发成功（SOTDMS013）====================");
				}else{
					logger.info("================更新客户信息（试乘试驾）APP更新下发失败（SOTDMS013）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================更新客户信息（试乘试驾）APP更新下发经销商无业务范围（SOTDMS013）====================");
			}
		} catch (Exception e) {
			logger.info("================更新客户信息（试乘试驾）APP更新下发异常（SOTDMS013）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppUTestDriveDto> getDataList() throws ServiceBizException {
		LinkedList<TiAppUTestDriveDto> vos = null;
		try {
			vos = dao.queryBodyInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vos;
	}

}
