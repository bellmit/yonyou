package com.yonyou.dcs.gacfca;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.infoservice.dms.cgcsl.vo.VoucherVO;
import com.yonyou.dcs.dao.SADCS075Dao;
import com.yonyou.dms.DTO.gacfca.VoucherDTO;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SADCS075CloudImpl extends BaseCloudImpl implements SADCS075Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS075CloudImpl.class);
	
	@Autowired
	SADCS075Dao dao;

	/**
	 * 下发数据方法
	 * @param list
	 * @param dealerCode
	 */
	@Override
	public String sendData(String actId){
		try {
			logger.info("==============SADCS075Cloud  保险营销活动下发开始================");
			//下发的经销商 全网下发
			List<String> dealerList= dao.getAllDmsCode(0);
			//下发的数据
			List<VoucherDTO> dtolist=dao.queryVoucherDTO(actId);
			if(null!=dtolist && dtolist.size()>0){
				for(int i=0;i<dealerList.size();i++){
					//下发操作
//					int flag = (dtolist,dealerList.get(i));
//					if(flag==1){
//						logger.info("====================SADCS075Cloud  保险营销活动下发成功========================");
//					}else{
//						logger.info("================SADCS075Cloud  保险营销活动下发失败====================");
//					}
				}

			}else{
				logger.info("====SADCS075Cloud  保险营销活动下发结束====,无数据！ ");
			}
		} catch (Exception e) {
			logger.info("================SADCS075Cloud  保险营销活动下发异常====================");
		}
		return null;
	}

	@Override
	public List<VoucherVO> getSendData(String actId) throws ServiceBizException {
		return dao.queryVoucherVO(actId);
	}

}
