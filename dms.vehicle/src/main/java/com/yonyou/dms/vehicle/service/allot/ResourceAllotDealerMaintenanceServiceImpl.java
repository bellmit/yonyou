package com.yonyou.dms.vehicle.service.allot;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.javalite.activejdbc.LazyList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TcOrgBigPO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.CommonUtils;
import com.yonyou.dms.vehicle.dao.allot.ResourceAllotDealerMaintenanceDao;
import com.yonyou.dms.vehicle.domains.DTO.allot.EditAllotDerDto;
import com.yonyou.dms.vehicle.domains.DTO.allot.EditOrgDto;
import com.yonyou.dms.vehicle.domains.DTO.allot.EditOrgDto2;
import com.yonyou.dms.vehicle.domains.PO.allot.TmDealerMaintenancePO;
import com.yonyou.dms.vehicle.domains.PO.allot.TmDealerWhsPO;

/**
 * 资源分配经销商维护
 * 
 * @author Administrator
 *
 */
@SuppressWarnings("all")
@Service
public class ResourceAllotDealerMaintenanceServiceImpl implements ResourceAllotDealerMaintenanceService {
	@Autowired
	ResourceAllotDealerMaintenanceDao maintenanceDao;
	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 资源分配经销商维护查询
	 */
	@Override
	public PageInfoDto allotDealerMaintenanceQuery(Map<String, String> queryParam) {
		return maintenanceDao.allotDealerMaintenanceQuery(queryParam);
	}

