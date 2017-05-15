package com.yonyou.dms.schedule.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtBigCustomerVisitingIntentPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPoCusLinkmanPO;
import com.yonyou.dms.common.domains.PO.basedata.TtSalesPromotionPlanPO;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.schedule.domains.DTO.BigCustomerVisitIntentDto;
import com.yonyou.dms.schedule.domains.DTO.BigCustomerVisitItemDto;
/**
 * 计划任务每周一上报大客户拜访信息 接口实现类
 * 计划任务
 * @author wangliang
 * @date 2017年2月22日
 */
@Service
@SuppressWarnings({ "unchecked", "rawtypes" })
public class SADMS053ServiceImpl implements SADMS053Service {

	@Override
	public LinkedList<BigCustomerVisitItemDto> getSADMS053()  {

		try {
			
			
			LinkedList<BigCustomerVisitItemDto> resultList = new LinkedList<BigCustomerVisitItemDto>();
			Date date = new Date();
			Calendar calm = Calendar.getInstance();
			calm.setTime(date); // 计划任务周五的凌晨执行 >= beigin < end
			
			/*if (calm.get(Calendar.DAY_OF_WEEK) != 2) {// 1是周日 5是周四
				//return null;
			}*/
			
			String endDate = new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString() + " 23:59:59 ";
			calm.add(Calendar.DATE, -7);
			String beginDate = new SimpleDateFormat("yyyy-MM-dd").format(calm.getTime()).toString() + " 00:00:00 ";
			
			
			List<TtSalesPromotionPlanPO> itemList = queryBigCustomerSalesPromotionPlan(beginDate,endDate);
			
			if (itemList != null && itemList.size() > 0) {
				for (int i = 0; i < itemList.size(); i++) {
					BigCustomerVisitItemDto dto = new BigCustomerVisitItemDto();
					TtSalesPromotionPlanPO po = new TtSalesPromotionPlanPO();
					po =  (TtSalesPromotionPlanPO) itemList.get(i);;
					dto.setItemId(po.getLong("ITEM_ID"));
					dto.setDealerCode(po.getString("DEALER_CODE"));
					dto.setCustomerNo(po.getString("CUSTOMER_NO"));
					dto.setNextGrade(po.getInteger("NEXT_GRADE") + "");
					dto.setPriorGrade(po.getInteger("PRIOR_GRADE") + "");
					dto.setVisitDate(po.getDate("ACTION_DATE"));
					dto.setVisitSummary(po.getString("SCENE"));
					
					
					/*
					 * // 获取潜客信息
					 * TtSalesPromotionPlanPO tpc = new TtSalesPromotionPlanPO();
					 * tpc.setString("CUSTOMER_NO",
					 * po.getString("CUSTOMER_NO"));
					 * tpc.setString("DEALER_CODE",
					 * po.getString("DEALER_CODE")); tpc.setInteger("D_KEY",
					 * CommonConstants.D_KEY);
					 */
					List<TtSalesPromotionPlanPO> listcus = TtSalesPromotionPlanPO.findBySQL(
							"SELECT * FROM tt_sales_promotion_plan WHERE CUSTOMER_NO = ? AND D_KEY = ? ",
							new Object[] { po.getString("CUSTOMER_NO"), CommonConstants.D_KEY });
					if (listcus != null && listcus.size() > 0) {
						dto.setPolicyType(listcus.get(0).getInteger("KA_TYPE"));
						dto.setCustomerName(listcus.get(0).getString("CUSTOMER_NAME"));
						
						/*
						 * TtPoCusLinkmanPO ltpc = new TtPoCusLinkmanPO();
						 * ltpc.setString("CUSTOMER_NO",
						 * po.getString("CUSTOMER_NO"));
						 * ltpc.setString("DEALER_CODE",
						 * po.getString("DEALER_CODE")); ltpc.setString("D_KEY",
						 * CommonConstants.D_KEY);
						 * ltpc.setString("IS_DEFAULT_CONTACTOR", 12781001);
						 */
						List<TtPoCusLinkmanPO> listlinkman = TtPoCusLinkmanPO.findBySQL(
								"SELECT * FROM Tt_Po_Cus_Linkman WHERE CUSTOMER_NO = ? AND D_KEY = ? AND IS_DEFAULT_CONTACTOR = ? ",
								new Object[] { po.getString("CUSTOMER_NO"), CommonConstants.D_KEY, 12781001 });

						if (listlinkman != null && listlinkman.size() > 0) {
							dto.setCustomerContactsName(listlinkman.get(0).getString("CONTACTOR_NAME"));
							if (listlinkman.get(0).getString("MOBILE") != null && !"".equals(listlinkman))
								dto.setCustomerContactsPhone(listlinkman.get(0).getString("MOBILE"));
							else
								dto.setCustomerContactsPhone(listlinkman.get(0).getString("PHONE"));
							dto.setCustomerContactsPost(listlinkman.get(0).getString("POSITION_NAME"));
						}
					}
					// 获取意向明细
					LinkedList<BigCustomerVisitIntentDto> resultDetailList = new LinkedList<BigCustomerVisitIntentDto>();
					List<TtBigCustomerVisitingIntentPO> intentList = TtBigCustomerVisitingIntentPO.findBySQL(
							"SELECT * FROM Tt_Big_Customer_Visiting_Intent WHERE ITEM_ID = ? ", po.getLong("ITEM_ID"));
					for (int j = 0; j < intentList.size(); j++) {
						BigCustomerVisitIntentDto idto = new BigCustomerVisitIntentDto();
						TtBigCustomerVisitingIntentPO ipo = new TtBigCustomerVisitingIntentPO();
						ipo = intentList.get(j);
						idto.setDealerCode(ipo.getString("DEALER_CODE"));
						idto.setIntentBrand(ipo.getString("INTENT_BRAND"));
						idto.setIntentSeries(ipo.getString("INTENT_SERIES"));
						idto.setIntentModel(ipo.getString("INTENT_MODEL"));
						idto.setIntentProduct(ipo.getString("INTENT_PRODUCT"));
						idto.setIntentColor(ipo.getString("INTENT_COLOR"));
						idto.setItemId(ipo.getLong("ITEM_ID"));
						idto.setCompetitorBrand(ipo.getString("COMPETITOR_BRAND"));
						idto.setPurchaseCount(ipo.getInteger("PURCHASE_COUNT"));
						idto.setIntentBuyTime(ipo.getInteger("INTENDING_BUY_TIME"));
						resultDetailList.add(idto);
					}
					dto.setBigCustomerVisitIntentDtoList(resultDetailList);
					resultList.add(dto);
				}
				return resultList;
			}
			return null;
		} catch (Exception e) {
			return null;
		}
	}
	
	
	public List<TtSalesPromotionPlanPO> queryBigCustomerSalesPromotionPlan(String beginDate,String endDate) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT   * FROM TT_SALES_PROMOTION_PLAN a LEFT JOIN TM_POTENTIAL_CUSTOMER b ON a.dealer_code=b.dealer_code AND a.CUSTOMER_NO=b.CUSTOMER_NO ");
		sb.append(" WHERE a.ACTION_DATE>= ? AND a.ACTION_DATE<=  ? ");
		sb.append(" AND a.PROM_RESULT IS NOT NULL AND a.PROM_RESULT!=0 ");
		sb.append(" AND a.IS_BIG_CUSTOMER_PLAN="+CommonConstants.DICT_IS_YES+" ");
		sb.append(" AND b.IS_BIG_CUSTOMER=12781001 ");
		System.out.println("*********************");
		System.out.println(sb.toString());
		System.out.println("*********************");
		List<TtSalesPromotionPlanPO> list = TtSalesPromotionPlanPO.findBySQL(sb.toString(),new Object[]{beginDate,endDate});
		return list;
	} 
	
	
	
}
