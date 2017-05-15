package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.yonyou.dcs.dao.TiAppUCustomerInfoDao;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.DTO.gacfca.TiAppUCustomerInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppNSwapPO;
import com.yonyou.dms.common.domains.PO.basedata.TiAppUCustomerInfoPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SOTDMS012;

/**
 * 更新客户信息（客户接待信息/需求分析）APP更新
 * @author luoyang
 *
 */
@Repository
public class SOTDMS012CloudImpl extends BaseCloudImpl implements SOTDMS012Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS012CloudImpl.class);
	
	@Autowired
	TiAppUCustomerInfoDao dao;
	
	@Autowired
	SOTDMS012 sotdms012;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================更新客户信息（客户接待信息/需求分析）APP更新下发执行开始（SOTDMS012）====================");
		LinkedList<TiAppUCustomerInfoDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================更新客户信息（客户接待信息/需求分析）APP更新下发执行结束（SOTDMS012）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(TiAppUCustomerInfoDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<TiAppUCustomerInfoDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
				flag = sotdms012.getSOTDMS012(list);
				if(flag==1){
					// IS_SEND字段更新为1	
					Date currentTime = new Date();
					LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TiAppUCustomerInfoPO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "CUSTOMER_ID = ?",
							"1",loginUser.getUserId(), currentTime,dto.getCustomerId());
					logger.info("================更新客户信息（客户接待信息/需求分析）APP更新下发成功（SOTDMS012）====================");
				}else{
					logger.info("================更新客户信息（客户接待信息/需求分析）APP更新下发失败（SOTDMS012）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================更新客户信息（客户接待信息/需求分析）APP更新下发经销商无业务范围（SOTDMS012）====================");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("================更新客户信息（客户接待信息/需求分析）APP更新下发异常（SOTDMS012）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppUCustomerInfoDto> getDataList()
			throws ServiceBizException {
		LinkedList<TiAppUCustomerInfoDto> vos = null;
		try {
			vos = dao.queryBodyInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return vos;
	}

}
