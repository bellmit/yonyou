package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppUSwapDao;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.DTO.gacfca.TiAppUSwapDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppUSwapPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SOTDMS014Coud;

/**
 * 更新客户信息（置换需求）APP更新
 * @author luoyang
 *
 */
@Service
public class SOTDMS014CloudImpl extends BaseCloudImpl implements SOTDMS014Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS014CloudImpl.class);
	
	@Autowired
	TiAppUSwapDao dao;
	
	@Autowired
	SOTDMS014Coud sotdms014;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================更新客户信息（置换需求）APP更新下发执行开始（SOTDMS014）====================");
		LinkedList<TiAppUSwapDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================更新客户信息（置换需求）APP更新下发执行结束（SOTDMS014）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(TiAppUSwapDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<TiAppUSwapDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
				flag = sotdms014.getSOTDMS014(list);
				if(flag==1){
					// IS_SEND字段更新为1	
					Date currentTime = new Date();
					LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TiAppUSwapPO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "SWAP_ID = ?",
							"1",loginUser.getUserId(), currentTime,dto.getSwapId());
					logger.info("================更新客户信息（置换需求）APP更新下发成功（SOTDMS014）====================");
				}else{
					logger.info("================更新客户信息（置换需求）APP更新下发失败（SOTDMS014）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================更新客户信息（置换需求）APP更新下发经销商无业务范围（SOTDMS014）====================");
			}
		} catch (Exception e) {
			logger.info("================更新客户信息（置换需求）APP更新下发异常（SOTDMS014）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppUSwapDto> getDataList() throws ServiceBizException {
		LinkedList<TiAppUSwapDto> vos = null;
		try {
			vos = dao.queryBodyInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vos;
	}

}
