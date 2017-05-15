package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDMS066Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SEDMS066Dto;
import com.yonyou.dms.DTO.gacfca.TiAppNTestDriveDto;
import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialApplyDcsPO;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;

/**
 * 调拨出库单下发接口
 * @author luoyang
 *
 */
@Service
public class SEDMS066CloudImpl extends BaseCloudImpl implements SEDMS066Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDMS066CloudImpl.class);
	
	@Autowired
	SEDMS066Dao dao;

	@Override
	public String execute(String outWarehousNos) throws ServiceBizException {
		logger.info("================调拨出库单下发接口下发执行开始（SEDMS066）====================");
		LinkedList<SEDMS066Dto> vos = getDataList(outWarehousNos);
		if(vos != null && !vos.isEmpty()){
			for(int i = 0; i < vos.size(); i++){
				if(CommonUtils.checkIsNull(vos.get(i).getDealerCode())){					
					sendData(vos.get(i));
				}else{
					logger.info("SEDMS066 DCS没有维护经销商:"+vos.get(i).getDealerCode());
				}
			}
		}
		Integer size = vos==null?0:vos.size();
		logger.info("================调拨出库单下发接口下发执行结束（SEDMS066）,下发了(" + size + ")条数据====================");		
		return null;
	}

	private void sendData(SEDMS066Dto dto) {
		try {
			if(null!=dto){
				int flag = 0;
				List<SEDMS066Dto> list = new ArrayList<>();
				list.add(dto);
				logger.info("向经销商"+dto.getDealerCode()+"下发数据");
				//下发操作
//				int flag = sotdms003.SOTDMS003();
				//将发送出库单状态改为【已下发】
				String outWarehousNo=CommonUtils.checkNull(dto.getAllocateOutNo());
				logger.info("====出库单号===="+outWarehousNo+"=====");
				//将发送出库单状态改为【已下发】
				TtObsoleteMaterialApplyDcsPO.update("OUT_SEND_DMS = ?,UPDATE_BY = ?,UPDATE_DATE = ?", "OUT_WAREHOUS_NO = ?", 1,DEConstant.DE_UPDATE_BY,new Date(),outWarehousNo);
				if(flag==1){
					logger.info("================调拨出库单下发接口下发成功（SEDMS066）====================");
				}else{
					logger.info("================调拨出库单下发接口下发失败（SEDMS066）====================");
				}
				
			}else{
				//经销商无业务范围
				logger.info("================调拨出库单下发接口下发经销商无业务范围（SEDMS066）====================");
			}
		} catch (Exception e) {
			logger.info("================调拨出库单下发接口下发异常（SEDMS066）====================");
		}
		
	}

	@Override
	public LinkedList<SEDMS066Dto> getDataList(String outWarehousNos) throws ServiceBizException {
		LinkedList<SEDMS066Dto> vos = null;		
		try {
			vos = dao.getStockOutList(outWarehousNos);
		} catch (Exception e) {
			e.printStackTrace();
			throw new ServiceBizException(e);
		}
		Integer size = vos==null?0:vos.size();
		return vos;
	}

}
