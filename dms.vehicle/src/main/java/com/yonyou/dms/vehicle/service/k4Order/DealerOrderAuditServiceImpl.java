package com.yonyou.dms.vehicle.service.k4Order;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmOrderPayChangePO;
import com.yonyou.dms.framework.DAO.OemDAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.controller.k4Order.DealerCancelOrderAreaAuditController;
import com.yonyou.dms.vehicle.dao.k4Order.DealerOrderAuditDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;

/**
 *
 * 
 * @author liujiming
 * @date 2017年2月16日
 */
@Service
public class DealerOrderAuditServiceImpl implements DealerOrderAuditService {
	// 定义日志接口
	private static final Logger logger = LoggerFactory.getLogger(DealerCancelOrderAreaAuditController.class);

	@Autowired
	private DealerOrderAuditDao dealerCancelOrderAuditDao;

	@Autowired
	private ExcelGenerator excelService;

	/**
	 * 经销商撤单审核查询
	 */
	@Override
	public PageInfoDto queryInit1(Map<String, String> queryParam) throws ServiceBizException {
		// System.out.println("queryInit:"+queryParam);
		PageInfoDto pageInfoDto = dealerCancelOrderAuditDao.getDealerCancelOrderList1(queryParam);

		return pageInfoDto;

	}

	/**
	 * 经销商撤单审核查询(小区)
	 */
	@Override
	public PageInfoDto queryInit(Map<String, String> queryParam) throws ServiceBizException {
		// System.out.println("queryInit:"+queryParam);
		PageInfoDto pageInfoDto = dealerCancelOrderAuditDao.getDealerCancelOrderList(queryParam);

		return pageInfoDto;

	}

	/**
	 * 经销商撤单通过
	 */

	@Override
	public void modifyOderPass(K4OrderDTO k4OrderDTO) throws ServiceBizException {
		String[] ids = k4OrderDTO.getIds().split(",");
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);

