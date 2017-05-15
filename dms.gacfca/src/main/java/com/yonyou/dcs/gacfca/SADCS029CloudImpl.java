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
import com.yonyou.dms.DTO.gacfca.SADCS029DTO;
import com.yonyou.dms.common.domains.PO.basedata.TtPartCeilingPriceReportPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS029CloudImpl implements SADCS029Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS026CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	@Override
	public String handleExecutor(List<SADCS029DTO> dto) throws Exception {
		String msg = "1";
		logger.info("====总部监控经销商调价报表上报接收开始====");
		for (SADCS029DTO vo : dto) {
			try {
				insertVo(vo);
			} catch (Exception e) {
				logger.error("总部监控经销商调价报表上报接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====总部监控经销商调价报表上报接收结束====");
		return msg;
	}

	/**
	 * 写入数据 - 总部监控经销商调价报表数据上报接收
	 * 
	 * @param vo
	 * @throws Exception
	 */
	private void insertVo(SADCS029DTO vo) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());

		logger.info("====总部监控经销商调价报表数据上报接收开始====");
		try {
			Map<String, Object> map = dao.getSeDcsDealerCode(vo.getEntityCode());
			String dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息

			Date adjustDate = vo.getAdjustDate();
			if (null != findById(dealerCode, vo.getPartCode().toString(), adjustDate)) {
				Long updatedocumentId = findById(dealerCode, vo.getPartCode().toString(), adjustDate);
				TtPartCeilingPriceReportPO po = TtPartCeilingPriceReportPO.findFirst("PRICE_ID", updatedocumentId);
				po.setString("DEALER_CODE", dealerCode);// 经销商代码
				po.setString("PART_CODE", vo.getPartCode());// 配件代码
				po.setString("PART_NAME", vo.getPartName());// 配件名称
				po.setTimestamp("ADJUST_DATE", vo.getAdjustDate()); // 调整时间
				po.setInteger("NOW_CEILING_PRICE", vo.getNowCeilingPrice()); // 现销售限价
				po.setInteger("OLD_CEILING_PRICE", vo.getOldCeilingPrice()); // 原销售限价

				po.setLong("UPDATE_BY", 11111111L);// 修改人
				po.setTimestamp("UPDATE_DATE", new Date());// 修改日期
				po.saveIt();
			} else {
				TtPartCeilingPriceReportPO po = new TtPartCeilingPriceReportPO();
				po.setString("DEALER_CODE", dealerCode);// 经销商代码
				po.setString("PART_CODE", vo.getPartCode());// 配件代码
				po.setString("PART_NAME", vo.getPartName());// 配件名称
				po.setTimestamp("ADJUST_DATE", vo.getAdjustDate()); // 调整时间
				po.setInteger("NOW_CEILING_PRICE", vo.getNowCeilingPrice()); // 现销售限价
				po.setInteger("OLD_CEILING_PRICE", vo.getOldCeilingPrice()); // 原销售限价
				po.setLong("CREATE_BY", 11111111L);// 创建人
				po.setTimestamp("CREATE_DATE", new Date());// 创建日期
				po.insert();
			}
			logger.info("====总部监控经销商调价报表数据上报接收结束====");
		} catch (Exception e) {
			logger.error("总部监控经销商调价报表数据上报接收失败", e);
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
	private Long findById(String dealerCode, String partCode, Date adjustDate) {

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-mm-dd");
		Long documentId = 0L;
		try {
			String sql = new String(" SELECT PRICE_ID FROM TT_PART_CEILING_PRICE_REPORT T WHERE T.DEALER_CODE = '"
					+ dealerCode + "'" + " AND T.PART_CODE = '" + partCode + "'"
					+ " AND date_format(T.ADJUST_DATE , '%Y-%M-%D') = '" + sdf.format(adjustDate) + "' ");
			List<Map> list = OemDAOUtil.findAll(sql, null);

			if (null != list && list.size() > 0) {
				for (Map map : list) {
					documentId = (Long) map.get("PRICE_ID");
				}
				return documentId;

			}
		} catch (Exception e) {
			logger.error("总部监控经销商调价报表数据上报校验数据失败", e);
			throw new ServiceBizException(e);
		}
		return null;

	}

}
