
/** 
*Copyright 2017 Yonyou Corporation Ltd. All Rights Reserved.
* This software is published under the terms of the Yonyou Software
* License version 1.0, a copy of which has been included with this
* distribution in the LICENSE.txt file.
*
* @Project Name : dms.gacfca
*
* @File name : DCSTODMS003Imp.java
*
* @Author : LiGaoqi
*
* @Date : 2017年4月20日
*
----------------------------------------------------------------------------------
*     Date       Who       Version     Comments
* 1. 2017年4月20日    LiGaoqi    1.0
*
*
*
*
----------------------------------------------------------------------------------
*/
	
package com.yonyou.dms.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.yonyou.dms.DTO.gacfca.DCSTODMS003Dto;
import com.yonyou.dms.common.domains.PO.basedata.PotentialCusPO;
import com.yonyou.dms.common.domains.PO.basedata.TtCustomerIntentDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtEsCustomerOrderPO;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;


/**
* TODO description
* @author LiGaoqi
* @date 2017年4月20日
*/
@Service
public class DCSTODMS003CoudImp implements DCSTODMS003Coud {

    /**
    * @author LiGaoqi
    * @date 2017年4月20日
    * @param voList
    * @param dealerCode
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.gacfca.DCSTODMS003Coud#getSADMS061(java.util.LinkedList, java.lang.String)
    */

    @Override
    public int getSADMS061(LinkedList<DCSTODMS003Dto> voList, String dealerCode) throws ServiceBizException {
        try {
            //判断是否为空,循环操作，根据业务相应的修改数据
//          判断是否为空,循环操作，根据业务相应的修改数据
            if (voList != null && voList.size() > 0){
                for (int i = 0; i < voList.size(); i++){
                    DCSTODMS003Dto vo = new DCSTODMS003Dto();
                    vo =voList.get(i);
                    if(!StringUtils.isNullOrEmpty(vo.getEntityCode())&&!StringUtils.isNullOrEmpty(vo.getEcOrderNo())){
                        if(vo.getRevokeType()==1){
                            List<Object> querytecoList = new ArrayList<Object>();
                            querytecoList.add(vo.getEcOrderNo());
                            querytecoList.add(vo.getEntityCode());
                            querytecoList.add(DictCodeConstants.D_KEY);
                            List<TtEsCustomerOrderPO> queryteco = TtEsCustomerOrderPO.findBySQL("select * from TT_ES_CUSTOMER_ORDER where EC_ORDER_NO= ?  AND DEALER_CODE= ? AND D_KEY= ? ", querytecoList.toArray());
                            if(queryteco!=null&&queryteco.size()>0){
                                TtEsCustomerOrderPO po =queryteco.get(0);
                                po.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_UNVALID));
                                po.setDate("CANCEL_DATE", vo.getRevokeDate());
                                po.setString("UPDATED_BY", "1");
                                po.setDate("UPDATED_AT", new Date());
                                po.saveIt();
                              //修改原始电商订单表中的状态
                                List<Object> CusPOList = new ArrayList<Object>();       
                                CusPOList.add(po.getString("CUSTOMER_NO"));
                                CusPOList.add(vo.getEntityCode());
                                CusPOList.add(DictCodeConstants.D_KEY);
                                List<PotentialCusPO> tpcs = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where  CUSTOMER_NO= ?  AND DEALER_CODE= ? AND D_KEY= ? ", CusPOList.toArray());
                                PotentialCusPO tpcPO =null;
                                if(tpcs!=null&&tpcs.size()>0){
                                    tpcPO = tpcs.get(0);
                                    tpcPO.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_UNVALID));
                                    tpcPO.setString("UPDATED_BY", "1");
                                    tpcPO.setDate("UPDATED_AT", new Date());
                                    tpcPO.saveIt();
                                }
                                List<Object> intentDetaiList = new ArrayList<Object>();
                                intentDetaiList.add(tpcPO.getString("INTENT_ID"));
                                intentDetaiList.add(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                intentDetaiList.add(vo.getEntityCode());
                                intentDetaiList.add(Long.parseLong(DictCodeConstants.D_KEY));
                                intentDetaiList.add(Integer.parseInt(DictCodeConstants.DICT_IS_YES));
                                intentDetaiList.add(vo.getEcOrderNo());
                                List<TtCustomerIntentDetailPO> intentDetaiListPo = TtCustomerIntentDetailPO.findBySQL("select * from TT_CUSTOMER_INTENT_DETAIL where INTENT_ID= ? AND IS_MAIN_MODEL= ? AND DEALER_CODE= ? AND D_KEY= ? AND IS_ECO_INTENT_MODEL= ? AND EC_ORDER_NO= ?", intentDetaiList.toArray());
                                if(intentDetaiListPo!=null&&intentDetaiListPo.size()>0){
                                    TtCustomerIntentDetailPO tcidPO=intentDetaiListPo.get(0);
                                    tcidPO.setInteger("IS_ECO_INTENT_MODEL", Integer.parseInt(DictCodeConstants.DICT_IS_NO));
                                    tcidPO.setString("UPDATED_BY", "1");
                                    tcidPO.setDate("UPDATED_AT", new Date());
                                    tcidPO.saveIt();
                                }

                            }
                            
                        }else if(vo.getRevokeType()==2){
                           
                                List<Object> querytecoList = new ArrayList<Object>();
                                querytecoList.add(vo.getEcOrderNo());
                                querytecoList.add(vo.getEntityCode());
                                querytecoList.add(DictCodeConstants.D_KEY);
                                List<TtEsCustomerOrderPO> queryteco = TtEsCustomerOrderPO.findBySQL("select * from TT_ES_CUSTOMER_ORDER where EC_ORDER_NO= ?  AND DEALER_CODE= ? AND D_KEY= ? ", querytecoList.toArray());
                                if(queryteco!=null&&queryteco.size()>0){
                                    TtEsCustomerOrderPO po =queryteco.get(0);
                                    po.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_OVERDUE));
                                    po.setDate("OVERDUE_DATE", vo.getRevokeDate());
                                    po.setString("UPDATED_BY", "1");
                                    po.setDate("UPDATED_AT", new Date());
                                    po.saveIt();
                                  //修改原始电商订单表中的状态
                                    List<Object> CusPOList = new ArrayList<Object>();       
                                    CusPOList.add(po.getString("CUSTOMER_NO"));
                                    CusPOList.add(vo.getEntityCode());
                                    CusPOList.add(DictCodeConstants.D_KEY);
                                    List<PotentialCusPO> tpcs = PotentialCusPO.findBySQL("select * from TM_POTENTIAL_CUSTOMER where  CUSTOMER_NO= ?  AND DEALER_CODE= ? AND D_KEY= ? ", CusPOList.toArray());
                                    PotentialCusPO tpcPO =null;
                                    if(tpcs!=null&&tpcs.size()>0){
                                        tpcPO = tpcs.get(0);
                                        tpcPO.setInteger("ESC_ORDER_STATUS",Integer.parseInt(DictCodeConstants.DICT_DMS_ESC_STATUS_OVERDUE));
                                        tpcPO.setString("UPDATED_BY", "1");
                                        tpcPO.setDate("UPDATED_AT", new Date());
                                        tpcPO.saveIt();
                                    }
                             }
                           
                        }
                        
                    }
                }
            }
        } catch (Exception e) {
            return 0;
            // TODO: handle exception
        }

        // TODO Auto-generated method stub
        return 1;
    }

}
