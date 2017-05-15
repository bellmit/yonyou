package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.PartOrderManageDao;
import com.yonyou.dcs.dao.SEDCSP6Dao;
import com.yonyou.dms.DTO.gacfca.SEDCSP6DetailDto;
import com.yonyou.dms.DTO.gacfca.SEDCSP6Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtPtDeliverDcsPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 接口名称：经销商交货单发送接口
 * 接口方向：DCS -> DMS
 * 接口频次：
 * 接口描述：
 * @author luoyang
 *
 */
@Service
public class SEDCSP6CloudImpl extends BaseCloudImpl implements SEDCSP6Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDCSP6CloudImpl.class);
	
	@Autowired
	SEDCSP6Dao dao;
	
	@Autowired
	PartOrderManageDao orderDAO;
	
	@Override
	public String execute() throws ServiceBizException {
		logger.info("================经销商交货单发送接口下发执行开始（SEDCSP6）====================");
		LinkedList<SEDCSP6Dto> vos = getDataList();
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================经销商交货单发送接口下发执行结束（SEDCSP6）,下发了(" + size + ")条数据====================");
		return null;
	}

	private void sendData(SEDCSP6Dto dto) {
		try {
			if(null!=dto){
				String msgID = "";
				
				// 拆单开始
				List<SEDCSP6Dto> voListByOrder = new ArrayList<SEDCSP6Dto>();	// 拆单后的结果
				String sapOrderNo="";
				int listIndex = 0;	// 单个VO清单计数
				for (int k = 0; k < dto.getWaybillList().size(); k++) {
					
					// 发货单明细
					SEDCSP6DetailDto detailVo = (SEDCSP6DetailDto) dto.getWaybillList().get(k);
					
					if(detailVo.getOrderNo() != null && detailVo.getOrderNo().equals(sapOrderNo)){
						listIndex++;
						HashMap<Integer, SEDCSP6DetailDto> list = voListByOrder.get(voListByOrder.size() - 1).getWaybillList();
						list.put(listIndex, detailVo);
					} else {
						
					    sapOrderNo = detailVo.getOrderNo();
						listIndex = 0;
						
						SEDCSP6Dto p6Vo = new SEDCSP6Dto();
						p6Vo.setEntityCode(dto.getEntityCode());	// DMS经销商代码
						p6Vo.setDeliverNo(dto.getDeliverNo());	// 交货单号
						p6Vo.setSapOrderNo(sapOrderNo);	// SAP订单号
						
						// DMS订单号
						String dmsOrderNo = orderDAO.getOrderBySAPOrderNO(sapOrderNo);
						p6Vo.setDmsOrderNo(dmsOrderNo);
						
						p6Vo.setAmount(dto.getAmount());	// 总价
						p6Vo.setNetPrice(dto.getNetPrice());	// 净价
						p6Vo.setTaxAmount(dto.getTaxAmount());	// 税额
						p6Vo.setTransAmount(dto.getTransAmount());	// 运费
						p6Vo.setTransNo(dto.getTransNo());	 // 运单号
						p6Vo.setTransCompany(dto.getTransCompany());	// 运输公司
						p6Vo.setTransDate(dto.getTransDate());	// 运输日期
						p6Vo.setArrivedDate(dto.getArrivedDate());	// 到达日期
						p6Vo.setDeliverDate(dto.getDeliverDate());	// 交货单创建日期
						p6Vo.setEcOrderNo(dto.getEcOrderNo());//电商订单号
						p6Vo.setTransType(dto.getTransType());//运输方式
						
						HashMap<Integer, SEDCSP6DetailDto> list = new HashMap<Integer, SEDCSP6DetailDto>();
						list.put(listIndex, detailVo);
						p6Vo.setWaybillList(list);
						
						voListByOrder.add(p6Vo);
						
					}
				}
				// 拆单结束
				//发送数据
				List<SEDCSP6Dto> volist = new ArrayList<SEDCSP6Dto>();
				volist.addAll(voListByOrder);				
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				msgID = sotdms003.SOTDMS003();
				// 清除list
				volist.clear();
				
				// 如MSGID不为空，则把该记录修改已下发
				int flag = TtPtDeliverDcsPO.update("IS_DCS_SEND = ?,DCS_SEND_DATE  = ?,MSG_ID = ?", "DELIVER_NO = ?", OemDictCodeConstants.IF_TYPE_YES,new Date(),msgID,dto.getDeliverNo());
								
			}else{
				//经销商无业务范围
				logger.info("================经销商交货单发送接口下发经销商无业务范围（SEDCSP6）====================");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.info("================经销商交货单发送接口下发异常（SEDCSP6）====================");
		}
		
	}


	@Override
	public LinkedList<SEDCSP6Dto> getDataList() throws ServiceBizException {
		LinkedList<SEDCSP6Dto> vos = null;		
		try {
			vos = dao.queryData();
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException(e);
		}
		return vos;
	}
	
	

}
