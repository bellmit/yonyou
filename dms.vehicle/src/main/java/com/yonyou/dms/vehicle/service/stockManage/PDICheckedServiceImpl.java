package com.yonyou.dms.vehicle.service.stockManage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.javalite.activejdbc.Base;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yonyou.dms.common.domains.DTO.monitor.OperateLogDto;
import com.yonyou.dms.common.domains.PO.basedata.VehiclePdiResultDetailPO;
import com.yonyou.dms.common.domains.PO.basedata.VehiclePdiResultPO;
import com.yonyou.dms.common.service.monitor.OperateLogService;
import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.framework.util.Utility;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.DateUtil;
import com.yonyou.dms.function.utils.common.StringUtils;
import com.yonyou.dms.vehicle.domains.DTO.stockManage.PDICheckedDTO;

/**
 * PDI检查实现类
 * 
 * @author wangliang
 * @date 2017年1月11日
 */

@Service
@SuppressWarnings("unchecked")
public class PDICheckedServiceImpl implements PDICheckedService {
	@Autowired
	private OperateLogService operateLogService;

	/**
	 * 前台界面查询方法
	 * 
	 * @author wangliang
	 * @date 2017年1月11日
	 */
	@Override
	public PageInfoDto queryPDIChecked(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT B.*,BR.BRAND_NAME,SE.SERIES_NAME, MO.MODEL_NAME,PA.CONFIG_NAME FROM ( ");
		sb.append(
				" SELECT f.MILEAGE AS MILEAGE,f.dealer_code AS DEALER_CODE, f.se_no AS SE_NO, f.vin AS VIN,f.IS_MUST_PDI AS IS_MUST_PDI, f.brand_code AS BRAND_CODE,f.series_code AS SERIES_CODE, f.model_code AS MODEL_CODE,f.config_code AS CONFIG_CODE,f.PDI_RESULT AS PDI_RESULT,f.PDI_CHECK_DATE AS PDI_CHECK_DATE,f.PDI_SUBMIT_DATE AS PDI_SUBMIT_DATE,f.PDI_URL AS PDI_URL,f.TH_REPORT_NO AS TH_REPORT_NO,f.PDI_REMARK AS PDI_REMARK ");
		sb.append(" FROM(  ");
		sb.append(
				" SELECT d.MILEAGE,a.dealer_code, a.se_no, b.vin,c.IS_MUST_PDI,c.brand_code,c.series_code,c.model_code,c.config_code,d.PDI_RESULT,d.PDI_CHECK_DATE,d.PDI_SUBMIT_DATE,d.PDI_URL,d.TH_REPORT_NO ,e.PDI_REMARK  AS PDI_REMARK FROM Tt_Vs_Stock_Entry a LEFT JOIN Tt_Vs_Stock_Entry_item b ON a.dealer_code=b.dealer_code AND a.SE_NO=b.SE_NO  LEFT JOIN tm_vs_product c ON a.dealer_code=c.dealer_code AND b.product_code=c.product_code LEFT JOIN TT_VEHICLE_PDI_RESULT d  ON a.dealer_code=d.dealer_code AND b.vin=d.vin LEFT JOIN TT_VEHICLE_PDI_RESULT_DETAIL E ON a.dealer_code=E.dealer_code AND E.vin=d.vin  GROUP BY d.MILEAGE,a.dealer_code, a.se_no, b.vin,c.IS_MUST_PDI,c.brand_code,c.series_code, c.model_code,c.config_code,d.PDI_RESULT,d.PDI_CHECK_DATE,d.PDI_SUBMIT_DATE,d.PDI_URL,d.TH_REPORT_NO ");
		sb.append(" UNION ALL ");
		sb.append(
				" SELECT e.MILEAGE,dealer_code, '' AS se_no, e.vin,e.IS_MUST_PDI,e.brand_code,e.series_code,e.model_code,e.config_code, e.PDI_RESULT,e.PDI_CHECK_DATE,e.PDI_SUBMIT_DATE,e.PDI_URL,e.TH_REPORT_NO ,e.PDI_REMARK ");
		sb.append(" FROM ( ");
		sb.append(
				" SELECT  d.MILEAGE,a.dealer_code,a.vin,c.IS_MUST_PDI,c.brand_code,c.series_code,c.model_code,c.config_code, d.PDI_RESULT,d.PDI_CHECK_DATE,d.PDI_SUBMIT_DATE, d.PDI_URL,d.TH_REPORT_NO ,E.PDI_REMARK AS PDI_REMARK FROM Tt_Vs_Shipping_Notify a LEFT JOIN tm_vs_product c ON  a.dealer_code=c.dealer_code   AND a.product_code=c.product_code LEFT JOIN TT_VEHICLE_PDI_RESULT d ON a.dealer_code=d.dealer_code  AND a.vin=d.vin   LEFT JOIN TT_VEHICLE_PDI_RESULT_DETAIL E ON a.dealer_code=E.dealer_code AND E.vin=d.vin WHERE  NOT EXISTS ( SELECT    1 FROM  Tt_Vs_Stock_Entry_item b WHERE  b.dealer_code=a.dealer_code  AND b.vin=a.vin )  GROUP BY d.MILEAGE,a.dealer_code, a.vin,c.IS_MUST_PDI,c.brand_code,c.series_code,c.model_code,c.config_code, d.PDI_RESULT,d.PDI_CHECK_DATE,d.PDI_SUBMIT_DATE, d.PDI_URL,d.TH_REPORT_NO ");
		sb.append("  )e)f  ");
		sb.append(" WHERE f.IS_MUST_PDI = 12781001 ");
		// 创建参数的list列表
		List<Object> insuranceSql = new ArrayList<Object>();

		if (!StringUtils.isNullOrEmpty(queryParam.get("IsCompletePDI"))) {
			// 完成检查
			if ("89991001".equals(queryParam.get("IsCompletePDI"))) {
				sb.append(
						"AND EXISTS (SELECT 1 FROM TT_VEHICLE_PDI_RESULT b WHERE f.dealer_code=b.dealer_code AND f.vin=b.vin )");
				if (!StringUtils.isNullOrEmpty(queryParam.get("PDI_RESULT"))) {
					sb.append(" AND f.PDI_RESULT like ? ");
					insuranceSql.add("%" + queryParam.get("PDI_RESULT") + "%");
				}
				if (!StringUtils.isNullOrEmpty(queryParam.get("PDI_REMARK"))) {
					sb.append(" AND f.PDI_REMARK like ? ");
					insuranceSql.add("%" + queryParam.get("PDI_REMARK") + "%");
				}

				if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
					sb.append(" and f.PDI_CHECK_DATE >= ?");
					insuranceSql.add(DateUtil.parseDefaultDate(queryParam.get("beginDate")));
				}
				if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
					sb.append(" and f.PDI_CHECK_DATE < ?");
					insuranceSql.add(DateUtil.addOneDay(queryParam.get("endDate")));
				}

				if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate1"))) {
					sb.append(" and f.PDI_SUBMIT_DATE >= ?");
					insuranceSql.add(DateUtil.parseDefaultDate(queryParam.get("beginDate1")));
				}
				if (!StringUtils.isNullOrEmpty(queryParam.get("endDate1"))) {
					sb.append(" and f.PDI_SUBMIT_DATE < ?");
					insuranceSql.add(DateUtil.addOneDay(queryParam.get("endDate1")));
				}

				if (!StringUtils.isNullOrEmpty(queryParam.get("pdiResult"))) {
					sb.append(" AND f.PDI_RESULT like ? ");
					insuranceSql.add("%" + queryParam.get("pdiResult") + "%");
				}

			}
			// 没有完成检查
			else if ("89991002".equals(queryParam.get("IsCompletePDI"))) {
				sb.append(
						" AND NOT EXISTS (   SELECT 1 FROM Tt_Vs_Stock_Entry_item b    WHERE f.dealer_code=b.dealer_code AND f.vin=b.vin AND b.is_finished=12781001 ) AND NOT EXISTS (SELECT 1 FROM TT_VEHICLE_PDI_RESULT b WHERE f.dealer_code=b.dealer_code AND f.vin=b.vin ) ");
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sb.append(" AND f.VIN like ? ");
			insuranceSql.add("%" + queryParam.get("vin") + "%");
		}
		sb.append(" order by f.PDI_SUBMIT_DATE desc ");
		sb.append(
				" ) B LEFT JOIN tm_brand BR  ON B.BRAND_CODE = BR.BRAND_CODE  AND B.DEALER_CODE = BR.DEALER_CODE  LEFT JOIN TM_SERIES se  ON B.SERIES_CODE = se.SERIES_CODE  AND br.BRAND_CODE = se.BRAND_CODE  AND br.DEALER_CODE = se.DEALER_CODE  LEFT JOIN TM_MODEL mo  ON B.MODEL_CODE = mo.MODEL_CODE  AND mo.BRAND_CODE = se.BRAND_CODE  AND mo.series_code = se.series_code  AND se.DEALER_CODE = mo.DEALER_CODE  LEFT  JOIN   tm_configuration pa   ON   B.CONFIG_CODE=pa.CONFIG_CODE AND pa.brand_code=mo.brand_code AND pa.series_code=mo.series_code AND pa.model_code=mo.model_code AND mo.DEALER_CODE=pa.DEALER_CODE ");
		System.out.println("******************************************");
		System.out.println(sb.toString());
		System.out.println("******************************************");

