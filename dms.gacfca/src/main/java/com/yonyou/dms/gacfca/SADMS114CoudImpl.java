package com.yonyou.dms.gacfca;

import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.PayingBankDTO;
import com.yonyou.dms.common.domains.PO.basedata.TmPayingBankPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
public class SADMS114CoudImpl implements SADMS114Coud {

    @Override
    public int getSSADMS114(String dealerCode, List<PayingBankDTO> list) throws ServiceBizException {
        try {
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {

                    PayingBankDTO vo = new PayingBankDTO();
                    vo = (PayingBankDTO) list.get(i);
                    if (!StringUtils.isNullOrEmpty(vo.getUpdateStatus()) && ("10041001").equals(vo.getUpdateStatus().toString())) {//更新
                        if(!StringUtils.isNullOrEmpty(vo.getBankCode())) {
                            TmPayingBankPO.update("BANK_NAME= ?,IS_VALID= ?,UPDATE_STATUS= ?","DEALER_CODE= ? AND BANK_CODE= ? AND D_KEY= ?",
                                                  vo.getBankName(),returnStatus(vo.getStatus()),vo.getUpdateStatus(),dealerCode,vo.getBankCode(),0);
                        }

                            

                       

                }
                    else{
                    if (!StringUtils.isNullOrEmpty(vo.getUpdateStatus()) && ("10041002").equals(vo.getUpdateStatus().toString())) {//新增
                        if(!StringUtils.isNullOrEmpty(vo.getBankCode())) {
                            List<Map> poBankConList = Base.findAll("SELECT * FROM tm_paying_bank WHERE DEALER_CODE='"+dealerCode+"' AND BANK_CODE='"+vo.getBankCode()+"' AND D_KEY=0 ");
                 
                            if (poBankConList == null || poBankConList.size() == 0) {
                                TmPayingBankPO poBank = new TmPayingBankPO();
                                poBank.setString("BANK_NAME",vo.getBankName());
                                poBank.setString("DEALER_CODE",dealerCode);
                                poBank.setInteger("IS_VALID",returnStatus(vo.getStatus()));
                                poBank.setString("BANK_CODE",vo.getBankCode());
                                poBank.setInteger("UPDATE_STATUS",vo.getUpdateStatus());
                                //poBank.setCreateDate(Utility.getCurrentDateTime());
                                poBank.insert();
                            }


                        }
                        
                    }
                }
            }
        }
            
        } catch (Exception e) {
        	e.printStackTrace();
            return 0;
        }
        // TODO Auto-generated method stub
        return 1;
    }
    
//  字典翻译
    private int returnStatus(Integer orderStatus) {
        int num=0;
        switch (orderStatus.intValue()) {
        case 10011001:
            num=12781001 ;
            break;
        case 10011002:
            num= 12781002 ;
            break;

        }
        return num;

    }

}
