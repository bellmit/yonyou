package com.yonyou.dms.part.service.basedata;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TtPtOrderDcsPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.part.dao.PartOrderDao;
import com.yonyou.dms.part.domains.DTO.basedata.TtPtOrderDcsDTO;
import com.yonyou.dms.part.domains.PO.partBaseInfoManage.TtPtOrderHistoryDcsPO;

/**
 * 配件管理
 * @author ZhaoZ
 *@date 2017年3月22日
 */
@Service
public class PartOrderServiceImpl implements PartOrderService{

	@Autowired
	private  PartOrderDao partDao;

	/**
	 * 配件订单审核查询
	 */
	@Override
	public PageInfoDto checkOrderPartInfo(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.findOrderPartList(queryParams);
	}

	/**
	 * 配件订单审核回显信息
	 */
	@Override
	public Map<String, Object> findDealerInfoByOrderId(BigDecimal id) throws ServiceBizException {
		return partDao.findDealerInfoById(id);
	}

	/**
	 * 审核历史
	 */
	@Override
	public PageInfoDto checkHidtoryInfo(BigDecimal id) throws ServiceBizException {
		return partDao.findHidtoryInfo(id);
	}

	/**
	 * 审核
	 */
	@Override
	public void checkAgreeService(Long id,String type,TtPtOrderDcsDTO dto) throws ServiceBizException {
		boolean flag = false;
		TtPtOrderDcsPO updatePo = TtPtOrderDcsPO.findById(id);
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if ("1".equals(type)) {//审核通过
			updatePo.setInteger("ORDER_STATUS",OemDictCodeConstants.PART_ORDER_STATUS_04);
		} else if ("2".equals(type)) {//驳回
			updatePo.setInteger("ORDER_STATUS",OemDictCodeConstants.PART_ORDER_STATUS_02);
		} else if ("3".equals(type)) {//拒绝
			updatePo.setInteger("ORDER_STATUS",OemDictCodeConstants.PART_ORDER_STATUS_03);
		}
		updatePo.setLong("UPDATE_BY",loginUser.getUserId() );//修改人
		updatePo.setDate("UPDATE_DATE",new Date());// 修改时间 
		updatePo.setInteger("IS_DCS_DOWN",OemDictCodeConstants.IF_TYPE_NO);//未下发
		flag = updatePo.saveIt();
		if(flag){			
		}else{
			throw new ServiceBizException("审核失败!");
		}
		TtPtOrderHistoryDcsPO historyPo = new TtPtOrderHistoryDcsPO();
		historyPo.setLong("ORDER_ID", id);
		historyPo.setInteger("CHECK_STATUS", updatePo.getInteger("ORDER_STATUS"));
		historyPo.setString("CHECK_OPINION", dto.getContent());
		historyPo.setDate("CHECK_DATE", new Date());
		historyPo.setLong("CREATE_BY", loginUser.getUserId());
		historyPo.setDate("CREATE_DATE", new Date());
		flag = historyPo.insert();
		if(flag){			
		}else{
			throw new ServiceBizException("审核历史插入失败!");
		}
	}

	@Override
	public List<Map> queryDownLoad(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.queryDownLoadList(queryParams);
	}

	/**
	 * 查询经销商
	 */
	@Override
	public PageInfoDto getDealerList(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.dealerList(queryParams);
	}

	/**
	 * 配件订单查询
	 */
	@Override
	public PageInfoDto getOrderInfo(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.orderInfos(queryParams);
	}

	/**
	 * 配件订单异常监控查询
	 */
	@Override
	public PageInfoDto orderInterMonitorQuary(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getOrderInterMonitor(queryParams);
	}

	/**
	 * 重置
	 */
	@Override
	public void reset(BigDecimal id) throws ServiceBizException {
		boolean flag = false;
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		if(!StringUtils.isNullOrEmpty(id)){
			TtPtOrderDcsPO updatePo = TtPtOrderDcsPO.findById(id);
			if(updatePo!=null){
				updatePo.setLong("UPDATE_BY", loginUser.getUserId());
				updatePo.setDate("UPDATE_DATE", new Date());
				updatePo.setInteger("IS_DCS_SEND", OemDictCodeConstants.IF_TYPE_NO);
				flag = updatePo.saveIt();
				
				if(flag){			
				}else{
					throw new ServiceBizException("重置失败!");
				}
			}
		}
		
	}

	/**
	 * 发票信息查询
	 */
	@Override
	public PageInfoDto queryInvoices(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.getqueryInvoices(queryParams);
	}

	/**
	 * 配件订单查询dlr
	 */
	@Override
	public PageInfoDto dlrQueryOrderInfo(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.dlrOrderInfo(queryParams);
	}

	/**
	 * 配件订单查询下载
	 */
	@Override
	public List<Map> queryOrderDownLoad(Map<String, String> queryParams) throws ServiceBizException {
		return partDao.orderDownLoad(queryParams);
	}
	
	

	
	

}
