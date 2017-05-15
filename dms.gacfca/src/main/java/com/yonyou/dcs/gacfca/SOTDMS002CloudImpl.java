package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.TiAppSendVerificationDao;
import com.yonyou.dms.DTO.gacfca.SEDMS065Dto;
import com.yonyou.dms.DTO.gacfca.TiAppSendVerificationDto;
import com.yonyou.dms.common.domains.PO.basedata.TiAppSendVerificationPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 数据同步验证接口
 * @author luoyang
 *
 */
@Service
public class SOTDMS002CloudImpl extends BaseCloudImpl implements SOTDMS002Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SOTDMS002CloudImpl.class);
	
//	@Autowired
//	SOTDMS002 sotdm002;
	
	@Autowired
	TiAppSendVerificationDao dao;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================数据同步验证接口下发执行开始（SOTDMS002）====================");
		LinkedList<TiAppSendVerificationDto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("========== 数据同步验证接口（SOTDMS002）下发结束：下发了(" + size + ")条数据 ==========");
		return null;
	}

	private void sendData(TiAppSendVerificationDto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				LinkedList<TiAppSendVerificationDto> list = new LinkedList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				if(flag==1){
					// IS_SEND字段更新为1
					Date currentTime = new Date();
					LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
					TiAppSendVerificationPO.update("IS_SEND = ?,UPDATE_BY= ?,UPDATE_DATE= ?", "SEND_VERIFICATION = ?",
							"1",loginUser.getUserId(), currentTime,dto.getSendVerification());
					logger.info("================数据同步验证接口下发成功（SOTDMS002）====================");
				}else{
					logger.info("================数据同步验证接口下发失败（SOTDMS002）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================数据同步验证接口下发经销商无业务范围（SOTDMS002）====================");
			}
		} catch (Exception e) {
			logger.info("================数据同步验证接口下发异常（SOTDMS002）====================");
		}
		
	}

	@Override
	public LinkedList<TiAppSendVerificationDto> getDataList() throws ServiceBizException {
		LinkedList<TiAppSendVerificationDto> result = new LinkedList<>();
		try {
			List<Map> vos = dao.queryAppSendVerification();
			if (null == vos || vos.size() == 0) {
				logger.info("====数据同步验证接口下发结束====,无数据");
				return null;
			}
			for(int i = 0; i < vos.size(); i++){
				TiAppSendVerificationDto dto = new TiAppSendVerificationDto();
				Map<String,Object> map = vos.get(i);
				dto.setDealerCode(String.valueOf(map.get("DEALER_CODE")));
				String d = String.valueOf(map.get("EXECUTE_DATE"));
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date date = d == null ? null : sdf.parse(d);
				Long sendVerification = String.valueOf(map.get("SEND_VERIFICATION")) == null ? null : Long.parseLong(String.valueOf(map.get("SEND_VERIFICATION")));
				dto.setSendVerification(sendVerification);
				dto.setExecuteDate(date);
				dto.setFailReason(String.valueOf(map.get("FAIL_REASON")));
				dto.setFailureCode(String.valueOf(map.get("FAILURE_CODE")));
				dto.setFileType(String.valueOf(map.get("FILE_TYPE")));
				dto.setUniquenessId(String.valueOf(map.get("UNIQUENESS_ID")));
				result.add(dto);
			}
			Integer size = result.isEmpty()?0:result.size();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException(e);
		}
		return result;
	}

}
