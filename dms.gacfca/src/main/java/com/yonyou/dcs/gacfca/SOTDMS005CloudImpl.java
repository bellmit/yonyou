package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppNSwapDao;
import com.yonyou.dms.DTO.gacfca.TiAppNSwapDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppNSwapPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.gacfca.SOTDMS005;

@Service
public class SOTDMS005CloudImpl extends BaseCloudImpl implements SOTDMS005Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS005CloudImpl.class);
	
	@Autowired
	TiAppNSwapDao dao;
	
	@Autowired
	SOTDMS005 sotdms005;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================创建客户信息（置换需求）APP新增下发执行开始（SOTDMS005）====================");
		LinkedList<TiAppNSwapDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================创建客户信息（置换需求）APP新增下发执行结束（SOTDMS005）====,下发了(" + size + ")条数据");
		return null;
	}

	private void sendData(TiAppNSwapDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				LinkedList<TiAppNSwapDto> list = new LinkedList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
				flag = sotdms005.SOTDMS005(list);
				if(flag==1){
					// IS_SEND字段更新为1	
					Date currentTime = new Date();
					LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TiAppNSwapPO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "SWAP_ID = ?",
							"1",loginUser.getUserId(), currentTime,dto.getSwapId());
					logger.info("================创建客户信息（置换需求）APP新增下发成功（SOTDMS005）====================");
				}else{
					logger.info("================创建客户信息（置换需求）APP新增下发失败（SOTDMS005）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================创建客户信息（置换需求）APP新增下发经销商无业务范围（SOTDMS005）====================");
			}
		} catch (Exception e) {
			logger.info("================创建客户信息（置换需求）APP新增下发异常（SOTDMS005）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppNSwapDto> getDataList() throws ServiceBizException {
		LinkedList<TiAppNSwapDto> vos = null;
		try {
			vos = dao.queryAppNSwap();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}

}
