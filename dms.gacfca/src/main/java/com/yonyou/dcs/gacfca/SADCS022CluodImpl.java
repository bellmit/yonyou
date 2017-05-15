package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SADCS022DAO;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SADMS022Dto;
import com.yonyou.dms.common.domains.PO.basedata.TmLoanRatMaintainPO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.gacfca.SADMS022;

@Service
public class SADCS022CluodImpl extends BaseCloudImpl implements SADCS022Cluod {
	private static final Logger logger = LoggerFactory.getLogger(SADCS022CluodImpl.class);
	@Autowired
	SADCS022DAO dao;

	// 全量下发
	@Override
	public String handleExecute() throws ServiceBizException {
		logger.info("====贴息利率信息全量下发SADCS022开始====");
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String format = df.format(new Date());
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		String re = "1";
		try {
			List<String> dealer = dao.getAllDcsCode(0);
			Map<String, Object> outDmsDealer = new HashMap<>();
			List<SADMS022Dto> vos = dao.queryLoanRatMaintainInfo();
			if (null == vos || vos.size() == 0) {
				logger.info("====贴息利率信息全量下发SADCS022结束====,无数据");
				return re;
			}
			for (String dlr : dealer) {
				outDmsDealer = dao.getDmsDealerCode(dlr);
				if (outDmsDealer.get("DMS_CODE") != null && !"".equals(outDmsDealer.get("DMS_CODE"))) {
					String dmsCode = outDmsDealer.get("DMS_CODE").toString();
					int j = sendData(vos, dmsCode);
					// 更新状态
					if (j == 1) {
						for (SADMS022Dto sadms022Dto : vos) {
							sadms022Dto.getId();
							TmLoanRatMaintainPO.update("Is_Scan=?,Send_By=?,Send_Date=?,Update_By=?,Update_Date=?",
									" id=?", 1, loginInfo.getUserId(), format, DEConstant.DE_UPDATE_BY, format,
									sadms022Dto.getId());
						}
						logger.info("====贴息利率信息全量下发SADCS022结束====,下发了" + vos.size() + "条");
					} else {
						re = "2";
						logger.error("贴息利率信息全量下发失败");
					}

				}

			}

		} catch (ParseException e) {
			logger.error("贴息利率信息全量下发", e);
			re = "2";
		}
		return re;
	}

	@Autowired
	SADMS022 ser;

	private int sendData(List<SADMS022Dto> vos, String dmsCode) {
		try {
			if (null != vos) {

				// 下发操作
				int flag = ser.getSADMS022(vos, dmsCode);
				if (flag == 1) {
					logger.info("================SADCS022贴息利率信息全选下发成功（ ）====================");
					return 1;
				} else {
					logger.info("================SADCS022贴息利率信息全选下发失败（ ）====================");
					return 0;
				}
			} else {
				// 经销商无业务范围
				logger.info("================SADCS022贴息利率信息全选下发经销商无业务范围（ ）====================");
			}
		} catch (Exception e) {
			logger.info("================SADCS022贴息利率信息全选下发下发异常（ ）====================");
		}
		return 1;
	}

