package com.yonyou.dms.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.yonyou.dms.framework.DAO.DAOUtil;
import com.yonyou.dms.framework.DAO.PageInfoDto;
import com.yonyou.dms.function.common.CommonConstants;
import com.yonyou.dms.function.common.DictCodeConstants;
import com.yonyou.dms.function.exception.ServiceBizException;
import com.yonyou.dms.function.utils.common.StringUtils;

/**
 * 车辆入库明细表Service实现类
 * @author Benzc
 * @date 2017年1月17日
 */
@Service
public class vehicleInStoreServiceImpl implements vehicleInStoreService{
	
    /**
     * 分页查询
     * @author Benzc
     * @date 2017年1月17日
     */
	@Override
	public PageInfoDto queryVehicleInStore(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT a.* ,CASE WHEN (a.INSPECTION_CONSIGNED IS NULL) THEN '12781002' ELSE a.INSPECTION_CONSIGNED END INSPECTION_CONSIGNED2,");
		sb.append(" CASE WHEN a.ADDITIONAL_COST IS NULL THEN 0 ELSE a.ADDITIONAL_COST END ADDITIONAL_COST2,");
		sb.append(" a.FINISHED_DATE AS INSTOCK_DATE,c.STOCK_STATUS AS VEH_STATUS,b.MODEL_CODE AS MODEL,b.DIRECTIVE_PRICE AS SALES_ADVICE_PRICE,e.MODEL_NAME,f.COLOR_NAME,g.STORAGE_NAME"); 
		sb.append(" FROM TT_VS_STOCK_ENTRY_ITEM a" );
		sb.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") b ON a.dealer_code=b.dealer_code AND a.d_key=b.d_key AND a.product_code=b.product_code");
		sb.append(" LEFT JOIN TM_VS_STOCK c ON a.dealer_code=c.dealer_code AND a.vin=c.vin" );
		sb.append(" LEFT JOIN TT_VS_STOCK_ENTRY d ON d.dealer_code=a.dealer_code AND d.SE_NO=a.SE_NO");
		sb.append(" LEFT JOIN TM_MODEL e ON e.MODEL_CODE = b.MODEL_CODE");
		sb.append(" LEFT JOIN TM_COLOR f ON f.COLOR_CODE = b.COLOR_CODE");
		sb.append(" LEFT JOIN TM_STORAGE g ON g.STORAGE_CODE = a.STORAGE_CODE");
		sb.append(" WHERE 1=1");
		List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        PageInfoDto result = DAOUtil.pageQuery(sb.toString(), queryList);
        return result;
	}
    
	/**
	 * 查询条件设置
	 * @author Benzc
	 * @date 2017年1月17日
	 * @param sb
	 * @param queryParam
	 * @param queryList
	 */
	private void setWhere(StringBuffer sb, Map<String, String> queryParam, List<Object> queryList) {
		if (!StringUtils.isNullOrEmpty(queryParam.get("startdate"))) {
            sb.append(" and a.FINISHED_DATE >= ?");
            queryList.add(queryParam.get("startdate")+" 00:00:00");
        }	
		if (!StringUtils.isNullOrEmpty(queryParam.get("enddate"))) {
            sb.append(" and a.FINISHED_DATE <= ?");
            queryList.add(queryParam.get("enddate")+" 23:59:59");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("brandCode"))) {
            sb.append(" and b.brand_code = ?");
            queryList.add(queryParam.get("brandCode"));
        }	
		if (!StringUtils.isNullOrEmpty(queryParam.get("seriesCode"))) {
            sb.append(" and b.series_code = ?");
            queryList.add(queryParam.get("seriesCode"));
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("modelCode"))) {
            sb.append(" and b.model_code = ?");
            queryList.add(queryParam.get("modelCode"));
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("vin"))) {
            sb.append(" and a.vin like ?");
            queryList.add("%"+queryParam.get("vin")+"%");
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("isInStock"))) {
            sb.append(" and a.is_finished = ?");
            queryList.add(queryParam.get("isInStock"));
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockInType"))) {
            sb.append(" and d.stock_in_type = ?");
            queryList.add(queryParam.get("stockInType"));
        }
		if (!StringUtils.isNullOrEmpty(queryParam.get("stockName"))) {
            sb.append(" and a.storage_code = ?");
            queryList.add(queryParam.get("stockName"));
        }
	}
    
	/**
	 * @author Benzc
	 * @date 2017年1月17日
	 * 查询仓库信息下拉选
	 */
	@SuppressWarnings("rawtypes")
	@Override
	public List<Map> storeSelect(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT DEALER_CODE,STORAGE_CODE,STORAGE_NAME FROM TM_STORAGE WHERE OEM_TAG='12781002' AND 1=1");
		List<Object> list = new ArrayList<Object>();
		List<Map> result = DAOUtil.findAll(sb.toString(),list);
		return result;
	}
    
	/**
	 * 导出
	 * @author Benzc
	 * @date 2017年1月17日
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public List<Map> queryInStoreExport(Map<String, String> queryParam) throws ServiceBizException {
		StringBuffer sb = new StringBuffer();
		sb.append(" SELECT DISTINCT a.* ,CASE WHEN (a.INSPECTION_CONSIGNED IS NULL) THEN '12781002' ELSE a.INSPECTION_CONSIGNED END INSPECTION_CONSIGNED2,a.FINISHED_DATE AS INSTOCK_DATE,c.STOCK_STATUS AS VEH_STATUS,b.MODEL_CODE AS MODEL,b.DIRECTIVE_PRICE AS SALES_ADVICE_PRICE,e.MODEL_NAME,f.COLOR_NAME,g.STORAGE_NAME"); 
		sb.append(" FROM TT_VS_STOCK_ENTRY_ITEM a" );
		sb.append(" LEFT JOIN ("+CommonConstants.VM_VS_PRODUCT+") b ON a.dealer_code=b.dealer_code AND a.d_key=b.d_key AND a.product_code=b.product_code");
		sb.append(" LEFT JOIN TM_VS_STOCK c ON a.dealer_code=c.dealer_code AND a.vin=c.vin" );
		sb.append(" LEFT JOIN TT_VS_STOCK_ENTRY d ON d.dealer_code=a.dealer_code AND d.SE_NO=a.SE_NO");
		sb.append(" LEFT JOIN TM_MODEL e ON e.MODEL_CODE = b.MODEL_CODE");
		sb.append(" LEFT JOIN TM_COLOR f ON f.COLOR_CODE = b.COLOR_CODE");
		sb.append(" LEFT JOIN TM_STORAGE g ON g.STORAGE_CODE = a.STORAGE_CODE");
		sb.append(" WHERE 1=1");
		List<Object> queryList = new ArrayList<Object>();
        this.setWhere(sb, queryParam, queryList);
        List<Map> list = DAOUtil.findAll(sb.toString(), queryList);
        for (Map map : list) {
        	if (map.get("VEH_STATUS") != null && map.get("VEH_STATUS") != "") {
                if (Integer.parseInt(map.get("VEH_STATUS").toString()) == 13041001) {
                    map.put("VEH_STATUS", "出库");
                } else if (Integer.parseInt(map.get("VEH_STATUS").toString()) == 13041002) {
                    map.put("VEH_STATUS", "在库");
                } else if (Integer.parseInt(map.get("VEH_STATUS").toString()) == 13041003) {
                    map.put("VEH_STATUS", "在途");
                } else if (Integer.parseInt(map.get("VEH_STATUS").toString()) == 13041004) {
                    map.put("VEH_STATUS", "借出");
                }
            }
        	if (map.get("INSPECTION_CONSIGNED2") != null && map.get("INSPECTION_CONSIGNED2") != "") {
                if (Integer.parseInt(map.get("INSPECTION_CONSIGNED2").toString()) == DictCodeConstants.STATUS_IS_YES) {
                    map.put("INSPECTION_CONSIGNED2", "是");
                } else if (Integer.parseInt(map.get("INSPECTION_CONSIGNED2").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                    map.put("INSPECTION_CONSIGNED2", "否");
                }
            }
        	if (map.get("IS_DIRECT") != null && map.get("IS_DIRECT") != "") {
                if (Integer.parseInt(map.get("IS_DIRECT").toString()) == DictCodeConstants.STATUS_IS_YES) {
                    map.put("IS_DIRECT", "是");
                } else if (Integer.parseInt(map.get("IS_DIRECT").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                    map.put("IS_DIRECT", "否");
                }
            }
        	if (map.get("HAS_CERTIFICATE") != null && map.get("HAS_CERTIFICATE") != "") {
                if (Integer.parseInt(map.get("HAS_CERTIFICATE").toString()) == DictCodeConstants.STATUS_IS_YES) {
                    map.put("HAS_CERTIFICATE", "是");
                } else if (Integer.parseInt(map.get("HAS_CERTIFICATE").toString()) == DictCodeConstants.STATUS_IS_NOT) {
                    map.put("HAS_CERTIFICATE", "否");
                }
            }
        }
		return list;
	}

}
