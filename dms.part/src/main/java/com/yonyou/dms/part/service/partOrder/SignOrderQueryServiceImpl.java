package com.yonyou.dms.part.service.partOrder;

import java.util.Map;

import org.springframework.stereotype.Service;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.common.domains.PO.basedata.TtPartSignOrderPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
/**
 * 货运签收单查询
* TODO description
* @author yujiangheng
* @date 2017年5月10日
 */
@Service
public class SignOrderQueryServiceImpl implements SignOrderQueryService{
    /**
     * 查询货运签收单信息
    * @author yujiangheng
    * @date 2017年5月10日
    * @param queryParam
    * @return
    * @throws ServiceBizException
    * (non-Javadoc)
    * @see com.yonyou.dms.part.service.partOrder.SignOrderQueryService#SignOrderQuery(java.util.Map)
     */
    @Override
    public PageInfoDto SignOrderQuery(Map<String, String> queryParam) throws ServiceBizException {
        String psoNo=queryParam.get("PSO_NO");
        String orderRegeditNo=queryParam.get("ORDER_REGEDIT_NO");
        String isFinished=queryParam.get("IS_FINISHED");
        String signforDateBegin=queryParam.get("SIGNFOR_DATE_BEGIN");
        String signforDateEnd=queryParam.get("SIGNFOR_DATE_END");
        String deliverTimeBegin=queryParam.get("DELIVERY_TIME_BEGIN");
        String deliverTimeEnd=queryParam.get("DELIVERY_TIME_END");
        StringBuffer sql = new StringBuffer(" SELECT * FROM tt_part_sign_order");
        sql.append(" WHERE DEALER_CODE = '"  + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
        sql.append(" AND D_KEY = " + CommonConstants.D_KEY);
        sql.append(Utility.getLikeCond(null, "PSO_NO", psoNo, "AND"));
        sql.append(Utility.getLikeCond(null, "ORDER_REGEDIT_NO", orderRegeditNo, "AND"));
        if (isFinished!= null && !isFinished.equals("")){
            sql.append(" AND IS_FINISHED = " + isFinished + " ");
        }
        sql.append( Utility.getDateCond("", "SIGNFOR_DATE", signforDateBegin, signforDateEnd));
        sql.append( Utility.getDateCond("", "DELIVERY_TIME", deliverTimeBegin, deliverTimeEnd));
        return DAOUtil.pageQuery(sql.toString(), null);
    }

    @Override
    public PageInfoDto QuerySignOrderDetail(String psoNo) throws ServiceBizException {
        TtPartSignOrderPO mainPo =
                TtPartSignOrderPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),psoNo);
        if(mainPo!=null){
            
        }
//        mainPo.setEntityCode(entityCode);
//        mainPo.setDKey(CommonConstant.D_KEY);
//        mainPo.setPsoNo(psoNo);
////        List mainList = POFactory.select(conn, mainPo);
////        if (mainList != null && mainList.size() > 0)
////            actionContext.setArrayValue("TT_SIGN_ORDER", mainList.toArray());
//        StringBuffer sql = new StringBuffer(" SELECT * FROM tt_part_sign_order");
//        sql.append(" WHERE DEALER_CODE = '"  + FrameworkUtil.getLoginInfo().getDealerCode() + "' ");
//        TtPtDeliverItemPO detailPo = new TtPtDeliverItemPO();
//        detailPo.setEntityCode(entityCode);
//        detailPo.setDKey(CommonConstant.D_KEY);
    //    detailPo.setPsoNo(psoNo);
     //  List detailList = POFactory.select(conn, detailPo);
        return null;
    }
    
}
