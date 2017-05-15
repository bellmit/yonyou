package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppNCustomerInfoDao;
import com.yonyou.dms.DTO.gacfca.TiAppNCustomerInfoDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppNCustomerInfoPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.gacfca.SOTDMS003;

/**
 * DCS->DMS
 * 客户接待信息/需求分析(APP新增)信息下发
 * @author luoyang
 *
 */
@Service
public class SOTDMS003CloudImpl extends BaseCloudImpl implements SOTDMS003Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS003CloudImpl.class);
	
	@Autowired
	TiAppNCustomerInfoDao dao;
	
	@Autowired
	SOTDMS003 sotdms003;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================创建客户信息（客户接待信息/需求分析）APP新增下发执行开始（SOTDMS003）====================");
		LinkedList<TiAppNCustomerInfoDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("========== 创建客户信息（客户接待信息/需求分析）APP新增下发结束：下发了(" + size + ")条数据 ==========");
		return null;
	}

	private void sendData(TiAppNCustomerInfoDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				LinkedList<TiAppNCustomerInfoDto> list = new LinkedList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				flag = sotdms003.SOTDMS003(list);
				if(flag==1){
					// IS_SEND字段更新为1	
					Date currentTime = new Date();
					LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TiAppNCustomerInfoPO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "CUSTOMER_ID = ?",
							"1",loginUser.getUserId(), currentTime,dto.getCustomerId());
					logger.info("================创建客户信息（客户接待信息/需求分析）APP新增下发成功（SOTDMS003）====================");
				}else{
					logger.info("================创建客户信息（客户接待信息/需求分析）APP新增下发失败（SOTDMS003）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================创建客户信息（客户接待信息/需求分析）APP新增下发经销商无业务范围（SOTDMS003）====================");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("================创建客户信息（客户接待信息/需求分析）APP新增下发异常（SOTDMS003）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppNCustomerInfoDto> getDataList()
			throws ServiceBizException {
		LinkedList<TiAppNCustomerInfoDto> vos = null;
		try {
			vos = dao.queryAppNCustomerInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return vos;
	}

}
