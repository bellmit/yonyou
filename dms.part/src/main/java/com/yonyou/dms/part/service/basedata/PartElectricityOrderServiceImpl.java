package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtPtEcOrderHistoryPO;
import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.part.dao.PartElectricityOrderDao;

/**
 * 电商订单确认
 * @author ZhaoZ
 *@date 2017年4月10日
 */
@Service
public class PartElectricityOrderServiceImpl implements PartElectricityOrderService{

	@Autowired
	private PartElectricityOrderDao partDao;

	/**
	 * 订单查询
	 */
	@Override
	public PageInfoDto queryECOrderInfo(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.eCOrderInfo(queryParams);
	}

	/**
	 * 导出查询
	 */
	@Override
	public List<Map> queryDownLoad(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.queryDownLoadList(queryParams);
	}

	/**
	 * 回显信息
	 */
	@Override
	public Map<String, Object> findDealerInfoByOrderId(BigDecimal id) throws ServiceBizException {
		return partDao.dealerInfoByOrderId(id);
	}

	/**
	 * 配件信息查询
	 */
	@Override
	public PageInfoDto queryPartInfo(BigDecimal id) throws ServiceBizException {
		return partDao.queryPartInfoList(id);
	}

	/**
	 * 审核历史
	 */
	@Override
	public PageInfoDto checkHidtoryInfo(BigDecimal id) throws ServiceBizException {
		return partDao.checkHidtoryInfoList(id);
	}

	/**
	 * 确认状态
	 */
	@Override
	public void confirmOrder(BigDecimal id) throws ServiceBizException {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		boolean flag = false;
		TtPtOrderDcsPO tpoPo = TtPtOrderDcsPO.findFirst("ORDER_ID = ?", id);
		tpoPo.setInteger("IS_AFFIRM",OemDictCodeConstants.EC_CONFIRM_STATUS_01);
		tpoPo.setInteger("ORDER_STATUS",OemDictCodeConstants.PART_ORDER_STATUS_04);
		tpoPo.setDate("DEAL_ORDER_AFFIRM_DATE",new Date());
		tpoPo.setLong("DEAL_ORDER_AFFIRM_BY",new Long(logonUser.getDealerId()));
		flag = tpoPo.saveIt();
		if(flag){			
		}else{
			throw new ServiceBizException("确认失败!");
		}
		
	}

	/**
	 * 配件订单历史记录
	 */
	@Override
	public void insertPtEcOrderHistory(String no, String operatMessage, String reamrk) throws ServiceBizException {
		LoginInfoDto logonUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		TtPtEcOrderHistoryPO po = new TtPtEcOrderHistoryPO();
		boolean flag = false;
		po.setString("EC_ORDER_NO", no);
		po.setString("OPERAT_MESSAGE", operatMessage);
		po.setString("REAMRK", reamrk);
		po.setLong("CREATE_BY",logonUser.getUserId());	// 创建人
		SimpleDateFormat sdf= new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	long time= System.currentTimeMillis();
    	try {
    		Date date = sdf.parse(sdf.format(new Date(time)));
    		java.sql.Timestamp st = new java.sql.Timestamp(date.getTime());
    		po.setTimestamp("CREATE_DATE",st);
    	} catch (ParseException e) {
    		e.printStackTrace();
    	} 	
		flag = po.insert();
		if(flag){			
		}else{
			throw new ServiceBizException("确认失败!");
		}
	}

	
}
