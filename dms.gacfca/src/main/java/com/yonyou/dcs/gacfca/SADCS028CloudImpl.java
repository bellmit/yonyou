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

import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.DTO.gacfca.WXBindingDTO;
import com.yonyou.dms.common.domains.PO.customer.TtCustomerBindPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS028CloudImpl extends BaseCloudImpl implements SADCS028Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS026CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	public String handleExecutor(List<WXBindingDTO> dto) throws Exception {
		String msg = "1";
		logger.info("##############SADMS028当月进厂客户微信绑定率统计报表数据接受开始#################");
		for (WXBindingDTO vo : dto) {
			try {
				insertVo(vo);
			} catch (Exception e) {
				logger.error("当月进厂客户微信绑定率统计报表数据上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====SADMS028当月进厂客户微信绑定率统计报表数据接受结束====");
		return msg;
	}

	/**
	 * 当月进厂客户微信绑定率统计报表数据操作
	 * 
	 * @param vo
	 * @throws Exception
	 */
	private void insertVo(WXBindingDTO vo) {

		try {
			Map<String, Object> map = dao.getSeDcsDealerCode(vo.getEntityCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息

			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = df.format(new Date());

			if (null != findById(vo.getNowYear().toString(), vo.getNowMonth().toString(), dealerCode)) {
				Long updatedocumentId = findById(vo.getNowYear().toString(), vo.getNowMonth().toString(), dealerCode);
				TtCustomerBindPO po1 = TtCustomerBindPO.findFirst("CUSTOMER_ID=?", updatedocumentId);
				po1.setString("DEALER_CODE", dealerCode);// 经销商代码
				po1.setInteger("YEAR", vo.getNowYear());// 年
				po1.setInteger("MONTH", vo.getNowMonth());// 月
				po1.setInteger("INNOTBINDINGNUM", vo.getInNotBindingNum());// 当月进站客户进站时未绑定的总数
				po1.setInteger("OUTBINDINGNUM", vo.getOutBindingNum());// 当月进站未绑定客户截止月底的绑定总数
				po1.setDouble("BINDINGRATE", Double.parseDouble(vo.getBindingRate()));// 微信绑定率
				po1.setInteger("INNUM", vo.getInNum());// 进厂台次
				po1.setInteger("INCUSTOMERNUM", vo.getInCustomerNum());// 进厂客户数
				po1.setLong("CUSTOMER_ID", updatedocumentId);
				po1.setLong("UPDATE_BY", 11111111L);// 修改人
				po1.setTimestamp("UPDATE_DATE", format);// 修改日期
				po1.saveIt();

			} else {
				TtCustomerBindPO po = new TtCustomerBindPO();
				po.setString("DEALER_CODE", dealerCode);// 经销商代码
				po.setInteger("YEAR", vo.getNowYear());// 年
				po.setInteger("MONTH", vo.getNowMonth());// 月
				po.setInteger("INNOTBINDINGNUM", vo.getInNotBindingNum());// 当月进站客户进站时未绑定的总数
				po.setInteger("OUTBINDINGNUM", vo.getOutBindingNum());// 当月进站未绑定客户截止月底的绑定总数
				po.setDouble("BINDINGRATE", Double.parseDouble(vo.getBindingRate()));// 微信绑定率
				po.setInteger("INNUM", vo.getInNum());// 进厂台次
				po.setInteger("INCUSTOMERNUM", vo.getInCustomerNum());// 进厂客户数
				po.setLong("Create_By", 11111111L);// 创建人
				po.setTimestamp("CREATE_DATE", format);// 创建日期
				po.insert();
			}
		} catch (Exception e) {
			logger.error("当月进厂客户微信绑定率统计报表数据上报接收失败", e);
			throw new ServiceBizException(e);
		}

	}

	/**
	 * 查询当前数据是否存在
	 * 
	 * @param po
	 * @return
	 * @throws Exception
	 */
	private Long findById(String year, String month, String dealercode) {

		Long documentId = 0L;
		try {
			String sql = new String(" SELECT TCB.CUSTOMER_ID FROM TT_CUSTOMER_BIND_DCS TCB WHERE TCB.DEALER_CODE = '"
					+ dealercode + "' AND TCB.YEAR = " + year + " AND TCB.MONTH = " + month + " ");
			List<Map> list = OemDAOUtil.findAll(sql, null);
			if (null != list && list.size() > 0) {
				for (Map map : list) {
					documentId = (Long) map.get("CUSTOMER_ID");
				}
				return documentId;
			}
		} catch (Exception e) {
			logger.error("当月进厂客户微信绑定率统计报表数据上报校验数据失败", e);
			throw new ServiceBizException(e);
		}
		return null;

	}

}
