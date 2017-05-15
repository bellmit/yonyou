package com.yonyou.dcs.gacfca;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.dao.ActivityResultDao;
import com.yonyou.dcs.util.DEConstant;
import com.yonyou.dms.DTO.gacfca.VsStockEntryItemDto;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVehicleTransferPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
@Service
public class SA03CloudImpl extends BaseCloudImpl implements SA03Cloud {
	private static final Logger logger = LoggerFactory.getLogger(SA03CloudImpl.class);
	@Autowired
	ActivityResultDao dao ;

	@Override
	public String receiveDate(List<VsStockEntryItemDto> dtos) throws Exception {
		String msg = "1";
		
		try {
			logger.info("*************** SA03调拨入库数据上传接收开始 *******************");
			for (VsStockEntryItemDto dto : dtos) {
				allocateStorage(dto);
			}
			logger.info("*************** SA03调拨入库数据上报完成 ********************");
			
		} catch (Exception e) {
			logger.error("*************** SA03调拨入库数据上报异常 *****************", e);
			msg = "0";
			throw new ServiceBizException(e);
		} 
		return msg;
	}
	/**
	 *  调拨入库操作
	 * @param dto
	 * @throws Exception
	 */
	public synchronized void allocateStorage(VsStockEntryItemDto dto) throws Exception {
		
		String sql = "select * from TM_VEHICLE_DEC where vin = ? ";
		List<TmVehiclePO> tvList = TmVehiclePO.findBySQL(sql, dto.getVin());
		
		if (tvList.size() != 0) {
			TmVehiclePO tv = new TmVehiclePO();
			tv = (TmVehiclePO) tvList.get(0);
			Long vehicleId = Long.parseLong(CommonUtils.checkNull(tvList.get(0).get("VEHICLE_ID"), "0L")) ;//车辆ID 主键

			TtVsVehicleTransferPO tvvt = new TtVsVehicleTransferPO();
			String sqltv = "select * from tt_vs_vehicle_transfer where VEHICLE_ID = ? and check_status = ?";
			List<TtVsVehicleTransferPO> tvvtList = TmVehiclePO.findBySQL(sqltv, vehicleId,OemDictCodeConstants.TRANSFER_CHECK_STATUS_02);
			tvvt=(TtVsVehicleTransferPO)tvvtList.get(0);
			
			//调拨申请修改  更新结果
			StringBuffer tvvtUpSqlV=new StringBuffer();
			tvvtUpSqlV.append("VER = ?");
			tvvtUpSqlV.append("CHECK_STATUS = ?");
			tvvtUpSqlV.append("UPDATE_BY = ?");
			tvvtUpSqlV.append("UPDATE_DATE = ?");
			//调拨申请修改  更新条件
			StringBuffer tvvtUpSqlC=new StringBuffer();
			tvvtUpSqlC.append("TRANSFER_ID = ?");
			//调拨申请修改  参数
			Integer ver=Integer.parseInt(CommonUtils.checkNull(tvvt.get("VER"), "0"));
			List<Object> tvvtParams = new ArrayList<Object>();
			tvvtParams.add(ver+1);//校验结果:成功：10041001;失败：10041002
			tvvtParams.add(OemDictCodeConstants.TRANSFER_CHECK_STATUS_04);
			tvvtParams.add(DEConstant.DE_UPDATE_BY);
			tvvtParams.add(new Date());
			tvvtParams.add(tvvt.get("TRANSFER_ID"));
			// 调拨申请修改
			TtVsVehicleTransferPO.update(tvvtUpSqlV.toString(), tvvtUpSqlC.toString(), tvvtParams.toArray());
			
			//车辆修改  更新结果
			StringBuffer tvUpSqlV=new StringBuffer();
			tvUpSqlV.append("DEALER_ID = ?");
			tvUpSqlV.append("DEALER_STORAGE_DATE = ?");
			tvUpSqlV.append("VER = ?");
			tvUpSqlV.append("UPDATE_BY = ?");
			tvUpSqlV.append("UPDATE_DATE = ?");
			//车辆修改  更新条件
			StringBuffer tvUpSqlC=new StringBuffer();
			tvUpSqlC.append("VEHICLE_ID = ?");
			//车辆修改  参数
			List<Object> tvParams = new ArrayList<Object>();
			tvParams.add(tvvt.get("DEALER_ID"));//校验结果:成功：10041001;失败：10041002
			tvParams.add(new Date());
			tvParams.add(Integer.parseInt(CommonUtils.checkNull(tv.get("VER"), "0")) + 1);
			tvParams.add(DEConstant.DE_UPDATE_BY);
			tvParams.add(new Date());
			tvParams.add(vehicleId);
			// 车辆修改
			TmVehiclePO.update(tvUpSqlV.toString(), tvUpSqlC.toString(), tvParams.toArray());
			
			// 详细车籍新增
			TtVsVhclChngPO tvvc = new TtVsVhclChngPO();
			tvvc.setLong("VEHICLE_ID", vehicleId);
			tvvc.setInteger("CHANGE_CODE",OemDictCodeConstants.VEHICLE_CHANGE_TYPE_14);// 调拨入库
			tvvc.setString("CHANGE_DESC","调拨入库");
			tvvc.setTimestamp("CHANGE_DATE",new Date());
			tvvc.setString("CHANGE_MEMO",dto.getRemark());
			tvvc.setBigDecimal("CREATE_BY",DEConstant.DE_UPDATE_BY);
			tvvc.setTimestamp("CREATE_DATE",new Date());
			tvvc.insert();
		} else {
			logger.info("====DCS中没有VIN为" + dto.getVin() + "的车辆====");
		}
	}
	
}
