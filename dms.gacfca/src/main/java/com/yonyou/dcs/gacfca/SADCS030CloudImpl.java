package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS030Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.TmWxReserverInfoReportDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmWxReserverInfoReportPO;
import com.yonyou.dms.common.domains.PO.customer.TmWxReserverInfoPO;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS030CloudImpl extends BaseCloudImpl implements SADCS030Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS026CloudImpl.class);
	@Autowired
	SADCS030Dao dao;

	@Override
	public String handleExecutor(List<TmWxReserverInfoReportDTO> dto) throws Exception {
		String msg = "1";
		logger.info("====logger.info ->>> 微信保养预约信息上报[DMS->DCS], start..====");
		for (TmWxReserverInfoReportDTO vo : dto) {
			try {
				// 根据预约单号查询该预约信息是否存在

				List<Map> list = dao.isExistReserveIdQuery(vo.getReserveId());
				if (list.size() > 0 && null != list) {

					// 处理[TM_WX_RESERVER_INFO]表
					if (list.get(0).get("A").equals("1")) {

						// 更新[TM_WX_RESERVER_INFO]表，预约单信息
						this.updateTmWxReserverInfo(vo);

					} else {

						// 新增[TM_WX_RESERVER_INFO]表，预约单信息
						this.insertTmWxReserverInfo(vo);

					}

					// 处理[TM_WX_RESERVER_INFO_REPORT]表
					if (list.get(1).get("B").equals("1")) {

						// 更新[TM_WX_RESERVER_INFO_REPORT]表，预约单信息
						this.updateTmWxReserverInfoReport(vo);

					} else {

						// 新增[TM_WX_RESERVER_INFO_REPORT]表，预约单信息
						this.insertTmWxReserverInfoReport(vo);

					}

				} else {

					// 新增[TM_WX_RESERVER_INFO]表，预约单信息
					this.insertTmWxReserverInfo(vo);

					// 新增[TM_WX_RESERVER_INFO_REPORT]表，预约单信息
					this.insertTmWxReserverInfoReport(vo);

				}

				logger.info("logger.info ->>> 微信保养预约信息上报[DMS->DCS], success!");

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
	 * 新增保养预约单[TM_WX_RESERVER_INFO_REPORT]表
	 * 
	 * @param vo
	 * @throws Exception
	 */
	private void insertTmWxReserverInfoReport(TmWxReserverInfoReportDTO vo) {

		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		TmWxReserverInfoReportPO po = new TmWxReserverInfoReportPO();
		// 根据 ENTITY_CODE 查 DEALER_CODE
		Map<String, Object> map = dao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE")); // 上报经销商信息
		po.setString("DEALER_CODE", dealerCode);
		po.setString("LINKMAN_NAME", vo.getLinkmanName());
		po.setString("TELEPHONE", vo.getTelephone());
		po.setString("VIN", vo.getVin());
		po.setString("LICENCE_NUM", vo.getLicenceNum());
		po.setString("MILES", vo.getMiles());
		po.setTimestamp("RESERVE_DATE", vo.getReserveDate());
		po.setInteger("STATUS", vo.getStatus());
		po.setString("REMARK", vo.getRemark());
		po.setString("RESERVE_TIME_ID", vo.getReserveTimeId());
		po.setLong("UPDATE_BY", DEConstant.DE_UPDATE_BY);
		po.setTimestamp("CREATE_DATE", format);
		po.setTimestamp("RESERVER_CONFIRM_TIME", vo.getReserverConfirmTime());
		po.setTimestamp("RESERVE_FINISH_DATE", vo.getServiceFinishDate());
		po.setTimestamp("RESERVE_EXPIRE_DATE", vo.getReserveExpireDate());
		po.setTimestamp("RESERVE_CANCEL_DATE", vo.getReserveCancelDate());
		po.insert();
	}

	/**
	 * 根据保养预约单号更新[TM_WX_RESERVER_INFO_REPORT]表
	 * 
	 * @param vo
	 * @throws Exception
	 */
	private void updateTmWxReserverInfoReport(TmWxReserverInfoReportDTO vo) {
		// 根据 ENTITY_CODE 查 DEALER_CODE
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		Map<String, Object> map = dao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE")); // 上报经销商信息

		TmWxReserverInfoReportPO.update(
				"DEALER_CODE=?,LINKMAN_NAME=?,TELEPHONE=?,VIN=?,LICENCE_NUM=?,MILES=?,RESERVE_DATE=? ,STATUS=?,REMARK=?,RESERVE_TIME_ID=?,UPDATE_BY=?,"
						+ "UPDATE_DATE=?,RESERVER_CONFIRM_TIME=?,RESERVE_FINISH_DATE=?,RESERVE_EXPIRE_DATE=?,RESERVE_CANCEL_DATE=?",
				"RESERVE_ID=?", dealerCode, vo.getLinkmanName(), vo.getTelephone(), vo.getVin(), vo.getLicenceNum(),
				vo.getMiles(), vo.getReserveDate(), vo.getStatus(), vo.getRemark(), vo.getReserveTimeId(),
				DEConstant.DE_UPDATE_BY, format, vo.getReserverConfirmTime(), vo.getServiceFinishDate(),
				vo.getReserveExpireDate(), vo.getReserveCancelDate(), vo.getReserveId());

	}

	/**
	 * 新增保养预约单[TM_WX_RESERVER_INFO]表
	 * 
	 * @param vo
	 * @throws Exception
	 */
	private void insertTmWxReserverInfo(TmWxReserverInfoReportDTO vo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		TmWxReserverInfoPO po = new TmWxReserverInfoPO();
		// 根据 ENTITY_CODE 查 DEALER_CODE
		Map<String, Object> map = dao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE")); // 上报经销商信息
		po.setString("DEALER_CODE", dealerCode);
		po.setString("LINKMAN_NAME", vo.getLinkmanName());
		po.setString("TELEPHONE", vo.getTelephone());
		po.setString("VIN", vo.getVin());
		po.setString("LICENCE_NUM", vo.getLicenceNum());
		po.setString("MILES", vo.getMiles());
		po.setTimestamp("RESERVE_DATE", vo.getReserveDate());
		po.setInteger("STATUS", vo.getStatus());
		po.setString("REMARK", vo.getRemark());
		po.setString("RESERVE_TIME_ID", vo.getReserveTimeId());
		po.setLong("Create_By", DEConstant.DE_UPDATE_BY);
		po.setTimestamp("Create_Date", format);
		po.setInteger("Is_Del", 0);
		po.setTimestamp("RESERVER_CONFIRM_TIME", vo.getReserverConfirmTime());
		po.setTimestamp("RESERVER_FINISH_TIME", vo.getServiceFinishDate());
		po.setString("ENTITY_CODE", vo.getEntityCode());
		po.setString("RESERVE_ID", vo.getReserveId());

		po.insert();

	}

	/**
	 * 根据保养预约单号更新[TM_WX_RESERVER_INFO_dcs]表
	 * 
	 * @param vo
	 * @throws Exception
	 */
	private void updateTmWxReserverInfo(TmWxReserverInfoReportDTO vo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		// TmWxReserverInfoPO setPo =
		// TmWxReserverInfoPO.findFirst("RESERVE_ID=?", vo.getReserveId());
		// 根据 ENTITY_CODE 查 DEALER_CODE
		Map<String, Object> map = dao.getSaDcsDealerCode(vo.getDealerCode());
		String dealerCode = String.valueOf(map.get("DEALER_CODE")); // 上报经销商信息

		TmWxReserverInfoPO.update(
				"DEALER_CODE=?,LINKMAN_NAME=?,TELEPHONE=?,VIN=?,LICENCE_NUM=?,MILES=?,RESERVE_DATE=?,STATUS=?,REMARK=?,RESERVE_TIME_ID=?,"
						+ "UPDATE_BY=?,UPDATE_DATE=?,RESERVER_CONFIRM_TIME=?,RESERVER_FINISH_TIME=?,ENTITY_CODE=?",
				"RESERVE_ID=?", dealerCode, vo.getLinkmanName(), vo.getTelephone(), vo.getVin(), vo.getLicenceNum(),
				vo.getMiles(), vo.getReserveDate(), vo.getStatus(), vo.getRemark(), vo.getReserveTimeId(),
				DEConstant.DE_UPDATE_BY, format, vo.getReserverConfirmTime(), vo.getServiceFinishDate(),
				vo.getDealerCode(), vo.getReserveId());
	}

}
