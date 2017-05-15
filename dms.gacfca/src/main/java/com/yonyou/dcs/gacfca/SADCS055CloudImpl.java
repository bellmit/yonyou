package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.DeCommonDao;
import com.yonyou.dcs.dao.SADCS055Dao;
import com.yonyou.dms.DTO.gacfca.TtActivityResultDto;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.gacfca.SEDMS055;

@Service
public class SADCS055CloudImpl extends BaseCloudImpl implements SADCS055Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS055CloudImpl.class);

	@Autowired
	SADCS055Dao dao;
	@Autowired
	SEDMS055 sedms055;
	@Autowired
	DeCommonDao deCommonDao;

	@Override
	public String execute() throws ServiceBizException {
		logger.info("================服务活动下发下发执行开始（SADCS055CulodImpl）====================");
		List<TtActivityResultDto> vos = getDataList();
		List<String> dealer = dao.getAllDcsCode(0);
		for (String dlr : dealer) {
			Map<String, Object> dmsDealer = deCommonDao.getDmsDealerCode(dlr);
			if ((!StringUtils.isNullOrEmpty(dmsDealer.get("DMS_CODE"))) && vos != null && vos.size() > 0) {

				sendData(dmsDealer.get("DMS_CODE").toString(), vos);

			}
		}
		logger.info("================服务活动下发执行结束（SADCS055CulodImpl）====================");
		return null;
	}

	private void sendData(String dmsCode, List<TtActivityResultDto> vos) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		StringBuffer vinStr = new StringBuffer();
		StringBuffer updateSql = new StringBuffer(
				" UPDATE tt_wr_activity_vehicle_complete_dcs SET DOWN_STATUS=1 , UPDATE_DATE=now() WHERE 1=1 ");
		try {

			if (null == vos || vos.size() <= 0) {
				logger.info("====SADCS055服务活动下发====,无数据！ ");
			} else {

				int tempLenth = 1000;// 容器大小
				if (vos.size() > tempLenth) {
					List<TtActivityResultDto> tempList = new ArrayList<TtActivityResultDto>();// 临时数据容器
					// 每次1000
					dbService();
					for (int i = 0, j = vos.size(); i < j; i++) {
						tempList.add(vos.get(i));
						vinStr.append("'" + vos.get(i).getVin() + "',");
						if (i == vos.size() - 1 || (i + 1) % tempLenth == 0) {
							try {
								int flag = sedms055.getSEDMS055(tempList, dmsCode);
								if (flag == 1) {
									logger.info("================服务活动下发成功（SADCS055CulodImpl）====================");
									// 下发成功之后 把状态修改成已下发
									updateSql.append(
											" AND VIN IN(" + vinStr.toString().substring(0, vinStr.length() - 1) + ")");
									OemDAOUtil.execBatchPreparement(updateSql.toString(), new ArrayList<>());

									dbService.endTxn(true);
									Base.detach();
									dbService.clean(); // 清理事务
									beginDbService();

								} else {
									logger.info("================服务活动下发失败（SADCS055CulodImpl）====================");
								}

							} catch (Exception e) {
								logger.error("SADCS055服务活动下发失败", e);
								throw new Exception(e);
							}
							logger.info(
									"#########SADCS055服务活动下发:,下发了(" + tempList.size() + ")条数据#####################");
							vinStr.setLength(0);
							tempList.clear();
						}
					}
				} else {
					try {
						int flag = sedms055.getSEDMS055(vos, dmsCode);
						List<TtActivityResultDto> tempList = new ArrayList<TtActivityResultDto>();// 临时数据容器
						// 每次1000
						for (int i = 0, j = vos.size(); i < j; i++) {
							tempList.add(vos.get(i));
							vinStr.append("'" + vos.get(i).getVin() + "',");
						}
						if (flag == 1) {
							updateSql
									.append(" AND VIN IN(" + vinStr.toString().substring(0, vinStr.length() - 1) + ")");
							OemDAOUtil.execBatchPreparement(updateSql.toString(), new ArrayList<>());
							logger.info("#########SADCS055服务活动下发:,成功下发了(" + vos.size() + ")条数据#####################");
						}
					} catch (Exception e) {
						logger.error("SADCS055服务活动下发失败", e);
						throw new Exception(e);
					}
				}
			}

		} catch (Exception e) {
			logger.info("================服务活动下发下发异常（SADMS065）====================");
		}

	}

	@Override
	public List<TtActivityResultDto> getDataList() throws ServiceBizException {
		List<TtActivityResultDto> vos = dao.queryTtActivityResultVO();
		return vos;
	}

}
