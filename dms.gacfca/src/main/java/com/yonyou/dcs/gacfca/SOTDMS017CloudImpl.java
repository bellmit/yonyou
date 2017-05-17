package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppUSalesQuotasDao;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.DTO.gacfca.TiAppUSalesQuotasDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppSendVerificationPO;
import com.yonyou.dms.common.domains.PO.basedata.TiAppUSalesQuotasPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.gacfca.SOTDMS017Coud;

/**
 * 销售人员分配信息APP更新
 * @author luoyang
 *
 */
@Service
public class SOTDMS017CloudImpl extends BaseCloudImpl implements SOTDMS017Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS017CloudImpl.class);
	
	@Autowired
	TiAppUSalesQuotasDao dao;
	
	@Autowired
	SOTDMS017Coud sotdms017;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================销售人员分配信息APP更新下发执行开始（SOTDMS017）====================");
		LinkedList<TiAppUSalesQuotasDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================销售人员分配信息APP更新下发执行结束（SOTDMS017）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(TiAppUSalesQuotasDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<TiAppUSalesQuotasDto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				if(flag==1){
					//下发操作
					flag = sotdms017.getSOTDMS017(list);
					// IS_SEND字段更新为1
					Date currentTime = new Date();
					LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TiAppUSalesQuotasPO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "SALES_QUOTAS_ID = ?",
							"1",loginUser.getUserId(), currentTime,dto.getSalesQuotasId());
					logger.info("================销售人员分配信息APP更新下发成功（SOTDMS017）====================");
				}else{
					logger.info("================销售人员分配信息APP更新下发失败（SOTDMS017）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================销售人员分配信息APP更新下发经销商无业务范围（SOTDMS017）====================");
			}
		} catch (Exception e) {
			logger.info("================销售人员分配信息APP更新下发异常（SOTDMS017）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppUSalesQuotasDto> getDataList()
			throws ServiceBizException {
		LinkedList<TiAppUSalesQuotasDto> vos = null;
		try {
			vos = dao.queryBodyInfo();
		} catch (Exception e) {
			e.printStackTrace();
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}

}
