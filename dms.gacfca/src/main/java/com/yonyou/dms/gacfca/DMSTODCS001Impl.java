package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.yonyou.dms.DTO.gacfca.DMSTODCS001DTO;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 上报撞单的信息
 * @author Benzc
 * @date 2017年4月20日
 */
public class DMSTODCS001Impl implements DMSTODCS001{

	@SuppressWarnings({ "static-access", "rawtypes" })
	@Override
	public LinkedList<DMSTODCS001DTO> getDMSTODCS001(String[] cusno, String[] actiondate, String[] promresult)
			throws ServiceBizException {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		LinkedList<DMSTODCS001DTO> resultList = new LinkedList<DMSTODCS001DTO>();
		if(cusno != null && cusno.length > 0){
			for(int i=0;i<cusno.length;i++){
				if(!StringUtils.isNullOrEmpty(cusno[i]) && !StringUtils.isNullOrEmpty(actiondate[i]) && !StringUtils.isNullOrEmpty(promresult[i]) ){
					if (promresult[i].toString() != "0") {
						PotentialCusPO tpspo = new PotentialCusPO();
						List<Object> queryList = new ArrayList<Object>();
						queryList.add(dealerCode);
						queryList.add(cusno[i]);
						queryList.add(CommonConstants.D_KEY);
						List listPotentialCustomer = tpspo.findBySQL("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE=? AND CUSTOMER_NO=? AND D_KEY=?", queryList);
						PotentialCusPO selectTpspo = new PotentialCusPO();
						selectTpspo = (PotentialCusPO)listPotentialCustomer.get(0);
						if(!StringUtils.isNullOrEmpty(selectTpspo.getInteger("IS_HIT_FOLLOW_UPLOAD")) && (selectTpspo.getInteger("IS_HIT_FOLLOW_UPLOAD") == 12781002)){
							DMSTODCS001DTO dto = new DMSTODCS001DTO();
							StringBuffer sql = new StringBuffer();
							sql.append("select X.ACTION_DATE,x.EC_ORDER_NO,x.Contactor_Mobile from (select a.action_date,a.dealer_code,a.customer_no,a.d_key,b.EC_ORDER_NO,b.Contactor_Mobile, "); 
							sql.append(" ROW_NUMBER() OVER(PARTITION BY a.customer_no,a.dealer_code ORDER BY a.action_date DESC) SNID ");	
							sql.append(" from TT_SALES_PROMOTION_PLAN a left join Tm_Potential_Customer B on a.customer_no=b.customer_no ");	
							sql.append(" and a.DEALER_CODE = b.DEALER_CODE where A.PROM_RESULT IS NOT NULL AND A.PROM_RESULT <> 0) X WHERE X.SNID = 1 ");					
							sql.append(" and X.DEALER_CODE='"+dealerCode+"'" );
							sql.append(" and X.CUSTOMER_NO = '"+tpspo.getString("CUSTOMER_NO")+"'");
							sql.append(" with ur");
							List<Map> listown = DAOUtil.findAll(sql.toString(),null);
							if(listown.size() > 0){
								Map bean = listown.get(0);
								dto.setEcOrderNo((String)bean.get("EC_ORDER_NO"));
								dto.setDealerCode(dealerCode);
								dto.setTel((String)bean.get("CONTACTOR_MOBILE"));
								dto.setTrailDate((Date)bean.get("ACTION_DATE"));
								resultList.add(dto);
								if(resultList != null && resultList.size() > 0){
									//是否上报更新
									List<Object> list = new ArrayList<Object>();
									list.add(dealerCode);
									list.add(cusno[i]);
									list.add(CommonConstants.D_KEY);
									List<PotentialCusPO> list2 = PotentialCusPO.findBySQL("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE=? AND CUSTOMER_NO=? AND D_KEY=?", list);
									PotentialCusPO pcPO = (PotentialCusPO) list2.get(i);
									pcPO.setInteger("IS_HIT_FOLLOW_UPLOAD", 12781001);
									pcPO.setDate("UPDATED_AT", new Date());
									pcPO.setLong("UPDATED_BY", userId);
									pcPO.saveIt();
								}
							}
						}
					}
				}
			}
		}
		return resultList;
	}

}
