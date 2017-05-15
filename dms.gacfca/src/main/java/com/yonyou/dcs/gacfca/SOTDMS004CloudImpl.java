package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppNTestDriveDao;
import com.yonyou.dms.DTO.gacfca.SEDMS065Dto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppNTestDrivePO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class SOTDMS004CloudImpl extends BaseCloudImpl implements SOTDMS004Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS004CloudImpl.class);
	
	@Autowired
	TiAppNTestDriveDao dao;
	
//	@Autowired
//	SOTDMS004 sotdms004;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================创建客户信息（试乘试驾）APP新增下发执行开始（SOTDMS004）====================");
		LinkedList<TiAppNTestDriveDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================创建客户信息（试乘试驾）APP新增下发执行结束（SOTDMS004）====,下发了(" + size + ")条数据");
		return null;
	}

	private void sendData(TiAppNTestDriveDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<TiAppNTestDriveDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				flag = sotdms004.SOTDMS004();
				// IS_SEND字段更新为1	
				Date currentTime = new Date();
				LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				TiAppNTestDrivePO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "TEST_DRIVE_ID = ?",
						"1",loginUser.getUserId(), currentTime,dto.getTestDriveId());
				if(flag==1){
					logger.info("================创建客户信息（试乘试驾）APP新增下发成功（SOTDMS004）====================");
				}else{
					logger.info("================创建客户信息（试乘试驾）APP新增下发失败（SOTDMS004）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================创建客户信息（试乘试驾）APP新增下发经销商无业务范围（SOTDMS004）====================");
			}
		} catch (Exception e) {
			logger.info("================创建客户信息（试乘试驾）APP新增下发异常（SOTDMS004）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppNTestDriveDto> getDataList() throws ServiceBizException {
		LinkedList<TiAppNTestDriveDto> vos = null;
		try {
			vos = dao.queryAppNTestDrive();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}

}
