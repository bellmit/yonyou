package com.yonyou.dms.gacfca;

import java.util.LinkedList;
import java.util.List;

import org.javalite.activejdbc.Base;

import com.yonyou.dms.DTO.gacfca.TiDmsUCustomerStatusDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;


/**
 * 更新客户信息(休眠，订单，交车客户状态数据传递)DMS更新 接口显现类
 * 休眠NF
 * @author wangliang
 * @date 2017年2月24日
 */
public class SOTDCS015NFImpl implements SOTDCS015NF{

	@SuppressWarnings("unused")
	@Override
	public LinkedList<TiDmsUCustomerStatusDto> getSOTDCS015NF() {
		String customerNo = null;
		String auditResult = null;
		try {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		Long userId = FrameworkUtil.getLoginInfo().getUserId();
		
		
		if(customerNo != null && "".equals(customerNo) && auditResult != null && "Y".equals(auditResult)) {
			PotentialCusPO po = new PotentialCusPO();
			po.setString("DEALER_CODE", dealerCode);
			po.setInteger("D_KEY", CommonConstants.D_KEY);
			List<PotentialCusPO> list = PotentialCusPO.findBySQL("SELECT * FROM TM_POTENTIAL_CUSTOMER WHERE DEALER_CODE = ? AND D_KEY = ? ", new Object[]{dealerCode,CommonConstants.D_KEY});
			po = list.get(0); 
			if(po != null) {
				//改成F级并且上报的客户 要把未跟进记录删除才行
				deleteFcustomerAction(dealerCode,customerNo);
				LinkedList<TiDmsUCustomerStatusDto> resultList = new LinkedList<TiDmsUCustomerStatusDto>();
				TiDmsUCustomerStatusDto dto = new TiDmsUCustomerStatusDto();
				dto.setDealerCode(dealerCode);
				dto.setUniquenessID(customerNo);
				if(Utility.testString(po.getLong("SPAD_CUSTOMER_ID"))) {
					dto.setFCAID(po.getLong("SPAD_CUSTOMER_ID"));
				}
				if(Utility.testString(po.getInteger("INTENT_LEVEL"))) {
					dto.setOppLevelID(po.getInteger("INTENT_LEVEL").toString());
				}
				if(Utility.testString(po.getInteger("SLEEP_TYPE"))) {
					dto.setGiveUpType(po.getInteger("SLEEP_TYPE").toString());
				}
				dto.setCompareCar(po.getString("SLEEP_SERIES"));
				dto.setGiveUpReason(po.getString("KEEP_APPLY_REASION"));
				dto.setGiveUpDate(po.getDate("DCC_DATE"));
				if(Utility.testString(po.getLong("SOLD_BY").toString())) {
					dto.setDealerUserID(po.getLong("SOLD_BY").toString());
				}
				dto.setDealerCode(dealerCode);
				resultList.add(dto);

				List<PotentialCusPO> list2 = PotentialCusPO.findBySQL("SELECT * from TM_POTENTIAL_CUSTOMER WHERE CUSTOMER_NO = ? AND DEALER_CODE = ? AND D_KEY = ? ", new Object[]{customerNo,dealerCode,CommonConstants.D_KEY});
				PotentialCusPO po1 = list2.get(0);
				po1.setInteger("IS_UPLOAD", Utility.getInt(CommonConstants.DICT_IS_YES));
				po1.setLong("userId",userId);
				po1.setDate("UPDATED_AT", Utility.getCurrentDateTime());
				po1.saveIt();
				return resultList;
			}
			
		}
			
		} catch (Exception e) {
			return null;
		}
		return null;
	}

	//改成F级并且上报的客户 要把未跟进记录删除才行
	private void deleteFcustomerAction(String dealerCode, String customerNo) {
		StringBuffer sb = new StringBuffer(" DELETE FROM TT_SALES_PROMOTION_PLAN WHERE (PROM_RESULT IS NULL OR PROM_RESULT=0) ");
		sb.append(" AND dealer_code= "+dealerCode+"AND customer_no="+customerNo+" ");
		Base.exec(sb.toString());
	}

	
	
}
