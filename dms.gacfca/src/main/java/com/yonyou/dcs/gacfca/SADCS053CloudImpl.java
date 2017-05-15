package com.yonyou.dcs.gacfca;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.LazyList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SaDcs056Dao;
import com.yonyou.dms.DTO.gacfca.BigCustomerVisitIntentDTo;
import com.yonyou.dms.DTO.gacfca.BigCustomerVisitItemDTO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerVisitDetailInfoPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerVisitInfoPO;
import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

@Service
public class SADCS053CloudImpl extends BaseCloudImpl implements SADCS053Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SADCS053CloudImpl.class);
	@Autowired
	SaDcs056Dao dao;

	@Override
	public String handleExecutor(List<BigCustomerVisitItemDTO> dto) throws Exception {
		String msg = "1";
		logger.info("====大客户周报上报开始====");
		Map<String, Object> map = null;
		String dealerCode = "";
		String visitDate = null;
		int count = 0;
		try {
			for (BigCustomerVisitItemDTO vo : dto) {
				if (count == 0) {
					map = dao.getSaDcsDealerCode(vo.getEntityCode());
					dealerCode = String.valueOf(map.get("DEALER_CODE"));// 上报经销商信息
				}
				visitDate = insertVO(vo, dealerCode);
				count++;
			}

		} catch (Exception e) {
			msg = "0";
			logger.error("大客户周报上报失败", e);
			throw new ServiceBizException(e);
		}

		// 统计拜访周次相关数据
		logger.info("**************************** 调用计算拜访周次相关数据过程 ************************");
		List<Object> iparam = new ArrayList<Object>();
		iparam.add(dealerCode);
		iparam.add(visitDate);
		// factory.callProcedure("BIG_CUSTOMER_VISIT_STATISTIC", iparam, null);
		logger.info("****************************  成功调用计算拜访周次相关数据过程 ***********************************");
		logger.info("====大客户周报上报结束====");
		return msg;
	}

	/**
	 * 写入数据
	 * 
	 * @param vo
	 */
	private String insertVO(BigCustomerVisitItemDTO vo, String dealerCode) {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		Integer week = null;
		String visitDate = null;
		try {
			logger.info("====大客户周报主信息(大客户信息)开始====");
			TtBigCustomerVisitInfoPO ttBigCustomerVisitUpdatePO = new TtBigCustomerVisitInfoPO();
			LazyList<TtBigCustomerVisitInfoPO> ttBigCustomerVisitInfoPOList = TtBigCustomerVisitInfoPO
					.findBySQL("selecr * from tt_big_customer_visit_info_dcs where Customer_No=?", vo.getCustomerNo());// 是否存在记录
			TtBigCustomerVisitInfoPO ttBigCustomerVisitInfoPO = new TtBigCustomerVisitInfoPO();
			Long vistitId = 0L;
			ttBigCustomerVisitInfoPO.setLong("VISTIT_ID", vo.getItemId());// 记录id
			ttBigCustomerVisitInfoPO.setString("DEALER_CODE", dealerCode);// code
			ttBigCustomerVisitInfoPO.setString("CUSTOMER_NAME", vo.getCustomerName());// 客户单位名称
			ttBigCustomerVisitInfoPO.setString("POLICY_TYPE", vo.getPolicyType());// 政策类型
			ttBigCustomerVisitInfoPO.setString("CUSTOMER_CONTACTS_NAME", vo.getCustomerContactsName());// 联系人姓名
			ttBigCustomerVisitInfoPO.setString("CUSTOMER_CONTACTS_POST", vo.getCustomerContactsPost());// 联系人职位
			ttBigCustomerVisitInfoPO.setString("CUSTOMER_CONTACTS_PHONE", vo.getCustomerContactsPhone());// 联系人电话
			ttBigCustomerVisitInfoPO.setInteger("Enabled", OemDictCodeConstants.STATUS_ENABLE);// 有效
			if (null != ttBigCustomerVisitInfoPOList && ttBigCustomerVisitInfoPOList.size() > 0) {

				vistitId = (Long) ttBigCustomerVisitInfoPOList.get(0).get("Vistit_Id");
				ttBigCustomerVisitInfoPO.setLong("Update_By", 11111111L);// 修改人
				ttBigCustomerVisitInfoPO.setTimestamp("UPDATE_DATE", format);// 修改时间
				ttBigCustomerVisitInfoPO.saveIt();

			} else {
				ttBigCustomerVisitInfoPO.setString("Customer_No", vo.getCustomerNo());// 客户编号
				ttBigCustomerVisitInfoPO.setLong("Create_By", 11111111L);// 创建人
				ttBigCustomerVisitInfoPO.setTimestamp("Create_Date", format);// 创建时间
				ttBigCustomerVisitInfoPO.insert();// 写入
			}
			logger.info("====大客户周报主信息(大客户信息)结束====");

			logger.info("====大客户周报主信息(大客户拜访信息)开始====");
			TtBigCustomerVisitDetailInfoPO ttBigCustomerVisitDetailInfoPO = new TtBigCustomerVisitDetailInfoPO();
			// Long detailId = new Long(SequenceManager.getSequence(""));
			// ttBigCustomerVisitDetailInfoPO.setDetailId(detailId);// 主键
			ttBigCustomerVisitDetailInfoPO.setLong("vistit_Id", vistitId);// 外键
			ttBigCustomerVisitDetailInfoPO.setString("CUSTOMER_NO", vo.getCustomerNo());// 客户编号
			ttBigCustomerVisitDetailInfoPO.setString("PRIOR_GRADE", new Long(vo.getPriorGrade()));// 拜访前级别
			ttBigCustomerVisitDetailInfoPO.setString("NEXT_GRADE", new Long(vo.getNextGrade()));// 拜访后级别
			ttBigCustomerVisitDetailInfoPO.setTimestamp("VISIT_DATE", vo.getVisitDate());// 拜访日期
			week = findVisitWeekByVisitDate(vo.getVisitDate());

			if (null != vo.getVisitDate() && !"".equals(vo.getVisitDate())) {
				SimpleDateFormat simp = new SimpleDateFormat("yyyy-MM-dd");
				visitDate = simp.format(vo.getVisitDate());
			}
			ttBigCustomerVisitDetailInfoPO.setString("Visit_Week", week);// 拜访日期所在的周
			ttBigCustomerVisitDetailInfoPO.setTimestamp("Build_Date", format);// 填报日期
			List<BigCustomerVisitIntentDTo> intentList = vo.getBigCustomerVisitIntentList();
			if (null != intentList && intentList.size() > 0) {

				String appendIntentName = "";
				Integer purchaseCount = 0;// 采购数量
				Integer intentBuyTime = 0;// 预计采购时间段
				String competitorBrand = "";// 竞争品牌
				BigCustomerVisitIntentDTo intentVO = null;
				for (int i = 0; i < intentList.size(); i++) {
					String customerIntentName = "";// 意向车型
					intentVO = intentList.get(i);
					if (this.findBrandNameByCode(intentVO.getIntentBrand()).length() != 0) {
						customerIntentName += this.findBrandNameByCode(intentVO.getIntentBrand()) + ",";// 品牌
					}
					if (this.findModelNameByCode(intentVO.getIntentModel()).length() != 0) {
						customerIntentName += this.findModelNameByCode(intentVO.getIntentModel()) + ",";// 车型
					}
					if (this.findConfigNameByCode(intentVO.getIntentConfig()).length() != 0) {
						customerIntentName += this.findConfigNameByCode(intentVO.getIntentConfig()) + ",";// 配置
					}
					if (this.findColorNameByCode(intentVO.getIntentColor()).length() != 0) {
						customerIntentName += this.findColorNameByCode(intentVO.getIntentColor()) + ",";// 颜色
					}
					// 组装品牌,车系,车型，配置，颜色
					if (!"".equals(customerIntentName)) {
						if (customerIntentName.split(",").length != 0) {
							customerIntentName = customerIntentName.substring(0, customerIntentName.length() - 1);
							appendIntentName += customerIntentName + " ";
						}
					}

					if (null != intentVO.getPurchaseCount()) {
						purchaseCount += intentVO.getPurchaseCount();
					}
					if (i == 0) {
						intentBuyTime = intentVO.getIntentBuyTime();
						competitorBrand = intentVO.getCompetitorBrand();
					}
				}
				// 意向车型多个""
				ttBigCustomerVisitDetailInfoPO.setString("CUSTOMER_INTENT_MODEL", appendIntentName);
				ttBigCustomerVisitDetailInfoPO.setString("PURCHASE_COUNT", purchaseCount);// 采购数量
				ttBigCustomerVisitDetailInfoPO.setTimestamp("EXPECTED_PURCHASE_DATE", intentBuyTime);// 预计采购时间段
				ttBigCustomerVisitDetailInfoPO.setString("COMPETITION_BRAND", competitorBrand);// 竞争品牌
				ttBigCustomerVisitDetailInfoPO.setString("VISIT_SUMMARY", vo.getVisitSummary());// 拜访小结
				ttBigCustomerVisitDetailInfoPO.setLong("CREATE_BY", 11111111L);// 创建人
				ttBigCustomerVisitDetailInfoPO.setTimestamp("CREATE_DATE", format);// 创建时间
				ttBigCustomerVisitDetailInfoPO.setString("DEALER_CODE", dealerCode);// 经销商code
			}
			ttBigCustomerVisitDetailInfoPO.insert();
			logger.info("====大客户周报主信息(大客户拜访信息)结束====");

		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException(e);
		}
		return visitDate;

	}

	/**
	 * 通过颜色code 找到name
	 * 
	 * @param intentBrand
	 * @return
	 */
	private String findColorNameByCode(String colorCode) {

		String colorName = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT COLOR_NAME --车型的名字  \n");
		sql.append("FROM (" + OemBaseDAO.getVwMaterialSql() + ")v \n");
		sql.append("WHERE  COLOR_CODE  = '" + colorCode + "'");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if (null != list && list.size() > 0) {
			for (Map map : list) {
				colorName = map.get("COLOR_NAME").toString();
			}

		}
		return colorName;

	}

	/**
	 * 配置
	 */
	private String findConfigNameByCode(String configCode) {

		String configName = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT MATERIAL_NAME --车型的名字  \n");
		sql.append("FROM (" + OemBaseDAO.getVwMaterialSql() + ")v \n");
		sql.append("WHERE  v.MATERIAL_CODE  = '" + configCode + "'");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		String modelName = "";
		if (null != list && list.size() > 0) {
			for (Map map : list) {
				modelName = map.get("MATERIAL_NAME").toString();
			}

		}
		return configName;

	}

	/**
	 * 通过车型code 找到name
	 * 
	 * @param intentBrand
	 * @return
	 */
	private String findModelNameByCode(String modelCode) {

		String modelName = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT MODEL_NAME --车型的名字  \n");
		sql.append("FROM (" + OemBaseDAO.getVwMaterialSql() + ")v \n");
		sql.append("WHERE  v.MODEL_CODE  = '" + modelCode + "'");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);

		if (null != list && list.size() > 0) {
			for (Map map : list) {
				modelName = map.get("MODEL_NAME").toString();
			}
		}
		return modelName;

	}

	private String findBrandNameByCode(String brandCode) {

		String brandName = "";
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT DISTINCT BRAND_NAME --品牌的名字  \n");
		sql.append("FROM (" + OemBaseDAO.getVwMaterialSql() + ")v \n");
		sql.append("WHERE v. BRAND_CODE  = '" + brandCode + "'");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);
		if (null != list && list.size() > 0) {
			for (Map map : list) {
				brandName = (String) map.get("BRAND_NAME");
			}
		}
		return brandName;

	}

	private Integer findVisitWeekByVisitDate(Date visitDate) {

		Integer visitWeek = 0;
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT WEEK_CODE   -- 周\n");
		sql.append("FROM TM_WEEK \n");
		sql.append("WHERE  date_format(START_DATE,'%y-%m-%d')  <= '" + visitDate + "'");
		sql.append(" AND date_format(START_DATE,'%y-%m-%d')  >= '" + visitDate + "'");
		List<Map> list = OemDAOUtil.findAll(sql.toString(), null);

		if (null != list && list.size() > 0) {
			for (Map map : list) {
				visitWeek = (Integer) map.get("WEEK_CODE");
				return visitWeek;
			}
		}
		return visitWeek;

	}

}
