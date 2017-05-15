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
import com.yonyou.dms.DTO.gacfca.SADCS026DTO;
import com.yonyou.dms.common.domains.PO.basedata.TtStoreInfoPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS026CloudImpl extends BaseCloudImpl implements SADCS026Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS026CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	public String handleExecutor(List<SADCS026DTO> dto) throws Exception {
		String msg = "1";
		logger.info("====配件入库来源监控报表数据上报接收开始====");
		for (SADCS026DTO vo : dto) {
			try {
				insertVo(vo);
			} catch (Exception e) {
				logger.error("配件入库来源监控报表数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====配件入库来源监控报表数据上报接收结束====");
		return msg;
	}

	/**
	 * 写入数据 - 配件入库来源监控报表
	 * 
	 * @param vo
	 * @throws Exception
	 */
	private void insertVo(SADCS026DTO vo) {

		logger.info("====配件入库来源监控报表开始====");
		try {
			Map<String, Object> map = dao.getSeDcsDealerCode(vo.getEntityCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
			TtStoreInfoPO po = new TtStoreInfoPO();
			po.setString("DEALER_CODE", dealerCode);// 经销商代码
			po.setDouble("DELIVER_PART_QUANTITY", vo.getDeliverPartQuantity());// 货运单入库配件数量
			po.setDouble("DELIVER_PART_AMOUNT", vo.getDeliverPartAmount());// 货运单入库配件金额
			po.setDouble("PROVIS_PART_QUANTITY", vo.getProvisPartQuantity());// 临时入库已核销配件数量
			po.setDouble("PROVIS_PART_AMOUNT", vo.getProvisPartAmount());// 临时入库已核销配件金额
			po.setDouble("VER_PART_QUANTITY", vo.getVerPartQuantity());// 临时入库未核销核销配件数量
			po.setDouble("VER_PART_AMOUNT", vo.getVerPartAmount());// 临时入库未核销配件金额
			po.setDouble("HANDLE_PART_QUANTITY", vo.getHandlePartQuantity());// 手工入库配件数量
			po.setDouble("HANDLE_PART_AMOUNT", vo.getHandlePartAmount());// 手工入库配件金额
			po.setDouble("ALLOCATE_PART_QUANTITY", vo.getAllocatePartQuantity());// 调拨入库配件数量
			po.setDouble("ALLOCATE_PART_AMOUNT", vo.getAllocatePartAmount());// 调拨入库配件金额
			po.setDouble("PROFIT_PART_QUANTITY", vo.getProfitPartQuantity());// 报溢入库配件数量
			po.setDouble("PROFIT_PART_AMOUNT", vo.getProfitPartAmount());// 报溢入库配件金额
			po.setDouble("SUM_PART_QUANTITY", vo.getSumPartQuantity());// 汇总配件数量
			po.setDouble("SUM_PART_AMOUNT", vo.getSumPartAmount());// 汇总配件金额
			po.setTimestamp("UPLOAD_datetime", vo.getUploadTimestamp());// 上报时间
			if (null != findById(vo, dealerCode)) {
				Long updatedocumentId = findById(vo, dealerCode);
				TtStoreInfoPO.update("UpdateBy=?,UpdateDate=?", "STORAGE_ID=?", 11111111L, new Date(),
						updatedocumentId);
			} else {
				po.setLong("Create_By", 11111111L);// 创建人
				po.setTimestamp("Create_Date", new Date());// 创建日期
				po.insert();
			}
			logger.info("====配件入库来源监控报表结束====");
		} catch (Exception e) {
			logger.error("配件入库来源监控报表数据上报接收失败", e);
			throw new ServiceBizException(e);
		}

	}

	/**
	 * 校验经销商本月是否入库数据上报
	 * 
	 * @param vo
	 * @return
	 */
	private Long findById(SADCS026DTO vo, String dealerCode) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		Long documentId = 0L;
		try {
			String sql = new String(
					"SELECT T1.STORAGE_ID  FROM TT_STORE_INFO_dcs T1 WHERE date_format(T1.UPLOAD_TIMESTAMP,'%Y-%M') = "
							+ "'" + sdf.format(vo.getUploadTimestamp()) + "' AND T1.DEALER_CODE = '" + dealerCode
							+ "' ");
			List<Map> list = OemDAOUtil.findAll(sql, null);
			if (null != list && list.size() > 0) {
				for (Map map : list) {
					documentId = (Long) map.get("STORAGE_ID");
					return documentId;
				}
			}
		} catch (Exception e) {
			logger.error("配件入库来源监控报表数据上报校验数据失败", e);
			throw new ServiceBizException(e);
		}
		return null;

	}

}
