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

import com.yonyou.dcs.dao.SADCS033DTO;
import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.common.domains.PO.basedata.TtNewPaycarVerifyPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS033CloudImpl extends BaseCloudImpl implements SADCS033Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS033CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	@Override
	public String handleExecutor(List<SADCS033DTO> dto) throws Exception {
		String msg = "1";
		logger.info("====车主信息核实固化月报表上报接收开始====");
		for (SADCS033DTO vo : dto) {
			try {
				insertVo(vo);
			} catch (Exception e) {
				logger.error("车主信息核实固化月报表上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====车主信息核实固化月报表上报接收结束====");
		return msg;
	}

	/**
	 * 写入数据 - 车主信息核实固化月报表上报接收
	 * 
	 * @param vo
	 * @throws Exception
	 */
	private void insertVo(SADCS033DTO vo) {

		logger.info("====车主信息核实固化月报表数据上报接收开始====");
		try {
			Map<String, Object> map = dao.getSeDcsDealerCode(vo.getDealerCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
			DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String format = df.format(new Date());

			if (null != findById(dealerCode, vo.getParcarDate())) {
				Long updatedocumentId = findById(dealerCode, vo.getParcarDate());
				TtNewPaycarVerifyPO updatePo = new TtNewPaycarVerifyPO();
				TtNewPaycarVerifyPO po = TtNewPaycarVerifyPO.findFirst("ID=?", updatedocumentId);
				po.setString("DEALER_CODE", dealerCode); // 经销商代码
				po.setDouble("PAYCAR_DATE", vo.getBindingRate()); // 绑定率
				po.setString("BUSY", vo.getBusy()); // 占线/停机
				po.setString("ISBINDINGSTANDARDS", vo.getIsBindingStandards()); // 绑定是否达标
				po.setString("ISSTANDARDS", vo.getIsStandards()); // 核实是否达标
				po.setInteger("MONTHBINDINGNUM", vo.getMonthBindingNum()); // 当月绑定数
				po.setInteger("MONTHDMSPAYCARNUM", vo.getMonthDmsPayCarNum()); // 当月DMS交车数
				po.setString("NOTCAROWNER", vo.getNotCarOwner()); // 非车主
				po.setString("NOTOWNER", vo.getNotOwner()); // 非机主
				po.setInteger("NULLNUM", vo.getNullNum()); // 空号/错号
				po.setTimestamp("PAYCAR_DATE", vo.getParcarDate()); // 上报日期
				po.setString("BIZ_YEAR", vo.getBizYear()); // 业务年
				po.setString("BIZ_MONTH", vo.getBizMonth()); // 业务月
				po.setInteger("VERIFYFAILURENUM", vo.getVerifyFailureNum()); // 核实失败数
				po.setInteger("VERIFYSUCCESSNUM", vo.getVerifySuccessNum()); // 核实成功数
				po.setDouble("VERIFYPASSRATE", vo.getVerifyPassRate()); // 核实通过率
				po.setLong("Update_By", 11111111L);// 修改人
				po.setTimestamp("Update_Date", format);// 修改日期
				po.saveIt();
			} else {
				TtNewPaycarVerifyPO po = new TtNewPaycarVerifyPO();
				po.setString("DEALER_CODE", dealerCode); // 经销商代码
				po.setDouble("PAYCAR_DATE", vo.getBindingRate()); // 绑定率
				po.setString("BUSY", vo.getBusy()); // 占线/停机
				po.setString("ISBINDINGSTANDARDS", vo.getIsBindingStandards()); // 绑定是否达标
				po.setString("ISSTANDARDS", vo.getIsStandards()); // 核实是否达标
				po.setInteger("MONTHBINDINGNUM", vo.getMonthBindingNum()); // 当月绑定数
				po.setInteger("MONTHDMSPAYCARNUM", vo.getMonthDmsPayCarNum()); // 当月DMS交车数
				po.setString("NOTCAROWNER", vo.getNotCarOwner()); // 非车主
				po.setString("NOTOWNER", vo.getNotOwner()); // 非机主
				po.setInteger("NULLNUM", vo.getNullNum()); // 空号/错号
				po.setTimestamp("PAYCAR_DATE", vo.getParcarDate()); // 上报日期
				po.setString("BIZ_YEAR", vo.getBizYear()); // 业务年
				po.setString("BIZ_MONTH", vo.getBizMonth()); // 业务月
				po.setInteger("VERIFYFAILURENUM", vo.getVerifyFailureNum()); // 核实失败数
				po.setInteger("VERIFYSUCCESSNUM", vo.getVerifySuccessNum()); // 核实成功数
				po.setDouble("VERIFYPASSRATE", vo.getVerifyPassRate()); // 核实通过率
				po.setLong("Create_By", 11111111L);// 创建人
				po.setTimestamp("Create_Date", format);// 创建日期
				po.insert();
			}
			logger.info("====车主信息核实固化月报表数据上报接收结束====");
		} catch (Exception e) {
			logger.error("车主信息核实固化月报表数据上报接收失败", e);
			throw new ServiceBizException(e);
		}

	}

	/**
	 * 查询当前数据是否存在
	 * 
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	private Long findById(String dealerCode, Date parcarDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		SimpleDateFormat sdf1 = new SimpleDateFormat("MM");
		Long documentId = 0L;
		try {
			String sql = new String(" SELECT ID FROM TT_NEW_PAYCAR_VERIFY T WHERE DEALER_CODE = '" + dealerCode
					+ "' AND TO_NUMBER(BIZ_YEAR) = TO_NUMBER('" + sdf.format(parcarDate)
					+ "') AND TO_NUMBER(BIZ_MONTH)  = TO_NUMBER(' " + sdf1.format(parcarDate) + "')  ");
			List<Map> list = OemDAOUtil.findAll(sql, null);
			if (null != list && list.size() > 0) {
				for (Map map : list) {
					documentId = (Long) map.get("ID");
				}
				return documentId;
			}
		} catch (Exception e) {
			logger.error("车主信息核实固化月报表数据上报校验数据失败", e);
			throw new ServiceBizException(e);
		}
		return null;

	}

}
