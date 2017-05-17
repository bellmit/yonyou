package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppNCultivateDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiAppNCultivateDto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppNCultivatePO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SOTDMS007Coud;

@Service
public class SOTDMS007CloudImpl extends BaseCloudImpl implements SOTDMS007Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS007CloudImpl.class);
	
	@Autowired
	TiAppNCultivateDao dao;
	
	@Autowired
	SOTDMS007Coud sotmds007;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================创建客户信息（客户培育）APP新增下发执行开始（SOTDMS007）====================");
		LinkedList<TiAppNCultivateDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================创建客户信息（客户培育）APP新增下发执行结束（SOTDMS007）====,下发了(" + size + ")条数据");
		return null;
	}

	private void sendData(TiAppNCultivateDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<TiAppNCultivateDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
				flag = sotmds007.getSOTDMS007(list);
				if(flag==1){
					// IS_SEND字段更新为1
					TiAppNCultivatePO.update("IS_SEND = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "CULTIVATE_ID = ?", "1",DEConstant.DE_UPDATE_BY,new Date(),dto.getCultivateId());
					logger.info("================创建客户信息（客户培育）APP新增下发成功（SOTDMS007）====================");
				}else{
					logger.info("================创建客户信息（客户培育）APP新增下发失败（SOTDMS007）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================创建客户信息（客户培育）APP新增下发经销商无业务范围（SOTDMS007）====================");
			}
		} catch (Exception e) {
			logger.info("================创建客户信息（客户培育）APP新增下发异常（SOTDMS007）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppNCultivateDto> getDataList() throws ServiceBizException {
		LinkedList<TiAppNCultivateDto> vos = null;
		try {
			vos = dao.queryAppNCultivate();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}

}
