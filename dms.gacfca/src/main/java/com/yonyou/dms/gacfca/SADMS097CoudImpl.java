package com.yonyou.dms.gacfca;

import java.util.ArrayList;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.BigCustomerAuthorityApprovalDto;
import com.yonyou.dms.common.domains.PO.basedata.TmBigCustomerOrgApplyPO;

import com.yonyou.dms.common.domains.PO.basedata.UserPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.manage.domains.PO.basedata.user.UserCtrlPO;
@Service
public class SADMS097CoudImpl implements SADMS097Coud{
    @Override
    public int getSADMS097(List<BigCustomerAuthorityApprovalDto> dtList) throws ServiceBizException {
        if (dtList != null && dtList.size() > 0){
            for (int i = 0; i < dtList.size(); i++){
                BigCustomerAuthorityApprovalDto vo = new BigCustomerAuthorityApprovalDto();
        //        TmBigCustomerOrgApplyPO po = new TmBigCustomerOrgApplyPO();
//                TmBigCustomerOrgApplyPO wpo = new TmBigCustomerOrgApplyPO();
                vo = (BigCustomerAuthorityApprovalDto)dtList.get(i);
                if(!StringUtils.isEmpty(vo.getEntityCode())&&!StringUtils.isEmpty(vo.getApplyNo())&&!StringUtils.isEmpty(vo.getAuthorityApprovalStatus().toString())){
                    TmBigCustomerOrgApplyPO po= TmBigCustomerOrgApplyPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),vo.getApplyNo());
                    po.setInteger("Apply_Status",vo.getAuthorityApprovalStatus());
                    po.setDate("Apply_Date",vo.getAuthorityApplyDate());
                    po.setString("Remark",vo.getApplyRemark());
                    po.saveIt();
                
                    if (vo.getAuthorityApprovalStatus()==47771002){
                        List<Object> cus2 = new ArrayList<Object>();
                        cus2.add(vo.getApplyNo());  
                        cus2.add(FrameworkUtil.getLoginInfo().getDealerCode()); 
                        List<TmBigCustomerOrgApplyPO> listcus1 = TmBigCustomerOrgApplyPO.findBySQL("select * from Tm_Big_Customer_Org_Apply where Apply_No=? and dealer_code=?", cus2.toArray());
                  
                    TmBigCustomerOrgApplyPO strTPCPO = (TmBigCustomerOrgApplyPO)listcus1.get(0);
                  
                    List<Object> cus1 = new ArrayList<Object>();
                    cus1.add(strTPCPO.get("User_Code"));  
                    cus1.add(FrameworkUtil.getLoginInfo().getDealerCode()); 
                    List<UserPO> listcus2 = UserPO.findBySQL("select * from tm_user where User_Code=? and dealer_code=?", cus2.toArray());
                   
                    UserPO ustp = (UserPO)listcus2.get(0);
                    ustp.getLong("USER_ID");
                    UserCtrlPO uPO = new UserCtrlPO();  
                    uPO.setLong("USER_ID",ustp.getLong("User_Id")); 
                    uPO.setString("Option_Code","80900000");
                    uPO.saveIt();
                    
                    }
                }
                
            }
        }
        return 1;
    }
}