			String sql = "update TM_ORDER_PAY_CHANGE set AUDIT_TYPE=" + OemDictCodeConstants.DUTY_TYPE_DEPT
					+ " , AUDIT_STATUS=" + OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_03 + " where ID in(" + id
					+ ")";
			OemDAOUtil.execBatchPreparement(sql, new ArrayList<>());
			// TmOrderPayChangePO upPo = TmOrderPayChangePO.findById(id);
			// // 设置对象属性
			// setK4OrderPassPO(upPo, k4OrderDTO);
			// upPo.saveIt();
		}

	}

	@Override
	public void modifyOderPass11(K4OrderDTO k4OrderDTO) {
		String[] ids = k4OrderDTO.getIds().split(",");
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);

			String sql = "update TM_ORDER_PAY_CHANGE set AUDIT_TYPE=" + OemDictCodeConstants.DUTY_TYPE_DEPT
					+ " , AUDIT_STATUS=" + OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_04 + " where ID in(" + id
					+ ")";
			OemDAOUtil.execBatchPreparement(sql, new ArrayList<>());

		}

	}

	/**
	 * 经销商撤单驳回()
	 */

	@Override
	public void modifyOderReject(K4OrderDTO k4OrderDTO) throws ServiceBizException {
		String[] ids = k4OrderDTO.getIds().split(",");
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);
			String sql = "update TM_ORDER_PAY_CHANGE set AUDIT_STATUS="
					+ OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_05 + ",AUDIT_DATE=current_timestamp where ID in("
					+ id + ")";
			OemDAOUtil.execBatchPreparement(sql, new ArrayList<>());
			// TmOrderPayChangePO upPo = TmOrderPayChangePO.findById(id);
			// 设置对象属性
			// setK4OrderRejectPO(upPo, k4OrderDTO);
			// upPo.saveIt();
		}

	}

	/**
	 * 经销商撤单驳回(小区)
	 */
	@Override
	public void modifyOderRejectSam(K4OrderDTO k4OrderDTO) {
		String[] ids = k4OrderDTO.getIds().split(",");
		for (int i = 0; i < ids.length; i++) {
			Long id = Long.parseLong(ids[i]);
			String sql = "update TM_ORDER_PAY_CHANGE set AUDIT_STATUS="
					+ OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_02 + ",AUDIT_DATE=current_timestamp where ID in("
					+ id + ")";
			OemDAOUtil.execBatchPreparement(sql, new ArrayList<>());
		}
	}

	/**
	 * 
	 * 设置对象属性
	 * 
	 * @author liujiming
	 * @date 2017年2月19日
	 * @param id
	 */

	private void setK4OrderPassPO(TmOrderPayChangePO k4OrderPO, K4OrderDTO k4OrderDTO) {

		k4OrderPO.setLong("AUDIT_STATUS", OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_04);
		k4OrderPO.setLong("IS_SUC", 3);
	}

	private void setK4OrderRejectPO(TmOrderPayChangePO k4OrderPO, K4OrderDTO k4OrderDTO) {

		k4OrderPO.setLong("AUDIT_STATUS", OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_05);
		k4OrderPO.setTimestamp("AUDIT_DATE", new Date(System.currentTimeMillis()));

	}

	@Override
	public PageInfoDto queryAuditList(Map<String, String> queryParam) throws ServiceBizException {

		PageInfoDto pageInfoDto = dealerCancelOrderAuditDao.findCancelOrderApplyList(queryParam);

		return pageInfoDto;
	}

	@Override
	public void findCancleOrderDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = dealerCancelOrderAuditDao.findCancelOrderApplyDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("经销商撤单审核（大区）", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "销售大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME_SMALL", "销售小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE", "付款方式", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "外饰颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰颜色"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "车辆分配备注", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		exportColumnList.add(new ExcelExportColumn("OTHER_REMARK", "其它备注"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "是否锁定", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "申请日期"));
		exportColumnList.add(new ExcelExportColumn("IS_SUC", "撤单状态"));
		exportColumnList.add(new ExcelExportColumn("ERROR_INFO", "失败原因"));
		exportColumnList.add(new ExcelExportColumn("AUDIT_STATUS", "审核状态", ExcelDataType.Oem_Dict));
		excelService.generateExcel(excelData, exportColumnList, "经销商审核查询信息.xls", request, response);

	}

	/**
	 * 审核查询下载
	 */
	@Override
	public void findCancleOrderAuditDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		logger.info("============经销商撤单查询下载===============");
		List<Map> orderList = dealerCancelOrderAuditDao.findDealerCancelOrderDownload(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("经销商撤单审核（小区）", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
		exportColumnList.add(new ExcelExportColumn("ORG_NAME", "销售大区"));
		exportColumnList.add(new ExcelExportColumn("ORG_NAME_SMALL", "销售小区"));
		exportColumnList.add(new ExcelExportColumn("DEALER_CODE", "经销商代码"));
		exportColumnList.add(new ExcelExportColumn("DEALER_SHORTNAME", "经销商名称"));
		exportColumnList.add(new ExcelExportColumn("ORDER_TYPE", "订单类型", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("PAYMENT_TYPE", "付款方式", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VIN", "VIN"));
		exportColumnList.add(new ExcelExportColumn("BRAND_CODE", "品牌"));
		exportColumnList.add(new ExcelExportColumn("SERIES_NAME", "车系"));
		exportColumnList.add(new ExcelExportColumn("GROUP_NAME", "车款"));
		exportColumnList.add(new ExcelExportColumn("COLOR_NAME", "外饰颜色"));
		exportColumnList.add(new ExcelExportColumn("TRIM_NAME", "内饰颜色"));
		exportColumnList.add(new ExcelExportColumn("REMARK", "车辆分配备注", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("VEHICLE_USAGE", "车辆用途"));
		exportColumnList.add(new ExcelExportColumn("OTHER_REMARK", "其它备注"));
		exportColumnList.add(new ExcelExportColumn("IS_LOCK", "是否锁定", ExcelDataType.Oem_Dict));
		exportColumnList.add(new ExcelExportColumn("CREATE_DATE", "申请日期"));
		// exportColumnList.add(new ExcelExportColumn("IS_SUC","撤单状态"));
		// exportColumnList.add(new ExcelExportColumn("ERROR_INFO", "失败原因"));
		// exportColumnList.add(new ExcelExportColumn("AUDIT_STATUS","审核状态",
		// ExcelDataType.Dict));
		// exportColumnList.add(new ExcelExportColumn("AUDIT_STATUS","审核状态"));
		excelService.generateExcel(excelData, exportColumnList, "经销商审核信息.xls", request, response);

	}

}
