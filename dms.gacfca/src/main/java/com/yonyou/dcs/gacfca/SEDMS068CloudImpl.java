package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDMS068Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SEDMS066Dto;
import com.yonyou.dms.DTO.gacfca.SEDMS068Dto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialApplyDcsPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 接口名称：调拨入库单下发接口
 * 接口频次：SEDMS067上报时触发
 * 传输方向：DCS->DMS
 * 业务描述：呆滞件申请状态为出库的入库单下发给指定经销商
 * @author luoyang
 *
 */
@Service
public class SEDMS068CloudImpl extends BaseCloudImpl implements SEDMS068Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDMS068CloudImpl.class);
	
	@Autowired
	SEDMS068Dao dao;

	@Override
	public String execute( String allocateOutNo) throws ServiceBizException {
		logger.info("================调拨入库单下发接口下发执行开始（SEDMS068）====================");
		LinkedList<SEDMS068Dto> vos = getDataList(allocateOutNo);
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				sendData(vos.get(i));
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================调拨入库单下发接口下发执行结束（SEDMS068）,下发了(" + size + ")条数据====================");		
		return null;
	}

	private void sendData(SEDMS068Dto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<SEDMS068Dto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				
				//根据出库单号修改入库单号，并将是否发送入库单状态改为【已发送】
				logger.info("====SEDMS068 出库单号=="+dto.getOutWarehousNo()+"==");
				logger.info("====SEDMS068 入库单号=="+dto.getAllocateInNo()+"==");
				//将发送出库单状态改为【已下发】
				TtObsoleteMaterialApplyDcsPO.update("PUT_WAREHOUS_NO = ?,PUT_SEND_DMS = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "OUT_WAREHOUS_NO = ?",dto.getAllocateInNo(),1,DEConstant.DE_UPDATE_BY,new Date(),dto.getOutWarehousNo());
				if(flag==1){
					logger.info("================调拨入库单下发接口下发成功（SEDMS068）====================");
				}else{
					logger.info("================调拨入库单下发接口下发失败（SEDMS068）====================");
				}
				
			}else{
				//经销商无业务范围
				logger.info("================调拨入库单下发接口下发经销商无业务范围（SEDMS068）====================");
			}
		} catch (Exception e) {
			logger.info("================调拨入库单下发接口下发异常（SEDMS068）====================");
		}
		
	}

	@Override
	public LinkedList<SEDMS068Dto> getDataList( String allocateOutNo)
			throws ServiceBizException {
		LinkedList<SEDMS068Dto> vos = null;
		try {
			vos = dao.getStockInList(allocateOutNo);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException(e);
		}
		return vos;
	}

}
