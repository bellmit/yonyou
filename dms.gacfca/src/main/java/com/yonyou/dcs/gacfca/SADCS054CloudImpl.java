package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.javalite.activejdbc.Model;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.DTO.gacfca.SADMS054DTO;
import com.yonyou.dms.common.domains.PO.basedata.TtMysteriousDateDownPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.f4.common.database.DBService;

@Service
public class SADCS054CloudImpl implements SADCS054Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS054CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	@Autowired
	DBService dbService;

	@Override
	public String handleExecutor(List<SADMS054DTO> dto) throws Exception {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
		String format = df.format(new Date());
		String msg = "1";
		logger.info("====克莱斯勒明检和神秘上报开始====");
		try { // 开启事物
			dbService.beginTxn();
			for (SADMS054DTO vo : dto) {
				Map<String, Object> dcsInfoMap = dao.getSaDcsDealerCode(vo.getEntityCode());
				if (dcsInfoMap == null) {
					logger.error("DCS没有【" + vo.getEntityCode() + "】经销商信息！");
					throw new ServiceBizException("DCS没有【" + vo.getEntityCode() + "】经销商信息！");
				}
				// 上端的
				String dealerCode = CommonUtils.checkNull(dcsInfoMap.get("DEALER_CODE"));
				LazyList<Model> listTmddPO = TtMysteriousDateDownPO.findBySQL(
						"select * from tt_mysterious_date_down where DEALER_CODE=? and PHONE=?", dealerCode,
						vo.getPhone());
				if (listTmddPO == null) {
					throw new ServiceBizException("DCS没有下发克莱斯勒明检和神秘客【" + vo.getEntityCode() + "】经销商信息！");
				}
				TtMysteriousDateDownPO updatePO = new TtMysteriousDateDownPO();
				Integer isInputDms = vo.getIsInputDms();
				if (null != isInputDms) {
					if (isInputDms == 12781001) {
						updatePO.setString("IS_INPUT_DMS", OemDictCodeConstants.IS_INPUT_DMS_01);// 是录入DMS
					} else if (isInputDms == 12781002) {
						updatePO.setString("IS_INPUT_DMS", OemDictCodeConstants.IS_INPUT_DMS_02);// 否录入DMS
					}
				}
				updatePO.setString("INPUT_PHONE", vo.getInputPhone());// 录入电话
				updatePO.setString("INPUT_NAME", vo.getInputName());// 录入姓名
				updatePO.setTimestamp("INPUT_DATE", vo.getInputDate());// 录入时间
				updatePO.setLong("UPDATE_BY", 1111111L);// 修改人
				updatePO.setTimestamp("UPDATE_DATE", format);// 修改时间
				updatePO.saveIt();

			}
		} catch (Exception e) {
			msg = "0";
			logger.error("SADCS054克莱斯勒明检和神秘上报开始异常", e);
			throw new ServiceBizException(e);
		}
		logger.info("====克莱斯勒明检和神秘上报结束====");
		return msg;
	}

}