	// 多选下发
	@Override
	public String esendDataAll(String array) throws ServiceBizException {
		logger.info("====贴息利率信息多选下发SADCS022开始====");
		String re = "1";
		try {
			List<SADMS022Dto> tlrmvolist = dao.queryLoanRatMaintainInfoMore(array);
			if (null == tlrmvolist || tlrmvolist.size() == 0) {
				logger.info("====贴息利率信息多选下发SADCS022结束====,无数据");
				return re;
			}
			if (tlrmvolist != null && !tlrmvolist.isEmpty()) {
				List<String> dealer = dao.getAllDcsCode(0);
				for (String dlr : dealer) {
					Map<String, Object> outDmsDealer = dao.getDmsDealerCode(dlr);
					if (outDmsDealer.get("DMS_CODE") != null && !"".equals(outDmsDealer.get("DMS_CODE"))) {
						String dmsCode = outDmsDealer.get("DMS_CODE").toString();
						int j = sendData(tlrmvolist, dmsCode);
						if (j == 1) {
							LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
							DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String format = df.format(new Date());
							// 更新下发状态
							String[] id = array.split(",");
							for (String idd : id) {

								String id1 = idd.replaceAll("'", "");
								// TmLoanRatMaintainPO tlrmpo =
								// TmLoanRatMaintainPO.findFirst("set_Id=?,Is_Scan=?",
								// id1, 0);
								// tlrmpo.setInteger("Is_Scan", "1");
								// tlrmpo.setInteger("Send_By",
								// loginInfo.getUserId());
								// tlrmpo.setTimestamp("Send_Date", format);
								// tlrmpo.setInteger("Update_By",
								// DEConstant.DE_UPDATE_BY);
								// tlrmpo.setTimestamp("Update_Date", format);
								// tlrmpo.saveIt();
								TmLoanRatMaintainPO.update("Is_Scan=?,Send_By=?,Send_Date=?,Update_By=?,Update_Date=?",
										"Id=? and Is_Scan=?", "1", loginInfo.getUserId(), format,
										DEConstant.DE_UPDATE_BY, format, id1, 0);

							}
						} else {
							logger.error("贴息利率信息多选下发失败");
						}
					}
				}
			}

			logger.info("====贴息利率信息多选下发SADCS022结束====,下发了(" + tlrmvolist.size() + ")条数据");
			// POContext.endTxn(true);
		} catch (Exception e) {
			logger.error("贴息利率信息多选下发", e);
			re = "2";
			// POContext.endTxn(false);
		} finally {
			try {
				// POContext.cleanTxn();
			} catch (Exception e) {
				// LOG.info(e.getMessage(), e);
			}
		}
		return re;

	}

	// 单选下发

	public String sendDataEach(String id) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		logger.info("====贴息利率信息单选下发SADCS022开始====");
		// POContext.beginTxn(DBService.getInstance().getDefTxnManager(), -1);
		String re = "1";
		try {
			List<SADMS022Dto> tlrmvolist = dao.queryLoanRatMaintainInfoEach(id);
			if (null == tlrmvolist || tlrmvolist.size() == 0) {
				logger.info("====贴息利率信息单选下发SADCS022结束====,无数据");
				return re;
			}
			List<String> dealer = dao.getAllDcsCode(0);
			Map<String, Object> outDmsDealer = new HashMap<>();
			for (String dlr : dealer) {
				outDmsDealer = dao.getDmsDealerCode(dlr);
				if (outDmsDealer.get("DMS_CODE") != null && !"".equals(outDmsDealer.get("DMS_CODE"))) {
					String dmsCode = outDmsDealer.get("DMS_CODE").toString();
					if (null != tlrmvolist && tlrmvolist.size() > 0) {
						int i = sendData(tlrmvolist, dmsCode);
						if (i == 1) {
							// 更新下发状态，下发人,下发时间
							TmLoanRatMaintainPO.update("Is_Scan=?,Send_By=?,Send_Date=?,Update_By=?,Update_Date=?",
									"Id=? AND Is_Scan=?", "1", loginInfo.getUserId(), format, DEConstant.DE_UPDATE_BY,
									format, id, 0);
							logger.info("====贴息利率信息单选下发SADCS022结束====,下发了(" + tlrmvolist.size() + ")条数据");
						} else {
							logger.error("贴息利率信息单选下发失败");
						}
						// POContext.endTxn(true);
					}
				}
			}
		} catch (Exception e) {
			logger.error("贴息利率信息单选下发", e);
			re = "2";
			// POContext.endTxn(false);
		} finally {
			try {
				// POContext.cleanTxn();
			} catch (Exception e) {
				logger.info(e.getMessage(), e);
			}
		}
		return re;
	}

	@Override
	public List<SADMS022Dto> getDataList(String param) throws ServiceBizException {
		List<SADMS022Dto> tlrmvolist = new ArrayList<>();
		try {
			tlrmvolist = dao.queryLoanRatMaintainInfo();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tlrmvolist;
	}

}
