package com.yonyou.dms.vehicle.service.orderSendTimeManage;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmOrderCancelRemarksPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.orderSendTimeManage.CancelRemarkMaintenanceDao;
import com.yonyou.dms.vehicle.domains.DTO.k4OrderQueryManage.TmOrderCancelRemarksDTO;

/**
 * 取消备注维护
 * @author Administrator
 *
 */
@Service
public class CancelRemarkMaintenanceServiceImpl implements CancelRemarkMaintenanceService{
	@Autowired
	CancelRemarkMaintenanceDao  cancelRemarkDao;

	@Override
	public PageInfoDto CancelRemarkQuery(Map<String, String> queryParam) {
		// TODO Auto-generated method stub
		return cancelRemarkDao.CancelRemarkQuery(queryParam);
	}
	/**
	 * 新增取消备注维护
	 * 
	 */
    public Long addCancelRemark(TmOrderCancelRemarksDTO ptdto) throws ServiceBizException {
    	TmOrderCancelRemarksPO ptPo=new TmOrderCancelRemarksPO();
         setApplyPo(ptPo,ptdto);
         ptPo.saveIt();
         return ptPo.getLongId();
         
     }
 	private void setApplyPo(TmOrderCancelRemarksPO ptPo, TmOrderCancelRemarksDTO ptdto) {
 	   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
       ptPo.setString("CANCEL_REMARKS_NO",ptdto.getCancelRemarksNo());
       ptPo.setString("CANCEL_REASON_TEXT", ptdto.getCancelReasonText());
       ptPo.setInteger("UPDATE_TYPE",OemDictCodeConstants.CANCEL_REMARK_INPUT);
       ptPo.setInteger("CANCEL_REMARKS_STATUS",ptdto.getCancelRemarksStatus());
       ptPo.setLong("UPDATE_BY", loginInfo.getUserId());
       ptPo.setTimestamp("UPDATE_DATE", new Date());
       ptPo.setLong("CREATE_BY", loginInfo.getUserId());
       ptPo.setTimestamp("CREATE_DATE", new Date());
 	}
 	
 	/**
 	 * 新增时获得取消备注维护代码
 	 */
 	public String getId(){
 	   int fourNo = (int) (Math.random()*9000+1000);//随机4位数
 		SimpleDateFormat df = new SimpleDateFormat("yyMMdd");//设置日期格式
 		String timeNow = df.format(new Date());// new Date()为获取当前系统时间
 		String customerNo = "QX"+timeNow+fourNo;
 		return customerNo;
 	}
	@Override
	public Map findDetailById(Long id) {
		TmOrderCancelRemarksPO po = TmOrderCancelRemarksPO.findById(id);
		return po.toMap();
	}
	@Override
	public Long editCancelRemark(TmOrderCancelRemarksDTO ptdto) {
	 	   LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TmOrderCancelRemarksPO po = TmOrderCancelRemarksPO.findById(ptdto.getCancelRemarksId());
		po.setString("CANCEL_REASON_TEXT", ptdto.getCancelReasonText());
	    po.setInteger("CANCEL_REMARKS_STATUS",ptdto.getCancelRemarksStatus());
        po.setLong("UPDATE_BY", loginInfo.getUserId());
        po.setTimestamp("UPDATE_DATE", new Date());
        boolean flag = po.saveIt();
		return null;
	}

}
