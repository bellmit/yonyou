package com.yonyou.dms.vehicle.dao.factoryOrderDao;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.yonyou.dms.framework.DAO.OemBaseDAO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.domains.DTO.orderManager.ResourceOrderUploadDTO;
import com.yonyou.dms.vehicle.domains.PO.orderManager.TtVsUploadCommonResourcePO;

/**
 * 
 * @author 廉兴鲁
 *
 */
@Repository
@SuppressWarnings("all")
public class ResourceExcelDao extends OemBaseDAO {

	// 临时表数据回显
	public List<Map> importShow(String orderType) {
		String type = CommonUtils.checkNull(orderType);
		List<Map> tmpList = selectComResExcel(type);
		return tmpList;
	}

	public List<Map<String, Object>> checkData(String type) {
		StringBuffer sql1 = new StringBuffer();

		sql1.append("select VIN from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql1.append("	where exists (select 1 from TM_DEALER where DEALER_CODE=t.RESOURCE_SCOPE and DEALER_TYPE="
				+ OemDictCodeConstants.DEALER_TYPE_DVS + ")\n");
		List<Map> map1 = OemDAOUtil.findAll(sql1.toString(), null);
		StringBuffer sql2 = new StringBuffer();
		sql2.append("select VIN from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql2.append("	where exists (select 1 from TM_ORG where ORG_CODE=t.RESOURCE_SCOPE  and DUTY_TYPE="
				+ OemDictCodeConstants.DUTY_TYPE_LARGEREGION + " and BUSS_TYPE=" + OemDictCodeConstants.ORG_BUSS_TYPE_01
				+ ")\n");
		List<Map> map2 = OemDAOUtil.findAll(sql2.toString(), null);

		StringBuffer sql3 = new StringBuffer();
		sql3.append("select VIN from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql3.append("	where exists (select 1 from TC_ORG_BIG where TOTAL_ORG_CODE=t.RESOURCE_SCOPE) \n");
		List<Map> map3 = OemDAOUtil.findAll(sql3.toString(), null);
		// ResourceOrderUploadDTO row = new ResourceOrderUploadDTO();
		List<Map<String, Object>> tvypDTOList = new ArrayList<Map<String, Object>>();
		if (map1.size() > 0 && map2.size() > 0 && map3.size() > 0) {
			// ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
			Map<String, Object> errorMap = new HashMap<>();
			// rowDto.setRowNO(0);
			// rowDto.setErrorMsg("资源范围不一致");
			errorMap.put("rowNO", 0);
			errorMap.put("errorMsg", "资源范围不一致");
			tvypDTOList.add(errorMap);
			return tvypDTOList;
		} else {
			// 1.先判断资源是否存在
			List<Map> isResList = findNoExistsResource();
			if (isResList.size() > 0 && isResList != null) {
				// TtVsUploadCommonResourcePO.deleteAll();
				for (Map map : isResList) {
					// ResourceOrderUploadDTO rowDto = new
					// ResourceOrderUploadDTO();
					// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
					// rowDto.setErrorMsg("资源范围:" +
					// map.get("RESOURCE_SCOPE").toString() + "不存在");
					Map<String, Object> errorMap = new HashMap<>();
					errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
					errorMap.put("errorMsg", "资源范围:" + map.get("RESOURCE_SCOPE").toString() + "不存在");
					tvypDTOList.add(errorMap);
				}
				return tvypDTOList;

			} else {
				// 1.资源范围都存在的情况下，再判断资源范围是否一致
				String resource = findType();
				if (resource.equals("1")) {
					String orderType = findOrderType();
					// 期货
					if (type.equals(OemDictCodeConstants.COMMON_RESOURCE_TYPE_01.toString())) {
						// 期货资源分配给经销商
						if (orderType.equals("1")) {
							SimpleDateFormat sf2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
							String currdate = sf2.format(new Date());
							List<Map<String, Object>> errorList = checkData(type, 1);
							if (errorList == null && errorList.size() == 0) {
								ArrayList<ResourceOrderUploadDTO> errorList2 = checkDataTwo(type);
								for (ResourceOrderUploadDTO resourceOrderUploadDTO : errorList2) {
									Map<String, Object> errorMap = new HashMap<>();
									errorMap.put("rowNO", resourceOrderUploadDTO.getRowNO());
									errorMap.put("errorMsg", resourceOrderUploadDTO.getErrorMsg());
									tvypDTOList.add(errorMap);
									return tvypDTOList;
								}
							}
							// 期货资源分配给区域
						} else {
							List<Map<String, Object>> errorList = checkData(type, 2);
							if (errorList.size() != 0 && errorList != null) {
								for (Map<String, Object> map : errorList) {
									Map<String, Object> errorMap = new HashMap<>();
									errorMap.put("rowNO", map.get("rowNO"));
									errorMap.put("errorMsg", map.get("errorMsg"));
									tvypDTOList.add(errorMap);
									return tvypDTOList;
								}
								TtVsUploadCommonResourcePO.deleteAll();

							} else {
								ArrayList<ResourceOrderUploadDTO> errorList2 = checkDataTwo(type);
								for (ResourceOrderUploadDTO resourceOrderUploadDTO : errorList2) {
									Map<String, Object> errorMap = new HashMap<>();
									errorMap.put("rowNO", resourceOrderUploadDTO.getRowNO());
									errorMap.put("errorMsg", resourceOrderUploadDTO.getErrorMsg());
									tvypDTOList.add(errorMap);
									return tvypDTOList;
								}
							}
						}
						// 现货
					} else {
						// 指派 订单导入
						if (orderType.equals("1")) {

							ArrayList<ResourceOrderUploadDTO> errorL = checkDataApp(type);

							if (errorL.size() != 0 && errorL != null) {
								for (ResourceOrderUploadDTO resourceOrderUploadDTO : errorL) {
									Map<String, Object> errorMap = new HashMap<>();
									errorMap.put("rowNO", resourceOrderUploadDTO.getRowNO());
									errorMap.put("errorMsg", resourceOrderUploadDTO.getErrorMsg());
									tvypDTOList.add(errorMap);
									return tvypDTOList;
								}
								TtVsUploadCommonResourcePO.deleteAll();
							} else {
								List<ResourceOrderUploadDTO> errorList2 = checkDataTwo(type);
								if (errorList2.size() != 0 && errorList2 != null) {
									for (ResourceOrderUploadDTO resourceOrderUploadDTO : errorList2) {
										Map<String, Object> errorMap = new HashMap<>();
										errorMap.put("rowNO", resourceOrderUploadDTO.getRowNO());
										errorMap.put("errorMsg", resourceOrderUploadDTO.getErrorMsg());
										tvypDTOList.add(errorMap);
										return tvypDTOList;
									}

								}
							}
						} else {
							// 现货导入

							List<Map<String, Object>> errorList = checkData(type, 2);

							if (errorList.size() != 0 && errorList != null) {
								List<ResourceOrderUploadDTO> errorList2 = checkDataTwo(type);
								for (ResourceOrderUploadDTO resourceOrderUploadDTO : errorList2) {
									Map<String, Object> errorMap = new HashMap<>();
									errorMap.put("rowNO", resourceOrderUploadDTO.getRowNO());
									errorMap.put("errorMsg", resourceOrderUploadDTO.getErrorMsg());
									tvypDTOList.add(errorMap);
									return tvypDTOList;
								}
							}
						}
					}
				} else {
					TtVsUploadCommonResourcePO.deleteAll();
					// ResourceOrderUploadDTO rowDto = new
					// ResourceOrderUploadDTO();
					// rowDto.setRowNO(0);
					// rowDto.setErrorMsg("资源范围不统一");
					// tvypDTOList.add(rowDto);
					Map<String, Object> errorMap = new HashMap<>();
					errorMap.put("rowNO", 0);
					errorMap.put("errorMsg", "资源范围不统一");
					tvypDTOList.add(errorMap);
					return tvypDTOList;
				}
			}

		}
		return null;

	}

	/**
	 * 指派订单判断
	 * 
	 * @param type
	 * @return
	 */
	private ArrayList<ResourceOrderUploadDTO> checkDataApp(String type) {
		ArrayList<ResourceOrderUploadDTO> tvypDTOList = new ArrayList<ResourceOrderUploadDTO>();
		List<Map> bkList = findBlankData(type);
		if (bkList.size() > 0 && bkList != null) {
			for (Map map : bkList) {
				ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
				rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
				rowDto.setErrorMsg("资源代码为空");
				tvypDTOList.add(rowDto);
			}
			return tvypDTOList;

		}
		List<Map> rList = findRepeatData(type);

		if (rList.size() > 0 && rList != null) {
			for (Map map : rList) {
				ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
				rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
				rowDto.setErrorMsg("与第" + map.get("ROW_NO") + "行数据重复!");
				tvypDTOList.add(rowDto);
			}
			return tvypDTOList;

		} else {
			List<Map> bList = findRepeatData2(type);
			if (bList.size() > 0 && bList != null) {
				for (Map map : bList) {
					ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
					rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
					rowDto.setErrorMsg("与第" + map.get("ROW_NO") + "行数据重复!");
					tvypDTOList.add(rowDto);
				}
				return tvypDTOList;
			} else {
				/**
				 * 优化后1-8
				 */
				// 1.*对于正确的VIN码判断是否在车辆表中存在
				List<Map> vehList = findNoExistsVin();
				if (vehList.size() > 0 && vehList != null) {
					for (int i = 0; i < vehList.size(); i++) {
						ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
						rowDto.setRowNO(Integer.parseInt(vehList.get(i).get("ROW_NO").toString()));
						rowDto.setErrorMsg("VIN号:" + vehList.get(i).get("VIN") + "不存在");
						tvypDTOList.add(rowDto);
					}
					return tvypDTOList;
				}
				// 2.*验证"资源代码"是否存在
				List<Map> isResList = findNoExistsResource();
				if (isResList.size() > 0) {
					for (int i = 0; i < isResList.size(); i++) {
						ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
						rowDto.setRowNO(Integer.parseInt(vehList.get(i).get("ROW_NO").toString()));
						rowDto.setErrorMsg("资源范围:" + vehList.get(i).get("VIN") + "不存在");
						tvypDTOList.add(rowDto);
					}
					return tvypDTOList;
				}
				// 3.*验证只能导入销售经销商
				List<Map> noSalesList = findNotSales(type);
				if (noSalesList != null) {
					for (int i = 0; i < noSalesList.size(); i++) {
						ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
						rowDto.setRowNO(Integer.parseInt(vehList.get(i).get("ROW_NO").toString()));
						rowDto.setErrorMsg("经销商:" + noSalesList.get(i).get("RESOURCE_SCOPE") + "不是整车销售经销商");
						tvypDTOList.add(rowDto);
					}
					return tvypDTOList;
				}
				// +是否生成有效订单
				List<Map> vinList = findIsOrderApp(type);
				if (null != vinList && vinList.size() > 0) {
					if (vinList.size() > 0) {
						for (int i = 0; i < vinList.size(); i++) {
							ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
							Map<String, Object> map = vinList.get(i);
							/*
							 * if(map.get("NODE_STATUS").toString().equals("0")&
							 * &map.get("ORDER_STATUS").toString().equals("0")){
							 * continue; }
							 */
							// String orderType =
							// CodeDict.getDictDescById(Constant.ORDER_TYPE.toString(),
							// map.get("ORDER_TYPE").toString());
							// String orderState =
							// CodeDict.getDictDescById(Constant.ORDER_STATUS.toString(),
							// map.get("ORDER_STATUS").toString());
							rowDto.setErrorMsg("vin号:" + map.get("VIN") + "订单已确认");
							tvypDTOList.add(rowDto);
						}
						return tvypDTOList;
					}
				}
				// 4.是否在车厂库存
				List<Map> istList = checkVinIsStore();
				if (istList.size() > 0 && istList != null) {

					for (Map map : istList) {
						ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
						rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
						rowDto.setErrorMsg("vin号:" + map.get("VIN") + "不在车厂库存");
						tvypDTOList.add(rowDto);
					}
					return tvypDTOList;

				}
				// 5.车辆在车厂库存，而且zbildate是否为空(在车厂库存且为空则报错)
				List<Map> istList2 = checkVinIsStore2();
				if (istList2.size() > 0 && istList2 != null) {
					for (Map map : istList2) {

						ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
						rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
						rowDto.setErrorMsg("vin号:" + map.get("VIN") + "未开票");
						tvypDTOList.add(rowDto);
					}
					// for (int i = 0; i < istList2.size(); i++) {
					// String string = vehList.get(i).get("ROW_NO").toString();
					// ResourceOrderUploadDTO rowDto = new
					// ResourceOrderUploadDTO();
					// rowDto.setRowNO(Integer.parseInt(vehList.get(i).get("ROW_NO").toString()));
					// rowDto.setErrorMsg("vin号:" + istList2.get(i).get("VIN") +
					// "未开票");
					// tvypDTOList.add(rowDto);
					// }
					return tvypDTOList;
				}
				// 8.判断该VIN是否锁定(资源备注)
				List<Map> isLockList = findComnsIsLock();
				if (isLockList.size() > 0 && isLockList != null) {
					for (Map map : isLockList) {
						ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
						rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
						rowDto.setErrorMsg("vin号:" + map.get("VIN") + "已锁定");
						tvypDTOList.add(rowDto);
					}
					return tvypDTOList;
				}
				// 9.判断该VIN是否在资源分配中待分配
				List<Map> isAllotList = findIsAllotList();
				if (isAllotList.size() > 0 && isAllotList != null) {
					for (int i = 0; i < isAllotList.size(); i++) {
						ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
						rowDto.setRowNO(Integer.parseInt(vehList.get(i).get("ROW_NO").toString()));
						rowDto.setErrorMsg("vin号:" + isAllotList.get(i).get("VIN") + "在【资源分配】中待审核");
						tvypDTOList.add(rowDto);
					}
					return tvypDTOList;
				}
				// 10.判断是否是进口车
				List<Map> importList = findIsNotImport();
				if (null != importList && importList.size() > 0) {
					for (int i = 0; i < importList.size(); i++) {
						ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
						Map map = importList.get(i);
						rowDto.setRowNO(Integer.parseInt(vehList.get(i).get("ROW_NO").toString()));
						rowDto.setErrorMsg("VIN号:" + map.get("VIN") + "不是进口车!");
						tvypDTOList.add(rowDto);
					}
					return tvypDTOList;
				}
			}
		}
		return tvypDTOList;
	}

	private List<Map> checkVinIsStore2() {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvuc.ROW_NO,tvuc.VIN from Tt_Vs_Upload_Common_Resource tvuc,TM_VEHICLE_DEC tv\n");
		sql.append("   where  tvuc.VIN=tv.VIN\n");
		sql.append("   	 and  tv.LIFE_CYCLE=" + OemDictCodeConstants.LIF_CYCLE_02 + "\n");
		sql.append("     and  tv.ZBIL_DATE is null\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> checkVinIsStore() {

		StringBuffer sql = new StringBuffer();
		sql.append("select tvuc.ROW_NO,tvuc.VIN from Tt_Vs_Upload_Common_Resource tvuc,TM_VEHICLE_Dec tv\n");
		sql.append("   where  tvuc.VIN=tv.VIN\n");
		sql.append("     and  tv.LIFE_CYCLE!=" + OemDictCodeConstants.LIF_CYCLE_02 + "\n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> selectComResExcel(String type) {
		StringBuffer sql = new StringBuffer("");
		sql.append("select t.* from (SELECT TMP.COMMONALITY_ID,TMP.ROW_NO,\n");
		sql.append(" (case when ORG_ID='" + OemDictCodeConstants.OEM_ACTIVITIES
				+ "' then '全国' else TOR.ORG_NAME end) RESOURCE_SCOPE,\n");
		sql.append("       TMP.VIN,\n");
		sql.append("       TMP.RESOURCE_TYPE,\n");
		sql.append("       TMP.CREATE_DATE,\n");
		sql.append("       TMP.UPDATE_DATE\n");
		sql.append("  FROM TT_VS_UPLOAD_COMMON_RESOURCE TMP,\n");
		sql.append("       TM_ORG                         TOR \n");
		sql.append("   WHERE TMP.RESOURCE_SCOPE = TOR.ORG_CODE\n");
		sql.append("	 AND IS_ORDER in(0,1)\n");
		if (!type.equals("")) {
			sql.append("   AND tmp.RESOURCE_TYPE=" + type + "\n");
		}
		sql.append("  union all\n");
		sql.append("	SELECT distinct TMP.COMMONALITY_ID,TMP.ROW_NO,\n");
		sql.append("       TOR.TOTAL_ORG_CODE  RESOURCE_SCOPE,\n");
		sql.append("       TMP.VIN,\n");
		sql.append("       TMP.RESOURCE_TYPE,\n");
		sql.append("       TMP.CREATE_DATE,\n");
		sql.append("       TMP.UPDATE_DATE\n");
		sql.append("  FROM TT_VS_UPLOAD_COMMON_RESOURCE TMP,\n");
		sql.append("       TC_ORG_BIG       TOR \n");
		sql.append("   WHERE TMP.RESOURCE_SCOPE = TOR.TOTAL_ORG_CODE \n");
		sql.append("	 AND IS_ORDER in(0,1)\n");
		if (!type.equals("")) {
			sql.append("   AND tmp.RESOURCE_TYPE=" + type + "\n");
		}
		sql.append("  union all\n");
		sql.append(
				" select tmp.COMMONALITY_ID,TMP.ROW_NO,'天津受影响车辆资源池' RESOURCE_SCOPE,tmp.VIN,20811003 RESOURCE_TYPE,tmp.CREATE_DATE,tmp.UPDATE_DATE\n");
		sql.append("  from TT_VS_UPLOAD_COMMON_RESOURCE tmp\n");
		sql.append(" where tmp.RESOURCE_SCOPE='CG_TIANJING') t\n");
		sql.append("   ORDER BY CAST(t.ROW_NO AS DECIMAL(30,0)) ASC \n");
		System.out.println(sql.toString());
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private ArrayList<ResourceOrderUploadDTO> checkDataTwo(String type) {

		ArrayList<ResourceOrderUploadDTO> tvypDTOList = new ArrayList<ResourceOrderUploadDTO>();
		List<Map> tempList = selectComResZDR1(type);
		for (Map map : tempList) {
			ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
			rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
			rowDto.setErrorMsg("VIN号:" + map.get("VIN").toString() + " -资源已分配!");
			tvypDTOList.add(rowDto);

		}
		return tvypDTOList;
	}

	private List<Map> selectComResZDR1(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  t1.ROW_NO,t1.VIN from TT_VS_UPLOAD_COMMON_RESOURCE t1,TM_VEHICLE_DEC t2\n");
		sql.append("   where t1.RESOURCE_TYPE=" + type + "\n");
		sql.append("     and t1.VIN = t2.VIN and t2.NODE_STATUS in(11511018,11511003,11511004,11511005,11511006)\n");
		sql.append("     and (exists (select 1 from TT_VS_ORDER where IS_DEL <> '1' AND  VIN=t2.VIN and ORDER_STATUS<"
				+ OemDictCodeConstants.ORDER_STATUS_08 + ")\n");
		sql.append(
				"		 or exists (select 1 from TM_DEALER where DEALER_CODE = T1.RESOURCE_SCOPE and DEALER_ID = t2.DEALER_ID");
		sql.append("                       and DEALER_TYPE=" + OemDictCodeConstants.DEALER_TYPE_DVS + " )\n");// by
																												// wangJian
																												// 判断该资源是否已存在
		sql.append("		 or exists (select 1 from TT_VS_COMMON_RESOURCE  tc,TT_VS_COMMON_RESOURCE_DETAIL tcd\n");
		sql.append("		 			  where tc.COMMON_ID = tcd.COMMON_ID and tc.VEHICLE_ID=t2.VEHICLE_ID\n");
		sql.append("		 				and tcd.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + " and tc.STATUS="
				+ OemDictCodeConstants.COMMON_RESOURCE_STATUS_02 + "))\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 期货、现货订单判断
	 * 
	 * @param type
	 * @param rowDto
	 * @return
	 */
	private List<Map<String, Object>> checkData(String type, int flag) {
		List<Map<String, Object>> tvypDTOList = new ArrayList<Map<String, Object>>();
		List<Map> bkList = findBlankData(type);
		if (bkList != null) {
			for (Map map : bkList) {
				Map<String, Object> errorMap = new HashMap<>();
				errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
				errorMap.put("errorMsg", "资源代码为空");
				tvypDTOList.add(errorMap);

				// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
				// rowDto.setErrorMsg("资源代码为空");
				// tvypDTOList.add(rowDto);
			}
			return tvypDTOList;
		}
		List<Map> rList = findRepeatData(type);
		if (bkList != null) {
			for (Map map : rList) {
				// ResourceOrderUploadDTO rowDto = new ResourceOrderUploadDTO();
				// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
				// rowDto.setErrorMsg("与第" + map.get("ROW_NO") + "行数据重复!");
				// tvypDTOList.add(rowDto);
				Map<String, Object> errorMap = new HashMap<>();
				errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
				errorMap.put("errorMsg", "与第" + map.get("ROW_NO") + "行数据重复!");
				tvypDTOList.add(errorMap);

			}
			return tvypDTOList;

		} else {
			List<Map> bList = findRepeatData2(type);
			if (bList != null) {
				for (Map map : bList) {
					// ResourceOrderUploadDTO rowDto = new
					// ResourceOrderUploadDTO();
					// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
					// rowDto.setErrorMsg("与第" + map.get("ROW_NO") +
					// "行VIN码重复!");
					// tvypDTOList.add(rowDto);
					Map<String, Object> errorMap = new HashMap<>();
					errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
					errorMap.put("errorMsg", "与第" + map.get("ROW_NO") + "行VIN码重复!");
					tvypDTOList.add(errorMap);

				}
				return tvypDTOList;
			}
			/**
			 * 优化后 1-8
			 */
			// 1.*对于正确的VIN码判断是否在车辆表中存在
			List<Map> vehList = findNoExistsVin();
			if (vehList != null) {
				for (Map map : vehList) {
					// ResourceOrderUploadDTO rowDto = new
					// ResourceOrderUploadDTO();
					// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
					// rowDto.setErrorMsg("VIN号:" + map.get("VIN") + "不存在");
					// tvypDTOList.add(rowDto);
					Map<String, Object> errorMap = new HashMap<>();
					errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
					errorMap.put("errorMsg", "VIN号:" + map.get("VIN") + "不存在");
					tvypDTOList.add(errorMap);
				}
				return tvypDTOList;
			}
			// 期货导入销售经销商时需判断是否是整车销售经销商
			if (type.equals(String.valueOf(OemDictCodeConstants.COMMON_RESOURCE_TYPE_01))) {
				List<Map> noSalesList = findNotSales(type);
				if (noSalesList != null) {
					for (Map map : noSalesList) {
						// ResourceOrderUploadDTO rowDto = new
						// ResourceOrderUploadDTO();
						// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
						// rowDto.setErrorMsg("经销商:" + map.get("RESOURCE_SCOPE")
						// + "不是整车销售经销商");
						// tvypDTOList.add(rowDto);
						Map<String, Object> errorMap = new HashMap<>();
						errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
						errorMap.put("errorMsg", "经销商:" + map.get("RESOURCE_SCOPE") + "不是整车销售经销商");
						tvypDTOList.add(errorMap);

					}
					return tvypDTOList;
				}
			}
			// 期货、现货导入区域需要判断是否是整车销售区域
			if (flag == 2) {
				List<Map> noSalesList = findNotDvs();
				if (noSalesList != null) {
					for (Map map : noSalesList) {
						// ResourceOrderUploadDTO rowDto = new
						// ResourceOrderUploadDTO();
						// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
						// rowDto.setErrorMsg("区域: " + map.get("RESOURCE_SCOPE")
						// + "不是整车销售区域");
						// tvypDTOList.add(rowDto);

						Map<String, Object> errorMap = new HashMap<>();
						errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
						errorMap.put("errorMsg", "经销商:" + map.get("RESOURCE_SCOPE") + "不是整车销售区域");
						tvypDTOList.add(errorMap);

					}
					return tvypDTOList;
				}
			}
			// +是否生成有效订单
			List<Map> vinList = findIsOrderApp(type);
			if (null != vinList) {
				if (vinList != null) {
					for (Map map : vinList) {
						// ResourceOrderUploadDTO rowDto = new
						// ResourceOrderUploadDTO();
						// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
						// rowDto.setErrorMsg("vin号:" + map.get("VIN") +
						// "订单已确认");
						// tvypDTOList.add(rowDto);
						Map<String, Object> errorMap = new HashMap<>();
						errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
						errorMap.put("errorMsg", "vin号:" + map.get("VIN") + "订单已确认");
						tvypDTOList.add(errorMap);

					}
					return tvypDTOList;
				}
			}
			// 3.*期货需要判断是否是在-生产线上
			/*
			 * if(type.equals(String.valueOf(Constant.
			 * COMMON_RESOURCE_TYPE_01))){ List<Map<String,Object>> istList =
			 * dao.checkVinIsOnLine(type); if(istList.size()>0){ for(int
			 * i=0;i<istList.size();i++){ ExcelErrors err = new ExcelErrors();
			 * err.setRowNum(Integer.parseInt(istList.get(i).get("ROW_NO").
			 * toString())); err.setErrorDesc("vin号:"+istList.get(i).get("VIN")+
			 * " 的车辆不在'生产线上'"); if(!eSet.contains(err.getRowNum())){
			 * errorList.add(err); eSet.add(err.getRowNum()); } } } }
			 */
			// 4.*现货判断是否在-车厂库存
			if (type.equals(String.valueOf(OemDictCodeConstants.COMMON_RESOURCE_TYPE_02))) {
				List<Map> isnList = checkVinIsOnStore(type);
				if (isnList != null) {
					for (Map map : isnList) {
						// ResourceOrderUploadDTO rowDto = new
						// ResourceOrderUploadDTO();
						// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
						// rowDto.setErrorMsg("vin号:" + map.get("VIN") +
						// "的车辆不在'车厂库存'");
						// tvypDTOList.add(rowDto);
						Map<String, Object> errorMap = new HashMap<>();
						errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
						errorMap.put("errorMsg", "vin号:" + map.get("VIN") + "的车辆不在'车厂库存'");
						tvypDTOList.add(errorMap);
					}
					return tvypDTOList;
				}
				// 5.现货 需要判断车辆表中zbilDate是否有值
				List<Map> istList2 = checkVinIsOnStore2(type);
				if (istList2 != null) {
					for (Map map : istList2) {
						// ResourceOrderUploadDTO rowDto = new
						// ResourceOrderUploadDTO();
						// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
						// rowDto.setErrorMsg("vin号:" + map.get("VIN") + "未开票");
						// tvypDTOList.add(rowDto);
						Map<String, Object> errorMap = new HashMap<>();
						errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
						errorMap.put("errorMsg", "vin号:" + map.get("VIN") + "未开票");
						tvypDTOList.add(errorMap);
					}
					return tvypDTOList;
				}
			}
			// 期货需要判断节点范围：资源范围设定的范围
			if (type.equals(OemDictCodeConstants.COMMON_RESOURCE_TYPE_01.toString())) {
				List<Map> fuList = checkedNodeStatus();
				if (fuList != null) {
					for (Map map : fuList) {
						// ResourceOrderUploadDTO rowDto = new
						// ResourceOrderUploadDTO();
						// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
						// rowDto.setErrorMsg("VIN号:" + map.get("VIN") +
						// "不符合期货设定条件");
						// tvypDTOList.add(rowDto);
						Map<String, Object> errorMap = new HashMap<>();
						errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
						errorMap.put("errorMsg", "VIN号:" + map.get("VIN") + "不符合期货设定条件");
						tvypDTOList.add(errorMap);
					}
					return tvypDTOList;
				}
			}
			// 8.判断该VIN是否锁定(资源备注)
			List<Map> isLockList = findComnsIsLock();
			if (isLockList != null) {
				for (Map map : isLockList) {
					// ResourceOrderUploadDTO rowDto = new
					// ResourceOrderUploadDTO();
					// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
					// rowDto.setErrorMsg("vin号:" + map.get("VIN") + "已锁定");
					// tvypDTOList.add(rowDto);
					Map<String, Object> errorMap = new HashMap<>();
					errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
					errorMap.put("errorMsg", "vin号:" + map.get("VIN") + "已锁定");
					tvypDTOList.add(errorMap);
				}
				return tvypDTOList;
			}
			// 9.判断该VIN是否在资源分配中待分配
			List<Map> isAllotList = findIsAllotList();
			if (isAllotList != null) {
				for (Map map : isAllotList) {
					// ResourceOrderUploadDTO rowDto = new
					// ResourceOrderUploadDTO();
					// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
					// rowDto.setErrorMsg("vin号:" + map.get("VIN") +
					// "在资源分配中待审核");
					// tvypDTOList.add(rowDto);

					Map<String, Object> errorMap = new HashMap<>();
					errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
					errorMap.put("errorMsg", "vin号:" + map.get("VIN") + "在资源分配中待审核");
					tvypDTOList.add(errorMap);
				}
				return tvypDTOList;
			}
			// 10.判断是否是进口车
			List<Map> importList = findIsNotImport();
			if (null != importList) {
				for (Map map : importList) {
					// ResourceOrderUploadDTO rowDto = new
					// ResourceOrderUploadDTO();
					// rowDto.setRowNO(Integer.parseInt(map.get("ROW_NO").toString()));
					// rowDto.setErrorMsg("vin号:" + map.get("VIN") + "不是进口车!");
					// tvypDTOList.add(rowDto);

					Map<String, Object> errorMap = new HashMap<>();
					errorMap.put("rowNO", Integer.parseInt(map.get("ROW_NO").toString()));
					errorMap.put("errorMsg", "vin号:" + map.get("VIN") + "不是进口车!");
					tvypDTOList.add(errorMap);
				}
				return tvypDTOList;
			}
		}
		return null;
	}

	private List<Map> findIsNotImport() {
		String sql = "select * from TT_VS_UPLOAD_COMMON_RESOURCE tvuc where exists(select 1 from (" + getVwMaterialSql()
				+ ") vm,TM_VEHICLE_DEC tv where vm.MATERIAL_ID=tv.MATERIAL_ID and tv.VIN=tvuc.VIN and vm.GROUP_TYPE="
				+ OemDictCodeConstants.GROUP_TYPE_DOMESTIC + ")";
		return OemDAOUtil.findAll(sql, null);
	}

	private List<Map> findIsAllotList() {
		StringBuffer sql = new StringBuffer();
		sql.append("select p.ROW_NO,p.VIN from TT_VS_UPLOAD_COMMON_RESOURCE   p,TMP_UPLOAD_RESOURCE   tur\n");
		sql.append("  where p.VIN=tur.VIN\n");
		sql.append("    and tur.ALLOT=0 \n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> findComnsIsLock() {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvuc.ROW_NO,tvuc.VIN from TT_VS_UPLOAD_COMMON_RESOURCE  tvuc,TT_RESOURCE_REMARK  trr\n");
		sql.append("   where trr.VIN=tvuc.VIN\n");
		sql.append("     and trr.IS_LOCK=1 \n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> checkedNodeStatus() {
		String sql = "select ROW_NO,t.VIN from Tt_Vs_Upload_Common_Resource  t,TM_VEHICLE_DEC tv where t.VIN=tv.VIN and tv.NODE_STATUS not in(select NODE_STATUS from TT_VS_RESOURCE_RANGE)";
		return OemDAOUtil.findAll(sql, null);
	}

	private List<Map> checkVinIsOnStore2(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvuc.ROW_NO,tvuc.VIN from Tt_Vs_Upload_Common_Resource tvuc,TM_VEHICLE_DEC tv\n");
		sql.append("   where  tvuc.VIN=tv.VIN\n");
		sql.append("     and  tvuc.RESOURCE_TYPE=" + type + "\n");
		sql.append("   	 and  tv.LIFE_CYCLE=" + OemDictCodeConstants.LIF_CYCLE_02 + "\n");
		sql.append("     and  tv.ZBIL_DATE is null\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> checkVinIsOnStore(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.ROW_NO,t.VIN from Tt_Vs_Upload_Common_Resource t\n");
		sql.append("    where  t.RESOURCE_TYPE=" + type + "\n");
		sql.append("    and  not exists (select 1 from TM_VEHICLE_DEC tv where tv.VIN=t.VIN and tv.LIFE_CYCLE="
				+ OemDictCodeConstants.LIF_CYCLE_02 + ")\n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> findIsOrderApp(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvuc.VIN,tvuc.ROW_NO\n");
		sql.append("    from Tt_Vs_Upload_Common_Resource tvuc\n");
		sql.append(
				"    where exists(select 1 from TT_VS_ORDER t where t.IS_DEL <> '1' AND t.ORDER_STATUS<20071007 and t.VIN=tvuc.VIN)\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> findNotDvs() {
		StringBuffer sql = new StringBuffer();
		sql.append("select ROW_NO,RESOURCE_SCOPE from TT_VS_UPLOAD_COMMON_RESOURCE   p,TM_ORG   tor\n");
		sql.append("  where  p.RESOURCE_SCOPE=tor.ORG_CODE \n");
		sql.append("    and  not exists (select 1 from TM_ORG t\n");
		sql.append("     		where p.RESOURCE_SCOPE = t.ORG_CODE\n");
		sql.append("    		  and t.BUSS_TYPE=" + OemDictCodeConstants.ORG_BUSS_TYPE_01 + ")\n");
		sql.append("	and  tor.ORG_ID!=" + OemDictCodeConstants.OEM_ACTIVITIES + " \n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> findNotSales(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ROW_NO,RESOURCE_SCOPE from Tt_Vs_Upload_Common_Resource   p,TM_DEALER   td\n");
		sql.append("  where  p.RESOURCE_SCOPE=td.DEALER_CODE and p.RESOURCE_TYPE=" + type + "\n");
		sql.append("     and  not exists (select 1 from TM_DEALER t\n");
		sql.append(" 					 where p.RESOURCE_SCOPE = t.DEALER_CODE\n");
		sql.append("                       and t.DEALER_TYPE=" + OemDictCodeConstants.DEALER_TYPE_DVS + ")\n");
		return null;
	}

	private List<Map> findNoExistsVin() {
		String sql = "select ROW_NO,VIN from Tt_Vs_Upload_Common_Resource where VIN not in(select tv.VIN from TM_VEHICLE_DEC tv where tv.VIN=VIN)";
		return OemDAOUtil.findAll(sql, null);
	}

	private List<Map> findRepeatData2(String type) {
		StringBuffer sql = new StringBuffer("\n");
		sql.append("select count(1),p1.VIN,p1.RESOURCE_SCOPE,p1.RESOURCE_TYPE \n");
		sql.append("  from TT_VS_UPLOAD_COMMON_RESOURCE p1 \n");
		sql.append("  where p1.RESOURCE_TYPE=" + type + " \n");
		sql.append("    and p1.RESOURCE_SCOPE<>'' \n");
		sql.append("    group by p1.VIN,p1.RESOURCE_SCOPE,p1.RESOURCE_TYPE \n");
		sql.append("    having count(1)>=2 \n");
		sql.append(" \n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 查询重复数据
	 * 
	 * @return
	 */
	private List<Map> findRepeatData(String type) {
		// StringBuffer sql = new StringBuffer();
		// sql.append("select
		// p1.ROW_NO,p1.VIN,p1.RESOURCE_SCOPE,p1.RESOURCE_TYPE\n");
		// sql.append(" from TT_VS_UPLOAD_COMMON_RESOURCE p1\n");
		// sql.append(" where p1.RESOURCE_TYPE=" + type + "\n");
		// sql.append(" and p1.RESOURCE_SCOPE<>''\n");
		// sql.append(" and exists (select 1\n");
		// sql.append(" from TT_VS_UPLOAD_COMMON_RESOURCE p2\n");
		// sql.append(" where p1.RESOURCE_SCOPE = p2.RESOURCE_SCOPE\n");
		// sql.append(" and p1.VIN = p2.VIN\n");
		// sql.append(" and p1.RESOURCE_TYPE = p2.RESOURCE_TYPE\n");
		// sql.append(" and p1.ROW_NO <> p2.ROW_NO)\n");

		StringBuffer sql = new StringBuffer("\n");
		sql.append("select count(1),p1.VIN,p1.RESOURCE_SCOPE,p1.RESOURCE_TYPE,p1.ROW_NO \n");
		sql.append("  from TT_VS_UPLOAD_COMMON_RESOURCE p1 \n");
		sql.append("  where p1.RESOURCE_TYPE=" + type + " \n");
		sql.append("    and p1.RESOURCE_SCOPE<>'' \n");
		sql.append("    group by p1.VIN,p1.RESOURCE_SCOPE,p1.RESOURCE_TYPE \n");
		sql.append("    having count(1)>=2 \n");
		sql.append(" \n");

		List<Map> findAll = OemDAOUtil.findAll(sql.toString(), null);
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	private List<Map> findBlankData(String type) {
		StringBuffer sql = new StringBuffer();
		sql.append("select ROW_NO from Tt_Vs_Upload_Common_Resource\n");
		sql.append("   where  RESOURCE_TYPE=" + type + "\n");
		sql.append("     and  RESOURCE_SCOPE='" + type + "'\n");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	/**
	 * 判断资源范围是否一致
	 * 
	 * @return
	 */
	private String findOrderType() {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) TOTAL from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql.append("	where exists (select 1 from TM_DEALER where DEALER_CODE=t.RESOURCE_SCOPE) \n");
		Map map1 = OemDAOUtil.findFirst(sql.toString(), null);

		StringBuffer sql2 = new StringBuffer();
		sql2.append("select count(*) TOTAL from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql2.append(
				"	where exists (select 1 from TM_ORG where ORG_CODE=t.RESOURCE_SCOPE and  DUTY_TYPE in(10431001,10431003))\n");
		Map map2 = OemDAOUtil.findFirst(sql2.toString(), null);

		StringBuffer sql3 = new StringBuffer();
		sql3.append("select count(*) TOTAL from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql3.append("	where exists (select 1 from TC_ORG_BIG where TOTAL_ORG_CODE=t.RESOURCE_SCOPE)\n");
		Map map3 = OemDAOUtil.findFirst(sql3.toString(), null);

		int total1 = new Integer(map1.get("TOTAL").toString());
		int total2 = new Integer(map2.get("TOTAL").toString());
		int total3 = new Integer(map3.get("TOTAL").toString());
		// 如果经销商和区域数都大于1，则认为是资源范围不统一
		if (total1 > 0 && total2 > 0 && total3 > 0) {
			return "0";
		} else {
			return "1";
		}
	}

	private String findType() {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) TOTAL from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql.append("	where exists (select 1 from TM_DEALER where DEALER_CODE=t.RESOURCE_SCOPE) \n");
		Map map1 = OemDAOUtil.findFirst(sql.toString(), null);

		StringBuffer sql2 = new StringBuffer();
		sql2.append("select count(*) TOTAL from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql2.append(
				"	where exists (select 1 from TM_ORG where ORG_CODE=t.RESOURCE_SCOPE and  DUTY_TYPE in(10431001,10431003))\n");
		Map map2 = OemDAOUtil.findFirst(sql2.toString(), null);

		StringBuffer sql3 = new StringBuffer();
		sql3.append("select count(*) TOTAL from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql3.append("	where exists (select 1 from TC_ORG_BIG where TOTAL_ORG_CODE=t.RESOURCE_SCOPE)\n");
		Map map3 = OemDAOUtil.findFirst(sql3.toString(), null);
		int total1 = new Integer(map1.get("TOTAL").toString());
		int total2 = new Integer(map2.get("TOTAL").toString());
		int total3 = new Integer(map3.get("TOTAL").toString());
		// 如果经销商和区域数都大于1，则认为是资源范围不统一
		if (total1 > 0 && total2 > 0 && total3 > 0) {
			return "0";
		} else {
			return "1";
		}

	}

	private List<Map> findNoExistsResource() {

		StringBuffer sql = new StringBuffer();
		sql.append("select t.ROW_NO,t.RESOURCE_SCOPE from Tt_Vs_Upload_Common_Resource t\n");
		sql.append("   where t.RESOURCE_SCOPE<>'CG_TIANJING'\n");
		sql.append("     and t.RESOURCE_SCOPE not in(select RESOURCE_SCOPE from Tt_Vs_Upload_Common_Resource   p\n");
		sql.append(
				"      where  (exists (select 1 from TM_ORG where ORG_CODE=p.RESOURCE_SCOPE and DUTY_TYPE in(10431001,10431003))\n");
		sql.append("              or  exists (select 1 from TM_DEALER where DEALER_CODE=p.RESOURCE_SCOPE) \n");
		sql.append("              or  exists (select 1 from TC_ORG_BIG where TOTAL_ORG_CODE=p.RESOURCE_SCOPE))) \n");
		List<Map> findAll = OemDAOUtil.findAll(sql.toString(), null);
		return OemDAOUtil.findAll(sql.toString(), null);

	}

	public List<Map> findZdrrList(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tvo.ORDER_ID,tv.VEHICLE_ID,tvo.VIN\n");
		sql.append("    from TT_VS_ORDER tvo,TM_VEHICLE_DEC tv \n");
		sql.append("     where tvo.IS_DEL <> '1' AND tvo.VIN=tv.VIN\n");
		sql.append("       and tv.NODE_STATUS=" + OemDictCodeConstants.VEHICLE_NODE_18 + "\n");
		sql.append("       and (tvo.ORDER_STATUS<" + OemDictCodeConstants.ORDER_STATUS_08
				+ "  or tvo.ORDER_STATUS is null)\n");
		sql.append("	   and tv.VIN='" + vin + "'\n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> getFactoryId(String vin) {
		StringBuffer sql = new StringBuffer("");

		sql.append("SELECT COMMON_DETAIL_ID\n");
		sql.append("  FROM TT_VS_FACTORY_ORDER\n");
		sql.append(" WHERE 1 = 1\n");
		sql.append(" and VIN = '");
		sql.append(vin);
		sql.append("'");
		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public List<Map> findZdrrList2(String vin) {
		StringBuffer sql = new StringBuffer();
		sql.append("select tv.VEHICLE_ID,tvcr.TYPE,tvcr.COMMON_ID\n");
		sql.append("   from TM_VEHICLE_DEC  tv,TT_VS_COMMON_RESOURCE  tvcr,TT_VS_COMMON_RESOURCE_DETAIL tvcrd\n");
		sql.append("     where tvcr.VEHICLE_ID=tv.VEHICLE_ID\n");
		sql.append("       and tvcr.COMMON_ID=tvcrd.COMMON_ID\n");
		sql.append("       and tvcrd.STATUS=" + OemDictCodeConstants.STATUS_ENABLE + "\n");
		sql.append("       and tvcr.STATUS=" + OemDictCodeConstants.COMMON_RESOURCE_STATUS_02 + "\n");
		sql.append("       and tv.VIN='" + vin + "'\n");

		return OemDAOUtil.findAll(sql.toString(), null);
	}

	public String findOrderType1() {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) TOTAL from TT_VS_UPLOAD_COMMON_RESOURCE t\n");
		sql.append("	where exists (select 1 from TM_DEALER where DEALER_CODE=t.RESOURCE_SCOPE and DEALER_TYPE="
				+ OemDictCodeConstants.DEALER_TYPE_DVS + ")\n");
		List<Map> list1 = OemDAOUtil.findAll(sql.toString(), null);

		String sql2 = "select count(*) TOTAL from TT_VS_UPLOAD_COMMON_RESOURCE";
		List<Map> list2 = OemDAOUtil.findAll(sql2, null);

		// 现货：如果临时表的数据总数全部是分配给经销商的则认为是"指派",否则认为是"现货"
		// 期货：如果临时表的数据总数全部是分配给经销商的则认为是"期货资源分配给经销商"，否则认为是"分配给区域"
		if (list1.get(0).get("TOTAL").toString().equals(list2.get(0).get("TOTAL").toString())) {
			return "1";
		} else {
			return "0";
		}
	}

}
