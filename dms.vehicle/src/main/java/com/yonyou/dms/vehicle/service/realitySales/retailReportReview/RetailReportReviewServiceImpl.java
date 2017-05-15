package com.yonyou.dms.vehicle.service.realitySales.retailReportReview;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TiVehicleInspectionPO;
import com.yonyou.dms.common.domains.PO.basedata.TiVehicleRetailPO;
import com.yonyou.dms.common.domains.PO.basedata.TtVsInspectionPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.realitySales.retailReportReview.RetailReportReviewDao;

@Service
@SuppressWarnings("all")
public class RetailReportReviewServiceImpl implements RetailReportReviewService {
	
	private static final Logger logger = LoggerFactory.getLogger(DAOUtil.class);
	
	@Autowired
	private RetailReportReviewDao dao;

	/**
	 * 零售上报审核查询
	 */
	@Override
	public PageInfoDto retailReportReviewQuery(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		PageInfoDto pgInfo = dao.queryList(queryParam,loginInfo);
		return pgInfo;
	}

	/**
	 * 零售上报审核下载
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> retailReportReviewQuerySuccList(Map<String, String> queryParam, LoginInfoDto loginInfo)
			throws ServiceBizException {
		List<Map> reviewList = dao.queryRetailReportQueryForExport(queryParam,loginInfo);
		return reviewList;
	}

	/**
	 * 根据ID获取详细信息
	 */
	@Override
	public Map<String, Object> queryDetail(Long id) throws ServiceBizException {
		Map<String, Object> detailMap = dao.queryDetail(id);
		return detailMap;
	}

	/**
	 * 批量通过
	 */
	@Override
	public void batchPass(String nvdrId,LoginInfoDto loginInfo) throws ServiceBizException {
		Date nowDate = new Date();
		SimpleDateFormat sDate = new SimpleDateFormat("yyyy-MM-dd");
		String arriveDate = sDate.format(nowDate);
		dao.updateTtVsNvdr(nvdrId, loginInfo);	// 更新零售表交车状态
		dao.insertTiVehicleRetail(nvdrId,loginInfo); // 进口车零售接口表写入
		dao.insertTiK4VsNvdr(nvdrId, loginInfo);// 国产车零售接口表写入
			
		List<Map> list = dao.lackInspectionVinQuery(nvdrId);//查询未验收的车辆
		if (list.size() > 0) {
			for (int i = 0; i < list.size(); i++) {
				String vehicleId = list.get(i).get("VEHICLE_ID").toString();
				String vin = list.get(i).get("VIN").toString();
				String dealerCode = list.get(i).get("DEALER_CODE").toString();
				String groupType = list.get(i).get("GROUP_TYPE").toString();
				
				if (groupType.equals(OemDictCodeConstants.GROUP_TYPE_DOMESTIC)) {
					
					// 国产车业务数据处理
					TtVsInspectionPO inspectionPo = new TtVsInspectionPO();
					List<TtVsInspectionPO> inspectionPoList = inspectionPo.find("VEHICLE_ID = ?", vehicleId);
					if (null != inspectionPoList && inspectionPoList.size() > 0) {
						//DMS上报实际到车日期日期为NULL判断
						if(inspectionPoList.get(0).getDate("ARRIVE_DATE") != null){ //getArriveDate()
							nowDate = inspectionPoList.get(0).getDate("ARRIVE_DATE");
							arriveDate = sDate.format(nowDate);
							dao.insertTiK4VsNvdr2(arriveDate, dealerCode, null, vin);//插入国产车验收接口表
						}else{
							arriveDate = sDate.format(nowDate);
							dao.insertTiK4VsNvdr2(arriveDate, dealerCode, null, vin);//插入国产车验收接口表
						}
					} else {
						arriveDate = sDate.format(nowDate);
						dao.insertTiK4VsNvdr2(arriveDate, dealerCode, null, vin);//插入国产车验收接口表
					}
				} else if (groupType.equals(OemDictCodeConstants.GROUP_TYPE_IMPORT)) {
					// 进口车业务数据处理
					TiVehicleRetailPO vehicleRetail = new TiVehicleRetailPO();
					List<TiVehicleRetailPO> vehicleRetailList = vehicleRetail.find("   VIN = ?", vin);
					TiVehicleRetailPO tvrPO = vehicleRetailList.get(0);
					TiVehicleInspectionPO po = new TiVehicleInspectionPO();
					po.setString("DEALER_CODE",tvrPO.getString("DEALER_CODE"));
					po.setString("ACTION_CODE", "ZDLD");
					po.setDate("ACTION_DATE", tvrPO.getDate("ACTION_DATE"));
					po.setString("VIN", tvrPO.getString("VIN"));
					po.setLong("CREATE_BY", null); // DEConstant.DE_CREATE_BY  (接口需要)
					po.setDate("CREATE_DATE", tvrPO.getDate("CREATE_DATE"));
					po.setString("IS_SCAN", "0");
					po.setString("IS_CTCAI_SCAN", "0");
					po.insert();
				} else {
					throw new ServiceBizException("车辆：" + vin + ", 没有业务类别, 写入验收接口表失败!"); 
				}
			}
		}
	}
	
}
