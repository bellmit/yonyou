package com.yonyou.dms.vehicle.service.vehicleAllot;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dcs.gacfca.SADCS007Cloud;
import com.yonyou.dms.common.domains.PO.basedata.TiWxVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TmDealerPO;
import com.yonyou.dms.common.domains.PO.basedata.TmVehiclePO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVehicleTransferPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsVhclChngPO;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.vehicle.PO.vehicleAllot.TtVsVehicleTransferChkPO;
import com.yonyou.dms.vehicle.dao.vehicleAllotDao.VehicleAllotApproveDao;


@Service
public class VehicleAllotServiceImpl implements VehicleAllotApproveService {
	
	@Autowired
	private VehicleAllotApproveDao approveDao;
	@Autowired
	SADCS007Cloud sadcs007cloud;

	@Override
	public PageInfoDto searchVehicleAllotApprove(Map<String, String> param) {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		PageInfoDto pageInfo = approveDao.searchVehicleAllotApprove(param,loginInfo);
		return pageInfo;
	}

	@Override
	public List<Map> selectSeriesName(Map<String, String> params) {
		List<Map> list = approveDao.selectSeriesName(params);
		return list;
	}

	@Override
	public List<Map> selectBrandName(Map<String, String> params) {
		List<Map> list = approveDao.selectBrandName(params);
		return list;
	}

	@Override
	public List<Map> selectGroupName(Map<String, String> params) {
		List<Map> list = approveDao.selectGroupName(params);
		return list;
	}

	@Override
	public List<Map> selectModelYear(Map<String, String> params) {
		List<Map> list = approveDao.selectModelYear(params);
		return list;
	}

	@Override
	public List<Map> selectColorName(Map<String, String> params) {
		List<Map> list = approveDao.selectColorName(params);
		return list;
	}

	@Override
	public List<Map> selectTrimName(Map<String, String> params) {
		List<Map> list = approveDao.selectTrimName(params);
		return list;
	}

