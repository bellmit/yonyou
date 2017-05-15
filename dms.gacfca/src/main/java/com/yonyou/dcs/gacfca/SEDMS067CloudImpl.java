package com.yonyou.dcs.gacfca;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.SEDMS067Dao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.SEDMS067Dto;
import com.yonyou.dms.common.domains.PO.basedata.TtObsoleteMaterialApplyDcsPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;

/**
 * 出库信息or作废信息上报接口
 * @author luoyang
 * @return 1 success 0 fail
 */
@Service
public class SEDMS067CloudImpl extends BaseCloudImpl implements SEDMS067Cloud {
	
	private static final Logger logger = LoggerFactory.getLogger(SEDMS067CloudImpl.class);
	
	@Autowired
	SEDMS067Dao dao;
	
	@Autowired
	SEDMS068Cloud sedms068cloud;

	@Override
	public String handleExecutor(List<SEDMS067Dto> list) throws Exception {
		String msg = "1";
		logger.info("====出库信息or作废信息上报接口(SEDMS067)接收开始===="); 
		int flag = 0;
		for (SEDMS067Dto dto : list) {
			try {
				Date currentTime = new Date();
				logger.info("****************调拨单状态***"+dto.getAllocateState()+"********************");
				//调拨单状态 （1 出库  2 作废）
				if(dto.getAllocateState()==1){
					TtObsoleteMaterialApplyDcsPO.update("STATUS = ?,OUT_WAREHOUS_DATE = ?,OUT_WAREHOUS_BY = ?,UPDATE_BY= ?,UPDATE_DATE = ?", "OUT_WAREHOUS_NO = ?",
							OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_03,dto.getStockOutDate(),dto.getHandler(),DEConstant.DE_UPDATE_BY,currentTime,dto.getAllocateOutNo());
				}else if(dto.getAllocateState()==2){
					TtObsoleteMaterialApplyDcsPO.update("STATUS = ?,CANCEL_DATE = ?,OUT_WAREHOUS_BY = ?,UPDATE_BY= ?,UPDATE_DATE = ?", "OUT_WAREHOUS_NO = ?",
							OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_06,dto.getMakeUnableDate(),dto.getHandler(),DEConstant.DE_UPDATE_BY,currentTime,dto.getAllocateOutNo());
				}
				//调拨出库后获取出库单号
				flag++;
				if(flag==1 && dto.getAllocateState()==1){
					String allocateOutNo = dto.getAllocateOutNo();
					//接收调拨出库数据后，将配件调拨入库信息下发给DMS下发
					logger.info("##################### 配件调拨出库后/出库作废 出库单号："+allocateOutNo+"############################");
//					SEDMS068 sedms068 = new SEDMS068();
//					sedms068.sendData(allocateOutNo);
				}else if(dto.getAllocateState()==2){//作废状态
					//作废后需将配件申请数量加到发布经销商的申请数量上
					List<TtObsoleteMaterialApplyDcsPO> applyList = TtObsoleteMaterialApplyDcsPO.find("OUT_WAREHOUS_NO = ? ", dto.getAllocateOutNo());
					if(applyList!=null && applyList.size()>0){
						Long releaseId = applyList.get(0).getLong("RELEASE_ID");
						Integer applyNumber = applyList.get(0).getInteger("APPLY_NUMBER");				
						dao.updateApplyNumber(releaseId, applyNumber);
					}
				}
			} catch (Exception e) {
				logger.error("出库信息or作废信息上报接口(SEDMS067)接收失败", e);
				msg = "0";
				throw new ServiceBizException(e);
			}
		}
		logger.info("====呆滞件下架or变更数量上报接口(SEDMS067)接收成功====");
		logger.info("====呆滞件下架or变更数量上报接口(SEDMS067)接收结束===="); 
		return msg;
	}

	private void insertData(SEDMS067Dto dto,int flag) {
		Date currentTime = new Date();
		logger.info("****************调拨单状态***"+dto.getAllocateState()+"********************");
		//调拨单状态 （1 出库  2 作废）
		if(dto.getAllocateState()==1){
			TtObsoleteMaterialApplyDcsPO.update("STATUS = ?,OUT_WAREHOUS_DATE = ?,OUT_WAREHOUS_BY = ?,UPDATE_BY= ?,UPDATE_DATE = ?", "OUT_WAREHOUS_NO = ?",
					OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_03,dto.getStockOutDate(),dto.getHandler(),DEConstant.DE_UPDATE_BY,currentTime,dto.getAllocateOutNo());
		}else if(dto.getAllocateState()==2){
			TtObsoleteMaterialApplyDcsPO.update("STATUS = ?,CANCEL_DATE = ?,OUT_WAREHOUS_BY = ?,UPDATE_BY= ?,UPDATE_DATE = ?", "OUT_WAREHOUS_NO = ?",
					OemDictCodeConstants.PART_OBSOLETE_APPLY_STATUS_06,dto.getMakeUnableDate(),dto.getHandler(),DEConstant.DE_UPDATE_BY,currentTime,dto.getAllocateOutNo());
		}
		//调拨出库后获取出库单号
		flag++;
		if(flag==1 && dto.getAllocateState()==1){
			String allocateOutNo = dto.getAllocateOutNo();
			//接收调拨出库数据后，将配件调拨入库信息下发给DMS下发
			logger.info("##################### 配件调拨出库后/出库作废 出库单号："+allocateOutNo+"############################");
			sedms068cloud.execute(allocateOutNo);
		}else if(dto.getAllocateState()==2){//作废状态
			//作废后需将配件申请数量加到发布经销商的申请数量上
			List<TtObsoleteMaterialApplyDcsPO> applyList = TtObsoleteMaterialApplyDcsPO.find("OUT_WAREHOUS_NO = ? ", dto.getAllocateOutNo());
			if(applyList!=null && applyList.size()>0){
				Long releaseId = applyList.get(0).getLong("RELEASE_ID");
				Integer applyNumber = applyList.get(0).getInteger("APPLY_NUMBER");				
				dao.updateApplyNumber(releaseId, applyNumber);
			}
		}
		
	}

}
