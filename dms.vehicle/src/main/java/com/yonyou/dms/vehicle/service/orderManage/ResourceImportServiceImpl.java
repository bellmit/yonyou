package com.yonyou.dms.vehicle.service.orderManage;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmAllotSupportPO;
import com.yonyou.dms.common.domains.PO.basedata.TmOrgPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtResourceRemarkPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsAppointOrderDcsPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourceDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsCommonResourcePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsMatchCheckPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsOrderPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.dao.factoryOrderDao.ResourceExcelDao;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.ResourceOrderUploadDTO;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TtVsUploadCommonResourcePO;

@Service
public class ResourceImportServiceImpl implements ResourceImportService {

	@Autowired
	private ResourceExcelDao dao;

	// 插入临时表
	@Override
	public void insertTmp(ResourceOrderUploadDTO rowDto, String orderType) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String currdate = sf2.format(new Date());
		TtVsUploadCommonResourcePO po = new TtVsUploadCommonResourcePO();
		po.setString("RESOURCE_TYPE", orderType);
		po.setString("VIN", rowDto.getVin());
		po.setString("RESOURCE_SCOPE", rowDto.getResourceScope());
		po.setInteger("USER_ID", loginInfo.getUserId());
		po.setTimestamp("CREATE_DATE", currdate);
		po.setTimestamp("UPDATE_DATE", currdate);
		po.setString("ROW_NO", rowDto.getRowNO());
		po.setInteger("IS_ORDER", 0);
		boolean flag = po.saveIt();
		System.out.println(flag);

