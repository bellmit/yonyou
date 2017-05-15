package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppUCultivateDao;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.DTO.gacfca.TiAppUCultivateDto;
import com.yonyou.dms.DTO.gacfca.TiAppUSwapDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppSendVerificationPO;
import com.yonyou.dms.common.domains.PO.basedata.TiAppUCultivatePO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 更新客户信息（客户培育）APP更新
 * @author luoyang
 *
 */
@Service
public class SOTDMS016CloudImpl extends BaseCloudImpl implements SOTDMS016Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS016CloudImpl.class);
	
	@Autowired
	TiAppUCultivateDao dao;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================更新客户信息（客户培育）APP更新下发执行开始（SOTDMS016）====================");
		LinkedList<TiAppUCultivateDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================更新客户信息（客户培育）APP更新下发执行结束（SOTDMS016）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(TiAppUCultivateDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<TiAppUCultivateDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				// IS_SEND字段更新为1
				Date currentTime = new Date();
				LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				TiAppUCultivatePO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "SEND_VERIFICATION = ?",
						"1",loginUser.getUserId(), currentTime,dto.getCultivateId());
				if(flag==1){
					logger.info("================更新客户信息（客户培育）APP更新下发成功（SOTDMS016）====================");
				}else{
					logger.info("================更新客户信息（客户培育）APP更新下发失败（SOTDMS016）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================更新客户信息（客户培育）APP更新下发经销商无业务范围（SOTDMS016）====================");
			}
		} catch (Exception e) {
			logger.info("================更新客户信息（客户培育）APP更新下发异常（SOTDMS016）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppUCultivateDto> getDataList() throws ServiceBizException {
		LinkedList<TiAppUCultivateDto> vos = null;
		try {
			vos = dao.queryBodyInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}

}
