package com.yonyou.dms.vehicle.service.k4Order;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.TmOrderPayChangePO;
import com.yonyou.dms.framework.domain.LoginInfoDto;
import com.yonyou.dms.framework.service.excel.ExcelDataType;
import com.yonyou.dms.framework.service.excel.ExcelExportColumn;
import com.yonyou.dms.framework.service.excel.ExcelGenerator;
import com.yonyou.dms.framework.util.bean.ApplicationContextHelper;
import com.yonyou.dms.function.common.OemDictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.vehicle.dao.k4Order.CancelOrderApplyDao;
import com.yonyou.dms.vehicle.domains.DTO.k4Order.K4OrderDTO;

/**
 * @author liujiming
 * @date 2017年3月10日
 */
@Service
public class CancelOrderApplyServiceImpl implements CancelOrderApplyService {

	@Autowired
	private ExcelGenerator excelService;
	@Autowired
	private CancelOrderApplyDao cancleAppDao;

	/**
	 * 撤单申请(经销商)下载
	 */
	@Override
	public void findCancelOrderApplyDownload(Map<String, String> queryParam, HttpServletRequest request,
			HttpServletResponse response) throws ServiceBizException {
		List<Map> orderList = cancleAppDao.getCancelOrderApplyDownloadList(queryParam);
		Map<String, List<Map>> excelData = new HashMap<>();
		excelData.put("撤单申请(经销商)", orderList);
		List<ExcelExportColumn> exportColumnList = new ArrayList<>();
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

		excelService.generateExcel(excelData, exportColumnList, "撤单申请(经销商).xls", request, response);

	}

	/**
	 * 撤单申请(经销商) 修改方法
	 */
	@Override
	public void updateTmOrderPayChangeByVIN(K4OrderDTO k4OrderDTO) throws ServiceBizException {
		LoginInfoDto loginInfo = ApplicationContextHelper.getBeanByType(LoginInfoDto.class);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String format = df.format(new Date());
		String[] arrVins = k4OrderDTO.getVins().split(",");
		String[] arrIds = k4OrderDTO.getOrderIds().split(",");
		String[] split = k4OrderDTO.getIsEc().split(",");
		for (int i = 0; i < arrVins.length; i++) {
			// 批处理数据
			String id = arrIds[i];
			String vin = arrVins[i];
			TmOrderPayChangePO topChange = new TmOrderPayChangePO();
			topChange.setLong("CREATE_BY", loginInfo.getUserId());
			topChange.setTimestamp("CREATE_DATE", format);
			topChange.setString("VIN", vin);
			topChange.setLong("ORDER_ID", new Long(id));
			topChange.setLong("DEALER_ID", loginInfo.getDealerId());
			topChange.setTimestamp("APPLY_DATE", format);
			topChange.setTimestamp("AUDIT_DATE", format);
			topChange.setTimestamp("UPDATE_DATE", format);
			topChange.setLong("UPDATE_BY", loginInfo.getUserId());
			topChange.setInteger("AUDIT_STATUS", OemDictCodeConstants.CANCEL_ORDER_APPLY_STATUS_01);// 申请状态(小区待审核）
			topChange.setInteger("AUDIT_TYPE", OemDictCodeConstants.DUTY_TYPE_SMALLREGION);// 小区
			topChange.saveIt();
		}
	}

}
