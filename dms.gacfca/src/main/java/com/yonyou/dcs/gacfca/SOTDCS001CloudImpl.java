package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TiDmsSendVerificationDTO;
import com.yonyou.dms.common.domains.PO.basedata.TiDmsSendVerificationPO;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 
 * Title:SOTDCS001CloudImpl
 * Description: 数据同步验证接口上报接收DMS->DCS
 * @author DC
 * @date 2017年4月13日 上午11:16:56
 * result msg 1：成功 0：失败
 */
@Service
public class SOTDCS001CloudImpl implements SOTDCS001Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SADCS057CloudImpl.class);

	@Override
	public String handleExecutor(List<TiDmsSendVerificationDTO> dtoList) throws Exception {
		String msg = "1";
		logger.info("====数据同步验证接口接收开始====");
		for (TiDmsSendVerificationDTO entry : dtoList) {
			try {
				insertData(entry);
			} catch (Exception e) {
				logger.error("数据同步验证接口接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====数据同步验证接口接收结束====");
		return msg;
	}

	// 插入新增的客户信息
		public void insertData(TiDmsSendVerificationDTO vo) throws Exception {
			// Map<String, Object> map =
			// deCommonDao.getSaDcsDealerCode(vo.getEntityCode());
			// String dealerCode = String.valueOf(map.get("DEALER_CODE"));//上报经销商信息
			TiDmsSendVerificationPO insertPO = new TiDmsSendVerificationPO();
			// insertPO.setFileName(vo.getFileName());// 文件的名称
			insertPO.setString("FILE_TYPE", vo.getFileType());// 文件的类型
			insertPO.setString("FILE_FCAID", vo.getFileFcaid());// 失败的FCA 客户ID
			insertPO.setString("FAIL_REASON", vo.getFailReason());// 失败的原因
			insertPO.setDate("CREATE_DATE", new Date());// 创建日期
			insertPO.setString("IS_SEND", "0");// 同步标志
			insertPO.setLong("CREATE_BY", DEConstant.DE_CREATE_BY);// 创建者
			// 插入数据
			insertPO.insert();
		}

}
