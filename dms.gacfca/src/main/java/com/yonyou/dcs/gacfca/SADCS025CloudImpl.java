package com.yonyou.dcs.gacfca;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.DTO.gacfca.SADCS025Dto;
import com.yonyou.dms.common.domains.PO.basedata.PadDocumentRateReportPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS025CloudImpl extends BaseCloudImpl implements SADCS025Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS025CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	@Override
	public String handleExecutor(List<SADCS025Dto> dto) throws Exception {
		String msg = "1";
		for (SADCS025Dto vo : dto) {
			try {
				insertVO(vo);
			} catch (Exception e) {
				logger.error("====PAD建档率报表写入错误====", e);
				msg = "0";
				throw new ServiceBizException(e);

			}
		}
		return msg;
	}

	/**
	 * 写入数据 一周报一次
	 * 
	 * @param vo
	 */
	private void insertVO(SADCS025Dto vo) {

		try {
			logger.info("====PAD建档率报表开始====");
			Map<String, Object> map = dao.getSaDcsDealerCode(vo.getEntityCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
			PadDocumentRateReportPO po = new PadDocumentRateReportPO();
			po.setString("DEALER_CODE", dealerCode);// 经销商code
			po.setInteger("PAD_ARCHIVED_CUSTOMERS", vo.getPadArchivedCustomers());// PAD建档量
			po.setInteger("PAD_HSL_ARCHIVED_CUSTOMERS", vo.getPadHslArchivedCustomers());// PAD建档客户HSL
			po.setInteger("HSL_ARCHIVED_CUSTOMERS", vo.getHslArchivedCustomers());// 建档客户HSL
			po.setInteger("PAD_ARCHIVED_RATIO", vo.getPadArchivedRatio() * 100);// PAD建档率
			po.setTimestamp("UPLOAD_timestamp", vo.getUploadTimestamp());// 上报时间
			po.setInteger("IS_END_OF_MONTH", vo.getIsEndOfMonth());// 是否月末
			if (null != findIdByDate(vo.getUploadTimestamp(), dealerCode)) {// 如果同一天上报2条记录，修改前一条记录
				Long updatedocumentId = findIdByDate(vo.getUploadTimestamp(), dealerCode);
				PadDocumentRateReportPO updatePo = new PadDocumentRateReportPO();
				updatePo.setLong("DOCUMENT_ID", updatedocumentId);
				po.setInteger("UPDATE_BY", 11111111L);// 修改人
				po.setTimestamp("UPDATE_DATE", new Date());// 修改日期
				po.setInteger("IS_VALID", OemDictCodeConstants.STATUS_ENABLE);// 有效
				// factory.update("", updatePo, po);
				PadDocumentRateReportPO.update("UPDATE_BY=?,UPDATE_DATE=?,IS_VALID=?,IS_VALID=?", "DOCUMENT_ID=?",
						11111111L, new Date(), OemDictCodeConstants.STATUS_ENABLE, updatedocumentId);

			} else {// 一个月只有条数据是有效的
				Long updateId = findIdByUploadTimestamp(vo.getUploadTimestamp(), dealerCode);
				if (null != updateId) {// 修改成无效的

					PadDocumentRateReportPO.update("Is_Valid=?", "DOCUMENT_ID=?", OemDictCodeConstants.STATUS_DISABLE,
							updateId);
				}
				// 新增一条有效记录
				po.setLong("CREATE_BY", 11111111L);// 创建人
				po.setTimestamp("CREATE_DATE", new Date());// 创建日期
				po.setInteger("Is_Valid", OemDictCodeConstants.STATUS_ENABLE);// 有效
				po.insert();
			}
			logger.info("====PAD建档率报表结束====");
		} catch (Exception e) {
			throw new ServiceBizException(e);
		}

	}

	/**
	 * 找到上一条记录的ID 年 月
	 */
	private Long findIdByUploadTimestamp(Date uploadTimestamp, String dealerCode) {

		Long documentId = 0L;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DOCUMENT_ID FROM PAD_DOCUMENT_RATE_REPORT_dcs \n");
		sql.append("  WHERE DEALER_CODE = '" + dealerCode + "' AND date_format(current_date,'%Y-%m')='"
				+ new SimpleDateFormat("yyyy-MM").format(uploadTimestamp) + "'  \n");
		sql.append(" ORDER BY UPLOAD_TIMESTAMP DESC FETCH FIRST 2 ROW ONLY \n");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if (null != list && list.size() > 0) {
			for (Map map : list) {
				documentId = (Long) map.get("DOCUMENT_ID");
			}
			return documentId;
		}
		return null;

	}

	private Long findIdByDate(Date uploadTimestamp, String dealerCode) {

		Long documentId = 0L;
		String sql = new String("SELECT DOCUMENT_ID FROM PAD_DOCUMENT_RATE_REPORT_Dcs WHERE DEALER_CODE = '"
				+ dealerCode + "' AND date_format(UPLOAD_TIMESTAMP,'yyyy-MM-dd')='"
				+ new SimpleDateFormat("yyyy-MM-dd").format(uploadTimestamp) + "'");
		List<Map> list = OemDAOUtil.findAll(sql, null);
		if (null != list && list.size() > 0) {
			for (Map map : list) {
				documentId = (Long) map.get("DOCUMENT_ID");
				return documentId;
			}

		}
		return documentId;

	}

}