	@Override
	public Map<String, String> checkStatus(String[] ids, String opinion, String result) throws Exception {
		Map<String, String> resultMap = new HashMap<String, String>();
		LoginInfoDto loginUser = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Integer checkStatus = OemDictCodeConstants.TRANSFER_CHECK_STATUS_03;
		Date currentTime = new Date();
		if (null != ids && ids.length > 0) {
			for (int i = 0; i < ids.length; i++) {
				// 查找TT_VS_VEHICLE_TRANSFER记录
				TtVsVehicleTransferPO transferPO = TtVsVehicleTransferPO.findById(ids[i]);
				if (transferPO != null) {
					// 查找TT_VS_VEHICLE_TRANSFER_CHK记录
//					List<TtVsVehicleTransferChkPO> chkList = approveDao.findChkList(ids[i]);
					List<TtVsVehicleTransferChkPO> chkList = TtVsVehicleTransferChkPO.find("TRANSFER_ID = ? ", ids[i]);
					Integer transRegionalFlag = transferPO.getInteger("TRANS_REGIONAL_FLAG");
					Integer status = transferPO.getInteger("CHECK_STATUS");// 记录的checkStatus

					if (transRegionalFlag == 0) {// 小区内调拨
						/*
						 * 小区内调拨
						 */
						if ("1".equals(result)) {
							checkStatus = OemDictCodeConstants.TRANSFER_CHECK_STATUS_02;// 同意调拨
						} else {
							checkStatus = OemDictCodeConstants.TRANSFER_CHECK_STATUS_03;// 驳回调拨
						}
						transferPO.setInteger("CHECK_STATUS", checkStatus);
						transferPO.setInteger("IS_SEND", 0);
						transferPO.setLong("UPDATE_BY", loginUser.getUserId());
						transferPO.setTimestamp("UPDATE_DATE", currentTime);
						transferPO.saveIt();

						//新增TtVsVehicleTransferChkPO
						TtVsVehicleTransferChkPO chkPO = new TtVsVehicleTransferChkPO();
						chkPO.setLong("TRANSFER_ID", Long.parseLong(ids[i]));
						chkPO.setLong("CHECK_ORG_ID", loginUser.getOrgId()); // 调出审核部门
						chkPO.setLong("CHECK_POSITION_ID", loginUser.getPoseId()); // 调出审核职位
						chkPO.setLong("CHECK_USER_ID", loginUser.getUserId()); // 调出审核人
						chkPO.setTimestamp("CHECK_DATE", currentTime); // 调出审核日期
						chkPO.setString("CHECK_DESC", opinion); // 调出审核描述
						chkPO.setInteger("CHECK_STATUS", checkStatus); // 审核结果
						chkPO.setInteger("APPROVAL_RECORD", 1); // 审批记录
						chkPO.setLong("CREATE_BY", loginUser.getUserId()); // 创建人
						chkPO.setTimestamp("CREATE_DATE", currentTime); // 创建日期
						chkPO.saveIt();

					} else if (transRegionalFlag == 1) {// 小区间调拨
						/*
						 * 小区间调拨
						 */
						if (result.equals("1")) {
							if ((OemDictCodeConstants.TRANSFER_CHECK_STATUS_05).equals(status)) {
								checkStatus = OemDictCodeConstants.TRANSFER_CHECK_STATUS_02; // 审核通过
							} else {
								checkStatus = OemDictCodeConstants.TRANSFER_CHECK_STATUS_05; // 审核中
							}
						} else {
							checkStatus = OemDictCodeConstants.TRANSFER_CHECK_STATUS_03; // 审核驳回
						}

						transferPO.setInteger("CHECK_STATUS", checkStatus);
						transferPO.setInteger("IS_SEND", 0);
						transferPO.setLong("UPDATE_BY", loginUser.getUserId());
						transferPO.setTimestamp("UPDATE_DATE", currentTime);
						transferPO.saveIt();

						if (chkList.size() > 0) {
							/*
							 * 调入小区审批
							 */
							//更新TtVsVehicleTransferChkPO
							String updates = "IN_SMALL_ORG_ID = ?,IN_SMALL_POSITION_ID = ?,IN_SMALL_USER_ID = ?,"
									+ "IN_SMALL_CHK_DATE = ?,IN_SMALL_CHK_DESC = ?,CHECK_STATUS = ?,APPROVAL_RECORD = ?,"
									+ "UPDATE_BY = ?,UPDATE_DATE = ?";
							String conditions = "TRANSFER_ID = ?";
							List<Object> params = new ArrayList<>();
							params.add( loginUser.getOrgId()); // 调入小区审核部门
							params.add( loginUser.getPoseId()); // 调入小区审核职位
							params.add( loginUser.getUserId()); // 调入小区审核人
							params.add( currentTime); // 调入小区审核日期
							params.add( opinion); // 调入小区审核描述
							params.add( checkStatus); // 审核结果
							params.add( 1); // 审批记录
							params.add( loginUser.getUserId()); // 更新人
							params.add( currentTime); // 更新日期
							params.add(	ids[i]);//transferId
							TtVsVehicleTransferChkPO.update(updates, conditions, params.toArray());
						} else {
							/*
							 * 调出小区审批
							 */
							//新增TtVsVehicleTransferChkPO
							TtVsVehicleTransferChkPO chkPO = new TtVsVehicleTransferChkPO();
							chkPO.setLong("TRANSFER_ID", Long.parseLong(ids[i]));
							chkPO.setLong("OUT_SMALL_ORG_ID", loginUser.getOrgId()); // 调出小区审核部门
							chkPO.setLong("OUT_SMALL_POSITION_ID", loginUser.getPoseId()); // 调出小区审核职位
							chkPO.setLong("OUT_SMALL_USER_ID", loginUser.getUserId()); // 调出小区小区审核人
							chkPO.setTimestamp("OUT_SMALL_CHK_DATE", currentTime); // 调出小区审核日期
							chkPO.setString("OUT_SMALL_CHK_DESC", opinion); // 调出小区审核描述
							chkPO.setInteger("CHECK_STATUS", checkStatus); // 审核结果
							chkPO.setInteger("APPROVAL_RECORD", 1); // 审批记录
							chkPO.setLong("CREATE_BY", loginUser.getUserId()); // 更新人
							chkPO.setTimestamp("CREATE_DATE", currentTime); // 更新日期
							chkPO.saveIt();
						}

					} else if (transRegionalFlag == 2) {// 大区间调拨
						/*
						 * 大区间调拨
						 */
						TtVsVehicleTransferChkPO chk = null;
						if (chkList != null && chkList.size() > 0) {
							chk = chkList.get(0);
						}

						Integer record = null == chk ? 0 : chk.getInteger("APPROVAL_RECORD");

						if (result.equals("1")) {
							if ((OemDictCodeConstants.TRANSFER_CHECK_STATUS_05).equals(status) && record == 3) {
								checkStatus = OemDictCodeConstants.TRANSFER_CHECK_STATUS_02; // 审核通过
							} else {
								checkStatus = OemDictCodeConstants.TRANSFER_CHECK_STATUS_05; // 审核中
							}
						} else {
							checkStatus = OemDictCodeConstants.TRANSFER_CHECK_STATUS_03; // 审核驳回
						}

						transferPO.setInteger("CHECK_STATUS", checkStatus);
						transferPO.setInteger("IS_SEND", 0);
						transferPO.setLong("UPDATE_BY", loginUser.getUserId());
						transferPO.setTimestamp("UPDATE_DATE", currentTime);
						transferPO.saveIt();

						if (chkList.size() > 0) {
							if (chk.getInteger("APPROVAL_RECORD") == 1) {
								/*
								 * 调出大区审批
								 */
								//更新TtVsVehicleTransferChkPO
								String updates = "OUT_BIG_ORG_ID = ?,OUT_BIG_POSITION_ID = ?,OUT_BIG_USER_ID = ?,"
										+ "OUT_BIG_CHK_DATE = ?,OUT_BIG_CHK_DESC = ?,CHECK_STATUS = ?,APPROVAL_RECORD = ?,"
										+ "UPDATE_BY = ?,UPDATE_DATE = ?";
								String conditions = "TRANSFER_ID = ?";
								List<Object> params = new ArrayList<>();
								params.add(loginUser.getOrgId()); // 调出大区审核部门
								params.add(loginUser.getPoseId()); // 调出大区审核职位
								params.add(loginUser.getUserId()); // 调出大区审核人
								params.add(currentTime); // 调出大区审核日期
								params.add(opinion); // 调出大区审核描述
								params.add(checkStatus); // 审核结果
								params.add(2); // 审批记录
								params.add(loginUser.getUserId()); // 更新人
								params.add(currentTime); // 更新日期
								params.add(ids[i]);//transferId
								TtVsVehicleTransferChkPO.update(updates, conditions, params.toArray());
							} else if (chk.getInteger("APPROVAL_RECORD") == 2) {
								/*
								 * 调入小区审批
								 */
								//更新TtVsVehicleTransferChkPO
								String updates = "IN_SMALL_ORG_ID = ?,IN_SMALL_POSITION_ID = ?,IN_SMALL_USER_ID = ?,"
										+ "IN_SMALL_CHK_DATE = ?,IN_SMALL_CHK_DESC = ?,CHECK_STATUS = ?,APPROVAL_RECORD = ?,"
										+ "UPDATE_BY = ?,UPDATE_DATE = ?";
								String conditions = "TRANSFER_ID = ?";
								List<Object> params = new ArrayList<>();
								params.add(loginUser.getOrgId()); // 调入小区审核部门
								params.add(loginUser.getPoseId()); // 调入小区审核职位
								params.add(loginUser.getUserId()); // 调入小区审核人
								params.add(currentTime); // 调入小区审核日期
								params.add(opinion); // 调入小区审核描述
								params.add(checkStatus); // 审核结果
								params.add(3); // 审批记录
								params.add(loginUser.getUserId()); // 更新人
								params.add(currentTime); // 更新日期
								params.add(ids[i]);//transferId
								TtVsVehicleTransferChkPO.update(updates, conditions, params.toArray());
							} else if (chk.getInteger("APPROVAL_RECORD") == 3) {
								/*
								 * 调入大区审批
								 */
								//更新TtVsVehicleTransferChkPO
								String updates = "IN_BIG_ORG_ID = ?,IN_BIG_POSITION_ID = ?,IN_BIG_USER_ID = ?,"
										+ "IN_BIG_CHK_DATE = ?,IN_BIG_CHK_DESC = ?,CHECK_STATUS = ?,APPROVAL_RECORD = ?,"
										+ "UPDATE_BY = ?,UPDATE_DATE = ?";
								String conditions = "TRANSFER_ID = ?";
								List<Object> params = new ArrayList<>();
								params.add(loginUser.getOrgId()); // 调入大区审核部门
								params.add(loginUser.getPoseId()); // 调入大区审核职位
								params.add(loginUser.getUserId()); // 调入大区审核人
								params.add(currentTime); // 调入大区审核日期
								params.add(opinion); // 调入大区审核描述
								params.add(checkStatus); // 审核结果
								params.add(4); // 审批记录
								params.add(loginUser.getUserId()); // 更新人
								params.add(currentTime); // 更新日期
								params.add(ids[i]);//transferId
								TtVsVehicleTransferChkPO.update(updates, conditions, params.toArray());
							} else {

							}
						} else {
							/*
							 * 调出小区审批
							 */
							//新增TtVsVehicleTransferChkPO
							TtVsVehicleTransferChkPO chkPO = new TtVsVehicleTransferChkPO();
							chkPO.setLong("TRANSFER_ID", Long.parseLong(ids[i]));
							chkPO.setLong("CHECK_ORG_ID", loginUser.getOrgId()); // 调出小区审核部门
							chkPO.setLong("CHECK_POSITION_ID", loginUser.getPoseId()); // 调出小区审核职位
							chkPO.setLong("CHECK_USER_ID", loginUser.getUserId()); // 调出小区小区审核人
							chkPO.setTimestamp("CHECK_DATE", currentTime); // 调出小区审核日期
							chkPO.setTimestamp("CREATE_DATE", currentTime); // 调出小区审核日期
							chkPO.setString("CHECK_DESC", opinion); // 调出小区审核描述
							chkPO.setInteger("CHECK_STATUS", checkStatus); // 审核结果
							chkPO.setInteger("APPROVAL_RECORD", 1); // 审批记录
							chkPO.setLong("CREATE_BY", loginUser.getUserId()); // 更新人
							chkPO.setTimestamp("CREATE_DATE", currentTime); // 更新日期
							chkPO.saveIt();
						}
					} else {

					}
				}

				if (transferPO != null) {
					// 调拨审核通过后写此接口表
					if (checkStatus == OemDictCodeConstants.TRANSFER_CHECK_STATUS_02
							&& approveDao.isImportVehicle(transferPO.getLong("VEHICLE_ID"))) {
						//更新TmVehiclePO中车辆dealer_id
						TmVehiclePO.update("DEALER_ID = ?,UPDATE_BY = ?,UPDATE_DATE = ?", 
								"VEHICLE_ID = ?", 
								transferPO.getLong("IN_DEALER_ID"),loginUser.getUserId(),currentTime,
								transferPO.getLong("VEHICLE_ID"));
					}

					// 详细车籍
					TtVsVhclChngPO vehicleChangPO = new TtVsVhclChngPO();
					vehicleChangPO.setLong("VEHICLE_ID", transferPO.getLong("VEHICLE_ID"));
					vehicleChangPO.setLong("CHANGE_CODE", OemDictCodeConstants.VEHICLE_CHANGE_TYPE_14);
					vehicleChangPO.setString("CHANGE_DESC", "调拨入库");
					vehicleChangPO.setTimestamp("CHANGE_DATE", currentTime);
					vehicleChangPO.setLong("CREATE_BY", loginUser.getUserId());
					vehicleChangPO.setTimestamp("CREATE_DATE", currentTime);
					vehicleChangPO.setInteger("RESOURCE_TYPE", OemDictCodeConstants.ORG_TYPE_DEALER);
					vehicleChangPO.setLong("RESOURCE_ID", transferPO.getLong("OUT_DEALER_ID"));
					vehicleChangPO.saveIt();
					
					/*
					 * 调用车辆调拨接口 Begin
					 */
					Long inDealerId = transferPO.getLong("IN_DEALER_ID");
					Long outDealerId = transferPO.getLong("OUT_DEALER_ID");
					List<Long> dealerIdList = new LinkedList<Long>();
					dealerIdList.add(inDealerId);
					dealerIdList.add(outDealerId);
					// 下发
					sadcs007cloud.execute(dealerIdList, vehicleChangPO.getLong("VEHICLE_ID"),inDealerId, outDealerId);

					transferPO.setInteger("IS_SEND", 1);
					transferPO.saveIt();
					/*
					 * 调用车辆调拨接口 End..
					 */

					TmVehiclePO tmv = TmVehiclePO.findById(transferPO.getLong("VEHICLE_ID"));
					TmDealerPO tdPO = TmDealerPO.findById(inDealerId);

					// 车辆验收表中有isUpdate写1，否则0
					TiWxVehiclePO tPO = new TiWxVehiclePO();
					tPO.setLong("INSPECTION_ID", transferPO.getLong("TRANSFER_ID"));
					tPO.setString("VIN", tmv.getString("VIN"));
					tPO.setLong("DEALER_CODE", tdPO.getLong("DEALER_CODE"));
					tPO.setString("IS_SCAN", "0");
					tPO.setString("IS_UPDATE", "1");

					List<Map> mList = approveDao.findMaterial(tmv.getLong("MATERIAL_ID"));
					if (mList.size() > 0) {
						Map<String, Object> map = mList.get(0);
						tPO.setString("BRAND_ID", map.get("BRAND_CODE").toString());
						tPO.setString("SERIES_ID", map.get("SERIES_CODE").toString());
						tPO.setString("MODEL_ID", map.get("GROUP_CODE").toString());
						tPO.setString("COLOR_ID", map.get("COLOR_CODE").toString());
					}
					tPO.setLong("CREATE_BY", loginUser.getUserId());
					tPO.setTimestamp("CREATE_DATE", currentTime);
					tPO.saveIt();
				} else {
					resultMap.put("res", "fail");
					resultMap.put("resMsg", "数据错误请重新查询后操作");
				}
			}
		} else {
			resultMap.put("res", "fail");
			resultMap.put("resMsg", "请选择需操作的数据");
		}
		resultMap.put("res", "success");
		return resultMap;
	}
	

}
