package com.yonyou.dms.vehicle.service.orderSendTimeManage;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtOrderSendTimeManagePO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.orderSendTimeManage.OrderSendTimeManageDao;
import com.yonyou.dms.vehicle.domains.DTO.orderSendTimeManageDTO.TtOrderSendTimeManageDTO;

/**
 * 订单发送时间维护
 * @author Administrator
 *
 */
@Service
public class OrderSendTimeManageServiceImpl implements OrderSendTimeManageService{
	@Autowired
	OrderSendTimeManageDao  sendTimeManageDao;
/**
 * 订单发送时间查询
 */
	@Override
	public PageInfoDto orderSendTimeQuery() {
		// TODO Auto-generated method stub
		return sendTimeManageDao.orderSendTimeQuery();
	}
	
	/**
	 *通过id删除订单发送时间
	 */
	
	public void  deleteOrderSendTimeById(Long id) {
		TtOrderSendTimeManagePO ptPo=TtOrderSendTimeManagePO.findById(id);
		if(ptPo!=null){
			 ptPo.deleteCascadeShallow();
		}	
	}
	/**
	 * 通过id查询订单发送时间(回显)
	 */
	@Override
	public TtOrderSendTimeManagePO getSendTimeById(Long id,TtOrderSendTimeManageDTO  dto) {
		// TODO Auto-generated method stub
		 TtOrderSendTimeManagePO po = TtOrderSendTimeManagePO.findById(id);
		 //获取查询出来的week(1,2,3,4......转成int类型)
		 int week= Integer.parseInt(po.get("WEEK").toString());
		 if(week==1){
			 po.setInteger("WEEK", OemDictCodeConstants.REGION_TYPE_WEEK);
		 }else if(week==2){
			 po.setInteger("WEEK", OemDictCodeConstants.REGION_TYPE_WEEK2);
		 }else if(week==3){
			 po.setInteger("WEEK", OemDictCodeConstants.REGION_TYPE_WEEK3);
		 }else if(week==4){
			 po.setInteger("WEEK", OemDictCodeConstants.REGION_TYPE_WEEK4);
		 }else if(week==5){
			 po.setInteger("WEEK", OemDictCodeConstants.REGION_TYPE_WEEK5);
		 } else if(week==6){
			 po.setInteger("WEEK", OemDictCodeConstants.REGION_TYPE_WEEK6);
		 } else if(week==0){
			 po.setInteger("WEEK", OemDictCodeConstants.REGION_TYPE_WEEK7);
		 }
		return po;

	}
	
	/**
	 * 通过id修改订单发送时间
	 */
    public void modifySendTime(Long id,TtOrderSendTimeManageDTO ptdto) throws ServiceBizException {
    	TtOrderSendTimeManagePO ptPo=TtOrderSendTimeManagePO.findById(id);
        setSendTime(ptPo,ptdto);
        ptPo.saveIt();
       }
    
	private void setSendTime(TtOrderSendTimeManagePO ptPo, TtOrderSendTimeManageDTO ptdto) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
  		if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK){         //   星期一
  			ptPo.setInteger("WEEK", 1);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK2){
  			ptPo.setInteger("WEEK", 2);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK3){
  			ptPo.setInteger("WEEK", 3);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK4){
  			ptPo.setInteger("WEEK", 4);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK5){
  			ptPo.setInteger("WEEK", 5);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK6){
  			ptPo.setInteger("WEEK", 6);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK7){
  			ptPo.setInteger("WEEK", 0);
  		}
		//ptPo.setInteger("WEEK", ptdto.getWeek());
        ptPo.setString("START_TIME",ptdto.getStartTime());
        ptPo.setString("STOP_TIME", ptdto.getStopTime());
        ptPo.setInteger("STATUS",ptdto.getStatus());
        ptPo.setInteger("IS_DEL", 0); //未删除
        ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
        ptPo.setTimestamp("UPDATE_DATE", new Date());
	}
	
	/**
	 * 添加订单发送时间
	 */
    public Long addOrdersendTime(TtOrderSendTimeManageDTO ptdto) throws ServiceBizException {
         TtOrderSendTimeManagePO ptPo=new TtOrderSendTimeManagePO();
          setApplyPo(ptPo,ptdto);
          ptPo.saveIt();
          return ptPo.getLongId();
          
      }
  	private void setApplyPo(TtOrderSendTimeManagePO ptPo, TtOrderSendTimeManageDTO ptdto) {
  		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
  		if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK){         //   星期一
  			ptPo.setInteger("WEEK", 1);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK2){
  			ptPo.setInteger("WEEK", 2);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK3){
  			ptPo.setInteger("WEEK", 3);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK4){
  			ptPo.setInteger("WEEK", 4);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK5){
  			ptPo.setInteger("WEEK", 5);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK6){
  			ptPo.setInteger("WEEK", 6);
  		}else if(ptdto.getWeek()==OemDictCodeConstants.REGION_TYPE_WEEK7){
  			ptPo.setInteger("WEEK", 0);
  		}
  		//OemDictCodeConstants.REGION_TYPE_WEEK
		//ptPo.setInteger("WEEK", ptdto.getWeek());
        ptPo.setString("START_TIME",ptdto.getStartTime());
        ptPo.setString("STOP_TIME", ptdto.getStopTime());
        ptPo.setInteger("STATUS",ptdto.getStatus());
        ptPo.setInteger("IS_DEL", 0); //未删除
        ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
        ptPo.setTimestamp("UPDATE_DATE", new Date());
        ptPo.setLong("CREATE_BY", loginInfo.getUserId());
        ptPo.setTimestamp("CREATE_DATE", new Date());
  	}
	
}