		System.out.println("******************************************");
		System.out.println(queryParam.get("beginDate"));
		System.out.println(queryParam.get("endDate"));
		System.out.println(queryParam.get("beginDate1"));
		System.out.println(queryParam.get("endDate1"));
		System.out.println("******************************************");
		PageInfoDto id = DAOUtil.pageQuery(sb.toString(), insuranceSql);
		return id;
	}

	/**
	 * 根据vin 修改保存
	 * 
	 * @author wangliang
	 * @date 2017年1月12日
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public void update(String vin, PDICheckedDTO pdiCheckeddto) throws ServiceBizException {
		// VehiclePdiResultPO pdicheckedpo =
		// VehiclePdiResultPO.findByCompositeKeys(FrameworkUtil.getLoginInfo().getDealerCode(),
		// vin);
		// List<VehiclePdiResultPO> list = VehiclePdiResultPO.findBySQL("select
		// * from tt_vehicle_pdi_result where dealer_code = ? ", new
		// Object[]{FrameworkUtil.getLoginInfo().getDealerCode()});
		VehiclePdiResultPO pdicheckedpo = new VehiclePdiResultPO();
		StringBuffer sb = new StringBuffer("select * from tt_vehicle_pdi_result where vin = ?");
		List<Object> list = new ArrayList<Object>();
		list.add(vin);
		List<Map> map = DAOUtil.findAll(sb.toString(), list);
		if (map.size() > 0) {
			throw new ServiceBizException("VIN不能重复！");
		} else {
			pdicheckedpo.setString("VIN", vin);
			if (!StringUtils.isNullOrEmpty(pdiCheckeddto.getPdiCheckDate())) {
				pdicheckedpo.setDate("PDI_CHECK_DATE", pdiCheckeddto.getPdiCheckDate());
			}
			if (!StringUtils.isNullOrEmpty(pdiCheckeddto.getThReportNo())) {
				pdicheckedpo.setString("TH_REPORT_NO", pdiCheckeddto.getThReportNo());
			}
			if (!StringUtils.isNullOrEmpty(pdiCheckeddto.getPdiResult())) {
				pdicheckedpo.setInteger("PDI_RESULT", pdiCheckeddto.getPdiResult());
			}
			if (!StringUtils.isNullOrEmpty(pdiCheckeddto.getMileAge())) {
				pdicheckedpo.setDouble("MILEAGE", pdiCheckeddto.getMileAge());
			}
			pdicheckedpo.saveIt();

			// 处理“故障描述”的存储问题
			for (Map mapT3 : pdiCheckeddto.getDms_table3()) {
				String Remark = null;
				VehiclePdiResultDetailPO detailpo = new VehiclePdiResultDetailPO();
				detailpo.setString("VIN", vin);
				if (!StringUtils.isNullOrEmpty(mapT3.get("pdiRemark"))) {
					Remark = mapT3.get("pdiRemark").toString();
					detailpo.setString("PDI_REMARK", Remark);
				}
				detailpo.saveIt();
			}

			List query = new ArrayList();
			query.add(vin);
			List<Map> rList = DAOUtil.findAll("SELECT * FROM  tt_vehicle_pdi_result_detail  WHERE vin = ? ", query);

			StringBuffer sbString = new StringBuffer();
			if (!StringUtils.isNullOrEmpty(rList)) {
				for (int i = 0; i < rList.size(); i++) {
					Map map1 = rList.get(i);
					String remark = map1.get("PDI_REMARK").toString();

					if (i == rList.size() - 1) {
						sbString.append(remark);
					} else {
						sbString.append(remark).append(" ; ");
					}

				}
			}

			if (!StringUtils.isNullOrEmpty(sbString)) {
				Base.exec(" DELETE FROM  tt_vehicle_pdi_result_detail  WHERE vin = ? ", new Object[] { vin });
				VehiclePdiResultDetailPO detailpo = new VehiclePdiResultDetailPO();
				detailpo.setString("VIN", vin);
				detailpo.setString("PDI_REMARK", sbString);
				detailpo.saveIt();
			}

		}

	}

	@SuppressWarnings("rawtypes")
	@Override
	public Map editSearch(String vin) throws ServiceBizException {

		StringBuffer sb = new StringBuffer(
				" SELECT DEALER_CODE, TH_REPORT_NO,PDI_RESULT,PDI_CHECK_DATE,MILEAGE,PDI_SUBMIT_DATE,PDI_REMARK,PDI_ID,PDI_URL FROM tt_vehicle_pdi_result WHERE vin = ? ");
		List<Object> l = new ArrayList<Object>();
		l.add(vin);
		Map map = DAOUtil.findFirst(sb.toString(), l);
		return map;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> queryPDICheckedExport(Map<String, String> queryParam) {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT B.*,BR.BRAND_NAME,SE.SERIES_NAME, MO.MODEL_NAME,PA.CONFIG_NAME FROM ( ");
		sb.append(
				" SELECT f.MILEAGE AS MILEAGE,f.dealer_code AS DEALER_CODE, f.se_no AS SE_NO, f.vin AS VIN,f.IS_MUST_PDI AS IS_MUST_PDI, f.brand_code AS BRAND_CODE,f.series_code AS SERIES_CODE, f.model_code AS MODEL_CODE,f.config_code AS CONFIG_CODE,f.PDI_RESULT AS PDI_RESULT,f.PDI_CHECK_DATE AS PDI_CHECK_DATE,f.PDI_SUBMIT_DATE AS PDI_SUBMIT_DATE,f.PDI_URL AS PDI_URL,f.TH_REPORT_NO AS TH_REPORT_NO,f.PDI_REMARK AS PDI_REMARK ");
		sb.append(" FROM(  ");
		sb.append(
				" SELECT d.MILEAGE,a.dealer_code, a.se_no, b.vin,c.IS_MUST_PDI,c.brand_code,c.series_code,c.model_code,c.config_code,d.PDI_RESULT,d.PDI_CHECK_DATE,d.PDI_SUBMIT_DATE,d.PDI_URL,d.TH_REPORT_NO ,e.PDI_REMARK  AS PDI_REMARK FROM Tt_Vs_Stock_Entry a LEFT JOIN Tt_Vs_Stock_Entry_item b ON a.dealer_code=b.dealer_code AND a.SE_NO=b.SE_NO  LEFT JOIN tm_vs_product c ON a.dealer_code=c.dealer_code AND b.product_code=c.product_code LEFT JOIN TT_VEHICLE_PDI_RESULT d  ON a.dealer_code=d.dealer_code AND b.vin=d.vin LEFT JOIN TT_VEHICLE_PDI_RESULT_DETAIL E ON a.dealer_code=E.dealer_code AND E.vin=d.vin  GROUP BY d.MILEAGE,a.dealer_code, a.se_no, b.vin,c.IS_MUST_PDI,c.brand_code,c.series_code, c.model_code,c.config_code,d.PDI_RESULT,d.PDI_CHECK_DATE,d.PDI_SUBMIT_DATE,d.PDI_URL,d.TH_REPORT_NO ");
		sb.append(" UNION ALL ");
		sb.append(
				" SELECT e.MILEAGE,dealer_code, '' AS se_no, e.vin,e.IS_MUST_PDI,e.brand_code,e.series_code,e.model_code,e.config_code, e.PDI_RESULT,e.PDI_CHECK_DATE,e.PDI_SUBMIT_DATE,e.PDI_URL,e.TH_REPORT_NO ,e.PDI_REMARK ");
		sb.append(" FROM ( ");
		sb.append(
				" SELECT  d.MILEAGE,a.dealer_code,a.vin,c.IS_MUST_PDI,c.brand_code,c.series_code,c.model_code,c.config_code, d.PDI_RESULT,d.PDI_CHECK_DATE,d.PDI_SUBMIT_DATE, d.PDI_URL,d.TH_REPORT_NO ,E.PDI_REMARK AS PDI_REMARK FROM Tt_Vs_Shipping_Notify a LEFT JOIN tm_vs_product c ON  a.dealer_code=c.dealer_code   AND a.product_code=c.product_code LEFT JOIN TT_VEHICLE_PDI_RESULT d ON a.dealer_code=d.dealer_code  AND a.vin=d.vin   LEFT JOIN TT_VEHICLE_PDI_RESULT_DETAIL E ON a.dealer_code=E.dealer_code AND E.vin=d.vin WHERE  NOT EXISTS ( SELECT    1 FROM  Tt_Vs_Stock_Entry_item b WHERE  b.dealer_code=a.dealer_code  AND b.vin=a.vin )  GROUP BY d.MILEAGE,a.dealer_code, a.vin,c.IS_MUST_PDI,c.brand_code,c.series_code,c.model_code,c.config_code, d.PDI_RESULT,d.PDI_CHECK_DATE,d.PDI_SUBMIT_DATE, d.PDI_URL,d.TH_REPORT_NO ");
		sb.append("  )e)f  ");
		sb.append(" WHERE f.IS_MUST_PDI = 12781001 ");
		// 创建参数的list列表
		List<Object> insuranceSql = new ArrayList<Object>();

		if (!StringUtils.isNullOrEmpty(queryParam.get("IsCompletePDI"))) {
			// 完成检查
			if ("89991001".equals(queryParam.get("IsCompletePDI"))) {
				sb.append(
						"AND EXISTS (SELECT 1 FROM TT_VEHICLE_PDI_RESULT b WHERE f.dealer_code=b.dealer_code AND f.vin=b.vin )");
				if (!StringUtils.isNullOrEmpty(queryParam.get("PDI_RESULT"))) {
					sb.append(" AND f.PDI_RESULT like ? ");
					insuranceSql.add("%" + queryParam.get("PDI_RESULT") + "%");
				}
				if (!StringUtils.isNullOrEmpty(queryParam.get("PDI_REMARK"))) {
					sb.append(" AND f.PDI_REMARK like ? ");
					insuranceSql.add("%" + queryParam.get("PDI_REMARK") + "%");
				}

				if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate"))) {
					sb.append(" and f.PDI_CHECK_DATE >= ?");
					insuranceSql.add(DateUtil.parseDefaultDate(queryParam.get("beginDate")));
				}
				if (!StringUtils.isNullOrEmpty(queryParam.get("endDate"))) {
					sb.append(" and f.PDI_CHECK_DATE < ?");
					insuranceSql.add(DateUtil.addOneDay(queryParam.get("endDate")));
				}

				if (!StringUtils.isNullOrEmpty(queryParam.get("beginDate1"))) {
					sb.append(" and f.PDI_SUBMIT_DATE >= ?");
					insuranceSql.add(DateUtil.parseDefaultDate(queryParam.get("beginDate1")));
				}
				if (!StringUtils.isNullOrEmpty(queryParam.get("endDate1"))) {
					sb.append(" and f.PDI_SUBMIT_DATE < ?");
					insuranceSql.add(DateUtil.addOneDay(queryParam.get("endDate1")));
				}

				if (!StringUtils.isNullOrEmpty(queryParam.get("pdiResult"))) {
					sb.append(" AND f.PDI_RESULT like ? ");
					insuranceSql.add("%" + queryParam.get("pdiResult") + "%");
				}

			}
			// 没有完成检查
			else if ("89991002".equals(queryParam.get("IsCompletePDI"))) {
				sb.append(
						" AND NOT EXISTS (   SELECT 1 FROM Tt_Vs_Stock_Entry_item b    WHERE f.dealer_code=b.dealer_code AND f.vin=b.vin AND b.is_finished=12781001 ) AND NOT EXISTS (SELECT 1 FROM TT_VEHICLE_PDI_RESULT b WHERE f.dealer_code=b.dealer_code AND f.vin=b.vin ) ");
			}
		}
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
			sb.append(" AND f.VIN like ? ");
			insuranceSql.add("%" + queryParam.get("vin") + "%");
		}
		sb.append(" order by f.PDI_SUBMIT_DATE desc ");
		sb.append(
				" ) B LEFT JOIN tm_brand BR  ON B.BRAND_CODE = BR.BRAND_CODE  AND B.DEALER_CODE = BR.DEALER_CODE  LEFT JOIN TM_SERIES se  ON B.SERIES_CODE = se.SERIES_CODE  AND br.BRAND_CODE = se.BRAND_CODE  AND br.DEALER_CODE = se.DEALER_CODE  LEFT JOIN TM_MODEL mo  ON B.MODEL_CODE = mo.MODEL_CODE  AND mo.BRAND_CODE = se.BRAND_CODE  AND mo.series_code = se.series_code  AND se.DEALER_CODE = mo.DEALER_CODE  LEFT  JOIN   tm_configuration pa   ON   B.CONFIG_CODE=pa.CONFIG_CODE AND pa.brand_code=mo.brand_code AND pa.series_code=mo.series_code AND pa.model_code=mo.model_code AND mo.DEALER_CODE=pa.DEALER_CODE ");

		List<Map> resultList = DAOUtil.findAll(sb.toString(), insuranceSql);
		OperateLogDto operateLogDto = new OperateLogDto();
		operateLogDto.setOperateContent("PDI检查导出");
		operateLogDto.setOperateType(DictCodeConstants.LOG_CUSTOMER_MANAGEMENT);
		operateLogService.writeOperateLog(operateLogDto);
		return resultList;
	}

	public static String getTest(String businessType) {
		StringBuffer sql = new StringBuffer();
		String so_no = "SN1511120004";
		String dealer_code = "2100000";
		String dealerCode = "2100000";
		String soNo = "SN1611040003";
		String vin = "LWVCAF755FA006400";
		if (businessType.equals(CommonConstants.DICT_SO_TYPE_RERURN)) {
			sql.append(
					" SELECT DISTINCT ef.PRODUCTING_AREA, ef.ENGINE_NO, ef.CERTIFICATE_NUMBER,TA1.*,B.WHOLESALE_DIRECTIVE_PRICE,B.REMARK,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,B.CONFIG_CODE, ");
			sql.append(" B.PRODUCT_NAME AS PRODUCT_NAME,COALESCE(TA1.COLOR_CODE,B.COLOR_CODE) as COLOR_CODE ");
			sql.append(" FROM ( ");
			sql.append(
					" SELECT A.*,C.VEHICLE_PRICE AS OLD_VEHICLE_PRICE,C.ORDER_PAYED_AMOUNT  AS OLD_ORDER_PAYED_AMOUNT FROM TT_SALES_ORDER A,TT_SALES_ORDER C ");
			sql.append(" WHERE 1=1 AND A.OLD_SO_NO = C.SO_NO AND A.DEALER_CODE = C.DEALER_CODE AND A.DEALER_CODE = '"
					+ dealer_code + "'");
			sql.append(" AND A.SO_NO = '" + so_no + "' AND A.D_KEY = " + CommonConstants.D_KEY + ") TA1 ");
			sql.append(" LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT
					+ ") B  ON TA1.PRODUCT_CODE = B.PRODUCT_CODE AND TA1.DEALER_CODE = B.DEALER_CODE AND TA1.D_KEY = B.D_KEY ");
			sql.append(
					" left join TT_VS_STOCK_ENTRY_ITEM ef on (ef.DEALER_CODE = TA1.DEALER_CODE and ef.vin = TA1.vin) ");
		} else {
			sql.append(
					" SELECT DISTINCT ef.PRODUCTING_AREA, ef.ENGINE_NO, ef.CERTIFICATE_NUMBER,A.*,   B.WHOLESALE_DIRECTIVE_PRICE,B.REMARK,B.BRAND_CODE,B.SERIES_CODE,B.MODEL_CODE,0 AS OLD_VEHICLE_PRICE,0  AS OLD_ORDER_PAYED_AMOUNT, ");
			sql.append(" B.CONFIG_CODE, B.PRODUCT_NAME AS PRODUCT_NAME,B.COLOR_CODE,O.ORG_NAME ");
			
			if (null != businessType && businessType.equals(CommonConstants.DICT_SO_TYPE_GENERAL)) {
				sql.append(" ,TA1.WS_NO,TA1.SALES_ITEM_ID,TA1.WS_TYPE ");
			}
			
			if (CommonConstants.DICT_SO_TYPE_ALLOCATION.equals(businessType)) {
				sql.append(" ,TAB1.DEALER_CODE ");
			}

			sql.append(" FROM TT_SALES_ORDER A ");
			sql.append(" LEFT JOIN (" + CommonConstants.VM_VS_PRODUCT
					+ ") B  ON A.PRODUCT_CODE = B.PRODUCT_CODE AND A.DEALER_CODE = B.DEALER_CODE AND A.D_KEY= B.D_KEY ");
			sql.append(" LEFT JOIN TM_USER U ON A.DEALER_CODE = U.DEALER_CODE AND A.SOLD_BY= U.USER_ID ");
			sql.append(" LEFT JOIN TM_ORGANIZATION O ON A.DEALER_CODE = O.DEALER_CODE AND U.ORG_CODE=O.ORG_CODE ");
			
			if (null != businessType && businessType.equals(CommonConstants.DICT_SO_TYPE_GENERAL)) {
				sql.append(
						" LEFT JOIN ( SELECT DISTINCT N.DEALER_CODE,N.SALES_ITEM_ID,M.WS_NO,N.SO_NO,N.VIN,K.WS_TYPE ");
				sql.append("  FROM TT_PO_CUS_WHOLESALE K, TT_WS_CONFIG_INFO M,TT_WS_SALES_INFO N ");
				sql.append(" WHERE M.DEALER_CODE = N.DEALER_CODE AND M.ITEM_ID = N.ITEM_ID ");
				sql.append(" AND K.DEALER_CODE = M.DEALER_CODE AND K.WS_NO = M.WS_NO AND K.WS_STATUS = "
						+ DictCodeConstants.DICT_WHOLESALE_STATUS_PASS);
				Utility.getStrCond("N", "DEALER_CODE", dealerCode);
				Utility.getStrCond("N", "SO_NO", soNo);
				Utility.getStrCond("N", "VIN", vin);
				sql.append(") TA1 ON A.DEALER_CODE = TA1.DEALER_CODE AND A.SO_NO = TA1.SO_NO ");
			}
			
			if (CommonConstants.DICT_SO_TYPE_ALLOCATION.equals(businessType)) {
				sql.append(
						" LEFT JOIN TM_PART_CUSTOMER TAB1 ON A.DEALER_CODE=TAB1.DEALER_CODE and A.CONSIGNEE_CODE=TAB1.CUSTOMER_CODE ");
			}
			
			sql.append(" left join TT_VS_STOCK_ENTRY_ITEM ef on (ef.DEALER_CODE = a.DEALER_CODE and ef.vin = a.vin) ");
			sql.append(" WHERE 1=1 AND  A.D_KEY = " + CommonConstants.D_KEY);
			sql.append(Utility.getStrCond("A", "SO_NO", soNo));
			sql.append(Utility.getStrCond("A", "DEALER_CODE", dealerCode));
		}

		return sql.toString();
	}

	public static void main(String[] args) {
		System.out.println("*********************************");
		// 13001002 13001001
		System.out.println(getTest("13001002"));
		System.out.println("*********************************");
	}

}
