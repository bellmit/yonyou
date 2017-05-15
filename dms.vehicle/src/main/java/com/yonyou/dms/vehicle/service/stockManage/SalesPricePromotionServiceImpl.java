package com.yonyou.dms.vehicle.service.stockManage;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.PO.basedata.VsStockLogPO;
import com.yonyou.dms.common.domains.PO.basedata.VsStockPO;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.FrameworkUtil;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

@Service
@SuppressWarnings("unchecked")
public class SalesPricePromotionServiceImpl implements SalesPricePromotionService {
	/**
	 * 分页查询
	 */
	@Override
	public PageInfoDto QuerySalesPrice(Map<String, String> queryParam) {
	
		String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
		StringBuffer sb = new StringBuffer();
		sb.append(
				"select  	DISTINCT co.COLOR_NAME,ts.STORAGE_NAME,  M.MODEL_NAME,S.SERIES_NAME,C.CONFIG_NAME ,BB.BRAND_NAME,ST.* from (SELECT   A.DEALER_CODE, A.STORAGE_CODE, A.STORAGE_POSITION_CODE, A.VIN,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE, B.CONFIG_CODE, "
						+ "    COALESCE(A.COLOR_CODE,B.COLOR_CODE) AS COLOR_CODE, A.DIRECTIVE_PRICE,    A.OLD_DIRECTIVE_PRICE,    A.ADDITIONAL_COST, A.IS_CONSIGNED,   A.ADJUST_REASON,A.LATEST_STOCK_IN_DATE,  A.MANUFACTURE_DATE,A.IS_PRICE_ADJUSTED "
						+ " FROM TM_VS_STOCK A  LEFT JOIN ( " + CommonConstants.VM_VS_PRODUCT
						+ " )  B ON A.DEALER_CODE=B.DEALER_CODE  AND A.PRODUCT_CODE=B.PRODUCT_CODE "
						+ "WHERE A.DEALER_CODE='" + dealerCode	+ "'  AND A.D_KEY="
								+ CommonConstants.D_KEY + "  AND A.IS_CONSIGNED=" + CommonConstants.DICT_IS_NO
						+ " AND A.DISPATCHED_STATUS=" + CommonConstants.DICT_DISPATCHED_STATUS_NOT_DISPATCHED + " ) ST ");
		sb.append(" LEFT JOIN TM_MODEL M on  ST.DEALER_CODE =M.DEALER_CODE AND ST.MODEL_CODE=M.MODEL_CODE  LEFT JOIN tm_series  S on  ST.DEALER_CODE =S.DEALER_CODE AND ST.SERIES_CODE=S.SERIES_CODE   LEFT JOIN tm_configuration c on  ST.DEALER_CODE =c.DEALER_CODE AND ST.CONFIG_CODE=c.CONFIG_CODE LEFT JOIN tm_brand bb on  ST.DEALER_CODE =bb.DEALER_CODE AND ST.BRAND_CODE=bb.BRAND_CODE ");
		sb.append("	LEFT JOIN tm_storage ts ON ST.DEALER_CODE = ts.DEALER_CODE AND ST.STORAGE_CODE =ts.STORAGE_CODE  LEFT JOIN tm_color co ON ST.DEALER_CODE = co.DEALER_CODE	AND ST.COLOR_CODE = co.COLOR_CODE  WHERE 1=1 ");

		List<Object> queryList = new ArrayList<Object>();
		this.setWhere(sb, queryParam, queryList);
		PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
		System.out.println(sb.toString());
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
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {// 车系
			sb.append(" and ST.SERIES_CODE = ?");
			queryList.add(queryParam.get("seriesCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("configCode"))) {// 配置
			sb.append(" and ST.CONFIG_CODE = ?");
			queryList.add(queryParam.get("configCode"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {// 配置
			sb.append(" and ST.VIN LIKE ?");
			queryList.add("%" + queryParam.get("vin") + "%");
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("inStorage"))) {// 仓库
			sb.append(" and ST.STORAGE_CODE = ?");
			queryList.add(queryParam.get("inStorage"));
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("isPriceAdjusted"))) {// 是否调价
			sb.append(" and ST.IS_PRICE_ADJUSTED = ?");
			queryList.add(queryParam.get("isPriceAdjusted"));
		}
	}
	
	
	@Override
	public void editsalesPrice(Map<String, String> map) throws ServiceBizException{
		String[] vin = map.get("vins").toString().split(";");
		for (int i = 0; i < vin.length; i++) {
//			System.out.println(vin[i]+"----"+map.get("Cost").toString()+"----"+ map.get("Reason").toString()+"----"+map.get("SalesPrice").toString());
			String dealerCode = FrameworkUtil.getLoginInfo().getDealerCode();
			Long name  =	FrameworkUtil.getLoginInfo().getUserId();
			System.out.println(dealerCode+"----"+name);
			VsStockPO stockPOCon= VsStockPO.findFirst("VIN=?", vin[i]);
		    if (stockPOCon != null) {
		    	 VsStockPO sto = VsStockPO.findByCompositeKeys(dealerCode, vin[i]);
		    	if (!StringUtils.isNullOrEmpty(map.get("Reason"))) {
		    	 sto.setString("ADJUST_REASON", map.get("Reason"));}
		    	if (!StringUtils.isNullOrEmpty(map.get("Cost"))) {
		    	 sto.setDouble("ADDITIONAL_COST", map.get("Cost"));}
		    	if (!StringUtils.isNullOrEmpty(map.get("SalesPrice"))) {
		    	 sto.setDouble("DIRECTIVE_PRICE", map.get("SalesPrice"));}
		    	 sto.setDouble("OLD_DIRECTIVE_PRICE", stockPOCon.getDouble("DIRECTIVE_PRICE"));
		    	 sto.setInteger("IS_PRICE_ADJUSTED", CommonConstants.DICT_IS_YES);
		    	 sto.setTimestamp("UPDATED_AT", new Date());
		    	 sto.setString("UPDATED_BY", name);
//		    	 sto.setString("OWNED_BY", name);
		    	 sto.saveIt();// 保存销售指导价
		    	
		    	 //增加车辆库存日志信息
		    	 //查询车辆库存
		    	 VsStockPO stockPO= VsStockPO.findFirst("VIN=?", vin[i]);
		    	 VsStockLogPO logPO=new VsStockLogPO();
		    	 logPO.setString("DEALER_CODE", dealerCode);
		     	 logPO.setString("VIN", vin[i]);
	
		     	 logPO.setInteger("D_KEY",CommonConstants.D_KEY);
			   	 logPO.setTimestamp("OPERATE_DATE", new Date());
			     logPO.setString("OPERATED_BY",name);
		    	 logPO.setInteger("OPERATION_TYPE",CommonConstants.DICT_VEHICLE_STORAGE_TYPE_CHANGE_PRICE);
		    	 logPO.setDouble("ADDITIONAL_COST", stockPO.getDouble("ADDITIONAL_COST"));
		    	 logPO.setInteger("IS_SECONDHAND",stockPO.getInteger("IS_SECONDHAND"));
		    	 logPO.setInteger("IS_VIP", stockPO.getInteger("IS_VIP"));
		    	 logPO.setInteger("IS_TEST_DRIVE_CAR",stockPO.getInteger("IS_TEST_DRIVE_CAR"));
		     	 logPO.setInteger("IS_CONSIGNED",stockPO.getInteger("IS_CONSIGNED"));//受托交车
		    	 logPO.setInteger("IS_PROMOTION", stockPO.getInteger("IS_PROMOTION"));//促销
		    	 logPO.setInteger("IS_PURCHASE_RETURN",stockPO.getInteger("IS_PURCHASE_RETURN"));//采购退回
		    	 logPO.setInteger("IS_PRICE_ADJUSTED",CommonConstants.DICT_IS_YES);//是否调价
		    	 logPO.setString("ADJUST_REASON", map.get("Reason"));
		    	 logPO.setDouble("ADDITIONAL_COST", map.get("Cost"));
		    	 logPO.setDouble("DIRECTIVE_PRICE", map.get("SalesPrice"));
		    	 logPO.setDouble("OLD_DIRECTIVE_PRICE", stockPO.getDouble("OLD_DIRECTIVE_PRICE"));
		    	 logPO.setInteger("OEM_TAG",stockPO.getInteger("OEM_TAG"));//是否oem
		    	 logPO.setInteger("STOCK_STATUS",stockPO.getInteger("STOCK_STATUS"));//库存状态
		       	 logPO.setInteger("DISPATCHED_STATUS",stockPO.getInteger("DISPATCHED_STATUS"));//配车状态
		    	 logPO.setInteger("MAR_STATUS",stockPO.getInteger("MAR_STATUS"));//质损状态
		    	 logPO.setDouble("PURCHASE_PRICE", stockPO.getDouble("PURCHASE_PRICE"));//采购价格
		    	 logPO.setDouble("ADDITIONAL_COST", stockPO.getDouble("ADDITIONAL_COST"));//附加价格
		    	 logPO.setString("STORAGE_CODE",stockPO.getString("STORAGE_CODE"));
		    	 System.out.println(stockPO.getInteger("STOCK_STATUS")+"----"+ stockPO.getDouble("ADDITIONAL_COST")+"----"+ stockPO.getInteger("OEM_TAG"));
		    	 logPO.setString("STORAGE_POSITION_CODE",stockPO.getString("STORAGE_POSITION_CODE"));
		    	 logPO.setString("PRODUCT_CODE",stockPO.getString("PRODUCT_CODE"));
		    	 logPO.setString("CREATED_BY", name);
		    	 logPO.setString("OWNED_BY", name);
		    	 //增加车辆库存日志信息
		    	 logPO.saveIt();
		    }
		}		
	}
}
