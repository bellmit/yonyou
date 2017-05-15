package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppNFinancialDao;
import com.yonyou.dms.DTO.gacfca.TiAppNFinancialDto;
import com.yonyou.dms.DTO.gacfca.TiAppNSwapDto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppNSwapPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

@Service
public class SOTDMS006CloudImpl extends BaseCloudImpl implements SOTDMS006Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS006CloudImpl.class);
	
	@Autowired
	TiAppNFinancialDao dao;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================创建客户信息（金融报价）APP新增下发执行开始（SOTDMS006）====================");
		LinkedList<TiAppNFinancialDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================创建客户信息（金融报价）APP新增下发执行结束（SOTDMS006）====,下发了(" + size + ")条数据");
		return null;
	}

	private void sendData(TiAppNFinancialDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<TiAppNFinancialDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				// IS_SEND字段更新为1	
				Date currentTime = new Date();
				LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
				TiAppNSwapPO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "FINANCIAL_OFFER_ID = ?",
						"1",loginUser.getUserId(), currentTime,dto.getFinancialOfferId());
				if(flag==1){
					logger.info("================创建客户信息（金融报价）APP新增下发成功（SOTDMS006）====================");
				}else{
					logger.info("================创建客户信息（金融报价）APP新增下发失败（SOTDMS006）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================创建客户信息（金融报价）APP新增下发经销商无业务范围（SOTDMS006）====================");
			}
		} catch (Exception e) {
			logger.info("================创建客户信息（金融报价）APP新增下发异常（SOTDMS006）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppNFinancialDto> getDataList() throws ServiceBizException {
		LinkedList<TiAppNFinancialDto> vos = null;
		try {
			vos = dao.queryAppNFinancial();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}

}
