package com.yonyou.dms.gacfca;


import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS096Cloud;
import com.yonyou.dms.DTO.gacfca.BigCustomerAuthorityApprovalDto;
import com.yonyou.dms.common.domains.PO.basedata.TmBigCustomerOrgApplyPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
@Service
public class SADMS096Impl implements SADMS096{
	
	private static final Logger logger = LoggerFactory.getLogger(SADMS096Impl.class);
   
	@Autowired SADCS096Cloud SADCS096Cloud;
    
    public String getSADMS096 (String applyNo,String userName,String remark) {
        LinkedList<BigCustomerAuthorityApprovalDto> WholeClryslerList;
        try {
        	System.err.println(applyNo);
        	System.err.println(userName);
        	System.err.println(remark);
        	logger.info("=============SADMS096开始=============");
			WholeClryslerList = new LinkedList<BigCustomerAuthorityApprovalDto>();
			if (!StringUtils.isNullOrEmpty(remark)
					&& !StringUtils.isNullOrEmpty(FrameworkUtil.getLoginInfo().getDealerCode())
					&& !StringUtils.isNullOrEmpty(userName)) {
				BigCustomerAuthorityApprovalDto vo = new BigCustomerAuthorityApprovalDto();
				List<Object> cus = new ArrayList<Object>();
				cus.add(applyNo);
				cus.add(userName);
				cus.add(remark);
				List<TmBigCustomerOrgApplyPO> cListSame = TmBigCustomerOrgApplyPO.findBySQL(
						"select * from Tm_Big_Customer_Org_Apply where APPLY_NO=? and USER_NAME=? and REMARK=?",
						cus.toArray());
				if (cListSame == null) {
					throw new ServiceBizException("没找到申请单，请重新查询后再试");
				}
				if (cListSame != null && cListSame.size() > 0) {
					TmBigCustomerOrgApplyPO po = (TmBigCustomerOrgApplyPO) cListSame.get(0);
					vo.setBrandYear(po.getDouble("Brand_Year"));
					vo.setParttimeStation(po.getString("Parttime_Station"));
					vo.setOriginalStation(po.getString("Original_Station"));
					vo.setIsParttime(po.getInteger("Is_Parttime"));
					vo.setSelfEvalution(po.getString("Self_Evalution"));
					vo.setContactorMobile(po.getString("Contactor_Mobile"));
					vo.setApplyNo(po.getString("Apply_No"));//
					vo.setUserName(po.getString("User_Name"));
					vo.setUserCode(po.getString("User_Code"));
					vo.setEmpCode(po.getString("Employee_No"));
					vo.setAuthorityApplyDate(new Date());
					vo.setApplyRemark(po.getString("Remark"));
					vo.setAuthorityApprovalStatus(47771001);
					vo.setEntityCode(po.getString("DEALER_CODE"));
				}
				WholeClryslerList.add(vo);
			}
			List<Object> cus = new ArrayList<Object>();
			cus.add(FrameworkUtil.getLoginInfo().getDealerCode());
			cus.add(applyNo);
			cus.add(userName);
			cus.add(remark);
			cus.add(0);
			List<TmBigCustomerOrgApplyPO> ListSame = TmBigCustomerOrgApplyPO.findBySQL("SELECT * FROM TM_BIG_CUSTOMER_ORG_APPLY WHERE DEALER_CODE=? AND APPLY_NO=? AND USER_NAME=? AND REMARK=? AND D_KEY=?", cus.toArray());
			System.err.println(ListSame);
			TmBigCustomerOrgApplyPO cListSame = ListSame.get(0);
			cListSame.setInteger("APPLY_STATUS", 47771001);
			cListSame.setDate("Apply_Date", new Date());
			cListSame.setLong("UPDATED_BY", FrameworkUtil.getLoginInfo().getUserId());
			cListSame.setDate("UPDATED_AT", new Date());
			cListSame.saveIt();
			//处理逻辑，，最后返回
			logger.info("=================SADMS096执行结束===================");
			return SADCS096Cloud.handleExecutor(WholeClryslerList);
			
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			logger.info("======================SADCS096异常========================");
			return "0";
		}
    }
   
}
