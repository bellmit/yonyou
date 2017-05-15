package com.yonyou.dms.vehicle.service.paymentMaintenance;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.dao.paymentMaintenance.PaymentMaintenanceDao;
import com.yonyou.dms.vehicle.domains.DTO.dealerPayment.TmDealerPaymentDTO;
import com.yonyou.dms.vehicle.domains.PO.k4OrderExecuteManage.TmDealerPaymentPO;

/**
 * 经销商付款方式维护
 * @author Administrator
 *
 */
@Service
public class PaymentMaintenanceServiceImpl implements PaymentMaintenanceService{
	@Autowired
	PaymentMaintenanceDao  paymentMaintenanceDao;

	/**
	 * 查询经销商付款方式
	 */
	@Override
	public PageInfoDto PaymentMaintenanceQuery(Map<String, String> queryParam) {
		PageInfoDto dto = paymentMaintenanceDao.PaymentMaintenanceQuery(queryParam);
		List<Map> list = dto.getRows();
		if(list != null && !list.isEmpty()){
			for(int i = 0;i < list.size(); i++){
				Map map = list.get(i);
				Map temp = getPaymentById(String.valueOf(map.get("DEALER_ID")));
				Map<Integer,String> pmap = paymentMaintenanceDao.getPaymentType();
				String paymentType = "";
				String type = temp.get("PAYMENT_TYPE") == null ? null : String.valueOf(temp.get("PAYMENT_TYPE"));
				if(!StringUtils.isNullOrEmpty(type)){
					String[] types = type.split(",");
					for(String str : types){
						Integer key = Integer.parseInt(str);
						paymentType += pmap.get(key) +",";
					}
				}
				if(!"".equals(paymentType)){
					paymentType = paymentType.substring(0,paymentType.length() -1);
				}
				map.put("PAYMENT_TYPE", paymentType);
			}
		}
		dto.setRows(list);
		return dto;
	}
	/**
	 * 通过id查询经销商付款方式（回显信息）
	 */
	public Map getPaymentById(String id) {
		 List<Map> list = paymentMaintenanceDao.getPaymentById(id);
	     Map<String, Object> m= list.get(0) ;
	     String PAYMENT_TYPE = "";
		 for (Map map : list) {
			 if("".equals(PAYMENT_TYPE)){
				 PAYMENT_TYPE = map.get("PAYMENT_TYPE").toString();
			 }else{
				 PAYMENT_TYPE = PAYMENT_TYPE+","+ map.get("PAYMENT_TYPE").toString();
			 }
		}
		 m.put("PAYMENT_TYPE", PAYMENT_TYPE);
		return m ;
	}
 
  	

    
	/**
	 * 添加经销商付款方式
	 */
    public Long addDealerPayment(TmDealerPaymentDTO ptdto) throws ServiceBizException {
       /* if(!CommonUtils.isNullOrEmpty( getDealerPaymentBy(ptdto))){
              throw new ServiceBizException("已存在付款方式数据！");
          }
        else{  */
    		Long id = 0l;
        	TmDealerPaymentPO.delete(" DEALER_ID = ? ", ptdto.getDealerId());
        	List<String> list = ptdto.getPaymentType();
        	if(null!=list && list.size()>0){
        		for(int i =0;i<list.size();i++){
        			TmDealerPaymentPO ptPo=new TmDealerPaymentPO(); 
        			ptPo.setInteger("PAYMENT_TYPE", list.get(i));
        			setDealerPaymentPo(ptPo,ptdto);
        			boolean flag = ptPo.saveIt();
        			id = ptPo.getLongId();
        		}
        	}
          return id;
      //}
    }
    
	/**
	 * 修改经销商付款方式
	 */
	public void modifyDealerPayment(Long id, TmDealerPaymentDTO ptdto){
    	List<String> list = ptdto.getPaymentType();
    	if(null!=list && list.size()>0){
    		int flag = TmDealerPaymentPO.delete(" DEALER_ID = ? ", ptdto.getDealerId());
    		for(int i =0;i<list.size();i++){
    			TmDealerPaymentPO ptPo=new TmDealerPaymentPO(); 
    			ptPo.setInteger("PAYMENT_TYPE", list.get(i));
    			setDealerPaymentPo(ptPo,ptdto);
    			ptPo.saveIt();
    			id = ptPo.getLongId();
    		}
    	}
	}
  	private void setDealerPaymentPo(TmDealerPaymentPO ptPo, TmDealerPaymentDTO ptdto) {
  		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
  		  ptPo.setString("DEALER_ID", ptdto.getDealerId());
          ptPo.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
          ptPo.setTimestamp("CREATE_DATE", new Date());
          ptPo.setTimestamp("UPDATE_DATE", new Date());
          ptPo.setLong("CREATE_BY", loginInfo.getUserId());
          ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
  	}
  	
  	//如果存在付款方式
/*  	private List<Map> getDealerPaymentBy(TmDealerPaymentDTO ptdto) throws ServiceBizException {
        StringBuilder sqlSb = new StringBuilder("  SELECT  payment_type  FROM  TM_DEALER_PAYMENT  WHERE 1=1");
        List<Object> params = new ArrayList<>();
        sqlSb.append(" and payment_type= ? ");
        params.add(ptdto.getPaymentType());
        List<Map> applyList=OemDAOUtil.findAll(sqlSb.toString(), params);
        return applyList;
    }  
	*/
	
	
	
}