	/**
	 * 资源分配经销商维护下载
	 */
	@Override
	public void download(Map<String, String> queryParam, HttpServletResponse response, HttpServletRequest request) {
		Map<String, List<Map>> excelData = new HashMap<>();
		List<Map> list = maintenanceDao.download(queryParam);
		excelData.put("资源分配备注下载", (List<Map>) list);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("DEALER_TYPE", "经销商类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("BIG_AREA", "所属大区"));
		exportColumnList.add(new ExcelExportColumn("SMALL_AREA", "所属小区"));
		exportColumnList.add(new ExcelExportColumn("COMPANY_NAME", "经销商公司"));
		exportColumnList.add(new ExcelExportColumn("STATUS", "状态", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("TJ_PORT_LEVEL", "天津港"));
		exportColumnList.add(new ExcelExportColumn("SH_PORT_LEVEL", "上海港"));
		excelService.generateExcel(excelData, exportColumnList, "资源分配备注下载.xls", request, response);
	}

	@Override
	public List<Map> queryTotalOrg(Map<String, String> queryParam) {
		List<Map> list = maintenanceDao.queryTotalOrg(queryParam);
		return list;
	}

	@Override
	public List<Map> queryOrgName(Map<String, String> queryParam) {
		List<Map> list = maintenanceDao.queryOrgName(queryParam);
		return list;
	}

	@Override
	public List<Map> queryAll(Map<String, String> queryParam) {

		return maintenanceDao.queryAll(queryParam);
	}

	@Override
	public List<Map> queryTcUserName(Map<String, String> queryParam) {

		return maintenanceDao.queryTcUserName(queryParam);
	}

	@Override
	public Map findById(Long userId) {
		// 查大区总监名称

		List<Map> list = maintenanceDao.findById(userId);
		List<Map> list3 = maintenanceDao.findById1(userId);
		// 查询大区
		String orgSql = "select * from TC_ORG_BIG WHERE USER_ID=" + userId;
		List<Map> list2 = OemDAOUtil.findAll(orgSql, null);
		Map m = new HashMap<>();
		for (Map map : list) {
			m.put("NAME", map.get("NAME"));
			m.put("USER_ID", map.get("USER_ID"));
			m.put("USER_ID1", map.get("USER_ID"));
			m.put("NAME1", map.get("NAME"));
		}
		for (Map map : list2) {
			m.put("TOTAL_ORG_NAME", map.get("TOTAL_ORG_NAME"));
			m.put("TOTAL_ORG_NAME1", map.get("TOTAL_ORG_NAME"));
			m.put("TOTAL_ORG_CODE1", map.get("TOTAL_ORG_CODE"));
			m.put("TOTAL_ORG_CODE", map.get("TOTAL_ORG_CODE"));
		}

		StringBuilder s = new StringBuilder();
		for (Map map : list3) {
			String string = map.get("ORG_ID").toString();

			s.append(string + ",");
		}
		m.put("ORG_ID1", s);

		return m;
	}

	@Override
	public List<Map> queryAllById(Long id) {

		return maintenanceDao.queryAllById(id);
	}

	// 修改
	@Override
	public void editOrg(EditOrgDto dto) {
		String orgId = CommonUtils.checkNull(dto.getOrgIds());
		String orgId11 = CommonUtils.checkNull(dto.getOrgIds1());// 修改之前大区总监
		String resourceCode = CommonUtils.checkNull(dto.getResourceCode());
		String resourceCode1 = CommonUtils.checkNull(dto.getResourceCode1());
		String portId = CommonUtils.checkNull(dto.getTcUserId());
		String portId1 = CommonUtils.checkNull(dto.getTcUserId1());
		String areaName = CommonUtils.checkNull(dto.getTotalOrg());
		String areaName1 = CommonUtils.checkNull(dto.getTotalOrg1());
		String orgId1 = orgId11.substring(0, orgId11.length() - 1);
		String[] orgIds = orgId.split(",");
		String returnValue = "";

		String orgSql = "select * from TC_ORG_BIG T1 WHERE not exists (select* from TC_ORG_BIG T2 where T1.ORG_ID=T2.ORG_ID AND T2.ORG_ID in("
				+ orgId1 + "))";
		List<Map> orgList = OemDAOUtil.findAll(orgSql, null);
		// 判断修改的大区总监是否与原来的一致
		if (!portId1.equals(portId)) {
			// 修改大区总监时，判断，是否存在
			LazyList<TcOrgBigPO> list3 = TcOrgBigPO.findBySQL("select * from TC_ORG_BIG where user_id=?", portId);
			if (list3.size() > 0 && list3 != null) {
				throw new ServiceBizException("您所选的大区总监已存在!");
			} else {
				// 当前大区总与修改前大区总比较是否相同
				if (!areaName1.equals(areaName)) {
					LazyList<TcOrgBigPO> list1 = TcOrgBigPO.findBySQL("select * from TC_ORG_BIG where TOTAL_ORG_NAME=?",
							areaName);
					if (list1.size() > 0 && list1 != null) {
						throw new ServiceBizException("您所修改的大区总已存在!");
					} else {
						if (!resourceCode1.equals(resourceCode)) {
							LazyList<TcOrgBigPO> list2 = TcOrgBigPO
									.findBySQL("select * from TC_ORG_BIG where TOTAL_ORG_CODE=?", resourceCode);
							if (list2.size() > 0 && list2 != null) {
								throw new ServiceBizException("您所修改的大区总资源池代码已存在!");
							} else {
								returnValue = this.editMain(orgIds, orgList, portId, areaName, resourceCode, portId1);
								throw new ServiceBizException(returnValue);
							}
						} else {
							returnValue = this.editMain(orgIds, orgList, portId, areaName, resourceCode, portId1);
							throw new ServiceBizException(returnValue);
						}
					}
				} else {
					if (!resourceCode1.equals(resourceCode)) {
						LazyList<TcOrgBigPO> list2 = TcOrgBigPO
								.findBySQL("select * from TC_ORG_BIG where TOTAL_ORG_CODE=?", resourceCode);
						if (list2.size() > 0 && list2 != null) {
							throw new ServiceBizException("您所修改的大区总资源池代码已存在!");
						} else {
							returnValue = this.editMain(orgIds, orgList, portId, areaName, resourceCode, portId1);
							throw new ServiceBizException(returnValue);
						}
					} else {
						returnValue = this.editMain(orgIds, orgList, portId, areaName, resourceCode, portId1);
						throw new ServiceBizException(returnValue);
					}
				}
			}
		} else {

			// 当前大区总与修改前大区总比较是否相同
			if (!areaName1.equals(areaName)) {
				LazyList<TcOrgBigPO> list1 = TcOrgBigPO.findBySQL("select * from TC_ORG_BIG where TOTAL_ORG_NAME=?",
						areaName);
				if (list1.size() > 0) {
					throw new ServiceBizException("您所修改的大区总已存在!");
				} else {
					if (!resourceCode1.equals(resourceCode)) {
						LazyList<TcOrgBigPO> list2 = TcOrgBigPO
								.findBySQL("select * from TC_ORG_BIG where TOTAL_ORG_CODE=?", resourceCode);
						if (list2.size() > 0 && list2 != null) {
							throw new ServiceBizException("您所修改的大区总资源池代码已存在!");
						} else {
							returnValue = this.editMain(orgIds, orgList, portId, areaName, resourceCode, portId1);
						}
					} else {
						returnValue = this.editMain(orgIds, orgList, portId, areaName, resourceCode, portId1);
					}
				}
			} else {
				if (!resourceCode1.equals(resourceCode)) {
					LazyList<TcOrgBigPO> list2 = TcOrgBigPO.findBySQL("select * from TC_ORG_BIG where TOTAL_ORG_CODE=?",
							resourceCode);
					if (list2.size() > 0 && list2 != null) {
						returnValue = "您所修改的大区总资源池代码已存在!";
					} else {
						returnValue = this.editMain(orgIds, orgList, portId, areaName, resourceCode, portId1);
					}
				} else {
					returnValue = this.editMain(orgIds, orgList, portId, areaName, resourceCode, portId1);
				}
			}
		}

	}

	private String editMain(String[] orgIds, List<Map> orgList, String portId, String areaName, String resourceCode,
			String portId1) {

		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(currentTime);
		String a = "";
		String returnValue = "";
		out: for (int k = 0; k < orgIds.length; k++) {
			for (int j = 0; j < orgList.size(); j++) {
				if (orgIds[k].equals(orgList.get(j).get("ORG_ID").toString())) {
					a = "1";
					break out;
				} else {
					a = "2";
				}
			}
		}
		if (a.equals("1")) {
			returnValue = "您所选的大区已存!";
		} else {
			// String userSql = "delete from TC_ORG_BIG WHERE USER_ID =?" +
			// portId1;
			TcOrgBigPO.delete("USER_ID =? ", portId1);
			for (int i = 0; i < orgIds.length; i++) {
				String sql = "select * from TC_ORG_BIG where ORG_ID=? and USER_ID=? ";
				LazyList<TcOrgBigPO> list = TcOrgBigPO.findBySQL(sql, orgIds[i], portId);
				// 定位修改数据
				if (list.size() > 0 && list != null) {

					// 修改
					TcOrgBigPO aAPO1 = new TcOrgBigPO();
					aAPO1.setLong("USER_ID", new Long(portId));
					aAPO1.setLong("USER_ID", new Long(orgIds[i]));
					aAPO1.setString("TOTAL_ORG_NAME", areaName);
					aAPO1.setString("TOTAL_ORG_CODE", resourceCode);
					aAPO1.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
					aAPO1.setInteger("UPDATE_BY", 111L);
					aAPO1.setTimestamp("UPDATE_DATE", date);
					aAPO1.saveIt();
					// returnValue = "修改成功!";
				} else {
					// 当数据库中没有勾选大区时，新增
					TcOrgBigPO aAPO4 = new TcOrgBigPO();
					aAPO4.setLong("ORG_ID", new Long(orgIds[i]));
					aAPO4.setLong("USER_ID", new Long(portId));
					aAPO4.setString("TOTAL_ORG_NAME", areaName);
					aAPO4.setString("TOTAL_ORG_CODE", resourceCode);
					aAPO4.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
					aAPO4.setInteger("UPDATE_BY", 111L);
					aAPO4.setTimestamp("UPDATE_DATE", date);
					aAPO4.saveIt();
					// returnValue = "修改成功";
				}
			}
		}
		return returnValue;

	}

	@Override
	public void addRegion(EditOrgDto2 pyDto) {
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(currentTime);
		String orgId = CommonUtils.checkNull(pyDto.getOrgIds());
		String resourceCode = CommonUtils.checkNull(pyDto.getResourceCode());
		String portId = CommonUtils.checkNull(pyDto.getTcUserId());
		String areaName = CommonUtils.checkNull(pyDto.getTotalOrg());
		String[] orgIds = orgId.split(",");
		// 判断大区总监是否存在
		LazyList<TcOrgBigPO> list1 = TcOrgBigPO.findBySQL("select * from TC_ORG_BIG where user_id=?", portId);
		if (list1.size() != 0 && list1 != null) {
			throw new ServiceBizException("您所选的大区总监已存!");
			// returnValue="3";
		} else {
			// 大区总监存在，判断大区总是否存在
			LazyList<TcOrgBigPO> list2 = TcOrgBigPO.findBySQL("select * from TC_ORG_BIG where TOTAL_ORG_NAME=?",
					areaName);

			if (list2.size() > 0 && list2 != null) {
				// returnValue="0";
				throw new ServiceBizException("您所填的大区总已存在!");
			} else {
				// 大区总存在，判断大区总资源池代码是否存在
				LazyList<TcOrgBigPO> list4 = TcOrgBigPO.findBySQL("select * from TC_ORG_BIG where TOTAL_ORG_CODE=?",
						resourceCode);
				if (list4.size() != 0 && list4 != null) {
					// returnValue="4";
					throw new ServiceBizException("您所填的大区总资源池代码已存在!");
				} else {
					for (int i = 0; i < orgIds.length; i++) {
						String sql = "select * from TC_ORG_BIG where ORG_ID=?";
						LazyList<TcOrgBigPO> list = TcOrgBigPO.findBySQL(sql, orgIds[i]);
						if (list.size() != 0 && list != null) {
							// returnValue="1";
							throw new ServiceBizException("您所选的大区已存在!");
						} else {
							TcOrgBigPO aAPO3 = new TcOrgBigPO();

							// aAPO3.setLong("USER_ID", new Long(portId));
							aAPO3.setLong("ORG_ID", new Long(orgIds[i]));
							aAPO3.setString("TOTAL_ORG_NAME", areaName);
							aAPO3.setLong("User_Id", new Long(portId));
							aAPO3.setString("TOTAL_ORG_CODE", resourceCode);
							aAPO3.setInteger("STATUS", OemDictCodeConstants.STATUS_ENABLE);
							aAPO3.setInteger("UPDATE_BY", 111L);
							aAPO3.setTimestamp("CREATE_DATE", date);
							aAPO3.saveIt();

							String Org_Id = aAPO3.get("Org_Id").toString();
							LazyList<TmDealerWhsPO> li = TmDealerWhsPO
									.findBySQL("select * from Tm_Dealer_Whs where Type=2 and DEALER_ID=?", Org_Id);
							if (li.size() == 0) {
								for (TmDealerWhsPO twPO : li) {

									Map map = maintenanceDao.finndMaxOrderNum();
									String s = map.get("ORDER_NUM").toString();
									// Long num = Long.valueOf(s);
									twPO.setTimestamp("CREATE_DATE", date);
									// twPO.setInteger("CREATE_BY", 111L);
									twPO.setString("ORDER_NUM", s);
									twPO.saveIt();
								}
							}

						}
					}
				}

			}
		}

	}

	@Override
	public List<Map> queryAddInt() {
		return maintenanceDao.queryAddInt();
	}

	@Override
	public void delOrderRepeal(Long id) {

		TcOrgBigPO.delete("USER_ID=?", id);

	}

	@Override
	public Map findByid(Long id) {
		Map map = maintenanceDao.findByid(id);
		Map<String, Object> m = new HashMap<>();
		String s = map.get("SH_PORT_LEVEL").toString();
		String s2 = map.get("TJ_PORT_LEVEL").toString();
		String s3 = map.get("DEALER_SHORTNAME").toString();
		String s4 = map.get("DEALER_ID").toString();
		if (s2.equals("2")) {
			// s2 = "13931002";
			// 港口优先级（天津
			s2 = OemDictCodeConstants.VPC_PORT_02;
		}
		if (s2.equals("1")) {
			// s2 = "13931001";
			s2 = OemDictCodeConstants.VPC_PORT_01;
		}
		if (s.equals("2")) {
			// s = "13931004";
			// 港口优先级（上海）
			s = OemDictCodeConstants.VPC_PORT_04;
		}
		if (s.equals("1")) {
			// s = "13931003";
			s = OemDictCodeConstants.VPC_PORT_03;
		}
		m.put("SH_PORT_LEVEL", s);
		m.put("TJ_PORT_LEVEL", s2);
		m.put("DEALER_SHORTNAME", s3);
		m.put("DEALER_ID", s4);
		return m;
	}

	@Override
	public void editAllotDealer(EditAllotDerDto dto) {
		String dealerShortNmae = CommonUtils.checkNull(dto.getDealerShortNmae());
		String dealerI = CommonUtils.checkNull(dto.getDealerId());
		Long dealerId = Long.parseLong(dealerI);
		String portLevelSH = CommonUtils.checkNull(dto.getPortLevelSH());
		String portLevelTJ = CommonUtils.checkNull(dto.getPortLevelTJ());
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		Date currentTime = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String date = formatter.format(currentTime);

		if (!portLevelSH.equals("")) {
			if (portLevelSH.equals(OemDictCodeConstants.VPC_PORT_03)) {
				// portLevelSH = OemDictCodeConstants.VPC_PORT_02;

				LazyList<TmDealerMaintenancePO> list = TmDealerMaintenancePO
						.findBySQL("select * from Tm_Dealer_Maintenance where DEALER_ID=?", dealerId);
				if (list.size() > 0) {
					for (TmDealerMaintenancePO tdmp : list) {
						tdmp.setInteger("VPC_PORT", portLevelSH);
						tdmp.setInteger("PORT_LEVEL", 1);
						tdmp.setLong("DEALER_ID", dealerId);
						tdmp.setLong("Update_By", loginInfo.getUserId());
						tdmp.setTimestamp("UPDATE_DATE", date);
						tdmp.saveIt();
					}
				} else {
					TmDealerMaintenancePO tdm = new TmDealerMaintenancePO();
					tdm.setInteger("VPC_PORT", portLevelSH);
					tdm.setInteger("PORT_LEVEL", 1);
					tdm.setLong("DEALER_ID", dealerId);
					tdm.setLong("CREATE_BY", loginInfo.getUserId());
					tdm.setTimestamp("CREATE_DATE", date);
					tdm.saveIt();
				}
			}
			if (portLevelSH.equals(OemDictCodeConstants.VPC_PORT_04)) {
				// portLevelSH = OemDictCodeConstants.VPC_PORT_02;

				LazyList<TmDealerMaintenancePO> list = TmDealerMaintenancePO
						.findBySQL("select * from Tm_Dealer_Maintenance where DEALER_ID=?", dealerId);
				if (list.size() > 0) {
					for (TmDealerMaintenancePO tdmp : list) {
						tdmp.setInteger("VPC_PORT", portLevelSH);
						tdmp.setInteger("PORT_LEVEL", 2);
						tdmp.setLong("DEALER_ID", dealerId);
						tdmp.setLong("Update_By", loginInfo.getUserId());
						tdmp.setTimestamp("UPDATE_DATE", date);
						tdmp.saveIt();
					}
				} else {
					TmDealerMaintenancePO tdm = new TmDealerMaintenancePO();
					tdm.setInteger("VPC_PORT", portLevelSH);
					tdm.setInteger("PORT_LEVEL", 2);
					tdm.setLong("DEALER_ID", dealerId);
					tdm.setLong("CREATE_BY", loginInfo.getUserId());
					tdm.setTimestamp("CREATE_DATE", date);
					tdm.saveIt();
				}
			}
		}
		if (!portLevelTJ.equals("")) {
			if (portLevelTJ.equals(OemDictCodeConstants.VPC_PORT_01)) {
				// portLevelTJ = OemDictCodeConstants.VPC_PORT_01;
				LazyList<TmDealerMaintenancePO> list = TmDealerMaintenancePO
						.findBySQL("select * from Tm_Dealer_Maintenance where DEALER_ID=?", dealerId);
				if (list.size() > 0) {
					for (TmDealerMaintenancePO tdmp : list) {
						tdmp.setInteger("VPC_PORT", portLevelTJ);
						tdmp.setInteger("PORT_LEVEL", 1);
						tdmp.setLong("DEALER_ID", dealerId);
						tdmp.setLong("Update_By", loginInfo.getUserId());
						tdmp.setTimestamp("UPDATE_DATE", date);
						tdmp.saveIt();
					}
				} else {
					TmDealerMaintenancePO tdm = new TmDealerMaintenancePO();
					tdm.setInteger("VPC_PORT", portLevelTJ);
					tdm.setInteger("PORT_LEVEL", 1);
					tdm.setLong("DEALER_ID", dealerId);
					tdm.setLong("CREATE_BY", loginInfo.getUserId());
					tdm.setTimestamp("CREATE_DATE", date);
					tdm.saveIt();
				}
			}
			if (portLevelTJ.equals(OemDictCodeConstants.VPC_PORT_01)) {
				// portLevelTJ = OemDictCodeConstants.VPC_PORT_01;
				LazyList<TmDealerMaintenancePO> list = TmDealerMaintenancePO
						.findBySQL("select * from Tm_Dealer_Maintenance where DEALER_ID=?", dealerId);
				if (list.size() > 0) {
					for (TmDealerMaintenancePO tdmp : list) {
						tdmp.setInteger("VPC_PORT", portLevelTJ);
						tdmp.setInteger("PORT_LEVEL", 2);
						tdmp.setLong("DEALER_ID", dealerId);
						tdmp.setLong("Update_By", loginInfo.getUserId());
						tdmp.setTimestamp("UPDATE_DATE", date);
						tdmp.saveIt();
					}

				} else {
					TmDealerMaintenancePO tdm = new TmDealerMaintenancePO();
					tdm.setInteger("VPC_PORT", portLevelTJ);
					tdm.setInteger("PORT_LEVEL", 2);
					tdm.setLong("DEALER_ID", dealerId);
					tdm.setLong("CREATE_BY", loginInfo.getUserId());
					tdm.setTimestamp("CREATE_DATE", date);
					tdm.saveIt();
				}
			}
		}

	}

}
