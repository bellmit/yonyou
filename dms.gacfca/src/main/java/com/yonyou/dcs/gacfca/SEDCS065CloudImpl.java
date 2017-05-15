package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDCS065Dao;
import com.yonyou.dms.DTO.gacfca.SEDMS065Dto;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 呆滞件下架or取消下发接口
 * @author luoyang
 *
 */
@Service
public class SEDCS065CloudImpl extends BaseCloudImpl implements SEDCS065Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCS065CloudImpl.class);
	
	@Autowired
	SEDCS065Dao dao;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================呆滞件下架or取消下发接口下发执行开始（SEDCS065）====================");
		LinkedList<SEDMS065Dto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================呆滞件下架or取消下发接口下发执行结束（SEDCS065）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(SEDMS065Dto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				LinkedList<SEDMS065Dto> list = new LinkedList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				if(flag==1){
					logger.info("================呆滞件下架or取消下发接口下发成功（SEDCS065）====================");
				}else{
					logger.info("================呆滞件下架or取消下发接口下发失败（SEDCS065）====================");
				}
			}else{
				//经销商无业务范围
				logger.info("================呆滞件下架or取消下发接口下发经销商无业务范围（SEDCS065）====================");
			}
		} catch (Exception e) {
			logger.info("================呆滞件下架or取消下发接口下发异常（SEDCS065）====================");
		}
		
	}

	@Override
	public LinkedList<SEDMS065Dto> getDataList()
			throws ServiceBizException {
		LinkedList<SEDMS065Dto> vos = null;		
		SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar currDate = Calendar.getInstance();
        //当前时间
        currDate.add(Calendar.DAY_OF_MONTH, 0);  
        String currTime = sf.format(currDate.getTime());
		try {
			vos = dao.queryObsoleteMaterialRelease(currTime);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException(e);
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}

}
