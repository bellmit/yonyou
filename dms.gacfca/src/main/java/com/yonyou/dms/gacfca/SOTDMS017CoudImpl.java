package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.TiAppUSalesQuotasDto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
@Service
public class SOTDMS017CoudImpl implements SOTDMS017Coud{
    public int getSOTDMS017(List<TiAppUSalesQuotasDto> dtList) throws ServiceBizException {
        
        if (dtList != null && dtList.size() > 0){
            for (int i = 0; i < dtList.size(); i++){
                TiAppUSalesQuotasDto vo=(TiAppUSalesQuotasDto)dtList.get(i);
//                PotentialCusPO po=new PotentialCusPO();
//                PotentialCusPO po1=new PotentialCusPO();
                List<PotentialCusPO> poList=null;
                if(vo.getFCAID()!=null){
                    List<Object> cus1 = new ArrayList<Object>();
                    cus1.add(Long.valueOf(vo.getFCAID()+""));  
                    cus1.add(FrameworkUtil.getLoginInfo().getDealerCode()); 
                     poList = PotentialCusPO.findBySQL("select * from Tm_Potential_Customer where Spad_Customer_Id=? and dealer_code=?", cus1.toArray());
                }
                if(poList!=null&&poList.size()>0){
                   
                }else{
                    if(vo.getUniquenessID()!=null){
                        List<Object> cus2 = new ArrayList<Object>();
                        cus2.add(vo.getUniquenessID());  
                        cus2.add(FrameworkUtil.getLoginInfo().getDealerCode()); 
                         poList = PotentialCusPO.findBySQL("select * from Tm_Potential_Customer where Customer_No=? and dealer_code=?", cus2.toArray());
                      
                    }
                }
                if(poList!=null&&poList.size()>0){                      
                    PotentialCusPO po4=(PotentialCusPO)poList.get(0);
              //      PotentialCusPO po4=new PotentialCusPO();
                    if(vo.getDealerUserID()!=null)
                        po4.setLong("SOLD_BY",Long.valueOf(vo.getDealerUserID()));
                    if(vo.getOldDealerUserID()!=null)
                        po4.setLong("Last_Sold_By",Long.valueOf(vo.getOldDealerUserID()));
                    if(vo.getDealerUserID()!=null)
                        po4.setLong("Owned_By",Long.valueOf(vo.getDealerUserID()));
                    if(vo.getUpdateDate()!=null)
                        po4.setDate("Update_Date",vo.getUpdateDate());
                    po4.setDate("Spad_Update_Date",vo.getUpdateDate());
              //      po4.setUpdateBy(Long.valueOf(vo.getDealerUserID()));
                    po4.setInteger("Is_Update",Integer.valueOf(DictCodeConstants.DICT_IS_NO));
                    if(vo.getUpdateDate()!=null)
                        po4.set("Consultant_Time",vo.getUpdateDate());
                    po4.saveIt();
                }
            }
        }
        return 1;
    }
}
