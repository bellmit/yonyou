package com.yonyou.dms.vehicle.service.stockLog;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.stockmanage.VsInspectionMarPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 库存日志接口实现类
 * 
 * @author Benzc
 * @date 2017年2月13日
 */
@Service
public class StockLogServiceImpl implements StockLogService {

	/**
	 * 根据库存日志ID查询
	 */
	@Override
	public VsStockLogPO getStockLogById(String id) throws ServiceBizException {
		return VsStockLogPO.findById(id);
	}

	/**
	 * 根据质损信息ID查询
	 */
	@Override
	public VsInspectionMarPO getInspectionMarById(String itemId) throws ServiceBizException {
		return VsInspectionMarPO.findById(itemId);
	}

	/**
	 * 分页查询
	 */
	@Override
	public PageInfoDto QueryStockLog(Map<String, String> queryParam) {
		String vin = queryParam.get("vin");
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT A.DEALER_CODE,A.VIN,A.PRODUCT_CODE,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE, br.BRAND_NAME,se.SERIES_NAME,mo.MODEL_NAME,pa.CONFIG_NAME,co.COLOR_NAME,COALESCE((SELECT s.COLOR_CODE FROM TM_VS_STOCK s ");
		sb.append(" WHERE s.VIN='" + vin + "' and s.DEALER_CODE='" + dealerCode + "'),B.COLOR_CODE) AS COLOR_CODE ");
		sb.append(" FROM TT_VS_STOCK_LOG A ,(" + CommonConstants.VM_VS_PRODUCT + ") B ");
		sb.append(" LEFT JOIN TM_BRAND br ON B.BRAND_CODE=br.BRAND_CODE AND B.DEALER_CODE=br.DEALER_CODE ");
		sb.append(" LEFT JOIN TM_SERIES  se   ON   B.SERIES_CODE=se.SERIES_CODE AND B.DEALER_CODE=se.DEALER_CODE ");
		sb.append(" LEFT JOIN TM_MODEL   mo   ON   B.MODEL_CODE=mo.MODEL_CODE AND B.DEALER_CODE=mo.DEALER_CODE ");
		sb.append(
				" LEFT JOIN tm_configuration pa   ON  B.CONFIG_CODE=pa.CONFIG_CODE AND B.DEALER_CODE=pa.DEALER_CODE ");
		sb.append(" LEFT JOIN TM_COLOR co ON B.COLOR_CODE=co.COLOR_CODE AND B.DEALER_CODE=co.DEALER_CODE");
		sb.append(" WHERE A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE ");
		sb.append(" AND A.DEALER_CODE='" + dealerCode + "' AND A.D_KEY=0 ");
		List<Object> queryList = new ArrayList<Object>();
		this.setWhere(sb, queryParam, queryList);
		sb.append(" GROUP BY A.VIN,A.PRODUCT_CODE,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,B.COLOR_CODE ");
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 查询条件设置
	 * 
	 * @param sb
	 * @param queryParam
	 * @param queryList
	 */
	public void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
		if (!StringUtils.isNullOrEmpty(queryParam.get("storageName"))) {
			sb.append(" and A.STORAGE_CODE like ?");
			queryList.add("%" + queryParam.get("storageName") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sb.append(" and A.VIN like ?");
			queryList.add("%" + queryParam.get("vin") + "%");
		}
	}

	/**
	 * 查询明细根据vin起始日期和结束日期
	 */
	@Override
	public PageInfoDto QueryStockLogVehicle(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(
				" SELECT A.DEALER_CODE,A.VIN,A.PRODUCT_CODE,A.OPERATE_DATE,A.OPERATION_TYPE,A.OPERATED_BY,A.OPERATION_REMARK,A.STORAGE_CODE,");
		sb.append(
				" A.STORAGE_POSITION_CODE,A.PURCHASE_PRICE,A.ADDITIONAL_COST, A.STOCK_STATUS,A.DISPATCHED_STATUS,A.MAR_STATUS,");
		sb.append(
				" CASE WHEN A.IS_SECONDHAND=12781001 THEN 10571001 END AS IS_SECONDHAND,CASE WHEN A.IS_VIP=12781001 THEN 10571001 END AS IS_VIP,");
		sb.append(
				" CASE WHEN A.IS_TEST_DRIVE_CAR=12781001 THEN 10571001 END AS IS_TEST_DRIVE_CAR,CASE WHEN A.IS_CONSIGNED=12781001 THEN 10571001 END AS IS_CONSIGNED,");
		sb.append(
				" CASE WHEN A.IS_PROMOTION=12781001 THEN 10571001 END AS IS_PROMOTION,CASE WHEN A.IS_PURCHASE_RETURN=12781001 THEN 10571001 END AS IS_PURCHASE_RETURN,");
		sb.append(" CASE WHEN A.IS_PRICE_ADJUSTED=12781001 THEN 10571001 END AS IS_PRICE_ADJUSTED,");
		sb.append(
				" A.ADJUST_REASON, A.OLD_DIRECTIVE_PRICE,A.DIRECTIVE_PRICE,CASE WHEN A.OEM_TAG=12781001 THEN 10571001 END AS OEM_TAG,S.STORAGE_NAME,U.USER_NAME ");
		sb.append(" FROM TT_VS_STOCK_LOG A ");
		sb.append(" LEFT JOIN TM_STORAGE S ON S.STORAGE_CODE=A.STORAGE_CODE AND S.DEALER_CODE=A.DEALER_CODE ");
		sb.append(" LEFT JOIN TM_USER U ON U.USER_ID=A.OPERATED_BY AND U.DEALER_CODE=A.DEALER_CODE ");
		sb.append(" WHERE A.DEALER_CODE='" + dealerCode + "' AND A.D_KEY=0 AND A.VIN=" + "'" + id + "'");
		List<Object> queryList = new ArrayList<Object>();

		if (!StringUtils.isNullOrEmpty(queryParam.get("startDate"))) {
			sb.append(" AND A.OPERATE_DATE >= ?");
			queryList.add(DateUtil.parseDefaultDate(queryParam.get("startDate")));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
			sb.append(" AND A.OPERATE_DATE < ?");
			queryList.add(DateUtil.addOneDay(queryParam.get("endDate")));
		}
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);

		System.err.println(sb.toString());
		return result;
	}

	/**
	 * 根据车辆VIN查询车辆信息
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryVehicleByVin(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.DEALER_CODE,A.VIN,B.PRODUCT_CODE,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE,B.COLOR_CODE, ");
		sb.append(" B.PRODUCT_TYPE,A.STORAGE_CODE,A.STORAGE_POSITION_CODE,A.STOCK_STATUS,A.DISPATCHED_STATUS,");
		sb.append(" A.MAR_STATUS,A.REMARK FROM TM_VS_STOCK A ");
		sb.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") B ON A.DEALER_CODE=B.DEALER_CODE AND A.D_KEY=B.D_KEY AND A.PRODUCT_CODE=B.PRODUCT_CODE ");
		sb.append(" WHERE A.DEALER_CODE='"+ dealerCode+ "' AND A.D_KEY=0 AND vin="+"'"+id+"'");
		System.err.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		List<Map> result = DAOUtil.findAll(sb.toString(), queryList);
		return result;
	}

	/**
	 * 根据入库单号和vin查询明细信息
	 */
	@Override
	public PageInfoDto queryInspectionMar(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT A.DEALER_CODE,A.SE_NO,A.PRODUCT_CODE,A.VIN,A.ENGINE_NO,A.SHIPPING_DATE,A.SHIPPER_LICENSE,A.SHIPPER_NAME,");
		sb.append(" A.DELIVERYMAN_NAME,A.DELIVERYMAN_PHONE,");
		sb.append(" A.ARRIVING_DATE,A.ARRIVED_DATE,A.INSPECTOR,A.INSPECTION_DATE,A.MAR_STATUS,A.OEM_TAG,A.REMARK,U.USER_NAME ");
		sb.append(" FROM TT_VS_STOCK_ENTRY_ITEM  A");
		sb.append(" LEFT JOIN TM_USER U ON A.INSPECTOR=U.USER_ID");
		sb.append(" WHERE A.DEALER_CODE='" + dealerCode + "' AND A.D_KEY=0 AND A.vin="+"'" + id + "'");
		System.err.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		return result;
	}

	/**
	 * 根据VIN查询整车质损明细
	 */
	@Override
	public PageInfoDto queryMar(Map<String, String> queryParam, String id) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DEALER_CODE,ITEM_ID,SE_NO,VIN,MAR_POSITION,MAR_DEGREE,MAR_TYPE,MAR_REMARK ");
		sb.append(" FROM TT_VS_INSPECTION_MAR WHERE DEALER_CODE='" + dealerCode + "' AND D_KEY=0 AND VIN=" + "'"+ id + "'");
		System.err.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		return result;
	}

	/**
	 * 查询整车质损附件
	 */
	@Override
	public PageInfoDto queryMarAttachment(Map<String, String> queryParam, String itemId) {
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,FILE_ID,MAR_ATTACHMENT_ID AS ATTACHED,ITEM_ID,MAR_ATTACHMENT_NAME AS FILE_NAME FROM TT_VS_MAR_ATTACHMENT WHERE DEALER_CODE='"
						+ dealerCode + "' AND D_KEY=0" + " AND ITEM_ID=" + "'" + itemId + "'");
		System.out.println(sb.toString());
		List<Object> queryList = new ArrayList<Object>();
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		return result;
	}

}