		List<TmAllotSupportPO> TmSuPO = TmAllotSupportPO.find(" vin=? ", rowDto.getVin());
		if (TmSuPO.size() > 0) {
			for (TmAllotSupportPO PO : TmSuPO) {
				String vin = PO.getString("VIN");
				String sql = "update TM_ALLOT_SUPPORT set IS_SUPPORT=" + rowDto.getResourceType() + " where vin='" + vin
						+ "'";
				System.out.println(sql);
				OemDAOUtil.execBatchPreparement(sql, new ArrayList<>());
			}
		} else {
			String sql = "insert into TM_ALLOT_SUPPORT(VIN,IS_SUPPORT,CREATE_BY,CREATE_DATE) values(" + "'"
					+ rowDto.getVin() + "'" + "," + rowDto.getResourceType() + "," + loginInfo.getUserId()
					+ ",current_timestamp" + ")";
			OemDAOUtil.execBatchPreparement(sql, new ArrayList<>());
		}
	}

	@Override
	public List<Map<String, Object>> checkData(String type) {

		return dao.checkData(type);
	}

	// 临时数据回显
	@Override
	public List<Map> importShow(String orderType) {
		// TODO Auto-generated method stub
		return dao.importShow(orderType);
	}

	// 导入
	@Override
	public void importTableAppProce() {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		// TmpImportStatusPO po = new TmpImportStatusPO();
		// po.setInteger("ITYPE", "1");
		// po.setInteger("STATUS", OemDictCodeConstants.TMP_IMPORT_STATUS_01);
		// po.setLong("IMPORT_BY", loginInfo.getUserId());
		// po.setTimestamp("CREATE_DATE", format);
		// po.saveIt();
		// TtVsUploadCommonResourcePO.update("IS_ORDER=?", "IS_ORDER=?", 1, 0);
		// TmpImportStatusPO po1 = TmpImportStatusPO.findFirst("ITYPE=1 and
		// STATUS=?",
		// OemDictCodeConstants.TMP_IMPORT_STATUS_01);
		//
		// po1.setInteger("STATUS", OemDictCodeConstants.TMP_IMPORT_STATUS_02);
		// po1.setTimestamp("CREATE_DATE", format);
		// po1.saveIt();

		List<TtVsUploadCommonResourcePO> tmpList = TtVsUploadCommonResourcePO.findAll();
		for (int i = 0; i < tmpList.size(); i++) {
			TtVsUploadCommonResourcePO tvucPO = tmpList.get(i);
			TmVehiclePO tmPO = TmVehiclePO.findFirst("VIN=?", tvucPO.get("Vin").toString());
			// 非普通用途车辆，OTD在资源分配后，系统需要默认锁定车辆信息
			if (tmPO.get("VEHICLE_USAGE") == null) {

				tmPO.setString("VEHICLE_USAGE", "");
			}
			if (!tmPO.get("VEHICLE_USAGE").equals("")
					&& !tmPO.get("VEHICLE_USAGE").equals(OemDictCodeConstants.VEHICLE_USE_01.toString())) {
				List<TtResourceRemarkPO> list = TtResourceRemarkPO.find("VIN=?", tmPO.get("Vin"));
				if (list.size() > 0) {

					TtResourceRemarkPO.update("Update_By=?,Update_Date=?", "vin=?", loginInfo.getUserId(), format,
							tmPO.get("Vin"));
					// valuePO.setInteger("Is_Lock",new Integer(1));
					// valuePO.setInteger("Update_By",loginInfo.getUserId());
					// valuePO.setTimestamp("Update_Date",format);

				} else {
					TtResourceRemarkPO trrPO = new TtResourceRemarkPO();
					trrPO.setInteger("Remark", new Integer(0));
					trrPO.setString("Other_Remark", "");
					trrPO.setString("Vin", tmPO.get("Vin"));
					trrPO.setInteger("Is_Lock", new Integer(1));
					trrPO.setInteger("Create_By", loginInfo.getUserId());
					trrPO.setTimestamp("Create_Date", format);
					trrPO.setInteger("Update_By", loginInfo.getUserId());
					trrPO.setTimestamp("Update_Date", format);
					trrPO.saveIt();
				}
			}
			// 查询是否已指派了订单
			List<Map> mList = dao.findZdrrList(tvucPO.get("Vin").toString());
			Map<String, Object> map = new HashMap<String, Object>();
			if (mList.size() > 0) {
				map = mList.get(0);
				TtVsOrderPO tvoPO = new TtVsOrderPO();
				tvoPO.setLong("Order_Id", new Long(map.get("ORDER_ID").toString()));
				tvoPO.setString("VIN", tvucPO.get("Vin"));

				tvoPO.setLong("Dealer_Id", new Long(0));
				tvoPO.setInteger("Order_Type", new Integer(0));
				tvoPO.setInteger("Order_Status", OemDictCodeConstants.ORDER_STATUS_08);
				tvoPO.saveIt();
				TtVsAppointOrderDcsPO.update("Org_Id=?,Org_Type=?,Status=?", "Order_Id=?", new Long(0), new Long(0),
						new Integer(0), OemDictCodeConstants.STATUS_DISABLE, tvoPO.get("Order_Id").toString());
				// 更新车辆表 车辆状态为ZBIL
				TmVehiclePO tmvPO = new TmVehiclePO();
				tmvPO.setString("Vin", tvucPO.get("Vin"));
				tmvPO.setInteger("Org_Type", OemDictCodeConstants.ORG_TYPE_OEM);
				tmvPO.setLong("Dealer_Id", new Long(0));
				tmvPO.setLong("Org_Id", new Long(0));
				tmvPO.setInteger("Node_Status", OemDictCodeConstants.VEHICLE_NODE_08);
				tmvPO.setTimestamp("Node_Date", format);
				tmvPO.setInteger("Update_By", loginInfo.getUserId());
				tmvPO.setTimestamp("Update_Date", format);
				tmvPO.saveIt();
				// 记录日志
				TtVsMatchCheckPO mcPo = new TtVsMatchCheckPO(); // 匹配更改日志表
				mcPo.setLong("Order_Id", new Long(map.get("ORDER_ID").toString()));
				mcPo.setLong("Old_Vehicle_Id", map.get("VEHICLE_ID").toString());// 原车辆ID
				mcPo.setLong("Chg_Vehicle_Id", tmPO.get("VEHICLE_ID"));// 改变车辆ID
				mcPo.setLong("Update_By", loginInfo.getUserId());// 操作人
				mcPo.setTimestamp("Update_Date", format);// 操作时间
				mcPo.setInteger("Cancel_Type", 1001);// 1001订单取消 1002 订单撤单
				mcPo.setString("CancelReason", "重新分配");
				mcPo.insert();
			}
			List<TmOrgPO> tmList = TmOrgPO.find("ORG_CODE=?", tvucPO.get("Resource_Scope"));

			// TmOrgPO tmpo = tmList.get(0);
			List<Map> factorys = dao.getFactoryId(tvucPO.get("Vin").toString());
			long factoryid = 0;
			if (factorys.size() > 0) {
				factorys.get(0);
				String tmp = factorys.get(0).get("COMMON_DETAIL_ID").toString();
				factoryid = Long.valueOf(tmp);
			}

			/* 公共资源上传成功以后直接下发 (原来的作废 重新生成新的订单) */
			List<Map> tvcrList = dao.findZdrrList2(tvucPO.get("Vin").toString());
			Map<String, Object> map2 = new HashMap<String, Object>();
			// 如果已存在记录
			if (tvcrList.size() > 0) {
				map2 = tvcrList.get(0);
				// 原来的公共资源设为"已取消"
				TtVsCommonResourcePO.update("Status=?,Update_Date=?", "COMMON_ID=? and Vehicle_Id=? and Status=?",
						OemDictCodeConstants.COMMON_RESOURCE_STATUS_03, format, map2.get("COMMON_ID").toString(),
						map2.get("Vehicle_Id").toString(), OemDictCodeConstants.COMMON_RESOURCE_STATUS_02);
				// 公共资源明细状态设为"无效"
				TtVsCommonResourceDetailPO.update("Status=?", "COMMON_ID=? and VEHICLE_ID=?",
						OemDictCodeConstants.STATUS_DISABLE, map2.get("COMMON_ID").toString(),
						map2.get("VEHICLE_ID").toString());
				// 将车辆表经销商ID设为空
				TmVehiclePO.update("Dealer_Id=?,Node_Date=?", "vin=?", new Long(0), format, tvucPO.get("Vin"));
				// 记录日志
				TtVsMatchCheckPO mcPo = new TtVsMatchCheckPO(); // 匹配更改日志表
				mcPo.setLong("Old_Vehicle_Id", (new Long(map2.get("VEHICLE_ID").toString())));// 原车辆ID
				mcPo.setLong("Chg_Vehicle_Id", tmPO.get("Vehicle_Id"));// 改变车辆ID
				mcPo.setInteger("Update_By", loginInfo.getUserId());// 操作人
				mcPo.setTimestamp("Update_Date", format);// 操作时间
				mcPo.setInteger("Cancel_Type", 1001);// 1001订单取消 1002 订单撤单
				mcPo.setString("Cancel_Reason", "重新分配");
				mcPo.insert();
			}

			// 插入公共资源记录
			TtVsCommonResourcePO tvcr = new TtVsCommonResourcePO();
			tvcr.setLong("Vehicle_Id", tmPO.get("Vehicle_Id"));
			tvcr.setLong("Create_By", loginInfo.getUserId());
			tvcr.setTimestamp("Create_Date", tvucPO.get("Create_Date"));
			tvcr.setInteger("Resource_Scope", tmPO.get("Org_Id"));
			tvcr.setInteger("Status", OemDictCodeConstants.COMMON_RESOURCE_STATUS_02);// 资源状态（未下发1，已下发2，已取消3）
			tvcr.setInteger("Type", new Integer(tvucPO.get("Resource_Type").toString()));
			tvcr.setTimestamp("Issued_Date", format);
			tvcr.insert();

			// 插入公共资源明细记录
			TtVsCommonResourceDetailPO tvcrd = new TtVsCommonResourceDetailPO();
			tvcrd.setLong("Common_Id", tvcr.get("Common_Id"));
			tvcrd.setInteger("Create_By", loginInfo.getUserId());
			tvcrd.setTimestamp("Create_Date", tvucPO.get("Create_Date"));
			tvcrd.setLong("FACTORY_ORDER_ID", factoryid);// 工厂id
			tvcrd.setInteger("Status", OemDictCodeConstants.STATUS_ENABLE);// 资源有效状态
			tvcrd.setInteger("Type", new Integer(tvucPO.get("Resource_Type").toString()));
			tvcrd.setLong("Vehicle_Id", tmPO.get("Vehicle_Id"));// 车辆ID
			tvcrd.insert();

			// 车辆表车辆节点设为"资源已分配"

			// 现货更新车辆节点，期货不更新
			if (tvucPO.get("Resource_Type").toString()
					.equals(OemDictCodeConstants.COMMON_RESOURCE_TYPE_02.toString())) {
				// tvPO.setNodeStatus(OemDictCodeConstants.VEHICLE_NODE_18);
				TmVehiclePO.update("Node_Status=?", "vin=?", OemDictCodeConstants.VEHICLE_NODE_18, tvucPO.get("Vin"));
			}
			// tvPO.setNodeDate(format);
			TmVehiclePO.update("Node_Date=?", "vin=?", format, tvucPO.get("Vin"));

			// 记录详细车籍
			TtVsVhclChngPO vhclChng = new TtVsVhclChngPO();
			vhclChng.setLong("Vehicle_Id", tmPO.get("Vehicle_Id"));
			vhclChng.setInteger("Change_Code", OemDictCodeConstants.VEHICLE_CHANGE_TYPE_24);
			vhclChng.setString("Change_Desc", "下发公共资源");
			vhclChng.setTimestamp("Change_Date", format);
			vhclChng.setInteger("Create_By", loginInfo.getUserId());
			vhclChng.setTimestamp("Create_Date", format);
			vhclChng.insert();

			// 写入接口表，将期货车辆（VIN+车型）传给中进CATC系统
			// 需要把代码的逻辑同步到存储过程ORDER_IMPORT_SAVE_COMN相应位置
			// 不写了2014/02/11

		}

	}

}
